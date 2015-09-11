package com.bbcall.struts.actions;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.functions.VersionControl;
import com.opensymphony.xwork2.ActionSupport;


@Scope("prototype")
@Controller("systemAction")
public class SystemAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2930214750478335826L;
	
	private String version;
	private Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	
	/**
	 * getAndroidVersion 
	 * @return
	 * @throws Exception
	 */
	public String getAndroidVersion() throws Exception {
		version = VersionControl.getAndroidVer();
		dataMap.put("version", version);
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return null;
	}
	public String getAndroidVersionJson() throws Exception {
		getAndroidVersion();
		return "json";
	}
	
	/**
	 * getIosVersion
	 * @return
	 * @throws Exception
	 */
	public String getIosVersion() throws Exception {
		version = VersionControl.getIosVer();
		dataMap.put("version", version);
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return null;
	}
	public String getIosVersionJson() throws Exception {
		getIosVersion();
		return "json";
	}
	
	/**
	 * getServerVersion
	 * @return
	 * @throws Exception
	 */
	public String getServerVersion() throws Exception {
		version = VersionControl.getServerVer();
		dataMap.put("version", version);
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return null;
	}
	public String getServerVersionJson() throws Exception {
		getServerVersion();
		return "json";
	}
	
	// Json Format Return 
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	/**
	 * Getter & Setter
	 * @return
	 */
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
