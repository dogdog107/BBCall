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
				target="main">主頁(Home)</a> -> 編輯權限組(Edit AccessGroup)
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
				<input id="btnBack" type="submit" value="返回權限組列表/Go Back" onclick="location.href='${pageContext.request.contextPath }/page/accessGroupList.jsp'"/>
			</span>
			<div id="div_message" class="div_message" style="display: none"
				width="50%" align="left">
				<span id="message"> </span>
			</div>
		</div>
		<div id="div_message" class="div_message" style="display: none">
			<span id="message"> </span>
		</div>
		<div style="font-size: 12px; margin: 10px 5px;">
		<form id="update_form" action="" method="post" enctype="multipart/form-data">
			<table class="table_list" border="1" width="100%">
				<tbody id="datas">
					<tr  style="font-size: 15px; color: green;">
						<td style="border-right:none;">
							<span>權限組名字：</span><br/>
							<span>(AccessGroup Name):</span> 
						</td>
						<td style="border-width: 1px 0px 1px 0px;">
							${accessgroup_name }
						</td>
						<td style="border-width: 1px 1px 1px 0px;" colspan="3"></td>
					</tr>
					<tr style="color:#1c94c4">
						<td width="300px" style="border:none;">現有權限<br/>Current Access</td>
						<td width="20px" style="border:none;"></td>
						<td width="300px" style="border:none;">系統權限<br/>System Access</td>
						<td width="400px" style="border-width: 0px 0px 1px 1px;">權限說明<br/>Access Description</td>
						<td style="border-width: 1px 1px 1px 0px;"></td>
					</tr>
					<tr style="color: #1c94c4">
						<td style="border:none;"><select multiple size="20" name="list1" id="list1" style="width:300px"
							ondblclick="move(this.form.list1,this.form.list2)" onclick="showDesc(this.form.list1)">
						</select></td>
						<td style="border:none;"><input type="button" value="   >>   "
							onclick="move(this.form.list1,this.form.list2)" name="B1" /><br />
							<input type="button" value="   <<   "
							onclick="move(this.form.list2,this.form.list1)" name="B2" /></td>
						<td style="border:none;"><select multiple size="20" name="list2" id="list2" style="width:300px"
							ondblclick="move(this.form.list2,this.form.list1)" onclick="showDesc(this.form.list2)">
						</select></td>
						<td  valign="top" style="border-width: 1px 0px 0px 1px; text-align:left;">
							<span id="access_desc" style="color:#CC0000;"></span>
						</td>
						<td style="border-width: 0px 1px 0px 0px;">
						</td>
					</tr>
				</tbody>
				<tr>
					<td colspan="20" align="center">

					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
<script type="text/javascript">
var token = "${sessionScope.user_token}";
var link = "${pageContext.request.contextPath }";
var accessGroupId = "${accessgroup_id }";
var accessGroupName = "${accessgroup_name }";
var updateResult = "${updateResult}";
var updateErrmsg = "${dataMap.errmsg}";

</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/editAccessGroupPage.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/multiSelect.js"></script>
</body>
</html>