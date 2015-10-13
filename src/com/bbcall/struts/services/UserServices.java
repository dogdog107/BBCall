package com.bbcall.struts.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bbcall.functions.RandomCode;
import com.bbcall.functions.ResultCode;
import com.bbcall.functions.SHA1_Encode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.table.User;
import com.github.pagehelper.PageHelper;

@Scope("prototype")
@Service("userServices")
public class UserServices {

	private static Logger logger = Logger.getLogger(UserServices.class);  
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AddressServices addressServices;
	@Autowired
	private ReferdocServices referdocServices;
	@Autowired
	private FileUploadServices fileUploadServices;
	
	private SHA1_Encode encrypt = new SHA1_Encode();
	private User userinfo = new User();
	private User tokenUserInfo = new User();
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
	// ## (2) 'addresscode'
	// ## (3) 'address'
	// ## (4) 'accessgroup'
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

	public int register(String token, String account, String password,
			Integer usertype, String name, String picurl, BigInteger mobile,
			Integer gender, String email, String language, String skill,
			String description, String accessgroup, Integer addresscode,
			String address, Double grade, Integer driver, String pushtoken) {
		System.out.println("Here is UserServices.register method...");

		int registermode = 0; // 记录register的模式: 1=user,2=admin,3=guest
		String defaultaccess = "";
		
		// ***** 检测 usertype 是否正确 *****
		if (usertype == null || (usertype != 1 && usertype != 2 && usertype != 3 && usertype != 4 && usertype != 5)) {
			return ResultCode.USERTYPE_ERROR;
		}
		
		if(usertype == 5) { // usertype=5时为临时客户号，检测注册信息是否完整
			// ***** 添加手机号mobile 判断格式 *****
			if (mobile != null) {
				if (Tools.isNumeric(mobile.toString())
						&& (mobile.toString().length() >= 8)) {
					int checkMobileResult = checkUserName(mobile.toString()); // 调用checkUserName方法检测手机号是否唯一并得到返回码
					User tempuser = new User();
					if (checkMobileResult == ResultCode.USERNAME_NOTEXIST) { // 检测用户名是否存在
						tempuser.setUser_type(usertype);
						tempuser.setUser_mobile(mobile);
						tempuser.setUser_account("guest_" + mobile.toString());
						tempuser.setUser_password(encrypt.getDigestOfString(mobile.toString().getBytes()));
						tempuser.setUser_access_group("guest_default");
						tempuser.setUser_status(1);
						// ***** 添加用户推送信息 *****
						if(driver != null && (driver.equals(1) || driver.equals(2))) {
							if (!Tools.isEmpty(pushtoken)) {
								tempuser.setUser_driver(driver);
								tempuser.setUser_push_token(pushtoken);
							}
						}
						userMapper.addUserByAccount(tempuser);
						tempuser = userMapper.getUserByMobile(mobile);
						// ***** 更新用户token *****
						RandomCode randomCode = new RandomCode();
						String newtoken = randomCode.getToken();// 正确则创建新token，并更新数据库
						while (null != userMapper.getUserByToken(newtoken)) {// 确保token唯一
							newtoken = randomCode.getToken();
						}
						tempuser.setUser_token(newtoken);
						userMapper.updateToken(tempuser);
						
						tempuser.setUser_login_time(new Timestamp(new Date().getTime()));
						userMapper.updateLoginTime(tempuser);// 插入 login 时间

						userinfo = tempuser;// 返回更新的user对象给userinfo
						
						return ResultCode.SUCCESS;
					} else {
						// ***** 检测 account & password 是否符合格式 *****
						if (!Tools.isEmpty(account, password)) {
							if (account.length() < 6) {
								return ResultCode.USERACCOUNT_ERROR;
							} else if (password.length() < 6) {
								return ResultCode.USERPASSWORD_ERROR;
							}
						} else {
							return ResultCode.USERNAME_EXIST;
						}
					}
				} else {
					return ResultCode.USERMOBILE_ERROR;
				}
			} else {
				return ResultCode.REQUIREINFO_NOTENOUGH;
			}
		}
		
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

		if (usertype == 1) { // usertype=1时为用户号，检测注册信息是否完整
			registermode = checkRegisterMode(token); // 判断register的模式:1=user,2=admin
			defaultaccess = "user_default"; // 分配user_default权限
		}

		if (usertype == 2) { // usertype=2时为师傅号，检测注册信息是否完整
// 苹果审核不通过。。师傅注册模块资料太全
//			if (Tools.isEmpty(name, email, language)
//					|| mobile == null || gender == null) {
//				return ResultCode.REGISTERINFO_NOTENOUGH;
//			}
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
				switch (usertype) {
				case 3:
					defaultaccess = "admin_default"; // 当权限组参数为空时，分配admin_default权限
					break;
				case 4:
					defaultaccess = "superadmin_default"; // 当权限组参数为空时，分配admin_default权限
					break;
				default:
					return ResultCode.USERTYPE_ERROR;
				}
			} else {
				return ResultCode.ACCESS_REJECT; // register的模式为用户模式模式时，拒绝注册。
			}
			
			
		}
		
