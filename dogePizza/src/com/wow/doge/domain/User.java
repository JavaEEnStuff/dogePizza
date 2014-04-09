package com.wow.doge.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Hauptbenutzerobjekt. Der Name "user" ist in PostgreSQL reserviert, weshalb ein alternativer Entitätsname angegeben wird. Ein Benutzer hat eine Standardadresse
 * und wird von Bestellungen referenziert. Außerdem können einem Benutzer verschiedene Favoriten-Menüs zugeordnet werden.
 */
@Entity(name = "dogePizzaUser")
public class User implements Comparable<User> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String firstName;
	private String lastName;
	@Column(nullable = false)
	private String emailAddress;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private boolean isAdmin;

	@OneToOne
	private Address defaultAddress;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Meal> favoriteMeals;

	@OneToMany(mappedBy = "user")
	private Set<Order> orders;

	public User() {
		favoriteMeals = new HashSet<Meal>();
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

	public Set<Meal> getFavoriteMeals() {
		return favoriteMeals;
	}

	public void setFavoriteMeals(Set<Meal> favoriteMeals) {
		this.favoriteMeals = favoriteMeals;
	}

	public void addFavoriteMeals(Meal... newFavoriteMeals) {
		for (Meal nextMeal : newFavoriteMeals) {
			this.favoriteMeals.add(nextMeal);
		}
	}

	public void removeFavoritedMeal(Meal meal) {
		Meal mealToRemove = null;
		for(Meal nextMeal : favoriteMeals){
			if(meal.equals(nextMeal)){
				mealToRemove = nextMeal;
			}
		}
		favoriteMeals.remove(mealToRemove);
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	public boolean hasFavoredMeal(Meal meal) {
		for (Meal nextMeal : favoriteMeals) {
			if (nextMeal.equals(meal))
				return true;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [id=").append(id).append(", firstName=").append(firstName).append(", lastName=").append(lastName).append(", emailAddress=")
				.append(emailAddress).append(", password=*********").append(", defaultAddress=").append(defaultAddress).append("]");
		return builder.toString();
	}

	/**
	 * natural order: id
	 */
	@Override
	public int compareTo(User o) {
		return Integer.valueOf(id).compareTo(Integer.valueOf(o.getId()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((defaultAddress == null) ? 0 : defaultAddress.hashCode());
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + (isAdmin ? 1231 : 1237);
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		User other = (User) obj;
		return compareTo(other)==0;
	}

}
