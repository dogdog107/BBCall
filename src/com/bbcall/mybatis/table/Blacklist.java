package com.bbcall.mybatis.table;

//* Table Blacklist's sql command:
//CREATE TABLE Blacklist (
//	blacklist_id INT NOT NULL auto_increment PRIMARY KEY,
//blacklist_user_id  INT NOT NULL,
//blacklist_master_id  INT NOT NULL,
//blacklist_order_id INT NOT NULL
//) DEFAULT CHARSET = utf8;	

public class Blacklist {

	private int blacklist_id;
	private int blacklist_user_id;
	private int blacklist_master_id;
	private int blacklist_order_id;

	public int getBlacklist_id() {
		return blacklist_id;
	}

	public void setBlacklist_id(int blacklist_id) {
		this.blacklist_id = blacklist_id;
	}

	public int getBlacklist_user_id() {
		return blacklist_user_id;
	}

	public void setBlacklist_user_id(int blacklist_user_id) {
		this.blacklist_user_id = blacklist_user_id;
	}

	public int getBlacklist_master_id() {
		return blacklist_master_id;
	}

	public void setBlacklist_master_id(int blacklist_master_id) {
		this.blacklist_master_id = blacklist_master_id;
	}

	public int getBlacklist_order_id() {
		return blacklist_order_id;
	}

	public void setBlacklist_order_id(int blacklist_order_id) {
		this.blacklist_order_id = blacklist_order_id;
	}

}
