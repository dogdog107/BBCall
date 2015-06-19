function onload() {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_getlistJson.action",
		data : {},
		success : function(data) {
			if(data.getlistResult){
				var referdoclist = data.referdoclist;
				$.each(referdoclist, function(i, n){
					var row = $("#template").clone();
                    row.find("#referdoc_id").text(n.referdoc_id);
                    row.find("#referdoc_type").text(n.referdoc_type);
                    row.find("#referdoc_price").text(n.referdoc_price);                 
                    row.find("#referdoc_href").html("<a href=" + link + "/page/referdoc_delete.action?referdoc_id=" + n.referdoc_id +">删除</a>");
                    row.appendTo("#datas");//添加到模板的容器中
                    row.toggle();
				});
			}else{
				$("#message").html("<font color=red>Page Fail ! "+data.errmsg+"</font>");
				$("#div_message").toggle();
			}
		}
	});
}