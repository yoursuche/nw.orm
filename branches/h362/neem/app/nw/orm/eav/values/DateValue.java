package nw.orm.eav.values;

import java.util.Date;

import nw.orm.eav.data.EavValue;

public class DateValue extends EavValue {
	
	private static final long serialVersionUID = 1099196834781609499L;
	
	private Date value;

	public Date getValue() {
		return value;
	}

	public void setValue(Date value) {
		this.value = value;
	}
}
