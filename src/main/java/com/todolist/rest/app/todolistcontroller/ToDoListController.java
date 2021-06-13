package com.todolist.rest.app.todolistcontroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import com.todolist.rest.app.dto.ToDoListExceptionDto;
import com.todolist.rest.app.todolistentity.Subtask;
import com.todolist.rest.app.todolistentity.ToDoList;
import com.todolist.rest.app.todolistrepository.ToDoListRepository;
import com.todolist.rest.app.todolistservice.ToDoListService;

import io.dropwizard.hibernate.UnitOfWork;
/**
 * 
 * @author anupriya
 *
 */
@Path("/todos")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class ToDoListController {

	private ToDoListRepository toDoListRepository;

	public ToDoListController(ToDoListRepository toDoListRepository) {
		this.toDoListRepository = toDoListRepository;
	}

	/**
	 * Returns a list of all Todos
	 * @return toDoLiST
	 * @throws ToDoListExceptionDto
	 */
	@GET
	@UnitOfWork
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Optional<List<ToDoList>> getAllToDoList() throws ToDoListExceptionDto {
		Optional<List<ToDoList>> toDoLiST = toDoListRepository.getAll();
		if (!toDoLiST.isPresent()) {
			throw new ToDoListExceptionDto(HttpStatus.NOT_FOUND_404, "No Data/ToDoList is available");

		}
		return toDoLiST;
	}

	/**
	 * Returns a Todo
	 * @param id
	 * @return toDo
	 * @throws ToDoListExceptionDto
	 */
	@GET
	@UnitOfWork
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Optional<ToDoList> getbyid(@PathParam("id") int id) throws ToDoListExceptionDto {
		Optional<ToDoList> toDo = toDoListRepository.findById(id);
		if (!toDo.isPresent()) {
			throw new ToDoListExceptionDto(HttpStatus.NOT_FOUND_404, "ToDoList with mentioned id is NOT found");

		}
		return toDo;
	}

	/**
	 * Expects a Todo (without id) and returns a Todo with id
	 * @param toDoListObj
	 * @return Response Obj
	 * @throws ToDoListExceptionDto
	 */
	@POST
	@UnitOfWork
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getbyid(ToDoList toDoListObj) throws ToDoListExceptionDto {

		if (toDoListObj != null) {
			List<Subtask> subtaskListObj = toDoListObj.getSubtask();
			ToDoList toDoList = new ToDoList();
			ToDoListService toDoListService = new ToDoListService();
			toDoList = toDoListService.setToDo(toDoList, toDoListObj);

			List<Subtask> subtasklist = toDoListService.setSubtaskList(subtaskListObj, toDoList);
			toDoList.setSubtask(subtasklist);

			return Response.ok(toDoListRepository.saveToDo(toDoList)).build();
		}
		throw new ToDoListExceptionDto(HttpStatus.BAD_REQUEST_400, "ToDoList Request Detail is Empty or Null");

	}

	/**
	 * Overwrites an existing Todo
	 * @param id
	 * @param toDoListObj
	 * @return Response object
	 * @throws ToDoListExceptionDto
	 */
	@PUT
	@UnitOfWork
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateTODOS(@PathParam("id") int id, ToDoList toDoListObj) throws ToDoListExceptionDto {

		if (toDoListObj != null) {
			Optional<ToDoList> todo = toDoListRepository.findById(id);
			ToDoListService toDoListService = new ToDoListService();

			if (todo.isPresent()) {

				ToDoList toDoList = todo.get();
				List<Subtask> subtasklist = toDoList.getSubtask();
				List<Subtask> subtasklistUpdate = toDoListObj.getSubtask();
				List<Subtask> subtaskListSave = new ArrayList<>();
				toDoList = toDoListService.setToDo(toDoList, toDoListObj);

				subtasklist = Optional.ofNullable(subtasklist).orElse(Collections.emptyList()).stream()
						.collect(Collectors.toList());
				subtasklistUpdate = Optional.ofNullable(subtasklistUpdate).orElse(Collections.emptyList()).stream()
						.collect(Collectors.toList());

				if (!subtasklist.isEmpty()) {
					for (Subtask subtaskObj : subtasklist) {
						for (Subtask subtask : subtasklistUpdate) {

							if (subtaskObj.getSubtaskId().equals(subtask.getSubtaskId()))

							{
								subtaskObj = toDoListService.setSubTask(subtaskObj, subtask);
								subtaskListSave.add(subtaskObj);

							} else if (subtask.getSubtaskId() == null
									&& subtaskListSave.size() != subtasklistUpdate.size()) {
								Subtask subtaskObject = new Subtask();

								subtaskObject = toDoListService.setSubTask(subtaskObject, subtask);
								subtaskObject.setId(toDoList);
								subtaskListSave.add(subtaskObject);
							}

						}
					}
				} else {
					subtaskListSave = toDoListService.setSubtaskList(subtasklistUpdate, toDoList);

				}

				toDoList.setSubtask(subtaskListSave);
				ToDoList finalobj = toDoListRepository.saveToDo(toDoList);
				return Response.ok(finalobj).build();

			}
		}

		throw new ToDoListExceptionDto(HttpStatus.BAD_REQUEST_400, "ToDoList Request Detail is Empty or Null");

	}

	/**
	 *  Deletes a Todo
	 * @param id
	 * @return Response Object
	 * @throws ToDoListExceptionDto
	 */
	@Path("{id}")
	@DELETE 
	@UnitOfWork
	public Response delete(@PathParam("id") int id) throws ToDoListExceptionDto {
		Optional<ToDoList> todo = toDoListRepository.findById(id);
		if (todo.isPresent()) {
			ToDoList toDoList = todo.get();
			toDoListRepository.deleteTODO(toDoList);
			return Response.ok("ToDoList is deleted successfully").build();

		}
		throw new ToDoListExceptionDto(HttpStatus.NOT_FOUND_404, "ToDoList with mentioned id is NOT found");
	}
}