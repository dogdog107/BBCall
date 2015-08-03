package com.bbcall.struts.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.PageInfoToMap;
import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.table.Blacklist;
import com.bbcall.mybatis.table.User;
import com.bbcall.struts.services.BlacklistServices;
import com.bbcall.struts.services.UserServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("blacklistAction")
@SuppressWarnings("serial")
public class BlacklistAction extends ActionSupport {

	@Autowired
	private BlacklistServices blacklistServices;

	@Autowired
	private UserServices userServices;

	private String blacklist_id;
	private String blacklist_user_id;
	private String blacklist_master_id;
	private String blacklist_order_id;
	private String user_id;
	private Integer pagenum; // 页面页数

	private Map<String, Object> dataMap;
	private PageInfoToMap pageinfo2map = new PageInfoToMap();// 新建PageInfoToMap对象
	private static Logger logger = Logger.getLogger(BlacklistAction.class);  
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	// 添加黑名单action
	public String insert() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int user_id = Integer.parseInt(blacklist_user_id);
		int master_id = Integer.parseInt(blacklist_master_id);
		int order_id = Integer.parseInt(blacklist_order_id);

		int result = blacklistServices.addBlacklist(user_id, master_id,
				order_id, pagenum);

		if (result == ResultCode.SUCCESS) {
			List<Blacklist> blacklists = blacklistServices.blacklistinfos();
			dataMap.put("blacklists", blacklists);
			dataMap.putAll(pageinfo2map.pageInfoMap(blacklists));// 把分页信息放进dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("insertResult", true);
			// System.out.println(dataMap);
			logger.info("userOpr:[insert]" + dataMap);  
			return SUCCESS;
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			logger.info("userOpr:[insert]" + dataMap);  
			return "insertfail";
		}

		
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

		int user_id = Integer.parseInt(blacklist_user_id);
		int black_id = Integer.parseInt(blacklist_id);

		int result = blacklistServices.deleteBlacklist(black_id, user_id,
				pagenum);

		if (result == ResultCode.SUCCESS) {
			List<Blacklist> blacklists = blacklistServices.blacklistinfos();
			dataMap.put("blacklists", blacklists);
			dataMap.putAll(pageinfo2map.pageInfoMap(blacklists));// 把分页信息放进dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			logger.info("userOpr:[delete]" + dataMap);  
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("deleteResult", true);
			// System.out.println(dataMap);
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

		int result = 1;

		int userid = Integer.parseInt(user_id);

		userServices.getUserById(userid);

		User tempuUser = userServices.getUserinfo();

		Integer usertype = tempuUser.getUser_type();

		if (usertype.equals(1)) {
			result = blacklistServices.getBlacklistForCustomer(userid, pagenum);
		} else if (usertype.equals(2)) {
			result = blacklistServices.getBlacklistForMaster(userid, pagenum);
		} else {
			result = blacklistServices.getBlacklistForAdm(pagenum);
		}
		
//		if (blacklist_user_id != null) {
//			int user_id = Integer.parseInt(blacklist_user_id);
//			result = blacklistServices.getBlacklist(user_id);
//		} else {
//			int master_id = Integer.parseInt(blacklist_master_id);
//			result = blacklistServices.getBlacklist(master_id);
//		}

		if (result == ResultCode.SUCCESS) {
			List<Blacklist> blacklists = blacklistServices.blacklistinfos();
			dataMap.put("blacklists", blacklists);
			dataMap.putAll(pageinfo2map.pageInfoMap(blacklists));// 把分页信息放进dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("getBlacklistResult", true);
			// System.out.println(dataMap);
			logger.info("userOpr:[getBlacklist]" + dataMap);  
			
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

		int bk_id = Integer.parseInt(blacklist_id);

		int result = blacklistServices.getBlacklistById(bk_id);

		if (result == ResultCode.SUCCESS) {
			Blacklist blacklists = blacklistServices.blacklistinfo();
			dataMap.put("blacklists", blacklists);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
//			dataMap.put("resultcode", result);
//			dataMap.put("errmsg", ResultCode.getErrmsg(result));
//			dataMap.put("getBlacklistByIdResult", true);
//			System.out.println(dataMap);
			logger.info("userOpr:[getBlacklistById]" + dataMap);  
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

	public String getBlacklist_id() {
		return blacklist_id;
	}

	public void setBlacklist_id(String blacklist_id) {
		this.blacklist_id = blacklist_id;
	}

	public String getBlacklist_user_id() {
		return blacklist_user_id;
	}

	public void setBlacklist_user_id(String blacklist_user_id) {
		this.blacklist_user_id = blacklist_user_id;
	}

	public String getBlacklist_master_id() {
		return blacklist_master_id;
	}

	public void setBlacklist_master_id(String blacklist_master_id) {
		this.blacklist_master_id = blacklist_master_id;
	}

	public String getBlacklist_order_id() {
		return blacklist_order_id;
	}

	public void setBlacklist_order_id(String blacklist_order_id) {
		this.blacklist_order_id = blacklist_order_id;
	}

	public BlacklistServices getBlacklistServices() {
		return blacklistServices;
	}

	public Integer getPagenum() {
		return pagenum;
	}

	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public UserServices getUserServices() {
		return userServices;
	}

	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

}
