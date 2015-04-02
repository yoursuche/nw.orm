package nw.orm.eav.values;

import java.sql.Array;

import nw.orm.eav.data.EavValue;

// TODO: Auto-generated Javadoc
/**
 * The Class ArrayValue.
 */
public class ArrayValue extends EavValue{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1070233110529382319L;
	
	/** The value. */
	private Array value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Array getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Array value) {
		this.value = value;
	}

}
