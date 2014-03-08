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

	public List<Menu> getMenusToShow() {
		List<Criterion> restrictions = new LinkedList<>();
		MenuService service = new MenuService();
		if (sessionBean.userIsLoggedIn()) {
			logger.info("User ist eingeloggt, nur diese Menüs suchen");
			User user = sessionBean.getLoggedInUser();
			if (!user.isAdmin()) {
				logger.info("User ist kein Admin, nur diese Menüs suchen");
				restrictions.add(Restrictions.eq("adminMenu", false));
			}
		} else {
			restrictions.add(Restrictions.eq("loginRequired", false));
		}
		return service.getList(restrictions);
	}
}
