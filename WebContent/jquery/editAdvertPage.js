////文件上传
//jQuery(function() {
//    var $ = jQuery,
//        $list = $('#thelist'),
//        $btn = $('#ctlBtn'),
//        state = 'pending',
//        uploader;
//
//    uploader = WebUploader.create({
//
//        // 不压缩image
//        resize: false,
//
//        // swf文件路径
//        swf: BASE_URL + '/js/Uploader.swf',
//
//        // 文件接收服务端。
//        server: 'http://webuploader.duapp.com/server/fileupload.php',
//
//        // 选择文件的按钮。可选。
//        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
//        pick: '#picker'
//    });
//
//    // 当有文件添加进来的时候
//    uploader.on( 'fileQueued', function( file ) {
//        $list.append( '<div id="' + file.id + '" class="item">' +
//            '<h4 class="info">' + file.name + '</h4>' +
//            '<p class="state">等待上传...</p>' +
//        '</div>' );
//    });
//
//    // 文件上传过程中创建进度条实时显示。
//    uploader.on( 'uploadProgress', function( file, percentage ) {
//        var $li = $( '#'+file.id ),
//            $percent = $li.find('.progress .progress-bar');
//
//        // 避免重复创建
//        if ( !$percent.length ) {
//            $percent = $('<div class="progress progress-striped active">' +
//              '<div class="progress-bar" role="progressbar" style="width: 0%">' +
//              '</div>' +
//            '</div>').appendTo( $li ).find('.progress-bar');
//        }
//
//        $li.find('p.state').text('上传中');
//
//        $percent.css( 'width', percentage * 100 + '%' );
//    });
//
//    uploader.on( 'uploadSuccess', function( file ) {
//        $( '#'+file.id ).find('p.state').text('已上传');
//    });
//
//    uploader.on( 'uploadError', function( file ) {
//        $( '#'+file.id ).find('p.state').text('上传出错');
//    });
//
//    uploader.on( 'uploadComplete', function( file ) {
//        $( '#'+file.id ).find('.progress').fadeOut();
//    });
//
//    uploader.on( 'all', function( type ) {
//        if ( type === 'startUpload' ) {
//            state = 'uploading';
//        } else if ( type === 'stopUpload' ) {
//            state = 'paused';
//        } else if ( type === 'uploadFinished' ) {
//            state = 'done';
//        }
//
//        if ( state === 'uploading' ) {
//            $btn.text('暂停上传');
//        } else {
//            $btn.text('开始上传');
//        }
//    });
//
//    $btn.on( 'click', function() {
//        if ( state === 'uploading' ) {
//            uploader.stop();
//        } else {
//            uploader.upload();
//        }
//    });
//});

