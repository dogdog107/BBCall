<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>修改用户信息</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css"
	type="text/css" rel="stylesheet" />

<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/updatePage.js?token=${sessionScope.user.user_token}"></script>
<script type="text/javascript">
	var usertype = "${sessionScope.user.user_type}";
	var gender = "${sessionScope.user.user_gender}";
	var addresscode = "${sessionScope.user.user_address_code}";
	var language = "${sessionScope.user.user_language}";
	var skill = "${sessionScope.user.user_skill}";
</script>
</head>

<body onload="onload()">

	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0
		style="font-size: 12px;">
		<tr height=28>
			<td background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a href="${pageContext.request.contextPath }/page/defult.jsp" target=main>主頁(Home)</a>
			 -> 修改用户信息失敗
			</td>
		</tr>
		<tr>
			<td bgcolor=#b1ceef height=1></td>
		</tr>
		<tr height=20>
			<td background="${pageContext.request.contextPath }/page/img/shadow_bg.jpg"></td>
		</tr>
	</table>
	<div></div>

	<div style="font-size: 13px; margin: 10px 5px">
		<form action="user_update" method="post">
			<table border="1" width="100%" class="table_update">
				<div style="font-size: 13px; margin: 10px 5px">
					<span> <font color="red">修改失败！${ dataMap.errmsg}</font>
					</span>
				</div>
				<div align="center">
					<input type="button" value="返回"
						Onclick="location='${pageContext.request.contextPath}/page/referdoc.jsp'"></input>
				</div>
			</table>
		</form>
	</div>
	<div class="footer"></div>
</body>
</html>