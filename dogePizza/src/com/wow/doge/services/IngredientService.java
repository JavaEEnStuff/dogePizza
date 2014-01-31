package com.wow.doge.services;

import com.wow.doge.domain.Ingredient;

public class IngredientService extends AbstractService<Ingredient> {

	@Override
	protected Class<Ingredient> getHibernateClass() {
		return Ingredient.class;
	}

}
