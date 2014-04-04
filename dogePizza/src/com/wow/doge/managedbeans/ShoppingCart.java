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
import com.wow.doge.services.OrderPositionService;
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

	private static final Logger logger = Logger.getLogger(ShoppingCart.class);

	private List<OrderPosition> orderPositions;
	private boolean useUserAddress = true;
	private Address address;
	private String remark;
	/** YYYY-MM-dd */
	private String date;
	/** HH:mi:ss */
	private String time;

	public ShoppingCart() {
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

	public double getOverallPrice() {
		double value = 0;
		for (OrderPosition nextPosition : orderPositions) {
			value += nextPosition.getPrice();
		}

		return value;
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

	public String order() {
		logger.info("Bestellung!");
		return "orderAddressConfirmation.jsf";
	}
	
	public long getSelectedDateTimeInMillis()  {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
			Date d = dateFormat.parse(date + " " + time);
			return d.getTime();
		} catch (ParseException e) {
			return new Date().getTime()+(1000*60*60);
		}
	}

	public void completeOrder(User user) {
		OrderPositionService orderPositionService = new OrderPositionService();
		OrderService orderService = new OrderService();
		logger.info("Bestellung abschicken!");
		if (user != null && useUserAddress) {
			Order order = new Order();
			order.setAddress(user.getDefaultAddress());
			order.setDate(getSelectedDateTimeInMillis());
			for (OrderPosition position : orderPositions) {
				orderPositionService.saveOrUpdate(position);
				order.addPosition(position);
			}
			order.setRemark(remark);
			order.setOrderDate(new Date().getTime());
			orderService.saveOrUpdate(order);
			clearShoppingCart();
		} else {
			// TODO: was da los?
		}
	}
}
