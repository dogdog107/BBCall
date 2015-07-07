package com.bbcall.mybatis.table;

//CREATE TABLE ACCESSGROUP (
//		accessgroup_id INT NOT NULL auto_increment UNIQUE,
//		accessgroup_name VARCHAR (100) NOT NULL,
//		accessgroup_access VARCHAR (100) NOT NULL,
//		PRIMARY KEY (
//			accessgroup_name,
//			accessgroup_access
//		)
//	) DEFAULT CHARSET = utf8;

public class AccessGroup {
	private Integer accessgroup_id;
	private String accessgroup_name;
	private String accessgroup_access;

	public Integer getAccessgroup_id() {
		return accessgroup_id;
	}

	public void setAccessgroup_id(Integer accessgroup_id) {
		this.accessgroup_id = accessgroup_id;
	}

	public String getAccessgroup_name() {
		return accessgroup_name;
	}

	public void setAccessgroup_name(String accessgroup_name) {
		this.accessgroup_name = accessgroup_name;
	}

	public String getAccessgroup_access() {
		return accessgroup_access;
	}

	public void setAccessgroup_access(String accessgroup_access) {
		this.accessgroup_access = accessgroup_access;
	}
}
