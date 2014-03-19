package com.wow.doge.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class OrderPosition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private Meal meal;

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

	/**
	 * @return Zusammengesetzter Preis aus Grundpreis * Größenanpassung
	 */
	public double getPrice() {
		return 0;
	}

	@Override
	public String toString() {
		return "OrderPosition [id=" + id + ", meal=" + meal + "]";
	}

	
}
