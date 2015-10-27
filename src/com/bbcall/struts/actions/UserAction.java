package com.bbcall.struts.actions;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.PageInfoToMap;
import com.bbcall.functions.RandomCode;
import com.bbcall.functions.ResultCode;
import com.bbcall.functions.ObjectToMap;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.table.User;
import com.bbcall.mybatis.table.UserSkill;
import com.bbcall.struts.services.MailServices;
import com.bbcall.struts.services.UserServices;
import com.bbcall.struts.services.UserSkillServices;
import com.opensymphony.xwork2.ActionSupport;


@Scope("prototype")
@Controller("userAction")
@SuppressWarnings("serial")
public class UserAction extends ActionSupport implements SessionAware{

	private static Logger logger = Logger.getLogger(UserAction.class);  
	
	@Autowired
	private UserServices userServices;
	@Autowired
	private UserSkillServices userSkillServices;
	@Autowired
	private MailServices mailservices;
	
	private Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	private ObjectToMap obj2map = new ObjectToMap();// 新建ObjectToMap对象
	private PageInfoToMap pageinfo2map = new PageInfoToMap();// 新建PageInfoToMap对象
	private RandomCode randomCode = new RandomCode();
	private Map<String, Object> session;
	
	private String username;
	private String password;
	private String account;
	private Integer usertype;
	private String name;
	private String picurl;
	private BigInteger mobile;
	private Integer gender;
	private String email;
	private String language;
	private String skill;
	private Integer skillid;
	private Integer skillcode;
	private Integer skillstatus;
	private String skillurl;
	private String skillname;
	private String token;
	private Integer driver;
	private String pushtoken;
	private String description;
	private Integer addresscode;
	private String address;
	private String accessgroup;
	private Integer status;
	private Integer userid;
	private Double grade;
	private String col_name;
	private String specify_value;
	private String search_value;
	private String order_col;
	private String order_value;
	private String where_col;
	private String where_value;
	private String where_col2;
	private String where_value2;
	
	// page related parameters
	private Integer pagenum; // 页面页数
	
	// others parameters
	private String updateResult;

	
	
//	private int test;
	
//	private HttpServletRequest request;
//    //实现接口中的方法
//    public void setServletRequest(HttpServletRequest request){
//     this.request = request;
//    }
	
//	@Override
//	public String execute() throws Exception {
//		return super.execute();
//	}
//
//	
//	public String test() throws Exception{
//		String a = shaEncode.getDigestOfString(password.getBytes());
//		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
//		dataMap.put("SHA", a);
//		return "json";
//	}
	
	

	// Login Action
	public String login() throws Exception {
		System.out.println("Here is UserAction.login");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.login(username, password, driver, pushtoken); // 调用userServices.login

		if (result == ResultCode.SUCCESS) {
//			Object userinfo = userServices.getUserinfo(); // 调用userInfo对象
//			dataMap.put("userinfo", userinfo); // 把userinfo对象放入dataMap
//			dataMap = obj2map.getValueMap(userinfo); //将对象转换成Map
			dataMap = obj2map.getValueMap(userServices.getUserinfo()); //将对象转换成Map
			session.putAll(dataMap);// 把用户信息放进session
			dataMap.putAll(Tools.JsonHeadMap(result, true));
//			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
//			dataMap.put("errmsg", ResultCode.getErrmsg(result));
//			dataMap.put("loginResult", true); // 放入loginResult
			System.out.println(dataMap);
			System.out.println(session);
//			System.out.println((dataMap.get("userinfo")));
			logger.info("userOpr:[Login][" + username + "]" + Tools.JsonHeadMap(result, true));  
			return "loginSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
//			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
//			dataMap.put("errmsg", ResultCode.getErrmsg(result));
//			dataMap.put("loginResult", false); // 放入loginResult
			System.out.println(dataMap);
			System.out.println("login Failed");
//			request.setAttribute("loginResult", false);
			logger.info("userOpr:[Login][" + username + "]" + Tools.JsonHeadMap(result, false));  
			return "loginFailed";
		}
	}
	public String loginJson() throws Exception {
		System.out.println("Here is UserAction.loginJson");
		login();
		return "json";
	}
	
