package com.wow.doge.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.wow.doge.hibernate.HibernateUtil;

public class CommonService {
	
	private static final Logger logger = Logger.getLogger(CommonService.class);

	/**
	 * führt ein SQL aus, das als Rückgabe eine Liste von IDs hat (Integer)
	 * @param sql
	 * @return Liste mit IDs
	 */
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
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
}
