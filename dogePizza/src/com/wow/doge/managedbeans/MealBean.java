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
import com.wow.doge.helper.SizeSelectItemHelper;
import com.wow.doge.services.IngredientService;
import com.wow.doge.services.MealService;
import com.wow.doge.services.SizeService;

@ManagedBean
@RequestScoped
public class MealBean {

	private static final Logger logger = Logger.getLogger(MealBean.class);

	private Meal meal;
	// die ausgewählten Ids (rechte Seite, aber nur die, die selektiert wurden)
	private List<String> selectedIngredientIds;
	private List<String> selectedSizeIds;

	@ManagedProperty("#{param.mealId}")
	private int mealId;

	public MealBean() {
		meal = new Meal();
		selectedIngredientIds = new LinkedList<>();
		selectedSizeIds = new LinkedList<>();
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

	public List<SelectItem> getAllIngredients() {
		IngredientService service = new IngredientService();
		IngredientSelectItemHelper helper = new IngredientSelectItemHelper();
		List<SelectItem> asSelectItemList = helper.asSelectItemList(service.getList());
		return asSelectItemList;
	}

	public List<SelectItem> getAllSizes() {
		SizeService service = new SizeService();
		SizeSelectItemHelper helper = new SizeSelectItemHelper();
		List<SelectItem> asSelectItemList = helper.asSelectItemList(service.getList());
		return asSelectItemList;
	}

	public List<Meal> getAllMeals() {
		MealService service = new MealService();
		List<Meal> list = service.getListWithComparator(Meal.getMealNameComparator());
		return list;
	}

	public int getMealId() {
		return mealId;
	}

	public void setMealId(int mealId) {
		this.mealId = mealId;
		if (mealId != 0) {
			MealService service = new MealService();
			this.meal = service.get(mealId);
			logger.info("MealID: " + mealId + ", Meal: " + meal);
			this.selectedIngredientIds = new LinkedList<>();
			for (Ingredient nextIngredient : meal.getIngredients()) {
				selectedIngredientIds.add(nextIngredient.getId() + "");
			}
			
			this.selectedSizeIds = new LinkedList<>();
			for(Size nextSize: meal.getPossibleSizes()){
				selectedSizeIds.add(nextSize.getId()+"");
			}
		}
	}

	// FUNKTIONEN

	public List<String> getSelectedIngredientIds() {
		return selectedIngredientIds;
	}

	public void setSelectedIngredientIds(List<String> selectedIngredientIds) {
		this.selectedIngredientIds = selectedIngredientIds;
	}
	
	public List<String> getSelectedSizeIds() {
		return selectedSizeIds;
	}

	public void setSelectedSizeIds(List<String> selectedSizeIds) {
		this.selectedSizeIds = selectedSizeIds;
	}

	public String deleteMeal() {
		logger.info("Versuche Meal zu löschen: " + mealId);
		MealService service = new MealService();
		Meal mealToDelete = service.get(mealId);
		logger.info("Vollständiges Objekt: " + mealToDelete);
		service.delete(mealToDelete);
		return "";
	}

	public String saveMeal() {
		List<Integer> idsAsInteger = new LinkedList<>();
		for (String nextId : selectedIngredientIds) {
			idsAsInteger.add(Integer.valueOf(nextId));
		}
		MealService service = new MealService();
		IngredientService ingredientService = new IngredientService();
		List<Ingredient> ingredients = ingredientService.whereIdsIn(idsAsInteger);
		meal.setIngredients(ingredients);

		List<Integer> sizeIdsAsInteger = new LinkedList<>();
		for (String nextId : selectedSizeIds) {
			sizeIdsAsInteger.add(Integer.valueOf(nextId));
		}
		SizeService sizeService = new SizeService();
		List<Size> sizes = sizeService.whereIdsIn(sizeIdsAsInteger);
		meal.setPossibleSizes(sizes);
		service.saveOrUpdate(meal);
		return mealList();
	}

	public String showMeal() {
		MealService service = new MealService();
		meal = service.get(mealId);
		return "showMeal.jsf";
	}

	public String changeMeal() {
		MealService service = new MealService();
		meal = service.get(mealId);
		return "changeMeal.jsf";
	}

	public String createMeal() {
		return "createMeal.jsf";
	}

	public String mealList() {
		return "mealList.jsf";
	}

}
