package com.bbcall.mybatis.dao;

import java.math.BigInteger;
import java.util.List;

import com.bbcall.mybatis.table.User;

public interface UserMapper {
//	void save(User user);
//
//	void update(User user);
//
//	void delete(int id);
//
//	User findById(int id);
//
//	List<User> findAll();

	// 通过帐号创建用户
	public void addUserByAccount(User user);
	
	// 通过手机号创建用户
//	public void addUserByMobile(User user);

	// 通过邮箱创建用户
//	public void addUserByEmail(User user);

	// 通过账号获取用户资料
	public User getUserByAccount(String user_account);

	// 通过手机获取用户资料
	public User getUserByMobile(BigInteger user_mobile);

	// 通过邮箱获取用户资料
	public User getUserByEmail(String user_email);

	// 通过id获取用户资料
	public User getUserById(Integer user_id);

	// 通过token获取用户资料
	public User getUserByToken(String user_token);

	// 更新用户资料
	public void updateUser(User user);

	// 更新登录token
	public void updateToken(User user);
	
	// 更新登录loginTime
	public void updateLoginTime(User user);
}
