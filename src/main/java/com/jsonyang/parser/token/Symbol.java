package com.jsonyang.parser.token;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum Symbol implements TokenType {

	 LEFT_PAREN("("),
	    RIGHT_PAREN(")"),
	    LEFT_BRACE("{"),
	    RIGHT_BRACE("}"),
	    LEFT_BRACKET("["),
	    RIGHT_BRACKET("]"),
	    SEMI(";"),
	    COMMA(","),
	    DOT("."),
	    DOUBLE_DOT(".."),
	    PLUS("+"),
	    SUB("-"),
	    STAR("*"),
	    SLASH("/"),
	    QUESTION("?"),
	    EQ("="),
	    GT(">"),
	    LT("<"),
	    BANG("!"),
	    TILDE("~"),
	    CARET("^"),
	    PERCENT("%"),
	    COLON(":"),
	    DOUBLE_COLON("::"),
	    COLON_EQ(":="),
	    LT_EQ("<="),
	    GT_EQ(">="),
	    LT_EQ_GT("<=>"),
	    LT_GT("<>"),
	    BANG_EQ("!="),
	    BANG_GT("!>"),
	    BANG_LT("!<"),
	    AMP("&"),
	    BAR("|"),
	    DOUBLE_AMP("&&"),
	    DOUBLE_BAR("||"),
	    DOUBLE_LT("<<"),
	    DOUBLE_GT(">>"),
	    AT("@"),
	    POUND("#");
	
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	Symbol(String text) {
		this.text = text;
	}

	public static boolean isSymbol(Character ch) {
		List<String> stringList = Arrays.asList(Symbol.values()).stream().map(it -> it.getText())
				.collect(Collectors.toList());
		Set<Character> charSet = new HashSet<Character>();
		for (String it : stringList) {
			Set<Character> set = it.chars().mapToObj((i) -> Character.valueOf((char) i)).collect(Collectors.toSet());
			charSet.addAll(set);
		}
		return charSet.contains(ch);
		// return textSet.contains(text);
	}

	public static boolean isSymbolStart(Character ch) {
		return isSymbol(ch);
	}
	
	public static TokenType typeOf(String keyWord) {
		Map<String, TokenType> map = 	Arrays.asList(Symbol.values()).stream().
				collect(Collectors.toMap(Symbol::getText, TokenType -> TokenType));
		return map.get(keyWord);
	}
}
