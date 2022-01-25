package com.lcomputerstudy.example.service;

import java.util.HashMap;

public interface KakaoLoginService {
	
	public String getAccessToken(String authorize_code);

	public HashMap<String, Object> getUserInfo(String access_Token);

	String kakaoLogout(String code);

}
