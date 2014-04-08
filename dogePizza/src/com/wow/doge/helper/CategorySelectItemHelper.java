package com.wow.doge.helper;

import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.wow.doge.domain.Category;

public class CategorySelectItemHelper extends SelectItemHelper<Category> {

	@Override
	public List<SelectItem> asSelectItemList(List<Category> elements) {
		List<SelectItem> items = new LinkedList<SelectItem>();
		if (elements != null) {
			for (Category nextCategory : elements) {
				items.add(new SelectItem(nextCategory.getId(), nextCategory.getName()));
			}
		}
		return items;
	}

}
