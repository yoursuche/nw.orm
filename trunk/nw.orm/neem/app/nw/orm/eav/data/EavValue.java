package nw.orm.eav.data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nw.orm.base.REntity;
import nw.orm.eav.EavAttribute;

@MappedSuperclass
public class EavValue extends REntity{

	private static final long serialVersionUID = 973216881716108289L;
	
	@JoinColumn(name = "TARGET_FIELD_FK")
	@ManyToOne(optional = false)
	private EavAttribute targetField;
	
	@JoinColumn(name = "TARGET_OBJ_FK")
	@ManyToOne(optional = false)
	private EavObjectData targetObject;

	public EavAttribute getTargetField() {
		return targetField;
	}

	public void setTargetField(EavAttribute targetField) {
		this.targetField = targetField;
	}

	public EavObjectData getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(EavObjectData targetObject) {
		this.targetObject = targetObject;
	}
}
