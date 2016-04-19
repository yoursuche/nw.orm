package nw.orm.eav;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import nw.orm.core.REntity;
import nw.orm.eav.enums.FieldDataType;
import nw.orm.eav.enums.GuiType;

// TODO: Auto-generated Javadoc
/**
 * Meta data for constraints on attributes. Used to specified 
 * all validation constraints on the attributes
 * @author Ogwara O. Rowland
 *
 */
@Entity
@Table(name="EAV_CONSTRAINT")
public class EavConstraint extends REntity{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -607922917149941409L;

	/** The required. */
	@Column(name = "IS_REQUIRED", nullable = false)
	private boolean required = false;
	
	/** The data type. */
	@Enumerated(EnumType.STRING)
	@Column(name = "DATA_TYPE")
	private FieldDataType dataType; // data type
	
	/** The display type. */
	@Enumerated(EnumType.STRING)
	@Column(name = "DISPLAY_TYPE")
	private GuiType displayType; // display type
	
	/* Begin String Validation */
	
	/** The default string. */
	@Column(name = "DEFAULT_STRING", nullable = true)
	private String defaultString;
	
	/** The min length. */
	@Column(name = "MIN_LENGTH", nullable = true)
	private Integer minLength;
	
	/** The max length. */
	@Column(name = "MAX_LENGTH", nullable = true)
	private Integer maxLength;
	
	/** The format. */
	@Column(name = "FORMAT", nullable = true)
	private String format; // regex
	
	/* End String Validation */
	
	// Number Validation
	/** The max value. */
	@Column(name = "MAX_VALUE", nullable = true)
	private Long maxValue;
	
	/** The min value. */
	@Column(name = "MIN_VALUE", nullable = true)
	private Long minValue;
	
	// Data Validation
	/** The min date. */
	@Column(name = "MIN_DATE", nullable = true)
	private Date minDate;
	
	/** The max date. */
	@Column(name = "MAX_DATE", nullable = true)
	private Date maxDate;
	
	/**
	 * Checks if is required.
	 *
	 * @return true, if is required
	 */
	public boolean isRequired() {
		return required;
	}
	
	/**
	 * Sets the required.
	 *
	 * @param required the new required
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	/**
	 * Gets the min length.
	 *
	 * @return the min length
	 */
	public int getMinLength() {
		return minLength;
	}
	
	/**
	 * Sets the min length.
	 *
	 * @param minLength the new min length
	 */
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	
	/**
	 * Gets the max length.
	 *
	 * @return the max length
	 */
	public Integer getMaxLength() {
		return maxLength;
	}
	
	/**
	 * Sets the max length.
	 *
	 * @param maxLength the new max length
	 */
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	
	/**
	 * Gets the format.
	 *
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	
	/**
	 * Sets the format.
	 *
	 * @param matchRegex the new format
	 */
	public void setFormat(String matchRegex) {
		this.format = matchRegex;
	}
	
	/**
	 * Gets the max value.
	 *
	 * @return the max value
	 */
	public Long getMaxValue() {
		return maxValue;
	}
	
	/**
	 * Sets the max value.
	 *
	 * @param maxValue the new max value
	 */
	public void setMaxValue(Long maxValue) {
		this.maxValue = maxValue;
	}
	
	/**
	 * Gets the min value.
	 *
	 * @return the min value
	 */
	public Long getMinValue() {
		return minValue;
	}
	
	/**
	 * Sets the min value.
	 *
	 * @param minValue the new min value
	 */
	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}
	
	/**
	 * Gets the min date.
	 *
	 * @return the min date
	 */
	public Date getMinDate() {
		return minDate;
	}
	
	/**
	 * Sets the min date.
	 *
	 * @param minDate the new min date
	 */
	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}
	
	/**
	 * Gets the max date.
	 *
	 * @return the max date
	 */
	public Date getMaxDate() {
		return maxDate;
	}
	
	/**
	 * Sets the max date.
	 *
	 * @param maxDate the new max date
	 */
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	
	/**
	 * Gets the default string.
	 *
	 * @return the default string
	 */
	public String getDefaultString() {
		return defaultString;
	}
	
	/**
	 * Sets the default string.
	 *
	 * @param defaultString the new default string
	 */
	public void setDefaultString(String defaultString) {
		this.defaultString = defaultString;
	}
	
	/**
	 * Gets the data type.
	 *
	 * @return the data type
	 */
	public FieldDataType getDataType() {
		return dataType;
	}
	
	/**
	 * Sets the data type.
	 *
	 * @param dataType the new data type
	 */
	public void setDataType(FieldDataType dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * Gets the display type.
	 *
	 * @return the display type
	 */
	public GuiType getDisplayType() {
		return displayType;
	}
	
	/**
	 * Sets the display type.
	 *
	 * @param displayType the new display type
	 */
	public void setDisplayType(GuiType displayType) {
		this.displayType = displayType;
	}

}
