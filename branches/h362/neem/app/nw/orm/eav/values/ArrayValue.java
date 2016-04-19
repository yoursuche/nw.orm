package nw.orm.eav.values;

import java.sql.Array;

import nw.orm.eav.data.EavValue;

public class ArrayValue extends EavValue{
	
	private static final long serialVersionUID = 1070233110529382319L;
	
	private Array value;

	public Array getValue() {
		return value;
	}

	public void setValue(Array value) {
		this.value = value;
	}

}
