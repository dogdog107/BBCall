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
	src="${pageContext.request.contextPath }/jquery/userlistPage.js?token=${sessionScope.user.user_token}"></script>
<script type="text/javascript">
	var token = "${sessionScope.user.user_token}";
</script>
</head>
<body onload="onload()">

	<table cellspacing=0 cellpadding=0 width="100%" align=center border=0 style="font-size: 12px;">
		<tr height=28>
			<td background=./img/title_bg1.jpg>当前位置:<a href="right.jsp" target=main>主页</a>
				-> 用户列表
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
                <form action="#" method="get">
                    排序：<select name="s_product_mark" style="width: 100px;">
                        <option selected="selected" value="0">请选择</option>
                        <option value="1">苹果apple</option>
                    </select>
                    <input value="查询" type="submit" />
                </form>
            </span>
        </div>
		<div id="div_message" class="div_message" style="display: none">
			<span id="message">
			</span>
		</div>
        <div style="font-size: 13px; margin: 10px 5px;">
		<table class="table_a" border="1" width="100%">
                <tbody id="datas">
                	<tr style="font-weight: bold;">
                        <td>序号</td>
                        <td>头像</td>
                        <td>帐号</td>
                        <td>姓名</td>
                        <td>类型</td>
                        <td>状态</td>
                        <td>登录时间</td>
                        <td>创建时间</td>
                        <td colspan="2" align="center">操作</td>
                    </tr>
                    <tr id="template" style="display: none">
                        <td id="userid"></td>
                        <td id="picurl"><img src="./img/20121018-174034-58977.jpg" height="60" width="60"></td>
                        <td id="account"></td>
                        <td id="name"></td>
                        <td id="usertype"></td>
                        <td id="status"></td>
                        <td id="logintime"></td>
                        <td id="createtime"></td>
                        <td><a href="#">修改</a></td>
                        <td><a href="javascript:;" onclick="delete_product(1)">删除</a></td>
                    </tr>
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