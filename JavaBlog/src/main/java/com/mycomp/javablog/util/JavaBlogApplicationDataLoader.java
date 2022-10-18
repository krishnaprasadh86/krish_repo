package com.mycomp.javablog.util;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycomp.javablog.domain.BlogPost;
import com.mycomp.javablog.domain.User;
import com.mycomp.javablog.service.JavaBlogService;

@Component
@Profile("!test")
public class JavaBlogApplicationDataLoader {

	private static Logger logger = LoggerFactory.getLogger(JavaBlogApplicationDataLoader.class);

	@Autowired
	JavaBlogService javaBlogService;

	@Autowired
	ResourceLoader resourceLoader;

	/**
	 * This Data loader is only for DEMO Assignment / Alternate way to load date is
	 * to write insert queries in data.sql for DEMO
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		logger.info("Loading Data in DB");
		ObjectMapper mapper = new ObjectMapper();
		try {
			InputStream is = new ClassPathResource("UserData.json").getInputStream();
			User[] users = mapper.readValue(is, User[].class);
			for (User user : users) {
				javaBlogService.createUser(user);
			}
			InputStream is2 = new ClassPathResource("Posts.json").getInputStream();
			BlogPost[] posts = mapper.readValue(is2, BlogPost[].class);
			for (BlogPost post : posts) {
				javaBlogService.createBlog(post);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
