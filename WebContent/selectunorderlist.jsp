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
	<h1>Struts get un order Page</h1>
	<s:form action="orderlist_selectUnOrderlist" theme="simple">
	user_account:<s:textfield name="user_account" size="20"></s:textfield>
	<br>
	skilllist:
	<s:checkbox name="skilllist" fieldValue="aaa" label="aaa"></s:checkbox>
	<s:checkbox name="skilllist" fieldValue="bbb" label="bbb"></s:checkbox>
	<s:checkbox name="skilllist" fieldValue="ccc" label="ccc"></s:checkbox>
	<s:checkbox name="skilllist" fieldValue="ddd" label="ddd"></s:checkbox>
	<s:checkbox name="skilllist" fieldValue="eee" label="eee"></s:checkbox>
	<s:checkbox name="skilllist" fieldValue="fff" label="fff"></s:checkbox>
	<br>
	locationlist:
	<s:checkbox name="locationlist" fieldValue="zzz" label="zzz"></s:checkbox>
	<s:checkbox name="locationlist" fieldValue="yyy" label="yyy"></s:checkbox>
	<s:checkbox name="locationlist" fieldValue="xxx" label="xxx"></s:checkbox>
	<s:checkbox name="locationlist" fieldValue="www" label="www"></s:checkbox>
	<br>
		<s:submit label="submit"></s:submit>
	</s:form>

</body>
</html>