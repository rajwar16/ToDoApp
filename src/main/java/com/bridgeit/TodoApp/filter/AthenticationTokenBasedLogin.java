package com.bridgeit.TodoApp.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bridgeit.TodoApp.JSONResponse.ErrorResponse;
import com.bridgeit.TodoApp.JSONResponse.TokenResponse;
import com.bridgeit.TodoApp.model.Token;
import com.bridgeit.TodoApp.services.TokenServices;

public class AthenticationTokenBasedLogin implements Filter
{
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request1, ServletResponse response1, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("servlet context :: "+request1.getServletContext());
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(request1.getServletContext());

		TokenServices tokenService = (TokenServices) applicationContext.getBean("tokenservices");
		/*TokenResponse tokenResponse=applicationContext.getBean(TokenResponse.class);
		System.out.println("token service object :: "+tokenService);*/
		
		String accessToken = null;
		
		HttpServletRequest request = (HttpServletRequest) request1;
		HttpServletResponse response = (HttpServletResponse) response1;
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		
		accessToken =  request.getHeader("accessToken");
		System.out.println("filter access token :: "+accessToken);
		
		if (accessToken == null || accessToken.trim().isEmpty()) {
			response.setContentType("application/json");
			String jsonResp = "{\"status\":\"-1\",\"errorMessage\":\"access token is missing\"}";
			response.getWriter().write(jsonResp);
			return;
		}
		
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
		if (differenceInSeconds > 60*30) // 30 min
		{
			// generate json error response - access token is expired
			response.setContentType("application/json");
			String jsonResp = "{\"status\":\"-2\",\"errorMessage\":\"access token expired\"}";
			response.getWriter().write(jsonResp);
			return;
		}
		chain.doFilter(request, response);
	}
	public void destroy() {
		
	}
	
}
