package com.bbcall.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bbcall.mybatis.table.Preorder;

public interface PreorderMapper {

	// 添加预处理订单
	public void addPreorder(Preorder preorder);

	// 删除预处理订单
	public void deletePreorder(int preorder_id);

	// 通过订单编号删除预订单
	public void deletePreorderByOrderId(int preorder_order_id);

	// 通过师傅账户取得所有预处理订单
	public List<Preorder> getPreordersByAccount(String preorder_master_account);

	// 通过订单编号取得预处理订单
	public List<Preorder> getPreodersByOrderId(int preorder_order_id);

	// 通过预处理订单编号取得预处理订单
	public Preorder getPreorderById(int preorder_id);

	// 通过师傅账户和订单编号取得特定的预处理订单
	public Preorder getPreoder(
			@Param("preorder_master_account") String preorder_master_account,
			@Param("preorder_order_id") int preorder_order_id);
}
