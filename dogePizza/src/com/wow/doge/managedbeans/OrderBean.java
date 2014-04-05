package com.wow.doge.managedbeans;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.wow.doge.domain.Order;
import com.wow.doge.domain.OrderPosition;
import com.wow.doge.services.OrderService;

@ManagedBean
@RequestScoped
public class OrderBean {

	@ManagedProperty("#{param.orderId}")
	private int orderId;
	private Order order;

	public OrderBean() {
		order = new Order();
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
		OrderService service = new OrderService();
		order = service.get(orderId);
	}

	/**
	 * umgekehrt sortiert nach Bestelldatum
	 * @return
	 */
	public List<Order> getAllOrders() {
		OrderService service = new OrderService();
		List<Order> list = service.getListWithComparator(Order.getReverseOrderDateComparator());
		return list;
	}

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
