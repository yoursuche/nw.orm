package nw.orm.eav.values;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.eav.data.EavValue;

// TODO: Auto-generated Javadoc
/**
 * The Class StringValue.
 */
@Entity
@Table(name="STR_VAL")
public class StringValue extends EavValue {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3333955139431299514L;
	
	/** The value. */
	@Column(name = "VALUE", nullable = true)
	private String value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
