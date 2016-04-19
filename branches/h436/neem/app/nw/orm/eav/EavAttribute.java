package nw.orm.eav;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nw.orm.core.REntity;

// TODO: Auto-generated Javadoc
/**
 * Meta data for attributes that is required to be captured for created entities.
 * All Attributes must belong to a specific entity
 * @author Ogwara O. Rowland
 *
 */
@Entity
@Table(name = "EAV_ATTRIBUTE")
public class EavAttribute extends REntity{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 588467298207482979L;

	/** The field name. */
	@Column(name = "FIELD_NAME", nullable = false, length = 255)
	private String fieldName; // name of attribute
	
	/** The field description. */
	@Column(name = "FIELD_DESC", nullable = true, length = 2048)
	private String fieldDescription; // simple description
	
	/** The parent object. */
	@ManyToOne(optional = false)
	@JoinColumn(name = "EAV_OBJECT_FK")
	private EavObject parentObject; // target entity
	
	/** The constraints. */
	@ManyToOne(optional = true)
	@JoinColumn(name = "EAV_CONSTRAINT_FK")
	private EavConstraint constraints; // defines this attribute (data types, display type etc)
	
	/**
	 * Gets the field description.
	 *
	 * @return the field description
	 */
	public String getFieldDescription() {
		return fieldDescription;
	}

	/**
	 * Sets the field description.
	 *
	 * @param fieldDescription the new field description
	 */
	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
	}

	/**
	 * Gets the field name.
	 *
	 * @return the field name
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Sets the field name.
	 *
	 * @param fieldName the new field name
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Gets the parent object.
	 *
	 * @return the parent object
	 */
	public EavObject getParentObject() {
		return parentObject;
	}

	/**
	 * Sets the parent object.
	 *
	 * @param parentObject the new parent object
	 */
	public void setParentObject(EavObject parentObject) {
		this.parentObject = parentObject;
	}

}
