package com.hncis.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hncis.common.Constant;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.message.HncisMessageSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * 파일처리에 관련된 클래스
 *
 * @author 6804401
 *
 */
public class FileUtil {
    private transient static Log logger = LogFactory.getLog(FileUtil.class.getClass());
    
	private static final String typeDoc = "doc";
	private static final String typeHwp = "hwp";
	private static final String typeDocx = "docx";
	private static final String typeXls = "xls";
	private static final String typePdf = "pdf";
	private static final String typePpt = "ppt";
	private static final String typeGif = "gif";
	private static final String typePng = "png";
	private static final String typeJpg = "jpg";
	private static final String typeJpeg = "jpeg";
	private static final String strLocal = "LOCAL";
	private static final String strTest = "TEST";
	private static final String strHttp = "http://";
	private static final String strUploadFile = "uploadFile:";
	private static final String strRealFolder = "realFolder:";
	private static final String strFileName = "fileName:";
	private static final String strOldFileName = "oldFileName:";
	private static final String strStar = "##################################";
	private static final String strMessage = "messege";
	/**
	 * 업로드 된 파일을 읽어 레파지토리에 저장한다.
	 * @param multipartFile MultipartFile Object
	 * @param writeFilePath 업로드 할 파일의 경로
	 * @return 파일의 전체경로정보
	 * @throws IOException 파일읽고 쓸때 발생되는 Exception Object
	 */
	public static String saveFile(MultipartFile multipartFile, String writeFilePath) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String tempName = UUID.randomUUID().toString();

		logger.info("size="+multipartFile.getSize());

