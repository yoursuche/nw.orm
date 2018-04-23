package nw.orm.filters;

import java.util.HashMap;
import java.util.Map;

public class JpqlFilter implements Filter {
	
	private String query;
	private Map<String, Object> values = new HashMap<String, Object>();
	
	public JpqlFilter(JpqlOperator op, String param) {
		this(op, param, null, null);
	}
	
	public JpqlFilter(JpqlOperator op, String param, Object a) {
		this(op, param, a, null);
	}
	
	public JpqlFilter(JpqlOperator op, String param, Object a, Object b) {
		String p1 = null;
		String p2 = null;
		
		if(a != null) {
			p1 = replaceDots(param) + "_1";
			addValue(p1, a);
		}
		if(b != null) {
			p2 = replaceDots(param) + "_2";
			addValue(p2, b);
		}
		this.query = buildQuery(op, param, p1, p2);
	}
	
	public JpqlFilter(JpqlOperator op, Filter a, Filter b) {
		values.putAll(a.params());
		values.putAll(b.params());
		this.query = buildLogicalQuery(op, a.query(), b.query());
	}
	
	public void addValue(String key, Object val) {
		values.put(key, val);
	}
	
	@Override
	public Map<String, Object> params() {
		return values;
	}
	
	@Override
	public String query() {
		return this.query;
	}
	
	private String buildLogicalQuery(JpqlOperator op, String q1, String q2) {
		String query = "";
		switch (op.getOpType()) {
			case 1: // unary
				query = op.getText() + " ( " + q1 + " )";
				break;
			default: // binary
				query = " ( " + q1 + op.getText() + q2 + " )";
				break;
		}
		
		return query;
	}
	
	private String buildQuery(JpqlOperator op, String param, String p1, String p2) {
		String query = "";
		switch (op.getOpType()) {
			case 1: // unary
				query = param + op.getText();
				break;
			case 3: // ternary
				query = param + op.getText() + " :" + p1 + " AND " + p2;
				break;
			default: // binary
				query = param + op.getText() + " :" + p1;
				break;
		}
		
		return query;
	}
	
	private String replaceDots(String param) {
		return param.replaceAll("\\.", "_");
	}
	
	public enum JpqlOperator {
		EQUALS(" = ", 2),
		NOT_EQUALS(" <> ", 2),
		LESS_THAN(" < ", 2),
		LESS_THAN_EQUALS(" <= ", 2),
		GREATER_THAN(" > ", 2),
		GREATER_THAN_EQUALS(" >= ", 2),
		IS(" IS ", 2),
		IS_NOT(" IS NOT ", 2),
		IN(" IN ", 2),
		NOT_IN(" NOT IN ", 2),
		IS_NULL(" IS NULL", 1),
		IS_NOT_NULL(" IS NOT NULL", 1),
		IS_EMPTY(" IS EMPTY", 1),
		IS_NOT_EMPTY(" IS NOT EMPTY", 1),
		BETWEEN(" BETWEEN ", 3),
		NOT_BETWEEN(" NOT BETWEEN ", 1),
		
		OR(" OR ", 2),
		AND(" AND ", 2),
		NOT(" NOT ", 1),
		
		LIKE(" LIKE ", 2),
		ILIKE(" LIKE ", 2),
		NOT_LIKE(" NOT LIKE ", 2);
		
		String op;
		int opType;
		private JpqlOperator(String op, int opType) {
			this.op = op;
			this.opType = opType;
		}
		
		String getText() {
			return op;
		}
		
		int getOpType() {
			return this.opType;
		}
	}
	
	public static void main(String[] args) {
		System.out.println("a.b.c".replaceAll("\\.", "_"));
	}

}
