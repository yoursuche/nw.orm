package nw.orm.eav;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import nw.orm.base.REntity;
import nw.orm.eav.enums.FieldDataType;
import nw.orm.eav.enums.GuiType;

/**
 * Meta data for constraints on attributes. Used to specified 
 * all validation constraints on the attributes
 * @author Ogwara O. Rowland
 *
 */
@Entity
@Table(name="EAV_CONSTRAINT")
public class EavConstraint extends REntity{
	
	private static final long serialVersionUID = -607922917149941409L;

	@Column(name = "IS_REQUIRED", nullable = false)
	private boolean required = false;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DATA_TYPE")
	private FieldDataType dataType; // data type
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DISPLAY_TYPE")
	private GuiType displayType; // display type
	
	/* Begin String Validation */
	
	@Column(name = "DEFAULT_STRING", nullable = true)
	private String defaultString;
	
	@Column(name = "MIN_LENGTH", nullable = true)
	private Integer minLength;
	
	@Column(name = "MAX_LENGTH", nullable = true)
	private Integer maxLength;
	
	@Column(name = "FORMAT", nullable = true)
	private String format; // regex
	
	/* End String Validation */
	
	// Number Validation
	@Column(name = "MAX_VALUE", nullable = true)
	private Long maxValue;
	
	@Column(name = "MIN_VALUE", nullable = true)
	private Long minValue;
	
	// Data Validation
	@Column(name = "MIN_DATE", nullable = true)
	private Date minDate;
	
	@Column(name = "MAX_DATE", nullable = true)
	private Date maxDate;
	
	public boolean isRequired() {
		return required;
	}
	
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	public int getMinLength() {
		return minLength;
	}
	
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	
	public Integer getMaxLength() {
		return maxLength;
	}
	
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String matchRegex) {
		this.format = matchRegex;
	}
	
	public Long getMaxValue() {
		return maxValue;
	}
	
	public void setMaxValue(Long maxValue) {
		this.maxValue = maxValue;
	}
	
	public Long getMinValue() {
		return minValue;
	}
	
	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}
	
	public Date getMinDate() {
		return minDate;
	}
	
	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}
	
	public Date getMaxDate() {
		return maxDate;
	}
	
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	
	public String getDefaultString() {
		return defaultString;
	}
	
	public void setDefaultString(String defaultString) {
		this.defaultString = defaultString;
	}
	
	public FieldDataType getDataType() {
		return dataType;
	}
	
	public void setDataType(FieldDataType dataType) {
		this.dataType = dataType;
	}
	
	public GuiType getDisplayType() {
		return displayType;
	}
	
	public void setDisplayType(GuiType displayType) {
		this.displayType = displayType;
	}

}
