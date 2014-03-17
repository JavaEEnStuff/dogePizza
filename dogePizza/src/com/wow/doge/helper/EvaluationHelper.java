package com.wow.doge.helper;

import java.util.LinkedList;
import java.util.List;

import com.wow.doge.domain.Meal;
import com.wow.doge.services.MealService;

public class EvaluationHelper {

	int margin = 10;
	
	public List<Meal> HighestPriceMeals() {
		List<Meal> allMeals = new LinkedList<Meal>();
		MealService service = new MealService();
		allMeals = service.getList();
		List<Meal> highestPriceMeals = new LinkedList<Meal>();
		int mealCounter = 0;
		for (Meal currentMeal : allMeals) {
			if (mealCounter <= margin) {
				highestPriceMeals.add(currentMeal);
				mealCounter++;
			} else {
				Meal lowestPriceMeal = GetLowestPriceMeal(highestPriceMeals);
				if (currentMeal.getRawPrice() > lowestPriceMeal.getRawPrice()) {
					highestPriceMeals.remove(lowestPriceMeal);
					highestPriceMeals.add(currentMeal);
				}
			}
		}
		return highestPriceMeals;
	}
	
	private Meal GetLowestPriceMeal(List<Meal> mealsToCheck) {
		Meal lowestPriceMeal = mealsToCheck.get(0);
		double lowestPrice = lowestPriceMeal.getRawPrice();
		for (Meal currentMeal : mealsToCheck) {
			if (currentMeal.getRawPrice() < lowestPrice) {
				lowestPrice = currentMeal.getRawPrice();
				lowestPriceMeal = currentMeal;
			}
		}
		return lowestPriceMeal;
	}
	
	public List<Meal> LowestPriceMeals() {
		List<Meal> allMeals = new LinkedList<Meal>();
		MealService service = new MealService();
		allMeals = service.getList();
		List<Meal> lowestPriceMeals = new LinkedList<Meal>();
		int mealCounter = 0;
		for (Meal currentMeal : allMeals) {
			if (mealCounter <= margin) {
				lowestPriceMeals.add(currentMeal);
				mealCounter++;
			} else {
				Meal highestPriceMeal = GetHighestPriceMeal(lowestPriceMeals);
				if (currentMeal.getRawPrice() < highestPriceMeal.getRawPrice()) {
					lowestPriceMeals.remove(highestPriceMeal);
					lowestPriceMeals.add(currentMeal);
				}
			}
		}
		return lowestPriceMeals;
	}
	
	private Meal GetHighestPriceMeal(List<Meal> mealsToCheck) {
		Meal highestPriceMeal = mealsToCheck.get(0);
		double highestPrice = highestPriceMeal.getRawPrice();
		for (Meal currentMeal : mealsToCheck) {
			if (currentMeal.getRawPrice() > highestPrice) {
				highestPrice = currentMeal.getRawPrice();
				highestPriceMeal = currentMeal;
			}
		}
		return highestPriceMeal;
	}
}
