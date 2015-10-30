<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css" type="text/css" rel="stylesheet" />

</head>
<body onload="onload()">

	<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0"
		style="font-size: 12px;">
		<tr height="28">
			<td background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a href="defult.jsp"
				target="main">主頁(Home)</a> -> 師傅列表(Master List)
			</td>
		</tr>
		<tr>
			<td bgcolor="#b1ceef" height="1"></td>
		</tr>
		<tr height="20">
			<td background="${pageContext.request.contextPath }/page/img/shadow_bg.jpg"></td>
		</tr>
	</table>
	<div></div>
	<div class="div_content">
		<div class="div_search">
			<span>
					<span>排序(Order By):</span>
					<select name="order_col" id="order_col" onchange="col_name_change(this.value)">
						<option value="user_id">默認排序(ID)</option>
						<option value="user_account">按用戶帳號(Account)</option>
						<option value="user_name">按用戶姓名(Name)</option>
						<option value="user_status">按用戶狀態(Status)</option>
						<option value="user_create_time">按註冊時間(CreateTime)</option>
						<option value="user_login_time">按登錄時間(LoginTime)</option>
					</select>
					<select name="order_value" id="order_value" onchange="order_value_change(this.value)">
						<option value='ASC'>升序(ASC)</option>
						<option value='DESC'>降序(DESC)</option>
					</select>
					<span id="search_value_span" style="display: none">
						<font color=#1c94c4 id="search_value_message"></font>
						<input name="search_value" id="search_value" type="text" style="width: 100px;"/>
						<input value="查詢/Submit" type="submit" onclick="search_value()"/>
					</span>
					<div id="div_message" class="div_message" style="display: none">
						<span id="message"> </span>
					</div>
			</span>
		</div>
		<div style="font-size: 12px; margin: 10px 5px;">
			<table class="table_list" border="1" width="100%">
				<tbody id="datas">
					<tr style="color:#1c94c4">
						<td width="60px">用戶序號<br/>User ID</td>
						<td width="60px">頭像<br/>User Pic</td>
						<td width="150px">帳戶<br/>Account</td>
						<td width="100px">姓名<br/>Name</td>
						<td width="50px">身份<br/>Type</td>
						<td width="50px">狀態<br/>Status</td>
						<td width="140px">登陸時間<br/>Login Time</td>
						<td width="140px">註冊時間<br/>Create Time</td>
						<td  colspan="2" align="center">操作<br/>Operations</td>
					</tr>
					<tr id="template" style="display: none; font-size: 12px;">
						<td id="userid"></td>
						<td id="picurl"></td>
						<td id="account"></td>
						<td id="name"></td>
						<td id="usertype"></td>
						<td id="status"></td>
						<td id="logintime"></td>
						<td id="createtime"></td>
						<td id="status_operation">
							<span>修改狀態:</span>
							<select id="statusOpr" onchange="updateStatus(this.id, this.value)">
								<option value="1">Active</option>
								<option value="2">Pause</option>
								<option value="3">Pending</option>
								<option value="4">Locked</option>
							</select><br/>
							<input id="btnDetail" type="submit" value="詳情/Detail" onclick=""/>
							<input id="btnDelete" type="submit" value="刪除/Delete" onclick=""/>
						</td>
					</tr>
				</tbody>
				<tr>
					<td colspan="20" style="text-align: center;"></td>
				</tr>
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
	</div>
	<div class="footer"></div>
	<script type="text/javascript">
		var token = "${sessionScope.user_token}";
		var link = "${pageContext.request.contextPath }";

		var where_col = "user_type";
		var where_value = "2";
	</script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/getUserWhereOrderBy.js?token=${sessionScope.user_token}"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/paging.js"></script>
</body>
</html>