package nw.orm.eav.values;

import nw.orm.eav.data.EavValue;

// TODO: Auto-generated Javadoc
/**
 * The Class DoubleValue.
 */
public class DoubleValue extends EavValue {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6558689694028819965L;
	
	/** The value. */
	private Double value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Double value) {
		this.value = value;
	}

}
