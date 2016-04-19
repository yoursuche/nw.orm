package nw.orm.eav.values;

import java.sql.Timestamp;

import nw.orm.eav.data.EavValue;

public class TimestampValue extends EavValue {
	
	private static final long serialVersionUID = -3466357983533502788L;
	
	private Timestamp value;

	public Timestamp getValue() {
		return value;
	}

	public void setValue(Timestamp value) {
		this.value = value;
	}
}
