package com.bbcall.struts.actions;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.table.AddressList;
import com.bbcall.struts.services.UserServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("userAction")
@SuppressWarnings("serial")
public class UserAction extends ActionSupport {

	@Autowired
	private UserServices userServices;
	private Map<String, Object> dataMap;

	private String username;
	private String password;
	private String account;
	private Integer usertype;
	private String name;
	private String picurl;
	private BigInteger mobile;
	private String gender;
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
	
//	private int test;
	
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
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public String login() throws Exception {
		System.out.println("Here is UserAction.login");

		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		//Map<String, Map<String, String>> map0 = new HashMap<String, Map<String, String>>();
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.login(username, password); // 调用userServices.login

		if (result == ResultCode.SUCCESS) {
			Object userinfo = userServices.userInfo(); // 调用userInfo对象
			dataMap.put("userinfo", userinfo); // 把userinfo对象放入dataMap
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("loginResult", true); // 放入loginResult
			System.out.println(dataMap);
			System.out.println((dataMap.get("userinfo")));
			return "loginSuccess";
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("loginResult", false); // 放入loginResult
			System.out.println(dataMap);
			System.out.println("login Failed");
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

		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
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
		
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.update(account, password, usertype, name, picurl, mobile, gender, addresscode, address, email, language, skill, description, accessgroup, status, token, userid); // 调用userServices.login

		if (result == ResultCode.SUCCESS) {
			Object userinfo = userServices.userInfo(); // 调用userInfo对象
			dataMap.put("userinfo", userinfo); // 把userinfo对象放入dataMap
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
	
	// checkAddressList Action
	public String checkAddressList() throws Exception {
		System.out.println("Here is UserAction.checkAddressList");
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.checkAddressList(addresscode);// 调用userServices.checkAddressList
		
		if (result == ResultCode.SUCCESS) {
			List<AddressList> addresslist = userServices.addressList();
			dataMap.put("addresslist", addresslist); // 把addresslist对象放入dataMap
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkAddressListResult", true); // 放入checkUserNameResult
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkAddressListResult", false); // 放入checkUserNameResult
			System.out.println(dataMap);
		}
		return SUCCESS;
	}
	
	public String checkAddressListJson() throws Exception {
		System.out.println("Here is UserAction.checkAddressListJson");
		checkAddressList();
		return "json";
	}
	
	// checkUserName Action
	public String checkUserName() throws Exception {
		System.out.println("Here is UserAction.checkUserName");
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
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

		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
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
	
	// Json Format Return 
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public void setMobile(BigInteger mobile) {
		this.mobile = mobile;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAddresscode(Integer addresscode) {
		this.addresscode = addresscode;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAccessgroup(String accessgroup) {
		this.accessgroup = accessgroup;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
//	public void setTest(int test) {
//		this.test = test;
//	}

}
