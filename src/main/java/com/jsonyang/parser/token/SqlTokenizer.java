package com.jsonyang.parser.token;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import com.jsonyang.parser.exception.TokenizeException;

public class SqlTokenizer {

	public String sql;

	public char[] chars;

	public int offset;

	public SqlTokenizer(String sql) {
		this.sql = sql;
		this.chars = sql.toCharArray();

	}

	public TokenStream tokenize() {
		List<Token> tokens = new ArrayList<Token>();
		Token token = nextToken();
		while (token != null) {
			tokens.add(token);
			token = nextToken();
		}
		return new TokenStream(tokens);
	}

	Token nextToken() {
		offset = skipWhitespace(offset);
		Token token = null;
		if (offset >= sql.length()) {
			return token;
		} else {
			if (Literal.isIdentifierStart(chars[offset])) {
				token = scanIdentifier(offset);
				offset = token.getEndOffset();
			} else if (Literal.isNumberStart(chars[offset])) {
				token = scanNumber(offset);
				offset = token.getEndOffset();
			} else if (Symbol.isSymbolStart(chars[offset])) {
				token = scanSymbol(offset);
				offset = token.getEndOffset();
				// ' "包围的字符
			} else if (Literal.isCharsStart(chars[offset])) {
				token = scanChars(offset, chars[offset]);
				offset = token.getEndOffset();
			}

			return token;
		}
	}

	Token scanIdentifier(int startOffset) {
		if ('`' == chars[startOffset]) {
			int endOffset = getOffsetUntilTerminatedChar('`', startOffset);
			return new Token(sql.substring(offset, endOffset), Literal.IDENTIFIER, endOffset);
		}

		int endOffset = indexOfFirst(startOffset, ch -> !Literal.isIdentifierPart(ch));
		String text = sql.substring(startOffset, endOffset);
		if (isAmbiguousIdentifier(text)) {
			return new Token(text, processAmbiguousIdentifier(endOffset, text), endOffset);
		} else {
			// 先看是不是关键字 不是关键字 就是标识符
			TokenType tokenType = Keyword.typeOf(text);
			if (tokenType == null) {
				tokenType = Literal.IDENTIFIER;
			}
			return new Token(text, tokenType, endOffset);
		}
	}

	/**
	 * 
	 * @param startOffset
	 * @return
	 */
	Token scanNumber(int startOffset) {
		int endOffset = 0;
		if (('-' == chars[startOffset])) {
			endOffset = indexOfFirst(startOffset + 1, ch->!Character.isDigit(ch));
		} else {
			endOffset = indexOfFirst(startOffset, ch->!Character.isDigit(ch));
		}
		// System.out.println("startOffset : "+startOffset + "; endOffset "+endOffset);
		if (endOffset == sql.length()) {
			return new Token(sql.substring(startOffset, endOffset), Literal.LONG, endOffset);
		}
		boolean isFloat = '.' == chars[endOffset];
		// System.out.println(" char "+chars[endOffset]);
		if (isFloat) {
			endOffset = indexOfFirst(endOffset + 1, ch->!Character.isDigit(ch));
		}
	
		return new Token(sql.substring(startOffset, endOffset), isFloat ? Literal.DOUBLE : Literal.LONG, endOffset);
	}

	Token scanSymbol(int startOffset) {
		int endOffset = indexOfFirst(startOffset, ch->!Symbol.isSymbol(ch));
		String text = sql.substring(startOffset, endOffset);
		Symbol symbol = (Symbol) Symbol.typeOf(text);
		while (symbol == null) {
			text = sql.substring(offset, --endOffset);
			symbol = (Symbol) Symbol.typeOf(text);
		}
		return new Token(text, symbol, endOffset);
	}

	Token scanChars(int startOffset, char terminatedChar) {
		int endOffset = getOffsetUntilTerminatedChar(terminatedChar, startOffset + 1);
		return new Token(sql.substring(startOffset + 1, endOffset), Literal.STRING, endOffset + 1);
	}

	/**
	 * table name: group / order keyword: group by / order by
	 * 
	 * @param text
	 * @return
	 */
	boolean isAmbiguousIdentifier(String text) {
		return Keyword.ORDER.name().equals(text.toUpperCase()) || Keyword.GROUP.name().equals(text.toUpperCase());
	}

