

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
	checkLogList("User");
	if (addResult == 'true'){
		$("#message").html("<font color=green> Add AccessGroup Success ! </font>");
		$("#div_main").hide(300);
		$("#div_message").show(300).delay(5000).hide(300);
	}
}

//调用pagechange方法
//function pagechange(pagenum){
//	checkAGList(pagenum);
//}

function checkLogList(fileType){
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/download_getFileListJson.action",
		data : {
			"token" : token,
			"fileType" : fileType
		},
		success : function(data) {
			if (data.result) {
				//*初始化分页条
				if (data.lastPageNum == null || data.lastPageNum == 1){
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
				$("tr[id^='loglist_']").remove();
				// 拿出列表
				$.each(data.fileList, function(i, n) {
					var name = n.split(".");
					if (name.length <= 2) {
						var row = $("#currentLog");
						var now = new Date();
						var todayDate = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
						row.find("#log_time").text(todayDate);
					} else {
						var row = $("#template").clone();
						row.find("#log_time").text(name[2]);
					}
					row.find("#log_name").text(name[0]);
					row.find("#btnDownload").attr("onclick", "location.href='download_downloadFile.action?token=" + token + "&fileName=" + n + "'");
					if (name.length == 3) {
						row.attr("id", "loglist_" + n);// 改变绑定好数据的行的id
						row.appendTo("#datas");// 添加到模板的容器中
						row.toggle(300);
					}
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

function logType_change(value) {
	checkLogList(value);
}