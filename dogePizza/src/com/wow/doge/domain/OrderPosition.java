package com.wow.doge.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class OrderPosition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private Meal meal;
	private Size size;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Meal getGericht() {
		return meal;
	}

	public void setGericht(Meal gericht) {
		this.meal = gericht;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	/**
	 * @return Zusammengesetzter Preis aus Grundpreis * Größenanpassung
	 */
	public double getPrice() {
		return meal.getRawPrice() * size.getPriceMultiplicator();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderPosition [id=").append(id).append(", gericht=")
				.append(meal).append(", size=").append(size).append("]");
		return builder.toString();
	}

}
