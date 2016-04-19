package nw.orm.core.jpa;

import javax.persistence.Persistence;

// TODO: Auto-generated Javadoc
/**
 * The Class NwormJpa.
 */
public class NwormJpa extends NwormJpaImpl {

	/** The persistent unit. */
	private String persistentUnit;

	/**
	 * Gets the single instance of NwormJpa.
	 *
	 * @return single instance of NwormJpa
	 */
	public static NwormJpa getInstance(){
		return getInstance("nwormJpaUnit");
	}

	/**
	 * Gets the single instance of NwormJpa.
	 *
	 * @param unitName the unit name
	 * @return single instance of NwormJpa
	 */
	public static NwormJpa getInstance(String unitName){

		NwormJpa jpa = (NwormJpa) getManager(unitName);
		if(jpa == null){
			jpa = new NwormJpa(unitName);
			putManager(jpa.getPersistentUnit(), jpa);
		}

		return jpa;
	}

	/**
	 * Instantiates a new nworm jpa.
	 *
	 * @param unitName the unit name
	 */
	public NwormJpa(String unitName) {
		this.persistentUnit = unitName;
		emFactory = Persistence.createEntityManagerFactory(persistentUnit);
	}

	/**
	 * Gets the persistent unit.
	 *
	 * @return the persistent unit
	 */
	public String getPersistentUnit() {
		return persistentUnit;
	}

	/**
	 * Sets the persistent unit.
	 *
	 * @param persistentUnit the new persistent unit
	 */
	public void setPersistentUnit(String persistentUnit) {
		this.persistentUnit = persistentUnit;
	}

}
