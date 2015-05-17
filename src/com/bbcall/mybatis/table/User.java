package com.bbcall.mybatis.table;

import java.math.BigInteger;
import java.util.Date;

/*	
 * Table USER's sql command:
 CREATE TABLE USER (
 user_id INT NOT NULL auto_increment PRIMARY KEY,
 user_account VARCHAR (30),
 user_password VARCHAR (30) NOT NULL,
 user_email VARCHAR (30),
 user_mobile BIGINT,
 user_type VARCHAR (10),
 user_name VARCHAR (20),
 user_sex VARCHAR (10),
 user_pic_url VARCHAR (255),
 user_language VARCHAR (10),
 user_address text,
 user_skill text,
 user_description text,
 user_access_group VARCHAR (30),
 user_status VARCHAR (10),
 user_create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 user_login_time datetime,
 user_token VARCHAR (255)
 ) DEFAULT CHARSET = utf8;
 */

public class User {
	private int user_id;
	private String user_account;
	private String user_password;
	private String user_email;
	private BigInteger user_mobile;
	private String user_type;
	private String user_name;
	private String user_sex;
	private String user_pic_url;
	private String user_language;
	private String user_address;
	private String user_skill;
	private String user_description;
	private String user_access_group;
	private String user_status;
	private Date user_login_time;
	private String user_token;

	public User(int user_id, String user_account, String user_password,
			String user_email, BigInteger user_mobile, String user_type,
			String user_name, String user_sex, String user_pic_url,
			String user_language, String user_skill, String user_description,
			String user_access_group, String user_status, Date user_login_time,
			String user_token) {
		super();
		this.user_id = user_id;
		this.user_account = user_account;
		this.user_password = user_password;
		this.user_email = user_email;
		this.user_mobile = user_mobile;
		this.user_type = user_type;
		this.user_name = user_name;
		this.user_sex = user_sex;
		this.user_pic_url = user_pic_url;
		this.user_language = user_language;
		this.user_skill = user_skill;
		this.user_description = user_description;
		this.user_access_group = user_access_group;
		this.user_status = user_status;
		this.user_login_time = user_login_time;
		this.user_token = user_token;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
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

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
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

	public String getUser_status() {
		return user_status;
	}

	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}

	public Date getUser_login_time() {
		return user_login_time;
	}

	public void setUser_login_time(Date user_login_time) {
		this.user_login_time = user_login_time;
	}

	public String getUser_token() {
		return user_token;
	}

	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}

	public void setToken(String user_token) {
		this.user_token = user_token == null ? null : user_token.trim();
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_account=" + user_account
				+ ", user_password=" + user_password + ", user_email="
				+ user_email + ", user_mobile=" + user_mobile + ", user_type="
				+ user_type + ", user_name=" + user_name + ", user_sex="
				+ user_sex + ", user_pic_url=" + user_pic_url
				+ ", user_language=" + user_language + ", user_skill="
				+ user_skill + ", user_description=" + user_description
				+ ", user_access_group=" + user_access_group + ", user_status="
				+ user_status + ", user_login_time=" + user_login_time
				+ ", user_token=" + user_token + "]";
	}

}
