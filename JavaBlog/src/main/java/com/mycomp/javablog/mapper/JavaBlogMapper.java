package com.mycomp.javablog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycomp.javablog.domain.BlogAudit;

@Mapper
public interface JavaBlogMapper {

	/**
	 * This method is used to insert the blog audit
	 * 
	 * @param blogAudit
	 */
	public void insertBlogAudit(@Param("blogAudit") BlogAudit blogAudit);

}
