package com.bbcall.struts.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.table.Referdoc;
import com.bbcall.struts.services.ReferdocServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("referdocAction")
@SuppressWarnings("serial")
public class ReferdocAction extends ActionSupport {

	@Autowired
	private ReferdocServices referdocServices;

	private Map<String, Object> dataMap;

	private int referdoc_id;
	private String referdoc_type;
	private double referdoc_price;

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	// 添加参考数据action
	public String add() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = referdocServices
				.addReferdoc(referdoc_type, referdoc_price);

		if (result == ResultCode.SUCCESS) {
			List<Referdoc> referdoclist = referdocServices.referdocinfos();
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("addResult", true);
			return SUCCESS;
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("addResult", false); // 放入registerResult
			System.out.println(dataMap);
			System.out.println("add Failed");
			return "addFailed";
		}

	}

	public String addJson() throws Exception {
		add();
		return "json";
	}

	public String update() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = referdocServices.updateReferdoc(referdoc_id,
				referdoc_type, referdoc_price);

		if (result == ResultCode.SUCCESS) {
			Referdoc referdoc = referdocServices.referdocinfo();
			dataMap.put("orderlist", referdoc);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("updateResult", true);
		}

		return SUCCESS;

	}

	public String updateJson() throws Exception {
		update();
		return "json";
	}

	public String delete() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = referdocServices.deleteReferdoc(referdoc_id);

		if (result == ResultCode.SUCCESS) {
			List<Referdoc> referdoclist = referdocServices.referdocinfos();
			dataMap.put("referdoclist", referdoclist);
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

	public String select() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = referdocServices.getReferdoc(referdoc_id);

		if (result == ResultCode.SUCCESS) {
			Referdoc referdoc = referdocServices.referdocinfo();
			dataMap.put("referdoc", referdoc);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("deleteResult", true);
		}

		return SUCCESS;
	}

	public String selectJson() throws Exception {
		select();
		return "json";
	}

	public String getlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = referdocServices.getReferdoclist();

		if (result == ResultCode.SUCCESS) {
			List<Referdoc> referdoclist = referdocServices.referdocinfos();
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("getlistResult", true);
		}

		return SUCCESS;
	}

	public String getlistJson() throws Exception {
		getlist();
		return "json";
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public ReferdocServices getReferdocServices() {
		return referdocServices;
	}

	public void setReferdocServices(ReferdocServices referdocServices) {
		this.referdocServices = referdocServices;
	}

	public int getReferdoc_id() {
		return referdoc_id;
	}

	public void setReferdoc_id(int referdoc_id) {
		this.referdoc_id = referdoc_id;
	}

	public String getReferdoc_type() {
		return referdoc_type;
	}

	public void setReferdoc_type(String referdoc_type) {
		this.referdoc_type = referdoc_type;
	}

	public double getReferdoc_price() {
		return referdoc_price;
	}

	public void setReferdoc_price(double referdoc_price) {
		this.referdoc_price = referdoc_price;
	}

}
