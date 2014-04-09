package com.wow.doge.managedbeans;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import com.wow.doge.domain.Order;
import com.wow.doge.domain.OrderPosition;
import com.wow.doge.domain.User;
import com.wow.doge.services.OrderService;
import com.wow.doge.services.SelectionHelper;

@ManagedBean
@RequestScoped
public class OrderBean {

	private static final Logger logger = Logger.getLogger(OrderBean.class);
	
	@ManagedProperty("#{param.orderId}")
	private int orderId;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
		OrderService service = new OrderService();
		order = service.get(orderId);
	}
	
	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean sessionBean;

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	

	private Order order;

	public OrderBean() {
		order = new Order();
	}

	/**
	 * @return alle Aufträge des Benutzers, umgekehrt sortiert nach Bestelldatum
	 */
	public List<Order> getAllOrders() {
		User user = sessionBean.getLoggedInUser();
		OrderService service = new OrderService();
		SelectionHelper<Order> selectionHelper = new SelectionHelper<Order>();
		selectionHelper.setComparator(Order.getReverseOrderDateComparator());
		// wenn kein admin, dann nur die eigenen Bestellungen ansehen
		if(!user.isAdmin()){
			logger.info("Setze EQ für User :"+user);
			selectionHelper.addCriterion(Restrictions.eq("user", user));
		}
		List<Order> list = service.getList(selectionHelper);
		return list;
	}

	/**
	 * @return alle Positionen zum Auftrag
	 */
	public List<OrderPosition> getOrderPositionForOrder() {
		if (order != null) {
			Set<OrderPosition> positions = order.getPositions();
			List<OrderPosition> positionsAsList = new LinkedList<OrderPosition>();
			for (OrderPosition nextPostion : positions) {
				positionsAsList.add(nextPostion);
			}
			return positionsAsList;
		} else {
			return new LinkedList<>();
		}
	}

	public String getOrderDate() {
		if (order != null) {
			return order.getFormattedOrderDate();
		} else {
			return "";
		}
	}

	public Double getOrderPrice() {
		return order.getPrice();
	}

	public String getOrderRemark() {
		return order.getRemark();
	}

	public String getDeliveryTimestamp() {
		return order.getDeliveryDate() + " " + order.getDeliveryTime();
	}

	public String show() {
		return "showOrderPositions.jsf";
	}
	
}
