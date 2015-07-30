function showaddresslist(times, childcode, parentcode) {
	if (times < 1) {
		if ($("#adscode_3").val() == "0") {
			updateAddressCodeName(2);
		} else {
			updateAddressCodeName(3);
		}
		return;
	}
	var idname = 'adscode_' + times;
	$.ajax({
				type : "post",
				url : "${pageContext.request.contextPath}/address_checkChildAdsListJson.action",
				data : {
					"addresscode" : parentcode,
					"token" : token
				},
				success : function(data) {
					if (data.result) {
						for (var j = 0; j < data.addresslist.length; j++) {
							document.getElementById(idname).options
									.add(new Option(
											data.addresslist[j].areaname,
											data.addresslist[j].areano));
						}
						$("#" + idname).val(childcode);
						times--;
						childcode = parentcode;
						if (times == 1) {
							parentcode = 0;
						} else {
							parentcode = parseInt(parentcode / 10000) * 10000;
						}
						showaddresslist(times, childcode, parentcode);
					} else {
						alert(data.errmsg);
						document.getElementById(idname).style.display = "none";
					}
				}
			});
}


//拿技能表
function getChildSkillList(parentcode, idname, skillvalue, callback) {
	var idno = idname.split("_")[1];
	var childListDiv = "skillChildList_" + idno; 
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_getchildlistJson.action",
		data : {
			"referdoc_parentno" : parentcode,
			"token" : token
		},
		success : function(data) {
			if (data.result) {
				var $childskill = $("#" + childListDiv);
				$childskill.hide().text(" ");
				for ( var j = 0; j < data.referdoclist.length; j++) {
					$('<label><input name="skillcodepart" id="skillcodepartid_' + data.referdoclist[j].referdoc_id + '" type="checkbox" value="' + data.referdoclist[j].referdoc_id + '" />' + data.referdoclist[j].referdoc_type + '&nbsp;&nbsp;</label>').appendTo($childskill);
				}
				if (skillvalue != '' && skillvalue != undefined){
					var skillpartid = "skillcodepartid_" + skillvalue;
					$childskill.find("#" + skillpartid).attr("checked", true);
				}
				$childskill.show(300);
			} else {
				alert(data.errmsg);
			}
			if (typeof (callback) == "function") {
				callback();
			}
		}
	});
}

//拿一级项技能表
function getParentSkillList(idname, skillvalue, callback) {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_getparentlistJson.action",
		data : {
			"token" : token
		},
		success : function(skilldata) {
			if (skilldata.result) {
				for ( var j = 0; j < skilldata.parentreferdoclist.length; j++) {
					document.getElementById(idname).options
							.add(new Option(
									skilldata.parentreferdoclist[j].referdoc_type,
									skilldata.parentreferdoclist[j].referdoc_id));
				}
				if(skillvalue != '' && skillvalue != undefined){
					$("#" + idname).val(skillvalue);
				}
			} else {
				alert(skilldata.errmsg);
			}
			if (typeof (callback) == "function") {
				callback();
			}
		}
	});
}

function addSkill(parentCode, callback) {
	var objs = document.getElementsByName('skillpart');
	var objlength = objs.length + 1;
	while (document.getElementById("skillpart_" + objlength) != null) {
		objlength++;
	}

	var row = $("#skill_template").clone();
	row.find("#skillParentCode").attr("id", "skillParentCode_" + objlength);
	row.find("#deleteSkill").attr("onclick", "deleteSkillbtn(this.id)");
	row.find("#deleteSkill").attr("id", "deleteSkill_" + objlength);
	row.find("#skillChildList").attr("id", "skillChildList_" + objlength);
	row.attr("name", "skillpart");
	row.attr("id", "skillpart_" + objlength);
	row.appendTo("#skill_main");// 添加到模板的容器中
	row.toggle(300);
	getParentSkillList("skillParentCode_" + objlength, parentCode, function() {
		if (typeof (callback) == "function") {
			callback("skillParentCode_" + objlength);
		}
//		if (typeof (callback) == "function") {
//			callback(parentCode, "skillParentCode_" + objlength, childCode);
//		}
	});
}

