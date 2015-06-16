package com.bbcall.mybatis.table;

//* Table referdoc's sql command:
//CREATE TABLE referdoc (
//	referdoc_id INT NOT NULL auto_increment PRIMARY KEY,
//referdoc_type VARCHAR (30),
//referdoc_price DECIMAL(10,2)
//) DEFAULT CHARSET = utf8;

public class Referdoc {

	private int referdoc_id;
	private String referdoc_type;
	private double referdoc_price;

	public int getReferdoc_id() {
		return referdoc_id;
	}

	public void setReferdoc_id(int referdoc_id) {
		this.referdoc_id = referdoc_id;
	}

	public String getReferdoc_type() {
		return referdoc_type;
	}

	public void setReferdoc_type(String referdoc_type) {
		this.referdoc_type = referdoc_type;
	}

	public double getReferdoc_price() {
		return referdoc_price;
	}

	public void setReferdoc_price(double referdoc_price) {
		this.referdoc_price = referdoc_price;
	}

}
