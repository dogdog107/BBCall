function onload() {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/orderlist_getwashorderlistJson.action",
		data : {},
		success : function(data) {
			if(data.getwashorderlistResult){
				var washorderlist = data.orderlist;
				$.each(washorderlist, function(i, n){
					var row = $("#template").clone();
                    row.find("#order_id").text(n.order_id);
                    row.find("#order_create_time").text(n.order_create_time);
                    row.find("#order_book_time").text(n.order_book_time);
                    row.find("#order_book_location").text(n.order_book_location);
                    row.find("#order_type").text(n.order_type);                   
                    row.attr("id","order" + n.order_id);//改变绑定好数据的行的id
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