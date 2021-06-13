package com.todolist.rest.app.todolistrepository;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import com.todolist.rest.app.todolistentity.ToDoList;

import java.util.List;
import java.util.Optional;

/**
 * Repository for saving and get and delete from the db
 * @author anupriya
 *
 */
public class ToDoListRepository extends AbstractDAO<ToDoList> {

	public ToDoListRepository(SessionFactory factory) {
		super(factory);
	}

	public Optional<List<ToDoList>> getAll() {

		return Optional.ofNullable(this.list(this.criteria()));
	}

	public Optional<ToDoList> findById(int id) {
		return Optional.ofNullable(get(id));
	}

	public ToDoList saveToDo(ToDoList todolist) {
		return persist(todolist);
	}

	public void deleteTODO(ToDoList toDoList) {
		currentSession().delete(toDoList);
	}

}
