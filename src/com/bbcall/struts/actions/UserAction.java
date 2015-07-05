package com.bbcall.struts.actions;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.ObjectToMap;
import com.bbcall.mybatis.table.AddressList;
import com.bbcall.mybatis.table.User;
import com.bbcall.struts.services.UserServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("userAction")
@SuppressWarnings("serial")
public class UserAction extends ActionSupport implements SessionAware{

	@Autowired
	private UserServices userServices;
	private Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	private ObjectToMap obj2map = new ObjectToMap();// 新建ObjectToMap对象
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
	private String token;
	private String description;
	private Integer addresscode;
	private String address;
	private String accessgroup;
	private Integer status;
	private Integer userid;
	private String col_name;
	private String specify_value;
	private String search_value;
	
	
//	private int test;
	
//	private HttpServletRequest request;
//    //实现接口中的方法
//    public void setServletRequest(HttpServletRequest request){
//     this.request = request;
//    }
	
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	
//	public String test() throws Exception{
//		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
//		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
//		List<UserSkill> result = userServices.test(test);
//		dataMap.put("test list", result);
//		return "json";
//	}
	
	

	// Login Action
	public String login() throws Exception {
		System.out.println("Here is UserAction.login");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.login(username, password); // 调用userServices.login

		if (result == ResultCode.SUCCESS) {
			Object userinfo = userServices.getUserinfo(); // 调用userInfo对象
//			dataMap.put("userinfo", userinfo); // 把userinfo对象放入dataMap
			dataMap = obj2map.getValueMap(userinfo); //将对象转换成Map
			session.putAll(dataMap);// 把用户信息放进session
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("loginResult", true); // 放入loginResult
			System.out.println(dataMap);
			System.out.println(session);
//			System.out.println((dataMap.get("userinfo")));
			return "loginSuccess";
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("loginResult", false); // 放入loginResult
			System.out.println(dataMap);
			System.out.println("login Failed");
//			request.setAttribute("loginResult", false);
			return "loginFailed";
		}
	}

	public String loginJson() throws Exception {
		System.out.println("Here is UserAction.loginJson");
		login();
		return "json";
	}

	// Register Action
	
	public String register() throws Exception {
		System.out.println("Here is UserAction.register");

		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.register(account, password, usertype, name, picurl, mobile, gender, email, language, skill, description); // 调用userServices.register

		if (result == ResultCode.SUCCESS) {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("registerResult", true); // 放入registerResult
			System.out.println(dataMap);
			return "registerSuccess";
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("registerResult", false); // 放入registerResult
			System.out.println(dataMap);
			System.out.println("register Failed");
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
		int result = userServices.update(account, password, usertype, name, picurl, mobile, gender, addresscode, address, email, language, skill, description, accessgroup, status, token, userid); // 调用userServices.login

		if (result == ResultCode.SUCCESS) {
			Object userinfo = userServices.getUserinfo(); // 调用userInfo对象
//			Object updateduserinfo = userServices.getUpdateduserinfo(); // 调用userInfo对象
//			dataMap.put("userinfo", userinfo); // 把userinfo对象放入dataMap
			dataMap = obj2map.getValueMap(userinfo); //将对象转换成Map
			session.putAll(dataMap);// 把用户信息放进session
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("updateResult", true); // 放入registerResult
			System.out.println(dataMap);
			return "updateSuccess";
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("updateResult", false); // 放入registerResult
			System.out.println(dataMap);
			System.out.println("Update Failed");
			return "updateFailed";
		}
		
	}
	public String updateJson() throws Exception{
		System.out.println("Here is UserAction.updateJson");
		update();
		return "json";
	}
	// UpdateStatus Action
	public String updateStatus() throws Exception{
		System.out.println("Here is UserAction.updateStatus");
		
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.update(account, password, usertype, name, picurl, mobile, gender, addresscode, address, email, language, skill, description, accessgroup, status, token, userid); // 调用userServices.login
		
		if (result == ResultCode.SUCCESS) {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("updateStatusResult", true); // 放入registerResult
			System.out.println(dataMap);
			return "updateStatusSuccess";
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("updateStatusResult", false); // 放入registerResult
			System.out.println(dataMap);
			System.out.println("updateStatus Failed");
			return "updateStatusFailed";
		}
		
	}
	public String updateStatusJson() throws Exception{
		System.out.println("Here is UserAction.updateStatusJson");
		updateStatus();
		return "json";
	}
	
	// checkParentAdsList Action
	public String checkChildAdsList() throws Exception {
		System.out.println("Here is UserAction.checkChildAdsList");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.checkChildAdsList(addresscode);// 调用userServices.checkParentAdsList
		
		if (result == ResultCode.SUCCESS) {
			List<AddressList> addresslist = userServices.getAddresslist();
			dataMap.put("addresslist", addresslist); // 把addresslist对象放入dataMap
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkChildAdsListResult", true); // 放入checkUserNameResult
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkChildAdsListResult", false); // 放入checkUserNameResult
			System.out.println(dataMap);
		}
		return SUCCESS;
	}
	
	public String checkChildAdsListJson() throws Exception {
		System.out.println("Here is UserAction.checkChildAdsListJson");
		checkChildAdsList();
		return "json";
	}
	
	// checkAdsList Action
	public String checkAdsList() throws Exception {
		System.out.println("Here is UserAction.checkAdsList");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.checkAdsList(addresscode);// 调用userServices.checkAdsList
		
		if (result == ResultCode.SUCCESS) {
			List<AddressList> addresslist = userServices.getAddresslist();
			dataMap.put("addresslist", addresslist); // 把addresslist对象放入dataMap
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkAdsListResult", true); // 放入checkUserNameResult
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkAdsListResult", false); // 放入checkUserNameResult
			System.out.println(dataMap);
		}
		return SUCCESS;
	}
	
	public String checkAdsListJson() throws Exception {
		System.out.println("Here is UserAction.checkAdsListJson");
		checkAdsList();
		return "json";
	}
	
	// checkUserList Action
	public String checkUserList() throws Exception {
		System.out.println("Here is UserAction.checkUserList");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.checkUserList(token, col_name, specify_value, search_value);// 调用userServices.checkAddressList
		if (result == ResultCode.SUCCESS) {
			List<User> userlist = userServices.getUserlist();
			dataMap.put("userlist", userlist); // 把addresslist对象放入dataMap
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkUserListResult", true); // 放入checkUserNameResult
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkUserListResult", false); // 放入checkUserNameResult
			System.out.println(dataMap);
		}
		return SUCCESS;
	}
	
	public String checkUserListJson() throws Exception {
		System.out.println("Here is UserAction.checkUserListJson");
		checkUserList();
		return "json";
	}
	
	// checkUserName Action
	public String checkUserName() throws Exception {
		System.out.println("Here is UserAction.checkUserName");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.checkUserName(username);// 调用userServices.checkUserName
		if (result == ResultCode.USERNAME_NOTEXIST) {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkUserNameResult", true); // 放入checkUserNameResult
			System.out.println(dataMap);
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkUserNameResult", false); // 放入checkUserNameResult
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
		int result = userServices.checkToken(token); // 调用userServices.login

		if (result == ResultCode.SUCCESS) {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkTokenResult", true); // 放入registerResult
			System.out.println(dataMap);
			return "checkTokenSuccess";
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkTokenResult", false); // 放入registerResult
			System.out.println(dataMap);
			System.out.println("Check Token Failed");
			return "checkTokenFailed";
		}
	}
	
	public String checkTokenJson() throws Exception {
		System.out.println("Here is UserAction.checkTokenJson");
		checkToken();
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
}
