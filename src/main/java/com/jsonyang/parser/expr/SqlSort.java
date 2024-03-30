package com.jsonyang.parser.expr;

public class SqlSort implements SqlExpr {

	SqlExpr sqlExpr;

	Boolean asc;

	public SqlSort(SqlExpr sqlExpr, Boolean asc) {
		super();
		this.sqlExpr = sqlExpr;
		this.asc = asc;
	}

	public SqlExpr getSqlExpr() {
		return sqlExpr;
	}

	public void setSqlExpr(SqlExpr sqlExpr) {
		this.sqlExpr = sqlExpr;
	}

	public Boolean getAsc() {
		return asc;
	}

	public void setAsc(Boolean asc) {
		this.asc = asc;
	}

	@Override
	public String toString() {
		return "SqlSort [sqlExpr=" + sqlExpr + ", asc=" + asc + "]";
	}

}
