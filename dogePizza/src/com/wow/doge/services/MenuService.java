package com.wow.doge.services;

import org.hibernate.Query;
import org.hibernate.Session;

import com.wow.doge.domain.html.Menu;
import com.wow.doge.hibernate.HibernateUtil;

public class MenuService extends AbstractService<Menu> {

	@Override
	protected Class<Menu> getHibernateClass() {
		return Menu.class;
	}

	public int deleteAll() {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery("DELETE FROM Menu");
			int count = query.executeUpdate();
			session.flush();
			session.getTransaction().commit();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

}
