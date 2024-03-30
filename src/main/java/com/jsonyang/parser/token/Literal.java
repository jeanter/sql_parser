package com.jsonyang.parser.token;
/**
 * 字面量
 * @author diannao
 *
 */
public enum Literal  implements TokenType {
	    LONG,
	    DOUBLE,
	    STRING,
	    IDENTIFIER;
	
	public static boolean isNumberStart(Character ch) {
		 return Character.isDigit(ch) || '.' == ch;
	}
	
	public static boolean isIdentifierStart(Character ch) {
		 return Character.isLetter(ch) ;
	}
	
	
	public static boolean isIdentifierPart(Character ch) {
		 return Character.isLetter(ch) || Character.isDigit(ch) || ch == '_';
		 
	}
	
	public static boolean isCharsStart(Character ch) {
		return '\'' == ch || '"' == ch;
	}
}
