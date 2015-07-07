<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link href="./css/admin.css" type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
</head>
<body>
	<table cellspacing=0 cellpadding=0 width="100%"
		background="./img/header_bg.jpg" border=0>
		<tr height=56>
			<td width=260><img height=56 src="./img/header_left.jpg"
				width=260></td>
			<td style="font-weight: bold; color: #fff; padding-top: 20px"
				align=middle>當前用戶：${sessionScope.user_account}&nbsp;&nbsp;
				<a style="color: #fff" href="${pageContext.request.contextPath }/page/update.jsp" target=main>修改個人信息</a> &nbsp;&nbsp; <a
				style="color: #fff"
				onclick="if (confirm('確定要登出嗎？')) return true; else return false;"
				href="user_logout.action" target=_top>登出系統</a>
			</td>
			<td align=right width=268><img height=56
				src="./img/header_right.jpg" width=268></td>
		</tr>
	</table>
	<table cellspacing=0 cellpadding=0 width="100%" border=0>
		<tr bgcolor=#1c5db6 height=4>
			<td></td>
		</tr>
	</table>
</body>
</html>