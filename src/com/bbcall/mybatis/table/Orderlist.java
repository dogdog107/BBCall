package com.bbcall.mybatis.table;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

//* Table Order's sql command:
//CREATE TABLE Orderlist (
//	order_id INT NOT NULL auto_increment PRIMARY KEY,
//  order_create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//  order_end_time TIMESTAMP,
//  order_book_time TIMESTAMP NOT NULL,
//  order_book_location text NOT NULL,
//  order_book_location_code INT NOT NULL,
//  order_contact_mobile  BIGINT NOT NULL,
//  order_contact_name  VARCHAR (30) NOT NULL,
//  order_pic_url text,
//  order_description text,
//  order_price DECIMAL(10,2),
//  order_urgent BIT,
//  order_urgent_bonus DECIMAL(10,2),
//  order_user_account  VARCHAR (30),
//  order_master_account VARCHAR (30),
//  order_type_code INT,
//  order_status INT,
//  /* 1=created, 2=in progress, 3=finished */
//  order_score INT,
//  order_evaluation text
//) DEFAULT CHARSET = utf8;

public class Orderlist {

	private int order_id;
	private Timestamp order_create_time;
	private Timestamp order_end_time;
	private Timestamp order_book_time;
	private String order_book_location;
	private int order_book_location_code;
	private BigInteger order_contact_mobile;
	private String order_contact_name;
	private String order_pic_url;
	private String order_description;
	private double order_price;
	private boolean order_urgent;
	private double order_urgent_bonus;
	private String order_user_account;
	private String order_master_account;
	private int order_type_code;
	private int order_status;
	private int order_score;
	private String order_evaluation;
	private String order_remark;

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public Date getOrder_create_time() {
		return order_create_time;
	}

	public void setOrder_create_time(Timestamp order_create_time) {
		this.order_create_time = order_create_time;
	}

	public Date getOrder_end_time() {
		return order_end_time;
	}

	public void setOrder_end_time(Timestamp order_end_time) {
		this.order_end_time = order_end_time;
	}

	public Date getOrder_book_time() {
		return order_book_time;
	}

	public void setOrder_book_time(Timestamp order_book_time) {
		this.order_book_time = order_book_time;
	}

	public String getOrder_book_location() {
		return order_book_location;
	}

	public void setOrder_book_location(String order_book_location) {
		this.order_book_location = order_book_location;
	}

	public BigInteger getOrder_contact_mobile() {
		return order_contact_mobile;
	}

	public void setOrder_contact_mobile(BigInteger order_contact_mobile) {
		this.order_contact_mobile = order_contact_mobile;
	}

	public String getOrder_contact_name() {
		return order_contact_name;
	}

	public void setOrder_contact_name(String order_contact_name) {
		this.order_contact_name = order_contact_name;
	}

	public String getOrder_description() {
		return order_description;
	}

	public void setOrder_description(String order_description) {
		this.order_description = order_description;
	}

	public double getOrder_price() {
		return order_price;
	}

	public void setOrder_price(double order_price) {
		this.order_price = order_price;
	}

	public boolean isOrder_urgent() {
		return order_urgent;
	}

	public void setOrder_urgent(boolean order_urgent) {
		this.order_urgent = order_urgent;
	}

	public double getOrder_urgent_bonus() {
		return order_urgent_bonus;
	}

	public void setOrder_urgent_bonus(double order_urgent_bonus) {
		this.order_urgent_bonus = order_urgent_bonus;
	}

	public String getOrder_user_account() {
		return order_user_account;
	}

	public void setOrder_user_account(String order_user_account) {
		this.order_user_account = order_user_account;
	}

	public String getOrder_master_account() {
		return order_master_account;
	}

	public void setOrder_master_account(String order_master_account) {
		this.order_master_account = order_master_account;
	}

	public int getOrder_type_code() {
		return order_type_code;
	}

	public void setOrder_type_code(int order_type_code) {
		this.order_type_code = order_type_code;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public int getOrder_score() {
		return order_score;
	}

	public void setOrder_score(int order_score) {
		this.order_score = order_score;
	}

	public String getOrder_evaluation() {
		return order_evaluation;
	}

	public void setOrder_evaluation(String order_evaluation) {
		this.order_evaluation = order_evaluation;
	}

	public int getOrder_book_location_code() {
		return order_book_location_code;
	}

	public void setOrder_book_location_code(int order_book_location_code) {
		this.order_book_location_code = order_book_location_code;
	}

	public String getOrder_pic_url() {
		return order_pic_url;
	}

	public void setOrder_pic_url(String order_pic_url) {
		this.order_pic_url = order_pic_url;
	}

	public String getOrder_remark() {
		return order_remark;
	}

	public void setOrder_remark(String order_remark) {
		this.order_remark = order_remark;
	}

}
