package com.bridgeit.TodoApp.model;

public class FacebookToken 
{
	String access_token;
	String token_type;
	String expires_in;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	
	@Override
	public String toString() {
		return "FacebookToken [access_token=" + access_token + ", token_type=" + token_type + ", expires_in="
				+ expires_in + "]";
	}
	
	

}
