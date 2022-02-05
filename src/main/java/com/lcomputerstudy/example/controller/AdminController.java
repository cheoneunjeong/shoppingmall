package com.lcomputerstudy.example.controller;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	@PostMapping("product")
	public ResponseEntity<?> createProduct(@Validated @RequestBody Product product) {
		
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
			type.deleteCharAt(p2);
		}
		product.setType_s(type.toString());
		
		productService.createProduct(product);
		
		List<Option> list3 = product.getOptions();
		for(Option option : list3) {
			productService.insertOptions(option, product.getCode());
		}
		
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	@RequestMapping(value="/product-files", method=RequestMethod.POST)
	public ResponseEntity<?> createProductFiles(@RequestParam("file") List<MultipartFile> multipartFiles,
												@RequestParam("code") String code) {
		
		String path = System.getProperty("user.home")+"\\shoppingmall_vue\\src\\assets\\";
		StringBuilder builder = new StringBuilder();
		
		for(MultipartFile file : multipartFiles) {
			if(!file.isEmpty()) {
				String filename = file.getOriginalFilename();
				builder.append(filename);
				builder.append(",");
				
				File f = new File(path+filename);
				
				try {
					InputStream input = file.getInputStream();
					FileUtils.copyInputStreamToFile(input, f);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		if(builder.toString().contains(",")) {
			int p = builder.toString().lastIndexOf(",");
			builder.deleteCharAt(p);
		}
		System.out.println("ÆÄÀÏ1¹ø¤Š = "+multipartFiles.get(0).getOriginalFilename());
		productService.insertMainPhoto(multipartFiles.get(0).getOriginalFilename(), code);
		productService.insertfilesname(builder.toString(), code);	

		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	@GetMapping("product")
	public ResponseEntity<?> getProductList() {
		
		List<Product> result = productService.getProductList();
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("delete-product")
	public ResponseEntity<?> deleteProduct(@Validated @RequestBody List<Integer> codeList) {
		for(int code : codeList) {
			productService.deleteProduct(code);
		}
		
		return null;
	}
	
	@PostMapping("delete-category")
	public ResponseEntity<?> deleteCategory(@Validated @RequestBody List<Integer> codeList) {
		for(int code : codeList) {
			categoryService.deleteCategory(code);
		}
		
		return null;
	}
	
	
}
