package com.bbcall.struts.actions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.table.AddressList;
import com.bbcall.struts.services.AddressServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("addressAction")
@SuppressWarnings("serial")
public class AddressAction extends ActionSupport{
	@Autowired
	private AddressServices addressServices;
	private Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	private Integer addresscode;
	
	// checkChildAdsList Action
	public String checkChildAdsList() throws Exception {
		System.out.println("Here is UserAction.checkChildAdsList");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = addressServices.checkChildAdsList(addresscode);// 调用userServices.checkParentAdsList
		
		if (result == ResultCode.SUCCESS) {
			List<AddressList> addresslist = addressServices.getAddresslist();
			dataMap.put("addresslist", addresslist); // 把addresslist对象放入dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
		}
		return SUCCESS;
	}
	
	public String checkChildAdsListJson() throws Exception {
		System.out.println("Here is UserAction.checkChildAdsListJson");
		checkChildAdsList();
		return "json";
	}
	
	// checkAdsList Action
	public String checkAdsList() throws Exception {
		System.out.println("Here is UserAction.checkAdsList");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = addressServices.checkAdsList(addresscode);// 调用userServices.checkAdsList
		
		if (result == ResultCode.SUCCESS) {
			List<AddressList> addresslist = addressServices.getAddresslist();
			dataMap.put("addresslist", addresslist); // 把addresslist对象放入dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
		}
		return SUCCESS;
	}
	
	public String checkAdsListJson() throws Exception {
		System.out.println("Here is UserAction.checkAdsListJson");
		checkAdsList();
		return "json";
	}
	
	// Json Format Return 
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public Integer getAddresscode() {
		return addresscode;
	}

	public void setAddresscode(Integer addresscode) {
		this.addresscode = addresscode;
	}
	
}
