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

	private Order order;
	private List<Criterion> criterions;
	/** default = 0 */
	private int maxResults;
	private Comparator<T> comparator;
	private ProjectionList projectionList;

	public SelectionHelper() {
		maxResults = 0;
		criterions = new LinkedList<Criterion>();
		projectionList = Projections.projectionList();
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
}
