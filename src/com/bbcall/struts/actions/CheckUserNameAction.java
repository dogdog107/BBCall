package com.bbcall.struts.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.struts.services.UserServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("checkUserNameAction")
@SuppressWarnings("serial")
public class CheckUserNameAction extends ActionSupport {

	@Autowired
	private UserServices userServices;
	private String username;
	private boolean userResult;

	@Override
	public String execute() throws Exception {
		if (userServices.checkUserName(username)) {
			userResult = true;
			System.out.println(userResult);
		} else {
			userResult = false;
			System.out.println(userResult);
		}
		return SUCCESS;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean getUserResult() {
		return userResult;
	}

}
