package com.wow.doge.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.wow.doge.domain.Order;
import com.wow.doge.services.OrderService;

@ManagedBean
@RequestScoped
public class OrderBean {

	public List<Order> getAllOrders() {
		OrderService service = new OrderService();
		List<Order> list = service.getListWithComparator(Order.getReverseOrderDateComparator());
		return list;
	}
}
