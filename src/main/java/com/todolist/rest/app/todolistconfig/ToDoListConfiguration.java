package com.todolist.rest.app.todolistconfig;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
/**
 * Class for setting/getting the data source 
 * @author anupriya
 *
 */
public class ToDoListConfiguration extends Configuration {
	private String url;
	private DataSourceFactory database = new DataSourceFactory();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty("database")
	public DataSourceFactory getDataSourceFactory() {
		return database;
	}

	public void setDatabase(DataSourceFactory database) {
		this.database = database;
	}
}