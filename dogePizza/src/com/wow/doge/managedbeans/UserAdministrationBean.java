package com.wow.doge.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import com.wow.doge.domain.User;
import com.wow.doge.helper.UserEvaluationHelper;
import com.wow.doge.services.UserService;

@ManagedBean
public class UserAdministrationBean {

	private static final Logger logger = Logger.getLogger(UserAdministrationBean.class);

	@ManagedProperty("#{param.userId}")
	private int userId;
	private User user;
	private Double minimumOrderValue;

	public UserAdministrationBean() {
		user = new User();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	// =========== Funktionen ===============

	public Double getMinimumOrderValue() {
		return minimumOrderValue;
	}

	public void setMinimumOrderValue(Double minimumOrderValue) {
		this.minimumOrderValue = minimumOrderValue;
	}

	public List<User> getUsers() {
		UserEvaluationHelper helper = new UserEvaluationHelper();
		return helper.getAllUsersWithMinimumOrderValue(minimumOrderValue);
	}

	public String deleteUser() {
		logger.info("Versuche User zu löschen: " + userId);
		UserService service = new UserService();
		User user = service.get(userId);
		logger.info("Vollständiges Objekt: " + user);
		service.delete(user);
		return "";
	}

	public String saveUser() {
		logger.info("Versuche User zu speichern: " + userId);
		UserService service = new UserService();
		logger.info("Vollständiges Objekt: " + user);
		service.saveOrUpdate(user);
		return userList();
	}

	// ============ Links ============

	public String showUser() {
		UserService service = new UserService();
		user = service.get(userId);
		return "showUser.jsf";
	}

	public String changeUser() {
		UserService service = new UserService();
		user = service.get(userId);
		return "changeUser.jsf";
	}

	public String userList() {
		return "userList.jsf";
	}

	public String search() {
		return "";
	}

	public String clearSearch() {
		minimumOrderValue = null;
		return "";
	}

}
