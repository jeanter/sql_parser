package com.jsonyang.parser.expr;

import java.util.Arrays;
import java.util.List;

public class SqlSelect implements SqlRelation{
	/**
	 * 投影
	 */
	List<SqlExpr> projection;
	
	SqlExpr selection;
	
	
	List<SqlExpr> groupBy;
	
	List<SqlExpr> orderBy;
	
	SqlExpr having;
	
	String tableName;

	public SqlSelect(List<SqlExpr> projection, SqlExpr selection, List<SqlExpr> groupBy, List<SqlExpr> orderBy,
			SqlExpr having, String tableName) {
		super();
		this.projection = projection;
		this.selection = selection;
		this.groupBy = groupBy;
		this.orderBy = orderBy;
		this.having = having;
		this.tableName = tableName;
	}

	public List<SqlExpr> getProjection() {
		return projection;
	}

	public void setProjection(List<SqlExpr> projection) {
		this.projection = projection;
	}

	public SqlExpr getSelection() {
		return selection;
	}

	public void setSelection(SqlExpr selection) {
		this.selection = selection;
	}

	public List<SqlExpr> getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(List<SqlExpr> groupBy) {
		this.groupBy = groupBy;
	}

	public List<SqlExpr> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(List<SqlExpr> orderBy) {
		this.orderBy = orderBy;
	}

	public SqlExpr getHaving() {
		return having;
	}

	public void setHaving(SqlExpr having) {
		this.having = having;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "SqlSelect [projection=" + ( projection != null ? Arrays.toString(projection.toArray()):"null") + ", selection=" + selection + ", groupBy=" +( groupBy != null? Arrays.toString(groupBy.toArray()):"null")
				+ ", orderBy= " + (orderBy != null?Arrays.toString(orderBy.toArray()):" null") + ", having=" + having + ", tableName=" + tableName + "]";
	}
	
	
}
