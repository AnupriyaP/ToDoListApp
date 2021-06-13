package com.todolist.rest.app.dto;

/**
 * 
 * @author anupriya
 * pojo class for exception
 *
 */
public class ToDoListExceptionDto  extends Throwable {
  
	
	private static final long serialVersionUID = 1L;
	private final int code;

    public ToDoListExceptionDto() {
        this(500);
    }

    public ToDoListExceptionDto(int code) {
        this(code, "Error while processing the request", null);
    }

    public ToDoListExceptionDto(int code, String message) {
        this(code, message, null);
    }

    public ToDoListExceptionDto(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