	public String loginByAdmin() throws Exception {
		return login();
	}
	public String loginByAdminJson() throws Exception {
		login();
		return "json";
	}
	
	public String loginByUser() throws Exception {
		return login();
	}
	public String loginByUserJson() throws Exception {
		login();
		return "json";
	}
	
	public String loginByMaster() throws Exception {
		return login();
	}
	public String loginByMasterJson() throws Exception {
		login();
		return "json";
	}
	
	// Logout Action
	public String logout() throws Exception {
		System.out.println("Here is UserAction.logout");
		dataMap.clear();
		logger.info("userOpr:[Logout][" + session.get("user_id") + "]" + session.get("user_account") + " - Logout Success.");  
//		int result = userServices.updateUserPushToken(userid, "");
		int result;
		if (userid == null) {
			result = userServices.logout(Integer.parseInt(session.get("user_id").toString()), "", "");
		} else {
			result = userServices.logout(userid, "", "");
		}
		session.clear(); // 清空session
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
		}
		
		return "login";
	}
	public String logoutJson() throws Exception {
		logout();
		return "json";
	}
	
	public String homePage() throws Exception {
		return "homePage";
	}
	
	// Register Action
	public String register() throws Exception {
		System.out.println("Here is UserAction.register");

		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.register(userid, token, account, password, usertype, name, picurl, mobile, gender, email, language, skill, description, accessgroup, addresscode, address, grade, driver, pushtoken); // 调用userServices.register

		if (result == ResultCode.SUCCESS) {
//			Integer newuserid = userServices.getUserinfo().getUser_id();
			dataMap = obj2map.getValueMap(userServices.getUserinfo()); //将对象转换成Map
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			logger.info("userOpr:[Register][" + account + "]" + Tools.JsonHeadMap(result, true));
			return "registerSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			System.out.println("register Failed");
			logger.info("userOpr:[Register][" + account + "]" + Tools.JsonHeadMap(result, false));
			return "registerFailed";
		}
	}
	
	public String registerJson() throws Exception {
		System.out.println("Here is UserAction.registerJson");
		register();
		return "json";
	}
	
	// Update Action
	public String update() throws Exception{
		System.out.println("Here is UserAction.update");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		
//****************************************************************************
// ** if URL have problem, 如果中文字有乱码，可以用下面的方法转换
//		String tempAds = new String(address.getBytes("ISO-8859-1"), "UTF-8");
//****************************************************************************
		int result = userServices.update(account, password, usertype, name, picurl, mobile, gender, addresscode, address, email, language, skill, description, accessgroup, status, token, userid, grade); // 调用userServices.login

		if (result == ResultCode.SUCCESS) {
//			Object userinfo = userServices.getUserinfo(); // 调用userInfo对象
//			dataMap.put("userinfo", userinfo); // 把userinfo对象放入dataMap
//			dataMap = obj2map.getValueMap(userinfo); //将对象转换成Map
//			Object updateduserinfo = userServices.getUpdateduserinfo(); // 调用userInfo对象
//			dataMap.put("userinfo", userinfo); // 把userinfo对象放入dataMap
			dataMap = obj2map.getValueMap(userServices.getUserinfo()); //将对象转换成Map
			session.putAll(dataMap);// 把用户信息放进session
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]" + Tools.JsonHeadMap(result, true));
			return "updateSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			System.out.println("Update Failed");
			logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]" + Tools.JsonHeadMap(result, false));
			return "updateFailed";
		}
		
	}
	public String updateJson() throws Exception{
		System.out.println("Here is UserAction.updateJson");
		update();
		return "json";
	}
	
	public String updateUserById() throws Exception{
		System.out.println("Here is UserAction.update");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		
		int result = userServices.update(account, password, usertype, name, picurl, mobile, gender, addresscode, address, email, language, skill, description, accessgroup, status, token, userid, grade); // 调用userServices.login

		if (result == ResultCode.SUCCESS) {
			dataMap = obj2map.getValueMap(userServices.getUserinfo()); //将对象转换成Map
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]" + Tools.JsonHeadMap(result, true));
			return "updateUserSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			System.out.println("Update Failed");
			logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]" + Tools.JsonHeadMap(result, false));
			return "updateUserFailed";
		}
	}
	
	// UpdateStatus Action
	public String updateStatus() throws Exception{
		System.out.println("Here is UserAction.updateStatus");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		
		int result = userServices.update(account, password, usertype, name, picurl, mobile, gender, addresscode, address, email, language, skill, description, accessgroup, status, token, userid, grade); // 调用userServices.login
		
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			logger.info("userOpr:[UserUpdate_Status][Updated ID: " + userid + "]" + Tools.JsonHeadMap(result, true));
			return "updateStatusSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			System.out.println("updateStatus Failed");
			logger.info("userOpr:[UserUpdate_Status][Updated ID: " + userid + "]" + Tools.JsonHeadMap(result, false));
			return "updateStatusFailed";
		}
		
	}
	public String updateStatusJson() throws Exception{
		System.out.println("Here is UserAction.updateStatusJson");
		updateStatus();
		return "json";
	}

	/**
	 * getUserSkillByUserId 拿用户技能列表
	 * @return
	 * @throws Exception
	 */
	public String getUserSkillByUserId() throws Exception{
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userSkillServices.getUserSkillByUserId(userid);
		if (result == ResultCode.SUCCESS) {
			dataMap.put("skilllist", userSkillServices.getUserSkillList()); // 把addresslist对象放入dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			logger.info("userOpr:[getUserSkillByUserId]" + Tools.JsonHeadMap(result, true));
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			logger.info("userOpr:[getUserSkillByUserId]" + Tools.JsonHeadMap(result, false));
		}
		return SUCCESS;
	}
	public String getUserSkillByUserIdJson() throws Exception{
		getUserSkillByUserId();
		return "json";
	}
	
	/**
	 * getAllUserSkill 拿取全部用户技能列表
	 * @return
	 * @throws Exception
	 */
	public String getAllUserSkill() throws Exception{
		List<UserSkill> userSkill = userSkillServices.getAllUserSkill(pagenum);
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("skilllist", userSkill);
		dataMap.putAll(pageinfo2map.pageInfoMap(userSkill));// 把分页信息放进dataMap
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String getAllUserSkillJson() throws Exception{
		getAllUserSkill();
		return "json";
	}
	
	/**
	 * addUserSkill 添加技能
	 * @return
	 * @throws Exception
	 */
	public String addUserSkill() throws Exception{
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userSkillServices.addUserSkill(userid, skillcode, skillurl);
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			logger.info("userOpr:[addUserSkill]" + Tools.JsonHeadMap(result, true));
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			logger.info("userOpr:[addUserSkill]" + Tools.JsonHeadMap(result, false));
		}
		return SUCCESS;
	}
	public String addUserSkillJson() throws Exception{
		addUserSkill();
		return "json";
	}
	
	/**
	 * updateUserSkill 更新技能
	 * @return
	 * @throws Exception
	 */
	public String updateUserSkill() throws Exception{
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userSkillServices.updateUserSkill(userid, skillcode, skillurl);
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			logger.info("userOpr:[updateUserSkill]" + Tools.JsonHeadMap(result, true));
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			logger.info("userOpr:[updateUserSkill]" + Tools.JsonHeadMap(result, false));
		}
		return SUCCESS;
	}
	public String updateUserSkillJson() throws Exception{
		updateUserSkill();
		return "json";
	}
	
	/**
	 * verifyUserSkill 审核技能
	 * @return
	 * @throws Exception
	 */
