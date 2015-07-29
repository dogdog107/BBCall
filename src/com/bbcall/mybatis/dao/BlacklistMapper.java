package com.bbcall.mybatis.dao;

import java.util.List;

import com.bbcall.mybatis.table.Blacklist;

public interface BlacklistMapper {

	// 添加黑名单
	public void addBlacklist(Blacklist blacklist);

	// 删除黑名单
	public void deleteBlacklistById(int blacklist_id);

	// 取得详细黑名单
	public Blacklist getBlacklistById(int blacklist_id);

	// 取得详细黑名单
	public Blacklist getBlacklistByOrder(int blacklist_order_id);

	// 获取用户的黑名单列表
	public List<Blacklist> getBlacklistByUser(int blacklist_user_id);

	// 获取关于师傅的黑名单列表
	public List<Blacklist> getBlacklistByMaster(int blacklist_master_id);

	// 获取所有的黑名单列表
	public List<Blacklist> getBlacklist();
}
