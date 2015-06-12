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
	<h1>Struts get  order Page</h1>
	<s:form action="orderlist_select" theme="simple">
	order_id:<s:textfield name="order_id" size="20"></s:textfield>
	<br>
		<s:submit label="submit"></s:submit>
	</s:form>

</body>
</html>