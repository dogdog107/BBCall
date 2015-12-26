package com.bbcall.struts.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.table.PushMessage;
import com.bbcall.struts.services.DevicePushServices;
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
	
	@Autowired
	private DevicePushServices devicePushServices;
	
	private Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串

	private String datamsg;
	private Integer usertype;
	
	private Integer msgid;
	private String msgcontents;
	
	private String pushToken;

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
		sendmsgIos();
		return "json";
	}

	public String sendmsgIos() throws Exception {

		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = 0;
		if (!Tools.isEmpty(pushToken) && usertype != null) {
			List<String> pushTokenList = new ArrayList<String>();
			pushTokenList.add(pushToken);
			result = devicePushServices.devicePush(2, pushTokenList, datamsg, usertype, null, null);
			if (result != ResultCode.SUCCESS) {
				dataMap.putAll(Tools.JsonHeadMap(result, false));
				return "exception";
			}
		} else {
			List<String> userPushTokenList = new ArrayList<String>();
			List<String> masterPushTokenList = new ArrayList<String>();
			switch (usertype) {
			case 1:
				userPushTokenList = userServices.getPushTokenByDriver(2, 1);
				masterPushTokenList = null;
				break;
			case 2:
				masterPushTokenList = userServices.getPushTokenByDriver(2, 2);
				userPushTokenList = null;
				break;

			default:
				userPushTokenList = userServices.getPushTokenByDriver(2, 1);
				masterPushTokenList = userServices.getPushTokenByDriver(2, 2);
				break;
			}
			
			if (userPushTokenList != null && userPushTokenList.size() > 0) {
//			result = iosPushServices.iosPush(userPushTokenList, datamsg, 1, null, null);
				result = devicePushServices.devicePush(2, userPushTokenList, datamsg, 1, null, null);
//			result = devicePushServices.devicePush(2, userPushTokenList, 3, 1, 503);
				if (result != ResultCode.SUCCESS) {
					dataMap.putAll(Tools.JsonHeadMap(result, false));
					return "exception";
				}
			}
			
			if (masterPushTokenList != null && masterPushTokenList.size() > 0) {
//			result = iosPushServices.iosPush(masterPushTokenList, datamsg, 2, null, null);
				result = devicePushServices.devicePush(2, masterPushTokenList, datamsg, 2, null, null);
//			result = devicePushServices.devicePush(2, masterPushTokenList, 3, 2, 503);
				if (result != ResultCode.SUCCESS) {
					dataMap.putAll(Tools.JsonHeadMap(result, false));
					return "exception";
				}
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

	/**
	 * getPushMessage
	 * @return
	 * @throws Exception
	 */
	public String getPushMessage() throws Exception {
		List<PushMessage> pushMsg = devicePushServices.getPushMessage();
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("pushMessage", pushMsg);
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String getPushMessageJson() throws Exception {
		getPushMessage();
		return "json";
	}
	
	/**
	 * updatePushMessage
	 * @return
	 * @throws Exception
	 */
	public String updatePushMessage() throws Exception {
		dataMap.clear();
		int result = devicePushServices.updatePushMessage(msgid, msgcontents);
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
//			logger.info("userOpr:[UserUpdate][Updated ID: " + userid + "]" + Tools.JsonHeadMap(result, true));
			return SUCCESS;
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			return INPUT;
		}
	}
	public String updatePushMessageJson() throws Exception {
		updatePushMessage();
		return "json";
	}
	
	/**
	 * Getter & Setter
	 * @return
	 */
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

	public Integer getMsgid() {
		return msgid;
	}

	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}

	public String getMsgcontents() {
		return msgcontents;
	}

	public void setMsgcontents(String msgcontents) {
		this.msgcontents = msgcontents;
	}

	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

}