<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>修改用户信息</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<link href="${pageContext.request.contextPath }/page/css/mine.css"
	type="text/css" rel="stylesheet" />

<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/updatePage.js"></script>
<script type="text/javascript">
	var usertype = "${sessionScope.user.user_type}";
	var gender = "${sessionScope.user.user_gender}";
</script>
</head>

<body onload="onload()">

	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>
		<tr height=28>
			<td background=${pageContext.request.contextPath }
				/page/img/title_bg1.jpg>当前位置:<a href="right.jsp" target=main>主页</a>
				-> 修改用户信息
			</td>
		</tr>
		<tr>
			<td bgcolor=#b1ceef height=1></td>
		</tr>
		<tr height=20>
			<td background=${pageContext.request.contextPath }
				/page/img/shadow_bg.jpg></td>
		</tr>
	</table>
	<div></div>

	<div style="font-size: 13px; margin: 10px 5px">
		<form action="user_update" method="post">
			<table border="1" width="100%" class="table_a">
				<div style="font-size: 13px; margin: 10px 5px">
					<span><s:if test="#{ dataMap.updateResult}">
							<font color="green">修改成功！${ dataMap.errmsg}</font>
							<s:else>
								<font color="red">修改失败！</font>
							</s:else>
						</s:if></span>
				</div>
				<tr id="userid_tr" style="display: none">
					<td>用户ID</td>
					<td><input type="text" name="userid" id="userid"
						value="${sessionScope.user.user_id}" disabled="disabled" /></td>
				</tr>
				<tr id="token_tr" style="display: none">
					<td>用户token</td>
					<td><input type="text" name="token" id="token"
						value="${sessionScope.user.user_token}" disabled="disabled" /></td>
				</tr>
				<tr>
					<td>用户类型</td>
					<td><select name="usertype" id="usertype">
							<option>请选择</option>
							<option value="1">Customer</option>
							<option value="2">Master</option>
							<option value="3">Admin</option>
					</select></td>
				</tr>
				<tr>
					<td>帐号名</td>
					<td><input type="text" name="account" onfocus="this.value=''"
						onblur="if(this.value==''){this.value='${sessionScope.user.user_account}'}"
						value="${sessionScope.user.user_account}" /></td>
				</tr>
				<tr>
					<td>修改密码</td>
					<td><input type="password" name="prepassword" id="prepassword"
						onblur="if(this.value!=''){document.getElementById('repwd').style.display=''}" /></td>
				</tr>
				<tr id="repwd" style="display: none">
					<td>再输一次</td>
					<td><input type="password" name="password" id="password"
						onblur="checkpwd('password')" /> <span id="pwdnotice"></span></td>
				</tr>
				<tr>
					<td>用户姓名</td>
					<td><input type="text" name="" onfocus="this.value=''"
						onblur="if(this.value==''){this.value='${sessionScope.user.user_name}'}"
						value="${sessionScope.user.user_name}" /></td>
				</tr>
				<tr>
					<td>用户性别</td>
					<td><select name="gender" id="gender">
							<option>请选择</option>
							<option value="1">Male</option>
							<option value="2">Female</option>
					</select></td>
				</tr>
				<tr>
					<td>手机号码</td>
					<td><input type="text" name="mobile" onfocus="this.value=''"
						onblur="if(this.value==''){this.value='${sessionScope.user.user_mobile}'}"
						value="${sessionScope.user.user_mobile}" /></td>
				</tr>
				<tr>
					<td>电子邮箱</td>
					<td><input type="text" name="email" onfocus="this.value=''"
						onblur="if(this.value==''){this.value='${sessionScope.user.user_email}'}"
						value="${sessionScope.user.user_email}" /></td>
				</tr>
				<tr>
					<td>用户语言</td>
					<td><input type="text" name="language" onfocus="this.value=''"
						onblur="if(this.value==''){this.value='${sessionScope.user.user_language}'}"
						value="${sessionScope.user.user_language}" /></td>
				</tr>
				<tr>
					<td>用户头像</td>
					<td><input type="file" name="f_goods_image"
						value="./img/2013-12-33.jpg" /></td>
				</tr>
				<tr>
					<td>用户技能</td>
					<td><input type="text" name="skill" onfocus="this.value=''"
						onblur="if(this.value==''){this.value='${sessionScope.user.user_skill}'}"
						value="${sessionScope.user.user_skill}" /></td>
				</tr>
				<tr>
					<td>默认地址</td>
					<td><input type="text" name="address" onfocus="this.value=''"
						onblur="if(this.value==''){this.value='${sessionScope.user.user_address}'}"
						value="${sessionScope.user.user_address}" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="修改" /><input
						type="button" value="取消" onclick="location.href='right.jsp'" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>