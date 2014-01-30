package com.wow.doge.domain;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Gericht {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private double rawPrice;
	private boolean vegeterian;
	private String description;
	private byte[] image;
	@OneToMany
	private List<Size> possibleSizes;
	@OneToMany
	private List<Zutat> zutaten;

	// TODO Erweiterungsmöglichkeiten

	public Gericht() {
		possibleSizes = new LinkedList<Size>();
		zutaten = new LinkedList<Zutat>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRawPrice() {
		return rawPrice;
	}

	public void setRawPrice(double rawPrice) {
		this.rawPrice = rawPrice;
	}

	public boolean isVegeterian() {
		return vegeterian;
	}

	public void setVegeterian(boolean vegeterian) {
		this.vegeterian = vegeterian;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public List<Size> getPossibleSizes() {
		return possibleSizes;
	}

	public void setPossibleSizes(List<Size> possibleSizes) {
		this.possibleSizes = possibleSizes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Zutat> getZutaten() {
		return zutaten;
	}

	public void setZutaten(List<Zutat> zutaten) {
		this.zutaten = zutaten;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Gericht [id=").append(id).append(", name=")
				.append(name).append(", price=").append(rawPrice)
				.append(", vegeterian=").append(vegeterian)
				.append(", description=").append(description)
				.append(", image=").append(Arrays.toString(image))
				.append(", possibleSizes=").append(possibleSizes)
				.append(", zutaten=").append(zutaten).append("]");
		return builder.toString();
	}

}
