package nw.orm.core.jpa;

import javax.persistence.Persistence;

public class NwormJpa extends NwormJpaImpl {

	private String persistentUnit;

	public static NwormJpa getInstance(){
		return getInstance("nwormJpaUnit");
	}

	public static NwormJpa getInstance(String unitName){

		NwormJpa jpa = (NwormJpa) getManager(unitName);
		if(jpa == null){
			jpa = new NwormJpa(unitName);
			putManager(jpa.getPersistentUnit(), jpa);
		}

		return jpa;
	}

	public NwormJpa(String unitName) {
		this.persistentUnit = unitName;
		emFactory = Persistence.createEntityManagerFactory(persistentUnit);
	}

	public String getPersistentUnit() {
		return persistentUnit;
	}

	public void setPersistentUnit(String persistentUnit) {
		this.persistentUnit = persistentUnit;
	}

}
