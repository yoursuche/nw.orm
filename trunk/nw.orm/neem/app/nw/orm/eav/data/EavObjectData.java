package nw.orm.eav.data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nw.orm.base.REntity;
import nw.orm.eav.EavObject;

/**
 * Actual captured entity data
 * @author Ogwara O. Rowland
 *
 */
@Entity
@Table(name = "OBJECT_DATA")
public class EavObjectData extends REntity{
	
	private static final long serialVersionUID = 955105470876195986L;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "OBJ_REF_FK")
	private EavObject objectReference;

	public EavObject getObjectReference() {
		return objectReference;
	}

	public void setObjectReference(EavObject objectReference) {
		this.objectReference = objectReference;
	}

}
