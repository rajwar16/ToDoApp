package com.bridgeit.TodoApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.TodoApp.dao.TokenDao;
import com.bridgeit.TodoApp.model.Token;

public class TokenServices 
{
	@Autowired
	TokenDao tokenDao;
	
	@Transactional
	public Token addToken(Token token) 
	{
		return tokenDao.addToken(token);
	}

	public Token getToken(String accessToken) {
		return tokenDao.getToken(accessToken);
	}
	
	@Transactional
	public Object deleteToken(Token token) {
		return tokenDao.deleteToken(token);
	}
}
