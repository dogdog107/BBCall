<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register Page</title>
</head>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function() {
		$("#username").blur(function(){
			$.post("${pageContext.request.contextPath}/user_checkUserNameJson.action",{"username": $(this).val()}, function(data){
				if(data.checkUserNameResult){
					$("#checkUserNameResult").html("<font color=green>用户名可以使用</font>");
				}else{
					$("#checkUserNameResult").html("<font color=red>用户名已存在</font>");
				}
			});
		});
	});
</script>
<body>
	<h1>Normal Customer Register Page</h1>
	<table>
		<tr>
			<td width="150">Register Type:</td>
			<td><select name="type" size="1">
					<option value="1" selected>Customer
					<option value="2">Master
			</select></td>
		</tr>
		<tr>
			<td>Username:</td>
			<td><input type="text" name="username" id="username" /></td>
			<td><span
				id="checkUserNameResult"></span></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type="password" name="password" id="password" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="Submit" /></td>
		</tr>
	</table>


	<h1>Struts Customer Register Page</h1>
	<s:form action="user_register" theme="simple">
		<table>
			<tr>
				<td width="150">Register Type:</td>
				<td><s:select name="type" size="1" list="#{1:'Customer',2:'Master'}"></s:select></td>
			</tr>
			<tr>
				<td>Username:</td>
				<td><s:textfield name="account" size="20" id="username" /></td>
				<td><span id="checkUserNameResult"></span></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><s:password name="password" size="20" id="password" /></td>
			</tr>
			<tr>
				<td colspan="2"><s:submit label="Submit"></s:submit></td>
			</tr>
		</table>
	</s:form>
</body>
</html>