function onload() {
	document.getElementById('order_status').options[value = order_status-1].selected = true;
	document.getElementById('order_id').value = order_id;
	document.getElementById('order_book_time').value = order_book_time;
	document.getElementById('order_book_location').value = order_book_location;
	document.getElementById('order_create_time').value = order_create_time;
	document.getElementById('order_contact_name').value = order_contact_name;
	document.getElementById('order_contact_mobile').value = order_contact_mobile;
	document.getElementById('order_remark').value = order_remark;
}