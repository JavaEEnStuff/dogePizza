package com.wow.doge.services;

import com.wow.doge.domain.Size;

public class SizeService extends AbstractService<Size> {

	@Override
	protected Class<Size> getHibernateClass() {
		return Size.class;
	}

}
