/*
 * Property of Neemworks Nigeria 
 * Copyright 2013 - 2015, all rights reserved
 */
package nw.orm.core.query;

import java.util.Map;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryParameter.
 */
public class QueryParameter {

	/** The name. */
	private String name;
	
	/** The value. */
	private Object value;

	/**
	 * Instantiates a new query parameter.
	 *
	 * @param name the name
	 * @param value the value
	 */
	public QueryParameter(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

	/**
	 * Instantiates a new query parameter.
	 */
	public QueryParameter(){

	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Creates the.
	 *
	 * @param name the name
	 * @param value the value
	 * @return the query parameter
	 */
	public static QueryParameter create(String name, Object value){
		return new QueryParameter(name, value);
	}
	
	/**
	 * From map.
	 *
	 * @param params the params
	 * @return the query parameter[]
	 */
	public static QueryParameter[] fromMap(Map<String, Object> params){
		Set<String> keySet = params.keySet();
		QueryParameter[] arr = new QueryParameter[keySet.size()];
		int r = 0;
		for(String key: keySet){
			arr[r] = new QueryParameter(key, params.get(key));
			r +=1;
		}
		return arr;
	}

}
