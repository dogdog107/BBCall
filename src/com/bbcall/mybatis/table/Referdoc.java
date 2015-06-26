package com.bbcall.mybatis.table;

//* Table referdoc's sql command:
//CREATE TABLE referdoc (
//	referdoc_id INT NOT NULL auto_increment PRIMARY KEY,
//referdoc_type VARCHAR (30),
//referdoc_parentno int,
//referdoc_level int,
//referdoc_price DECIMAL(10,2)
//) DEFAULT CHARSET = utf8;

public class Referdoc {

	private int referdoc_id;
	private String referdoc_type;
	private int referdoc_parentno;
	private int referdoc_level;
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

	public int getReferdoc_parentno() {
		return referdoc_parentno;
	}

	public void setReferdoc_parentno(int referdoc_parentno) {
		this.referdoc_parentno = referdoc_parentno;
	}

	public int getReferdoc_level() {
		return referdoc_level;
	}

	public void setReferdoc_level(int referdoc_level) {
		this.referdoc_level = referdoc_level;
	}

}
