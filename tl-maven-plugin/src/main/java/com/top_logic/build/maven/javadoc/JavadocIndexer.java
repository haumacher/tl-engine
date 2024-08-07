/*
 * SPDX-FileCopyrightText: 2020 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.build.maven.javadoc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Utility for creating an index of a JavaDoc tree generated by the <i>TopLogic</i> doclet.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
@Mojo(name = "javadoc-index", requiresProject = true, aggregator = true, inheritByDefault = false)
public class JavadocIndexer extends AbstractMojo {

	private static final int MAX_PREFIX_LEN = 33;

	private static final String SEPARATOR_PATTERN = "[^a-zA-Z0-9]+";

	static final Pattern TERM_SPLITTER = Pattern.compile("^" + "|" + SEPARATOR_PATTERN + "|" + "(?=[A-Z][a-z])");

	@Parameter(name = "javadocDir", required = true, property = "tl.javadoc.dir")
	private File _javadocDir;

	@Parameter(name = "compress", defaultValue = "true", property = "tl.javadoc.compress")
	private boolean _compress;

	@Parameter(name = "skip", defaultValue = "false", property = "tl.javadoc.skipIndex")
	private boolean _skip;

	private Map<String, Name> _names = new HashMap<>();

	private Map<String, Pkg> _packages = new HashMap<>();

	private Map<Type, Type> _types = new HashMap<>();

	private List<Member> _methods = new ArrayList<>();

	private List<Member> _fields = new ArrayList<>();

	private Map<Name, List<Item>> _praefixIndex = new HashMap<>();

	private Map<Name, List<Item>> _suffixIndex = new HashMap<>();

	private Map<Type, List<Item>> _usageReturn = new HashMap<>();

	private Map<Type, List<Item>> _usageParam = new HashMap<>();

	private Map<Type, List<Type>> _specializations = new HashMap<>();

	/**
	 * Whether to compress the generated index. Default is <code>true</code>.
	 */
	public void setCompress(boolean compress) {
		_compress = compress;
	}

	/**
	 * Whether the execution should be skipped.
	 */
	public void setSkip(boolean skip) {
		_skip = skip;
	}

	/**
	 * Sets the directory that contains the generated JavaDoc files to index.
	 */
	public void setJavadocDir(File javadocDir) {
		_javadocDir = javadocDir;
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (_skip) {
			getLog().info("Skipping JavaDoc indexing.");
			return;
		}
		try {
			buildIndex();

			try (FileWriter out = new FileWriter(new File(_javadocDir, "index.json"))) {
				writeIndex(out);
			}

			writeXRef(_javadocDir);
		} catch (IOException | ParserConfigurationException ex) {
			throw new MojoFailureException("Failed to index JavaDoc: " + ex.getMessage(), ex);
		}
	}

	private void buildIndex() throws IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();

		Path root = _javadocDir.toPath();
		Files.walk(root).filter(p -> {
			String name = p.getFileName().toString();
			return name.endsWith(".xml")
				&& !name.equals("package-info.xml")
				&& !name.equals("package-list.xml");
		}).forEach(p -> {
			try {
				org.w3c.dom.Document xmlDoc = builder.parse(p.toFile());

				Element typeElement = xmlDoc.getDocumentElement();
				String typeName = typeElement.getAttribute("name");

				String packageName = packageName(typeName);
				String localId = localName(typeName);

				synchronized (JavadocIndexer.class) {
					Pkg pkg = enterPackage(packageName);
					Type type = enterType(pkg, localId);
					type.markIndexed();

					indexItemSuffix(type, localId);
					indexAnnotations(typeElement, type);

					addSpecializations(type, typeElement);
					addMembers(type, typeElement);
				}
			} catch (SAXException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
	}

	private void indexAnnotations(Element element, Item item) {
		for (Element annotationsElement : elements(element, "annotations")) {
			for (Element annotationElement : elements(annotationsElement, "annotation")) {
				indexItem(item, localName(annotationElement.getAttribute("id")));
				for (Element paramsElement : elements(annotationElement, "params")) {
					for (Element paramElement : elements(paramsElement, "param")) {
						for (Element valueElement : elements(paramElement, "value")) {
							if ("string".equals(valueElement.getAttribute("kind"))) {
								indexItem(item, valueElement.getAttribute("label"));
							}
						}
					}
				}
			}
		}
	}

	private void addSpecializations(Type type, Element typeElement) {
		for (Element interfacesElement : elements(typeElement, "implements")) {
			for (Element intfElement : elements(interfacesElement, "type")) {
				Type intf = enterType(intfElement.getAttribute("id"));

				addIndexEntry(_specializations, type, intf);
			}
		}
		for (Element extendsElement : elements(typeElement, "extends")) {
			Type superType = enterType(extendsElement.getAttribute("id"));
			addIndexEntry(_specializations, type, superType);
		}
	}

	private void writeIndex(Writer out) throws IOException {
		ArrayList<Name> nameList = new ArrayList<>(_names.values());
		nameList.sort(Name.SEARCH_ORDER);
		assignIds(0, nameList);

		ArrayList<Pkg> pkgList = new ArrayList<>(_packages.values());
		pkgList.sort(Pkg.INDEX_ORDER);
		int firstTypeId = assignIds(0, pkgList);

		ArrayList<Type> typeList = new ArrayList<>(_types.keySet());
		typeList.sort(Type.TYPE_INDEX_ORDER);
		int firstMethodId = assignIds(firstTypeId, typeList);

		_methods.sort(Member.MEMBER_INDEX_ORDER);
		int firstFieldId = assignIds(firstMethodId, _methods);

		_fields.sort(Member.MEMBER_INDEX_ORDER);
		assignIds(firstFieldId, _fields);

		out.write("{\n");

		out.write("\"compressed\":" + _compress + ",\n");

		out.write("\"names\":[\n");
		String lastValue = "";
		for (int n = 0, cnt = nameList.size(); n < cnt; n++) {
			Name name = nameList.get(n);
			if (n > 0) {
				out.write(",\n");
			}
			String value = name.getName();
			String targetValue;
			if (_compress) {
				int prefix = prefixLength(lastValue, value);
				char encodedPrefixLengh = (char) (48 + prefix + (prefix > 0 ? getPrefixOffset(lastValue, value) : 0));
				targetValue = encodedPrefixLengh + value.substring(prefix);
			} else {
				targetValue = value;
			}
			out.write("\"" + targetValue + "\"");

			lastValue = value;
		}
		out.write("],\n");

		out.write("\"packages\":[\n");
		int lastPkgName = 0, lastPkgOuter = 0;
		for (int n = 0, cnt = pkgList.size(); n < cnt; n++) {
			Pkg item = pkgList.get(n);
			if (n > 0) {
				out.write(",\n");
			}
			int nameId = item._name.getId();
			out.write("[" + delta(nameId, lastPkgName));
			lastPkgName = nameId;

			if (item._outer != null) {
				int outerId = item._outer.getId();
				out.write(", " + delta(outerId, lastPkgOuter));
				lastPkgOuter = outerId;
			}

			out.write("]");
		}
		out.write("],\n");

		out.write("\"types\":[\n");
		int lastTypeName = 0, lastTypeSuper = 0;
		for (int n = 0, cnt = typeList.size(); n < cnt; n++) {
			Type item = typeList.get(n);
			if (n > 0) {
				out.write(",\n");
			}

			int typeName = item._name.getId();
			int typeSuper = item._pkg.getId();
			out.write("[" + delta(typeName, lastTypeName) + "," + delta(typeSuper, lastTypeSuper) + "]");

			lastTypeName = typeName;
			lastTypeSuper = typeSuper;
		}
		out.write("],\n");

		writeMemberTable(out, "methods", firstTypeId, _methods);
		out.write(",\n");

		writeMemberTable(out, "fields", firstTypeId, _fields);
		out.write(",\n");

		writeIndex(out, _praefixIndex, "praefixes");
		out.write(",\n");

		writeIndex(out, _suffixIndex, "suffixes");
		out.write(",\n");

		writeIndex(out, _usageReturn, "usageReturn");
		out.write(",\n");

		writeIndex(out, _usageParam, "usageParam");
		out.write("\n");

		out.write("}\n");
	}

	private void writeMemberTable(Writer out, String tableName, int firstTypeId, List<Member> list)
			throws IOException {
		IntBuffer lastMethodIds = new IntBuffer();
		IntBuffer methodIds = new IntBuffer();

		out.write("\"" + tableName + "\":[\n");
		for (int n = 0, cnt = list.size(); n < cnt; n++) {
			Member item = list.get(n);
			if (n > 0) {
				out.write(",\n");
			}

			methodIds.clear();
			methodIds.add(item._name.getId());

			// Note: Use local type IDs for monomorphic type references from methods
			methodIds.add(item._type.getId() - firstTypeId);
			for (Type param : item._sig) {
				methodIds.add(param.getId() - firstTypeId);
			}

			if (_compress) {
				lastMethodIds.delta(methodIds);
			} else {
				lastMethodIds.copy(methodIds);
			}

			out.write("[");
			for (int i = 0, length = lastMethodIds.length(); i < length; i++) {
				if (i > 0) {
					out.write(",");
					out.write(Integer.toString(lastMethodIds.get(i)));
				} else {
					out.write(Integer.toString(lastMethodIds.get(i)));
				}
			}
			out.write("]");

			if (_compress) {
				lastMethodIds.copy(methodIds);
			}
		}
		out.write("]");
	}

	private int delta(int value, int base) {
		return _compress ? value - base : value;
	}

	private static int prefixLength(String s1, String s2) {
		int minLen = Math.min(s1.length(), s2.length());
		if (minLen == 0) {
			return 0;
		}

		if (Character.toLowerCase(s1.charAt(0)) != Character.toLowerCase(s2.charAt(0))) {
			return 0;
		}

		int maxPrefix = minLen < MAX_PREFIX_LEN ? minLen : MAX_PREFIX_LEN;
		int result = 1;
		while (result < maxPrefix) {
			if (s1.charAt(result) != s2.charAt(result)) {
				break;
			}
			result++;
		}

		return result;
	}

	private static int getPrefixOffset(String s1, String s2) {
		// Case of first character is encoded in the prefix length char.
		// Note: 92 is the character code for backslash. This character must not occur in a JSON
		// string, since it is the escape character. Since the offset is added to the prefix length,
		// which is at least one, this condition holds: 48 ('0') is added to all prefix lengths.
		int offset = s1.charAt(0) == s2.charAt(0) ? 0 : 44;
		return offset;
	}

	private int assignIds(int first, List<? extends WithId> nameList) {
		int id = first;
		for (int n = 0, cnt = nameList.size(); n < cnt; n++) {
			WithId name = nameList.get(n);
			name.initId(id++);
		}
		return id;
	}

	private <K extends WithId, V extends Item> void writeIndex(Writer out,
			Map<K, ? extends List<? extends V>> index,
			String indexName) throws IOException {
		ArrayList<K> nameList = new ArrayList<>(index.keySet());
		nameList.sort((k1, k2) -> Integer.compare(k1.getId(), k2.getId()));

		int lastNameId = 0;
		int idOffset = 0;
		out.write("\"" + indexName + "\":[\n");
		for (int n = 0, cnt = nameList.size(); n < cnt; n++) {
			if (n > 0) {
				out.write(",\n");
			}

			K name = nameList.get(n);
			int nameId = name.getId();

			out.write("[" + delta(nameId, lastNameId));
			lastNameId = nameId;

			List<? extends V> list = index.get(name);
			list.sort((x, y) -> Integer.compare(x.getId(), y.getId()));
			int lastId = -1;

			int delta = idOffset;
			for (int i = 0, size = list.size(); i < size; i++) {
				V ref = list.get(i);
				int id = ref.getId();
				if (id == lastId) {
					// Make unique, rare case where items are indexed multiple times with the same
					// token.
					continue;
				}
				out.write("," + delta(id, delta));
				lastId = id;
				delta = id;
			}
			idOffset = list.get(0).getId();

			out.write("]");
		}
		out.write("]");
	}

	private void writeXRef(File javaDocDir) throws IOException {
		for (Type type : _types.keySet()) {
			if (!type.isIndexed()) {
				// No specialization info for external classes.
				continue;
			}

			File jsonFile = new File(javaDocDir, type.qName().replace(".", "/") + "-xref.json");
			try (Writer out = new OutputStreamWriter(new FileOutputStream(jsonFile))) {
				out.write("{\n");
				out.write("\"specializations\":[\n");
				List<Type> specializationsOfType = _specializations.get(type);
				if (specializationsOfType != null) {
					specializationsOfType.sort(Type.QNAME_ORDER);

					boolean first = true;
					for (Type specialization : specializationsOfType) {
						if (first) {
							first = false;
						} else {
							out.write(",\n");
						}
						out.write("\"");
						out.write(specialization.qName());
						out.write("\"");
					}
				}
				out.write("\n]");
				out.write("}");
			}
		}
	}

	private void addMembers(Type type, Element typeElement) {
		for (Element child : elements(typeElement, "methods")) {
			for (Element method : elements(child, "method")) {
				addMethod(type, method);
			}
		}
		for (Element child : elements(typeElement, "fields")) {
			for (Element field : elements(child, "field")) {
				addField(type, field);
			}
		}
	}

	private void addField(Type type, Element memberElement) {
		Member member = member(type, memberElement.getAttribute("id"));
		_fields.add(member);

		indexAnnotations(memberElement, member);

		for (Element typeElement : elements(memberElement, "type")) {
			String typeId = typeElement.getAttribute("id");
			indexItem(member, localTypeName(typeId));
			addIndexEntry(_usageReturn, member, enterType(typeId));
		}
		String value = memberElement.getAttribute("value");
		if (value != null && !value.isEmpty()) {
			indexItem(member, value);
		}
	}

	private void addMethod(Type type, Element memberElement) {
		String overrides = memberElement.getAttribute("overrides");
		if (overrides != null && !overrides.isEmpty()) {
			return;
		}
		Member member = member(type, memberElement.getAttribute("id"));
		_methods.add(member);

		indexAnnotations(memberElement, member);

		// In case of method:
		for (Element returnElement : elements(memberElement, "return")) {
			for (Element typeElement : elements(returnElement, "type")) {
				String typeId = typeElement.getAttribute("id");
				indexItem(member, localTypeName(typeId));
				addIndexEntry(_usageReturn, member, enterType(typeId));
			}
		}
		for (Element paramsElement : elements(memberElement, "params")) {
			for (Element paramElement : elements(paramsElement, "param")) {
				indexItem(member, paramElement.getAttribute("name"));

				for (Element typeElement : elements(paramElement, "type")) {
					String typeId = typeElement.getAttribute("id");
					indexItem(member, localTypeName(typeId));
					addIndexEntry(_usageParam, member, enterType(typeId));
				}
			}
		}
	}

	private Member member(Type type, String signature) {
		List<Type> sig = new ArrayList<>(3);
		int start = signature.indexOf('(');
		String name;
		if (start >= 0) {
			name = signature.substring(0, start);
			while (true) {
				start++;
				int next = signature.indexOf(',', start);
				if (next < 0) {
					break;
				}

				sig.add(enterType(signature.substring(start, next)));
				start = next;
			}
			sig.add(enterType(signature.substring(start, signature.length() - 1)));
		} else {
			name = signature;
		}
		return new Member(type, name(name), sig);
	}

	private void indexItem(Item item, String fullName) {
		index(item, fullName, false);
	}

	private void indexItemSuffix(Item item, String fullName) {
		index(item, fullName, true);
	}

	private <T extends Item> void index(T item, String fullName, boolean skipFirst) {
		Matcher matcher = TERM_SPLITTER.matcher(fullName);
		if (skipFirst) {
			if (!matcher.find()) {
				return;
			}
		} else {
			if (matcher.find()) {
				addIndexEntry(_praefixIndex, item,
					name(fullName.substring(matcher.start()).replaceAll(SEPARATOR_PATTERN, "")));
			} else {
				return;
			}
		}
		while (matcher.find()) {
			addIndexEntry(_suffixIndex, item,
				fullName.substring(matcher.start()).replaceAll(SEPARATOR_PATTERN, ""));
		}
	}

	private void addIndexEntry(Map<Name, List<Item>> index, Item item, String text) {
		if (text.isEmpty()) {
			return;
		}
		addIndexEntry(index, item, name(text));
	}

	private <K, V> void addIndexEntry(Map<K, List<V>> index, V item, K key) {
		List<V> list = index.get(key);
		if (list == null) {
			list = new ArrayList<>(3);
			index.put(key, list);
		}
		list.add(item);
	}

	Type enterType(String qName) {
		return enterType(enterPackage(packageName(qName)), localName(qName));
	}

	private Type enterType(Pkg pkg, String localName) {
		Type type = new Type(name(localName), pkg);

		Type orig = _types.get(type);
		if (orig != null) {
			return orig;
		}

		_types.put(type, type);
		return type;
	}

	private Pkg enterPackage(String name) {
		Pkg pkg = _packages.get(name);
		if (pkg == null) {
			String outerName = packageName(name);
			Pkg outer;
			if (outerName.isEmpty()) {
				outer = null;
			} else {
				outer = enterPackage(outerName);
			}
			pkg = new Pkg(name(localName(name)), outer);
			_packages.put(name, pkg);
		}
		return pkg;
	}

	private Name name(String name) {
		Name existing = _names.get(name);
		if (existing != null) {
			return existing;
		}

		Name newName = new Name(name);
		_names.put(name, newName);
		return newName;
	}

	private static Iterable<Element> elements(Node parent, String name) {
		return new Iterable<>() {
			@Override
			public Iterator<Element> iterator() {
				return new Iterator<>() {
					Element _child = next(parent.getFirstChild());

					private Element next(Node child) {
						while (child != null
							&& (child.getNodeType() != Node.ELEMENT_NODE || !child.getLocalName().equals(name))) {
							child = child.getNextSibling();
						}
						return (Element) child;
					}

					@Override
					public boolean hasNext() {
						return _child != null;
					}

					@Override
					public Element next() {
						if (!hasNext()) {
							throw new NoSuchElementException();
						}
						Element result = _child;
						_child = next(_child.getNextSibling());
						return result;
					}
				};
			}
		};
	}

	static abstract class WithId {

		public static Comparator<WithId> ID_ORDER = new Comparator<>() {
			@Override
			public int compare(WithId o1, WithId o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}
		};

		private int _id;

		final int getId() {
			return _id;
		}

		void initId(int id) {
			_id = id;
		}
	}

	static class Name extends WithId {

		protected static final Comparator<Name> SEARCH_ORDER = new Comparator<>() {
			@Override
			public int compare(Name o1, Name o2) {
				int searchCompare = o1._seachName.compareTo(o2._seachName);
				if (searchCompare != 0) {
					return searchCompare;
				}

				return o1.getName().compareTo(o2.getName());
			}
		};

		private final String _name;

		final String _seachName;

		public Name(String name) {
			_name = name;
			_seachName = name.toLowerCase().replaceAll("[-_\\$\\.]", "");
		}

		public final String getName() {
			return _name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Name other = (Name) obj;
			if (getName() == null) {
				if (other.getName() != null)
					return false;
			} else if (!getName().equals(other.getName()))
				return false;
			return true;
		}

	}

	static abstract class Item extends WithId {
		protected static final Comparator<Item> NAME_ORDER = new Comparator<>() {
			@Override
			public int compare(Item o1, Item o2) {
				return Name.SEARCH_ORDER.compare(o1._name, o2._name);
			}
		};

		final Name _name;

		public Item(Name name) {
			_name = name;
		}
	}

	static class Pkg extends Item {

		public static final Comparator<? super Pkg> INDEX_ORDER = new Comparator<>() {
			@Override
			public int compare(Pkg o1, Pkg o2) {
				int nameOrder = Item.NAME_ORDER.compare(o1, o2);
				if (nameOrder != 0) {
					return nameOrder;
				}

				if (o1._outer == null) {
					if (o2._outer == null) {
						return 0;
					} else {
						return -1;
					}
				} else {
					if (o2._outer == null) {
						return 1;
					} else {
						return compare(o1._outer, o2._outer);
					}
				}
			}
		};

		protected static final Comparator<Pkg> QNAME_ORDER = new Comparator<>() {
			@Override
			public int compare(Pkg o1, Pkg o2) {
				return o1.qName().compareTo(o2.qName());
			}
		};

		final Pkg _outer;

		public Pkg(Name name, Pkg outer) {
			super(name);
			_outer = outer;
		}

		public String qName() {
			return (_outer != null ? _outer.qName() + "." : "") + _name.getName();
		}

	}

	static class Type extends Item {

		public static final Comparator<? super Type> QNAME_ORDER = new Comparator<>() {
			@Override
			public int compare(Type t1, Type t2) {
				int pkgCompare = Pkg.QNAME_ORDER.compare(t1._pkg, t2._pkg);
				if (pkgCompare != 0) {
					return pkgCompare;
				}

				return WithId.ID_ORDER.compare(t1._name, t2._name);
			}
		};

		public static final Comparator<? super Type> TYPE_INDEX_ORDER = new Comparator<>() {
			@Override
			public int compare(Type t1, Type t2) {
				int nameCompare = WithId.ID_ORDER.compare(t1._name, t2._name);
				if (nameCompare != 0) {
					return nameCompare;
				}

				return WithId.ID_ORDER.compare(t1._pkg, t2._pkg);
			}
		};

		final Pkg _pkg;

		private boolean _indexed;

		public Type(Name name, Pkg pkg) {
			super(name);
			_pkg = pkg;
		}

		public boolean isIndexed() {
			return _indexed;
		}

		public void markIndexed() {
			_indexed = true;
		}

		public String qName() {
			String pkgName = _pkg.qName();
			return pkgName.isEmpty() ? "" : (pkgName + ".") + _name.getName();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((_name == null) ? 0 : _name.hashCode());
			result = prime * result + ((_pkg == null) ? 0 : _pkg.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Type other = (Type) obj;
			if (_name == null) {
				if (other._name != null)
					return false;
			} else if (!_name.equals(other._name))
				return false;
			if (_pkg == null) {
				if (other._pkg != null)
					return false;
			} else if (!_pkg.equals(other._pkg))
				return false;
			return true;
		}

	}

	static class Member extends Item {

		/**
		 * Order of {@link Member}s in the method table of the index.
		 */
		public static final Comparator<? super Member> MEMBER_INDEX_ORDER = new Comparator<>() {
			@Override
			public int compare(Member m1, Member m2) {
				if (m1 == m2) {
					return 0;
				}

				int itemCompare = WithId.ID_ORDER.compare(m1._name, m2._name);
				if (itemCompare != 0) {
					return itemCompare;
				}

				int typeCompare = WithId.ID_ORDER.compare(m1._type, m2._type);
				if (typeCompare != 0) {
					return typeCompare;
				}

				for (int n = 0, cnt = Math.min(m1._sig.size(), m2._sig.size()); n < cnt; n++) {
					int paramCompare = WithId.ID_ORDER.compare(m1._sig.get(n), m2._sig.get(n));
					if (paramCompare != 0) {
						return paramCompare;
					}
				}

				return Integer.compare(m1._sig.size(), m2._sig.size());
			}
		};

		final Type _type;

		final List<Type> _sig;

		public Member(Type type, Name name, List<Type> sig) {
			super(name);
			_type = type;
			_sig = sig;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((_name == null) ? 0 : _name.hashCode());
			result = prime * result + ((_sig == null) ? 0 : _sig.hashCode());
			result = prime * result + ((_type == null) ? 0 : _type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Member other = (Member) obj;
			if (_name == null) {
				if (other._name != null)
					return false;
			} else if (!_name.equals(other._name))
				return false;
			if (_sig == null) {
				if (other._sig != null)
					return false;
			} else if (!_sig.equals(other._sig))
				return false;
			if (_type == null) {
				if (other._type != null)
					return false;
			} else if (!_type.equals(other._type))
				return false;
			return true;
		}

	}

	static String localTypeName(String typeName) {
		int dollar = typeName.lastIndexOf('$');
		if (dollar >= 0) {
			return typeName.substring(dollar + 1);
		}

		return localName(typeName);
	}

	static String localName(String typeName) {
		int dot = typeName.lastIndexOf('.');
		if (dot >= 0) {
			typeName = typeName.substring(dot + 1);
		}
		return typeName;
	}

	static String packageName(String typeName) {
		int dot = typeName.lastIndexOf('.');
		if (dot >= 0) {
			return typeName.substring(0, dot);
		}
		return "";
	}

}