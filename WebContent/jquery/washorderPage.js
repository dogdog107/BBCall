var global_status = '';
var global_section = '';
var global_mastername = '';
var global_booktime = '';
var global_orderid = '';
var global_bookcode = '';
var global_order_col = "order_id";
var global_order_value = "";

//调用pagechange方法
function pagechange(pagenum){
	getwashorder(global_status, global_section, global_mastername,global_booktime,global_orderid, global_bookcode, global_order_col, global_order_value, pagenum);
}

function onload(global_status, global_section, global_mastername,global_booktime,global_orderid, global_bookcode) {
	global_status =  $("#order_status").val();
	global_section = $("#order_section").val();
	global_mastername = $("#order_master_name").val();
	global_booktime = $("#order_time").val();
	global_bookcode = $("#order_book_location_code").val();
	global_orderid = $("#order_id").val();
	getwashorder(global_status, global_section, global_mastername,global_booktime,global_orderid, global_bookcode, global_order_col, global_order_value);
}

function getwashorder(status,section,mastername,booktime,orderid,bookcode, order_col, order_value, pagenumber) {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/orderlist_getwashorderlistascJson.action",
		data : {
			"order_status" : status,
			"order_section" : section,
			"order_master_name" : mastername,
			"order_book_location_code" : bookcode,
			"order_id" : orderid,
			"order_time" : booktime,
			"order_col" : order_col,
			"order_value" : order_value,
			"pagenum" : pagenumber
		},
		success : function(data) {
			if (data.result && data.orderlist != null && data.orderlist != "") {
				//*初始化分页条
				if (data.lastPageNum <= 1){
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
				$("tr[id^='orderlist_']").remove();
				// 拿出列表
				var washorderlist = data.orderlist;
				$.each(washorderlist, function(i, n) {
					var row = $("#template").clone();
					row.find("#orderid").text(n.order_id);
					row.find("#ordertype").text(n.order_type);
					var createtime = n.order_create_time;
					if (createtime != null) {
						row.find("#ordercreatetime").text(createtime.replace("T", " "));
					}
					var booktime = n.order_book_time;
					if (booktime != null) {
						row.find("#orderbooktime").text(booktime.replace("T", " "));
					}
					row.find("#orderbooklocation").text(n.order_book_location);
					if (n.order_master_name != null && n.order_master_name != "") {
						row.find("#ordermastername").text(n.order_master_name);
					}
					switch (n.order_status) {
					case 1:
						row.find("#orderstatus").text("新建訂單");
						break;
					case 2:
						row.find("#orderstatus").text("待評價訂單");
						break;
					case 3:
						row.find("#orderstatus").text("已評價訂單");
						break;
					case 4:
						row.find("#orderstatus").text("收到貨物");
						break;
					case 5:
						row.find("#orderstatus").text("正在清洗");
						break;
					case 6:
						row.find("#orderstatus").text("正在配送");
						break;
					case 7:
						row.find("#orderstatus").text("已出價訂單");
						break;
					}
					row.find("#orderview").attr("onclick", "location.href='orderlist_select.action?order_id=" + n.order_id +"'");
					row.attr("id", "orderlist_" + n.referdoc_id);// 改变绑定好数据的行的id
					row.appendTo("#datas");// 添加到模板的容器中
					row.toggle(300);
				});
				// 显示数据
//				$("#datas").show(300);
			} else {
				$("tr[id^='orderlist_']").remove();
				//隐藏分页条
				$("#page_bar").hide(300);
				if(data.orderlist == null || data.orderlist == "") {
					$("#message").html(
					"<font color=red>Page Fail ! Empty Order List.</font>");
				} else {
					$("#message").html(
							"<font color=red>Page Fail ! " + data.errmsg
							+ "</font>");
				}
				$("#div_message").show(300).delay(5000).hide(300);
			}
		}
	});
}

function searchwash() {
	global_status =  $("#order_status").val();
	global_section = $("#order_section").val();
	global_mastername = $("#order_master_name").val();
	global_booktime = $("#order_time").val();
	global_bookcode = $("#order_book_location_code").val();
	global_orderid = $("#order_id").val();
	getwashorder(global_status, global_section, global_mastername,global_booktime,global_orderid, global_bookcode, global_order_col, global_order_value);
}

function reset() {
	$("#order_status").val("");
	$("#order_section").val("");
	$("#order_master_name").val("");
	$("#order_time").val("");
	$("#order_book_location_code").val("");
	$("#order_id").val("");
	global_status =  $("#order_status").val();
	global_section = $("#order_section").val();
	global_mastername = $("#order_master_name").val();
	global_booktime = $("#order_time").val();
	global_bookcode = $("#order_book_location_code").val();
	global_orderid = $("#order_id").val();
	getwashorder(global_status, global_section, global_mastername,global_booktime,global_orderid, global_bookcode, global_order_col, global_order_value);
}

function col_name_change(colname_value) {
	$("#order_value").hide(300);
	$("#order_value").val("ASC");
	$("#order_value").show(300);
	global_order_col = colname_value;
	global_order_value = "";
	getwashorder(global_status, global_section, global_mastername,global_booktime,global_orderid, global_bookcode, global_order_col, global_order_value);
	$("#message").html("<font color=green> Sorting by " + colname_value + " </font>");
	$("#div_message").show(300).delay(3000).hide(300);
}

function order_value_change(order_value) {
	global_order_value = order_value;
	getwashorder(global_status, global_section, global_mastername,global_booktime,global_orderid, global_bookcode, global_order_col, global_order_value);
	$("#message").html("<font color=green> Sorting by " + global_order_col + " " + global_order_value + " </font>");
	$("#div_message").show(300).delay(3000).hide(300);
}

function getsub() {
	var addresscode = $("#adscode_2").val();
	$("#order_book_location_code").empty();
	if (addresscode == 1) {
		$("#order_book_location_code").append("<option value=''>--請選擇鎮區--</option>");
		$("#order_book_location_code").append("<option value='810101'>中西區</option>");
		$("#order_book_location_code").append("<option value='810102'>灣仔區</option>");
		$("#order_book_location_code").append("<option value='810103'>東區</option>");
		$("#order_book_location_code").append("<option value='810104'>南區</option>");
	} else if (addresscode == 2) {
		$("#order_book_location_code").append("<option value=''>--請選擇鎮區--</option>");
		$("#order_book_location_code").append("<option value='810201'>油尖旺區</option>");
		$("#order_book_location_code").append("<option value='810202'>深水埗區</option>");
		$("#order_book_location_code").append("<option value='810203'>九龍城區</option>");
		$("#order_book_location_code").append("<option value='810204'>黃大仙區</option>");
		$("#order_book_location_code").append("<option value='810205'>觀塘區</option>");
	} else if (addresscode == 3) {
		$("#order_book_location_code").append("<option value=''>--請選擇鎮區--</option>");
		$("#order_book_location_code").append("<option value='810301'>北區</option>");
		$("#order_book_location_code").append("<option value='810302'>大埔區</option>");
		$("#order_book_location_code").append("<option value='810303'>沙田區</option>");
		$("#order_book_location_code").append("<option value='810304'>西貢區</option>");
		$("#order_book_location_code").append("<option value='810305'>荃灣區</option>");
		$("#order_book_location_code").append("<option value='810306'>屯門區</option>");
		$("#order_book_location_code").append("<option value='810307'>元朗區</option>");
		$("#order_book_location_code").append("<option value='810308'>葵青區</option>");
		$("#order_book_location_code").append("<option value='810309'>離島區</option>");
	} else {
		$("#order_book_location_code").append("<option value=''>--請選擇鎮區--</option>");
	}
}