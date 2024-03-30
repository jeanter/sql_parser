package com.jsonyang.parser.logical;

public class LiteralDouble implements Expr {

	Double n;

	public LiteralDouble(Double n) {
		super();
		this.n = n;
	}

	@Override
	public Value getValue() {
		// TODO Auto-generated method stub
		return new Value(n);
	}

	@Override
	public String toString() {
		return "LiteralDouble [n=" + n + "]";
	}

}
