package com.wow.doge.domain;

import java.text.SimpleDateFormat;
import java.util.Comparator;
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

@Entity(name = "dogeOrder")
public class Order implements Comparable<Order>{

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
	private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

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
	private long orderDate;

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
		return timeFormat.format(new Date(date));
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

	public Long getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(long orderDate) {
		this.orderDate = orderDate;
	}
	
	public String getFormattedOrderDate(){
		return timestampFormat.format(new Date(orderDate));
	}
	
	public int getPositionCount(){
		return positions.size();
	}
	
	public double getPrice(){
		double price = 0;
		for(OrderPosition position : positions){
			price += position.getPrice();
		}
		return price;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", address=" + address + ", positions=" + positions + ", remark=" + remark + ", orderDate=" + orderDate
				+ "]";
	}

	/**
	 * natural order: nach Bestelldatum
	 */
	@Override
	public int compareTo(Order o) {
		return o.getOrderDate().compareTo(getOrderDate());
	}
	
	public static Comparator<Order> getReverseOrderDateComparator(){
		return new ReverseOrderDateComparator();
	}
	
	private static class ReverseOrderDateComparator implements Comparator<Order>{

		@Override
		public int compare(Order o1, Order o2) {
			return o2.getOrderDate().compareTo(o1.getOrderDate());
		}
		
	}
}
