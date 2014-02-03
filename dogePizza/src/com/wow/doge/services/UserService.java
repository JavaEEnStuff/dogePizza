package com.wow.doge.services;

import com.wow.doge.domain.User;

public class UserService extends AbstractService<User> {

	@Override
	protected Class<User> getHibernateClass() {
		return User.class;
	}

	@Override
	public void saveOrUpdate(User t) {
		AddressService addressService = new AddressService();
		addressService.saveOrUpdate(t.getDefaultAddress());
		super.saveOrUpdate(t);
	}
}
