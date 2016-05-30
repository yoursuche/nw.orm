package nw.orm.eav.values;

import java.sql.Timestamp;

import nw.orm.eav.data.EavValue;

// TODO: Auto-generated Javadoc
/**
 * The Class TimestampValue.
 */
public class TimestampValue extends EavValue {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3466357983533502788L;
	
	/** The value. */
	private Timestamp value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Timestamp getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Timestamp value) {
		this.value = value;
	}
}
