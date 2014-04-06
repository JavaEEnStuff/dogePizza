package com.wow.doge.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import com.wow.doge.domain.Category;
import com.wow.doge.services.CategoryService;

@ManagedBean
@RequestScoped
public class CategoryBean {

	private static final Logger logger = Logger.getLogger(CategoryBean.class);

	@ManagedProperty("#{param.categoryId}")
	private int categoryId;

	private Category category;

	public CategoryBean() {
		category = new Category();
	}

	public Integer getId() {
		return category.getId();
	}

	public void setId(Integer id) {
		category.setId(id);
	}

	public String getName() {
		return category.getName();
	}

	public void setName(String name) {
		category.setName(name);
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return category.toString();
	}

	// ====== Funktionen ===========

	public List<Category> getAllCategories() {
		CategoryService service = new CategoryService();
		return service.getList();
	}

	public String save() {
		logger.info("Versuche Category zu speichern... " + category);
		CategoryService service = new CategoryService();
		service.saveOrUpdate(category);

		return categoryList();
	}

	public String showCategory() {
		CategoryService service = new CategoryService();
		category = service.get(categoryId);
		return "showCategory.jsf";
	}

	public String changeCategory() {
		CategoryService service = new CategoryService();
		category = service.get(categoryId);
		logger.info("Versuche Category zu aendern: " + category);
		return "changeCategory.jsf";
	}

	public String deleteCategory() {
		CategoryService service = new CategoryService();
		Category categoryToDelete = service.get(categoryId);
		service.delete(categoryToDelete);
		return categoryList();
	}

	public String createCategory() {
		return "createCategory.xhtml";
	}

	public String categoryList() {
		return "categoryList.xhtml";
	}
}
