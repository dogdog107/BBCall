/**
 * check username whether exist
 */
$(function() {
	$("#account").blur(function(){
		if (this.value != '') {
			if (this.value.toString().length < 6) {
				$("#checkAccountResult").hide();
				$("#checkAccountResult").html("<font color=red>Error: 用戶名少於6位數</font>").show(300);
				return;
			}
			$.post("${pageContext.request.contextPath}/user_checkUserNameJson.action",{"username": $(this).val()}, function(data){
				if(data.result){
					$("#checkAccountResult").hide();
					$("#checkAccountResult").html("<font color=green>用戶名可以使用</font>").show(300);
				}else{
					$("#checkAccountResult").hide();
					$("#checkAccountResult").html("<font color=red>Error: 用戶名已存在</font>").show(300);
				}
			});
		} else {
			$("#checkAccountResult").hide(300);
		}
	});
});

$(function() {
	$("#mobile").blur(function(){
		if (this.value != '') {
			if (isNaN(this.value.toString())) {
				$("#checkMobileResult").hide();
				$("#checkMobileResult").html("<font color=red>Error: 手機號碼只能為數字</font>").show(300);
				return;
			}
			if (this.value.toString().length < 8) {
				$("#checkMobileResult").hide();
				$("#checkMobileResult").html("<font color=red>Error: 手機號碼少於8位數</font>").show(300);
				return;
			}
			$.post("${pageContext.request.contextPath}/user_checkUserNameJson.action",{"username": $(this).val()}, function(data){
				if(data.result){
					$("#checkMobileResult").hide();
					$("#checkMobileResult").html("<font color=green>手機號碼可以使用</font>").show(300);
				}else{
					$("#checkMobileResult").hide();
					$("#checkMobileResult").html("<font color=red>Error: 手機號碼已被使用</font>").show(300);
				}
			});
		} else {
			$("#checkMobileResult").hide(300);
		}
	});
});
$(function() {
	$("#email").blur(function(){
		if (this.value != '') {
			if (this.value.toString().indexOf("@") < 0 || this.value.toString().indexOf(".") < 0) {
				$("#checkEmailResult").hide();
				$("#checkEmailResult").html("<font color=red>Error: 郵箱地址格式不正確</font>").show(300);
				return;
			}
			$.post("${pageContext.request.contextPath}/user_checkUserNameJson.action",{"username": $(this).val()}, function(data){
				if(data.result){
					$("#checkEmailResult").hide();
					$("#checkEmailResult").html("<font color=green>郵箱地址可以使用</font>").show(300);
				}else{
					$("#checkEmailResult").hide();
					$("#checkEmailResult").html("<font color=red>郵箱地址已被使用</font>").show(300);
				}
			});
		} else {
			$("#checkEmailResult").hide(300);
		}
	});
});

$(function() {
	$("#prepassword").blur(function() {
		if (this.value != '') {
			if (this.value.toString().length < 6) {
				$("#checkPrepasswordResult").hide();
				$("#checkPrepasswordResult").html("<font color=red>Error: 密碼不能少於6位數</font>").show(300);
				return;
			}
			$("#checkPrepasswordResult").hide(300);
			$("#repwd").hide().show(300);
		}
	});
});

