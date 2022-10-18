package com.mycomp.javablog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mycomp.javablog.domain.ErrorResponse;

@ControllerAdvice
public class JavaBlogExceptionHandler extends ResponseEntityExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(JavaBlogExceptionHandler.class);

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<?> handleAllExceptions(Exception ex) {
		logger.error(ex.getMessage());
		ErrorResponse resp = new ErrorResponse();
		resp.setErrorCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		resp.setErrorDescription(
				"Error Occured while processing request, Please reach out to Support group @Javablog_support.com");
		return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}