package nw.orm.core.query;

import java.util.Map;
import java.util.Set;

public class QueryParameter {

	private String name;
	private Object value;

	public QueryParameter(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

	public QueryParameter(){

	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public static QueryParameter create(String name, Object value){
		return new QueryParameter(name, value);
	}
	
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
