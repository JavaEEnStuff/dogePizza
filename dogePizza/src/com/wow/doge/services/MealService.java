package com.wow.doge.services;

import com.wow.doge.domain.Meal;

public class MealService extends AbstractService<Meal>{

	@Override
	protected Class<Meal> getHibernateClass() {
		return Meal.class;
	}
}
