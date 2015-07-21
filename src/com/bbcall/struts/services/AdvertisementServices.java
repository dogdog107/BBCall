package com.bbcall.struts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.dao.AdvertisementMapper;
import com.bbcall.mybatis.table.Advertisement;
import com.github.pagehelper.PageHelper;

@Service("advertisementServices")
public class AdvertisementServices {
	@Autowired
	private AdvertisementMapper advertisementMapper;
	
	private Advertisement advertisement = new Advertisement();
	private List<Advertisement> advertList;
	
	// Add advertisement service
	public int addAdvert(String advertisement_title, Integer advertisement_type,
			String advertisement_bigphoto_url,
			String advertisement_smallphoto_url, String advertisement_summary,
			String advertisement_content) {
		
		// Check required parm
		if (Tools.isEmpty(advertisement_title, advertisement_bigphoto_url,
				advertisement_smallphoto_url, advertisement_summary,
				advertisement_content)
				&& advertisement_type != null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		
		Advertisement advert = new Advertisement();
		advert.setAdvertisement_bigphoto_url(advertisement_bigphoto_url);
		advert.setAdvertisement_smallphoto_url(advertisement_smallphoto_url);
		advert.setAdvertisement_content(advertisement_content);
		advert.setAdvertisement_summary(advertisement_summary);
		advert.setAdvertisement_title(advertisement_title);
		advert.setAdvertisement_type(advertisement_type);
		advertisementMapper.addAdvert(advert);
		return ResultCode.SUCCESS;
	}
	
	// Delete advertisement service
	public int deleteAdvert(Integer advertisement_id){
		if (advertisement_id == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		advertisementMapper.deleteAdvertById(advertisement_id);
		return ResultCode.SUCCESS;
	}

	// Update advertisement
	public int updateAdvert(Integer advertisement_id,
			String advertisement_title, Integer advertisement_type,
			String advertisement_bigphoto_url,
			String advertisement_smallphoto_url, String advertisement_summary,
			String advertisement_content, Integer advertisement_istop,
			Integer advertisement_status) {
		if (advertisement_id == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;

		Advertisement tempAdvert = advertisementMapper
				.getAdvertById(advertisement_id);
		if (tempAdvert != null) {
			int updateCount = 0;
			
			// ***** 检测title *****
			if(!Tools.isEmpty(advertisement_title) && !advertisement_title.equals(tempAdvert.getAdvertisement_title())) {
				tempAdvert.setAdvertisement_title(advertisement_title);
				System.out.println("Title changed!");
				updateCount++;
			}
			// ***** 检测type *****
			if(advertisement_type != null && !advertisement_type.equals(tempAdvert.getAdvertisement_type())) {
				tempAdvert.setAdvertisement_type(advertisement_type);
				System.out.println("Type changed!");
				updateCount++;
			}
			
			// ***** 检测bigphoto_url *****
			if(!Tools.isEmpty(advertisement_bigphoto_url) && !advertisement_bigphoto_url.equals(tempAdvert.getAdvertisement_bigphoto_url())) {
				tempAdvert.setAdvertisement_bigphoto_url(advertisement_bigphoto_url);
				System.out.println("bigphoto_url changed!");
				updateCount++;
			}
			
			// ***** 检测smallphoto_url *****
			if(!Tools.isEmpty(advertisement_smallphoto_url) && !advertisement_smallphoto_url.equals(tempAdvert.getAdvertisement_smallphoto_url())) {
				tempAdvert.setAdvertisement_smallphoto_url(advertisement_smallphoto_url);
				System.out.println("smallphoto_url changed!");
				updateCount++;
			}
			
			// ***** 检测advertisement_summary *****
			if(!Tools.isEmpty(advertisement_summary) && !advertisement_summary.equals(tempAdvert.getAdvertisement_summary())) {
				tempAdvert.setAdvertisement_summary(advertisement_summary);
				System.out.println("summary changed!");
				updateCount++;
			}
			
			// ***** 检测advertisement_content *****
			if(!Tools.isEmpty(advertisement_content) && !advertisement_content.equals(tempAdvert.getAdvertisement_content())) {
				tempAdvert.setAdvertisement_content(advertisement_content);
				System.out.println("content changed!");
				updateCount++;
			}
			
			// ***** 检测advertisement_istop *****
			if(advertisement_istop != null && !advertisement_istop.equals(tempAdvert.getAdvertisement_istop())) {
				if (!advertisement_istop.equals(1) && !advertisement_istop.equals(0)) {
					return ResultCode.REQUIREINFO_ERROR;
				}
				tempAdvert.setAdvertisement_istop(advertisement_istop);
				System.out.println("istop changed!");
				updateCount++;
			}
			
			// ***** 检测advertisement_status *****
			if(advertisement_status != null && !advertisement_status.equals(tempAdvert.getAdvertisement_status())) {
				if (!advertisement_status.equals(1) && !advertisement_status.equals(0)) {
					return ResultCode.REQUIREINFO_ERROR;
				}
				tempAdvert.setAdvertisement_status(advertisement_status);
				System.out.println("status changed!");
				updateCount++;
			}
			
			if (updateCount > 0) {
				advertisementMapper.updateAdvertById(tempAdvert);
			}
		} else {
			return ResultCode.ADVERTID_ERROR;
		}

		return ResultCode.SUCCESS;
	}
	
	// Update istop advertisement service
	public int updateAdvertIsTop(Integer advertisement_id, Integer advertisement_istop){
		if (advertisement_id == null || advertisement_istop == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		
		if (!advertisement_istop.equals(1) && !advertisement_istop.equals(0))
			return ResultCode.REQUIREINFO_ERROR;
		
		advertisementMapper.updateAdvertIsTop(advertisement_id, advertisement_istop);
		return ResultCode.SUCCESS;
	}
	
	// Update Status advertisement service
	public int updateAdvertStatus(Integer advertisement_id, Integer advertisement_Status){
		if (advertisement_id == null || advertisement_Status == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		
		if (!advertisement_Status.equals(1) && !advertisement_Status.equals(0))
			return ResultCode.REQUIREINFO_ERROR;
		
		advertisementMapper.updateAdvertStatus(advertisement_id, advertisement_Status);
		return ResultCode.SUCCESS;
	}
	
	// List advertisement service
	public int listAdvert(Integer advertisement_id){
		if (advertisement_id == null)
			return ResultCode.REQUIREINFO_NOTENOUGH;
		advertisement = advertisementMapper.getAdvertById(advertisement_id);
		return ResultCode.SUCCESS;
	}
	
	// ListAll advertisement service
	public List<Advertisement> getAllAdvertList(Integer pagenum){
		//当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;
		
	    //PageHelper.startPage(PageNum, PageSize) 
		//获取第1页，10条内容，当PageSize=0时会查询出全部的结果
	    PageHelper.startPage(pagenum, 5);

	    //紧跟着的第一个select方法会被分页
		advertList = advertisementMapper.getAllAdvert();
		return advertList;
	}
	
	// ListAll advertisement summary service
	public List<Advertisement> getAllAdvertSummaryList(Integer pagenum){
		//当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;
		
	    //PageHelper.startPage(PageNum, PageSize, "order by") 
		//获取第1页，10条内容，当PageSize=0时会查询出全部的结果
//	    PageHelper.startPage(pagenum, 5, "advertisement_create_time desc");
	    PageHelper.startPage(pagenum, 5);

	    //紧跟着的第一个select方法会被分页
		advertList = advertisementMapper.getAllAdvertSummary();
		return advertList;
	}
	
	// List Active advertisement service
	public List<Advertisement> getActiveAdvertList(Integer pagenum){
		//当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;
		
		//PageHelper.startPage(PageNum, PageSize) 
		//获取第1页，10条内容，当PageSize=0时会查询出全部的结果
		PageHelper.startPage(pagenum, 5);
		
		//紧跟着的第一个select方法会被分页
		advertList = advertisementMapper.getActiveAdvert();
		return advertList;
	}
	
	// List Active advertisement summary service
	public List<Advertisement> getActiveAdvertSummaryList(Integer pagenum){
		//当传进来的pagenum为空 或者 pagenum == 0 时，显示第一页
		if (pagenum == null || pagenum == 0)
			pagenum = 1;
		
		//PageHelper.startPage(PageNum, PageSize, "order by") 
		//获取第1页，10条内容，当PageSize=0时会查询出全部的结果
//	    PageHelper.startPage(pagenum, 5, "advertisement_create_time desc");
		PageHelper.startPage(pagenum, 5);
		
		//紧跟着的第一个select方法会被分页
		advertList = advertisementMapper.getActiveAdvertSummary();
		return advertList;
	}

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public List<Advertisement> getAdvertList() {
		return advertList;
	}
}
