package com.jsonyang.parser.expr;

public class SqlCast  implements SqlExpr {
	
	SqlExpr sqlExpr;

	SqlIdentifier dataType;

	public SqlCast(SqlExpr sqlExpr, SqlIdentifier dataType) {
		super();
		this.sqlExpr = sqlExpr;
		this.dataType = dataType;
	}

	public SqlExpr getSqlExpr() {
		return sqlExpr;
	}

	public void setSqlExpr(SqlExpr sqlExpr) {
		this.sqlExpr = sqlExpr;
	}

	public SqlIdentifier getDataType() {
		return dataType;
	}

	public void setDataType(SqlIdentifier dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return "SqlCast [sqlExpr=" + sqlExpr + ", dataType=" + dataType + "]";
	}
	
	
	
}


