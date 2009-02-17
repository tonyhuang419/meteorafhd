package com.fhd.test3;

public class Address {
	private long id;

	private String addressName;

	private String codeNumber;

	private People people;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public String getCodeNumber() {
		return codeNumber;
	}
	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	} 

	public People getPeople() {
		return people;
	}
	public void setPeople(People people) {
		this.people = people;
	}
}
