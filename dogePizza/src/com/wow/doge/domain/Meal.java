package com.wow.doge.domain;

import java.util.Arrays;
import java.util.Comparator;
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

	@OneToMany(mappedBy = "meal")
	private Set<OrderPosition> positions;

	private Double firstPrice;
	private Double secondPrice;
	private Double thirdPrice;

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

	public static Comparator<Meal> getMealNameComparator() {
		return new MealNameComparator();
	}

	private static class MealNameComparator implements Comparator<Meal> {

		@Override
		public int compare(Meal o1, Meal o2) {
			return o1.getName().compareTo(o2.getName());
		}

	}

	public static Comparator<Meal> getMealPriceComparator() {
		return new MealPriceComparator();
	}

	private static class MealPriceComparator implements Comparator<Meal> {

		@Override
		public int compare(Meal o1, Meal o2) {
			int mealWithHigherPrice = 1;
			double highestPrice = o1.getFirstPrice();
			if (o1.getSecondPrice() != null && o1.getSecondPrice() > highestPrice)
				highestPrice = o1.getSecondPrice();
			if (o1.getThirdPrice() != null && o1.getThirdPrice() > highestPrice)
				highestPrice = o1.getThirdPrice();

			if (o2.getFirstPrice() != null && o2.getFirstPrice() > highestPrice) {
				mealWithHigherPrice = -1;
				highestPrice = o1.getFirstPrice();
			}
			if (o2.getSecondPrice() != null && o2.getSecondPrice() > highestPrice) {
				mealWithHigherPrice = -1;
				highestPrice = o1.getSecondPrice();
			}
			if (o2.getThirdPrice() != null && o2.getThirdPrice() > highestPrice) {
				mealWithHigherPrice = -1;
				highestPrice = o1.getThirdPrice();
			}
			return mealWithHigherPrice;
		}

	}

	public static Comparator<Meal> getReverseMealPriceComparator() {
		return new ReverseMealPriceComparator();
	}

	private static class ReverseMealPriceComparator implements Comparator<Meal> {

		private static final int GREATER = 1;
		private static final int LOWER = -1;

		@Override
		public int compare(Meal o1, Meal o2) {
			int mealWithHigherPrice = LOWER;
			double lowest = o1.getFirstPrice();
			if (o1.getSecondPrice() != null && o1.getSecondPrice() < lowest)
				lowest = o1.getSecondPrice();
			if (o1.getThirdPrice() != null && o1.getThirdPrice() < lowest)
				lowest = o1.getThirdPrice();
			if (o2.getFirstPrice() != null && o2.getFirstPrice() < lowest) {
				mealWithHigherPrice = GREATER;
				lowest = o1.getFirstPrice();
			}
			if (o2.getSecondPrice() != null && o2.getSecondPrice() < lowest) {
				mealWithHigherPrice = GREATER;
				lowest = o1.getSecondPrice();
			}
			if (o2.getThirdPrice() != null && o2.getThirdPrice() < lowest) {
				mealWithHigherPrice = GREATER;
				lowest = o1.getThirdPrice();
			}
			return mealWithHigherPrice;
		}

	}

	@Override
	public String toString() {
		return "Meal [id=" + id + ", name=" + name + ", vegeterian=" + vegeterian + ", description=" + description + ", image=" + Arrays.toString(image)
				+ ", ingredients=" + ingredients + ", favoredBy=" + favoredBy + ", firstPrice=" + firstPrice + ", secondPrice=" + secondPrice + ", thirdPrice="
				+ thirdPrice + "]";
	}

	//	public static void main(String[] args) {
	//		SortedSet<Meal> meals = new TreeSet<>(Meal.getReverseMealPriceComparator());
	//		Meal meal = new Meal();
	//		meal.setFirstPrice(1.0);
	//		meal.setSecondPrice(2.0);
	//		meal.setThirdPrice(3.0);
	//
	//		Meal meal1 = new Meal();
	//		meal1.setFirstPrice(3.0);
	//		meal1.setSecondPrice(4.0);
	//		meal1.setThirdPrice(2.0);
	//		meals.add(meal);
	//		meals.add(meal1);
	//		System.out.println(meals);
	//	}

	@Override
	public int compareTo(Meal o) {
		if (id == null || o.getId() == null) {
			return name.compareTo(o.getName());
		} else {
			return id.compareTo(o.getId());
		}
	}

}
