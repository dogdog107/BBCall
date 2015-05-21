package com.bbcall.struts.actions;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.struts.services.ResultCode;
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
	private int type;
	private String name;
	private String picurl;
	private BigInteger mobile;
	private String gender;
	private String email;
	private String language;
	private String skill;
	
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	// CheckUserName Action

	public String checkUserName() throws Exception {
		System.out.println("Here is UserAction.checkusername");
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
		System.out.println("Here is UserAction.checkusernameJson");
		checkUserName();
		return "json";
	}

	// Login Action
	
	public String login() throws Exception {
		System.out.println("Here is UserAction.login");

		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = userServices.login(username, password); // 调用userServices.login

		if (result == ResultCode.SUCCESS) {
			Object userinfo = userServices.userInfo(); // 调用userInfo对象
			dataMap.put("user", userinfo); // 把userinfo对象放入dataMap
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("loginResult", true); // 放入loginResult
			System.out.println(dataMap);
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
		int result = userServices.register(account, password, type, name, picurl, mobile, gender, email, language, skill); // 调用userServices.register

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

	// Json Format Return 
	public Map<String, Object> getDataMap() {
		return dataMap;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public void setMobile(BigInteger  mobile) {
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

	public void setType(int type) {
		this.type = type;
	}

}
