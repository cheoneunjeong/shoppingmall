package com.lcomputerstudy.example.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lcomputerstudy.example.domain.Category;
import com.lcomputerstudy.example.domain.Option;
import com.lcomputerstudy.example.domain.Point;
import com.lcomputerstudy.example.domain.Product;
import com.lcomputerstudy.example.domain.QABoard;
import com.lcomputerstudy.example.domain.Review;
import com.lcomputerstudy.example.domain.UserInfo;
import com.lcomputerstudy.example.service.BoardService;
import com.lcomputerstudy.example.service.CategoryService;
import com.lcomputerstudy.example.service.PointService;
import com.lcomputerstudy.example.service.ProductService;
import com.lcomputerstudy.example.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PointService pointService;
	
	@Autowired
	BoardService boardService;
	
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
		
		String category = product.getCategory();
		int point = category.indexOf("(");
		String mainCategory = category.substring(point+1, category.length()-1);
		product.setMainCategory(mainCategory);
		
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
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("delete-category")
	public ResponseEntity<?> deleteCategory(@Validated @RequestBody List<Integer> codeList) {
		for(int code : codeList) {
			categoryService.deleteCategory(code);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("product-details")
	public ResponseEntity<?> getProductDetails(@Validated int code) throws IOException {
		
		Product product = productService.getProductDetails(code);

		String type = product.getType_s();
		if(type.contains(",")) {
			product.setType(Arrays.asList(type.split(",")));
		} else {
			List<String> list = Arrays.asList(type);
			product.setType(list);
		}

		List<Option> options = productService.getOptions(code);
		product.setOptions(options);

		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@PutMapping("product")
	public ResponseEntity<?> editProductDetails(@Validated @RequestBody Product product) {
		
		String category = product.getCategory();
		int point = category.indexOf("(");
		String mainCategory = category.substring(point+1, category.length()-1);
		product.setMainCategory(mainCategory);

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
		
		productService.editProduct(product);
		
		List<Option> list3 = product.getOptions();
		for(Option option : list3) {	
			productService.editOptions(option, option.getNum());
		}
		
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	@GetMapping("userlist")
	public ResponseEntity<?> getUserList() {
		
		List<UserInfo> list = userService.getUserList();
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("point")
	public ResponseEntity<?> givePoint(@Validated @RequestBody Point point_) {

		String point = point_.getPoint();
		String id = point_.getId();
		userService.givePoint(id, point);

		int totalP = userService.getTotal_point(id);
		point_.setTotal(totalP);
		pointService.insertPoint(point_);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("point")
	public ResponseEntity<?> getPointList() {
		
		List<Point> list = pointService.getPointList();
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PutMapping("block-user")
	public ResponseEntity<?> blockUser(@Validated @RequestBody UserInfo user) {
		
		userService.updateBlockUser(user.getUsername());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("unblock-user")
	public ResponseEntity<?> unblockUser(@Validated @RequestBody UserInfo user) {
		
		userService.updateUnblockUser(user.getUsername());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("a-post")
	public ResponseEntity<?> saveAnswerPost(@Validated @RequestBody QABoard post) {
		
		boardService.insertAnswerPost(post);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("delete-post")
	public ResponseEntity<?> delete_selectedPost(@Validated @RequestBody List<QABoard> list) {
		
		for(QABoard post : list) {
			System.out.println(post.getNum());
			
			boardService.deletePost(post.getNum());
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("review-list")
	public ResponseEntity<?> getReviewList() {
		
		List<Review> list = boardService.getReviewList();
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}
