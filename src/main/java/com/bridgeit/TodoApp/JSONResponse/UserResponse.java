package com.bridgeit.TodoApp.JSONResponse;

import java.util.List;

import com.bridgeit.TodoApp.model.User;
import com.bridgeit.TodoApp.model.UserRegistration;

public class UserResponse extends Response{

	private User userRegistration;
	private List<User> list;
	
	public User getUserRegistration() {
		return userRegistration;
	}
	public void setUserRegistration(User userRegistration) {
		this.userRegistration = userRegistration;
	}
	public List<User> getList() {
		return list;
	}
	public void setList(List<User> list2) {
		this.list = list2;
	}
	
	
	
}
