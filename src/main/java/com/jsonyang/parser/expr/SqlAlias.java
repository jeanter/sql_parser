package com.jsonyang.parser.expr;

public class SqlAlias implements SqlExpr {
	
	SqlExpr sqlExpr;
	
	SqlIdentifier alias;

	public SqlAlias(SqlExpr sqlExpr, SqlIdentifier alias) {
		super();
		this.sqlExpr = sqlExpr;
		this.alias = alias;
	}

	public SqlExpr getSqlExpr() {
		return sqlExpr;
	}

	public void setSqlExpr(SqlExpr sqlExpr) {
		this.sqlExpr = sqlExpr;
	}

	public SqlIdentifier getAlias() {
		return alias;
	}

	public void setAlias(SqlIdentifier alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		return "SqlAlias [sqlExpr=" + sqlExpr + ", alias=" + alias + "]";
	}
	
	

}