//
//// bigPhoto图片上传
//jQuery(function() {
//    var $ = jQuery,
//        $list = $('#bigPhoto'),
//        // 优化retina, 在retina下这个值是2
//        ratio = window.devicePixelRatio || 1,
//
//        // 缩略图大小
//        thumbnailWidth = 400 * ratio,
//        thumbnailHeight = 200 * ratio,
//
//        // Web Uploader实例
//        uploader;
//
//    // 初始化Web Uploader
//    uploader = WebUploader.create({
//
//        // 自动上传。
//        auto: true,
//
//        // swf文件路径
//        swf: BASE_URL + '/page/WebUploader/Uploader.swf',
//
//        // 文件接收服务端。
//        server: BASE_URL + '/upload_advertUploadJson.action',
//
//        // 选择文件的按钮。可选。
//        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
//        pick: '#bigPhotoPicker',
//        
//        // 设置文件上传域的name.
//        fileVal: 'upload',
//        
//        // 上传时的自定义参数(token).
//        formData: {
//            token: token
//        },
//        
//        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
//        resize: false,
//        
//        // 只允许选择文件，可选。
//        accept: {
//            title: 'Images',
//            extensions: 'gif,jpg,jpeg,bmp,png',
//            mimeTypes: 'image/*'
//        }
//    });
//
//    // 当有文件添加进来的时候
//    uploader.on( 'fileQueued', function( file ) {
//        var $li = $(
//                '<div id="' + file.id + '" class="file-item thumbnail">' +
//                    '<img>' +
//                    '<div class="info">' + file.name + '</div>' +
//                '</div>'
//                ),
//            $img = $li.find('img');
//
//        $list.append( $li );
//
//        // 创建缩略图
//        uploader.makeThumb( file, function( error, src ) {
//            if ( error ) {
//                $img.replaceWith('<span>不能预览</span>');
//                return;
//            }
//
//            $img.attr( 'src', src );
//            $("#bigPhotoImgTemp").remove();
//        }, thumbnailWidth, thumbnailHeight );
//    });
//
//    // 文件上传过程中创建进度条实时显示。
//    uploader.on( 'uploadProgress', function( file, percentage ) {
//        var $li = $( '#'+file.id ),
//            $percent = $li.find('.progress span');
//
//        // 避免重复创建
//        if ( !$percent.length ) {
//            $percent = $('<p class="progress"><span></span></p>')
//                    .appendTo( $li )
//                    .find('span');
//        }
//
//        $percent.css( 'width', percentage * 100 + '%' );
//    });
//    
//    // 检测文件上传后服务器返回的数据。
//    uploader.on( 'uploadAccept', function( file, response ) {
//        if ( !response.result ) {
//            // 通过return false来告诉组件，此文件上传有错。
//            return false;
//        }
//    });
//    
//    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
//    uploader.on( 'uploadSuccess', function( file, response ) {
//        $( '#'+file.id ).addClass('upload-state-done');
//        $( '#bigPhotoPicker' ).remove();
//        $( '#advertisement_bigphoto_url' ).val(response.picurl);
//    });
//
//    // 文件上传失败，现实上传出错。
//    uploader.on( 'uploadError', function( file, reason ) {
//        var $li = $( '#'+file.id ),
//            $error = $li.find('div.error');
//
//        // 避免重复创建
//        if ( !$error.length ) {
//            $error = $('<div class="error"></div>').appendTo( $li );
//        }
//        $error.text("Failed");
//    });
//
//    // 完成上传完了，成功或者失败，先删除进度条。
//    uploader.on( 'uploadComplete', function( file ) {
//        $( '#'+file.id ).find('.progress').remove();
//    });
//});
//
//// smallPhoto图片上传
//jQuery(function() {
//	var $ = jQuery,
//	$list = $('#smallPhoto'),
//	// 优化retina, 在retina下这个值是2
//	ratio = window.devicePixelRatio || 1,
//	
//	// 缩略图大小
//	thumbnailWidth = 100 * ratio,
//	thumbnailHeight = 100 * ratio,
//	
//	// Web Uploader实例
//	uploader;
//	
//	// 初始化Web Uploader
//	uploader = WebUploader.create({
//		
//		// 自动上传。
//		auto: true,
//		
//		// swf文件路径
//		swf: BASE_URL + '/page/WebUploader/Uploader.swf',
//		
//		// 文件接收服务端。
//		server: BASE_URL + '/upload_advertUploadJson.action',
//		
//		// 选择文件的按钮。可选。
//		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
//		pick: '#smallPhotoPicker',
//		
//		// 设置文件上传域的name.
//		fileVal: 'upload',
//		
//        // 上传时的自定义参数(token).
//        formData: {
//            token: token
//        },
//        
//		// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
//		resize: false,
//		
//		// 只允许选择文件，可选。
//		accept: {
//			title: 'Images',
//			extensions: 'gif,jpg,jpeg,bmp,png',
//			mimeTypes: 'image/*'
//		}
//	});
//	
//	// 当有文件添加进来的时候
//	uploader.on( 'fileQueued', function( file ) {
//		var $li = $(
//				'<div id="' + file.id + '" class="file-item thumbnail">' +
//				'<img>' +
//				'<div class="info">' + file.name + '</div>' +
//				'</div>'
//		),
//		$img = $li.find('img');
//		
//		$list.append( $li );
//		
//		// 创建缩略图
//		uploader.makeThumb( file, function( error, src ) {
//			if ( error ) {
//				$img.replaceWith('<span>不能预览</span>');
//				return;
//			}
//			
//			$img.attr( 'src', src );
//			$("#smallPhotoImgTemp").remove();
//		}, thumbnailWidth, thumbnailHeight );
//	});
//	
//	// 文件上传过程中创建进度条实时显示。
//	uploader.on( 'uploadProgress', function( file, percentage ) {
//		var $li = $( '#'+file.id ),
//		$percent = $li.find('.progress span');
//		
//		// 避免重复创建
//		if ( !$percent.length ) {
//			$percent = $('<p class="progress"><span></span></p>')
//			.appendTo( $li )
//			.find('span');
//		}
//		
//		$percent.css( 'width', percentage * 100 + '%' );
//	});
//	
//	// 检测文件上传后服务器返回的数据。
//	uploader.on( 'uploadAccept', function( file, response ) {
//		if ( !response.result ) {
//			// 通过return false来告诉组件，此文件上传有错。
//			return false;
//		}
//	});
//	
//	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
//	uploader.on( 'uploadSuccess', function( file, response ) {
//		$( '#'+file.id ).addClass('upload-state-done');
//		$( '#smallPhotoPicker' ).remove();
//		$( '#advertisement_smallphoto_url' ).val(response.picurl);
//	});
//	
//	// 文件上传失败，现实上传出错。
//	uploader.on( 'uploadError', function( file ) {
//		var $li = $( '#'+file.id ),
//		$error = $li.find('div.error');
//		
//		// 避免重复创建
//		if ( !$error.length ) {
//			$error = $('<div class="error"></div>').appendTo( $li );
//		}
//		
//		$error.text('Failed');
//	});
//	
//	// 完成上传完了，成功或者失败，先删除进度条。
//	uploader.on( 'uploadComplete', function( file ) {
//		$( '#'+file.id ).find('.progress').remove();
//	});
//});

//实例化UEditor编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var ue = UE.getEditor('editor');

//拿一级项技能表
function getParentSkillList(idname, parentCode) {
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
					if(parentCode != '' && parentCode != undefined && parentCode == data.parentreferdoclist[j].referdoc_id){
						$("#" + idname).val(data.parentreferdoclist[j].referdoc_id);
					}
				}
			} else {
				alert(data.errmsg);
			}
		}
	});
}

//拿技能表
function getChildSkillList(parentcode, childcode) {
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

function onload(){
	
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
		getParentSkillList("skillParentCode", parentCode);
		getChildSkillList(parentCode, advertType);
	}
}

function validate() {
	var ADContent = UE.getEditor('editor').getContent();
	$( '#advertisement_content' ).val(ADContent);
	
	
	var objs2 = document.getElementsByName('skillcodepart');
	for(var i=0;i<objs2.length;i++){
		if (objs2[i].type == 'radio'){
			if(objs2[i].checked == true){
				document.getElementById("advertisement_type").value = objs2[i].value;
				break;
			}
		}
	}
	return true;
}