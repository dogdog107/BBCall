package com.bbcall.struts.actions;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ObjectToMap;
import com.bbcall.functions.PageInfoToMap;
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
	private PageInfoToMap pageinfo2map = new PageInfoToMap();// 新建PageInfoToMap对象
	private static Logger logger = Logger.getLogger(AdvertisementAction.class);  
	// User-related parameters
	private String token;
	
	// Advertisement-related parameters
	private Integer advertisement_id;
	private Integer advertisement_istop;
	private String advertisement_title;
	private Integer advertisement_type;
	private String advertisement_bigphoto_url;
	private String advertisement_smallphoto_url;
	private String advertisement_summary;
	private String advertisement_content;
	private Integer advertisement_status;
	private Timestamp advertisement_create_time;
	private List<Advertisement> advertList;
	
	// Page-related parameters
	private Integer pagenum; // 页面页数
	
	// others parameters
	private String updateResult;
	
	// sort parameters
	private String order_col;
	private String order_value;
	
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
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			logger.info("userOpr:[addAdvert]" + dataMap);  
			return "addAdvertSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			System.out.println("addAdvert Failed");
			logger.info("userOpr:[addAdvert]" + dataMap);  
			return "addAdvertFailed";
		}
	}
	public String addAdvertJson() throws Exception {
		addAdvert();
		return "json";
	}
	public String showAddAdvertPage() throws Exception {
		return "showAddAdvertPage";
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
			advertisement_istop = advertisementServices.getAdvertisement().getAdvertisement_istop();
			advertisement_status = advertisementServices.getAdvertisement().getAdvertisement_status();
			System.out.println(advertisement_create_time);
			dataMap = obj2map.getValueMap(advertisementServices.getAdvertisement());
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			logger.info("userOpr:[showAdvert][" + advertisement_id + "]" + dataMap);  
			return "showAdvertSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			System.out.println("showAdvert Failed");
			logger.info("userOpr:[showAdvert][" + advertisement_id + "]" + dataMap);  
			return "showAdvertFailed";
		}
	}
	
	public String showAdvertJson() throws Exception {
		showAdvert();
		return "json";
	}
	public String editAdvertPage() throws Exception {
		String result = showAdvert();
		if (result.equals("showAdvertSuccess")){
			return "editAdvertPageSuccess";
		} else {
			return "editAdvertPageFailed";
		}
		
	}
	
	/**
	 * showAllAdvertList Action
	 * @author Roger Luo
	 * @return
	 * @throws Exception
	 */
	public String showAllAdvertList() throws Exception {
		advertList = advertisementServices.getAllAdvertList(pagenum, order_col, order_value);
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("advertList", advertList);
		dataMap.putAll(pageinfo2map.pageInfoMap(advertList));// 把分页信息放进dataMap
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String showAllAdvertListJson() throws Exception {
		showAllAdvertList();
		return "json";
	}
	
	/**
	 * showAllAdvertSummaryList Action
	 * @author Roger Luo
	 * @return
	 * @throws Exception
	 */
	public String showAllAdvertSummaryList() throws Exception {
		advertList = advertisementServices.getAllAdvertSummaryList(pagenum, order_col, order_value);
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("advertList", advertList);
		dataMap.putAll(pageinfo2map.pageInfoMap(advertList));// 把分页信息放进dataMap
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String showAllAdvertSummaryListJson() throws Exception {
		showAllAdvertSummaryList();
		return "json";
	}
	
	
	/**
	 * showActiveAdvertList Action
	 * @author Roger Luo
	 * @return
	 * @throws Exception
	 */
	public String showActiveAdvertList() throws Exception {
		advertList = advertisementServices.getActiveAdvertList(pagenum);
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("advertList", advertList);
		dataMap.putAll(pageinfo2map.pageInfoMap(advertList));// 把分页信息放进dataMap
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String showActiveAdvertListJson() throws Exception {
		showActiveAdvertList();
		return "json";
	}
	
	/**
	 * showActiveAdvertSummaryList Action
	 * @author Roger Luo
	 * @return
	 * @throws Exception
	 */
	public String showActiveAdvertSummaryList() throws Exception {
		advertList = advertisementServices.getActiveAdvertSummaryList(pagenum);
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.put("advertList", advertList);
		dataMap.putAll(pageinfo2map.pageInfoMap(advertList));// 把分页信息放进dataMap
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		return SUCCESS;
	}
	public String showActiveAdvertSummaryListJson() throws Exception {
		showActiveAdvertSummaryList();
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
			logger.info("userOpr:[deleteAdvert][" + advertisement_id + "]" + dataMap);  
			return "deleteAdvertSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			System.out.println("deleteAdvert Failed");
			logger.info("userOpr:[deleteAdvert][" + advertisement_id + "]" + dataMap);  
			return "deleteAdvertFailed";
		}
	}
	
	public String deleteAdvertJson() throws Exception {
		deleteAdvert();
		return "json";
	}
	
	/**
	 * updateAdvertById Action
	 * @return
	 * @throws Exception
	 */
	public String updateAdvertById() throws Exception {
		int result = advertisementServices.updateAdvert(advertisement_id,
				advertisement_title, advertisement_type,
				advertisement_bigphoto_url, advertisement_smallphoto_url,
				advertisement_summary, advertisement_content,
				advertisement_istop, advertisement_status);
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			logger.info("userOpr:[updateAdvertById][" + advertisement_id + "]" + dataMap);  
			return "updateAdvertByIdSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			System.out.println("updateAdvertById Failed");
			logger.info("userOpr:[updateAdvertById][" + advertisement_id + "]" + dataMap);  
			return "updateAdvertByIdFailed";
		}
	}
	public String updateAdvertByIdJson() throws Exception {
		updateAdvertById();
		return "json";
	}
	
	/**
	 * updateAdvertIsTop Action
	 * @return
	 * @throws Exception
	 */
	public String updateAdvertIsTop() throws Exception {
		int result = advertisementServices.updateAdvertIsTop(advertisement_id, advertisement_istop);
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			logger.info("userOpr:[updateAdvertIsTop][" + advertisement_id + "]" + dataMap);  
			return "updateAdvertIsTopSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			System.out.println("updateAdvertIsTop Failed");
			logger.info("userOpr:[updateAdvertIsTop][" + advertisement_id + "]" + dataMap);  
			return "updateAdvertIsTopFailed";
		}
	}
	public String updateAdvertIsTopJson() throws Exception {
		updateAdvertIsTop();
		return "json";
	}
	
	/**
	 * updateAdvertStatus Action
	 * @return
	 * @throws Exception
	 */
	public String updateAdvertStatus() throws Exception {
		int result = advertisementServices.updateAdvertStatus(advertisement_id, advertisement_status);
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		if (result == ResultCode.SUCCESS) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			System.out.println(dataMap);
			logger.info("userOpr:[updateAdvertStatus][" + advertisement_id + "]" + dataMap); 
			return "updateAdvertStatusSuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, false));
			System.out.println(dataMap);
			System.out.println("updateAdvertStatus Failed");
			logger.info("userOpr:[updateAdvertStatus][" + advertisement_id + "]" + dataMap); 
			return "updateAdvertStatusFailed";
		}
	}
	public String updateAdvertStatusJson() throws Exception {
		updateAdvertStatus();
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

	public Integer getAdvertisement_type() {
		return advertisement_type;
	}

	public void setAdvertisement_type(Integer advertisement_type) {
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

	public Integer getPagenum() {
		return pagenum;
	}

	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}

	public Integer getAdvertisement_istop() {
		return advertisement_istop;
	}

	public void setAdvertisement_istop(Integer advertisement_istop) {
		this.advertisement_istop = advertisement_istop;
	}

	public Integer getAdvertisement_status() {
		return advertisement_status;
	}

	public void setAdvertisement_status(Integer advertisement_status) {
		this.advertisement_status = advertisement_status;
	}

	public String getUpdateResult() {
		return updateResult;
	}

	public void setUpdateResult(String updateResult) {
		this.updateResult = updateResult;
	}
	public String getOrder_col() {
		return order_col;
	}
	public void setOrder_col(String order_col) {
		this.order_col = order_col;
	}
	public String getOrder_value() {
		return order_value;
	}
	public void setOrder_value(String order_value) {
		this.order_value = order_value;
	}
}
