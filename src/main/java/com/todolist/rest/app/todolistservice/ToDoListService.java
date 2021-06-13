package com.todolist.rest.app.todolistservice;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.todolist.rest.app.todolistentity.Subtask;
import com.todolist.rest.app.todolistentity.ToDoList;

public class ToDoListService {
	
	/**
	 * sets and Returns a list of subtask
	 * @param subtaskListObj
	 * @param toDoList
	 * @return List<Subtask>
	 */
	public List<Subtask> setSubtaskList(List<Subtask> subtaskListObj,ToDoList toDoList)
	{
		return  Optional.ofNullable(subtaskListObj).orElse(Collections.emptyList()).stream()
				.filter(Objects::nonNull).map(subtasks -> {
					Subtask subtask = new Subtask();
					subtask.setDescription(subtasks.getDescription());
					subtask.setName(subtasks.getName());
					subtask.setId(toDoList);
					return subtask;
				}).collect(Collectors.toList());
	}
	
	
	/**
	 * sets the todolist name and desc
	 * @param toDoList
	 * @param toDoListReqObj
	 * @return ToDoList
	 */
	public ToDoList setToDo(ToDoList toDoList,ToDoList toDoListReqObj)
	{
		toDoList.setName(toDoListReqObj.getName());
		toDoList.setDescription(toDoListReqObj.getDescription());
		return toDoList;
	}
	
	/**
	 * sets the subtask name and desc
	 * @param subtaskDbObject
	 * @param subtaskReqObj
	 * @return
	 */
	public Subtask setSubTask(Subtask subtaskDbObject,Subtask subtaskReqObj)
	{
		subtaskDbObject.setDescription(subtaskReqObj.getDescription());
		subtaskDbObject.setName(subtaskReqObj.getName());
		return subtaskDbObject;
		
	}
	


}
