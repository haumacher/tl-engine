/* HTMLTemplateParser.java */
/* Generated By:JavaCC: Do not edit this line. HTMLTemplateParser.java */
/*
 * SPDX-FileCopyrightText: 2018 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.html.template.parser;

import java.util.List;

import com.top_logic.html.template.*;

/**
 * Parser for html template expressions.
 *
 * @author <a href="mailto:sfo@top-logic.com">Sven Förster</a>
 */
public class HTMLTemplateParser implements HTMLTemplateParserConstants {

  private HTMLTemplateFactory _f = new HTMLTemplateFactory();

  public HTMLTemplateFactory getFactory() {
        return _f;
  }

  public void setFactory(HTMLTemplateFactory f) {
        _f = f;
  }

  final public RawTemplateFragment html() throws ParseException {List<RawTemplateFragment> result = _f.builder();
  RawTemplateFragment content;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case EMBEDD:
      case IF_TAG:
      case CHOOSE_TAG:
      case FOREACH_TAG:
      case WITH_TAG:
      case TEXT_TAG:
      case TAG_START:
      case END_TAG:
      case TEXT_CONTENT:
      case EntityRef:
      case CharRef:
      case CharRefHex:{
        ;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      content = htmlContent();
result.add(content);
    }
{if ("" != null) return _f.template(result);}
    throw new Error("Missing return statement in function");
}

  final public RawTemplateFragment htmlContent() throws ParseException {Token t;
  TemplateExpression expr;
  RawTemplateFragment result;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IF_TAG:
    case CHOOSE_TAG:
    case FOREACH_TAG:
    case WITH_TAG:
    case TEXT_TAG:
    case TAG_START:{
      result = startTag();
      break;
      }
    case TEXT_CONTENT:{
      t = jj_consume_token(TEXT_CONTENT);
result = _f.text(t);
      break;
      }
    case CharRef:{
      t = jj_consume_token(CharRef);
result = _f.charRef(t);
      break;
      }
    case CharRefHex:{
      t = jj_consume_token(CharRefHex);
result = _f.charRefHex(t);
      break;
      }
    case EntityRef:{
      t = jj_consume_token(EntityRef);
result = _f.entityRef(t);
      break;
      }
    case EMBEDD:{
      jj_consume_token(EMBEDD);
      expr = templateExpression();
result = _f.renderExpression(expr); token_source.SwitchTo(DEFAULT);
      jj_consume_token(EMBEDD_END);
      break;
      }
    case END_TAG:{
      t = jj_consume_token(END_TAG);
result = _f.endTag(t);
      break;
      }
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return result;}
    throw new Error("Missing return statement in function");
}

  final public TemplateExpression templateExpression() throws ParseException {TemplateExpression result;
    result = ifThenExpression();
{if ("" != null) return result;}
    throw new Error("Missing return statement in function");
}

  final public TemplateExpression ifThenExpression() throws ParseException {TemplateExpression booleanExpression;
  TemplateExpression thenExpr;
  TemplateExpression elseExpr;
    booleanExpression = booleanExpression();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EXPRESSION_IF:{
      jj_consume_token(EXPRESSION_IF);
      thenExpr = booleanExpression();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case EXPRESSION_ELSE:{
        jj_consume_token(EXPRESSION_ELSE);
        elseExpr = templateExpression();
{if ("" != null) return _f.ifThenExpression(booleanExpression, thenExpr, elseExpr);}
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        ;
      }
{if ("" != null) return _f.ifThenExpression(booleanExpression, thenExpr);}
      break;
      }
    default:
      jj_la1[3] = jj_gen;
      ;
    }
{if ("" != null) return booleanExpression;}
    throw new Error("Missing return statement in function");
}

  final public TemplateExpression booleanExpression() throws ParseException {TemplateExpression expr;
  TemplateExpression right;
    expr = orExpr();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case OR:{
        ;
        break;
        }
      default:
        jj_la1[4] = jj_gen;
        break label_2;
      }
      jj_consume_token(OR);
      right = orExpr();
expr=_f.or(expr, right);
    }
{if ("" != null) return expr;}
    throw new Error("Missing return statement in function");
}

  final public TemplateExpression orExpr() throws ParseException {TemplateExpression expr;
  TemplateExpression right;
    expr = andExpr();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case AND:{
        ;
        break;
        }
      default:
        jj_la1[5] = jj_gen;
        break label_3;
      }
      jj_consume_token(AND);
      right = andExpr();
