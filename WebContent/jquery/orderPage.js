function addonload() {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/orderlist_gettypelistJson.action",
		data : {},
		success : function(data) {
			if (data.gettypelistResult) {
				var ordertypelist = data.ordertypelist;
				$.each(ordertypelist, function(i, n) {
					var row = $("#template").clone();
					row.find("#order_type_list").val(n);
					row.find("#order_type_text").text(n);
					row.appendTo("#datas");// 添加到模板的容器中
					row.toggle();
				});
			} else {
				$("#message").html(
						"<font color=red>Page Fail ! " + data.errmsg
								+ "</font>");
				$("#div_message").show(300).delay(6000).hide(300);
			}
		}
		
	});
	
}


