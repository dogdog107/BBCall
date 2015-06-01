package com.bbcall.mybatis.table;

import java.sql.Date;

public class Advertisement {

	private int advertisement_Id;
	private String advertisement_Title;
	private Date advertisement_Create_Time;
	private String advertisement_Type;
	private String advertisement_Description;
	private String advertisement_Photo;
	private String advertisement_URL;

	public int getAdvertisement_Id() {
		return advertisement_Id;
	}

	public void setAdvertisement_Id(int advertisement_Id) {
		this.advertisement_Id = advertisement_Id;
	}

	public String getAdvertisement_Title() {
		return advertisement_Title;
	}

	public void setAdvertisement_Title(String advertisement_Title) {
		this.advertisement_Title = advertisement_Title;
	}

	public Date getAdvertisement_Create_Time() {
		return advertisement_Create_Time;
	}

	public void setAdvertisement_Create_Time(Date advertisement_Create_Time) {
		this.advertisement_Create_Time = advertisement_Create_Time;
	}

	public String getAdvertisement_Type() {
		return advertisement_Type;
	}

	public void setAdvertisement_Type(String advertisement_Type) {
		this.advertisement_Type = advertisement_Type;
	}

	public String getAdvertisement_Description() {
		return advertisement_Description;
	}

	public void setAdvertisement_Description(String advertisement_Description) {
		this.advertisement_Description = advertisement_Description;
	}

	public String getAdvertisement_Photo() {
		return advertisement_Photo;
	}

	public void setAdvertisement_Photo(String advertisement_Photo) {
		this.advertisement_Photo = advertisement_Photo;
	}

	public String getAdvertisement_URL() {
		return advertisement_URL;
	}

	public void setAdvertisement_URL(String advertisement_URL) {
		this.advertisement_URL = advertisement_URL;
	}

}
