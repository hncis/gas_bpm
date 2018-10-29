package org.uengine.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.uengine.kernel.FormActivity;

public class FileDownoader extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest pinsRequest,
			HttpServletResponse pinsResponse) throws ServletException,
			IOException {

		String filePath= pinsRequest.getQueryString();
		filePath = java.net.URLDecoder.decode(filePath, "UTF-8");
		filePath = filePath.substring(filePath.indexOf("=")+1, filePath.length());
		
	    FileInputStream fis = new FileInputStream(FormActivity.FILE_SYSTEM_DIR +  filePath);
	    ServletOutputStream sos = pinsResponse.getOutputStream();
		
		String contentType = "text/html";

		if (filePath.endsWith(".doc")) {
			contentType = "application/msword";
		} else if (filePath.endsWith(".ppt") || filePath.endsWith(".pps")) {
			contentType = "application/vnd.ms-powerpoint";
		} else if (filePath.endsWith(".xls")) {
			contentType = "application/vnd.ms-excel";
		} else if (filePath.endsWith(".txt")) {
			contentType = "text/plain";
		} else if (filePath.endsWith(".zip")) {
			contentType = "application/zip";
		} else if (filePath.endsWith(".bmp")) {
			contentType = "image/bmp";
		} else if (filePath.endsWith(".gif")) {
			contentType = "image/gif";
		} else if (
				filePath.endsWith(".jpe") 
				|| filePath.endsWith(".jpeg")
				|| filePath.endsWith(".jpg")
		) {
			contentType = "image/jpeg";
		}
		String filename = filePath;
		//filePath.substring(filePath.indexOf("_")+1);
		//System.out.println("contentType = " +contentType);
		//System.out.println("filename = " +filename);

		pinsResponse.setContentType(contentType + "; charset=EUC-KR");
		pinsResponse.setHeader("Cache-Control","private"); //HTTP 1.1
		pinsResponse.setHeader("Pragma","no-cache"); //HTTP 1.0
		pinsResponse.setDateHeader ("Expires", 0); //prevents caching at the proxy server
		pinsResponse.setHeader ("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(filename, "UTF-8").replace('+',' ')+"\";");
		
		try {
			UEngineUtil.copyStream(fis, sos);
		} catch (Exception e) {
			System.out.println("Cancel download by user!!!");
		}

	}
}
