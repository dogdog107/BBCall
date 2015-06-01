package com.bbcall.struts.services;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.dao.OrderlistMapper;
import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.table.Orderlist;
import com.bbcall.mybatis.table.User;

@Service("orderlistServices")
public class OrderlistServices {

	@Autowired
	private OrderlistMapper orderlistMapper;

	@Autowired
	private UserMapper userMapper;

	Orderlist orderlistinfo;
	List<Orderlist> orderlistinfos;

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
	// ## (3) order_contact_mobile
	// ## (4) order_contact_name
	// ## (5) order_urgent
	// ## (6) order_urgent_bonus
	// ## (7) order_pic_url
	// ## (8) order_description
	// ## (9) order_price
	// ## (10) order_user_account
	// ## (11) order_type
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
			BigInteger order_contact_mobile, String order_contact_name,
			String order_urgent, int order_urgent_bonus, String order_pic_url,
			String order_description, int order_price,
			String order_user_account, String order_type) {
		// TODO Auto-generated method stub

		System.out.println("Here is OrderlistServices.add method...");

		// 创建订单对象，写入数据
		Orderlist orderlist = new Orderlist();

		Timestamp ts = new Timestamp(System.currentTimeMillis());

		try {

			ts = Timestamp.valueOf(order_book_time);

		} catch (Exception e) {
			e.printStackTrace();
		}

		orderlist
				.setOrder_create_time(new Timestamp(System.currentTimeMillis()));
		orderlist.setOrder_book_time(ts);
		orderlist.setOrder_book_location(order_book_location);
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
	// ## (3) order_contact_mobile
	// ## (4) order_contact_name
	// ## (5) order_urgent
	// ## (6) order_urgent_bonus
	// ## (7) order_pic_url
	// ## (8) order_description
	// ## (9) order_price
	// ## (10) order_user_account
	// ## (11) order_type
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
			String order_book_location, BigInteger order_contact_mobile,
			String order_contact_name, String order_urgent,
			int order_urgent_bonus, String order_pic_url,
			String order_description, int order_price,
			String order_user_account, String order_type) {

		Orderlist orderlist = orderlistMapper.getOrder(order_id);

		Timestamp ts = new Timestamp(System.currentTimeMillis());

		try {

			ts = Timestamp.valueOf(order_book_time);

		} catch (Exception e) {
			e.printStackTrace();
		}

		orderlist
				.setOrder_create_time(new Timestamp(System.currentTimeMillis()));
		orderlist.setOrder_book_time(ts);
		orderlist.setOrder_book_location(order_book_location);
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
	// ## Get Unfinished Order by Master Account services
	// ## 师傅查询未完成订单列表
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) order_master_account
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

	public int getUnOrderForMaster(String order_master_account) {

		// List<Skill> skills = skillMapper.getSkills(master_account);

		orderlistinfos = orderlistMapper
				.getUnOrdersByMasterAccount(order_master_account);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Completed Order by Master Account services
	// ## 师傅查询完成订单列表
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) order_master_account
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
	public int getUnOrderByMasterAccount(String order_master_account) {

		orderlistinfos = orderlistMapper.getComOrdersByMasterAccount(order_master_account);
		
		return ResultCode.SUCCESS;
	}
}
