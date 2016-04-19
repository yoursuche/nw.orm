package nw.orm.entity.geo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nw.orm.core.IEntity;

@Entity
@Table(name = "REGION")
public class Region extends IEntity {

	private static final long serialVersionUID = -4620253909428111320L;

	@Column(name = "NAME", nullable = false, length = 1024)
	private String name;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "COUNTRY_FK", nullable = false)
	private Country country;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
