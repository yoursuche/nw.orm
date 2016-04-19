package nw.orm.eav;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nw.orm.core.REntity;

/**
 * Meta data for attributes that is required to be captured for created entities.
 * All Attributes must belong to a specific entity
 * @author Ogwara O. Rowland
 *
 */
@Entity
@Table(name = "EAV_ATTRIBUTE")
public class EavAttribute extends REntity{
	
	private static final long serialVersionUID = 588467298207482979L;

	@Column(name = "FIELD_NAME", nullable = false, length = 255)
	private String fieldName; // name of attribute
	
	@Column(name = "FIELD_DESC", nullable = true, length = 2048)
	private String fieldDescription; // simple description
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "EAV_OBJECT_FK")
	private EavObject parentObject; // target entity
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "EAV_CONSTRAINT_FK")
	private EavConstraint constraints; // defines this attribute (data types, display type etc)
	
	public String getFieldDescription() {
		return fieldDescription;
	}

	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public EavObject getParentObject() {
		return parentObject;
	}

	public void setParentObject(EavObject parentObject) {
		this.parentObject = parentObject;
	}

}
