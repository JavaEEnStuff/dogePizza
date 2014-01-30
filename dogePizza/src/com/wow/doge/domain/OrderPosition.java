package com.wow.doge.domain;

public class OrderPosition {

	private int id;
	private Gericht gericht;
	private Size size;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Gericht getGericht() {
		return gericht;
	}

	public void setGericht(Gericht gericht) {
		this.gericht = gericht;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	/**
	 * @return Zusammengesetzter Preis aus Grundpreis * Größenanpassung
	 */
	public double getPrice() {
		return gericht.getRawPrice() * size.getPriceMultiplicator();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderPosition [id=").append(id).append(", gericht=")
				.append(gericht).append(", size=").append(size).append("]");
		return builder.toString();
	}

}
