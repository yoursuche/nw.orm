package nw.orm.core;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nw.orm.core.Entity;

public class EntityUpdateListener {
	
	/**
	 * 
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public EntityUpdateListener() {
		System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLl");
	}
	
	/**
	 * 
	 */
	@PrePersist @PreUpdate
	public void setLastUpdated(Entity entity) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Pre Update: " + entity);
		entity.setLastUpdated();
	}

}
