package com.bbcall.struts.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.table.User;

public class UserServices {
	@Autowired
	private UserMapper userMapper;

	//用户注册
	public void register() {
		System.out.println("Here is UserServices - register method...");
		
//		userMapper.addUserByMobile("");
//		userMapper.addUserByAccount("");
//		userMapper.addUserByEmail("");
	}
	//用户登录
	public String login(String username, String password) {
		System.out.println("Here is UserServices - Login method...");
		
		//判断是否为手机号码：
		String mobileStartStr = "13";
		if (username.length() ==11 && username.startsWith(mobileStartStr) && isNumeric(username)){
			//通过账号获取用户资料
			User user = userMapper.getUserByMobile(username);
			
		}
		
		
		
		userMapper.getUserByAccount("");
//		userMapper.getUserByMobile("");
//		userMapper.getUserByEmail("");
//		userMapper.getUserById(1);
//		userMapper.getUserByToken("");
		return "";
	}
	//用户信息修改
	public void update() {
		System.out.println("Here is UserServices - update method...");
		
//		userMapper.updateUser("");
//		userMapper.updateToken("");
	}
	// 判断是否数字的方法
	private static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
