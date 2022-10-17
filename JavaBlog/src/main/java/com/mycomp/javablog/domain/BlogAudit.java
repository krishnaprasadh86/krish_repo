package com.mycomp.javablog.domain;

public class BlogAudit {
	
	private int id;
	private int blogPostId;
	private String createdBy;
	private int action;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	

}