function changeUserType(typevalue) {
	$("#table_main").hide();
	$("span[id^='td_']").text(" ");
	switch (typevalue) {
	case "1":
	case "3":
	case "4":
		break;
	case "2":
		$("#td_pic").text("*");
		$("#td_name").text("*");
		$("#td_email").text("*");
		$("#td_language").text("*");
		$("#td_skill").text("*");
		$("#td_mobile").text("*");
		$("#td_gender").text("*");
		$("#td_description").text("*");

		break;
	}
	$("#table_main").show(300);
	$("#smallPhotoPicker").remove();
	$("#tempPicker").html("<div id='smallPhotoPicker'>选择图片</div>");

	//smallPhoto图片上传
	$(function() {
		var $ = jQuery,
		$list = $('#smallPhoto'),
		// 优化retina, 在retina下这个值是2
		ratio = window.devicePixelRatio || 1,
		
		// 缩略图大小
		thumbnailWidth = 100 * ratio,
		thumbnailHeight = 100 * ratio,
		
		// Web Uploader实例
		uploader;
		// 初始化Web Uploader
		uploader = WebUploader.create({
			
			// 自动上传。
			auto: true,
			
			// swf文件路径
			swf: BASE_URL + '/page/WebUploader/Uploader.swf',
			
			// 文件接收服务端。
			server: BASE_URL + '/upload_userUploadJson.action',
			
			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick: '#smallPhotoPicker',
			
			// 设置文件上传域的name.
			fileVal: 'upload',
			
	     // 上传时的自定义参数(token).
	     formData: {
	         token: token
	     },
	     
			// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			resize: false,
			
			// 只允许选择文件，可选。
			accept: {
				title: 'Images',
				extensions: 'gif,jpg,jpeg,bmp,png',
				mimeTypes: 'image/*'
			}
		});
		// 当有文件添加进来的时候
		uploader.on( 'fileQueued', function( file ) {
			var $li = $(
					'<div id="' + file.id + '" class="file-item thumbnail">' +
					'<img>' +
					'<div class="info">' + file.name + '</div>' +
					'</div>'
			),
			$img = $li.find('img');
			
			$list.append( $li );
			
			// 创建缩略图
			uploader.makeThumb( file, function( error, src ) {
				if ( error ) {
					$img.replaceWith('<span>不能预览</span>');
					return;
				}
				
				$img.attr( 'src', src );
			}, thumbnailWidth, thumbnailHeight );
		});
		
		// 文件上传过程中创建进度条实时显示。
		uploader.on( 'uploadProgress', function( file, percentage ) {
			var $li = $( '#'+file.id ),
			$percent = $li.find('.progress span');
			
			// 避免重复创建
			if ( !$percent.length ) {
				$percent = $('<p class="progress"><span></span></p>')
				.appendTo( $li )
				.find('span');
			}
			
			$percent.css( 'width', percentage * 100 + '%' );
		});
		
		// 检测文件上传后服务器返回的数据。
		uploader.on( 'uploadAccept', function( file, response ) {
			if ( !response.result ) {
				// 通过return false来告诉组件，此文件上传有错。
				return false;
			}
		});
		
		// 文件上传成功，给item添加成功class, 用样式标记上传成功。
		uploader.on( 'uploadSuccess', function( file, response ) {
			$( '#'+file.id ).addClass('upload-state-done');
			$( '#smallPhotoPicker' ).remove();
			$( '#picurl' ).val(response.picurl);
		});
		
		// 文件上传失败，现实上传出错。
		uploader.on( 'uploadError', function( file ) {
			var $li = $( '#'+file.id ),
			$error = $li.find('div.error');
			
			// 避免重复创建
			if ( !$error.length ) {
				$error = $('<div class="error"></div>').appendTo( $li );
			}
			
			$error.text('Failed');
		});
		
		// 完成上传完了，成功或者失败，先删除进度条。
		uploader.on( 'uploadComplete', function( file ) {
			$( '#'+file.id ).find('.progress').remove();
		});
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
					"addresscode" : parentcode
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
						document.getElementById(idname).style.display = "none";
					}
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
				url : "${pageContext.request.contextPath}/address_checkChildAdsListJson.action",
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
			"referdoc_parentno" : parentcode
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
	
	if (registerResult == 'true'){
		$("#message").html("<font color=green> Register Success ! </font>");
		$("#span_message").html("<p align='center' style='font-size: 16px; color: green'>##添加成功！" + registerMsg + " ##</p>");
		$("#span_search").hide(300);
		$("#div_main").hide(300);
		$("#div_message").show(300).delay(5000).hide(300);
	}
	
	if (registerResult == 'false'){
		$("#message").html("<font color=red> Register Failed ! "  + registerMsg + "</font>");
		$("#span_message").html("<p align='center' style='font-size: 16px; color: red'>##添加失敗！" + registerMsg + " ##</p>");
		$("#div_message").show(300).delay(10000).hide(300);
	}
	
	addSkill();
	
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