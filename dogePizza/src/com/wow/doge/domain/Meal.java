package com.wow.doge.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Meal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private double rawPrice;
	private boolean vegeterian;
	private String description;
	private byte[] image;
	@OneToMany
	private List<Size> possibleSizes;
	@OneToMany(fetch=FetchType.EAGER)
	private List<Ingredient> ingredients;

	// TODO Erweiterungsmöglichkeiten

	public Meal() {
		possibleSizes = new LinkedList<Size>();
		ingredients = new LinkedList<Ingredient>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRawPrice() {
		return rawPrice;
	}

	public void setRawPrice(double rawPrice) {
		this.rawPrice = rawPrice;
	}

	public boolean isVegeterian() {
		return vegeterian;
	}

	public void setVegeterian(boolean vegeterian) {
		this.vegeterian = vegeterian;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public List<Size> getPossibleSizes() {
		return possibleSizes;
	}

	public void setPossibleSizes(List<Size> possibleSizes) {
		this.possibleSizes = possibleSizes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> zutaten) {
		this.ingredients = zutaten;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Gericht [id=").append(id).append(", name=")
				.append(name).append(", price=").append(rawPrice)
				.append(", vegeterian=").append(vegeterian)
				.append(", description=").append(description)
				.append(", image=").append(image!=null)
				.append(", possibleSizes=").append(possibleSizes)
				.append(", zutaten=").append(ingredients).append("]");
		return builder.toString();
	}

}
