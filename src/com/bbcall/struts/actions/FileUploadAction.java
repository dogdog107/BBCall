package com.bbcall.struts.actions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.RandomCode;
import com.bbcall.functions.ResultCode;
import com.bbcall.struts.services.FileUploadServices;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("fileUploadAction")
@SuppressWarnings("serial")
public class FileUploadAction extends ActionSupport {

	@Autowired
	private FileUploadServices fileUploadServices;
	private Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	RandomCode randomCode = new RandomCode();
	
	private String savePath; // 封装保存文件的路径目录
	private String title; // 封装上传文件的标题
	private File upload; // 封装上传文件的内容
	private List<File> uploadlist;
	private List<String> uploadlistContentType;
	private List<String> uploadlistFileName;
	private String uploadContentType; // 封装上传文件类型
	private String uploadFileName; // 封装上传文件名
	private Integer userid;
	private Integer orderid;
	private String token;
	private String picurl;
	private String orderpicurl;

	public String userUpload() throws Exception {
		System.out.println("Here is FileUploadAction.userUpload()");
		savePath = "UserPhoto";
		String storePath = ServletActionContext.getServletContext()
				.getRealPath(savePath); // 得到保存文件的路径
		String uploadFileType = uploadFileName.substring(uploadFileName
				.lastIndexOf(".") + 1); // 封装上传文件后缀
		String storeFileName = userid + "_photo." + uploadFileType; // 封装保存文件名
		
		int result = fileUploadServices.uploadFile(upload, uploadFileName, storePath, storeFileName);
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		if (result == ResultCode.SUCCESS) {
			picurl = fileUploadServices.getFileurl();
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
 
	public String orderUpload() throws Exception {
		System.out.println("Here is FileUploadAction.orderUpload()");
		savePath = "OrderPhoto";
		String storePath = ServletActionContext.getServletContext()
				.getRealPath(savePath); // 得到保存文件的路径

		
		System.out.println(storePath);
		int result = 1;

		System.out.println(uploadlist.size());
		for (int i = 0; i < uploadlist.size(); i++) {
			String storeFileName = orderid + "_photo_" + uploadlistFileName.get(i); // 封装保存文件名
			result = fileUploadServices.uploadFile(uploadlist.get(i), uploadlistFileName.get(i),
					storePath, storeFileName);
			System.out.println(result);
			if (orderpicurl == null) {
				orderpicurl = fileUploadServices.getFileurl();
			} else {
				orderpicurl = orderpicurl + ";"
						+ fileUploadServices.getFileurl();
			}
		}

		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		if (result == ResultCode.SUCCESS) {
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("orderUploadResult", true); // 放入registerResult
			System.out.println(dataMap);
			System.out.println(orderpicurl);
			return orderpicurl;
		} else {
			fileUploadServices.deleteFile(storePath);
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("orderUploadResult", false); // 放入registerResult
			System.out.println(dataMap);
			System.out.println("orderUpload Failed");
			return "orderUploadFailed";
		}
	}

	public String orderUploadJson() throws Exception {
		System.out.println("Here is FileUploadAction.orderUploadJson()");
		orderUpload();
		return "json";
	}

	public String advertUpload() throws Exception {
		System.out.println("Here is FileUploadAction.advertUpload()");
		savePath = "ADupload";
		String storePath = ServletActionContext.getServletContext()
				.getRealPath(savePath); // 得到保存文件的路径
		String uploadFileType = uploadFileName.substring(uploadFileName
				.lastIndexOf(".") + 1); // 封装上传文件后缀
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		String tempDate = df.format(new Date()); // new Date()为获取当前系统时间
		String storeFileName = tempDate + randomCode.getNoncestr() + "." + uploadFileType; // 封装保存文件名
		
		int result = fileUploadServices.uploadFile(upload, uploadFileName, storePath, storeFileName);
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		if (result == ResultCode.SUCCESS) {
			picurl = fileUploadServices.getFileurl();
			dataMap.put("picurl", picurl); // 放入一个是否操作成功的标识
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("advertUploadResult", true); // 放入registerResult
			System.out.println(dataMap);
			return "advertUploadSuccess";
		} else {
			fileUploadServices.deleteFile(storePath);
			dataMap.put("resultcode", result); // 放入一个是否操作成功的标识
			dataMap.put("errmsg", ResultCode.getErrmsg(result));
			dataMap.put("advertUploadResult", false); // 放入registerResult
			System.out.println(dataMap);
			System.out.println("advertUpload Failed");
			return "advertUploadFailed";
		}
	}
	
	public String advertUploadJson() throws Exception {
		System.out.println("Here is FileUploadAction.advertUploadJson()");
		advertUpload();
		return "json";
	}
	
	// public String uploadFile() throws Exception {
	//
	// // 保存文件的地址
	// FileOutputStream fos = new FileOutputStream(getSavePath() + "\\"
	// + uploadFileName);
	//
	// // 获得上传文件流
	// FileInputStream fis = new FileInputStream(upload);
	//
	// // 将文件写入服务器
	// byte[] buffer = new byte[BUFFER_SIZE];
	// int len = 0;
	// while ((len = fis.read(buffer)) > 0) {
	// fos.write(buffer, 0, len);
	// }
	// fos.close();
	// fis.close();
	// return SUCCESS;
	// }

	// Json Format Return
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
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

	public List<File> getUploadlist() {
		return uploadlist;
	}

	public void setUploadlist(List<File> uploadlist) {
		this.uploadlist = uploadlist;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getOrderpicurl() {
		return orderpicurl;
	}

	public void setOrderpicurl(String orderpicurl) {
		this.orderpicurl = orderpicurl;
	}

	public List<String> getUploadlistContentType() {
		return uploadlistContentType;
	}

	public void setUploadlistContentType(List<String> uploadlistContentType) {
		this.uploadlistContentType = uploadlistContentType;
	}

	public List<String> getUploadlistFileName() {
		return uploadlistFileName;
	}

	public void setUploadlistFileName(List<String> uploadlistFileName) {
		this.uploadlistFileName = uploadlistFileName;
	}

	public FileUploadServices getFileUploadServices() {
		return fileUploadServices;
	}

	public void setFileUploadServices(FileUploadServices fileUploadServices) {
		this.fileUploadServices = fileUploadServices;
	}

}
