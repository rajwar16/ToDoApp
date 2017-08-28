package com.bridgeit.TodoApp.dao;

import java.util.List;

import com.bridgeit.TodoApp.model.Collaborator;
import com.bridgeit.TodoApp.model.ToDoNotes;

public interface TodoNotesDao 
{
	Object CreateAndUpdateToDoNotes(ToDoNotes toDoNotes);

	List<ToDoNotes> getNotesList(long userId);

	ToDoNotes getNotesById(long noteId,long userId);

	List<ToDoNotes> getNotesByTitle(String noteTitle, long userId);

	boolean deleteNoteById(long noteId);

	Boolean collaboratorNoteCreate(Collaborator collaborator);
}
