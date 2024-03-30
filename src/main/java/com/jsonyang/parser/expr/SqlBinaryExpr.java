package com.jsonyang.parser.expr;

/**
 * 二元表达式
 * @author diannao
 *
 */
public class SqlBinaryExpr implements SqlExpr {

	SqlExpr l;

	String op;

	SqlExpr r;

	public SqlBinaryExpr(SqlExpr l, String op, SqlExpr r) {
		super();
		this.l = l;
		this.op = op;
		this.r = r;
	}

	public SqlExpr getL() {
		return l;
	}

	public void setL(SqlExpr l) {
		this.l = l;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public SqlExpr getR() {
		return r;
	}

	public void setR(SqlExpr r) {
		this.r = r;
	}

	@Override
	public String toString() {
		return "SqlBinaryExpr [l=" + l + ", op= '" + op + "', r=" + r + "]";
	}

}
