package com.wow.doge.services;

import com.wow.doge.domain.Order;
import com.wow.doge.domain.OrderPosition;

public class OrderService extends AbstractService<Order>{

	@Override
	protected Class<Order> getHibernateClass() {
		return Order.class;
	}
	
	/**
	 * Speichert zusätzlich noch die OrderPositionen und die Adresse weg
	 */
	@Override
	public void merge(Order t) {
		OrderPositionService opService = new OrderPositionService();
		for(OrderPosition position:t.getPositions()){
			opService.saveOrUpdate(position);
		}
		AddressService addressService = new AddressService();
		addressService.saveOrUpdate(t.getAddress());
		super.merge(t);
	}

}
