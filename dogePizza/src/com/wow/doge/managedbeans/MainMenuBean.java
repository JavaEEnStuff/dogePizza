package com.wow.doge.managedbeans;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.wow.doge.domain.User;
import com.wow.doge.domain.html.Menu;
import com.wow.doge.services.MenuService;

@ManagedBean
@RequestScoped
public class MainMenuBean {

	private Logger logger = Logger.getLogger(MainMenuBean.class);

	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean sessionBean;

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	/**
	 * Hier werden alle Menüs gesucht, die der Benutzer sehen darf. Dabei gelten folgende Unterscheidungen:<br/>
	 * - Benutzer nicht angemeldet<br/>
	 * - Benutzer angemeldet und kein Admin<br/>
	 * - Benutzer angemeldet und Admin<br/>
	 * @return alle Menüs entsprechend der Berechtigung der Nutzeranmeldung
	 */
	public List<Menu> getMenusToShow() {
		List<Criterion> restrictions = new LinkedList<>();
		MenuService service = new MenuService();
		if (sessionBean.userIsLoggedIn()) {
			logger.debug("User ist eingeloggt, nur diese Menüs suchen");
			User user = sessionBean.getLoggedInUser();
			if (!user.isAdmin()) {
				logger.debug("User ist kein Admin, nur diese Menüs suchen");
				restrictions.add(Restrictions.eq("adminMenu", false));
			}
		} else {
			restrictions.add(Restrictions.eq("loginRequired", false));
		}
		return service.getList(restrictions);
	}
}
