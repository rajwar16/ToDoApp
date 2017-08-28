package com.bridgeit.TodoApp.JSONResponse;

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
	private User noteSharedWithUser;
	
	public User getNoteSharedWithUser() {
		return noteSharedWithUser;
	}

	public void setNoteSharedWithUser(User noteSharedWithUser) {
		this.noteSharedWithUser = noteSharedWithUser;
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
				+ ", editedDate=" + editedDate + ", noteSharedWithUser=" + noteSharedWithUser + "]";
	}
}
