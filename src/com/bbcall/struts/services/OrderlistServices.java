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
import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.table.AddressList;
import com.bbcall.mybatis.table.Orderlist;
import com.bbcall.mybatis.table.Preorder;
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

	public Orderlist orderlistinfo;
	public List<Orderlist> orderlistinfos;
	public String master_skill;
	public List<String> master_skills;
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

	public int addOrder(String order_book_time, String order_book_location,
			int order_book_location_code, BigInteger order_contact_mobile,
			String order_contact_name, String order_urgent,
			double order_urgent_bonus, String order_pic_url,
			String order_description, double order_price,
			String order_user_account, int order_type_code, int order_section) {
		// TODO Auto-generated method stub

		// 创建订单对象，写入数据
		Orderlist orderlist = new Orderlist();

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		orderlist.setOrder_type_code(order_type_code);
		orderlist.setOrder_status(1);
		orderlist.setOrder_section(order_section);

		orderlistMapper.addOrder(orderlist);

		orderlistinfos = orderlistMapper
				.getUnOrdersByUserAccount(order_user_account);

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
			String order_user_account, int order_type_code,
			String order_remark, int order_section) {

		Orderlist orderlist = orderlistMapper.getOrder(order_id);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		orderlist.setOrder_type_code(order_type_code);
		orderlist.setOrder_remark(order_remark);
		orderlist.setOrder_section(order_section);

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
		int type_code = 0;
		User user = userMapper.getUserByAccount(user_account);

		if (user.getUser_type().equals(1)) { // 如果是用户的account
			orderlistinfos = orderlistMapper
					.getUnOrdersByUserAccount(user_account);
		} else if (user.getUser_type().equals(2)) { // 如果是师傅的account
			skilllist = user.getUser_skill().split(";"); // 取得师傅的技能列表
			for (int i = 0; i < skilllist.length; i++) { // 通过技能列表取得所有符合师傅技能的订单

				type_code = Integer.parseInt(skilllist[i]);
				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getOrdersByMasterSkill(1,
							type_code, user_account);
				} else {
					orderlistinfos
							.addAll(orderlistMapper.getOrdersByMasterSkill(1,
									type_code, user_account));
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
		int type_code = 0;
		int area_code = 0;

		if (skilllist == null && locationlist == null) {
			User user = userMapper.getUserByAccount(master_account);
			String[] skills = user.getUser_skill().split(";"); // 取得师傅的技能列表
			for (int i = 0; i < skills.length; i++) { // 通过技能列表取得所有符合师傅技能的订单

				type_code = Integer.parseInt(skills[i]);

				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getOrdersByMasterSkill(1,
							type_code, master_account);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getOrdersByMasterSkill(1, type_code,
									master_account));
				}
			}
		} else if (skilllist == null && locationlist != null) {
			for (int i = 0; i < locationlist.size(); i++) {
				area_code = Integer.parseInt(locationlist.get(i));

				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getOrdersByMasterLocation(
							1, area_code, master_account);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getOrdersByMasterLocation(1, area_code,
									master_account));
				}
			}
		} else if (skilllist != null && locationlist == null) {
			for (int i = 0; i < skilllist.size(); i++) { // 通过技能列表取得所有符合师傅技能的订单

				type_code = Integer.parseInt(skilllist.get(i));

				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getOrdersByMasterSkill(1,
							type_code, master_account);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getOrdersByMasterSkill(1, type_code,
									master_account));
				}

			}
		} else {
			for (int i = 0; i < skilllist.size(); i++) { // 通过技能列表取得所有符合师傅技能的订单

				type_code = Integer.parseInt(skilllist.get(i));

				for (int j = 0; j < locationlist.size(); j++) {

					area_code = Integer.parseInt(locationlist.get(j));

					if (orderlistinfos == null) {
						orderlistinfos = orderlistMapper
								.getUnOrdersByMasterLocation(type_code,
										area_code, master_account);
					} else {
						orderlistinfos.addAll(orderlistMapper
								.getUnOrdersByMasterLocation(type_code,
										area_code, master_account));
					}

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
		int type_code = 0;
		int area_code = 0;
		
		

		for (int i = 0; i < skilllist.size(); i++) { // 通过技能列表取得所有符合师傅技能的订单

			type_code = Integer.parseInt(skilllist.get(i));

			for (int j = 0; j < locationlist.size(); j++) {

				area_code = Integer.parseInt(locationlist.get(j));

				if (orderlistinfos == null) {
					orderlistinfos = orderlistMapper.getUnOrdersByBookTime(
							type_code, area_code, master_account);
				} else {
					orderlistinfos.addAll(orderlistMapper
							.getUnOrdersByBookTime(type_code, area_code,
									master_account));
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

		if (user.getUser_type().equals(1)) { // 如果是用户的account
			orderlistinfos = orderlistMapper
					.getProOrdersByUserAccount(user_account);
		} else if (user.getUser_type().equals(2)) { // 如果是师傅的account
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

		double price = 0;
		Preorder preroder = preorderMapper.getPreoder(master_account, order_id);

		if (preroder != null) {
			price = preroder.getPreorder_price();
		}

		orderlistMapper.updateOrderAsMasterAccount(master_account, price,
				order_id);

		preorderMapper.deletePreorderByOrderId(order_id);

		orderlistinfos = orderlistMapper
				.getProOrdersByUserAccount(orderlistMapper.getOrder(order_id)
						.getOrder_user_account());

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

		if (orderlistinfos == null) {
			System.out.println("null");
		}

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

		orderlistinfos = orderlistMapper
				.getComOrdersByUserAccount(orderlistMapper.getOrder(order_id)
						.getOrder_user_account());

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
	public int selectWashOrderlist(int order_status, String order_master_account) {

		if (order_status == 0) {

			if (order_master_account.equals("") || order_master_account == null) {
				orderlistinfos = orderlistMapper.getWashOrderlist();
			} else {
				orderlistinfos = orderlistMapper
						.getWashOrderByMaster(order_master_account);
			}

		} else {
			if (order_master_account.equals("") || order_master_account == null) {
				orderlistinfos = orderlistMapper
						.getWashOrderByStatus(order_status);

			} else {
				orderlistinfos = orderlistMapper.getWashOrders(order_status,
						order_master_account);
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
	public int selectOrderlist(int order_status, String order_master_account,
			int order_type_code) {

		if (order_status == 0) {

			if (order_type_code == 0) {
				orderlistinfos = orderlistMapper
						.getOrdersByMaster(order_master_account);
			} else {
				orderlistinfos.addAll(orderlistMapper.getOrdersByMasterSkill(2,
						order_type_code, order_master_account));
				orderlistinfos.addAll(orderlistMapper.getOrdersByMasterSkill(3,
						order_type_code, order_master_account));
			}

		} else {
			if (order_type_code == 0) {
				orderlistinfos = orderlistMapper.getOrders(order_status,
						order_master_account);

			} else {
				orderlistinfos = orderlistMapper.getOrdersByMasterSkill(
						order_status, order_type_code, order_master_account);
			}
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
