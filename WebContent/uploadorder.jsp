<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--WebUploader引入CSS-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/page/WebUploader/webuploader.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/orderPage.js">
	
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jquery/addorderPage.js">
	
</script>
<title>add order</title>
</head>

<body>
	<h1>Struts add order Page</h1>
	<s:form action="upload_orderUpload" theme="simple" method="post"
		enctype="multipart/form-data">
	
	orderid:<input id="orderid" name="orderid" value="6">
	
	order_pic_url1:<s:file name="uploadlist" label="文件1"></s:file>
		<br>
	order_pic_url2:<s:file name="uploadlist" label="文件2"></s:file>
		<br>
	order_pic_url3:<s:file name="uploadlist" label="文件3"></s:file>
		<br>
		<s:submit label="submit"></s:submit>
	</s:form>
	<table>
		<tr>
			<td>廣告大標圖 (AD Big Photo)</td>
			<td><input type="hidden" id="advertisement_bigphoto_url"
				name="advertisement_bigphoto_url" value="" /> <!--用来存放item-->
				<div id="bigPhoto" class="uploader-list">
					<img id="bigPhotoImgTemp" src='' height='200' width='400'>
				</div>
				<div id="bigPhotoPicker">选择图片</div></td>
		</tr>
	</table>
	<script type="text/javascript">
		// 添加全局站点信息
		var BASE_URL = '${pageContext.request.contextPath }';
		var token = '${sessionScope.user_token}';
	</script>
	<!--WebUploader引入JS-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/page/WebUploader/webuploader.js"></script>
	<script type="text/javascript">
		//bigPhoto图片上传
		jQuery(function() {
			var $ = jQuery, $list = $('#bigPhoto'),
			// 优化retina, 在retina下这个值是2
			ratio = window.devicePixelRatio || 1,

			// 缩略图大小
			thumbnailWidth = 400 * ratio, thumbnailHeight = 200 * ratio,

			// Web Uploader实例
			uploader;

			// 初始化Web Uploader
			uploader = WebUploader.create({

				// 自动上传。
				auto : true,

				// swf文件路径
				swf : BASE_URL + '/page/WebUploader/Uploader.swf',

				// 文件接收服务端。
				server : BASE_URL + '/upload_orderUploadJson.action',

				// 选择文件的按钮。可选。
				// 内部根据当前运行是创建，可能是input元素，也可能是flash.
				pick : '#bigPhotoPicker',

				// 设置文件上传域的name.
				fileVal : 'uploadlist',

				// 上传时的自定义参数(token).
				formData : {
					token : token,
					orderid : "12"
				},

				// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
				resize : false,

				// 只允许选择文件，可选。
				accept : {
					title : 'Images',
					extensions : 'gif,jpg,jpeg,bmp,png',
					mimeTypes : 'image/*'
				}
			});

			// 当有文件添加进来的时候
			uploader
					.on(
							'fileQueued',
							function(file) {
								var $li = $('<div id="' + file.id + '" class="file-item thumbnail">'
										+ '<img>'
										+ '<div class="info">'
										+ file.name + '</div>' + '</div>'), $img = $li
										.find('img');

								$list.append($li);

								// 创建缩略图
								uploader.makeThumb(file, function(error, src) {
									if (error) {
										$img.replaceWith('<span>不能预览</span>');
										return;
									}

									$img.attr('src', src);
									$("#bigPhotoImgTemp").remove();
								}, thumbnailWidth, thumbnailHeight);
							});

			// 文件上传过程中创建进度条实时显示。
			uploader.on('uploadProgress', function(file, percentage) {
				var $li = $('#' + file.id), $percent = $li
						.find('.progress span');

				// 避免重复创建
				if (!$percent.length) {
					$percent = $('<p class="progress"><span></span></p>')
							.appendTo($li).find('span');
				}

				$percent.css('width', percentage * 100 + '%');
			});

			// 检测文件上传后服务器返回的数据。
			uploader.on('uploadAccept', function(file, response) {
				if (!response.result) {
					// 通过return false来告诉组件，此文件上传有错。
					return false;
				}
			});

			// 文件上传成功，给item添加成功class, 用样式标记上传成功。
			uploader.on('uploadSuccess', function(file, response) {
				$('#' + file.id).addClass('upload-state-done');
				$('#bigPhotoPicker').remove();
				$('#advertisement_bigphoto_url').val(response.orderpicurl);
			});

			// 文件上传失败，现实上传出错。
			uploader.on('uploadError', function(file, reason) {
				var $li = $('#' + file.id), $error = $li.find('div.error');

				// 避免重复创建
				if (!$error.length) {
					$error = $('<div class="error"></div>').appendTo($li);
				}
				$error.text("Failed");
			});

			// 完成上传完了，成功或者失败，先删除进度条。
			uploader.on('uploadComplete', function(file) {
				$('#' + file.id).find('.progress').remove();
			});
		});
	</script>
</body>
</html>