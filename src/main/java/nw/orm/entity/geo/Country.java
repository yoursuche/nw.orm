package nw.orm.entity.geo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nw.orm.core.IEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Country.
 */
@Entity
@Table(name = "COUNTRY", indexes = {
		@Index(columnList= "NAME", unique = true)
})
public class Country extends IEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1518248137801443711L;

	/** The name. */
	@Column(name = "NAME", nullable = false, unique = true)
	private String name;
	
	/** The iso alpha2. */
	@Column(name = "ALPHA_2", nullable = false)
	private String isoAlpha2;
	
	/** The iso alpha3. */
	@Column(name = "ALPHA_3", nullable = false)
	private String isoAlpha3;
	
	/** The phone code. */
	@Column(name = "PHONE_CODE", nullable = false)
	private String phoneCode;
	
	/** The regions. */
	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
	private List<Region> regions;

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

	/**
	 * Gets the iso alpha2.
	 *
	 * @return the iso alpha2
	 */
	public String getIsoAlpha2() {
		return isoAlpha2;
	}

	/**
	 * Sets the iso alpha2.
	 *
	 * @param isoAlpha2 the new iso alpha2
	 */
	public void setIsoAlpha2(String isoAlpha2) {
		this.isoAlpha2 = isoAlpha2;
	}

	/**
	 * Gets the iso alpha3.
	 *
	 * @return the iso alpha3
	 */
	public String getIsoAlpha3() {
		return isoAlpha3;
	}

	/**
	 * Sets the iso alpha3.
	 *
	 * @param isoAlpha3 the new iso alpha3
	 */
	public void setIsoAlpha3(String isoAlpha3) {
		this.isoAlpha3 = isoAlpha3;
	}

	/**
	 * Gets the phone code.
	 *
	 * @return the phone code
	 */
	public String getPhoneCode() {
		return phoneCode;
	}

	/**
	 * Sets the phone code.
	 *
	 * @param phoneCode the new phone code
	 */
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	/**
	 * From string.
	 *
	 * @param line the line
	 * @return the country
	 */
	public static Country fromString(String line) {
		Country c = new Country();
		String[] split = line.split(",");
		if(split.length < 4){
			return null;
		}
		c.setName(split[0]);
		c.setIsoAlpha2(split[1]);
		c.setIsoAlpha3(split[2]);
		c.setPhoneCode(split[3]);
		return c;
	}

	/**
	 * Gets the regions.
	 *
	 * @return the regions
	 */
	public List<Region> getRegions() {
		if(this.regions == null){
			this.regions = new ArrayList<Region>();
		}
		return regions;
	}

	/**
	 * Sets the regions.
	 *
	 * @param regions the new regions
	 */
	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}
	
	/**
	 * Adds the region.
	 *
	 * @param region the region
	 */
	public void addRegion(Region region) {
		if(this.regions == null){
			this.regions = new ArrayList<Region>();
		}
		this.regions.add(region);
	}
	
}
