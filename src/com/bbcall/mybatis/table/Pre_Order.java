package com.bbcall.mybatis.table;

import java.sql.Date;

public class Pre_Order {

	private int pre_Order_Id;
	private int pre_Order_Master_Id;
	private Date pre_Order_Create_Time;
	private int pre_Order_Price;

	public int getPre_Order_Id() {
		return pre_Order_Id;
	}

	public void setPre_Order_Id(int pre_Order_Id) {
		this.pre_Order_Id = pre_Order_Id;
	}

	public int getPre_Order_Master_Id() {
		return pre_Order_Master_Id;
	}

	public void setPre_Order_Master_Id(int pre_Order_Master_Id) {
		this.pre_Order_Master_Id = pre_Order_Master_Id;
	}

	public Date getPre_Order_Create_Time() {
		return pre_Order_Create_Time;
	}

	public void setPre_Order_Create_Time(Date pre_Order_Create_Time) {
		this.pre_Order_Create_Time = pre_Order_Create_Time;
	}

	public int getPre_Order_Price() {
		return pre_Order_Price;
	}

	public void setPre_Order_Price(int pre_Order_Price) {
		this.pre_Order_Price = pre_Order_Price;
	}
}
