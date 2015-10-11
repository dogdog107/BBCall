<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv=content-type content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css" type="text/css" rel="stylesheet" />
<!--WebUploader引入CSS-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/page/WebUploader/webuploader.css" />
<%
  String path=request.getContextPath();
%>
</head>
<body onload="onload()">

	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0 style="font-size: 12px;">
		<tr height=28>
			<td background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a href="${pageContext.request.contextPath }/page/defult.jsp" target=main>主頁(Home)</a>
				-> 添加訂單推荐
			</td>
		</tr>
		<tr>
			<td bgcolor=#b1ceef height=1></td>
		</tr>
		<tr height=20>
			<td background="${pageContext.request.contextPath }/page/img/shadow_bg.jpg"></td>
		</tr>
	</table>
	<div></div>
	
		<div id="div_message" class="div_message" style="display: none">
			<span id="message">
			</span>
		</div>
		<div style="font-size: 13px; margin: 10px 5px;">
			<form id="referdoc_add" action="referdoc_add" method="post">
			
				<table class="table_update" border="1" width="100%">
                <tbody id="datas">
                	<tr>
                		<td style="width:400px;">
                			添加
                		</td>
                		<td>
                			<select id="referdoc_level" name="referdoc_level">
								<option value="2">二級項</option>
							</select>
                		</td>
                	</tr>
					<tr>
						<td>參考類型</td>
						<td><input type="text" name="referdoc_type" id="referdoc_type"></input></td>
					</tr>
					<tr id="parentno_tr" style="display:none">
						<td>參考類型歸屬</td>
						<td>
							<select name="referdoc_parentno" id="referdoc_parentno">
								<option value='0'>請選擇</option>
							</select>
						</td>
					</tr>
					<tr id="price_tr" style="display:none">
						<td>參考價格</td>
						<td><input type="text" name="referdoc_price" id="referdoc_price" value="0"></input></td>
					</tr>
					<tr id="flag_tr" style="display:none">
						<td>是否固定價格</td>
						<td>
							<select name="referdoc_flag" id="referdoc_flag">
								<option id="select2" value="false" selected="selected">false</option>
								<option id="select1" value="true">true</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>Apps內 正常時圖片 (Normal Photo)</td>
						<td><input type="hidden" id="referdoc_pic_url"
							name="referdoc_pic_url" value="" /> <!--用来存放item-->
							<div id="normalPhoto" class="uploader-list">
								<img id="normalPhotoImgTemp" src='' height='100' width='100'>
							</div>
							<div id="normalPhotoPicker">
								<span id="normalPhotoPickerValue">選擇圖片</span>
							</div></td>
					</tr>
					<tr>
						<td>Apps內 點擊時圖片 (Pressed Photo)</td>
						<td><input type="hidden" id="referdoc_downpic_url"
							name="referdoc_downpic_url" value="" /> <!--用来存放item-->
							<div id="downPhoto" class="uploader-list">
								<img id="downPhotoImgTemp" src='' height='100' width='100'>
							</div>
							<div id="downPhotoPicker"><span id="downPhotoPickerValue">選擇圖片</span></div></td>
					</tr>
					<tr align="center">
						<td colspan="2"><input type="submit" value="添加"></input> <input
							type="button" value="返回"
							onclick="location='${pageContext.request.contextPath}/referdoc_getlist.action'"></input></td>
					</tr>
				</tbody>
                
            </table>
				
			</form>
		</div>
	<div class="footer"></div>
	<script type="text/javascript">
		// 添加全局站点信息
		var BASE_URL = '${pageContext.request.contextPath }';
		var token = "${sessionScope.user.user_token}";
		var link = "${pageContext.request.contextPath}";
	</script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
	<!--WebUploader引入JS-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/page/WebUploader/webuploader.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/addrefPage.js"></script>
</body>
</html>