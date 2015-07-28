package com.bbcall.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bbcall.mybatis.table.AccessGroup;
import com.bbcall.mybatis.table.AccessGroup_AccessList;

public interface AccessGroupMapper {
	
	// 增加权限组
	public void addAccessGroup(AccessGroup accessgroup);
	
	// 为权限组增加权限
	public void addAccessByAccessGroup(List<AccessGroup_AccessList> accessgroup_accesslist);

	// 查找指定权限组下的权限
	public AccessGroup getAccessByAccessGroupName(String accessgroup_name);
	
	// 查找权限组
	public AccessGroup getAccessGroupByName(String accessgroup_name);
	
	// 查找全部权限组与权限说明
	public List<AccessGroup> getAll();
	
	// 删除指定的权限组
	public void deleteAccessGroupName(String accessgroup_name);
	
	// 删除指定的权限组
	public void deleteAccessGroupById(Integer accessgroup_id);
	
	// 删除指定权限组中的全部权限
	public void deleteAllAccessUnderGroupId(Integer accessgroup_id);
	
	// 根据权限list权限组名
//	public List<AccessGroup> getAccess(String accessgroup_access);

	// 查找全部权限组名
//	public List<String> getAccessGroupNameList();
	
	// 在所有权限组里删除指定的权限
//	public void deleteAccess(String accessgroup_access);
	
	// 在指定的权限组里删除指定权限
//	public void deleteAccessByAccessGroupName(
//			@Param("accessgroup_name") String accessgroup_name,
//			@Param("accessgroup_access") String accessgroup_access);
}
