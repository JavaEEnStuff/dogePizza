package com.wow.doge.services;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

/**
 * Selektionshelfer, der verschiedene Einschränkungen vorgibt, die dann im allgemeinen getList des AbstractService abgefangen werden können.
 * In der Regel ist es mit den hier angegebenen Variablen so, dass sie nur zum Tragen kommen, wenn sie gesetzt wurden. Null-Werte werden also einfach ignoriert.
 * 
 * @param <T>
 */
public class SelectionHelper<T> {

	/** SQL-Sortierung */
	private Order order;
	/** Kritieren */
	private List<Criterion> criterions;
	/** default = 0 */
	private int maxResults;
	/** Java-Sortierung */
	private Comparator<T> comparator;
	/** Spalteneinschränkung */
	private ProjectionList projectionList;
	/** Alias-Liste */
	private List<Alias> alias;

	public SelectionHelper() {
		maxResults = 0;
		criterions = new LinkedList<Criterion>();
		projectionList = Projections.projectionList();
		alias = new LinkedList<>();
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<Criterion> getCriterions() {
		return criterions;
	}

	public void setCriterions(List<Criterion> criterions) {
		this.criterions = criterions;
	}

	public SelectionHelper<T> addCriterion(Criterion criterion) {
		criterions.add(criterion);
		return this;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public ProjectionList getProjectionList() {
		return projectionList;
	}

	public void setProjectionList(ProjectionList projectionList) {
		this.projectionList = projectionList;
	}

	public SelectionHelper<T> addProjection(Projection projection) {
		this.projectionList.add(projection);
		return this;
	}

	public List<Alias> getAlias() {
		return alias;
	}

	public void setAlias(List<Alias> alias) {
		this.alias = alias;
	}

	public void addAlias(Alias newAlias) {
		this.alias.add(newAlias);
	}

	/**
	 * Repräsentationsklasse eines SQL-Alias. Diese können nur zur Laufzeit "erstellt" werden (über die Session). Daher werden hier die Informationen abgespeichert,
	 * um das Erstellen möglich zu machen
	 */
	public static class Alias {
		private final String name;
		private final String alias;

		public Alias(String name, String alias) {
			super();
			this.name = name;
			this.alias = alias;
		}

		public String getName() {
			return name;
		}

		public String getAlias() {
			return alias;
		}

		@Override
		public String toString() {
			return "Alias [name=" + name + ", alias=" + alias + "]";
		}
	}
}
