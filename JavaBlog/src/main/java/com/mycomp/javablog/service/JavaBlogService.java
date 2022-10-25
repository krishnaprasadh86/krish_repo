package com.mycomp.javablog.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.mycomp.javablog.domain.BlogAudit;
import com.mycomp.javablog.domain.BlogPost;
import com.mycomp.javablog.domain.UserBlogs;
import com.mycomp.javablog.mapper.JavaBlogMapper;
import com.mycomp.javablog.util.JavaBlogConfiguration;
import com.mycomp.javablog.util.JavaBlogConstants;

@Service
public class JavaBlogService {

	private static Logger logger = LoggerFactory.getLogger(JavaBlogService.class);

	@Autowired
	JavaBlogMapper javaBlogMapper;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	JavaBlogConfiguration javaBlogConfiguration;

	/**
	 * This method is used to retrieve all user and associated blogs
	 * 
	 * @return List of Users and associated Blogs
	 */
	public List<UserBlogs> getAllUserBlogs() {
		logger.info("Invoking getAllUserBlogs");
		UserBlogs[] userBlogs = restTemplate.getForObject(javaBlogConfiguration.getUsersEndPointUrl(),
				UserBlogs[].class);
		logger.info("User Details Fetched Successfully");
		if (userBlogs != null && userBlogs.length > 0) {
			BlogPost[] blogposts = restTemplate.getForObject(javaBlogConfiguration.getPostsEndPointUrl(),
					BlogPost[].class);
			logger.info("Blog Post Details Fetched Successfully");
			if (blogposts != null && blogposts.length > 0) {
				Map<Integer, List<BlogPost>> userPostMap = Arrays.stream(blogposts)
						.collect(Collectors.groupingBy(BlogPost::getUserId));
				Arrays.stream(userBlogs).forEach(u -> {
					u.setBlogs(userPostMap.get(u.getId()));
				});
			} else {
				logger.info("User Blogpost is Empty");
			}
			return Arrays.asList(userBlogs);
		} else {
			logger.info("User Response is Empty");
		}
		return new ArrayList<UserBlogs>();
	}

	/**
	 * This method is used to create a blog post by admin on behlf of another user.
	 * This method inturn will invoke create Blog post with actionnedBy set as 1
	 * (admin). This method will also create a audit entry for admin blog blogPost
	 * actionnedBy 1- admin user , 0 - bloguser
	 * 
	 * @param blogPost
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public BlogPost createBlogOnBehalf(BlogPost blogPost) throws Exception {
		logger.info("Invoking createBlogOnBehalf");
		blogPost = restTemplate.postForObject(javaBlogConfiguration.getPostsEndPointUrl(), blogPost, BlogPost.class);
		if (blogPost != null && blogPost.getId() > 0) {
			logger.info("Blog Post created sucessfully");
			createAuditRecord(blogPost);
			logger.info("exit createBlogOnBehalf");
		} else {
			logger.error("Blog Post not created successfully");
			throw new Exception("Error Occured while creating Blogpost");
		}
		return blogPost;
	}

	/**
	 * This method is used to create audit record for the blogpost action 0 - create
	 * blog on behalf , 1- update on behalf, 2 - delete on behalf createdBy -> Admin
	 * user id
	 * 
	 * @param blogPost object
	 * @return Audit entry
	 */
	private BlogAudit createAuditRecord(BlogPost blogPost) {
		logger.info("Invoking createAuditRecord for blogPostId :" + blogPost.getId());
		BlogAudit auditEntry = new BlogAudit();
		auditEntry.setAction(JavaBlogConstants.CREATE_BLOG_CODE_VALUE);
		auditEntry.setBlogPostId(blogPost.getId());
		auditEntry.setBlogTitle(blogPost.getTitle());
		auditEntry.setBlogBody(blogPost.getBody());
		auditEntry.setCreatedBy(11); // harcoded for DEMO purpose
		javaBlogMapper.insertBlogAudit(auditEntry);
		logger.info("Audit entry created  id : " + auditEntry.getId());
		return auditEntry;
	}

}
