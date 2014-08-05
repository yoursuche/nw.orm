package nw.orm.entity.geo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import nw.orm.base.IEntity;

@Entity
@Table(name = "COUNTRY")
public class Country extends IEntity {
	
	private static final long serialVersionUID = -1518248137801443711L;

	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "ALPHA_2", nullable = false)
	private String isoAlpha2;
	
	@Column(name = "ALPHA_3", nullable = false)
	private String isoAlpha3;
	
	@Column(name = "PHONE_CODE", nullable = false)
	private String phoneCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsoAlpha2() {
		return isoAlpha2;
	}

	public void setIsoAlpha2(String isoAlpha2) {
		this.isoAlpha2 = isoAlpha2;
	}

	public String getIsoAlpha3() {
		return isoAlpha3;
	}

	public void setIsoAlpha3(String isoAlpha3) {
		this.isoAlpha3 = isoAlpha3;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

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
	
}
