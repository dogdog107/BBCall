

function onload() {
	checkAGList();
	if (addResult == 'true'){
		$("#message").html("<font color=green> Add AccessGroup Success ! </font>");
		$("#div_main").hide(300);
		$("#div_message").show(300).delay(5000).hide(300);
	}
}

//调用pagechange方法
function pagechange(pagenum){
	checkAGList(pagenum);
}

function checkAGList(pagenum){
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/access_getAccessGroupJson.action",
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
				$("tr[id^='accessgrouplist_']").remove();
				// 拿出列表
				var accessGroupList = data.accessGroupList;
				$.each(accessGroupList, function(i, n) {
					var row = $("#template").clone();
					row.find("#accessgroup_id").text(n.accessgroup_id);
					row.find("#accessgroup_name").text(n.accessgroup_name);
					row.find("#accessgroup_description").text(n.accessgroup_description);
					row.find("#btnDetail").attr("onclick", "location.href='access_showAccessGroupDetailsPage.action?token=" + token + "&accessgroup_name=" + n.accessgroup_name + "&accessgroup_id=" + n.accessgroup_id +"'");
					row.find("#btnDelete").attr("onclick", "deleteAG(" + n.accessgroup_id + ")");
					if (n.accessgroup_default == 1) {
						row.find("#btnDelete").attr("disabled", true);
					}
					row.attr("id", "accessgrouplist_" + n.accessgroup_id);// 改变绑定好数据的行的id
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

function deleteAG(idno){
	if (confirm('確定要刪除該權限組(ID:'+ idno +')嗎？\n Confirm to delete AccessGroup (ID:'+ idno +')?')) {
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/access_deleteAccessGroupByIdJson.action",
			data : {
				"token" : token,
				"accessgroup_id" : idno
			},
			success : function(data) {
				if (data.result) {
					var rowname = "accessgrouplist_" + idno;
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
