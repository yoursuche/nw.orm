package nw.orm.core;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * A base implementation of an Entity that uses a number base on Hibernate Sequence as primary key
 * @author Ogwara O. Rowland
 *
 */
@MappedSuperclass
public abstract class IEntity extends NwormEntity<Long> {

	private static final long serialVersionUID = -5965442215210696967L;

	/**
	 * Primary Key
	 */
	@Id
	@GeneratedValue
	@Column(name = "PK", nullable = false, insertable = true, updatable = false)
	private Long pk;

	@Override
	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

}
