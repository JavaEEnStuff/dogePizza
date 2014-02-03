package com.wow.doge.services;

import com.wow.doge.domain.Address;

public class AddressService extends AbstractService<Address>{

	@Override
	protected Class<Address> getHibernateClass() {
		return Address.class;
	}

}
