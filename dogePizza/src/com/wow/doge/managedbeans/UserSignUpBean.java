package com.wow.doge.managedbeans;

import javax.faces.bean.ManagedBean;

import com.wow.doge.domain.User;
import com.wow.doge.services.UserService;

@ManagedBean
public class UserSignUpBean {

	private User user;
	private String passwordToCheck;

	public UserSignUpBean() {
		user = new User();
	}

	public String getFirstName() {
		return user.getFirstName();
	}

	public void setFirstName(String firstName) {
		user.setFirstName(firstName);
	}

	public String getLastName() {
		return user.getLastName();
	}

	public void setLastName(String lastName) {
		user.setLastName(lastName);
	}

	public String getEmailAddress() {
		return user.getEmailAddress();
	}

	public void setEmailAddress(String emailAddress) {
		user.setEmailAddress(emailAddress);
	}

	public String getPassword() {
		return user.getPassword();
	}

	public void setPassword(String password) {
		user.setPassword(password);
	}

	public String getStreetName() {
		return user.getDefaultAddress().getStreetName();
	}
	
	public void setStreetName(String streetName) {
		user.getDefaultAddress().setStreetName(streetName);
	}

	public Integer getNumber() {
		return user.getDefaultAddress().getNumber();
	}
	public void setNumber(Integer number) {
		user.getDefaultAddress().setNumber(number);
	}

	public void setStreetName(int number) {
		user.getDefaultAddress().setNumber(number);
	}

	public String getCity() {
		return user.getDefaultAddress().getCity();
	}

	public void setCity(String city) {
		user.getDefaultAddress().setCity(city);
	}

	public Integer getZipCode() {
		return user.getDefaultAddress().getZipCode();
	}

	public void setZipCode(Integer zipCode) {
		user.getDefaultAddress().setZipCode(zipCode);
	}

	public String getPasswordToCheck() {
		return passwordToCheck;
	}

	public void setPasswordToCheck(String passwortToCheck) {
		this.passwordToCheck = passwortToCheck;
	}
	
	public String tryToSignUp(){
		if(user.getPassword()!=null && !user.getPassword().isEmpty() && user.getPassword().equals(passwordToCheck)){
			UserService userService = new UserService();
			userService.saveOrUpdate(user);
			return "../login.xhtml";
		} else {
			return "";
		}
	}

}