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
			$.post("${pageContext.request.contextPath}/user_checkUserNameJson.action",{"username": $(this).val()}, function(data){
				if(data.checkUserNameResult){
					$("#checkUserNameResult").html("<font color=green>用户名可以使用</font>");
				}else{
					$("#checkUserNameResult").html("<font color=red>用户名已存在</font>");
				}
			});
		});
	});
</script>
<body>
	<h1>Register Page</h1>
	<form>
		Username:<input type="text" name="username" id="username" /><span id="checkUserNameResult"></span><br />
		Password:<input type="password" name="password" id="password" /><br />
		<input type="submit" value="Submit" />
	</form>
</body>
</html>