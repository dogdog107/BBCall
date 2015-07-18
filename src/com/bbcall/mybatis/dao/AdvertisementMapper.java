package com.bbcall.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bbcall.mybatis.table.Advertisement;

public interface AdvertisementMapper {
	
	// 添加广告 add advertisement
	public void addAdvert(Advertisement advertisement);

	// 删除广告 delete advertisement
	public void deleteAdvertById(Integer advertisement_id);
	
	// 置顶广告 
	public void updateAdvertIsTop(
			@Param("advertisement_id") Integer advertisement_id,
			@Param("advertisement_istop") Integer advertisement_istop);
	
	// 显示全部广告 get all advertisements
	public List<Advertisement> getAllAdvert();
	
	// 显示全部广告de的概要 get all advertisements Summary
	public List<Advertisement> getAllAdvertSummary();
	
	// 按ID得到广告内容
	public Advertisement getAdvertById(Integer advertisement_id);
}
