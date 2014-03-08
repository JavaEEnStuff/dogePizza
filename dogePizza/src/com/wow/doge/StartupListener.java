package com.wow.doge;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.wow.doge.domain.html.Menu;
import com.wow.doge.services.MenuService;

public class StartupListener implements ServletContextListener{

	Logger logger = Logger.getLogger(StartupListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Context initialisiert! ");
		System.setProperty("appRootPath", sce.getServletContext().getRealPath("/"));
		String prefix=sce.getServletContext().getRealPath("/");
		String file="WEB-INF"+System.getProperty("file.separator")+"classes"+System.getProperty("file.separator")+"log4j.properties";
	    logger.info(prefix+file); 
		PropertyConfigurator.configure(prefix+file);
	    logger.info("Starting the system."); 
	    
	    createMenus();
	}

	private void createMenus() {
		MenuService service = new MenuService();
		int count = service.deleteAll();
		logger.info(count+" Men�s wurden gel�scht");
		
	    Menu ingredients = new Menu();
	    ingredients.setName("Zutaten");
	    ingredients.setText("Zutaten verwalten");
	    ingredients.setAdminMenu(true);
	    ingredients.setLink("/dogePizza/resources/javaee/ingredient/ingredientList.xhtml");
	    ingredients.setLoginRequired(true);
	    service.saveOrUpdate(ingredients);
	    

	    Menu sizes = new Menu();
	    sizes.setName("Gr��en");
	    sizes.setText("Gr��en verwalten");
	    sizes.setAdminMenu(true);
	    sizes.setLink("/dogePizza/resources/javaee/size/sizeList.xhtml");
	    sizes.setLoginRequired(true);
	    service.saveOrUpdate(sizes);
	    
	    Menu meals = new Menu();
	    meals.setName("Men�s");
	    meals.setText("Men�s verwalten");
	    meals.setAdminMenu(true);
	    meals.setLink("/dogePizza/resources/javaee/meal/mealList.xhtml");
	    meals.setLoginRequired(true);
	    service.saveOrUpdate(meals);
	    
	    Menu questions = new Menu();
	    questions.setName("Men�s");
	    questions.setText("Men�s verwalten");
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
	    logger.info("Men�s erstellt");
	}

}
