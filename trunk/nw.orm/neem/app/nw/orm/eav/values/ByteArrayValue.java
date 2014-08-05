package nw.orm.eav.values;

import nw.orm.eav.data.EavValue;

public class ByteArrayValue extends EavValue {

	private static final long serialVersionUID = 5379887179638806200L;
	
	private byte[] value;

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}
}
