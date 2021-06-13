package com.todolist.rest.app.todolistcustomexception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.todolist.rest.app.dto.ToDoListExceptionDto;

/**
 * Custom Exception Mapper class for handling the exception from the api
 * @author anupriya
 *
 */
@Provider
	public class ToDoListExceptionMapper implements ExceptionMapper<ToDoListExceptionDto> {
	    public Response toResponse(ToDoListExceptionDto exception) {
	        return Response.status(exception.getCode())
	                .entity(exception.getMessage())
	                .type(MediaType.APPLICATION_JSON)
	                .build();
	    }

}