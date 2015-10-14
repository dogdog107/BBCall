var orgBigPic = "";
var orgSmallPic = "";

// bigPhoto图片上传
jQuery(function() {
    var $ = jQuery,
        $list = $('#bigPhoto'),
        // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,

        // 缩略图大小
        thumbnailWidth = 400 * ratio,
        thumbnailHeight = 200 * ratio,

        // Web Uploader实例
        uploader;

    // 初始化Web Uploader
    uploader = WebUploader.create({

        // 自动上传。
        auto: true,

        // swf文件路径
        swf: BASE_URL + '/page/WebUploader/Uploader.swf',

        // 文件接收服务端。
        server: BASE_URL + '/upload_advertUploadJson.action',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#bigPhotoPicker',
        
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
        if ($("#bigPhotoImg").length > 0) {
        	deltefiles($( '#advertisement_bigphoto_url' ).val());
        	$("#bigPhotoImg").remove();
        }
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
            if ($("#bigPhotoImgTemp").length > 0) {
            	orgBigPic = $( '#bigPhotoImgTemp' ).attr("src");
            	$("#bigPhotoImgTemp").remove();
            }
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
        $( '#bigPhotoPickerValue' ).text("修改圖片");
        $( '#advertisement_bigphoto_url' ).val(response.picurl);
    });

    // 文件上传失败，现实上传出错。
    uploader.on( 'uploadError', function( file, reason ) {
        var $li = $( '#'+file.id ),
            $error = $li.find('div.error');

        // 避免重复创建
        if ( !$error.length ) {
            $error = $('<div class="error"></div>').appendTo( $li );
        }
        $error.text("Failed");
    });

    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').remove();
        $( '#'+file.id ).attr("id", "bigPhotoImg");
    });
});

// smallPhoto图片上传
jQuery(function() {
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
		server: BASE_URL + '/upload_advertUploadJson.action',
		
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
        if ($("#smallPhotoImg").length > 0) {
        	deltefiles($( '#advertisement_smallphoto_url' ).val());
        	$("#smallPhotoImg").remove();
        }
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
			if($("#smallPhotoImgTemp").length > 0) {
				orgSmallPic = $( '#smallPhotoImgTemp' ).attr("src");
				$("#smallPhotoImgTemp").remove();
			}
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
		$( '#smallPhotoPickerValue' ).text("修改圖片");
		$( '#advertisement_smallphoto_url' ).val(response.picurl);
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
		$( '#'+file.id ).attr("id", "smallPhotoImg");
	});
});

//实例化UEditor编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var ue = UE.getEditor('editor');
ue.ready(function(){
    ue.focus();
    ue.setContent(content);
})
//
////拿一级项技能表
//function getParentSkillList(idname, parentCode) {
//	$.ajax({
//		type : "post",
//		url : "${pageContext.request.contextPath}/referdoc_getparentlistJson.action",
//		success : function(data) {
//			if (data.result) {
//				for ( var j = 0; j < data.parentreferdoclist.length; j++) {
//					document.getElementById(idname).options
//							.add(new Option(
//									data.parentreferdoclist[j].referdoc_type,
//									data.parentreferdoclist[j].referdoc_id));
//					if(parentCode != '' && parentCode != undefined && parentCode == data.parentreferdoclist[j].referdoc_id){
//						$("#" + idname).val(data.parentreferdoclist[j].referdoc_id);
//					}
//				}
//			} else {
//				alert(data.errmsg);
//			}
//		}
//	});
//}
//
//拿技能表
function getChildSkillList(parentcode, childcode) {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_getchildlistJson.action",
		data : {
			"referdoc_parentno" : parentcode,
			"token" : token
		},
		success : function(data) {
			if (data.result) {
				var $childskill = $("#skillChildList");
				$childskill.hide().text(" ");
				for ( var j = 0; j < data.referdoclist.length; j++) {
					$('<label><input name="skillcodepart" id="skillcodepartid_' + data.referdoclist[j].referdoc_id + '" type="radio" value="' + data.referdoclist[j].referdoc_id + '" />' + data.referdoclist[j].referdoc_type + '&nbsp;&nbsp;</label>').appendTo($childskill);
					if(childcode != '' && childcode != undefined && childcode == data.referdoclist[j].referdoc_id){
						var tempIdName = "skillcodepartid_" + data.referdoclist[j].referdoc_id;
						$("#" + tempIdName).attr("checked","checked"); 
					}
				}
				$childskill.show(300);
			} else {
				alert(data.errmsg);
			}
		}
	});
}

