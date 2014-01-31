package com.wow.doge.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import com.wow.doge.hibernate.HibernateUtil;

public abstract class AbstractService<T> {
	
	protected abstract Class<T> getHibernateClass();

	public void delete(T object) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			if (object != null) {
				session.delete(object);
				session.flush();
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Sucht eine Sehenswürdigkeit anhand ihres Primärschlüssels aus der
	 * Datenbank.
	 * 
	 * @param id
	 *            Der Primärschlüssel der zu suchenden Sehenswürdigkeit.
	 * @return Die gefundene Sehenswürdigkeit oder null.
	 */
	public T get(int id) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			T sight = (T) session.get(getHibernateClass(), id);
			session.getTransaction().commit();
			return sight;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Gibt alle Sehenswürdigkeiten als Liste aus der Datenbank zurück.
	 * 
	 * @return Alle Sehenswürdigkeiten.
	 */
	public List<T> getList() {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			List<T> resultList = (ArrayList<T>) session.createCriteria(getHibernateClass()).list();
			session.getTransaction().commit();
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Sucht alle Sehenswürdigkeiten aus der Datenbank, deren Name dem
	 * Suchstring entspricht.
	 * 
	 * @param searchString
	 *            Der Suchstring.
	 * @return Alle passenden Sehenswürdigkeiten.
	 */
	public List<T> getList(Criterion...criterions) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(getHibernateClass());
			for(Criterion nextCriterion : criterions){
				criteria.add(nextCriterion);
			}
			List<T>resultList = (ArrayList<T>) criteria.list();
			session.getTransaction().commit();
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Speichert eine Sehenswürdigkeit in der Datenbank. Diese Methode erkennt
	 * selbstständig, ob ein Objekt neu in die Datenbank eingefügt oder dort
	 * aktualisiert werden muss.
	 * 
	 * @param sight
	 *            Die zu speichernde Sehenswürdigkeit.
	 */
	public void save(T sight) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(sight);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
}
