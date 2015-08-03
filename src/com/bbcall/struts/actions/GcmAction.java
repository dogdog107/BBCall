package com.bbcall.struts.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.struts.services.GcmServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("gcmAction")
@SuppressWarnings("serial")
public class GcmAction extends ActionSupport {

	@Autowired
	private GcmServices gcmServices;

	private Map<String, Object> dataMap;

	private String datamsg;

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	public String sendmsg() throws Exception {
		
		List<String> registeridList = new ArrayList<String>();
		
		int result = gcmServices.sendtogoogle(datamsg,registeridList);
		
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
		}
		return SUCCESS;
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

}
