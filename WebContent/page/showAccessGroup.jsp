<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico"
	type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css"
	type="text/css" rel="stylesheet" />

</head>
<body onload="onload()">

	<table cellspacing="0" cellpadding="0" width="100%" align="center"
		border="0" style="font-size: 12px;">
		<tr height="28">
			<td
				background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a
				href="defult.jsp" target="main">主頁(Home)</a> -> 權限組詳情頁面(AccessGroup
				Details)
			</td>
		</tr>
		<tr>
			<td bgcolor="#b1ceef" height="1"></td>
		</tr>
		<tr height="20">
			<td
				background="${pageContext.request.contextPath }/page/img/shadow_bg.jpg"></td>
		</tr>
	</table>
	<div></div>
	<div class="div_content">
		<div class="div_search">
			<span> <input id="btnBack" type="submit"
				value="返回權限組列表/Go Back"
				onclick="location.href='${pageContext.request.contextPath }/page/accessGroupList.jsp'" />
				<input id="btnBack" type="submit" value="編輯權限組/Edit AccessGroup"
				onclick="location.href='access_showEditAccessGroupPage.action?token=${sessionScope.user_token}&accessgroup_name=${accessgroup_name}&accessgroup_id=${accessgroup_id}'" />
			</span>
			<div id="div_message" class="div_message" style="display: none"
				width="50%" align="left">
				<span id="message"> </span>
			</div>
		</div>
		<div id="div_message" class="div_message" style="display: none">
			<span id="message"> </span>
		</div>
		<div style="font-size: 12px; margin: 10px 5px;">
			<table class="table_list" border="1" width="100%">
				<tbody id="datas">
					<tr style="font-size: 15px; color: green;">
						<td width="200px"><span>權限組名字：</span><br /> <span>(AccessGroup
								Name):</span></td>
						<td style="text-align: left;">${accessgroup_name }</td>
					</tr>
					<tr style="color: #1c94c4">
						<td>權限名字<br />Access Name
						</td>
						<td>權限說明<br />Access Description
						</td>
					</tr>
					<tr id="template" style="display: none; font-size: 12px;">
						<td id="access_name" style="text-align: left"></td>
						<td id="access_description" style="text-align: left"></td>
					</tr>
				</tbody>
				<tr>
					<td colspan="20" align="center"></td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		var token = "${sessionScope.user_token}";
		var link = "${pageContext.request.contextPath }";
		var accessGroupId = "${accessgroup_id }";
		var accessGroupName = "${accessgroup_name }";
		var updateResult = "${updateResult}";
		var updateErrmsg = "${dataMap.errmsg}";
	</script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/showAccessGroupPage.js"></script>
</body>
</html>