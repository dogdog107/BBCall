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
<title>新增廣告 (Add Advertisement)</title>
</head>
<body onload="onload()">
	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0
		style="font-size: 12px;">
		<tr height=28>
			<td
				background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a
				href="${pageContext.request.contextPath }/page/defult.jsp"
				target=main>主頁(Home)</a> -> 修改廣告 (Edit Advertisement)
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
<%-- 		<span> <s:if test="dataMap.result">
				<p align="center" style="font-size: 15px; color: green">##
					成功！${ dataMap.errmsg} ##</p>
			</s:if> <s:else>
				<s:if test="!dataMap.result">
					<p align="center" style="font-size: 15px; color: red">##
						失敗！${ dataMap.errmsg} ##</p>
				</s:if>
			</s:else>
		</span> --%>
	</div>
	<div style="font-size: 13px; margin: 10px 5px">
		<form id="updateAdvert_form" action="advert_updateAdvertById" method="post">
			<input type="hidden" id="token" name="token" value="${sessionScope.user_token}" />
			<input type="hidden" id="advertisement_id" name="advertisement_id" value="${advertisement_id}" />
			<table border="1" width="100%" class="table_update">

				<tr>
					<td style="width:250px">廣告標題 (AD Title)</td>
					<td><input type="text" id="advertisement_title" name="advertisement_title" style="width:400px;height:20px;" value="${advertisement_title }"/></td>
				</tr>
				<tr>
					<td>廣告概要 (AD Summary)</td>
					<td><textarea rows="3" id="advertisement_summary" name="advertisement_summary" style="width:400px;">${advertisement_summary }</textarea></td>
				</tr>
				<tr>
					<td>用戶技能 (User Skill)
					</td>
					<td id="skill_main">
					<input type="hidden" id="advertisement_type" name="advertisement_type" value="${advertisement_type }"/>
					<div style="padding: 6px 12px;">
						<div>
							<select id="skillParentCode"
								onchange="getChildSkillList(this.options[selectedIndex].value)">
								<option value="0">--請選擇廣告類型--</option>
							</select>
						</div>
						<div id="skillChildList">
						
						</div>
					</div>

					</td>
				</tr>
				<tr>
					<td>廣告大標圖 (AD Big Photo)</td>
					<td>
						<input type="hidden" id="advertisement_bigphoto_url" name="advertisement_bigphoto_url" value="${advertisement_bigphoto_url }" />
						<%-- <img id="big_photo" name="advertisement_bigphoto_url" src="${advertisement_bigphoto_url }" height="200" width="400" /><br/> --%>
						<div id="bigPhoto" class="uploader-list"><img id="bigPhotoImgTemp" src='${advertisement_bigphoto_url }' height='200' width='400'></div>
						<div id="bigPhotoPicker"><span id="bigPhotoPickerValue">修改圖片</span></div>
					</td>
				</tr>
				<tr>
					<td>廣告小標圖 (AD Small Photo)</td>
					<td>
						<input type="hidden" id="advertisement_smallphoto_url" name="advertisement_smallphoto_url" value="${advertisement_smallphoto_url }" />
						<%-- <img id="small_photo" name="advertisement_smallphoto_url" src="${advertisement_smallphoto_url }" height="100" width="100" /><br/> --%>
						<div id="smallPhoto" class="uploader-list"><img id="smallPhotoImgTemp" src='${advertisement_smallphoto_url }' height='100' width='100'></div>
						<div id="smallPhotoPicker"><span id="smallPhotoPickerValue">修改圖片</span></div>
					</td>
				</tr>
				<tr>
					<td colspan="2">廣告內容 (AD Content)</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="hidden" id="advertisement_content" name="advertisement_content" value="" />
						<script id="editor" type="text/plain" style="width:99%;height:400px;"></script>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="提交(Submit)" onclick="return validate();" class="btn btn-default" />
						<input type="button" value="重置(Reset)" onclick="location.href='advert_editAdvertPage.action?token=${sessionScope.user_token}&advertisement_id=${advertisement_id}'" class="btn btn-default" />
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
		var link = "${pageContext.request.contextPath}";
		var advertType = '${advertisement_type }';
		var updateResult = "${updateResult}";
		var updateErrmsg = "${dataMap.errmsg}";
		var content = "${advertisement_content}";
	</script>
	
	<!--引入jquery-->
	<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
	
	<!-- UEditor配置文件 -->
	<script type="text/javascript" charset="utf-8"
		src="${pageContext.request.contextPath }/page/UEditor/ueditor.config.js"></script>

	<!-- UEditor编辑器源码文件 -->
	<script type="text/javascript" charset="utf-8"
		src="${pageContext.request.contextPath }/page/UEditor/ueditor.all.min.js"></script>

	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<!-- <script type="text/javascript" charset="utf-8" src="UEditor/lang/zh-cn/zh-cn.js"></script> -->
	
	<!--WebUploader引入JS-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/page/WebUploader/webuploader.js"></script>
		
	<!-- 页面JS文件 -->
	<script type="text/javascript" charset="utf-8"
		src="${pageContext.request.contextPath }/jquery/editAdvertPage.js"></script>
</body>
</html>