package com.bbcall.struts.actions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.table.AddressList;
import com.bbcall.mybatis.table.Orderlist;
import com.bbcall.struts.services.OrderlistServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("orderlistAction")
@SuppressWarnings("serial")
public class OrderlistAction extends ActionSupport {

	@Autowired
	private OrderlistServices orderlistServices;

	private Map<String, Object> dataMap;

	private String order_id;
	private String order_book_time;
	private String order_book_location;
	private String order_book_location_code;
	private String order_contact_mobile;
	private String order_contact_name;
	private String order_urgent;
	private String order_urgent_bonus;
	private String orderpicurl;
	private String order_description;
	private String order_price;
	private String user_id;
	private String order_master_name;
	private String order_status;
	private String order_type_code;
	private String order_score;
	private String order_evaluation;
	private String order_remark;
	private int addresscode;
	private String order_section;
	private String skilllist;
	private String locationlist;
	private String sortparm;
	private String order_type_list;
	private String offset;

	private List<String> orderFileFileName = new ArrayList<String>(); // 文件名

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	// 添加订单action
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public String add() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		System.out.println("add()");
		int result = 1;

		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();
		int book_location_code = Integer.parseInt(order_book_location_code);
		BigInteger contact_mobile = new BigInteger(order_contact_mobile);
		double urgent_bonus = 0;
		if (order_urgent_bonus != null && !order_urgent_bonus.equals("")) {
			urgent_bonus = Double.parseDouble(order_urgent_bonus);

			order_urgent = "true";
		} else {
			order_urgent = "false";
		}

		double price = 0;
		if (order_price != null && !order_price.equals("")) {
			price = Double.parseDouble(order_price);
		}
		int section = Integer.parseInt(order_section);
		int order_user_id = Integer.parseInt(user_id);

		String[] type_list = order_type_list.split(";");

