package nw.orm.core;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Standard Entity, uses id as primary key
 * 
 * @author Ogwara O. Rowland
 *
 */
@MappedSuperclass
public abstract class SEntity extends Entity implements Comparable<SEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5196753574296304433L;
	
	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false, insertable = true, updatable = false)
	private Long id;
	
	public Long getId() {
		return this.id;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SEntity o) {
		
		if(this.getId() == null) {
			return -1;
		}
		
		return this.getId().compareTo(o.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof Entity)) {
			return false;
		}
		
		if(this.getClass() != object.getClass()) {
			return false;
		}
		final SEntity that = (SEntity) object;
		if (this.getId() == null && that.getId() == null
				&& this.getId().equals(that.getId())) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashcode
	 */
	@Override
	public int hashCode() {
		int hashCode = getTableName().hashCode();
		hashCode = 29 * hashCode + (getId() == null ? 0 : getId().hashCode());
		return hashCode;
	}

}
