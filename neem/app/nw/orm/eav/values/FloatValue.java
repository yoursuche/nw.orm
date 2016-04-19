package nw.orm.eav.values;

import nw.orm.eav.data.EavValue;

// TODO: Auto-generated Javadoc
/**
 * The Class FloatValue.
 */
public class FloatValue extends EavValue {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -217560342040100960L;
	
	/** The value. */
	private Float value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Float getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Float value) {
		this.value = value;
	}
}
