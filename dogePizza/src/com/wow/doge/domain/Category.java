package com.wow.doge.domain;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category implements Comparable<Category> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;

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

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int compareTo(Category o) {
		return getId().compareTo(o.getId());
	}

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
