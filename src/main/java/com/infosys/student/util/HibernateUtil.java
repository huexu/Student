package com.infosys.student.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 */
public class HibernateUtil {
	private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static final ThreadLocal<Transaction> ts = new ThreadLocal<Transaction>();
	private static Configuration configuration = new Configuration();
	private static org.hibernate.SessionFactory sessionFactory;
	private static String configFile = CONFIG_FILE_LOCATION;
	static {
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	private HibernateUtil() {
	}

	public static Transaction beginTransaction() throws HibernateException {
		Transaction tx = ts.get();
		if (tx == null) {
			Session session = getSession();
			tx = session != null ? session.beginTransaction() : null;
			ts.set(tx);
		}
		return tx;
	}

	public static void commitTransaction() throws HibernateException {
		Transaction tx = ts.get();
		ts.set(null);
		if (tx != null)
			tx.commit();
	}

	public static void rollbackTransaction() throws HibernateException {
		Transaction tx = ts.get();
		ts.set(null);
		if (tx != null)
			tx.rollback();
	}

	public static Session getSession() throws HibernateException {
		Session session = threadLocal.get();
		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}
		return session;
	}

	/**
	 * Rebuild hibernate session factory
	 *
	 */
	public static void rebuildSessionFactory() {
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
	 * Close the single hibernate session instance.
	 *
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		Session session = threadLocal.get();
		threadLocal.set(null);
		if (session != null) {
			session.close();
		}
	}

	/**
	 * return session factory
	 *
	 */
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * return session factory
	 *
	 * session factory will be rebuilded in the next call
	 */
	public static void setConfigFile(String configFile) {
		HibernateUtil.configFile = configFile;
		sessionFactory = null;
	}

	/**
	 * return hibernate configuration
	 *
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}
}