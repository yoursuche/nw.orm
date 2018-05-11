package nw.orm.hibernate;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class WormHibernateIntegrator implements Integrator {

	@Override
	public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		final EventListenerRegistry registry =
	            serviceRegistry.getService( EventListenerRegistry.class );
		
		LastUpdateListener listener = new LastUpdateListener();
		registry.addDuplicationStrategy(new WormDuplicateStrategy());
		registry.prependListeners(EventType.PERSIST, listener);
		registry.prependListeners(EventType.PRE_UPDATE, listener);
		
	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
		// TODO Auto-generated method stub
		
	}

}
