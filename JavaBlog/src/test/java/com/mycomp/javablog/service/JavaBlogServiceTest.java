package com.mycomp.javablog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycomp.javablog.domain.BlogAudit;
import com.mycomp.javablog.domain.BlogPost;
import com.mycomp.javablog.domain.UserBlogs;
import com.mycomp.javablog.mapper.JavaBlogMapper;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class JavaBlogServiceTest {

	private static final String POSTS_API = "https://jsonplaceholder.typicode.com/posts";

	private static final String USERS_API = "https://jsonplaceholder.typicode.com/users";

	@Autowired
	JavaBlogService javaBlogService;

	@MockBean
	JavaBlogMapper javaBlogMapper;

	@MockBean
	RestTemplate restTemplate;

	private UserBlogs[] userBlogs = null;
	private BlogPost[] blogPosts = null;

	private static Logger logger = LoggerFactory.getLogger(JavaBlogServiceTest.class);

	@BeforeAll
	public void loadMockDataFromFile() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		try {
			InputStream users = new ClassPathResource("UserData.json").getInputStream();
			userBlogs = mapper.readValue(users, UserBlogs[].class);

			InputStream blogs = new ClassPathResource("Posts.json").getInputStream();
			blogPosts = mapper.readValue(blogs, BlogPost[].class);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Test
	public void getAllUserBlogsSuccess() throws Exception {
		when(this.restTemplate.getForObject(USERS_API, UserBlogs[].class)).thenReturn(userBlogs);
		when(this.restTemplate.getForObject(POSTS_API, BlogPost[].class)).thenReturn(blogPosts);
		List<UserBlogs> userBlogs = javaBlogService.getAllUserBlogs();
		assertThat(userBlogs.size() == 10);
		assertThat(userBlogs.stream().allMatch(k -> k.getBlogs().size() == 10));
	}

	@Test
	public void getAllUserBlogsSuccessUserBlank() throws Exception {
		when(this.restTemplate.getForObject(USERS_API, UserBlogs[].class)).thenReturn(null);
		when(this.restTemplate.getForObject(POSTS_API, BlogPost[].class)).thenReturn(blogPosts);
		List<UserBlogs> userBlogs = javaBlogService.getAllUserBlogs();
		assertThat(userBlogs.size() == 0);
	}

	@Test
	public void getAllUserBlogsSuccesBlogsBlank() throws Exception {
		when(this.restTemplate.getForObject(USERS_API, UserBlogs[].class)).thenReturn(userBlogs);
		when(this.restTemplate.getForObject(POSTS_API, BlogPost[].class)).thenReturn(null);
		List<UserBlogs> userBlogs = javaBlogService.getAllUserBlogs();
		assertThat(userBlogs.size() == 10);
		assertThat(userBlogs.stream().allMatch(k -> k.getBlogs() == null));

	}

	@Test
	public void getAllUserBlogsFailure() throws Exception {
		when(this.restTemplate.getForObject(USERS_API, UserBlogs[].class)).thenThrow(new NullPointerException());
		when(this.restTemplate.getForObject(POSTS_API, BlogPost[].class)).thenReturn(null);
		assertThrows(Exception.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				javaBlogService.getAllUserBlogs();
			}
		});
	}

	@Test
	public void createBlogOnBehalfSuccess() throws Exception {
		BlogPost blogPost = createNewBlogPost();
		when(this.restTemplate.postForObject(POSTS_API, blogPost, BlogPost.class)).thenReturn(blogPost);
		doNothing().when(javaBlogMapper).insertBlogAudit(Mockito.any(BlogAudit.class));

		BlogPost op = this.javaBlogService.createBlogOnBehalf(blogPost);
		assertThat(op.getId() == 101);
		verify(javaBlogMapper, times(1)).insertBlogAudit(Mockito.any(BlogAudit.class));
	}

	/**
	 * @return
	 */
	private BlogPost createNewBlogPost() {
		BlogPost blogPost = new BlogPost();
		blogPost.setBody("Creating blog content on behalf content");
		blogPost.setTitle("Creating blog content on behalf title ");
		blogPost.setUserId(1);
		blogPost.setId(101);
		return blogPost;
	}

	@Test
	public void createBlogOnBehalfFailure() throws Exception {
		BlogPost blogPost = createNewBlogPost();
		when(this.restTemplate.postForObject(POSTS_API, blogPost, BlogPost.class))
				.thenThrow(new NullPointerException());
		doNothing().when(javaBlogMapper).insertBlogAudit(Mockito.any(BlogAudit.class));

		assertThrows(Exception.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				javaBlogService.createBlogOnBehalf(blogPost);
			}
		});
		verify(javaBlogMapper, times(0)).insertBlogAudit(Mockito.any(BlogAudit.class));
	}

	/**
	 * @Test public void getAllUserBlogsFailure() throws Exception {
	 *       when(this.javaBlogMapper.getAllUserBlogs()).thenThrow(new
	 *       NullPointerException()); assertThrows(Exception.class, new Executable()
	 *       {
	 * @Override public void execute() throws Throwable {
	 *           javaBlogService.getAllUserBlogs(); } }); }
	 * 
	 * 
	 * 
	 * @Test public void createBlogOnBehalfFailure() throws Exception { BlogPost
	 *       post = new BlogPost(); post.setBody("TestUser1 blog content 1");
	 *       post.setTitle("TestUser1 title"); post.setUserId(1);
	 * 
	 *       doThrow(new
	 *       NullPointerException()).when(javaBlogMapper).insertBlogPost(Mockito.any(BlogPost.class));
	 *       doNothing().when(javaBlogMapper).insertBlogAudit(Mockito.any(BlogAudit.class));
	 * 
	 *       assertThrows(Exception.class, new Executable() {
	 * @Override public void execute() throws Throwable {
	 *           javaBlogService.createBlogOnBehalf(post); } });
	 * 
	 *           }
	 */

}
