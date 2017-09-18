package com.bridgeit.TodoApp.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.TodoApp.JSONResponse.ErrorResponse;
import com.bridgeit.TodoApp.JSONResponse.Response;
import com.bridgeit.TodoApp.JSONResponse.TokenResponse;
import com.bridgeit.TodoApp.JSONResponse.UserResponse;
import com.bridgeit.TodoApp.model.Token;
import com.bridgeit.TodoApp.model.User;
import com.bridgeit.TodoApp.services.TokenServices;
import com.bridgeit.TodoApp.services.UserServices;

@RestController
public class LoginController {

	@Autowired
	UserServices userServices;
	
	@Autowired
	TokenServices tokenservices;

	protected static Logger logger = Logger.getLogger("login");

	@RequestMapping(value="UserLogin",method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> loginUser(@RequestBody Map<String, String> loginMap,HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println(loginMap.get("userName")+" "+loginMap.get("password"));
		Response response1=new Response();
		User user=null;		
		
		String accessToken=UUID.randomUUID().toString().replaceAll("-", "");
		String refreshToken=UUID.randomUUID().toString().replaceAll("-", "");
		
		System.out.println("Access Token is :: "+accessToken+"\nRefresh Token is :: "+refreshToken );
		
		try
		{
			user=userServices.loginUser(loginMap.get("userName"),loginMap.get("password"));
			
			logger.debug("User Login executed!");
			
			/*System.out.println(new Date());
			System.out.println(user);*/		
		}
		
		catch (Exception e) 
		{
			System.out.println(e);
			ErrorResponse errorResponse=new ErrorResponse();
			errorResponse.setStatus(500);
			errorResponse.setMessage("some internal server error is occured...");
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
		}
		
		if(user==null)
		{
			response1.setStatus(404);
			response1.setMessage("user Not found inside database...");
			return new ResponseEntity<Response>(response1,HttpStatus.OK);
		}
		
		if(user.getVerifyEmail()==null || user.getVerifyEmail()=="false")
		{
			response1.setStatus(-2);
			response1.setMessage("user email id is not vrified plzz verify ur email id first.....");
			return new ResponseEntity<Response>(response1,HttpStatus.OK);
		}
		//user is not null so add 'User' inside the session
		HttpSession session=request.getSession();
		session.setAttribute("user", user);


		//create token object and set the value for it
		Token token=new Token();
		token.setCreatedDate(new Date());
		token.setAccessToken(accessToken);
		token.setRefreshToken(refreshToken);
		token.setUserId(user.getId());
		
		//---------genreted token add into database-----------------------
		System.out.println("genered token is :: "+token);
		tokenservices.addToken(token);
		
		//-----------token add to header----------------------------------
		response.setHeader("accessToken", token.getAccessToken());
		System.out.println("accessToken inside the header :: :: "+response.getHeader("accessToken"));
		
		//--------send success response to user--------------------------- 
		TokenResponse tokenResponse=new TokenResponse();
		tokenResponse.setStatus(200);
		tokenResponse.setMessage("user logged successfully...");
		tokenResponse.setAccessToken(token.getAccessToken());
		tokenResponse.setRefreshToken(token.getRefreshToken());
		tokenResponse.setToken(token);
		
		/*-------------token add inside Cookie----------------------------
	 	Cookie cookie=new Cookie("AccessToken", token.getAccessToken());
		response.addCookie(cookie);
		System.out.println("cookie value :: "+cookie.getValue());*/
		
		return new ResponseEntity<Response>(tokenResponse,HttpStatus.OK);
	}
	
	@RequestMapping(value="refreshToken",method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> refreshToken(@RequestBody Map<String, String> refreshTokenMap,HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("Access Token is :: "+refreshTokenMap.get("accessToken")+"\nRefresh Token is :: "+refreshTokenMap.get("refreshToken") );
		TokenResponse tokenResponse=new TokenResponse();
		
		String accessToken=refreshTokenMap.get("accessToken");
		String refreshToken=refreshTokenMap.get("refreshToken");
		
		if (accessToken == null || accessToken.trim().isEmpty()) {
			tokenResponse.setStatus(-1);
			tokenResponse.setMessage("accessToken is missing");
			return new ResponseEntity<Response>(tokenResponse,HttpStatus.OK);
		}
		
		Token token = null;
		try {
			token = tokenservices.getToken(accessToken);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		System.out.println("authetication token from database :: "+token.getAccessToken());
		
		if (token.getAccessToken() == null) {
			tokenResponse.setStatus(-1);
			tokenResponse.setMessage("Invalid access Token.");
			return new ResponseEntity<Response>(tokenResponse,HttpStatus.OK);
		}
		
		Date currentDate = new Date();
		Date date = token.getCreatedDate();

		long difference = currentDate.getTime() - date.getTime();
		long differenceInSeconds = TimeUnit.MILLISECONDS.toSeconds(difference);
		if (differenceInSeconds > 60*2) // 2 min
		{
			try {
				tokenservices.deleteToken(token);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tokenResponse.setStatus(-2);
			tokenResponse.setMessage("Refresh token is expired u have to login again");
			return new ResponseEntity<Response>(tokenResponse,HttpStatus.OK);
		}
		
		HttpSession httpSession=request.getSession();
		User user=(User) httpSession.getAttribute("user");
		accessToken=UUID.randomUUID().toString().replaceAll("-", "");
		refreshToken=UUID.randomUUID().toString().replaceAll("-", "");
		
		token.setId(token.getId());
		token.setCreatedDate(token.getCreatedDate());
		token.setAccessToken(accessToken);
		token.setRefreshToken(refreshToken);
		token.setUserId(user.getId());
		
		try {
			tokenservices.addToken(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tokenResponse.setStatus(200);
		tokenResponse.setMessage("accessToken Genreted successfully...");
		tokenResponse.setAccessToken(accessToken);
		tokenResponse.setRefreshToken(refreshToken);
		tokenResponse.setToken(token);
		return new ResponseEntity<Response>(tokenResponse,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="UserLogout",method=RequestMethod.DELETE)
	public ResponseEntity<Response> userLogout(@RequestBody String accessToten1,HttpServletRequest request,HttpServletResponse response)
	{
		String accessToken=null;
		TokenResponse tokenResponse=new TokenResponse();
		
		accessToken =  accessToten1;
		
		if (accessToken == null || accessToken.trim().isEmpty()) {
			tokenResponse.setMessage("access token is null...");
			tokenResponse.setStatus(404);
			return new ResponseEntity<Response>(tokenResponse,HttpStatus.OK);
		}
		
		Token token = null;
		try {
			token = tokenservices.getToken(accessToken);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		System.out.println("authetication token from database :: "+token.getAccessToken());
		
		if (token.getAccessToken() == null) {
			tokenResponse.setStatus(400);
			tokenResponse.setMessage("Invalid access Token.");
			return new ResponseEntity<Response>(tokenResponse,HttpStatus.OK);
		}
		
		try {
			tokenservices.deleteToken(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tokenResponse.setMessage("logout successfully...");
		tokenResponse.setStatus(200);
		return new ResponseEntity<Response>(tokenResponse,HttpStatus.OK);
	}
	
	@RequestMapping(value="forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody String emailId,HttpServletRequest request) throws FileNotFoundException, IOException
	{	
		UserResponse userResponse=new UserResponse();
		User user=null;
		
		
		try {
			user=userServices.getUserByEmail(emailId);
		} catch (Exception e1) {
			userResponse.setStatus(500);
			userResponse.setMessage("exception occurs...");
			return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
		}
		if(user==null)
		{
			userResponse.setStatus(404);
			userResponse.setMessage("Email id is not found in records....invalid email id....");
			return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
		}
		
		//Random rnd = new Random();
		//int sixDigitCode = 100000 + rnd.nextInt(900000);
		String token=UUID.randomUUID().toString().replaceAll("-", "");
		request.getSession().setAttribute(token, user);
		
		final String username = "demorajwar@gmail.com";
		final String password = "rajwar@123";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("demorajwar@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(emailId));
			message.setSubject("ToDoNotes password reset");
			
			/*StringWriter writer = new StringWriter();
			IOUtils.copy(new FileInputStream(new File("template/home.html")), writer);
			message.setContent(writer.toString(), "text/html");*/
			
			message.setText("We received a request to reset your Facebook password.");
			/*message.setContent("<a href='http://localhost:8080/TodoApp/validateCode?codeToken="+token+"'>Click here to change your password.</a>"+
			"<br>Alternatively, you can enter the following password reset code:<br><h2>"+sixDigitCode+"</h2>", "text/html");*/
			
			message.setContent("<a href='http://localhost:8080/TodoApp/validateCode?userToken="+token+"'>Click here to change your password.</a>","text/html");
			
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		userResponse.setStatus(200);
		userResponse.setMessage("code send via email successfully....");
		return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
	}
	
	@RequestMapping(value="validateCode")
	public void validateCode(HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException
	{
		System.out.println("validate code :: ");
		String codeToken=request.getParameter("codeToken");
		User  user =(User) request.getSession().getAttribute(codeToken);
		String token=UUID.randomUUID().toString().replaceAll("-", "");
		request.getSession().setAttribute(token, user);
		
		if(user!=null)
		{
			response.sendRedirect("http://localhost:8080/TodoApp/#!/passwordChange?token="+token);
		}
	}
}
