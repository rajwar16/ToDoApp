package com.bridgeit.TodoApp.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import com.bridgeit.TodoApp.model.User;

public interface UserDao 
{
	Object addUserRegister(User userRegistration, String string) throws NoSuchAlgorithmException, InvalidKeySpecException;

	User loginUser(String emailId, String password) throws NoSuchAlgorithmException, InvalidKeySpecException;

	List<User> getUserList();

	Object updateUser(User userRegistration) throws NoSuchAlgorithmException, InvalidKeySpecException;

	boolean deleteUser(long userId);

	User getUserByEmail(String email);

	User getUserById(long userId);
	
}
