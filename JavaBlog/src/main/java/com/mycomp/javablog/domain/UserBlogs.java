package com.mycomp.javablog.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBlogs extends User {

	public UserBlogs() {

	}

	private List<BlogPost> blogs;

	public List<BlogPost> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<BlogPost> blogs) {
		this.blogs = blogs;
	}

}
