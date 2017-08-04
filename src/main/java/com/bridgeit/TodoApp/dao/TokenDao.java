package com.bridgeit.TodoApp.dao;

import com.bridgeit.TodoApp.model.Token;

public interface TokenDao 
{
	public Token addToken(Token token);
	public Token getToken(String AccessToken);
	public Object deleteToken(Token token);
}
