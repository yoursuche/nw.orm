package nw.orm.entity.geo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nw.orm.core.IEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class City.
 */
@Entity
@Table(name = "CITY")
public class City extends IEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1895860011896803797L;

	/** The name. */
	@Column(name = "NAME", nullable = false, length = 1024)
	private String name;
	
	/** The region. */
	@ManyToOne(optional = false)
	@JoinColumn(name = "REGION_FK", nullable = false)
	private Region region;

	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * Sets the region.
	 *
	 * @param region the new region
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
