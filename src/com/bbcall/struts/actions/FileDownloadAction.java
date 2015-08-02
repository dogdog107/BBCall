package com.bbcall.struts.actions;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("fileDownloadAction")
@SuppressWarnings("serial")
public class FileDownloadAction extends ActionSupport {

	private Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	private static Logger logger = Logger.getLogger(FileDownloadAction.class);  
	private String fileName;// 用户请求的文件名
	private String inputPath;// 下载资源的路径(在struts配置文件中设置)
	private String fileType;// 文件类型 （Grade, Trade, User）

	public String downloadFile() throws Exception {
		ServletContext context = ServletActionContext.getServletContext();

		String downloadDir = context.getRealPath("/WEB-INF/logs");
		String downloadFile = context.getRealPath(inputPath);
		// 防止用户请求不安全的资源
		if (!downloadFile.startsWith(downloadDir)) {
			System.out.println("path incorrect.");
			logger.info("userOpr:[downloadFile]Download reject. Download path incorrect.");  
			return null;
		}
		
        File file = new File(downloadFile + File.separatorChar + fileName);
        if (!file.isFile()) {
        	System.out.println("File not exist.");
        	logger.info("userOpr:[downloadFile]Download reject. Download file not exist.");  
        	return null;
        }
		return "download_success";
	}

	/*
	 * 获取输入流资源
	 */
	public InputStream getInputStream() throws Exception {
		String path = inputPath + File.separatorChar
				+ new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
		return ServletActionContext.getServletContext().getResourceAsStream(
				path); // 如果path是Resource下的相对路径
		// return new FileInputStream(dir); //如果path是绝对路径
	}

	/*
	 * 获取下载时文件默认的文件名
	 */
	public String getDownloadFileName() {
		String[] newName = fileName.split("\\.");
		String downloadFileName;
		if (newName.length <= 2) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			downloadFileName = newName[0] + "_" +  dateFormat.format(new Date()) + ".txt";
		} else {
			downloadFileName = newName[0] + "_" +  newName[2] + ".txt";
		}
		try {
			downloadFileName = URLEncoder
					.encode(downloadFileName, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.getMessage();
			e.printStackTrace();
		}
		System.out.println(downloadFileName);
		logger.info("userOpr:[downloadFile]File be downloaded: " + fileName);  
		return downloadFileName;
	}

	public String getFileList() throws Exception {
		dataMap.clear();
		System.out.println(fileType);
		if (fileType == null || (!fileType.equals("Grade") && !fileType.equals("Trade") && !fileType.equals("User"))) {
			dataMap.putAll(Tools.JsonHeadMap(ResultCode.REQUIREINFO_ERROR, false));
			logger.info("userOpr:[getFileList]" + Tools.JsonHeadMap(ResultCode.REQUIREINFO_ERROR, false));  
			return null;
		}
		
		ServletContext context = ServletActionContext.getServletContext();
		String downloadFile = context.getRealPath(inputPath);
		File file = new File(downloadFile);
		File[] subFile = file.listFiles();
		List<String> tempList = new ArrayList<String>();
		if (subFile != null) {
			for (File f : subFile) {
				if (f.getName().contains(fileType)) {
					tempList.add(f.getName());
				}
			}
		}
		//升序排列；
//		Collections.sort(tempList);
		//逆序输出
		Collections.reverse(tempList); 
		dataMap.put("fileList", tempList);
		dataMap.putAll(Tools.JsonHeadMap(ResultCode.SUCCESS, true));
		logger.info("userOpr:[getFileList]" + Tools.JsonHeadMap(ResultCode.SUCCESS, true));  
		return null;
	}
	public String getFileListJson() throws Exception {
		getFileList();
		return "json";
	}
	
	// Json Format Return 
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	
	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}

//	public String getInputPath() {
//		return inputPath;
//	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
