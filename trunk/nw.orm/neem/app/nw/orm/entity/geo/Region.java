package nw.orm.entity.geo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nw.orm.core.IEntity;

@Entity
@Table(name = "REGION")
public class Region extends IEntity {

	private static final long serialVersionUID = -4620253909428111320L;

	@Column(name = "NAME", nullable = false, length = 1024)
	private String name;
	
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "COUNTRY_FK", nullable = false)
	private Country country;
	
	@OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
	private List<City> cities;

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

	public List<City> getCities() {
		if(this.cities == null){
			this.cities = new ArrayList<City>();
		}
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	
	public void addCity(City city) {
		if(this.cities == null){
			this.cities = new ArrayList<City>();
		}
		this.cities.add(city);
	}

}
