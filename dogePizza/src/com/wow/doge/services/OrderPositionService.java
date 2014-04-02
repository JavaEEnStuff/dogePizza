package com.wow.doge.services;

import com.wow.doge.domain.OrderPosition;

public class OrderPositionService extends AbstractService<OrderPosition>{

	@Override
	protected Class<OrderPosition> getHibernateClass() {
		return OrderPosition.class;
	}

}
