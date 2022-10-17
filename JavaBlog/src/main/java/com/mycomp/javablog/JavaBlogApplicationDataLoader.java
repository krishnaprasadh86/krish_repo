package com.mycomp.javablog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycomp.javablog.domain.BlogPost;
import com.mycomp.javablog.domain.User;
import com.mycomp.javablog.service.JavaBlogService;

@Component
public class JavaBlogApplicationDataLoader {

	@Autowired
	JavaBlogService javaBlogService;

	/** This Data loader  is only for DEMO activity purpose / Alternate way to load is writing insert queries in data.sql  */
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			User[] users = mapper.readValue(ResourceUtils.getFile("classpath:UserData.json"), User[].class);
			for (User user : users) {
				javaBlogService.createUser(user);
			}
			
			BlogPost[] posts = mapper.readValue(ResourceUtils.getFile("classpath:Posts.json"), BlogPost[].class);
			for(BlogPost post :posts) {
				javaBlogService.createBlog(post);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
