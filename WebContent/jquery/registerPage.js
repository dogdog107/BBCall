/**
 * check username whether exist
 */

$(function() {
	$("#account").blur(function(){
		$.post("${pageContext.request.contextPath}/user_checkUserNameJson.action",{"username": $(this).val()}, function(data){
			if(data.checkUserNameResult){
				$("#checkUserNameResult").html("<font color=green>用戶名名可以使用</font>");
			}else{
				$("#checkUserNameResult").html("<font color=red>用戶名已存在</font>");
			}
		});
	});
});

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



//smallPhoto图片上传
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
	});
});