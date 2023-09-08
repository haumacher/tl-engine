/* Generated By:JavaCC: Do not edit this line. ConfigTemplateParserConstants.java */
package com.top_logic.basic.config.template.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ConfigTemplateParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int TAG_START = 1;
  /** RegularExpression Id. */
  int END_TAG_START = 2;
  /** RegularExpression Id. */
  int STARTEXPR = 3;
  /** RegularExpression Id. */
  int ENDTEMPLATE = 4;
  /** RegularExpression Id. */
  int LITERAL = 5;
  /** RegularExpression Id. */
  int XML_NAME = 10;
  /** RegularExpression Id. */
  int EQ = 11;
  /** RegularExpression Id. */
  int DQUOT = 12;
  /** RegularExpression Id. */
  int SQUOT = 13;
  /** RegularExpression Id. */
  int EMPTY_TAG_END = 14;
  /** RegularExpression Id. */
  int TAG_END = 15;
  /** RegularExpression Id. */
  int LOCAL_NAME = 16;
  /** RegularExpression Id. */
  int XML_WORD = 17;
  /** RegularExpression Id. */
  int DQUOT_END = 18;
  /** RegularExpression Id. */
  int STARTEXPR_D = 19;
  /** RegularExpression Id. */
  int ENDTEMPLATE_D = 20;
  /** RegularExpression Id. */
  int LITERAL_D = 21;
  /** RegularExpression Id. */
  int SQUOT_END = 22;
  /** RegularExpression Id. */
  int STARTEXPR_S = 23;
  /** RegularExpression Id. */
  int ENDTEMPLATE_S = 24;
  /** RegularExpression Id. */
  int LITERAL_S = 25;
  /** RegularExpression Id. */
  int ENDEXPR = 30;
  /** RegularExpression Id. */
  int STARTTEMPLATE = 31;
  /** RegularExpression Id. */
  int FOREACH = 32;
  /** RegularExpression Id. */
  int THIS = 33;
  /** RegularExpression Id. */
  int IDENTIFIER = 34;
  /** RegularExpression Id. */
  int VARNAME = 35;
  /** RegularExpression Id. */
  int FUNNAME = 36;
  /** RegularExpression Id. */
  int STRING = 37;
  /** RegularExpression Id. */
  int NUMBER = 38;
  /** RegularExpression Id. */
  int DOT = 39;
  /** RegularExpression Id. */
  int COLON = 40;
  /** RegularExpression Id. */
  int QUESTIONMARK = 41;
  /** RegularExpression Id. */
  int PIPE = 42;
  /** RegularExpression Id. */
  int ARROW = 43;
  /** RegularExpression Id. */
  int OPENBRACE = 44;
  /** RegularExpression Id. */
  int CLOSEBRACE = 45;
  /** RegularExpression Id. */
  int OPENPAREN = 46;
  /** RegularExpression Id. */
  int CLOSEPAREN = 47;
  /** RegularExpression Id. */
  int COMMA = 48;
  /** RegularExpression Id. */
  int LETTER = 49;
  /** RegularExpression Id. */
  int LETTER_NO_DOLLAR = 50;
  /** RegularExpression Id. */
  int DIGIT = 51;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int XML = 1;
  /** Lexical state. */
  int AttributeValueD = 2;
  /** Lexical state. */
  int AttributeValueS = 3;
  /** Lexical state. */
  int Syntax = 4;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "<TAG_START>",
    "<END_TAG_START>",
    "\"{\"",
    "\"}\"",
    "<LITERAL>",
    "\" \"",
    "\"\\r\"",
    "\"\\t\"",
    "\"\\n\"",
    "<XML_NAME>",
    "\"=\"",
    "\"\\\"\"",
    "\"\\\'\"",
    "\"/>\"",
    "\">\"",
    "<LOCAL_NAME>",
    "<XML_WORD>",
    "\"\\\"\"",
    "\"{\"",
    "\"}\"",
    "<LITERAL_D>",
    "\"\\\'\"",
    "\"{\"",
    "\"}\"",
    "<LITERAL_S>",
    "\" \"",
    "\"\\r\"",
    "\"\\t\"",
    "\"\\n\"",
    "\"}\"",
    "\"{\"",
    "\"foreach\"",
    "\"this\"",
    "<IDENTIFIER>",
    "<VARNAME>",
    "<FUNNAME>",
    "<STRING>",
    "<NUMBER>",
    "\".\"",
    "\":\"",
    "\"?\"",
    "\"|\"",
    "\"->\"",
    "\"[\"",
    "\"]\"",
    "\"(\"",
    "\")\"",
    "\",\"",
    "<LETTER>",
    "<LETTER_NO_DOLLAR>",
    "<DIGIT>",
  };

}
