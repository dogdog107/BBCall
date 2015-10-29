

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
	checkSkillList();
}

//调用pagechange方法
function pagechange(pagenum){
	checkSkillList(pagenum);
}

function checkSkillList(pagenum){
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/user_getAllUserSkillJson.action",
		data : {
			"token" : token,
			"pagenum" : pagenum
		},
		success : function(data) {
			if (data.result && data.skilllist != null && data.skilllist != "") {
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
				$("tr[id^='skilllist_']").remove();
				// 拿出列表
				var skilllist = data.skilllist;
				$.each(skilllist, function(i, n) {
					var row = $("#template").clone();
					row.find("#skillid").text(n.userskill_id);
					row.find("#userid").text(n.user_id);
					row.find("#useraccount").text(n.user_account);
					row.find("#useraccount").text(n.user_account);
//					row.find("#skillname").text(n.user_skill_name);
					if (n.user_skill_url != null && n.user_skill_url != "") {
						var photoUrl = n.user_skill_url.split(';');
						var photoBox = "skillPhoto_" + n.userskill_id;
						for (var i = 0; i < photoUrl.length; i++) {
							var tempPhoto = row.find("#PhotoBtn").clone();
							tempPhoto.attr("href", photoUrl[i]);// 改变绑定好数据的行的id
							tempPhoto.attr("data-lightbox", photoBox);// 改变绑定好数据的行的id
							tempPhoto.attr("id", photoBox + "_" + i);
							if (i > 0) {
								tempPhoto.attr("display","none");
							}
//							tempPhoto.appendTo("#order_pic");
							row.find("#skillname").append(tempPhoto);
						}
						var photoBox0 = photoBox + "_0";
						row.find("#" + photoBox0).text(n.user_skill_name);
					} else {
						row.find("#skillname").text(n.user_skill_name);
					}
					row.find("#skillname").attr("id", "skillname_" + n.userskill_id);
//					checkSkillName(n.user_skill, "skillname_" + n.userskill_id);
					switch (n.user_skill_status) {
					case 0:
						row.find("#skillstatus").html("<span style='color:#EE7700'>Pending</span>");
						break;
					case 1:
						row.find("#skillstatus").html("<span style='color:#55AA00'>Approved</span>");
						break;
					case 2:
						row.find("#skillstatus").html("<span style='color:#CC0000'>Rejected</span>");
						break;
					}
					row.find("#skillstatus").attr("id", "skillstatus_" + n.userskill_id);
					row.find("#statusOpr").val(n.user_skill_status);
					row.find("#statusOpr").attr("id", "statusOpr_" + n.userskill_id);
					var createtime = n.user_skill_create_time;
					row.find("#createtime").text(createtime.replace("T", " "));
					row.find("#btnDetail").attr("onclick", "location.href='user_getUserById.action?token=" + token + "&userid=" + n.user_id + "'");
					row.find("#btnDelete").attr("onclick", "deleteSkill(" + n.userskill_id + ")");

					row.attr("id", "skilllist_" + n.userskill_id);// 改变绑定好数据的行的id
					row.appendTo("#datas");// 添加到模板的容器中
					row.toggle(300);
				});
			} else {
				//隐藏分页条
				$("#page_bar").hide(300);
				if (data.skilllist == null || data.skilllist == "") {
					$("#message").html(
							"<font color=red>Page Fail ! Empty Skill List.</font>");
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

function updateStatus(idname, value) {
	var skillid = idname.split("_")[1];
	var defaultStatusName = "skillstatus_" + skillid;
	if (confirm('確定要修改技能(ID:'+ skillid +')的狀態嗎？\n Confirm to change the status for skill (ID:'+ skillid +')?')) {
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/user_verifyUserSkillBySkillIdJson",
			data : {
				"token" : token,
				"skillid" : skillid,
				"skillstatus" : value
			},
			success : function(data) {
				if (data.result) {
					$("#" + defaultStatusName).hide();
					switch (value) {
					case "0":
						$("#" + defaultStatusName).html("<span style='color:#EE7700'>Pending</span>");
						break;
					case "1":
						$("#" + defaultStatusName).html("<span style='color:#55AA00'>Approved</span>");
						break;
					case "2":
						$("#" + defaultStatusName).html("<span style='color:#CC0000'>Rejected</span>");
						break;
					}
					$("#" + defaultStatusName).show(300);
					$("#" + defaultStatusName).attr("style", "border:solid #55AA00 2px;");
					$("#message").html("<font color=green> (ID:"+ skillid +") Status Update Success ! </font>");
					$("#div_message").show(300).delay(5000).hide(300);
				} else {
					$("#message").html("<font color=red> (ID:"+ skillid +") Status Update Failed ! " + data.errmsg + "</font>");
					$("#div_message").show(300).delay(5000).hide(300);
					switch ($("#" + defaultStatusName).text()) {
					case "Pending":
						$("#" + idname).val(0);
						break;
					case "Approved":
						$("#" + idname).val(1);
						break;
					case "Rejected":
						$("#" + idname).val(2);
						break;
					}
				}
			}
		});
	} else {
		switch ($("#" + defaultStatusName).text()) {
		case "Pending":
			$("#" + idname).val(0);
			break;
		case "Approved":
			$("#" + idname).val(1);
			break;
		case "Rejected":
			$("#" + idname).val(2);
			break;
		}
	}
}

function deleteSkill(idno){
	if (confirm('確定要刪除廣告(ID:'+ idno +')嗎？\n Confirm to delete skill item (ID:'+ idno +')?')) {
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/user_deleteUserSkillBySkillIdJson.action",
			data : {
				"token" : token,
				"skillid" : idno
			},
			success : function(data) {
				if (data.result) {
					var rowname = "skilllist_" + idno;
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

function checkSkillName(typecode, idname){
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