package nw.orm.eav.metadata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nw.orm.core.IEntity;

@Entity
@Table(name = "EAV_FIELD")
public class EavField extends IEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4727476783812113205L;

	@Column(name = "NAME", nullable = false, length = 128)
	private String name;
	
	@Column(name = "DESCRIPTION", nullable = true, length = 256)
	private String description;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ENTITY_FK", nullable = false)
	private EavEntity entity;
	
	@Column(name = "EAV_VERSION", nullable = false)
	private int eavVersion;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EavEntity getEntity() {
		return entity;
	}

	public void setEntity(EavEntity entity) {
		this.entity = entity;
	}

	public int getEavVersion() {
		return eavVersion;
	}

	public void setEavVersion(int eavVersion) {
		this.eavVersion = eavVersion;
	}

}
