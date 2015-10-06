<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html public "-//w3c//dtd xhtml 1.0 frameset//en" "http://www.w3.org/tr/xhtml1/dtd/xhtml1-frameset.dtd">
<html>
<head>
<meta http-equiv=content-type content="text/html; charset=utf-8" />
<meta http-equiv=pragma content=no-cache />
<meta http-equiv=cache-control content=no-cache />
<meta http-equiv=expires content=-1000 />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<title>管理中心</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript">
	var token = "${sessionScope.user_token}";
	$(function() {
		$.post(
			"${pageContext.request.contextPath}/system_getServerVersionJson.action",
			function(data) {
				if (data.result) {
					document.title = "管理中心 V" + data.version;
				}
			});
	});
</script>
</head>

<frameset border=0 framespacing=0 rows="60, *" frameborder=0>
	<frame name=head src="${pageContext.request.contextPath }/page/head.jsp" frameborder=0 noresize scrolling=no>
	<frameset cols="170, *">
		<frame name=left src="${pageContext.request.contextPath }/page/left.jsp" frameborder=0 noresize />

		<frame name=main src="${pageContext.request.contextPath }/page/defult.jsp" frameborder=0 noresize scrolling=yes />
	</frameset>
</frameset>
<noframes>
</noframes>
</html>