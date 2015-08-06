var global_order_col = '';
var global_order_value = '';

function onload() {
	if (token == "" || token == null) {
		if (confirm('Session has been expired! Please re-login again.\n Click "OK" to return login page.')) {
			window.parent.frames.location.href="./login.jsp";
		}
		$("#message").html("<font color=red> Session has been expired! Please re-login again. </font>");
		$("#div_main").hide(300);
		$("#div_message").show(300).delay(10000).hide(300);
		return;
	}
	checkUserList(global_order_col, global_order_value, where_col, where_value); // where user_type = 2
}

//调用pagechange方法
function pagechange(pagenum){
	checkUserList(global_order_col, global_order_value, where_col, where_value, pagenum);
}

function checkUserList(order_col, order_value, where_col, where_value, pagenum){
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/user_checkUserListWhereOrderByJson.action",
		data : {
			"token" : token,
			"order_col" : order_col,
			"order_value" : order_value,
			"where_col" : where_col,
			"where_value" : where_value,
			"pagenum" : pagenum
		},
		success : function(data) {
			if (data.result) {
				
				//*初始化分页条
				if (data.lastPageNum == 1){
					//当只有一页时隐藏分页条
					$("#page_bar").hide(300);
				} else {
					//显示分页条
					$("#page_bar").show(300);
					//当前页页码
					$("#xiye").html(data.currentPageNum);
					//尾页页码
					$("#mo").html(data.totalpagesNum);
				}
				// 清除现有列表
//				$("#datas").hide();
				$("tr[id^='userlist_']").remove();
				// 拿出列表
				var userlist = data.userlist;
				$.each(userlist, function(i, n) {
					var row = $("#template").clone();
					row.find("#userid").text(n.user_id);
					if (n.user_pic_url == "") {
						row.find("#picurl").html("<img src='' height='60' width='60'>");
					} else {
						row.find("#picurl").html("<img align='center' src=" + n.user_pic_url + " height='60' width='60'>");
					}
//					row.find("#amendlink").html("<a href=" + link + "/user_checkUserListJson.action?id=" + n.user_id + ">修改</a>");
					row.find("#account").text(n.user_account);
					row.find("#name").text(n.user_name);
					switch (n.user_type) {
					case 1:
						row.find("#usertype").html("<span style='color:#55AA00'>User</span>");
						break;
					case 2:
						row.find("#usertype").html("<span style='color:#0066FF'>Master</span>");
						break;
					case 3:
						row.find("#usertype").html("<span style='color:#EE7700'>Admin</span>");
						break;
					case 4:
						row.find("#usertype").html("<span style='color:#CC0000'>SuperAdmin</span>");
						break;
					case 5:
						row.find("#usertype").html("<span style='color:#996600'>Guest</span>");
						break;
					}
					
					switch (n.user_status) {
					case 1:
						row.find("#status").html("<span style='color:#55AA00'>Active</span>");
						break;
					case 2:
						row.find("#status").html("<span style='color:#0066FF'>Pause</span>");
						break;
					case 3:
						row.find("#status").html("<span style='color:#EE7700'>Pending</span>");
						break;
					case 4:
						row.find("#status").html("<span style='color:#CC0000'>Locked</span>");
						break;
					}
					row.find("#statusOpr").val(n	.user_status);
					row.find("#status").attr("id", "status_" + n.user_id);
					row.find("#statusOpr").attr("id", "statusOpr_" + n.user_id);
					var logintime = n.user_login_time;
					var createtime = n.user_create_time;
					if (logintime != null) {
						row.find("#logintime").text(logintime.replace("T", " "));
					}
					row.find("#createtime").text(createtime.replace("T", " "));
					// row.find("#OrderDate").text(ChangeDate(n.订购日期));
					// if(n.发货日期!== undefined)
					// row.find("#ShippedDate").text(ChangeDate(n.发货日期));
					// row.find("#more").html("<a
					// href=OrderInfo.aspx?id=" + n.订单ID +
					// "&pageindex="+pageIndex+">&nbsp;More</a>");
					row.find("#btnDetail").attr("onclick", "location.href='user_getUserById.action?token=" + token + "&userid=" + n.user_id + "'");
					row.find("#btnDelete").attr("onclick", "deleteUser(this.id)");
					row.find("#btnDelete").attr("id", "btnDelete_" + n.user_id);
					row.attr("id", "userlist_" + n.user_id);// 改变绑定好数据的行的id
					row.appendTo("#datas");// 添加到模板的容器中
					row.toggle(300);
				});
				// 显示数据
//				$("#datas").show(300);
			} else {
				//隐藏分页条
				$("#page_bar").hide(300);
				$("#message").html(
						"<font color=red>Page Fail ! " + data.errmsg
								+ "</font>");
				$("#div_message").show(300).delay(10000).hide(300);
			}
		}
	});
}

