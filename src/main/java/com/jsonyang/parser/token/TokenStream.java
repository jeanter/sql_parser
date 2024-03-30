package com.jsonyang.parser.token;

import java.util.List;

public class TokenStream {

	private int i = 0;

	private List<Token> tokens;

	public TokenStream(List<Token> tokens) {
		this.tokens = tokens;
		this.i = 0;
	}

	public Token peek() {
		return this.i < this.tokens.size() ? (Token) this.tokens.get(i) : null;
	}

	public final Token next() {
		if (i < tokens.size()) {
			return tokens.get(i++);
		} else {
			return null;
		}
	}

	public boolean consumeKeywords(List<String> texts) {
		int save = i;
		boolean result = true;
		for (String keyword : texts) {
			if (!consumeKeyword(keyword)) {
				i = save;
				result = false;
				break;
			}
		}
		return result;
	}

	public boolean consumeKeyword(String text) {
		Token peek = this.peek();
		if (peek != null && peek.getType() instanceof Keyword && peek.getText().equals(text)) {
			i++;
			return true;
		} else {
			return false;
		}
	}

	public boolean consumeTokenType(TokenType t) {
		Token peek = this.peek();
		if (peek != null && peek.getType() == t) {
			i++;
			return true;
		} else {
			return false;
		}
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}
}
