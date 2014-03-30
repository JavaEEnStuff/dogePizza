package com.wow.doge.startup;

import com.wow.doge.domain.Ingredient;
import com.wow.doge.domain.Meal;
import com.wow.doge.services.IngredientService;
import com.wow.doge.services.MealService;

/**
 * meal haengt direkt von ingredient ab, daher wird hier beides zusammen generiert 
 */
public class IngredientMealStartupCreator implements StartupCreator {

	@Override
	public void create() {
		IngredientService service = new IngredientService();
		Ingredient salami = new Ingredient();
		salami.setName("Salami");
		service.saveOrUpdate(salami);
		
		Ingredient cheese = new Ingredient();
		cheese.setName("Käse");
		service.saveOrUpdate(cheese);
		
		
		MealService mealService = new MealService();
		Meal pizzaNormale = new Meal();
		pizzaNormale.setName("Pizza Normale");
		pizzaNormale.setFirstPrice(5d);
		pizzaNormale.setSecondPrice(6d);
		pizzaNormale.setThirdPrice(7d);
		pizzaNormale.setVegeterian(false);
		pizzaNormale.addIngredient(cheese);
		pizzaNormale.addIngredient(salami);
		mealService.saveOrUpdate(pizzaNormale);
		
	}

}
