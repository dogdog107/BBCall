/**
 * check rederdoc type whether exist
 */

$(function() {
	$("#referdoc_type").blur(function(){
		$.post("${pageContext.request.contextPath}/referdoc_chkreferdoctypeJson.action",{"referdoc_type": $(this).val()}, function(data){
			if(data.chkreferdoctypeResult){
				$("#chkReferdocType").html("<font color=green>參考類型可以使用</font>");
			}else{
				$("#chkReferdocType").html("<font color=red>參考類型已經存在</font>");
			}
		});
	});
});