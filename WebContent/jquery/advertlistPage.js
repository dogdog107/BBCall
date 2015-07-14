


function onload() {
	checkAdvertList();
}

//调用pagechange方法
function pagechange(pagenum){
	checkUserList(global_col_name, global_specify_value, global_search_value, pagenum);
}

function checkAdvertList(col_name, specify_value, search_value, pagenum){
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/advert_showAdvertSummaryListJson.action",
		data : {
			"token" : token,
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
					$("#mo").html(data.lastPageNum);
				}
				// 清除现有列表
//				$("#datas").hide();
				$("tr[id^='advertlist_']").remove();
				// 拿出列表
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
					row.find("#btnDelete").attr("onclick", "deleteAdvert(" + n.advertisement_id + ")");
//					row.find("#btnDelete").attr("onclick", "location.href='advert_deleteAdvert.action?advertisement_id=" + n.advertisement_id + "'");

					row.attr("id", "advertlist_" + n.advertisement_id);// 改变绑定好数据的行的id
					row.appendTo("#datas");// 添加到模板的容器中
					row.toggle(300);
				});
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