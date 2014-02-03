package com.wow.doge.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name="dogePizzaUser")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false)
	private String emailAddress;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private boolean isAdmin;

	@OneToOne
	private Address defaultAddress;

	@JoinColumn
	@OneToMany
	private List<Meal> favoriteMeals;

	public User() {
		favoriteMeals = new LinkedList<Meal>();
		isAdmin = false;
		defaultAddress = new Address();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(Address defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<Meal> getFavoriteMeals() {
		return favoriteMeals;
	}

	public void setFavoriteMeals(List<Meal> favoriteMeals) {
		this.favoriteMeals = favoriteMeals;
	}

	public void addFavoriteMeals(Meal... newFavoriteMeals) {
		for (Meal nextMeal : newFavoriteMeals) {
			this.favoriteMeals.add(nextMeal);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [id=").append(id).append(", firstName=")
				.append(firstName).append(", lastName=").append(lastName)
				.append(", emailAddress=").append(emailAddress)
				.append(", password=*********")
				.append(", defaultAddress=").append(defaultAddress).append("]");
		return builder.toString();
	}
}
