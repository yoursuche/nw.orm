package nw.orm.eav.values;

import nw.orm.eav.data.EavValue;

// TODO: Auto-generated Javadoc
/**
 * The Class IntValue.
 */
public class IntValue extends EavValue {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2359837806817637385L;
	
	/** The value. */
	private Integer value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

}
