

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
	showMsg();
}

function showMsg(){
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/gcm_getPushMessageJson.action",
		data : {
			"token" : token
		},
		success : function(data) {
			if (data.result) {
				// 清除现有列表
				$("tr[id^='Msglist_']").remove();
				// 拿出列表
				var msgList = data.pushMessage;
				if (msgList =='' || msgList == null) {
					var row = $("<tr><td colspan='2'><span style='color:red;'>## Empty Message List ##</span></td></tr>");
					row.hide().appendTo("#datas");// 添加到模板的容器中
					row.show(300);
				} else {
					$.each(msgList, function(i, n) {
						var row = $("#template").clone();
						row.find("#msgid").text(n.msg_id);
						row.find("#msgtype").text(n.msg_type);
						row.find("#msgcontents").text(n.msg_content);
						row.find("#msgcontents").attr("id", "msgcontents_" + n.msg_id);
						row.find("#btnRevise").attr("onclick", "reviseMsg(" + n.msg_id + ")");
						row.attr("id", "Msglist_" + n.msg_id);// 改变绑定好数据的行的id
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

function reviseMsg(msgId) {
	var idName = "msgcontents_" + msgId;
	var newMsg = $("#" + idName).val();
	if (confirm('確定要修改推送訊息(ID:' + msgId
			+ ')的內容嗎？\n Confirm to change the contents for message (ID:'
			+ msgId + ')?')) {
		$
				.ajax({
					type : "post",
					url : "${pageContext.request.contextPath}/gcm_updatePushMessageJson.action",
					data : {
						"token" : token,
						"msgid" : msgId,
						"msgcontents" : newMsg
					},
					success : function(data) {
						if (data.result) {
							$("#" + idName).attr("style",
									"border:solid #55AA00 2px;");
							$("#" + idName).show(300);
							$("#message")
									.html(
											"<font color=green> (ID:"
													+ msgId
													+ ") Message Update Success ! </font>");
							$("#div_message").show(300).delay(5000).hide(300);
						} else {
							// 隐藏分页条
							$("#message").html(
									"<font color=red>Page Fail ! "
											+ data.errmsg + "</font>");
							$("#div_message").show(300).delay(10000).hide(300);
							setTimeout(function() {
								window.location.reload();
							}, 1500); // how long do you want the delay to be?
						}
					}
				});
	}
}