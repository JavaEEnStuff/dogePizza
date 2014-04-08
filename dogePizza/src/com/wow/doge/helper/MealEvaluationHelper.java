package com.wow.doge.helper;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import com.wow.doge.domain.Category;
import com.wow.doge.domain.Meal;
import com.wow.doge.domain.User;
import com.wow.doge.services.CategoryService;
import com.wow.doge.services.MealService;
import com.wow.doge.services.SelectionHelper;
import com.wow.doge.services.SelectionHelper.Alias;

public class MealEvaluationHelper {

	private static final Logger logger = Logger.getLogger(MealEvaluationHelper.class);
	private static final int MAX_COUNT = 10;

	public List<Meal> highestPriceMeals() {
		MealService service = new MealService();
		SelectionHelper<Meal> mealSelection = new SelectionHelper<>();
		mealSelection.setComparator(MealSorting.getReverseMealPriceComparator());
		List<Meal> highestPriceMeals = service.getList(mealSelection);
		if (highestPriceMeals.size() >= 10) {
			highestPriceMeals.subList(0, 9);
		}
		return highestPriceMeals;
	}

	public List<Meal> getLowestPriceMeals(List<Meal> mealsToCheck) {
		MealService service = new MealService();
		SelectionHelper<Meal> mealSelection = new SelectionHelper<>();
		mealSelection.setComparator(MealSorting.getMealPriceComparator());
		List<Meal> lowestPriceMeals = service.getList(mealSelection);
		if (lowestPriceMeals.size() >= MAX_COUNT) {
			lowestPriceMeals.subList(0, 9);
		}
		return lowestPriceMeals;
	}

	public List<Meal> getMealsInPriceRangeAndCategory(Double fromPrice, Double toPrice, Integer categoryId, Integer sort, boolean onlyFavorites, User user) {
		logger.info("Sort: " + sort);
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
		if (categoryId != null && categoryId != 0) {
			CategoryService categoryService = new CategoryService();
			Category category = categoryService.get(categoryId);
			mealSelection.addCriterion(Restrictions.eq("category", category));
		}
		if (onlyFavorites) {
			mealSelection.addAlias(new Alias("favoredBy", "f"));
			mealSelection.addCriterion(Restrictions.eq("f.id", user.getId()));
		}

		switch (sort) {
		case MealSorting.NAME_COMP:
			mealSelection.setComparator(MealSorting.getMealNameComparator());
			break;
		case MealSorting.PRICE_COMP:
			mealSelection.setComparator(MealSorting.getMealPriceComparator());
			break;
		case MealSorting.REV_PRICE_COMP:
			mealSelection.setComparator(MealSorting.getReverseMealPriceComparator());
			break;
		case MealSorting.REV_ORDER_COUNT_COMP:
			mealSelection.setComparator(MealSorting.getReverseOrderCountComparator());
			break;
		default:
			mealSelection.setComparator(MealSorting.getMealNameComparator());
			break;
		}

		return service.getList(mealSelection);
	}
}
