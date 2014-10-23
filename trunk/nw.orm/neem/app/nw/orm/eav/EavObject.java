package nw.orm.eav;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nw.orm.core.IEntity;
import nw.orm.eav.metadata.EavEntity;

/**
 * Actual captured entity data
 * @author Ogwara O. Rowland
 *
 */
@Entity
@Table(name = "EAV_OBJECT")
public class EavObject extends IEntity{
	
	private static final long serialVersionUID = 955105470876195986L;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ENTITY_FK")
	private EavEntity entity;

	public EavEntity getObjectReference() {
		return entity;
	}

	public void setObjectReference(EavEntity entity) {
		this.entity = entity;
	}

}
