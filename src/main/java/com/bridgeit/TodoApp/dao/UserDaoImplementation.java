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
import com.bridgeit.TodoApp.model.UserRegistration;
import com.bridgeit.TodoApp.validator.StrongSecuredPassword;

public class UserDaoImplementation implements UserDao
{
	@Autowired
	SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * Register user added inside database `ToDoGoogleKeep` of table `User_Registration` 
	 * @see com.bridgeit.TodoApp.dao.UserDao#addUserRegister(com.bridgeit.TodoApp.model.UserRegistration)
	 */
	
	public Object addUserRegister(User userRegistration) throws NoSuchAlgorithmException, InvalidKeySpecException {

		String originalPassword=userRegistration.getPassword();
		System.out.println("Original password comming from view page :: "+originalPassword);
		StrongSecuredPassword strongSecuredPassword=new StrongSecuredPassword();

		String EncryptedPassword=strongSecuredPassword.EncryptPassword(originalPassword);
		userRegistration.setPassword(EncryptedPassword);

		System.out.println("dao Implementation : "+userRegistration);

		Session session=sessionFactory.getCurrentSession();

		session.saveOrUpdate(userRegistration);		
		return true;
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

		System.out.println("useregistration data "+user);
		System.out.println("database password :: "+user.getPassword());
		System.out.println("incoming password :: "+incomingPassword);
		boolean validatePassword=strongSecuredPassword.validatingPassword(user.getPassword(),incomingPassword);

		if(validatePassword)
		{
			return user;
		}
		return null;

	}

	public User getUserById(String id) {
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(UserRegistration.class);
		criteria.add(Restrictions.eq("userName", id));

		return (User) criteria.uniqueResult();
	}

	public List<User> getUserList() 
	{
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(UserRegistration.class);

		List<User> list=criteria.list();
		return list;

	}

	public Object updateUser(User userRegistration) throws NoSuchAlgorithmException, InvalidKeySpecException {

		/*String originalPassword=userRegistration.getPassword();
		System.out.println("Original password comming from view page :: "+originalPassword);
		StrongSecuredPassword strongSecuredPassword=new StrongSecuredPassword();

		String EncryptedPassword=strongSecuredPassword.EncryptPassword(originalPassword);
		userRegistration.setPassword(EncryptedPassword);

		System.out.println("dao Implementation : "+userRegistration);

		Session session=sessionFactory.getCurrentSession();

		session.update(userRegistration);	*/	
		return null;
	}
	public boolean deleteUser(long userId) {
		Session session=sessionFactory.getCurrentSession();
		
		
		
		 UserRegistration user = (UserRegistration ) session.createCriteria(UserRegistration.class)
          .add(Restrictions.eq("id", userId)).uniqueResult();

		session.delete(user);		
		return true;
	}


}
