/*
 * SPDX-FileCopyrightText: 2013 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.util.css;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.top_logic.basic.StringServices;
import com.top_logic.basic.xml.TagWriter;
import com.top_logic.gui.ThemeFactory;
import com.top_logic.gui.ThemeVar;
import com.top_logic.layout.DisplayDimension;
import com.top_logic.mig.html.HTMLConstants;


/**
 * Utilities for CSS handling.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class CssUtil {

	private static final Pattern HEIGHT_VALUE_PATTERN = Pattern.compile("height:\\s*(\\d+)");

	/**
	 * Writes a {@link HTMLConstants#CLASS_ATTR} containing the combination of both optional
	 * classes.
	 * 
	 * @param out
	 *        The writer to write to.
	 * @param class1
	 *        The optional first class, or <code>null</code>.
	 * @param class2
	 *        The second optional class, or <code>null</code>.
	 */
	public static void writeCombinedCssClasses(TagWriter out, String class1, String class2) throws IOException {
		out.beginCssClasses();
		out.append(class1);
		out.append(class2);
		out.endCssClasses();
	}

	/**
	 * Writes a {@link HTMLConstants#CLASS_ATTR} containing the combination of both optional
	 * classes.
	 * 
	 * @param out
	 *        The writer to write to.
	 * @param class1
	 *        The optional first class, or <code>null</code>.
	 * @param class2
	 *        The second optional class, or <code>null</code>.
	 * @param class3
	 *        The third optional class, or <code>null</code>.
	 */
	public static void writeCombinedCssClasses(TagWriter out, String class1, String class2, String class3)
			throws IOException {
		out.beginCssClasses();
		out.append(class1);
		out.append(class2);
		out.append(class3);
		out.endCssClasses();
	}

	/**
	 * Joins the two CSS class specifier to a single CSS class attribute value.
	 * 
	 * <p>
	 * Note: Consider using {@link #writeCombinedCssClasses(TagWriter, String, String)} when called
	 * from rendering.
	 * </p>
	 * 
	 * @param cssClass1
	 *        The first class, may be <code>null</code>, which means no class to join.
	 * @param cssClass2
	 *        The second class, may be <code>null</code>, which means no class to join.
	 * @return The joined class, if at least one of the given classes is non-<code>null</code>,
	 *         <code>null</code> otherwise.
	 * 
	 * @see #writeCombinedCssClasses(TagWriter, String, String)
	 */
	public static String joinCssClasses(String cssClass1, String cssClass2) {
		return join(' ', cssClass1, cssClass2);
	}

	/**
	 * Joins the two CSS class specifiers to a single CSS class attribute value.
	 * 
	 * <p>
	 * Special care is taken that the resulting classes are unique.
	 * </p>
	 * 
	 * @param cssClass1
	 *        The first class, may be <code>null</code>, which means no class to join.
	 * @param cssClass2
	 *        The second class, may be <code>null</code>, which means no class to join.
	 * @return The joined class, if at least one of the given classes is non-<code>null</code>,
	 *         <code>null</code> otherwise.
	 * 
	 * @see #writeCombinedCssClasses(TagWriter, String, String)
	 */
	public static String joinCssClassesUnique(String cssClass1, String cssClass2) {
		if (cssClass1 == null) {
			return cssClass2;
		}
		if (cssClass2 == null) {
			return cssClass1;
		}

		StringBuilder buffer = new StringBuilder();
		appendAll(buffer, cssClass1);
		appendAll(buffer, cssClass2);
		return buffer.toString();
	}

	private static void appendAll(StringBuilder buffer, String classes) {
		int start = 0;
		int length = classes.length();
		do {
			int separator = classes.indexOf(' ', start);
			if (separator < 0) {
				separator = length;
			}
			if (separator > start) {
				// A class token was found.
				String css = classes.substring(start, separator);

				if (!containsToken(buffer, css)) {
					if (!buffer.isEmpty()) {
						buffer.append(' ');
					}
					buffer.append(css);
				}
			}
			start = separator + 1;
		} while (start < length);
	}

	private static boolean containsToken(StringBuilder buffer, String css) {
		return tokenIndex(buffer, css, 0) >= 0;
	}

	/**
	 * Removes the CSS classes given in the second parameter from the CSS classes in the first
	 * parameter.
	 * 
	 * @param allClasses
	 *        CSS classes to remove from.
	 * @param removedClasses
	 *        CSS classes to remove from all classes.
	 * @return The CSS classes from all classes without the ones given in removed classes,
	 *         <code>null</code> if no more classes are left.
	 * 
	 * @see #writeCombinedCssClasses(TagWriter, String, String)
	 */
	public static String removeCssClasses(String allClasses, String removedClasses) {
		if (allClasses == null) {
			return null;
		}
		if (removedClasses == null) {
			return allClasses;
		}
		int start = 0;
		int length = removedClasses.length();
		do {
			int separator = removedClasses.indexOf(' ', start);
			if (separator < 0) {
				separator = length;
			}
			if (separator > start) {
				// A class token was found.
				String css = removedClasses.substring(start, separator);

				int index = 0;
				while (true) {
					index = tokenIndex(allClasses, css, index);

					if (index < 0) {
						break;
					}

					int endIndex = index + css.length();
					if (endIndex >= allClasses.length()) {
						// Last class was removed.
						if (index == 0) {
							// The only class was removed, there are no more left.
							return null;
						} else {
							String prefix = allClasses.substring(0, index - 1);
							allClasses = prefix;
						}
					} else {
						String prefix = allClasses.substring(0, index);
						String suffix = allClasses.substring(endIndex + 1);
						allClasses = prefix + suffix;
					}
				}

			}
			start = separator + 1;
		} while (start < length);

		return allClasses;
	}

	private static int tokenIndex(CharSequence classes, String token, int pos) {
		int length = classes.length();
		while (true) {
			int match = StringServices.indexOf(classes, token, pos);
			if (match < 0) {
				// Not found.
				return -1;
			} else {
				int end = match + token.length();
				if (end >= length) {
					// Match was found at the end.
					if (match == pos || classes.charAt(match - 1) == ' ') {
						return match;
					} else {
						return -1;
					}
				} else {
					// Check whether a complete token was found.
					if (classes.charAt(end) == ' ') {
						// Found token suffix.
						if (match == pos) {
							// Match was found in first token scanned.
							return match;
						}

						if (classes.charAt(match - 1) == ' ') {
							// Match was found in some following token.
							return match;
						}

						// Scan next token.
						pos = end + 1;
					} else {
						// Match within token, seach end of token.
						pos = end + 1;
						while (true) {
							if (pos >= length) {
								// Token was not found and there are no more tokens.
								return -1;
							}
							if (classes.charAt(pos++) == ' ') {
								// There are more tokens.
								break;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Joins given CSS class specifiers to a single CSS class attribute value.
	 * 
	 * @param cssClasses
	 *        The CSS class to join, may contain <code>null</code> values, which means no class to
	 *        join.
	 * @return The joined class, if at least one of the given classes is non-<code>null</code>,
	 *         <code>null</code> otherwise.
	 */
	public static String joinCssClasses(String... cssClasses) {
		return join(' ', cssClasses);
	}

	/**
	 * @param terminatedWidthStyle
	 *        must be terminated ({@link #terminateStyleDefinition(String)}), may be null
	 * @return true, if specified column width is relative (% unit). Returns false if column width
	 *         is not relative or null.
	 */
	public static boolean isRelativeWidth(String terminatedWidthStyle) {
		if (terminatedWidthStyle == null) {
			return false;
		}
		else {
			return terminatedWidthStyle.endsWith("%;");
		}
	}

	/**
	 * Ensures that a returned style is either <code>null</code>, or begins with <code>width:</code>
	 * and is terminated by a <code>';'</code> character.
	 * 
	 * @param givenStyle
	 *        the input style
	 */
	public static String ensureWidthStyle(String givenStyle) {
		String terminatedStyle = CssUtil.terminateStyleDefinition(givenStyle);
		if (terminatedStyle == null) {
			return null;
		}
		if (terminatedStyle.startsWith("width:")) {
			return terminatedStyle;
		} else {
			return "width:" + terminatedStyle;
		}
	}

	/**
	 * Ensures that a returned style is either <code>null</code>, or terminated by a
	 * <code>';'</code> character.
	 * 
	 * @param givenStyle
	 *        The input style.
	 * 
	 * @return The terminated style, or <code>null</code>.
	 */
	public static String terminateStyleDefinition(String givenStyle) {
		if (givenStyle == null) {
			return null;
		}
	
		String trimmedStyle = givenStyle.trim();
		if (trimmedStyle.length() == 0) {
			return null;
		}
	
		if (trimmedStyle.endsWith(";")) {
			return trimmedStyle;
		} else {
			return trimmedStyle + ";";
		}
	}

	/**
	 * Concatenates the given potentially <code>null</code> styles.
	 * 
	 * @param style1
	 *        First style to concatenate, <code>null</code> means no style.
	 * @param style2
	 *        Second style to concatenate, <code>null</code> means no style.
	 * @return The concatenated style, or <code>null</code>, if no style was given.
	 */
	public static String joinStyles(String style1, String style2) {
		return join(';', style1, style2);
	}

	/**
	 * Concatenates the given potentially <code>null</code> styles.
	 * 
	 * @param styles
	 *        Styles to concatenate, <code>null</code> in the contents is skipped.
	 * @return The concatenated style, or <code>null</code>, if no style was given at all.
	 */
	public static String joinStyles(String... styles) {
		return join(';', styles);
	}

	private static String join(char separator, String cssClass1, String cssClass2) {
		if (cssClass1 == null) {
			return cssClass2;
		}
		if (cssClass2 == null) {
			return cssClass1;
		}
		return cssClass1 + separator + cssClass2;
	}

	private static String join(char separator, String... values) {
		StringBuilder buffer = null;
		String result = null;
		for (String value : values) {
			if (value == null) {
				continue;
			}
			if (result == null) {
				result = value;
				continue;
			}
	
			if (buffer == null) {
				buffer = new StringBuilder(result.length() + 1 + value.length());
				buffer.append(result);
			}
			buffer.append(separator);
			buffer.append(value);
		}
		if (buffer != null) {
			result = buffer.toString();
		}
		return result;
	}

	/**
	 * Writes an optional {@link HTMLConstants#STYLE_ATTR} attribute consisting of two optional
	 * styles.
	 * 
	 * @param out
	 *        The writer to write to.
	 * @param style1
	 *        The optional style 1, or <code>null</code>.
	 * @param style2
	 *        The optional style 2, or <code>null</code>.
	 */
	public static void writeCombinedStyle(TagWriter out, String style1, String style2) throws IOException {
		boolean hasStyle1 = !StringServices.isEmpty(style1);
		boolean hasStyle2 = !StringServices.isEmpty(style2);

		if (hasStyle1 || hasStyle2) {
			out.beginAttribute(HTMLConstants.STYLE_ATTR);
			if (hasStyle1) {
				appendStyle(out, style1);
			}
			if (hasStyle2) {
				appendStyle(out, style2);
			}
			out.endAttribute();
		}
	}

	/**
	 * Appends the given style optional value.
	 * 
	 * @param out
	 *        The writer to write to.
	 * @param style
	 *        The optional style, or <code>null</code>.
	 */
	public static void appendStyleOptional(Appendable out, String style) throws IOException {
		if (!StringServices.isEmpty(style)) {
			appendStyle(out, style);
		}
	}

	/**
	 * Appends the given style value.
	 * 
	 * @param out
	 *        The writer to write to.
	 * @param style
	 *        The style.
	 */
	public static void appendStyle(Appendable out, String style) throws IOException {
		out.append(style);
		if (!style.endsWith(";")) {
			out.append(';');
		}
	}

	/**
	 * @param style
	 *        - which may contain a height attribute, maybe <code>null</code>
	 * @param defaultHeight
	 *        - return value, if the style contains no height attribute
	 * @return height value of the first occurrence of an height attribute in the given style, if it
	 *         contains any height attribute - otherwise given default height.
	 */
	public static int retrieveHeightValueOrDefault(String style, ThemeVar<DisplayDimension> defaultHeight) {
		if (!StringServices.isEmpty(style)) {
			Matcher matcher = HEIGHT_VALUE_PATTERN.matcher(style);
			if (matcher.find()) {
				return Integer.valueOf(Integer.parseInt(matcher.group(1)));
			}
		}
		return (int) ThemeFactory.getTheme().getValue(defaultHeight).getValue();
	}
}
