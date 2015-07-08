package com.bbcall.struts.actions;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.table.Preorder;
import com.bbcall.struts.services.PreorderServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("preorderAction")
@SuppressWarnings("serial")
public class PreorderAction extends ActionSupport {

	@Autowired
	PreorderServices preorderServices;

	private String preorder_id;
	private String preorder_master_account;
	private Timestamp preorder_create_time;
	private String preorder_price;
	private String preorder_order_id;

	private Map<String, Object> dataMap;

	public String add() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		
		double price = Double.parseDouble(preorder_price);
		int preorderid = Integer.parseInt(preorder_order_id);
		
		
		int result = preorderServices.addPreorder(preorder_master_account, price, preorderid);
		
		if (result == ResultCode.SUCCESS) {
			List<Preorder> preorderlist = preorderServices.preorderinfos();
			
			dataMap.put("preorderlist", preorderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("addResult", true);
		}
		return SUCCESS;
	}
	
	public String addJson() throws Exception {
		add();
		return "json";
	}
	
	public String showbyid() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		
		int preorderid = Integer.parseInt(preorder_order_id);
		
		int result = preorderServices.getPreorderById(preorderid);
		
		if (result == ResultCode.SUCCESS) {
			List<Preorder> preorderlist = preorderServices.preorderinfos();
			
			dataMap.put("preorderlist", preorderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("showbyidResult", true);
		}
		return SUCCESS;
	}
	
	public String showbyidJson() throws Exception {
		showbyid();
		return "json";
	}
	
	public String showbyaccount() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		
		int result = preorderServices.getPreorderByAccount(preorder_master_account);
		
		if (result == ResultCode.SUCCESS) {
			List<Preorder> preorderlist = preorderServices.preorderinfos();
			
			dataMap.put("preorderlist", preorderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("showbyaccountResult", true);
		}
		return SUCCESS;
	}
	
	public String showbyaccountJson() throws Exception {
		showbyaccount();
		return "json";
	}
	
	public String delete() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		
		int preorderid = Integer.parseInt(preorder_id);
		
		int result = preorderServices.deletePreorder(preorderid,preorder_master_account);
		
		if (result == ResultCode.SUCCESS) {
			List<Preorder> preorderlist = preorderServices.preorderinfos();
			
			dataMap.put("preorderlist", preorderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("deleteResult", true);
		}
		return SUCCESS;
	}
	
	public String deleteJson() throws Exception {
		delete();
		return "json";
	}
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public PreorderServices getPreorderServices() {
		return preorderServices;
	}

	public void setPreorderServices(PreorderServices preorderServices) {
		this.preorderServices = preorderServices;
	}

	public String getPreorder_id() {
		return preorder_id;
	}

	public void setPreorder_id(String preorder_id) {
		this.preorder_id = preorder_id;
	}

	public String getPreorder_master_account() {
		return preorder_master_account;
	}

	public void setPreorder_master_account(String preorder_master_account) {
		this.preorder_master_account = preorder_master_account;
	}

	public Timestamp getPreorder_create_time() {
		return preorder_create_time;
	}

	public void setPreorder_create_time(Timestamp preorder_create_time) {
		this.preorder_create_time = preorder_create_time;
	}

	public String getPreorder_price() {
		return preorder_price;
	}

	public void setPreorder_price(String preorder_price) {
		this.preorder_price = preorder_price;
	}

	public String getPreorder_order_id() {
		return preorder_order_id;
	}

	public void setPreorder_order_id(String preorder_order_id) {
		this.preorder_order_id = preorder_order_id;
	}

}
