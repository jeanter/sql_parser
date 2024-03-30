package com.jsonyang.parser.expr;

public class SqlString implements SqlExpr  {
	
	/** SQL literal string */
	String value;

	public SqlString(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SqlString [value=" + value + "]";
	}
	 
	

}
