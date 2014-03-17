package com.wow.doge.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Order {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final SimpleDateFormat timestampFormat = new SimpleDateFormat(
			"hh-mm-ss");

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/** Datum + Lieferuhrzeit */
	private long date;
	private Address address;
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
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

	public String getDeliveryDate() {
		return dateFormat.format(new Date(date));
	}

	public String getDeliveryTime() {
		return timestampFormat.format(new Date(date));
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
