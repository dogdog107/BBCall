package com.bbcall.struts.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.bbcall.functions.RandomCode;
import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;

@Service("fileUploadServices")
public class FileUploadServices {
	private static final int BUFFER_SIZE = 16 * 1024;
	private String fileurl;

	public int uploadFile(File srcFile, String uploadFileName, Integer userid,
			String storePath) throws Exception {
		if(srcFile == null || Tools.isEmpty(uploadFileName, storePath)){
			return ResultCode.REQUIREINFO_NOTENOUGH;
		}
		
		String uploadContentType = uploadFileName.substring(uploadFileName
				.lastIndexOf(".") + 1); // 封装上传文件类型
		String uploadName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1, uploadFileName.lastIndexOf("."));
		String storeFileName = "";
		RandomCode randomCode = new RandomCode();
		switch (storePath.substring(storePath.lastIndexOf("/") + 1)) {
		
		case "UserPhoto":
			storeFileName = userid + "_photo." + uploadContentType; // 封装保存文件名
			break;
		case "ADupload":
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
			String tempDate = df.format(new Date()); // new Date()为获取当前系统时间
			System.out.println(tempDate);
			storeFileName = tempDate + randomCode.getNoncestr() + "." + uploadContentType; // 封装保存文件名
			break;
		case "OrderPhoto":
			storeFileName = userid + "_photo_" + uploadName + "."
					+ uploadContentType; // 封装保存文件名
			break;
		default:
			return ResultCode.UNKNOWN_ERROR;
		}
		File destFile = new File(storePath + "//" + storeFileName);

		if (destFile.exists()) {
			destFile.delete();
		}

		// 保存文件的地址
		FileOutputStream fos = new FileOutputStream(destFile);

		// 获得上传文件流
		FileInputStream fis = new FileInputStream(srcFile);

		// 将文件写入服务器
		byte[] buffer = new byte[BUFFER_SIZE];
		int len = 0;
		while ((len = fis.read(buffer)) > 0) {
			fos.write(buffer, 0, len);
		}
		fos.close();
		fis.close();
		fileurl = "/BBCall/"
				+ storePath.substring(storePath.lastIndexOf("/") + 1) + "/"
				+ storeFileName;
		// fileurl = destFile.toURI().toURL().toString();
		System.out.println(fileurl);
		return ResultCode.SUCCESS;
	}

	public int deleteFile(String deleteFilePath) {

		File destFile = new File(deleteFilePath);
		if (destFile.exists()) {
			destFile.delete();
		}
		return ResultCode.SUCCESS;
	}

	public String getFileurl() {
		return fileurl;
	}
}