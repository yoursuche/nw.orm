package nw.orm.eav.values;

import nw.orm.eav.data.EavValue;

public class FloatValue extends EavValue {

	private static final long serialVersionUID = -217560342040100960L;
	
	private Float value;

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}
}
