package com.bbcall.mybatis.table;

// CREATE TABLE USER_SKILL (
// userskill_id INT NOT NULL auto_increment PRIMARY KEY,
// user_id INT,
// user_skill text,
// FOREIGN KEY (`user_id`) REFERENCES `bbcall`.`USER` (`user_id`) ON UPDATE
// CASCADE ON DELETE CASCADE
// ) DEFAULT CHARSET = utf8;

public class UserSkill {
	private int userskill_id;
	private int user_id;
	private String user_skill;

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

	public String getUser_skill() {
		return user_skill;
	}

	public void setUser_skill(String user_skill) {
		this.user_skill = user_skill;
	}

}
