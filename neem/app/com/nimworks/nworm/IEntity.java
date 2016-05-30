package com.nimworks.nworm;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * A base implementation of an Entity that uses a sequence based primary key.
 *
 * @author Ogwara O. Rowland
 */
@MappedSuperclass
public abstract class IEntity extends Entity<Long> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5965442215210696967L;

	/** Primary Key. */
	@Id
	@GeneratedValue
	@Column(name = "PK", nullable = false, insertable = true, updatable = false)
	private Long pk;

	/* (non-Javadoc)
	 * @see nw.orm.core.NwormEntity#getPk()
	 */
	@Override
	public Long getPk() {
		return pk;
	}

	/**
	 * Sets the primary Key.
	 *
	 * @param pk the new primary Key
	 */
	public void setPk(Long pk) {
		this.pk = pk;
	}

}
