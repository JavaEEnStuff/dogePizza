package com.wow.doge.helper;

import java.util.List;

import com.wow.doge.domain.Meal;
import com.wow.doge.services.MealService;
import com.wow.doge.services.SelectionHelper;

public class EvaluationHelper {

	private static final int MAX_COUNT = 10;

	public List<Meal> HighestPriceMeals() {
		MealService service = new MealService();
		SelectionHelper<Meal> mealSelection = new SelectionHelper<>();
		mealSelection.setComparator(Meal.getReverseMealPriceComparator());
		List<Meal> highestPriceMeals = service.getList(mealSelection);
		if(highestPriceMeals.size()>=10){
			highestPriceMeals.subList(0, 9);
		}
		return highestPriceMeals;
	}
	
	public List<Meal> getLowestPriceMeals(List<Meal> mealsToCheck) {
		MealService service = new MealService();
		SelectionHelper<Meal> mealSelection = new SelectionHelper<>();
		mealSelection.setComparator(Meal.getMealPriceComparator());
		List<Meal> lowestPriceMeals = service.getList(mealSelection);
		if(lowestPriceMeals.size()>=MAX_COUNT){
			lowestPriceMeals.subList(0, 9);
		}
		return lowestPriceMeals;
	}
}
