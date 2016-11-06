package com.macktaby.sbrp.model;

public class Block {

	private int blockID;
	private String name;
	private Project project;

	public Block() {
		this.blockID = 0;
		this.name = "";
		this.project = new Project();
	}

	public Block(int blockID, String name, int projectID) {
		this.blockID = blockID;
		this.name = name;
		this.project = new Project(projectID, "", "", "", "", 1);
	}

	public Block(int blockID, String name, Project project) {
		this.blockID = blockID;
		this.name = name;
		this.project = project;
	}

	public int getBlockID() {
		return blockID;
	}

	public void setBlockID(int blockID) {
		this.blockID = blockID;
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
