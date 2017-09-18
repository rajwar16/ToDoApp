package com.bridgeit.TodoApp.services;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.stereotype.Service;

import com.bridgeit.TodoApp.model.FacebookProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GmailUtil 
{
	public static final String Gimail_APP_ID = "913913337450-105q2a8ccpbn9n0m7uavpd945cph6cn9.apps.googleusercontent.com";
	public static final String Gimail_APP_SECRET = "sNyZ_kE-xqD8eARvchQU09rE";
	public static final String REDIRECT_URI = "http://localhost:8080/TodoApp/googleRedirect";
	

	public static String gmailLoginUrl()
	{
		String gmailLoginUrl="";
		
		try {
			gmailLoginUrl = "https://accounts.google.com/o/oauth2/auth?client_id="+Gimail_APP_ID+"&redirect_uri="+URLEncoder.encode(REDIRECT_URI,"UTF-8")+"&response_type=code&scope=profile email&approval_prompt=force&access_type=offline";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return gmailLoginUrl;
	}

	public static String gmailAccessToken(String gmailCode) {
		
		String googleaccesstokenurl="";
		try{
				googleaccesstokenurl="https://accounts.google.com/o/oauth2/token";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(googleaccesstokenurl);
		
		Form form = new Form();
		form.param("client_id", Gimail_APP_ID);
		form.param("client_secret", Gimail_APP_SECRET);
		form.param("redirect_uri", REDIRECT_URI);
		form.param("code", gmailCode);
		form.param("grant_type", "authorization_code");
		
		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(form));
		
		String token = response.readEntity(String.class);
		
		System.out.println("GOOGLE TOKEN"+token);
		
		ObjectMapper mapper = new ObjectMapper();
		String googletoken=null;
		
		try {
			googletoken = mapper.readTree(token).get("access_token").asText();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.close();
		return googletoken;
	}

	public static JsonNode googleProfile(String googleToken) {
		
		String googleprofileurl="https://www.googleapis.com/plus/v1/people/me";
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(googleprofileurl);
	
		String headerAuth="Bearer "+googleToken;
		System.out.println("accestoken google"+googleToken);
		
		Response response = target.request().header("Authorization", headerAuth).accept(MediaType.APPLICATION_JSON).get();		
	
		String profile = response.readEntity(String.class);
		System.out.println("google profile is :"+profile);
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode gmailprofile = null;
		
		try {
			
			 gmailprofile = mapper.readTree(profile);
		
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gmailprofile;
	}
	
	
}
