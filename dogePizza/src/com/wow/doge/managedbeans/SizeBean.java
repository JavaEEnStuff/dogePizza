package com.wow.doge.managedbeans;

import com.wow.doge.domain.Size;

@ManagedBean
@SessionScoped
public class SizeBean {

	private Size size;
	
	public SizeBean(){
		
	}

	public int getId() {
		return size.getId();
	}
	public void setId(int id) {
		size.setId(id);
	}
	public String getName() {
		return size.getName();
	}
	public void setName(String name) {
		size.setName(name);
	}
	public String getDescription() {
		return size.getDescription();
	}
	public void setDescription(String description) {
		this.setDescription(description);
	}
	public double getPriceMultiplicator() {
		return size.getPriceMultiplicator();
	}
	public void setPriceMultiplicator(double priceMultiplicator) {
		size.setPriceMultiplicator(priceMultiplicator);
	}

	@Override
	public String toString() {
		return size.toString();
	}
}
