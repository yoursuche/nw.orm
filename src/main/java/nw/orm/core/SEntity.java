package nw.orm.core;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Standard Entity, uses id as primary key
 * 
 * @author Ogwara O. Rowland
 *
 */
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
		int cmp = 0;
		if (this.getId() != null && (this.getId() == o.getId() || this.getId().equals(o.getId()))) {
			cmp = 0;
		}else
			cmp = -1;
		return cmp;
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
		final SEntity that = (SEntity) object;
		if (this.getId() == null || that.getId() == null
				|| !this.getId().equals(that.getId())) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashcode
	 */
	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (getId() == null ? 0 : getId().hashCode());
		return hashCode;
	}

}
