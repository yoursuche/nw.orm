package nw.orm.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NwormTransaction {

	/**
	 * Configuration file name
	 * @return the name of the config relative to the classpath
	 */
	String config() default "hibernate.cfg.xml";

}
