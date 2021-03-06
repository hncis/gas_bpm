package com.hncis.restCenter.manager.impl;

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
import com.hncis.common.application.SessionInfo;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.restCenter.dao.RestCenterDao;
import com.hncis.restCenter.manager.RestCenterManager;
import com.hncis.restCenter.vo.BgabGascrc01Dto;
import com.hncis.restCenter.vo.BgabGascrc02Dto;
import com.hncis.restCenter.vo.BgabGascrc03Dto;
import com.hncis.restCenter.vo.BgabGascrc04Dto;
import com.hncis.restCenter.vo.BgabGascrc05Dto;
import com.hncis.restCenter.vo.BgabGascrc06Dto;

@Service("restCenterManagerImpl")
public class RestCenterManagerImpl  implements RestCenterManager{
    private transient Log logger = LogFactory.getLog(getClass());

    private static final String pCode = "P-B-001";
    private static final String sCode = "GASBZ01210010";
    private static final String rCode = "GASROLE01210030";
    private static final String adminID = "10000001";

	@Autowired
	public RestCenterDao restCenterDao;
	
	@Autowired
	public CommonJobDao commonJobDao;
	
	@Override
	public List<BgabGascrc01Dto> selectRcToRestCenterCombo(BgabGascrc01Dto vo) {
		return restCenterDao.selectRcToRestCenterCombo(vo);
	}

	@Override
	public List<BgabGascrc03Dto> selectRcToRoomCombo(BgabGascrc03Dto vo) {
		return restCenterDao.selectRcToRoomCombo(vo);
	}
	
	@Override
	public void saveRcToRestCenterList(List<BgabGascrc01Dto> iList, List<BgabGascrc01Dto> uList) {
		int iCnt = restCenterDao.insertRcToRestCenterList(iList);
		int uCnt = restCenterDao.updateRcToRestCenterList(uList);
	}

