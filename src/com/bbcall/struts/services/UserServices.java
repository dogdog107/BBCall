package com.bbcall.struts.services;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.RandomCode;
import com.bbcall.functions.ResultCode;
import com.bbcall.functions.SHA1_Encode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.table.User;

@Service("userServices")
public class UserServices {

	// @Autowired
	// private UserSkillMapper userSkillMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AddressServices addressServices;
	
	private SHA1_Encode encrypt = new SHA1_Encode();
	private User userinfo = new User();
	private User updateduserinfo = new User();
	private List<User> userlist;

	// ################################################################################
	// ## User Register services
	// ## 用户注册
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) Register by User / Master: 'usertype'
	// ## (2) Register by User / Master: 'account'
	// ## (3) Register by User / Master: 'password'
	// ## (4) Register by Master: 'name'
	// ## (5) Register by Master: 'picurl'
	// ## (6) Register by Master: 'mobile'
	// ## (7) Register by Master: 'gender'
	// ## (8) Register by Master: 'email'
	// ## (9) Register by Master: 'language'
	// ## (10) Register by Master: 'skill'
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters:
	// ## (1) 'description'
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (1) ResultCode.REGISTERINFO_TYPEERROR
	// ## (2) ResultCode.REGISTERINFO_NOTENOUGH
	// ## (3) ResultCode.USERNAME_EXIST
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return userinfo:
	// ## (1) userinfo
	// ##
	// ################################################################################

	public int register(String token, String account, String password, Integer usertype,
			String name, String picurl, BigInteger mobile, Integer gender,
			String email, String language, String skill, String description, String accessgroup) {
		System.out.println("Here is UserServices.register method...");

		int registermode = 0; // 记录register的模式: 1=user,2=admin
		String defaultaccess = "";
		
		// ***** 检测 account & password 是否符合格式 *****
		if (!Tools.isEmpty(account, password)) {
			if (account.length() < 6) {
				return ResultCode.USERACCOUNT_ERROR;
			} else if (password.length() < 6) {
				return ResultCode.USERPASSWORD_ERROR;
			}
		} else {
			return ResultCode.REGISTERINFO_NOTENOUGH;
		}
		
		// ***** 检测 usertype 是否正确 *****
		if (usertype != 1 && usertype != 2 && usertype != 3 && usertype != 4) {
			return ResultCode.USERTYPE_ERROR;
		}

		if (usertype == 1) { // usertype=1时为用户号，检测注册信息是否完整
			registermode = checkRegisterMode(token); // 判断register的模式:1=user,2=admin
			defaultaccess = "user_default"; // 分配user_default权限
		}

		if (usertype == 2) { // usertype=2时为师傅号，检测注册信息是否完整
			if (Tools.isEmpty(name, picurl, email, language, skill)
					|| mobile == null || gender == null) {
				return ResultCode.REGISTERINFO_NOTENOUGH;
			}
			registermode = checkRegisterMode(token); // 判断register的模式:1=user,2=admin
			defaultaccess = "master_default"; // 分配master_default权限
		}

		if (usertype == 3 || usertype == 4) {// usertype=3 / 4 时为管理员号，检测注册信息是否完整
			if (Tools.isEmpty(token)) {
				return ResultCode.REGISTERINFO_NOTENOUGH;
			}
			registermode = checkRegisterMode(token); // 判断register的模式:1=user,2=admin
			if (registermode == 2) {
				if (userinfo.getUser_type().equals(3)) {
					return ResultCode.ACCESS_REJECT; // register的模式为用户模式模式时，拒绝注册。
				}
				defaultaccess = "admin_default"; // 当权限组参数为空时，分配admin_default权限
			} else {
				return ResultCode.ACCESS_REJECT; // register的模式为用户模式模式时，拒绝注册。
			}
			
			
		}
		
		int registerResult;// 新建返回值
		int checkUserNameResult = checkUserName(account);// 调用checkUserName方法并得到返回码

		if (checkUserNameResult == ResultCode.USERNAME_NOTEXIST) {// 检测用户名是否存在
			User user = new User(); // 实例化用户对象
			
			// ***** 添加account *****
			user.setUser_account(account);
			
			// ***** 添加加密的password *****
			user.setUser_password(encrypt.getDigestOfString(password.getBytes()));
			
			// ***** 添加 usertype *****
			user.setUser_type(usertype);
			if (usertype == 2 && registermode == 1) {// 当用户在注册师傅账号时，状态转为待审核
				user.setUser_status(3); // 1=active, 2=pause, 3=pending,
										// 4=locked
			} else {
				user.setUser_status(1);
			}
			
			// ***** 添加 picurl *****
			while (Tools.isEmpty(picurl)) {
				if (usertype == 3) {
					picurl = "/BBCall/UserPhoto/Admin_photo.png";
				} else if (usertype == 4){
					picurl = "/BBCall/UserPhoto/SuperAdmin_photo.png";
				}else {
					if (gender != null) {
						switch (gender) {
						case 1:
							picurl = "/BBCall/UserPhoto/Male_photo.png";
							break;
						case 2:
							picurl = "/BBCall/UserPhoto/Female_photo.png";
							break;
						default:
							picurl = "/BBCall/UserPhoto/Default_photo.png";
							break;
						}
					} else {
						picurl = "/BBCall/UserPhoto/Default_photo.png";
					}
				}
			}
			if (!Tools.isEmpty(picurl)) {
				user.setUser_pic_url(picurl);
			}
			
			// ***** 添加姓名 *****
			if (!Tools.isEmpty(name)) {
				user.setUser_name(name);
			}
			
			// ***** 添加手机号 判断格式 *****
			if (mobile != null) {
				if (Tools.isNumeric(mobile.toString())
						&& (mobile.toString().length() >= 8)) {
					user.setUser_mobile(mobile);
				} else {
					return ResultCode.USERMOBILE_ERROR;
				}
			}

			// ***** 添加性别 *****
			if (gender != null) {
				if ((gender.equals(1) || gender.equals(2))) { // 1=Male,2=Female
					user.setUser_gender(gender);
				} else {
					return ResultCode.USERGENDER_ERROR;
				}
			}
			
			// ***** 添加邮箱 *****
			if (!Tools.isEmpty(email)) {
				if (email.contains("@")) {
					user.setUser_email(email);
				} else {
					return ResultCode.USEREMAIL_ERROR;
				}
			}

			// ***** 添加语言 *****
			if (!Tools.isEmpty(language)) {
				user.setUser_language(language);
			}
			
			// ***** 添加技能 *****
			if (!Tools.isEmpty(skill)) {
				user.setUser_skill(skill);
			}

			// ***** 添加描述 *****
			if (!Tools.isEmpty(description)) {
				user.setUser_description(description);
			}
			
			// ***** 添加权限组 *****
			if (registermode == 2) { // 判断register的模式:1=user,2=admin
				if(Tools.isEmpty(accessgroup)) {
					user.setUser_access_group(defaultaccess);
				} else {
					user.setUser_access_group(accessgroup);
				}
			} else {
				user.setUser_access_group(defaultaccess);
			}
			
			userMapper.addUserByAccount(user);// 把用户信息插入数据表
			// ** user_skill子表的逻辑部分
			// if (!isEmpty(skill)) {
			// UserSkill userskill = new UserSkill();
			// userskill.setUser_id(user.getUser_id());
			// String skillTemp[] = skill.split(";");
			// for (int i = 0; i < skillTemp.length; i++) {
			// userskill.setUser_skill(skillTemp[i]);
			// userSkillMapper.addUserSkill(userskill);
			// }
			// }

			userinfo = userMapper.getUserByAccount(account); // 返回更新的user对象给userinfo
			registerResult = ResultCode.SUCCESS;
		} else {
			registerResult = ResultCode.USERNAME_EXIST;
			System.out.println(checkUserNameResult);
		}
		return registerResult;
	}

	// ################################################################################
	// ## User Login services
	// ## 用户登录
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) 'username'
	// ## (2) 'password'
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NULL
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (1) ResultCode.REQUIREINFO_NOTENOUGH
	// ## (2) ResultCode.SUCCESS
	// ## (3) ResultCode.PASSWORD_ERROR
	// ## (4) ResultCode.USERNAME_NOTEXIST
	// ## (5) checkUserStatus()
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return userinfo:
	// ## (1) userinfo
	// ##
	// ################################################################################

	public int login(String username, String password) {
		System.out.println("Here is UserServices.login method...");

		if (Tools.isEmpty(username, password))// 检测参数是否为空、null
			return ResultCode.REQUIREINFO_NOTENOUGH;

		int checkUserNameResult = checkUserName(username);// 调用checkUserName方法并得到返回码

		// 判断用户名的类型：
		if (checkUserNameResult != ResultCode.USERNAME_NOTEXIST) {
			User user = (User) userinfo; // 引用user对象
			int checkUserStatusResult = checkUserStatusValue(user.getUser_status());
			if (checkUserStatusResult == ResultCode.USERSTATUS_ACTIVE) { // 验证用户状态
				if (encrypt.getDigestOfString(password.getBytes()).equals(user.getUser_password())) {// 验证加密的密码是否正确
					RandomCode randomCode = new RandomCode();
					String token = randomCode.getToken();// 正确则创建新token，并更新数据库
					while (null != userMapper.getUserByToken(token)) {// 确保token唯一
						token = randomCode.getToken();
					}
					user.setUser_token(token);
					userMapper.updateToken(user);// 插入 token 值

					user.setUser_login_time(new Timestamp(new Date().getTime()));
					userMapper.updateLoginTime(user);// 插入 login 时间

					userinfo = user;// 返回更新的user对象给userinfo
					return ResultCode.SUCCESS;
				} else {
					return ResultCode.PASSWORD_ERROR;
				}
			} else {
				return checkUserStatusResult;
			}
		} else {
			return ResultCode.USERNAME_NOTEXIST;
		}
	}

	// ################################################################################
	// ## User information Update services
	// ## 用户信息修改、更新
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) 'token'
	// ## (2) Update by admin: 'token' & 'userid'
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: (leave blank will not change)
	// ## (1) 'account'
	// ## (2) 'password'
	// ## (3) 'usertype'
	// ## (4) 'name'
	// ## (5) 'picurl'
	// ## (6) 'mobile'
	// ## (7) 'gender'
	// ## (8) 'addresscode'
	// ## (9) 'address'
	// ## (10) 'email'
	// ## (11) 'language'
	// ## (12) 'skill'
	// ## (13) 'description'
	// ## (14) 'accessgroup'
	// ## (15) 'status'
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (1) ResultCode.REQUIREINFO_NOTENOUGH
	// ## (2) ResultCode.SUCCESS
	// ## (3) ResultCode.USERID_ERROR
	// ## (4) ResultCode.UNKNOWN_ERROR
	// ## (5) checkResult()
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return userinfo:
	// ## (1) userinfo
	// ##
	// ################################################################################

	public int update(String account, String password, Integer usertype,
			String name, String picurl, BigInteger mobile, Integer gender,
			Integer addresscode, String address, String email, String language,
			String skill, String description, String accessgroup,
			Integer status, String token, Integer userid) {
		System.out.println("Here is UserServices.update method...");

		int updatemode = 0; // 记录update的模式: 1=user,2=admin
		int changecount = 0; // 记录更改次数
		User user = new User();

		// ***** 检测 token & userid *****
		if (Tools.isEmpty(token) && userid == null)// 检测必要参数是否为空、null
			return ResultCode.REQUIREINFO_NOTENOUGH;
		if (!Tools.isEmpty(token)) {
			int checkResult = checkUserToken(token);
			if (checkResult == ResultCode.SUCCESS) {
				user = userinfo;
				if (userid != null) {
					if (user.getUser_type().equals(3) || user.getUser_type().equals(4)) { // 管理员后台update模式
						user = userMapper.getUserById(userid); // 根据userID读取用户数据
						updatemode = 2;
						if (null == user)
							return ResultCode.USERID_ERROR;
					} else if (userid.equals(user.getUser_id())) {
						updatemode = 1; // 用户update模式
					} else {
						return ResultCode.ACCESS_REJECT;
					}
				} else {
					updatemode = 1; // 用户update模式
				}
			} else
				return checkResult;
		} else {
			return ResultCode.REQUIREINFO_NOTENOUGH;
		}

		// ***** 检测 addresscode & address *****
		if (addresscode != null && addresscode != 0 && (addresscode + "").length() != 6)// 判断地址码是否六位数
			return ResultCode.ADDRESSCODE_ERROR;

		if (addresscode != null && addresscode != 0 && Tools.isEmpty(address))
			return ResultCode.ADDRESS_NOTMATCH;

		if (addresscode == null && !Tools.isEmpty(address)) {// 判断更改的地址名是否与原来的addresscode对应
			addresscode = user.getUser_address_code();// 读取数据库的addresscode

			if (addresscode == null)
				return ResultCode.ADDRESSCODE_ERROR;

			String addressname = addressServices.checkAddresscodeName(addresscode);// 调用checkAddresscodeName方法，获取地址名
			if (address.replace(";", "").matches(addressname + "(.*)")) {// 判断地址名是否一致
				user.setUser_address(address);// 放入新地址名
				changecount++;
				System.out.println("address changed!");
			} else
				return ResultCode.ADDRESS_NOTMATCH;
		}

		if ((addresscode != null && !Tools.isEmpty(address))
				&& (!addresscode.equals(user.getUser_address_code()) || !address.equals(user
						.getUser_address()))) {

			String addressname = addressServices.checkAddresscodeName(addresscode);// 调用checkAddresscodeName方法，获取地址名
			if (addressname == null)// 如果addressname 返回null, 表明addresscode错误
				return ResultCode.ADDRESSCODE_ERROR;
			if (address.replace(";", "").matches(addressname + "(.*)")) {// 判断地址名是否一致
				user.setUser_address(address);// 放入新地址名
				user.setUser_address_code(addresscode);// 放入新地址码
				changecount++;
				System.out.println("address&addresscode changed!");
			} else
				return ResultCode.ADDRESS_NOTMATCH;
		}

		// ***** 检测其它变量 *****
		if (!Tools.isEmpty(account) && !account.equals(user.getUser_account())) {
			if (account.length() >= 6){
				user.setUser_account(account);
				changecount++;
				System.out.println("account changed!");
			} else {
				return ResultCode.USERACCOUNT_ERROR;
			}
		}
		if (!Tools.isEmpty(password) && !encrypt.getDigestOfString(password.getBytes()).equals(user.getUser_password())) {
			if (password.length() >= 6) {
				user.setUser_password(encrypt.getDigestOfString(password.getBytes()));
				changecount++;
				System.out.println("password changed!");
			} else {
				return ResultCode.USERPASSWORD_ERROR;
			}
		}
		if (!Tools.isEmpty(name) && !name.equals(user.getUser_name())) {
			user.setUser_name(name);
			changecount++;
			System.out.println("name changed!");
		}
		if (Tools.isEmpty(picurl)
				&& (Tools.isEmpty(user.getUser_pic_url()) || !Tools
						.isNumeric(user
								.getUser_pic_url()
								.substring(
										user.getUser_pic_url().lastIndexOf("/") + 1)
								.split("_")[0]))) {
			if (user.getUser_type().equals(3)) {
				picurl = "/BBCall/UserPhoto/Admin_photo.png";
			} else if (user.getUser_type().equals(4)) {
				picurl = "/BBCall/UserPhoto/SuperAdmin_photo.png";
			} else {
				switch (user.getUser_gender()) {
				case 1:
					picurl = "/BBCall/UserPhoto/Male_photo.png";
					break;
				case 2:
					picurl = "/BBCall/UserPhoto/Female_photo.png";
					break;
				}
			}
		}
		if (!Tools.isEmpty(picurl) && !picurl.equals(user.getUser_pic_url())) {
			user.setUser_pic_url(picurl);
			changecount++;
			System.out.println("picurl changed!");
			if (updatemode == 1) {// 用户模式时，user状态转为pending 待审核
				user.setUser_status(3);
				System.out.println("status changed as user mode!(usertype)");
			}
		}
		if (mobile != null && !mobile.equals(user.getUser_mobile())) {
			if (Tools.isNumeric(mobile.toString())
					&& (mobile.toString().length() >= 8)) {
				user.setUser_mobile(mobile);
				changecount++;
				System.out.println("mobile changed!");
			} else {
				return ResultCode.USERMOBILE_ERROR;
			}
		}
		if (gender != null && !gender.equals(user.getUser_gender())) {
			if ((gender.equals(1) || gender.equals(2))) { // 1=Male,2=Female
				user.setUser_gender(gender);
				changecount++;
				System.out.println("gender changed!");
			} else {
				return ResultCode.USERGENDER_ERROR;
			}
		}
		if (!Tools.isEmpty(email) && !email.equals(user.getUser_email())) {
			if (email.contains("@")) {
				user.setUser_email(email);
				changecount++;
				System.out.println("email changed!");
			} else {
				return ResultCode.USEREMAIL_ERROR;
			}
		}
		if (!Tools.isEmpty(language) && !language.equals(user.getUser_language())) {
			user.setUser_language(language);
			changecount++;
			System.out.println("language changed!");
		}
		if (!Tools.isEmpty(description)
				&& !description.equals(user.getUser_description())) {
			user.setUser_description(description);
			changecount++;
			System.out.println("description changed!");
		}
		if (!Tools.isEmpty(accessgroup)
				&& !accessgroup.equals(user.getUser_access_group())) {
			user.setUser_access_group(accessgroup);
			changecount++;
			System.out.println("accessgroup changed!");
		}
		if (updatemode == 2 && status != null
				&& !user.getUser_status().equals(status)) {// admin模式时，判断status是否有变化
			user.setUser_status(status);
			changecount++;
			System.out.println("status changed!");
		}
		if (!Tools.isEmpty(skill) && !skill.equals(user.getUser_skill())) {
			user.setUser_skill(skill);
			changecount++;
			System.out.println("skill changed!");
			if (updatemode == 1) {// 用户模式时，user状态转为pending 待审核
				user.setUser_status(3);
				System.out.println("status changed as user mode!(skill)");
			}
		}
		if (usertype != null && !user.getUser_type().equals(usertype)) {
			user.setUser_type(usertype);
			changecount++;
			System.out.println("usertype changed!");
			if (updatemode == 1) {// 用户模式时，user状态转为pending 待审核
				user.setUser_status(3);
				System.out.println("status changed as user mode!(usertype)");
			}
		}

		// ** user_skill子表的逻辑部分
		// if (!isEmpty(skill)) {
		// UserSkill userskill = new UserSkill();
		// userskill.setUser_id(user.getUser_id());
		// String skillTemp[] = skill.split(";");
		// for (int i = 0; i < skillTemp.length; i++) {
		// userskill.setUser_skill(skillTemp[i]);
		// userSkillMapper.addUserSkill(userskill);
		// }
		// }

		if (changecount > 0)
			userMapper.updateUser(user);// 把用户信息插入数据表
		
		switch (updatemode) {
		case 1:
			userinfo = user;// 返回更新后的user对象给userinfo
			break;
		case 2:
			if (user.getUser_id().equals(userinfo.getUser_id())){
				userinfo = user;// 返回更新的user对象给userinfo
			} else {
				updateduserinfo = user;// 返回更新后的user对象给updateduserinfo
			}
			break;
		}
		return ResultCode.SUCCESS;

	}

	public int checkUserListWhereOrderBy(String order_col, String order_value,String where_col, String where_value) {
		System.out.println("Here is UserServices.checkUserListWhereOrderBy method...");
		
		List<User> userlist = userMapper.listUserWhereOrderBy(where_col, order_col, where_value, order_value);
		if (userlist.size() > 0) {
			this.userlist = userlist;
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.USERLIST_NULL;
		}
	}
	
	// ###################
	// ## 拉取User表
	// ###################

	public int checkUserList(String col_name, String specify_value, String search_value) {
		System.out.println("Here is UserServices.checkUserList method...");
		
		List<User> userlist = userMapper.listUserOrderBy(col_name, specify_value, search_value);
		if (userlist.size() > 0) {
			this.userlist = userlist;
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.USERLIST_NULL;
		}
		
//		int checkTokenResult = checkUserToken(token);
//		if (checkTokenResult == ResultCode.SUCCESS) {
//			if (userinfo.getUser_type() != 3) {
//				return ResultCode.ACCESS_REJECT;
//			}
//			List<User> userlist = userMapper.listUserOrderBy(col_name, specify_value, search_value);
//			if (userlist.size() > 0) {
//				this.userlist = userlist;
//				return ResultCode.SUCCESS;
//			} else {
//				return ResultCode.USERLIST_NULL;
//			}
//		} else {
//			return checkTokenResult;
//		}
	}

	// ###################
	// ## 检测用户 token
	// ###################

	public int checkUserToken(String token) {
		System.out.println("Here is UserServices.checkUserToken method...");

		if (Tools.isEmpty(token))
			return ResultCode.REQUIREINFO_NOTENOUGH;

		User user = userMapper.getUserByToken(token);

		if (null != user) {
			int checkUserStatusResult = checkUserStatusValue(user.getUser_status());
			if (checkUserStatusResult == ResultCode.USERSTATUS_ACTIVE) {
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
				return checkUserStatusResult;
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

		if (Tools.isEmpty(username))
			return ResultCode.REQUIREINFO_NOTENOUGH;

		// 判断用户名的类型：
		if (Tools.isNumeric(username)) { // 判断登录名是否为手机号码
			userinfo = userMapper.getUserByMobile(new BigInteger(username));
			if (null != userinfo) {
				System.out.println("Username_mobile exist");
				return ResultCode.USERNAME_MOBILE;
			}
		}
		if (username.contains("@")) { // 判断登录名是否为邮箱地址
			userinfo = userMapper.getUserByEmail(username);
			if (null != userinfo) {
				System.out.println("Username_email exist");
				return ResultCode.USERNAME_EMAIL;
			}
		}

		userinfo = userMapper.getUserByAccount(username);
		if (null != userinfo) { // 判断登录名是否为帐号
			System.out.println("Username_account exist");
			return ResultCode.USERNAME_ACCOUNT;
		} else {
			return ResultCode.USERNAME_NOTEXIST;
		}
	}

	// ###################
	// ## 检测用户状态
	// ###################

	public int checkUserStatusValue(int status) {
		int checkUserResult;

		switch (status) {// * 1=active, 2=pause, 3=pending, 4=locked
		case 1:
			checkUserResult = ResultCode.USERSTATUS_ACTIVE;
			break;
		case 2:
			checkUserResult = ResultCode.USERSTATUS_PAUSE;
			break;
		case 3:
			checkUserResult = ResultCode.USERSTATUS_PENDING;
			break;
		case 4:
			checkUserResult = ResultCode.USERSTATUS_LOCKED;
			break;
		default:
			checkUserResult = ResultCode.USERSTATUS_ERROR;
			break;
		}
		return checkUserResult;
	}
	
	// ###################
	// ## 检测注册模式
	// ###################

	public int checkRegisterMode(String token) {
		int registermode; // 记录register的模式: 1=user,2=admin
		if (!Tools.isEmpty(token)) { // 判断是否管理员在创建用户
			int checkTokenResult = checkUserToken(token);
			if (checkTokenResult == ResultCode.SUCCESS) {
				userinfo.getUser_type();
				switch (userinfo.getUser_type()) {
				case 3:
					registermode = 2; // 管理员注册模式
					break;
				case 4:
					registermode = 2; // 管理员注册模式
					break;
				default:
					registermode = 1; // 用户注册模式
					break;
				}
			} else {
				registermode = 1; // 用户注册模式
			}
		} else {
			registermode = 1; // 用户注册模式
		}
		return registermode;
	}

	// ###################
	// ## 检测用户修改权限
	// ###################

	public int checkUpdateAccess(String token, Integer userid) {
		System.out.println("Here is UserServices.checkUpdateAccess method...");
		if (Tools.isEmpty(token) && userid == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;

		if (userid.equals(4)) { // superadmin 不能被修改
			return ResultCode.ACCESS_REJECT;
		}

		int tokenresult = checkUserToken(token); // 调用userServices.checkUserTokenes
		if (tokenresult == ResultCode.SUCCESS) {
			Integer updateUserID = userinfo.getUser_type();
			switch (userid) {
			case 3:
				if (!updateUserID.equals(4)) {
					return ResultCode.ACCESS_REJECT;
				}
				break;
			case 2:
				if (!updateUserID.equals(3) || !updateUserID.equals(4)) {
					return ResultCode.ACCESS_REJECT;
				}
				break;
			case 1:
				if (!updateUserID.equals(3) || !updateUserID.equals(4)) {
					return ResultCode.ACCESS_REJECT;
				}
				break;

			default:
				return ResultCode.UNKNOWN_ERROR;
			}
			return ResultCode.SUCCESS;
		} else {
			return tokenresult;
		}
	}

	/**
	 * getUserById
	 * @param userid
	 * @return
	 */
	public int getUserById(Integer userid){
		System.out.println("Here is UserServices.getUserById method...");
		if (userid == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		User tempUser = userMapper.getUserById(userid);
		if (tempUser != null) {
			userinfo = tempUser;
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.USERID_ERROR;
		}
	}

	/**
	 * deleteUserById
	 * @param userid
	 * @return
	 */
	public int deleteUserById(Integer userid) {
		System.out.println("Here is UserServices.deleteUserById method...");
		if (userid == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		userMapper.deleteUserById(userid);
		return ResultCode.SUCCESS;
	}
	
	// ###################
	// ##  更新用户评分
	// ###################
	public int updateUserGrade (Integer userid, Double usergrade) {
		System.out.println("Here is UserServices.updateUserGrade method...");
		if (userid == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		User tempUser = userMapper.getUserById(userid);
		if (tempUser != null) {
			tempUser.setUser_grade(usergrade);
			userMapper.updateUser(tempUser);
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.USERID_ERROR;
		}
	}
	
	//getter & setter
	
	public User getUserinfo() {
		return userinfo;
	}

	public List<User> getUserlist() {
		return userlist;
	}

	public User getUpdateduserinfo() {
		return updateduserinfo;
	}

	// public List<UserSkill> test(int test){
	//
	// List<UserSkill> temp = userSkillMapper.getUserSkillById(test);
	//
	// for (int i = 0; i < temp.size(); i++) {
	// System.out.println(temp.get(i).getUser_skill());
	// }
	//
	// return temp;
	// }
}
