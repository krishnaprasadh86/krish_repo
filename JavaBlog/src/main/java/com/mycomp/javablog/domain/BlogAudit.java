package com.mycomp.javablog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogAudit {

	public BlogAudit() {

	}

	private int id;
	private int blogPostId;
	private int createdBy;
	private int action;
	private String blogTitle;
	private String blogBody;

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getBlogBody() {
		return blogBody;
	}

	public void setBlogBody(String blogBody) {
		this.blogBody = blogBody;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBlogPostId() {
		return blogPostId;
	}

	public void setBlogPostId(int blogPostId) {
		this.blogPostId = blogPostId;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

}
