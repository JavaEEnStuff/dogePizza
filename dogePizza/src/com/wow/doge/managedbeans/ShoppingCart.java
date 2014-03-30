package com.wow.doge.managedbeans;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.wow.doge.domain.Meal;
import com.wow.doge.domain.OrderPosition;
import com.wow.doge.services.MealService;

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
	
	public String addOrderPosition(){
		logger.info("Fuege neue Position hinzu");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Integer mealId =Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("mealId"));
		logger.info("MealId:"+mealId);
		Double price = Double.parseDouble(facesContext.getExternalContext().getRequestParameterMap().get("price"));
		logger.info("price:"+price);
		OrderPosition position = new OrderPosition();
		MealService mealService = new MealService();
		Meal meal = mealService.get(mealId);
		position.setMeal(meal);
		position.setPrice(price);
		orderPositions.add(position);
		return "";
	}

	/**
	 * Anpassung des Index durch Veringerum um 1 (da 0-basiert)
	 * @param position
	 */
	public String removeOrderPosition() {
		logger.info("Loesche Position");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Integer mealId = Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("mealId"));
		logger.info("MealId:" + mealId);
		Double price = Double.parseDouble(facesContext.getExternalContext().getRequestParameterMap().get("price"));
		logger.info("price:" + price);
		for (OrderPosition orderPosition : orderPositions) {
			if (orderPosition.getMeal().getId().equals(mealId) && orderPosition.getPrice().equals(price)) {
				orderPositions.remove(orderPosition);
				break;
			}
		}
		return "";
	}

	public List<OrderPosition> getOrderPositions() {
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

	public void clearShoppingCart() {
		logger.info("Leere Einkaufswagen, aktuelle Anzahl an Positionen: " + orderPositions.size());
		orderPositions = new LinkedList<>();
	}

	public String showShoppingCart() {
		return "/resources/javaee/shoppingCart/shoppingCartList.jsf";
	}

	@Override
	public String toString() {
		return orderPositions.size() + " Produkte" + getOverallPrice() + " €";
	}

	public String getText() {
		return toString();
	}
	
	public String order(){
		logger.info("Bestellung!");
		return "";
	}
}
