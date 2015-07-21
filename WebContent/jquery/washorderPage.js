var global_status = '';
var global_section = '';
var global_mastername = '';

//调用pagechange方法
function pagechange(pagenum){
	getwashorder(global_status, global_section, global_mastername, pagenum);
}

function onload() {
	getwashorder();
}

function getwashorder(status,section,mastername,pagenumber) {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/orderlist_getwashorderlistascJson.action",
		data : {
			"order_status" : status,
			"order_section" : section,
			"order_master_name" : mastername,
			"pagenum" : pagenumber
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
					$("#mo").html(data.lastPageNum);
				}
				// 清除现有列表
//				$("#datas").hide();
				$("tr[id^='orderlist_']").remove();
				// 拿出列表
				var washorderlist = data.orderlist;
				$.each(washorderlist, function(i, n) {
					var row = $("#template").clone();
					row.find("#orderid").text(n.order_id);
					var createtime = n.order_create_time;
					if (createtime != null) {
						row.find("#ordercreatetime").text(createtime.replace("T", " "));
					}
					var booktime = n.order_book_time;
					if (booktime != null) {
						row.find("#orderbooktime").text(booktime.replace("T", " "));
					}
					row.find("#orderbooklocation").text(n.order_book_location);
					row.find("#ordermastername").text(n.order_master_name);
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
					row.toggle();
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

function searchwash() {
	global_status =  $("#order_status").val();
	global_section = $("#order_section").val();
	global_mastername = $("#order_master_name").val();
	getwashorder(global_status, global_section, global_mastername);
}