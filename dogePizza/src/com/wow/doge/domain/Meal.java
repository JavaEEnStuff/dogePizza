package com.wow.doge.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Ingredient> ingredients;
	@ManyToMany(mappedBy = "favoriteMeals", fetch=FetchType.LAZY)
	private Set<User> favoredBy;
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<Size> possibleSizes;

	// TODO Erweiterungsmöglichkeiten

	public Meal() {
		ingredients = new HashSet<Ingredient>();
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void setIngredients(List<Ingredient> ingredientsAsList) {
		ingredients = new HashSet<>();
		for(Ingredient nextIng :ingredientsAsList){
			ingredients.add(nextIng);
		}
	}

	public Set<User> getFavoredBy() {
		return favoredBy;
	}

	public void setFavoredBy(Set<User> favoredBy) {
		this.favoredBy = favoredBy;
	}

	@Override
	public String toString() {
		return "Meal [id=" + id + ", name=" + name + ", rawPrice=" + rawPrice + ", vegeterian=" + vegeterian + ", description=" + description + ", image="
				+ Arrays.toString(image) + ", ingredients=" + ingredients + "]";
	}

}
