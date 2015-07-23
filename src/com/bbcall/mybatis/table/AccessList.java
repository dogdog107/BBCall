package com.bbcall.mybatis.table;

import java.util.List;

//CREATE TABLE ACCESSLIST (
//		accesslist_id INT NOT NULL auto_increment PRIMARY KEY,
//		accesslist_name VARCHAR (100) NOT NULL UNIQUE,
//		accesslist_description VARCHAR (225) NOT NULL
//	) DEFAULT CHARSET = utf8;

public class AccessList {
	private Integer accesslist_id;
	private String accesslist_name;
	private String accesslist_description;
	private List<AccessGroup> accessgroup; // 存取权限组的信息

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

	public String getAccesslist_description() {
		return accesslist_description;
	}

	public void setAccesslist_description(String accesslist_description) {
		this.accesslist_description = accesslist_description;
	}

	public List<AccessGroup> getAccessgroup() {
		return accessgroup;
	}

	public void setAccessgroup(List<AccessGroup> accessgroup) {
		this.accessgroup = accessgroup;
	}
}
