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

		List<AccessGroup> accessGroupAccess = accessGroupMapper
				.getAccessByAccessGroupName(UserAccessGroup);
		System.out.println(requireAccess);
		if (accessGroupAccess.size() > 0) {
			for (Iterator<AccessGroup> it = accessGroupAccess.iterator(); it
					.hasNext();) {
				if (it.next().getAccessgroup_access().equals(requireAccess)) {
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
		AGAccessList = accessGroupMapper
				.getAccessByAccessGroupName(accessgroup);
		if (AGAccessList.size() > 0) {
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.ACCESSGROUP_ERROR;
		}
	}

	/**
	 * getAccessGroupName
	 * 得到权限组列表
	 * 
	 * @return List<String>
	 */
	public List<String> getAccessGroupName() {
		return accessGroupMapper.getAccessGroupName();
	}

	/**
	 * getAccessList
	 * 得到权限列表
	 * 
	 * @return List<String>
	 */
	public List<String> getAccessList() {
		return accessListMapper.getAllAccessName();
	}

	/**
	 * getter & setter
	 * @return
	 */
	public List<AccessGroup> getAGAccessList() {
		return AGAccessList;
	}
}
