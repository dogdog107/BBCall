package com.bbcall.mybatis.dao;

import java.util.List;

import com.bbcall.mybatis.table.AccessList;

public interface AccessListMapper {
	
	// 查找全部权限
	public List<AccessList> getAll();
	
	// 查找全部权限名
	public List<String> getAllAccessName();
}
