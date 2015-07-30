<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv=content-type content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css" type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/viewwashorderPage.js?order_id=${sessionScope.order_id}"></script>
<script type="text/javascript">
	var token = "${sessionScope.user.user_token}";
</script>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
</head>
<body>

	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0
		style="font-size: 12px;">
		<tr height=28>
			<td background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a href="${pageContext.request.contextPath }/page/defult.jsp" target=main>主頁(Home)</a>
			 -> 洗衣订单列表
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
	<table cellspacing=0 cellpadding=0 width="95%" align=center border=0>
		<tr height=20>
			<td></td>
		</tr>
		<tr height=22>
			<td style="padding-left: 20px; font-weight: bold; color: #ffffff"
				align=middle background=./img/title_bg2.jpg>订单相关信息</td>
		</tr>
		<tr bgcolor=#ecf4fc height=12>
			<td></td>
		</tr>
		<tr height=20>
			<td></td>
		</tr>
	</table>
	<form action="orderlist_change" id="orderlist_change" method="post">



		<table cellspacing=0 cellpadding=2 width="95%" align=center border=0>
			<tr style="display: none">
				<td><input type="text" id="order_id" name="order_id"
					value="<s:property value='dataMap.orderlist.order_id' />"></input></td>
			</tr>
			<tr>
				<td align=right width=100>订单状态：</td>
				<td style="color: #880000"><s:if
						test="%{dataMap.orderlist.order_status == 1}">新建訂單</s:if> <s:if
						test="%{dataMap.orderlist.order_status == 2}">待評價訂單</s:if> <s:if
						test="%{dataMap.orderlist.order_status == 3}">已評價訂單</s:if> <s:if
						test="%{dataMap.orderlist.order_status == 4}">收到貨物</s:if> <s:if
						test="%{dataMap.orderlist.order_status == 5}">正在清洗</s:if> <s:if
						test="%{dataMap.orderlist.order_status == 6}">正在配送</s:if></td>
			</tr>
			<tr>
				<td align=right width=100>更改状态：</td>
				<td style="color: #880000">
				<select name="order_status" style="width: 100px;">
					<option selected="selected" value="0">请选择</option>
					<option value="1">新建訂單</option>
					<option value="2">待評價訂單</option>
					<option value="3">已評價訂單</option>
					<option value="4">收到貨物</option>
					<option value="5">正在清洗</option>
					<option value="6">正在配送</option>
				</select>
				</td>
			</tr>
			<tr>
				<td align=right>備註：</td>
				<td style="color: #880000"><input id="order_description" name="order_description" type="text"></input></td>
			</tr>

		</table>

		<div style="text-align: center;">
			<input type="submit" value="修改"
				Onclick="location='${pageContext.request.contextPath}/page/updatewashorder.jsp?order_id=${dataMap.orderlist.order_id}&order_status=${dataMap.orderlist.order_status}'"></input>
			<input type="button" value="返回"
				Onclick="location='${pageContext.request.contextPath}/orderlist_getwashorderlist.action'"></input>
		</div>

	</form>
	<div class="footer"></div>
</body>
</html>