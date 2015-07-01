package com.bbcall.struts.actions;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
import com.bbcall.struts.services.FileUploadServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("fileUploadAction")
@SuppressWarnings("serial")
public class FileUploadAction extends ActionSupport{
	
	@Autowired
	private FileUploadServices fileUploadServices;
	private Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	
	private String savePath; // 封装保存文件的路径目录
	private String title; // 封装上传文件的标题
	private File upload; // 封装上传文件的内容
	private String uploadContentType; // 封装上传文件类型
	private String uploadFileName; // 封装上传文件名
	private Integer userid;
	private String token;
	private String picurl;
	
	public String userUpload() throws Exception {
		System.out.println("Here is FileUploadAction.userUpload()");
		savePath = "/UserPhoto";

		String storePath = ServletActionContext.getServletContext()
				.getRealPath(savePath);
		int result = fileUploadServices.uploadFile(upload, uploadFileName,
				userid, storePath);
		picurl = fileUploadServices.getFileurl();
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		if (result == ResultCode.SUCCESS) {
			dataMap.put("picurl", picurl); // 放入一个是否操作成功的标识
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("userUploadResult", true); // 放入registerResult
			System.out.println(dataMap);
			return "userUploadSuccess";
		} else {
			fileUploadServices.deleteFile(storePath);
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("userUploadResult", false); // 放入registerResult
			System.out.println(dataMap);
			System.out.println("userUpload Failed");
			return "userUploadFailed";
		}
	}
	
	public String userUploadJson() throws Exception {
		System.out.println("Here is FileUploadAction.userUploadJson()");
		userUpload();
		return "json";
	}
	
//	public String uploadFile() throws Exception {
//
//		// 保存文件的地址
//		FileOutputStream fos = new FileOutputStream(getSavePath() + "\\"
//				+ uploadFileName);
//
//		// 获得上传文件流
//		FileInputStream fis = new FileInputStream(upload);
//
//		// 将文件写入服务器
//		byte[] buffer = new byte[BUFFER_SIZE];
//		int len = 0;
//		while ((len = fis.read(buffer)) > 0) {
//			fos.write(buffer, 0, len);
//		}
//    fos.close();
//    fis.close();
//		return SUCCESS;
//	}
	
	// Json Format Return 
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	
	public String getSavePath() {
		return ServletActionContext.getServletContext().getRealPath(savePath);
	}

	public String getTitle() {
		return title;
	}

	public File getUpload() {
		return upload;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
