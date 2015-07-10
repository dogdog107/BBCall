<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>修改用戶信息 Update User Information</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css"
	type="text/css" rel="stylesheet" />

<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function() {
		var usertype = ${usertype};

		switch (usertype) {
		case 1:
			$('#usertype').text("User");
			break;
		case 2:
			$('#usertype').text("Master");
			break;
		case 3:
			$('#usertype').text("Admin");
			break;
		case 4:
			$('#usertype').text("SuperAdmin");
			break;
		}
	});

	
</script>
</head>

<body onload="onload()">

	<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0" style="font-size: 12px;">
		<tr height="28">
			<td background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a href="${pageContext.request.contextPath }/page/defult.jsp" target="main">主頁(Home)</a>
				-> 修改用戶信息(Update user information)
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

	<div style="font-size: 13px; margin: 10px 5px">
		<form id="update_form" action="user_update" method="post" enctype="multipart/form-data">
			<div style="font-size: 13px; margin: 10px 5px">
				<span> <s:if test="dataMap.result">
							<p align="center" style="font-size: 15px;color: green"> ## 成功！${ dataMap.errmsg} ## </p>
					</s:if> <s:else>
						<s:if test="!dataMap.result">
								<p align="center" style="font-size: 15px;color: red"> ## 失敗！${ dataMap.errmsg} ## </p>
						</s:if>
					</s:else>
				</span>
			</div>
			<table border="1" width="100%" class="table_update">
					<input type="hidden" id="token" name="token" value="${sessionScope.user_token}" />
				<tr id="userid_tr" style="display: none">
					<td width="100">用戶ID (UserID)</td>
					<td>
					${userid}
					</td>
				</tr>
				<tr>
					<td>用戶頭像 (UserPhoto)</td>
					<td>
						<img id="user_photo" src="${picurl}" height="80" width="80" /><br/>
					</td>
				</tr>
				<tr>
					<td>用戶類型 (UserType)</td>
					<td id="usertype">
					</td>
				</tr>
				<tr>
					<td>帳號 (Account)</td>
					<td>
					${account}
					</td>
				</tr>
				<tr>
					<td>用戶姓名 (User Name)</td>
					<td>
					${name}
					</td>
				</tr>
				<tr>
					<td>用戶性別 (User Gender)</td>
					<td>
					${gender}
					</td>
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
					<td>
					${language}
					</td>
				</tr>
				<tr>
					<td>用戶技能 (User Skill)</td>
					<td>
					${skill}
					</td>
				</tr>
				<tr>
					<td>默認地址 (User Address)</td>
					<td>
					${address}
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">							
					<select id="statusOpr" onchange="updateStatus(this.id, this.value)">
								<option value="1">Active</option>
								<option value="2">Pause</option>
								<option value="3">Pending</option>
								<option value="4">Locked</option>
							</select>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>