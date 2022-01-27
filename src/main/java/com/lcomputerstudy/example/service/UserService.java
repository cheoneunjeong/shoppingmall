package com.lcomputerstudy.example.service;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.lcomputerstudy.example.domain.User;


public interface UserService extends UserDetailsService {
	
	public User readUser(String username);
	
	Collection<GrantedAuthority> getAuthorities(String username);

	public void createUser(User user);

	public void createAuthority(User user);

	public int findKakaoId(String kakaoId);

}
