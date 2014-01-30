package com.wow.doge.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Size {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String description;
	private double priceMultiplicator;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPriceMultiplicator() {
		return priceMultiplicator;
	}

	public void setPriceMultiplicator(double priceMultiplicator) {
		this.priceMultiplicator = priceMultiplicator;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Size [id=").append(id).append(", name=").append(name)
				.append(", description=").append(description)
				.append(", priceMultiplicator=").append(priceMultiplicator)
				.append("]");
		return builder.toString();
	}

}
