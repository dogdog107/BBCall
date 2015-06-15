package com.bbcall.struts.services;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.dao.OrderlistMapper;
import com.bbcall.mybatis.dao.PreorderMapper;
import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.table.Orderlist;
import com.bbcall.mybatis.table.User;

@Service("orderlistServices")
public class OrderlistServices {

	@Autowired
	private OrderlistMapper orderlistMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PreorderMapper preorderMapper;

	public Orderlist orderlistinfo;
	public List<Orderlist> orderlistinfos;
	public String master_skill;
	public List<String> master_skills;

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
			String order_description, double order_price,
			String order_user_account, String order_type) {
		// TODO Auto-generated method stub

		System.out.println("Here is OrderlistServices.add method...");

		// 创建订单对象，写入数据
		Orderlist orderlist = new Orderlist();

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
		orderlist.setOrder_user_account(order_user_account);
		orderlist.setOrder_type(order_type);
		orderlist.setOrder_status(1);

		orderlistMapper.addOrder(orderlist);

		orderlistinfo = orderlist;

		return ResultCode.SUCCESS;

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
	// ## (8) order_pic_url
	// ## (9) order_description
	// ## (10) order_price
	// ## (11) order_user_account
	// ## (12) order_type
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
			String order_user_account, String order_type) {

		Orderlist orderlist = orderlistMapper.getOrder(order_id);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
		orderlist.setOrder_user_account(order_user_account);
		orderlist.setOrder_type(order_type);

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

	public int deleteOrder(int order_id, String order_user_account) {
		orderlistMapper.deleteOrder(order_id);
		
		preorderMapper.deletePreorderByOrderId(order_id);

		orderlistinfos = orderlistMapper
				.getUnOrdersByUserAccount(order_user_account);

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

	public int getUnOrders(String user_account) {

		String[] skilllist = null;
		orderlistinfos = null;
		User user = userMapper.getUserByAccount(user_account);

		if (user.getUser_type() == 1) { // 如果是用户的account
			orderlistinfos = orderlistMapper
					.getUnOrdersByUserAccount(user_account);
		} else if (user.getUser_type() == 2) { // 如果是师傅的account
			skilllist = user.getUser_skill().split(";"); // 取得师傅的技能列表
			for (int i = 0; i < skilllist.length; i++) { // 通过技能列表取得所有符合师傅技能的订单

				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getUnOrdersByMasterSkill(
							skilllist[i], user_account);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getUnOrdersByMasterSkill(skilllist[i],
									user_account));
				}
			}

		} else { // 如果是管理员的account,取出所有未确认的订单
			orderlistinfos = orderlistMapper.getUnOrders();
		}

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

	public int getUnOrders(List<String> skilllist, List<String> locationlist,
			String master_account) {

		orderlistinfos = null;

		if (null != locationlist) {
			for (int i = 0; i < skilllist.size(); i++) { // 通过技能列表取得所有符合师傅技能的订单
				for (int j = 0; j < locationlist.size(); j++) {
					if (orderlistinfos == null) {
						orderlistinfos = orderlistMapper
								.getUnOrdersByMasterLocation(skilllist.get(i),
										locationlist.get(j), master_account);
					} else {
						orderlistinfos.addAll(orderlistMapper
								.getUnOrdersByMasterLocation(skilllist.get(i),
										locationlist.get(j), master_account));
					}

				}
			}
		} else {
			for (int i = 0; i < skilllist.size(); i++) { // 通过技能列表取得所有符合师傅技能的订单
				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getUnOrdersByMasterSkill(
							skilllist.get(i), master_account);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getUnOrdersByMasterSkill(skilllist.get(i),
									master_account));
				}

			}
		}

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

	public int getUnOrdersByBookTime(String master_account,
			List<String> skilllist, List<String> locationlist) {

		orderlistinfos = null;
		
		for (int i = 0; i < skilllist.size(); i++) { // 通过技能列表取得所有符合师傅技能的订单
			for (int j = 0; j < locationlist.size(); j++) {
				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getUnOrdersByBookTime(
							skilllist.get(i), locationlist.get(j),
							master_account);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getUnOrdersByBookTime(skilllist.get(i),
									locationlist.get(j), master_account));
				}

			}
		}

		return ResultCode.SUCCESS;
	}

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
	public int getComOrders(String user_account) {

		User user = userMapper.getUserByAccount(user_account);

		if (user.getUser_type().equals(1)) { // 如果是用户的account
			orderlistinfos = orderlistMapper
					.getComOrdersByUserAccount(user_account);
		} else if (user.getUser_type().equals(2)) { // 如果是师傅的account
			orderlistinfos = orderlistMapper
					.getComOrdersByMasterAccount(user_account);
		} else { // 如果是管理员的account,取出所有已完成的订单
			orderlistinfos = orderlistMapper.getComOrders();
		}

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
	public int getProOrders(String user_account) {

		User user = userMapper.getUserByAccount(user_account);

		orderlistinfos = null;
		
		if (user.getUser_type() == 1) { // 如果是用户的account
			orderlistinfos = orderlistMapper
					.getProOrdersByUserAccount(user_account);
		} else if (user.getUser_type() == 2) { // 如果是师傅的account
			orderlistinfos = orderlistMapper
					.getProOrdersByMasterAccount(user_account);
		} else { // 如果是管理员的account,取出所有已完成的订单
			orderlistinfos = orderlistMapper.getProOrders();
		}

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
	public int ChangeOrderStatus(String master_account, int order_id) {

		double price = preorderMapper.getPreoder(master_account, order_id)
				.getPreorder_price();

		orderlistMapper.updateOrderAsMasterAccount(master_account, price,
				order_id);

		preorderMapper.deletePreorderByOrderId(order_id);

		orderlistinfos = orderlistMapper
				.getProOrdersByUserAccount(orderlistMapper.getOrder(order_id)
						.getOrder_user_account());

		return ResultCode.SUCCESS;
	}

	public Orderlist orderlistinfo() {

		return orderlistinfo;
	}

	public List<Orderlist> orderlistinfos() {

		return orderlistinfos;
	}
}
