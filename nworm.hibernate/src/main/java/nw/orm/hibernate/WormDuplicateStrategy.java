package nw.orm.hibernate;

import org.hibernate.event.service.spi.DuplicationStrategy;

public class WormDuplicateStrategy implements DuplicationStrategy {

	@Override
	public boolean areMatch(Object listener, Object original) {
		return listener.getClass().equals( original.getClass() ) &&
                LastUpdateListener.class.isInstance( original );
	}

	@Override
	public Action getAction() {
		return Action.KEEP_ORIGINAL;
	}

}
