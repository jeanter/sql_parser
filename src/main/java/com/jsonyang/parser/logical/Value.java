package com.jsonyang.parser.logical;

public class Value {

	private Object v;

	public Value(Object v) {
		super();
		this.v = v;
	}

	public Double getDouble() {
		return (Double) v;
	}

	public Long getLong() {
		return (Long) v;
	}

	public Object getValue() {
		return v;
	}

	public String getStringValue() {
		return (String) v;
	}

}
