package com.fhd.test3;

import java.util.HashSet;
import java.util.Set;

public class People {
	private long id;
	private String name;
	private Set addresses = new HashSet();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set getAddresses() {
		return addresses;
	}
	public void setAddresses(Set addresses) {
		this.addresses = addresses;
	}
	
}
