package com.jsonyang.parser.expr;

public class SqlIdentifier implements SqlExpr {
	
	
	public SqlIdentifier(String id) {
		super();
		this.id = id;
	}

	String id ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "SqlIdentifier [id=" + id + "]";
	}
	
	
	

}
