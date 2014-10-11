package nw.orm.eav.values;

import nw.orm.eav.data.EavValue;

public class DoubleValue extends EavValue {
	
	private static final long serialVersionUID = 6558689694028819965L;
	
	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
