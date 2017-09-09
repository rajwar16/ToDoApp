package com.bridgeit.TodoApp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class COREFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("cores filter...");
		HttpServletResponse response1 = (HttpServletResponse) response;
		response1.addHeader("Access-Control-Allow-Credentials", "true");
		response1.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
		response1.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response1.addHeader("Access-Control-Max-Age", "3600");
		response1.addHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

	
}
