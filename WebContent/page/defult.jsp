<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link href="css/admin.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
	var photourl = "${sessionScope.user.user_pic_url}";
	var usertype = "${sessionScope.user.user_type}";
	var status = "${sessionScope.user.user_status}";
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/defultPage.js?token=${sessionScope.user.user_token}"></script>

</head>
<body onload="onload()">
	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>
		<tr height=28>
			<td background=./img/title_bg1.jpg>当前位置:主页</td>
		</tr>
		<tr>
			<td bgcolor=#b1ceef height=1></td>
		</tr>
		<tr height=20>
			<td background=./img/shadow_bg.jpg></td>
		</tr>
	</table>
	<table cellspacing=0 cellpadding=0 width="90%" align=center border=0>
		<tr height=100>
			<td align=middle width=100>
			<img id="user_photo" height="100" src="" width="100" />
			</td>
			<td width=60>&nbsp;</td>
			<td>
				<table height=100 cellspacing=0 cellpadding=0 width="100%" border=0>

					<tr>
						<td>当前时间：<label id="showTime"></label></td>
					</tr>
					<tr>
						<td style="font-weight: bold; font-size: 16px">${sessionScope.user.user_account}</td>
					</tr>
					<tr>
						<td>欢迎进入网站管理中心！</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan=3 height=10></td>
		</tr>
	</table>
	<table cellspacing=0 cellpadding=0 width="95%" align=center border=0>
		<tr height=20>
			<td></td>
		</tr>
		<tr height=22>
			<td style="padding-left: 20px; font-weight: bold; color: #ffffff"
				align=middle background=./img/title_bg2.jpg>您的相关信息</td>
		</tr>
		<tr bgcolor=#ecf4fc height=12>
			<td></td>
		</tr>
		<tr height=20>
			<td></td>
		</tr>
	</table>
	<table cellspacing=0 cellpadding=2 width="95%" align=center border=0>
		<tr>
			<td align=right width=100>登陆帐号：</td>
			<td style="color: #880000">${sessionScope.user.user_account}</td>
		</tr>
		<tr>
			<td align=right>电子邮箱：</td>
			<td style="color: #880000">${sessionScope.user.user_email}</td>
		</tr>
		<tr>
			<td align=right>真实姓名：</td>
			<td style="color: #880000">${sessionScope.user.user_name}</td>
		</tr>
		<tr>
			<td align=right>手机号：</td>
			<td style="color: #880000">${sessionScope.user.user_mobile}</td>
		</tr>
		<tr>
			<td align=right>身份：</td>
			<td style="color: #880000"><span id="usertype"></span></td>
		</tr>
		<tr>
			<td align=right>帐号状态：</td>
			<td style="color: #880000"><span id="status"></span></td>
		</tr>
		<tr>
			<td align=right>登录标识：</td>
			<td style="color: #880000">${sessionScope.user.user_token}</td>
		</tr>
		<tr>
			<td align=right>登录时间：</td>
			<td style="color: #880000">${sessionScope.user.user_login_time}</td>
		</tr>
		<tr>
			<td align=right>注册时间：</td>
			<td style="color: #880000">${sessionScope.user.user_create_time}</td>
		</tr>
	</table>
	<div style="text-align: center;">
		<p>
			来源：<a href="" target="_blank">源码之家</a>
		</p>
	</div>
</body>
</html>