expr=_f.and(expr, right);
    }
{if ("" != null) return expr;}
    throw new Error("Missing return statement in function");
}

  final public TemplateExpression andExpr() throws ParseException {TemplateExpression expr;
  TemplateExpression right;
    expr = aditiveExpr();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EQ:
    case NEQ:
    case GE:
    case LE:
    case GT:
    case LT:{
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case EQ:{
        jj_consume_token(EQ);
        right = aditiveExpr();
expr=_f.eq(expr, right);
        break;
        }
      case NEQ:{
        jj_consume_token(NEQ);
        right = aditiveExpr();
expr=_f.neq(expr, right);
        break;
        }
      case GE:{
        jj_consume_token(GE);
        right = aditiveExpr();
expr=_f.ge(expr, right);
        break;
        }
      case GT:{
        jj_consume_token(GT);
        right = aditiveExpr();
expr=_f.gt(expr, right);
        break;
        }
      case LE:{
        jj_consume_token(LE);
        right = aditiveExpr();
expr=_f.le(expr, right);
        break;
        }
      case LT:{
        jj_consume_token(LT);
        right = aditiveExpr();
expr=_f.lt(expr, right);
        break;
        }
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
      }
    default:
      jj_la1[7] = jj_gen;
      ;
    }
{if ("" != null) return expr;}
    throw new Error("Missing return statement in function");
}

  final public TemplateExpression aditiveExpr() throws ParseException {TemplateExpression expr;
  TemplateExpression right;
    expr = multiplicativeExpr();
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLUS:
      case MINUS:{
        ;
        break;
        }
      default:
        jj_la1[8] = jj_gen;
        break label_4;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLUS:{
        jj_consume_token(PLUS);
        right = multiplicativeExpr();
expr=_f.add(expr, right);
        break;
        }
      case MINUS:{
        jj_consume_token(MINUS);
        right = multiplicativeExpr();
expr=_f.sub(expr, right);
        break;
        }
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return expr;}
    throw new Error("Missing return statement in function");
}

  final public TemplateExpression multiplicativeExpr() throws ParseException {TemplateExpression expr;
  TemplateExpression right;
    expr = negatedExpr();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case MUL:
      case DIV:
      case MOD:{
        ;
        break;
        }
      default:
        jj_la1[10] = jj_gen;
        break label_5;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case MUL:{
        jj_consume_token(MUL);
        right = negatedExpr();
expr=_f.mul(expr, right);
        break;
        }
      case DIV:{
        jj_consume_token(DIV);
        right = negatedExpr();
expr=_f.div(expr, right);
        break;
        }
      case MOD:{
        jj_consume_token(MOD);
        right = negatedExpr();
expr=_f.mod(expr, right);
        break;
        }
      default:
        jj_la1[11] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return expr;}
    throw new Error("Missing return statement in function");
}

  final public TemplateExpression negatedExpr() throws ParseException {TemplateExpression expr;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NOT:{
      jj_consume_token(NOT);
      expr = accessExpression();
expr = _f.not(expr);
      break;
      }
    case MINUS:{
      jj_consume_token(MINUS);
      expr = atomicExpression();
expr = _f.neg(expr);
      break;
      }
    case TRUE:
    case FALSE:
    case NULL:
    case VARIABLE_NAME:
    case NUM:
    case OPEN:
    case STRING:{
      expr = accessExpression();
      break;
      }
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return expr;}
    throw new Error("Missing return statement in function");
}

  final public TemplateExpression accessExpression() throws ParseException {TemplateExpression expr;
  TemplateExpression index;
  Token t;
    expr = atomicExpression();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case DOT:
      case OPEN_BRACE:{
        ;
        break;
        }
      default:
        jj_la1[13] = jj_gen;
        break label_6;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case DOT:{
        jj_consume_token(DOT);
        t = jj_consume_token(VARIABLE_NAME);
expr = _f.access(t, expr, t.image);
        break;
        }
      case OPEN_BRACE:{
        t = jj_consume_token(OPEN_BRACE);
        index = templateExpression();
        jj_consume_token(CLOSE_BRACE);
expr = _f.access(t, expr, index);
        break;
        }
      default:
        jj_la1[14] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return expr;}
    throw new Error("Missing return statement in function");
}

  final public TemplateExpression atomicExpression() throws ParseException {Token t;
  TemplateExpression result;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VARIABLE_NAME:{
      t = jj_consume_token(VARIABLE_NAME);
result = _f.variable(t);
      break;
      }
    case STRING:{
      t = jj_consume_token(STRING);
result = _f.string(t);
      break;
      }
    case NUM:{
      t = jj_consume_token(NUM);
result = _f.numberLiteral(t);
      break;
      }
    case TRUE:{
      t = jj_consume_token(TRUE);
result = _f.boolenLiteral(t, true);
      break;
      }
    case FALSE:{
      t = jj_consume_token(FALSE);
result = _f.boolenLiteral(t, false);
      break;
      }
    case NULL:{
      t = jj_consume_token(NULL);
result = _f.nullLiteral(t);
      break;
      }
    case OPEN:{
      jj_consume_token(OPEN);
      result = templateExpression();
      jj_consume_token(CLOSE);
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return result;}
    throw new Error("Missing return statement in function");
}

  final public RawTemplateFragment startTag() throws ParseException {Token t, v, iter = null;
  TagAttributeTemplate attribute;
  StartTagTemplate result;
  TemplateExpression expression;
  RawTemplateFragment content;
  ConditionalTemplate top, current;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case TAG_START:{
      t = jj_consume_token(TAG_START);
result = _f.startTag(t);
      label_7:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case IF_ATTR:
        case FOREACH_ATTR:
        case ATTRIBUTE_NAME:{
          ;
          break;
          }
        default:
          jj_la1[16] = jj_gen;
          break label_7;
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case ATTRIBUTE_NAME:{
          attribute = attribute();
result.addAttribute(attribute);
          break;
          }
        case IF_ATTR:{
          jj_consume_token(IF_ATTR);
          jj_consume_token(SPECIAL_ATTR_EQ);
          jj_consume_token(SPECIAL_ATTR_VALUE);
          expression = templateExpression();
          jj_consume_token(SPECIAL_VALUE_END);
result = _f.conditionalTag(result, expression); token_source.SwitchTo(TAG);
          break;
          }
        case FOREACH_ATTR:{
          jj_consume_token(FOREACH_ATTR);
          jj_consume_token(SPECIAL_ATTR_EQ);
          jj_consume_token(SPECIAL_ATTR_VALUE);
          t = jj_consume_token(VARIABLE_NAME);
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case COMMA:{
            jj_consume_token(COMMA);
            iter = jj_consume_token(VARIABLE_NAME);
            break;
            }
          default:
            jj_la1[17] = jj_gen;
            ;
          }
          jj_consume_token(EXPRESSION_ELSE);
          expression = templateExpression();
          jj_consume_token(SPECIAL_VALUE_END);
result = _f.foreachTag(result, t.image, iter == null ? null : iter.image, expression); token_source.SwitchTo(TAG);
          break;
          }
        default:
          jj_la1[18] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case END_EMPTY_TAG:{
        jj_consume_token(END_EMPTY_TAG);
result.setEmpty(true);
        break;
        }
      case TAG_END:{
        jj_consume_token(TAG_END);
        break;
        }
      default:
        jj_la1[19] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
{if ("" != null) return result;}
      break;
      }
    case IF_TAG:{
      jj_consume_token(IF_TAG);
      jj_consume_token(TEST_ATTR);
      jj_consume_token(SPECIAL_EQ);
      jj_consume_token(SPECIAL_VALUE_START);
      expression = templateExpression();
      jj_consume_token(SPECIAL_VALUE_END);
token_source.SwitchTo(SPECIAL);
      jj_consume_token(SPECIAL_CLOSE);
      content = html();
      jj_consume_token(IF_END);
{if ("" != null) return _f.ifTag(expression, content);}
      break;
      }
    case CHOOSE_TAG:{
      jj_consume_token(CHOOSE_TAG);
top = current = null;
      label_8:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case WHEN_TAG:{
          ;
          break;
          }
        default:
          jj_la1[20] = jj_gen;
          break label_8;
        }
        jj_consume_token(WHEN_TAG);
        jj_consume_token(TEST_ATTR);
        jj_consume_token(SPECIAL_EQ);
        jj_consume_token(SPECIAL_VALUE_START);
        expression = templateExpression();
        jj_consume_token(SPECIAL_VALUE_END);
