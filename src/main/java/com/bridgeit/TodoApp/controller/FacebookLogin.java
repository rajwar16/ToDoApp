package com.bridgeit.TodoApp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.api.FacebookApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.bridgeit.TodoApp.JSONResponse.ErrorResponse;
import com.bridgeit.TodoApp.JSONResponse.RegisterErrorResponse;
import com.bridgeit.TodoApp.JSONResponse.Response;
import com.bridgeit.TodoApp.model.FacebookProfile;
import com.bridgeit.TodoApp.model.Picture;
import com.bridgeit.TodoApp.model.User;
import com.bridgeit.TodoApp.services.FacebookUtil;
import com.bridgeit.TodoApp.services.OAuthServiceConfig;
import com.bridgeit.TodoApp.services.UserServices;

@RestController
public class FacebookLogin {
	/*private static final String FACEBOOK = "facebook";
	private static final String PUBLISH_SUCCESS = "success";
	private static final String PUBLISH_ERROR = "error";
	
	@Autowired
	private ConnectionFactoryRegistry connectionFactoryRegistry;

	@Autowired
	private OAuth2Parameters oAuth2Parameters;

	@Autowired
	FacebookUtil facebookUtil;

	@Autowired
	@Qualifier("facebookServiceProvider")
	private OAuthServiceConfig<FacebookApi> facebookServiceProvider;*/

	
	@Autowired
	UserServices userServices;
	@RequestMapping(value = "facebookLogin")
	public void facebookLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("facebook login");
		String facebookUrlLink = FacebookUtil.getFacebookUrl();
		response.sendRedirect(facebookUrlLink);
	}
	
	@RequestMapping(value = "facebookUrl")
	public ResponseEntity<Response> facebookUrl(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String facebookCode=request.getParameter("code");
		System.out.println("facebookUrl login :: "+facebookCode);
		
		String facebookToken=FacebookUtil.facebookAccessToken(facebookCode);
		FacebookProfile facebookProfileData= FacebookUtil.facebookProfile(facebookToken);
		System.out.println("facebook login profile :: "+facebookProfileData);
		Picture picture=facebookProfileData.getPicture();
		
		User user =new User();
		boolean userRegistered=false;
		RegisterErrorResponse registerErrorResponse=new RegisterErrorResponse();
		
		user.setUserName(facebookProfileData.getName());
		user.setEmail(facebookProfileData.getEmail());
		user.setFacebookProfile(picture.getData().getUrl());
		
		try
		{
			userRegistered=(Boolean) userServices.addUserRegister(user,"facebook");
		}
		
		catch (Exception e) {
			e.printStackTrace();
			ErrorResponse errorResponse=new ErrorResponse();
			errorResponse.setStatus(500);
			errorResponse.setMessage("some internal Database server problem");
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK); 
		}
		
		if (!userRegistered) {
			registerErrorResponse.setStatus(404);
			registerErrorResponse.setMessage("user not registered....");
			return new ResponseEntity<Response>(registerErrorResponse,HttpStatus.OK); 
		}
		
		registerErrorResponse.setStatus(200);
		registerErrorResponse.setMessage("user registered sucessfully....");
		registerErrorResponse.setAccessToken(facebookToken);
		return new ResponseEntity<Response>(registerErrorResponse,HttpStatus.OK); 
	}
	

	
	/*@RequestMapping(value = "facebookLogin")
	public ModelAndView facebookLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("facebook login");
		
		FacebookConnectionFactory facebookConnectionFactory = (FacebookConnectionFactory) connectionFactoryRegistry.getConnectionFactory(FACEBOOK);
		OAuth2Operations oauthOperations = facebookConnectionFactory.getOAuthOperations();
		oAuth2Parameters.setState("recivedfromfacebooktoken");
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
		RedirectView redirectView = new RedirectView(authorizeUrl, true, true,true);

		return new ModelAndView(redirectView);
		
	}*/

}