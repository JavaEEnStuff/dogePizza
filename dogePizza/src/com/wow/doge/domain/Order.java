package com.wow.doge.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="dogeOrder")
public class Order {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("hh-mm-ss");

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/** Datum + Lieferuhrzeit */
	private long date;
	@ManyToOne(cascade = CascadeType.ALL)
	private Address address;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<OrderPosition> positions;
	private String remark;

	public Order() {
		positions = new HashSet<OrderPosition>();
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

	public Set<OrderPosition> getPositions() {
		return positions;
	}

	public void setPositions(Set<OrderPosition> positions) {
		this.positions = positions;
	}

	public void addPosition(OrderPosition position) {
		this.positions.add(position);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", remark=" + remark + "]";
	}

	

}