//	public String verifyUserSkill() throws Exception{
//		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
//		int result = userSkillServices.verifyUserSkill(userid, skillcode, skillstatus);
//		if (result == ResultCode.SUCCESS) {
//			dataMap.putAll(Tools.JsonHeadMap(result, true));
//			logger.info("userOpr:[verifyUserSkill]" + Tools.JsonHeadMap(result, true));
//		} else {
//			dataMap.putAll(Tools.JsonHeadMap(result, false));
//			logger.info("userOpr:[verifyUserSkill]" + Tools.JsonHeadMap(result, false));
//		}
//		return SUCCESS;
//	}
//	public String verifyUserSkillJson() throws Exception{
//		verifyUserSkill();
//		return "json";
//	}
	
	/**
	 * verifyUserSkillBySkillId 通过skillid审批用户技能
	 * @return
	 * @throws Exception
	 */
	public String verifyUserSkillBySkillId() throws Exception{
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userSkillServices.verifyUserSkillBySkillId(skillid, skillstatus);
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			logger.info("userOpr:[verifyUserSkillBySkillId]" + Tools.JsonHeadMap(result, true));
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			logger.info("userOpr:[verifyUserSkillBySkillId]" + Tools.JsonHeadMap(result, false));
		}
		return SUCCESS;
	}
	public String verifyUserSkillBySkillIdJson() throws Exception{
		verifyUserSkillBySkillId();
		return "json";
	}
	
	/**
	 * deleteUserSkillBySkillId 刪除一個技能
	 * @return
	 * @throws Exception
	 */
	public String deleteUserSkillBySkillId() throws Exception{
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userSkillServices.deleteUserSkillBySkillId(skillid);
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			logger.info("userOpr:[deleteUserSkillBySkillId]" + Tools.JsonHeadMap(result, true));
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			logger.info("userOpr:[deleteUserSkillBySkillId]" + Tools.JsonHeadMap(result, false));
		}
		return SUCCESS;
	}
	public String deleteUserSkillBySkillIdJson() throws Exception{
		deleteUserSkillBySkillId();
		return "json";
	}
	
	/**
	 * deleteUserSkillByUserId 刪除userid下的全部技能
	 * @return
	 * @throws Exception
	 */
	public String deleteUserSkillByUserId() throws Exception{
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userSkillServices.deleteUserSkillByUserId(userid);
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			logger.info("userOpr:[deleteUserSkillByUserId]" + Tools.JsonHeadMap(result, true));
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			logger.info("userOpr:[deleteUserSkillByUserId]" + Tools.JsonHeadMap(result, false));
		}
		return SUCCESS;
	}
	public String deleteUserSkillByUserIdJson() throws Exception{
		deleteUserSkillByUserId();
		return "json";
	}
	
	
	public String checkUserListWhereOrderBy() throws Exception {
		System.out.println("Here is UserAction.checkUserListWhereOrderBy");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.checkUserListWhereOrderBy(order_col, order_value, where_col, where_value, where_col2, where_value2, pagenum);// 调用userServices.checkAddressList
		if (result == ResultCode.SUCCESS) {
			List<User> userlist = userServices.getUserlist();
			dataMap.put("userlist", userlist); // 把addresslist对象放入dataMap
			dataMap.putAll(pageinfo2map.pageInfoMap(userlist));// 把分页信息放进dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			logger.info("userOpr:[CheckUserList]" + Tools.JsonHeadMap(result, true));
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			logger.info("userOpr:[CheckUserList]" + Tools.JsonHeadMap(result, false));
		}

		return SUCCESS;
	}
	public String checkUserListWhereOrderByJson() throws Exception {
		System.out.println("Here is UserAction.checkUserListWhereOrderByJson");
		checkUserListWhereOrderBy();
		return "json";
	}
	
	// checkUserList Action
	public String checkUserList() throws Exception {
		System.out.println("Here is UserAction.checkUserList");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.checkUserList(col_name, specify_value, search_value, pagenum);// 调用userServices.checkAddressList
		if (result == ResultCode.SUCCESS) {
			List<User> userlist = userServices.getUserlist();
			
			dataMap.put("userlist", userlist); // 把addresslist对象放入dataMap
			dataMap.putAll(pageinfo2map.pageInfoMap(userlist)); // 把分页信息放进dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			logger.info("userOpr:[CheckUserList]" + Tools.JsonHeadMap(result, true));
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			logger.info("userOpr:[CheckUserList]" + Tools.JsonHeadMap(result, false));
		}
		
		return SUCCESS;
//		int tokenResult = userServices.checkUserToken(token);
//		if (tokenResult == ResultCode.SUCCESS) {
//			int accessResult = userServices.checkUserAccess(userServices.getUserinfo().getUser_access_group(), requireAccess);
//			if (accessResult == ResultCode.SUCCESS) {
//				int result = userServices.checkUserList(col_name, specify_value, search_value);// 调用userServices.checkAddressList
//				if (result == ResultCode.SUCCESS) {
//					List<User> userlist = userServices.getUserlist();
//					dataMap.put("userlist", userlist); // 把addresslist对象放入dataMap
//					dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
//					dataMap.put("errmsg", ResultCode.getErrmsg(result));
//					dataMap.put("checkUserListResult", true); // 放入checkUserNameResult
//				} else {
//					dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
//					dataMap.put("errmsg", ResultCode.getErrmsg(result));
//					dataMap.put("checkUserListResult", false); // 放入checkUserNameResult
//					System.out.println(dataMap);
//				}
//			} else {
//				dataMap.put("resultcode", accessResult); // 放入一个是否操作成功的标识
//				dataMap.put("errmsg", ResultCode.getErrmsg(accessResult));
//				dataMap.put("checkUserListResult", false); // 放入checkUserNameResult
//				System.out.println(dataMap);
//			}
//		} else {
//			dataMap.put("resultcode", tokenResult); // 放入一个是否操作成功的标识
//			dataMap.put("errmsg", ResultCode.getErrmsg(tokenResult));
//			dataMap.put("checkUserListResult", false); // 放入checkUserNameResult
//			System.out.println(dataMap);
//		}
	}
	
	public String checkUserListJson() throws Exception {
		System.out.println("Here is UserAction.checkUserListJson");
		checkUserList();
		return "json";
	}
	
	/**
	 * getUserById Action
	 * @return
	 * @throws Exception
	 */
	public String getUserById() throws Exception {
		System.out.println("Here is UserAction.getUserById");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		if (userid == null) {
			dataMap.putAll(Tools.JsonHeadMap(ResultCode.REQUIREINFO_NOTENOUGH,
					false));
			logger.info("userOpr:[CheckUser]"
					+ Tools.JsonHeadMap(ResultCode.REQUIREINFO_NOTENOUGH, false));
			return INPUT;
		}

		int result = userServices.getUserById(userid);
		if (result == ResultCode.SUCCESS) {
			User tempUser = userServices.getUserinfo();
			username = tempUser.getUser_name();
			account = tempUser.getUser_account();
			usertype = tempUser.getUser_type();
			name = tempUser.getUser_name();
			picurl = tempUser.getUser_pic_url();
			mobile = tempUser.getUser_mobile();
			gender = tempUser.getUser_gender();
			email = tempUser.getUser_email();
			language = tempUser.getUser_language();
			skill = tempUser.getUser_skill();
			skillname = tempUser.getUser_skill_name();
			token = tempUser.getUser_token();
			description = tempUser.getUser_description();
			addresscode = tempUser.getUser_address_code();
			address = tempUser.getUser_address();
			accessgroup = tempUser.getUser_access_group();
			status = tempUser.getUser_status();
			userid = tempUser.getUser_id();

//			dataMap = obj2map.getValueMap(tempUser); // 将对象转换成Map
			dataMap.put("userlist", tempUser); // 把addresslist对象放入dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			logger.info("userOpr:[CheckUser]" + Tools.JsonHeadMap(result, true));
			return "getUserByIdSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			logger.info("userOpr:[CheckUser]" + Tools.JsonHeadMap(result, false));
			return "getUserByIdFailed";
		}
		// int result = userServices.checkUserList("user_id", "",
		// userid.toString());// 调用userServices.checkAddressList
		// if (result == ResultCode.SUCCESS) {
		// List<User> userlist = userServices.getUserlist();
		//
		// dataMap.put("userlist", userlist); // 把addresslist对象放入dataMap
		// dataMap.putAll(Tools.JsonHeadMap(result, true));
		// } else {
		// dataMap.putAll(Tools.JsonHeadMap(result, false));
		// System.out.println(dataMap);
		// }
		// return SUCCESS;
	}
	public String getUserByIdJson() throws Exception {
		System.out.println("Here is UserAction.getUserByIdJson");
		getUserById();
		return "json";
	}
	
	// edituserPage Action
	public String editUser() throws Exception {
		getUserById();
		return "editUser";
	}
	
	// checkUserName Action
	public String checkUserName() throws Exception {
		System.out.println("Here is UserAction.checkUserName");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.checkUserName(username);// 调用userServices.checkUserName
		if (result == ResultCode.USERNAME_NOTEXIST) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
		}
		return SUCCESS;
	}
	
	public String checkUserNameJson() throws Exception {
		System.out.println("Here is UserAction.checkUserNameJson");
		checkUserName();
		return "json";
	}
	
	// Check user token Action
	public String checkToken() throws Exception {
		System.out.println("Here is UserAction.checkToken");

		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.checkUserToken(token); // 调用userServices.login

		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			logger.info("userOpr:[CheckToken]" + Tools.JsonHeadMap(result, true));
			return "checkTokenSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			System.out.println("Check Token Failed");
			logger.info("userOpr:[CheckToken]" + Tools.JsonHeadMap(result, false));
			return "checkTokenFailed";
		}
	}
	public String checkTokenJson() throws Exception {
		System.out.println("Here is UserAction.checkTokenJson");
		checkToken();
		return "json";
	}
	
	/**
	 * deleteUserById Action
	 * @return
	 * @throws Exception
	 */
	public String deleteUserById() throws Exception {
		System.out.println("Here is UserAction.deleteUserById");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		if (userid == null) {
			dataMap.putAll(Tools.JsonHeadMap(ResultCode.REQUIREINFO_NOTENOUGH,
					false));
			logger.info("userOpr:[DeleteUser]"
					+ Tools.JsonHeadMap(ResultCode.REQUIREINFO_NOTENOUGH, false));
			return INPUT;
		}

		int result = userServices.deleteUserById(token, userid);
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			logger.info("userOpr:[DeleteUser]" + Tools.JsonHeadMap(result, true));
			return "deleteUserByIdSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			logger.info("userOpr:[DeleteUser]" + Tools.JsonHeadMap(result, false));
			return "deleteUserByIdFailed";
		}
	}
	public String deleteUserByIdJson() throws Exception {
		System.out.println("Here is UserAction.deleteUserByIdJson");
		deleteUserById();
		return "json";
	}
	
	/**
	 * forgetPassword Action
	 * @return
	 * @throws Exception
	 */
	public String forgetPassword() throws Exception {
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int chkUsernameResult = userServices.checkUserName(username);// 调用userServices.checkUserName
		if (chkUsernameResult == ResultCode.REQUIREINFO_NOTENOUGH || chkUsernameResult == ResultCode.USERNAME_NOTEXIST) {
			dataMap.putAll(Tools.JsonHeadMap(chkUsernameResult, false));
			logger.info("userOpr:[forgetPassword][" + username + "]" + Tools.JsonHeadMap(chkUsernameResult, false));
			return null;
		} else {
			User tempUser = new User();
			tempUser = userServices.getUserinfo();
			if (Tools.isEmpty(tempUser.getUser_email())) {
				dataMap.putAll(Tools.JsonHeadMap(ResultCode.USEREMAIL_NULL, false));
				logger.info("userOpr:[forgetPassword][" + username + "]" + Tools.JsonHeadMap(ResultCode.USEREMAIL_NULL, false));
				return null;
			}
			String newPwd = randomCode.getSmallChar();
			int updateResult = userServices.updateUserPassword(tempUser.getUser_id(), newPwd);
			if (updateResult == ResultCode.SUCCESS) {
				int result = mailservices.sendSimpleMail(tempUser.getUser_email(), username, newPwd);
				dataMap.putAll(Tools.JsonHeadMap(result, true));
				logger.info("userOpr:[forgetPassword][" + username + "]Change password success." + Tools.JsonHeadMap(result, true));
				return null;
			} else {
				dataMap.putAll(Tools.JsonHeadMap(updateResult, false));
				logger.info("userOpr:[forgetPassword][" + username + "]" + Tools.JsonHeadMap(updateResult, false));
				return null;
			}
		}
	}
	public String forgetPasswordJson() throws Exception {
		forgetPassword();
		return "json";
	}
	
