package com.macktaby.sbrp.models;

public class Attribute {

	private int attributeID;
	private String name;
	private String value;

	public Attribute() {
		this.attributeID = 0;
		this.name = "";
		this.value = "";
	}

	public Attribute(int attributeID, String name) {
		this.attributeID = attributeID;
		this.name = name;
		this.value = "";
	}

	public Attribute(int attributeID, String name, String value) {
		this.attributeID = attributeID;
		this.name = name;
		this.value = value;
	}

	public int getAttributeID() {
		return attributeID;
	}

	public void setAttributeID(int attributeID) {
		this.attributeID = attributeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
