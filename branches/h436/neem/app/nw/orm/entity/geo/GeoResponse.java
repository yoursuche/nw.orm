package nw.orm.entity.geo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class GeoResponse.
 */
@XmlRootElement(name = "Response")
public class GeoResponse {
	
	/** The ip. */
	private String ip;
	
	/** The country code. */
	private String countryCode;
	
	/** The country name. */
	private String countryName;
	
	/** The region code. */
	private String regionCode;
	
	/** The region name. */
	private String regionName;
	
	/** The city. */
	private String city;
	
	/** The zip code. */
	private String zipCode;
	
	/** The longitude. */
	private float longitude;
	
	/** The latitude. */
	private float latitude;
	
	/** The metro code. */
	private String metroCode;
	
	/** The area code. */
	private String areaCode;

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	@XmlElement(name = "Ip")
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip.
	 *
	 * @param ip the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Gets the country code.
	 *
	 * @return the country code
	 */
	@XmlElement(name = "CountryCode")
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * Sets the country code.
	 *
	 * @param countryCode the new country code
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * Gets the country name.
	 *
	 * @return the country name
	 */
	@XmlElement(name = "CountryName")
	public String getCountryName() {
		return countryName;
	}

	/**
	 * Sets the country name.
	 *
	 * @param countryName the new country name
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * Gets the region code.
	 *
	 * @return the region code
	 */
	@XmlElement(name = "RegionCode")
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * Sets the region code.
	 *
	 * @param regionCode the new region code
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * Gets the region name.
	 *
	 * @return the region name
	 */
	@XmlElement(name = "RegionName")
	public String getRegionName() {
		return regionName;
	}

	/**
	 * Sets the region name.
	 *
	 * @param regionName the new region name
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	@XmlElement(name = "City")
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
	 * Gets the zip code.
	 *
	 * @return the zip code
	 */
	@XmlElement(name = "ZipCode")
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Sets the zip code.
	 *
	 * @param zipCode the new zip code
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	@XmlElement(name = "Longitude")
	public float getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude the new longitude
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	@XmlElement(name = "Latitude")
	public float getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the metro code.
	 *
	 * @return the metro code
	 */
	@XmlElement(name = "MetroCode")
	public String getMetroCode() {
		return metroCode;
	}

	/**
	 * Sets the metro code.
	 *
	 * @param metroCode the new metro code
	 */
	public void setMetroCode(String metroCode) {
		this.metroCode = metroCode;
	}

	/**
	 * Gets the area code.
	 *
	 * @return the area code
	 */
	@XmlElement(name = "AreaCode")
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * Sets the area code.
	 *
	 * @param areaCode the new area code
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

}
