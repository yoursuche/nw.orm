package nw.orm.filters;

import nw.orm.filters.JpqlFilter.JpqlOperator;

public class JpqlFilters {
	
	// Comparison Operators
	
	public static Filter eq(final String name, final Object value) {
		return new JpqlFilter(JpqlOperator.EQUALS, name, value);
	}
	
	public static Filter ne(final String name, final Object value) {
		return new JpqlFilter(JpqlOperator.NOT_EQUALS, name, value);
	}
	
	public static Filter lt(final String name, final Object value) {
		return new JpqlFilter(JpqlOperator.LESS_THAN, name, value);
	}
	
	public static Filter lte(final String name, final Object value) {
		return new JpqlFilter(JpqlOperator.LESS_THAN_EQUALS, name, value);
	}
	
	public static Filter gt(final String name, final Object value) {
		return new JpqlFilter(JpqlOperator.GREATER_THAN, name, value);
	}
	
	public static Filter gte(final String name, final Object value) {
		return new JpqlFilter(JpqlOperator.GREATER_THAN_EQUALS, name, value);
	}
	
	public static Filter is(final String name, final Object value) {
		return new JpqlFilter(JpqlOperator.IS, name, value);
	}
	
	public static Filter isNot(final String name, final Object value) {
		return new JpqlFilter(JpqlOperator.IS_NOT, name, value);
	}
	
	public static Filter isNull(final String name) {
		return new JpqlFilter(JpqlOperator.IS_NULL, name);
	}
	
	public static Filter isNotNull(final String name) {
		return new JpqlFilter(JpqlOperator.IS_NOT_NULL, name);
	}

	public static Filter between(final String name, final Object left, final Object right) {
		return new JpqlFilter(JpqlOperator.BETWEEN, name, left, right);
	}
	
	public static Filter like(final String name, final String regex) {
		return new JpqlFilter(JpqlOperator.LIKE, name, regex);
	}
	
	public static Filter ilike(final String name, final String regex) {
		return new JpqlFilter(JpqlOperator.ILIKE, name, regex);
	}
	
	public static Filter notLike(final String name, final String regex) {
		return new JpqlFilter(JpqlOperator.NOT_LIKE, name, regex);
	}

	public static Filter notBetween(final String name, final Object left, final Object right) {
		return new JpqlFilter(JpqlOperator.NOT_BETWEEN, name, left, right);
	}
	
	public static Filter in(final String name, final Object[] values) {
		return new JpqlFilter(JpqlOperator.IN, name, values);
	}
	
	public static Filter notIn(final String name, final Object[] values) {
		return new JpqlFilter(JpqlOperator.NOT_IN, name, values);
	}
	
	public static Filter isEmpty(final String name) {
		return new JpqlFilter(JpqlOperator.IS_EMPTY, name);
	}
	
	public static Filter notEmpty(final String name) {
		return new JpqlFilter(JpqlOperator.IS_NOT_EMPTY, name);
	}
	
	public static Filter or(final Filter a, final Filter b) {
		return new JpqlFilter(JpqlOperator.OR, a, b);
	}
	
	public static Filter and(final Filter a, final Filter b) {
		return new JpqlFilter(JpqlOperator.AND, a, b);
	}
	
	public static Filter not(final Filter a) {
		return new JpqlFilter(JpqlOperator.NOT, a, null);
	}
	
	public static Filter jsonContains(final String name, Object val) {
		return new JpqlFilter(JpqlOperator.JSONB_IN_ARRAY, name, val);
	}

}
