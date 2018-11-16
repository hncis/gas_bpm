package com.hncis.leave.manager.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.common.application.ApprovalGasc;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.leave.dao.LeaveDao;
import com.hncis.leave.manager.LeaveManager;
import com.hncis.leave.vo.BgabGasclv01Dto;
import com.hncis.leave.vo.BgabGasclv02Dto;
import com.hncis.leave.vo.BgabGasclv03Dto;

@Service("leaveManagerImpl")
public class LeaveManagerImpl implements LeaveManager{
    private transient Log logger = LogFactory.getLog(getClass());

    private static final String pCode = "P-E-006";
    private static final String sCode = "GASBZ01560010";
    private static final String rCode = "GASROLE01560030";
    private static final String adminID = "10000001";

	@Autowired
	public LeaveDao leaveDao;
	
	@Autowired
	public CommonJobDao commonJobDao;

	@Override
	public BgabGasclv01Dto selectLvToRemainDaysInfo(BgabGasclv01Dto dto) {
		
		BgabGasclv01Dto rtnDto = null;
		
		try{
		
			rtnDto = leaveDao.selectLvToJoinYearsInfo(dto);
			
			String currYYYY	= CurrentDateTime.getDate().substring(0,4);
			String joinYYYY = rtnDto.getJoin_ymd().substring(0,4);
			int currMMDD = Integer.parseInt(CurrentDateTime.getDate().substring(4,8));
			int joinMMDD = Integer.parseInt(rtnDto.getJoin_ymd().substring(4,8));
			String strJoinMMDD = rtnDto.getJoin_ymd().substring(4,8);
			//입사년차
			int wYear = calculateWorkYear(rtnDto.getJoin_ymd());
			rtnDto.setJoin_year(Integer.toString(wYear));
			
			//잔여일수
			dto.setJoin_year("year"+wYear);
			BgabGasclv01Dto rmInfo = leaveDao.selectLvToRemainDaysInfo(dto);
			
			if(rmInfo == null){
				rtnDto.setRm_days("0");
			}
			else{
				rtnDto.setRm_days(StringUtil.isNullToString(rmInfo.getRm_days()).equals("") ? "0" : rmInfo.getRm_days());
			}
			
			
			boolean flagDiv = false;
			if(!currYYYY.equals(joinYYYY)){
				if(currMMDD >= joinMMDD){
					flagDiv = true;
				}
			}
			
			String to_yyyy = currYYYY;
			String fr_yyyy = currYYYY+1;
			
			if(!flagDiv){
				to_yyyy = Integer.parseInt(currYYYY) - 1 + "";
				fr_yyyy = currYYYY;
			}
			
			dto.setFr_ymd(to_yyyy + strJoinMMDD);
			dto.setTo_ymd(fr_yyyy + strJoinMMDD);
			
			
			int useDays = leaveDao.selectLvToUseDaysInfo(dto);
			rtnDto.setUse_days(Integer.toString(useDays));
			
		}catch (Exception e) {
			logger.error("messege", e);
		}
		
		
		return rtnDto;
	}
	
	public int calculateWorkYear(String jYmd){
		int wYear = 1;
		
		String currYYYY	= CurrentDateTime.getDate().substring(0,4);
		String joinYYYY =jYmd.substring(0,4);
		int currMMDD = Integer.parseInt(CurrentDateTime.getDate().substring(4,8));
		int joinMMDD = Integer.parseInt(jYmd.substring(4,8));
		
		
		//입사년차
		boolean flagDiv = false;
				
		if(currYYYY.equals(joinYYYY)){
			wYear = 1;
		}else{
			
			if(currMMDD >= joinMMDD){
				flagDiv = true;
			}
			
			int joinYear = Integer.parseInt(currYYYY) - Integer.parseInt(joinYYYY);
			
			if(flagDiv){
				joinYear = joinYear +1 ;
			}
			
			wYear = joinYear;
			
		}
		
		return wYear;
	}
	
	
	@Override
	public BgabGasclv01Dto selectLvToRequestInfo(BgabGasclv01Dto dto) {
		return leaveDao.selectLvToRequestInfo(dto);
	}

	@Override
	public List<BgabGasclv01Dto> selectLvToHistoryList(BgabGasclv01Dto dto) {
		
		
		return leaveDao.selectLvToHistoryList(dto);
	}
	
