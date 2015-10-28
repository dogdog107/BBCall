function onload() {
//	document.getElementById('order_id').value = order_id;
//	document.getElementById('order_book_time').value = order_book_time;
//	document.getElementById('order_book_location').value = order_book_location;
//	document.getElementById('order_create_time').value = order_create_time;
//	document.getElementById('order_contact_name').value = order_contact_name;
//	document.getElementById('order_contact_mobile').value = order_contact_mobile;
//	document.getElementById('order_remark').value = order_remark;
//	document.getElementById('order_description').value = order_description;
	
	switch (order_status) {
		case "1":
//			document.getElementById('order_status').value = "新建訂單";
			$('#order_status').append("新建訂單");
			break;
		case "2":
//			document.getElementById('order_status').value = "待評價訂單";
			$('#order_status').append("待評價訂單");
			break;
		case "3":
//			document.getElementById('order_status').value = "已評價訂單";
			$('#order_status').append("已評價訂單");
			break;
		case "4":
//			document.getElementById('order_status').value = "收到貨物";
			$('#order_status').append("收到貨物");
			break;
		case "5":
//			document.getElementById('order_status').value = "正在清洗";
			$('#order_status').append("正在清洗");
			break;
		case "6":
//			document.getElementById('order_status').value = "正在配送";
			$('#order_status').append("正在配送");
			break;
		case "7":
//			document.getElementById('order_status').value = "已出價訂單";
			$('#order_status').append("已出價訂單");
			break;
	}
	
	if (order_master_id != null && order_master_id != "" && order_master_id != 0) {
		$('#order_master_id').append(order_master_id);
	}
	
	showPhoto();
	
	if (order_status == 7) {
		showPreOrder();
	}
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

function showPreOrder() {
	$.ajax({
				type : "post",
				url : "${pageContext.request.contextPath}/preorder_showbyidJson.action",
				data : {
					"token" : token,
					"preorder_order_id" : order_id
				},
				success : function(data) {
					if (data.result) {
						$("#table2").show(300);
						var preorderlist = data.preorderlist;
						if (preorderlist != null && preorderlist != "") {
							$.each(preorderlist, function(i, n) {
								var row = $("#template").clone();
								row.find("#preOrderId").text(n.preorder_id);
								row.find("#createTime").text(n.preorder_create_time.replace("T", " "));
								row.find("#price").text(n.preorder_price);
								row.find("#masterId").text(n.preorder_master_id);
								row.find("#masterAccount").text(n.preorder_master_account);
								row.find("#masterGrade").text(n.preorder_master_grade);
								row.find("#masterName").text(n.preorder_master_name);
								
								if (n.preorder_master_pic == "") {
									row.find("#masterPhoto").html("<img src='' height='60' width='60'>");
								} else {
									row.find("#masterPhoto").html("<img align='center' src=" + n.preorder_master_pic + " height='60' width='60'>");
								}
//								row.find("#masterSkill").text(n.preorder_master_skill);
								row.find("#btnDelete").attr("onclick", "deletePreOrder(this.id)");
								row.find("#btnDelete").attr("id", "btnDelete_" + n.preorder_id);
								row.attr("id", "preOrder_" + n.preorder_id);// 改变绑定好数据的行的id
								row.appendTo("#datas");// 添加到模板的容器中
								row.toggle(300);
							});
						} else {
							var row = $("<tr><td colspan='10'><span style='color:red;'>## Empty Bid Order List ##</span></td></tr>");
							row.hide().appendTo("#datas");// 添加到模板的容器中
							row.toggle(300);
						}
					}
				}
			});
}

function deletePreOrder(idname) {
	var preOrderId = idname.split("_")[1];
	if (confirm('確定要刪除競價訂單(ID:'+ preOrderId +')嗎？\n Confirm to delete bid order (ID:'+ preOrderId +')?')) {
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/preorder_deleteJson.action",
			data : {
				"token" : token,
				"preorder_id" : preOrderId,
				"preorder_master_id" : 1
				
			},
			success : function(data) {
				if (data.result) {
					var rowname = "preOrder_" + preOrderId;
					$("#message").html("<font color=green> (ID:"+ preOrderId +") Delete Success ! </font>");
					$("#div_message").show(300).delay(5000).hide(300);
					$("#" + rowname).hide(300);
				} else {
					$("#message").html("<font color=red> (ID:"+ preOrderId +") Delete Failed ! " + data.errmsg + "</font>");
					$("#div_message").show(300).delay(5000).hide(300);
					alert("Delete failed. " + data.errmsg);
				}
			}
		});
	}
}