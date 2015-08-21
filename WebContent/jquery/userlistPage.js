var global_col_name = '';
var global_specify_value = '';
var global_search_value = '';

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
	checkUserList();
}

//调用pagechange方法
function pagechange(pagenum){
	checkUserList(global_col_name, global_specify_value, global_search_value, pagenum);
}

function checkUserList(col_name, specify_value, search_value, pagenum){
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/user_checkUserListJson.action",
		data : {
			"token" : token,
			"col_name" : col_name,
			"specify_value" : specify_value,
			"search_value" : search_value,
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
				var templist = data.userlist;
				$.each(templist, function(i, n) {
					var row = $("#template").clone();
					row.find("#userid").text(n.user_id);
					if (n.user_pic_url == "") {
						row.find("#picurl").html("<img src='' height='60' width='60'>");
					} else {
						row.find("#picurl").html("<img align='center' src=" + n.user_pic_url + " height='60' width='60'>");
					}
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
					row.find("#statusOpr").val(n.user_status);
					row.find("#status").attr("id", "status_" + n.user_id);
					row.find("#statusOpr").attr("id", "statusOpr_" + n.user_id);
					var logintime = n.user_login_time;
					var createtime = n.user_create_time;
					if (logintime != null) {
						row.find("#logintime").text(logintime.replace("T", " "));
					}
					row.find("#createtime").text(createtime.replace("T", " "));
					row.find("#btnDetail").attr("onclick", "location.href='user_getUserById.action?token=" + token + "&userid=" + n.user_id + "'");
					row.find("#btnDelete").attr("onclick", "deleteUser(this.id)");
					row.find("#btnDelete").attr("id", "btnDelete_" + n.user_id);
					// row.find("#OrderDate").text(ChangeDate(n.订购日期));
					// if(n.发货日期!== undefined)
					// row.find("#ShippedDate").text(ChangeDate(n.发货日期));
					// row.find("#more").html("<a
					// href=OrderInfo.aspx?id=" + n.订单ID +
					// "&pageindex="+pageIndex+">&nbsp;More</a>");
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
					$("#" + defaultStatusName).show(300);
					$("#" + defaultStatusName).attr("style", "border:solid #55AA00 2px;");
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

function col_name_change(colname_value) {
	var validateResult = false;
	$("#search_value_span").hide();
	$("#specify_value").empty();
	switch (colname_value) {
	case "user_id":
	case "user_create_time":
	case "user_login_time":
		validateResult = true;
		$("#specify_value").append("<option value='ASC'>升序(ASC)</option>");
		$("#specify_value").append("<option value='DESC'>降序(DESC)</option>");
		break;
	case "user_account":
	case "user_name":
		validateResult = true;
		$("#specify_value").append("<option value='ASC'>升序(ASC)</option>");
		$("#specify_value").append("<option value='DESC'>降序(DESC)</option>");
		$("#specify_value").append("<option value='custom'>自定義搜索(Search)</option>");
		break;
	case "user_type":
		validateResult = true;
		$("#specify_value").append("<option value='ASC'>升序(ASC)</option>");
		$("#specify_value").append("<option value='DESC'>降序(DESC)</option>");
		$("#specify_value").append("<option value='1'>用戶排頭(User on Top)</option>");
		$("#specify_value").append("<option value='2'>師傅排頭(Master on Top)</option>");
		$("#specify_value").append("<option value='3'>管理員排頭(Admin on Top)</option>");
		$("#specify_value").append("<option value='4'>超級管理員排頭(SuperAdmin on Top)</option>");
		$("#specify_value").append("<option value='5'>訪客排頭(Guest on Top)</option>");
		break;
	case "user_status":
		validateResult = true;
		$("#specify_value").append("<option value='ASC'>升序(ASC)</option>");
		$("#specify_value").append("<option value='DESC'>降序(DESC)</option>");
		$("#specify_value").append("<option value='1'>Active排頭(Active on Top)</option>");
		$("#specify_value").append("<option value='2'>Pause排頭(Pause on Top)</option>");
		$("#specify_value").append("<option value='3'>Pending排頭(Pending on Top)</option>");
		$("#specify_value").append("<option value='4'>Locked排頭(Locked on Top)</option>");
		break;
	}
	if (validateResult){
		$("#specify_value").val("ASC");
		checkUserList(colname_value);
		global_col_name = colname_value;
		$("#message").html("<font color=green> Sorting by " + colname_value + " </font>");
		$("#div_message").show(300).delay(3000).hide(300);
	}else{
		$("#message").html("<font color=red> Invalid value : " + colname_value + " </font>");
		$("#div_message").show(300).delay(5000).hide(300);
	}
}

function specify_value_change(specify_value) {
	var validateResult = false;
	var new_col_name = "";
	var new_specify_value = "";
	$("#search_value_span").hide();
	switch (specify_value) {
	case "ASC":
	case "DESC":
		validateResult = true;
		new_col_name = $("#col_name").val() + " " + specify_value;
		break;
	case "1":
	case "2":
	case "3":
	case "4":
	case "5":
		validateResult = true;
		new_col_name = $("#col_name").val();
		new_specify_value = specify_value;
		break;
	case "custom":
		validateResult = true;
		break;
	}
	if (validateResult){
		if(specify_value == 'custom'){
			$("#search_value").val("");
			$("#search_value_message").text(" | " + $("#col_name").find("option:selected").text() + "搜索(Search): "); 
			$("#search_value_span").show();
		}else{
			checkUserList(new_col_name, new_specify_value);
			global_col_name = new_col_name;
			global_specify_value = new_specify_value;
			$("#message").html("<font color=green> Sorting by " + $("#col_name").val() + " with " + specify_value + " </font>");
			$("#div_message").show(300).delay(3000).hide(300);
		}
	}else{
		$("#message").html("<font color=red> Invalid value : " + specify_value + " </font>");
		$("#div_message").show(300).delay(5000).hide(300);
	}
}


function search_value() {
	var new_col_name = $("#col_name").val();
	var new_search_value = $("#search_value").val();
	checkUserList(new_col_name,"",new_search_value);
}