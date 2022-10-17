package com.mycomp.javablog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycomp.javablog.domain.BlogAudit;
import com.mycomp.javablog.domain.BlogPost;
import com.mycomp.javablog.domain.User;
import com.mycomp.javablog.domain.UserBlogs;
import com.mycomp.javablog.mapper.JavaBlogMapper;

@Service
public class JavaBlogService {

	@Autowired
	JavaBlogMapper javaBlogMapper;

	public List<User> getAllUsers() {
		return javaBlogMapper.getAllUsers();
	}

	public User createUser(User user) {
		javaBlogMapper.insertBlogUser(user);
		javaBlogMapper.insertAddress(user.getAddress());
		javaBlogMapper.insertGeoLocation(user.getAddress().getGeo());
		javaBlogMapper.insertCompany(user.getCompany());
		return user;
	}

	public BlogPost createBlog(BlogPost blogPost) {
		javaBlogMapper.insertBlogPost(blogPost);
		return blogPost;
	}

	public BlogPost createBlogOnBehalf(BlogPost blogPost) {
		blogPost.setActionedBy(1); // 1- admin user , 0 - bloguser
		createBlog(blogPost);
		createAuditRecord(blogPost);
		return blogPost;
	}

	private BlogAudit createAuditRecord(BlogPost blogPost) {
		// Add Audit Entry for Admin Blog Post
		BlogAudit auditEntry = new BlogAudit();
		auditEntry.setAction(0); // 0 - create blog on behalf , 1- update on behalf, 2 - delete on behalf
		auditEntry.setBlogPostId(blogPost.getId());
		auditEntry.setCreatedBy("Vj"); // get invoked user from security header/context // hardcoding for now for
										// Assignment DEMO
		javaBlogMapper.insertBlogAudit(auditEntry);
		return auditEntry;
	}

	public List<UserBlogs> getAllUserBlogs() {		
		return javaBlogMapper.getAllUserBlogs();
	}

}
