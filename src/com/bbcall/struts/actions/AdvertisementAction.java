package com.bbcall.struts.actions;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.struts.services.AdvertisementServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("advertisementAction")
@SuppressWarnings("serial")
public class AdvertisementAction extends ActionSupport{
	@Autowired
	private AdvertisementServices advertisementServices;
	private Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	
	
	// Json Format Return
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
}
