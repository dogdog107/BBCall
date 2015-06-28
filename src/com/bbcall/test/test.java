package com.bbcall.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bbcall.mybatis.table.AddressList;
import com.bbcall.struts.services.OrderlistServices;
import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = null;
		ctx = new FileSystemXmlApplicationContext("/conf_xml/applicationContext.xml");
		OrderlistServices orderlistServices = (OrderlistServices) ctx.getBean("orderlistServices");
		
//		List<AddressList> adlist = orderlistServices.getAddresslist();
		
		int result = orderlistServices.checkAdsList(110000);
		
		List<AddressList> adlist = orderlistServices.getAddresslist();
		
		System.out.println(result);	
		
		for (int i=0;i<adlist.size();i++) {
			System.out.println(adlist.get(i).getAreaname());
		}
		
		
//		UserMapper userMapper = (UserMapper) ctx.getBean("userMapper");
//		
//		User user = userMapper.getUserByAccount("hong");
//		
//		if (user == null) {
//			System.out.print("null");
//		}
//		
//		System.out.println(user.getUser_name());
//		
//		
//		OrderlistServices orderlistServices = (OrderlistServices) ctx.getBean("orderlistServices");
//		
//		int result = orderlistServices.getUnOrders("hong");
//		
//		List<Orderlist> orderlists = orderlistServices.orderlistinfos();
//		
//		if (orderlists == null) {
//			System.out.print("null");
//		}
//		
//		for(int i=0; i < orderlists.size(); i++) {
//			System.out.println(orderlists.get(i).getOrder_contact_name());
//		}
		
//		int i = orderlistServices.addOrder("1992-10-19 23:52:18", "广东汕尾", new BigInteger("12332132122"), "Maple", "False", 0, "c:/", "ssssssss", 4.59, "maplehong", "washing");
//		int i = orderlistServices.ChangeOrderStatus("abc", 1);
	}
}