function showskill(skillValue) {
	var tempskillvalue = skillValue.split(";");
	if (tempskillvalue.length == 0) {
		return;
	} else {
		var doskillvalue = tempskillvalue[(tempskillvalue.length - 1)];
		var doParentCode = parseInt(doskillvalue / 1000) * 1000;
		addSkill(doParentCode, function(ParentCodeID){
			getChildSkillList(doParentCode, ParentCodeID, doskillvalue, function(){
				if (tempskillvalue.length == 1) {
					return;
				} else {
					var tempdoskillvalue = ";" + doskillvalue;
					showskill(skillValue.split(tempdoskillvalue)[0]);
				}
			});
		});
//		addSkill(doParentCode, getChildSkillList, doskillvalue);
	}
}

function deleteSkillbtn(idname) {
	var idno = idname.split("_")[1];
	var deletename = "skillpart_" + idno;
	$("#" + deletename).hide(300);
	setTimeout(function() {
		$("#" + deletename).remove();
	}, 300); // how long do you want the delay to be?
}

function updateAddressCodeName(idno) {
	var namevalue = "";
	var idname = "";
	for ( var i = idno; i >= 1; i--) {
		idname = 'adscode_' + i;
		var selectIndex = document.getElementById(idname).selectedIndex;
		namevalue = document.getElementById(idname).options[selectIndex].text
				+ ";" + namevalue;
	}
	document.getElementById("addresscodename").value = namevalue;
}

function getaddresslist(parentcode, idno) {
	updateAddressCodeName(idno);
	document.getElementById("addresscode").value = parentcode;
	if (idno == 3) {
		return;
	}
	idno++;
	var idname = 'adscode_' + idno;
	if (idno == 2) {
		document.getElementById(idname).options.length = 1;
		var idnameT = 'adscode_' + (idno + 1);
		document.getElementById(idnameT).options.length = 1;
	} else {
		document.getElementById(idname).options.length = 1;
	}
	$.ajax({
				type : "post",
				url : "${pageContext.request.contextPath}/address_checkChildAdsListJson.action",
				data : {
					"addresscode" : parentcode,
					"token" : token
				},
				success : function(data) {
					if (data.result) {
						document.getElementById(idname).style.display = "";
						for ( var j = 0; j < data.addresslist.length; j++) {
							document.getElementById(idname).options
									.add(new Option(
											data.addresslist[j].areaname,
											data.addresslist[j].areano));
						}
					} else {
						alert(data.errmsg);
						document.getElementById(idname).style.display = "none";
					}
				}
			});
}

function checkpwd(id) {
	childobj = document.getElementById(id);
	if (childobj.value != document.getElementById('prepassword').value) {
		childobj.value = '';
		childobj.style.borderColor = 'red';
		document.getElementById('pwdnotice').innerHTML = '兩次的密碼不一致！請重新輸入。';
		document.getElementById('pwdnotice').style.color = 'red';
	} else {
		childobj.style.borderColor = '#00e500';
		document.getElementById('pwdnotice').innerHTML = '密碼正確。';
		document.getElementById('pwdnotice').style.color = 'green';
	}
	return;
}

$(document).ready(function() {
	$('#upload').live('change', function() {
		if (confirm('確定要修改頭像嗎？\n Confirm to change the photo?')) {
			$('#update_form').attr('action', 'upload_userUploadById').submit();
		} else {
			$('#upload').val("");
		}
	});
});  

function checkAGList(){
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/access_getAccessGroupJson.action",
		data : {
			"token" : token
		},
		success : function(data) {
			if (data.result) {
				var accessGroupList = data.accessGroupList;
				var accessgroupid = $("#accessgroup");
				$.each(accessGroupList, function(i, n) {
					var row = $("<option value='"+ n.accessgroup_name + "' title='" + n.accessgroup_description + "'>"+ n.accessgroup_name + "</option>");
					accessgroupid.append(row);
				});
				accessgroupid.val(accessgroup);
			} else {
				$("#message").html(
						"<font color=red>Page Fail ! " + data.errmsg
								+ "</font>");
				$("#div_message").show(300).delay(10000).hide(300);
			}
		}
	});
}
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
	
	$("#user_photo").attr("src", photourl + "?" + Math.random());
	
