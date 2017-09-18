package com.bridgeit.TodoApp.model;

public class GmailProfile {
	private String id;
	private String name;
	private String email;
	private String first_name;
	private String last_name;
	private Picture picture;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public Picture getPicture() {
		return picture;
	}
	public void setPicture(Picture picture) {
		this.picture = picture;
	}
	
	
	@Override
	public String toString() {
		return "facebookProfile [id=" + id + ", name=" + name + ", email=" + email + ", first_name=" + first_name
				+ ", last_name=" + last_name + ", picture=" + picture + "]";
	} 

}
