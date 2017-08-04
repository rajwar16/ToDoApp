package com.bridgeit.TodoApp.JSONResponse;
import com.bridgeit.TodoApp.model.Token;

public class TokenResponse extends Response
{
	private String accessToken;
	private String refreshToken;
	private Token token;
	
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	@Override
	public String toString() {
		return "TokenResponse [accessToken=" + accessToken + ", refreshToken=" + refreshToken + ", token=" + token
				+ "]";
	}
	
	
}
