package com.macktaby.sbrp.model;

import java.io.Serializable;

public class Person implements Serializable{

	private int personID;
	private String name;
	private String role;

	public Person() {
		this.personID = 0;
		this.name = "";
		this.role = "";
	}

	public Person(int personID, String name, String role) {
		this.personID = personID;
		this.name = name;
		this.role = role;
	}

	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
