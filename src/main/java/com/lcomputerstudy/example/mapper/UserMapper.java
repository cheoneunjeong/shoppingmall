package com.lcomputerstudy.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.GrantedAuthority;

import com.lcomputerstudy.example.domain.User;

@Mapper
public interface UserMapper {

	User readUser(String username);

	List<GrantedAuthority> readAuthorities(String username);

	void createUser(User user);

	void createAuthority(User user);

	User getKakaoUserId(String kakaoId);

	int findKakaoId(String kakaoId);

}
