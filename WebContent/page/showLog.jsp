<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*,java.text.*,java.net.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico"
	type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css"
	type="text/css" rel="stylesheet" />


</head>
<body onload="onload()">
	<table cellspacing="0" cellpadding="0" width="100%" align="center"
		border="0" style="font-size: 12px;">
		<tr height="28">
			<td
				background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a
				href="defult.jsp" target="main">主頁(Home)</a> -> 系統日誌(System Logs)
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
	<div class="div_content">
		<div style="font-size: 12px; margin: 10px 5px;">
			<textarea rows="40" id="systemlog" style="width: 100%">
	<%
		String basePath = request.getServletContext().getRealPath("/");//取得当前目录的路径
		String path = basePath + "/WEB-INF/logs/SystemLog.log";

		File f = new File(path);
		if (f.exists()) {
			FileReader fr = new FileReader(path);//建立FileReader对象，并实例化为fr
			BufferedReader br = new BufferedReader(fr);//建立BufferedReader对象，并实例化为br
			String line = br.readLine();//从文件读取一行字符串
			//判断读取到的字符串是否不为空
			while (line != null) {
				out.println(line);//输出从文件中读取的数据
				line = br.readLine();//从文件中继续读取一行数据
			}
			br.close();//关闭BufferedReader对象
			fr.close();//关闭文件
		} else {
			//文件不存在
			out.println("Error..");
		}
	%>
	</textarea>
			<span id="rowcount"></span><span id="refresh"></span><a id="refreshBtn" href="javascript:void(0);" onclick="stoptime()" style="display:none">stop</a>
		</div>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript">
		var wait = 10;
		function refresh() {
			if (wait == 0) {
				window.location.reload();
			} else {
				timeout = setTimeout(function() {
					$("#refresh").text("(" + wait + "s)Refresh.  ");
					$("#refreshBtn").show();
					wait--;
					refresh();
				}, 1000)
			}
		}
		function onload() {
			var row = $("#systemlog").val().split("\n").length;
			$("#rowcount").text("(" + row + ")Rows.  ");
			$("#systemlog").scrollTop($('#systemlog')[0].scrollHeight);
			refresh();
		}
		function stoptime() {
			clearTimeout(timeout);
			$("#refreshBtn").text("resume");
			$("#refreshBtn").attr("onclick", "resume()");
		}
		function resume() {
			refresh();
			$("#refreshBtn").text("stop");
			$("#refreshBtn").attr("onclick", "stoptime()");
		}
	</script>
</body>
</html>