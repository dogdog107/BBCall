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
				target="main">主頁(Home)</a> -> 技能列表(Skill List)
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
		<span> <s:if test="dataMap.result">
				<p align="center" style="font-size: 15px; color: green">##
					成功！${ dataMap.errmsg} ##</p>
			</s:if> <s:else>
				<s:if test="!dataMap.result">
					<p align="center" style="font-size: 15px; color: red">##
						失敗！${ dataMap.errmsg} ##</p>
				</s:if>
			</s:else>
		</span>
	</div>
	<div class="div_content">
		<div class="div_search">
			<span>
			<input id="btnNew" type="submit" value="增加技能/New User Skill" onclick="location.href='${pageContext.request.contextPath }/page/addSkill.jsp'"/>
			</span>
			<div id="div_message" class="div_message" style="display: none">
				<span id="message"> </span>
			</div>
		</div>
		<div style="font-size: 12px; margin: 10px 5px;">
			<table class="table_list" border="1" width="100%">
				<tbody id="datas">
					<tr style="color:#1c94c4">
						<td width="100px">技能序號<br/>Skill ID</td>
						<td width="100px">用戶序號<br/>User ID</td>
						<td width="150px">用戶帳號<br/>User Account</td>
						<td width="150px">技能名字<br/>Skill Name</td>
						<td width="100px">技能狀態<br/>Skill Status</td>
						<td width="150px">創建時間<br/>Create Time</td>
						<td colspan="2" align="center">操作<br/>Operations</td>
					</tr>
					<tr id="template" style="display: none; font-size: 12px;">
						<td id="skillid"></td>
						<td id="userid"></td>
						<td id="useraccount"></td>
						<td id="skillname"></td>
						<td id="skillstatus"></td>
						<td id="createtime"></td>
						<td id="status_operation">
							<span>技能狀態:</span>
							<select id="statusOpr" onchange="updateStatus(this.id, this.value)">
								<option value="0">待審核</option>
								<option value="1">審核通過</option>
								<option value="2">審核不通過</option>
							</select><br/>
							<input id="btnDetail" type="submit" value="詳情/Detail" onclick=""/>
							<input id="btnDelete" type="submit" value="刪除/Delete" onclick=""/>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="wrap">
				<div id="page_bar" class="fenye">
					<ul>
						<li id="first">首頁</li>
						<li id="top" onclick="topclick()">上一頁</li>
						<li class="xifenye" id="xifenye"><a id="xiye"></a>/<a id="mo"></a>
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
	var BASE_URL = '${pageContext.request.contextPath }';
	var token = "${sessionScope.user_token}";
	var link = "${pageContext.request.contextPath }";
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/skillListPage.js?token=${sessionScope.user_token}"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/paging.js"></script>
</body>
</html>