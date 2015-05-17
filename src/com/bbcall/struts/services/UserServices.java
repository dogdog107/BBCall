package com.bbcall.struts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.table.User;


@Controller("userServices")
public class UserServices {
	@Autowired
	private UserMapper userMapper;
	

	RandomCode randomCode = new RandomCode();
	
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
		User user = new User();
		String result = null;

		// 判断用户名的类型：
		if (username.length() == 11 && username.startsWith("13")
				&& isNumeric(username)) {
			// 通过账号获取用户资料
			User user1 = userMapper.getUserByMobile(username);
			if (null != user1) {
				user = user1;
				System.out.println("getUserByMobile() selected.");
			}
		} else if (username.contains("@")) {
			User user2 = userMapper.getUserByEmail(username);
			if (null != user2) {
				user = user2;
				System.out.println("getUserByEmail() selected.");
			}
		} else {
			User user3 = userMapper.getUserByAccount(username);
			if (null != user3) {
				user = user3;
				System.out.println("getUserByAccount() selected.");
			} else {
				result = "No user";
			}
		}

		System.out.println(user.toString());

		if (result != "No user" && password.equals(user.getUser_password())) {// 验证密码是否正确

			// 正确则创建新token，并更新数据库
			String token = randomCode.getToken();
			while (null != userMapper.getUserByToken(token)) {// 确保token唯一
				token = randomCode.getToken();
			}
			user.setToken(token);
			userMapper.updateToken(user);
			result = "Login Success";
			// return user.toString();
		}
		System.out.println(result);
		return result;
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
