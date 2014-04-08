package com.wow.doge.managedbeans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import com.wow.doge.domain.Ingredient;
import com.wow.doge.services.IngredientService;
import com.wow.doge.services.SelectionHelper;

@ManagedBean
@RequestScoped
public class IngredientBean {

	private static final Logger logger = Logger.getLogger(IngredientBean.class);

	@ManagedProperty("#{param.ingredientId}")
	private int ingredientId;

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

	public int getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(int ingredientId) {
		this.ingredientId = ingredientId;
	}

	@Override
	public String toString() {
		return ingredient.toString();
	}

	// ============ Funktionen =============
	
	public List<Ingredient> getAllIngredients() {
		IngredientService service = new IngredientService();
		return service.getList();
	}

	public String save() {
		logger.info("Versuche Ingredient zu speichern... "+ingredient);
		IngredientService service = new IngredientService();
		service.saveOrUpdate(ingredient);

		return "ingredientList.xhtml";
	}

	public String showIngredient() {
		IngredientService service = new IngredientService();
		ingredient = service.get(ingredientId);
		return "showIngredient.xhtml";
	}

	public String changeIngredient() {
		IngredientService service = new IngredientService();
		ingredient = service.get(ingredientId);
		logger.info("Versuche Ingredient zu aendern: "+ingredient);
		return "changeIngredient.xhtml";
	}

	public String deleteIngredient() {
		IngredientService service = new IngredientService();
		Ingredient ingredientToDelete = service.get(ingredientId);
		service.delete(ingredientToDelete);
		return ingredientList();
	}

	public String createIngredient() {
		return "createIngredient.xhtml";
	}

	public String ingredientList() {
		return "ingredientList.jsf";
	}
	
	public String main(){
		return "/resources/javaee/main.xhtml";
	}
	
	public void validateName(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		IngredientService service = new IngredientService();
		SelectionHelper<Ingredient> helper = new SelectionHelper<>();
		helper.addCriterion(Restrictions.eq("name", value));
		List<Ingredient> ingredientsWithName = service.getList(helper);
		
		if(ingredientsWithName!=null && ingredientsWithName.size()>=1){
			Ingredient curIngredient=ingredientsWithName.get(0);
			logger.info("Id-Vergleich: "+curIngredient.getId()+", "+ingredient.getId());
			logger.info(ingredient);
			if(curIngredient.getId()!=ingredient.getId()){
				throw new ValidatorException(new FacesMessage("Der Name existiert bereits!"));
			}
		}
	}
}
