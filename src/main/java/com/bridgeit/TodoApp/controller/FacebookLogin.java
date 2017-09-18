package com.bridgeit.TodoApp.controller;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.TodoApp.JSONResponse.RegisterErrorResponse;
import com.bridgeit.TodoApp.JSONResponse.Response;
import com.bridgeit.TodoApp.JSONResponse.TokenResponse;
import com.bridgeit.TodoApp.model.FacebookProfile;
import com.bridgeit.TodoApp.model.Picture;
import com.bridgeit.TodoApp.model.Token;
import com.bridgeit.TodoApp.model.User;
import com.bridgeit.TodoApp.services.FacebookUtil;
import com.bridgeit.TodoApp.services.TokenServices;
import com.bridgeit.TodoApp.services.UserServices;


@RestController
public class FacebookLogin {
	@Autowired
	UserServices userServices;
	
	@Autowired
	TokenServices tokenservices;
	
	@RequestMapping(value = "facebookLogin")
	public void facebookLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("facebook login:: ");
		String facebookUrlLink = FacebookUtil.getFacebookUrl();
		response.sendRedirect(facebookUrlLink);
	}
	
	@RequestMapping(value = "facebookUrl")
	public void facebookUrl(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		
		String facebookCode=request.getParameter("code");
		System.out.println("facebookUrl login :: "+facebookCode);
		
		String facebookToken=FacebookUtil.facebookAccessToken(facebookCode);
		FacebookProfile facebookProfileData= FacebookUtil.facebookProfile(facebookToken);
		System.out.println("facebook login profile :: "+facebookProfileData);
		Picture picture=facebookProfileData.getPicture();
		String email=facebookProfileData.getEmail();
		User user=null;
		
		boolean userRegistered=false;
		RegisterErrorResponse registerErrorResponse=new RegisterErrorResponse();
		
		try {
			user=userServices.getUserByEmail(email);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if(user==null)
		{
			user =new User();
			user.setUserName(facebookProfileData.getName());
			user.setEmail(facebookProfileData.getEmail());
			user.setFacebookProfile(picture.getData().getUrl());
			
			try
			{
				userRegistered=(Boolean) userServices.addUserRegister(user,"facebook");
			}
			
			catch (Exception e) {
				e.printStackTrace();
			}
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
		
		System.out.println("hiii.....");
		response.sendRedirect("http://localhost:8080/TodoApp/#!/facebookLoginComplete?TempTokenKey=TempToken");
	}
	
	@RequestMapping(value="FacebookTokenKey",method = RequestMethod.POST)
	public ResponseEntity<Response> FacebookTokenKey(@RequestBody String tokenKey,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		System.out.println("Tokken key"+tokenKey);
		Token token=(Token) httpServletRequest.getSession().getAttribute(tokenKey);
		
		TokenResponse tokenResponse=new TokenResponse();
		tokenResponse.setStatus(200);
		tokenResponse.setMessage("user logged successfully...");
		tokenResponse.setAccessToken(token.getAccessToken());
		tokenResponse.setRefreshToken(token.getRefreshToken());
		tokenResponse.setToken(token);
		
		return new ResponseEntity<Response>(tokenResponse,HttpStatus.OK);
	}
}