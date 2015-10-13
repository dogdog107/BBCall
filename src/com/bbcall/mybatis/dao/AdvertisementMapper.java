package com.bbcall.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bbcall.mybatis.table.Advertisement;

public interface AdvertisementMapper {
	
	// 添加广告 add advertisement
	public void addAdvert(Advertisement advertisement);
	
	// 删除广告 delete advertisement
	public void deleteAdvertById(Integer advertisement_id);

	// 更新广告 update advertisement
	public void updateAdvertById(Advertisement advertisement);
	
	// 置顶广告 
	public void updateAdvertIsTop(
			@Param("advertisement_id") Integer advertisement_id,
			@Param("advertisement_istop") Integer advertisement_istop);
	
	// update广告的状态 
	public void updateAdvertStatus(
			@Param("advertisement_id") Integer advertisement_id,
			@Param("advertisement_status") Integer advertisement_status);
	
	// 显示全部广告 get all advertisements
	public List<Advertisement> getAllAdvert(
			@Param("order_col") String order_col,
			@Param("order_value") String order_value);
	
	// 显示全部广告的概要 get all advertisements Summary
	public List<Advertisement> getAllAdvertSummary(
			@Param("order_col") String order_col,
			@Param("order_value") String order_value);
	
	// 显示active的广告 get active advertisements
	public List<Advertisement> getActiveAdvert();
	
	// 显示active广告的概要 get active advertisements Summary
	public List<Advertisement> getActiveAdvertSummary();
	
	// 按ID得到广告内容
	public Advertisement getAdvertById(Integer advertisement_id);
}
