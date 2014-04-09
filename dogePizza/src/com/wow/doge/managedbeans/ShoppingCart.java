package com.wow.doge.managedbeans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

import com.wow.doge.domain.Address;
import com.wow.doge.domain.Meal;
import com.wow.doge.domain.Order;
import com.wow.doge.domain.OrderPosition;
import com.wow.doge.domain.User;
import com.wow.doge.services.MealService;
import com.wow.doge.services.OrderService;

/**
 * Einkaufswagen der Anwendung. Es handelt sich hierbei um kein persistentes Objekt, sondern ein rein auf die Session beschränktes Objekt.
 * Wird die Session beendet, wird auch der Einkaufswagen geleert.<p>
 * Ein Einkaufswagen beeinhaltet Auftragspositionen, die zum Zeitpunkt der Verwendung noch nicht in der Datenbank gespeichert sind. Sie besitzen
 * daher auch noch keine ID.  
 */
@ManagedBean
@SessionScoped
public class ShoppingCart {

	public static final double MINIMUM_ORDER_VALUE = 10.0;
	public static final double SHIPPING_COST = 3.0;

	private static final Logger logger = Logger.getLogger(ShoppingCart.class);

	private List<OrderPosition> orderPositions;
	private boolean useUserAddress = true;
	private Address address;
	private String remark;
	/** YYYY-MM-dd */
	private String date;
	/** HH:mi:ss */
	private String time;

	private User user;

	private String errorText;

	public ShoppingCart() {
		logger.info("user: " + user);
		orderPositions = new LinkedList<>();
		address = new Address();
	}

	public String addOrderPosition() {
		logger.info("Fuege neue Position hinzu");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Integer mealId = Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("mealId"));
		logger.info("MealId:" + mealId);
		Double price = Double.parseDouble(facesContext.getExternalContext().getRequestParameterMap().get("price"));
		logger.info("price:" + price);
		OrderPosition position = new OrderPosition();
		MealService mealService = new MealService();
		Meal meal = mealService.get(mealId);
		position.setMeal(meal);
		position.setPrice(price);
		orderPositions.add(position);
		errorText = "";
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
		errorText = "";
		return "";
	}

	public List<OrderPosition> getOrderPositions() {
		return orderPositions;
	}

	public int getPositionCount() {
		return orderPositions.size();
	}

	public boolean isUseUserAddress() {
		return useUserAddress;
	}

	public void setUseUserAddress(boolean useUserAddress) {
		this.useUserAddress = useUserAddress;
	}

	public Integer getZipCode() {
		return address.getZipCode();
	}

	public void setZipCode(Integer zipCode) {
		this.address.setZipCode(zipCode);
	}

	public void setCity(String city) {
		this.address.setCity(city);
	}

	public String getCity() {
		return address.getCity();
	}

	public void setStreetName(String streetName) {
		this.address.setStreetName(streetName);
	}

	public String getStreetName() {
		return address.getStreetName();
	}

	public void setNumber(Integer number) {
		this.address.setNumber(number);
	}

	public Integer getNumber() {
		return address.getNumber();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getMinimumOrderValue() {
		return MINIMUM_ORDER_VALUE;
	}

	public double getShippingCost() {
		return SHIPPING_COST;
	}

	public double getOverallPrice() {
		double value = 0;
		for (OrderPosition nextPosition : orderPositions) {
			value += nextPosition.getPrice();
		}

		return value;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getUseUserAddressStyle() {
		return useUserAddress ? "display: none;" : "";
	}

	public void changeUseUserAddress(ValueChangeEvent vce) {
		logger.info("Wert wurde geändert: " + useUserAddress);
	}

	public void clearShoppingCart() {
		logger.info("Leere Einkaufswagen, aktuelle Anzahl an Positionen: " + orderPositions.size());
		orderPositions = new LinkedList<>();
		remark = "";
		address = new Address();
	}

	public String showShoppingCart() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
		date = dateFormat.format(new Date().getTime());
		time = timeFormat.format(new Date().getTime() + (1000 * 60 * 60));
		return "/resources/javaee/shoppingCart/shoppingCartList.jsf";
	}

	@Override
	public String toString() {
		return orderPositions.size() + " Produkte" + getOverallPrice() + " €";
	}

	public String getText() {
		return toString();
	}

	public boolean getDefaultAddressSelectable() {
		return user != null;
	}

	/**
	 * ein Mindestbestellwert muss erfüllt sein, sonst wird eine Fehlermeldung angezeigt
	 * @return den Link zur Beställbestätigung
	 */
	public String order(User user) {
		if (getOverallPrice() < MINIMUM_ORDER_VALUE) {
			errorText = "Der Mindestbestellwert wurde noch nicht erreicht!";
			return "";
		} else {
			this.user = user;
			if (user == null) {
				useUserAddress = false;
			} else {
				useUserAddress = true;
			}
			errorText = "";
			logger.info("Bestellung mit User " + user);
			return "orderAddressConfirmation.jsf";
		}
	}

	/**
	 * @return die ausgewählten Felder Lieferdatum und -zeit als long
	 */
	public long getSelectedDateTimeInMillis() {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
			Date d = dateFormat.parse(date + " " + time);
			return d.getTime();
		} catch (ParseException e) {
			return new Date().getTime() + (1000 * 60 * 60);
		}
	}

	/**
	 * Schließt den Bestellvorgang ab. Hier wird unterschieden zwischen normaler Adresse und alternativer Adresse, 
	 * sowie zwischen angemeldetem Nutzer und nicht angemeldetem Nutzer.
	 * @param user der angemeldete Benutzer
	 */
	public void completeOrder(User user) {
		OrderService orderService = new OrderService();
		Order order = new Order();
		if (user != null) {
			order.setUser(user);
			if (useUserAddress) {
				logger.info("Bestellung mit normaler default-Adresse!");
				order.setAddress(user.getDefaultAddress());
			} else {
				logger.info("Bestellung mit alternativer Adresse!");
				order.setAddress(address);
			}
		} else {
			logger.info("Bestellung mit alternativer Adresse!");
			order.setAddress(address);
		}
		for (OrderPosition position : orderPositions) {
			order.addPosition(position);
		}
		order.setDate(getSelectedDateTimeInMillis());
		order.setRemark(remark);
		order.setOrderDate(new Date().getTime());
		orderService.merge(order);
		clearShoppingCart();
	}
}
