package com.mycomp.javablog.domain;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogPost {

	public BlogPost() {

	}

	@NotBlank(message = "UserId is mandatory")
	private int userId;

	@Range(min = 1)
	private int id;

	@NotBlank(message = "Blog Title is mandatory")
	private String title;

	@NotBlank(message = "Blog text is mandatory")
	private String body;

	@JsonIgnore

	private int actionedBy;
	@JsonIgnore

	private Date createdDateTime;

	public int getActionedBy() {
		return actionedBy;
	}

	public void setActionedBy(int actionedBy) {
		this.actionedBy = actionedBy;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
