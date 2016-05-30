package nw.orm.eav;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.core.REntity;

// TODO: Auto-generated Javadoc
/**
 * MetaData defining entities that needs to be captured.
 *
 * @author Ogwara O. Rowland
 */
@Entity
@Table(name = "EAV_OBJECT")
public class EavObject extends REntity{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6243652326429500193L;

	/** The object name. */
	@Column(name = "OBJECT_NAME", unique = true, length = 1024, nullable = false)
	private String objectName; //A simple and unique name for the target entity
	
	/** The object description. */
	@Column(name = "OBJECT_DESC", length = 2048, nullable = true)
	private String objectDescription; // simple description for this entity
	
	/**
	 * Gets the object description.
	 *
	 * @return the object description
	 */
	public String getObjectDescription() {
		return objectDescription;
	}

	/**
	 * Sets the object description.
	 *
	 * @param objectDescription the new object description
	 */
	public void setObjectDescription(String objectDescription) {
		this.objectDescription = objectDescription;
	}

	/**
	 * Gets the object name.
	 *
	 * @return the object name
	 */
	public String getObjectName() {
		return objectName;
	}

	/**
	 * Sets the object name.
	 *
	 * @param objectName the new object name
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

}
