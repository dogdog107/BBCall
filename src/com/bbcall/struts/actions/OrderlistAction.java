package com.bbcall.struts.actions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
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

	private String order_book_year;
	private String order_book_month;
	private String order_book_day;
	private int order_id;
	private String order_book_location;
	private int order_book_location_code;
	private BigInteger order_contact_mobile;
	private String order_contact_name;
	private String order_urgent;
	private double order_urgent_bonus;
	private String order_pic_url;
	private String order_description;
	private double order_price;
	private String user_account;
	private String order_type;
	private String[] skilllist;
	private String[] locationlist;

	private List<File> orderFile = new ArrayList<File>();
	private List<String> contentType = new ArrayList<String>();
	private List<String> fileName = new ArrayList<String>(); // 文件名
	private List<String> imageFileName = new ArrayList<String>();

	private static final int BUFFER_SIZE = 16 * 1024;

	private static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;

			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	// 添加订单action
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public String add() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		String order_book_time = order_book_year + "-" + order_book_month + "-"
				+ order_book_day;

		if (orderFile == null)
			return ERROR;
		
		for (int i = 0; i < orderFile.size(); i++) { 
			imageFileName.add(new Date().getTime()+ getExtention(this.getOrderFileFileName().get(i)));
			//得到图片保存的位置(根据root来得到图片保存的路径在tomcat下的该工程里) 
			File imageFile = new File(ServletActionContext.getServletContext().getRealPath("UploadImages")+ "/" + imageFileName);
			
			order_pic_url = order_pic_url + imageFile.getAbsolutePath() + ";";
			
			copy(orderFile.get(i), imageFile);  //把图片写入到上面设置的路径里
		}
		
		int result = orderlistServices.addOrder(order_book_time,
				order_book_location, order_book_location_code,
				order_contact_mobile, order_contact_name, order_urgent,
				order_urgent_bonus, order_pic_url, order_description,
				order_price, user_account, order_type);

		if (result == ResultCode.SUCCESS) {
			Orderlist orderlist = orderlistServices.orderlistinfo();
			dataMap.put("orderlist", orderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("insertResult", true);
			System.out.println(dataMap);
		}

		return SUCCESS;

	}

	public String addJson() throws Exception {
		System.out.println("Here is OrderlistAction.insertJson");
		add();
		return "json";
	}

	public String update() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		String order_book_time = order_book_year + "-" + order_book_month + "-"
				+ order_book_day;

		int result = orderlistServices.updateOrder(order_id, order_book_time,
				order_book_location, order_book_location_code,
				order_contact_mobile, order_contact_name, order_urgent,
				order_urgent_bonus, order_pic_url, order_description,
				order_price, user_account, order_type);

		if (result == ResultCode.SUCCESS) {
			Orderlist orderlist = orderlistServices.orderlistinfo();
			dataMap.put("orderlist", orderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("updateResult", true);
			System.out.println(dataMap);
		}

		return SUCCESS;

	}

	public String updateJson() throws Exception {
		System.out.println("Here is OrderlistAction.updateJson");
		update();
		return "json";
	}

	public String deal() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices
				.ChangeOrderStatus(user_account, order_id);

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			dataMap.put("orderlists", orderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("dealResult", true);
			System.out.println(dataMap);
		}

		return SUCCESS;
	}

	public String dealJson() throws Exception {
		System.out.println("Here is OrderlistAction.dealJson");
		deal();
		return "json";
	}

	public String unOrderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices.getUnOrders(user_account);

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			dataMap.put("orderlists", orderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("unOrderlistResult", true);
			System.out.println(dataMap);
		}

		return SUCCESS;
	}

	public String unOrderlistJson() throws Exception {
		System.out.println("Here is OrderlistAction.unOrderlistJson");
		unOrderlist();
		return "json";
	}

	public String selectUnOrderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices.getUnOrders(user_account, skilllist,
				locationlist);

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			dataMap.put("orderlists", orderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("selectUnOrderlistResult", true);
			System.out.println(dataMap);
		}

		return SUCCESS;
	}

	public String selectUnOrderlistJson() throws Exception {
		System.out.println("Here is OrderlistAction.selectUnOrderlistJson");
		selectUnOrderlist();
		return "json";
	}

	public String selectUnOrderlist2() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices.getUnOrdersByBookTime(user_account,
				skilllist, locationlist);

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			dataMap.put("orderlists", orderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("selectUnOrderlist2Result", true);
			System.out.println(dataMap);
		}

		return SUCCESS;
	}

	public String selectUnOrderlist2Json() throws Exception {
		System.out.println("Here is OrderlistAction.selectUnOrderlist2Json");
		selectUnOrderlist2();
		return "json";
	}

	public String selectProOrderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices.getProOrders(user_account);

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			dataMap.put("orderlists", orderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("selectProOrderlistResult", true);
			System.out.println(dataMap);
		}

		return SUCCESS;
	}

	public String selectProOrderlistJson() throws Exception {
		System.out.println("Here is OrderlistAction.selectProOrderlistJson");
		selectProOrderlist();
		return "json";
	}

	public String selectComOrderlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices.getComOrders(user_account);

		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			dataMap.put("orderlists", orderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("selectComOrderlistResult", true);
			System.out.println(dataMap);
		}

		return SUCCESS;
	}

	public String selectComOrderlistJson() throws Exception {
		System.out.println("Here is OrderlistAction.selectComOrderlistJson");
		selectComOrderlist();
		return "json";
	}

	public String select() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices.getOrderById(order_id);

		if (result == ResultCode.SUCCESS) {
			Orderlist orderlist = orderlistServices.orderlistinfo();
			dataMap.put("orderlist", orderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("selectResult", true);
			System.out.println(dataMap);
		}

		return SUCCESS;
	}

	public String selectJson() throws Exception {
		System.out.println("Here is OrderlistAction.selectJson");
		select();
		return "json";
	}

	public String delete() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = orderlistServices.deleteOrder(order_id, user_account);
		if (result == ResultCode.SUCCESS) {
			List<Orderlist> orderlist = orderlistServices.orderlistinfos();
			dataMap.put("orderlists", orderlist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("deleteResult", true);
			System.out.println(dataMap);
		}

		return SUCCESS;
	}

	public String deleteJson() throws Exception {
		System.out.println("Here is OrderlistAction.deleteJson");
		delete();
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

	public void setOrder_pic_url(String order_pic_url) {
		this.order_pic_url = order_pic_url;
	}

	public void setOrder_description(String order_description) {
		this.order_description = order_description;
	}

	public void setOrder_price(double order_price) {
		this.order_price = order_price;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public void setOrder_book_year(String order_book_year) {
		this.order_book_year = order_book_year;
	}

	public void setOrder_book_month(String order_book_month) {
		this.order_book_month = order_book_month;
	}

	public void setOrder_book_day(String order_book_day) {
		this.order_book_day = order_book_day;
	}

	public void setOrder_book_location_code(int order_book_location_code) {
		this.order_book_location_code = order_book_location_code;
	}

	public void setSkilllist(String[] skilllist) {
		this.skilllist = skilllist;
	}

	public void setLocationlist(String[] locationlist) {
		this.locationlist = locationlist;
	}

	public List<File> getOrderFile() {
		return orderFile;
	}

	public void setOrderFile(List<File> orderFile) {
		this.orderFile = orderFile;
	}
	
	public List<String> getOrderFileFileName() {
		return fileName;
	}
	
	public void setOrderFileFileName(List<String> fileName) {
		this.fileName = fileName;
	}

	public void setContentType(List<String> contentType) {
		this.contentType = contentType;
	}

	public void setImageFileName(List<String> imageFileName) {
		this.imageFileName = imageFileName;
	}

}
