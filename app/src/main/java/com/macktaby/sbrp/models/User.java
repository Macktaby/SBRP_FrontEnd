package com.macktaby.sbrp.models;

public class User {

	private int userID;
	private String name;
	private Project project;

	public User() {
		this.userID = 0;
		this.name = "";
		this.project = new Project();
	}

	public User(int userID, String name, int projectID) {
		this.userID = userID;
		this.name = name;
		this.project = new Project(projectID, "", "", "", "", 1);
	}

	public User(int userID, String name, Project project) {
		this.userID = userID;
		this.name = name;
		this.project = project;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
