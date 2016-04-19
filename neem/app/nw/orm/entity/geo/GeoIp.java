package nw.orm.entity.geo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import nw.orm.core.NwormEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class GeoIp.
 */
@javax.persistence.Entity
@Table(name = "GEO_IP")
public class GeoIp extends NwormEntity<String>{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 77385623074926543L;

	/** The pk. */
	@Column(name = "PK", nullable = false, insertable = true, updatable = false, length = 15)
	@Id
	private String pk;
	
	/** The country. */
	@Column(name = "COUNTRY_CODE", nullable = false)
	private String country;
	
	/** The region. */
	@Column(name = "REGION", nullable = true)
	private String region;
	
	/** The city. */
	@Column(name = "CITY", nullable = true)
	private String city;

	/* (non-Javadoc)
	 * @see nw.orm.core.NwormEntity#getPk()
	 */
	@Override
	public String getPk() {
		return pk;
	}
	
	/**
	 * Sets the pk.
	 *
	 * @param pk the new pk
	 */
	public void setPk(String pk) {
		this.pk = pk;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Sets the region.
	 *
	 * @param region the new region
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * From response.
	 *
	 * @param response the response
	 * @return the geo ip
	 */
	public static GeoIp fromResponse(GeoResponse response){
		GeoIp ip = new GeoIp();
		ip.setCountry(response.getCountryCode());
		ip.setRegion(response.getRegionCode());
		ip.setCity(response.getCity());
		ip.setPk(response.getIp());
		return ip;
	}

}
