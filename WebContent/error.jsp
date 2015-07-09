<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<title>程式異常</title>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

.errTitle {
	min-width: 300px;
	width: 90%;
	height: 50px;
	line-height: 50px;
	border: 1px solid #999999;
	border-radius: 5px;
	background: #0169B2;
	text-align: center;
	word-spacing: 10px;
 	FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif";
	font-size: 24px;
	margin: 10px 70px;
	padding: 4px 4px 4px 6px;
	position: relative;
	color: red;
}
.errMsg {
	min-width: 300px;
	width: 90%;
	height: 50px;
	line-height: 50px;
	border: 1px solid #999999;
	border-radius: 5px;
	text-align: center;
	word-spacing: 10px;
 	FONT-FAMILY: "Verdana", "Arial", "Helvetica", "sans-serif";
	font-size: 20px;
	margin: 10px 70px;
	padding: 4px 4px 4px 6px;
	position: relative;
	color: red;
}

.showErrWrap {
	font-size: 10px;
	position: absolute;
	right: 10px;
	bottom: -5px;
}

.showErrWrap a:link, .showErrWrap a:visited {
	text-decoration: none;
	color: white;
}

.showErrWrap a:hover {
	text-decoration: underline;
	color: yellow;
}

.showErrWrap span {
	margin: 0 4px;
	color: black;
}

.errStack {
	min-width: 300px;
	width: 90%;
	font-family: "Courier New", Courier, monospace;
	font-size: 15px;
	border: 0 none;
	margin: 10px 70px;
	overflow: auto;
	padding: 4px;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#showErrBtn').toggle(function() {
			$('#showErrBtn').text('關閉詳情');
			$('.errStack').slideDown('normal');
		}, function() {
			$('.errStack').slideUp('normal');
			$('#showErrBtn').text('查看詳情');
		});
	});
	if (window.parent != window) {
		window.parent.location.href = window.location.href;
	}
</script>

</head>
<body>
	<div class="errTitle">
		程 式 出 現 異 常
		<span class="showErrWrap"><a
			href="javascript:void(0);" id="showErrBtn">查看詳情</a><span>|</span><a
			href="javascript:void(0);" onclick="javascript:history.go(-1);">返回上一頁</a></span>
	</div>
	<div class="errMsg">
		<s:property value="exception.message" />
		${accessMsg }
	</div>
	<div class="errStack" style="display: none;">
		<pre>  
<s:property value="exceptionStack" />
			<!-- 异常信息 -->  
</pre>
	</div>
</body>
</html>