	@Override
	public List<BgabGascrc01Dto> selectRcListToRestCenter(BgabGascrc01Dto vo) {
		return restCenterDao.selectRcListToRestCenter(vo);
	}
	
	
	@Override
	public void saveRcToCalList(List<BgabGascrc02Dto> iList, List<BgabGascrc02Dto> uList) {
		try{
		int iCnt = restCenterDao.insertRcToCalList(iList);
		int uCnt = restCenterDao.updateRcToCalList(uList);
		}catch(Exception e){
			logger.error("messege", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	@Override
	public List<BgabGascrc02Dto> selectRcListToCal(BgabGascrc02Dto vo) {
		
		String [] day_name = {"", HncisMessageSource.getMessage("sun")
				, HncisMessageSource.getMessage("mon"), HncisMessageSource.getMessage("tue")
				, HncisMessageSource.getMessage("wed"), HncisMessageSource.getMessage("thu")
				, HncisMessageSource.getMessage("fri"), HncisMessageSource.getMessage("sat") };
		
		int maxDay	   = CurrentDateTime.getMonthDays(vo.getCal_ymd()+"01");
		
		vo.setFr_ymd(vo.getCal_ymd() + "01");
		vo.setTo_ymd(vo.getCal_ymd() + maxDay);
		
		List<BgabGascrc02Dto> list = restCenterDao.selectRcListToCal(vo);
		
		for(BgabGascrc02Dto rtnVo : list){
			rtnVo.setCal_day(day_name[Integer.parseInt(rtnVo.getCal_day())]);
		}
		
		return list;
	}
	
	@Override
	public void saveRcToRoomList(List<BgabGascrc03Dto> iList, List<BgabGascrc03Dto> uList) {
		int iCnt = restCenterDao.insertRcToRoomList(iList);
		int uCnt = restCenterDao.updateRcToRoomList(uList);
	}

	@Override
	public List<BgabGascrc03Dto> selectRcListToRoom(BgabGascrc03Dto vo) {
		return restCenterDao.selectRcListToRoom(vo);
	}
	
	@Override
	public void saveRcToRateList(List<BgabGascrc04Dto> iList, List<BgabGascrc04Dto> uList) {
		try{
			restCenterDao.insertRcToRateList(iList);
		}catch(Exception e){
			restCenterDao.updateRcToRateList(uList);
		}
	}

	@Override
	public List<BgabGascrc04Dto> selectRcListToRate(BgabGascrc04Dto vo) {
		return restCenterDao.selectRcListToRate(vo);
	}

	@Override
	public void deleteRcToRestCenterList(List<BgabGascrc01Dto> dList) {
		restCenterDao.deleteRcToRestCenterList(dList);
	}

	@Override
	public void deleteRcToCalList(List<BgabGascrc02Dto> dList) {
		restCenterDao.deleteRcToCalList(dList);
	}

	@Override
	public void deleteRcToRoomList(List<BgabGascrc03Dto> dList) {
		restCenterDao.deleteRcToRoomList(dList);
	}

	@Override
	public void deleteRcToRateList(List<BgabGascrc04Dto> dList) {
		restCenterDao.deleteRcToRateList(dList);
	}

	@Override
	public BgabGascrc05Dto selectRcToRequestCountInfo(BgabGascrc05Dto dto) {
		return restCenterDao.selectRcToRequestCountInfo(dto);
	}

	@Override
	public void updateToRequestCountInfo(BgabGascrc05Dto dto) {
		restCenterDao.updateToRequestCountInfo(dto);
	}

	@Override
	public BgabGascrc06Dto selectRcToRemainDaysInfo(BgabGascrc06Dto dto) {
		dto.setReq_yy(CurrentDateTime.getYear());
		return restCenterDao.selectRcToRemainDaysInfo(dto);
	}

	@Override
	public BgabGascrc06Dto selectRcToUseAmt(BgabGascrc06Dto dto) {
		return restCenterDao.selectRcToUseAmt(dto);
	}

	@Override
	public CommonMessage isnertRcToRequestInfo(BgabGascrc06Dto dto) {
		
		CommonMessage message = new CommonMessage();
		
		try{
			
			boolean chkFlag = true;
			
			
			
			int diidDays = (int) CurrentDateTime.diffOfDate(dto.getFr_ymd(), dto.getTo_ymd());
			
			int chkCnt = restCenterDao.selectCountRcToAmtCheck(dto);
			
			if(diidDays != chkCnt){
				chkFlag = false;
				message.setCode1("N");
				message.setMessage("저장 할 수 없습니다. 담당자에게 문의해주세요.");
			}
			
			if(chkFlag){
				if(dto.getDoc_no().equals("")){
					String docNo = StringUtil.getDocNo();
					dto.setDoc_no(docNo);
				}
				
				int cnt = restCenterDao.isnertRcToRequestInfo(dto);
								
				message.setCode1("Y");
				message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
				message.setCode(dto.getDoc_no());
				
				// BPM API호출
				String processCode = pCode; 	//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (휴양소신청서작성) - 프로세스 정의서 참조
				String loginUserId = dto.getEeno();	//로그인 사용자 아이디
				String comment = null;
				String roleCode = rCode;   //휴양소 담당자 역할코드
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
		
		return message;
	}

	@Override
	public BgabGascrc06Dto selectRcToRequestInfo(BgabGascrc06Dto dto) {
		return restCenterDao.selectRcToRequestInfo(dto);
	}
	
	@Override
	public BgabGascrc06Dto selectRcToRequestInfoByIfId(BgabGascrc06Dto dto) {
		return restCenterDao.selectRcToRequestInfoByIfId(dto);
	}

	@Override
	public int selectCountRcToReqList(BgabGascrc06Dto dto) {
		return restCenterDao.selectCountRcToReqList(dto);
	}

	@Override
	public List<BgabGascrc06Dto> selectRcToReqList(BgabGascrc06Dto dto) {
		return restCenterDao.selectRcToReqList(dto);
	}

	@Override
	public List<BgabGascrc06Dto> selectRcToHistoryList(BgabGascrc06Dto dto) {
		return restCenterDao.selectRcToHistoryList(dto);
	}

	@Override
	public CommonMessage deleteRcToRequest(BgabGascrc06Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt = restCenterDao.deleteRcToRequest(dto);
			message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
			message.setCode1("Y");
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (휴양소 신청서작성) - 프로세스 정의서 참조
			String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
	
			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
						
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("DELETE.0001"));
			message.setCode1("N");
		}
		return message;
	}

	@Override
	public CommonMessage updateRcToRequestForApprove(BgabGascrc06Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) {
		BgabGascz002Dto userParam = new BgabGascz002Dto();
		userParam.setCorp_cd(dto.getCorp_cd());
		userParam.setXusr_empno(dto.getEeno());
		BgabGascz002Dto userInfo = commonJobDao.getXusrInfo(userParam);
		
		appInfo.setDoc_no(dto.getDoc_no());					// 문서번호
		appInfo.setSystem_code("RC");								// 시스템코드
		appInfo.setTable_name("bgab_gascrc06");						// 업무 테이블이름
		appInfo.setRpts_eeno(userInfo.getXusr_empno());		// 상신자 사번
		appInfo.setRpts_dept(userInfo.getXusr_dept_code());	// 상신자 부서코드
		appInfo.setStep_code(userInfo.getXusr_step_code());	// 상신자 직위코드
		appInfo.setRpts_eeno_nm(userInfo.getXusr_name());		// 상신자 성명
		appInfo.setStep_nm(userInfo.getXusr_step_name());		// 직위 이름
		appInfo.setTitle_nm(HncisMessageSource.getMessage("rc"));								// 업무 이름
		appInfo.setAppType("RC");									// 전결권자 업무
		appInfo.setMax_level(5);									// 해외 결재 LEVEL
		appInfo.setCorp_cd(userInfo.getCorp_cd());
		
		CommonApproval commonApproval = ApprovalGasc.setApprovalRequestUseYn(appInfo);

		dto.setIf_id(commonApproval.getIf_id());
		dto.setRpts_eeno(userInfo.getXusr_empno());

		if(commonApproval.getResult().equals("Z")){
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (휴양소신청서작성) - 프로세스 정의서 참조
			String loginUserId = dto.getEeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = rCode;   //휴양소 담당자 역할코드
			
			//역할정보
			List<String> approveList = commonApproval.getApproveList();
			List<String> managerList = new ArrayList<String>();
			managerList.add(adminID);

			BpmApiUtil.sendCompleteTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList);
				
		}else{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0001"));
			message.setErrorCode("E");
			message.setCode("");
			message.setCode1("");
		}

		return message;
	}

	@Override
	public CommonMessage updateRcToRequestForApproveCancel(BgabGascrc06Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt = restCenterDao.updateRcToRequestForApproveCancel(dto);
			message.setMessage(HncisMessageSource.getMessage("APPROVE.0002"));
			message.setCode1("Y");
			
			// BPM API호출
			String processCode = pCode; 		//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();		//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (휴양소신청서작성) - 프로세스 정의서 참조
			String loginUserId = dto.getUpdr_eeno();		//로그인 사용자 아이디
			String comment = null;
			String roleCode = rCode;  	//휴양소 담당자 역할코드
			
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add(adminID);
			
			BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );

		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("APPROVE.0003"));
			message.setCode1("N");
		}
		return message;
	}
	
	/**
	 * approve cancel
	 * @throws SessionException 
	 */
	@Override
	public CommonMessage setApprovalCancel(BgabGascrc06Dto keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) throws SessionException{
		appInfo.setIf_id(keyParamDto.getIf_id());
		appInfo.setTable_name("bgab_gascrc06");
		appInfo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		
		CommonApproval commonApproval = ApprovalGasc.setApprovalCancelProcess(appInfo);

		if(commonApproval.getResult().equals("Z")){
//			updateInfoTXToApprove(keyParamDto);
			
			// BPM API호출
			String processCode = pCode; 		//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
			String bizKey = keyParamDto.getDoc_no();		//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (휴양소신청서작성) - 프로세스 정의서 참조
			String loginUserId = keyParamDto.getUpdr_eeno();		//로그인 사용자 아이디
			String comment = null;
			String roleCode = rCode;  	//휴양소 담당자 역할코드
			
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add(adminID);
			
			BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );

			message.setMessage(HncisMessageSource.getMessage("REQUEST.0002"));
		}else{
			message.setMessage(commonApproval.getMessage());
		}

		return message;
	}

	@Override
	public CommonMessage updateRcToRequestForConfirm(BgabGascrc06Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt = restCenterDao.updateRcToRequestForConfirm(dto);
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
			message.setCode1("Y");
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01210030";	//액티비티 코드 (휴양소 담당자확인) - 프로세스 정의서 참조
			String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
			String comment = null;
	
			BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
			
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
			message.setCode1("N");
		}
		return message;
	}

	@Override
	public CommonMessage updateRcToRequestForReject(BgabGascrc06Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt = restCenterDao.updateRcToRequestForReject(dto);
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
			message.setCode1("Y");
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01210030";	//액티비티 코드 (휴양소 당당자 확인) - 프로세스 정의서 참조
			String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
	
			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
						
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
			message.setCode1("N");
		}
		return message;
	}

}
