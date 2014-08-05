package nw.orm.entity.geo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import nw.orm.base.Entity;

@javax.persistence.Entity
@Table(name = "GEO_IP")
public class GeoIp extends Entity<String>{
	
	private static final long serialVersionUID = 77385623074926543L;

	@Column(name = "PK", nullable = false, insertable = true, updatable = false, length = 15)
	@Id
	private String pk;
	
	@Column(name = "COUNTRY_CODE", nullable = false)
	private String country;
	
	@Column(name = "REGION", nullable = true)
	private String region;
	
	@Column(name = "CITY", nullable = true)
	private String city;

	@Override
	public String getPk() {
		return pk;
	}
	
	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public static GeoIp fromResponse(GeoResponse response){
		GeoIp ip = new GeoIp();
		ip.setCountry(response.getCountryCode());
		ip.setRegion(response.getRegionCode());
		ip.setCity(response.getCity());
		ip.setPk(response.getIp());
		return ip;
	}

}
