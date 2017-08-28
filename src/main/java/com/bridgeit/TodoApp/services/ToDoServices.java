package com.bridgeit.TodoApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.TodoApp.dao.TodoNotesDao;
import com.bridgeit.TodoApp.model.Collaborator;
import com.bridgeit.TodoApp.model.ToDoNotes;

public class ToDoServices 
{
	@Autowired
	TodoNotesDao todoNotesDao;
	
	@Transactional
	public Object CreateAndUpdateToDoNotes(ToDoNotes toDoNotes) {
		return todoNotesDao.CreateAndUpdateToDoNotes(toDoNotes);
	}

	public List<ToDoNotes> getNotesList(long userId) {
		return todoNotesDao.getNotesList(userId);
	}

	public ToDoNotes getNotesById(long noteId,long userId) {
		return todoNotesDao.getNotesById(noteId,userId);
	}

	public List<ToDoNotes> getNotesByTitle(String noteTitle, long userId) {
		return todoNotesDao.getNotesByTitle(noteTitle,userId);
	}
	
	@Transactional
	public boolean deleteNoteById(long noteId) {
		return todoNotesDao.deleteNoteById(noteId);
	}

	@Transactional
	public Boolean collaboratorNoteCreate(Collaborator collaborator) {
		return todoNotesDao.collaboratorNoteCreate(collaborator);
	}

}
