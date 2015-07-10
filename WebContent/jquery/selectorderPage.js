function onload() {
	document.getElementById('order_id').value = order_id;
	document.getElementById('order_book_time').value = order_book_time;
	document.getElementById('order_book_location').value = order_book_location;
	document.getElementById('order_create_time').value = order_create_time;
	document.getElementById('order_contact_name').value = order_contact_name;
	document.getElementById('order_contact_mobile').value = order_contact_mobile;
	document.getElementById('order_remark').value = order_remark;
	
	switch (order_status) {
		case "1":
			document.getElementById('order_status').value = "新建訂單";
			break;
		case "2":
			document.getElementById('order_status').value = "待評價訂單";
			break;
		case "3":
			document.getElementById('order_status').value = "已評價訂單";
			break;
		case "4":
			document.getElementById('order_status').value = "收到貨物";
			break;
		case "5":
			document.getElementById('order_status').value = "正在清洗";
			break;
		case "6":
			document.getElementById('order_status').value = "正在配送";
			break;
	}
}