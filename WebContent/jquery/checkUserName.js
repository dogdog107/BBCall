/**
 * check username whether exist
 */

$(function() {
	$("#account").blur(function(){
		$.post("${pageContext.request.contextPath}/user_checkUserNameJson.action",{"username": $(this).val()}, function(data){
			if(data.checkUserNameResult){
				$("#checkUserNameResult").html("<font color=green>用戶名名可以使用</font>");
			}else{
				$("#checkUserNameResult").html("<font color=red>用戶名已存在</font>");
			}
		});
	});
});