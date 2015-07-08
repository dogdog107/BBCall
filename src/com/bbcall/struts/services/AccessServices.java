package com.bbcall.struts.services;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.dao.AccessGroupMapper;
import com.bbcall.mybatis.table.AccessGroup;

@Service("accessServices")
public class AccessServices {
	
	@Autowired
	private AccessGroupMapper accessGroupMapper;
	
	
	// ###################
	// ## 检测用户权限
	// ###################

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
}
