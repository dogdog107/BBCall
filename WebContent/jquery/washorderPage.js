function onload() {

		var washorderlist = data.orderlist;
		$.each(washorderlist, function(i, n) {
			alert(this);
			var row = $("#template").clone();
			row.find("#order_id").text(n.order_id);
			row.find("#order_create_time").text(n.order_create_time);
			row.find("#order_book_time").text(n.order_book_time);
			row.find("#order_book_location").text(n.order_book_location);
			row.find("#order_master_account").text(n.order_master_account);
			switch (n.order_status) {
			case 1:
				row.find("#order_status").text("created");
				break;
			case 2:
				row.find("#order_status").text("in progress");
				break;
			case 3:
				row.find("#order_status").text("finished");
				break;
			}
			row.find("#order_type").text(n.order_type);
			row.find("#order_href").html(
					"<a href=" + link
							+ "/page/orderlist_select.action?order_id="
							+ n.order_id + ">查看</a>");
			row.appendTo("#datas");// 添加到模板的容器中
			row.toggle();
		});
	

}