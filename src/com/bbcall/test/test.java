package com.bbcall.test;

import java.math.BigInteger;
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
		
//		int result = orderlistServices.checkAdsList(110000);
		
		orderlistServices.getOrderById(2);
		
		System.out.println(orderlistServices.orderlistinfo.getOrder_book_time());
		
//		orderlistServices.addOrder("1992-10-19 23:52:18", "广东汕尾", 100010, new BigInteger("12332132122"), "Maple", "False", 0.00, "c:/", "ssssssss", 4.59, "maplehong", 15001);
//		int i = orderlistServices.ChangeOrderStatus("abc", 1);
	}
}