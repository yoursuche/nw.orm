package nw.orm.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class IEntity extends Entity<Long> {

	private static final long serialVersionUID = -5965442215210696967L;

	/**
	 * Primary Key
	 */
	@Id
	@GeneratedValue
	@Column(name = "PK", nullable = false, insertable = true, updatable = false)
	private Long pk;

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

}
