package com.jsonyang.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.jsonyang.parser.expr.SqlCast;
import com.jsonyang.parser.expr.SqlDouble;
import com.jsonyang.parser.expr.SqlExpr;
import com.jsonyang.parser.expr.SqlFunction;
import com.jsonyang.parser.expr.SqlIdentifier;
import com.jsonyang.parser.expr.SqlLong;
import com.jsonyang.parser.expr.SqlSelect;
import com.jsonyang.parser.expr.SqlSort;
import com.jsonyang.parser.expr.SqlString;
import com.jsonyang.parser.token.Keyword;
import com.jsonyang.parser.token.Literal;
import com.jsonyang.parser.token.SqlTokenizer;
import com.jsonyang.parser.token.Symbol;
import com.jsonyang.parser.token.Token;
import com.jsonyang.parser.token.TokenStream;
import com.jsonyang.parser.token.TokenType;
import com.jsonyang.parser.exception.SQLParserException;
import com.jsonyang.parser.expr.SqlAlias;
import com.jsonyang.parser.expr.SqlBinaryExpr;

public class SqlParser {

	TokenStream tokens;

	public SqlParser(TokenStream tokens) {
		super();
		this.tokens = tokens;
	}

	/**
	 * 1 + 2 * 3
	 * Tokens:      [1]  [+]  [2]  [*]  [3]
       Precedence:  [0]  [50] [0]  [60] [0]
       SqlBinaryExpr [l=SqlLong [value=1], op= '+', r=SqlBinaryExpr [l=SqlLong [value=2], op= '*', r=SqlLong [value=3]]]
       1 + 2 + 3
	   Tokens:      [1]  [+]  [2]  [+]  [3]
       Precedence:  [0]  [50] [0]  [50] [0] 
       SqlBinaryExpr [l=SqlBinaryExpr [l=SqlLong [value=1], op= '+', r=SqlLong [value=2]], op= '+', r=SqlLong [value=3]]
	 * @param precedence
	 * @return
	 */
	public SqlExpr parse(int precedence) {
		SqlExpr expr = parsePrefix();
		while (precedence < nextPrecedence()) {
			expr = parseInfix(expr, nextPrecedence());
		}
		return expr;
	}
	/**
	 * 遇到操作符 结合符 结合符号右边的表达式  
	 * 
	 * @param left
	 * @param Int
	 * @return
	 */
	public SqlExpr parseInfix(SqlExpr left, int precedence) {
		Token token = tokens.peek();
		if (token == null) {
			return null;
		}
		TokenType type = token.getType();
		SqlExpr expr = null;
		if (type instanceof Symbol) {
			switch ((Symbol) type) {
			case PLUS:
			case SUB:
			case STAR:
			case SLASH:
			case EQ:
			case GT:
			case LT:
				tokens.next();
				expr = new SqlBinaryExpr(left, token.getText(), parse(precedence));
				break;
			case LEFT_PAREN:
				if (left instanceof SqlIdentifier) {
					tokens.next();
					List<SqlExpr> exprList = parseExprList();
					tokens.consumeTokenType(Symbol.RIGHT_PAREN);
					expr = new SqlFunction(((SqlIdentifier) left).getId(), exprList);
				} else {
					throw new IllegalStateException("Unexpected LPAREN");
				}
				break;
			default:
				break;
			}
		} else if (type instanceof Keyword) {
			switch ((Keyword) type) {
			case AS:
				tokens.next();
				expr = new SqlAlias(left, parseIdentifier());
				break;
			case AND:
			case OR:
				tokens.next();
				expr = new SqlBinaryExpr(left, token.getText(), parse(precedence));
				break;
			case ASC:
			case DESC:
				tokens.next();
				expr = new SqlSort(left, token.getType() == Keyword.ASC);
				break;
			default:
				break;
			}
		}
		return expr;
	}

	/**
	 * 处理最小单位的表达式
	 * 
	 * @return
	 */
	public SqlExpr parsePrefix() {
		Token token = tokens.next();
		if (token == null) {
			return null;
		}
		TokenType type = token.getType();
		SqlExpr expr = null;
		if (type instanceof Keyword) {
			switch ((Keyword) type) {
			case SELECT:
				expr = parseSelect();
				break;
			case CAST:
				expr = parseCast();
				break;
			case MAX:
				expr = new SqlIdentifier(token.getText());
				break;
			case INT:
				expr = new SqlIdentifier(token.getText());
				break;
			case DOUBLE:
				expr = new SqlIdentifier(token.getText());
				break;
			default:
				break;
			}
		} else if (type instanceof Literal) {
			switch ((Literal) type) {
			case IDENTIFIER:
				expr = new SqlIdentifier(token.getText());
				break;
			case STRING:
				expr = new SqlString(token.getText());
				break;
			case LONG:
				expr = new SqlLong(Long.valueOf(token.getText()));
				break;
			case DOUBLE:
				expr = new SqlDouble(Double.valueOf(token.getText()));
				break;
			}
		} else if (type instanceof Symbol) {
			switch ((Symbol) type) {
			// 处理( 高优先级
			case LEFT_PAREN:
				expr = parseLeftParen();
				break;
			default:
				break;
			}
		}
		return expr;
	}

