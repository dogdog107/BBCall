<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css" type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript">
	var token = "${sessionScope.user_token}";
	var link = "${pageContext.request.contextPath }";
</script>
</head>
<body onload="onload()">

	<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0"
		style="font-size: 12px;">
		<tr height="28">
			<td background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a href="defult.jsp"
				target="main">主頁(Home)</a> -> 廣告詳情頁面(Advertisement Details)
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
					排序(Order By)：
					<select name="col_name" id="col_name" onchange="col_name_change(this.value)">
						<option value="user_id">默認排序(ID)</option>
						<option value="user_account">按用戶帳號(Account)</option>
						<option value="user_name">按用戶姓名(Name)</option>
						<option value="user_type">按用戶身份(Type)</option>
						<option value="user_status">按用戶狀態(Status)</option>
						<option value="user_create_time">按註冊時間(CreateTime)</option>
						<option value="user_login_time">按登錄時間(LoginTime)</option>
					</select>
					<select name="specify_value" id="specify_value" onchange="specify_value_change(this.value)">
						<option value='ASC'>升序(ASC)</option>
						<option value='DESC'>降序(DESC)</option>
					</select>
					<span id="search_value_span" style="display: none">
						<font color=#1c94c4 id="search_value_message"></font>
						<input name="search_value" id="search_value" type="text" style="width: 100px;"/>
						<input value="查詢/Submit" type="submit" onclick="search_value()"/>
					</span>
			</span>
		</div>
		<div id="div_message" class="div_message" style="display: none">
			<span id="message"> </span>
		</div>
		<div style="font-size: 133.33%; margin: 10px 5px;">
			<div class="advert_head">
				<h1 align="center">${advertisement_title }</h1>
				<div class="l1" style="font-size: 13px; color: #666" align="center">
					<span>${advertisement_type }</span> <span> | </span> <span>${advertisement_create_time }</span>
				</div>
			</div>
			<div width="100%" align="center">
				<img src="${advertisement_bigphoto_url }" height="200" width="400" />
			</div>
			<h2 align="center">${advertisement_summary }</h2>
			<div class="advert_content">${advertisement_content }</div>
		</div>
	</div>
</body>
</html>