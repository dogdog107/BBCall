

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
	showAGA();
}

function showAGA(){
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/access_getAccessByAccessGroupJson.action",
		data : {
			"token" : token,
			"accessgroup_name" : accessGroupName
		},
		success : function(data) {
			if (data.result) {
				// 清除现有列表
				$("tr[id^='AGAlist_']").remove();
				// 拿出列表
				var accessList = data.accesslist;
				if (accessList =='' || accessList == null) {
					var row = $("<tr><td colspan='2'><span style='color:red;'>## Empty Access List ##</span></td></tr>");
					row.hide().appendTo("#datas");// 添加到模板的容器中
					row.show(300);
				} else {
					$.each(accessList, function(i, n) {
						var row = $("#template").clone();
						row.find("#access_name").text(n.accesslist_name);
						row.find("#access_description").text(n.accesslist_description);
						row.attr("id", "AGAlist_" + n.accesslist_id);// 改变绑定好数据的行的id
						row.appendTo("#datas");// 添加到模板的容器中
						row.toggle(300);
					});
				}
			} else {
				//隐藏分页条
				$("#message").html(
						"<font color=red>Page Fail ! " + data.errmsg
								+ "</font>");
				$("#div_message").show(300).delay(10000).hide(300);
			}
		}
	});
}