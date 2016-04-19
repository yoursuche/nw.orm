package nw.orm.eav.values;

import nw.orm.eav.data.EavValue;

// TODO: Auto-generated Javadoc
/**
 * The Class BooleanValue.
 */
public class BooleanValue extends EavValue {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7316677900193250848L;
	
	/** The value. */
	private Boolean value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Boolean getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Boolean value) {
		this.value = value;
	}

}
