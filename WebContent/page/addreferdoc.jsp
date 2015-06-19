<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv=content-type content="text/html; charset=utf-8" />
<link href="./css/mine.css" type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/referdocPage.js?token=${sessionScope.user.user_token}"></script>
<script type="text/javascript">
	var token = "${sessionScope.user.user_token}";
	var link = "${pageContext.request.contextPath}";
</script>
<%
  String path=request.getContextPath();
%>
</head>
<body onload="onload()">

	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0 style="font-size: 12px;">
		<tr height=28>
			<td background=./img/title_bg1.jpg>当前位置:<a href="right.jsp" target=main>主页</a>
				-> 添加訂單推荐
			</td>
		</tr>
		<tr>
			<td bgcolor=#b1ceef height=1></td>
		</tr>
		<tr height=20>
			<td background=./img/shadow_bg.jpg></td>
		</tr>
	</table>
	<div></div>
		<div style="font-size: 13px; margin: 10px 5px;">
			<form id="referdoc_add" action="referdoc_add" method="post">
				<label for="referdoc_type">
					<strong>訂單類型：</strong>
				</label>
				<input id="referdoc_type" name="referdoc_type" type="text"><span>訂單類型不能為空</span><br /><br />
				
				<label for="referdoc_price">
					<strong>訂單參考價位：</strong>
				</label>
				<input id="referdoc_price" name="referdoc_price" type="text"><span>訂單參考價格不能為空</span><br /><br />
				<input type="submit" value="添加"></input>
				<input type="button" value="返回" Onclick="location='${pageContext.request.contextPath}/page/referdoc.jsp'"></input>
			</form>
		</div>
</body>
</html>