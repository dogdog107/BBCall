package com.bbcall.struts.actions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ObjectToMap;
import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.table.AccessGroup;
import com.bbcall.mybatis.table.AccessList;
import com.bbcall.struts.services.AccessServices;
import com.opensymphony.xwork2.ActionSupport;


@Scope("prototype")
@Controller("accessAction")
@SuppressWarnings("serial")
public class AccessAction extends ActionSupport{
	
	@Autowired
	private AccessServices accessServices;
	private Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	private ObjectToMap obj2map = new ObjectToMap();// 新建ObjectToMap对象
	
	private AccessGroup accessGroupTemp;
	private List<AccessList> accessListTemp;
	private List<String> accessNameList;
	private List<String> accessGroupNameList;
	private List<AccessGroup> accessGroupList;
	
	private String accessgroup;
	
	/**
	 * getAccessList 获取现有权限列表
	 * @return
	 * @throws Exception
	 */
	public String getAccessNameList() throws Exception{
		accessNameList = accessServices.getAccessNameList();
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("accessNameList", accessNameList);
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String getAccessNameListJson() throws Exception{
		getAccessNameList();
		return "json";
	}
	
	/**
	 * getAccessList 获取现有权限列表
	 * @return
	 * @throws Exception
	 */
	public String getAccessList() throws Exception{
		accessListTemp = accessServices.getAccessList();
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("accessList", accessListTemp);
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String getAccessListJson() throws Exception{
		getAccessList();
		return "json";
	}
	
	/**
	 * getAccessGroup 获取现有权限组列表
	 * @return
	 * @throws Exception
	 */
	public String getAccessGroup() throws Exception{
		accessGroupList = accessServices.getAccessGroupName();
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("accessGroupList", accessGroupList);
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String getAccessGroupJson() throws Exception{
		getAccessGroup();
		return "json";
	}
	
	/**
	 * getAccessByAccessGroup
	 * 根据指定的权限组获取权限列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAccessByAccessGroup() throws Exception{
		
		int result = accessServices.getAccessByAccessGroup(accessgroup);
		if (result == ResultCode.SUCCESS) {
			dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
			dataMap = obj2map.getValueMap(accessServices.getAccessGroup()); //将对象转换成Map
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			return SUCCESS;
		} else {
			dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			return INPUT;
		}
	}
	public String getAccessByAccessGroupJson() throws Exception{
		getAccessByAccessGroup();
		return "json";
	}
	/**
	 * Getter & Setter
	 * @return
	 */
	// Json Format Return 
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public String getAccessgroup() {
		return accessgroup;
	}
	public void setAccessgroup(String accessgroup) {
		this.accessgroup = accessgroup;
	}
}
