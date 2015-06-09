<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>add order</title>
</head>
<body>
	<h1>Struts add order Page</h1>
	<s:form action="orderlist_add" theme="simple" method="post" enctype="multipart/form-data">
	order_book_year:<s:select list="#{'1990':'1990','2000':'2000','2010':'2010'}" name="order_book_year"></s:select>
	<br>
	order_book_month:<s:select list="#{'1':'1','2':'2','3':'3'}" name="order_book_month"></s:select>
	<br>
	order_book_day:<s:select list="#{'10':'10','20':'20','30':'30'}" name="order_book_day"></s:select>
	<br>
	order_book_location:<s:textfield name="order_book_location" size="20"></s:textfield>
  	<br>
	order_contact_mobile:<s:textfield name="order_contact_mobile" size="20"></s:textfield>
	<br>
	order_contact_name:<s:textfield name="order_contact_name" size="20"></s:textfield>
	<br>
	order_urgent:<s:radio list="#{'true':'true','false':'false'}" name="order_urgent" value="false"/>
	<br>
	order_urgent_bonus:<s:textfield name="order_urgent_bonus" size="20"></s:textfield>
	<br>
	order_pic_url1:<s:file name="orderFile" label="文件1"></s:file>
	<br>
	order_pic_url2:<s:file name="orderFile" label="文件2"></s:file>
	<br>
	order_pic_url3:<s:file name="orderFile" label="文件3"></s:file>
	<br>
	order_description:<s:textfield name="order_description" size="20"></s:textfield>
	<br>
	order_price:<s:textfield name="order_price" size="20"></s:textfield>
	<br>
	user_account:<s:textfield name="user_account" size="20"></s:textfield>
	<br>
	order_type:<s:textfield name="order_type" size="20"></s:textfield>
	<br>
		<s:submit label="submit"></s:submit>
	</s:form>

</body>
</html>