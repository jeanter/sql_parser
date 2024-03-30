package com.jsonyang.parser;

import com.jsonyang.parser.expr.SqlBinaryExpr;
import com.jsonyang.parser.expr.SqlDouble;
import com.jsonyang.parser.expr.SqlExpr;
import com.jsonyang.parser.expr.SqlLong;
import com.jsonyang.parser.logical.Add;
import com.jsonyang.parser.logical.Divide;
import com.jsonyang.parser.logical.Expr;
import com.jsonyang.parser.logical.LiteralDouble;
import com.jsonyang.parser.logical.LiteralLong;
import com.jsonyang.parser.logical.Modulus;
import com.jsonyang.parser.logical.Multiply;
import com.jsonyang.parser.logical.Subtract;
import com.jsonyang.parser.token.SqlTokenizer;
import com.jsonyang.parser.token.TokenStream;

public class ExecExprParser {

	/**
	 * 目前只实现了部分数学运算的表达式计算 
	 * @param expr
	 * @return
	 */
	Expr createPhysicalExpr(SqlExpr expr) {
		if (expr instanceof SqlLong) {
			return new LiteralLong(((SqlLong) expr).getValue());
		} else if (expr instanceof SqlDouble) {
			return new LiteralDouble(((SqlDouble) expr).getValue());
		} else if (expr instanceof SqlBinaryExpr) {
			Expr l = createPhysicalExpr(((SqlBinaryExpr) expr).getL());
			Expr r = createPhysicalExpr(((SqlBinaryExpr) expr).getR());
			switch (((SqlBinaryExpr) expr).getOp()) {
			case "+":
				return new Add(l, r);
			case "-":
				return new Subtract(l, r);
			case "*":
				return new Multiply(l, r);
			case "/":
				return new Divide(l, r);
			case "%":
				return new Modulus(l, r);
			}
		}
		return null;
	}

	public static void main(String args[]) {

		SqlTokenizer tokenizer = null;
		TokenStream ts = null;
		SqlParser parser = null;
		tokenizer = new SqlTokenizer("((1+2)*4)/2");
		ts = tokenizer.tokenize();
		parser = new SqlParser(ts);
		SqlExpr sqlExpr = parser.parse(0);
		System.out.println(sqlExpr.toString());
		ExecExprParser exprParser = new ExecExprParser();
		Expr expr = exprParser.createPhysicalExpr(sqlExpr);
		System.out.println(expr.toString());
		System.out.println(expr.getValue().getValue().getClass().getSimpleName());
		System.out.println(expr.getValue().getValue());

	}
}
