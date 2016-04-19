package nw.orm.eav;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.core.REntity;

import org.hibernate.annotations.Index;
/**
 * MetaData defining entities that needs to be captured
 * @author Ogwara O. Rowland
 *
 */
@Entity
@Table(name = "EAV_OBJECT")
public class EavObject extends REntity{
	
	private static final long serialVersionUID = -6243652326429500193L;

	@Index(name = "ON_OR_IX")
	@Column(name = "OBJECT_NAME", unique = true, length = 1024, nullable = false)
	private String objectName; //A simple and unique name for the target entity
	
	@Column(name = "OBJECT_DESC", length = 2048, nullable = true)
	private String objectDescription; // simple description for this entity
	
	public String getObjectDescription() {
		return objectDescription;
	}

	public void setObjectDescription(String objectDescription) {
		this.objectDescription = objectDescription;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

}
