package com.wow.doge.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Meal implements Comparable<Meal> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	private boolean vegeterian;
	private String description;
	private byte[] image;
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Ingredient> ingredients;
	@ManyToMany(mappedBy = "favoriteMeals", fetch = FetchType.EAGER)
	private Set<User> favoredBy;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Category category;

	@OneToMany(mappedBy = "meal", fetch=FetchType.EAGER)
	private Set<OrderPosition> positions;

	private Double firstPrice;
	private Double secondPrice;
	private Double thirdPrice;

	public Meal() {
		ingredients = new HashSet<Ingredient>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
		for (Ingredient nextIng : ingredientsAsList) {
			ingredients.add(nextIng);
		}
	}

	public void addIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
	}

	public Set<User> getFavoredBy() {
		return favoredBy;
	}

	public void setFavoredBy(Set<User> favoredBy) {
		this.favoredBy = favoredBy;
	}

	public void addFavoredBy(User user) {
		favoredBy.add(user);
	}

	public void removeFavoredBy(User user) {
		User userToRemove = null;
		for (User nextUser : favoredBy) {
			if (nextUser.equals(user))
				userToRemove = nextUser;
		}

		favoredBy.remove(userToRemove);
	}
	
	public boolean isFavoredBy(User user) {
		for (User nextUser : favoredBy) {
			if (nextUser.equals(user))
				return true;
		}

		return false;
	}

	// ============== COMPARATOR ===============

	public Double getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(Double firstPrice) {
		this.firstPrice = firstPrice;
	}

	public Double getSecondPrice() {
		return secondPrice;
	}

	public void setSecondPrice(Double secondPrice) {
		this.secondPrice = secondPrice;
	}

	public Double getThirdPrice() {
		return thirdPrice;
	}

	public void setThirdPrice(Double thirdPrice) {
		this.thirdPrice = thirdPrice;
	}

	public Set<OrderPosition> getPositions() {
		return positions;
	}

	public void setPositions(Set<OrderPosition> positions) {
		this.positions = positions;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	@Override
	public String toString() {
		return "Meal [id=" + id + ", name=" + name + ", vegeterian=" + vegeterian + ", description=" + description + ", image=" + Arrays.toString(image)
				+ ", ingredients=" + ingredients + ", favoredBy=" + favoredBy + ", firstPrice=" + firstPrice + ", secondPrice=" + secondPrice + ", thirdPrice="
				+ thirdPrice + "]";
	}

	@Override
	public int compareTo(Meal o) {
		if (id == null || o.getId() == null) {
			return name.compareTo(o.getName());
		} else {
			return id.compareTo(o.getId());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((firstPrice == null) ? 0 : firstPrice.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((positions == null) ? 0 : positions.hashCode());
		result = prime * result + ((secondPrice == null) ? 0 : secondPrice.hashCode());
		result = prime * result + ((thirdPrice == null) ? 0 : thirdPrice.hashCode());
		result = prime * result + (vegeterian ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meal other = (Meal) obj;
		return compareTo(other) == 0;
	}

}
