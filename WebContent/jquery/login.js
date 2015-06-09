/**
 * check username whether exist
 */

$(function() {
	$("#IbtnEnter").click(function(){
		$.post("${pageContext.request.contextPath}/user_loginJson.action",{"username": $("#username").val(), "password": $("#password").val()}, function(data){
			if(data.loginResult){
				$("#loginResult").html("<font color=green>登陆成功！</font>");
				location.href="./index.html";
			}else{
				$("#loginResult").html("<font color=red>Login Fail ! "+data.errmsg+"</font>");
			}
		});
	});
});