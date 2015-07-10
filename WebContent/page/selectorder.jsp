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
	src="${pageContext.request.contextPath }/jquery/selectorderPage.js"></script>
<script type="text/javascript">
	var order_status = "${dataMap.orderlist.order_status}";
	var order_id = "${dataMap.orderlist.order_id}";
	var order_book_time = "${dataMap.orderlist.order_book_time}";
	var order_book_location = "${dataMap.orderlist.order_book_location}";
	var order_create_time = "${dataMap.orderlist.order_create_time}";
	var order_contact_name = "${dataMap.orderlist.order_contact_name}";
	var order_contact_mobile = "${dataMap.orderlist.order_contact_mobile}";
	var order_remark = "${dataMap.orderlist.order_remark}";
</script>
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
			 -> 訂單詳情
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
				<tr id="orderid_tr" style="display:none">
					<td>訂單ID (OrderID)</td>
					<td>
					<input name="order_id" id="order_id" />
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
					<td>預約時間  (Order Book Time)</td>
					<td>
					<input readonly="true" id="order_book_time" />
					</td>
				</tr>
				<tr>
					<td>預約地點 (Order Book Location)</td>
					<td>
					<input readonly="true" id="order_book_location" />
					</td>
				</tr>
				<tr>
					<td>創建時間 (Order Create Time)</td>
					<td>
					<input readonly="true" id="order_create_time" />
					</td>
				</tr>
				<tr>
					<td>聯繫人 (Contact Point)</td>
					<td>
					<input readonly="true" id="order_contact_name" />
					</td>
				</tr>
				<tr>
					<td>聯繫電話 (Telephone Number)</td>
					<td>
					<input readonly="true" id="order_contact_mobile" />
					</td>
				</tr>
				
				<tr>
					<td>訂單狀態 (OrderStatus)</td>
					<td>
						<input readonly="true" id="order_status" />
						</td>
				</tr>
				
				<tr>
					<td>備註 (Remark)</td>
					<td><input type="text" name="order_remark" id="order_remark" readonly="true" /></td>
				</tr>
				 
				<tr>
					<td colspan="2" align="center">
					<input type="button" value="取消(Cancel)" Onclick="location='${pageContext.request.contextPath}/orderlist_unorderlist.action?user_id=${sessionScope.user_id}&offset=0'" /></td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>