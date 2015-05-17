package com.bbcall.struts.actions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.struts.services.UserServices;
import com.opensymphony.xwork2.ActionSupport;


@Scope("prototype")
@Controller("loginAction")
public class LoginAction extends ActionSupport {
	
	@Autowired
	private UserServices userServices;
	private String username;
	private String password;
	private String result;
    private Map<String,Object> dataMap; 
    
	@Override
	public String execute() throws Exception {
		System.out.println("Here is LoginAction");
		String result = userServices.login(username, password);
		
        // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据  
        dataMap = new HashMap<String, Object>();  
		if (result == "Login Success") {
			return SUCCESS;
		} else {
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

	public String getResult() {
		return result;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}


}
