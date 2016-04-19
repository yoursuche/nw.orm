package nw.orm.eav.values;

import nw.orm.eav.data.EavValue;

public class BooleanValue extends EavValue {

	private static final long serialVersionUID = -7316677900193250848L;
	
	private Boolean value;

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

}
