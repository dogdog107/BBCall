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
	checkAdvertList();
}

//调用pagechange方法
function pagechange(pagenum){
	checkAdvertList(global_order_col, global_order_value, pagenum);
}

function checkAdvertList(order_col, order_value, pagenum){
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/advert_showAllAdvertSummaryListJson.action",
		data : {
			"token" : token,
			"pagenum" : pagenum,
			"order_col" : order_col,
			"order_value" : order_value
		},
		success : function(data) {
			if (data.result && data.advertList != null && data.advertList != "") {
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
				$("tr[id^='advertlist_']").remove();
				// 拿出列表
				var advertList = data.advertList;
				$.each(advertList, function(i, n) {
					var row = $("#template").clone();
					var smalllogo = '';
					row.find("#advertid").text(n.advertisement_id);
					if (n.advertisement_smallphoto_url == "") {
						row.find("#smallpicurl").html("<img src='' height='60' width='60'>");
					} else {
						row.find("#smallpicurl").html("<img src=" + n.advertisement_smallphoto_url + " height='60' width='60'>");
					}
					if(n.advertisement_status == 0) {
						smalllogo = smalllogo + "<img id='statusimg_" + n.advertisement_id + "' align='center' src='" + BASE_URL + "/page/img/stop_1.png'>"
						row.find("#statusOpr").val(n.advertisement_status);
					}
					row.find("#statusOpr").val(n.advertisement_status);
					row.find("#statusOpr").attr("id", "statusOpr_" + n.advertisement_id);
					if(n.advertisement_istop == 1) {
						smalllogo = smalllogo + "<img id='topimg_" + n.advertisement_id + "' align='center' src='" + BASE_URL + "/page/img/top_3.png'>"
//						row.find("#adverttitle").html("<img id='topimg_" + n.advertisement_id + "' align='center' src='" + BASE_URL + "/page/img/top_1.gif'><span>" + n.advertisement_title + "</span>");
						row.find("#istopOpr").val(n.advertisement_istop);
					}
					row.find("#istopOpr").val(n.advertisement_istop);
					row.find("#istopOpr").attr("id", "istopOpr_" + n.advertisement_id);
					row.find("#adverttitle").html(smalllogo + "<span>" + n.advertisement_title + "</span>");
					row.find("#adverttitle").attr("id", "adverttitle_" + n.advertisement_id);
					row.find("#advertsummary").text(n.advertisement_summary);
					row.find("#adverttype").attr("id", "adverttype_" + n.advertisement_id);
					checkAdvertType(n.advertisement_type, "adverttype_" + n.advertisement_id)
					var createtime = n.advertisement_create_time;
					row.find("#createtime").text(createtime.replace("T", " "));
					row.find("#btnDetail").attr("onclick", "location.href='advert_showAdvert.action?token=" + token + "&advertisement_id=" + n.advertisement_id + "'");
					row.find("#btnDelete").attr("onclick", "deleteAdvert(" + n.advertisement_id + ")");
//					row.find("#btnDelete").attr("onclick", "location.href='advert_deleteAdvert.action?advertisement_id=" + n.advertisement_id + "'");

					row.attr("id", "advertlist_" + n.advertisement_id);// 改变绑定好数据的行的id
					row.appendTo("#datas");// 添加到模板的容器中
					row.toggle(300);
				});
				$.each(advertList, function(i) {
					
				});
			} else {
				$("tr[id^='advertlist_']").remove();
				//隐藏分页条
				$("#page_bar").hide(300);
				if (data.advertList == null || data.advertList == "") {
					$("#message").html(
							"<font color=red>Page Fail ! Empty Advertisement List.</font>");
				} else {
					$("#message").html(
							"<font color=red>Page Fail ! " + data.errmsg
							+ "</font>");
				}
				$("#div_message").show(300).delay(10000).hide(300);
			}
		}
	});
}

function updateIsTop(idname, value) {
	var advertid = idname.split("_")[1];
	if (confirm('確定要修改廣告(ID:'+ advertid +')的置頂狀態嗎？\n Confirm to change the "TOP" status for AD (ID:'+ advertid +')?')) {
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/advert_updateAdvertIsTopJson",
			data : {
				"token" : token,
				"advertisement_id" : advertid,
				"advertisement_istop" : value
			},
			success : function(data) {
				if (data.result) {
					var titleid = "adverttitle_" + advertid;
					$("#" + titleid).hide();
					switch (value) {
					case "0":
						var topimgid = "topimg_" + advertid;
						$("#" + topimgid).remove();
						break;
					case "1":
						var tempTitle = $("#" + titleid).html();
						tempTitle = "<img id='topimg_" + advertid + "' align='center' src='" + BASE_URL + "/page/img/top_3.png'>" + tempTitle;
						$("#" + titleid).html(tempTitle);
//						$("#" + titleid).append("<img id='topimg_" + advertid + "' align='center' src='" + BASE_URL + "/page/img/top_3.png'>");
						break;
					}
					$("#" + titleid).attr("style", "border:solid #55AA00 2px;");
					$("#" + titleid).show(300);
					$("#message").html("<font color=green> (ID:"+ advertid +") TOP Update Success ! </font>");
					$("#div_message").show(300).delay(5000).hide(300);
					setTimeout(function() {
						window.location.reload();
					}, 1500); // how long do you want the delay to be?
				} else {
					$("#message").html("<font color=red> (ID:"+ advertid +") TOP Update Failed ! " + data.errmsg + "</font>");
					$("#div_message").show(300).delay(5000).hide(300);
					var istopOprid = "istopOpr_" + advertid;
					switch (value) {
					case "0":
						$("#" + istopOprid).val(1);
						break;
					case "1":
						$("#" + istopOprid).val(0);
						break;
					}
				}
			}
		});
	} else {
		var istopOprid = "istopOpr_" + advertid;
		switch (value) {
		case "0":
			$("#" + istopOprid).val(1);
			break;
		case "1":
			$("#" + istopOprid).val(0);
			break;
		}
	}
}

