package com.wow.doge.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Auftragsposition als Teil eines Auftrags.
 */
@Entity
public class OrderPosition implements Comparable<OrderPosition> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Meal meal;
	private Double price;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "OrderPosition [id=" + id + ", meal=" + meal + ", price=" + price + "]";
	}

	/**
	 * natürliche Sortierung: id. Ist diese leer, wird nach Meal sortiert.
	 */
	@Override
	public int compareTo(OrderPosition o) {
		if (id == null || o.getId() == null) {
			return meal.compareTo(o.getMeal());
		} else {
			return id.compareTo(o.getId());
		}
	}
}
