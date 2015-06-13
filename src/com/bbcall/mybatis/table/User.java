package com.bbcall.mybatis.table;

import java.math.BigInteger;
import java.sql.Timestamp;


//* Table USER's sql command:
//CREATE TABLE USER (
//	user_id INT NOT NULL auto_increment PRIMARY KEY,
//	user_account VARCHAR (30),
//	user_password VARCHAR (30) NOT NULL,
//	user_email VARCHAR (30),
//	user_mobile BIGINT,
//	user_type INT,
//	/* 1=customer, 2=master, 3=admin */
//	user_name VARCHAR (20),
//	user_gender INT,
//  /* 1=male, 2=female */
//	user_pic_url VARCHAR (255),
//	user_language VARCHAR (10),
//  user_skill text,
//  user_address_code INT,
//	user_address text,
//	user_description text,
//	user_access_group VARCHAR (30),
//	user_status INT,
//	/* 1=active, 2=pause, 3=pending, 4=locked */
//	user_create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//	user_login_time TIMESTAMP NULL DEFAULT NULL,
//	user_token VARCHAR (255)
//) DEFAULT CHARSET = utf8;

public class User {
	private Integer user_id;
	private String user_account;
	private String user_password;
	private String user_email;
	private BigInteger user_mobile;
	private Integer user_type;
	private String user_name;
	private Integer user_gender;
	private String user_pic_url;
	private String user_language;
	private Integer user_address_code;
	private String user_address;
	private String user_skill;
	private String user_description;
	private String user_access_group;
	private Integer user_status;
	private Timestamp user_login_time;
	private Timestamp user_create_time;
	private String user_token;

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public BigInteger getUser_mobile() {
		return user_mobile;
	}

	public void setUser_mobile(BigInteger user_mobile) {
		this.user_mobile = user_mobile;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Integer getUser_gender() {
		return user_gender;
	}

	public void setUser_gender(Integer user_gender) {
		this.user_gender = user_gender;
	}

	public String getUser_pic_url() {
		return user_pic_url;
	}

	public void setUser_pic_url(String user_pic_url) {
		this.user_pic_url = user_pic_url;
	}

	public String getUser_language() {
		return user_language;
	}

	public void setUser_language(String user_language) {
		this.user_language = user_language;
	}

	public Integer getUser_address_code() {
		return user_address_code;
	}

	public void setUser_address_code(Integer user_address_code) {
		this.user_address_code = user_address_code;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public String getUser_skill() {
		return user_skill;
	}

	public void setUser_skill(String user_skill) {
		this.user_skill = user_skill;
	}

	public String getUser_description() {
		return user_description;
	}

	public void setUser_description(String user_description) {
		this.user_description = user_description;
	}

	public String getUser_access_group() {
		return user_access_group;
	}

	public void setUser_access_group(String user_access_group) {
		this.user_access_group = user_access_group;
	}

	public Integer getUser_status() {
		return user_status;
	}

	public void setUser_status(Integer user_status) {
		this.user_status = user_status;
	}

	public Timestamp getUser_login_time() {
		return user_login_time;
	}

	public void setUser_login_time(Timestamp user_login_time) {
		this.user_login_time = user_login_time;
	}

	public String getUser_token() {
		return user_token;
	}

	public void setUser_token(String user_token) {
		this.user_token = user_token == null ? null : user_token.trim();
	}

	public Timestamp getUser_create_time() {
		return user_create_time;
	}

	public void setUser_create_time(Timestamp user_create_time) {
		this.user_create_time = user_create_time;
	}
}
