package com.bbcall.struts.actions;

import com.bbcall.struts.services.UserServices;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {

	private UserServices userServices;
	private String username;
	private String password;



	@Override
	public String execute() throws Exception {
		System.out.println("Here is LoginAction");


		return SUCCESS;
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
	
}
