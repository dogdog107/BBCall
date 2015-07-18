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
<!--WebUploader引入CSS-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/page/WebUploader/webuploader.css" />

</head>

<body onload="onload()">

	<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0" style="font-size: 12px;">
		<tr height="28">
			<td background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a href="${pageContext.request.contextPath }/page/defult.jsp" target="main">主頁(Home)</a>
				-> 註冊新帳戶(Register)
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
		<span id="span_message" >
		</span>
	</div>
	<div class="div_search">
		<span> <span id="span_search">  請先選擇要添加的帳戶類型 (Please choose user type firstly):</span>
		</span>
			<div id="div_message" class="div_message" style="display: none">
				<span id="message"> </span>
			</div>
	</div>
	<div id="div_main" style="font-size: 13px; margin: 10px 5px">
		<form id="register_form" action="user_register" method="post" enctype="multipart/form-data">
			<table border="1" width="100%" class="table_update">
				<input type="hidden" id="token" name="token"
					value="${sessionScope.user_token}" />
				<tr>
					<td width="300px">用戶類型 (UserType)<span style="color: red; font-weight:bold">*</span></td>
					<td><select name="usertype" id="usertype" onchange="changeUserType(this.value)">
							<option value="0">請選擇(Choose)</option>
							<option value="1">Customer</option>
							<option value="2">Master</option>
							<option value="3">Admin</option>
							<option value="4">SuperAdmin</option>
					</select></td>
				</tr>
			</table>
			<table border="1" width="100%" class="table_update" id="table_main" style="display: none">
				<tr>
					<td width="300px" >用戶頭像 (UserPhoto)<span id="td_pic" style="color: red; font-weight:bold"></span></td>
					<td>
						<input type="hidden" id="picurl" name="picurl" value="" />
						<!--用来存放item-->
						<div id="smallPhoto" class="uploader-list"></div>
						<div id="tempPicker"><div id="smallPhotoPicker">选择图片</div></div>
					</td>
				</tr>
				<tr>
					<td>用戶名 (account)<span style="color: red; font-weight:bold">*</span></td>
					<td><input type="text" name="account" id="account" /><span id="checkAccountResult"></span></td>
				</tr>
				<tr>
					<td>輸入密碼 (Enter Password)<span style="color: red; font-weight:bold">*</span></td>
					<td><input type="password" id="prepassword" /></td>
				</tr>
				<tr id="repwd" style="display: none">
					<td style="color:#55AA00">再輸一次 (Enter Password Again)<span style="color: red; font-weight:bold">*</span></td>
					<td style="border:solid #55AA00 1px;">
						<input type="password" name="password" id="password"
						onblur="checkpwd('password')" /> 
						<span id="pwdnotice"></span>
					</td>
				</tr>
				<tr>
					<td>用戶姓名 (User Name)<span id="td_name" style="color: red; font-weight:bold"></span></td>
					<td><input type="text" name="name" /></td>
				</tr>
				<tr>
					<td>用戶性別 (User Gender)<span id="td_gender" style="color: red; font-weight:bold"></span></td>
					<td><select name="gender" id="gender">
							<option value="">請選擇(Choose)</option>
							<option value="1">Male</option>
							<option value="2">Female</option>
					</select></td>
				</tr>
				<tr>
					<td>手機號碼 (User Mobile)<span id="td_mobile" style="color: red; font-weight:bold"></span></td>
					<td><input type="text" name="mobile" id="mobile" /><span id="checkMobileResult"></span></td>
				</tr>
				<tr>
					<td>電子郵箱 (User Email)<span id="td_email" style="color: red; font-weight:bold"></span></td>
					<td><input type="text" name="email" id="email" /><span id="checkEmailResult"></span></td>
				</tr>
				<tr>
					<td>用戶語言 (User Language)<span id="td_language" style="color: red; font-weight:bold"></span></td>
					<td>
					<input type="hidden" id="language" name="language" value=""/>
					
					<label><input name="languagepart" type="checkbox" id="English" value="English" />英文(English)</label>&nbsp;&nbsp;
					<label><input name="languagepart" type="checkbox" id="Cantonese" value="Cantonese" />廣東話(Cantonese)</label>&nbsp;&nbsp;
					<label><input name="languagepart" type="checkbox" id="Chinese" value="Chinese" />普通話(Chinese)</label>&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td>用戶技能 (User Skill)<span id="td_skill" style="color: red; font-weight:bold"></span></td>
					<td>
					<input type="text" name="skill" /></td>
				</tr>
				<tr>
					<td>默認地址 (User Address)<span id="td_address" style="color: red; font-weight:bold"></span></td>
					<td>
					<select id="adscode_1" onchange="getaddresslist(this.options[selectedIndex].value,1)">
							<option value="0">--請選擇省份--</option>
					</select>
					<select id="adscode_2" onchange="getaddresslist(this.options[selectedIndex].value,2)">
							<option value="0">--請選擇城市--</option>
					</select>
					<select id="adscode_3" onchange="getaddresslist(this.options[selectedIndex].value,3)">
							<option value="0">--請選擇鎮區--</option>
					</select>
					<input type="hidden" name="addresscode" id="addresscode" value=""/>
					<input type="hidden" id="addresscodename" value=""/>
					
					<input type="text" onfocus="this.value=''" id="lastads" />
					<input type="hidden" name="address" id="address" value=""/>	
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="提交(Submit)" onclick="return validate();" class="btn btn-default" /><input
						type="button" value="取消(Cancel)" onclick="location.href='defult.jsp'" class="btn btn-default" /></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		// 添加全局站点信息
		var BASE_URL = '${pageContext.request.contextPath }';
		var token = '${sessionScope.user_token}';
		var photourl = '';
		var usertype = '';
		var gender = '';
		var addresscode = '';
		var language = '';
		var skill = '';
		var registerResult = '${dataMap.result}';
		var registerMsg = '${dataMap.errmsg}';
	</script>

	<!--引入jquery-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>

	<!--WebUploader引入JS-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/page/WebUploader/webuploader.js"></script>
	<!-- 页面JS文件 -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/registerPage.js"></script>
</body>
</html>