function updateStatus(idname, value) {
	var advertid = idname.split("_")[1];
	if (confirm('確定要修改廣告(ID:'+ advertid +')的狀態嗎？\n Confirm to change the status for AD (ID:'+ advertid +')?')) {
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/advert_updateAdvertStatusJson",
			data : {
				"token" : token,
				"advertisement_id" : advertid,
				"advertisement_status" : value
			},
			success : function(data) {
				if (data.result) {
					var titleid = "adverttitle_" + advertid;
					$("#" + titleid).hide();
					switch (value) {
					case "0":
						var tempTitle = $("#" + titleid).html();
						tempTitle = "<img id='statusimg_" + advertid + "' align='center' src='" + BASE_URL + "/page/img/stop_1.png'>" + tempTitle;
						$("#" + titleid).html(tempTitle);
//						$("#" + titleid).append("<img id='topimg_" + advertid + "' align='center' src='" + BASE_URL + "/page/img/top_1.gif'>");
						break;
					case "1":
						var statusimgid = "statusimg_" + advertid;
						$("#" + statusimgid).remove();
						break;
					}
					$("#" + titleid).attr("style", "border:solid #55AA00 2px;");
					$("#" + titleid).show(300);
					$("#message").html("<font color=green> (ID:"+ advertid +") Status Update Success ! </font>");
					$("#div_message").show(300).delay(5000).hide(300);
					setTimeout(function() {
						window.location.reload();
					}, 1500); // how long do you want the delay to be?
				} else {
					$("#message").html("<font color=red> (ID:"+ advertid +") Status Update Failed ! " + data.errmsg + "</font>");
					$("#div_message").show(300).delay(5000).hide(300);
					var statusOprid = "statusOpr_" + advertid;
					switch (value) {
					case "0":
						$("#" + statusOprid).val(1);
						break;
					case "1":
						$("#" + statusOprid).val(0);
						break;
					}
				}
			}
		});
	} else {
		var statusOprid = "statusOpr_" + advertid;
		switch (value) {
		case "0":
			$("#" + statusOprid).val(1);
			break;
		case "1":
			$("#" + statusOprid).val(0);
			break;
		}
	}
}

function deleteAdvert(idno){
	if (confirm('確定要刪除廣告(ID:'+ idno +')嗎？\n Confirm to delete Advertisement (ID:'+ idno +')?')) {
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/advert_deleteAdvertJson.action",
			data : {
				"token" : token,
				"advertisement_id" : idno
			},
			success : function(data) {
				if (data.result) {
					var rowname = "advertlist_" + idno;
					$("#message").html("<font color=green> (ID:"+ idno +") Delete Success ! </font>");
					$("#div_message").show(300).delay(5000).hide(300);
					$("#" + rowname).hide(300).remove();
				} else {
					$("#message").html("<font color=red> (ID:"+ idno +") Delete Failed ! " + data.errmsg + "</font>");
					$("#div_message").show(300).delay(5000).hide(300);
					alert("Delete failed. " + data.errmsg);
				}
			}
		});
	}
}

function checkAdvertType(typecode, idname){
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_selectJson.action",
		data : {
			"token" : token,
			"referdoc_id" : typecode
		},
		success : function(data) {
			if (data.result) {
				$("#" + idname).text(data.referdoc.referdoc_type).show(300);
			}
		}
	});
}


function col_name_change(colname_value) {
	if (colname_value == "default") {
		$("#order_value").hide(300);
		global_order_col = "";
		global_order_value = "";
		checkAdvertList(global_order_col, global_order_value);
		$("#message").html("<font color=green> Sorting by " + colname_value + " </font>");
		$("#div_message").show(300).delay(3000).hide(300);
	} else {
		$("#order_value").val("ASC");
		$("#order_value").show(300);
		global_order_col = colname_value;
		global_order_value = "";
		checkAdvertList(global_order_col, global_order_value);
		$("#message").html("<font color=green> Sorting by " + colname_value + " </font>");
		$("#div_message").show(300).delay(3000).hide(300);
	}
}

function order_value_change(order_value) {
	global_order_value = order_value;
	checkAdvertList(global_order_col, global_order_value);
	$("#message").html("<font color=green> Sorting by " + global_order_col + " " + global_order_value + " </font>");
	$("#div_message").show(300).delay(3000).hide(300);
}