package com.wow.doge.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(HibernateUtil.class);

	static {
		try {
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
			logger.info("SessionFactory erzeugt!");
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void closeSession(Session session) {
		try {
			if ((session != null) && (session.isOpen())) {
				session.close();
			}
		} catch (HibernateException e) {
			logger.warn("Closing session not possible", e);
		}
	}
}