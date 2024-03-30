package com.jsonyang.parser.expr;

public class SqlDouble implements SqlExpr  {
	
	Double value;

	public SqlDouble(Double value) {
		super();
		this.value = value;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SqlDouble [value=" + value + "]";
	}
	
	

}
