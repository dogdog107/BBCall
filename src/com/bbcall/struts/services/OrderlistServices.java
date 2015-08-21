package com.bbcall.struts.services;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.dao.AddressListMapper;
import com.bbcall.mybatis.dao.OrderlistMapper;
import com.bbcall.mybatis.dao.PreorderMapper;
import com.bbcall.mybatis.dao.ReferdocMapper;
import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.table.AddressList;
import com.bbcall.mybatis.table.Orderlist;
import com.bbcall.mybatis.table.Preorder;
import com.bbcall.mybatis.table.Referdoc;
import com.bbcall.mybatis.table.User;
import com.github.pagehelper.PageHelper;

@Scope("prototype")
@Service("orderlistServices")
public class OrderlistServices {

	private static Logger logger = Logger.getLogger(OrderlistServices.class);

	@Autowired
	private OrderlistMapper orderlistMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PreorderMapper preorderMapper;

	@Autowired
	private AddressListMapper addressListMapper;

	@Autowired
	private ReferdocMapper referdocMapper;

	public Orderlist orderlistinfo;
	public List<Orderlist> orderlistinfos;
	private List<AddressList> addresslist;

	// ################################################################################
	// ## Add Order services
	// ## 新增订单
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) order_book_time
	// ## (2) order_book_location
	// ## (3) order_book_location_code
	// ## (4) order_contact_mobile
	// ## (5) order_contact_name
	// ## (6) order_urgent
	// ## (7) order_urgent_bonus
	// ## (8) order_pic_url
	// ## (9) order_description
	// ## (10) order_price
	// ## (11) order_user_account
	// ## (12) order_type
	// ## (13) order_type_code
	// ## (14) order_refer_price
	// ## (15) order_section
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfo:
	// ## (1) orderlistinfo
	// ##
	// ################################################################################

	public int addOrder(String order_book_time, String order_book_location,
			int order_book_location_code, BigInteger order_contact_mobile,
			String order_contact_name, String order_urgent,
			double order_urgent_bonus, String order_pic_url,
			String order_description, double order_price, int order_user_id,
			int order_type_code, int order_section, Integer pagenum) {
		// TODO Auto-generated method stub

		// 创建订单对象，写入数据
		Orderlist orderlist = new Orderlist();
		Referdoc referdoc = referdocMapper.getReferdoc(order_type_code);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		format.setLenient(false);

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		System.out.println("ts" + ts);

		try {

			ts = new Timestamp(format.parse(order_book_time).getTime());
			System.out.println("ts" + ts);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		if (ts.after(new Timestamp(System.currentTimeMillis()))) {
			orderlist.setOrder_create_time(new Timestamp(System
					.currentTimeMillis()));
			orderlist.setOrder_book_time(ts);
			orderlist.setOrder_book_location(order_book_location);
			orderlist.setOrder_book_location_code(order_book_location_code);
			orderlist.setOrder_contact_mobile(order_contact_mobile);
			orderlist.setOrder_contact_name(order_contact_name);
			orderlist.setOrder_pic_url(order_pic_url);
			orderlist.setOrder_description(order_description);
			orderlist.setOrder_urgent(Boolean.parseBoolean(order_urgent));
			orderlist.setOrder_urgent_bonus(order_urgent_bonus);
			orderlist.setOrder_user_id(order_user_id);
			orderlist.setOrder_type_code(order_type_code);
			orderlist.setOrder_status(1);
			orderlist.setOrder_section(order_section);
			orderlist.setOrder_type(referdoc.getReferdoc_type());
			orderlist.setOrder_refer_price(referdoc.getReferdoc_price());
			orderlist.setOrder_score(0);
			if (referdoc.isReferdoc_flag()) {
				orderlist.setOrder_price(referdoc.getReferdoc_price());
			} else {
				orderlist.setOrder_price(order_price);
			}

			orderlistMapper.addOrder(orderlist);

			// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
			if (pagenum == null || pagenum == 0)
				pagenum = 1;

			// PageHelper.startPage(PageNum, PageSize)
			// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
			PageHelper.startPage(pagenum, 10);

			orderlistinfos = orderlistMapper
					.getUnOrdersByUserAccount(order_user_id);

			return ResultCode.SUCCESS;
		} else {
			return ResultCode.ORDER_INVALID_DATETIME;
		}

	}

	// ################################################################################
	// ## Update Order services
	// ## 修改订单
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) order_book_time
	// ## (2) order_book_location
	// ## (3) order_book_location
	// ## (4) order_contact_mobile
	// ## (5) order_contact_name
	// ## (6) order_urgent
	// ## (7) order_urgent_bonus
	// ## (8) order_pic_url1
	// ## (8) order_pic_url2
	// ## (8) order_pic_url3
	// ## (9) order_description
	// ## (10) order_price
	// ## (11) order_user_account
	// ## (12) order_type
	// ## (13) order_section
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfo:
	// ## (1) orderlistinfo
	// ##
	// ################################################################################

	public int updateOrder(int order_id, String order_book_time,
			String order_book_location, int order_book_location_code,
			BigInteger order_contact_mobile, String order_contact_name,
			String order_urgent, double order_urgent_bonus,
			String order_pic_url, String order_description, double order_price,
			int order_user_id, int order_type_code, String order_remark,
			int order_section) {

		Orderlist orderlist = orderlistMapper.getOrder(order_id);
		Referdoc referdoc = referdocMapper.getReferdoc(order_type_code);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		format.setLenient(false);

		Timestamp ts = new Timestamp(System.currentTimeMillis());

		try {

			ts = new Timestamp(format.parse(order_book_time).getTime());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		orderlist
				.setOrder_create_time(new Timestamp(System.currentTimeMillis()));
		orderlist.setOrder_end_time(new Timestamp(System.currentTimeMillis()));
		orderlist.setOrder_book_time(ts);
		orderlist.setOrder_book_location(order_book_location);
		orderlist.setOrder_book_location_code(order_book_location_code);
		orderlist.setOrder_contact_mobile(order_contact_mobile);
		orderlist.setOrder_contact_name(order_contact_name);
		orderlist.setOrder_pic_url(order_pic_url);
		orderlist.setOrder_description(order_description);
		orderlist.setOrder_price(order_price);
		orderlist.setOrder_urgent(Boolean.parseBoolean(order_urgent));
		orderlist.setOrder_urgent_bonus(order_urgent_bonus);
		orderlist.setOrder_user_id(order_user_id);
		orderlist.setOrder_type_code(order_type_code);
		orderlist.setOrder_remark(order_remark);
		orderlist.setOrder_section(order_section);
		orderlist.setOrder_type(referdoc.getReferdoc_type());
		orderlist.setOrder_refer_price(referdoc.getReferdoc_price());

		orderlistMapper.updateOrder(orderlist);

		orderlistinfo = orderlist;

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Delete Order services
	// ## 删除订单
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) order_id
	// ## (2) order_user_account
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfos:
	// ## (1) orderlistinfos
	// ##
	// ################################################################################

	public int deleteOrder(int order_id, int order_user_id, Integer pagenum) {
		orderlistMapper.deleteOrder(order_id);

		preorderMapper.deletePreorderByOrderId(order_id);

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper
				.getUnOrdersByUserAccount(order_user_id);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Order by order id services
	// ## 查询订单
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) order_id
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfo:
	// ## (1) orderlistinfo
	// ##
	// ################################################################################

	public int getOrderById(int order_id) {

		orderlistinfo = orderlistMapper.getOrder(order_id);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Unfinished Order Services
	// ## 查询未完成订单列表
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) user_account
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfos:
	// ## (1) orderlistinfos
	// ##
	// ################################################################################

	public int getUnOrdersForCustomer(int user_id, Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getUnOrdersByUserAccount(user_id);

		return ResultCode.SUCCESS;
	}

	public int getUnOrdersForMaster(int user_id, Integer pagenum) {

		String[] skilllist = null;
		User user = userMapper.getUserById(user_id);
		skilllist = user.getUser_skill().split(";");

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getUnOrdersBySkill(skilllist, user_id);

		return ResultCode.SUCCESS;
	}

	public int getUnOrdersForAdm(Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getUnOrders();
		//
		// for (int i=0; i< orderlistinfos.size();i++) {
		// System.out.println(orderlistinfos.get(i).getOrder_id());
		// }

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Unfinished Order by order type & locationServices
	// ## 查询未完成订单列表 (按发布时间排序)
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) master_account
	// ## (2) skilllist
	// ## (3) locationlist
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfos:
	// ## (1) orderlistinfos
	// ##
	// ################################################################################

	public int getUnOrderlist(String skilllist, String locationlist,
			int master_id, String sortparm, Integer pagenum) {

		orderlistinfos = null;
		String[] sklist = null;

		if (skilllist.equals("")) {
			User user = userMapper.getUserById(master_id);
			sklist = user.getUser_skill().split(";");
		} else {
			sklist = skilllist.split(";");
		}

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getUnOrdersByMasterLocation(sklist,
				locationlist, sortparm, master_id);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Unfinished Order by order type & locationServices
	// ## 查询未完成订单列表 (按工作截止时间排序)
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) master_account
	// ## (2) skilllist
	// ## (3) locationlist
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfos:
	// ## (1) orderlistinfos
	// ##
	// ################################################################################

	// public int getUnOrdersByBookTime(int master_id, String skilllist,
	// String locationlist, Integer pagenum) {
	//
	// orderlistinfos = null;
	// int type_code = 0;
	// int area_code = 0;
	// String[] sklist = null;
	//
	// // 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
	// if (pagenum == null || pagenum == 0)
	// pagenum = 1;
	//
	// // PageHelper.startPage(PageNum, PageSize)
	// // 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
	// PageHelper.startPage(pagenum, 10);
	//
	// if (skilllist.equals("") && locationlist.equals("")) {
	// User user = userMapper.getUserById(master_id);
	// String[] skills = user.getUser_skill().split(";"); // 取得师傅的技能列表
	// for (int i = 0; i < skills.length; i++) { // 通过技能列表取得所有符合师傅技能的订单
	//
	// type_code = Integer.parseInt(skills[i]);
	//
	// if (orderlistinfos == null) {
	// orderlistinfos = orderlistMapper.getOrdersByMasterSkill2(1,
	// type_code, master_id);
	// } else {
	// orderlistinfos.addAll(orderlistMapper
	// .getOrdersByMasterSkill2(1, type_code, master_id));
	// }
	// }
	// } else if (skilllist.equals("") && !locationlist.equals("")) {
	//
	// area_code = Integer.parseInt(locationlist);
	//
	// if (orderlistinfos == null) {
	// orderlistinfos = orderlistMapper.getOrdersByMasterLocation2(1,
	// area_code, master_id);
	// } else {
	// orderlistinfos.addAll(orderlistMapper
	// .getOrdersByMasterLocation2(1, area_code, master_id));
	// }
	// } else if (!skilllist.equals("") && locationlist.equals("")) {
	//
	// sklist = skilllist.split(";");
	//
	// for (int i = 0; i < sklist.length; i++) { // 通过技能列表取得所有符合师傅技能的订单
	//
	// type_code = Integer.parseInt(sklist[i]);
	//
	// if (orderlistinfos == null) {
	// orderlistinfos = orderlistMapper.getOrdersByMasterSkill2(1,
	// type_code, master_id);
	// } else {
	// orderlistinfos.addAll(orderlistMapper
	// .getOrdersByMasterSkill2(1, type_code, master_id));
	// }
	//
	// }
	// } else {
	//
	// sklist = skilllist.split(";");
	//
	// for (int i = 0; i < sklist.length; i++) { // 通过技能列表取得所有符合师傅技能的订单
	//
	// type_code = Integer.parseInt(sklist[i]);
	//
	// area_code = Integer.parseInt(locationlist);
	//
	// if (orderlistinfos == null) {
	// orderlistinfos = orderlistMapper.getUnOrdersByBookTime(
	// type_code, area_code, master_id);
	// } else {
	// orderlistinfos.addAll(orderlistMapper
	// .getUnOrdersByBookTime(type_code, area_code,
	// master_id));
	// }
	//
	// }
	// }
	//
	// return ResultCode.SUCCESS;
	// }

	// ################################################################################
	// ## Get Completed Order services
	// ## 查询完成订单列表
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) user_account
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfos:
	// ## (1) orderlistinfos
	// ##
	// ################################################################################
	public int getComOrdersForCustomer(int user_id, Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getComOrdersByUserAccount(user_id);

		return ResultCode.SUCCESS;
	}

	public int getComOrdersForMaster(int user_id, Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getComOrdersByMasterAccount(user_id);

		return ResultCode.SUCCESS;
	}

	public int getComOrdersForAdm(Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getComOrders();

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get In progress Order services
	// ## 查询正在处理订单列表
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) user_account
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfos:
	// ## (1) orderlistinfos
	// ##
	// ################################################################################
	public int getProOrdersForCustomer(int user_id, Integer pagenum) {

		System.out.println("1");

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getProOrdersByUserAccount(user_id);

		return ResultCode.SUCCESS;
	}

	public int getProOrdersForMaster(int user_id, Integer pagenum) {

		System.out.println("2");
		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getProOrdersByMasterAccount(user_id);

		return ResultCode.SUCCESS;
	}

	public int getProOrdersForAdm(Integer pagenum) {

		System.out.println("3");
		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getProOrders();

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Change Order In Progress services
	// ## 用户和师傅确定订单
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) master_account
	// ## (2) order_id
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfos:
	// ## (1) orderlistinfos
	// ##
	// ################################################################################
	public int ChangeOrderStatus(int master_id, int order_id, Integer pagenum) {

		double price = 0;
		Preorder preroder = preorderMapper.getPreoder(master_id, order_id);

		if (preroder != null) {
			price = preroder.getPreorder_price();
		}

		User tempuser = userMapper.getUserById(master_id);
		orderlistMapper.updateOrderAsMasterAccount(master_id,
				tempuser.getUser_name(), price, order_id);

		preorderMapper.deletePreorderByOrderId(order_id);

		int userid = orderlistMapper.getOrder(order_id).getOrder_user_id();

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getProOrdersByUserAccount(userid);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Change Order status services
	// ## 更改订单状态
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) order_id
	// ## (2) order_status
	// ## (2) order_description
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfos:
	// ## (1) orderlistinfos
	// ##
	// ################################################################################
	public int change(int order_id, int order_status, String order_remark,
			String order_master_name) {

		System.out.println(order_remark);

		// if (order_status == 2) {

		// User user = userMapper.

		// return ResultCode.SUCCESS;

		// } else {
		orderlistMapper.change(order_id, order_status, order_remark);

		orderlistinfo = orderlistMapper.getOrder(order_id);
		System.out.println("orderlistinfo " + orderlistinfo.getOrder_status());

		return ResultCode.SUCCESS;
		// }

	}

	// ################################################################################
	// ## Complete Order services
	// ## 更改订单状态为完成
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) order_score
	// ## (3) order_evaluation
	// ## (2) order_id
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfos:
	// ## (1) orderlistinfos
	// ##
	// ################################################################################
	public int completeOrder(double order_score, String order_evaluation,
			int order_id, Integer pagenum) {

		System.out.println("this is order action complete order function");
		orderlistMapper.completeOrder(order_score, order_evaluation, order_id,
				new Timestamp(System.currentTimeMillis()));
		System.out.println("mapper completed");

		Orderlist orderlist = orderlistMapper.getOrder(order_id);

		int master_id = orderlist.getOrder_master_id();
		int user_id = orderlist.getOrder_user_id();
		Double price = orderlist.getOrder_price();
		String type = orderlist.getOrder_type();
		String mastername = orderlist.getOrder_master_name();
		System.out.println("master_id: " + master_id);
		System.out.println("user_id: " + user_id);
		List<Orderlist> ors = orderlistMapper.getOrdersByMId(master_id, 3);
		for (int j = 0; j < ors.size(); j++) {

			System.out.println("order_id: " + ors.get(j).getOrder_id());
		}
		User tempuser = userMapper.getUserById(master_id);
		double finalscore = tempuser.getUser_grade();
		if (order_score != 0) {
			double scores = 0;
			System.out.println("score not empty");
			if (ors.size() <= 1) {
				finalscore = order_score;
				System.out.println("size le 1: " + finalscore);
			} else {
				for (int i = 0; i < ors.size(); i++) {
					scores = scores + ors.get(i).getOrder_score();
				}
				finalscore = scores / ors.size();
				System.out.println("size gt 1: " + finalscore);
			}

			System.out.println(tempuser.getUser_id());
			tempuser.setUser_grade(finalscore);
			userMapper.updateUser(tempuser);
		}

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getComOrdersByUserAccount(user_id);

		logger.info("tradeOpr:[Tradecomplete][User ID: " + user_id
				+ "]Order ID: " + order_id + "; Master name: " + mastername
				+ "; Order type: " + type + "; Order price: " + price);
		logger.info("tradeOpr:[Tradecomplete][Master ID: " + master_id
				+ "]Order ID: " + order_id + "; Order type: " + type
				+ "; Order price: " + price);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get wash Order services
	// ## 查询洗衣订单列表
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfos:
	// ## (1) orderlistinfos
	// ##
	// ################################################################################
	public int getWashOrderlist(Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getWashOrderlist();

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Select wash Order services
	// ## 筛选洗衣订单列表
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) order_status
	// ## (1) order_master_account
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfos:
	// ## (1) orderlistinfos
	// ##
	// ################################################################################
	public int selectWashOrderlist(int order_status, int order_master_id,
			Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		// if (order_status == 0) {
		//
		// if (order_master_id == 0) {
		// orderlistinfos = orderlistMapper.getWashOrderlist();
		// } else {
		// orderlistinfos = orderlistMapper
		// .getWashOrderByMaster(order_master_id);
		// }
		//
		// } else {
		// if (order_master_id == 0) {
		// orderlistinfos = orderlistMapper
		// .getWashOrderByStatus(order_status);
		//
		// } else {
		// orderlistinfos = orderlistMapper.getWashOrders(order_status,
		// order_master_id);
		// }
		// }

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get wash Order services
	// ## 查询洗衣订单列表排序
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) sortparm
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfos:
	// ## (1) orderlistinfos
	// ##
	// ################################################################################
	public int getWashOrderlist(String order_status, String order_section,
			String order_master_name, Integer pagenum) {

		// Map<String, Object> params = new HashMap<String, Object>();
		//
		// params.put("sortparm", sortparm);
		// params.put("order_status", order_status);
		// params.put("order_section", order_section);

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getWashOrderlistByParm(order_status,
				order_section, order_master_name);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Select Order services
	// ## 筛选订单列表
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) order_status
	// ## (2) order_master_account
	// ## (3) order_type_code
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return orderlistinfos:
	// ## (1) orderlistinfos
	// ##
	// ################################################################################
	public int selectOrderlist(String order_status, int order_master_id,
			String order_type_code, Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getOrders(order_status,
				order_master_id, order_type_code);

		return ResultCode.SUCCESS;
	}

	public int getOrderlist1(Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getUnOrders2();

		return ResultCode.SUCCESS;
	}

	public int getOrderlist2(Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getProOrders();

		return ResultCode.SUCCESS;
	}

	public int getOrderlist3(Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getComOrders();

		return ResultCode.SUCCESS;
	}

	public int getOrderlist4(Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getRecOrders();

		return ResultCode.SUCCESS;
	}

	public int getOrderlist5(Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getWasOrders();

		return ResultCode.SUCCESS;
	}

	public int getOrderlist6(Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getDelOrders();

		return ResultCode.SUCCESS;
	}

	public int getOrderlist7(Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		orderlistinfos = orderlistMapper.getAucOrders();

		return ResultCode.SUCCESS;
	}

	// ###################
	// ## 读取省、市、区列表
	// ###################

	public int checkAdsList(int addresscode) {
		System.out.println("Here is UserServices.checkAdsList method...");

		List<AddressList> addresslist = addressListMapper
				.getAddressByAreano(addresscode);
		if (addresslist.size() > 0) {

			this.addresslist = addresslist;

			return ResultCode.SUCCESS;
		} else {
			return ResultCode.ADDRESS_NULL;
		}
	}

	// ###################
	// ## 读取Child省、市、区列表
	// ###################

	public int checkChildAdsList(int addresscode) {
		System.out.println("Here is UserServices.checkChildAdsList method...");

		List<AddressList> addresslist = addressListMapper
				.getAddressByParentno(addresscode);
		if (addresslist.size() > 0) {

			this.addresslist = addresslist;

			return ResultCode.SUCCESS;
		} else {
			return ResultCode.ADDRESS_NULL;
		}
	}

	public Orderlist orderlistinfo() {

		return orderlistinfo;
	}

	public List<Orderlist> orderlistinfos() {

		return orderlistinfos;
	}

	public List<AddressList> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(List<AddressList> addresslist) {
		this.addresslist = addresslist;
	}

}
