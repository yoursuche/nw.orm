package nw.orm.eav.data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nw.orm.core.REntity;
import nw.orm.eav.EavObject;

// TODO: Auto-generated Javadoc
/**
 * Actual captured entity data.
 *
 * @author Ogwara O. Rowland
 */
@Entity
@Table(name = "OBJECT_DATA")
public class EavObjectData extends REntity{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 955105470876195986L;
	
	/** The object reference. */
	@ManyToOne(optional = false)
	@JoinColumn(name = "OBJ_REF_FK")
	private EavObject objectReference;

	/**
	 * Gets the object reference.
	 *
	 * @return the object reference
	 */
	public EavObject getObjectReference() {
		return objectReference;
	}

	/**
	 * Sets the object reference.
	 *
	 * @param objectReference the new object reference
	 */
	public void setObjectReference(EavObject objectReference) {
		this.objectReference = objectReference;
	}

}
