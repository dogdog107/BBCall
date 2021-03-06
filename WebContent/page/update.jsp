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

<script type="text/javascript">
	var photourl = "${sessionScope.user_pic_url}";
	var usertype = "${sessionScope.user_type}";
	var gender = "${sessionScope.user_gender}";
	var addresscode = "${sessionScope.user_address_code}";
	var accessgroup = "${sessionScope.user_access_group}";
	var language = "${sessionScope.user_language}";
	var skill = "${sessionScope.user_skill}";
	var token = "${sessionScope.user_token}";
	var account = "${sessionScope.user_account}";
	var mobile = "${sessionScope.user_mobile}";
	var email = "${sessionScope.user_email}";
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/updatePage.js?token=${sessionScope.user_token}"></script>
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
				<span> <s:if test="dataMap.result != null">
						<s:if test="dataMap.result">
							<p align="center" style="font-size: 15px; color: green">##
								修改成功！${ dataMap.errmsg} ##</p>
						</s:if>
						<s:else>
							<s:if test="!dataMap.result">
								<p align="center" style="font-size: 15px; color: red">##
									修改失敗！${ dataMap.errmsg} ##</p>
							</s:if>
						</s:else>
					</s:if>
				</span>
			</div>
			<table border="1" width="100%" class="table_update">
				<tr id="userid_tr" style="display: none">
					<td  width="300px">用戶ID (UserID)</td>
					<td>
					<span>${sessionScope.user_id}</span>
					<%-- <input name="userid" id="userid" value="${sessionScope.user_id}" /> --%>
					<input type="hidden" id="token" name="token" value="${sessionScope.user_token}" />
					<input type="hidden" id="userid" name="userid" value="${sessionScope.user_id}" />
					</td>
				</tr>
				<tr>
					<td  width="300px">用戶頭像 (UserPhoto)</td>
					<td>
						<img id="user_photo" src="" height="80" width="80" /><br/>
						<input type="file" name="upload" id="upload" onchange="upload(this)" class="btn btn-default" />
					</td>
				</tr>
				<tr>
					<td>用戶類型 (UserType)</td>
					<td><select name="usertype" id="usertype">
							<option value="0">請選擇(Choose)</option>
							<option value="1">Customer</option>
							<option value="2">Master</option>
							<option value="3">Admin</option>
							<option value="4">SuperAdmin</option>
					</select></td>
				</tr>
				<tr>
					<td>權限組 (AccessGroup)</td>
					<td><select name="accessgroup" id="accessgroup">
							<option value="">請選擇(Choose)</option>
					</select></td>
				</tr>
				<tr>
					<td>帳號 (Account)</td>
					<td><input type="text" name="account" id="account"
						value="${sessionScope.user_account}" /><span id="checkAccountResult"></span></td>
				</tr>
				<tr>
					<td>修改密碼 (Change Password)</td>
					<td><input type="password" id="prepassword"/><span id="checkPrepasswordResult"></span></td>
				</tr>
				<tr id="repwd" style="display: none">
					<td>再輸一次 (Enter Password Again)</td>
					<td><input type="password" name="password" id="password"
						onblur="checkpwd('password')" /> <span id="pwdnotice"></span></td>
				</tr>
				<tr>
					<td>用戶姓名 (User Name)</td>
					<td><input type="text" name="name" id="name"
						value="${sessionScope.user_name}" /></td>
				</tr>
				<tr>
					<td>用戶性別 (User Gender)</td>
					<td><select name="gender" id="gender">
							<option value="0">請選擇(Choose)</option>
							<option value="1">Male</option>
							<option value="2">Female</option>
					</select></td>
				</tr>
				<tr>
					<td>手機號碼 (User Mobile)</td>
					<td><input type="text" name="mobile" id="mobile"
						value="${sessionScope.user_mobile}" /><span id="checkMobileResult"></span></td>
				</tr>
				<tr>
					<td>電子郵箱 (User Email)</td>
					<td><input type="text" name="email" id="email"
						value="${sessionScope.user_email}" /><span id="checkEmailResult"></span></td>
				</tr>
				<tr>
					<td>用戶語言 (User Language)</td>
					<td>
					<input type="hidden" id="language" name="language" value="${sessionScope.user_language}"/>
					
					<label><input name="languagepart" type="checkbox" id="English" value="English" />英文(English)</label>&nbsp;&nbsp;
					<label><input name="languagepart" type="checkbox" id="Cantonese" value="Cantonese" />廣東話(Cantonese)</label>&nbsp;&nbsp;
					<label><input name="languagepart" type="checkbox" id="Chinese" value="Chinese" />普通話(Chinese)</label>&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td>用戶技能 (User Skill)
						<img type="image" align="center" src="${pageContext.request.contextPath }/page/img/add_1.png" onclick="addSkill()" />
					</td>
					<td id="skill_main">
					<input type="hidden" id="skill" name="skill" value="${sessionScope.user_skill}"/>
					<div  id="skill_template" style="display: none; padding: 6px 12px;">
						<div>
							<select id="skillParentCode"
								onchange="getChildSkillList(this.options[selectedIndex].value, this.id)">
								<option value="0">--請選擇技能--</option>
							</select>
							<img id="deleteSkill" align="center" src="${pageContext.request.contextPath }/page/img/remove_1.png" onclick="deleteSkillbtn(this.id)"/>
						</div>
						<div id="skillChildList">
						
						</div>
					</div>

					</td>
				</tr>
				<tr>
					<td>默認地址 (User Address)</td>
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
					<input type="hidden" name="addresscode" id="addresscode" value="${sessionScope.user_address_code}"/>
					<input type="hidden" id="addresscodename" value=""/>
					
					<s:if test="%{#session.user_address!=null}">
						<s:set name="lastadsset" value="%{#session.user_address.split(';')[#session.user_address.split(';').length-1]}"/>
					</s:if>
					<input type="text" id="lastads"
						value="${lastadsset}" />
					<input type="hidden" name="address" id="address" value="${sessionScope.user_address}"/>	
					</td>
				</tr>
				<tr>
					<td>用戶簡介 (Description)</td>
					<td> 
						<textarea rows="5" name="description" style="width:400px;" >${sessionScope.user_description}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="修改&#10;(Submit)" onclick="return validate();" class="btn btn-default" />&nbsp;&nbsp;<input
						type="button" value="取消&#10;(Cancel)" onclick="location.href='defult.jsp'" class="btn btn-default" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="footer"></div>
</body>
</html>