package com.wow.doge.helper;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import com.wow.doge.domain.Category;
import com.wow.doge.domain.Meal;
import com.wow.doge.services.CategoryService;
import com.wow.doge.services.MealService;
import com.wow.doge.services.SelectionHelper;

public class MealEvaluationHelper {

	private static final int MAX_COUNT = 10;

	public List<Meal> highestPriceMeals() {
		MealService service = new MealService();
		SelectionHelper<Meal> mealSelection = new SelectionHelper<>();
		mealSelection.setComparator(Meal.getReverseMealPriceComparator());
		List<Meal> highestPriceMeals = service.getList(mealSelection);
		if (highestPriceMeals.size() >= 10) {
			highestPriceMeals.subList(0, 9);
		}
		return highestPriceMeals;
	}

	public List<Meal> getLowestPriceMeals(List<Meal> mealsToCheck) {
		MealService service = new MealService();
		SelectionHelper<Meal> mealSelection = new SelectionHelper<>();
		mealSelection.setComparator(Meal.getMealPriceComparator());
		List<Meal> lowestPriceMeals = service.getList(mealSelection);
		if (lowestPriceMeals.size() >= MAX_COUNT) {
			lowestPriceMeals.subList(0, 9);
		}
		return lowestPriceMeals;
	}

	public List<Meal> getMealsInPriceRangeAndCategory(Double fromPrice, Double toPrice, Integer categoryId) {
		MealService service = new MealService();
		SelectionHelper<Meal> mealSelection = new SelectionHelper<>();
		if (fromPrice != null) {
			SimpleExpression firstPrice = Restrictions.ge("firstPrice", fromPrice);
			SimpleExpression secondPrice = Restrictions.ge("secondPrice", fromPrice);
			SimpleExpression thirdPrice = Restrictions.ge("thirdPrice", fromPrice);
			mealSelection.addCriterion(Restrictions.or(firstPrice, Restrictions.or(secondPrice, thirdPrice)));
		}
		if (toPrice != null) {
			SimpleExpression firstPrice = Restrictions.le("firstPrice", toPrice);
			SimpleExpression secondPrice = Restrictions.le("secondPrice", toPrice);
			SimpleExpression thirdPrice = Restrictions.le("thirdPrice", toPrice);
			mealSelection.addCriterion(Restrictions.or(firstPrice, Restrictions.or(secondPrice, thirdPrice)));
		}
		if(categoryId!=0){
			CategoryService categoryService = new CategoryService();
			Category category = categoryService.get(categoryId);
			mealSelection.addCriterion(Restrictions.eq("category", category));
		}
		mealSelection.setComparator(Meal.getMealNameComparator());

		return service.getList(mealSelection);
	}
}
