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
import com.bbcall.mybatis.table.AddressList;
import com.bbcall.mybatis.table.Orderlist;
import com.bbcall.mybatis.table.Referdoc;
import com.bbcall.struts.services.OrderlistServices;
import com.bbcall.struts.services.ReferdocServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("orderlistAction")
@SuppressWarnings("serial")
public class OrderlistAction extends ActionSupport {

	@Autowired
	private OrderlistServices orderlistServices;

	@Autowired
	private ReferdocServices referdocServices;

	private Map<String, Object> dataMap;

	private int order_id;
	private String order_book_time;
	private String order_book_location;
	private int order_book_location_code;
	private BigInteger order_contact_mobile;
	private String order_contact_name;
	private String order_urgent;
	private double order_urgent_bonus;
	private String orderpicurl;
	private String order_description;
	private double order_price;
	private String user_account;
	private String order_master_account;
	private int order_status;
	private int order_type_code;
	private int order_score;
	private String order_evaluation;
	private int addresscode;
	private List<String> skilllist;
	private List<String> locationlist;
	private String sortparm;
	private List<Integer> order_type_list;

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

		int result = 1;
		List<Referdoc> referdoclist = new ArrayList<Referdoc>();
		
		for (int i = 0; i < order_type_list.size(); i++) {

			result = orderlistServices.addOrder(order_book_time,
					order_book_location, order_book_location_code,
					order_contact_mobile, order_contact_name, order_urgent,
					order_urgent_bonus, orderpicurl, order_description,
					order_price, user_account, order_type_list.get(i));
		}

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("addResult", true);
			return "addordersuccess";
		} else {
			return "addorderfailed";
		}

	}

	public String addJson() throws Exception {
		add();
		return "json";
	}

	public String update() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		Referdoc referdoclist = new Referdoc();

		int result = orderlistServices.updateOrder(order_id, order_book_time,
				order_book_location, order_book_location_code,
				order_contact_mobile, order_contact_name, order_urgent,
				order_urgent_bonus, orderpicurl, order_description,
				order_price, user_account, order_type_code);

		if (result == ResultCode.SUCCESS) {
			Orderlist orderlist = orderlistServices.orderlistinfo();
			referdocServices.getReferdoc(orderlist.getOrder_type_code());

			referdoclist = referdocServices.referdocinfo();

			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("updateResult", true);
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

		int result = orderlistServices
				.ChangeOrderStatus(user_account, order_id);
		List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("dealResult", true);
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

		int result = orderlistServices.completeOrder(order_score,
				order_evaluation, order_id);
		List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("completeResult", true);
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

		int result = orderlistServices.getUnOrders(user_account);
		List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("unorderlistResult", true);
		}

		return SUCCESS;
	}

	public String unorderlistJson() throws Exception {
		unorderlist();
		return "json";
	}

	// 按照发布日期排序
	public String selectunorderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices.getUnOrders(skilllist, locationlist,
				user_account);
		List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("selectunorderlistResult", true);
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

		int result = orderlistServices.getUnOrdersByBookTime(user_account,
				skilllist, locationlist);
		List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("selectunordersbybooktimeResult", true);
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

		int result = orderlistServices.getProOrders(user_account);
		List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("selectproorderlistResult", true);
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

		int result = orderlistServices.getComOrders(user_account);
		List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("selectComOrderlistResult", true);
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

		int result = orderlistServices.getOrderById(order_id);
		Referdoc referdoclist = new Referdoc();

		if (result == ResultCode.SUCCESS) {
			Orderlist orderlist = orderlistServices.orderlistinfo();

			referdocServices.getReferdoc(orderlist.getOrder_type_code());

			referdoclist = referdocServices.referdocinfo();

			String[] url = orderlist.getOrder_pic_url().split(";");

			for (int i = 0; i < url.length; i++) {
				orderFileFileName.add(url[i]);
			}

			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("orderFileFileName", orderFileFileName);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("selectResult", true);
		}

		return SUCCESS;
	}

	public String selectJson() throws Exception {
		select();
		return "json";
	}

	public String getwashorderlistasc() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices.getWashOrderlist(sortparm);
		List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("getwashorderlistascResult", true);
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
		List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("getwashorderlistResult", true);
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

		int result = orderlistServices.selectWashOrderlist(order_status,
				order_master_account);
		List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("selectwashorderlistResult", true);
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

		int result = orderlistServices.selectOrderlist(order_status,
				order_master_account, order_type_code);

		List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();

			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("selectorderlistResult", true);
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

		int result = orderlistServices.deleteOrder(order_id, user_account);
		List<Referdoc> referdoclist = new ArrayList<Referdoc>();
		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("deleteResult", true);
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
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkChildAdsListResult", true); // 放入checkUserNameResult
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkChildAdsListResult", false); // 放入checkUserNameResult
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
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkAdsListResult", true); // 放入checkUserNameResult
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("checkAdsListResult", false); // 放入checkUserNameResult
			System.out.println(dataMap);
		}
		return SUCCESS;
	}

	public String checkAdsListJson() throws Exception {
		System.out.println("Here is UserAction.checkAdsListJson");
		checkAdsList();
		return "json";
	}

	// 拿到所有的订单类型进行展示
	public String gettypelist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = referdocServices.getReferdoclist();
		if (result == ResultCode.SUCCESS) {
			List<String> ordertypelist = new ArrayList<String>();

			List<Referdoc> referdocs = referdocServices.referdocinfos();
			for (int i = 0; i < referdocs.size(); i++) {
				String type = referdocs.get(i).getReferdoc_type();
				ordertypelist.add(type);

			}
			dataMap.put("ordertypelist", ordertypelist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("gettypelistResult", true);
		}

		return SUCCESS;
	}

	public String gettypelistJson() throws Exception {
		gettypelist();
		return "json";
	}

	public String change() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices.change(order_id, order_status,
				order_description);

		List<Referdoc> referdoclist = new ArrayList<Referdoc>();

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			for (int j = 0; j < orderlist.size(); j++) {
				referdocServices.getReferdoc(orderlist.get(j)
						.getOrder_type_code());
				referdoclist.add(referdocServices.referdocinfo());
			}
			dataMap.put("orderlist", orderlist);
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("changeResult", true);
		}

		return SUCCESS;
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

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public void setOrder_book_location(String order_book_location) {
		this.order_book_location = order_book_location;
	}

	public void setOrder_contact_mobile(BigInteger order_contact_mobile) {
		this.order_contact_mobile = order_contact_mobile;
	}

	public void setOrder_contact_name(String order_contact_name) {
		this.order_contact_name = order_contact_name;
	}

	public void setOrder_urgent(String order_urgent) {
		this.order_urgent = order_urgent;
	}

	public void setOrder_urgent_bonus(double order_urgent_bonus) {
		this.order_urgent_bonus = order_urgent_bonus;
	}

	public void setOrder_description(String order_description) {
		this.order_description = order_description;
	}

	public void setOrder_price(double order_price) {
		this.order_price = order_price;
	}

	public void setOrder_type_code(int order_type_code) {
		this.order_type_code = order_type_code;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public void setOrder_book_location_code(int order_book_location_code) {
		this.order_book_location_code = order_book_location_code;
	}

	public void setSkilllist(List<String> skilllist) {
		this.skilllist = skilllist;
	}

	public void setLocationlist(List<String> locationlist) {
		this.locationlist = locationlist;
	}

	public OrderlistServices getOrderlistServices() {
		return orderlistServices;
	}

	public int getOrder_id() {
		return order_id;
	}

	public String getOrder_book_location() {
		return order_book_location;
	}

	public int getOrder_book_location_code() {
		return order_book_location_code;
	}

	public BigInteger getOrder_contact_mobile() {
		return order_contact_mobile;
	}

	public String getOrder_contact_name() {
		return order_contact_name;
	}

	public String getOrder_urgent() {
		return order_urgent;
	}

	public double getOrder_urgent_bonus() {
		return order_urgent_bonus;
	}

	public String getOrder_description() {
		return order_description;
	}

	public double getOrder_price() {
		return order_price;
	}

	public String getUser_account() {
		return user_account;
	}

	public int getOrder_type_code() {
		return order_type_code;
	}

	public List<String> getSkilllist() {
		return skilllist;
	}

	public List<String> getLocationlist() {
		return locationlist;
	}

	public int getAddresscode() {
		return addresscode;
	}

	public void setAddresscode(int addresscode) {
		this.addresscode = addresscode;
	}

	public String getSortparm() {
		return sortparm;
	}

	public void setSortparm(String sortparm) {
		this.sortparm = sortparm;
	}

	public String getOrder_master_account() {
		return order_master_account;
	}

	public void setOrder_master_account(String order_master_account) {
		this.order_master_account = order_master_account;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public List<Integer> getOrder_type_list() {
		return order_type_list;
	}

	public void setOrder_type_list(List<Integer> order_type_list) {
		this.order_type_list = order_type_list;
	}

	public int getOrder_score() {
		return order_score;
	}

	public void setOrder_score(int order_score) {
		this.order_score = order_score;
	}

	public String getOrder_evaluation() {
		return order_evaluation;
	}

	public void setOrder_evaluation(String order_evaluation) {
		this.order_evaluation = order_evaluation;
	}

	public ReferdocServices getReferdocServices() {
		return referdocServices;
	}

	public void setReferdocServices(ReferdocServices referdocServices) {
		this.referdocServices = referdocServices;
	}

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
	
	

}
