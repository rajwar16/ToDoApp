package com.bridgeit.TodoApp.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.TodoApp.dao.UserDao;
import com.bridgeit.TodoApp.model.User;
import com.bridgeit.TodoApp.model.UserRegistration;

/**
 * @author Pooja Rajwar
 *
 */
public class UserServices{
	
	@Autowired
	UserDao userDao;
	
	/* (non-Javadoc)
	 * @see com.bridgeit.TodoApp.services.UserServices#addUserRegister(com.bridgeit.TodoApp.model.UserRegistration)
	 * it adds User's related all data inside 'ToDoGoogleKeep' dataBase of 'UserRegistration' Table
	 */
	@Transactional
	public Object addUserRegister(User userRegistration, String string) throws NoSuchAlgorithmException, InvalidKeySpecException 
	{
		return userDao.addUserRegister(userRegistration,string);
	}
	
	public User loginUser(String emailId,String password) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		return userDao.loginUser(emailId,password);
		
	}

	public User getUserById(String id) {
		return userDao.getUserById(id);
	}

	public List<User> getUserList() {
		return userDao.getUserList();
	}
	
	@Transactional
	public Object updateUser(User userRegistration) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return userDao.updateUser(userRegistration);
	}

	@Transactional
	public boolean deleteUser(long userId) {
		return userDao.deleteUser(userId);
	}

}
