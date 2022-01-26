package com.lcomputerstudy.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lcomputerstudy.example.domain.KakaoUser;
import com.lcomputerstudy.example.response.KakaoLoginResponse;
import com.lcomputerstudy.example.service.KakaoLoginService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/public")
public class PublicController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
	
}
