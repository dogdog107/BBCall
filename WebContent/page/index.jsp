<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html public "-//w3c//dtd xhtml 1.0 frameset//en" "http://www.w3.org/tr/xhtml1/dtd/xhtml1-frameset.dtd">
<html>
<head>
<meta http-equiv=content-type content="text/html; charset=utf-8" />
<meta http-equiv=pragma content=no-cache />
<meta http-equiv=cache-control content=no-cache />
<meta http-equiv=expires content=-1000 />

<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript">
	var account = "${dataMap.user_account}";
	var name = "${dataMap.user_name}";
	var email = "${dataMap.user_email}";
	var logintime = "${dataMap.user_login_time}";
	var mobile = "${dataMap.user_mobile}";
	var type = "${dataMap.user_type}";
	var gender = "${dataMap.user_gender}";
	var language = "${dataMap.user_language}";
	var skill = "${dataMap.user_skill}";
	var status = "${dataMap.user_status}";
	var token = "${dataMap.user_token}";
	var createtime = "${dataMap.user_create_time}";
</script>
<title>管理中心 v1.0</title>
</head>
<frameset border=0 framespacing=0 rows="60, *" frameborder=0>
	<frame name=head src="${pageContext.request.contextPath }/page/head.jsp" frameborder=0 noresize scrolling=no>
	<frameset cols="170, *">
		<frame name=left src="${pageContext.request.contextPath }/page/left.jsp" frameborder=0 noresize />

		<frame name=main src="${pageContext.request.contextPath }/page/right.jsp" frameborder=0 noresize scrolling=yes />
	</frameset>
</frameset>
<noframes>
</noframes>
</html>