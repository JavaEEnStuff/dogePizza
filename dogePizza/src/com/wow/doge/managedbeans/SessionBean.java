package com.wow.doge.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.wow.doge.domain.User;

/**
 * Zentrale Bean, die die Session und den damit verkn�pften Benutzer handelt.
 */
@ManagedBean(name = "sessionBean")
@SessionScoped
public class SessionBean {

	@ManagedProperty(value="#{shoppingCart}")
    private ShoppingCart shoppingCart; // +setter
	
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
			shoppingCart.clearShoppingCart();
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
	
	public boolean isUserLoggedIn(){
		return userIsLoggedIn();
	}

	@Override
	public String toString() {
		return "SessionBean [loggedInUser=" + loggedInUser + "]";
	}
	
	public String getLoginText(){
		return userIsLoggedIn()?"Logout":"Login";
	}
	
	public String main(){
		return "/resources/javaee/main.xhtml";
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	public String order(){
		return shoppingCart.order(loggedInUser);
	}
	
	/**
	 * muss von hier aus aufgerufen werden, da die SessionBean nicht als ManagedProperty in den Shopping Cart gesetzt werden kann (Zirkel),
	 * aber trotzdem Informationen �ber den Benutzer im Shopping Cart ben�tigt werden.
	 * @return Link zur n�chsten Maske
	 */
	public String completeOrder(){
		shoppingCart.completeOrder(loggedInUser);
		return "orderFinished.jsf";
	}
}