token_source.SwitchTo(SPECIAL);
        jj_consume_token(SPECIAL_CLOSE);
        content = html();
        jj_consume_token(WHEN_END);
ConditionalTemplate outer = current;
      current = _f.ifTag(expression, content);
      if (outer == null) {
        top = current;
      } else {
        outer.setElseFragment(current);
      }
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case OTHERWISE_TAG:{
        jj_consume_token(OTHERWISE_TAG);
        content = html();
        jj_consume_token(OTHERWISE_END);
        break;
        }
      default:
        jj_la1[21] = jj_gen;
content = EmptyTemplate.INSTANCE;
      }
      jj_consume_token(CHOOSE_END);
if (current == null) { {if ("" != null) return content;} } else { current.setElseFragment(content); {if ("" != null) return top;} }
      break;
      }
    case WITH_TAG:{
      jj_consume_token(WITH_TAG);
      jj_consume_token(DEF_ATTR);
      jj_consume_token(SPECIAL_EQ);
      jj_consume_token(SPECIAL_VALUE_START);
      v = jj_consume_token(VARIABLE_NAME);
      jj_consume_token(EXPRESSION_ELSE);
      expression = templateExpression();
      jj_consume_token(SPECIAL_VALUE_END);
token_source.SwitchTo(SPECIAL);
      jj_consume_token(SPECIAL_CLOSE);
      content = html();
      jj_consume_token(WITH_END);
{if ("" != null) return _f.withTag(v.image, expression, content);}
      break;
      }
    case FOREACH_TAG:{
      jj_consume_token(FOREACH_TAG);
      jj_consume_token(ELEMENTS_ATTR);
      jj_consume_token(SPECIAL_EQ);
      jj_consume_token(SPECIAL_VALUE_START);
      v = jj_consume_token(VARIABLE_NAME);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        jj_consume_token(COMMA);
        iter = jj_consume_token(VARIABLE_NAME);
        break;
        }
      default:
        jj_la1[22] = jj_gen;
        ;
      }
      jj_consume_token(EXPRESSION_ELSE);
      expression = templateExpression();
      jj_consume_token(SPECIAL_VALUE_END);
