/* Generated By:JavaCC: Do not edit this line. HTMLTemplateParserConstants.java */
/*
 * SPDX-FileCopyrightText: 2018 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.html.template.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface HTMLTemplateParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int JSP_COMMENT_START = 1;
  /** RegularExpression Id. */
  int JSP_COMMENT = 2;
  /** RegularExpression Id. */
  int XML_COMMENT_START = 3;
  /** RegularExpression Id. */
  int XML_COMMENT = 4;
  /** RegularExpression Id. */
  int EMBEDD = 5;
  /** RegularExpression Id. */
  int IF_TAG = 6;
  /** RegularExpression Id. */
  int IF_END = 7;
  /** RegularExpression Id. */
  int CHOOSE_TAG = 8;
  /** RegularExpression Id. */
  int WHEN_END = 9;
  /** RegularExpression Id. */
  int OTHERWISE_END = 10;
  /** RegularExpression Id. */
  int FOREACH_TAG = 11;
  /** RegularExpression Id. */
  int FOREACH_END = 12;
  /** RegularExpression Id. */
  int WITH_TAG = 13;
  /** RegularExpression Id. */
  int WITH_END = 14;
  /** RegularExpression Id. */
  int TEXT_TAG = 15;
  /** RegularExpression Id. */
  int TEXT_END = 16;
  /** RegularExpression Id. */
  int TAG_START = 17;
  /** RegularExpression Id. */
  int END_TAG = 18;
  /** RegularExpression Id. */
  int TAG_NAME = 19;
  /** RegularExpression Id. */
  int TEXT_CONTENT = 20;
  /** RegularExpression Id. */
  int WS = 21;
  /** RegularExpression Id. */
  int WS_OPT = 22;
  /** RegularExpression Id. */
  int EntityRef = 23;
  /** RegularExpression Id. */
  int CharRef = 24;
  /** RegularExpression Id. */
  int CharRefHex = 25;
  /** RegularExpression Id. */
  int NameStartChar = 26;
  /** RegularExpression Id. */
  int NameChar = 27;
  /** RegularExpression Id. */
  int Name = 28;
  /** RegularExpression Id. */
  int CHOOSE_SPACE = 29;
  /** RegularExpression Id. */
  int CHOOSE_TAB = 30;
  /** RegularExpression Id. */
  int CHOOSE_CR = 31;
  /** RegularExpression Id. */
  int CHOOSE_NL = 32;
  /** RegularExpression Id. */
  int CHOOSE_END = 33;
  /** RegularExpression Id. */
  int WHEN_TAG = 34;
  /** RegularExpression Id. */
  int OTHERWISE_TAG = 35;
  /** RegularExpression Id. */
  int EXPRESSION_SPACE = 36;
  /** RegularExpression Id. */
  int EXPRESSION_TAB = 37;
  /** RegularExpression Id. */
  int EXPRESSION_CR = 38;
  /** RegularExpression Id. */
  int EXPRESSION_NL = 39;
  /** RegularExpression Id. */
  int TRUE = 40;
  /** RegularExpression Id. */
  int FALSE = 41;
  /** RegularExpression Id. */
  int NULL = 42;
  /** RegularExpression Id. */
  int AND = 43;
  /** RegularExpression Id. */
  int OR = 44;
  /** RegularExpression Id. */
  int VARIABLE_NAME = 45;
  /** RegularExpression Id. */
  int NUM = 46;
  /** RegularExpression Id. */
  int EXPRESSION_IF = 47;
  /** RegularExpression Id. */
  int EXPRESSION_ELSE = 48;
  /** RegularExpression Id. */
  int EMBEDD_END = 49;
  /** RegularExpression Id. */
  int SPECIAL_VALUE_END = 50;
  /** RegularExpression Id. */
  int NOT = 51;
  /** RegularExpression Id. */
  int EQ = 52;
  /** RegularExpression Id. */
  int NEQ = 53;
  /** RegularExpression Id. */
  int GE = 54;
  /** RegularExpression Id. */
  int LE = 55;
  /** RegularExpression Id. */
  int GT = 56;
  /** RegularExpression Id. */
  int LT = 57;
  /** RegularExpression Id. */
  int PLUS = 58;
  /** RegularExpression Id. */
  int MINUS = 59;
  /** RegularExpression Id. */
  int MUL = 60;
  /** RegularExpression Id. */
  int DIV = 61;
  /** RegularExpression Id. */
  int MOD = 62;
  /** RegularExpression Id. */
  int OPEN = 63;
  /** RegularExpression Id. */
  int CLOSE = 64;
  /** RegularExpression Id. */
  int STRING = 65;
  /** RegularExpression Id. */
  int TAG_SPACE = 66;
  /** RegularExpression Id. */
  int TAG_TAB = 67;
  /** RegularExpression Id. */
  int TAG_CR = 68;
  /** RegularExpression Id. */
  int TAG_NL = 69;
  /** RegularExpression Id. */
  int IF_ATTR = 70;
  /** RegularExpression Id. */
  int FOREACH_ATTR = 71;
  /** RegularExpression Id. */
  int ATTRIBUTE_EQ = 72;
  /** RegularExpression Id. */
  int QUOT_START = 73;
  /** RegularExpression Id. */
  int TAG_END = 74;
  /** RegularExpression Id. */
  int END_EMPTY_TAG = 75;
  /** RegularExpression Id. */
  int ATTRIBUTE_NAME = 76;
  /** RegularExpression Id. */
  int AttributeNameStartChar = 77;
  /** RegularExpression Id. */
  int AttributeNameChar = 78;
  /** RegularExpression Id. */
  int AttributeName = 79;
  /** RegularExpression Id. */
  int SPECIAL_ATTR_SPACE = 80;
  /** RegularExpression Id. */
  int SPECIAL_ATTR_TAB = 81;
  /** RegularExpression Id. */
  int SPECIAL_ATTR_CR = 82;
  /** RegularExpression Id. */
  int SPECIAL_ATTR_NL = 83;
  /** RegularExpression Id. */
  int SPECIAL_ATTR_EQ = 84;
  /** RegularExpression Id. */
  int SPECIAL_ATTR_VALUE = 85;
  /** RegularExpression Id. */
  int SPECIAL_SPACE = 86;
  /** RegularExpression Id. */
  int SPECIAL_TAB = 87;
  /** RegularExpression Id. */
  int SPECIAL_CR = 88;
  /** RegularExpression Id. */
  int SPECIAL_NL = 89;
  /** RegularExpression Id. */
  int TEST_ATTR = 90;
  /** RegularExpression Id. */
  int ELEMENTS_ATTR = 91;
  /** RegularExpression Id. */
  int DEF_ATTR = 92;
  /** RegularExpression Id. */
  int SPECIAL_EQ = 93;
  /** RegularExpression Id. */
  int SPECIAL_VALUE_START = 94;
  /** RegularExpression Id. */
  int SPECIAL_CLOSE = 95;
  /** RegularExpression Id. */
  int EMBEDD_ATTR = 96;
  /** RegularExpression Id. */
  int QUOT_END = 97;
  /** RegularExpression Id. */
  int ATTRIBUTE_TEXT = 98;
  /** RegularExpression Id. */
  int ATTRIBUTE_EntityRef = 99;
  /** RegularExpression Id. */
  int ATTRIBUTE_CharRef = 100;
  /** RegularExpression Id. */
  int ATTRIBUTE_CharRefHex = 101;
  /** RegularExpression Id. */
  int ATTRIBUTE_NameStartChar = 102;
  /** RegularExpression Id. */
  int ATTRIBUTE_NameChar = 103;
  /** RegularExpression Id. */
  int ATTRIBUTE_Name = 104;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int CHOOSE = 1;
  /** Lexical state. */
  int EXPRESSION = 2;
  /** Lexical state. */
  int TAG = 3;
  /** Lexical state. */
  int SPECIAL_ATTR = 4;
  /** Lexical state. */
  int SPECIAL = 5;
  /** Lexical state. */
  int ATTR_CONTENT = 6;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\"<%--\"",
    "<JSP_COMMENT>",
    "\"<!--\"",
    "<XML_COMMENT>",
    "\"{\"",
    "\"<tl:if\"",
    "\"</tl:if>\"",
    "\"<tl:choose>\"",
    "\"</tl:when>\"",
    "\"</tl:otherwise>\"",
    "\"<tl:foreach\"",
    "\"</tl:foreach>\"",
    "\"<tl:with\"",
    "\"</tl:with>\"",
    "\"<tl:text>\"",
    "\"</tl:text>\"",
    "<TAG_START>",
    "<END_TAG>",
    "<TAG_NAME>",
    "<TEXT_CONTENT>",
    "<WS>",
    "<WS_OPT>",
    "<EntityRef>",
    "<CharRef>",
    "<CharRefHex>",
    "<NameStartChar>",
    "<NameChar>",
    "<Name>",
    "\" \"",
    "\"\\t\"",
    "\"\\r\"",
    "\"\\n\"",
    "\"</tl:choose>\"",
    "\"<tl:when\"",
    "\"<tl:otherwise>\"",
    "\" \"",
    "\"\\t\"",
    "\"\\r\"",
    "\"\\n\"",
    "\"true\"",
    "\"false\"",
    "\"null\"",
    "<AND>",
    "<OR>",
    "<VARIABLE_NAME>",
    "<NUM>",
    "\"?\"",
    "\":\"",
    "\"}\"",
    "\"\\\"\"",
    "\"!\"",
    "\"==\"",
    "\"!=\"",
    "\">=\"",
    "\"<=\"",
    "\">\"",
    "\"<\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"%\"",
    "\"(\"",
    "\")\"",
    "<STRING>",
    "\" \"",
    "\"\\t\"",
    "\"\\r\"",
    "\"\\n\"",
    "\"tl:if\"",
    "\"tl:foreach\"",
    "\"=\"",
    "\"\\\"\"",
    "\">\"",
    "\"/>\"",
    "<ATTRIBUTE_NAME>",
    "<AttributeNameStartChar>",
    "<AttributeNameChar>",
    "<AttributeName>",
    "\" \"",
    "\"\\t\"",
    "\"\\r\"",
    "\"\\n\"",
    "\"=\"",
    "\"\\\"\"",
    "\" \"",
    "\"\\t\"",
    "\"\\r\"",
    "\"\\n\"",
    "\"test\"",
    "\"elements\"",
    "\"def\"",
    "\"=\"",
    "\"\\\"\"",
    "\">\"",
    "\"{\"",
    "\"\\\"\"",
    "<ATTRIBUTE_TEXT>",
    "<ATTRIBUTE_EntityRef>",
    "<ATTRIBUTE_CharRef>",
    "<ATTRIBUTE_CharRefHex>",
    "<ATTRIBUTE_NameStartChar>",
    "<ATTRIBUTE_NameChar>",
    "<ATTRIBUTE_Name>",
  };

}
