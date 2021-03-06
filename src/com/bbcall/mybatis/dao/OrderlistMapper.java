package com.bbcall.mybatis.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bbcall.mybatis.table.Orderlist;

public interface OrderlistMapper {

	// 添加新订单
	public void addOrder(Orderlist order);

	// 通过用户账号取得未完成订单列表
	public List<Orderlist> getUnOrdersByUserAccount(
			@Param("order_user_id") int order_user_id);

	// 通过用户账号取得已完成订单列表
	public List<Orderlist> getComOrdersByUserAccount(
			@Param("order_user_id") int order_user_id);

	// 通过订单类型、师傅账号、技能类型取得未完成订单列表
	public List<Orderlist> getUnOrdersBySkill(
			@Param("ordertypelist") String[] ordertypelist,
			@Param("order_master_id") int order_master_id);
	
	// 通过订单类型、师傅账号、技能类型取得未完成订单列表
		public List<Orderlist> getUnOrdersBySkill2(
				@Param("ordertypelist") String[] ordertypelist,
				@Param("order_master_id") int order_master_id,
				@Param("order_status") int order_status);

	// 通过师傅账号/技能类型/订单地区取得未完成订单列表 (按发布时间排序)
	public List<Orderlist> getUnOrdersByMasterLocation(
			@Param("ordertypelist") String[] ordertypelist,
//			@Param("order_book_location_code") String order_book_location_code,
			@Param("order_book_location_code") String[] order_book_location_code,
			@Param("order_status") String order_status,
			@Param("sortparm") String sortparm,
			@Param("order_master_id") int order_master_id);

	// 通过师傅账号取得已完成订单列表
	public List<Orderlist> getComOrdersByMasterAccount(
			@Param("order_master_id") int order_master_id);

	// 通过用户账号取得正在处理中的订单
	public List<Orderlist> getProOrdersByUserAccount(
			@Param("order_user_id") int order_user_id);

	// 通过师傅账号取得正在处理订单列表
	public List<Orderlist> getProOrdersByMasterAccount(
			@Param("order_master_id") int order_master_id);

	// 通过id取得特定订单
	public Orderlist getOrder(int order_id);

	// 获取所有未完成订单列表
	public List<Orderlist> getUnOrders();

	// 获取所有未完成订单列表
	public List<Orderlist> getUnOrders2();

	// 获取所有未完成订单列表
	public List<Orderlist> getAucOrders();

	// 获取所有已完成订单列表
	public List<Orderlist> getComOrders();

	// 获取所有已接收订单列表
	public List<Orderlist> getRecOrders();

	// 获取所有正在清洗订单列表
	public List<Orderlist> getWasOrders();

	// 获取所有正在配送订单列表
	public List<Orderlist> getDelOrders();

	// 获取所有正在处理订单列表
	public List<Orderlist> getProOrders();

	// 获取所有洗衣订单
	public List<Orderlist> getWashOrderlist();

	// 通过订单状态，师傅账号获取洗衣订单
	public List<Orderlist> getWashOrders(
			@Param("order_status") int order_status,
			@Param("order_master_id") int order_master_id);

	// 通过订单状态获取洗衣订单
	public List<Orderlist> getWashOrderByStatus(int order_status);

	// 通过订单状态，师傅账号获取洗衣订单
	public List<Orderlist> getWashOrderByMaster(int order_master_id);

	// 获取所有洗衣订单按照订单状态排序
	public List<Orderlist> getWashOrderlistByParm(
			@Param("order_status") String order_status,
			@Param("order_section") String order_section,
			@Param("order_master_name") String order_master_name,
			@Param("order_book_location_code") String order_book_location_code,
			@Param("order_id") String order_id,
			@Param("order_time") String order_time,
			@Param("order_col") String order_col,
			@Param("order_value") String order_value);
	
	// 获取所有洗衣订单按照订单状态排序
	public List<Orderlist> getOrderlistByParm(
			@Param("order_status") String order_status,
			@Param("order_master_name") String order_master_name,
			@Param("order_book_location_code") String order_book_location_code,
			@Param("order_id") String order_id,
			@Param("order_time") String order_time,
			@Param("order_col") String order_col,
			@Param("order_value") String order_value);

	// 更新订单详情
	public void updateOrder(Orderlist order);

	// 当用户确认后，更改订单师傅账户和订单状态
	public void updateOrderAsMasterAccount(
			@Param("order_master_id") int order_master_id,
			@Param("order_master_name") String order_master_name,
			@Param("order_price") double order_price,
			@Param("order_id") int order_id);

	// 删除订单
	public void deleteOrder(int order_id);

	// 通过师傅账号取得订单列表
	public List<Orderlist> getOrdersByMaster(
			@Param("order_master_id") int order_master_id);

	// 通过师傅账号取得订单列表
	public List<Orderlist> getOrdersByMId(
			@Param("order_master_id") int order_master_id,
			@Param("order_status") int order_status);

	// 通过师傅账户和订单状态取得订单列表
	public List<Orderlist> getOrders(
			@Param("order_status") String order_status,
			@Param("order_master_id") int order_master_id,
			@Param("order_type_code") String order_type_code);

	// 更改订单为完成状态
	public void completeOrder(@Param("order_score") double order_score,
			@Param("order_evaluation") String order_evaluation,
			@Param("order_id") int order_id,
			@Param("order_end_time") Timestamp order_end_time);

	public void change(@Param("order_id") int order_id,
			@Param("order_status") int order_status,
			@Param("order_remark") String order_remark);

	public void changeForMaster(@Param("order_id") int order_id,
			@Param("order_remark") String order_remark,
			@Param("order_master_id") String order_master_id,
			@Param("order_master_name") String order_master_name);

}
