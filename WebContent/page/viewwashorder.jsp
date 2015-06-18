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
	src="${pageContext.request.contextPath }/jquery/viewwashorderPage.js?order_id=${sessionScope.order_id}"></script>
<script type="text/javascript">
	var token = "${sessionScope.user.user_token}";
</script>
</head>
<body>

	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0 style="font-size: 12px;">
		<tr height=28>
			<td background=./img/title_bg1.jpg>当前位置:<a href="right.jsp" target=main>主页</a>
				-> 洗衣订单列表
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
	<table cellspacing=0 cellpadding=2 width="95%" align=center border=0>
		<tr>
			<td align=right width=100>预约时间：</td>
			<td style="color: #880000">${dataMap.orderlist.order_book_time}</td>
		</tr>
		<tr>
			<td align=right>预约地点：</td>
			<td style="color: #880000">${dataMap.orderlist.order_book_location}</td>
		</tr>
		<tr>
			<td align=right>照片：</td>
			<s:iterator value="dataMap.orderFileFileName" id="number">
				<td style="color: #880000"><img src="<s:property value="number"/>"></img>
				</td>
			</s:iterator>
		</tr>
		<tr>
			<td align=right>客户姓名：</td>
			<td style="color: #880000">${dataMap.orderlist.order_contact_name}</td>
		</tr>
		<tr>
			<td align=right>客户联系方式：</td>
			<td style="color: #880000">${dataMap.orderlist.order_contact_mobile}</td>
		</tr>
	</table>	
        
</body>
</html>