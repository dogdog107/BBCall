/**
 * check username whether exist
 */

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