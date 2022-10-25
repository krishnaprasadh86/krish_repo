package com.mycomp.javablog.mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mycomp.javablog.domain.BlogAudit;
import com.mycomp.javablog.domain.BlogPost;

/**
 * @author vijaykrishna
 *
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class JavaBlogMapperTests {

	@Autowired
	JavaBlogMapper javaBlogMapper;

	/**
	 * @return
	 */
	private BlogPost formBlogPost() {
		BlogPost blogPost = new BlogPost();
		blogPost.setTitle("TestUser1 blog title 1");
		blogPost.setUserId(1);
		blogPost.setBody("TestUser1 blog content1");
		return blogPost;
	}

	/**
	 * 
	 */
	@Test
	public void insertBlogAuditSucess() {
		BlogAudit blogAudit = new BlogAudit();
		blogAudit.setAction(0);
		BlogPost blogPost = formBlogPost();
		blogAudit.setBlogPostId(blogPost.getId());
		blogAudit.setBlogTitle(blogPost.getTitle());
		blogAudit.setBlogBody(blogPost.getBody());
		blogAudit.setCreatedBy(11);
		javaBlogMapper.insertBlogAudit(blogAudit);
		assertTrue(blogAudit.getId() > 0);
	}

}
