package com.wow.doge.managedbeans;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.wow.doge.domain.OrderPosition;

/**
 * Einkaufswagen der Anwendung. Es handelt sich hierbei um kein persistentes Objekt, sondern ein rein auf die Session beschränktes Objekt.
 * Wird die Session beendet, wird auch der Einkaufswagen geleert.<p>
 * Ein Einkaufswagen beeinhaltet Auftragspositionen, die zum Zeitpunkt der Verwendung noch nicht in der Datenbank gespeichert sind. Sie besitzen
 * daher auch noch keine ID.  
 */
@ManagedBean
@SessionScoped
public class ShoppingCart {
	
	private static final Logger logger = Logger.getLogger(ShoppingCart.class);

	private List<OrderPosition> orderPositions;

	public ShoppingCart() {
		orderPositions = new LinkedList<>();
	}

	public void addOrderPosition(OrderPosition position) {
		logger.info("neuer Eintrag im Einkaufswagen: "+position);
		orderPositions.add(position);
	}

	/**
	 * Anpassung des Index durch Veringerum um 1 (da 0-basiert)
	 * @param position
	 */
	public void removeOrderPosition(int position) {
		logger.info("Element aus dem Einkaufswagen entfernt: "+orderPositions.get(position));
		orderPositions.remove(position - 1);
	}
	
	public List<OrderPosition> getOrderPositions(){
		return orderPositions;
	}

	public int getPositionCount() {
		return orderPositions.size();
	}

	public double getOverallPrice() {
		double value = 0;
		for (OrderPosition nextPosition : orderPositions) {
			value += nextPosition.getPrice();
		}

		return value;
	}
	
	public void clearShoppingCart(){
		logger.info("Leere Einkaufswagen, aktuelle Anzahl an Positionen: "+orderPositions.size());
		orderPositions = new LinkedList<>();
	}
}
