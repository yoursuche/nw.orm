package nw.orm.core;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

/**
 * A base implementation of an Entity that uses a UUID based primary key string
 * @author Ogwara O. Rowland
 *
 */
@MappedSuperclass
public abstract class REntity extends NwormEntity<String> {

	private static final long serialVersionUID = 8126268423762341105L;

	/**
	 * Primary Key
	 */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "PK", nullable = false, insertable = true, updatable = false)
	private String pk;

	@Override
	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

}
