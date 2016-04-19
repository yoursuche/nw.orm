package nw.orm.eav.values;

import java.util.Date;

import nw.orm.eav.data.EavValue;

// TODO: Auto-generated Javadoc
/**
 * The Class DateValue.
 */
public class DateValue extends EavValue {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1099196834781609499L;
	
	/** The value. */
	private Date value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Date getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Date value) {
		this.value = value;
	}
}
