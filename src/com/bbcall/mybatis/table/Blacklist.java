package com.bbcall.mybatis.table;

public class Blacklist {

	private int blacklist_id;
	private String blacklist_user_account;
	private String blacklist_master_account;
	private int blacklist_order_id;

	public int getBlacklist_id() {
		return blacklist_id;
	}

	public void setBlacklist_id(int blacklist_id) {
		this.blacklist_id = blacklist_id;
	}

	public String getBlacklist_user_account() {
		return blacklist_user_account;
	}

	public void setBlacklist_user_account(String blacklist_user_account) {
		this.blacklist_user_account = blacklist_user_account;
	}

	public String getBlacklist_master_account() {
		return blacklist_master_account;
	}

	public void setBlacklist_master_account(String blacklist_master_account) {
		this.blacklist_master_account = blacklist_master_account;
	}

	public int getBlacklist_order_id() {
		return blacklist_order_id;
	}

	public void setBlacklist_order_id(int blacklist_order_id) {
		this.blacklist_order_id = blacklist_order_id;
	}

}
