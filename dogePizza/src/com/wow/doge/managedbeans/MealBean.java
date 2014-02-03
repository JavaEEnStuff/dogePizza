package com.wow.doge.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;

import com.wow.doge.domain.Ingredient;
import com.wow.doge.domain.Meal;
import com.wow.doge.domain.Size;
import com.wow.doge.services.IngredientService;
import com.wow.doge.services.SizeService;

@ManagedBean
public class MealBean {
	private Meal meal;

	public MealBean() {
		meal = new Meal();
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

	public List<Ingredient> getAllIngredients() {
		IngredientService service = new IngredientService();
		return service.getList();
	}
	
	public List<Size> getAllSizes() {
		SizeService service = new SizeService();
		return service.getList();
	}

}
