package com.bbcall.mybatis.dao;

import java.util.List;

import com.bbcall.mybatis.table.Advertisement;

public interface AdvertisementMapper {
	
	// 添加广告 add advertisement
	public void addAdvert(Advertisement advertisement);

	// 删除广告 delete advertisement
	public void deleteAdvertById(Integer advertisement_id);
	
	// 显示全部广告 get all advertisements
	public List<Advertisement> getAllAdvert();
	
	// 按ID得到广告内容
	public Advertisement getAdvertById(Integer advertisement_id);
}
