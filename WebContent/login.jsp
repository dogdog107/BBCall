<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<s:form action="user_login" theme="simple">
  	username:<s:textfield name="username" size="20" />
		<br>
  	password:<s:password name="password" size="20" />
		<br>
		<s:submit label="submit"></s:submit>
	</s:form>

</body>
</html>