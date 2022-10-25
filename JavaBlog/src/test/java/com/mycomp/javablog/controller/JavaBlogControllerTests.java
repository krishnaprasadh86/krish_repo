package com.mycomp.javablog.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycomp.javablog.domain.BlogPost;
import com.mycomp.javablog.domain.UserBlogs;
import com.mycomp.javablog.service.JavaBlogService;

/**
 * @author vijaykrishna
 *
 */
@WebMvcTest(JavaBlogController.class)
@ActiveProfiles("test")
public class JavaBlogControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private JavaBlogService javaBlogService;

	/**
	 * @throws Exception
	 */
	@Test
	public void getAllUserBlogsStatusOK() throws Exception {
		when(this.javaBlogService.getAllUserBlogs()).thenReturn(new ArrayList<UserBlogs>());
		this.mvc.perform(get("/v1/javablog/admin/userblogs")).andExpect(status().isOk());
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void getAllUserBlogsStatusNotOk() throws Exception {
		when(this.javaBlogService.getAllUserBlogs()).thenThrow(new NullPointerException());
		this.mvc.perform(get("/v1/javablog/admin/userblogs")).andExpect(status().isInternalServerError());
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void getAllUserBlogsAuthorized() throws Exception {
		// Based on security context mechanism
		// This should work only for Admin Role User
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void createPostOnBehalfStatusOK() throws Exception {
		BlogPost post = createBlogPost();
		ObjectMapper mapper = new ObjectMapper();
		String reqData = mapper.writeValueAsString(post);
		when(this.javaBlogService.createBlogOnBehalf(Mockito.any(BlogPost.class))).thenReturn(post);
		this.mvc.perform(post("/v1/javablog/admin/posts").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(reqData)).andExpect(status().isOk());
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void createPostOnBehalfNotOk() throws Exception {
		BlogPost post = createBlogPost();
		ObjectMapper mapper = new ObjectMapper();
		String reqData = mapper.writeValueAsString(post);
		when(this.javaBlogService.createBlogOnBehalf(Mockito.any(BlogPost.class)))
				.thenThrow(new NullPointerException());
		this.mvc.perform(post("/v1/javablog/admin/posts").contentType(MediaType.APPLICATION_JSON).content(reqData))
				.andExpect(status().isInternalServerError());
	}

	/**
	 * @return
	 */
	private BlogPost createBlogPost() {
		BlogPost post = new BlogPost();
		post.setTitle("TestUser1 Blog title");
		post.setUserId(1);
		post.setBody("TestUser1 Java Blog");
		return post;
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void createPostOnBehalfAuthorized() throws Exception {
		// Based on security context machanism
		// This should work only for Admin Role User
	}

}
