<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv=content-type content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css" type="text/css" rel="stylesheet" />
<link
	href="${pageContext.request.contextPath }/page/lightbox2/css/lightbox.css"
	type="text/css" rel="stylesheet" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
</head>
<body onload="onload()">

	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0
		style="font-size: 12px;">
		<tr height=28>
			<td background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a href="${pageContext.request.contextPath }/page/defult.jsp" target=main>主頁(Home)</a>
			 -> 洗衣訂單詳情
			</td>
		</tr>
		<tr>
			<td bgcolor=#b1ceef height=1></td>
		</tr>
		<tr height=20>
			<td background="${pageContext.request.contextPath }/page/img/shadow_bg.jpg"></td>
		</tr>
	</table>
	
	<div style="font-size: 13px; margin: 10px 5px">
		<form id="orderlist_change" action="orderlist_change" method="post">
			<div style="font-size: 13px; margin: 10px 5px">
				<span> <s:if test="dataMap.changeResult">
						<font color="green">修改成功！${ dataMap.errmsg}</font>
					</s:if> 
				</span>
			</div>
			
			<table border="1" width="100%" class="table_update">
				<tr style="font-weight: bold;">
					<td colspan="20" style="padding:10px 100px;">訂單詳情 Order Details
					</td>
				</tr>
				<tr id="orderid_tr">
					<td width="300px">訂單ID (OrderID)</td>
					<td>
					${dataMap.orderlist.order_id}
					</td>
				</tr>
				<tr style="display:none">
					<td id="orderinput">
						<input id="order_id" name="order_id" value="${dataMap.orderlist.order_id}"></input>
					</td>
				</tr>
				<tr>
					<td>訂單類型 (Order Type)</td>
					<td>
					${dataMap.orderlist.order_type}
					</td>
				</tr>
				<tr>
					<td>訂單價格 (Order Price)</td>
					<td>
					$${dataMap.orderlist.order_price}
					</td>
				</tr>
				<tr>
					<td>是否緊急訂單 (Urgent Order)</td>
					<td>
					${dataMap.orderlist.order_urgent}
					</td>
				</tr>
				<tr>
					<td>緊急獎金 (Urgent Bonus)</td>
					<td>
					$${dataMap.orderlist.order_urgent_bonus}
					</td>
				</tr>
				<tr>
					<td>創建時間 (Order Create Time)</td>
					<td>
					<!-- <input readonly="true" id="order_create_time" /> -->
					${dataMap.orderlist.order_create_time}
					</td>
				</tr>
				<tr>
					<td>預約時間  (Order Book Time)</td>
					<td>
					<!-- <input readonly="true" id="order_book_time" /> -->
					${dataMap.orderlist.order_book_time}
					</td>
				</tr>
				<tr>
					<td>預約地點 (Order Book Location)</td>
					<td>
					<!-- <input readonly="true" id="order_book_location" /> -->
					${dataMap.orderlist.order_book_location}
					</td>
				</tr>
				<tr>
					<td width="300px">訂單照片 (OrderPhoto)</td>
					<td style="color: #880000" id="order_pic">
<%--  						<s:iterator value="dataMap.orderFileFileName" id="number">
							<img src="<s:property value="number"/>" height='60' width='60'></img>
						</s:iterator> --%>
						<a id="PhotoBtn" href="" title=""></a>
						</td>
				</tr>
				
				<tr>
					<td>訂單狀態 (OrderStatus)</td>
					<td><select name="order_status" id="order_status">
							<option id="option1" value=1>新建訂單</option>
							<option id="option2" value=2>待評價訂單</option>
							<option id="option3" value=3>已評價訂單</option>
							<option id="option4" value=4>收到貨物</option>
							<option id="option5" value=5>正在清洗</option>
							<option id="option6" value=6>正在配送</option>
					</select></td>
				</tr>
				
				<tr>
					<td>訂單描述 (Description)</td>
					<td><!-- <input type="text" name="order_description" id="order_description" readonly="true" /> -->
					${dataMap.orderlist.order_description}
					</td>
				</tr>
				<tr>
					<td>備註 (Remark)</td>
					<td><input type="text" name="order_remark" id="order_remark" value="${dataMap.orderlist.order_remark}"/></td>
				</tr>
				<tr style="font-weight: bold;">
					<td colspan="20" style="padding:10px 100px;">用戶資料 Customer Info
					</td>
				</tr>
				<tr>
					<td>用戶序號 (User ID)</td>
					<td>
					${dataMap.orderlist.order_user_id}
					</td>
				</tr>
				<tr>
					<td>訂單聯繫人 (Contact Point)</td>
					<td>
					<!-- <input readonly="true" id="order_contact_name" /> -->
					${dataMap.orderlist.order_contact_name}
					</td>
				</tr>
				
				<tr>
					<td>聯繫電話 (Telephone Number)</td>
					<td>
					<!-- <input readonly="true" id="order_contact_mobile" /> -->
					${dataMap.orderlist.order_contact_mobile}
					</td>
				</tr>
				<tr style="font-weight: bold;">
					<td colspan="20" style="padding:10px 100px;">師傅資料 Master Info
					</td>
				</tr>
				<tr>
					<td>師傅序號 (Master ID)</td>
					<td id="order_master_id">
					<%-- ${dataMap.orderlist.order_master_id} --%>
					</td>
				</tr>
				<tr>
					<td>師傅姓名 (Master Name)</td>
					<td>
					${dataMap.orderlist.order_master_name}
					</td>
				</tr>
				<tr style="font-weight: bold;">
					<td colspan="20" style="padding:10px 100px;">訂單評價 Order Evaluation
					</td>
				</tr>
				<tr>
					<td>訂單評分 (Order Score)</td>
					<td>
					${dataMap.orderlist.order_score}
					</td>
				</tr>
				<tr>
					<td>訂單評語 (Order Evaluation)</td>
					<td>
					${dataMap.orderlist.order_evaluation}
					</td>
				</tr>
