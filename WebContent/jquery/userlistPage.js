function onload() {
	$
			.ajax({
				type : "post",
				url : "${pageContext.request.contextPath}/user_checkUserListJson.action",
				data : {
					"token" : token
				},
				success : function(data) {
					if (data.checkUserListResult) {
						var userlist = data.userlist;
						$.each(userlist, function(i, n) {
							var row = $("#template").clone();
							row.find("#userid").text(n.user_id);
							if (n.user_pic_url == "") {
								row.find("#picurl").html("<img src='' height='60' width='60'>");
							} else {
								row.find("#picurl").html("<img src=" + n.user_pic_url + " height='60' width='60'>");
							}
//							row.find("#amendlink").html("<a href=" + link + "/user_checkUserListJson.action?id=" + n.user_id + ">修改</a>");
							row.find("#account").text(n.user_account);
							row.find("#name").text(n.user_name);
							switch (n.user_type) {
							case 1:
								row.find("#usertype").text("User");
								break;
							case 2:
								row.find("#usertype").text("Master");
								break;
							case 3:
								row.find("#usertype").text("Admin");
								break;
							}
							
							switch (n.user_status) {
							case 1:
								row.find("#status").text("Active");
								break;
							case 2:
								row.find("#status").text("Pause");
								break;
							case 3:
								row.find("#status").text("Pending");
								break;
							case 4:
								row.find("#status").text("Locked");
								break;
							}
							row.find("#statusOpr").val(n.user_status);
							row.find("#status").attr("id", "status_" + n.user_id);
							row.find("#statusOpr").attr("id", "statusOpr_" + n.user_id);
							var logintime = n.user_login_time;
							var createtime = n.user_create_time;
							row.find("#logintime").text(logintime.replace("T", " "));
							row.find("#createtime").text(createtime.replace("T", " "));
							// row.find("#OrderDate").text(ChangeDate(n.订购日期));
							// if(n.发货日期!== undefined)
							// row.find("#ShippedDate").text(ChangeDate(n.发货日期));
							// row.find("#more").html("<a
							// href=OrderInfo.aspx?id=" + n.订单ID +
							// "&pageindex="+pageIndex+">&nbsp;More</a>");
							row.attr("id", "user" + n.user_id);// 改变绑定好数据的行的id
							row.appendTo("#datas");// 添加到模板的容器中
							row.toggle();
						});
					} else {
						$("#message").html(
								"<font color=red>Page Fail ! " + data.errmsg
										+ "</font>");
						$("#div_message").toggle();
					}
				}
			});
}

function updateStatus(idname, value) {
	var userid = idname.split("_")[1];
	if (confirm('确定要修改状态吗？')) {
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/user_updateStatusJson.action",
			data : {
				"userid" : userid,
				"status" : value
			},
			success : function(data) {
				if (data.updateStatusResult) {
					window.location.reload()
				} else {
					alert("Update failed. " + data.errmsg);
				}
			}
		});
	} else {
		var defaultStatusName = "status_" + userid;
		switch ($("#" + defaultStatusName).text()) {
		case "Active":
			$("#" + idname).val(1);
			break;
		case "Pause":
			$("#" + idname).val(2);
			break;
		case "Pending":
			$("#" + idname).val(3);
			break;
		case "Locked":
			$("#" + idname).val(4);
			break;
		}
	}

}