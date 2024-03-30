package com.jsonyang.parser.expr;

public class SqlLong implements SqlExpr {
	
	/** SQL literal long */
	Long value;

	public SqlLong(Long value) {
		super();
		this.value = value;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SqlLong [value=" + value + "]";
	}

}
