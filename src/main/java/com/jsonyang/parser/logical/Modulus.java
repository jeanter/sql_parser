package com.jsonyang.parser.logical;

public class Modulus extends MathExpr {

	public Modulus( Expr l, Expr r) {
		super(l, r);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Value getValue() {
		Value l = left.getValue();
		Value r = right.getValue();
		if (l.getValue() instanceof Long && r.getValue() instanceof Long) {
			return new Value(l.getLong() % r.getLong());
		} else if (l.getValue() instanceof Double && r.getValue() instanceof Double) {
			return new Value(l.getDouble() % r.getDouble());
		} else if (l.getValue() instanceof Long && r.getValue() instanceof Double) {
			return new Value(l.getLong() % r.getDouble());
		} else if (l.getValue() instanceof Double && r.getValue() instanceof Long) {
			return new Value(l.getDouble() % r.getLong());
		}else {
			throw new IllegalStateException("Expected the same expr "+ this.toString());
		}
	}
	
	@Override
	public String toString() {
		return "Modulus [left=" + left + ", right=" + right + "]";
	}
}
