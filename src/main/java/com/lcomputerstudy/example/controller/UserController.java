package com.lcomputerstudy.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcomputerstudy.example.config.JwtUtils;
import com.lcomputerstudy.example.domain.Product;
import com.lcomputerstudy.example.domain.UserInfo;
import com.lcomputerstudy.example.response.JwtResponse;
import com.lcomputerstudy.example.response.WishListResponse;
import com.lcomputerstudy.example.service.ProductService;
import com.lcomputerstudy.example.service.UserService;

import lombok.Delegate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/wishlist")
	public ResponseEntity<?> updateWishList(@Validated @RequestBody UserInfo user) {
		System.out.println("############");
		for(int s : user.getWishList()) {
			
			userService.insertWishList(s, user.getUsername());
		}
		List<Integer> list = userService.getWishList(user.getUsername());

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("wishlist-details")
	public ResponseEntity<?> getWishListDetails(@Validated @RequestBody List<Integer> code) {
		
		List<Product> list = new ArrayList<>();
		for(int c : code) {
			Product product = productService.getProductDetails(c);
			list.add(product);
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@DeleteMapping("wishlist")
	public ResponseEntity<?> deleteWishItem(HttpServletRequest request, @Validated int code) {
		
		String token = new String();
		token = request.getHeader("Authorization");

		if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			token = token.substring(7, token.length());
		}
		
		String username = jwtUtils.getUserEmailFromToken(token);
		
		userService.deleteWishItem(code, username);
		
		List<Integer> wishItems = userService.getWishList(username);
		List<Product> list = new ArrayList<>();
		for(int c : wishItems) {
			Product product = productService.getProductDetails(c);
			list.add(product);
		}
		
		return ResponseEntity.ok(new WishListResponse(wishItems, list));
	}
}
