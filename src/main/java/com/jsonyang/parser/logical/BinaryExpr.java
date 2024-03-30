package com.jsonyang.parser.logical;

public abstract class BinaryExpr implements Expr {

	Expr left;

	Expr right;

	public BinaryExpr(Expr left, Expr right) {

		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return "BinaryExpr [left=" + left + ", right=" + right + "]";
	}

}