	/**
	 * process group by | order by
	 */
	TokenType processAmbiguousIdentifier(int startOffset, String text) {
		int skipWhitespaceOffset = skipWhitespace(startOffset);
		if (skipWhitespaceOffset != sql.length()) {
			String continueText = sql.substring(skipWhitespaceOffset, skipWhitespaceOffset + 2);
			if (Keyword.BY.name().equals(continueText)) {
				TokenType result = Keyword.typeOf(text);
				if (result != null) {
					return result;
				} else {
					return Literal.IDENTIFIER;
				}
			}
		}
		return Literal.IDENTIFIER;
	}

	int indexOfFirst2(int startIndex, Matchable match) {
		for (int i = startIndex; i < chars.length; i++) {
			if (match.match(chars[i])) {
				return i;
			}
		}
		return sql.length();
	}
	/**
	 * Predicate版本
	 * @param startIndex
	 * @param check
	 * @return
	 */
	int indexOfFirst(int startIndex, Predicate<Character> check) {
		for (int i = startIndex; i < chars.length; i++) {
			if (check.test(chars[i])) {
				return i;
			}
		}
		return sql.length();
	}

	int skipWhitespace(int i) {
		for (; i < sql.length() && (Character.isWhitespace(chars[i])); i++) {
		}
		return i;
	}

	int skipWhitespace(int i, String s) {
		for (; i < s.length() && (chars[i] == ' ' || chars[i] == '\t'); i++) {
		}
		return i;
	}

	int skipWhitespace(int start, CharSequence text) {
		while (start < text.length()) {
			char c = chars[start];
			if (!Character.isWhitespace(c)) {
				break;
			}
			start++;
		}
		return start;
	}

	/**
	 * find another char's offset equals terminatedChar
	 * 
	 * @param terminatedChar
	 * @param startOffset
	 * @return
	 */
	int getOffsetUntilTerminatedChar(char terminatedChar, int startOffset) {
		int offset = sql.indexOf(terminatedChar, startOffset);
		if (offset != -1) {
			return offset;
		} else {
			throw new TokenizeException(
					"Must contain " + terminatedChar + " in remain sql[" + startOffset + " .. end]");
		}
	}

	public static void main(String[] args) {
		String sql = "SELECT id, first_name, last_name FROM employee";
		SqlTokenizer tokenizer = null;
		TokenStream ts = null;
		List<Token> tokens = null;
		tokenizer = new SqlTokenizer("SELECT id, first_name, last_name FROM employee");
		ts = tokenizer.tokenize();
		ts.getTokens().forEach(t -> {
			System.out.println(t.toString());
		});
		System.out.println("------------------------------------------------------------------");
		sql = "SELECT salary * 0.1 FROM employee";
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		tokens = ts.getTokens();
		for (Token tk : tokens) {
			System.out.println(tk.toString());
		}
		System.out.println("------------------------------------------------------------------");
		sql = "SELECT salary * 0.1 AS bonus FROM employee";
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		ts.getTokens().forEach(t -> {
			System.out.println(t.toString());
		});
		System.out.println("------------------------------------------------------------------");

		sql = "SELECT a, b FROM employee WHERE state = 'CO'";
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		ts.getTokens().forEach(t -> {
			System.out.println(t.toString());
		});
		System.out.println("------------------------------------------------------------------");

		sql = "SELECT state, MAX(salary) FROM employee GROUP BY state";
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		ts.getTokens().forEach(t -> {
			System.out.println(t.toString());
		});
		System.out.println("------------------------------------------------------------------");

		sql = "SELECT state, MAX(salary) FROM employee GROUP BY state HAVING MAX(salary) > 10";
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		ts.getTokens().forEach(t -> {
			System.out.println(t.toString());
		});

		sql = "SELECT state, MAX(salary) FROM employee GROUP BY state HAVING MAX(salary) > 10";
		System.out.println("------------------------------------------------------------------");
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		ts.getTokens().forEach(t -> {
			System.out.println(t.toString());
		});
		System.out.println("------------------------------------------------------------------");

		sql = "123456789 + 987654321";
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		ts.getTokens().forEach(t -> {
			System.out.println(t.toString());
		});
		System.out.println("------------------------------------------------------------------");

		sql = "123456789.00 + 987654321.001";
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		ts.getTokens().forEach(t -> {
			System.out.println(t.toString());
		});
		System.out.println("------------------------------------------------------------------");

		sql = "select * from group";
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		ts.getTokens().forEach(t -> {
			System.out.println(t.toString());
		});
		System.out.println("------------------------------------------------------------------");

		sql = "(2+6*4+6/2)/2";
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		ts.getTokens().forEach(t -> {
			System.out.println(t.toString());
		});

		System.out.println("------------------------------------------------------------------");

		sql = "a<=b";
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		ts.getTokens().forEach(t -> {
			System.out.println(t.toString());
		});
	}
}