		for (int i = 0; i < type_list.length; i++) {

			int type_code = Integer.parseInt(type_list[i]);

			result = orderlistServices.addOrder(order_book_time,
					order_book_location, book_location_code, contact_mobile,
					order_contact_name, order_urgent, urgent_bonus,
					orderpicurl, order_description, price, order_user_id,
					type_code, section);
		}

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("addResult", true);
			return "addordersuccess";
		} else {
			return "addorderfailed";
		}

	}

	public String addJson() throws Exception {
		System.out.println("addJson");
		add();
		return "json";
	}

	public String update() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		// Referdoc referdoclist = new Referdoc();

		int orderid = Integer.parseInt(order_id);
		int book_location_code = Integer.parseInt(order_book_location_code);
		BigInteger contact_mobile = new BigInteger(order_contact_mobile);
		double urgent_bonus = Double.parseDouble(order_urgent_bonus);
		double price = Double.parseDouble(order_price);
		int section = Integer.parseInt(order_section);
		int type_code = Integer.parseInt(order_type_code);
		int order_user_id = Integer.parseInt(user_id);

		int result = orderlistServices.updateOrder(orderid, order_book_time,
				order_book_location, book_location_code, contact_mobile,
				order_contact_name, order_urgent, urgent_bonus, orderpicurl,
				order_description, price, order_user_id, type_code,
				order_remark, section);

		if (result == ResultCode.SUCCESS) {
			Orderlist orderlist = orderlistServices.orderlistinfo();
			// referdocServices.getReferdoc(orderlist.getOrder_type_code());

			// referdoclist = referdocServices.referdocinfo();

			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("updateResult", true);
		}

		return SUCCESS;

	}

	public String updateJson() throws Exception {
		update();
		return "json";
	}

	public String deal() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int orderid = Integer.parseInt(order_id);
		int order_user_id = Integer.parseInt(user_id);

		int result = orderlistServices
				.ChangeOrderStatus(order_user_id, orderid);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("dealResult", true);
		}

		return SUCCESS;
	}

	public String dealJson() throws Exception {
		deal();
		return "json";
	}

	public String complete() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int orderid = Integer.parseInt(order_id);
		double score = 0;
		System.out.println("order_id in");
		if (order_score != null && !order_score.equals("")) {
			System.out.println("order_score in " + order_score);
			score = Double.parseDouble(order_score);

		}

		int result = orderlistServices.completeOrder(score, order_evaluation,
				orderid);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("completeResult", true);
		}

		return SUCCESS;
	}

	public String completeJson() throws Exception {
		complete();
		return "json";
	}

	public String unorderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int order_user_id = Integer.parseInt(user_id);
		int order_offset = Integer.parseInt(offset);

		int result = orderlistServices.getUnOrders(order_user_id, order_offset);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("unorderlistResult", true);
		}

		return SUCCESS;
	}

	public String unorderlistJson() throws Exception {
		unorderlist();
		return "json";
	}

	public String getorderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int order_user_id = Integer.parseInt(user_id);

		int order_offset = 0;

		if (offset != null) {
			order_offset = Integer.parseInt(offset);
		}

		int result = orderlistServices.getOrderlist(order_user_id,
				order_status, order_offset);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("unorderlistResult", true);
		}

		return SUCCESS;
	}

	public String getorderlistJson() throws Exception {
		getorderlist();
		return "json";
	}

	// 按照发布日期排序
	public String selectunorderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int order_user_id = Integer.parseInt(user_id);

		int order_offset = Integer.parseInt(offset);
		int result = orderlistServices.getUnOrderlist(skilllist, locationlist,
				order_user_id, order_offset);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("selectunorderlistResult", true);
		}

		return SUCCESS;
	}

	public String selectunorderlistJson() throws Exception {
		selectunorderlist();
		return "json";
	}

	// 按照截止日期排序
	public String selectunordersbybooktime() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int order_user_id = Integer.parseInt(user_id);

		int order_offset = Integer.parseInt(offset);
		int result = orderlistServices.getUnOrdersByBookTime(order_user_id,
				skilllist, locationlist, order_offset);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("selectunordersbybooktimeResult", true);
		}

		return SUCCESS;
	}

	public String selectunordersbybooktimeJson() throws Exception {
		selectunordersbybooktime();
		return "json";
	}

	public String selectproorderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int order_user_id = Integer.parseInt(user_id);

		int order_offset = Integer.parseInt(offset);
		int result = orderlistServices
				.getProOrders(order_user_id, order_offset);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("selectproorderlistResult", true);
		}

		return SUCCESS;
	}

	public String selectproorderlistJson() throws Exception {
		selectproorderlist();
		return "json";
	}

	public String selectcomorderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int order_user_id = Integer.parseInt(user_id);
		int order_offset = Integer.parseInt(offset);

		int result = orderlistServices
				.getComOrders(order_user_id, order_offset);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("selectComOrderlistResult", true);
		}

		return SUCCESS;
	}

	public String selectcomorderlistJson() throws Exception {
		selectcomorderlist();
		return "json";
	}

	public String select() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int orderid = Integer.parseInt(order_id);
		System.out.println("order_id " + order_id);
		int result = orderlistServices.getOrderById(orderid);
		System.out.println("get success");
		// Referdoc referdoclist = new Referdoc();

		if (result == ResultCode.SUCCESS) {
			Orderlist orderlist = orderlistServices.orderlistinfo();
			System.out.println(orderlist.getOrder_price());

			// referdocServices.getReferdoc(orderlist.getOrder_type_code());

			// referdoclist = referdocServices.referdocinfo();

			String[] url = null;
			String picurl = orderlist.getOrder_pic_url();

			if (picurl != null) {
				url = orderlist.getOrder_pic_url().split(";");
				for (int i = 0; i < url.length; i++) {
					orderFileFileName.add(url[i]);
				}
			}

			System.out.println("url");

			System.out.println("orderFileFileName");

			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
			dataMap.put("orderFileFileName", orderFileFileName);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("selectResult", true);
		}

		return "selectsuccess";
	}

	public String selectJson() throws Exception {
		select();
		return "json";
	}

	public String selectother() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int orderid = Integer.parseInt(order_id);
		int result = orderlistServices.getOrderById(orderid);
		// Referdoc referdoclist = new Referdoc();

		if (result == ResultCode.SUCCESS) {
			Orderlist orderlist = orderlistServices.orderlistinfo();

			// referdocServices.getReferdoc(orderlist.getOrder_type_code());

			// referdoclist = referdocServices.referdocinfo();

			String[] url = orderlist.getOrder_pic_url().split(";");

			for (int i = 0; i < url.length; i++) {
				orderFileFileName.add(url[i]);
			}

			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
			dataMap.put("orderFileFileName", orderFileFileName);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("selectResult", true);
		}

		return "selectother";
	}

	public String selectotherJson() throws Exception {
		selectother();
		return "json";
	}

	public String getwashorderlistasc() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices.getWashOrderlist(order_status,
				order_section,order_master_name);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("referdoclist", referdoclist);
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("getwashorderlistascResult", true);
		}

		return "getsuccess";
	}

	public String getwashorderlistascJson() throws Exception {
		getwashorderlistasc();
		return "json";
	}

	public String getwashorderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices.getWashOrderlist();
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("referdoclist", referdoclist);
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("getwashorderlistResult", true);
		}

		return "getsuccess";
	}

	public String getwashorderlistJson() throws Exception {
		getwashorderlist();
		return "json";
	}

	public String selectwashorderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int orderstatus = Integer.parseInt(order_status);
		int order_user_id = Integer.parseInt(user_id);

		int result = orderlistServices.selectWashOrderlist(orderstatus,
				order_user_id);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("referdoclist", referdoclist);
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("selectwashorderlistResult", true);
		}

		return "getsuccess";
	}

	public String selectwashorderlistJson() throws Exception {
		selectwashorderlist();
		return "json";
	}

	public String selectorderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int orderstatus = Integer.parseInt(order_status);
		int type_code = Integer.parseInt(order_type_code);
		int order_user_id = Integer.parseInt(user_id);
		int order_offset = Integer.parseInt(offset);

		int result = orderlistServices.selectOrderlist(orderstatus,
				order_user_id, type_code, order_offset);

		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("referdoclist", referdoclist);
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("selectorderlistResult", true);
		}

		return "getsuccess";
	}

	public String selectorderlistJson() throws Exception {
		selectorderlist();
		return "json";
	}

	public String delete() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int orderid = Integer.parseInt(order_id);
		int order_user_id = Integer.parseInt(user_id);
		int order_offset = Integer.parseInt(offset);
		int result = orderlistServices.deleteOrder(orderid, order_user_id,
				order_offset);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();
		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
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

	// checkParentAdsList Action
	public String checkChildAdsList() throws Exception {
		System.out.println("Here is UserAction.checkChildAdsList");
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		int result = orderlistServices.checkChildAdsList(addresscode);// 调用userServices.checkParentAdsList

		if (result == ResultCode.SUCCESS) {
			List<AddressList> addresslist = orderlistServices.getAddresslist();
			dataMap.put("addresslist", addresslist); // 把addresslist对象放入dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("checkChildAdsListResult", true); //
			// 放入checkUserNameResult
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("checkChildAdsListResult", false); //
			// 放入checkUserNameResult
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
		int result = orderlistServices.checkAdsList(addresscode);// 调用userServices.checkAdsList

		if (result == ResultCode.SUCCESS) {
			List<AddressList> addresslist = orderlistServices.getAddresslist();
			dataMap.put("addresslist", addresslist); // 把addresslist对象放入dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("checkAdsListResult", true); // 放入checkUserNameResult
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("checkAdsListResult", false); //
			// 放入checkUserNameResult
			System.out.println(dataMap);
		}
		return SUCCESS;
	}

	public String checkAdsListJson() throws Exception {
		System.out.println("Here is UserAction.checkAdsListJson");
		checkAdsList();
		return "json";
	}

	// // 拿到所有的订单类型进行展示
	// public String gettypelist() throws Exception {
	// dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	// dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
	//
	// int result = referdocServices.getReferdoclist();
	// if (result == ResultCode.SUCCESS) {
	// List<String> ordertypelist = new ArrayList<String>();
	//
	// List<Referdoc> referdocs = referdocServices.referdocinfos();
	// for (int i = 0; i < referdocs.size(); i++) {
	// String type = referdocs.get(i).getReferdoc_type();
	// ordertypelist.add(type);
	//
	// }
	// dataMap.put("ordertypelist", ordertypelist);
	// dataMap.put("resultcode", result);
	// dataMap.put("errmsg", ResultCode.getErrmsg(result));
	// dataMap.put("gettypelistResult", true);
	// }
	//
	// return SUCCESS;
	// }
	//
	// public String gettypelistJson() throws Exception {
	// gettypelist();
	// return "json";
	// }

	public String change() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int orderstatus = Integer.parseInt(order_status);
		int orderid = Integer.parseInt(order_id);
		int result = orderlistServices.change(orderid, orderstatus,
				order_remark);

		// Referdoc referdoclist = new Referdoc();

		if (result == ResultCode.SUCCESS) {
			Orderlist orderlist = orderlistServices.orderlistinfo();

			// referdocServices.getReferdoc(orderlist.getOrder_type_code());
			// referdoclist = referdocServices.referdocinfo();

			String[] url = orderlist.getOrder_pic_url().split(";");

			for (int i = 0; i < url.length; i++) {
				orderFileFileName.add(url[i]);
			}

			dataMap.put("orderlist", orderlist);
			// dataMap.put("referdoclist", referdoclist);
			dataMap.put("orderFileFileName", orderFileFileName);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("changeResult", true);
		}

		return "selectsuccess";
	}

	public String changeJson() throws Exception {
		change();
		return "json";
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setOrderlistServices(OrderlistServices orderlistServices) {
		this.orderlistServices = orderlistServices;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public void setOrder_book_location(String order_book_location) {
		this.order_book_location = order_book_location;
	}

	public void setOrder_contact_name(String order_contact_name) {
		this.order_contact_name = order_contact_name;
	}

	public void setOrder_urgent(String order_urgent) {
		this.order_urgent = order_urgent;
	}

	public void setOrder_description(String order_description) {
		this.order_description = order_description;
	}

	public void setSkilllist(String skilllist) {
		this.skilllist = skilllist;
	}

	public void setLocationlist(String locationlist) {
		this.locationlist = locationlist;
	}

	public OrderlistServices getOrderlistServices() {
		return orderlistServices;
	}

	public String getOrder_id() {
		return order_id;
	}

	public String getOrder_book_location() {
		return order_book_location;
	}

	public String getOrder_contact_name() {
		return order_contact_name;
	}

	public String getOrder_urgent() {
		return order_urgent;
	}

	public String getOrder_description() {
		return order_description;
	}

	public String getSkilllist() {
		return skilllist;
	}

	public String getLocationlist() {
		return locationlist;
	}

	public String getSortparm() {
		return sortparm;
	}

	public void setSortparm(String sortparm) {
		this.sortparm = sortparm;
	}

	public String getOrder_master_name() {
		return order_master_name;
	}

	public void setOrder_master_name(String order_master_name) {
		this.order_master_name = order_master_name;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getOrder_evaluation() {
		return order_evaluation;
	}

	public void setOrder_evaluation(String order_evaluation) {
		this.order_evaluation = order_evaluation;
	}

	//
	// public ReferdocServices getReferdocServices() {
	// return referdocServices;
	// }
	//
	// public void setReferdocServices(ReferdocServices referdocServices) {
	// this.referdocServices = referdocServices;
	// }

	public String getOrderpicurl() {
		return orderpicurl;
	}

	public void setOrderpicurl(String orderpicurl) {
		this.orderpicurl = orderpicurl;
	}

	public List<String> getOrderFileFileName() {
		return orderFileFileName;
	}

	public void setOrderFileFileName(List<String> orderFileFileName) {
		this.orderFileFileName = orderFileFileName;
	}

	public String getOrder_book_time() {
		return order_book_time;
	}

	public void setOrder_book_time(String order_book_time) {
		this.order_book_time = order_book_time;
	}

	public String getOrder_remark() {
		return order_remark;
	}

	public void setOrder_remark(String order_remark) {
		this.order_remark = order_remark;
	}

	public String getOrder_book_location_code() {
		return order_book_location_code;
	}

	public void setOrder_book_location_code(String order_book_location_code) {
		this.order_book_location_code = order_book_location_code;
	}

	public String getOrder_contact_mobile() {
		return order_contact_mobile;
	}

	public void setOrder_contact_mobile(String order_contact_mobile) {
		this.order_contact_mobile = order_contact_mobile;
	}

	public String getOrder_urgent_bonus() {
		return order_urgent_bonus;
	}

	public void setOrder_urgent_bonus(String order_urgent_bonus) {
		this.order_urgent_bonus = order_urgent_bonus;
	}

	public String getOrder_price() {
		return order_price;
	}

	public void setOrder_price(String order_price) {
		this.order_price = order_price;
	}

	public String getOrder_type_code() {
		return order_type_code;
	}

	public void setOrder_type_code(String order_type_code) {
		this.order_type_code = order_type_code;
	}

	public String getOrder_score() {
		return order_score;
	}

	public void setOrder_score(String order_score) {
		this.order_score = order_score;
	}

	public int getAddresscode() {
		return addresscode;
	}

	public void setAddresscode(int addresscode) {
		this.addresscode = addresscode;
	}

	public String getOrder_section() {
		return order_section;
	}

	public void setOrder_section(String order_section) {
		this.order_section = order_section;
	}

	public String getOrder_type_list() {
		return order_type_list;
	}

	public void setOrder_type_list(String order_type_list) {
		this.order_type_list = order_type_list;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

}
