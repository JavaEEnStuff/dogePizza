package com.wow.doge.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import com.wow.doge.domain.Ingredient;
import com.wow.doge.services.IngredientService;

@ManagedBean
@RequestScoped
public class ShowIngredientBean {
	
	private static final Logger logger = Logger.getLogger(ShowIngredientBean.class);

	@ManagedProperty(value="#{param.ingredientToShow}")
	private Ingredient ingredient;

	public ShowIngredientBean() {
		ingredient = new Ingredient();
	}

	public int getId() {
		return ingredient.getId();
	}

	public void setId(int id) {
		ingredient.setId(id);
	}

	public String getName() {
		return ingredient.getName();
	}

	public void setName(String name) {
		ingredient.setName(name);
	}

	@Override
	public String toString() {
		return ingredient.toString();
	}
	
	public List<Ingredient> getAllIngredients(){
		IngredientService service = new IngredientService();
		return service.getList();
	}
	
	public String save() {
		logger.info("Versuche Ingredient zu speichern...");
		IngredientService service = new IngredientService();
		service.saveOrUpdate(ingredient);
		
		return "ingredientList.xhtml";
	}
	
	public String showIngredient(){
		return "showIngredient.xhtml";
	}
	
	public String changeIngredient(){
		return "changeIngredient.xhtml";
	}
}
