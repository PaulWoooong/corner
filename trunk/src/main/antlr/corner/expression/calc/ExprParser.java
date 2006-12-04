// $ANTLR : "calc.g" -> "ExprParser.java"$

	
package corner.expression.calc;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

/**
 * expression parser
 * @author Jun Tsai
 * @version $Revision$
 * @since 2.0.5
 */
public class ExprParser extends antlr.LLkParser       implements ExprTokenTypes
 {

protected ExprParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public ExprParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected ExprParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public ExprParser(TokenStream lexer) {
  this(lexer,2);
}

public ExprParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void imaginaryTokenDefinitions() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST imaginaryTokenDefinitions_AST = null;
		
		AST tmp7_AST = null;
		tmp7_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp7_AST);
		match(SIGN_MINUS);
		AST tmp8_AST = null;
		tmp8_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp8_AST);
		match(SIGN_PLUS);
		imaginaryTokenDefinitions_AST = (AST)currentAST.root;
		returnAST = imaginaryTokenDefinitions_AST;
	}
	
	public final void expr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expr_AST = null;
		
		mexpr();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop89:
		do {
			if ((LA(1)==PLUS||LA(1)==MINUS)) {
				{
				switch ( LA(1)) {
				case PLUS:
				{
					AST tmp9_AST = null;
					tmp9_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp9_AST);
					match(PLUS);
					break;
				}
				case MINUS:
				{
					AST tmp10_AST = null;
					tmp10_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp10_AST);
					match(MINUS);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				mexpr();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop89;
			}
			
		} while (true);
		}
		expr_AST = (AST)currentAST.root;
		returnAST = expr_AST;
	}
	
	public final void mexpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST mexpr_AST = null;
		
		signExpr();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop93:
		do {
			if ((LA(1)==STAR||LA(1)==DIV)) {
				{
				switch ( LA(1)) {
				case STAR:
				{
					AST tmp11_AST = null;
					tmp11_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp11_AST);
					match(STAR);
					break;
				}
				case DIV:
				{
					AST tmp12_AST = null;
					tmp12_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp12_AST);
					match(DIV);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				atom();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop93;
			}
			
		} while (true);
		}
		mexpr_AST = (AST)currentAST.root;
		returnAST = mexpr_AST;
	}
	
	public final void signExpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST signExpr_AST = null;
		Token  m = null;
		AST m_AST = null;
		Token  p = null;
		AST p_AST = null;
		
		{
		switch ( LA(1)) {
		case MINUS:
		{
			m = LT(1);
			m_AST = astFactory.create(m);
			astFactory.makeASTRoot(currentAST, m_AST);
			match(MINUS);
			m_AST.setType(SIGN_MINUS);
			break;
		}
		case PLUS:
		{
			p = LT(1);
			p_AST = astFactory.create(p);
			astFactory.makeASTRoot(currentAST, p_AST);
			match(PLUS);
			p_AST.setType(SIGN_PLUS);
			break;
		}
		case NUM_INT:
		case NUM_DOUBLE:
		case NUM_LONG:
		case NUM_FLOAT:
		case LPAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		atom();
		astFactory.addASTChild(currentAST, returnAST);
		signExpr_AST = (AST)currentAST.root;
		returnAST = signExpr_AST;
	}
	
	public final void atom() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST atom_AST = null;
		
		switch ( LA(1)) {
		case NUM_INT:
		{
			AST tmp13_AST = null;
			tmp13_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp13_AST);
			match(NUM_INT);
			atom_AST = (AST)currentAST.root;
			break;
		}
		case NUM_DOUBLE:
		{
			AST tmp14_AST = null;
			tmp14_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp14_AST);
			match(NUM_DOUBLE);
			atom_AST = (AST)currentAST.root;
			break;
		}
		case NUM_LONG:
		{
			AST tmp15_AST = null;
			tmp15_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp15_AST);
			match(NUM_LONG);
			atom_AST = (AST)currentAST.root;
			break;
		}
		case NUM_FLOAT:
		{
			AST tmp16_AST = null;
			tmp16_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp16_AST);
			match(NUM_FLOAT);
			atom_AST = (AST)currentAST.root;
			break;
		}
		case LPAREN:
		{
			match(LPAREN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			atom_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = atom_AST;
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
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	
	}
