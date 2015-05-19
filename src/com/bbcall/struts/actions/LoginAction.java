package com.bbcall.struts.actions;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.struts.services.ResultCode;
import com.bbcall.struts.services.UserServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("loginAction")
@SuppressWarnings("serial")
public class LoginAction extends ActionSupport {

	/**
	 * 
	 */
	// private static final long serialVersionUID = 1L;
	@Autowired
	private UserServices userServices;

	private String username;
	private String password;
	private String type;
	private String result;

	private Map<String, Object> dataMap;

	@Override
	public String execute() throws Exception {
		System.out.println("Here is LoginAction");
		int resultcode;
		//String returntype = null;
		String result = userServices.login(username, password);

		// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap = new HashMap<String, Object>();

		if (result == "Login Success") {
			// 调用userInfo对象
			Object userinfo = userServices.userInfo();

			// 设置resultcode
			resultcode = ResultCode.SUCCESS;
			// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
			dataMap.clear();
			// 把userinfo对象放入dataMap
			dataMap.put("user", userinfo);
			// 放入一个是否操作成功的标识
			dataMap.put("resultcode", resultcode);
			dataMap.put("errmsg", ResultCode.getErrmsg(resultcode));
			System.out.println(dataMap);

			return SUCCESS;

		} else {

			System.out.println("Failed");
			// 设置resultcode
			resultcode = ResultCode.PASSWORD_ERROR;
			// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
			dataMap.clear();
			// 放入一个是否操作成功的标识
			dataMap.put("resultcode", resultcode);
			dataMap.put("errmsg", ResultCode.getErrmsg(resultcode));

			return INPUT;

		}

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

	public void setType(String type) {
		this.type = type;
	}

	@JSON(serialize = false)
	public String getResult() {
		return result;
	}

	// @JSON(name = "USER")
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

}
