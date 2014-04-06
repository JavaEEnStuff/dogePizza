package com.wow.doge.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.wow.doge.hibernate.HibernateUtil;

public class CommonService {
	
	private static final Logger logger = Logger.getLogger(CommonService.class);

	public List<Integer> getIds(String sql) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			List<Integer> resultList = (ArrayList<Integer>)session.createQuery(sql).list();
			session.getTransaction().commit();
			return resultList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// TODO: errorHandling
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
}
