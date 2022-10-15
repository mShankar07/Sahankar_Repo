package com.squareship.model;

public class Product {

	
	private int id;
	private String name;
	private double price;
	private String  description;
	private String category;
	private String image;
	private int discount_percentage;
	private int weight_in_grams ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getDiscount_percentage() {
		return discount_percentage;
	}
	public void setDiscount_percentage(int discount_percentage) {
		this.discount_percentage = discount_percentage;
	}
	public int getWeight_in_grams() {
		return weight_in_grams;
	}
	public void setWeight_in_grams(int weight_in_grams) {
		this.weight_in_grams = weight_in_grams;
	}
	
	
	
}


