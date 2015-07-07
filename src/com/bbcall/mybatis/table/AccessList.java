package com.bbcall.mybatis.table;

//CREATE TABLE ACCESSLIST (
//		accesslist_id INT NOT NULL auto_increment PRIMARY KEY,
//		accesslist_name VARCHAR (100) NOT NULL UNIQUE
//	) DEFAULT CHARSET = utf8;

public class AccessList {
	private Integer accesslist_id;
	private String accesslist_name;

	public Integer getAccesslist_id() {
		return accesslist_id;
	}

	public void setAccesslist_id(Integer accesslist_id) {
		this.accesslist_id = accesslist_id;
	}

	public String getAccesslist_name() {
		return accesslist_name;
	}

	public void setAccesslist_name(String accesslist_name) {
		this.accesslist_name = accesslist_name;
	}
}
