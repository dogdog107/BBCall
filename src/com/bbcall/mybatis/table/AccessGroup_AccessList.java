package com.bbcall.mybatis.table;

//** 关系表
//CREATE TABLE ACCESSGROUP_ACCESSLIST (
//		ag_id INT NOT NULL,
//		al_id INT NOT NULL,
//		PRIMARY KEY (ag_id, al_id),
//		CONSTRAINT `FK_ACCESSGROUP_ACCESSLIST_ag_id` FOREIGN KEY (`ag_id`) REFERENCES `ACCESSGROUP` (`accessgroup_id`),
//		CONSTRAINT `FK_ACCESSGROUP_ACCESSLIST_al_id` FOREIGN KEY (`al_id`) REFERENCES `ACCESSLIST` (`accesslist_id`)
//	) DEFAULT CHARSET = utf8;

public class AccessGroup_AccessList {
	private Integer ag_id;
	private Integer al_id;

	public Integer getAg_id() {
		return ag_id;
	}

	public void setAg_id(Integer ag_id) {
		this.ag_id = ag_id;
	}

	public Integer getAl_id() {
		return al_id;
	}

	public void setAl_id(Integer al_id) {
		this.al_id = al_id;
	}
}
