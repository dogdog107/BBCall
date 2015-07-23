package com.bbcall.mybatis.table;

import java.util.List;

//** Old Table
//CREATE TABLE ACCESSGROUP (
//accessgroup_id INT NOT NULL auto_increment UNIQUE,
//accessgroup_name VARCHAR (100) NOT NULL,
//accessgroup_access VARCHAR (100) NOT NULL,
//PRIMARY KEY (
//	accessgroup_name,
//	accessgroup_access
//)
//) DEFAULT CHARSET = utf8;

//CREATE TABLE ACCESSGROUP (
//		accessgroup_id INT NOT NULL auto_increment PRIMARY KEY,
//		accessgroup_name VARCHAR (100) NOT NULL UNIQUE,
//		accessgroup_description VARCHAR (255) NOT NULL,
//		accessgroup_default TINYINT DEFAULT 0
//	) DEFAULT CHARSET = utf8;

public class AccessGroup {
	private Integer accessgroup_id;
	private String accessgroup_name;
	private String accessgroup_description;
	private Integer accessgroup_default;
	private List<AccessList> accesslist; // 存取权限表的信息

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

	public String getAccessgroup_description() {
		return accessgroup_description;
	}

	public void setAccessgroup_description(String accessgroup_description) {
		this.accessgroup_description = accessgroup_description;
	}

	public Integer getAccessgroup_default() {
		return accessgroup_default;
	}

	public void setAccessgroup_default(Integer accessgroup_default) {
		this.accessgroup_default = accessgroup_default;
	}

	public List<AccessList> getAccesslist() {
		return accesslist;
	}

	public void setAccesslist(List<AccessList> accesslist) {
		this.accesslist = accesslist;
	}

}
