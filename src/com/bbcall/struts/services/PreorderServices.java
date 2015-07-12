package com.bbcall.struts.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.dao.OrderlistMapper;
import com.bbcall.mybatis.dao.PreorderMapper;
import com.bbcall.mybatis.dao.ReferdocMapper;
import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.table.Orderlist;
import com.bbcall.mybatis.table.Preorder;
import com.bbcall.mybatis.table.User;

@Service("preorderServices")
public class PreorderServices {

	@Autowired
	private PreorderMapper preorderMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private ReferdocMapper referdocMapper;

	@Autowired
	private OrderlistMapper orderlistMapper;

	public Preorder preorderinfo;
	public List<Preorder> preorderinfos;

	// ################################################################################
	// ## Add Preorder services
	// ## 新增预处理订单
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) preorder_master_account
	// ## (2) preorder_price
	// ## (3) preorder_order_id
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return preorderinfos:
	// ## (1) preorderinfos
	// ##
	// ################################################################################
	public int addPreorder(int preorder_master_id, double preorder_price,
			int preorder_order_id) {
		System.out.println("Here is PreorderServices.add method...");

		Preorder preorder = new Preorder();
		User user = userMapper.getUserById(preorder_master_id);
		Orderlist orderlist = orderlistMapper.getOrder(preorder_order_id);

		String userskill = "";
		if (user.getUser_skill() != null && !user.getUser_skill().equals("")) {

			String[] skilllist = user.getUser_skill().split(";");

			int referdoc_code_num = 0;

			for (int i = 0; i < skilllist.length; i++) {

				referdoc_code_num = Integer.parseInt(skilllist[i]);

				userskill = userskill
						+ referdocMapper.getReferdoc(referdoc_code_num)
								.getReferdoc_type() + " ";
			}
		}

		System.out.println("userskill" + userskill);

		preorder.setPreorder_master_id(preorder_master_id);
		preorder.setPreorder_master_account(user.getUser_account());
		preorder.setPreorder_create_time(new Timestamp(System
				.currentTimeMillis()));
		preorder.setPreorder_master_name(user.getUser_name());
		System.out.println("user.getUser_name()" + user.getUser_name());
		preorder.setPreorder_master_grade(user.getUser_grade());
		System.out.println("user.getUser_grade()" + user.getUser_grade());
		preorder.setPreorder_master_pic(user.getUser_pic_url());
		System.out.println("user.getUser_pic_url()" + user.getUser_pic_url());
		preorder.setPreorder_price(preorder_price);
		preorder.setPreorder_master_skill(userskill);
		preorder.setPreorder_order_id(preorder_order_id);

		preorderMapper.addPreorder(preorder);
		System.out.println("addsuccess");

		orderlist.setOrder_status(7);

		orderlistMapper.updateOrder(orderlist);

		preorderinfos = preorderMapper
				.getPreordersByAccount(preorder_master_id);
		System.out.println("getsuccess");

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Delete Preorder services
	// ## 删除预处理订单
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) preorder_id
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return: NONE
	// ##
	// ################################################################################
	public int deletePreorder(int preorder_id, int preorder_master_id) {
		System.out.println("Here is PreorderServices.delete method...");

		preorderMapper.deletePreorder(preorder_id);

		preorderinfos = preorderMapper
				.getPreordersByAccount(preorder_master_id);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Preorder services
	// ## 通过师傅账号取得预处理订单
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) preorder_master_account
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return preorderinfos:
	// ## (1) preorderinfos
	// ##
	// ################################################################################
	public int getPreorderByAccount(int preorder_master_id) {
		System.out.println("Here is PreorderServices.get method...");

		preorderinfos = preorderMapper
				.getPreordersByAccount(preorder_master_id);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Preorder services
	// ## 通过订单编号取得预处理订单
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) preorder_order_id
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return preorderinfos:
	// ## (1) preorderinfos
	// ##
	// ################################################################################
	public int getPreorderDesc(int preorder_order_id) {
		System.out.println("Here is PreorderServices.get method...");

		preorderinfos = preorderMapper.getPreodersByOrderId(preorder_order_id);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Preorder services
	// ## 通过订单编号取得预处理订单
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) preorder_order_id
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return preorderinfos:
	// ## (1) preorderinfos
	// ##
	// ################################################################################
	public int getPreorderAsc(int preorder_order_id) {
		System.out.println("Here is PreorderServices.get method...");

		preorderinfos = preorderMapper.getPreodersByOrder(preorder_order_id);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Preorder services
	// ## 通过预订单编号取得预处理订单
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) preorder_id
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return preorderinfo:
	// ## (1) preorderinfo
	// ##
	// ################################################################################
	public int getPreorder(int preorder_id) {
		System.out.println("Here is PreorderServices.get method...");

		preorderinfo = preorderMapper.getPreorderById(preorder_id);

		return ResultCode.SUCCESS;
	}

	public Preorder preorderinfo() {

		return preorderinfo;
	}

	public List<Preorder> preorderinfos() {

		return preorderinfos;
	}
}
