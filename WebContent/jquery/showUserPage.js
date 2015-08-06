
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
	
	$('#statusOpr').val(status);
	$('#statusOpr').attr("id", "statusOpr_" + userid);
	if (userskill != ''){
		showSkill(userskill,"userSkillID");
	}
	
	showPendingSkill();
	
	if (updateResult != '') {
		if(updateResult == 'true'){
			$("#message").html("<font color=green> Edit User Success! </font>");
			$("#div_message").show(300).delay(5000).hide(300);
		}
		if (updateResult == 'false') {
			$("#message").html("<font color=red> Edit User Failed ! " + updateErrmsg + " </font>");
			$("#div_message").show(300).delay(5000).hide(300);
		}
	}
}

$(function() {

	switch (usertype) {
	case "1":
		$('#usertype').text("User");
		break;
	case "2":
		$('#usertype').text("Master");
		break;
	case "3":
		$('#usertype').text("Admin");
		break;
	case "4":
		$('#usertype').text("SuperAdmin");
		break;
	case "5":
		$('#usertype').text("Guest");
		break;
	}
	switch (gender) {
	case "1":
		$('#gender').text("Male");
		break;
	case "2":
		$('#gender').text("Female");
		break;
	}
});

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

					$("#message").html("<font color=green> (ID:"+ userid +") Status Update Success ! </font>");
					$("#div_message").show(300).delay(3000).hide(300);
				} else {
					$("#message").html("<font color=red> (ID:"+ userid +") Status Update Failed ! " + data.errmsg + "</font>");
					$("#div_message").show(300).delay(3000).hide(300);
					$('#' + idname).val(status);
				}
			}
		});
	} else {
		$('#' + idname).val(status);
	}

}

function checkUserSkill(skillCode, idname, callback){
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_selectJson.action",
		data : {
			"token" : token,
			"referdoc_id" : skillCode
		},
		success : function(data) {
			if (data.result) {
				$("#" + idname).append(data.referdoc.referdoc_type + " ");
			}
			if (typeof (callback) == "function") {
				callback();
			}
		}
	});
}

function showSkill(skillValue, idname) {
	var tempskillvalue = skillValue.split(";");
	if (tempskillvalue.length == 0) {
		return;
	} else {
		var doskillvalue = tempskillvalue[(tempskillvalue.length - 1)];
		checkUserSkill(doskillvalue, idname, function(){
			if (tempskillvalue.length == 1) {
				return;
			} else {
				var tempdoskillvalue = ";" + doskillvalue;
				showSkill(skillValue.split(tempdoskillvalue)[0], idname);
			}
		});
	}
}
function checkPendingSkill(skillCode, idname, callback){
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_selectJson.action",
		data : {
			"token" : token,
			"referdoc_id" : skillCode
		},
		success : function(data) {
			if (data.result) {
				$("#" + idname).text(">> " + data.referdoc.referdoc_type + " <<");
			}
			if (typeof (callback) == "function") {
				callback();
			}
		}
	});
}
function showPendingSkill() {
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/user_getUserSkillByUserIdJson.action",
		data : {
			"token" : token,
			"userid" : userid
		},
		success : function(data) {
			if (data.result) {
				// 拿出列表
				var skilllist = data.skilllist;
				var count = 0;
				$.each(skilllist, function(i, n) {
					if (n.user_skill_status == 0) {
						var row = $("#template").clone();
						var fullurl = n.user_skill_url.split(";");
						if(fullurl.length > 0){
							for (var j = 0; j < fullurl.length; j++) {
								var tempPSkill = $("#PSkill").clone();
								tempPSkill.attr("href", fullurl[j]);// 改变绑定好数据的行的id
								tempPSkill.attr("data-lightbox", "pendingSkill_" + n.user_skill);// 改变绑定好数据的行的id
								tempPSkill.attr("id", "pendingSkill_" + n.user_skill + "_" + j);
								if (j > 0) {
									tempPSkill.attr("display","none");
								}
								tempPSkill.appendTo(row);
							}
						}
						row.find("#skillStatusOpr").val(n.user_skill_status);
						row.find("#skillStatusOpr").attr("id", "skillStatusOpr_" + n.userskill_id);
						row.attr("id", "template_" + n.userskill_id);
						row.appendTo("#pendingSkill");// 添加到模板的容器中
						checkPendingSkill(n.user_skill, "pendingSkill_" + n.user_skill + "_0", function(){
						row.toggle(300);
						});
						count++;
					}
				});
				if (count == 0) {
					$("#pendingSkill").text("無");
				}
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

function updateSkillStatus(idname, value) {
	var skillid = idname.split("_")[1];
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
					$("#message").html("<font color=green> (ID:"+ skillid +") Status Update Success ! </font>");
					$("#div_message").show(300).delay(5000).hide(300);
					setTimeout(function() {
						window.location.reload();
					}, 2000); // how long do you want the delay to be?
				} else {
					$("#message").html("<font color=red> (ID:"+ skillid +") Status Update Failed ! " + data.errmsg + "</font>");
					$("#div_message").show(300).delay(5000).hide(300);
				}
			}
		});
	} else {
		alert("Status Update Failed ! " + data.errmsg);
	}
}
