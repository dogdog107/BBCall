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
        server: BASE_URL + '/upload_skillUploadJson.action',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#bigPhotoPicker',
        
        // 设置文件上传域的name.
        fileVal: 'upload',
        
        // 上传时的自定义参数(token).
        formData: {
            token: token,
            userid: userid
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
            $("#bigPhotoImgTemp").remove();
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
        $( '#bigPhotoPicker' ).remove();
        $( '#skillurl' ).val(response.picurl);
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
    });
});

//拿一级项技能表
function getParentSkillList(idname) {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_getparentlistJson.action",
		success : function(data) {
			if (data.result) {
				for ( var j = 0; j < data.parentreferdoclist.length; j++) {
					document.getElementById(idname).options
							.add(new Option(
									data.parentreferdoclist[j].referdoc_type,
									data.parentreferdoclist[j].referdoc_id));
				}
			} else {
				alert(data.errmsg);
			}
		}
	});
}

//拿技能表
function getChildSkillList(parentcode) {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_getchildlistJson.action",
		data : {
			"referdoc_parentno" : parentcode
		},
		success : function(data) {
			if (data.result) {
				var $childskill = $("#skillChildList");
				$childskill.hide().text(" ");
				for ( var j = 0; j < data.referdoclist.length; j++) {
					$('<label><input name="skillcodepart" id="skillcodepartid_' + data.referdoclist[j].referdoc_id + '" type="radio" value="' + data.referdoclist[j].referdoc_id + '" />' + data.referdoclist[j].referdoc_type + '&nbsp;&nbsp;</label>').appendTo($childskill);
				}
				$childskill.show(300);
			} else {
				alert(data.errmsg);
			}
		}
	});
}

function onload(){
	getParentSkillList("skillParentCode");
}

function validateAdd() {
	var objs2 = document.getElementsByName('skillcodepart');
	for(var i=0;i<objs2.length;i++){
		if (objs2[i].type == 'radio'){
			if(objs2[i].checked == true){
				document.getElementById("skillcode").value = objs2[i].value;
				break;
			}
		}
	}
	if ($("#skillcode").val() == '' || $("#skillcode").val() == null) {
		alert("skillcode is empty, please varify!");
		return false;
	}
	if ($("#token").val() == '' || $("#token").val() == null) {
		alert("token is empty, please varify!");
		return false;
	}
	if ($("#userid").val() == '' || $("#userid").val() == null) {
		alert("userid is empty, please varify!");
		return false;
	}
	if ($("#skillurl").val() == '' || $("#skillurl").val() == null) {
		alert("userid is empty, please varify!");
		return false;
	}
	$('#addSkill_form').attr('action', 'user_addUserSkill');
	return true;
}
function validateUpdate() {
	var objs2 = document.getElementsByName('skillcodepart');
	for(var i=0;i<objs2.length;i++){
		if (objs2[i].type == 'radio'){
			if(objs2[i].checked == true){
				document.getElementById("skillcode").value = objs2[i].value;
				break;
			}
		}
	}
	if ($("#skillcode").val() == '' || $("#skillcode").val() == null) {
		alert("skillcode is empty, please varify!");
		return false;
	}
	if ($("#token").val() == '' || $("#token").val() == null) {
		alert("token is empty, please varify!");
		return false;
	}
	if ($("#userid").val() == '' || $("#userid").val() == null) {
		alert("userid is empty, please varify!");
		return false;
	}
	if ($("#skillurl").val() == '' || $("#skillurl").val() == null) {
		alert("userid is empty, please varify!");
		return false;
	}
	
	$('#addSkill_form').attr('action', 'user_updateUserSkill');
	return true;
}