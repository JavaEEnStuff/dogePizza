package com.wow.doge.managedbeans;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.wow.doge.domain.Category;
import com.wow.doge.domain.Ingredient;
import com.wow.doge.domain.Meal;
import com.wow.doge.domain.User;
import com.wow.doge.helper.CategorySelectItemHelper;
import com.wow.doge.helper.IngredientSelectItemHelper;
import com.wow.doge.helper.MealEvaluationHelper;
import com.wow.doge.helper.MealSorting;
import com.wow.doge.services.CategoryService;
import com.wow.doge.services.IngredientService;
import com.wow.doge.services.MealService;
import com.wow.doge.services.UserService;
import com.wow.doge.view.MealViewItem;

@ManagedBean
@RequestScoped
public class MealBean {

	private static final int CATEGORY_ID_ALL = 0;

	private static final Logger logger = Logger.getLogger(MealBean.class);

	private Meal meal;
	// die ausgewählten Ids (rechte Seite, aber nur die, die selektiert wurden)
	private List<String> selectedIngredientIds;
	private Integer selectedCategoryId;
	private Integer selectedComparator;
	private boolean onlyFavorites;

	@ManagedProperty("#{param.mealId}")
	private int mealId;

	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean sessionBean;

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	private Double fromPrice;
	private Double toPrice;

	public MealBean() {
		meal = new Meal();
		selectedIngredientIds = new LinkedList<>();
		selectedCategoryId = CATEGORY_ID_ALL;
		selectedComparator = MealSorting.NAME_COMP;
		onlyFavorites = false;
	}

	public void setId(Integer id) {
		meal.setId(id);
	}

	public Integer getId() {
		return meal.getId();
	}

	public String getName() {
		return meal.getName();
	}

	public void setName(String name) {
		meal.setName(name);
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

	public String getCategory() {
		return meal.getCategory().getName();
	}

	public int getMealId() {
		return mealId;
	}

	public Double getFromPrice() {
		return fromPrice;
	}

	public void setFromPrice(Double fromPrice) {
		this.fromPrice = fromPrice;
	}

	public Double getToPrice() {
		return toPrice;
	}

	public void setToPrice(Double toPrice) {
		this.toPrice = toPrice;
	}

	public Integer getSelectedCategoryId() {
		return selectedCategoryId;
	}

	public void setSelectedCategoryId(Integer selectedCategoryId) {
		this.selectedCategoryId = selectedCategoryId;
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
			this.selectedCategoryId = meal.getCategory().getId();
		}
	}

	public Double getFirstPrice() {
		return meal.getFirstPrice();
	}

	public void setFirstPrice(Double firstPrice) {
		meal.setFirstPrice(firstPrice);
	}

	public Double getSecondPrice() {
		return meal.getSecondPrice();
	}

	public void setSecondPrice(Double secondPrice) {
		meal.setSecondPrice(secondPrice);
	}

	public Double getThirdPrice() {
		return meal.getThirdPrice();
	}

	public void setThirdPrice(Double thirdPrice) {
		meal.setThirdPrice(thirdPrice);
	}

	public boolean isOnlyFavorites() {
		return onlyFavorites;
	}

	public void setOnlyFavorites(boolean onlyFavorites) {
		this.onlyFavorites = onlyFavorites;
	}

	public Integer getSelectedComparator() {
		return selectedComparator;
	}

	public void setSelectedComparator(Integer selectedComparator) {
		this.selectedComparator = selectedComparator;
	}

	// ========== FUNKTIONEN ================

	public List<SelectItem> getAllIngredients() {
		IngredientService service = new IngredientService();
		IngredientSelectItemHelper helper = new IngredientSelectItemHelper();
		List<SelectItem> asSelectItemList = helper.asSelectItemList(service.getList());
		return asSelectItemList;
	}

	public List<SelectItem> getAllCategories() {
		CategoryService service = new CategoryService();
		CategorySelectItemHelper helper = new CategorySelectItemHelper();
		List<SelectItem> asSelectItemList = helper.asSelectItemList(service.getList());
		return asSelectItemList;
	}

	public List<SelectItem> getAllCategoriesPlusAll() {
		CategoryService service = new CategoryService();
		CategorySelectItemHelper helper = new CategorySelectItemHelper();
		List<SelectItem> asSelectItemList = helper.asSelectItemList(service.getList());
		asSelectItemList.add(0, new SelectItem(CATEGORY_ID_ALL, "Alle"));
		return asSelectItemList;
	}

	public List<SelectItem> getAllSortings() {
		return MealSorting.getSortings();
	}

	public List<MealViewItem> getMeals() {
		logger.info("Suche nach meals...");
		MealEvaluationHelper helper = new MealEvaluationHelper();
		List<Meal> meals = helper.getMealsInPriceRangeAndCategory(fromPrice, toPrice, selectedCategoryId, selectedComparator, onlyFavorites, sessionBean.getLoggedInUser());
		List<MealViewItem> elements = new LinkedList<MealViewItem>();
		for (Meal nextMeal : meals) {
			if (sessionBean.getLoggedInUser() != null) {
				if (nextMeal.isFavoredBy(sessionBean.getLoggedInUser())) {
					elements.add(new MealViewItem(nextMeal, true, true));
				} else {
					elements.add(new MealViewItem(nextMeal, false, true));
				}
			} else {
				elements.add(new MealViewItem(nextMeal, false, false));
			}
		}
		return elements;
	}

	public List<String> getSelectedIngredientIds() {
		return selectedIngredientIds;
	}

	public void setSelectedIngredientIds(List<String> selectedIngredientIds) {
		this.selectedIngredientIds = selectedIngredientIds;
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

		CategoryService categoryService = new CategoryService();
		Category selectedCategory = categoryService.get(selectedCategoryId);
		meal.setCategory(selectedCategory);

		service.saveOrUpdate(meal);
		return mealList();
	}

	public String favoriteMeal() {
		MealService mealService = new MealService();
		UserService userService = new UserService();
		meal = mealService.get(mealId);
		User user = sessionBean.getLoggedInUser();
		if (!meal.isFavoredBy(user)) {
			logger.info("Noch nicht vorhanden, kann hinzufügen");
			user.addFavoriteMeals(meal);
			meal.addFavoredBy(user);

			userService.saveOrUpdate(user);
			mealService.saveOrUpdate(meal);
		} else {
			logger.info("Schon vorhanden, muss entfernen");
			user.removeFavoritedMeal(meal);
			userService.saveOrUpdate(user);

			meal.removeFavoredBy(user);
			mealService.saveOrUpdate(meal);
		}
		return "";
	}

	// ========= Links ===============

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

	public String main() {
		return "/resources/javaee/main.xhtml";
	}

	public String search() {
		return "";
	}

	public String clearSearch() {
		fromPrice = null;
		toPrice = null;
		selectedCategoryId = CATEGORY_ID_ALL;
		selectedComparator = MealSorting.NAME_COMP;
		onlyFavorites = false;
		return "";
	}
}
