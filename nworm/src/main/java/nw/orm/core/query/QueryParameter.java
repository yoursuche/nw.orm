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

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Instantiates a new query parameter.
	 *
	 * @param name the name of the field
	 * @param value the field value
	 * @param title custom title for the field
	 */
	public QueryParameter(String name, Object value, String title) {
		super();
		this.name = name;
		this.value = value;
		this.title = title;
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
		return new QueryParameter(name, value, name);
	}
	
	public static QueryParameter create(String name, String title, Object value){
		return new QueryParameter(name, value, title);
	}
	
	public static QueryParameter param(String name, Object value){
		return new QueryParameter(name, value, name);
	}
	
	public static QueryParameter param(String name, String title, Object value){
		return new QueryParameter(name, value, title);
	}
	
	public String toSqlExpression() {
		String exp = "";
		if(this.getTitle() != null)
			exp += this.getName() + " = :" + this.getTitle();
		else {
			exp += this.getName() + " = :" + this.getName();
		}
		
		return exp;
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
			arr[r] = new QueryParameter(key, params.get(key), key);
			r +=1;
		}
		return arr;
	}

}
