package com.bridgeit.TodoApp.JSONResponse;

import java.util.Date;
import java.util.List;

import com.bridgeit.TodoApp.model.ToDoNotes;

public class ToDoNotesResponse extends Response
{
	private List<ToDoNotes> list;
	private ToDoNotes toDoNotes;	
	private Date createdDate;
	private Date editedDate;
	
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


	}
