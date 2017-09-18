package com.bridgeit.TodoApp.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.TodoApp.JSONResponse.ErrorResponse;
import com.bridgeit.TodoApp.JSONResponse.RegisterErrorResponse;
import com.bridgeit.TodoApp.JSONResponse.Response;
import com.bridgeit.TodoApp.JSONResponse.UserResponse;
import com.bridgeit.TodoApp.model.User;
import com.bridgeit.TodoApp.services.UserServices;
import com.bridgeit.TodoApp.validator.RegisterValidation;
import com.bridgeit.TodoApp.validator.UserEmailValidator;

@RestController
@RequestMapping(value="/")
public class UserController 
{
	@Autowired
	UserServices userServices;
	
	@Autowired
	RegisterValidation registerValidation;

	protected static Logger logger = Logger.getLogger("controller");

	protected static Logger logger1 = Logger.getRootLogger();
	
	/**
	 * 1) this method add the user inside the `ToDoGoogleKeep` database of `User` table
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="userRegister", method=RequestMethod.POST)
	public ResponseEntity<Response> addUserRegister(@RequestBody User user,BindingResult bindingResult,HttpServletRequest request)
	{
		logger1.debug("addUserRegister547356 is executed!");
		
		boolean userRegistered=false;
		
		RegisterErrorResponse registerErrorResponse=new RegisterErrorResponse();
		
		System.out.println(user);
		registerValidation.validate(user, bindingResult);

		//logs debug message
		
		logger.debug("addUserRegister is executed!");
		
		//logs exception
		logger.error("This is Error message", new Exception("Testing"));
		
		if(bindingResult.hasErrors())
		{
			List<FieldError> fieldErrorsList=bindingResult.getFieldErrors();
			registerErrorResponse.setErrorList(fieldErrorsList);
			registerErrorResponse.setStatus(400);
			registerErrorResponse.setMessage("some binding problem occure...(validation problem)");
			return new ResponseEntity<Response>(registerErrorResponse,HttpStatus.OK); 
		}
		
		try
		{
			userRegistered=(Boolean) userServices.addUserRegister(user,"mannual");
		}
		
		catch (Exception e) {
			ErrorResponse errorResponse=new ErrorResponse();
			errorResponse.setStatus(500);
			errorResponse.setMessage("some internal Database server problem... so user not registered successfully...");
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK); 
		}
		long userId=user.getId();
		String emailID=user.getEmail();
		String token=UUID.randomUUID().toString().replaceAll("-", "");
		
		request.getSession().setAttribute(token, userId);
		UserEmailValidator.mailVerification(emailID,token);
		
		registerErrorResponse.setStatus(200);
		registerErrorResponse.setMessage("user registered sucessfully....");
		return new ResponseEntity<Response>(registerErrorResponse,HttpStatus.OK); 
	}
	
	@RequestMapping(value="verifyMail")
	public void verifyMail(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		User user=null;
		UserResponse userResponse=new UserResponse();
		String userToken=request.getParameter("userToken");
		long userid =(Long) request.getSession().getAttribute(userToken);
		System.out.println("userid by session :: "+userid);
		
		try {
			user =userServices.getUserById(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*if(user==null)
		{
			userResponse.setStatus(-1);
			userResponse.setMessage("invalid user email id....");
			return new ResponseEntity<Response>(userResponse,HttpStatus.OK); 
		}*/
		user.setVerifyEmail("true");
		try {
			userServices.updateUser(user);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
	/*	userResponse.setStatus(200);
		userResponse.setMessage("user Email id verified successfully now plzz login....");
		return new ResponseEntity<Response>(userResponse,HttpStatus.OK);*/
		response.sendRedirect("http://localhost:8080/TodoApp/#!/login");
		
	}
	/*-----------------------------------updateUser--------------------------------------------*/
	/**
	 * 
	 *2) this method controller update the user data inside the `ToDoGoogleKeep` database of `User_Registration` table
	 * @param userRegistration {@link UserRegistration}
	 * @param httpServletRequest {@link HttpServletRequest}
	 * @return {@link ResponseEntity<Response>}
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * 
	 */
	@RequestMapping(value="userUpdate",method=RequestMethod.PUT)
	public ResponseEntity<Response> updateUser(@RequestBody User user,HttpServletRequest httpServletRequest) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		boolean update=false;
		UserResponse userResponse=new UserResponse();
		/*HttpSession httpSession=httpServletRequest.getSession();
		User user=(User) httpSession.getAttribute("user");*/
		long userId=user.getId();
		String profilepic=user.getProfileImage();
		User getUserById=null;
		try
		{
			getUserById=userServices.getUserById(userId);
		}
		catch(Exception e)
		{
			System.out.println(e);
			ErrorResponse errorResponse=new ErrorResponse();
			errorResponse.setStatus(-1);
			errorResponse.setMessage("500 internal server error...");
			return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
		}
		
