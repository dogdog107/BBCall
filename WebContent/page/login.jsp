<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户登录</title>

<link href="${pageContext.request.contextPath }/page/css/login.css"
	type="text/css" rel="stylesheet" />

<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
</head>
<body id="userlogin_body">

	<div id="user_login">
		<dl>
			<dd id="user_top">
				<ul>
					<li class="user_top_l"></li>
					<li class="user_top_c"></li>
					<li class="user_top_r"></li>
				</ul>
			</dd>
			<dd id="user_main">
				<form action="user_login" method="post">
					<ul>
						<li class="user_main_l"></li>
						<li class="user_main_c">

							<div class="user_main_box">
								<ul>
									<li class="user_main_text">用户名：</li>
									<li class="user_main_input"><input name="username"
										maxlength="18" id="username" class="txtusernamecssclass" /></li>
								</ul>
								<ul>
									<li class="user_main_text">密 码：</li>
									<li class="user_main_input"><input type="password"
										name="password" id="password" class="txtpasswordcssclass" /></li>
								</ul>
								<ul style="text-align: center">
									<s:if test="#{ dataMap.loginResult}">
										<font color="red">登陆失败！<br />${ dataMap.errmsg}</font>
										<s:else>
											<font color="green">登陆成功！</font>
										</s:else>
									</s:if>
								</ul>
							</div>

						</li>
						<li class="user_main_r"><input id="IbtnEnter" type="image"
							src="./img/user_botton.gif" class="ibtnentercssclass" /></li>
					</ul>
				</form>
			</dd>
			<dd id="user_bottom">
				<ul>
					<li class="user_bottom_l"></li>
					<li class="user_bottom_c"></li>
					<li class="user_bottom_r"></li>
				</ul>
			</dd>
		</dl>
	</div>


</body>
</html>