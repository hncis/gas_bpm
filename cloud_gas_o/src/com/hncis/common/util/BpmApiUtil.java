package com.hncis.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.uengine.bpmutil.util.BpmUtil;

import java.text.SimpleDateFormat;

public class BpmApiUtil {
    private transient static Log logger = LogFactory.getLog(BpmApiUtil.class.getClass());
	private static final String approvalLine = "APPROVALLINE";
	private static final String strMessage = "messege";
	
	public static String sendSaveTask(String pCode, String docNo, String sCode, String userId, String roleCd, List<String> aList, List<String> mList){
		String returnMessage = "";
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info("Start time : " + strDT);
		try {
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
			String bizKey = docNo;			//신청서 번호
			String statusCode = sCode;		//액티비티 코드 (휴양소신청서작성) - 프로세스 정의서 참조
			String loginUserId = userId;	//로그인 사용자 아이디

			//역할정보
			Map<String, List<String>> roleMap = new HashMap<String, List<String>>();

			roleMap.put(roleCd, mList); //담당자 - 프로세스 정의서 참조

			//프로세스 변수 정보
			Map<String, List<String>> varMap = new HashMap<String, List<String>>();
			List<String> varList = new ArrayList<String>();
			if (aList.size() > 0) {
				for(int i = 0 ; i < aList.size() ; i++){
				varList.add(aList.get(i));
				}
				varMap.put(approvalLine, varList); //결재라인생성여부 - 프로세스 정의서 참조

			} else {
				varMap = null;
			}
			returnMessage = BpmUtil.saveTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap  );

		} catch (IOException e) {
			logger.error(strMessage, e);
		}
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		strDT = dayTime.format(new Date(time)); 
		logger.info("End time : " + strDT);
		return returnMessage;

	}

	public static String sendCompleteTask(String pCode, String docNo, String sCode, String userId, String roleCd,List<String> aList, List<String> mList){
		String returnMessage = "";
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info("Start time : " + strDT);
		try {
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
			String bizKey = docNo;			//신청서 번호
			String statusCode = sCode;		//액티비티 코드 (휴양소신청서작성) - 프로세스 정의서 참조
			String loginUserId = userId;	//로그인 사용자 아이디
			String comment = null;

			//역할정보
			Map<String, List<String>> roleMap = new HashMap<String, List<String>>();

			roleMap.put(roleCd, mList); //담당자 - 프로세스 정의서 참조

			//프로세스 변수 정보
			Map<String, List<String>> varMap = new HashMap<String, List<String>>();
			List<String> varList = new ArrayList<String>();
			
			if (aList.size() > 0) {
				for(int i = 0 ; i < aList.size() ; i++){
				varList.add(aList.get(i));
				}
				varMap.put(approvalLine, varList); //결재라인생성여부 - 프로세스 정의서 참조

			} else {
				varMap = null;
			}

			returnMessage = BpmUtil.completeTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap, comment );

		} catch (IOException e) {
			logger.error(strMessage, e);
		}
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		strDT = dayTime.format(new Date(time)); 
		logger.info("End time : " + strDT);
		return returnMessage;

	}
	
	
	public static String sendFinalCompleteTask(String pCode, String docNo, String sCode, String userId){
		String returnMessage = "";
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info("Start time : " + strDT);
		try {
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
			String bizKey = docNo;			//신청서 번호
			String statusCode = sCode;		//액티비티 코드 (휴양소신청서작성) - 프로세스 정의서 참조
			String loginUserId = userId;	//로그인 사용자 아이디
			String comment = null;

			//역할정보
			Map<String, List<String>> roleMap = null;

			//프로세스 변수 정보
			Map<String, List<String>> varMap = null;

			returnMessage = BpmUtil.completeTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap, comment );

		} catch (IOException e) {
			logger.error(strMessage, e);
		}
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		strDT = dayTime.format(new Date(time)); 
		logger.info("End time : " + strDT);
		return returnMessage;

	}

	public static String sendRejectTask(String pCode, String docNo, String sCode, String userId, String roleCd, List<String> aList, List<String> mList){
		String returnMessage = "";
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info("Start time : " + strDT);
		try {
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 - 프로세스 정의서 참조
			String bizKey = docNo;			//신청서 번호
			String statusCode = sCode;		//액티비티 코드 - 프로세스 정의서 참조
			String loginUserId = userId;	//로그인 사용자 아이디
			String comment = null;

			//역할정보
			Map<String, List<String>> roleMap = new HashMap<String, List<String>>();
			roleMap.put(roleCd, mList); //담당자 - 프로세스 정의서 참조

			//프로세스 변수 정보
			Map<String, List<String>> varMap = new HashMap<String, List<String>>();
			List<String> varList = new ArrayList<String>();
			if (aList.size() > 0) {
				for(int i = 0 ; i < aList.size() ; i++){
				varList.add(aList.get(i));
				}
				varMap.put(approvalLine, varList); //결재라인생성여부 - 프로세스 정의서 참조

			} else {
				varMap = null;
			}
			returnMessage = BpmUtil.rejectTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap, comment );

		} catch (IOException e) {
			logger.error(strMessage, e);
		}
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		strDT = dayTime.format(new Date(time)); 
		logger.info("End time : " + strDT);
		return returnMessage;

	}
	
	public static String sendCollectTask(String pCode, String docNo, String sCode, String userId, String roleCd, List<String> aList, List<String> mList){
		String returnMessage = "";
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info("Start time : " + strDT);
		try {
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 - 프로세스 정의서 참조
			String bizKey = docNo;			//신청서 번호
			String statusCode = sCode;		//액티비티 코드 - 프로세스 정의서 참조
			String loginUserId = userId;	//로그인 사용자 아이디
			String comment = null;

			//역할정보 – 불필요할 경우, roleMap  = null 로 셋팅
			Map<String, List<String>> roleMap = new HashMap<String, List<String>>();
			roleMap.put(roleCd, mList); //담당자 - 프로세스 정의서 참조
				
			//프로세스 변수 정보 – 불필요할 경우, varMap = null 로 셋팅
			Map<String, List<String>> varMap = new HashMap<String, List<String>>();
			List<String> varList = new ArrayList<String>();
			if (aList.size() > 0) {
				for(int i = 0 ; i < aList.size() ; i++){
				varList.add(aList.get(i));
				}
				varMap.put(approvalLine, varList); //결재라인생성여부 - 프로세스 정의서 참조

			} else {
				varMap = null;
			}

			returnMessage = BpmUtil.collectTask(processCode, bizKey, statusCode, loginUserId, roleMap, varMap);

		} catch (IOException e) {
			logger.error(strMessage, e);
		}
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		strDT = dayTime.format(new Date(time)); 
		logger.info("End time : " + strDT);
		return returnMessage;

	}
	
	public static String sendDeleteAndRejectTask(String pCode, String docNo, String sCode, String userId){
		String returnMessage = "";
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info("Start time : " + strDT);
		try {
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드  - 프로세스 정의서 참조
			String bizKey = docNo;	        //신청서 번호
			String statusCode = sCode;	    //액티비티 코드 - 프로세스 정의서 참조
			String loginUserId = userId;	//로그인 사용자 아이디
			
			returnMessage = BpmUtil.completeProcess(processCode, bizKey, statusCode, loginUserId);

		} catch (IOException e) {
			logger.error(strMessage, e);
		}
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		strDT = dayTime.format(new Date(time)); 
		logger.info("End time : " + strDT);
		return returnMessage;

	}
	
	public static String sendRestoreTask(String pCode, String docNo, String sCode, String userId){
		String returnMessage = "";
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info("Start time : " + strDT);
		try {
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드  - 프로세스 정의서 참조
			String bizKey = docNo;	        //신청서 번호
			String statusCode = sCode;	    //액티비티 코드 - 프로세스 정의서 참조
			String loginUserId = userId;	//로그인 사용자 아이디
			
			returnMessage = BpmUtil.compensateTask(processCode, bizKey, statusCode, loginUserId);

		} catch (IOException e) {
			logger.error(strMessage, e);
		}
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		strDT = dayTime.format(new Date(time)); 
		logger.info("End time : " + strDT);
		return returnMessage;

	}
	
	
}
