function onload() {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_getparentlistJson.action",
		data : {},
		success : function(data) {
			if(data.getparentlistResult){
				var parentreferdoclist = data.parentreferdoclist;
				$.each(parentreferdoclist, function(i, n){
					$("#col_name").append("<option value='"+n.referdoc_id+"'>"+n.referdoc_type+"</option>");
				});
			}else{
				$("#message").html("<font color=red>Page Fail ! "+data.errmsg+"</font>");
				$("#div_message").toggle();
			}
		}
	});
}