package com.wow.doge.helper;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.wow.doge.domain.Meal;

public class MealSorting {

	public static final int NAME_COMP = 1;
	public static final int PRICE_COMP = 2;
	public static final int REV_PRICE_COMP = 3;
	public static final int REV_ORDER_COUNT_COMP = 4;

	public static List<SelectItem> getSortings() {
		List<SelectItem> items = new LinkedList<>();
		items.add(new SelectItem(NAME_COMP, "nach Name"));
		items.add(new SelectItem(PRICE_COMP, "billigste zuerst"));
		items.add(new SelectItem(REV_PRICE_COMP, "teuerste zuerst"));
		items.add(new SelectItem(REV_ORDER_COUNT_COMP, "am beliebtesten"));
		return items;
	}

	public static Comparator<Meal> getMealNameComparator() {
		return new MealNameComparator();
	}

	public static Comparator<Meal> getMealPriceComparator() {
		return new MealPriceComparator();
	}

	public static Comparator<Meal> getReverseMealPriceComparator() {
		return new ReverseMealPriceComparator();
	}

	public static Comparator<Meal> getReverseOrderCountComparator() {
		return new ReverseOrderCountComparator();
	}

	/**
	 * Sortierungsalgorithmus. Dabei werden zunaechst die 3 Preise des ersten Menüs verglichen und danach das Ergebnis des Vergleichs mit den restlichen 3 Preisen.
	 * Es gilt hierbei: ist der kleinste Preis beim ersten Menü, ist dies das Menü mit dem kleineren Preis. Ist irgendeiner der Preise des anderen Menüs noch kleiner, so ist dieses Menü
	 * das Menü mit dem kleineren Preis
	 * @param o1
	 * @param o2
	 * @return Preisvergleich
	 */
	private static int comparePrice(Meal o1, Meal o2) {
		int mealWithHigherPrice = 1;
		double highestPrice = o1.getFirstPrice();
		if (o1.getSecondPrice() != null && o1.getSecondPrice() > highestPrice)
			highestPrice = o1.getSecondPrice();
		if (o1.getThirdPrice() != null && o1.getThirdPrice() > highestPrice)
			highestPrice = o1.getThirdPrice();

		if (o2.getFirstPrice() != null && o2.getFirstPrice() > highestPrice) {
			mealWithHigherPrice = -1;
			highestPrice = o2.getFirstPrice();
		}
		if (o2.getSecondPrice() != null && o2.getSecondPrice() > highestPrice) {
			mealWithHigherPrice = -1;
			highestPrice = o2.getSecondPrice();
		}
		if (o2.getThirdPrice() != null && o2.getThirdPrice() > highestPrice) {
			mealWithHigherPrice = -1;
			highestPrice = o2.getThirdPrice();
		}
		return mealWithHigherPrice;
	}

	private static class MealNameComparator implements Comparator<Meal> {

		@Override
		public int compare(Meal o1, Meal o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}

	private static class MealPriceComparator implements Comparator<Meal> {

		@Override
		public int compare(Meal o1, Meal o2) {
			return comparePrice(o1, o2);
		}
	}

	private static class ReverseMealPriceComparator implements Comparator<Meal> {

		@Override
		public int compare(Meal o1, Meal o2) {
			return comparePrice(o1, o2) * -1;
		}
	}

	private static class ReverseOrderCountComparator implements Comparator<Meal> {

		@Override
		public int compare(Meal o1, Meal o2) {
			if (o1.getPositions() != null && o2.getPositions() != null) {
				int countCompare = Integer.valueOf(o2.getPositions().size()).compareTo(o1.getPositions().size());
				if (countCompare == 0) {
					return o1.compareTo(o2);
				} else {
					return countCompare;
				}
			} else {
				if (o1.getPositions() != null) {
					return -1;
				} else if (o2.getPositions() != null) {
					return 1;
				} else {
					return o1.compareTo(o2);
				}
			}
		}

	}

}
