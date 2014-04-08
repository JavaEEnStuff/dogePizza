package com.wow.doge.startup;

import com.wow.doge.domain.Category;
import com.wow.doge.domain.Ingredient;
import com.wow.doge.domain.Meal;
import com.wow.doge.services.CategoryService;
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
		
		Ingredient fungi = new Ingredient();
		fungi.setName("Pilze");
		service.saveOrUpdate(fungi);
		
		CategoryService categoryService = new CategoryService();
		Category nudeln = new Category();
		nudeln.setName("Nudeln");
		categoryService.saveOrUpdate(nudeln);
		
		Category salat = new Category();
		salat.setName("Salat");
		categoryService.saveOrUpdate(salat);
		
		Category pizza = new Category();
		pizza.setName("Pizza");
		categoryService.saveOrUpdate(pizza);
		
		MealService mealService = new MealService();
		Meal pizzaNormale = new Meal();
		pizzaNormale.setName("Pizza Normale");
		pizzaNormale.setFirstPrice(5d);
		pizzaNormale.setSecondPrice(6d);
		pizzaNormale.setThirdPrice(7d);
		pizzaNormale.setVegeterian(false);
		pizzaNormale.addIngredient(cheese);
		pizzaNormale.addIngredient(salami);
		pizzaNormale.setCategory(pizza);
		mealService.saveOrUpdate(pizzaNormale);
		
		Meal pizzaMargherita = new Meal();
		pizzaMargherita.setName("Pizza Margherita");
		pizzaMargherita.setFirstPrice(4d);
		pizzaMargherita.setSecondPrice(5d);
		pizzaMargherita.setThirdPrice(6d);
		pizzaMargherita.setVegeterian(true);
		pizzaMargherita.addIngredient(cheese);
		pizzaMargherita.setCategory(pizza);
		mealService.saveOrUpdate(pizzaMargherita);
		
	}

}
