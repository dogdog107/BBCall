<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/orderPage.js">
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/addorderPage.js">
</script>
<title>add order</title>
</head>

<body >
	<h1>Struts add order Page</h1>
	<s:form action="orderUpload" theme="simple" method="post" enctype="multipart/form-data">
	
	orderid:<input id="orderid" name="orderid" value="6">
	
	order_pic_url1:<s:file name="uploadlist" label="文件1"></s:file>
	<br>
	order_pic_url2:<s:file name="uploadlist" label="文件2"></s:file>
	<br>
	order_pic_url3:<s:file name="uploadlist" label="文件3"></s:file>
	<br>
		<s:submit label="submit"></s:submit>
	</s:form>

</body>
</html>