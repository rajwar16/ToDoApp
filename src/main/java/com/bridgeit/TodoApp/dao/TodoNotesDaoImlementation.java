package com.bridgeit.TodoApp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.TodoApp.model.ToDoNotes;
import com.bridgeit.TodoApp.model.UserRegistration;

public class TodoNotesDaoImlementation implements TodoNotesDao{

	@Autowired
	SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * ----------Create and update ToDoNote----------------------------
	 * @see com.bridgeit.TodoApp.dao.TodoNotesDao#CreateToDoNotes(com.bridgeit.TodoApp.model.ToDoNotes)
	 */
	public Object CreateAndUpdateToDoNotes(ToDoNotes toDoNotes) 
	{
		System.out.println("todo Item :: "+toDoNotes);
		System.out.println("session factory :: "+sessionFactory);
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(toDoNotes);
		return true;
	}

	/* (non-Javadoc)
	 * --------------get Notes List-------------
	 * @see com.bridgeit.TodoApp.dao.TodoNotesDao#getNotesList(long)
	 */
	public List<ToDoNotes> getNotesList(long userId) 
	{
		System.out.println("get notes Dao ......");
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(ToDoNotes.class);
		criteria.add(Restrictions.eq("user.id", userId));
		List<ToDoNotes> list=criteria.list();
		System.out.println("list of Notes :: "+criteria.list());
		return list;
	}
	
	/* (non-Javadoc)
	 * ------------delete Note By Id------------------
	 * @see com.bridgeit.TodoApp.dao.TodoNotesDao#deleteNoteById(long)
	 */
	public boolean deleteNoteById(long noteId) {
		Session session=sessionFactory.getCurrentSession();
	/*	ToDoNotes notes = (ToDoNotes ) session.createCriteria(ToDoNotes.class)
          .add(Restrictions.eq("id", noteId)).uniqueResult();
		System.out.println("notes from database :: "+notes);
		session.delete(notes);	*/	
		Query query=session.createQuery("delete from ToDoNotes where noteid=:idNote");
		query.setParameter("idNote", noteId);
		query.executeUpdate();
		
		return true;
	}

	/* (non-Javadoc)
	 * ---------get Note By Id----------------
	 * 
	 * @see com.bridgeit.TodoApp.dao.TodoNotesDao#getNotesById(long)
	 */
	public ToDoNotes getNotesById(long noteId) {
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(ToDoNotes.class);
		ToDoNotes toDoNotes=(ToDoNotes) criteria.add(Restrictions.eq("Noteid", noteId)).uniqueResult();
		return toDoNotes;
	}

	/* (non-Javadoc)
	 * -----------------get Note By Title-----------------
	 * @see com.bridgeit.TodoApp.dao.TodoNotesDao#getNotesByTitle(java.lang.String, long)
	 */
	public List<ToDoNotes> getNotesByTitle(String noteTitle,long userId) {
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(ToDoNotes.class);
		criteria.add(Restrictions.eq("title", noteTitle)).add(Restrictions.eq("user.id", userId));
		List<ToDoNotes> toDoNotes=criteria.list();	
		return toDoNotes;
	}
	
	
	
}
