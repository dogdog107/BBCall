<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css" type="text/css" rel="stylesheet" />


</head>
<body onload="onload()">

	<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0"
		style="font-size: 12px;">
		<tr height="28">
			<td background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a href="defult.jsp"
				target="main">主頁(Home)</a> -> 下載列表(Download List)
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
	<div class="div_content">
		<div class="div_search">
			<span> <span>日誌類型(Logs Type):</span> 
			<select name="logType"
				id="logType" onchange="logType_change(this.value)">
					<option value="User">用戶操作日誌(User Operation)</option>
					<option value="Trade">交易日誌(Trade Operation)</option>
					<option value="Grade">評分日誌(Grade Operation)</option>
					<option value="System">系統日誌(Grade Operation)</option>
			</select>
				<div id="div_message" class="div_message" style="display: none">
					<span id="message"> </span>
				</div>
			</span>
		</div>
		<div style="font-size: 12px; margin: 10px 5px;">
			<table class="table_list" border="1" width="100%">
				<tbody id="datas">
					<tr style="color:#1c94c4">
						<td width="400px">日誌類型<br/>Logs Name</td>
						<td width="400px">日誌日期<br/>Logs Time</td>
						<td  colspan="2" align="center">操作<br/>Operations</td>
					</tr>
					<tr id="template" style="display: none; font-size: 12px;">
						<td id="log_name"></td>
						<td id="log_time"></td>
						<td id="log_operation">
							<input id="btnDownload" type="submit" value="下載/Download" onclick=""/>
						</td>
					</tr>
					<tr id="currentLog" style="font-size: 12px;">
						<td id="log_name"></td>
						<td id="log_time"></td>
						<td id="log_operation">
							<input id="btnDownload" type="submit" value="下載/Download" onclick=""/>
						</td>
					</tr>
				</tbody>
				<tr>
					<td colspan="20" align="center">

					</td>
				</tr>
			</table>
			<div class="wrap">
				<div id="page_bar" class="fenye">
					<ul>
						<li id="first">首頁</li>
						<li id="top" onclick="topclick()">上一頁</li>
						<li class="xifenye" id="xifenye"><a id="xiye"></a>/<a
							id="mo"></a>
							<div class="xab" id="xab" style="display: none">
								<ul id="uljia">

								</ul>
							</div></li>
						<li id="down" onclick="downclick()">下一頁</li>
						<li id="last">尾頁</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="footer"></div>
	<script type="text/javascript">
		var token = "${sessionScope.user_token}";
		var link = "${pageContext.request.contextPath }";
		var addResult = '${dataMap.addresult}';
		var addMsg = '${dataMap.errmsg}';
	</script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/downloadLogListPage.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/paging.js"></script>
</body>
</html>