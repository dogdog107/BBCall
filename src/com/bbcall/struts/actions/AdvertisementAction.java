package com.bbcall.struts.actions;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ObjectToMap;
import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.table.Advertisement;
import com.bbcall.struts.services.AdvertisementServices;
import com.bbcall.struts.services.UserServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("advertisementAction")
@SuppressWarnings("serial")
public class AdvertisementAction extends ActionSupport{
	
	/**
	 * Define Parameters Area
	 */
	
	// advertisementAction required parameters
	@Autowired
	private AdvertisementServices advertisementServices;
	@Autowired
	private UserServices userServices;
	private Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	private ObjectToMap obj2map = new ObjectToMap();// 新建ObjectToMap对象
	
	// User-related parameters
	private String token;
	
	// Advertisement-related parameters
	private Integer advertisement_id;
	private String advertisement_title;
	private String advertisement_type;
	private String advertisement_bigphoto_url;
	private String advertisement_smallphoto_url;
	private String advertisement_summary;
	private String advertisement_content;
	private Timestamp advertisement_create_time;
	private List<Advertisement> advertList;

	/**
	 * addAdvert Action
	 * @author Roger Luo
	 * @return 
	 * @throws Exception
	 */
	public String addAdvert() throws Exception {
		System.out.println("Here is advertisementAction.addAdvert");
		System.out.println(advertisement_content);

		int result = advertisementServices.addAdvert(advertisement_title,
				advertisement_type, advertisement_bigphoto_url,
				advertisement_smallphoto_url, advertisement_summary,
				advertisement_content);

		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		if (result == ResultCode.SUCCESS) {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("addAdvertResult", true); // 放入loginResult
			System.out.println(dataMap);
			return "addAdvertSuccess";
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("addAdvertResult", false); // 放入loginResult
			System.out.println(dataMap);
			System.out.println("addAdvert Failed");
			return "addAdvertFailed";
		}
	}

	public String addAdvertJson() throws Exception {
		addAdvert();
		return "json";
	}
	
	/**
	 * showAdvert Action
	 * @author Roger Luo
	 * @return 
	 * @throws Exception
	 */
	public String showAdvert() throws Exception {
		System.out.println("Here is advertisementAction.showAdvert");

		int result = advertisementServices.listAdvert(advertisement_id);

		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		if (result == ResultCode.SUCCESS) {
			advertisement_id = advertisementServices.getAdvertisement().getAdvertisement_id();
			advertisement_title = advertisementServices.getAdvertisement().getAdvertisement_title();
			advertisement_type = advertisementServices.getAdvertisement().getAdvertisement_type();
			advertisement_bigphoto_url = advertisementServices.getAdvertisement().getAdvertisement_bigphoto_url();
			advertisement_smallphoto_url = advertisementServices.getAdvertisement().getAdvertisement_smallphoto_url();
			advertisement_summary = advertisementServices.getAdvertisement().getAdvertisement_summary();
			advertisement_content = advertisementServices.getAdvertisement().getAdvertisement_content();
			advertisement_create_time = advertisementServices.getAdvertisement().getAdvertisement_create_time();
			System.out.println(advertisement_create_time);
			dataMap = obj2map.getValueMap(advertisementServices.getAdvertisement());
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("showAdvertResult", true); // 放入loginResult
			System.out.println(dataMap);
			return "showAdvertSuccess";
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("showAdvertResult", false); // 放入loginResult
			System.out.println(dataMap);
			System.out.println("showAdvert Failed");
			return "showAdvertFailed";
		}
	}
	
	public String showAdvertJson() throws Exception {
		showAdvert();
		return "json";
	}
	
	/**
	 * showAdvertList Action
	 * @author Roger Luo
	 * @return
	 * @throws Exception
	 */
	public String showAdvertList() throws Exception {
		advertList = advertisementServices.getAllAdvertList();
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("advertList", advertList);
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String showAdvertListJson() throws Exception {
		showAdvertList();
		return "json";
	}
	
	/**
	 * showAdvertSummaryList Action
	 * @author Roger Luo
	 * @return
	 * @throws Exception
	 */
	public String showAdvertSummaryList() throws Exception {
		advertList = advertisementServices.getAllAdvertSummaryList();
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("advertList", advertList);
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String showAdvertSummaryListJson() throws Exception {
		showAdvertSummaryList();
		return "json";
	}
	
	
	/**
	 * deleteAdvert Action
	 * @return
	 * @throws Exception
	 */
	public String deleteAdvert() throws Exception {
		int result = advertisementServices.deleteAdvert(advertisement_id);
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			return "deleteAdvertSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			System.out.println("deleteAdvert Failed");
			return "deleteAdvertFailed";
		}
	}
	
	public String deleteAdvertJson() throws Exception {
		deleteAdvert();
		return "json";
	}
	
	
	// Json Format Return
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	/**
	 * Getter & Setter
	 * @return
	 */
	public AdvertisementServices getAdvertisementServices() {
		return advertisementServices;
	}

	public void setAdvertisementServices(AdvertisementServices advertisementServices) {
		this.advertisementServices = advertisementServices;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getAdvertisement_id() {
		return advertisement_id;
	}

	public void setAdvertisement_id(Integer advertisement_id) {
		this.advertisement_id = advertisement_id;
	}

	public String getAdvertisement_title() {
		return advertisement_title;
	}

	public void setAdvertisement_title(String advertisement_title) {
		this.advertisement_title = advertisement_title;
	}

	public String getAdvertisement_type() {
		return advertisement_type;
	}

	public void setAdvertisement_type(String advertisement_type) {
		this.advertisement_type = advertisement_type;
	}

	public String getAdvertisement_bigphoto_url() {
		return advertisement_bigphoto_url;
	}

	public void setAdvertisement_bigphoto_url(String advertisement_bigphoto_url) {
		this.advertisement_bigphoto_url = advertisement_bigphoto_url;
	}

	public String getAdvertisement_smallphoto_url() {
		return advertisement_smallphoto_url;
	}

	public void setAdvertisement_smallphoto_url(String advertisement_smallphoto_url) {
		this.advertisement_smallphoto_url = advertisement_smallphoto_url;
	}

	public String getAdvertisement_summary() {
		return advertisement_summary;
	}

	public void setAdvertisement_summary(String advertisement_summary) {
		this.advertisement_summary = advertisement_summary;
	}

	public String getAdvertisement_content() {
		return advertisement_content;
	}

	public void setAdvertisement_content(String advertisement_content) {
		this.advertisement_content = advertisement_content;
	}

	public Timestamp getAdvertisement_create_time() {
		return advertisement_create_time;
	}

	public void setAdvertisement_create_time(Timestamp advertisement_create_time) {
		this.advertisement_create_time = advertisement_create_time;
	}

	public List<Advertisement> getAdvertList() {
		return advertList;
	}

	public void setAdvertList(List<Advertisement> advertList) {
		this.advertList = advertList;
	}
}
