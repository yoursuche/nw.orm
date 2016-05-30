package nw.orm.eav.values;

import nw.orm.eav.data.EavValue;

// TODO: Auto-generated Javadoc
/**
 * The Class ByteArrayValue.
 */
public class ByteArrayValue extends EavValue {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5379887179638806200L;
	
	/** The value. */
	private byte[] value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public byte[] getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(byte[] value) {
		this.value = value;
	}
}
