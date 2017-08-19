package com.bridgeit.TodoApp.model;

public class Picture {

	private ProfileData data;

	public ProfileData getData() {
		return data;
	}

	public void setData(ProfileData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Picture [data=" + data + "]";
	}
	
	
}
