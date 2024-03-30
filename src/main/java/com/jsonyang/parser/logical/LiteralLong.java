package com.jsonyang.parser.logical;

public class LiteralLong implements Expr {

	Long n;

	public LiteralLong(Long n) {
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
		return "LiteralLong [n=" + n + "]";
	}

}
