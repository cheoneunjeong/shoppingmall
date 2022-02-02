package com.lcomputerstudy.example.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcomputerstudy.example.domain.Category;
import com.lcomputerstudy.example.service.CategoryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("category")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getCategories() {
		
		List<Category> list = categoryService.getCategories();

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("category")
	public ResponseEntity<?> createCategory(@Validated @RequestBody Category category) {
		
		categoryService.insertCategory(category);
		
		List<Category> list = categoryService.getCategories();

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("childCategory")
	public ResponseEntity<?> createChildCategory(@Validated @RequestBody Category category) {

		categoryService.insertchildCategory(category);
		
		List<Category> list = categoryService.getCategories();

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("edit-Category")
	public ResponseEntity<?> updateChildCategory(@Validated @RequestBody Category category) {
		
		categoryService.editCategory(category);
		
		List<Category> list = categoryService.getCategories();
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
}
