package nw.orm.hibernate;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import nw.orm.core.Entity;

public class UpdateListener {
	
	@PrePersist @PreUpdate
	public void setLastUpdated(Entity entity) {
		System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		entity.setLastUpdated();
	}

}
