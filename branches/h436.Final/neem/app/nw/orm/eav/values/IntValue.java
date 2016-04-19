package nw.orm.eav.values;

import nw.orm.eav.data.EavValue;

public class IntValue extends EavValue {
	
	private static final long serialVersionUID = 2359837806817637385L;
	
	private Integer value;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
