package com.todolist.rest.app.todolistentity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Entity class for todo
 * @author anupriya
 *
 */
@Entity
@Table(name = "todolist")
public class ToDoList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@JsonProperty("id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	@NotNull
	@JsonProperty
	private String name;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "description")
	@JsonProperty
	private String description;

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ToDoList() {
		super();
	}

	public List<Subtask> getSubtask() {
		return subtask;
	}

	public void setSubtask(List<Subtask> subtask) {
		this.subtask = subtask;
	}

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tasks")
	// @JoinColumn(name="todolist_id")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id", targetEntity = Subtask.class)
	private List<Subtask> subtask;
	
	@JsonIgnore
	@CreationTimestamp
	private LocalDateTime createDateTime;

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	@JsonIgnore
	@UpdateTimestamp
	private LocalDateTime updateDateTime;

}