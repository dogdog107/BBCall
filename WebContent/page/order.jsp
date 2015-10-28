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
		<span style="color: #1c94c4">排序(Order By):</span>
			<select name="col_name" id="col_name" onchange="col_name_change(this.value)">
				<option value="order_id">默認排序(ID)</option>
				<option value="order_create_time">按創建時間(Create Time)</option>
				<option value="order_book_time">按預約時間(Book Time)</option>
			</select>
			<select name="order_value" id="order_value" onchange="order_value_change(this.value)">
				<option value='ASC'>升序(ASC)</option>
				<option value='DESC'>降序(DESC)</option>
			</select>
		</span>
		<div id="div_message" class="div_message" style="display: none">
			<span id="message"> </span>
		</div>
	</div>
	<div class="div_search2">
		<span> <span style="color: #1c94c4">具體搜索(Search): </span> <span>|
				預約日期：</span> <input type="text" name="order_time" id="order_time"
			class="tcal"></input> <br /><span>| 訂單狀態：</span> <select id="order_status"
			name="order_status" style="width: 100px;">
				<option value="">--請選擇--</option>
				<option value="1">新建訂單</option>
				<option value="7">已出價訂單</option>
				<option value="2">待評價訂單</option>
				<option value="3">已評價訂單</option>
				<option value="4">收到貨物</option>
				<option value="5">正在清洗</option>
				<option value="6">正在配送</option>
		</select> <span>| 地點：</span> <select id="order_book_location_code"
			name="order_book_location_code">
				<option value="">--請選擇--</option>
				<option value='810101'>香港島->中西區</option>
				<option value='810102'>香港島->灣仔區</option>
				<option value='810103'>香港島->東區</option>
				<option value='810104'>香港島->南區</option>
				<option value='810201'>九龍->油尖旺區</option>
				<option value='810202'>九龍->深水埗區</option>
				<option value='810203'>九龍->九龍城區</option>
				<option value='810204'>九龍->黃大仙區</option>
				<option value='810205'>九龍->觀塘區</option>
				<option value='810301'>新界->北區</option>
				<option value='810302'>新界->大埔區</option>
				<option value='810303'>新界->沙田區</option>
				<option value='810304'>新界->西貢區</option>
				<option value='810305'>新界->荃灣區</option>
				<option value='810306'>新界->屯門區</option>
				<option value='810307'>新界->元朗區</option>
				<option value='810308'>新界->葵青區</option>
				<option value='810309'>新界->離島區</option>
		</select> <span>| 序號：</span> <input type="text" name="order_id" id="order_id"
			style="width: 80px"></input> <span>| 師傅名：</span> <input type="text"
			name="order_master_name" id="order_master_name" style="width: 100px"></input>
			<input value="查詢/Submit" type="submit" onclick="searchorder()" /> <input
			value="重置/Reset" type="submit" onclick="reset()" />

		</span>
	</div>
	<div style="font-size: 13px; margin: 10px 5px;">
		<table class="table_list" border="1" width="100%">
			<tbody id="datas">
				<tr style="font-weight: bold;">
					<td width="40px">序號</td>
					<td width="80px">訂單類型</td>
					<td width="160px">訂單生成時間</td>
					<td width="160px">預約時間</td>
					<td width="250px">預約地點</td>
					<td width="100px">負責師傅</td>
					<td width="80px">訂單狀態</td>
					<td colspan="2" align="center">操作</td>
				</tr>
					<tr id="template" style="display:none">
						<td id="orderid"></td>
						<td id="ordertype"></td>
						<td id="ordercreatetime"></td>
						<td id="orderbooktime"></td>
						<td id="orderbooklocation"></td>
						<td id="ordermastername"></td>
						<td id="orderstatus"></td>
						<td>
							<input id="orderview" type="submit" value="查看" onclick=""/><input id="btnDelete" type="submit" value="删除" onclick=""/>
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
	var token = "${sessionScope.user_token}";
	</script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/orderlistPage.js?token=${sessionScope.user.user_token}"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/paging.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/tcal.js"></script>
</body>
</html>