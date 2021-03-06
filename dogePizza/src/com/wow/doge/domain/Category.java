package com.wow.doge.domain;

import java.util.Comparator;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Kategorie eines Men�s. Eine Kategorie kann auch mehreren Men�s zugeordnet sein.
 */
@Entity
public class Category implements Comparable<Category> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;

	@OneToMany(mappedBy = "category")
	private Set<Meal> meals;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Meal> getMeals() {
		return meals;
	}

	public void setMeals(Set<Meal> meals) {
		this.meals = meals;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}

	/**
	 * natural order: id
	 */
	@Override
	public int compareTo(Category o) {
		return getId().compareTo(o.getId());
	}

	/**
	 * @return Comparator zur Sortierung nach Namen
	 */
	public static Comparator<Category> getCategoryNameComparator() {
		return new CategoryNameComparator();
	}

	private static class CategoryNameComparator implements Comparator<Category> {

		@Override
		public int compare(Category o1, Category o2) {
			return o1.getName().compareTo(o2.getName());
		}

	}

}
