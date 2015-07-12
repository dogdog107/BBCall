package com.bbcall.test;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bbcall.mybatis.dao.OrderlistMapper;
import com.bbcall.mybatis.table.AddressList;
import com.bbcall.mybatis.table.Orderlist;
import com.bbcall.struts.services.OrderlistServices;
import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = null;
		ctx = new FileSystemXmlApplicationContext("/conf_xml/applicationContext.xml");
		OrderlistServices orderlistServices = (OrderlistServices) ctx.getBean("orderlistServices");
//		OrderlistMapper orderlistMapper = (OrderlistMapper) ctx.getBean("orderlistMapper");
		
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		format.setLenient(false);
//
//		Timestamp ts = new Timestamp(System.currentTimeMillis());
//
//		try {
//
//			ts = new Timestamp(format.parse("2015-07-10 11:51").getTime());
//			System.out.println(ts);
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//
//		}
		
		System.out.println(new Double("4.0"));
//		List<AddressList> adlist = orderlistServices.getAddresslist();
//		
//		int result = orderlistServices.getUnOrders(1,4);
//		
//		List<Orderlist> ors = orderlistServices.orderlistinfos();
//		
//		if (ors==null) {
//			System.out.println("null");
//		}
//		
//		for (int i=0; i<ors.size();i++) {
//			System.out.println("sss"+ors.get(i).getOrder_id());
//		}
		
//		orderlistServices.getOrderById(2);
		
//		System.out.println(orderlistServices.orderlistinfo.getOrder_book_time());
		
//		orderlistServices.addOrder("2015-07-10 12:52", "香港;香港島;中西區;西區999號", 810101, new BigInteger("13144445555"), "nameaaa", "False", 0.00, "", "啊啊啊吧测的", 111, 1, 15001,1,0);
//		orderlistServices.addOrder("1992-10-19 23:52", "香港;香港島;中西區;西區999號", 810101, new BigInteger("13144445555"), "nameaaa", "False", 0.00, "", "hi倪敏", 1111, 1, 15001,1,0);
//		orderlistServices.addOrder("1992-10-19 23:52", "香港;香港島;中西區;西區999號", 810101, new BigInteger("13144445555"), "nameaaa", "False", 0.00, "", "hi倪敏", 1111, 1, 15001,1,0);
//		orderlistServices.addOrder("1992-10-19 23:52", "香港;香港島;中西區;西區999號", 810101, new BigInteger("13144445555"), "nameaaa", "False", 0.00, "", "hi倪敏", 1111, 1, 15001,1,0);
//		orderlistServices.addOrder("1992-10-19 23:52", "香港;香港島;中西區;西區999號", 810101, new BigInteger("13144445555"), "nameaaa", "False", 0.00, "", "hi倪敏", 1111, 1, 15001,1,0);
//		orderlistServices.addOrder("1992-10-19 23:52", "香港;香港島;中西區;西區999號", 810101, new BigInteger("13144445555"), "nameaaa", "False", 0.00, "", "hi倪敏", 1111, 1, 15001,1,0);
//		orderlistServices.addOrder("1992-10-19 23:52", "香港;香港島;中西區;西區999號", 810101, new BigInteger("13144445555"), "nameaaa", "False", 0.00, "", "hi倪敏", 1111, 1, 15001,1,0);
//		orderlistServices.addOrder("1992-10-19 23:52", "香港;香港島;中西區;西區999號", 810101, new BigInteger("13144445555"), "nameaaa", "False", 0.00, "", "hi倪敏", 1111, 1, 15001,1,0);
//		orderlistServices.addOrder("1992-10-19 23:52", "香港;香港島;中西區;西區999號", 810101, new BigInteger("13144445555"), "nameaaa", "False", 0.00, "", "hi倪敏", 1111, 1, 15001,1,0);
//		int i = orderlistServices.ChangeOrderStatus("abc", 1);
	}
}