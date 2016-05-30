package nw.orm.eav.data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nw.orm.core.REntity;
import nw.orm.eav.EavAttribute;

// TODO: Auto-generated Javadoc
/**
 * The Class EavValue.
 */
@MappedSuperclass
public class EavValue extends REntity{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 973216881716108289L;
	
	/** The target field. */
	@JoinColumn(name = "TARGET_FIELD_FK")
	@ManyToOne(optional = false)
	private EavAttribute targetField;
	
	/** The target object. */
	@JoinColumn(name = "TARGET_OBJ_FK")
	@ManyToOne(optional = false)
	private EavObjectData targetObject;

	/**
	 * Gets the target field.
	 *
	 * @return the target field
	 */
	public EavAttribute getTargetField() {
		return targetField;
	}

	/**
	 * Sets the target field.
	 *
	 * @param targetField the new target field
	 */
	public void setTargetField(EavAttribute targetField) {
		this.targetField = targetField;
	}

	/**
	 * Gets the target object.
	 *
	 * @return the target object
	 */
	public EavObjectData getTargetObject() {
		return targetObject;
	}

	/**
	 * Sets the target object.
	 *
	 * @param targetObject the new target object
	 */
	public void setTargetObject(EavObjectData targetObject) {
		this.targetObject = targetObject;
	}
}
