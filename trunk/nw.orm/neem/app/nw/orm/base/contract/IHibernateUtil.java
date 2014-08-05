package nw.orm.base.contract;

import java.util.Properties;
import org.hibernate.SessionFactory;

public abstract interface IHibernateUtil {
	
	public abstract void init(String paramString);

	public abstract void init(Properties paramProperties, String paramString);

	public abstract void init(Properties paramProperties, String paramString1,
			String paramString2);

	public abstract SessionFactory buildSessionFactory();

	public abstract String closeFactory();

	public abstract SessionFactory getSessionFactory();
}