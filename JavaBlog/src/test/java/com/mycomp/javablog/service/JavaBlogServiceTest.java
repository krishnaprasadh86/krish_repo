package com.mycomp.javablog.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.mycomp.javablog.domain.BlogAudit;
import com.mycomp.javablog.domain.BlogPost;
import com.mycomp.javablog.domain.UserBlogs;
import com.mycomp.javablog.mapper.JavaBlogMapper;

@SpringBootTest
@ActiveProfiles("test")
public class JavaBlogServiceTest {

	@Autowired
	JavaBlogService javaBlogService;

	@MockBean
	JavaBlogMapper javaBlogMapper;

	@Test
	public void getAllUserBlogsSuccess() throws Exception {
		UserBlogs blogs = new UserBlogs();
		blogs.setId(1);
		List<UserBlogs> userblogs = new ArrayList<UserBlogs>();
		userblogs.add(blogs);
		when(this.javaBlogMapper.getAllUserBlogs()).thenReturn(userblogs);
		assertTrue(javaBlogService.getAllUserBlogs().size() == 1);
	}

	@Test
	public void getAllUserBlogsFailure() throws Exception {
		when(this.javaBlogMapper.getAllUserBlogs()).thenThrow(new NullPointerException());
		assertThrows(Exception.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				javaBlogService.getAllUserBlogs();
			}
		});
	}

	@Test
	public void createBlogOnBehalfSuccess() throws Exception {
		BlogPost post = new BlogPost();
		post.setBody("TestUser1 blog content 1");
		post.setTitle("TestUser1 title");
		post.setUserId(1);

		doNothing().when(javaBlogMapper).insertBlogPost(Mockito.any(BlogPost.class));
		doNothing().when(javaBlogMapper).insertBlogAudit(Mockito.any(BlogAudit.class));
		BlogPost op = this.javaBlogService.createBlogOnBehalf(post);
		assertTrue(op.getActionedBy() == 1);

	}

	@Test
	public void createBlogOnBehalfFailure() throws Exception {
		BlogPost post = new BlogPost();
		post.setBody("TestUser1 blog content 1");
		post.setTitle("TestUser1 title");
		post.setUserId(1);

		doThrow(new NullPointerException()).when(javaBlogMapper).insertBlogPost(Mockito.any(BlogPost.class));
		doNothing().when(javaBlogMapper).insertBlogAudit(Mockito.any(BlogAudit.class));

		assertThrows(Exception.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				javaBlogService.createBlogOnBehalf(post);
			}
		});

	}
}
