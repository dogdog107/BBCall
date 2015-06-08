package com.bbcall.mybatis.table;

//CREATE TABLE `ADDRESS_LIST` (
//		  `areano` mediumint(6) unsigned NOT NULL,
//		  `areaname` varchar(45) DEFAULT NULL,
//		  `parentno` mediumint(6) unsigned DEFAULT NULL,
//		  `areacode` varchar(5) DEFAULT NULL,
//		  `arealevel` tinyint(1) DEFAULT NULL,
//		  `typename` char(3) DEFAULT NULL,
//		  PRIMARY KEY (`areano`)
//		) ENGINE=InnoDB DEFAULT CHARSET=utf8;

public class AddressList {

	private Integer areano;
	private String areaname;
	private Integer parentno;
	private String areacode;
	private Integer arealevel;
	private String typename;

	public Integer getAreano() {
		return areano;
	}

	public void setAreano(int areano) {
		this.areano = areano;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public Integer getParentno() {
		return parentno;
	}

	public void setParentno(int parentno) {
		this.parentno = parentno;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public Integer getArealevel() {
		return arealevel;
	}

	public void setArealevel(int arealevel) {
		this.arealevel = arealevel;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

}
