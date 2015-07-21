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
				target="main">主頁(Home)</a> -> 廣告詳情頁面(Advertisement Details)
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
			<span>
				<input id="btnBack" type="submit" value="返回廣告列表/Go Back" onclick="location.href='${pageContext.request.contextPath }/page/advertList.jsp'"/>
				<input id="btnBack" type="submit" value="編輯廣告/Edit Advert" onclick="location.href='advert_editAdvertPage.action?token=${sessionScope.user_token}&advertisement_id=${advertisement_id}'"/>
			</span>
			<div id="div_message" class="div_message" style="display: none"
				width="50%" align="left">
				<span id="message"> </span>
			</div>
		</div>
		<div id="div_message" class="div_message" style="display: none">
			<span id="message"> </span>
		</div>
		<div style="font-size: 133.33%; margin: 10px 5px;">
			<div class="advert_head">
			<div style="margin: 30px;">
				<h1 align="center">${advertisement_title }</h1>
			</div>
				<div style="font-size: 13px; color: #666; margin: 20px;" align="center">
					<span id="advertType"></span> <span> | </span> <span>${advertisement_create_time }</span>
				</div>
			</div>
			<div width="100%" align="center">
				<img src="${advertisement_bigphoto_url }" height="200" width="400" />
			</div>
			<div style="margin: 20px;">
				<h3 align="center">${advertisement_summary }</h3>
			</div>
			<div class="advert_content">${advertisement_content }</div>
		</div>
	</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript">
var token = "${sessionScope.user_token}";
var link = "${pageContext.request.contextPath }";
var advert_type = "${advertisement_type }";
var updateResult = "${updateResult}";
var updateErrmsg = "${dataMap.errmsg}";

function onload(){
	checkAdvertType(advert_type);
	if (updateResult != '') {
		if(updateResult == 'true'){
			$("#message").html("<font color=green> Edit Advertisement Success! </font>");
			$("#div_message").show(300).delay(5000).hide(300);
		}
		if (updateResult == 'false') {
			$("#message").html("<font color=red> Edit Advertisement Failed ! " + updateErrmsg + " </font>");
			$("#div_message").show(300).delay(5000).hide(300);
		}
	}
}

function checkAdvertType(typecode){
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_selectJson.action",
		data : {
			"token" : token,
			"referdoc_id" : typecode
		},
		success : function(data) {
			if (data.result) {
				$("#advertType").text(data.referdoc.referdoc_type);
			}
		}
	});
}
</script>
</body>
</html>