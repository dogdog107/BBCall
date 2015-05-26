package com.bbcall.mybatis.table;

import java.sql.Date;

public class Trace_Log {

	private int trace_Log_Id;
	private int trace_Log_User_Id;
	private int trace_Log_Order_Id;
	private Date trace_Log_Start_Time;
	private Date trace_Log_End_Time;
	private String trace_Log_Order_Type;
	private int trace_Log_Order_Price;
	private int trace_Log_Master_Id;

	public int getTrace_Log_Id() {
		return trace_Log_Id;
	}

	public void setTrace_Log_Id(int trace_Log_Id) {
		this.trace_Log_Id = trace_Log_Id;
	}

	public int getTrace_Log_User_Id() {
		return trace_Log_User_Id;
	}

	public void setTrace_Log_User_Id(int trace_Log_User_Id) {
		this.trace_Log_User_Id = trace_Log_User_Id;
	}

	public int getTrace_Log_Order_Id() {
		return trace_Log_Order_Id;
	}

	public void setTrace_Log_Order_Id(int trace_Log_Order_Id) {
		this.trace_Log_Order_Id = trace_Log_Order_Id;
	}

	public Date getTrace_Log_Start_Time() {
		return trace_Log_Start_Time;
	}

	public void setTrace_Log_Start_Time(Date trace_Log_Start_Time) {
		this.trace_Log_Start_Time = trace_Log_Start_Time;
	}

	public Date getTrace_Log_End_Time() {
		return trace_Log_End_Time;
	}

	public void setTrace_Log_End_Time(Date trace_Log_End_Time) {
		this.trace_Log_End_Time = trace_Log_End_Time;
	}

	public String getTrace_Log_Order_Type() {
		return trace_Log_Order_Type;
	}

	public void setTrace_Log_Order_Type(String trace_Log_Order_Type) {
		this.trace_Log_Order_Type = trace_Log_Order_Type;
	}

	public int getTrace_Log_Order_Price() {
		return trace_Log_Order_Price;
	}

	public void setTrace_Log_Order_Price(int trace_Log_Order_Price) {
		this.trace_Log_Order_Price = trace_Log_Order_Price;
	}

	public int getTrace_Log_Master_Id() {
		return trace_Log_Master_Id;
	}

	public void setTrace_Log_Master_Id(int trace_Log_Master_Id) {
		this.trace_Log_Master_Id = trace_Log_Master_Id;
	}

}
