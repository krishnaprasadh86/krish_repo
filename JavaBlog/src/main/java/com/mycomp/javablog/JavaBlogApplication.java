package com.mycomp.javablog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
public class JavaBlogApplication {

	private static Logger logger = LoggerFactory.getLogger(JavaBlogApplication.class);

	public static void main(String[] args) {
		logger.info("Java Blog application starting");
		SpringApplication.run(JavaBlogApplication.class, args);
		logger.info("Java Blog application Started successfully");
	}

}
