package com.todolist.rest.app.todolistentity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity class for subtask
 * @author anupriya
 *
 */
@Entity
@Table(name = "todolist_subtask")
public class Subtask implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "subtask_id", nullable = false)
	@JsonProperty("subtask_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer subtaskId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private ToDoList id;
	
	@Column(name = "name")
	@NotNull
	private String name;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "description")
	private String description;


	public Integer getSubtaskId() {
		return subtaskId;
	}

	public void setSubtaskId(Integer subtaskId) {
		this.subtaskId = subtaskId;
	}

	public ToDoList getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(ToDoList id) {
		this.id = id;
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
	
	@JsonIgnore
	@CreationTimestamp
	private LocalDateTime createDateTime;

	@JsonIgnore
	@UpdateTimestamp
	private LocalDateTime updateDateTime;


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

}
