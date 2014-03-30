package com.wow.doge.startup;

/**
 * Creator-Interface fuer Klassen, die beim Startup bereits vorgefertigte Objekte in die Datenbank schreiben muessen.
 * Dabei stellt das Interface nur die Funktion zum Anlegen bereit. 
 */
public interface StartupCreator {

	/**
	 * Anlegen eines oder mehrere Objekte beim Start
	 */
	public void create();
}
