package com.bbcall.struts.services;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service("orderlistServices")
public class OrderlistServices {

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
			int order_type_code, int order_section) {
		// TODO Auto-generated method stub

		// 创建订单对象，写入数据
		Orderlist orderlist = new Orderlist();
		Referdoc referdoc = referdocMapper.getReferdoc(order_type_code);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		format.setLenient(false);

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		System.out.println("ts" +ts);

		try {

			ts = new Timestamp(format.parse(order_book_time).getTime());
			System.out.println("ts" +ts);

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
		orderlist.setOrder_user_id(order_user_id);
		orderlist.setOrder_type_code(order_type_code);
		orderlist.setOrder_status(1);
		orderlist.setOrder_section(order_section);
		orderlist.setOrder_type(referdoc.getReferdoc_type());
		orderlist.setOrder_refer_price(referdoc.getReferdoc_price());
		
		orderlistMapper.addOrder(orderlist);

		orderlistinfos = orderlistMapper
				.getUnOrdersByUserAccount(order_user_id,0);

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

	public int deleteOrder(int order_id, int order_user_id, int offset) {
		orderlistMapper.deleteOrder(order_id);

		preorderMapper.deletePreorderByOrderId(order_id);

		orderlistinfos = orderlistMapper
				.getUnOrdersByUserAccount(order_user_id,offset);

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

	public int getUnOrders(int user_id,int offset) {

		String[] skilllist = null;
		orderlistinfos = null;
		int type_code = 0;
		User user = userMapper.getUserById(user_id);

		if (user.getUser_type().equals(1)) { // 如果是用户的account
			orderlistinfos = orderlistMapper.getUnOrdersByUserAccount(user_id,offset);
		} else if (user.getUser_type().equals(2)) { // 如果是师傅的account
			skilllist = user.getUser_skill().split(";"); // 取得师傅的技能列表
			for (int i = 0; i < skilllist.length; i++) { // 通过技能列表取得所有符合师傅技能的订单

				type_code = Integer.parseInt(skilllist[i]);
				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getOrdersByMasterSkill(1,
							type_code, user_id,offset);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getOrdersByMasterSkill(1, type_code, user_id,offset));
				}
			}

		} else { // 如果是管理员的account,取出所有未确认的订单
			orderlistinfos = orderlistMapper.getUnOrders(offset);
		}
//		
//		for (int i=0; i< orderlistinfos.size();i++) {
//			System.out.println(orderlistinfos.get(i).getOrder_id());
//		}

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
			int master_id,int offset) {

		orderlistinfos = null;
		int type_code = 0;
		int area_code = 0;
		String[] sklist = null;

		if (skilllist.equals("") && locationlist.equals("")) {
			User user = userMapper.getUserById(master_id);
			String[] skills = user.getUser_skill().split(";"); // 取得师傅的技能列表
			for (int i = 0; i < skills.length; i++) { // 通过技能列表取得所有符合师傅技能的订单

				type_code = Integer.parseInt(skills[i]);

				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getOrdersByMasterSkill(1,
							type_code, master_id,offset);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getOrdersByMasterSkill(1, type_code, master_id,offset));
				}
			}
		} else if (skilllist.equals("") && !locationlist.equals("")) {

			area_code = Integer.parseInt(locationlist);

			orderlistinfos = orderlistMapper.getOrdersByMasterLocation(1,
					area_code, master_id,offset);
		} else if (!skilllist.equals("") && locationlist.equals("")) {

			sklist = skilllist.split(";");

			for (int i = 0; i < sklist.length; i++) { // 通过技能列表取得所有符合师傅技能的订单

				type_code = Integer.parseInt(sklist[i]);

				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getOrdersByMasterSkill(1,
							type_code, master_id,offset);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getOrdersByMasterSkill(1, type_code, master_id,offset));
				}

			}
		} else {

			sklist = skilllist.split(";");

			for (int i = 0; i < sklist.length; i++) { // 通过技能列表取得所有符合师傅技能的订单

				type_code = Integer.parseInt(sklist[i]);

				area_code = Integer.parseInt(locationlist);

				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper
							.getUnOrdersByMasterLocation(type_code, area_code,
									master_id,offset);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getUnOrdersByMasterLocation(type_code, area_code,
									master_id,offset));
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

	public int getUnOrdersByBookTime(int master_id, String skilllist,
			String locationlist,int offset) {

		orderlistinfos = null;
		int type_code = 0;
		int area_code = 0;
		String[] sklist = null;

		if (skilllist.equals("") && locationlist.equals("")) {
			User user = userMapper.getUserById(master_id);
			String[] skills = user.getUser_skill().split(";"); // 取得师傅的技能列表
			for (int i = 0; i < skills.length; i++) { // 通过技能列表取得所有符合师傅技能的订单

				type_code = Integer.parseInt(skills[i]);

				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getOrdersByMasterSkill2(1,
							type_code, master_id,offset);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getOrdersByMasterSkill2(1, type_code, master_id,offset));
				}
			}
		} else if (skilllist.equals("") && !locationlist.equals("")) {

			area_code = Integer.parseInt(locationlist);

			if (orderlistinfos == null) {
				orderlistinfos = orderlistMapper.getOrdersByMasterLocation2(1,
						area_code, master_id,offset);
			} else {
				orderlistinfos.addAll(orderlistMapper
						.getOrdersByMasterLocation2(1, area_code, master_id,offset));
			}
		} else if (!skilllist.equals("") && locationlist.equals("")) {

			sklist = skilllist.split(";");

			for (int i = 0; i < sklist.length; i++) { // 通过技能列表取得所有符合师傅技能的订单

				type_code = Integer.parseInt(sklist[i]);

				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getOrdersByMasterSkill2(1,
							type_code, master_id,offset);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getOrdersByMasterSkill2(1, type_code, master_id,offset));
				}

			}
		} else {

			sklist = skilllist.split(";");

			for (int i = 0; i < sklist.length; i++) { // 通过技能列表取得所有符合师傅技能的订单

				type_code = Integer.parseInt(sklist[i]);

				area_code = Integer.parseInt(locationlist);

				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getUnOrdersByBookTime(
							type_code, area_code, master_id,offset);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getUnOrdersByBookTime(type_code, area_code,
									master_id,offset));
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
	public int getComOrders(int user_id, int offset) {

		User user = userMapper.getUserById(user_id);

		if (user.getUser_type().equals(1)) { // 如果是用户的account
			orderlistinfos = orderlistMapper.getComOrdersByUserAccount(user_id,offset);
		} else if (user.getUser_type().equals(2)) { // 如果是师傅的account
			orderlistinfos = orderlistMapper
					.getComOrdersByMasterAccount(user_id,offset);
		} else { // 如果是管理员的account,取出所有已完成的订单
			orderlistinfos = orderlistMapper.getComOrders(offset);
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
	public int getProOrders(int user_id,int offset) {

		User user = userMapper.getUserById(user_id);
		System.out.println(user.getUser_account());

		orderlistinfos = null;

		if (user.getUser_type().equals(1)) { // 如果是用户的account
			System.out.println("1");
			orderlistinfos = orderlistMapper.getProOrdersByUserAccount(user_id,offset);
			
		} else if (user.getUser_type().equals(2)) { // 如果是师傅的account
			System.out.println("2");
			orderlistinfos = orderlistMapper
					.getProOrdersByMasterAccount(user_id,offset);
					
		} else { // 如果是管理员的account,取出所有已完成的订单
			System.out.println("3");
			orderlistinfos = orderlistMapper.getProOrders(offset);
			
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
	public int ChangeOrderStatus(int master_id, int order_id) {

		double price = 0;
		Preorder preroder = preorderMapper.getPreoder(master_id, order_id);

		if (preroder != null) {
			price = preroder.getPreorder_price();
		}

		orderlistMapper.updateOrderAsMasterAccount(master_id, price, order_id);

		preorderMapper.deletePreorderByOrderId(order_id);

		orderlistinfos = orderlistMapper
				.getProOrdersByUserAccount(orderlistMapper.getOrder(order_id)
						.getOrder_user_id(),0);

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
	public int change(int order_id, int order_status, String order_remark) {

		System.out.println(order_remark);
		orderlistMapper.change(order_id, order_status, order_remark);

		orderlistinfo = orderlistMapper.getOrder(order_id);
		System.out.println("orderlistinfo "+ orderlistinfo.getOrder_status());

		return ResultCode.SUCCESS;
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
	public int completeOrder(int order_score, String order_evaluation,
			int order_id) {

		orderlistMapper.completeOrder(order_score, order_evaluation, order_id);
		
		Orderlist orderlist = orderlistMapper.getOrder(order_id);
		
		int master_id = orderlist.getOrder_master_id();
		int user_id = orderlist.getOrder_user_id();

		List<Orderlist> ors = orderlistMapper.getOrdersByMId(master_id);
		User tempuser = userMapper.getUserById(user_id);
		
		if (order_score != 0) {
			int scores = 0;
			double finalscore = 0;
			for (int i=0; i<ors.size(); i++) {
				scores = scores + ors.get(i).getOrder_score();
			}
			
			finalscore = scores / ors.size();
			
			tempuser.setUser_grade(finalscore);
			userMapper.updateUser(tempuser);
		}
		
		orderlistinfos = orderlistMapper
				.getComOrdersByUserAccount(user_id,0);

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
	public int getWashOrderlist() {

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
	public int selectWashOrderlist(int order_status, int order_master_id) {

		if (order_status == 0) {

			if (order_master_id == 0) {
				orderlistinfos = orderlistMapper.getWashOrderlist();
			} else {
				orderlistinfos = orderlistMapper
						.getWashOrderByMaster(order_master_id);
			}

		} else {
			if (order_master_id == 0) {
				orderlistinfos = orderlistMapper
						.getWashOrderByStatus(order_status);

			} else {
				orderlistinfos = orderlistMapper.getWashOrders(order_status,
						order_master_id);
			}
		}

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
	public int getWashOrderlist(String sortparm) {

		if (sortparm.equals("order_status")) {
			orderlistinfos = orderlistMapper.getWashOrderlistByStatus();
		} else {
			orderlistinfos = orderlistMapper.getWashOrderlistByMaster();
		}

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
	public int selectOrderlist(int order_status, int order_master_id,
			int order_type_code,int offset) {

		if (order_status == 0) {

			if (order_type_code == 0) {
				orderlistinfos = orderlistMapper
						.getOrdersByMaster(order_master_id,0);
			} else {
				orderlistinfos.addAll(orderlistMapper.getOrdersByMasterSkill(2,
						order_type_code, order_master_id,offset));
				orderlistinfos.addAll(orderlistMapper.getOrdersByMasterSkill(3,
						order_type_code, order_master_id,offset));
			}

		} else {
			if (order_type_code == 0) {
				orderlistinfos = orderlistMapper.getOrders(order_status,
						order_master_id,offset);

			} else {
				orderlistinfos = orderlistMapper.getOrdersByMasterSkill(
						order_status, order_type_code, order_master_id,offset);
			}
		}

		return ResultCode.SUCCESS;
	}
	
	public int getOrderlist(int user_id,String order_status, int offset) {

		switch (order_status) {
		case "2":
			orderlistinfos = orderlistMapper.getProOrders(offset);
			break;
		
		case "3":
			orderlistinfos = orderlistMapper.getComOrders(offset);
			break;
		
		case "4":
			orderlistinfos = orderlistMapper.getRecOrders(offset);
			break;
			
		case "5":
			orderlistinfos = orderlistMapper.getWasOrders(offset);
			break;
	
		case "6":
			orderlistinfos = orderlistMapper.getDelOrders(offset);
			break;

		default:
			orderlistinfos = orderlistMapper.getUnOrders(offset);
			break;
		}

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
