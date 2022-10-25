package com.mycomp.javablog.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JavaBlogConfiguration {

	@Value("${blogapi.endpoint.users.url}")
	private String usersEndPointUrl;

	@Value("${blogapi.endpoint.posts.url}")
	private String postsEndPointUrl;

	public String getUsersEndPointUrl() {
		return usersEndPointUrl;
	}

	public void setUsersEndPointUrl(String usersEndPointUrl) {
		this.usersEndPointUrl = usersEndPointUrl;
	}

	public String getPostsEndPointUrl() {
		return postsEndPointUrl;
	}

	public void setPostsEndPointUrl(String postsEndPointUrl) {
		this.postsEndPointUrl = postsEndPointUrl;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
