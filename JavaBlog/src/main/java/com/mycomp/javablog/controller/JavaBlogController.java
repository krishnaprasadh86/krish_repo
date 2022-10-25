package com.mycomp.javablog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	/**
	 * This API is invoked to get list of all the Users and the blogs posted by User
	 * User -> Blogs. It will only be accessed by Users with admin role.
	 * 
	 * @return List of users and blogs
	 */
	// @secured(ROLE_SYSTEM_BLOG_ADMIN)
	@ApiOperation(value = "Get list of users and posted blogs", notes = "Admin role is required")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = UserBlogs.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "UnAuthorized"),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class) })
	@GetMapping("/v1/javablog/admin/userblogs")
	public ResponseEntity<?> getAllUserBlogs() throws Exception {
		logger.info("Invoking API getAllUserBlogs");
		return new ResponseEntity<>(javaBlogService.getAllUserBlogs(), HttpStatus.OK);
	}

	/**
	 * This API is invoked by admin to create blog post on behalf of another user.
	 * User -> Blogs. It will only be accessed by Users with admin role.
	 * 
	 * @return BlogPost object
	 * @throws Exception
	 */
	// @secured(ROLE_SYSTEM_BLOG_ADMIN)
	@ApiOperation(value = "Create Blog post on behalf of User", notes = "Admin role is required")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = UserBlogs.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 401, message = "UnAuthorized"),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class) })
	@PostMapping("/v1/javablog/admin/posts")
	public ResponseEntity<?> createPostOnBehalf(@RequestBody BlogPost blogPost) throws Exception {
		logger.info("Invoking API createPostOnBehalf");
		return new ResponseEntity<>(javaBlogService.createBlogOnBehalf(blogPost), HttpStatus.OK);
	}

}
