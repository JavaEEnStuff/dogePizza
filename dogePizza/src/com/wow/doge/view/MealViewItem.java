package com.wow.doge.view;

import com.wow.doge.domain.Meal;

public class MealViewItem {

	private Meal meal;
	private boolean favorited;
	private boolean canFavorite;

	public MealViewItem(Meal meal, boolean favorited, boolean canFavorite) {
		this.meal = meal;
		this.favorited = favorited;
		this.canFavorite = canFavorite;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public boolean isCanFavorite() {
		return canFavorite;
	}

	public void setCanFavorite(boolean canFavorite) {
		this.canFavorite = canFavorite;
	}

	@Override
	public String toString() {
		return "MealViewItem [meal=" + meal + ", favorited=" + favorited + ", canFavorite=" + canFavorite + "]";
	}

}
