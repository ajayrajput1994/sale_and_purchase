package com.olxseller.olx.service;
import java.util.List;
import org.springframework.stereotype.Component;
import com.olxseller.olx.model.Blog;

@Component
public interface BlogService { 
	public Blog addBlogs(Blog b);

	public Blog updateBlogByAdmin(Blog b,int id, int uid);
	
	public List<Blog> getAllBlogs();

	public Blog getBlogById(int id);

	public Blog getBlogDetailByTitle(String name);

	public String getPageUrl(String title); 

	public void deleteBlogs(int id);

	public Blog updateBlogs(Blog blog,int id);
}
