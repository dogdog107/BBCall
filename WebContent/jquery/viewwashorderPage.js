function onload() {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/orderlist_selectJson.action",
		data : {
			"order_id" : order_id
		},
		success : function(data) {
			if(data.selectResult){
				
				alart("success");
			}else{
				alart("fail");
			}
		}
	});
}