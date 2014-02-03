package com.wow.doge.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;

import com.wow.doge.domain.User;
import com.wow.doge.services.UserService;

@ManagedBean
public class UserAdministrationBean {

	public List<User> getAllUsers() {
		UserService userService = new UserService();
		return userService.getList();
	}
}
