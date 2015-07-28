<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>修改用戶信息 Update User Information</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico"
	type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css"
	type="text/css" rel="stylesheet" />
<link
	href="${pageContext.request.contextPath }/page/lightbox2/css/lightbox.css"
	type="text/css" rel="stylesheet" />

</head>

<body onload="onload()">

	<table cellspacing="0" cellpadding="0" width="100%" align="center"
		border="0" style="font-size: 12px;">
		<tr height="28">
			<td
				background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a
				href="${pageContext.request.contextPath }/page/defult.jsp"
				target="main">主頁(Home)</a> -> 用戶詳細信息(User information details)
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

	<div style="font-size: 13px; margin: 10px 5px">
		<div class="div_search">
			<span> <input id="btnBack" type="submit"
				value="返回用户列表/Go Back" onclick="javascript:history.go(-1);" /> <input
				id="btnBack" type="submit" value="編輯用戶信息/Edit User Info"
				onclick="location.href='user_editUser.action?token=${sessionScope.user_token}&userid=${userid}'" />
			</span>
			<div id="div_message" class="div_message" style="display: none"
				width="50%" align="left">
				<span id="message"> </span>
			</div>
		</div>
		<table border="1" width="100%" class="table_update">
			<tr id="userid_tr">
				<td width="300px">用戶ID (UserID)</td>
				<td>${userid}</td>
			</tr>
			<tr>
				<td>用戶頭像 (UserPhoto)</td>
				<td><img id="user_photo" src="${picurl}" height="80" width="80" /><br /></td>
			</tr>
			<tr>
				<td>用戶類型 (UserType)</td>
				<td id="usertype"></td>
			</tr>
			<tr>
				<td>帳號 (Account)</td>
				<td>${account}</td>
			</tr>
			<tr>
				<td>用戶姓名 (User Name)</td>
				<td>${name}</td>
			</tr>
			<tr>
				<td>用戶性別 (User Gender)</td>
				<td id="gender"></td>
			</tr>
			<tr>
				<td>手機號碼 (User Mobile)</td>
				<td>${mobile}</td>
			</tr>
			<tr>
				<td>電子郵箱 (User Email)</td>
				<td>${email}</td>
			</tr>
			<tr>
				<td>用戶語言 (User Language)</td>
				<td>${language}</td>
			</tr>
			<tr>
				<td>默認地址 (User Address)</td>
				<td>${address}</td>
			</tr>
			<tr>
				<td>用戶描述 (User Description)</td>
				<td>${description}</td>
			</tr>
			<tr>
				<td>用戶狀態 (User Status)</td>
				<td><select id="statusOpr"
					onchange="updateStatus(this.id, this.value)">
						<option value="1">Active</option>
						<option value="2">Pause</option>
						<option value="3">Pending</option>
						<option value="4">Locked</option>
				</select></td>
			</tr>
			<tr>
				<td>擁有的技能 (User Skill)</td>
				<td id="userSkillID"></td>
			</tr>
			<tr>
				<td>待批準的技能 (Pending Skill)<br /> <span style="color:red">(點擊技能查看技能證書)</span>
				</td>
				<td id="pendingSkill">
					<div id="template" style="display: none;">
						<select id="statusOpr"
							onchange="updateStatus(this.id, this.value)">
							<option value="0">待審核</option>
							<option value="1">審核通過</option>
							<option value="2">審核不通過</option>
						</select>&nbsp;&nbsp;<a id="PSkill" href="" title=""></a> <br />
					</div>
				</td>
			</tr>
		</table>
	</div>
<script type="text/javascript">
	var token = "${sessionScope.user_token}";
	var usertype = "${usertype}";
	var gender = "${gender}";
	var status = "${status}";
	var userid = "${userid}";
	var userskill = "${skill}";
	var updateResult = "${updateResult}";
	var updateErrmsg = "${dataMap.errmsg}";
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/showUserPage.js?token=${sessionScope.user_token}"></script>
<!-- 引入 Lightbox (压缩文件) -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/page/lightbox2/js/lightbox.min.js"></script>
</body>
</html>