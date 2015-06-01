package com.bbcall.mybatis.dao;

import java.util.List;

import com.bbcall.mybatis.table.Orderlist;

public interface OrderlistMapper {

	// 添加新订单
	public void addOrder(Orderlist order);

	// 通过用户账号取得未完成订单列表
	public List<Orderlist> getUnOrdersByUserAccount(String order_user_account);

	// 通过用户账号取得已完成订单列表
	public List<Orderlist> getComOrdersByUserAccount(String order_user_account);

	// 通过师傅账号取得未完成订单列表
	public List<Orderlist> getUnOrdersByMasterAccount(String order_master_account);

	// 通过师傅账号取得已完成订单列表
	public List<Orderlist> getComOrdersByMasterAccount(String order_master_account);

	// 通过id取得特定订单
	public Orderlist getOrder(int order_id);

	// 获取所有未完成订单列表
	public List<Orderlist> getUnOrders();

	// 获取所有已完成订单列表
	public List<Orderlist> getComOrders();

	// 更新订单详情
	public void updateOrder(Orderlist order);

	// 删除订单
	public void deleteOrder(int order_id);

}
