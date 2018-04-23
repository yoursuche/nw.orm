package nw.orm.filters;

import java.util.Map;

public interface Filter {
	
	Map<String, Object> params();
	
	String query();

}
