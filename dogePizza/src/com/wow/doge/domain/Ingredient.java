package com.wow.doge.domain;

import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Zutat eines Menüs. Kann auch mehreren Menüs zugeordnet sein.
 */
@Entity
public class Ingredient implements Comparable<Ingredient> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;

	@ManyToMany(mappedBy="ingredients",fetch=FetchType.LAZY)
	private List<Meal> meals;
	
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

	public List<Meal> getMeals() {
		return meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", name=" + name + "]";
	}

	/**
	 * natural order: id
	 */
	@Override
	public int compareTo(Ingredient o) {
		return Integer.valueOf(id).compareTo(Integer.valueOf(o.getId()));
	}
	
	/**
	 * @return Comparator zur Sortierung nach Namen
	 */
	public static Comparator<Ingredient> getIngredientNameComparator(){
		return new IngredientNameComparator();
	}
	
	private static class IngredientNameComparator implements Comparator<Ingredient>{

		@Override
		public int compare(Ingredient o1, Ingredient o2) {
			// TODO Auto-generated method stub
			return o1.getName().compareTo(o2.getName());
		}
		
	}

}
