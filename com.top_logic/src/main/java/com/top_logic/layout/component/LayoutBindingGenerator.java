/*
 * SPDX-FileCopyrightText: 2009 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.component;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.top_logic.basic.Protocol;
import com.top_logic.basic.StringServices;
import com.top_logic.basic.generate.CodeUtil;
import com.top_logic.basic.generate.JavaGenerator;
import com.top_logic.layout.processor.LayoutModelConstants;

/**
 * Generator for component bindings for automated application tests.
 * 
 * <p>
 * A component binding class declares a Java constant for each component
 * referenced from an application component configuration (layout XML file).
 * </p>
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public final class LayoutBindingGenerator extends JavaGenerator {
	private final Protocol protocol;

	private final Document applicationLayout;

	private final NodeList components;

	private final String bindingClassName;

	private final String outputPath;

	public LayoutBindingGenerator(String packageName, Protocol protocol,
			Document applicationLayout, NodeList components,
			String bindingClassName, String outputPath) {
		super(packageName);
		this.protocol = protocol;
		this.applicationLayout = applicationLayout;
		this.components = components;
		this.bindingClassName = bindingClassName;
		this.outputPath = outputPath;
	}

	@Override
	public String className() {
		return bindingClassName;
	}

	@Override
	protected void writeBody() {
		line("/**");
		line(" * Component references for automated tests.");
		line(" * ");
		line(" * <p>");
		line(" * Note: This file is automatically generated. Do not edit.");
		line(" * </p>");
		line(" * ");
		line(" * <p>");
		Element rootComponent = applicationLayout.getDocumentElement();
		String rootDefinitionName = rootComponent.getAttributeNS(LayoutModelConstants.ANNOTATION_NS, LayoutModelConstants.DEFINITION_FILE_ANNOTATION_ATTR);
		String rootDefinitionVersion = rootComponent.getAttributeNS(LayoutModelConstants.ANNOTATION_NS, LayoutModelConstants.DEFINITION_VERSION_ANNOTATION_ATTR);
		line(" * This file was generated from declaration <code>" + 
				stripCommonPrefix(outputPath, rootDefinitionName) + "</code> (" + rootDefinitionVersion + ").");
		line(" * </p>");
		line(" * ");
		writeCvsTags();
		line(" */");
		line("public class " + className() + " {");
		for (int n = 0, cnt = components.getLength(); n < cnt; n++) {
			Element component = (Element) components.item(n);
			
			if (! component.hasAttribute("name")) {
				continue;
			}
			
			String componentName = component.getAttribute("name");
			if (! component.hasAttribute("class")) {
				protocol.error("Component '" + componentName + "' without class declaration.");
				continue;
			}
			
			String constantName = CodeUtil.toAllUpperCase(componentName) + "_NAME";
			line("/**");
			line(" * Name of component <code>" + componentName + "</code>");
			line(" * ");
			line(" * <p>The component declaration is in</p>");
			line(" * <ul>");
			
			boolean first = true;
			for (Node parentOrSelf = component; parentOrSelf != null; parentOrSelf = parentOrSelf.getParentNode()) {
				if (parentOrSelf.getNodeType() == Node.ELEMENT_NODE) {
					Element parentOrSelfElement = (Element) parentOrSelf;
					if (parentOrSelfElement.hasAttributeNS(LayoutModelConstants.ANNOTATION_NS, LayoutModelConstants.DEFINITION_FILE_ANNOTATION_ATTR)) {
						String definitionPath = parentOrSelfElement.getAttributeNS(LayoutModelConstants.ANNOTATION_NS, LayoutModelConstants.DEFINITION_FILE_ANNOTATION_ATTR);
						String definitionVersion = parentOrSelfElement.getAttributeNS(LayoutModelConstants.ANNOTATION_NS, LayoutModelConstants.DEFINITION_VERSION_ANNOTATION_ATTR);
						if (StringServices.isEmpty(definitionVersion)) {
							definitionVersion = "no revision information";
						}
						String definitionName = stripCommonPrefix(outputPath, definitionPath);
						line(" * <li>" + (first ? "" : "included from ") + "<code>" + definitionName.replace('\\', '/') + "</code> (" + definitionVersion + ")</li>");
						first = false;
					}
				}
			}
			line(" * </ul>");
			line(" */");
			line("public static final String " + constantName + " = " + CodeUtil.toStringLiteral(componentName) + ";");
			nl();
			
			String className = component.getAttribute("class").replace('$', '.');
			if (className.indexOf('.') < 0 && className.indexOf('%') != 0) {
				className = LayoutModelProcessor.DEFAULT_COMPONENT_PACKAGE_NAME + '.' + className;
			}

			// No lookup method, if theme dependency.
			if (className.indexOf('%') < 0) {
				line("/** Looks up component named {@link #" + constantName + "} from given layout. */");
				line("public static " + className + " " + "get" + CodeUtil.toCamelCase(componentName) + "Component(com.top_logic.mig.html.layout.MainLayout ml) {");
				line(   "return (" + className + ") ml.getComponentByName(" + constantName + ");");
				line("}");
				nl();
			}

		}
		line("}");
	}

	private String stripCommonPrefix(String outputPath, String definitionPath) {
		int index = 0;
		for (int n = 0, cnt = Math.min(outputPath.length(), definitionPath.length()); n < cnt; n++) {
			if (outputPath.charAt(n) != definitionPath.charAt(n)) {
				break;
			}
			
			if (definitionPath.charAt(n) == '/' || definitionPath.charAt(n) == '\\') {
				index = n + 1;
			}
		}
		return definitionPath.substring(index);
	}
}