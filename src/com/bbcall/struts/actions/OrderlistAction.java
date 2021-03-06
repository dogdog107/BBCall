package com.bbcall.struts.actions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.PageInfoToMap;
import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.table.Orderlist;
import com.bbcall.mybatis.table.User;
import com.bbcall.struts.services.DevicePushServices;
import com.bbcall.struts.services.GcmServices;
import com.bbcall.struts.services.IosPushServices;
import com.bbcall.struts.services.OrderlistServices;
import com.bbcall.struts.services.UserServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("orderlistAction")
@SuppressWarnings("serial")
public class OrderlistAction extends ActionSupport {

	private static Logger logger = Logger.getLogger(OrderlistAction.class);

	@Autowired
	private OrderlistServices orderlistServices;

	@Autowired
	private UserServices userServices;

	@Autowired
	private IosPushServices iosPushServices;

	@Autowired
	private GcmServices gcmServices;

	@Autowired
	private DevicePushServices devicePushServices;
	
	private Map<String, Object> dataMap;
	private PageInfoToMap pageinfo2map = new PageInfoToMap();// 新建PageInfoToMap对象

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
	private Integer pagenum; // 页面页数
	private String order_time;

	private String order_col;
	private String order_value;
	
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
					type_code, section, pagenum);
		}

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
			// dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("addResult", true);
			return "addordersuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
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

		try {
			userServices.getUserById(order_user_id);
			Integer drivetype = userServices.getUserinfo().getUser_driver();
			String registerid = userServices.getUserinfo().getUser_push_token();
			List<String> iosList = new ArrayList<String>();
			iosList.add(registerid);

//			if (drivetype.equals(1)) {
//				gcmServices
//						.sendtouser(
//								"BBCall notification - Your order request has been accepted",
//								registerid, orderid);
//			} else if (drivetype.equals(2)) {
//				// 苹果推送
//				iosPushServices
//						.iosPush(
//								iosList,
//								"BBCall notification - Your order request has been accepted",
//								2, orderid);
//			}
			
			if (drivetype != 0 && !Tools.isEmpty(registerid)) {
				// msgID = 1
				//default: BBCall notification - Your order request has been accepted.
				List<String> deviceTokens = new ArrayList<String>();
				deviceTokens.add(registerid);
				devicePushServices.devicePush(drivetype, deviceTokens, 1, userServices.getUserinfo().getUser_type(), orderid);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			int result = orderlistServices.ChangeOrderStatus(order_user_id,
					orderid, pagenum);

			if (result == ResultCode.SUCCESS) {
				List<Orderlist> orderlist = orderlistServices.orderlistinfos();
				// for (int j = 0; j < orderlist.size(); j++) {
				// referdocServices.getReferdoc(orderlist.get(j)
				// .getOrder_type_code());
				// referdoclist.add(referdocServices.referdocinfo());
				// }
				dataMap.put("orderlist", orderlist);
				dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
				// dataMap.put("referdoclist", referdoclist);
				dataMap.putAll(Tools.JsonHeadMap(result, true));
				// dataMap.put("resultcode", result);
				// dataMap.put("errmsg", ResultCode.getErrmsg(result));
				// dataMap.put("dealResult", true);
			}
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

		orderlistServices.getOrderById(orderid);
		Orderlist order = orderlistServices.orderlistinfo();

		try {
			int master_id = order.getOrder_master_id();

			userServices.getUserById(master_id);

			Integer drivetype = userServices.getUserinfo().getUser_driver();
			String registerid = userServices.getUserinfo().getUser_push_token();
			List<String> iosList = new ArrayList<String>();
			iosList.add(registerid);

//			if (drivetype.equals(1)) {
//				gcmServices.sendtouser(
//						"BBCall notification - You have new completed Order",
//						registerid, orderid);
//			} else if (drivetype.equals(2)) {
//				// 苹果推送
//				iosPushServices
//						.iosPush(
//								iosList,
//								"BBCall notification - You have new completed Order",
//								2, orderid);
//			}

			if (drivetype != 0 && !Tools.isEmpty(registerid)) {
				// msgID = 2
				// default: BBCall notification - You have new completed Order.
				List<String> deviceTokens = new ArrayList<String>();
				deviceTokens.add(registerid);
				devicePushServices.devicePush(drivetype, deviceTokens, 2, userServices.getUserinfo().getUser_type(), orderid);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			int result = orderlistServices.completeOrder(score,
					order_evaluation, orderid, pagenum);
			// List<Referdoc> referdoclist = new ArrayList<Referdoc>();
			logger.info("gradeOpr:[Tradecomplete][User ID: "
					+ order.getOrder_user_id() + "]Order ID: " + order_id
					+ "; Master name: " + order.getOrder_master_name()
					+ "; Order type: " + order.getOrder_type()
					+ "; Order price: " + order.getOrder_price()
					+ "; Order Score: " + order_score + "; Order Evaluation: "
					+ order_evaluation);

			if (result == ResultCode.SUCCESS) {
				List<Orderlist> orderlist = orderlistServices.orderlistinfos();
				// for (int j = 0; j < orderlist.size(); j++) {
				// referdocServices.getReferdoc(orderlist.get(j)
				// .getOrder_type_code());
				// referdoclist.add(referdocServices.referdocinfo());
				// }
				dataMap.put("orderlist", orderlist);
				dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
				// dataMap.put("referdoclist", referdoclist);
				dataMap.putAll(Tools.JsonHeadMap(result, true));
				// dataMap.put("resultcode", result);
				// dataMap.put("errmsg", ResultCode.getErrmsg(result));
				// dataMap.put("completeResult", true);
			}
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

		int result = 1;

		int order_user_id = Integer.parseInt(user_id);

		userServices.getUserById(order_user_id);

		User tempuUser = userServices.getUserinfo();

		Integer usertype = tempuUser.getUser_type();

		if (usertype.equals(1) || usertype.equals(5)) {
			result = orderlistServices.getUnOrdersForCustomer(order_user_id,
					pagenum);
		} else if (usertype.equals(2)) {
			result = orderlistServices.getUnOrdersForMaster(order_user_id,
					pagenum);
		} else if (usertype.equals(3) || usertype.equals(4)) {
			result = orderlistServices.getUnOrdersForAdm(pagenum);
		} else {
			result = ResultCode.ORDER_READ_FAIL;
		}

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
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

	public String sortunorderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = 1;

		int order_master_id = Integer.parseInt(user_id);
		int order_sort_status = Integer.parseInt(order_status);

		result = orderlistServices.getUnOrdersForMaster2(order_master_id,
				order_sort_status, pagenum);

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
			// dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("unorderlistResult", true);
		}

		return SUCCESS;
	}

	public String sortunorderlistJson() throws Exception {
		sortunorderlist();
		return "json";
	}

	// 页面
	public String getorderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices.sortOrderlist(order_status,
				order_master_name, order_book_location_code, order_id,
				order_time, order_col, order_value, pagenum);

		// int status = 1;
		// if (order_status != null && !order_status.equals("")) {
		// status = Integer.parseInt(order_status);
		// }
		//
		// int result = 1;
		//
		// switch (status) {
		// case 1:
		// result = orderlistServices.getOrderlist1(pagenum);
		// break;
		// case 2:
		// result = orderlistServices.getOrderlist2(pagenum);
		// break;
		// case 3:
		// result = orderlistServices.getOrderlist3(pagenum);
		// break;
		// case 4:
		// result = orderlistServices.getOrderlist4(pagenum);
		// break;
		// case 5:
		// result = orderlistServices.getOrderlist5(pagenum);
		// break;
		// case 6:
		// result = orderlistServices.getOrderlist6(pagenum);
		// break;
		// case 7:
		// result = orderlistServices.getOrderlist7(pagenum);
		// break;
		// default:
		// result = orderlistServices.getOrderlist1(pagenum);
		// break;
		// }
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
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
		
		int result = orderlistServices.getUnOrderlist(skilllist, locationlist,
				order_user_id, order_status, sortparm, pagenum);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
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
	// public String selectunordersbybooktime() throws Exception {
	// dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	// dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
	//
	// int order_user_id = Integer.parseInt(user_id);
	//
	// int result = orderlistServices.getUnOrdersByBookTime(order_user_id,
	// skilllist, locationlist, pagenum);
	// // List<Referdoc> referdoclist = new ArrayList<Referdoc>();
	//
	// if (result == ResultCode.SUCCESS) {
	// List<Orderlist> orderlist = orderlistServices.orderlistinfos();
	//
	// // for (int j = 0; j < orderlist.size(); j++) {
	// // referdocServices.getReferdoc(orderlist.get(j)
	// // .getOrder_type_code());
	// // referdoclist.add(referdocServices.referdocinfo());
	// // }
	// dataMap.put("orderlist", orderlist);
	// dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
	// // dataMap.put("referdoclist", referdoclist);
	// dataMap.putAll(Tools.JsonHeadMap(result, true));
	// // dataMap.put("resultcode", result);
	// // dataMap.put("errmsg", ResultCode.getErrmsg(result));
	// // dataMap.put("selectunordersbybooktimeResult", true);
	// }
	//
	// return SUCCESS;
	// }
	//
	// public String selectunordersbybooktimeJson() throws Exception {
	// selectunordersbybooktime();
	// return "json";
	// }

	public String selectproorderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = 1;

		int order_user_id = Integer.parseInt(user_id);

		userServices.getUserById(order_user_id);

		User tempuUser = userServices.getUserinfo();

		Integer usertype = tempuUser.getUser_type();

		if (usertype.equals(1) || usertype.equals(5)) {
			result = orderlistServices.getProOrdersForCustomer(order_user_id,
					pagenum);
		} else if (usertype.equals(2)) {
			result = orderlistServices.getProOrdersForMaster(order_user_id,
					pagenum);
		} else if (usertype.equals(3) || usertype.equals(4)) {
			result = orderlistServices.getProOrdersForAdm(pagenum);
		} else {
			result = ResultCode.ORDER_READ_FAIL;
		}

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
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

		int result = 1;

		int order_user_id = Integer.parseInt(user_id);

		userServices.getUserById(order_user_id);

		User tempuUser = userServices.getUserinfo();

		Integer usertype = tempuUser.getUser_type();

		if (usertype.equals(1) || usertype.equals(5)) {
			result = orderlistServices.getComOrdersForCustomer(order_user_id,
					pagenum);
		} else if (usertype.equals(2)) {
			result = orderlistServices.getComOrdersForMaster(order_user_id,
					pagenum);
		} else if (usertype.equals(3) || usertype.equals(4)) {
			result = orderlistServices.getComOrdersForAdm(pagenum);
		} else {
			result = ResultCode.ORDER_READ_FAIL;
		}

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
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

			String[] url = null;
			String picurl = orderlist.getOrder_pic_url();
			if (picurl != null) {
				url = orderlist.getOrder_pic_url().split(",");
				for (int i = 0; i < url.length; i++) {
					orderFileFileName.add(url[i]);
				}
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
		//
		// if(order_book_location_code == "0" ||
		// order_book_location_code.equals("0")) {
		// order_book_location_code = null;
		// }

		int result = orderlistServices.getWashOrderlist(order_status,
				order_section, order_master_name, order_book_location_code,
				order_id, order_time, order_col, order_value, pagenum);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
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

		int result = orderlistServices.getWashOrderlist(pagenum);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
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
				order_user_id, pagenum);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
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

		int order_user_id = Integer.parseInt(user_id);

		int result = orderlistServices.selectOrderlist(order_status,
				order_user_id, order_type_code, pagenum);

		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
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
		int result = orderlistServices.deleteOrder(orderid, order_user_id,
				pagenum);
		// List<Referdoc> referdoclist = new ArrayList<Referdoc>();
		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			// for (int j = 0; j < orderlist.size(); j++) {
			// referdocServices.getReferdoc(orderlist.get(j)
			// .getOrder_type_code());
			// referdoclist.add(referdocServices.referdocinfo());
			// }
			dataMap.put("orderlist", orderlist);
			dataMap.putAll(pageinfo2map.pageInfoMap(orderlist));// 把分页信息放进dataMap
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
	// public String checkChildAdsList() throws Exception {
	// System.out.println("Here is UserAction.checkChildAdsList");
	// dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
	// int result = orderlistServices.checkChildAdsList(addresscode);//
	// 调用userServices.checkParentAdsList
	//
	// if (result == ResultCode.SUCCESS) {
	// List<AddressList> addresslist = orderlistServices.getAddresslist();
	// dataMap.put("addresslist", addresslist); // 把addresslist对象放入dataMap
	// dataMap.putAll(Tools.JsonHeadMap(result, true));
	// // dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
	// // dataMap.put("errmsg", ResultCode.getErrmsg(result));
	// // dataMap.put("checkChildAdsListResult", true); //
	// // 放入checkUserNameResult
	// } else {
	// dataMap.putAll(Tools.JsonHeadMap(result, true));
	// // dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
	// // dataMap.put("errmsg", ResultCode.getErrmsg(result));
	// // dataMap.put("checkChildAdsListResult", false); //
	// // 放入checkUserNameResult
	// System.out.println(dataMap);
	// }
	// return SUCCESS;
	// }
	//
	// public String checkChildAdsListJson() throws Exception {
	// System.out.println("Here is UserAction.checkChildAdsListJson");
	// checkChildAdsList();
	// return "json";
	// }
	//
	// // checkAdsList Action
	// public String checkAdsList() throws Exception {
	// System.out.println("Here is UserAction.checkAdsList");
	// dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
	// int result = orderlistServices.checkAdsList(addresscode);//
	// 调用userServices.checkAdsList
	//
	// if (result == ResultCode.SUCCESS) {
	// List<AddressList> addresslist = orderlistServices.getAddresslist();
	// dataMap.put("addresslist", addresslist); // 把addresslist对象放入dataMap
	// dataMap.putAll(Tools.JsonHeadMap(result, true));
	// // dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
	// // dataMap.put("errmsg", ResultCode.getErrmsg(result));
	// // dataMap.put("checkAdsListResult", true); // 放入checkUserNameResult
	// } else {
	// dataMap.putAll(Tools.JsonHeadMap(result, true));
	// // dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
	// // dataMap.put("errmsg", ResultCode.getErrmsg(result));
	// // dataMap.put("checkAdsListResult", false); //
	// // 放入checkUserNameResult
	// System.out.println(dataMap);
	// }
	// return SUCCESS;
	// }
	//
	// public String checkAdsListJson() throws Exception {
	// System.out.println("Here is UserAction.checkAdsListJson");
	// checkAdsList();
	// return "json";
	// }

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
				order_remark, null);

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

	public Integer getPagenum() {
		return pagenum;
	}

	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}

	public UserServices getUserServices() {
		return userServices;
	}

	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

	public GcmServices getGcmServices() {
		return gcmServices;
	}

	public void setGcmServices(GcmServices gcmServices) {
		this.gcmServices = gcmServices;
	}

	public IosPushServices getIosPushServices() {
		return iosPushServices;
	}

	public void setIosPushServices(IosPushServices iosPushServices) {
		this.iosPushServices = iosPushServices;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public String getOrder_col() {
		return order_col;
	}

	public void setOrder_col(String order_col) {
		this.order_col = order_col;
	}

	public String getOrder_value() {
		return order_value;
	}

	public void setOrder_value(String order_value) {
		this.order_value = order_value;
	}

}
