package com.bbcall.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bbcall.mybatis.table.Referdoc;

public interface ReferdocMapper {

	// 添加参考数据
	public void addReferdoc(Referdoc referdoc);

	// 通过订单类ID得对应的参考数据
	public Referdoc getReferdoc(int referdoc_id);

	// 通过订单类型取得对应的参考数据
	public Referdoc getReferdocByType(
			@Param("referdoc_type") String referdoc_type,
			@Param("referdoc_parentno") int referdoc_parentno);

	// 取得所有的参考数据列表
	public List<Referdoc> getReferdoclist(@Param("referdoc_parentno") String referdoc_parentno);

	// 取得所有的一级选项参考数据列表
	public List<Referdoc> getParentReferdoc();

	// 通过一级选项号码取得所有的二级选项参考数据列表
	public List<Referdoc> getReferdoclistByParent(int referdoc_parentno);

	// 更新参考数据
	public void updateReferdoc(Referdoc referdoc);

	// 删除参考数据
	public void deleteReferdoc(int referdoc_id);

}
