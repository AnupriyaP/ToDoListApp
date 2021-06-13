package com.todolist.rest.app.todolistresource;

import com.todolist.rest.app.todolistconfig.ToDoListConfiguration;
import com.todolist.rest.app.todolistcontroller.ToDoListController;
import com.todolist.rest.app.todolistcustomexception.ToDoListExceptionMapper;
import com.todolist.rest.app.todolistentity.Subtask;
import com.todolist.rest.app.todolistentity.ToDoList;
import com.todolist.rest.app.todolistrepository.ToDoListRepository;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
/**
 * Configuration class
 * @author anupriya
 *
 */
public class ToDoListResource extends Application<ToDoListConfiguration> {
	public static void main(String[] args) throws Exception {
		new ToDoListResource().run(args);
	}

	public void run(ToDoListConfiguration myConfiguration, Environment environment) throws Exception {

		ToDoListRepository infoDao = new ToDoListRepository(hibernate.getSessionFactory());

		final ToDoListController resource = new ToDoListController(infoDao);
		environment.jersey().register(resource);
		environment.jersey().register(new ToDoListExceptionMapper());

	}

	private HibernateBundle<ToDoListConfiguration> hibernate = new HibernateBundle<ToDoListConfiguration>(
			ToDoList.class, Subtask.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(ToDoListConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	
	@Override
	public void initialize(Bootstrap<ToDoListConfiguration> bootstrap) {
		bootstrap.addBundle(hibernate);
	}

}