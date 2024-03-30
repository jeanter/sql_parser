package com.jsonyang.parser;

import com.jsonyang.parser.expr.SqlExpr;

//public abstract class PrattParser {
//
//	public SqlExpr parse(int precedence) {
//		SqlExpr expr = parsePrefix();
//		while (precedence < nextPrecedence()) {
//			expr = parseInfix(expr, nextPrecedence());
//		}
//		return expr;
//	}
//
//	public abstract int nextPrecedence();
//
//	public abstract SqlExpr parsePrefix();
//
//	public abstract SqlExpr parseInfix(SqlExpr left, int precedence);
//
//}
