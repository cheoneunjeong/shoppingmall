package com.lcomputerstudy.example.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lcomputerstudy.example.config.JwtUtils;
import com.lcomputerstudy.example.domain.KakaoUser;
import com.lcomputerstudy.example.domain.User;
import com.lcomputerstudy.example.request.JoinRequest;
import com.lcomputerstudy.example.request.LoginRequest;
import com.lcomputerstudy.example.response.JwtResponse;
import com.lcomputerstudy.example.response.KakaoLoginResponse;
import com.lcomputerstudy.example.service.KakaoLoginService;
import com.lcomputerstudy.example.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/public")
public class PublicController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserService userservice;
	
	@Autowired
	private KakaoLoginService ks;
	
	@GetMapping("/kakaologin")
	public ResponseEntity<?> kakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception {
		System.out.println("#####"+code);
		
		String access_token = ks.getAccessToken(code);
		KakaoUser user = ks.getUserInfo(access_token);
		
		KakaoLoginResponse response = new KakaoLoginResponse(access_token, user);
	
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/kakaologout")
	public ResponseEntity<?> kakaologout(@RequestParam(value = "code", required = false) String code) throws Exception {
		
		String result = ks.kakaoLogout(code);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("login")
	public ResponseEntity<?> loginUser(@Validated @RequestBody LoginRequest loginUser) {
		
		logger.info("test" + loginUser);
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		User user = (User) authentication.getPrincipal();
		logger.info("dddd"+ authentication.getPrincipal());
		
		List<String> roles = user.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, user.getUsername(), user.getName(), roles));
	}
//	
//	@PostMapping("user")
//	public ResponseEntity<?> insertNewUser(@Validated @RequestBody JoinRequest joinUser) {
//		
//		String encodedPassword = new BCryptPasswordEncoder().encode(joinUser.getPassword());
//		
//		User user = new User();
//		
//	}
//	
}