		if(getUserById==null)
		{
			userResponse.setStatus(-1);
			userResponse.setMessage("User Not Found...");
			return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
		}
		
		try {
			getUserById.setProfileImage(profilepic);
			update=(Boolean) userServices.updateUser(getUserById);
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			ErrorResponse errorResponse=new ErrorResponse();
			errorResponse.setStatus(-1);
			errorResponse.setMessage("some internal error is occured...");
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		
		if(!update)
		{
			userResponse.setStatus(-1);
			userResponse.setMessage("User data not Updated....");
			return new ResponseEntity<Response>(userResponse,HttpStatus.NOT_FOUND);
		}
		userResponse.setUserRegistration(getUserById);
		userResponse.setStatus(200);
		userResponse.setMessage("User data Updated successfully....");
		
		return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
	}
	
	/**
	 * 3) this method controller Delete the user data from the `ToDoGoogleKeep` database of `User_Registration` table
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value="userDelete",method=RequestMethod.DELETE)
	public ResponseEntity<Response> deleteUser(HttpServletRequest httpServletRequest)
	{
		HttpSession httpSession=httpServletRequest.getSession();
		User user=(User) httpSession.getAttribute("user");
		long userId=user.getId();
	
		boolean deleteuser = userServices.deleteUser(userId);
		UserResponse userResponse=new UserResponse();
		userResponse.setMessage("user deleted successfully...");
		userResponse.setStatus(1);
		
		return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
	}
	
	
	/*---getUserByName------------*/
	@RequestMapping(value="User", method=RequestMethod.GET)
	public ResponseEntity<Response> getUserById(@PathVariable("userName") String email )
	{
		User userRegistration=null;
		UserResponse userResponse=new UserResponse();
		try
		{
			userRegistration=userServices.getUserByEmail(email);
			System.out.println(userRegistration);
			userResponse.setStatus(1);
			userResponse.setMessage("User Found...");
			userResponse.setUserRegistration(userRegistration);
		}
		catch(Exception e)
		{
			System.out.println(e);
			ErrorResponse errorResponse=new ErrorResponse();
			errorResponse.setStatus(-1);
			errorResponse.setMessage("500 internal server error...");
			return new ResponseEntity<Response>(userResponse,HttpStatus.NOT_FOUND);
		}
		
		if(userRegistration==null)
		{
			userResponse.setStatus(-1);
			userResponse.setMessage("User Not Found...");
		}
		return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
	}
	
	/*--------------------------getUserList--------------------------*/
	/**
	 * this Controller method gives all list of user from `ToDoGoogleKeep` database of `User_Registration` table..
	 * @return {@link ResponseEntity<Response>}
	 */
	@RequestMapping(value="getUserList", method=RequestMethod.GET)
	public ResponseEntity<Response> getUserList()
	{
		List<User> list=null;
		UserResponse userResponse=new UserResponse();
		try
		{
			list=userServices.getUserList();
			userResponse.setStatus(1);
			userResponse.setMessage("following are the UserList");
			userResponse.setList(list);
		}
		catch(Exception e)
		{
			System.out.println(e);
			ErrorResponse errorResponse=new ErrorResponse();
			errorResponse.setStatus(-1);
			errorResponse.setMessage("some internal error is occured...");
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		
		if(list==null)
		{
			userResponse.setStatus(-1);
			userResponse.setMessage("User List is Empty....");
			return new ResponseEntity<Response>(userResponse,HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
	}
	
	
}
