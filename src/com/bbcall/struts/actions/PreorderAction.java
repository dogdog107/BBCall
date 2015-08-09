package com.bbcall.struts.actions;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.PageInfoToMap;
import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.table.Preorder;
import com.bbcall.struts.services.GcmServices;
import com.bbcall.struts.services.OrderlistServices;
import com.bbcall.struts.services.PreorderServices;
import com.bbcall.struts.services.UserServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("preorderAction")
@SuppressWarnings("serial")
public class PreorderAction extends ActionSupport {

	@Autowired
	private PreorderServices preorderServices;

	@Autowired
	private GcmServices gcmServices;

	@Autowired
	private OrderlistServices orderlistServices;

	@Autowired
	private UserServices userServices;

	private String preorder_id;
	private String preorder_master_account;
	private Timestamp preorder_create_time;
	private String preorder_price;
	private String preorder_order_id;
	private String preorder_master_id;
	private String preorder_master_name;
	private String preorder_master_pic;
	private String preorder_master_skill;
	private double preorder_master_grade;
	private Integer pagenum; // 页面页数
	private String sortparm;

	private Map<String, Object> dataMap;
	private PageInfoToMap pageinfo2map = new PageInfoToMap();// 新建PageInfoToMap对象

	public String add() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		System.out.println("inin");
		double price = 0;

		if (preorder_price != null && !preorder_price.equals("")) {
			price = Double.parseDouble(preorder_price);
		}

		System.out.println("preorder_price " + preorder_price);
		int preorderid = Integer.parseInt(preorder_order_id);
		int preordermasterid = Integer.parseInt(preorder_master_id);

		try {
			orderlistServices.getOrderById(preorderid);

			int user_id = orderlistServices.orderlistinfo().getOrder_user_id();

			userServices.getUserById(user_id);

			Integer drivetype = userServices.getUserinfo().getUser_driver();
			String registerid = userServices.getUserinfo().getUser_push_token();

			if (drivetype.equals(1)) {
				gcmServices.sendtouser("BBCall notification - New Master wants to apply your Order", registerid);
			} else if (drivetype.equals(2)) {
				// 苹果推送
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {

			int result = preorderServices.addPreorder(preordermasterid, price,
					preorderid, pagenum);

			if (result == ResultCode.SUCCESS) {
				List<Preorder> preorderlist = preorderServices.preorderinfos();

				dataMap.put("preorderlist", preorderlist);
				dataMap.putAll(pageinfo2map.pageInfoMap(preorderlist));// 把分页信息放进dataMap
				dataMap.putAll(Tools.JsonHeadMap(result, true));
				// dataMap.put("resultcode", result);
				// dataMap.put("errmsg", ResultCode.getErrmsg(result));
				// dataMap.put("addResult", true);
			} else {
				dataMap.putAll(Tools.JsonHeadMap(result, false));
			}
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

		int result = preorderServices.getPreorderBySortparm(preorderid,
				sortparm, pagenum);

		if (result == ResultCode.SUCCESS) {
			List<Preorder> preorderlist = preorderServices.preorderinfos();

			dataMap.put("preorderlist", preorderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(preorderlist));// 把分页信息放进dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("showbyidResult", true);
		}
		return SUCCESS;
	}

	public String showbyidJson() throws Exception {
		showbyid();
		return "json";
	}

	// public String showbyprice() throws Exception {
	// dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	// dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
	//
	// int preorderid = Integer.parseInt(preorder_order_id);
	//
	// int result = preorderServices.getPreorderAsc(preorderid);
	//
	// if (result == ResultCode.SUCCESS) {
	// List<Preorder> preorderlist = preorderServices.preorderinfos();
	//
	// dataMap.put("preorderlist", preorderlist);
	// dataMap.putAll(Tools.JsonHeadMap(result, true));
	// // dataMap.put("resultcode", result);
	// // dataMap.put("errmsg", ResultCode.getErrmsg(result));
	// // dataMap.put("showbyidResult", true);
	// }
	// return SUCCESS;
	// }
	//
	// public String showbypriceJson() throws Exception {
	// showbyprice();
	// return "json";
	// }

	public String showbyaccount() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int preordermasterid = Integer.parseInt(preorder_master_id);

		int result = preorderServices.getPreorderByAccount(preordermasterid,
				pagenum);

		if (result == ResultCode.SUCCESS) {
			List<Preorder> preorderlist = preorderServices.preorderinfos();

			dataMap.put("preorderlist", preorderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(preorderlist));// 把分页信息放进dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("showbyaccountResult", true);
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
		int preordermasterid = Integer.parseInt(preorder_master_id);

		int result = preorderServices.deletePreorder(preorderid,
				preordermasterid, pagenum);

		if (result == ResultCode.SUCCESS) {
			List<Preorder> preorderlist = preorderServices.preorderinfos();

			dataMap.put("preorderlist", preorderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(preorderlist));// 把分页信息放进dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("deleteResult", true);
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

	public String getPreorder_master_id() {
		return preorder_master_id;
	}

	public void setPreorder_master_id(String preorder_master_id) {
		this.preorder_master_id = preorder_master_id;
	}

	public String getPreorder_master_name() {
		return preorder_master_name;
	}

	public void setPreorder_master_name(String preorder_master_name) {
		this.preorder_master_name = preorder_master_name;
	}

	public String getPreorder_master_pic() {
		return preorder_master_pic;
	}

	public void setPreorder_master_pic(String preorder_master_pic) {
		this.preorder_master_pic = preorder_master_pic;
	}

	public String getPreorder_master_skill() {
		return preorder_master_skill;
	}

	public void setPreorder_master_skill(String preorder_master_skill) {
		this.preorder_master_skill = preorder_master_skill;
	}

	public double getPreorder_master_grade() {
		return preorder_master_grade;
	}

	public void setPreorder_master_grade(double preorder_master_grade) {
		this.preorder_master_grade = preorder_master_grade;
	}

	public Integer getPagenum() {
		return pagenum;
	}

	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}

	public String getSortparm() {
		return sortparm;
	}

	public void setSortparm(String sortparm) {
		this.sortparm = sortparm;
	}

	public GcmServices getGcmServices() {
		return gcmServices;
	}

	public void setGcmServices(GcmServices gcmServices) {
		this.gcmServices = gcmServices;
	}

	public OrderlistServices getOrderlistServices() {
		return orderlistServices;
	}

	public void setOrderlistServices(OrderlistServices orderlistServices) {
		this.orderlistServices = orderlistServices;
	}

	public UserServices getUserServices() {
		return userServices;
	}

	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

}
