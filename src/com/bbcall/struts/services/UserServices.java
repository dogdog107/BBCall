package com.bbcall.struts.services;

import java.math.BigInteger;
import java.util.Date;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.RandomCode;
import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.table.User;

@Service("userServices")
public class UserServices {
	@Autowired
	private UserMapper userMapper;

	Object userinfo = null;

	// #############
	// ## 用户注册
	// #############

	public int register(String account, String password, int usertype,
			String name, String picurl, BigInteger mobile, String gender,
			String email, String language, String skill) {
		System.out.println("Here is UserServices.register method...");

		if (usertype != 1 && usertype != 2 && usertype != 3) {// 检测userype
			return ResultCode.REGISTERINFO_TYPEERROR;
		}

		if (usertype == 2) {// usertype=2时为师傅号，检测注册信息是否完整
			if (isEmpty(account, password, name, picurl, gender, email,
					language, skill) || mobile == null) {
				return ResultCode.REGISTERINFO_NOTENOUGH;
			}
		}

		if (usertype == 1 || usertype == 3) {// usertype=1时为用户号，检测注册信息是否完整
			if (isEmpty(account, password)) {
				return ResultCode.REGISTERINFO_NOTENOUGH;
			}
		}

		int registerResult;// 新建返回值
		int checkUserNameResult = checkUserName(account);// 调用checkUserName方法并得到返回码

		if (checkUserNameResult == ResultCode.USERNAME_NOTEXIST) {// 检测用户名是否存在
			User user = new User();
			user.setUser_account(account);
			user.setUser_password(password);
			user.setUser_type(usertype);
			if (usertype == 2) {// 1=customer, 2=master, 3=admin
				user.setUser_status(2); // 0=active, 1=pause, 2=pending,
										// 3=locked
			} else {
				user.setUser_status(0);
			}
			user.setUser_name(name);
			user.setUser_pic_url(picurl);
			user.setUser_mobile(mobile);
			user.setUser_gender(gender);
			user.setUser_email(email);
			user.setUser_language(language);
			user.setUser_skill(skill);

			userMapper.addUserByAccount(user);// 把用户信息插入数据表
			userinfo = user;// 返回更新的user对象给userinfo
			registerResult = ResultCode.SUCCESS;
		} else {
			registerResult = ResultCode.USERNAME_EXIST;
			System.out.println(checkUserNameResult);
		}
		return registerResult;
	}

	// #############
	// ## 用户登录
	// #############

	public int login(String username, String password) {
		System.out.println("Here is UserServices.login method...");

		if (isEmpty(username, password))// 检测参数是否为空、null
			return ResultCode.REQUIREINFO_NOTENOUGH;

		int checkUserNameResult = checkUserName(username);// 调用checkUserName方法并得到返回码

		// 判断用户名的类型：
		if (checkUserNameResult != ResultCode.USERNAME_NOTEXIST) {
			User user = (User) userinfo;// 引用user对象
			if (password.equals(user.getUser_password())) {// 验证密码是否正确
				RandomCode randomCode = new RandomCode();
				String token = randomCode.getToken();// 正确则创建新token，并更新数据库
				while (null != userMapper.getUserByToken(token)) {// 确保token唯一
					token = randomCode.getToken();
				}
				user.setToken(token);
				userMapper.updateToken(user);// 插入 token 值

				user.setUser_login_time(new Timestamp(new Date().getTime()));
				userMapper.updateLoginTime(user);// 插入 login 时间

				userinfo = user;// 返回更新的user对象给userinfo
				return ResultCode.SUCCESS;
			} else {
				return ResultCode.PASSWORD_ERROR;
			}
		} else {
			return ResultCode.USERNAME_NOTEXIST;
		}
	}

	// ###################
	// ## 用户信息修改、更新
	// ###################

	public int update() {
		System.out.println("Here is UserServices.update method...");

		// userMapper.updateUser("");
		// userMapper.updateToken("");
		return 1;
	}

	// ###################
	// ## 检测用户 token
	// ###################

	public int checkToken(String token) {
		System.out.println("Here is UserServices.checkToken method...");

		if (isEmpty(token))
			return ResultCode.REQUIREINFO_NOTENOUGH;

		User user = userMapper.getUserByToken(token);

		if (null != user) {
			long currenttime = new Date().getTime();
			long logintime = user.getUser_login_time().getTime();
			long duringtime = currenttime - logintime;

			if (duringtime > (7 * 24 * 60 * 60 * 1000)) { // Token 7天有效期
				return ResultCode.USERTOKEN_EXPIRED;
			} else {
				userinfo = user;// 返回更新的user对象给userinfo
				return ResultCode.SUCCESS;
			}
		} else {
			return ResultCode.USERTOKEN_ERROR;
		}
	}

	// ###################
	// ## 检测用户名是否存在
	// ###################

	public int checkUserName(String username) {
		System.out.println("Here is UserServices.checkUserName method...");

		if (isEmpty(username))
			return ResultCode.REQUIREINFO_NOTENOUGH;

		// 判断用户名的类型：
		if (isNumeric(username)) { // 判断登录名是否为手机号码

			User user1 = userMapper.getUserByMobile(new BigInteger(username));
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

	// ###################
	// 判断是否数字的方法
	// ###################

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	// ###################
	// 判断String参数是否为空 / null
	// ###################

	public static boolean isEmpty(String... strings) {
		for (int i = 0; i < strings.length; i++) {
			if (strings[i] == null || strings[i].isEmpty()) {
				return true;
			}
		}
		return false;
	}

	// ###################
	// 返回用户信息, 用于json返回
	// ###################

	public Object userInfo() {

		return userinfo;
	}
}
