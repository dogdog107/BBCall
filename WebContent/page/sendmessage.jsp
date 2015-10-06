<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv=content-type content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
<link href="${pageContext.request.contextPath }/page/css/mine.css" type="text/css" rel="stylesheet" />

<%
  String path=request.getContextPath();
%>
</head>
<body onload="onload()">

	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0 style="font-size: 12px;">
		<tr height=28>
			<td background="${pageContext.request.contextPath }/page/img/title_bg1.jpg">當前位置:<a href="${pageContext.request.contextPath }/page/defult.jsp" target=main>主頁(Home)</a>
				-> 推送管理
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
        <form id="gcm_sendmsg" action="gcm_sendmsg" method="post">
		<table class="table_list" border="1" width="100%">
                <tbody id="data1">
                	<tr style="font-weight: bold;">
                		
                        <td>推送內容： </td>
                    </tr>
					<tr>
						<td align="left">
							<textarea rows="5" cols="100" id="datamsg" name="datamsg"></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<input type="submit" value="發送"/>
						</td>
					</tr>
						
                    
                </tbody>
            </table>
            </form>
            <div style="height:50px"></div>
		<table class="table_list" border="1" width="100%">
			<tbody id="datas">
			<tr style="font-weight: bold;">
				<td colspan="20">
					系統內推送管理<br />
					System Message Push Management
				</td>
			</tr>
				<tr style="color: #1c94c4">
					<td width="120px">簡訊序號<br />Message ID
					</td>
					<td width="120px">簡訊類型<br />Message Type
					</td>
					<td width="600px">簡訊內容<br />Message Contents
					</td>
					<td colspan="2" align="center">操作<br />Operations
					</td>
				</tr>
				<tr id="template" style="display: none; font-size: 12px;">
					<td id="msgid"></td>
					<td id="msgtype"></td>
					<td><textarea rows="3" cols="70" id="msgcontents"></textarea></td>
					<td id="status_operation"><input id="btnRevise" type="submit" value="修改/Revise"
						onclick="" /></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="footer"></div>
	<script type="text/javascript">
		var token = "${sessionScope.user_token}";
		var link = "${pageContext.request.contextPath}";
	</script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/jquery/sendmessagePage.js"></script>
</body>
</html>