		System.out.println("Registermode: " + registermode);
		
		int registerResult;// 新建返回值
		int checkUserNameResult = checkUserName(account);// 调用checkUserName方法并得到返回码

		if (checkUserNameResult == ResultCode.USERNAME_NOTEXIST) {// 检测用户名是否存在
			
			User user = new User(); // 实例化用户对象
			
			// ***** 添加手机号mobile 判断格式 *****
			if (mobile != null) {
				if (Tools.isNumeric(mobile.toString())
						&& (mobile.toString().length() >= 8)) {
					int checkMobileResult = checkUserName(mobile.toString());// 调用checkUserName方法检测手机号是否唯一并得到返回码
					if (checkMobileResult == ResultCode.USERNAME_NOTEXIST) {// 检测用户名是否存在
						user.setUser_mobile(mobile);
					} else {
						if (usertype == 5) {
							user = userinfo;
							user.setUser_mobile(mobile);
							registermode = 1;
							defaultaccess = "user_default"; // 分配user_default权限
						} else {
							return checkMobileResult;
						}
					}
				} else {
					return ResultCode.USERMOBILE_ERROR;
				}
			}
			
			// ***** 添加account *****
			user.setUser_account(account);
			
			// ***** 添加加密的password *****
			user.setUser_password(encrypt.getDigestOfString(password.getBytes()));
			
			// ***** 添加 usertype *****
			user.setUser_type(usertype);
			user.setUser_status(1);
//			if (usertype == 2 && registermode == 1) {// 当用户在注册师傅账号时，状态转为待审核
//				user.setUser_status(3); // 1=active, 2=pause, 3=pending,
//										// 4=locked
//			} else {
//				user.setUser_status(1);
//			}
			
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
			
			// ***** 检测 addresscode & address *****
			if (addresscode != null && addresscode != 0 && (addresscode + "").length() != 6)// 判断地址码是否六位数
				return ResultCode.ADDRESSCODE_ERROR;

			if (addresscode != null && addresscode != 0 && Tools.isEmpty(address))
				return ResultCode.ADDRESS_NOTMATCH;

			if ((addresscode != null && !Tools.isEmpty(address))) {

				String addressname = addressServices.checkAddresscodeName(addresscode);// 调用checkAddresscodeName方法，获取地址名
				if (addressname == null)// 如果addressname 返回null, 表明addresscode错误
					return ResultCode.ADDRESSCODE_ERROR;
				
				// 清除开头为;的符号
				while(address.startsWith(";")){
					address = address.substring(1, address.length());
				}
				// 清除结尾为;的符号
				while(address.endsWith(";")){
					address = address.substring(0, address.length() - 1);
				}
				
				if (address.replace(";", "").matches(addressname + "(.*)")) {// 判断地址名是否一致
					user.setUser_address(address);// 放入新地址名
					user.setUser_address_code(addresscode);// 放入新地址码
				} else
					return ResultCode.ADDRESS_NOTMATCH;
			}

			// ***** 添加姓名name *****
			if (!Tools.isEmpty(name)) {
				user.setUser_name(name);
			}

			// ***** 添加性别gender *****
			if (gender != null) {
				if ((gender.equals(1) || gender.equals(2))) { // 1=Male,2=Female
					user.setUser_gender(gender);
				} else {
					return ResultCode.USERGENDER_ERROR;
				}
			}
			
			// ***** 添加邮箱email *****
			if (!Tools.isEmpty(email)) {
				if (email.contains("@")) {
					int checkEmailResult = checkUserName(email);// 调用checkUserName方法检测邮箱地址是否唯一并得到返回码
					if (checkEmailResult == ResultCode.USERNAME_NOTEXIST) {// 检测用户名是否存在
						user.setUser_email(email);
					} else {
						return checkEmailResult;
					}
				} else {
					return ResultCode.USEREMAIL_ERROR;
				}
			}

			// ***** 添加语言language *****
			if (!Tools.isEmpty(language)) {
				// 清除开头为;的符号
				while(language.startsWith(";")){
					language = language.substring(1, language.length());
				}
				// 清除结尾为;的符号
				while(language.endsWith(";")){
					language = language.substring(0, language.length() - 1);
				}
				user.setUser_language(language);
			}
			
			// ***** 添加技能skill *****
			if (registermode == 2) { // 当管理员注册师傅时才能添加技能
				if (!Tools.isEmpty(skill)) {
					// 清除开头为;的符号
					while(skill.startsWith(";")){
						skill = skill.substring(1, skill.length());
					}
					// 清除结尾为;的符号
					while(skill.endsWith(";")){
						skill = skill.substring(0, skill.length() - 1);
					}
					user.setUser_skill(skill);
					
					String skillName = referdocServices.getReferlist(skill);
					// 清除开头为;的符号
					while(skillName.startsWith(";")){
						skillName = skillName.substring(1, skillName.length());
					}
					// 清除结尾为;的符号
					while(skillName.endsWith(";")){
						skillName = skillName.substring(0, skillName.length() - 1);
					}
					user.setUser_skill_name(skillName);
				}
			}

			// ***** 添加描述description *****
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
			
			// ***** 添加用户分值 *****
			if (registermode == 2) { // 判断register的模式:1=user,2=admin
				if(grade == null) {
					user.setUser_grade((double) 0);
				} else {
					user.setUser_grade(grade);
				}
			} else {
				user.setUser_grade((double) 0);
			}
			
			// ***** 添加用户推送信息 *****
			if(driver != null && (driver.equals(1) || driver.equals(2))) {
				if (!Tools.isEmpty(pushtoken)) {
					user.setUser_driver(driver);
					user.setUser_push_token(pushtoken.replaceAll(" ", ""));
				}
			}
			if (usertype == 5) {
				user.setUser_type(1);
				user.setUser_access_group("user_default");
				userMapper.updateUser(user);
			} else {
				userMapper.addUserByAccount(user);// 把用户信息插入数据表
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

	public int login(String username, String password, Integer driver, String pushtoken) {
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
					// ***** 更新用户token *****
					user.setUser_token(token);
					userMapper.updateToken(user);// 插入 token 值
					
					// ***** 添加用户推送信息 *****
					if(driver != null && (driver.equals(1) || driver.equals(2))) {
						if (!Tools.isEmpty(pushtoken) && !pushtoken.equals(user.getUser_push_token())) {
							user.setUser_driver(driver);
							user.setUser_push_token(pushtoken.replaceAll(" ", ""));
							userMapper.updatePushToken(user);
						}
					}
					
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
			Integer status, String token, Integer userid, Double grade) {
		System.out.println("Here is UserServices.update method...");

		int updatemode = 0; // 记录update的模式: 1=user,2=admin
		int changecount = 0; // 记录更改次数
		User user = new User();

		// ***** 检测 token & userid *****
		if (Tools.isEmpty(token) && userid == null)// 检测必要参数是否为空、null
			return ResultCode.REQUIREINFO_NOTENOUGH;

		System.out.println("Operation token: " + token);
		System.out.println("Updating userid: " + userid);
		logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]Operation token: " + token);
		logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]Updating userid: " + userid);
		
		if (!Tools.isEmpty(token)) {
			int checkResult;
			if (userid != null) {
				checkResult = checkUpdateAccess(token, userid); // 检测token用户是否有权限
				if (checkResult == ResultCode.SUCCESS) { // 有权限时，判断updatemode
					user = userinfo; // 把用戶信息封裝給待update的user對象
					
					if (tokenUserInfo.getUser_id().equals(userid)) {// 检测token的用户id与传进来的userid是否相同
						
						updatemode = 1; // 用户update模式
						if (userinfo.getUser_type().equals(3) || userinfo.getUser_type().equals(4)){
							updatemode = 2; // 管理员update模式
						}
					} else {
						updatemode = 2; // 管理员update模式
					}
				} else
					return checkResult; // 返回token状态
			} else {
				checkResult = checkUserToken(token);// 檢測token狀態，並取出相應的用戶信息
				if (checkResult == ResultCode.SUCCESS) {
					user = userinfo; // 把用戶信息封裝給待update的user對象
					updatemode = 1; // 用户update模式
					if (userinfo.getUser_type().equals(3) || userinfo.getUser_type().equals(4)){
						updatemode = 2; // 管理员update模式
					}
				} else
					return checkResult; // 返回token状态
			}
		} else {
			return ResultCode.REQUIREINFO_NOTENOUGH;
		}
		
		System.out.println("UpdateMode: " + updatemode);
		logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]UpdateMode: " + updatemode);
		
