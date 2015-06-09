package com.bbcall.mybatis.table;

import java.sql.Timestamp;

//* Table preorder's sql command:
//CREATE TABLE Preorder (
//	preorder_id INT NOT NULL auto_increment PRIMARY KEY,
//preorder_master_account VARCHAR (30),	
//preorder_create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//preorder_price DECIMAL(10,2),
//preorder_order_id INT NOT NULL
//) DEFAULT CHARSET = utf8;

public class Preorder {

	private int preorder_id;
	private String preorder_master_account;
	private Timestamp preorder_create_time;
	private double preorder_price;
	private int preorder_order_id;

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

}
