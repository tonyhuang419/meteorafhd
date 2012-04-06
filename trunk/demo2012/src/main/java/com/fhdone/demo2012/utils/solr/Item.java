package com.fhdone.demo2012.utils.solr;

import org.apache.solr.client.solrj.beans.Field;

public class Item {
	
	@Field
    String id;

	@Field
    String name;

	@Field
	String price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
