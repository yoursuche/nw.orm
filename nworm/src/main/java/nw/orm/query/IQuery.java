package nw.orm.query;

import java.util.List;

public interface IQuery<T> {
	
	T get();
	
	List<T> list();

}
