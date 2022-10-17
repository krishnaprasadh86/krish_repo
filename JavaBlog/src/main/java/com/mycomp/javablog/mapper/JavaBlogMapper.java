package com.mycomp.javablog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycomp.javablog.domain.Address;
import com.mycomp.javablog.domain.BlogAudit;
import com.mycomp.javablog.domain.BlogPost;
import com.mycomp.javablog.domain.Company;
import com.mycomp.javablog.domain.GeoLocation;
import com.mycomp.javablog.domain.User;
import com.mycomp.javablog.domain.UserBlogs;

@Mapper
public interface JavaBlogMapper {

	public List<User> getAllUsers();

	public void insertBlogUser(@Param("user") User user);

	public void insertAddress(@Param("address") Address address);

	public void insertGeoLocation(@Param("geoLocation") GeoLocation geoLocation);

	public void insertCompany(@Param("company") Company company);

	public void insertBlogPost(@Param("blogPost") BlogPost blogPost);

	public void insertBlogAudit(@Param("blogAudit") BlogAudit blogAudit);
	
	public List<UserBlogs> getAllUserBlogs();

}