//	public void setTest(int test) {
//		this.test = test;
//	}
	
	// Json Format Return 
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	public UserServices getUserServices() {
		return userServices;
	}

	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public BigInteger getMobile() {
		return mobile;
	}

	public void setMobile(BigInteger mobile) {
		this.mobile = mobile;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAddresscode() {
		return addresscode;
	}

	public void setAddresscode(Integer addresscode) {
		this.addresscode = addresscode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAccessgroup() {
		return accessgroup;
	}

	public void setAccessgroup(String accessgroup) {
		this.accessgroup = accessgroup;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getCol_name() {
		return col_name;
	}

	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}

	public String getSpecify_value() {
		return specify_value;
	}

	public void setSpecify_value(String specify_value) {
		this.specify_value = specify_value;
	}

	public String getSearch_value() {
		return search_value;
	}

	public void setSearch_value(String search_value) {
		this.search_value = search_value;
	}

	public String getOrder_col() {
		return order_col;
	}

	public void setOrder_col(String order_col) {
		this.order_col = order_col;
	}

	public String getOrder_value() {
		return order_value;
	}

	public void setOrder_value(String order_value) {
		this.order_value = order_value;
	}

	public String getWhere_col() {
		return where_col;
	}

	public void setWhere_col(String where_col) {
		this.where_col = where_col;
	}

	public String getWhere_value() {
		return where_value;
	}

	public void setWhere_value(String where_value) {
		this.where_value = where_value;
	}

	public Integer getPagenum() {
		return pagenum;
	}

	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public String isUpdateResult() {
		return updateResult;
	}

	public void setUpdateResult(String updateResult) {
		this.updateResult = updateResult;
	}
	public String getSkillname() {
		return skillname;
	}
	public void setSkillname(String skillname) {
		this.skillname = skillname;
	}
	public String getSkillurl() {
		return skillurl;
	}
	public void setSkillurl(String skillurl) {
		this.skillurl = skillurl;
	}
	public Integer getSkillcode() {
		return skillcode;
	}
	public void setSkillcode(Integer skillcode) {
		this.skillcode = skillcode;
	}
	public Integer getSkillstatus() {
		return skillstatus;
	}
	public void setSkillstatus(Integer skillstatus) {
		this.skillstatus = skillstatus;
	}
	public Integer getSkillid() {
		return skillid;
	}
	public void setSkillid(Integer skillid) {
		this.skillid = skillid;
	}
	public Integer getDriver() {
		return driver;
	}
	public void setDriver(Integer driver) {
		this.driver = driver;
	}
	public String getPushtoken() {
		return pushtoken;
	}
	public void setPushtoken(String pushtoken) {
		this.pushtoken = pushtoken;
	}
	public String getWhere_value2() {
		return where_value2;
	}
	public void setWhere_value2(String where_value2) {
		this.where_value2 = where_value2;
	}
	public String getWhere_col2() {
		return where_col2;
	}
	public void setWhere_col2(String where_col2) {
		this.where_col2 = where_col2;
	}
}