function deltefiles(filePath){
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/upload_fileDeleteJson.action",
		data : {
			"picurl" : filePath,
			"token" : token
		},
		success : function(data) {
			if (data.result) {
			} else {
				alert(data.errmsg);
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


function onload(){
	if (token == "" || token == null) {
		if (confirm('Session has been expired! Please re-login again.\n Click "OK" to return login page.')) {
			window.parent.frames.location.href="./login.jsp";
		}
		$("#message").html("<font color=red> Session has been expired! Please re-login again. </font>");
		$("#div_main").hide(300);
		$("#div_message").show(300).delay(10000).hide(300);
		return;
	}
	
	if (updateResult != '') {
		if(updateResult == 'true'){
			$("#message").html("<font color=green> Edit Advertisement Success! </font>");
			$("#div_message").show(300).delay(5000).hide(300);
		}
		if (updateResult == "false") {
			$("#message").html("<font color=red> Edit Advertisement Failed ! Access Reject. </font>");
			$("#div_message").show(300).delay(5000).hide(300);
		}
	}
	
	if (advertType != '') {
		var parentCode = parseInt(advertType / 1000) * 1000;
		getParentSkillList("skillParentCode", parentCode, function(){
			getChildSkillList(parentCode, advertType);
		});
	}
}

function validate() {
	var ADContent = UE.getEditor('editor').getContent();
	
	// 临时修改所有图片的宽度 width="300px"
	var ADContentSplit = ADContent.split('<img ');
	for(var i = 0;i<ADContentSplit.length;i++){
		if(i != 0){
			if(ADContentSplit[i].indexOf('width=') == -1){
				var tempstr = ADContentSplit[i];
				ADContentSplit[i] = 'width="300px" ' + tempstr;
			}
		}
	}
	var ADContentConv = ADContentSplit.join('<img ');
	
	$( '#advertisement_content' ).val(ADContentConv);
	
	
	var objs2 = document.getElementsByName('skillcodepart');
	for(var i=0;i<objs2.length;i++){
		if (objs2[i].type == 'radio'){
			if(objs2[i].checked == true){
				document.getElementById("advertisement_type").value = objs2[i].value;
				break;
			}
		}
	}
	
	if ($("#advertisement_title").val() == '' || $("#advertisement_title").val() == null) {
		alert("Title is empty, please verify and sumbit again.");
		return false;
	}
	if ($("#advertisement_summary").val() == '' || $("#advertisement_summary").val() == null) {
		alert("Summary is empty, please verify and sumbit again.");
		return false;
	}
	if ($("#advertisement_type").val() == '' || $("#advertisement_type").val() == null) {
		alert("Advertisement type is empty, please verify and sumbit again.");
		return false;
	}
	if ($("#advertisement_bigphoto_url").val() == '' || $("#advertisement_bigphoto_url").val() == null) {
		alert("Big photo is empty, please verify and sumbit again.");
		return false;
	}
	if ($("#advertisement_smallphoto_url").val() == '' || $("#advertisement_smallphoto_url").val() == null) {
		alert("Small photo is empty, please verify and sumbit again.");
		return false;
	}
	if ($("#advertisement_content").val() == '' || $("#advertisement_content").val() == null) {
		alert("Advertisement content is empty, please verify and sumbit again.");
		return false;
	}
	
	if (orgBigPic != "") {
		deltefiles(orgBigPic);
	}
	
	if (orgSmallPic != "") {
		deltefiles(orgSmallPic);
	}
	
	return true;
}