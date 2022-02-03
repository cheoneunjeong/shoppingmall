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
import com.lcomputerstudy.example.domain.Option;
import com.lcomputerstudy.example.domain.Product;
import com.lcomputerstudy.example.service.CategoryService;
import com.lcomputerstudy.example.service.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
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
	
	@PostMapping("CreateProduct")
	public ResponseEntity<?> createProduct(@Validated @RequestBody Product product) {

		System.out.println(product.isSale());
		System.out.println(product.getDesc());
		
		List<String> list = product.getCategory();
		StringBuilder category = new StringBuilder();
		for(String s : list) {
			category.append(s);
			category.append(",");
		}
		if (category.toString().contains(",")) {
			int p = category.toString().lastIndexOf(",");
			category.deleteCharAt(p);
		}
		product.setCategory_s(category.toString());
			
		List<String> list2 = product.getType();
		StringBuilder type = new StringBuilder();
		for(String t : list2) {
			type.append(t);
			type.append(",");
		}
		if (type.toString().contains(",")) {
			int p2 = type.toString().lastIndexOf(",");
			category.deleteCharAt(p2);
		}
		product.setType_s(type.toString());
		
		List<Option> list3 = product.getOptions();
		for(Option option : list3) {
			productService.insertOptions(option, product.getCode());
		}
	
		productService.createProduct(product);
		
		return null;
	}
	
	
}
