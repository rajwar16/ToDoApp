package com.bridgeit.TodoApp.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.bridgeit.TodoApp.model.FacebookProfile;
import com.bridgeit.TodoApp.model.FacebookToken;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FacebookUtil 
{
	public static final String FB_APP_ID = "194065034464695";
	public static final String FB_APP_SECRET = "b56e5ff2407786e11c39afae8815e84c";
	public static final String REDIRECT_URI = "http://localhost:8080/TodoApp/facebookUrl";
	
	public static String Facebook_GET_USER_URL = "https://graph.facebook.com/v2.9/me?access_token=";
	public static String  binding = "&fields=id,name,email,first_name,last_name,picture";
	
	public static String getFacebookUrl()
	{
		String facebookLoginUrl="";
		
		try {
			facebookLoginUrl="https://www.facebook.com/v2.10/dialog/oauth?client_id=" + FB_APP_ID + "&redirect_uri="
								+ URLEncoder.encode(REDIRECT_URI, "UTF-8")+"&state="+123456789+"&response_type=code"+"&scope=public_profile,email";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return facebookLoginUrl;
	}
	
	public static String facebookAccessToken(String code) {
		String urlAccessToken="";
		try {
			urlAccessToken="https://graph.facebook.com/v2.10/oauth/access_token?" + "client_id=" + FB_APP_ID
							+ "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8") + "&client_secret=" + FB_APP_SECRET 
							+ "&code=" + code;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ResteasyClient facebookClient=new ResteasyClientBuilder().build();
		ResteasyWebTarget resteasyWebTarget=facebookClient.target(urlAccessToken);
		
		Response response=resteasyWebTarget.request().accept(MediaType.APPLICATION_JSON_VALUE).get();
		FacebookToken facebookToken=response.readEntity(FacebookToken.class);
		System.out.println("facebook token :: "+facebookToken);
		
		facebookClient.close();
		return facebookToken.getAccess_token();
	}
	
	public static FacebookProfile facebookProfile(String facebookToken)
	{
		String profileurl="";
		profileurl=Facebook_GET_USER_URL+facebookToken+binding;
		
		ResteasyClient facebookClient=new ResteasyClientBuilder().build();
		ResteasyWebTarget resteasyWebTarget=facebookClient.target(profileurl);
		Response response=resteasyWebTarget.request().accept(MediaType.APPLICATION_JSON_VALUE).get();
		
		String facebookprofile=response.readEntity(String.class);
		
		ObjectMapper objectMapper=new ObjectMapper();
		FacebookProfile facebookprofile1=null;
		try {
			 facebookprofile1 = objectMapper.readValue(facebookprofile, FacebookProfile.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		System.out.println("facebook profile :: "+facebookprofile);

		return facebookprofile1;
	}
}
