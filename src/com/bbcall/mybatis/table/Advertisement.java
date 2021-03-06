package com.bbcall.mybatis.table;

import java.sql.Timestamp;

//* Table ADVERTISEMENT's sql command:
//CREATE TABLE ADVERTISEMENT (
//	advertisement_id INT NOT NULL auto_increment PRIMARY KEY,
//	advertisement_title VARCHAR (255),
//	advertisement_bigphoto_url VARCHAR (255),
//	advertisement_smallphoto_url VARCHAR (255),
//	advertisement_type INT,
//	advertisement_summary VARCHAR (255),
//	advertisement_content text,
//	advertisement_istop tinyint NOT NULL DEFAULT 0,
//	advertisement_status tinyint NOT NULL DEFAULT 0,
//	advertisement_create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
//) DEFAULT CHARSET = utf8;

public class Advertisement {

	private Integer advertisement_id;
	private Integer advertisement_istop;
	private Integer advertisement_status;
	private String advertisement_title;
	private Integer advertisement_type;
	private String advertisement_type_name;
	private String advertisement_bigphoto_url;
	private String advertisement_smallphoto_url;
	private String advertisement_summary;
	private String advertisement_content;
	private Timestamp advertisement_create_time;

	public Integer getAdvertisement_id() {
		return advertisement_id;
	}

	public void setAdvertisement_id(Integer advertisement_id) {
		this.advertisement_id = advertisement_id;
	}

	public String getAdvertisement_title() {
		return advertisement_title;
	}

	public void setAdvertisement_title(String advertisement_title) {
		this.advertisement_title = advertisement_title;
	}

	public Integer getAdvertisement_type() {
		return advertisement_type;
	}

	public void setAdvertisement_type(Integer advertisement_type) {
		this.advertisement_type = advertisement_type;
	}

	public String getAdvertisement_bigphoto_url() {
		return advertisement_bigphoto_url;
	}

	public void setAdvertisement_bigphoto_url(String advertisement_bigphoto_url) {
		this.advertisement_bigphoto_url = advertisement_bigphoto_url;
	}

	public String getAdvertisement_smallphoto_url() {
		return advertisement_smallphoto_url;
	}

	public void setAdvertisement_smallphoto_url(
			String advertisement_smallphoto_url) {
		this.advertisement_smallphoto_url = advertisement_smallphoto_url;
	}

	public String getAdvertisement_summary() {
		return advertisement_summary;
	}

	public void setAdvertisement_summary(String advertisement_summary) {
		this.advertisement_summary = advertisement_summary;
	}

	public String getAdvertisement_content() {
		return advertisement_content;
	}

	public void setAdvertisement_content(String advertisement_content) {
		this.advertisement_content = advertisement_content;
	}

	public Timestamp getAdvertisement_create_time() {
		return advertisement_create_time;
	}

	public void setAdvertisement_create_time(Timestamp advertisement_create_time) {
		this.advertisement_create_time = advertisement_create_time;
	}

	public Integer getAdvertisement_istop() {
		return advertisement_istop;
	}

	public void setAdvertisement_istop(Integer advertisement_istop) {
		this.advertisement_istop = advertisement_istop;
	}

	public Integer getAdvertisement_status() {
		return advertisement_status;
	}

	public void setAdvertisement_status(Integer advertisement_status) {
		this.advertisement_status = advertisement_status;
	}

	public String getAdvertisement_type_name() {
		return advertisement_type_name;
	}

	public void setAdvertisement_type_name(String advertisement_type_name) {
		this.advertisement_type_name = advertisement_type_name;
	}

}
