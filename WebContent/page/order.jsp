<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv=content-type content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath }/page/css/tcal.css" type="text/css" rel="stylesheet" />

<%
	String path = request.getContextPath();
%>
</head>
<body onload="onload()">

	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0
		style="font-size: 12px;">
		<tr height=28>
			<td background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a
				href="${pageContext.request.contextPath }/page/defult.jsp"
				target=main>主頁(Home)</a> -> 訂單列表
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

	<div class="div_search">
		<span>
			<span>预约时间：</span>
				<input type="text" name="order_time" id="order_time" class="tcal"></input>
				&nbsp;
				訂單狀態：
				<select id="order_status" name="order_status" style="width: 100px;">
					<option value="">--請選擇--</option>
					<option value="1">新建訂單</option>
					<option value="7">已出價訂單</option>
					<option value="2">待評價訂單</option>
					<option value="3">已評價訂單</option>
					<option value="4">收到貨物</option>
					<option value="5">正在清洗</option>
					<option value="6">正在配送</option>
				</select>
		</span>
	</div>
	<div class="div_search">
		<span>
				<span>地点：</span>
				<select id="adscode_2" onchange="getsub()">
					<option value="0">--請選擇城市--</option>
					<option value="1">香港島</option>
					<option value="2">九龍</option>
					<option value="3">新界</option>
				</select>
				<select id="order_book_location_code" name="order_book_location_code">
					<option value="">--請選擇鎮區--</option>
				</select>
				&nbsp;
				<span>序号：</span>
				<input type="text" name="order_id" id="order_id"></input>
				&nbsp;
				<span>師傅名：</span>
				<input type="text" name="order_master_name" id="order_master_name"></input>
				<input value="查詢" type="submit" onclick="searchorder()"/>

		</span>
	</div>
	<div id="div_message" class="div_message" style="display: none">
		<span id="message"> </span>
	</div>
	<div style="font-size: 13px; margin: 10px 5px;">
		<table class="table_list" border="1" width="100%">
			<tbody id="datas">
				<tr style="font-weight: bold;">
					<td>序號</td>
					<td>訂單生成時間</td>
					<td>預約時間</td>
					<td>預約地點</td>
					<td>負責師傅</td>
					<td>訂單狀態</td>
					<td colspan="2" align="center">操作</td>
				</tr>
					<tr id="template" style="display:none">
						<td id="orderid"></td>
						<td id="ordercreatetime"></td>
						<td id="orderbooktime"></td>
						<td id="orderbooklocation"></td>
						<td id="ordermastername"></td>
						<td id="orderstatus"></td>
						<td>
							<input id="orderview" type="submit" value="查看" onclick=""/>
						</td>
					</tr>

			</tbody>
		</table>
		<div class="wrap">
				<div id="page_bar" class="fenye">
					<ul>
						<li id="first">首頁</li>
						<li id="top" onclick="topclick()">上一頁</li>
						<li class="xifenye" id="xifenye"><a id="xiye"></a>/<a
							id="mo"></a>
							<div class="xab" id="xab" style="display: none">
								<ul id="uljia">

								</ul>
							</div></li>
						<li id="down" onclick="downclick()">下一頁</li>
						<li id="last">尾頁</li>
					</ul>
				</div>
			</div>
	</div>
	<div class="footer"></div>
	<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript">
	var link = "${pageContext.request.contextPath}";
	var user_id = ${sessionScope.user_id};
	</script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/orderlistPage.js?token=${sessionScope.user.user_token}"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/paging.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/tcal.js"></script>
</body>
</html>