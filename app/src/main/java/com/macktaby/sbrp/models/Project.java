package com.macktaby.sbrp.models;

public class Project {

	private int projectID;

	private String name;
	private String techReflection;
	private String mngReflection;
	private String bzReflection;

	private int parentID;

	public Project() {
		this.projectID = 0;
		this.name = "";
		this.techReflection = "";
		this.mngReflection = "";
		this.bzReflection = "";
		this.parentID = 0;
	}

	public Project(int projectID, String name, String techReflection, String mngReflection, String bzReflection,
			int parentID) {
		this.projectID = projectID;
		this.name = name;
		this.techReflection = techReflection;
		this.mngReflection = mngReflection;
		this.bzReflection = bzReflection;
		this.parentID = parentID;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTechReflection() {
		return techReflection;
	}

	public void setTechReflection(String techReflection) {
		this.techReflection = techReflection;
	}

	public String getMngReflection() {
		return mngReflection;
	}

	public void setMngReflection(String mngReflection) {
		this.mngReflection = mngReflection;
	}

	public String getBzReflection() {
		return bzReflection;
	}

	public void setBzReflection(String bzReflection) {
		this.bzReflection = bzReflection;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

}
