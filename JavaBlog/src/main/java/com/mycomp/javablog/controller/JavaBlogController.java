package com.mycomp.javablog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycomp.javablog.domain.BlogPost;
import com.mycomp.javablog.service.JavaBlogService;

@RestController
public class JavaBlogController {

	@Autowired
	JavaBlogService javaBlogService;

	@PostMapping("/v1/javablog/posts")
	// @secured(ROLE_SYSTEM_BLOG_USER)
	public ResponseEntity<?> createPost(@RequestBody BlogPost blogPost) {
		return null;
	}

	@PutMapping("/v1/javablog/posts")
	// @secured(ROLE_SYSTEM_BLOG_USER)
	public ResponseEntity<?> updatePost(@RequestBody BlogPost blogPost) {
		return null;
	}

	@DeleteMapping("/v1/javablog/posts")
	// @secured(ROLE_SYSTEM_BLOG_USER)
	public ResponseEntity<?> deletePost(@RequestBody BlogPost blogPost) {
		return null;
	}

	@GetMapping("/v1/javablog/posts")
	// @secured(ROLE_SYSTEM_BLOG_USER,ROLE_SYSTEM_BLOG_ADMIN)
	public List<BlogPost> getAllPosts() {
		return null;
	}

	@GetMapping("/v1/javablog/users")
	// @secured(ROLE_SYSTEM_BLOG_ADMIN)
	public ResponseEntity<?> getAllUsers() {
		return new ResponseEntity<>(javaBlogService.getAllUsers(), HttpStatus.OK);
	}

	/** Assignment API **/
	// @secured(ROLE_SYSTEM_BLOG_ADMIN)
	@GetMapping("/v1/javablog/admin/userblogs")
	public ResponseEntity<?>  getAllUserBlogs() {
		return new ResponseEntity<>(javaBlogService.getAllUserBlogs(), HttpStatus.OK);
	}

	/** Assignment API **/
	// @secured(ROLE_SYSTEM_BLOG_ADMIN)
	@PostMapping("/v1/javablog/admin/posts")
	public ResponseEntity<?>  createPostOnBehalf(@RequestBody BlogPost blogPost) {
		return new ResponseEntity<>(javaBlogService.createBlogOnBehalf(blogPost), HttpStatus.OK);
	}

}
