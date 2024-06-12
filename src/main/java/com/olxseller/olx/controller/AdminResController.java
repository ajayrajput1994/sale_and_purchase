package com.olxseller.olx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.helper.ResponseData;
import com.olxseller.olx.model.MainCategory;
import com.olxseller.olx.service.CategoryService;

@RestController
@RequestMapping("/admin")
public class AdminResController {
  @Autowired
  public ResponseData responseData;
  @Autowired
  private CategoryService catService;

  @PostMapping("/category/create")
	public ResponseEntity<?> createCategory(@RequestBody MainCategory cat){
		System.out.println("category:"+cat);
		try{
      MainCategory category=catService.CreateMainCategory(cat);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS","Successfuly Created",catService.getAllMainCategory()),HttpStatus.OK);
		}catch(Exception ex){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}
  @GetMapping("/category/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable("id") int id){
		 System.out.println("category id:"+id);
		try{
      MainCategory category=catService.getCategoryById(id);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS","Successfuly Loaded",catService.getAllMainCategory()),HttpStatus.OK);
		}catch(Exception ex){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}
  @PostMapping("/category/update/{id}")
	public ResponseEntity<?> updateCategoryById(@PathVariable("id") int id,@RequestBody MainCategory cat){
		// System.out.println("category:"+cat);
		try{
      catService.updateCategory(cat,id);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS","Successfuly Update",""),HttpStatus.OK);
		}catch(Exception ex){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}
  @PostMapping("/category/delete/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable("id") int id){
		// System.out.println("category:"+cat);
		try{
      catService.deleteCategoryById(id);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS","Successfuly Deleted",""),HttpStatus.OK);
		}catch(Exception ex){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}
}
