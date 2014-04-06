package com.wow.doge.services;

import com.wow.doge.domain.Category;

public class CategoryService extends AbstractService<Category> {

	@Override
	protected Class<Category> getHibernateClass() {
		return Category.class;
	}
}
