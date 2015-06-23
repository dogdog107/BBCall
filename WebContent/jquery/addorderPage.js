$(document).ready(function(){
	$("#order_type_list").click(function(){
		alert("sss");
		$.post("${pageContext.request.contextPath}/referdoc_referpriceJson.action",{"order_type_list": $(this).val()}, function(data){
			if(data.referpriceResult){
				$("#referprice").html("<font color=green>參考類型可以使用</font>");
			}else{
				$("#referprice").html("<font color=red>參考類型已經存在</font>");
			}
		});
	});

});


