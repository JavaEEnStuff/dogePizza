package com.wow.doge.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.wow.doge.domain.User;

@ManagedBean(name = "sessionBean")
@SessionScoped
public class SessionBean {
	private Logger logger = Logger.getLogger(SessionBean.class);

	private User loggedInUser=null;
	private long loginTime;
	
	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		logger.info("Benutzer " + loggedInUser.getEmailAddress() + " wurde angemeldet!");
		this.loggedInUser = loggedInUser;
		loginTime = System.currentTimeMillis();
	}
	
	public String loginLogout() {
		logger.info("loggedIn: " + loggedInUser);
		if (userIsLoggedIn()) {
			logger.info("Benutzer " + loggedInUser.getEmailAddress() + " wird abgemeldet!");
			logger.info("Dauer der Anmeldung: " + (System.currentTimeMillis() - loginTime) / 1000);
			this.loggedInUser = null;
			return "/resources/javaee/main.jsf";
		} else {
			return "/resources/javaee/login.jsf";
		}
	}
	
	public String getUserName(){
		if(userIsLoggedIn()){
			return "Hallo "+loggedInUser.getFirstName()+" "+loggedInUser.getLastName();
		} else {
			return "";
		}
	}

	public boolean userIsLoggedIn(){
		return loggedInUser!=null;
	}

	@Override
	public String toString() {
		return "SessionBean [loggedInUser=" + loggedInUser + "]";
	}
	
	public String getLoginText(){
		return userIsLoggedIn()?"Logout":"Login";
	}

}
