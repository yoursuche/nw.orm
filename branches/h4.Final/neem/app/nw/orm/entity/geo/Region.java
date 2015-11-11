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

// TODO: Auto-generated Javadoc
/**
 * The Class Region.
 */
@Entity
@Table(name = "REGION")
public class Region extends IEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4620253909428111320L;

	/** The name. */
	@Column(name = "NAME", nullable = false, length = 1024)
	private String name;
	
	/** The country. */
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "COUNTRY_FK", nullable = false)
	private Country country;
	
	/** The cities. */
	@OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
	private List<City> cities;

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(Country country) {
		this.country = country;
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

	/**
	 * Gets the cities.
	 *
	 * @return the cities
	 */
	public List<City> getCities() {
		if(this.cities == null){
			this.cities = new ArrayList<City>();
		}
		return cities;
	}

	/**
	 * Sets the cities.
	 *
	 * @param cities the new cities
	 */
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	
	/**
	 * Adds the city.
	 *
	 * @param city the city
	 */
	public void addCity(City city) {
		if(this.cities == null){
			this.cities = new ArrayList<City>();
		}
		this.cities.add(city);
	}

}
