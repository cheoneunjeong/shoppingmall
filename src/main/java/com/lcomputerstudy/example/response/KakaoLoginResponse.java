package com.lcomputerstudy.example.response;

import com.lcomputerstudy.example.domain.KakaoUser;

public class KakaoLoginResponse {

	private String access_token;
	private KakaoUser user;
	
	public KakaoLoginResponse(String token, KakaoUser user) {
		this.access_token = token;
		this.user = user;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public KakaoUser getUser() {
		return user;
	}

	public void setUser(KakaoUser user) {
		this.user = user;
	}
	
	
	
}
