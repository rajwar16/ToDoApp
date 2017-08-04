package com.bridgeit.TodoApp.JSONResponse;

import java.util.List;

import org.springframework.validation.FieldError;


public class ErrorResponse extends Response
{
	private List<FieldError> list;

	public List<FieldError> getList() {
		return list;
	}

	public void setList(List<FieldError> list2) {
		this.list = list2;
	}
}
