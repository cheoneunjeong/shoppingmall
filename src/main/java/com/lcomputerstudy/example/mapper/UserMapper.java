package com.lcomputerstudy.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.GrantedAuthority;

import com.lcomputerstudy.example.domain.User;
import com.lcomputerstudy.example.domain.UserInfo;

@Mapper
public interface UserMapper {

	User readUser(String username);

	List<GrantedAuthority> readAuthorities(String username);

	void createUser(User user);

	void createAuthority(User user);

	User getKakaoUserId(String kakaoId);

	int findKakaoId(String kakaoId);

	UserInfo readUser_refresh(String username);

	void deleteUser(String username);

	void deleteAuthority(String username);

	void deleteRoleAdmin(String username);

}
