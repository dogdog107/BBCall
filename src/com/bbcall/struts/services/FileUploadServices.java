package com.bbcall.struts.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;

import com.bbcall.functions.ResultCode;

@Service("fileUploadServices")
public class FileUploadServices {
	private static final int BUFFER_SIZE = 16 * 1024;
	private String fileurl;

	public int uploadFile(File srcFile, String uploadFileName, Integer userid,
			String storePath) throws Exception {
		String uploadContentType = uploadFileName.substring(uploadFileName
				.lastIndexOf(".") + 1); // 封装上传文件类型
		String storeFileName = "";
		switch (storePath.substring(storePath.lastIndexOf("/") + 1)) {
		case "UserPhoto":
			storeFileName = userid + "_photo." + uploadContentType; // 封装保存文件名
			break;
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
		fileurl = "/BBCall/" + storePath.substring(storePath.lastIndexOf("/") + 1) + "/" + storeFileName;
//		fileurl = destFile.toURI().toURL().toString();
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