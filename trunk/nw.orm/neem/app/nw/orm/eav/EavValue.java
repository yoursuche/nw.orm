package nw.orm.eav;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nw.orm.core.IEntity;
import nw.orm.eav.metadata.EavField;

@MappedSuperclass
public class EavValue extends IEntity{

	private static final long serialVersionUID = 973216881716108289L;
	
	@JoinColumn(name = "FIELD_FK")
	@ManyToOne(optional = false)
	private EavField targetField;
	
	@JoinColumn(name = "OBJECT_FK")
	@ManyToOne(optional = false)
	private EavObject targetObject;

	public EavField getTargetField() {
		return targetField;
	}

	public void setTargetField(EavField targetField) {
		this.targetField = targetField;
	}

	public EavObject getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(EavObject targetObject) {
		this.targetObject = targetObject;
	}
}
