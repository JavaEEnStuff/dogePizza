package com.wow.doge.helper;

import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.wow.doge.domain.Ingredient;

public class IngredientSelectItemHelper extends SelectItemHelper<Ingredient> {

	@Override
	public List<SelectItem> asSelectItemList(List<Ingredient> elements) {
		List<SelectItem> items = new LinkedList<SelectItem>();
		for (Ingredient nextIngredient : elements) {
			items.add(new SelectItem(nextIngredient.getId(), nextIngredient.getName()));
		}
		return items;
	}

}
