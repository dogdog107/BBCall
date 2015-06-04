package com.bbcall.struts.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.table.Blacklist;
import com.bbcall.struts.services.BlacklistServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("blacklistAction")
@SuppressWarnings("serial")
public class BlacklistAction extends ActionSupport {

	@Autowired
	private BlacklistServices blacklistServices;

	private int blacklist_id;
	private String blacklist_user_account;
	private String blacklist_master_account;
	private int blacklist_order_id;

	private Map<String, Object> dataMap;

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	// 添加黑名单action
	public String insert() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = blacklistServices.addBlacklist(blacklist_user_account,
				blacklist_master_account, blacklist_order_id);

		if (result == ResultCode.SUCCESS) {
			List<Blacklist> blacklists = blacklistServices.blacklistinfos();
			dataMap.put("blacklists", blacklists);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("insertResult", true);
			System.out.println(dataMap);
		}

		return SUCCESS;
	}

	public String insertJson() throws Exception {
		System.out.println("Here is blacklistAction.insertJson");
		insert();
		return "json";
	}

	// 删除黑名单action
	public String delete() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = blacklistServices.deleteBlacklist(blacklist_id,
				blacklist_user_account);

		if (result == ResultCode.SUCCESS) {
			List<Blacklist> blacklists = blacklistServices.blacklistinfos();
			dataMap.put("blacklists", blacklists);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("deleteResult", true);
			System.out.println(dataMap);
		}

		return SUCCESS;
	}

	public String deleteJson() throws Exception {
		System.out.println("Here is blacklistAction.deleteJson");
		delete();
		return "json";
	}

	public String getBlacklist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = 0;

		if (blacklist_user_account != null) {
			result = blacklistServices.getBlacklist(blacklist_user_account);
		} else {
			result = blacklistServices.getBlacklist(blacklist_master_account);
		}

		if (result == ResultCode.SUCCESS) {
			List<Blacklist> blacklists = blacklistServices.blacklistinfos();
			dataMap.put("blacklists", blacklists);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("getBlacklistResult", true);
			System.out.println(dataMap);
		}

		return SUCCESS;

	}

	public String getBlacklistJson() throws Exception {
		System.out.println("Here is blacklistAction.getBlacklistJson");
		getBlacklist();
		return "json";
	}
	
	public String getBlacklistById() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		
		int result = blacklistServices.getBlacklistById(blacklist_id);
		
		if (result == ResultCode.SUCCESS) {
			Blacklist blacklist = blacklistServices.blacklistinfo();
			dataMap.put("blacklist", blacklist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("getBlacklistByIdResult", true);
			System.out.println(dataMap);
		}

		return SUCCESS;
	}
	
	public String getBlacklistByIdJson() throws Exception {
		System.out.println("Here is blacklistAction.getBlacklistByIdJson");
		getBlacklistById();
		return "json";
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setBlacklistServices(BlacklistServices blacklistServices) {
		this.blacklistServices = blacklistServices;
	}

	public void setBlacklist_id(int blacklist_id) {
		this.blacklist_id = blacklist_id;
	}

	public void setBlacklist_user_account(String blacklist_user_account) {
		this.blacklist_user_account = blacklist_user_account;
	}

	public void setBlacklist_master_account(String blacklist_master_account) {
		this.blacklist_master_account = blacklist_master_account;
	}

	public void setBlacklist_order_id(int blacklist_order_id) {
		this.blacklist_order_id = blacklist_order_id;
	}

}
