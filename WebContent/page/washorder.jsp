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
	src="${pageContext.request.contextPath }/jquery/washorderPage.js?token=${sessionScope.user.user_token}"></script>
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
			<td background=./img/title_bg1.jpg>当前位置:<a href="right.jsp" target=main>主页</a>
				-> 洗衣订单列表
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
                        <option value="1">订单状态</option>
                        <option value="2">负责师傅</option>
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
                        <td>訂單生成時間</td>
                        <td>预约时间</td>
                        <td>预约地点</td>
                        <td>类型</td>
                        <td colspan="2" align="center">操作</td>
                    </tr>
                    <tr id="template" style="display: none">
                        <td id="order_id"></td>
                        <td id="order_create_time"></td>
                        <td id="order_book_time"></td>
                        <td id="order_book_location"></td>
                        <td id="order_type"></td>
                        <td id="order_href"></td>
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