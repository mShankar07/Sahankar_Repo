package com.squareship.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ProductInfo {

	private String status;
	private String message;
	private ArrayList<Item> items= new ArrayList<Item>();
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	
}
