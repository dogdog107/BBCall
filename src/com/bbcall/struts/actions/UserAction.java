package com.bbcall.struts.actions;

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
			dataMap.put("checkUserNameResult", true); // 放入checkUserNameResult
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			System.out.println(dataMap);
		} else {
			dataMap.put("checkUserNameResult", false); // 放入checkUserNameResult
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			System.out.println(dataMap);
		}
		return SUCCESS;
	}

	public String checkUserNameJson() throws Exception {
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
			System.out.println(dataMap);
			return "loginSuccess";
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			System.out.println(dataMap);
			System.out.println("login Failed");
			return "loginFailed";
		}
	}

	public String loginJson() throws Exception {
		login();
		return "json";
	}

	// Register Action

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

}
