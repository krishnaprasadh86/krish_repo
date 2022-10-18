package com.mycomp.javablog.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycomp.javablog.domain.BlogAudit;
import com.mycomp.javablog.domain.BlogPost;
import com.mycomp.javablog.domain.User;
import com.mycomp.javablog.domain.UserBlogs;
import com.mycomp.javablog.mapper.JavaBlogMapper;
import com.mycomp.javablog.util.JavaBlogConstants;

@Service
public class JavaBlogService {

	private static Logger logger = LoggerFactory.getLogger(JavaBlogService.class);

	@Autowired
	JavaBlogMapper javaBlogMapper;

	/** Assignment code starts */

	/**
	 * This method is used to create a blog post by admin on behlf of another user.
	 * This method inturn will invoke create Blog post with actionnedBy set as 1
	 * (admin). This method will also create a audit entry for admin blog blogPost
	 * actionnedBy 1- admin user , 0 - bloguser
	 * 
	 * @param blogPost
	 * @return
	 */
	@Transactional
	public BlogPost createBlogOnBehalf(BlogPost blogPost) {
		logger.info("Invoking createBlogOnBehalf");
		blogPost.setActionedBy(JavaBlogConstants.ADMIN_ACTION_CODE_VALUE);
		createBlog(blogPost);
		logger.info("Blog post created  id : " + blogPost.getId());
		createAuditRecord(blogPost);
		logger.info("exit createBlogOnBehalf");
		return blogPost;
	}

	/**
	 * This method is used to create blog post for given obj
	 * 
	 * @param blogPost
	 * @return
	 */
	public BlogPost createBlog(BlogPost blogPost) {
		logger.info("Invoking createBlog : User Id : " + blogPost.getUserId());
		javaBlogMapper.insertBlogPost(blogPost);
		return blogPost;
	}

	/**
	 * This method is used to retrieve all user and associated blogs
	 * 
	 * @return List of Users and associated Blogs
	 */
	public List<UserBlogs> getAllUserBlogs() {
		logger.info("Invoking getAllUserBlogs");
		return javaBlogMapper.getAllUserBlogs();
	}

	/**
	 * This method is used to create audit record for the blogpost action 0 - create
	 * blog on behalf , 1- update on behalf, 2 - delete on behalf Blog createdBy ->
	 * Admin user id
	 * 
	 * @param blogPost object
	 * @return Audit entry
	 */
	private BlogAudit createAuditRecord(BlogPost blogPost) {
		logger.info("Invoking createAuditRecord for blogPostId :" + blogPost.getId());
		BlogAudit auditEntry = new BlogAudit();
		auditEntry.setAction(JavaBlogConstants.CREATE_BLOG_CODE_VALUE);
		auditEntry.setBlogPostId(blogPost.getId());
		auditEntry.setCreatedBy(11); // harcoded for DEMO purpose
		javaBlogMapper.insertBlogAudit(auditEntry);
		logger.info("Audit entry created  id : " + auditEntry.getId());
		return auditEntry;
	}

	/** Assignment code ends */

	public List<User> getAllUsers() {
		return javaBlogMapper.getAllUsers();
	}

	public User createUser(User user) {
		// Null checks are not incorporated in DEMO assignment code
		// as the USER object will be validated in controller for valid or BAD Request
		javaBlogMapper.insertBlogUser(user);
		user.getAddress().setUserId(user.getId());
		user.getCompany().setUserId(user.getId());
		javaBlogMapper.insertAddress(user.getAddress());
		user.getAddress().getGeo().setAddressId(user.getAddress().getId());
		javaBlogMapper.insertGeoLocation(user.getAddress().getGeo());
		javaBlogMapper.insertCompany(user.getCompany());
		return user;
	}

}
