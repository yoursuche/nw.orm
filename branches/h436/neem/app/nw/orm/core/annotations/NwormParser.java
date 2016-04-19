package nw.orm.core.annotations;

import java.lang.reflect.Method;

import org.hibernate.Session;

import nw.commons.NeemClazz;
import nw.orm.core.service.Nworm;

/**
 * Parser for annotations used by the framework
 * @author Rowland
 *
 */
public class NwormParser extends NeemClazz{

	public void parseMethod(Class<?> clazz) throws Exception{
		Method[] methods = clazz.getMethods();

		for (Method method : methods) {
			if(method.isAnnotationPresent(NwormTransaction.class)){
				NwormTransaction nwt = method.getAnnotation(NwormTransaction.class);
				Nworm dbService = Nworm.getInstance(nwt.configFile());

				Session sxn = dbService.getSessionService().getManagedSession();
				
			}
		}

	}

}
