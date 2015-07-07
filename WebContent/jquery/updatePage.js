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
				url : "${pageContext.request.contextPath}/user_checkChildAdsListJson.action",
				data : {
					"addresscode" : parentcode
				},
				success : function(data) {
					for ( var j = 0; j < data.addresslist.length; j++) {
						document.getElementById(idname).options.add(new Option(
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
				}
			});
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
				url : "${pageContext.request.contextPath}/user_checkChildAdsListJson.action",
				data : {
					"addresscode" : parentcode
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
		document.getElementById('pwdnotice').innerHTML = '两次的密码不一致！请重新输入。';
		document.getElementById('pwdnotice').style.color = 'red';
	} else {
		childobj.style.borderColor = '#00e500';
		document.getElementById('pwdnotice').innerHTML = '密码正确。';
		document.getElementById('pwdnotice').style.color = 'green';
	}
	return;
}

$(document).ready(function() {
	$('#upload').live('change', function() {
		if (confirm('確定要修改頭像嗎？\n Confirm to change the photo?')) {
			$('#update_form').attr('action', 'userUpload').submit();
		} else {
			$('#upload').val("");
		}
	});
});  

function onload() {
	$("#user_photo").attr("src", photourl + "?" + Math.random());
	
	if (usertype == 1 || usertype == 2) {
		document.getElementById('usertype').options[value = '3'].remove();
	}

	if (usertype == 3) {
		$("#userid_tr").show();
	}
//	if (usertype == 3) {
//		document.getElementById('userid').disabled = "";
//		document.getElementById('token').value = "";
//	}

	document.getElementById('usertype')[usertype].selected = true;
	document.getElementById('gender')[gender].selected = true;

	if (language != '') {
		var lgestr = language.split(";");
		for ( var i = 0; i < lgestr.length; i++) {
			document.getElementById(lgestr[i]).checked = true;
		}
	}
	$(function() {
		if (addresscode != '' && addresscode != 0) {
			$.post(
							"${pageContext.request.contextPath}/user_checkAdsListJson.action",
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
									alert("addresscode Error!");
								}
							});
		} else {
			$.post(
							"${pageContext.request.contextPath}/user_checkChildAdsListJson.action",
							{
								"addresscode" : 0
							},
							function(data) {
								for ( var i = 0; i < data.addresslist.length; i++) {
									document.getElementById('adscode_1').options
											.add(new Option(
													data.addresslist[i].areaname,
													data.addresslist[i].areano));
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
	
	return true;
}