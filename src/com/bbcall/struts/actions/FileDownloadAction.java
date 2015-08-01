package com.bbcall.struts.actions;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller("fileDownloadAction")
@SuppressWarnings("serial")
public class FileDownloadAction extends ActionSupport{
      
    private String fileName;//用户请求的文件名  
      
    private String inputPath;//下载资源的路径(在struts配置文件中设置)  
      
    public String downloadFile() throws Exception {  
        ServletContext context = ServletActionContext.getServletContext();  
          
        String downloadDir = context.getRealPath("/WEB-INF/logs");  
        String downloadFile = context.getRealPath(inputPath);  
        //防止用户请求不安全的资源  
        if(!downloadFile.startsWith(downloadDir)) {  
        	System.out.println("path incorrect.");
            return null;  
        }  
        return "download_success";  
    }  
    /*  
     * 获取输入流资源  
     */  
    public InputStream getInputStream() throws Exception {  
        String path = inputPath + File.separatorChar + new String(fileName.getBytes("ISO-8859-1"), "UTF-8");  
        return ServletActionContext.getServletContext().getResourceAsStream(path);  
    }  
    /*  
     * 获取下载时文件默认的文件名  
     */  
//	public String getDownloadFileName() throws Exception {
//		String downloadFileName = fileName;
//		downloadFileName = URLEncoder.encode(downloadFileName, "ISO-8859-1");
//		return downloadFileName;
//	}
    public String getDownloadFileName() {  
    	String downloadFileName = fileName;  
    	try {  
    		downloadFileName = URLEncoder.encode(downloadFileName, "ISO-8859-1");  
    	} catch (UnsupportedEncodingException e) {  
    		e.getMessage();
    		e.printStackTrace();
    	}  
    	return downloadFileName;  
    }
    
    public void setInputPath(String inputPath) {  
        this.inputPath = inputPath;  
    }  
  
    public String getInputPath() {  
        return inputPath;  
    }  
  
    public void setFileName(String fileName) {  
        this.fileName = fileName;  
    }  
  
    public String getFileName() {  
        return fileName;  
    }  
}
