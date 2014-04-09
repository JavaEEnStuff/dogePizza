package com.wow.doge.startup;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Startklasse die beim Start des Anwendungsservers aufgerufen wird und die Anwendung initialisiert.
 */
public class StartupListener implements ServletContextListener {

	private List<StartupCreator> startupCreators;

	private static final Logger logger = Logger.getLogger(StartupListener.class);

	public StartupListener() {
		initStartupCreators();
	}

	/**
	 * hier werden die einzelnen Creator-Klassen eingepflegt, die beim Startup aufgerufen werden sollen. Diese werden dann alle zu einem spaeteren Zeitpunkt
	 * verarbeitet und die entsprechende create-Methode aufgerufen.
	 * ACHTUNG: falls Abhängigkeiten bestehen, dann bitte auf die Reihenfolge beim Einpflegen achten!
	 */
	private void initStartupCreators() {
		startupCreators = new LinkedList<>();
		startupCreators.add(new MenuStartupCreator());
		startupCreators.add(new UserStartupCreator());
		startupCreators.add(new IngredientMealStartupCreator());
		startupCreators.add(new QuestionDraftStartupCreator());
	}

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

		// STARTUP CREATION
		for (StartupCreator creator : startupCreators) {
			creator.create();
		}
	}
}
