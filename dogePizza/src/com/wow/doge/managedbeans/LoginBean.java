package com.wow.doge.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import com.wow.doge.domain.User;
import com.wow.doge.services.UserService;

@ManagedBean
@RequestScoped
public class LoginBean {
	
	
	@ManagedProperty(value="#{sessionBean}")
	private SessionBean sessionBean;
	
	private Logger logger = Logger.getLogger(LoginBean.class);

	
	private String loginEmail;
	private String password;

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public String login() {
		UserService service = new UserService();
		List<User> usersWithGivenEmail = service.getList(Restrictions.eq("emailAddress", loginEmail));
		if (usersWithGivenEmail.size() == 0) {
			logger.info("Email-Adresse wurde nicht gefunden");
			return "";
		} else {
			User user = usersWithGivenEmail.get(0);
			if (user.getPassword().equals(password)) {
				logger.info("Anmeldung korrekt");
				sessionBean.setLoggedInUser(user);
				return "main.xhtml";
			} else {
				logger.error("Passwort ist nicht korrekt!");
				return "";
			}
		}
	}
}