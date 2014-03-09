package com.wow.doge;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.criterion.Restrictions;

import com.wow.doge.domain.Address;
import com.wow.doge.domain.User;
import com.wow.doge.domain.html.Menu;
import com.wow.doge.services.AddressService;
import com.wow.doge.services.MenuService;
import com.wow.doge.services.UserService;

public class StartupListener implements ServletContextListener {

	private static final String ADMIN_EMAIL_ADDRESS = "doge";

	Logger logger = Logger.getLogger(StartupListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Context initialisiert! ");
		System.setProperty("appRootPath", sce.getServletContext().getRealPath("/"));
		String prefix = sce.getServletContext().getRealPath("/");
		String file = "WEB-INF" + System.getProperty("file.separator") + "classes" + System.getProperty("file.separator") + "log4j.properties";
		logger.info(prefix + file);
		PropertyConfigurator.configure(prefix + file);
		logger.info("Starting the system.");

		createMenus();
		createAdminUser();
	}

	private void createAdminUser() {
		UserService userService = new UserService();
		List<User> doges = userService.getList(Restrictions.eq("emailAddress", ADMIN_EMAIL_ADDRESS));
		if (doges.size() == 0) {
			logger.info("Lege neuen Admin-User mit Mail "+ADMIN_EMAIL_ADDRESS+" an...");
			User admin = new User();
			admin.setAdmin(true);
			admin.setPassword("15admin15");
			admin.setEmailAddress(ADMIN_EMAIL_ADDRESS);
			admin.setFirstName(ADMIN_EMAIL_ADDRESS);
			admin.setLastName("pizza");

			Address defaultAddress = new Address();
			defaultAddress.setCity("DogeTown");
			defaultAddress.setNumber(12);
			defaultAddress.setStreetName("Doge Alley");
			defaultAddress.setZipCode(12369);
			AddressService addressService = new AddressService();
			addressService.saveOrUpdate(defaultAddress);
			admin.setDefaultAddress(defaultAddress);
			userService.saveOrUpdate(admin);
		}

	}

	private void createMenus() {
		MenuService service = new MenuService();
		int count = service.deleteAll();
		logger.info(count + " Menüs wurden gelöscht");

		Menu ingredients = new Menu();
		ingredients.setName("Zutaten");
		ingredients.setText("Zutaten verwalten");
		ingredients.setAdminMenu(true);
		ingredients.setLink("/dogePizza/resources/javaee/ingredient/ingredientList.xhtml");
		ingredients.setLoginRequired(true);
		service.saveOrUpdate(ingredients);

		Menu sizes = new Menu();
		sizes.setName("Größen");
		sizes.setText("Größen verwalten");
		sizes.setAdminMenu(true);
		sizes.setLink("/dogePizza/resources/javaee/size/sizeList.xhtml");
		sizes.setLoginRequired(true);
		service.saveOrUpdate(sizes);

		Menu meals = new Menu();
		meals.setName("Menüs");
		meals.setText("Menüs verwalten");
		meals.setAdminMenu(true);
		meals.setLink("/dogePizza/resources/javaee/meal/mealList.xhtml");
		meals.setLoginRequired(true);
		service.saveOrUpdate(meals);

		Menu questions = new Menu();
		questions.setName("Fragen");
		questions.setText("Fragen verwalten");
		questions.setAdminMenu(true);
		questions.setLink("/dogePizza/resources/javaee/question/questionList.xhtml");
		questions.setLoginRequired(true);
		service.saveOrUpdate(questions);

		Menu user = new Menu();
		user.setName("Benutzer");
		user.setText("Benutzer verwalten");
		user.setAdminMenu(true);
		user.setLink("/dogePizza/resources/javaee/user/userList.xhtml");
		user.setLoginRequired(true);
		service.saveOrUpdate(user);
		logger.info("Menüs erstellt");
	}

}
