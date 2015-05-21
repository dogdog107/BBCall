package com.bbcall.functions;

import java.io.File;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Service
public class FileUpload{

	protected static Logger logger = Logger.getLogger(FileUpload.class);

	@SuppressWarnings({ "rawtypes", "null" })
	public void userAvatarUpload(HttpServletRequest request, Integer user_id) {
		
		try {
			// 转型为MultipartHttpRequest：
			// MultipartHttpServletRequest multipartRequest =
			// (MultipartHttpServletRequest)request;
			MultipartResolver resolver = new CommonsMultipartResolver(request
					.getSession().getServletContext());
			MultipartHttpServletRequest multipartRequest = resolver
					.resolveMultipart(request);
			
			String filename = null;
			// 遍历所有文件域，获得上传的文件
			for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
				String key = (String) it.next();
				MultipartFile file = multipartRequest.getFile(key);
				// saveFile(file);
				if (file != null || !file.isEmpty()) {
					filename = "avatar_" + user_id + ".png";
					String localfileName = request.getSession()
							.getServletContext().getRealPath("/")
							+ "user_avatar/" + filename;
					// 写入文件
					File source = new File(localfileName.toString());
					// 判断目录是否存在，并创建
					if (!source.exists())
						source.mkdirs();
					// 保存文件
					file.transferTo(source);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}