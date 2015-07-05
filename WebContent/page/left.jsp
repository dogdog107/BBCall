<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link href="css/admin.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
	function expand(el) {
		childobj = document.getElementById("child" + el);

		if (childobj.style.display == 'none') {
			childobj.style.display = 'block';
		} else {
			childobj.style.display = 'none';
		}
		return;
	}
</script>
</head>
<body>
	<table height="100%" cellspacing=0 cellpadding=0 width=170
		background="./img/menu_bg.jpg" border=0>
		<tr>
			<td valign=top align=middle>
				<table cellspacing=0 cellpadding=0 width="100%" border=0>

					<tr>
						<td height=10></td>
					</tr>
				</table>
				<table cellspacing=0 cellpadding=0 width=150 border=0>
					<tr height=22>
						<td style="padding-left: 30px" background=./img/menu_bt.jpg><a
							class=menuparent onclick=expand(3) href="javascript:void(0);">訂單中心</a></td>
					</tr>
					<tr height=4>
						<td></td>
					</tr>
				</table>
				<table id=child3 style="display: none" cellspacing=0 cellpadding=0
					width=150 border=0>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild
							href="${pageContext.request.contextPath }/orderlist_getwashorderlist.action"
							target=main>洗衣訂單</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild
							href="${pageContext.request.contextPath }/referdoc_getlist.action"
							target=main>價格推薦</a></td>
					</tr>
					<tr height=4>
						<td colspan=2></td>
					</tr>
				</table>
				<table cellspacing=0 cellpadding=0 width=150 border=0>
					<tr height=22>
						<td style="padding-left: 30px" background=./img/menu_bt.jpg><a
							class=menuparent onclick=expand(5) href="javascript:void(0);">用戶管理</a></td>
					</tr>
					<tr height=4>
						<td></td>
					</tr>
				</table>
				<table id=child5 style="display: none" cellspacing=0 cellpadding=0
					width=150 border=0>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild
							href="${pageContext.request.contextPath }/page/userlist.jsp"
							target=main>用戶列表</a></td>
					</tr>
					<tr height=4>
						<td colspan=2></td>
					</tr>
				</table>
				<table cellspacing=0 cellpadding=0 width=150 border=0>

					<tr height=22>
						<td style="padding-left: 30px" background=./img/menu_bt.jpg><a
							class=menuparent onclick=expand(7) href="javascript:void(0);">系統管理</a></td>
					</tr>
					<tr height=4>
						<td></td>
					</tr>
				</table>
				<table id=child7 style="display: none" cellspacing=0 cellpadding=0
					width=150 border=0>

					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild href="${pageContext.request.contextPath }/page/addAdvert.jsp" target=main>廣告管理</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild href="#" target=main>推送管理</a></td>
					</tr>
					<tr height=4>
						<td colspan=2></td>
					</tr>
				</table>
				<table cellspacing=0 cellpadding=0 width=150 border=0>

					<tr height=22>
						<td style="padding-left: 30px" background=./img/menu_bt.jpg><a
							class=menuparent onclick=expand(0) href="javascript:void(0);">個人管理</a></td>
					</tr>
					<tr height=4>
						<td></td>
					</tr>
				</table>
				<table id=child0 style="display: none" cellspacing=0 cellpadding=0
					width=150 border=0>

					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild
							href="${pageContext.request.contextPath }/page/update.jsp"
							target=main>修改個人信息</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild
							onclick="if (confirm('確定要登出系統嗎？')) return true; else return false;"
							href="login.jsp" target=_top>登出系統</a></td>
					</tr>
				</table>
			</td>
			<td width=1 bgcolor=#d1e6f7></td>
		</tr>
		<tr height=20>
			<td background=./img/shadow_bg.jpg></td>
			<td width=1 background=./img/shadow_bg.jpg></td>
		</tr>
	</table>
</body>
</html>
