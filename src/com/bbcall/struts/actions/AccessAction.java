package com.bbcall.struts.actions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ObjectToMap;
import com.bbcall.functions.PageInfoToMap;
import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.table.AccessGroup;
import com.bbcall.mybatis.table.AccessList;
import com.bbcall.struts.services.AccessServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("accessAction")
@SuppressWarnings("serial")
public class AccessAction extends ActionSupport {

	@Autowired
	private AccessServices accessServices;
	private Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	private PageInfoToMap pageinfo2map = new PageInfoToMap();// 新建PageInfoToMap对象
	private ObjectToMap obj2map = new ObjectToMap();// 新建ObjectToMap对象

	private AccessGroup accessGroupTemp;
	private List<AccessList> accessListTemp;
	private List<String> accessNameList;
	private List<String> accessGroupNameList;
	private List<AccessGroup> accessGroupList;

	private Integer list1[];
	private String accessgroup_name;
	private String accessgroup_description;
	private Integer accessgroup_id;

	// page related parameters
	private Integer pagenum; // 页面页数
	
	public String checkAccessGroupByName() throws Exception {
		int result = accessServices.checkAccessGroupByName(accessgroup_name);
		if (result == ResultCode.SUCCESS) { // 權限組名字可以使用
			dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			return SUCCESS;
		} else {
			dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			return INPUT;
		}
	}
	public String checkAccessGroupByNameJson() throws Exception {
		checkAccessGroupByName();
		return "json";
	}
	
	/**
	 * addAccessGroup 增加权限组
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addAccessGroup() throws Exception {
		int result = accessServices.addAccessGroup(accessgroup_name, accessgroup_description);
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		if (result == ResultCode.SUCCESS) {
			dataMap.put("addresult", true);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			return "addAccessGroupSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			return "addAccessGroupFailed";
		}
	}
	public String addAccessGroupJson() throws Exception {
		addAccessGroup();
		return "json";
	}
	public String showAddAccessGroupPage() throws Exception {
		return "showAddAccessGroupPage";
	}
	/**
	 * updateAccessByAccessGroup 修改权限组的权限
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateAccessByAccessGroup() throws Exception {
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		if (accessgroup_id == null || list1.length <= 0){ // 检测参数是否为空
			dataMap.putAll(Tools.JsonHeadMap(ResultCode.REQUIREINFO_NOTENOUGH, false));
			System.out.println(dataMap);
			return INPUT;
		}
		System.out.println("accessgroup ID:" + accessgroup_id);
		System.out.println("list1 Length:" + list1.length);
		accessServices.deleteAllAccessUnderGroupId(accessgroup_id);
		int result = accessServices.addAccessByAccessGroup(accessgroup_id, list1);
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			return "updateAccessByAccessGroupSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			return "updateAccessByAccessGroupFailed";
		}
	}
	public String updateAccessByAccessGroupJson() throws Exception {
		updateAccessByAccessGroup();
		return "json";
	}
	
	/**
	 * getAccessList 获取现有权限列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAccessNameList() throws Exception {
		accessNameList = accessServices.getAccessNameList();
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("accessNameList", accessNameList);
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String getAccessNameListJson() throws Exception {
		getAccessNameList();
		return "json";
	}

	/**
	 * getAccessList 获取现有权限列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAccessList() throws Exception {
		accessListTemp = accessServices.getAccessList();
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("accesslist", accessListTemp);
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String getAccessListJson() throws Exception {
		getAccessList();
		return "json";
	}

	/**
	 * getAccessGroup 获取现有权限组列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAccessGroup() throws Exception {
		accessGroupList = accessServices.getAccessGroupName(pagenum);
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("accessGroupList", accessGroupList);
		dataMap.putAll(pageinfo2map.pageInfoMap(accessGroupList));// 把分页信息放进dataMap
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String getAccessGroupJson() throws Exception {
		getAccessGroup();
		return "json";
	}

	/**
	 * getAccessByAccessGroup 根据指定的权限组获取权限列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAccessByAccessGroup() throws Exception {
		int result = accessServices.getAccessByAccessGroupName(accessgroup_name);
		if (result == ResultCode.SUCCESS) {
			accessgroup_name = accessServices.getAccessGroup().getAccessgroup_name();
			accessgroup_id = accessServices.getAccessGroup().getAccessgroup_id();
			dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
			dataMap = obj2map.getValueMap(accessServices.getAccessGroup()); // 将对象转换成Map
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			return SUCCESS;
		} else {
			dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			return INPUT;
		}
	}
	public String getAccessByAccessGroupJson() throws Exception {
		getAccessByAccessGroup();
		return "json";
	}
	public String showAccessGroupDetailsPage() throws Exception {
		return "showAccessGroupDetailsPage";
	}
	public String showEditAccessGroupPage() throws Exception {
		return "showEditAccessGroupPage";
	}
	/**
	 * deleteAccessGroupById 刪除權限組
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteAccessGroupById() throws Exception {
		int result = accessServices.deleteAccessGroupById(accessgroup_id);
		if (result == ResultCode.SUCCESS) {
			dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			return SUCCESS;
		} else {
			dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			return INPUT;
		}
	}
	public String deleteAccessGroupByIdJson() throws Exception {
		deleteAccessGroupById();
		return "json";
	}

	/**
	 * Getter & Setter
	 * 
	 * @return
	 */
	// Json Format Return
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public Integer getPagenum() {
		return pagenum;
	}
	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}
	public String getAccessgroup_name() {
		return accessgroup_name;
	}
	public void setAccessgroup_name(String accessgroup_name) {
		this.accessgroup_name = accessgroup_name;
	}
	public Integer getAccessgroup_id() {
		return accessgroup_id;
	}
	public void setAccessgroup_id(Integer accessgroup_id) {
		this.accessgroup_id = accessgroup_id;
	}
	public String getAccessgroup_description() {
		return accessgroup_description;
	}
	public void setAccessgroup_description(String accessgroup_description) {
		this.accessgroup_description = accessgroup_description;
	}
	public Integer[] getList1() {
		return list1;
	}
	public void setList1(Integer[] list1) {
		this.list1 = list1;
	}
}
