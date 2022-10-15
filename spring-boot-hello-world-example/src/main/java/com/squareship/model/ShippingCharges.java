package com.squareship.model;

public class ShippingCharges {

	private int totalWeight;
	private double distance;
	private double discounts;
	private double finalPrice;
	
	
	public double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}
	public int getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(int totalWeight) {
		this.totalWeight = totalWeight;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getDiscounts() {
		return discounts;
	}
	public void setDiscounts(double discounts) {
		this.discounts = discounts;
	}
	
	
}
