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
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/checkUserName.js"></script>
<body>
	<h1>Normal Customer Register Page</h1>
	<table>
		<tr>
			<td width="150">Register Type:</td>
			<td><select name="usertype" size="1">
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
				<td><s:select name="usertype" size="1" list="#{1:'Customer',2:'Master'}"></s:select></td>
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
				<td>Nickname:</td>
				<td><s:textfield name="name" size="20" /></td>
			</tr>
			<tr>
				<td>Photo:</td>
				<td><s:textfield name="picurl" size="20" /></td>
			</tr>
			<tr>
				<td>Mobile:</td>
				<td><s:textfield name="mobile" size="20" /></td>
			</tr>
			<tr>
				<td>Gender:</td>
				<td><s:select name="gender" size="1" list="#{'1':'Male','2':'Female'}"></s:select></td>
			</tr>
			<tr>
				<td>E-Mail:</td>
				<td><s:textfield name="email" size="20" /></td>
			</tr>
			<tr>
				<td>language:</td>
				<td><s:textfield name="language" size="20" /></td>
			</tr>
			<tr>
				<td>skill:</td>
				<td><s:textfield name="skill" size="20" /></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><s:textfield name="description" size="20" /></td>
			</tr>
			<tr>
				<td colspan="2"><s:submit label="Submit"></s:submit></td>
			</tr>
		</table>
	</s:form>
	
		<h1>Struts Update Page</h1>
	<s:form action="user_update" theme="simple">
		<table>
			<tr>
				<td width="150">Token:</td>
				<td><s:textfield name="token" size="20" /></td>
			</tr>
			<tr>
				<td>UserID:</td>
				<td><s:textfield name="userid" size="20" /></td>
			</tr>
			<tr>
				<td>Account:</td>
				<td><s:textfield name="account" size="20" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><s:password name="password" size="20" /></td>
			</tr>
			<tr>
				<td>User Type:</td>
				<td><s:select name="usertype" size="1" list="#{1:'Customer',2:'Master',3:'Admin'}"></s:select></td>
			</tr>
			<tr>
				<td>Nickname:</td>
				<td><s:textfield name="name" size="20" /></td>
			</tr>
			<tr>
				<td>Photo:</td>
				<td><s:textfield name="picurl" size="20" /></td>
			</tr>
			<tr>
				<td>Mobile:</td>
				<td><s:textfield name="mobile" size="20" /></td>
			</tr>
			<tr>
				<td>Gender:</td>
				<td><s:select name="gender" size="1" list="#{'Male':'Male','Female':'Female'}"></s:select></td>
			</tr>
			<tr>
				<td>AddressCode:</td>
				<td><s:textfield name="addresscode" size="20" /></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><s:textfield name="address" size="20" /></td>
			</tr>
			<tr>
				<td>E-Mail:</td>
				<td><s:textfield name="email" size="20" /></td>
			</tr>
			<tr>
				<td>Language:</td>
				<td><s:textfield name="language" size="20" /></td>
			</tr>
			<tr>
				<td>Skill:</td>
				<td><s:textfield name="skill" size="20" /></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><s:textfield name="description" size="20" /></td>
			</tr>
			<tr>
				<td>Access Group:</td>
				<td><s:textfield name="accessgroup" size="20" /></td>
			</tr>
			<tr>
				<td>Status:</td>
				<td><s:select name="status" size="1" list="#{1:'Active',2:'Pause',3:'Pending',4:'Locked'}"></s:select></td>
			</tr>
			<tr>
				<td colspan="2"><s:submit label="Submit"></s:submit></td>
			</tr>
		</table>
	</s:form>
</body>
</html>