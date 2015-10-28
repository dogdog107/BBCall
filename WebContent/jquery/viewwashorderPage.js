function onload() {
	document.getElementById('order_status').options[value = order_status-1].selected = true;
//	document.getElementById('orderid').innerHTML = order_id;
//	document.getElementById('order_book_time').innerHTML = order_book_time;
//	document.getElementById('order_book_location').innerHTML = order_book_location;
//	document.getElementById('order_create_time').innerHTML = order_create_time;
//	document.getElementById('order_contact_name').innerHTML = order_contact_name;
//	document.getElementById('order_contact_mobile').innerHTML = order_contact_mobile;
	if (order_master_id != null && order_master_id != "" && order_master_id != 0) {
		$('#order_master_id').append(order_master_id);
	}
	showPhoto();
}

function showPhoto() {
	var photoUrl = order_pic.replace('[','').replace(']','').split(',');
	if (photoUrl.length > 1) {
		for (var i = 0; i < photoUrl.length; i++) {
			var tempPhoto = $("#PhotoBtn").clone();
			tempPhoto.attr("href", photoUrl[i]);// 改变绑定好数据的行的id
			tempPhoto.attr("data-lightbox", "orderPhoto");// 改变绑定好数据的行的id
			tempPhoto.attr("id", "orderPhoto_" + i);
			if (i > 0) {
				tempPhoto.attr("display","none");
			}
			tempPhoto.appendTo("#order_pic");
		}
		$("#orderPhoto_0").text(">> 查看 / View <<");
	} else {
		$("#order_pic").text("無照片 / No Photo");
	}
}