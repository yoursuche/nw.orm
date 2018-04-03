package nw.orm.hibernate;

import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.PersistEvent;
import org.hibernate.event.spi.PersistEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import nw.orm.core.Entity;

public class LastUpdateListener implements PreUpdateEventListener, PersistEventListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4070672322787287710L;

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		Entity entity = (Entity) event.getEntity();
		entity.setLastUpdated();
		return false;
	}



	@Override
	public void onPersist(PersistEvent event) throws HibernateException {
		Entity entity = (Entity) event.getObject();
		entity.setLastUpdated();
	}

	@Override
	public void onPersist(PersistEvent event, Map createdAlready) throws HibernateException {
		Entity entity = (Entity) event.getObject();
		entity.setLastUpdated();
		
	}

}
