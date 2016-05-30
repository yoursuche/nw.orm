package nw.orm.test.session.core;

import static org.junit.Assert.*;

import java.util.Properties;

import nw.orm.core.session.HibernateSessionFactory;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HibernateConfigurationTest {

	private static HibernateSessionFactory conf;
	private static HibernateSessionFactory pConf;

	private String cfg = "hibernate.cfg.xml";

	@BeforeClass
	public static void init(){
		conf = new HibernateSessionFactory();
		pConf = new HibernateSessionFactory();
	}

	@Test
	public void atestInit() {
		conf.init(null, cfg);
		assertNotNull(conf.getSessionFactory());

		Properties p = new Properties();
		p.put("config.name", "xtra");
		p.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/nw_test");
		pConf.init(p, cfg);
		assertNotNull(pConf.getSessionFactory());
		assertNotEquals(conf.getSessionFactory(), pConf.getSessionFactory());
	}

	@Test
	public void btestGetActiveConfiguration() {
		System.out.println(conf.getActiveConfiguration());
		String cm = conf.getActiveConfiguration().getProperty("hibernate.connection.username");
		String im = pConf.getActiveConfiguration().getProperty("hibernate.connection.url");
		assertTrue("postgres".equals(cm));
		assertTrue("jdbc:postgresql://localhost:5432/nw_test".equals(im));
	}

//	@Test
//	public void ctestRebuildConfiguration() {
//		conf.getActiveConfiguration().addAnnotatedClass(Item.class);
//		conf.rebuildConfiguration();
//
//		assertNotNull(conf.getActiveConfiguration());
//	}

	@Test
	public void dtestCloseFactory() {
		String cfg = conf.closeFactory();
		System.out.println(cfg);
		assertTrue(conf.getSessionFactory().isClosed());

		String cfg2 = pConf.closeFactory();
		System.out.println(cfg2);
		assertTrue(pConf.getSessionFactory().isClosed());
	}

}