<%-- 
				<tr id="orderid_tr">
					<td width="300px">訂單ID (OrderID)</td>
					<td id="orderid">
					</td>
				</tr>
				<tr style="display:none">
					<td id="orderinput">
						<input id="order_id" name="order_id" value="${dataMap.orderlist.order_id}"></input>
					</td>
				</tr>
				<tr>
					<td>訂單照片 (OrderPhoto)</td>
					<td style="color: #880000">
						<s:iterator value="dataMap.orderFileFileName" id="number">
							<img
								src="<s:property value="number"/>" height='60' width='60'></img>
						</s:iterator>
						</td>
				</tr>
				<tr>
					<td>預約時間 (Order Book Time)</td>
					<td id="order_book_time">
					</td>
				</tr>
				<tr>
					<td>預約地點 (Order Book Location)</td>
					<td id="order_book_location">
					</td>
				</tr>
				<tr>
					<td>創建時間 (Order Create Time)</td>
					<td id="order_create_time">
					</td>
				</tr>
				<tr>
					<td>聯繫人 (Contact Point)</td>
					<td id="order_contact_name">
					</td>
				</tr>
				<tr>
					<td>聯繫電話 (Telephone Number)</td>
					<td id="order_contact_mobile">
					</td>
				</tr>
				
				<tr>
					<td>訂單狀態 (OrderStatus)</td>
					<td><select name="order_status" id="order_status">
							<option id="option1" value=1>新建訂單</option>
							<option id="option2" value=2>待評價訂單</option>
							<option id="option3" value=3>已評價訂單</option>
							<option id="option4" value=4>收到貨物</option>
							<option id="option5" value=5>正在清洗</option>
							<option id="option6" value=6>正在配送</option>
					</select></td>
				</tr>
				
				<tr>
					<td>備註 (Remark)</td>
					<td><input type="text" name="order_remark" id="order_remark" value="${dataMap.orderlist.order_remark}"/></td>
				</tr>
--%>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="修改(Submit)"/><input
						type="button" value="取消(Cancel)" Onclick="location='${pageContext.request.contextPath}/orderlist_getwashorderlist.action'" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="footer"></div>
<script type="text/javascript">
	var order_status = "${dataMap.orderlist.order_status}";
	var order_id = "${dataMap.orderlist.order_id}";
	var order_master_id = "${dataMap.orderlist.order_master_id}";
	var order_book_time = "${dataMap.orderlist.order_book_time}";
	var order_book_location = "${dataMap.orderlist.order_book_location}";
	var order_create_time = "${dataMap.orderlist.order_create_time}";
	var order_contact_name = "${dataMap.orderlist.order_contact_name}";
	var order_contact_mobile = "${dataMap.orderlist.order_contact_mobile}";
	var order_remark = "${dataMap.orderlist.order_remark}";
	var order_pic = "${dataMap.orderFileFileName}";
	var token = "${sessionScope.user_token}";
</script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/viewwashorderPage.js"></script>
	<!-- 引入 Lightbox (压缩文件) -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/page/lightbox2/js/lightbox.min.js"></script>
</body>
</html>