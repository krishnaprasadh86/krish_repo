package com.mycomp.javablog.mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycomp.javablog.domain.BlogAudit;
import com.mycomp.javablog.domain.BlogPost;
import com.mycomp.javablog.domain.User;
import com.mycomp.javablog.domain.UserBlogs;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class JavaBlogMapperTests {

	private static Logger logger = LoggerFactory.getLogger(JavaBlogMapperTests.class);

	@Autowired
	JavaBlogMapper javaBlogMapper;

	@BeforeAll
	public void loadDataInDBForTest() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			User[] users = mapper.readValue(ResourceUtils.getFile("classpath:UserData.json"), User[].class);
			for (User user : users) {
				javaBlogMapper.insertBlogUser(user);
				user.getAddress().setUserId(user.getId());
				user.getCompany().setUserId(user.getId());
				javaBlogMapper.insertAddress(user.getAddress());
				user.getAddress().getGeo().setAddressId(user.getAddress().getId());
				javaBlogMapper.insertGeoLocation(user.getAddress().getGeo());
				javaBlogMapper.insertCompany(user.getCompany());
			}

			BlogPost[] posts = mapper.readValue(ResourceUtils.getFile("classpath:Posts.json"), BlogPost[].class);
			for (BlogPost post : posts) {
				javaBlogMapper.insertBlogPost(post);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Test
	public void insertBlogPostSuccess() {
		BlogPost blogPost = formBlogPost();
		assertTrue(blogPost.getId() > 0);
		assertTrue(blogPost.getActionedBy() == 0);
	}

	private BlogPost formBlogPost() {
		BlogPost blogPost = new BlogPost();
		blogPost.setTitle("TestUser1 blog title 1");
		blogPost.setUserId(1);
		blogPost.setBody("TestUser1 blog content1");
		javaBlogMapper.insertBlogPost(blogPost);
		return blogPost;
	}

	@Test
	public void insertBlogAuditSucess() {
		BlogAudit blogAudit = new BlogAudit();
		blogAudit.setAction(0);
		BlogPost blogPost = formBlogPost();
		blogAudit.setBlogPostId(blogPost.getId());
		blogAudit.setCreatedBy(11);
		javaBlogMapper.insertBlogAudit(blogAudit);
		assertTrue(blogAudit.getId() > 0);
	}

	@Test
	public void getAllUserBlogsSuccess() {
		List<UserBlogs> userblogs = javaBlogMapper.getAllUserBlogs();
		assertTrue(userblogs.stream().count() == 10);
		assertTrue(userblogs.stream().findFirst().get().getId() == 1);
		assertTrue("Leanne Graham".equalsIgnoreCase(userblogs.stream().findFirst().get().getName()));
		assertTrue("Romaguera-Crona".equalsIgnoreCase(userblogs.stream().findFirst().get().getCompany().getName()));
		assertTrue("Kulas Light".equalsIgnoreCase(userblogs.stream().findFirst().get().getAddress().getStreet()));
		assertTrue("-37.3159".equalsIgnoreCase(userblogs.stream().findFirst().get().getAddress().getGeo().getLat()));
		assertTrue("sunt aut facere repellat provident occaecati excepturi optio reprehenderit".equalsIgnoreCase(
				userblogs.stream().findFirst().get().getBlogs().stream().findFirst().get().getTitle()));

	}

}
