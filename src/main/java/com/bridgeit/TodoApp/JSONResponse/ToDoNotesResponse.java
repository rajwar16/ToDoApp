package com.bridgeit.TodoApp.JSONResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bridgeit.TodoApp.model.ToDoNotes;
import com.bridgeit.TodoApp.model.User;

public class ToDoNotesResponse extends Response
{
	private List<ToDoNotes> list;
	private ToDoNotes toDoNotes;	
	private Date createdDate;
	private Date editedDate;
	private User user;
	private User SharedUser;
	private List<String> links;
	
	public List<String> getLinks() {
		return links;
	}

	public void setLinks(List<String> containedUrls) {
		this.links = containedUrls;
	}

	public User getSharedUser() {
		return SharedUser;
	}

	public void setSharedUser(User sharedUser) {
		SharedUser = sharedUser;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Date getEditedDate() {
		return editedDate;
	}
	
	public void setEditedDate(Date editedDate) {
		this.editedDate = editedDate;
	}
	
	public List<ToDoNotes> getList() {
		return list;
	}
	
	public void setList(List<ToDoNotes> toDoNotes) {
		this.list = toDoNotes;
	}
	
	public ToDoNotes getToDoNotes() {
		return toDoNotes;
	}
	
	public void setToDoNotes(ToDoNotes toDoNotes) {
		this.toDoNotes = toDoNotes;
	}

	@Override
	public String toString() {
		return "ToDoNotesResponse [list=" + list + ", toDoNotes=" + toDoNotes + ", createdDate=" + createdDate
				+ ", editedDate=" + editedDate + ", User=" + user + "]";
	}
}
