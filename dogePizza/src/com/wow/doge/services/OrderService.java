package com.wow.doge.services;

import com.wow.doge.domain.Order;

public class OrderService extends AbstractService<Order>{

	@Override
	protected Class<Order> getHibernateClass() {
		return Order.class;
	}

}
