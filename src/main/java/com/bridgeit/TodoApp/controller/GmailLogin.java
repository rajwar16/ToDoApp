package com.bridgeit.TodoApp.controller;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.TodoApp.JSONResponse.RegisterErrorResponse;
import com.bridgeit.TodoApp.model.FacebookProfile;
import com.bridgeit.TodoApp.model.Picture;
import com.bridgeit.TodoApp.model.Token;
import com.bridgeit.TodoApp.model.User;
import com.bridgeit.TodoApp.services.FacebookUtil;
import com.bridgeit.TodoApp.services.GmailUtil;
import com.bridgeit.TodoApp.services.TokenServices;
import com.bridgeit.TodoApp.services.UserServices;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class GmailLogin 
{

	@Autowired
	UserServices userServices;
	
	@Autowired
	TokenServices tokenservices;
	
	@RequestMapping(value = "gmailLogin")
	public void gmailLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("gmailLogin login:: ");
		String gmailUrlLink = GmailUtil.gmailLoginUrl();
		System.out.println("gmail url link :: "+gmailUrlLink);
		response.sendRedirect(gmailUrlLink);
	}
	
	
	@RequestMapping(value = "googleRedirect")
	public void facebookUrl(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		
		String googleCode=request.getParameter("code");
		System.out.println("googleRedirect login :: "+googleCode);
		
		String googleToken=GmailUtil.gmailAccessToken(googleCode);
		System.out.println("google access token :: "+googleToken);
		
//		------------------------------------------------------------------------------
		JsonNode googleProfileData= GmailUtil.googleProfile(googleToken);
		System.out.println(googleProfileData.get("name").get("givenName").asText());
		System.out.println(googleProfileData.get("displayName").asText());
		System.out.println(googleProfileData.get("emails").get(0).get("value").asText());
		System.out.println(googleProfileData.get("image").get("url").asText());
		
		Picture picture=null;
		String userName=googleProfileData.get("displayName").asText();
		String userEmail=googleProfileData.get("emails").get(0).get("value").asText();
		String imageUrl=googleProfileData.get("image").get("url").asText();
		
		User user =new User();
		boolean userRegistered=false;
		RegisterErrorResponse registerErrorResponse=new RegisterErrorResponse();
		
		user.setUserName(userName);
		user.setEmail(userEmail);
		user.setFacebookProfile(imageUrl);
		
		try
		{
			userRegistered=(Boolean) userServices.addUserRegister(user,"facebook");
			System.out.println("userId is :: "+user.getId());
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!userRegistered) {
			response.sendRedirect("http://localhost:8080/TodoApp/#!/login");
		}
		
		String accessToken=UUID.randomUUID().toString().replaceAll("-", "");
		String refreshToken=UUID.randomUUID().toString().replaceAll("-", "");
		Token token=new Token();
		token.setCreatedDate(new Date());
		token.setAccessToken(accessToken);
		token.setRefreshToken(refreshToken);
		token.setUserId(user.getId());
		tokenservices.addToken(token);
		
		HttpSession session=request.getSession();
		session.setAttribute("TempToken", token);
		
		System.out.println("hiii............");
		
		response.sendRedirect("http://localhost:8080/TodoApp/#!/facebookLoginComplete?TempTokenKey=TempToken");
	}
	
	
}
