package com.wow.doge;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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
	}

}
