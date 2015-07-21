package com.bbcall.struts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.dao.ReferdocMapper;
import com.bbcall.mybatis.table.Referdoc;
import com.github.pagehelper.PageHelper;

@Service("referdocServices")
public class ReferdocServices {

	@Autowired
	private ReferdocMapper referdocMapper;

	private Referdoc referdocinfo;

	private List<Referdoc> referdocinfos;

	// ################################################################################
	// ## Add Referdoc services
	// ## 新增参考数据
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) referdoc_type
	// ## (2) referdoc_price
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return referdocinfo:
	// ## (1) referdocinfo
	// ##
	// ################################################################################

	public int addReferdoc(String referdoc_type, String referdoc_parentno,
			int referdoc_level, double referdoc_price, String referdoc_flag,
			Integer pagenum) {
		// TODO Auto-generated method stub

		int parentno = 0;
		if (referdoc_parentno != null) {
			parentno = Integer.parseInt(referdoc_parentno);
		}
		if (referdoc_type.equals("")) {
			return ResultCode.REFERDOC_ADD_FAILED;
		} else {
			int checkResult = chkReferType(referdoc_type, parentno);

			if (checkResult == ResultCode.REFERDOC_TYPE_NOTEXIST) {

				Referdoc referdoc = new Referdoc();
				referdoc.setReferdoc_type(referdoc_type);
				referdoc.setReferdoc_parentno(parentno);
				referdoc.setReferdoc_level(referdoc_level);
				referdoc.setReferdoc_price(referdoc_price);
				referdoc.setReferdoc_flag(Boolean.parseBoolean(referdoc_flag));

				referdocMapper.addReferdoc(referdoc);

				// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
				if (pagenum == null || pagenum == 0)
					pagenum = 1;

				// PageHelper.startPage(PageNum, PageSize)
				// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
				PageHelper.startPage(pagenum, 10);

				referdocinfos = referdocMapper
						.getReferdoclist(referdoc_parentno);
				return ResultCode.SUCCESS;
			} else {
				return checkResult;
			}
		}

	}

	// ################################################################################
	// ## Update Referdoc services
	// ## 更新参考数据
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) referdoc_id
	// ## (2) referdoc_type
	// ## (3) referdoc_price
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return referdocinfo:
	// ## (1) referdocinfo
	// ##
	// ################################################################################

	public int updateReferdoc(int referdoc_id, String referdoc_parentno,
			double referdoc_price, String referdoc_flag, Integer pagenum) {

		Referdoc referdoc = referdocMapper.getReferdoc(referdoc_id);

		referdoc.setReferdoc_price(referdoc_price);
		referdoc.setReferdoc_flag(Boolean.parseBoolean(referdoc_flag));

		referdocMapper.updateReferdoc(referdoc);

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		referdocinfos = referdocMapper.getReferdoclist(referdoc_parentno);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Delete Referdoc services
	// ## 删除参考数据
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) referdoc_id
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return referdocinfos:
	// ## (1) referdocinfos
	// ##
	// ################################################################################

	public int deleteReferdoc(int referdoc_id, Integer pagenum) {

		List<Referdoc> redocs = referdocMapper
				.getReferdoclistByParent(referdoc_id);

		if (redocs != null) {
			for (int i = 0; i < redocs.size(); i++) {
				referdocMapper.deleteReferdoc(redocs.get(i).getReferdoc_id());
			}
		}

		String parent_code = String.valueOf(referdocMapper.getReferdoc(referdoc_id)
				.getReferdoc_parentno());

		referdocMapper.deleteReferdoc(referdoc_id);

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		referdocinfos = referdocMapper.getReferdoclist(parent_code);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Referdoc services
	// ## 取得参考数据
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) referdoc_id
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return referdocinfo:
	// ## (1) referdocinfo
	// ##
	// ################################################################################

	public int getReferdoc(int referdoc_id) {

		referdocinfo = referdocMapper.getReferdoc(referdoc_id);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Referdoc services
	// ## 取得参考数据
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) referdoc_type
	// ## (2) referdoc_parentno
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return referdocinfo:
	// ## (1) referdocinfo
	// ##
	// ################################################################################

	public int getReferdocByType(String referdoc_type, int referdoc_parentno) {

		referdocinfo = referdocMapper.getReferdocByType(referdoc_type,
				referdoc_parentno);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Referdoc listservices
	// ## 取得参考数据列表
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
	// ## 4. Return referdocinfos:
	// ## (1) referdocinfos
	// ##
	// ################################################################################

	public int getReferdoclist(String referdoc_parentno, Integer pagenum) {

		// 当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;

		// PageHelper.startPage(PageNum, PageSize)
		// 获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 10);

		referdocinfos = referdocMapper.getReferdoclist(referdoc_parentno);

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Parent Referdoc listservices
	// ## 取得一级选项参考数据列表
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
	// ## 4. Return referdocinfos:
	// ## (1) referdocinfos
	// ##
	// ################################################################################

	public int getParentReferdoclist() {

		referdocinfos = referdocMapper.getParentReferdoc();

		return ResultCode.SUCCESS;
	}

	// ################################################################################
	// ## Get Child Referdoc listservices
	// ## 取得二级选项参考数据列表
	// ##==============================================================================
	// ## Instructions
	// ##
	// ##------------------------------------------------------------------------------
	// ## 1. Require parameters:
	// ## (1) referdoc_parentno
	// ##
	// ##------------------------------------------------------------------------------
	// ## 2. Optional parameters: NONE
	// ##
	// ##------------------------------------------------------------------------------
	// ## 3. Return parameters:
	// ## (4) ResultCode.SUCCESS
	// ##
	// ##------------------------------------------------------------------------------
	// ## 4. Return referdocinfos:
	// ## (1) referdocinfos
	// ##
	// ################################################################################

	public int getChildReferdoclist(int referdoc_parentno) {

		referdocinfos.clear();
		referdocinfos.add(referdocMapper.getReferdoc(referdoc_parentno));

		referdocinfos.addAll(referdocMapper
				.getReferdoclistByParent(referdoc_parentno));

		return ResultCode.SUCCESS;
	}

	public int chkReferType(String referdoc_type, int referdoc_parentno) {

		referdocinfo = referdocMapper.getReferdocByType(referdoc_type,
				referdoc_parentno);

		if (null != referdocinfo) {
			return ResultCode.REFERDOC_TYPE_TEXIST;
		} else {
			return ResultCode.REFERDOC_TYPE_NOTEXIST;
		}
	}

	// ###################
	// 返回用户信息, 用于json返回
	// ###################

	public Referdoc referdocinfo() {

		return referdocinfo;
	}

	public List<Referdoc> referdocinfos() {

		return referdocinfos;
	}
}
