package com.bbcall.struts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;
import com.bbcall.mybatis.dao.AddressListMapper;
import com.bbcall.mybatis.table.AddressList;
@Scope("prototype")
@Service("addressServices")
public class AddressServices {

	@Autowired
	private AddressListMapper addressListMapper;
	private List<AddressList> addresslist;

	// ###################
	// ## 根据addresscode读取省、市、区名
	// ###################

	public String checkAddresscodeName(Integer addresscode) {
		List<AddressList> addresslist = addressListMapper
				.getAddressByAreano(addresscode);// addressList 对象

		if (addresslist.size() == 0)
			return null;
		int arealevel = addresslist.get(0).getArealevel();// 读取对应的地址level

		String addressname = "";
		if (arealevel > 1) {
			for (int i = 1; i < arealevel; i++) {// 完整读取地址名（省、市、区）
				switch (i) {
				case 1:// 得到省级名字
					int tempcode1 = addresscode / 10000;
					addressname = addressListMapper
							.getAddressByAreano(tempcode1 * 10000).get(0)
							.getAreaname();
					break;
				case 2:// 得到市级名字
					addressname = addressname
							+ addressListMapper
									.getAddressByAreano(
											addresslist.get(0).getParentno())
									.get(0).getAreaname();
					break;
				}
			}
		}

		addressname = addressname + addresslist.get(0).getAreaname();// 得到原来addresscode的地址名
		return addressname;
	}

	// ###################
	// ## 读取Child省、市、区列表
	// ###################

	public int checkChildAdsList(Integer addresscode) {
		System.out.println("Here is UserServices.checkChildAdsList method...");

		List<AddressList> addresslist = addressListMapper
				.getAddressByParentno(addresscode);
		if (addresslist.size() > 0) {

			this.addresslist = addresslist;

			return ResultCode.SUCCESS;
		} else {
			return ResultCode.ADDRESS_NULL;
		}
	}

	// ###################
	// ## 读取省、市、区列表
	// ###################

	public int checkAdsList(Integer addresscode) {
		System.out.println("Here is UserServices.checkAdsList method...");

		List<AddressList> addresslist = addressListMapper
				.getAddressByAreano(addresscode);
		if (addresslist.size() > 0) {

			this.addresslist = addresslist;

			return ResultCode.SUCCESS;
		} else {
			return ResultCode.ADDRESS_NULL;
		}
	}

	
	/**
	 * getter & setter
	 * @return
	 */
	public List<AddressList> getAddresslist() {
		return addresslist;
	}
}
