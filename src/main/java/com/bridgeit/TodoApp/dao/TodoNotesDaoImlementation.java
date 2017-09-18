package com.bridgeit.TodoApp.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.TodoApp.model.Collaborator;
import com.bridgeit.TodoApp.model.PageScrapedata;
import com.bridgeit.TodoApp.model.ToDoNotes;
import com.bridgeit.TodoApp.model.User;

public class TodoNotesDaoImlementation implements TodoNotesDao{

	@Autowired
	SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * ----------Create and update ToDoNote----------------------------
	 * @see com.bridgeit.TodoApp.dao.TodoNotesDao#CreateToDoNotes(com.bridgeit.TodoApp.model.ToDoNotes)
	 */
	public Object CreateAndUpdateToDoNotes(ToDoNotes toDoNotes) 
	{
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(toDoNotes);
		return true;
	}

	/* (non-Javadoc)
	 * --------------get Notes List-------------
	 * @see com.bridgeit.TodoApp.dao.TodoNotesDao#getNotesList(long)
	 */
	
	public List<ToDoNotes> getSharedNotesList(long userId) 
	{
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(ToDoNotes.class);
		criteria.add(Restrictions.eq("user.id", userId));
		List<ToDoNotes> list=criteria.list();
		return list;
	}

	public List<ToDoNotes> getNotesList(long userId) 
	{
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(ToDoNotes.class);
		criteria.add(Restrictions.eq("user.id", userId));
		List<ToDoNotes> list=criteria.list();
		
		Criteria criteria1=session.createCriteria(Collaborator.class);
		criteria1.add(Restrictions.eq("sharedId", userId));
	    List list2 = criteria1.list();
	    Iterator iterator = list2.iterator(); 
	    while(iterator.hasNext()) { 
	    	Collaborator element = (Collaborator) iterator.next(); 
	    	list.add(element.getNoteid());
	    } 
	    
	    return list;
		
		/*Criteria accountCriteria = getCurrentSession().createCriteria(Account.class,"acc");*/
		/*Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(Collaborator.class);
		criteria.add(Restrictions.eq("sharedId", userId));

		List list=criteria.list();
		System.out.println("all list ::vvvvvvvvvvvv "+list);
		List<ToDoNotes> list1=null;
		
		Criteria criteria1=session.createCriteria(ToDoNotes.class);
		criteria.add(Restrictions.eq("user.id", userId));
		List<ToDoNotes> list1=criteria.list();
		return list;
		*/
		/*Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(ToDoNotes.class,"todonotes").createAlias("todonotes.Collaborator","collaborator").add(Restrictions.eq("user.id", userId));.add(Restrictions.eq("collaborator.sharedId", userId));
		List list = criteria.list();
		System.out.println("List :: "+list);
		return list;*/
		
		/*Session session=sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ToDoNotes.class);
	    criteria.setFetchMode("Collaborator", FetchMode.JOIN);
	    List list = criteria.list();
	    return list;*/
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
//		delete a , b from a inner join b on a.id=b.a_id where ...
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
	public ToDoNotes getNotesById(long noteId,long userId) {
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(ToDoNotes.class);
		ToDoNotes toDoNotes=(ToDoNotes) criteria.add(Restrictions.eq("Noteid", noteId)).add(Restrictions.eq("user.id", userId)).uniqueResult();
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

	public Boolean collaboratorNoteCreate(Collaborator collaborator) {
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(collaborator);
		return true;
	}

	public Object emptyTrash(long userId) {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("delete from ToDoNotes where user.id="+userId+" and isDelete='true'");
		query.executeUpdate();
	/*	Criteria criteria=session.createCriteria(ToDoNotes.class);
		criteria.add(Restrictions.eq("user.id", userId));
		List<ToDoNotes> list=criteria.list();*/
		return true;
	}

	public Boolean UpdateToDoNotes(ToDoNotes toDoNotes) {
		String hql = "UPDATE ToDoNotes set title=:title1,description=:description,noteCreatedDate=:noteCreatedDate,noteEditedDate=:noteEditedDate,color=:color,pin=:pin,isDelete=:isDelete,isArchive=:isArchive,isReminder=:isReminder,addImage=:addImage WHERE Noteid = :noteid";
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("title1", toDoNotes.getTitle());
		query.setParameter("description", toDoNotes.getDescription());
		query.setParameter("noteCreatedDate", toDoNotes.getNoteCreatedDate());
		query.setParameter("noteEditedDate", toDoNotes.getNoteEditedDate());
		query.setParameter("color", toDoNotes.getColor());
		query.setParameter("pin", toDoNotes.getPin());
		query.setParameter("isDelete", toDoNotes.getIsDelete());
		query.setParameter("isArchive", toDoNotes.getIsArchive());
		query.setParameter("isReminder", toDoNotes.getIsReminder());
		query.setParameter("addImage", toDoNotes.getAddImage());
		query.setParameter("noteid", toDoNotes.getNoteid());
		
		int result = query.executeUpdate();
		System.out.println("query executed successfully...");
		
		return true;
	}

	public Boolean createScrape(PageScrapedata pageScrapedata) {
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(pageScrapedata);
		return true;
	}

	public List<PageScrapedata> getPageScrapeData(long noteid) {
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(PageScrapedata.class);
		criteria.add(Restrictions.eq("noteid.Noteid", noteid));
		List<PageScrapedata> list=criteria.list();
		return list;
	}
}
