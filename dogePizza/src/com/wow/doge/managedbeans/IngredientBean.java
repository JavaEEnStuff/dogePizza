package com.wow.doge.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.wow.doge.domain.Ingredient;
import com.wow.doge.services.IngredientService;

@ManagedBean
@SessionScoped
public class IngredientBean {
	
	private static final Logger logger = Logger.getLogger(IngredientBean.class);

	private Ingredient ingredient;

	public IngredientBean() {
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
	
	public void save(ActionEvent event) {
		logger.info("Versuche Ingredient zu speichern...");
//		VorlesungService service = new VorlesungService();
//		if (vorlesung.getId() != null)
//			service.updateVorlesung(vorlesung);
//		else
//			service.saveVorlesung(vorlesung);
	}
}
