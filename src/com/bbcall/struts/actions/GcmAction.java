package com.bbcall.struts.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.struts.services.GcmServices;
import com.bbcall.struts.services.IosPushServices;
import com.bbcall.struts.services.UserServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("gcmAction")
@SuppressWarnings("serial")
public class GcmAction extends ActionSupport {

	@Autowired
	private GcmServices gcmServices;

	@Autowired
	private IosPushServices iosPushServices;
	
	@Autowired
	private UserServices userServices;

	private Map<String, Object> dataMap;

	private String datamsg;
	private Integer usertype;

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String sendmsg() throws Exception {
		
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		List<String> registeridList = userServices.getPushTokenByDriver(1, null);

		System.out.println(datamsg);
		int result = gcmServices.sendtogoogle(datamsg, registeridList, null);

		System.out.println("result : " + result);
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(datamsg);
			System.out.println(dataMap);
			return SUCCESS;
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			return "exception";
		}
	}

	public String sendmsgJson() throws Exception {
		System.out.println("sendmsgJson");
		sendmsg();
		return "json";
	}

	public String sendmsgIos() throws Exception {

		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		List<String> userPushTokenList = userServices.getPushTokenByDriver(2, 1);
		List<String> masterPushTokenList = userServices.getPushTokenByDriver(2, 2);

		int result = 0;
		if (userPushTokenList != null && userPushTokenList.size() > 0) {
			result = iosPushServices.iosPush(userPushTokenList, datamsg, 1, null, null);
			if (result != ResultCode.SUCCESS) {
				dataMap.putAll(Tools.JsonHeadMap(result, false));
				return "exception";
			}
		}

		if (masterPushTokenList != null && masterPushTokenList.size() > 0) {
			result = iosPushServices.iosPush(masterPushTokenList, datamsg, 2, null, null);
			if (result != ResultCode.SUCCESS) {
				dataMap.putAll(Tools.JsonHeadMap(result, false));
				return "exception";
			}
		}

		dataMap.putAll(Tools.JsonHeadMap(result, true));
		System.out.println(datamsg);
		System.out.println(dataMap);
		return SUCCESS;
	}
	
	public String sendmsgIosJson() throws Exception {
		System.out.println("sendmsgIosJson");
		sendmsgIos();
		return "json";
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public String getDatamsg() {
		return datamsg;
	}

	public void setDatamsg(String datamsg) {
		this.datamsg = datamsg;
	}

	public GcmServices getGcmServices() {
		return gcmServices;
	}

	public void setGcmServices(GcmServices gcmServices) {
		this.gcmServices = gcmServices;
	}

	public UserServices getUserServices() {
		return userServices;
	}

	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

}