	public CommonMessage setApproval(BgabGasclv01Dto keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req){
		BgabGascz002Dto userParam = new BgabGascz002Dto();
		userParam.setCorp_cd(keyParamDto.getCorp_cd());
		userParam.setXusr_empno(keyParamDto.getEeno());
		BgabGascz002Dto userInfo = commonJobDao.getXusrInfo(userParam);
		
		appInfo.setDoc_no(keyParamDto.getDoc_no());			// 문서번호
		appInfo.setSystem_code("LV");						// 시스템코드
		appInfo.setTable_name("bgab_gasclv01");				// 업무 테이블이름
		appInfo.setRpts_eeno(userInfo.getXusr_empno());		// 상신자 사번
		appInfo.setRpts_dept(userInfo.getXusr_dept_code());	// 상신자 부서코드
		appInfo.setStep_code(userInfo.getXusr_step_code());	// 상신자 직위코드
		appInfo.setRpts_eeno_nm(userInfo.getXusr_name());	// 상신자 성명
		appInfo.setStep_nm(userInfo.getXusr_step_name());	// 직위 이름
		appInfo.setTitle_nm(HncisMessageSource.getMessage("xlv"));							// 업무 이름		
		appInfo.setAppType("");								// 전결권자 업무
		appInfo.setMax_level(5);							// 국내 결재 LEVEL
		appInfo.setCorp_cd(userInfo.getCorp_cd());
		
		// 결재요청
		CommonApproval commonApproval = ApprovalGasc.setApprovalRequestUseYn(appInfo);
		
		if(commonApproval.getResult().equals("Z")){
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (연차 프로세스) - 프로세스 정의서 참조
			String bizKey = keyParamDto.getDoc_no();	//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (연차신청서작성) - 프로세스 정의서 참조
			String loginUserId = keyParamDto.getEeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = rCode;   //연차 담당자 역할코드
			
			//역할정보
			List<String> approveList = commonApproval.getApproveList();
			List<String> managerList = new ArrayList<String>();
			managerList.add(adminID);

			BpmApiUtil.sendCompleteTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList);
			
			message.setCode(keyParamDto.getPgs_st_cd());
			message.setCode1(keyParamDto.getIf_id());
		}else{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setErrorCode("E");
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0001"));
			message.setCode("");
			message.setCode1("");
		}
		
