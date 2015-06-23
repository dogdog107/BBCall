package com.bbcall.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bbcall.mybatis.table.Orderlist;

public interface OrderlistMapper {

	// 添加新订单
	public void addOrder(Orderlist order);

	// 通过用户账号取得未完成订单列表
	public List<Orderlist> getUnOrdersByUserAccount(String order_user_account);

	// 通过用户账号取得已完成订单列表
	public List<Orderlist> getComOrdersByUserAccount(String order_user_account);

	// 通过师傅账号/技能类型取得未完成订单列表
	public List<Orderlist> getUnOrdersByMasterSkill(
			@Param("order_type") String order_type,
			@Param("order_master_account") String order_master_account);

	// 通过师傅账号/技能类型/订单地区取得未完成订单列表 (按发布时间排序)
	public List<Orderlist> getUnOrdersByMasterLocation(
			@Param("order_type") String order_type,
			@Param("order_book_location") String order_book_location,
			@Param("order_master_account") String order_master_account);

	// 通过师傅账号/技能类型/订单地区取得未完成订单列表 (按工作截止时间排序)
	public List<Orderlist> getUnOrdersByBookTime(
			@Param("order_type") String order_type,
			@Param("order_book_location") String order_book_location,
			@Param("order_master_account") String order_master_account);

	// 通过师傅账号取得已完成订单列表
	public List<Orderlist> getComOrdersByMasterAccount(
			String order_master_account);

	// 通过用户账号取得正在处理中的订单
	public List<Orderlist> getProOrdersByUserAccount(String order_user_account);

	// 通过师傅账号取得正在处理订单列表
	public List<Orderlist> getProOrdersByMasterAccount(
			String order_master_account);

	// 通过id取得特定订单
	public Orderlist getOrder(int order_id);

	// 获取所有未完成订单列表
	public List<Orderlist> getUnOrders();

	// 获取所有已完成订单列表
	public List<Orderlist> getComOrders();

	// 获取所有正在处理订单列表
	public List<Orderlist> getProOrders();

	// 获取所有洗衣订单
	public List<Orderlist> getWashOrderlist();

	// 通过订单状态，师傅账号获取洗衣订单
	public List<Orderlist> getWashOrders(
			@Param("order_status") int order_status,
			@Param("order_master_account") String order_master_account);

	// 通过订单状态获取洗衣订单
	public List<Orderlist> getWashOrderByStatus(int order_status);

	// 通过订单状态，师傅账号获取洗衣订单
	public List<Orderlist> getWashOrderByMaster(String order_master_account);

	// 获取所有洗衣订单按照订单状态排序
	public List<Orderlist> getWashOrderlistByStatus();

	// 获取所有洗衣订单按照师傅账号排序
	public List<Orderlist> getWashOrderlistByMaster();

	// 更新订单详情
	public void updateOrder(Orderlist order);

	// 当用户确认后，更改订单师傅账户和订单状态
	public void updateOrderAsMasterAccount(
			@Param("order_master_account") String order_master_account,
			@Param("order_price") double order_price,
			@Param("order_id") int order_id);

	// 删除订单
	public void deleteOrder(int order_id);

}