token_source.SwitchTo(SPECIAL);
      jj_consume_token(SPECIAL_CLOSE);
      content = html();
      jj_consume_token(FOREACH_END);
{if ("" != null) return _f.foreachTag(v.image, iter == null ? null : iter.image, expression, content);}
      break;
      }
    case TEXT_TAG:{
      jj_consume_token(TEXT_TAG);
      t = jj_consume_token(TEXT_CONTENT);
      jj_consume_token(TEXT_END);
{if ("" != null) return _f.rawText(t);}
      break;
      }
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public TagAttributeTemplate attribute() throws ParseException {Token t;
  List<RawTemplateFragment> content;
    t = jj_consume_token(ATTRIBUTE_NAME);
    jj_consume_token(ATTRIBUTE_EQ);
    jj_consume_token(QUOT_START);
content=attributeContent();
    jj_consume_token(QUOT_END);
{if ("" != null) return _f.attribute(t, content);}
    throw new Error("Missing return statement in function");
}

  final public List<RawTemplateFragment> attributeContent() throws ParseException {Token t;
  TemplateExpression expression;
  List<RawTemplateFragment> result = _f.builder();
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case EMBEDD_ATTR:
      case ATTRIBUTE_TEXT:
      case ATTRIBUTE_EntityRef:
      case ATTRIBUTE_CharRef:
      case ATTRIBUTE_CharRefHex:{
        ;
        break;
        }
      default:
        jj_la1[24] = jj_gen;
        break label_9;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ATTRIBUTE_TEXT:{
        t = jj_consume_token(ATTRIBUTE_TEXT);
result.add(_f.rawText(t));
        break;
        }
      case ATTRIBUTE_EntityRef:{
        t = jj_consume_token(ATTRIBUTE_EntityRef);
result.add(_f.entityRef(t));
        break;
        }
      case ATTRIBUTE_CharRef:{
        t = jj_consume_token(ATTRIBUTE_CharRef);
result.add(_f.charRef(t));
        break;
        }
      case ATTRIBUTE_CharRefHex:{
        t = jj_consume_token(ATTRIBUTE_CharRefHex);
result.add(_f.charRefHex(t));
        break;
        }
      case EMBEDD_ATTR:{
        jj_consume_token(EMBEDD_ATTR);
        expression = templateExpression();
        jj_consume_token(EMBEDD_END);
result.add(_f.renderExpression(expression)); token_source.SwitchTo(ATTR_CONTENT);
        break;
        }
      default:
        jj_la1[25] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return result;}
    throw new Error("Missing return statement in function");
}

  /** Generated Token Manager. */
  public HTMLTemplateParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[26];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static private int[] jj_la1_2;
  static private int[] jj_la1_3;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	   jj_la1_init_2();
	   jj_la1_init_3();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x396a960,0x396a960,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x2a940,0x0,0x0,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x0,0x0,0x10000,0x8000,0x1000,0x800,0x3f00000,0x3f00000,0xc000000,0xc000000,0x70000000,0x70000000,0x8086700,0x80000000,0x80000000,0x6700,0x0,0x0,0x0,0x0,0x4,0x8,0x0,0x0,0x0,0x0,};
	}
	private static void jj_la1_init_2() {
	   jj_la1_2 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x28,0x2,0x2,0x28,0x10c00,0x1,0x10c00,0xc000,0x0,0x0,0x1,0x0,0x0,0x0,};
	}
	private static void jj_la1_init_3() {
	   jj_la1_3 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x3d0,0x3d0,};
	}

  /** Constructor with InputStream. */
  public HTMLTemplateParser(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public HTMLTemplateParser(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new HTMLTemplateParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 26; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 26; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public HTMLTemplateParser(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new HTMLTemplateParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 26; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new HTMLTemplateParserTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 26; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public HTMLTemplateParser(HTMLTemplateParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 26; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(HTMLTemplateParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 26; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[109];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 26; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		   if ((jj_la1_2[i] & (1<<j)) != 0) {
			 la1tokens[64+j] = true;
		   }
		   if ((jj_la1_3[i] & (1<<j)) != 0) {
			 la1tokens[96+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 109; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