		return message;
	}
	
	public CommonMessage setApprovalCancel(BgabGasclv01Dto keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req){
		CommonApproval commonApproval = null;
		
		if("".equals(StringUtil.isNullToString(keyParamDto.getIf_id()))){
			leaveDao.updateInfoLvToRequestCancel2(keyParamDto);
			
			// BPM API호출
			String processCode = pCode; 			//프로세스 코드 (연차 프로세스) - 프로세스 정의서 참조
			String bizKey = keyParamDto.getDoc_no();			//신청서 번호
			String statusCode = sCode;   		//액티비티 코드 (연차신청서작성) - 프로세스 정의서 참조
			String loginUserId = keyParamDto.getUpdr_eeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = rCode;  //연차 담당자 역할코드
			
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add(adminID);
			
			BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );

			message.setMessage(HncisMessageSource.getMessage("REQUEST.0002"));
		}else{
			appInfo.setIf_id(keyParamDto.getIf_id());
			appInfo.setTable_name("bgab_gasclv01");
			appInfo.setCorp_cd(keyParamDto.getCorp_cd());
			commonApproval = ApprovalGasc.setApprovalCancelProcess(appInfo);
			
			if(commonApproval.getResult().equals("Z")){
				leaveDao.updateInfoLvToRequestCancel(keyParamDto);
				
				// BPM API호출
				String processCode = pCode; 			//프로세스 코드 (연차 프로세스) - 프로세스 정의서 참조
				String bizKey = keyParamDto.getDoc_no();			//신청서 번호
				String statusCode = sCode;   		//액티비티 코드 (연차신청서작성) - 프로세스 정의서 참조
				String loginUserId = keyParamDto.getUpdr_eeno();	//로그인 사용자 아이디
				String comment = null;
				String roleCode = rCode;  //연차 담당자 역할코드
				
				//역할정보
				List<String> approveList = new ArrayList<String>();
				List<String> managerList = new ArrayList<String>();
				managerList.add(adminID);
				
				BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );

				message.setMessage(HncisMessageSource.getMessage("REQUEST.0002"));
				
			}else{
				message.setMessage(commonApproval.getMessage());
			}
		}
		
		return message;
	}
	
	public CommonMessage updateLvToRequestForConfirm(BgabGasclv01Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt = leaveDao.updateLvToRequestForConfirm(dto);
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
			message.setCode1("Y");
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (연차 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01560030";	//액티비티 코드 (연차 담당자 확인) - 프로세스 정의서 참조
			String loginUserId = dto.getAcpc_eeno();	//로그인 사용자 아이디
			String comment = null;
			
			BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
			
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
			message.setCode1("N");
		}
		return message;
	}
	
	public CommonMessage updateLvToRequestForReject(BgabGasclv01Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt = leaveDao.updateLvToRequestForReject(dto);
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
			message.setCode1("Y");
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (연차 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01560030";	//액티비티 코드 (연차 담당자 확인) - 프로세스 정의서 참조
			String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
			
			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
			
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
			message.setCode1("N");
		}
		return message;
	}
	
	
	
	
	@Override
	public CommonMessage deleteLvToRequest(BgabGasclv01Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt1 = leaveDao.deleteLvToRequest(dto);
			int cnt2 = leaveDao.deleteLvToRequestDtl(dto);
			message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
			message.setCode1("Y");
			
			// BPM API호출
			String processCode = pCode; 			//프로세스 코드 (연차 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();			//신청서 번호
			String statusCode = sCode;   		//액티비티 코드 (연차신청서작성) - 프로세스 정의서 참조
			String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
	
			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
								
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("DELETE.0001"));
			message.setCode1("N");
		}
		return message;
	}
	
	@Override
	public int selectCountLvToReqList(BgabGasclv01Dto dto) {
		return leaveDao.selectCountLvToReqList(dto);
	}

	@Override
	public List<BgabGasclv01Dto> selectLvToReqList(BgabGasclv01Dto dto) {
		
		List<BgabGasclv01Dto> list = leaveDao.selectLvToReqList(dto);
		
		for(BgabGasclv01Dto vo : list){
			int wYear = calculateWorkYear2(vo.getJoin_ymd(), vo.getFr_ymd());
			vo.setJoin_year(wYear+HncisMessageSource.getMessage("num_xlv"));
		}
		
		return list;
	}
	public int calculateWorkYear2(String jYmd, String frYmd){
		int wYear = 1;
		
		String currYYYY	= frYmd.substring(0,4);
		String joinYYYY =jYmd.substring(0,4);
		int currMMDD = Integer.parseInt(frYmd.substring(4,8));
		int joinMMDD = Integer.parseInt(jYmd.substring(4,8));
		
		
		//입사년차
		boolean flagDiv = false;
				
		if(currYYYY.equals(joinYYYY)){
			wYear = 1;
		}else{
			
			if(currMMDD >= joinMMDD){
				flagDiv = true;
			}
			
			int joinYear = Integer.parseInt(currYYYY) - Integer.parseInt(joinYYYY);
			
			if(flagDiv){
				joinYear = joinYear +1 ;
			}
			
			wYear = joinYear;
			
		}
		
		return wYear;
	}
	
	
	@Override
	public CommonMessage insertLvToRequestInfo(BgabGasclv01Dto dto) {
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssSSS"); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info("Start time : " + strDT);
		
		CommonMessage message = new CommonMessage();
		
		try{
			
			BgabGasclv01Dto joinDto = leaveDao.selectLvToJoinYearsInfo(dto);
			
			int lvYear = calculateWorkYear2(joinDto.getJoin_ymd(), dto.getFr_ymd());
			dto.setLv_year(Integer.toString(lvYear));
			
			List<String> dateArr = CurrentDateTime.getDateFrToList(dto.getFr_ymd(),dto.getTo_ymd());
			
			boolean chkFlag = true;
			
			if(dto.getReq_type().equals("A")){
				
				//잔여일수
				dto.setJoin_year("year"+lvYear);
				BgabGasclv01Dto rmInfo = leaveDao.selectLvToRemainDaysInfo(dto);
				
				if(lvYear == 1){
					
					int diffCnt = (int) CurrentDateTime.diffOfDate(joinDto.getJoin_ymd(), dto.getFr_ymd());
					
					if(diffCnt < Integer.parseInt(rmInfo.getUse_day())){
						
						String canUseDay = CurrentDateTime.getDate(joinDto.getJoin_ymd(), Integer.parseInt(rmInfo.getUse_day()));
						
						canUseDay = StringUtil.getMaskDate(canUseDay, "-");
						
						chkFlag = false;
						message.setCode1("N");
						message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0061")+ rmInfo.getUse_day()+HncisMessageSource.getMessage("day")+"("+ canUseDay +")"+HncisMessageSource.getMessage("MSG.VAL.0062"));
					}
					
				}

				if(chkFlag){
					
					int rmDays = Integer.parseInt(rmInfo.getRm_days());
					
					String currYYYY	= CurrentDateTime.getDate().substring(0,4);
					String joinYYYY = joinDto.getJoin_ymd().substring(0,4);
					int currMMDD = Integer.parseInt(CurrentDateTime.getDate().substring(4,8));
					int joinMMDD = Integer.parseInt(joinDto.getJoin_ymd().substring(4,8));
					String strJoinMMDD = joinDto.getJoin_ymd().substring(4,8);
					
					boolean flagDiv = false;
					if(!currYYYY.equals(joinYYYY)){
						if(currMMDD >= joinMMDD){
							flagDiv = true;
						}
					}
					
					String to_yyyy = currYYYY;
					String fr_yyyy = String.valueOf(Integer.parseInt(currYYYY)+1);
					
					if(!flagDiv){
						to_yyyy = Integer.parseInt(currYYYY) - 1 + "";
						fr_yyyy = currYYYY;
					}
					
					BgabGasclv01Dto dto1 = new BgabGasclv01Dto();
					
					dto1.setFr_ymd(to_yyyy + strJoinMMDD);
					dto1.setTo_ymd(fr_yyyy + strJoinMMDD);
					dto1.setEeno(dto.getEeno());
					dto1.setDoc_no(dto.getDoc_no());
					dto1.setCorp_cd(dto.getCorp_cd());
					dto1.setDoc_no(dto.getDoc_no());
					
					int useDays = leaveDao.selectLvToUseDaysInfo1(dto1);
					int reqDays = dateArr.size();
					
					int chkCnt = rmDays - useDays - reqDays;
					
					dto.setTot_days(Integer.toString(useDays+reqDays));
					dto.setRm_days(Integer.toString(chkCnt));
					if(chkCnt < 0){
						chkFlag = false;
						message.setCode1("N");
						message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0063"));
					}
					
				}
				
			}
			
			if(chkFlag){
				if(dto.getDoc_no().equals("")){
					String docNo = StringUtil.getDocNo();
					dto.setDoc_no(docNo);
				}
//				dto.setTot_days("0");
//				dto.setRm_days("0");
				int cnt = leaveDao.insertLvToRequestInfo(dto);
				
				
				List<BgabGasclv02Dto> dtlList = new ArrayList<BgabGasclv02Dto>();
				BgabGasclv02Dto dtlDto = null;
				for(int i=0 ; i< dateArr.size() ; i++){
					dtlDto = new BgabGasclv02Dto();
					dtlDto.setDoc_no(dto.getDoc_no());
					dtlDto.setEeno(dto.getEeno());
					dtlDto.setLv_ymd(dateArr.get(i));
					dtlDto.setCorp_cd(dto.getCorp_cd());
					dtlList.add(dtlDto);
				}
				
				int cnt1 = leaveDao.deleteLvToRequestInfoDtl(dtlList);
				int cnt2 = leaveDao.insertToRequestInfoDtl(dtlList);
				
				message.setCode1("Y");
				message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
				message.setCode(dto.getDoc_no());
				
				// BPM API호출
				String processCode = pCode; 	//프로세스 코드 (연차 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (연차신청서작성) - 프로세스 정의서 참조
				String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
				String comment = null;
				String roleCode = rCode;  //연차 담당자 역할코드
		
				//역할정보
				List<String> approveList = new ArrayList<String>();
				List<String> managerList = new ArrayList<String>();
				managerList.add(adminID);
				
				BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );

			}
			
			
		}catch (Exception e) {
			logger.error("messege", e);
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssSSS"); 
		strDT = dayTime.format(new Date(time)); 
		logger.info("End time : " + strDT);
		
		return message;
	}
	
	@Override
	public void updateLvToLeaveDayInfo(BgabGasclv03Dto dto) {
		
		int cnt = leaveDao.selectCountLvToLeaveDayInfo(dto);
		if(cnt == 0){
			leaveDao.insertLvToLeaveDayInfo(dto);
		}else{
			leaveDao.updateLvToLeaveDayInfo(dto);
		}
		
		
	}

	@Override
	public BgabGasclv03Dto selectLvToLeaveDayInfo(BgabGasclv03Dto dto) {
		return leaveDao.selectLvToLeaveDayInfo(dto);
	}



}
