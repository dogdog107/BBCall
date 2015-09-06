package com.bbcall.mybatis.table;

import java.sql.Timestamp;

//CREATE TABLE USERSKILL (
//		userskill_id INT NOT NULL auto_increment PRIMARY KEY,
//		user_id INT NOT NULL,
//		user_skill INT NOT NULL,
//		user_skill_url VARCHAR (255) NOT NULL,
//		user_skill_status TINYINT DEFAULT 0, // 0:未审核, 1:审核通过, 2:审核不通过
//		user_skill_create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//		FOREIGN KEY (`user_id`) REFERENCES `bbcall`.`USER` (`user_id`) ON UPDATE CASCADE ON DELETE CASCADE
//	) DEFAULT CHARSET = utf8;

public class UserSkill {
	private int userskill_id;
	private int user_id;
	private Integer user_skill;
	private String user_skill_name;
	private String user_skill_url;
	private Integer user_skill_status;
	private Timestamp user_skill_create_time;
	// 关联表User 参数
	private String user_account;

	public int getUserskill_id() {
		return userskill_id;
	}

	public void setUserskill_id(int userskill_id) {
		this.userskill_id = userskill_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Integer getUser_skill() {
		return user_skill;
	}

	public void setUser_skill(Integer user_skill) {
		this.user_skill = user_skill;
	}

	public String getUser_skill_url() {
		return user_skill_url;
	}

	public void setUser_skill_url(String user_skill_url) {
		this.user_skill_url = user_skill_url;
	}

	public Integer getUser_skill_status() {
		return user_skill_status;
	}

	public void setUser_skill_status(Integer user_skill_status) {
		this.user_skill_status = user_skill_status;
	}

	public Timestamp getUser_skill_create_time() {
		return user_skill_create_time;
	}

	public void setUser_skill_create_time(Timestamp user_skill_create_time) {
		this.user_skill_create_time = user_skill_create_time;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public String getUser_skill_name() {
		return user_skill_name;
	}

	public void setUser_skill_name(String user_skill_name) {
		this.user_skill_name = user_skill_name;
	}

}
