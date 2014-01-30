package com.wow.doge.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Order {

	@Id
	@GeneratedValue
	private int id;

	/** Datum + Lieferuhrzeit */
	private long date;
	private Address address;
	private List<OrderPosition> positions;
	private String remark;

	public Order() {
		positions = new LinkedList<OrderPosition>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<OrderPosition> getPositions() {
		return positions;
	}

	public void setPositions(List<OrderPosition> positions) {
		this.positions = positions;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [id=").append(id).append(", date=").append(date)
				.append(", address=").append(address).append(", positions=")
				.append(positions).append(", remark=").append(remark)
				.append("]");
		return builder.toString();
	}

}