		// ***** 检测 addresscode & address *****
		if (addresscode != null && addresscode != 0 && (addresscode + "").length() != 6)// 判断地址码是否六位数
			return ResultCode.ADDRESSCODE_ERROR;

		if (addresscode != null && addresscode != 0 && Tools.isEmpty(address))
			return ResultCode.ADDRESS_NOTMATCH;

		if (addresscode == null && !Tools.isEmpty(address)) {// 判断更改的地址名是否与原来的addresscode对应
			addresscode = user.getUser_address_code();// 读取数据库的addresscode

			if (addresscode == null)
				return ResultCode.ADDRESSCODE_ERROR;

			// 清除开头为;的符号
			while(address.startsWith(";")){
				address = address.substring(1, address.length());
			}
			// 清除结尾为;的符号
			while(address.endsWith(";")){
				address = address.substring(0, address.length() - 1);
			}
			
			String addressname = addressServices.checkAddresscodeName(addresscode);// 调用checkAddresscodeName方法，获取地址名
			if (address.replace(";", "").matches(addressname + "(.*)")) {// 判断地址名是否一致
				user.setUser_address(address);// 放入新地址名
				changecount++;
				System.out.println("address changed!");
				logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]address changed!");
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
				logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]address&addresscode changed!");
			} else
				return ResultCode.ADDRESS_NOTMATCH;
		}

		// ***** 检测account *****
		if (!Tools.isEmpty(account) && !account.equals(user.getUser_account())) {
			if (account.length() >= 6){
				int checkAccountResult = checkUserName(account);// 调用checkUserName方法检测账号是否唯一并得到返回码
				if (checkAccountResult == ResultCode.USERNAME_NOTEXIST) {// 检测用户名是否存在
					user.setUser_account(account);
					changecount++;
					System.out.println("account changed!");
					logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]account changed!");
				} else {
					return checkAccountResult;
				}
			} else {
				return ResultCode.USERACCOUNT_ERROR;
			}
		}
		// ***** 检测password *****
		if (!Tools.isEmpty(password) && !encrypt.getDigestOfString(password.getBytes()).equals(user.getUser_password())) {
			if (password.length() >= 6) {
				user.setUser_password(encrypt.getDigestOfString(password.getBytes()));
				changecount++;
				System.out.println("password changed!");
				logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]password changed!");
			} else {
				return ResultCode.USERPASSWORD_ERROR;
			}
		}
		// ***** 检测name *****
		if (!Tools.isEmpty(name) && !name.equals(user.getUser_name())) {
			user.setUser_name(name);
			changecount++;
			System.out.println("name changed!");
			logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]name changed!");
		}
		// ***** 检测picurl *****
		if (Tools.isEmpty(picurl) && (Tools.isEmpty(user.getUser_pic_url()))) {
			if (user.getUser_type().equals(3)) {
				picurl = "/BBCall/UserPhoto/Admin_photo.png";
			} else if (user.getUser_type().equals(4)) {
				picurl = "/BBCall/UserPhoto/SuperAdmin_photo.png";
			} else {
				if (gender != null) {
					switch (user.getUser_gender()) {
					case 1:
						picurl = "/BBCall/UserPhoto/Male_photo.png";
						break;
					case 2:
						picurl = "/BBCall/UserPhoto/Female_photo.png";
						break;
					}
				} else {
					picurl = "/BBCall/UserPhoto/Default_photo.png";
				}

			}
		}
		if (!Tools.isEmpty(picurl) && !picurl.equals(user.getUser_pic_url())) {
			if (!user.getUser_pic_url().contains("Admin")
					&& !user.getUser_pic_url().contains("Male")
					&& !user.getUser_pic_url().contains("Female")
					&& !user.getUser_pic_url().contains("Default")) { // 不删除内置头像
				fileUploadServices.deleteFile(user.getUser_pic_url());// 先删除之前的头像文件
			}
			user.setUser_pic_url(picurl);
			changecount++;
			System.out.println("picurl changed!");
			logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]picurl changed!");
