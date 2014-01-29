package com.wow.doge.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable=false)
	private String firstName;
	@Column(nullable=false)
	private String lastName;
	@Column(nullable=false)
	private String emailAddress;
	@Column(nullable=false)
	private String password;
	@JoinColumn(nullable=false)
	private Address defaultAddress;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [id=").append(id).append(", firstName=")
				.append(firstName).append(", lastName=").append(lastName)
				.append(", emailAddress=").append(emailAddress)
				.append(", password=").append(password)
				.append(", defaultAddress=").append(defaultAddress).append("]");
		return builder.toString();
	}
}