	public int nextPrecedence() {
		Token token = tokens.peek();
		if (token == null) {
			return 0;
		}
		int precedence = 0;
		TokenType type = token.getType();
		if (type instanceof Keyword) {
			switch ((Keyword) type) {
			case AS:
			case ASC:
			case DESC:
				precedence = 10;
				break;
			case OR:
				precedence = 20;
				break;
			case AND:
				precedence = 30;
				break;
			default:
				precedence = 0;
			}
		} else if (type instanceof Symbol) {
			switch ((Symbol) type) {
			case LT:
			case LT_EQ:
			case EQ:
			case BANG_EQ:
			case GT_EQ:
			case GT:
				precedence = 40;
				break;
			case PLUS:
			case SUB:
				precedence = 50;
				break;
			case STAR:
			case SLASH:
				precedence = 60;
				break;
			case LEFT_PAREN:
				precedence = 70;
				break;
			default:
				precedence = 0;
			}

		}
		return precedence;
	}

	public List<SqlExpr> parseOrder() {
		List<SqlExpr> exprList = new ArrayList<SqlExpr>();
		SqlExpr sort = parseExpr();
		while (sort != null) {
			if (sort instanceof SqlIdentifier) {
				sort = new SqlSort(sort, true);
			} else if (sort instanceof SqlSort) {
				// sort = sort;
			} else {
				throw new IllegalStateException("Unexpected expression " + sort.toString() + " after order by.");
			}
			exprList.add((SqlSort) sort);
			Token token = tokens.peek();
			if (token != null && token.getType() == Symbol.COMMA) {
				tokens.next();
			} else {
				break;
			}
			sort = parseExpr();
		}
		return exprList;
	}

	public SqlCast parseCast() {
		tokens.consumeTokenType(Symbol.LEFT_PAREN);
		SqlExpr expr = parseExpr();
		if (expr == null) {
			throw new SQLParserException("");
		}
		expr = (SqlAlias) expr;
		tokens.consumeTokenType(Symbol.RIGHT_PAREN);
		return new SqlCast(((SqlAlias) expr).getSqlExpr(), ((SqlAlias) expr).getAlias());
	}

	public SqlExpr parseLeftParen() {
		SqlExpr expr = parseExpr();
		if (expr == null) {
			throw new SQLParserException("");
		}
		tokens.consumeTokenType(Symbol.RIGHT_PAREN);
		return expr;
	}

	public SqlSelect parseSelect() {
		SqlSelect sqlSelect = null;
		List<SqlExpr> projection = parseExprList();
		if (tokens.consumeKeyword("FROM")) {
			SqlIdentifier table = (SqlIdentifier) parseExpr();
			SqlExpr filterExpr = null;
			if (tokens.consumeKeyword("WHERE")) {
				filterExpr = parseExpr();
			}
			List<SqlExpr> groupBy = null;
			if (tokens.consumeKeywords(Arrays.asList("GROUP", "BY"))) {
				groupBy = parseExprList();
			}
			SqlExpr havingExpr = null;
			if (tokens.consumeKeyword("HAVING")) {
				havingExpr = parseExpr();
			}
			// parse optional ORDER BY clause
			List<SqlExpr> orderBy = null;
			if (tokens.consumeKeywords(Arrays.asList("ORDER", "BY"))) {
				orderBy = parseOrder();
			}
			sqlSelect = new SqlSelect(projection, filterExpr, groupBy, orderBy, havingExpr, table.getId());
			return sqlSelect;
		} else {
			throw new IllegalStateException("Expected FROM keyword, found " + tokens.peek());
		}

	}

	public List<SqlExpr> parseExprList() {
		List<SqlExpr> exprList = new ArrayList<SqlExpr>();
		SqlExpr expr = parseExpr();
		while (expr != null) {
			exprList.add(expr);
			Token token = tokens.peek();
			if (token != null && token.getType() == Symbol.COMMA) {
				tokens.next();
			} else {
				break;
			}
			expr = parseExpr();
		}
		return exprList;
	}

	public SqlExpr parseExpr() {
		return parse(0);
	}

	public SqlIdentifier parseIdentifier() {
		SqlExpr expr = parseExpr();
		if (expr != null && expr instanceof SqlIdentifier) {
			return (SqlIdentifier) expr;
		} else {
			throw new SQLParserException("Expected identifier, found " + expr);
		}

	}

	public static void main(String[] args) {

		String sql = "SELECT state, MAX(salary) AS top_wage FROM employee GROUP BY state HAVING MAX(salary) > 10 AND MAX(salary) < 100";
		SqlTokenizer tokenizer = null;
		TokenStream ts = null;
		SqlParser parser = null;
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		parser = new SqlParser(ts);
		System.out.println(parser.parse(0).toString());

		sql = "SELECT * FROM employee WHERE  (a='1' or b = '2')  and c = '5' ";
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		parser = new SqlParser(ts);
		System.out.println(parser.parse(0).toString());

		sql = "1 + 2 + 3";
		tokenizer = new SqlTokenizer(sql);
		ts = tokenizer.tokenize();
		parser = new SqlParser(ts);
		System.out.println(parser.parse(0).toString());

	}
}
