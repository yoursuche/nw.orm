package nw.orm.eav.values;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.eav.data.EavValue;

// TODO: Auto-generated Javadoc
/**
 * The Class LongValue.
 */
@Entity
@Table(name="LONG_VAL")
public class LongValue extends EavValue{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5853035053034366287L;
	
	/** The value. */
	@Column(name = "VALUE", nullable = true)
	private Long value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Long getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Long value) {
		this.value = value;
	}
}
