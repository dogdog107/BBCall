package com.bbcall.mybatis.table;

import java.sql.Timestamp;

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
	private Timestamp blacklist_create_time;
	private String blacklist_master_name;
	private String blacklist_master_pic;
	private String blacklist_master_skill;
	private double blacklist_master_grade;

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

	public Timestamp getBlacklist_create_time() {
		return blacklist_create_time;
	}

	public void setBlacklist_create_time(Timestamp blacklist_create_time) {
		this.blacklist_create_time = blacklist_create_time;
	}

	public String getBlacklist_master_name() {
		return blacklist_master_name;
	}

	public void setBlacklist_master_name(String blacklist_master_name) {
		this.blacklist_master_name = blacklist_master_name;
	}

	public String getBlacklist_master_pic() {
		return blacklist_master_pic;
	}

	public void setBlacklist_master_pic(String blacklist_master_pic) {
		this.blacklist_master_pic = blacklist_master_pic;
	}

	public String getBlacklist_master_skill() {
		return blacklist_master_skill;
	}

	public void setBlacklist_master_skill(String blacklist_master_skill) {
		this.blacklist_master_skill = blacklist_master_skill;
	}

	public double getBlacklist_master_grade() {
		return blacklist_master_grade;
	}

	public void setBlacklist_master_grade(double blacklist_master_grade) {
		this.blacklist_master_grade = blacklist_master_grade;
	}

	
}
