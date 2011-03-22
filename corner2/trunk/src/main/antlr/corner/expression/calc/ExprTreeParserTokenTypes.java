// $ANTLR : "calc.g" -> "ExprParser.java"$

	
package corner.expression.calc;

public interface ExprTreeParserTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int DOT = 4;
	int SIGN_MINUS = 5;
	int SIGN_PLUS = 6;
	int PLUS = 7;
	int MINUS = 8;
	int STAR = 9;
	int DIV = 10;
	int NUM_INT = 11;
	int NUM_DOUBLE = 12;
	int NUM_LONG = 13;
	int NUM_FLOAT = 14;
	int LPAREN = 15;
	int RPAREN = 16;
	int WS = 17;
	int HEX_DIGIT = 18;
	int EXPONENT = 19;
	int FLOAT_SUFFIX = 20;
}
