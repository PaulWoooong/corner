// $ANTLR : "calc.g" -> "ExprTreeParser.java"$

	
package corner.expression.calc;

import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;


/**
 * expression tree parser
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public class ExprTreeParser extends antlr.TreeParser       implements ExprTreeParserTokenTypes
 {
public ExprTreeParser() {
	tokenNames = _tokenNames;
}

	public final double  expr(AST _t) throws RecognitionException {
		double r=0;
		
		AST expr_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		AST d = null;
		AST l = null;
		AST f = null;
		double a,b;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case PLUS:
			{
				AST __t138 = _t;
				AST tmp1_AST_in = (AST)_t;
				match(_t,PLUS);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t138;
				_t = _t.getNextSibling();
				r = a+b;
				break;
			}
			case MINUS:
			{
				AST __t139 = _t;
				AST tmp2_AST_in = (AST)_t;
				match(_t,MINUS);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t139;
				_t = _t.getNextSibling();
				r = a-b;
				break;
			}
			case STAR:
			{
				AST __t140 = _t;
				AST tmp3_AST_in = (AST)_t;
				match(_t,STAR);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t140;
				_t = _t.getNextSibling();
				r = a*b;
				break;
			}
			case DIV:
			{
				AST __t141 = _t;
				AST tmp4_AST_in = (AST)_t;
				match(_t,DIV);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				b=expr(_t);
				_t = _retTree;
				_t = __t141;
				_t = _t.getNextSibling();
				
						if(b == 0){
								throw new ArithmeticException("by zero!");
						}
						r = a/b;
						
				break;
			}
			case NUM_INT:
			{
				i = (AST)_t;
				match(_t,NUM_INT);
				_t = _t.getNextSibling();
				
								String str=	i.getText();
							if(str.startsWith("0x")){
								r=Integer.parseInt(str.substring(2),16);
							}else{
									r = Integer.parseInt(str);
							}
							
				break;
			}
			case NUM_DOUBLE:
			{
				d = (AST)_t;
				match(_t,NUM_DOUBLE);
				_t = _t.getNextSibling();
				r=Double.parseDouble(d.getText());
				break;
			}
			case NUM_LONG:
			{
				l = (AST)_t;
				match(_t,NUM_LONG);
				_t = _t.getNextSibling();
				r=Long.parseLong(d.getText());
				break;
			}
			case NUM_FLOAT:
			{
				f = (AST)_t;
				match(_t,NUM_FLOAT);
				_t = _t.getNextSibling();
				r=Float.parseFloat(d.getText());
				break;
			}
			case SIGN_MINUS:
			{
				AST __t142 = _t;
				AST tmp5_AST_in = (AST)_t;
				match(_t,SIGN_MINUS);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t142;
				_t = _t.getNextSibling();
				r=-1*a;
				break;
			}
			case SIGN_PLUS:
			{
				AST __t143 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,SIGN_PLUS);
				_t = _t.getFirstChild();
				a=expr(_t);
				_t = _retTree;
				_t = __t143;
				_t = _t.getNextSibling();
				if(a<0)r=0-a; else r=a;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return r;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"DOT",
		"SIGN_MINUS",
		"SIGN_PLUS",
		"PLUS",
		"MINUS",
		"STAR",
		"DIV",
		"NUM_INT",
		"NUM_DOUBLE",
		"NUM_LONG",
		"NUM_FLOAT",
		"LPAREN",
		"RPAREN",
		"WS",
		"HEX_DIGIT",
		"EXPONENT",
		"FLOAT_SUFFIX"
	};
	
	}
	
