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
	src="${pageContext.request.contextPath }/jquery/referdocPage.js?token=${sessionScope.user.user_token}"></script>
<script type="text/javascript">
	var token = "${sessionScope.user.user_token}";
	var link = "${pageContext.request.contextPath}";
</script>
<%
  String path=request.getContextPath();
%>
</head>
<body onload="onload()">

	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0 style="font-size: 12px;">
		<tr height=28>
			<td background=./img/title_bg1.jpg>當前位置:<a href="${pageContext.request.contextPath }/page/defult.jsp" target=main>主頁(Home)</a>
				-> 訂單價格推薦
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
		<div class="div_search">
			<span>
				一級項 ：
				<select name="col_name" id="col_name">
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="添加參考價" Onclick="location='${pageContext.request.contextPath}/page/addreferdoc.jsp'" />
			</span>
			<br/>
			
		</div>
        
		<div id="div_message" class="div_message" style="display: none">
			<span id="message">
			</span>
		</div>
        <div style="font-size: 13px; margin: 10px 5px;">
		<table class="table_list" border="1" width="100%">
                <tbody id="datas">
                	<tr style="font-weight: bold;">
                        <td>序號</td>
                        <td>訂單類型</td>
                        <td>訂單參考價格</td>
                        <td colspan="2" align="center">操作</td>
                    </tr>
                    <s:iterator value="dataMap.referdoclist" id="referdoclist">
					<tr id="template">
						<td id="referdoc_id"><s:property value='#referdoclist.referdoc_id' /></td>
						<td id="referdoc_type"><s:property
								value='#referdoclist.referdoc_type' /></td>
						<td id="referdoc_price"><s:property
								value='#referdoclist.referdoc_price' /></td>
						<td id="referdoc_href"><a
							href="${pageContext.request.contextPath}/page/referdoc_delete.action?referdoc_id=<s:property value='#referdoclist.referdoc_id'/>">刪除</a>
						</td>
					</tr>
					</s:iterator>
                    
                </tbody>
                    <tr>
                        <td colspan="20" style="text-align: center;">
                            [1]2
                        </td>
                    </tr>
            </table>
        </div>
</body>
</html>