function deleteUser(idname){
	var userid = idname.split("_")[1];
	if (confirm('確定要刪除用戶(ID:'+ userid +')嗎？\n Confirm to delete user (ID:'+ userid +')?')) {
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/user_deleteUserByIdJson.action",
			data : {
				"token" : token,
				"userid" : userid
			},
			success : function(data) {
				if (data.result) {
					var rowname = "userlist_" + userid;
					$("#message").html("<font color=green> (ID:"+ userid +") Delete Success ! </font>");
					$("#div_message").show(300).delay(5000).hide(300);
					$("#" + rowname).hide(300).remove();
				} else {
					$("#message").html("<font color=red> (ID:"+ userid +") Delete Failed ! " + data.errmsg + "</font>");
					$("#div_message").show(300).delay(5000).hide(300);
					alert("Delete failed. " + data.errmsg);
				}
			}
		});
	}
}

function updateStatus(idname, value) {
	var userid = idname.split("_")[1];
	var defaultStatusName = "status_" + userid;
	if (confirm('確定要修改用戶(ID:'+ userid +')的狀態嗎？\n Confirm to change the status for user (ID:'+ userid +')?')) {
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/user_updateStatusJson.action",
			data : {
				"token" : token,
				"userid" : userid,
				"status" : value
			},
			success : function(data) {
				if (data.result) {
//					window.location.reload();
					$("#" + defaultStatusName).hide();
					switch (value) {
					case "1":
						$("#" + defaultStatusName).html("<span style='color:#55AA00'>Active</span>");
						break;
					case "2":
						$("#" + defaultStatusName).html("<span style='color:#0066FF'>Pause</span>");
						break;
					case "3":
						$("#" + defaultStatusName).html("<span style='color:#EE7700'>Pending</span>");
						break;
					case "4":
						$("#" + defaultStatusName).html("<span style='color:#CC0000'>Locked</span>");
						break;
					}
					$("#" + defaultStatusName).attr("style", "border:solid #55AA00 2px;");
					$("#" + defaultStatusName).show(300);
					$("#message").html("<font color=green> (ID:"+ userid +") Status Update Success ! </font>");
					$("#div_message").show(300).delay(5000).hide(300);
				} else {
					$("#message").html("<font color=red> (ID:"+ userid +") Status Update Failed ! " + data.errmsg + "</font>");
					$("#div_message").show(300).delay(5000).hide(300);
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
		});
	} else {
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

function order_value_change(order_value) {
	var validateResult = false;
	var new_order_col = "";
	var new_order_value = "";
	$("#order_value_span").hide();
	switch (order_value) {
	case "ASC":
	case "DESC":
		validateResult = true;
		new_order_col = $("#order_col").val();
		break;
	case "1":
	case "2":
	case "3":
	case "4":
		validateResult = true;
		new_order_col = $("#order_col").val();
		new_order_value = order_value;
		break;
	case "custom":
		validateResult = true;
		break;
	}
	if (validateResult){
		if(order_value == 'custom'){
			$("#order_value").val("");
			$("#order_value_message").text(" | " + $("#order_col").find("option:selected").text() + "搜索(Search): "); 
			$("#order_value_span").show();
		}else{
			checkUserList(new_order_col, new_order_value, where_col, where_value);
			global_order_col = new_order_col;
			global_order_value = new_order_value;
			
			$("#message").html("<font color=green> Sorting by " + $("#order_col").val() + " with " + order_value + " </font>");
			$("#div_message").show(300).delay(3000).hide(300);
		}
	}else{
		$("#message").html("<font color=red> Invalid value : " + order_value + " </font>");
		$("#div_message").show(300).delay(5000).hide(300);
	}
}