<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css" type="text/css" rel="stylesheet" />

<!--WebUploader引入CSS-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/page/WebUploader/webuploader.css" />

<title>新增技能 (Add Skill)</title>
</head>
<body onload="onload()">
	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0
		style="font-size: 12px;">
		<tr height=28>
			<td
				background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a
				href="${pageContext.request.contextPath }/page/defult.jsp"
				target=main>主頁(Home)</a> -> 新增技能 (Add Skill)
			</td>
		</tr>
		<tr>
			<td bgcolor=#b1ceef height=1></td>
		</tr>
		<tr height=20>
			<td
				background="${pageContext.request.contextPath }/page/img/shadow_bg.jpg"></td>
		</tr>
	</table>
	<div></div>
	<div style="font-size: 13px; margin: 10px 5px">
		<span> <s:if test="dataMap.result != null">
				<s:if test="dataMap.result">
					<p align="center" style="font-size: 15px; color: green">##
						成功！${ dataMap.errmsg} ##</p>
				</s:if>
				<s:else>
					<s:if test="!dataMap.result">
						<p align="center" style="font-size: 15px; color: red">## 失敗！${ dataMap.errmsg}
							##</p>
					</s:if>
				</s:else>
			</s:if>
		</span>
	</div>
	<div style="font-size: 13px; margin: 10px 5px">
		<form id="addSkill_form" action="user_addUserSkill" method="post">
			<input type="hidden" id="token" name="token"
				value="${sessionScope.user_token}" />
			<table border="1" width="100%" class="table_update">

				<tr>
					<td style="width:250px">用戶序號 (User ID)</td>
					<td><input type="text" id="userid" name="userid" value="${sessionScope.user_id}" style="width:400px;height:20px;"/></td>
				</tr>
				<tr>
					<td>技能項 (Skill)</td>
					<td id="skill_main"><input type="hidden" id="skillcode"
						name="skillcode" value="" />
						<div style="padding: 6px 12px;">
							<div>
								<select id="skillParentCode"
									onchange="getChildSkillList(this.options[selectedIndex].value)">
									<option value="0">--請選擇廣告類型--</option>
								</select>
							</div>
							<div id="skillChildList"></div>
						</div></td>
				</tr>
				<tr>
					<td>技能證書 (Cert Photo)</td>
					<td> 
						<input type="hidden" id="skillurl" name="skillurl" value="" />
						<!--用来存放item-->
						<div id="bigPhoto" class="uploader-list"><img id="bigPhotoImgTemp" src='' height='200' width='400'></div>
						<div id="bigPhotoPicker">选择图片</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="添加(Submit)" onclick="return validateAdd();" class="btn btn-default" />
						<input type="submit" value="更新(Update)" onclick="return validateUpdate();" class="btn btn-default" />
						<input type="button" value="取消(Cancel)" onclick="location.href='defult.jsp'" class="btn btn-default" />
					</td>
				</tr>
			</table>
		</form>
	</div>
<!-- 	<div style="width:100%;height:100px;"></div> -->
	<div class="footer"></div>
	<script type="text/javascript">
		// 添加全局站点信息
		var BASE_URL = '${pageContext.request.contextPath }';
		var token = '${sessionScope.user_token}';
		var userid = '${sessionScope.user_id}';
	</script>
	
	<!--引入jquery-->
	<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
	
	<!--WebUploader引入JS-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/page/WebUploader/webuploader.js"></script>

	<!-- 页面JS文件 -->
	<script type="text/javascript" charset="utf-8"
		src="${pageContext.request.contextPath }/jquery/addSkillPage.js"></script>
</body>
</html>