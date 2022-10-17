package com.mycomp.javablog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycomp.javablog.domain.User;
import com.mycomp.javablog.service.JavaBlogService;

@SpringBootTest
class JavaBlogApplicationTests {
	
	@Autowired
	JavaBlogService javaBlogService;

	@Test
	void contextLoads() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			User[] users = mapper.readValue(ResourceUtils.getFile("classpath:UserData.json"), User[].class);
		for(User user: users) {
			javaBlogService.createUser(user);
		}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
