package com.bridgeit.TodoApp.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.TodoApp.model.User;
import com.bridgeit.TodoApp.validator.StrongSecuredPassword;

public class UserDaoImplementation implements UserDao
{
	@Autowired
	SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * Register user added inside database `ToDoGoogleKeep` of table `User_Registration` 
	 * @see com.bridgeit.TodoApp.dao.UserDao#addUserRegister(com.bridgeit.TodoApp.model.UserRegistration)
	 */
	
	public Object addUserRegister(User userRegistration ,String string) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		Session session=sessionFactory.getCurrentSession();
		if(string=="mannual")
		{
			
		String originalPassword=userRegistration.getPassword();
		StrongSecuredPassword strongSecuredPassword=new StrongSecuredPassword();

		String EncryptedPassword=strongSecuredPassword.EncryptPassword(originalPassword);
		userRegistration.setPassword(EncryptedPassword);
		
		session.saveOrUpdate(userRegistration);	
		return true;
		}
		else if (string=="facebook") {
			session.saveOrUpdate(userRegistration);		
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * this is user login method. validate user authentication
	 * @see com.bridgeit.TodoApp.dao.UserDao#loginUser(java.lang.String, java.lang.String)
	 */
	public User loginUser(String emailId, String incomingPassword) throws NoSuchAlgorithmException, InvalidKeySpecException 
	{
		User user=null;
		Session session=sessionFactory.openSession();

		Criteria criteria=session.createCriteria(User.class);

		StrongSecuredPassword strongSecuredPassword=new StrongSecuredPassword();

		user=(User) criteria.add(Restrictions.eq("email", emailId)).uniqueResult();

		if(user==null)
		{
			return null;
		}

		boolean validatePassword=strongSecuredPassword.validatingPassword(user.getPassword(),incomingPassword);

		if(validatePassword)
		{
			return user;
		}
		return null;
	}

	public User getUserByEmail(String email) {
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", email));
		return (User) criteria.uniqueResult();
	}

	public List<User> getUserList() 
	{
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(User.class);
		List<User> list=criteria.list();
		return list;
	}

	public Object updateUser(User userRegistration) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Session session=sessionFactory.getCurrentSession();
		session.update(userRegistration);		
		return true;
	}
	
	public boolean deleteUser(long userId) {
		Session session=sessionFactory.getCurrentSession();
		 User user = (User) session.createCriteria(User.class)
          .add(Restrictions.eq("id", userId)).uniqueResult();
		session.delete(user);		
		return true;
	}

	public User getUserById(long userId) {
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(User.class);
		criteria.add(Restrictions.eq("id", userId));
		return (User) criteria.uniqueResult();
	}

}
