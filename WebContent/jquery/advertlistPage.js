


function onload() {
	checkAdvertList();
}

function checkAdvertList(col_name, specify_value, search_value){
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/advert_showAdvertSummaryListJson.action",
		data : {
			"token" : token
		},
		success : function(data) {
			if (data.result) {
				var advertList = data.advertList;
				$.each(advertList, function(i, n) {
					var row = $("#template").clone();
					row.find("#advertid").text(n.advertisement_id);
					if (n.advertisement_smallphoto_url == "") {
						row.find("#smallpicurl").html("<img src='' height='60' width='60'>");
					} else {
						row.find("#smallpicurl").html("<img src=" + n.advertisement_smallphoto_url + " height='60' width='60'>");
					}
					row.find("#adverttitle").text(n.advertisement_title);
					row.find("#advertsummary").text(n.advertisement_summary);
					row.find("#adverttype").text(n.advertisement_type);
					var createtime = n.advertisement_create_time;
					row.find("#createtime").text(createtime.replace("T", " "));
					row.find("#btnDetail").attr("onclick", "location.href='advert_showAdvert.action?advertisement_id=" + n.advertisement_id + "'");
					row.find("#btnDelete").attr("onclick", "location.href='advert_deleteAdvert.action?advertisement_id=" + n.advertisement_id + "'");

					row.attr("id", "advertlist_" + n.advertisement_id);// 改变绑定好数据的行的id
					row.appendTo("#datas");// 添加到模板的容器中
					row.toggle();
				});
			} else {
				$("#message").html(
						"<font color=red>Page Fail ! " + data.errmsg
								+ "</font>");
				$("#div_message").show(300).delay(10000).hide(300);
			}
		}
	});
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
					switch (value) {
					case "1":
						$("#" + defaultStatusName).text("Active");
						break;
					case "2":
						$("#" + defaultStatusName).text("Pause");
						break;
					case "3":
						$("#" + defaultStatusName).text("Pending");
						break;
					case "4":
						$("#" + defaultStatusName).text("Locked");
						break;
					}
					$("#message").html("<font color=green> (ID:"+ userid +") Status Update Success ! </font>");
					$("#div_message").show(300).delay(5000).hide(300);
				} else {
					$("#message").html("<font color=red> (ID:"+ userid +") Status Update Failed ! </font>");
					$("#div_message").show(300).delay(5000).hide(300);
					alert("Update failed. " + data.errmsg);
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
		$("tr[id^='userlist_']").remove();
		checkUserList(colname_value);
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
			$("tr[id^='userlist_']").remove();
			checkUserList(new_col_name, new_specify_value);
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
	$("tr[id^='userlist_']").remove();
	checkUserList(new_col_name,"",new_search_value);
}