package com.bbcall.struts.services;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.table.User;

@Service("userServices")
public class UserServices {
	@Autowired
	private UserMapper userMapper;
	Object userinfo = null;
	RandomCode randomCode = new RandomCode();

	// 用户注册
	
	public int register(String username, String password) {
		System.out.println("Here is UserServices.register method...");

		int loginResult = 0;// 新建返回值
		int checkUserNameResult = checkUserName(username);// 调用checkUserName方法并得到返回码
		
		if(checkUserNameResult == ResultCode.USERNAME_NOTEXIST){
			User user = new User();
			user.setUser_mobile(username);
			user.setUser_password(password);
			userMapper.addUserByMobile(user);
			userinfo = user;// 返回更新的user对象给userinfo
			loginResult = ResultCode.SUCCESS;
		}else{
			loginResult = checkUserNameResult;
			System.out.println(checkUserNameResult);
		}
		
		// userMapper.addUserByMobile("");
		// userMapper.addUserByAccount("");
		// userMapper.addUserByEmail("");
		
		return loginResult;
	}

	// 用户登录

	public int login(String username, String password) {
		System.out.println("Here is UserServices.login method...");

		int loginResult;// 新建返回值
		int checkUserNameResult = checkUserName(username);// 调用checkUserName方法并得到返回码

		// 判断用户名的类型：
		if (checkUserNameResult != ResultCode.USERNAME_NOTEXIST) {
			User user = (User) userinfo;// 引用user对象
			if (password.equals(user.getUser_password())) {// 验证密码是否正确
				String token = randomCode.getToken();// 正确则创建新token，并更新数据库
				while (null != userMapper.getUserByToken(token)) {// 确保token唯一
					token = randomCode.getToken();
				}
				user.setToken(token);
				userMapper.updateToken(user);
				
				user.setUser_login_time(new Date());
				userMapper.updateLoginTime(user);
				
				loginResult = ResultCode.SUCCESS;
				userinfo = user;// 返回更新的user对象给userinfo
			} else {
				loginResult = ResultCode.PASSWORD_ERROR;
			}

		} else {
			loginResult = ResultCode.USERNAME_NOTEXIST;
		}
		return loginResult;
	}

	// 检测用户名是否存在
	
	public int checkUserName(String username) {
		System.out.println("Here is UserServices.checkUserName method...");

		// 判断用户名的类型：
		if (isNumeric(username)) { // 判断登录名是否为手机号码
			User user1 = userMapper.getUserByMobile(username);
			if (null != user1) {
				System.out.println("Username_mobile exist");
				userinfo = user1;
				return ResultCode.USERNAME_MOBILE;
			}
		}
		if (username.contains("@")) { // 判断登录名是否为邮箱地址
			User user2 = userMapper.getUserByEmail(username);
			if (null != user2) {
				System.out.println("Username_email exist");
				userinfo = user2;
				return ResultCode.USERNAME_EMAIL;
			}
		}

		User user3 = userMapper.getUserByAccount(username);
		if (null != user3) { // 判断登录名是否为帐号
			System.out.println("Username_account exist");
			userinfo = user3;
			return ResultCode.USERNAME_ACCOUNT;
		} else {
			return ResultCode.USERNAME_NOTEXIST;
		}
	}

	// 用户信息修改
	
	public void update() {
		System.out.println("Here is UserServices - update method...");

		// userMapper.updateUser("");
		// userMapper.updateToken("");
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

	// 返回用户信息, 用于json返回
	
	public Object userInfo() {

		return userinfo;

	}
}
