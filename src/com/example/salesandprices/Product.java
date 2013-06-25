package com.example.salesandprices;

import java.util.Date;

public class Product {
	private long id;
	private Date creationDateTime;
	private String name;
	private String description;
	private String descrCut;
	private int price;
	private boolean exclusive;

	public Product(long id, Date creationDateTime, String name, String description, String descrCut, int price, boolean exclusive) {
		this.id = id;
		this.creationDateTime = creationDateTime;
		this.name = name;
		this.description = description;
		this.descrCut = descrCut;
		this.price = price;
		this.setExclusive(exclusive);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescrCut() {
		return descrCut;
	}

	public void setDescrCut(String descrCut) {
		this.descrCut = descrCut;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product() {
		this.name = "";
		this.description = "";
		this.descrCut = "";
	}

	public Date getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDateTime = creationDate;
	}

	public boolean isExclusive() {
		return exclusive;
	}

	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
	}

	public boolean isNew()
	{
		long diffInMinutes = ((new Date()).getTime() - creationDateTime.getTime()) / (1000 * 60);
		return diffInMinutes < 10;
	}
}
