package com.bridgeit.TodoApp.redisUtil;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.bridgeit.TodoApp.model.Token;
@Repository
public class TokenRepositoryImpl implements TokenRepository{

	private static final String KEY = "Token";
	
	private RedisTemplate<String, Token> redisTemplate;
	private HashOperations hashOps;
	
	public TokenRepositoryImpl() {
		
	}

	@Autowired
    public TokenRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
 
    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }
	
	public void saveToken(Token token) {
		System.out.println("token repository :: "+token);
		hashOps.put(KEY, token.getAccessToken(), token);
	}

	public Token findToken(String id) {
        return (Token) hashOps.get(KEY, id);
    }
}
