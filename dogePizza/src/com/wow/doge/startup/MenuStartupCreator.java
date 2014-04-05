package com.wow.doge.startup;

import org.apache.log4j.Logger;

import com.wow.doge.domain.html.Menu;
import com.wow.doge.services.MenuService;

public class MenuStartupCreator implements StartupCreator {

	private static final Logger logger = Logger.getLogger(MenuStartupCreator.class);

	@Override
	public void create() {
		MenuService service = new MenuService();
		int count = service.deleteAll();
		logger.info(count + " Men�s wurden gel�scht");

		Menu ingredients = new Menu();
		ingredients.setName("Zutaten");
		ingredients.setText("Zutaten verwalten");
		ingredients.setAdminMenu(true);
		ingredients.setLink("/dogePizza/resources/javaee/ingredient/ingredientList.jsf");
		ingredients.setLoginRequired(true);
		service.saveOrUpdate(ingredients);

		Menu meals = new Menu();
		meals.setName("Men�s");
		meals.setText("Men�s verwalten");
		meals.setAdminMenu(true);
		meals.setLink("/dogePizza/resources/javaee/meal/mealList.jsf");
		meals.setLoginRequired(true);
		service.saveOrUpdate(meals);

		Menu questions = new Menu();
		questions.setName("Fragen");
		questions.setText("Fragen verwalten");
		questions.setAdminMenu(true);
		questions.setLink("/dogePizza/resources/javaee/question/questionList.jsf");
		questions.setLoginRequired(true);
		service.saveOrUpdate(questions);

		Menu user = new Menu();
		user.setName("Benutzer");
		user.setText("Benutzer verwalten");
		user.setAdminMenu(true);
		user.setLink("/dogePizza/resources/javaee/user/userList.jsf");
		user.setLoginRequired(true);
		service.saveOrUpdate(user);

		Menu searchMeal = new Menu();
		searchMeal.setName("Men�s");
		searchMeal.setText("Men�s suchen");
		searchMeal.setAdminMenu(false);
		searchMeal.setLink("/dogePizza/resources/javaee/meal/searchMeals.jsf");
		searchMeal.setLoginRequired(false);
		service.saveOrUpdate(searchMeal);
		logger.info("Men�s erstellt");

		Menu listOrders = new Menu();
		listOrders.setName("Bestellungen");
		listOrders.setText("Bestellungen ansehen");
		listOrders.setAdminMenu(false);
		listOrders.setLink("/dogePizza/resources/javaee/order/orderList.jsf");
		listOrders.setLoginRequired(true);
		service.saveOrUpdate(listOrders);
		logger.info("Men�s erstellt");

		Menu changePassword = new Menu();
		changePassword.setName("Passwort �ndern");
		changePassword.setText("Passwort �ndern");
		changePassword.setAdminMenu(false);
		changePassword.setLink("/dogePizza/resources/javaee/user/changePassword.jsf");
		changePassword.setLoginRequired(true);
		service.saveOrUpdate(changePassword);
		logger.info("Men�s erstellt");

	}

}
