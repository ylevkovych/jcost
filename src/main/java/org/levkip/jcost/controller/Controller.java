package org.levkip.jcost.controller;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class Controller {
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Not found")
	public void notFound() { }
	
	@ExceptionHandler({DataAccessException.class,SQLException.class,Exception.class})
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Data access error")
	public void dataAccessException() {
		
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value=HttpStatus.PRECONDITION_FAILED)
	@ResponseBody
	public String preconditionFailed(IllegalArgumentException e) {
		return e.getLocalizedMessage();
	}
	
}
