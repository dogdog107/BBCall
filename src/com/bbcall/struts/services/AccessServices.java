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

@Service("accessServices")
public class AccessServices {
	
	@Autowired
	private AccessGroupMapper accessGroupMapper;
	@Autowired
	private AccessListMapper accessListMapper;
	
	private AccessGroup accessGroup;
	private List<AccessGroup> AGAccessList;
	

	/**
	 * checkUserAccess
	 * 检测用户权限
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
			for (Iterator<AccessList> it = tempAccessGroup.getAccesslist().iterator(); it
					.hasNext();) {
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
	 * getAccessByAccessGroup
	 * 根据给定的accessgroup查询权限列表
	 * 
	 * @param accessgroup
	 * @return ResultCode
	 */
	public int getAccessByAccessGroup(String accessgroup) {
		if (Tools.isEmpty(accessgroup))
			return ResultCode.REQUIREINFO_NOTENOUGH;
		accessGroup = accessGroupMapper
				.getAccessByAccessGroupName(accessgroup);
		if (accessGroup != null) {
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.ACCESSGROUP_ERROR;
		}
	}

	/**
	 * getAccessGroupName
	 * 得到权限组列表
	 * 
	 * @return List<AccessGroup>
	 */
	public List<AccessGroup> getAccessGroupName() {
		return accessGroupMapper.getAll();
	}

	/**
	 * getAccessList
	 * 得到权限名以及描述的列表
	 * 
	 * @return List<AccessList>
	 */
	public List<AccessList> getAccessList() {
		return accessListMapper.getAll();
	}
	
	/**
	 * getAccessNameList
	 * 得到权限名字的列表
	 * 
	 * @return List<String>
	 */
	public List<String> getAccessNameList() {
		return accessListMapper.getAllAccessName();
	}

	/**
	 * getter & setter
	 * @return
	 */
	public List<AccessGroup> getAGAccessList() {
		return AGAccessList;
	}

	public AccessGroup getAccessGroup() {
		return accessGroup;
	}
}
