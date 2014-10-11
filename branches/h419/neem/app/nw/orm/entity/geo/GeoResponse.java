package nw.orm.entity.geo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
public class GeoResponse {
	
	private String ip;
	
	private String countryCode;
	
	private String countryName;
	
	private String regionCode;
	
	private String regionName;
	
	private String city;
	
	private String zipCode;
	
	private float longitude;
	
	private float latitude;
	
	private String metroCode;
	
	private String areaCode;

	@XmlElement(name = "Ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@XmlElement(name = "CountryCode")
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@XmlElement(name = "CountryName")
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@XmlElement(name = "RegionCode")
	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	@XmlElement(name = "RegionName")
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@XmlElement(name = "City")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@XmlElement(name = "ZipCode")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@XmlElement(name = "Longitude")
	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	@XmlElement(name = "Latitude")
	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	@XmlElement(name = "MetroCode")
	public String getMetroCode() {
		return metroCode;
	}

	public void setMetroCode(String metroCode) {
		this.metroCode = metroCode;
	}

	@XmlElement(name = "AreaCode")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

}
