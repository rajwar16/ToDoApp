package com.bridgeit.TodoApp.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.TodoApp.model.Token;

public class TokenDaoImplementation implements TokenDao
{
	@Autowired 
	SessionFactory sessionFactory;
	public Token addToken(Token token) 
	{
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(token);		
		return token;
	}

	public Token getToken(String AccessToken) 
	{
		Session session=sessionFactory.openSession();
		
		Criteria criteria=session.createCriteria(Token.class);
		Token token = null;
		try {
			token = (Token) criteria.add(Restrictions.conjunction().add(Restrictions.eq("AccessToken", AccessToken))).uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		if(token==null)
		{
			return null;
		}
		return token;
	}

	public Object deleteToken(Token token) {
		Session session=sessionFactory.getCurrentSession();
		session.delete(token);		
		return null;
	}


	/*public Token getTokenByRefreshToken(String refreshToken) {
		return refreshToken;
	}*/

}
