package com.bbcall.test;

import java.math.BigInteger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bbcall.struts.services.OrderlistServices;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = null;
		ctx = new FileSystemXmlApplicationContext("/conf_xml/applicationContext.xml");
		OrderlistServices orderlistServices = (OrderlistServices) ctx.getBean("orderlistServices");
		
//		int i = orderlistServices.addOrder("1992-10-19 23:52:18", "广东汕尾", new BigInteger("12332132122"), "Maple", "False", 0, "c:/", "ssssssss", 4.59, "maplehong", "washing");
		int i = orderlistServices.ChangeOrderStatus("abc", 1);
	}
}