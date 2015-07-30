<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>增加權限組 Add AccessGroup</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css"
	type="text/css" rel="stylesheet" />

</head>

<body onload="onload()">

	<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0" style="font-size: 12px;">
		<tr height="28">
			<td background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a href="${pageContext.request.contextPath }/page/defult.jsp" target="main">主頁(Home)</a>
				-> 增加權限組(Add AccessGroup)
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
		<span> 
		<input id="btnBack" type="submit" value="返回權限組列表/Go Back" onclick="location.href='${pageContext.request.contextPath }/page/accessGroupList.jsp'"/>
		<span id="span_search">  </span>
		</span>
			<div id="div_message" class="div_message" style="display: none">
				<span id="message"> </span>
			</div>
	</div>
	<div id="div_main" style="font-size: 13px; margin: 10px 5px">
		<form id="addAccessGroup_form" action="access_addAccessGroup" method="post" enctype="multipart/form-data">
				<input type="hidden" id="token" name="token"
					value="${sessionScope.user_token}" />
			<table border="1" width="100%" class="table_update" id="table_main">
				<tr>
					<td width="300px" >權限組名字 (AccessGroup Name)<span id="td_pic" style="color: red; font-weight:bold">*</span></td>
					<td>
						<input type="text" name="accessgroup_name" id="accessgroup_name" /><span id="checkAGNameResult"></span>
					</td>
				</tr>
				<tr>
					<td>權限組描述 (AccessGroup Description)<span style="color: red; font-weight:bold">*</span></td>
					<td>
						<textarea rows="3" id="accessgroup_description" name="accessgroup_description" style="width:400px;"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="提交(Submit)" onclick="return validate();" class="btn btn-default" /><input
						type="button" value="取消(Cancel)" onclick="location.href='defult.jsp'" class="btn btn-default" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="footer"></div>
	<script type="text/javascript">
		// 添加全局站点信息
		var BASE_URL = '${pageContext.request.contextPath }';
		var token = '${sessionScope.user_token}';
		var addResult = '${dataMap.result}';
		var addMsg = '${dataMap.errmsg}';
	</script>

	<!--引入jquery-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
	<!-- 页面JS文件 -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/addAccessGroupPage.js"></script>
</body>
</html>