//	if (usertype == 1 || usertype == 2) {
//		document.getElementById('usertype').options[value = '3'].remove();
//	}

	if (updateResult != '') {
		if(updateResult == 'true'){
			$("#message").html("<font color=green> Edit User Success! </font>");
			$("#div_message").show(300).delay(5000).hide(300);
		}
		if (updateResult == "false") {
			$("#message").html("<font color=red> Edit User Failed ! Access Reject. </font>");
			$("#div_message").show(300).delay(5000).hide(300);
		}
	}
	if (usertype == 3) {
		$("#userid_tr").show();
	}
//	if (usertype == 3) {
//		document.getElementById('userid').disabled = "";
//		document.getElementById('token').value = "";
//	}
	if (usertype != '') {
		document.getElementById('usertype')[usertype].selected = true;
	}
	
	if (gender != '') {
		document.getElementById('gender')[gender].selected = true;
	}
	if (accessgroup != '') {
		checkAGList();
	}

	if (language != '') {
		var lgestr = language.split(";");
		for ( var i = 0; i < lgestr.length; i++) {
			document.getElementById(lgestr[i]).checked = true;
		}
	}

	if (skill != '') {
		showskill(skill);
	} else {
		addSkill();
	}
	
	$(function() {
		if (addresscode != '' && addresscode != 0) {
			$.post(
							"${pageContext.request.contextPath}/address_checkAdsListJson.action",
							{
								"addresscode" : addresscode
							},
							function(data) {
								if (data.result) {
									if (data.addresslist[0].arealevel < 3) {
										document.getElementById('adscode_3').style.display = "none";
									}
									showaddresslist(
											data.addresslist[0].arealevel,
											addresscode,
											data.addresslist[0].parentno);
								} else {
									alert(data.errmsg);
								}
							});
		} else {
			$.post(
							"${pageContext.request.contextPath}/address_checkChildAdsListJson.action",
							{
								"addresscode" : 0
							},
							function(data) {
								if (data.result) {
									for (var i = 0; i < data.addresslist.length; i++) {
										document.getElementById('adscode_1').options
												.add(new Option(
														data.addresslist[i].areaname,
														data.addresslist[i].areano));
									}
								} else {
									alert(data.errmsg);
								}
							});
		}
	});
}

function validate() {
	addresscodename = document.getElementById("addresscodename").value;
	if (addresscodename != "") {
		document.getElementById("address").value = addresscodename
				+ document.getElementById("lastads").value;
	}
	
	var objs = document.getElementsByName('languagepart');
	var languagestr='';
	for(var i=0;i<objs.length;i++){
		if (objs[i].type == 'checkbox'){
			if(objs[i].checked == true){
				if(i == 0){
					languagestr = objs[i].value;
				}else{
					languagestr = objs[i].value + ';' + languagestr;
				}
			}
		}
	}
	document.getElementById("language").value = languagestr;
	
	var objs2 = document.getElementsByName('skillcodepart');
	var skillstr='';
	for(var i=0;i<objs2.length;i++){
		if (objs2[i].type == 'checkbox'){
			if(objs2[i].checked == true){
				if(i == 0){
					skillstr = objs2[i].value;
				}else{
					var skillstrTemp = skillstr.split(";");
					var count = 0;
					for(var j=0; j<skillstrTemp.length; j++){
						if (skillstrTemp[j] == objs2[i].value) {
							count++;
						}
					}
					if (count == 0){
						if (skillstr == '') {
							skillstr = objs2[i].value;
						} else {
							skillstr = objs2[i].value + ';' + skillstr;
						}
					}
				}
			}
		}
	}
	document.getElementById("skill").value = skillstr;
	
	return true;
}