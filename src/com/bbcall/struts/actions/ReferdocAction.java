package com.bbcall.struts.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
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

	private int referdoc_id;
	private String referdoc_type;
	private int referdoc_parentno;
	private int referdoc_level;
	private double referdoc_price;
	private List<String> order_type_code_list;

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	// 添加参考数据action
	public String add() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = referdocServices.addReferdoc(referdoc_type,
				referdoc_parentno, referdoc_level, referdoc_price);

		if (result == ResultCode.SUCCESS) {
			List<Referdoc> referdoclist = referdocServices.referdocinfos();
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("addResult", true);
			return SUCCESS;
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("addResult", false); // 放入registerResult
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

		int result = referdocServices.updateReferdoc(referdoc_id,
				referdoc_type, referdoc_parentno, referdoc_level,
				referdoc_price);
		
	    referdocServices.getChildReferdoclist(referdoc_parentno);
		List<Referdoc> referdoclist = referdocServices.referdocinfos();
		
		if (result == ResultCode.SUCCESS) {
			
			
			for (int i=0; i<referdoclist.size(); i++) {
				System.out.println(referdoclist.get(i).getReferdoc_type());
			}
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("updateResult", true);
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

		int result = referdocServices.deleteReferdoc(referdoc_id);

		if (result == ResultCode.SUCCESS) {
			List<Referdoc> referdoclist = referdocServices.referdocinfos();
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("deleteResult", true);
		}

		return SUCCESS;
	}

	public String deleteJson() throws Exception {
		delete();
		return "json";
	}

	public String select() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = referdocServices.getReferdoc(referdoc_id);

		if (result == ResultCode.SUCCESS) {
			Referdoc referdoc = referdocServices.referdocinfo();
			dataMap.put("referdoc", referdoc);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("deleteResult", true);
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

		int result = referdocServices.getReferdoclist();

		if (result == ResultCode.SUCCESS) {
			List<Referdoc> referdoclist = referdocServices.referdocinfos();
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("getlistResult", true);
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
			List<Referdoc> parentreferdoclist = referdocServices.referdocinfos();
			dataMap.put("parentreferdoclist", parentreferdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("getparentlistResult", true);
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

		int result = referdocServices.getChildReferdoclist(referdoc_parentno);

		if (result == ResultCode.SUCCESS) {
			List<Referdoc> referdoclist = referdocServices.referdocinfos();
			dataMap.put("referdoclist", referdoclist);
			dataMap.put("resultcode", result);
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("getchildlistResult", true);
		}

		return "getsuccess";
	}

	public String getchildlistJson() throws Exception {
		getchildlist();
		return "json";
	}

	public String chkreferdoctype() throws Exception {
		dataMap = new HashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		int result = referdocServices.chkReferType(referdoc_type,
				referdoc_parentno);

		if (result == ResultCode.REFERDOC_TYPE_NOTEXIST) {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("chkreferdoctypeResult", true); // 放入checkUserNameResult
		} else {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("chkreferdoctypeResult", false); // 放入checkUserNameResult
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
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("referpriceResult", false); // 放入chkreferdoctypeResult
		} else {
			for (int i = 0; i< order_type_code_list.size(); i++) {
				type_code = Integer.parseInt(order_type_code_list.get(i));
				result = referdocServices.getReferdoc(type_code);
				referd = referdocServices.referdocinfo();
				referprice = referprice + referd.getReferdoc_price();

				System.out.println(referprice);
				dataMap.put("referprice", referprice);
				dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
				dataMap.put("errmsg", ResultCode.getErrmsg(result));
				dataMap.put("referpriceResult", false); // 放入chkreferdoctypeResult
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

	public int getReferdoc_id() {
		return referdoc_id;
	}

	public void setReferdoc_id(int referdoc_id) {
		this.referdoc_id = referdoc_id;
	}

	public String getReferdoc_type() {
		return referdoc_type;
	}

	public void setReferdoc_type(String referdoc_type) {
		this.referdoc_type = referdoc_type;
	}

	public double getReferdoc_price() {
		return referdoc_price;
	}

	public void setReferdoc_price(double referdoc_price) {
		this.referdoc_price = referdoc_price;
	}

	public List<String> getOrder_type_code_list() {
		return order_type_code_list;
	}

	public void setOrder_type_code_list(List<String> order_type_code_list) {
		this.order_type_code_list = order_type_code_list;
	}

	public int getReferdoc_parentno() {
		return referdoc_parentno;
	}

	public void setReferdoc_parentno(int referdoc_parentno) {
		this.referdoc_parentno = referdoc_parentno;
	}

	public int getReferdoc_level() {
		return referdoc_level;
	}

	public void setReferdoc_level(int referdoc_level) {
		this.referdoc_level = referdoc_level;
	}

}
