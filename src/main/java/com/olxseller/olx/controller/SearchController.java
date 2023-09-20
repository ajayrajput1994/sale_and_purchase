package com.olxseller.olx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.model.Blog;
import com.olxseller.olx.repository.BlogRepository;

@RestController
public class SearchController {
	
	@Autowired
	private BlogRepository blogRepo;
	
	@GetMapping("/admin/blog/{query}")
	public ResponseEntity<?> searchblog(@PathVariable("query") String query){
		System.out.print(query);
		
		List<Blog> b=this.blogRepo.getBlogSearchByContent(query);
		
		return ResponseEntity.ok(b);
	}

}
