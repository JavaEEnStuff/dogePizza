package com.wow.doge.helper;

import java.util.List;

import javax.faces.model.SelectItem;

public abstract class SelectItemHelper<T> {

	abstract List<SelectItem> asSelectItemList(List<T> elements);
}
