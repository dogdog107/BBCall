<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv=content-type content="text/html; charset=utf-8" />
<link href="./css/mine.css" type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/addrefPage.js"></script>
<script type="text/javascript">
	var token = "${sessionScope.user.user_token}";
	var link = "${pageContext.request.contextPath}";
</script>
<%
  String path=request.getContextPath();
%>
</head>
<body>

	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0 style="font-size: 12px;">
		<tr height=28>
			<td background=./img/title_bg1.jpg>當前位置:<a href="${pageContext.request.contextPath }/page/defult.jsp" target=main>主頁(Home)</a>
				-> 添加訂單推荐
			</td>
		</tr>
		<tr>
			<td bgcolor=#b1ceef height=1></td>
		</tr>
		<tr height=20>
			<td background=./img/shadow_bg.jpg></td>
		</tr>
	</table>
	<div></div>
	
		<div id="div_message" class="div_message" style="display: none">
			<span id="message">
			</span>
		</div>
		<div style="font-size: 13px; margin: 10px 5px;">
			<form id="referdoc_add" action="referdoc_add" method="post">
			
				<table class="table_list" border="1" width="100%">
                <tbody id="datas">
                	<tr>
                		<td>
                			添加 
                			<select id="referdoc_level" name="referdoc_level" onchange="referdoc_level_change()">
								<option value="1">一級項</option>
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
							</select>
						</td>
					</tr>
					<tr id="price_tr" style="display:none">
						<td>參考價格</td>
						<td><input type="text" name="referdoc_price" id="referdoc_price" value="0"></input></td>
					</tr>
                    <tr align="center">
                    	<td><input type="submit" value="添加"></input></td>
					    <td><input type="button" value="返回" Onclick="location='${pageContext.request.contextPath}/referdoc_getlist.action'"></input></td>
					</tr>
                </tbody>
                
            </table>
				
			</form>
		</div>
</body>
</html>