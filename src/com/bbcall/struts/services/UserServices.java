package com.bbcall.struts.services;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.RandomCode;
import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.dao.AddressListMapper;
import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.table.AddressList;
import com.bbcall.mybatis.table.User;

@Service("userServices")
public class UserServices {

	// @Autowired
	// private UserSkillMapper userSkillMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AddressListMapper addressListMapper;

	private User userinfo = new User();
	private List<AddressList> addresslist;
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

	public int register(String account, String password, Integer usertype,
			String name, String picurl, BigInteger mobile, Integer gender,
			String email, String language, String skill, String description) {
		System.out.println("Here is UserServices.register method...");

		if (usertype != 1 && usertype != 2 && usertype != 3) {// 检测usertype
			return ResultCode.REGISTERINFO_TYPEERROR;
		}

		if (usertype == 2) {// usertype=2时为师傅号，检测注册信息是否完整
			if (isEmpty(account, password, name, picurl, email, language, skill)
					|| mobile == null || gender == null) {
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
				user.setUser_status(3); // 1=active, 2=pause, 3=pending,
										// 4=locked
			} else {
				user.setUser_status(1);
			}
			user.setUser_name(name);
			user.setUser_pic_url(picurl);
			user.setUser_mobile(mobile);
			user.setUser_gender(gender);
			user.setUser_email(email);
			user.setUser_language(language);
			user.setUser_skill(skill);

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

			userinfo = user;// 返回更新的user对象给userinfo
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

		if (isEmpty(username, password))// 检测参数是否为空、null
			return ResultCode.REQUIREINFO_NOTENOUGH;

		int checkUserNameResult = checkUserName(username);// 调用checkUserName方法并得到返回码

		// 判断用户名的类型：
		if (checkUserNameResult != ResultCode.USERNAME_NOTEXIST) {
			User user = (User) userinfo; // 引用user对象
			int checkUserStatusResult = checkUserStatus(user.getUser_status());
			if (checkUserStatusResult == ResultCode.USERSTATUS_ACTIVE) { // 验证用户状态
				if (password.equals(user.getUser_password())) {// 验证密码是否正确
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
	// ## (1) Update by user: 'token'
	// ## (2) Update by admin: 'userid'
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
		if (isEmpty(token) && userid == null)// 检测必要参数是否为空、null
			return ResultCode.REQUIREINFO_NOTENOUGH;

		if ((!isEmpty(token)) && userid == null) {// 用户update模式
			int checkResult = checkToken(token);
			if (checkResult == ResultCode.SUCCESS) {
				user = userinfo;
				updatemode = 1;
			} else
				return checkResult;
		} else if (isEmpty(token) && userid != null) {// 管理员后台update模式
			user = userMapper.getUserById(userid);// 根据userID读取用户数据
			updatemode = 2;
			if (null == user)
				return ResultCode.USERID_ERROR;
		} else
			return ResultCode.UNKNOWN_ERROR;

		// ***** 检测 addresscode & address *****
		if (addresscode != null && (addresscode + "").length() != 6)// 判断地址码是否六位数
			return ResultCode.ADDRESSCODE_ERROR;

		if (addresscode != null && isEmpty(address))
			return ResultCode.ADDRESS_NOTMATCH;

		if (addresscode == null && !isEmpty(address)) {// 判断更改的地址名是否与原来的addresscode对应
			addresscode = user.getUser_address_code();// 读取数据库的addresscode

			if (addresscode == null)
				return ResultCode.ADDRESSCODE_ERROR;

			String addressname = checkAddresscodeName(addresscode);// 调用checkAddresscodeName方法，获取地址名
			if (address.replace(";", "").matches(addressname + "(.*)")) {// 判断地址名是否一致
				user.setUser_address(address);// 放入新地址名
				changecount++;
				System.out.println("address changed!");
			} else
				return ResultCode.ADDRESS_NOTMATCH;
		}

		if ((addresscode != null && !isEmpty(address))
				&& (!user.getUser_address_code().equals(addresscode) || !user
						.getUser_address().equals(address))) {

			String addressname = checkAddresscodeName(addresscode);// 调用checkAddresscodeName方法，获取地址名
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
		if (!isEmpty(account) && !user.getUser_account().equals(account)) {
			user.setUser_account(account);
			changecount++;
			System.out.println("account changed!");
		}
		if (!isEmpty(password) && !user.getUser_password().equals(password)) {
			user.setUser_password(password);
			changecount++;
			System.out.println("password changed!");
		}
		if (!isEmpty(name) && !user.getUser_name().equals(name)) {
			user.setUser_name(name);
			changecount++;
			System.out.println("name changed!");
		}
		if (!isEmpty(picurl) && !user.getUser_pic_url().equals(picurl)) {
			user.setUser_pic_url(picurl);
			changecount++;
			System.out.println("picurl changed!");
		}
		if (mobile != null && !user.getUser_mobile().equals(mobile)) {
			user.setUser_mobile(mobile);
			changecount++;
			System.out.println("mobile changed!");
		}
		if (gender != null && !user.getUser_gender().equals(gender)) {
			user.setUser_gender(gender);
			changecount++;
			System.out.println("gender changed!");
		}
		if (!isEmpty(email) && !user.getUser_email().equals(email)) {
			user.setUser_email(email);
			changecount++;
			System.out.println("email changed!");
		}
		if (!isEmpty(language) && !user.getUser_language().equals(language)) {
			user.setUser_language(language);
			changecount++;
			System.out.println("language changed!");
		}
		if (!isEmpty(description)
				&& !user.getUser_description().equals(description)) {
			user.setUser_description(description);
			changecount++;
			System.out.println("description changed!");
		}
		if (!isEmpty(accessgroup)
				&& !user.getUser_access_group().equals(accessgroup)) {
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
		if (!isEmpty(skill) && !user.getUser_skill().equals(skill)) {
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

		userinfo = user;// 返回更新的user对象给userinfo
		return ResultCode.SUCCESS;

	}

	// ###################
	// ## 根据addresscode读取省、市、区名
	// ###################

	public String checkAddresscodeName(Integer addresscode) {
		List<AddressList> addresslist = addressListMapper
				.getAddressByAreano(addresscode);// addressList 对象

		if (addresslist.size() == 0)
			return null;
		int arealevel = addresslist.get(0).getArealevel();// 读取对应的地址level

		String addressname = "";
		if (arealevel > 1) {
			for (int i = 1; i < arealevel; i++) {// 完整读取地址名（省、市、区）
				switch (i) {
				case 1:// 得到省级名字
					int tempcode1 = addresscode / 10000;
					addressname = addressListMapper
							.getAddressByAreano(tempcode1 * 10000).get(0)
							.getAreaname();
					break;
				case 2:// 得到市级名字
					addressname = addressname
							+ addressListMapper
									.getAddressByAreano(
											addresslist.get(0).getParentno())
									.get(0).getAreaname();
					break;
				}
			}
		}

		addressname = addressname + addresslist.get(0).getAreaname();// 得到原来addresscode的地址名
		return addressname;
	}

	// ###################
	// ## 读取Child省、市、区列表
	// ###################

	public int checkChildAdsList(Integer addresscode) {
		System.out.println("Here is UserServices.checkChildAdsList method...");

		List<AddressList> addresslist = addressListMapper
				.getAddressByParentno(addresscode);
		if (addresslist.size() > 0) {

			this.addresslist = addresslist;

			return ResultCode.SUCCESS;
		} else {
			return ResultCode.ADDRESS_NULL;
		}
	}

	// ###################
	// ## 读取省、市、区列表
	// ###################

	public int checkAdsList(Integer addresscode) {
		System.out.println("Here is UserServices.checkAdsList method...");

		List<AddressList> addresslist = addressListMapper
				.getAddressByAreano(addresscode);
		if (addresslist.size() > 0) {

			this.addresslist = addresslist;

			return ResultCode.SUCCESS;
		} else {
			return ResultCode.ADDRESS_NULL;
		}
	}

	// ###################
	// ## 拉取User表
	// ###################

	public int checkUserList(String token) {
		System.out.println("Here is UserServices.checkUserList method...");

		int checkTokenResult = checkToken(token);
		if (checkTokenResult == ResultCode.SUCCESS) {
			if (userinfo.getUser_type() != 3) {
				return ResultCode.ACCESS_REJECT;
			}
			List<User> userlist = userMapper.findAll();
			if (userlist.size() > 0) {
				this.userlist = userlist;
				return ResultCode.SUCCESS;
			} else {
				return ResultCode.USERLIST_NULL;
			}
		} else {
			return checkTokenResult;
		}
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
			int checkUserStatusResult = checkUserStatus(user.getUser_status());
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

		if (isEmpty(username))
			return ResultCode.REQUIREINFO_NOTENOUGH;

		// 判断用户名的类型：
		if (isNumeric(username)) { // 判断登录名是否为手机号码

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

	public int checkUserStatus(int status) {
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

	public List<AddressList> getAddresslist() {
		return addresslist;
	}

	public List<User> getUserlist() {
		return userlist;
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
