package com.hncis.controller.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.hncis.common.Constant;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.util.FileUtil;
import com.hncis.common.util.StringUtil;

public class ImgFileDownView extends AbstractView{

	public void Download(){
		setContentType("application/download; utf-8");
	}

	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		//File file = (File)model.get("downloadFile");
		//System.out.println("file.getPath="+file.getPath());
		//System.out.println("file.getName="+file.getName());

		Map       datas      = (Map)model.get("fileData");
		String    fileRealName	= (String)datas.get("fileRealName");
		String    fileName   	= (String)datas.get("fileName");
		String    folderName   	= (String)datas.get("folderName");
		String    fileDelete   	= StringUtil.isNullToString(datas.get("fileDelete"));
		String 	  realFolder  = "";

		if(StringUtil.getSystemArea().equals("LOCAL")){
			realFolder = Constant.EDT_UPLOAD_LOCAL_PATH;
		}else if(StringUtil.getSystemArea().equals("TEST")){
			realFolder = Constant.EDT_UPLOAD_TEST_PATH;
		}else{
			realFolder = Constant.EDT_UPLOAD_REAL_PATH;
		}

		String corp_cd = "";
        try {
			corp_cd = SessionInfo.getSess_corp_cd(request);
		} catch (SessionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		File downloadFile = new File(realFolder+"/"+folderName+"/"+fileRealName);
        File downloadFile = new File(realFolder+"/"+corp_cd+"/"+folderName+"/"+fileRealName);

		response.setContentType(getContentType());
		response.setContentLength((int)downloadFile.length());

		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("rv:11.0") > -1;
		//String fileName = null;

		if(ie){
			fileName = URLEncoder.encode(fileName, "utf-8");
		}else{
			fileName = new String(fileName.getBytes("utf-8"));
		}

		System.out.println("fileName="+fileName);

		response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;

		System.out.println("Debug01");

		try{
			fis = new FileInputStream(downloadFile);
			FileCopyUtils.copy(fis, out);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fis != null){
				try{
					fis.close();
				}catch(Exception e){}
			}
		}
		System.out.println("Debug02");
		out.flush();
		System.out.println("Debug03");

		if(fileDelete.equals("Y")){
			FileUtil.deleteFile(corp_cd, folderName, fileRealName);
		}
	}
}