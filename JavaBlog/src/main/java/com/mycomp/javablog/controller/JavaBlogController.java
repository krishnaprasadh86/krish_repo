package com.mycomp.javablog.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.mycomp.javablog.domain.ErrorResponse;
import com.mycomp.javablog.domain.UserBlogs;
import com.mycomp.javablog.service.JavaBlogService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class JavaBlogController {

	private static Logger logger = LoggerFactory.getLogger(JavaBlogController.class);

	@Autowired
	JavaBlogService javaBlogService;

	/** Assignment API **/

	/**
	 * This API is invoked to get list of all the Users and the blogs posted by User
	 * User -> Blogs. It will only be accessed by Users with admin role.
	 * 
	 * @return
	 */
	// @secured(ROLE_SYSTEM_BLOG_ADMIN)
	@ApiOperation(value = "Get list of users and posted blogs", notes = "Admin role is required")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = UserBlogs.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "UnAuthorized"),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class) })
	@GetMapping("/v1/javablog/admin/userblogs")
	public ResponseEntity<?> getAllUserBlogs() {
		logger.info("Invoking API getAllUserBlogs");
		return new ResponseEntity<>(javaBlogService.getAllUserBlogs(), HttpStatus.OK);
	}

	/** Assignment API **/
	/**
	 * This API is invoked by admin to create blog post on behalf of another user.
	 * User -> Blogs. It will only be accessed by Users with admin role.
	 * 
	 * @return
	 */
	// @secured(ROLE_SYSTEM_BLOG_ADMIN)
	@ApiOperation(value = "Create Blog post on behalf of User", notes = "Admin role is required")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = UserBlogs.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "UnAuthorized"),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class) })
	@PostMapping("/v1/javablog/admin/posts")
	public ResponseEntity<?> createPostOnBehalf(@RequestBody BlogPost blogPost) {
		logger.info("Invoking API createPostOnBehalf");
		return new ResponseEntity<>(javaBlogService.createBlogOnBehalf(blogPost), HttpStatus.OK);
	}

	/** Assignment API ends */

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

}
