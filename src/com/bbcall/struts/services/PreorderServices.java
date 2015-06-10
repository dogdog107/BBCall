package com.bbcall.struts.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.dao.PreorderMapper;
import com.bbcall.mybatis.table.Preorder;

@Service("preorderServices")
public class PreorderServices {

	@Autowired
	private PreorderMapper preorderMapper;

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
	public int addPreorder(String preorder_master_account,
			double preorder_price, int preorder_order_id) {
		System.out.println("Here is PreorderServices.add method...");

		Preorder preorder = new Preorder();

		preorder.setPreorder_master_account(preorder_master_account);
		preorder.setPreorder_create_time(new Timestamp(System
				.currentTimeMillis()));
		preorder.setPreorder_price(preorder_price);
		preorder.setPreorder_order_id(preorder_order_id);

		preorderMapper.addPreorder(preorder);

		preorderinfos = preorderMapper
				.getPreordersByAccount(preorder_master_account);

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
	public int deletePreorder(int preorder_id) {
		System.out.println("Here is PreorderServices.delete method...");

		preorderMapper.deletePreorder(preorder_id);

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
	public int getPreorderByAccount(String preorder_master_account) {
		System.out.println("Here is PreorderServices.get method...");

		preorderinfos = preorderMapper
				.getPreordersByAccount(preorder_master_account);

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
	public int getPreorderById(int preorder_order_id) {
		System.out.println("Here is PreorderServices.get method...");

		preorderinfos = preorderMapper.getPreodersByOrderId(preorder_order_id);

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
