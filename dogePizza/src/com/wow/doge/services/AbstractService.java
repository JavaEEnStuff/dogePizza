package com.wow.doge.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.wow.doge.hibernate.HibernateUtil;
import com.wow.doge.services.SelectionHelper.Alias;

/**
 * Allmighty Service-Klasse für den Großteil der Datenbankanfragen zuständig ist. Durch Generics wird die Logik dabei zentralisiert und muss nicht für jede Domain-Klasse
 * neu geschrieben werden. Dabei werden verschiedene grundlegende Funktionen bereit gestellt, die sich um Standard-Datenbankanfragen kümmern.
 *
 * @param <T>
 */
public abstract class AbstractService<T> {

	protected Logger logger = Logger.getLogger(AbstractService.class);

	/** Klasse zum Casten, wird für Hibernate benötigt, damit das ganze auch mit den Generics klappt. */
	protected abstract Class<T> getHibernateClass();

	/**
	 * Löschen eines Objektes
	 * @param object
	 */
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
	 * @param ids
	 * @return alle Objekte, deren ids in der übergebene Liste sind.
	 */
	public List<T> whereIdsIn(List<Integer> ids) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(getHibernateClass());
			if (ids == null || ids.isEmpty()) {
				return new LinkedList<T>();
			}
			criteria.add(Restrictions.in("id", ids));
			List<T> list = (List<T>) criteria.list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * @param ids
	 * @return alle Objekte, deren Ids NICHT in der übergebenen Liste sind
	 */
	public List<T> whereIdsNotIn(List<Integer> ids) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(getHibernateClass());
			if (ids != null && !ids.isEmpty()) {
				criteria.add(Restrictions.not(Restrictions.in("id", ids)));
			}
			List<T> list = (List<T>) criteria.list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * @param id
	 * @return das Objekt mit der übergebenen ID
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
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * @return Liste ALLER Objekte, ausnahmslos
	 */
	public List<T> getList() {
		return getListWithComparator(null);
	}

	/**
	 * @param comparator
	 * @return Liste aller Objekte, sortiert
	 */
	public List<T> getListWithComparator(Comparator<T> comparator) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			List<T> resultList = (ArrayList<T>) session.createCriteria(getHibernateClass()).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			if (comparator != null) {
				Collections.sort(resultList, comparator);
			}
			session.getTransaction().commit();
			return resultList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * @param criterions
	 * @return alle Objekte, die den Übergabekriterien entsprechen
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
			List<T> resultList = (ArrayList<T>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			session.getTransaction().commit();
			return resultList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * @param helper spezielle Helperklasse, die verschiedene Einschränkungen bereit stellt.
	 * @return alle Objekte, die den Einschränkungen durch den SelectionHelper entsprechen.
	 */
	public List<T> getList(SelectionHelper<T> helper) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(getHibernateClass());
			// zunaechst die Aliase
			for(Alias alias : helper.getAlias()){
				criteria.createAlias(alias.getName(), alias.getAlias());
			}
			// Kriterien
			for (Criterion nextCriterion : helper.getCriterions()) {
				criteria.add(nextCriterion);
			}
			// LIMIT
			if (helper.getMaxResults() != 0) {
				criteria.setMaxResults(helper.getMaxResults());
			}
			// Sortierung auf SQL-Ebene
			if (helper.getOrder() != null) {
				criteria.addOrder(helper.getOrder());
			}
			// Einschränkung der Spalten
			if (helper.getProjectionList().getLength() > 0) {
				criteria.setProjection(helper.getProjectionList());
			}
			logger.debug(criteria.toString());
			List<T> resultList = (ArrayList<T>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			// Java-Sortierung
			if (helper.getComparator() != null) {
				Collections.sort(resultList, helper.getComparator());
			}
			session.getTransaction().commit();
			return resultList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * @param criterions
	 * @return Liste aller Objekte, die die Kriterien erfüllen
	 */
	public List<T> getList(Collection<Criterion> criterions) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(getHibernateClass());
			for (Criterion nextCriterion : criterions) {
				criteria.add(nextCriterion);
			}
			List<T> resultList = (ArrayList<T>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			session.getTransaction().commit();
			return resultList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	
	/**
	 * Speichert ein Objekt in der Datenbank. Diese Methode erkennt selbstständig, ob ein Objekt neu in die Datenbank eingefügt oder dort
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
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Alternative zu saveOrUpdate. Wird verwendet, wenn was mit dem Identifier bei Joins nicht klappt.
	 * @param t
	 */
	public void merge(T t) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.merge(t);
			session.flush();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			logger.error("HibernateException", e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
}