//			if (updatemode == 1 && tokenUserInfo.getUser_type().equals(2)) {// 用户模式时，user状态转为pending 待审核
//				user.setUser_status(3);
//				System.out.println("(picurl)status changed due to UpdateMode is 1 (user)!");
//				logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "](picurl)status changed due to UpdateMode is 1 (user)!");
//			}
		}
		// ***** 检测mobile *****
		if (mobile != null && !mobile.equals(user.getUser_mobile())) {
			if (Tools.isNumeric(mobile.toString())
					&& (mobile.toString().length() >= 8)) {
				int checkMobileResult = checkUserName(mobile.toString());// 调用checkUserName方法检测手机号是否唯一并得到返回码
				if (checkMobileResult == ResultCode.USERNAME_NOTEXIST) {// 检测用户名是否存在
					user.setUser_mobile(mobile);
					changecount++;
					System.out.println("mobile changed!");
					logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]mobile changed!");
				} else {
					return checkMobileResult;
				}
			} else {
				return ResultCode.USERMOBILE_ERROR;
			}
		}
		// ***** 检测gender *****
		if (gender != null && !gender.equals(user.getUser_gender())) {
			if ((gender.equals(1) || gender.equals(2))) { // 1=Male,2=Female
				user.setUser_gender(gender);
				changecount++;
				System.out.println("gender changed!");
				logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]gender changed!");
			} else {
				return ResultCode.USERGENDER_ERROR;
			}
		}
		// ***** 检测email *****
		if (!Tools.isEmpty(email) && !email.equals(user.getUser_email())) {
			if (email.contains("@")) {
				int checkEmailResult = checkUserName(email);// 调用checkUserName方法检测邮箱地址是否唯一并得到返回码
				if (checkEmailResult == ResultCode.USERNAME_NOTEXIST) {// 检测用户名是否存在
					user.setUser_email(email);
					changecount++;
					System.out.println("email changed!");
					logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]email changed!");
				} else {
					return checkEmailResult;
				}
			} else {
				return ResultCode.USEREMAIL_ERROR;
			}
		}
		// ***** 检测language *****
		if (!Tools.isEmpty(language) && !language.equals(user.getUser_language())) {
			// 清除开头为;的符号
			while(language.startsWith(";")){
				language = language.substring(1, language.length());
			}
			// 清除结尾为;的符号
			while(language.endsWith(";")){
				language = language.substring(0, language.length() - 1);
			}
			user.setUser_language(language);
			changecount++;
			System.out.println("language changed!");
			logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]language changed!");
		}
		// ***** 检测description *****
		if (!Tools.isEmpty(description)
				&& !description.equals(user.getUser_description())) {
			user.setUser_description(description);
			changecount++;
			System.out.println("description changed!");
			logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]description changed!");
		}
		// ***** 检测accessgroup *****
		if (updatemode == 2 && !Tools.isEmpty(accessgroup)
				&& !accessgroup.equals(user.getUser_access_group())) { // admin模式时才能修改accessgroup
			user.setUser_access_group(accessgroup);
			changecount++;
			System.out.println("accessgroup changed!");
			logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]accessgroup changed!");
		}
		// ***** 检测status *****
		if (updatemode == 2 && status != null
				&& !user.getUser_status().equals(status)) {// admin模式时，判断status是否有变化
			user.setUser_status(status);
			changecount++;
			System.out.println("status changed!");
			logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]status changed!");
		}
		if (updatemode == 1 && status != null
				&& !user.getUser_status().equals(status)) {
			return ResultCode.ACCESS_REJECT;
		}
		// ***** 检测grade *****
		if (updatemode == 2 && grade != null
				&& !user.getUser_grade().equals(grade)) {// admin模式时，判断grade是否需要修改
			user.setUser_grade(grade);
			changecount++;
			System.out.println("grade changed!");
			logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]grade changed!");
		}
		// ***** 检测skill *****
		if (updatemode == 2) {
			if (!Tools.isEmpty(skill) && !skill.equals(user.getUser_skill())) {
				// 清除开头为;的符号
				while(skill.startsWith(";")){
					skill = skill.substring(1, skill.length());
				}
				// 清除结尾为;的符号
				while(skill.endsWith(";")){
					skill = skill.substring(0, skill.length() - 1);
				}
				user.setUser_skill(skill);
				
				String skillName = referdocServices.getReferlist(skill);
				// 清除开头为;的符号
				while(skillName.startsWith(";")){
					skillName = skillName.substring(1, skillName.length());
				}
				// 清除结尾为;的符号
				while(skillName.endsWith(";")){
					skillName = skillName.substring(0, skillName.length() - 1);
				}
				user.setUser_skill_name(skillName);
				changecount++;
				System.out.println("skill changed!");
				logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]skill changed!");
//				if (updatemode == 1 && tokenUserInfo.getUser_type().equals(2)) {// 用户模式时，user状态转为pending 待审核
//					user.setUser_status(3);
//					System.out.println("(skill)status changed due to UpdateMode is 1 (user)!");
//					logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "](skill)status changed due to UpdateMode is 1 (user)!");
//				}
			}
		}
		// ***** 检测usertype *****
		if (usertype != null && !user.getUser_type().equals(usertype)) {
			if (updatemode == 1 && tokenUserInfo.getUser_type().equals(2)) {// 用户模式时，user状态转为pending 待审核
				if (usertype.equals(3) || usertype.equals(4)) { // 检测到要修改成管理员模式
					return ResultCode.USERTYPE_ERROR;
				}
				user.setUser_status(3);
				System.out.println("(usertype)status changed due to UpdateMode is 1 (user)!");
				logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "](usertype)status changed due to UpdateMode is 1 (user)!");
			}
			user.setUser_type(usertype);
			changecount++;
			System.out.println("usertype changed!");
			logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]usertype changed!");
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
		
		userinfo = user;// 返回更新后的user对象给userinfo
