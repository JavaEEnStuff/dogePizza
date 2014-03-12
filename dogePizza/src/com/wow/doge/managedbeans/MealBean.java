package com.wow.doge.managedbeans;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.wow.doge.domain.Ingredient;
import com.wow.doge.domain.Meal;
import com.wow.doge.domain.Size;
import com.wow.doge.helper.IngredientSelectItemHelper;
import com.wow.doge.services.IngredientService;
import com.wow.doge.services.MealService;
import com.wow.doge.services.SizeService;

@ManagedBean
@RequestScoped
public class MealBean {

	private static final Logger logger = Logger.getLogger(MealBean.class);

	private Meal meal;
	private List<String> ingredientIds;

	@ManagedProperty("#{param.mealId}")
	private int mealId;

	public MealBean() {
		meal = new Meal();
		ingredientIds = new LinkedList<String>();
	}

	public void setId(int id) {
		meal.setId(id);
	}

	public int getId() {
		return meal.getId();
	}

	public String getName() {
		return meal.getName();
	}

	public void setName(String name) {
		meal.setName(name);
	}

	public double getRawPrice() {
		return meal.getRawPrice();
	}

	public void setRawPrice(double rawPrice) {
		meal.setRawPrice(rawPrice);
	}

	public boolean isVegeterian() {
		return meal.isVegeterian();
	}

	public void setVegeterian(boolean vegeterian) {
		meal.setVegeterian(vegeterian);
	}

	public String getDescription() {
		return meal.getDescription();
	}

	public void setDescription(String description) {
		meal.setDescription(description);
	}

	public byte[] getImage() {
		return meal.getImage();
	}

	public void setImage(byte[] image) {
		meal.setImage(image);
	}

	public List<Size> getPossibleSizes() {
		return meal.getPossibleSizes();
	}

	public void setPossibleSizes(List<Size> possibleSizes) {
		meal.setPossibleSizes(possibleSizes);
	}

	public List<Ingredient> getIngredients() {
		return meal.getIngredients();
	}

	public void setIngredients(List<Ingredient> ingredients) {
		meal.setIngredients(ingredients);
	}

	public List<SelectItem> getAllIngredients() {
		IngredientService service = new IngredientService();
		IngredientSelectItemHelper helper = new IngredientSelectItemHelper();
		return helper.asSelectItemList(service.getList());
	}

	public List<Size> getAllSizes() {
		SizeService service = new SizeService();
		return service.getList();
	}

	public List<Meal> getAllMeals() {
		MealService service = new MealService();
		return service.getList();
	}

	public int getMealId() {
		return mealId;
	}

	public void setMealId(int mealId) {
		this.mealId = mealId;
	}

	public List<String> getIngredientIds() {
		return ingredientIds;
	}

	public List<SelectItem> getIngredientsAsSelectItems() {
		IngredientSelectItemHelper helper = new IngredientSelectItemHelper();
		return helper.asSelectItemList(meal.getIngredients());
	}

	public void setIngredientIds(List<String> ingredientIds) {
		this.ingredientIds = ingredientIds;
		IngredientService service = new IngredientService();
		List<Ingredient> ingredients = new LinkedList<>();
		for (String nextId : ingredientIds) {
			ingredients.add(service.get(Integer.parseInt(nextId)));
		}
		meal.setIngredients(ingredients);
	}

	// FUNKTIONEN

	public String deleteMeal() {
		logger.info("Versuche Meal zu löschen: " + mealId);
		MealService service = new MealService();
		Meal mealToDelete = service.get(mealId);
		logger.info("Vollständiges Objekt: " + mealToDelete);
		service.delete(mealToDelete);
		return "";
	}

	public String saveMeal() {
		MealService service = new MealService();
		service.saveOrUpdate(meal);
		return mealList();
	}

	public String showMeal() {
		MealService service = new MealService();
		meal = service.get(mealId);
		return "showMeal.xhtml";
	}

	public String changeMeal() {
		MealService service = new MealService();
		meal = service.get(mealId);
		return "changeMeal.xhtml";
	}

	public String createMeal() {
		return "createMeal.xhtml";
	}

	public String mealList() {
		return "mealList.xhtml";
	}

}
