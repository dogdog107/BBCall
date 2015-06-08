package com.bbcall.mybatis.dao;

import java.util.List;

import com.bbcall.mybatis.table.AddressList;

public interface AddressListMapper {

	public List<AddressList> getAddressByAreano(Integer areano);
	
	public List<AddressList> getAddressByParentno(Integer parentno);
	
}
