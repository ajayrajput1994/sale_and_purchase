package com.olxseller.olx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.Blog;
import com.olxseller.olx.repository.BlogRepository;

@Service
public class BlogService {
	
	@Autowired
	private BlogRepository blogRepo;
	 
	
	//check url  is category or city and title
	
	public String getPageUrl(String title) {
		System.out.println("title:"+title);
		String url="";
		int i=blogRepo.findByCategoryAndCity(title, title);
		int m=blogRepo.findByMainCategory(title);
		int t=blogRepo.findByTitle(title);
		if(blogRepo.getCategory(title)>0) {
			url="citypage";
		}else if(m>0) {
			url="citypage";
		}else {
			if(t>0) {
			url="post";	
			}
		}
		return url;
	}
	
	//get all Users
		public List<Blog> getAllBlogs(){
			List<Blog> list=(List<Blog>)this.blogRepo.findAll();
			return list;
		}
		
		//get single user by id
		public Blog getBlogById(int id) {
			Blog blog=null;
			try {
		//	user = list.stream().filter(e->e.getId()==id).findFirst().get();
			Optional<Blog> findById=this.blogRepo.findById(id);
			if (findById.isPresent()) {
				blog = findById.get();
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
				return blog;
			
		}
		
		//create user
		public Blog addBlogs(Blog b) {
			Blog result=blogRepo.save(b);
			return result;
		}
		
		//delete user 
		public void deleteBlogs(int id) {
			//list.stream().filter(user->user.getId()!=id).collect(Collectors.toList());
			blogRepo.deleteById(id);
		}
		
		//update user 
		public void updateBlogs(Blog blog,int id) {
			/*
			 * list = list.stream().map(u->{ if(u.getId()==id) { u.setName(user.getName());
			 * u.setEmail(user.getEmail()); u.setPhone(user.getPhone());
			 * u.setPassword(user.getPassword()); } return u;
			 * }).collect(Collectors.toList());
			 */
			
			blog.setId(id);
			blogRepo.save(blog);
		}

}