		if (multipartFile.getSize() > 0) {
			String ext = StringUtil.lowerCase(StringUtil.fileExtention(multipartFile.getOriginalFilename()));
			if((ext.equals(typeHwp) || ext.equals(typeDoc) || ext.equals(typeDocx) || ext.equals(typeXls) || ext.equals(typePdf) || ext.equals(typePpt) ||
					ext.equals(typeGif) || ext.equals(typePng) || ext.equals(typeJpg)) ){
				try {
					File dir = new File(writeFilePath);
					if(!dir.exists()) {
						dir.mkdirs();
					}
					String fileFullPath = writeFilePath+"/" + multipartFile.getOriginalFilename();
					inputStream = multipartFile.getInputStream();
					outputStream = new FileOutputStream(fileFullPath);
					int readBytes = 0;
					byte[] buffer = new byte[8192];
					while ((readBytes = inputStream.read(buffer, 0 , 8192))!=-1){
						outputStream.write(buffer, 0, readBytes);
					}
					outputStream.close();
					inputStream.close();
					return fileFullPath;
				} catch(Exception ex) {
					outputStream.close();
					inputStream.close();
				} finally {
					outputStream.close();
					inputStream.close();
				}
			}
		}
		return null;
	}

	/**
	 * 업로드 된 파일을 읽어 레파지토리에 저장한다.
	 * @param multipartFile MultipartFile Object
	 * @param writeFilePath 업로드 할 파일의 경로
	 * @param newFileName 업로드 할 파일의 새이름
	 * @return 파일의 전체경로정보
	 * @throws IOException 파일읽고 쓸때 발생되는 Exception Object
	 */
	public static String saveFile(MultipartFile multipartFile, String writeFilePath, String newFileName) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String tempName = UUID.randomUUID().toString();

		if (multipartFile.getSize() > 0) {
			String ext = StringUtil.lowerCase(StringUtil.fileExtention(newFileName));
			if((ext.equals(typeHwp) || ext.equals(typeDoc) || ext.equals(typeDocx) || ext.equals(typeXls) || ext.equals(typePdf) || ext.equals(typePpt) ||
					ext.equals(typeGif) || ext.equals(typePng) || ext.equals(typeJpg)) ){
				try {
					File dir = new File(writeFilePath);
					if(!dir.exists()) {
						dir.mkdirs();
					}
					String fileFullPath = writeFilePath+"/" + newFileName;
					inputStream = multipartFile.getInputStream();
					outputStream = new FileOutputStream(fileFullPath);
					int readBytes = 0;
					byte[] buffer = new byte[8192];
					while ((readBytes = inputStream.read(buffer, 0 , 8192))!=-1){
						outputStream.write(buffer, 0, readBytes);
					}
					outputStream.close();
					inputStream.close();
					return fileFullPath;
				} catch(Exception ex) {
					logger.error(strMessage, ex);
					outputStream.close();
					inputStream.close();
				} finally {
					if( outputStream != null ){
	                    outputStream.close();
	                }
	                if( inputStream != null ){
	                    inputStream.close();
	                }
				}
			}
		}
		return null;
	}

	/**
	 * 기존파일 삭제 후 업로드 된 파일을 읽어 레파지토리에 저장한다.
	 * @param multipartFile MultipartFile Object
	 * @param writeFilePath 업로드 할 파일의 경로
	 * @param newFileName 업로드 할 파일의 새이름
	 * @param oldFileName 삭제할 기존 파일의 이름
	 * @return 파일의 전체경로정보
	 * @throws IOException 파일읽고 쓸때 발생되는 Exception Object
	 */
	public static String saveFile(MultipartFile multipartFile, String writeFilePath, String newFileName, String oldFileName) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String tempName = UUID.randomUUID().toString();

		if (multipartFile.getSize() > 0) {
			String ext = StringUtil.lowerCase(StringUtil.fileExtention(newFileName));
			try {
				File dir = new File(writeFilePath);
				if(!dir.exists()) {
					dir.mkdirs();
				}else if(!oldFileName.equals("")){
					String oldFileFullPath = writeFilePath+"/" + oldFileName;
					File oldFile = new File(oldFileFullPath);
					if(oldFile.exists()){
						oldFile.delete();
					}
				}
				String newFileFullPath = writeFilePath+"/" + newFileName;
				inputStream = multipartFile.getInputStream();
				outputStream = new FileOutputStream(newFileFullPath);
				int readBytes = 0;
				byte[] buffer = new byte[8192];
				while ((readBytes = inputStream.read(buffer, 0 , 8192))!=-1){
					outputStream.write(buffer, 0, readBytes);
				}
				outputStream.close();
				inputStream.close();
				return newFileFullPath;
			} catch(Exception ex) {
				outputStream.close();
				inputStream.close();
			} finally {
				if( outputStream != null ){
					outputStream.close();
				}
				if( inputStream != null ){
					inputStream.close();
				}
			}
		}//end if (multipartFile.getSize() > 0) {
		return null;
	}

	public static String[] uploadFile(HttpServletRequest req, HttpServletResponse res, String[] paramVal){
		MultipartHttpServletRequest mr = null;
		MultipartFile uploadFile = null;

		String fileName    = "";
		String oldFileName = "";
		String realFolder  = "";
		String[] result = new String[6];
		mr = (MultipartHttpServletRequest) req;
		try{
			logger.info("paramVal[0]="+paramVal[0]);
			uploadFile = mr.getFile(paramVal[0]);
			logger.info("uploadFile="+uploadFile);

			logger.info(strStar);
			logger.info("Constant.UPLOAD_REAL_PATH="+Constant.UPLOAD_REAL_PATH);
			logger.info(strStar);

			if(StringUtil.getSystemArea().equals(strLocal)){
				realFolder = Constant.UPLOAD_LOCAL_PATH;
			}else if(StringUtil.getSystemArea().equals(strTest)){
				realFolder = Constant.UPLOAD_TEST_PATH;
			}else{
				realFolder = Constant.UPLOAD_REAL_PATH;
			}

			fileName = uploadFile.getOriginalFilename();

			logger.info("fileName11111="+fileName);
			String corp_cd = "";
	        try {
				corp_cd = SessionInfo.getSess_corp_cd(req);
			} catch (SessionException e1) {
				// TODO Auto-generated catch block
				logger.error(strMessage, e1);
			}

			String realUrl = strHttp + req.getServerName() + realFolder;
			if(!uploadFile.getOriginalFilename().equals("")){
				oldFileName = mr.getParameter(paramVal[1]);
				realFolder += "/"+corp_cd+"/"+paramVal[2];
				realUrl    += "/"+corp_cd+"/"+paramVal[2];

				String ext = StringUtil.lowerCase(StringUtil.lowerCase(StringUtil.fileExtention(fileName)));
				if(!(ext.equals(typeHwp) || ext.equals(typeDoc) || ext.equals(typeDocx) || ext.equals(typeXls) || ext.equals("xlsx") ||
					 ext.equals(typePdf) || ext.equals(typePpt) || ext.equals("pptx") || ext.equals(typeGif) ||
					 ext.equals(typePng) || ext.equals("bmp")  || ext.equals(typeJpg) || ext.equals(typeJpeg) || ext.equals("txt"))){
					//result = null;
					result[4] = HncisMessageSource.getMessage("FILE.0002");
				}else{
					File file = new File(realFolder+"/"+fileName);
					String extention = StringUtil.fileExtention(uploadFile.getOriginalFilename());
					//String tmpFileName = uploadFile.getOriginalFilename().substring(0,uploadFile.getOriginalFilename().lastIndexOf("."));

					/*
					while(file.exists()){
						Random rn   = new Random();
						tmpFileName = tmpFileName+rn.nextInt(100) + "." + extention;
						file        = new File(tmpFileName);
						fileName    = tmpFileName;
					}
					*/

					String tmpFileName = StringUtil.getFileNm() + "." + extention;

					if(oldFileName == null || oldFileName.equals("")){
						oldFileName = "";
					}
					logger.info(strUploadFile+uploadFile);
					logger.info(strRealFolder+realFolder);
					logger.info(strFileName+fileName);
					logger.info("tmpFileName:"+tmpFileName);
					logger.info(strOldFileName+oldFileName);
					logger.info("fileSize:"+uploadFile.getSize());
					logger.info("path:"+realFolder+"/"+fileName);
					//logger.info("exists:"+uploadFile.);



					if(uploadFile.getSize() > 10240000){
						result[4] = HncisMessageSource.getMessage("FILE.0003");
					}else{
						saveFile(uploadFile, realFolder, tmpFileName, oldFileName);
						result[0] = tmpFileName;
						result[1] = realUrl;
						result[2] = realFolder;
						result[3] = String.valueOf(uploadFile.getSize());
						result[4] = HncisMessageSource.getMessage("FILE.0000");
						result[5] = fileName;
					}
				}//END ELSE
			}else{
				result[4] = HncisMessageSource.getMessage("FILE.0001");
			}
		}catch(Exception e){
			logger.error(strMessage, e);
		}

		return result;
	}

	public static String[] uploadImgFile(HttpServletRequest req, HttpServletResponse res, String[] paramVal){
		MultipartHttpServletRequest mr = null;
		MultipartFile uploadFile = null;

		String fileName    = "";
		String oldFileName = "";
		String realFolder  = "";
		String[] result = new String[6];
		mr = (MultipartHttpServletRequest) req;
		try{
			logger.info("paramVal[0]="+paramVal[0]);
			uploadFile = mr.getFile(paramVal[0]);
			logger.info("uploadFile="+uploadFile);

			logger.info(strStar);
			logger.info("Constant.UPLOAD_REAL_PATH="+Constant.UPLOAD_REAL_PATH);
			logger.info(strStar);

			if(StringUtil.getSystemArea().equals(strLocal)){
				realFolder = Constant.EDT_UPLOAD_LOCAL_PATH;
			}else if(StringUtil.getSystemArea().equals(strTest)){
				realFolder = Constant.EDT_UPLOAD_TEST_PATH;
			}else{
				realFolder = Constant.EDT_UPLOAD_REAL_PATH;
			}

			fileName = uploadFile.getOriginalFilename();

			String corp_cd = "";
	        try {
				corp_cd = SessionInfo.getSess_corp_cd(req);
			} catch (SessionException e1) {
				// TODO Auto-generated catch block
				logger.error(strMessage, e1);
			}

			String realUrl = strHttp + req.getServerName() + realFolder;
			if(!uploadFile.getOriginalFilename().equals("")){
				oldFileName = mr.getParameter(paramVal[1]);
				realFolder += "/"+corp_cd+"/"+paramVal[2];
				realUrl    += "/"+corp_cd+"/"+paramVal[2];

				String ext = StringUtil.lowerCase(StringUtil.lowerCase(StringUtil.fileExtention(fileName)));
				if(!(ext.equals(typeGif) || ext.equals(typePng) || ext.equals("bmp")  || ext.equals(typeJpg) || ext.equals(typeJpeg))){
					//result = null;
					result[4] = HncisMessageSource.getMessage("FILE.0002");
				}else{
					File file = new File(realFolder+"/"+fileName);
					String extention = StringUtil.fileExtention(uploadFile.getOriginalFilename());
					//String tmpFileName = uploadFile.getOriginalFilename().substring(0,uploadFile.getOriginalFilename().lastIndexOf("."));

					/*
					while(file.exists()){
						Random rn   = new Random();
						tmpFileName = tmpFileName+rn.nextInt(100) + "." + extention;
						file        = new File(tmpFileName);
						fileName    = tmpFileName;
					}
					 */

					String tmpFileName = StringUtil.getFileNm() + "." + extention;

					if(oldFileName == null || oldFileName.equals("")){
						oldFileName = "";
					}
					logger.info(strUploadFile+uploadFile);
					logger.info(strRealFolder+realFolder);
					logger.info(strFileName+fileName);
					logger.info("tmpFileName:"+tmpFileName);
					logger.info(strOldFileName+oldFileName);
					logger.info("fileSize:"+uploadFile.getSize());
					logger.info("path:"+realFolder+"/"+fileName);
					//logger.info("exists:"+uploadFile.);



					if(uploadFile.getSize() > 10240000){
						result[4] = HncisMessageSource.getMessage("FILE.0003");
					}else{
						saveFile(uploadFile, realFolder, tmpFileName, oldFileName);
						result[0] = tmpFileName;
						result[1] = realUrl;
						result[2] = realFolder;
						result[3] = String.valueOf(uploadFile.getSize());
						result[4] = HncisMessageSource.getMessage("FILE.0000");
						result[5] = fileName;
					}
				}//END ELSE
			}else{
				result[4] = HncisMessageSource.getMessage("FILE.0001");
			}
		}catch(Exception e){
			logger.error(strMessage, e);
		}

		return result;
	}


	public static String deleteImgFile(String corp_cd, String delFolderName, String fileName) throws IOException {

		String result = "";
		String fileFullPath = "";
		String realFolder = "";

		if(StringUtil.getSystemArea().equals(strLocal)){
			realFolder = Constant.EDT_UPLOAD_LOCAL_PATH;
		}else if(StringUtil.getSystemArea().equals(strTest)){
			realFolder = Constant.EDT_UPLOAD_TEST_PATH;
		}else{
			realFolder = Constant.EDT_UPLOAD_REAL_PATH;
		}
        
		fileFullPath = realFolder+"/"+corp_cd+"/"+delFolderName+"/"+fileName;

		try{
			File delFile = new File(fileFullPath);
			if(delFile.exists()){
				delFile.delete();
			}
			result = "S";
		}catch(Exception e){
			logger.error(strMessage, e);
		}

		return result;
	}

	public static String[] uploadFileBackgroundImage(HttpServletRequest req, HttpServletResponse res, String[] paramVal){
		MultipartHttpServletRequest mr = null;
		MultipartFile uploadFile = null;

		String fileName    = "";
		String oldFileName = "";
		String realFolder  = "";
		String[] result = new String[4];
		mr = (MultipartHttpServletRequest) req;
		try{
			uploadFile = mr.getFile(paramVal[0]);
			realFolder = req.getSession().getServletContext().getRealPath("/") ;
			fileName = uploadFile.getOriginalFilename();

			String corp_cd = "";
	        try {
				corp_cd = SessionInfo.getSess_corp_cd(req);
			} catch (SessionException e1) {
				// TODO Auto-generated catch block
				logger.error(strMessage, e1);
			}
	        
			String realUrl = strHttp + req.getServerName() + realFolder;
			if(!uploadFile.getOriginalFilename().equals("")){
				oldFileName = mr.getParameter(paramVal[1]);
				realFolder += "/"+corp_cd+"/"+paramVal[2];
				realUrl    += "/"+corp_cd+"/"+paramVal[2];

				String ext = StringUtil.lowerCase(StringUtil.fileExtention(fileName));
				if(!(ext.equals(typeGif) || ext.equals(typePng) || ext.equals(typeJpg))){
					result = null;
				}else{
					File file = new File(realFolder+"/"+fileName);
					String extention = StringUtil.fileExtention(uploadFile.getOriginalFilename());
					String tmpFileName = uploadFile.getOriginalFilename().substring(0,uploadFile.getOriginalFilename().lastIndexOf("."));

					while(file.exists()){
						Random rn   = new Random();
						tmpFileName = tmpFileName+rn.nextInt(100) + "." + extention;
						file        = new File(tmpFileName);
						fileName    = tmpFileName;
					}

					if(oldFileName == null || oldFileName.equals("")){
						oldFileName = "";
					}
					logger.info(strUploadFile+uploadFile);
					logger.info(strRealFolder+realFolder);
					logger.info(strFileName+fileName);
					logger.info(strOldFileName+oldFileName);
					saveFile(uploadFile, realFolder, fileName, oldFileName);
					result[0] = fileName;
					result[1] = realUrl;
					result[2] = realFolder;
					result[3] = String.valueOf(file.length());
				}//END ELSE
			}else{
				result[0] = "";
				result[1] = realUrl;
				result[2] = realFolder;
				result[3] = "";
			}
		}catch(Exception e){
			logger.error(strMessage, e);
		}

		return result;
	}

	public static String deleteFile(String corp_cd, String delFolderName, String fileName) throws IOException {

		String result = "";
		String fileFullPath = "";
		String realFolder = "";

		if(StringUtil.getSystemArea().equals(strLocal)){
			realFolder = Constant.UPLOAD_LOCAL_PATH;
		}else if(StringUtil.getSystemArea().equals(strTest)){
			realFolder = Constant.UPLOAD_TEST_PATH;
		}else{
			realFolder = Constant.UPLOAD_REAL_PATH;
		}

		fileFullPath = realFolder+"/"+corp_cd+"/"+delFolderName+"/"+fileName;

		try{
			File delFile = new File(fileFullPath);
			if(delFile.exists()){
				delFile.delete();
			}
			result = "S";
		}catch(Exception e){
			logger.error(strMessage, e);
		}

		return result;
	}


	public static String uploadFile(HttpServletRequest req, String[] paramVal){
        MultipartHttpServletRequest mhr = null;

        String fileName     = "";
        String realFolder   = "";
        String resultString = "";

        mhr = (MultipartHttpServletRequest) req;
        try{
            if(StringUtil.getSystemArea().equals(strLocal)){
                realFolder = Constant.UPLOAD_LOCAL_PATH;
            }else if(StringUtil.getSystemArea().equals(strTest)){
                realFolder = Constant.UPLOAD_TEST_PATH;
            }else{
                realFolder = Constant.UPLOAD_REAL_PATH;
            }

            String corp_cd = "";
	        try {
				corp_cd = SessionInfo.getSess_corp_cd(req);
			} catch (SessionException e1) {
				// TODO Auto-generated catch block
				logger.error(strMessage, e1);
			}
	        
            String realUrl = strHttp + req.getServerName() + realFolder;
            realFolder +="/"+corp_cd+ "/"+paramVal[1];
            realUrl    +="/"+corp_cd+ "/"+paramVal[1];

            MultipartFile uploadFile = mhr.getFile(paramVal[0]);

            fileName = uploadFile.getOriginalFilename();

            if(!uploadFile.getOriginalFilename().equals("")){
                String ext = StringUtil.lowerCase(StringUtil.lowerCase(StringUtil.fileExtention(fileName)));
                if(!(ext.equals(typeHwp) || ext.equals(typeDoc) || ext.equals(typeDocx) || ext.equals(typeXls) || ext.equals("xlsx") ||
                     ext.equals(typePdf) || ext.equals(typePpt) || ext.equals("pptx")|| ext.equals(typeGif) || ext.equals("ncf") ||
                     ext.equals(typePng) || ext.equals(typeJpg) || ext.equals(typeJpeg) || ext.equals("txt") || ext.equals("zip"))){
                    //resultString += "E01:허용되지 않는 확장자;"+kdhecMessageSource.getMessage("FILE.0002")+";";
                    //resultString += "E01:허용되지 않는 확장자;";
                    return "E01";
                }else{
                    String extention = StringUtil.fileExtention(uploadFile.getOriginalFilename());
                    String tmpFileName = StringUtil.getDocNo() + "." + extention;

                    if(uploadFile.getSize() > 20480000){
                        //resultString += "E02:파일사이즈 초과;";
                        return "E02";
                    }else{
                        saveFile(uploadFile, realFolder, tmpFileName);
                        resultString += "S:"+fileName+":"+tmpFileName+":"+uploadFile.getSize()+";";
                    }
                }
            }else{
                //resultString += "E03:"+kdhecMessageSource.getMessage("FILE.0001")+";";
            }

        }catch(Exception e){
            logger.error(strMessage, e);
        }
        return resultString;
    }

	public static String uploadFileEdt(HttpServletRequest req, String[] paramVal){
        MultipartHttpServletRequest mhr = null;

        String fileName     = "";
        String realFolder   = "";
        String resultString = "";

        mhr = (MultipartHttpServletRequest) req;
        try{
            if(StringUtil.getSystemArea().equals(strLocal)){
                realFolder = Constant.EDT_UPLOAD_LOCAL_PATH;
            }else if(StringUtil.getSystemArea().equals(strTest)){
                realFolder = Constant.EDT_UPLOAD_TEST_PATH;
            }else{
                realFolder = Constant.EDT_UPLOAD_REAL_PATH;
            }

            String corp_cd = "";
	        try {
				corp_cd = SessionInfo.getSess_corp_cd(req);
			} catch (SessionException e1) {
				// TODO Auto-generated catch block
				logger.error(strMessage, e1);
			}

            String realUrl = strHttp + req.getServerName() + realFolder;
            realFolder += "/"+corp_cd+"/"+paramVal[1];
			realUrl    += "/"+corp_cd+"/"+paramVal[1];

            MultipartFile uploadFile = mhr.getFile(paramVal[0]);

            fileName = uploadFile.getOriginalFilename();

            if(!uploadFile.getOriginalFilename().equals("")){
                String ext = StringUtil.lowerCase(StringUtil.lowerCase(StringUtil.fileExtention(fileName)));
                if(!(ext.equals(typeGif) || ext.equals(typePng) || ext.equals(typeJpg))){
                    //resultString += "E01:허용되지 않는 확장자;"+kdhecMessageSource.getMessage("FILE.0002")+";";
                    //resultString += "E01:허용되지 않는 확장자;";
                    return "E01";
                }else{
                    String extention = StringUtil.fileExtention(uploadFile.getOriginalFilename());
                    String tmpFileName = StringUtil.getDocNo() + "." + extention;

                    if(uploadFile.getSize() > 20480000){
                        //resultString += "E02:파일사이즈 초과;";
                        return "E02";
                    }else{
                        saveFile(uploadFile, realFolder, tmpFileName);
                        resultString += "S:"+fileName+":"+tmpFileName+":"+uploadFile.getSize()+";";
                    }
                }
            }else{
                //resultString += "E03:"+kdhecMessageSource.getMessage("FILE.0001")+";";
            }

        }catch(Exception e){
            logger.error(strMessage, e);
        }
        return resultString;
    }

	public static String[] uploadFileView(HttpServletRequest req, HttpServletResponse res, String[] paramVal){
		MultipartHttpServletRequest mr = null;
		MultipartFile uploadFile = null;

		String fileName    = "";
		String oldFileName = "";
		String realFolder  = "";
		String[] result = new String[6];
		mr = (MultipartHttpServletRequest) req;
		try{
			logger.info("paramVal[0]="+paramVal[0]);
			uploadFile = mr.getFile(paramVal[0]);
			logger.info("uploadFile="+uploadFile);

			logger.info(strStar);
			logger.info("Constant.UPLOAD_REAL_PATH="+Constant.UPLOAD_REAL_PATH);
			logger.info(strStar);

			if(StringUtil.getSystemArea().equals(strLocal)){
				realFolder = Constant.EDT_UPLOAD_LOCAL_PATH;
			}else if(StringUtil.getSystemArea().equals(strTest)){
				realFolder = Constant.EDT_UPLOAD_TEST_PATH;
			}else{
				realFolder = Constant.EDT_UPLOAD_REAL_PATH;
			}

			fileName = uploadFile.getOriginalFilename();

			logger.info("fileName11111="+fileName);

			String corp_cd = "";
	        try {
				corp_cd = SessionInfo.getSess_corp_cd(req);
			} catch (SessionException e1) {
				// TODO Auto-generated catch block
				logger.error(strMessage, e1);
			}

			String realUrl = strHttp + req.getServerName() + realFolder;
			if(!uploadFile.getOriginalFilename().equals("")){
				oldFileName = mr.getParameter(paramVal[1]);
				realFolder += "/" + corp_cd + "/" + paramVal[2];
				realUrl    += "/" + corp_cd + "/" + paramVal[2];

				String ext = StringUtil.lowerCase(StringUtil.lowerCase(StringUtil.fileExtention(fileName)));
				if(!(ext.equals(typeGif) || ext.equals(typePng) || ext.equals("bmp")  || ext.equals(typeJpg) || ext.equals(typeJpeg))){
					//result = null;
					result[4] = HncisMessageSource.getMessage("FILE.0002");
				}else{
					File file = new File(realFolder+"/"+fileName);
					String extention = StringUtil.fileExtention(uploadFile.getOriginalFilename());
					//String tmpFileName = uploadFile.getOriginalFilename().substring(0,uploadFile.getOriginalFilename().lastIndexOf("."));

					/*
					while(file.exists()){
						Random rn   = new Random();
						tmpFileName = tmpFileName+rn.nextInt(100) + "." + extention;
						file        = new File(tmpFileName);
						fileName    = tmpFileName;
					}
					*/

					String tmpFileName = StringUtil.getFileNm() + "." + extention;

					if(oldFileName == null || oldFileName.equals("")){
						oldFileName = "";
					}
					logger.info(strUploadFile+uploadFile);
					logger.info(strRealFolder+realFolder);
					logger.info(strFileName+fileName);
					logger.info("tmpFileName:"+tmpFileName);
					logger.info(strOldFileName+oldFileName);
					logger.info("fileSize:"+uploadFile.getSize());
					logger.info("path:"+realFolder+"/"+fileName);
					//logger.info("exists:"+uploadFile.);



					if(uploadFile.getSize() > 10240000){
						result[4] = HncisMessageSource.getMessage("FILE.0003");
					}else{
						saveFile(uploadFile, realFolder, tmpFileName, oldFileName);
						result[0] = tmpFileName;
						result[1] = realUrl;
						result[2] = realFolder;
						result[3] = String.valueOf(uploadFile.getSize());
						result[4] = HncisMessageSource.getMessage("FILE.0000");
						result[5] = fileName;
					}
				}//END ELSE
			}else{
				result[4] = HncisMessageSource.getMessage("FILE.0001");
			}
		}catch(Exception e){
			logger.error(strMessage, e);
		}

		return result;
	}
	
	public static void copyFile(File sourceF, File targetF){
			if(!targetF.exists()){
				targetF.mkdir();
			}
			File[] ff = sourceF.listFiles();
			for (File file : ff) {
				File temp = new File(targetF.getAbsolutePath() + File.separator + file.getName());
				if(file.isDirectory()){
					temp.mkdir();
					copyFile(file, temp);
				} else {
					FileInputStream fis = null;
					FileOutputStream fos = null;
					try {
						fis = new FileInputStream(file);
						fos = new FileOutputStream(temp) ;
						byte[] b = new byte[4096];
						int cnt = 0;
						while((cnt=fis.read(b)) != -1){
							fos.write(b, 0, cnt);
						}
					} catch (Exception e) {
						logger.error(strMessage, e);
					} finally{
						try {
							fis.close();
							fos.close();
						} catch (IOException e) {
							logger.error(strMessage, e);
						}
						
					}
				}
			}
			
	 }
	
	public static boolean isDownloadFile(String corp_cd){

		String filePath    = "";
		String realFolder  = "";

		if(StringUtil.getSystemArea().equals(strLocal)){
			realFolder = Constant.EDT_UPLOAD_LOCAL_PATH;
		}else if(StringUtil.getSystemArea().equals(strTest)){
			realFolder = Constant.EDT_UPLOAD_TEST_PATH;
		}else{
			realFolder = Constant.EDT_UPLOAD_REAL_PATH;
		}
		filePath = realFolder + "/" + corp_cd + "/" + "shortcut/GAS.url";
		
		File file = new File(filePath);
		return file.exists();
	}
}
