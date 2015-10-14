package com.bbcall.struts.services;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bbcall.functions.RandomCode;
import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
@Scope("prototype")
@Service("fileUploadServices")
public class FileUploadServices {
	private static final int BUFFER_SIZE = 16 * 1024;
	private String fileurl;
	private String rootPath = System.getProperty("webApp.root");
	private String storeRootPath = rootPath.substring(0, rootPath.lastIndexOf("BBCall"));
	public int uploadFile(File srcFile, String uploadFileName,
			String storePath, String storeFileName) throws Exception {
		System.out.println("Here is FileUploadServices.uploadFile()");
		
		if(srcFile == null || Tools.isEmpty(uploadFileName, storePath, storeFileName)){
			return ResultCode.REQUIREINFO_NOTENOUGH;
		}
//		
//		String uploadContentType = uploadFileName.substring(uploadFileName
//				.lastIndexOf(".") + 1); // 封装上传文件类型
//		String uploadName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1, uploadFileName.lastIndexOf("."));
//		String storeFileName = "";
//		RandomCode randomCode = new RandomCode();
//		switch (storePath.substring(storePath.lastIndexOf("/") + 1)) {
//		
//		case "UserPhoto":
//			storeFileName = userid + "_photo." + uploadContentType; // 封装保存文件名
//			break;
//		case "ADupload":
//			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
//			String tempDate = df.format(new Date()); // new Date()为获取当前系统时间
//			System.out.println(tempDate);
//			storeFileName = tempDate + randomCode.getNoncestr() + "." + uploadContentType; // 封装保存文件名
//			break;
//		case "OrderPhoto":
//			storeFileName = userid + "_photo_" + uploadName + "."
//					+ uploadContentType; // 封装保存文件名
//			break;
//		default:
//			return ResultCode.UNKNOWN_ERROR;
//		}
		
		File destFile = new File(storePath + File.separator + storeFileName);
		destFile.mkdirs();
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
		String tempFileUrl = destFile.toURI().toURL().toString();
		fileurl = tempFileUrl.substring(tempFileUrl.lastIndexOf("BBCall") - 1);
//		fileurl = "/BBCall/"
//				+ storePath.substring(storePath.lastIndexOf("/") + 1) + "/"
//				+ storeFileName;
		// fileurl = destFile.toURI().toURL().toString();
		System.out.println(fileurl);
		return ResultCode.SUCCESS;
	}

	public int deleteFile(String deleteFilePath) {
		// Mac: storeRootPath: /Users/apple/Desktop/源构项目/TomcatServer/wtpwebapps/
		System.out.println("storeRootPath: " + storeRootPath);
		File destFile = new File(storeRootPath + deleteFilePath);
		if (destFile.exists()) {
			destFile.delete();
			return ResultCode.SUCCESS;
		} else {
			return ResultCode.FILE_NOTEXIST;
		}
	}

	public String getFileurl() {
		return fileurl;
	}

	// 图片处理
	public static String cutImage(String srcPath, int width, int height)
			throws IOException {
		RandomCode randomCode = new RandomCode();
		File srcFile = new File(srcPath);
		BufferedImage image = ImageIO.read(srcFile);
		int srcWidth = image.getWidth(null);
		int srcHeight = image.getHeight(null);
		int newWidth = 0, newHeight = 0;
		int x = 0, y = 0;
		double scale_w = (double) width / srcWidth;
		double scale_h = (double) height / srcHeight;
		System.out.println("scale_w=" + scale_w + ",scale_h=" + scale_h);
		// 按原比例缩放图片
		if (scale_w < scale_h) {
			newHeight = height;
			newWidth = (int) (srcWidth * scale_h);
			x = (newWidth - width) / 2;
		} else {
			newHeight = (int) (srcHeight * scale_w);
			newWidth = width;
			y = (newHeight - height) / 2;
		}
		BufferedImage newImage = new BufferedImage(newWidth, newHeight,
				BufferedImage.TYPE_INT_RGB);
		newImage.getGraphics()
				.drawImage(
						image.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
		// 保存缩放后的图片
		String fileSufix = srcFile.getName().substring(
				srcFile.getName().lastIndexOf(".") + 1);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		String tempDate = df.format(new Date()); // new Date()为获取当前系统时间
		String aa = tempDate + randomCode.getNoncestr() + "." + fileSufix;
		File destFile = new File(srcFile.getParent(), aa);
		// ImageIO.write(newImage, fileSufix, destFile);
		// 保存裁剪后的图片
		System.out.println("fileSufix====" + fileSufix);
		System.out.println("image=====" + srcPath + aa);
		ImageIO.write(newImage.getSubimage(x, y, width, height), fileSufix,
				destFile);
		return aa;
	}
}