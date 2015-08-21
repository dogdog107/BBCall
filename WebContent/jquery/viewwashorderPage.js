function onload() {
	document.getElementById('order_status').options[value = order_status-1].selected = true;
	document.getElementById('orderid').innerHTML = order_id;
	document.getElementById('order_book_time').innerHTML = order_book_time;
	document.getElementById('order_book_location').innerHTML = order_book_location;
	document.getElementById('order_create_time').innerHTML = order_create_time;
	document.getElementById('order_contact_name').innerHTML = order_contact_name;
	document.getElementById('order_contact_mobile').innerHTML = order_contact_mobile;
}