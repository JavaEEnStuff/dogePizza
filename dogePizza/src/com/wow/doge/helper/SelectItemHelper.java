package com.wow.doge.helper;

import java.util.List;

import javax.faces.model.SelectItem;

/**
 * Hilfsklassen zur Umformung von Listen eines bestimmten Objekttyps in HTML-SelectItems
 * 
 * @param <T>
 */
public abstract class SelectItemHelper<T> {

	/**
	 * Formt alle Objekte einer Liste in SelectItems um und gibt diese dann wieder als Liste zurück.
	 * @param elements
	 * @return Elemente als SelectItems
	 */
	abstract List<SelectItem> asSelectItemList(List<T> elements);
}
