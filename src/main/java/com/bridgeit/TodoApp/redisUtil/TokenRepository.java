package com.bridgeit.TodoApp.redisUtil;

import com.bridgeit.TodoApp.model.Token;

public interface TokenRepository {
	
	void saveToken(Token token);
	
	Token findToken(String id);
}
