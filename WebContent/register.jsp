<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register Page</title>
</head>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function() {
		$("#username").blur(function(){
			//alert($(this).val());
			$.post("${pageContext.request.contextPath}/checkUserName.action",{"username": $(this).val()}, function(data){
				if(data.userResult){
					alert("用户名不存在！");
				}else{
					alert("用户名已存在！");
				}
			});
		});
	});
</script>
<body>
	<h1>Register Page</h1>
	<form>
		Username:<input type="text" name="username" id="username" /><br />
		Password:<input type="password" name="password" id="password" /><br />
		<input type="submit" value="Submit" />
	</form>
</body>
</html>