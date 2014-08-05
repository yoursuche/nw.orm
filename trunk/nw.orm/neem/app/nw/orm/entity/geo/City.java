package nw.orm.entity.geo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import nw.orm.base.IEntity;

@Entity
@Table(name = "CITY")
public class City extends IEntity {
	
	private static final long serialVersionUID = 1895860011896803797L;

	@Column(name = "NAME", nullable = false, length = 1024)
	private String name;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "REGION_FK", nullable = false)
	private Region region;

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