//		switch (updatemode) {
//		case 1:
//			userinfo = user;// 返回更新后的user对象给userinfo
//			break;
//		case 2:
//			if (user.getUser_id().equals(userinfo.getUser_id())){
//				userinfo = user;// 返回更新的user对象给userinfo
//			} else {
//				updateduserinfo = user;// 返回更新后的user对象给updateduserinfo
//			}
//			break;
//		}
		return ResultCode.SUCCESS;

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

		User updateUser = new User();
		updateUser = userMapper.getUserById(userid);// 根据userid得出用户信息

		if (updateUser == null) {
			return ResultCode.USERID_ERROR; // 错误的userid.
		}

		Integer updateUserType = updateUser.getUser_type(); // 封装待更新的用户type

		int tokenresult = checkUserToken(token); // 调用userServices.checkUserTokenes
		if (tokenresult == ResultCode.SUCCESS) {
			Integer tokenUserType = userinfo.getUser_type();
			switch (updateUserType) {
			case 4:// superAdmin 的用户信息
				if (!userinfo.getUser_id().equals(userid)) { // 检测token的userid
					// 与待update的userid是否相同
					return ResultCode.ACCESS_REJECT;
				}
				break;
			case 3:// admin 的用户信息
				if (!userinfo.getUser_id().equals(userid)) { // 检测token的userid
					// 与待update的userid是否相同
					if (!tokenUserType.equals(4)) {
						return ResultCode.ACCESS_REJECT;
					}
				}
				break;
			case 2:// master 的用户信息
				if (!userinfo.getUser_id().equals(userid)) { // 检测token的userid
																// 与待update的userid是否相同
					if (!tokenUserType.equals(3) && !tokenUserType.equals(4)) { // 如果不一致，则需要admin以上的用户权限
						return ResultCode.ACCESS_REJECT;
					}
				}
				break;
			case 1:// user 的用户信息
				if (!userinfo.getUser_id().equals(userid)) { // 检测token的userid
																// 与待update的userid是否相同
					if (!tokenUserType.equals(3) && !tokenUserType.equals(4)) { // 如果不一致，则需要admin以上的用户权限
						return ResultCode.ACCESS_REJECT;
					}
				}
				break;

			default:
				return ResultCode.UNKNOWN_ERROR;
			}
			tokenUserInfo = userinfo; // 返回待token的用户对象到tokenUserInfo
			userinfo = updateUser; // 返回待update的用户对象到userinfo
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
	public int deleteUserById(String token, Integer userid) {
		System.out.println("Here is UserServices.deleteUserById method...");
		if (userid == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		int result = checkUpdateAccess(token, userid);
		if (result == ResultCode.SUCCESS) {
			userMapper.deleteUserById(userid);
			return ResultCode.SUCCESS;
		} else {
			return result;
		}
	}

	/**
	 * updateUserGrade 更新用户评分
	 * @param userid
	 * @param usergrade
	 * @return
	 */
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

	/**
	 * updateUserPassword 更新用户密码
	 * @param userid
	 * @param password
	 * @return
	 */
	public int updateUserPassword(Integer userid, String password) {
		System.out.println("Here is UserServices.updateUserPassword method...");
		if (userid == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		User tempUser = userMapper.getUserById(userid);
		if (tempUser != null) {
			tempUser.setUser_password(encrypt.getDigestOfString(password.getBytes()));
			userMapper.updateUser(tempUser);
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.USERID_ERROR;
		}
	}
	
	/**
	 * updateUserPushToken
	 * @param userid
	 * @param pushtoken
	 * @return
	 */
	public int updateUserPushToken(Integer userid, String pushtoken) {
		System.out.println("Here is UserServices.updateUserPushToken method...");
		if (userid == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		User tempUser = userMapper.getUserById(userid);
		if (tempUser != null) {
			if (tempUser.getUser_driver() != null && (tempUser.getUser_driver().equals(1) || tempUser.getUser_driver().equals(2))) {
				tempUser.setUser_push_token(pushtoken);
				userMapper.updatePushToken(tempUser);
				return ResultCode.SUCCESS;
			} else {
				return ResultCode.USERDRIVER_NULL;
			}
		} else {
			return ResultCode.USERID_ERROR;
		}
	}
	
	/**
	 * updateToken
	 * @param userid
	 * @param token
	 * @return
	 */
	public int updateToken(Integer userid, String token){
		System.out.println("Here is UserServices.updateToken method...");
		if (userid == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		User tempUser = userMapper.getUserById(userid);
		if (tempUser != null) {
			tempUser.setUser_token(token);
			userMapper.updateToken(tempUser);
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.USERID_ERROR;
		}
	}
	
	/**
	 * logout
	 * @param userid
	 * @param pushtoken
	 * @param token
	 * @return
	 */
	public int logout(Integer userid, String pushtoken, String token) {
		int result1 = updateToken(userid, token);
		int result2 = updateUserPushToken(userid, pushtoken);

		if (result1 == ResultCode.SUCCESS && result2 == ResultCode.SUCCESS) {
			return ResultCode.SUCCESS;
		}

		if (result1 > result2) {
			return result1;
		} else {
			return result2;
		}
	}
	
	public int checkUserListWhereOrderBy(String order_col, String order_value, String where_col, String where_value, String where_col2, String where_value2, Integer pagenum) {
		System.out.println("Here is UserServices.checkUserListWhereOrderBy method...");
		int pageSize = 0;
		//当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;
		
		if (pagenum >= 0) {
			pageSize = 20; // 默认显示内容条数
		}
		
		// 防止sql注入攻击
		if (!Tools.isEmpty(where_col) && (!where_col.contains("user_") || where_col.length() > 17)) {
			return ResultCode.REQUIREINFO_ERROR;
		}
		if (!Tools.isEmpty(where_col2) && (!where_col2.contains("user_") || where_col2.length() > 17)) {
			return ResultCode.REQUIREINFO_ERROR;
		}
		if (!Tools.isEmpty(order_col) && (!order_col.contains("user_") || order_col.length() > 22)) {
			return ResultCode.REQUIREINFO_ERROR;
		}
		
	    //PageHelper.startPage(PageNum, PageSize) 
		//获取第1页，10条内容，当PageSize=0时会查询出全部的结果
	    PageHelper.startPage(pagenum, pageSize);
	    
	    //紧跟着的第一个select方法会被分页
		List<User> userlist = userMapper.listUserWhereOrderBy(where_col, where_value, where_col2, where_value2, order_col, order_value);
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

	public int checkUserList(String col_name, String specify_value, String search_value, Integer pagenum) {
		System.out.println("Here is UserServices.checkUserList method...");
		
		//当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;
		
		// 防止sql注入攻击
		if (!Tools.isEmpty(col_name) && (!col_name.contains("user_") || col_name.length() > 22)) {
			return ResultCode.REQUIREINFO_ERROR;
		}
		
	    //PageHelper.startPage(PageNum, PageSize) 
		//获取第1页，10条内容，当PageSize=0时会查询出全部的结果
	    PageHelper.startPage(pagenum, 20);

	    //紧跟着的第一个select方法会被分页
		List<User> userlist = userMapper.listUserOrderBy(col_name, specify_value, search_value);

		
		if (userlist.size() > 0) {
			this.userlist = userlist;
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.USERLIST_NULL;
		}
	}
	
	/**
	 * getPushTokenByDriver 按照driver获取push token
	 * @param driver
	 * @return
	 */
	public List<String> getPushTokenByDriver(Integer driver, Integer usertype) {
		System.out.println("Here userServices.getPushTokenByDriver");
		List<String> userlist = new ArrayList<String>();
		if (driver == null || (driver.equals(1) && driver.equals(2)))
			return null;

		int result;
		if (usertype == null) {
			result = checkUserListWhereOrderBy(null, null, "user_driver", driver.toString(), null, null, -1);
		} else {
			result = checkUserListWhereOrderBy(null, null, "user_driver", driver.toString(), "user_type", usertype.toString(), -1);
		}
		
		if (result == ResultCode.SUCCESS) {
			for (int i = 0; i < this.userlist.size(); i++) {
				if (!Tools.isEmpty(this.userlist.get(i).getUser_push_token())) {
					userlist.add(this.userlist.get(i).getUser_push_token());
				}
			}
		}
		return userlist;
	}
	
	/**
	 * getWashMasterList 獲取有洗衣技能的师傅
	 * @return
	 */
	public List<User> getWashMasterList() {
		List<User> tempuserlist = userMapper.findAll();
		List<User> returnuserlist = new ArrayList<User>();
		for (int i = 0; i < tempuserlist.size(); i++) {
			if (tempuserlist.get(i).getUser_type().equals(2) // usertype=2 师傅
					&& !Tools.isEmpty(tempuserlist.get(i).getUser_skill_name()) // 判断技能名字不为空
					&& tempuserlist.get(i).getUser_skill_name().contains("洗衣")) { // 判断是否含“洗衣”关键字
				returnuserlist.add(tempuserlist.get(i));
			}
		}
		return returnuserlist;
	}

	/**
	 * getter & setter
	 * @return
	 */
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
