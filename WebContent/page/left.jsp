<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/page/img/BBCallicon_32X32.ico" type="image/x-icon" />
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
							href="${pageContext.request.contextPath }/page/washorder.jsp"
							target=main>洗衣訂單</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild
							href="${pageContext.request.contextPath }/page/referdoc.jsp"
							target=main>價格推薦</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild
							href="${pageContext.request.contextPath }/page/order.jsp"
							target=main>訂單浏览</a></td>
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
							target=main>全部帳號列表</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild
							href="${pageContext.request.contextPath }/page/guestlist.jsp"
							target=main>訪客列表</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild
							href="${pageContext.request.contextPath }/page/customerlist.jsp"
							target=main>顧客列表</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild
							href="${pageContext.request.contextPath }/page/masterlist.jsp"
							target=main>師傅列表</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild
							href="${pageContext.request.contextPath }/page/adminlist.jsp"
							target=main>管理員列表</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild
							href="${pageContext.request.contextPath }/page/skillList.jsp"
							target=main>技能審批</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild
							href="${pageContext.request.contextPath }/page/register.jsp"
							target=main>添加新帳戶</a></td>
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
						<td><a class=menuchild href="${pageContext.request.contextPath }/page/advertList.jsp" target=main>廣告管理</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild href="${pageContext.request.contextPath }/page/accessGroupList.jsp" target=main>權限組管理</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild href="${pageContext.request.contextPath }/page/sendmessage.jsp" target=main>推送管理</a></td>
					</tr>
					<tr height=20>
						<td align=middle width=30><img height=9
							src="./img/menu_icon.gif" width=9></td>
						<td><a class=menuchild href="${pageContext.request.contextPath }/page/downloadLogList.jsp" target=main>日誌下載</a></td>
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
							href="user_logout.action" target=_top>登出系統</a></td>
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
