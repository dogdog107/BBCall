<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv=content-type content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css" type="text/css" rel="stylesheet" />
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
			<td background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a href="${pageContext.request.contextPath }/page/defult.jsp" target=main>主頁(Home)</a>
				-> 訂單價格推薦
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
		<div class="div_merge">
			<span>
				一級項 ：
				<select name="referdoc_parentno" id="referdoc_parentno">
					<option value="0">請選擇</option>
				</select>
				<input value="查詢" type="submit" onclick="referdoc_parentno_change()"/>
			</span>
			
		</div>
		<div class="div_merge">
			<span>
				<input type="button" value="添加參考價" Onclick="location='${pageContext.request.contextPath}/page/addreferdoc.jsp'" />
			</span>
			
		</div>
        
		<div id="div_message" class="div_message" style="display: none">
			<span id="message">
			</span>
		</div>
        <div style="font-size: 13px; margin: 10px 5px;">
		<table class="table_list" border="1" width="100%">
                <tbody id="datas">
                	<tr style="font-weight: bold;">
                		
                        <td>级别</td>
                        <td>訂單類型</td>
                        <td>訂單參考價格</td>
                        <td>固定价格</td>
                        <td colspan="4" align="center">操作</td>
                    </tr>
                    
					<tr id="template" style="display:none">
						
						<td id="referlevel"></td>
						<td style="display:none"><input type="text" id="referid"></input></td>
						<td><input type="text" id="refertype" readonly="true"></input></td>
						<td><input type="text" id="referprice" ></input></td>
						<td>
							<select id="referflag">
								<option id="select1" value="true" selected="selected">true</option>
								<option id="select2" value="false">false</option>
							</select>	
						</td>
						<td style="display:none"><input type="text" id="referparentno" ></input></td>
						<td>
						<input id="referupdate" type="submit" value="修改" onclick=""/>
						<input id="referdelete" type="submit" value="刪除" onclick=""/>
						</td>
					</tr>
                    
                </tbody>
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
        <script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/referdocPage.js?token=${sessionScope.user.user_token}"></script>
<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/paging.js"></script>
</body>
</html>
