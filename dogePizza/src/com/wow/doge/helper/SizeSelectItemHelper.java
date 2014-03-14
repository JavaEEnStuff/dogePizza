package com.wow.doge.helper;

import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.wow.doge.domain.Size;

public class SizeSelectItemHelper extends SelectItemHelper<Size> {

	@Override
	public List<SelectItem> asSelectItemList(List<Size> elements) {
		List<SelectItem> items = new LinkedList<SelectItem>();
		for (Size nextSize : elements) {
			items.add(new SelectItem(nextSize.getId(), nextSize.getName()));
		}
		return items;
	}

}
