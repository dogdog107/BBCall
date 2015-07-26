package com.bbcall.struts.services;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.dao.AccessGroupMapper;
import com.bbcall.mybatis.dao.AccessListMapper;
import com.bbcall.mybatis.table.AccessGroup;
import com.bbcall.mybatis.table.AccessList;
import com.github.pagehelper.PageHelper;

@Service("accessServices")
public class AccessServices {

	@Autowired
	private AccessGroupMapper accessGroupMapper;
	@Autowired
	private AccessListMapper accessListMapper;

	private AccessGroup accessGroup;
	private List<AccessGroup> AGAccessList;

	/**
	 * checkUserAccess 检测用户权限
	 * 
	 * @param UserAccessGroup
	 * @param requireAccess
	 * @return
	 */
	public int checkUserAccess(String UserAccessGroup, String requireAccess) {
		System.out.println("Here is UserServices.checkUserAccess method...");
		if (Tools.isEmpty(UserAccessGroup, requireAccess))
			return ResultCode.REQUIREINFO_NOTENOUGH;
		AccessGroup tempAccessGroup = accessGroupMapper
				.getAccessByAccessGroupName(UserAccessGroup);
		System.out.println(requireAccess);
		if (tempAccessGroup != null) {
			for (Iterator<AccessList> it = tempAccessGroup.getAccesslist()
					.iterator(); it.hasNext();) {
				if (it.next().getAccesslist_name().equals(requireAccess)) {
					return ResultCode.SUCCESS;
				}
			}
			return ResultCode.ACCESS_REJECT;
		} else {
			return ResultCode.ACCESSGROUP_ERROR;
		}
	}

	/**
	 * checkAccessGroupByName 檢查權限組是否存在
	 * @param accessgroup_name
	 * @return
	 */
	public int checkAccessGroupByName(String accessgroup_name) {
		if (Tools.isEmpty(accessgroup_name))
			return ResultCode.REQUIREINFO_NOTENOUGH;
		if (accessGroupMapper.getAccessGroupByName(accessgroup_name) != null) {
			return ResultCode.ACCESSGROUP_EXIST;
		} else {
			return ResultCode.SUCCESS;
		}
	}
	
	/**
	 * addAccessGroup 增加权限组
	 * 
	 * @param accessgroup_name
	 * @param accessgroup_description
	 * @return
	 */
	public int addAccessGroup(String accessgroup_name, String accessgroup_description){
		if (Tools.isEmpty(accessgroup_name, accessgroup_description))
			return ResultCode.REQUIREINFO_NOTENOUGH;
		// 检测权限组名字是否存在
		if (accessGroupMapper.getAccessGroupByName(accessgroup_name) != null)
			return ResultCode.ACCESSGROUP_EXIST;
		
		AccessGroup tempAG = new AccessGroup();
		tempAG.setAccessgroup_name(accessgroup_name);
		tempAG.setAccessgroup_description(accessgroup_description);
		accessGroupMapper.addAccessGroup(tempAG);
		return ResultCode.SUCCESS;
	}
	
	/**
	 * getAccessGroupByName 查看權限組
	 * @param accessgroup_name
	 * @return
	 */
	public int getAccessGroupByName(String accessgroup_name) {
		if (Tools.isEmpty(accessgroup_name))
			return ResultCode.REQUIREINFO_NOTENOUGH;
		accessGroup = accessGroupMapper.getAccessGroupByName(accessgroup_name);
		if (accessGroup != null) {
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.ACCESSGROUP_ERROR;
		}
	}
	
	/**
	 * getAccessByAccessGroupName 根据给定的accessgroup_name查询权限列表
	 * 
	 * @param accessgroup_name
	 * @return ResultCode
	 */
	public int getAccessByAccessGroupName(String accessgroup_name) {
		if (Tools.isEmpty(accessgroup_name))
			return ResultCode.REQUIREINFO_NOTENOUGH;
		accessGroup = accessGroupMapper.getAccessByAccessGroupName(accessgroup_name);
		if (accessGroup != null) {
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.ACCESSGROUP_ERROR;
		}
	}
	
	/**
	 * getAccessGroupName 得到权限组列表
	 * 
	 * @return List<AccessGroup>
	 */
	public List<AccessGroup> getAccessGroupName(Integer pagenum) {
		//当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;
		
	    //PageHelper.startPage(PageNum, PageSize) 
		//获取第1页，10条内容，当PageSize=0时会查询出全部的结果
	    PageHelper.startPage(pagenum, 20);
		return accessGroupMapper.getAll();
	}

	/**
	 * getAccessList 得到权限名以及描述的列表
	 * 
	 * @return List<AccessList>
	 */
	public List<AccessList> getAccessList() {
		return accessListMapper.getAll();
	}

	/**
	 * getAccessNameList 得到权限名字的列表
	 * 
	 * @return List<String>
	 */
	public List<String> getAccessNameList() {
		return accessListMapper.getAllAccessName();
	}
	
	/**
	 * deleteAccessGroupById 刪除權限組
	 * 
	 * @param accessgroup_id
	 * @return ResultCode
	 */
	public int deleteAccessGroupById(Integer accessgroup_id) {
		if (accessgroup_id == null || accessgroup_id == 0)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		accessGroupMapper.deleteAccessGroupById(accessgroup_id);
		return ResultCode.SUCCESS;
	}
	
	/**
	 * getter & setter
	 * 
	 * @return
	 */
	public List<AccessGroup> getAGAccessList() {
		return AGAccessList;
	}

	public AccessGroup getAccessGroup() {
		return accessGroup;
	}
}
