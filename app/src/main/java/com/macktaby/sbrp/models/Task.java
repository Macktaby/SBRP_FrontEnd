package com.macktaby.sbrp.models;

import java.util.List;

public class Task {

	private int taskID;

	private String name;
	private String devComment;
	private List<Task> childs;
	private List<Attribute> attributes;

	private Block block;
	private User user;
	private Person assignedTo;
	private int parentID;

	public Task() {
		this.taskID = 0;
		this.name = "";
		this.block = new Block();
		this.user = new User();
		this.assignedTo = new Person();
		this.parentID = 0;
	}

	public Task(int taskID, String name, int blockID, int userID, int projectID, int personID, int parentID) {
		this.taskID = taskID;
		this.name = name;
		this.block = new Block(blockID, "", projectID);
		this.user = new User(userID, "", projectID);
		this.assignedTo = new Person(personID, "", "");
		this.parentID = parentID;
	}

	public Task(int taskID, String name, Block block, User user, Person assignedTo, int parentID) {
		this.taskID = taskID;
		this.name = name;
		this.block = block;
		this.user = user;
		this.assignedTo = assignedTo;
		this.parentID = parentID;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDevComment() {
		return devComment;
	}

	public void setDevComment(String devComment) {
		this.devComment = devComment;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Person getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Person assignedTo) {
		this.assignedTo = assignedTo;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public List<Task> getChilds() {
		return childs;
	}

	public void setChilds(List<Task> childs) {
		this.childs = childs;
	}

	public void addTask(Task task) {
		this.childs.add(task);
	}
}
