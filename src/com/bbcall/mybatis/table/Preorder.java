package com.bbcall.mybatis.table;

import java.sql.Timestamp;

//* Table preorder's sql command:
//CREATE TABLE Preorder (
//	preorder_id INT NOT NULL auto_increment PRIMARY KEY,
//preorder_master_account VARCHAR (30),	
//preorder_create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//preorder_price DECIMAL(10,2),
//preorder_order_id INT NOT NULL,
//preorder_master_id INT,
//preorder_master_name VARCHAR (30),
//preorder_master_skill text,
//preorder_master_grade INT
//) DEFAULT CHARSET = utf8;

public class Preorder {

	private int preorder_id;
	private String preorder_master_account;
	private Timestamp preorder_create_time;
	private double preorder_price;
	private int preorder_order_id;
	private int preorder_master_id;
	private String preorder_master_name;
	private String preorder_master_pic;
	private String preorder_master_skill;
	private double preorder_master_grade;

	public int getPreorder_id() {
		return preorder_id;
	}

	public void setPreorder_id(int preorder_id) {
		this.preorder_id = preorder_id;
	}

	public String getPreorder_master_account() {
		return preorder_master_account;
	}

	public void setPreorder_master_account(String preorder_master_account) {
		this.preorder_master_account = preorder_master_account;
	}

	public Timestamp getPreorder_create_time() {
		return preorder_create_time;
	}

	public void setPreorder_create_time(Timestamp preorder_create_time) {
		this.preorder_create_time = preorder_create_time;
	}

	public double getPreorder_price() {
		return preorder_price;
	}

	public void setPreorder_price(double preorder_price) {
		this.preorder_price = preorder_price;
	}

	public int getPreorder_order_id() {
		return preorder_order_id;
	}

	public void setPreorder_order_id(int preorder_order_id) {
		this.preorder_order_id = preorder_order_id;
	}

	public int getPreorder_master_id() {
		return preorder_master_id;
	}

	public void setPreorder_master_id(int preorder_master_id) {
		this.preorder_master_id = preorder_master_id;
	}

	public String getPreorder_master_name() {
		return preorder_master_name;
	}

	public void setPreorder_master_name(String preorder_master_name) {
		this.preorder_master_name = preorder_master_name;
	}

	public String getPreorder_master_skill() {
		return preorder_master_skill;
	}

	public void setPreorder_master_skill(String preorder_master_skill) {
		this.preorder_master_skill = preorder_master_skill;
	}

	public double getPreorder_master_grade() {
		return preorder_master_grade;
	}

	public void setPreorder_master_grade(double preorder_master_grade) {
		this.preorder_master_grade = preorder_master_grade;
	}

	public String getPreorder_master_pic() {
		return preorder_master_pic;
	}

	public void setPreorder_master_pic(String preorder_master_pic) {
		this.preorder_master_pic = preorder_master_pic;
	}
	
	

}
