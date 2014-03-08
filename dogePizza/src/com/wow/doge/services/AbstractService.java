package com.wow.doge.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import com.wow.doge.hibernate.HibernateUtil;

public abstract class AbstractService<T> {

	private Logger logger = Logger.getLogger(AbstractService.class);

	/** Klasse zum Casten */
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
	 * Sucht ein bestimmtes Objekt mit einer ID aus der Datenbank
	 * 
	 * @param id
	 * @return das gefundene Objekt oder null.
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
			logger.error(e.getMessage(), e);
			// TODO: errorHandling
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
			logger.error(e.getMessage(), e);
			// TODO: errorHandling
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * @param searchString
	 * @return all found Objects
	 */
	public List<T> getList(Criterion... criterions) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(getHibernateClass());
			for (Criterion nextCriterion : criterions) {
				criteria.add(nextCriterion);
			}
			List<T> resultList = (ArrayList<T>) criteria.list();
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
	
	public List<T> getList(Collection<Criterion> criterions) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(getHibernateClass());
			for (Criterion nextCriterion : criterions) {
				criteria.add(nextCriterion);
			}
			List<T> resultList = (ArrayList<T>) criteria.list();
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

	/**
	 * Speichert eine Sehenswürdigkeit in der Datenbank. Diese Methode erkennt
	 * selbstständig, ob ein Objekt neu in die Datenbank eingefügt oder dort
	 * aktualisiert werden muss.
	 * 
	 * @param t Object to save
	 */
	public void saveOrUpdate(T t) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(t);
			session.flush();
			session.getTransaction().commit();
		} catch(HibernateException e){
			logger.error("HibernateException", e);
			e.printStackTrace();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			// TODO: errorHandling
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
}
