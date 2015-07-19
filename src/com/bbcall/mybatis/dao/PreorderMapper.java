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
	public List<Preorder> getPreordersByAccount(int preorder_master_id);

	// 通过订单编号取得预处理订单 评分降序
	public List<Preorder> getPreodersByParm(@Param("preorder_order_id") int preorder_order_id,
			@Param("sortparm") String sortparm);

//	// 通过订单编号取得预处理订单 价格升序
//	public List<Preorder> getPreodersByOrder(int preorder_order_id);

	// 通过预处理订单编号取得预处理订单
	public Preorder getPreorderById(int preorder_id);

	// 通过师傅账户和订单编号取得特定的预处理订单
	public Preorder getPreoder(
			@Param("preorder_master_id") int preorder_master_id,
			@Param("preorder_order_id") int preorder_order_id);
}
