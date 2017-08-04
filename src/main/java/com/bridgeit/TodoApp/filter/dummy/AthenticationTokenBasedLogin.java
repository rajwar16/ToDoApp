package com.bridgeit.TodoApp.filter.dummy;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bridgeit.TodoApp.JSONResponse.ErrorResponse;
import com.bridgeit.TodoApp.model.Token;
import com.bridgeit.TodoApp.services.TokenServices;

public class AthenticationTokenBasedLogin implements Filter
{
	/*@Autowired
	TokenServices tokenServices;*/
	
	public void init(FilterConfig filterConfig) throws ServletException {
		/*ServletContext servletContext = filterConfig.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		
		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
		
		autowireCapableBeanFactory.configureBean(this, "ToDoServices");*/
	}

	public void doFilter(ServletRequest request1, ServletResponse response1, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("servlet context :: "+request1.getServletContext());
		
		
//		System.out.println("tokenService object :: "+tokenService);
		
		HttpServletRequest request = (HttpServletRequest) request1;
		HttpServletResponse response = (HttpServletResponse) response1;
		
		/*String accessToken = request.getHeader("access_token");*/
		String accessToken = null;
		String refreshToken= null;

		/*if (accessToken == null || accessToken.trim().isEmpty()) {*/
			Cookie[] cks = request.getCookies();
			
			System.out.println("cookies :: "+cks);
			
			if (cks != null) {
				System.out.println("cks if condition :: ");
				for (Cookie cookie : cks){
					System.out.println("cookiesss :: "+cookie.getName());
					if (cookie.getName().equals("AccessToken")) {
						accessToken = cookie.getValue();
						
						System.out.println(accessToken);
					}
				}
			}
//		}
		
		if (accessToken == null || accessToken.trim().isEmpty()) {
			ErrorResponse er = new ErrorResponse();
			er.setStatus(-1);
			er.setMessage("Invalid credential...user not found");

			response.setContentType("application/json");
			String jsonResp = "{\"status\":\"-2\",\"errorMessage\":\"Invalid access token\"}";
			response.getWriter().write(jsonResp);
			return;
		}
		
		
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(request1.getServletContext());

		TokenServices tokenService = (TokenServices) applicationContext.getBean("tokenservices");
		
		System.out.println("access token :: "+accessToken);
		System.out.println("token service bean :: "+tokenService);
		Token token = tokenService.getToken(accessToken);
		
		
		System.out.println("authetication token from database :: "+token.getAccessToken());
		
		if (token.getAccessToken() == null) {
			response.setContentType("application/json");
			String jsonResp = "{\"status\":\"-1\",\"errorMessage\":\"Invalid access token\"}";
			response.getWriter().write(jsonResp);
			return;
		}
		
		Date currentDate = new Date();
		Date date = token.getCreatedDate();

		long difference = currentDate.getTime() - date.getTime();
		long differenceInSeconds = TimeUnit.MILLISECONDS.toSeconds(difference);
		if (differenceInSeconds > 60) // 60min
		{
			
			// generate json error response - access token is expired
			response.setContentType("application/json");
			String jsonResp = "expired";
			response.getWriter().write(jsonResp);
			return ;
			
			// generate json error response - access token is expired
						/*response.setContentType("application/json");
						String jsonResp = "{\"status\":\"-4\",\"errorMessage\":\"Access token is expired. Generate new Access Tokens\"}";
						response.getWriter().write(jsonResp);
						return ;
			*/
			/*
//			Token refreshToken = tokenService.getTokenByRefreshToken(accessToken1);
			refreshToken=token.getRefreshToken();
			
			if (refreshToken == null) {
				response.setContentType("application/json");
				String jsonResp = "{\"status\":\"-1\",\"errorMessage\":\"Invalid RefrshToken token\"}";
				response.getWriter().write(jsonResp);
				return;
			}
			
			Date currentDateRefresh = new Date();
			//getting refresh token from accessToken
			//Date dateRefresh = token.getCreatedDate();

			long differenceRefresh = currentDateRefresh.getTime() - date.getTime();
			long differenceRefreshInSeconds = TimeUnit.MILLISECONDS.toSeconds(differenceRefresh);
			if(differenceRefreshInSeconds < 2 * 60)
			{	
				accessToken=refreshToken;
				token.setCreatedDate(new Date());
			}
			
			else
			{
				tokenService.deleteToken(refreshToken);
				response.setContentType("application/json");
				String jsonResp = "{\"status\":\"-4\",\"errorMessage\":\"RefreshToken token is expired. Generate new Access Tokens\"}";
				response.getWriter().write(jsonResp);
				return;
			}*/
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
		
	}
	
}
