function onload() {
	if (token == "" || token == null) {
		if (confirm('Session has been expired! Please re-login again.\n Click "OK" to return login page.')) {
			window.parent.frames.location.href="./login.jsp";
		}
		$("#message").html("<font color=red> Session has been expired! Please re-login again. </font>");
		$("#div_main").hide(300);
		$("#div_message").show(300).delay(10000).hide(300);
		return;
	}
	if (addResult == 'true'){
		$("#message").html("<font color=green> Add AccessGroup Success ! </font>");
		$("#span_message").html("<p align='center' style='font-size: 16px; color: green'>##添加成功！" + addMsg + " ##</p>");
		$("#span_search").hide(300);
		$("#div_main").hide(300);
		$("#div_message").show(300).delay(5000).hide(300);
	}
	
	if (addResult == 'false'){
		$("#message").html("<font color=red> Add AccessGroup Failed ! "  + addMsg + "</font>");
		$("#span_message").html("<p align='center' style='font-size: 16px; color: red'>##添加失敗！" + addMsg + " ##</p>");
		$("#div_message").show(300).delay(10000).hide(300);
	}
}

$(function() {
	$("#accessgroup_name").blur(function(){
		if (this.value != '') {
			$.post("${pageContext.request.contextPath}/access_checkAccessGroupByNameJson.action",{"accessgroup_name": $(this).val()}, function(data){
				if(data.result){
					$("#checkAGNameResult").hide();
					$("#checkAGNameResult").html("<font color=green>權限組名字可以使用</font>").show(300);
				}else{
					$("#checkAGNameResult").hide();
					$("#checkAGNameResult").html("<font color=red>Error: 權限組名字已存在</font>").show(300);
				}
			});
		}
	});
});

function validate() {
	var checkAGNResult = $("#checkAGNameResult").html();
	var AGName = $("#accessgroup_name").val();
	var AGDesc = $("#accessgroup_description").val();
	if (AGName == null || AGName == ''){
		alert("AccessGroup Name Error! \nPlease enter AccessGroup Name before submit.");
		return false;
	}
	
	if (AGDesc == null || AGDesc == ''){
		alert("AccessGroup Description Error! \nPlease enter AccessGroup Description before submit.");
		return false;
	}
	
	if (checkAGNResult.indexOf("Error") > 0){
		alert("AccessGroup Name Error! \nPlease change the AccessGroup Name before submit.");
		return false;
	}
	return true;
}