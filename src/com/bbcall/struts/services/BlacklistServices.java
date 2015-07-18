package com.bbcall.struts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.dao.BlacklistMapper;
import com.bbcall.mybatis.dao.UserMapper;
import com.bbcall.mybatis.table.Blacklist;
import com.github.pagehelper.PageHelper;

@Service("blacklistServices")
public class BlacklistServices {

	@Autowired
	private BlacklistMapper blacklistMapper;

	@Autowired
	private UserMapper userMapper;

	public Blacklist blacklistinfo;
	public List<Blacklist> blacklistinfos;

	// ################################################################################
	// ## Add Blacklist services
	// ## 新增黑名单
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) order_book_time
	// ## (2) order_book_location
	// ## (3) order_contact_mobile
	// ## (4) order_contact_name
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return blacklistinfos:
	// ## (1) blacklistinfos
	// ##
	// ################################################################################
	public int addBlacklist(int blacklist_user_id, int blacklist_master_id,
			int blacklist_order_id, Integer pagenum) {

		System.out.println("Here is BlacklistServices.add method...");
		Blacklist blacklist = new Blacklist();
		blacklist.setBlacklist_user_id(blacklist_user_id);
		blacklist.setBlacklist_master_id(blacklist_master_id);
		blacklist.setBlacklist_order_id(blacklist_order_id);

		blacklistMapper.addBlacklist(blacklist);

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		blacklistinfos = blacklistMapper.getBlacklistByUser(blacklist_user_id);

		return ResultCode.SUCCESS;
	}

	public int deleteBlacklist(int blacklist_id, int blacklist_user_id,
			Integer pagenum) {

		System.out.println("Here is BlacklistServices.delete method...");

		blacklistMapper.deleteBlacklistById(blacklist_id);

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		blacklistinfos = blacklistMapper.getBlacklistByUser(blacklist_user_id);

		return ResultCode.SUCCESS;
	}

	public int getBlacklistForCustomer(int user_id, Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		blacklistinfos = blacklistMapper.getBlacklistByUser(user_id);

		return ResultCode.SUCCESS;
	}

	public int getBlacklistForMaster(int user_id, Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		blacklistinfos = blacklistMapper.getBlacklistByMaster(user_id);

		return ResultCode.SUCCESS;
	}

	public int getBlacklistForAdm(Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		blacklistinfos = blacklistMapper.getBlacklist();

		return ResultCode.SUCCESS;
	}

	public int getBlacklistById(int blacklist_id) {

		blacklistinfo = blacklistMapper.getBlacklistById(blacklist_id);

		return ResultCode.SUCCESS;
	}

	public Blacklist blacklistinfo() {

		return blacklistinfo;
	}

	public List<Blacklist> blacklistinfos() {

		return blacklistinfos;
	}
}
