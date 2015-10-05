package com.bbcall.struts.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.PageInfoToMap;
import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.mybatis.table.Referdoc;
import com.bbcall.struts.services.ReferdocServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("referdocAction")
@SuppressWarnings("serial")
public class ReferdocAction extends ActionSupport {

	@Autowired
	private ReferdocServices referdocServices;

	private Map<String, Object> dataMap;
	private PageInfoToMap pageinfo2map = new PageInfoToMap();// 新建PageInfoToMap对象

	private String referdoc_id;
	private String referdoc_type;
	private String referdoc_parentno;
	private int referdoc_level;
	private String referdoc_price;
	private String referdoc_flag;
	private String referdoc_pic_url;
	private String referdoc_downpic_url;
	private List<String> order_type_code_list;
	private Integer pagenum; // 页面页数

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	// 添加参考数据action
	public String add() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		double price = 0;
		if (referdoc_price != null && !referdoc_price.equals("")) {
			price = Double.parseDouble(referdoc_price);
		}

		int result = referdocServices.addReferdoc(referdoc_type,
				referdoc_parentno, referdoc_level, price, referdoc_flag,
				pagenum, referdoc_pic_url, referdoc_downpic_url);

		if (result == ResultCode.SUCCESS) {
			List<Referdoc> referdoclist = referdocServices.referdocinfos();
			dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(pageinfo2map.pageInfoMap(referdoclist));// 把分页信息放进dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("addResult", true);
			return "getsuccess";
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("addResult", false); // 放入registerResult
			return "addFailed";
		}

	}

	public String addJson() throws Exception {
		add();
		return "json";
	}

	public String update() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int referdocid = Integer.parseInt(referdoc_id);

		double price = 0;
		if (referdoc_price != null && !referdoc_price.equals("")) {
			price = Double.parseDouble(referdoc_price);
		}

		int result = referdocServices.updateReferdoc(referdocid,
				referdoc_parentno, price, referdoc_flag, pagenum);

		List<Referdoc> referdoclist = referdocServices.referdocinfos();

		if (result == ResultCode.SUCCESS) {

			dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(pageinfo2map.pageInfoMap(referdoclist));// 把分页信息放进dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("updateResult", true);
		}

		return "getsuccess";

	}

	public String updateJson() throws Exception {
		update();
		return "json";
	}

	public String delete() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int referdocid = Integer.parseInt(referdoc_id);

		int result = referdocServices.deleteReferdoc(referdocid, pagenum);

		if (result == ResultCode.SUCCESS) {
			List<Referdoc> referdoclist = referdocServices.referdocinfos();
			dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(pageinfo2map.pageInfoMap(referdoclist));// 把分页信息放进dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("deleteResult", true);
		}

		return "getsuccess";
	}

	public String deleteJson() throws Exception {
		delete();
		return "json";
	}

	public String select() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int referdocid = Integer.parseInt(referdoc_id);
		int result = referdocServices.getReferdoc(referdocid);

		if (result == ResultCode.SUCCESS) {
			Referdoc referdoc = referdocServices.referdocinfo();
			dataMap.put("referdoc", referdoc);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("deleteResult", true);
		}

		return SUCCESS;
	}

	public String selectJson() throws Exception {
		select();
		return "json";
	}

	public String getlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = referdocServices.getReferdoclist(referdoc_parentno,
				pagenum);

		if (result == ResultCode.SUCCESS) {
			List<Referdoc> referdoclist = referdocServices.referdocinfos();
			dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(pageinfo2map.pageInfoMap(referdoclist));// 把分页信息放进dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("getlistResult", true);
		}

		return "getsuccess";
	}

	public String getlistJson() throws Exception {
		getlist();
		return "json";
	}

	public String getparentlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = referdocServices.getParentReferdoclist();

		if (result == ResultCode.SUCCESS) {
			List<Referdoc> parentreferdoclist = referdocServices
					.referdocinfos();
			dataMap.put("parentreferdoclist", parentreferdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("getparentlistResult", true);
		}

		return SUCCESS;
	}

	public String getparentlistJson() throws Exception {
		getparentlist();
		return "json";
	}

	public String getchildlist() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		System.out.println("referdoc_parentno " + referdoc_parentno);
		int parentno = 0;
		if (referdoc_parentno != null) {
			parentno = Integer.parseInt(referdoc_parentno);
		}

		int result = referdocServices.getChildReferdoclist(parentno);

		if (result == ResultCode.SUCCESS) {
			List<Referdoc> referdoclist = referdocServices.referdocinfos();
			dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("getchildlistResult", true);
		}

		return "getsuccess";
	}

	public String getchildlistJson() throws Exception {
		getchildlist();
		return "json";
	}

	public String getsubskill() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		List<Referdoc> referdoclist1 = referdocServices.getChildRefer(11000);
		List<Referdoc> referdoclist2 = referdocServices.getChildRefer(12000);
		List<Referdoc> referdoclist3 = referdocServices.getChildRefer(13000);
		List<Referdoc> referdoclist4 = referdocServices.getChildRefer(14000);
		List<Referdoc> referdoclist5 = referdocServices.getChildRefer(15000);
		List<Referdoc> referdoclist6 = referdocServices.getChildRefer(16000);
		List<Referdoc> referdoclist7 = referdocServices.getChildRefer(17000);
		List<Referdoc> referdoclist8 = referdocServices.getChildRefer(18000);
		List<Referdoc> referdoclist9 = referdocServices.getChildRefer(19000);
		List<Referdoc> referdoclist10 = referdocServices.getChildRefer(20000);
		List<Referdoc> referdoclist11 = referdocServices.getChildRefer(21000);
		List<Referdoc> referdoclist12 = referdocServices.getChildRefer(22000);

		dataMap.put("referdoclist1", referdoclist1);
		dataMap.put("referdoclist2", referdoclist2);
		dataMap.put("referdoclist3", referdoclist3);
		dataMap.put("referdoclist4", referdoclist4);
		dataMap.put("referdoclist5", referdoclist5);
		dataMap.put("referdoclist6", referdoclist6);
		dataMap.put("referdoclist7", referdoclist7);
		dataMap.put("referdoclist8", referdoclist8);
		dataMap.put("referdoclist9", referdoclist9);
		dataMap.put("referdoclist10", referdoclist10);
		dataMap.put("referdoclist11", referdoclist11);
		dataMap.put("referdoclist12", referdoclist12);
		dataMap.putAll(Tools.JsonHeadMap(0, true));
		// dataMap.put("resultcode", result);
		// dataMap.put("errmsg", ResultCode.getErrmsg(result));
		// dataMap.put("getchildlistResult", true);

		return "getsuccess";
	}

	public String getsubskillJson() throws Exception {
		getsubskill();
		return "json";
	}

	public String getchildlist2() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = referdocServices.getChildReferdoclist2(referdoc_parentno,
				pagenum);

		if (result == ResultCode.SUCCESS) {
			List<Referdoc> referdoclist = referdocServices.referdocinfos();
			dataMap.put("referdoclist", referdoclist);
			dataMap.putAll(pageinfo2map.pageInfoMap(referdoclist));// 把分页信息放进dataMap
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result);
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("getchildlistResult", true);
		}

		return "getsuccess";
	}

	public String getchildlist2Json() throws Exception {
		getchildlist2();
		return "json";
	}

	public String chkreferdoctype() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int parentno = Integer.parseInt(referdoc_parentno);

		int result = referdocServices.chkReferType(referdoc_type, parentno);

		if (result == ResultCode.REFERDOC_TYPE_NOTEXIST) {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("chkreferdoctypeResult", true); //
			// 放入checkUserNameResult
		} else {
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("chkreferdoctypeResult", false); //
			// 放入checkUserNameResult
		}
		return SUCCESS;
	}

	public String chkreferdoctypeJson() throws Exception {
		chkreferdoctype();
		return "json";
	}

	public String referprice() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = 1;
		int type_code = 0;
		Referdoc referd = new Referdoc();
		double referprice = 0;

		if (order_type_code_list == null) {
			dataMap.put("referprice", 0);
			dataMap.putAll(Tools.JsonHeadMap(result, true));
			// dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			// dataMap.put("errmsg", ResultCode.getErrmsg(result));
			// dataMap.put("referpriceResult", false); //
			// 放入chkreferdoctypeResult
		} else {
			for (int i = 0; i < order_type_code_list.size(); i++) {
				type_code = Integer.parseInt(order_type_code_list.get(i));
				result = referdocServices.getReferdoc(type_code);
				referd = referdocServices.referdocinfo();
				referprice = referprice + referd.getReferdoc_price();

				System.out.println(referprice);
				dataMap.put("referprice", referprice);
				dataMap.putAll(Tools.JsonHeadMap(result, true));
				// dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
				// dataMap.put("errmsg", ResultCode.getErrmsg(result));
				// dataMap.put("referpriceResult", false); //
				// 放入chkreferdoctypeResult
			}
		}

		return SUCCESS;
	}

	public String referpriceJson() throws Exception {
		referprice();
		return "json";
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public ReferdocServices getReferdocServices() {
		return referdocServices;
	}

	public void setReferdocServices(ReferdocServices referdocServices) {
		this.referdocServices = referdocServices;
	}

	public String getReferdoc_id() {
		return referdoc_id;
	}

	public void setReferdoc_id(String referdoc_id) {
		this.referdoc_id = referdoc_id;
	}

	public String getReferdoc_type() {
		return referdoc_type;
	}

	public void setReferdoc_type(String referdoc_type) {
		this.referdoc_type = referdoc_type;
	}

	public String getReferdoc_price() {
		return referdoc_price;
	}

	public void setReferdoc_price(String referdoc_price) {
		this.referdoc_price = referdoc_price;
	}

	public List<String> getOrder_type_code_list() {
		return order_type_code_list;
	}

	public void setOrder_type_code_list(List<String> order_type_code_list) {
		this.order_type_code_list = order_type_code_list;
	}

	public String getReferdoc_parentno() {
		return referdoc_parentno;
	}

	public void setReferdoc_parentno(String referdoc_parentno) {
		this.referdoc_parentno = referdoc_parentno;
	}

	public int getReferdoc_level() {
		return referdoc_level;
	}

	public void setReferdoc_level(int referdoc_level) {
		this.referdoc_level = referdoc_level;
	}

	public String getReferdoc_flag() {
		return referdoc_flag;
	}

	public void setReferdoc_flag(String referdoc_flag) {
		this.referdoc_flag = referdoc_flag;
	}

	public Integer getPagenum() {
		return pagenum;
	}

	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}

	public String getReferdoc_pic_url() {
		return referdoc_pic_url;
	}

	public void setReferdoc_pic_url(String referdoc_pic_url) {
		this.referdoc_pic_url = referdoc_pic_url;
	}

	public String getReferdoc_downpic_url() {
		return referdoc_downpic_url;
	}

	public void setReferdoc_downpic_url(String referdoc_downpic_url) {
		this.referdoc_downpic_url = referdoc_downpic_url;
	}

}
