package com.jsonyang.parser.token;

public final class Token {

	private final String text;

	private final TokenType type;
	
	private final int endOffset;

	public String toString() {
		TokenType var = this.type;
		String typeType = var instanceof Keyword ? "Keyword"
				: (var instanceof Symbol ? "Symbol" : (var instanceof Literal ? "Literal" : ""));
		return "Token(\"" + this.text + "\", " + typeType + '.' + this.type + ", " + this.endOffset + ')';
	}

	public final String getText() {
		return this.text;
	}

	public final TokenType getType() {
		return this.type;
	}

	public final int getEndOffset() {
		return this.endOffset;
	}

	public Token(String text, TokenType type, int endOffset) {
		this.text = text;
		this.type = type;
		this.endOffset = endOffset;
	}

 
	public final String component1() {
		return this.text;
	}

 
	public final TokenType component2() {
		return this.type;
	}

	public final int component3() {
		return this.endOffset;
	}

}
