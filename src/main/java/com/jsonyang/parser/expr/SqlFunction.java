package com.jsonyang.parser.expr;

import java.util.Arrays;
import java.util.List;

/** SQL function call */
public class SqlFunction implements SqlExpr  {
	
	String id ;
	
	List<SqlExpr> sqlExprs;

	public SqlFunction(String id, List<SqlExpr> sqlExprs) {
		super();
		this.id = id;
		this.sqlExprs = sqlExprs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<SqlExpr> getSqlExprs() {
		return sqlExprs;
	}

	public void setSqlExprs(List<SqlExpr> sqlExprs) {
		this.sqlExprs = sqlExprs;
	}

	@Override
	public String toString() {
		return "SqlFunction [id=" + id + ", sqlExprs=" + Arrays.toString(sqlExprs.toArray()) + "]";
	}
	
	
	
	

}
