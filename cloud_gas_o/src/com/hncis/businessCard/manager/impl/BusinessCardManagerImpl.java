package com.hncis.businessCard.manager.impl;

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

import com.hncis.businessCard.dao.BusinessCardDao;
import com.hncis.businessCard.manager.BusinessCardManager;
import com.hncis.businessCard.vo.BgabGascba01;
import com.hncis.businessCard.vo.BgabGascba02;
import com.hncis.businessCard.vo.BgabGascba03;
import com.hncis.common.application.ApprovalGasc;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.system.dao.SystemDao;

@Service("businessCardManagerImpl")
public class BusinessCardManagerImpl implements BusinessCardManager{
	@Autowired
	public BusinessCardDao businessCardDao;

	@Autowired
	public SystemDao systemDao;

	@Autowired
	public CommonJobDao commonJobDao;

    private transient Log logger = LogFactory.getLog(getClass());
	private static final String pCode = "P-C-001";
	private static final String sCode = "GASBZ01310010";
	private static final String rCode = "GASROLE01310030";
	private static final String adminID = "10000001";
	
	/**
	 * request search
	 * @return
	 */
	@Override
	public BgabGascba01 getSelectInfoBCToRequest(BgabGascba02 keyParamDto){
		BgabGascba01 info = new BgabGascba01();
		if("".equals(keyParamDto.getKey_req_date())){
			if(keyParamDto.getKey_mode() != null && "bpm".equals(keyParamDto.getKey_mode())){
				info = businessCardDao.getSelectInfoBCToBpmRequest(keyParamDto);
			}else{
				info = businessCardDao.getSelectInfoBCToDefaultRequest(keyParamDto);
			}
		}else{
			info = businessCardDao.getSelectInfoBCToRequest(keyParamDto);
		}
		return info;
	}

	/**
	 * request apply
	 * @return
	 */
	@Override
	public Object insertInfoBCToRequest_1(BgabGascba01 cgabGascba01){
		return businessCardDao.insertInfoBCToRequest_1(cgabGascba01);
	}
	@Override
	public Object insertInfoBCToRequest_2(BgabGascba01 cgabGascba01){
		
		// BPM API호출
		String processCode = pCode; 	//프로세스 코드 (명함신청 프로세스) - 프로세스 정의서 참조
		String bizKey = cgabGascba01.getDoc_no();	//신청서 번호
		String statusCode = sCode;	//액티비티 코드 (명함신청서작성) - 프로세스 정의서 참조
		String loginUserId = cgabGascba01.getEeno();	//로그인 사용자 아이디
		String comment = null;
		String roleCode = rCode;   //명함신청 담당자 역할코드
		//역할정보
		List<String> approveList = new ArrayList<String>();
		List<String> managerList = new ArrayList<String>();
		managerList.add(adminID);
		
		BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );

		
		return businessCardDao.insertInfoBCToRequest_2(cgabGascba01);
	}

	/**
	 * request delete
	 * @return
	 */
	@Override
	public Object deleteInfoBCToRequest(BgabGascba02 keyParamDto){
		
		// BPM API호출
		String processCode = pCode; 	//프로세스 코드 (명함 프로세스) - 프로세스 정의서 참조
		String bizKey = keyParamDto.getDoc_no();	//신청서 번호
		String statusCode = sCode;	//액티비티 코드 (명함 신청서작성) - 프로세스 정의서 참조
		String loginUserId = keyParamDto.getKey_eeno();	//로그인 사용자 아이디
		
		BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
		
		return businessCardDao.deleteInfoBCToRequest(keyParamDto);
	}

	/**
	 * request approve/confirm/confirm cancel
	 * @param keyParamDto
	 */
	@Override
	public Object updateInfoBCToRequest(BgabGascba02 keyParamDto){
		// BPM API호출
		String processCode = pCode; 	//프로세스 코드 (명함 프로세스) - 프로세스 정의서 참조
		String bizKey = keyParamDto.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01310030";	//액티비티 코드 (명함 담당자확인) - 프로세스 정의서 참조
		String loginUserId = keyParamDto.getUpdr_eeno();	//로그인 사용자 아이디
		String comment = null;

		BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
		
		return businessCardDao.updateInfoBCToRequest(keyParamDto);
	}

	/**
	 * accept search
	 * @param keyParamDto
	 * @return
	 */
	@Override
	public int getSelectCountBCToAccept(BgabGascba02 keyParamDto){
		return Integer.parseInt(businessCardDao.getSelectCountBCToAccept(keyParamDto));
	}
	@Override
	public List<BgabGascba01> getSelectListBCToAccept(BgabGascba02 keyParamDto){
		return businessCardDao.getSelectListBCToAccept(keyParamDto);
	}

	/**
	 * accept delete
	 * @param keyParamDto
	 * @return
	 */
	@Override
	public Object deleteListBCToAccept(List<BgabGascba02> keyParamDto){
		return businessCardDao.deleteListBCToAccept(keyParamDto);
	}

	/**
	 * accept return
	 * @param keyParamDto
	 * @return
	 */
	@Override
	public Object updateListBCToAcceptByReject(List<BgabGascba02> keyParamDto){
		return businessCardDao.updateListBCToAcceptByReject(keyParamDto);
	}

	/**
	 * accept confirm
	 * @param keyParamDto
	 * @return
	 */
	@Override
	public Object updateListBCToAcceptByConfirm1(List<BgabGascba02> keyParamDto){
		return businessCardDao.updateListBCToAcceptByConfirm1(keyParamDto);
	}

	/**
	 * accept confirmCancel
	 * @param keyParamDto
	 * @return
	 */
	@Override
	public Object updateListBCToAcceptByConfirmCancel(List<BgabGascba02> keyParamDto){
		return businessCardDao.updateListBCToAcceptByConfirmCancel(keyParamDto);
	}

	/**
	 * accept confirmAll
	 * @param keyParamDto
	 * @return
	 */
	@Override
	public Object updateListBCToAcceptByConfirm2(List<BgabGascba02> keyParamDto){
		return businessCardDao.updateListBCToAcceptByConfirm2(keyParamDto);
	}

	/**
	 * accept issue
	 * @param keyParamDto
	 * @return
	 */
	@Override
	public Object updateListBCToAcceptByIssue(List<BgabGascba02> keyParamDto){
		return businessCardDao.updateListBCToAcceptByIssue(keyParamDto);
	}

	/**
	 * approve
	 * @throws SessionException
	 */
	@Override
	public CommonMessage setApproval(BgabGascba02 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) throws SessionException{
		BgabGascz002Dto userParam = new BgabGascz002Dto();
		userParam.setCorp_cd(keyParamDto.getCorp_cd());
		userParam.setXusr_empno(keyParamDto.getKey_eeno());
		BgabGascz002Dto userInfo = commonJobDao.getXusrInfo(userParam);

		appInfo.setDoc_no(keyParamDto.getDoc_no());						// 문서번호
		appInfo.setSystem_code("BC");									// 시스템코드
		appInfo.setTable_name("bgab_gascba02");							// 업무 테이블이름
		appInfo.setRpts_eeno(userInfo.getXusr_empno());		// 상신자 사번
		appInfo.setRpts_dept(userInfo.getXusr_dept_code());	// 상신자 부서코드
		appInfo.setStep_code(userInfo.getXusr_step_code());	// 상신자 직위코드
		appInfo.setRpts_eeno_nm(userInfo.getXusr_name());		// 상신자 성명
		appInfo.setStep_nm(userInfo.getXusr_step_name());		// 직위 이름
		appInfo.setTitle_nm(HncisMessageSource.getMessage("business_card"));							// 업무 이름
		appInfo.setAppType("BC");						// 전결권자 업무
		appInfo.setMax_level(5);					// 국내 결재 LEVEL - 2014.02.07 국내출장 결재레벨 해외출장과 동일하게 변경. (Suzi요청)
		appInfo.setCorp_cd(userInfo.getCorp_cd());
		
		// 결재요청
		CommonApproval commonApproval = ApprovalGasc.setApprovalRequestUseYn(appInfo);

		if(commonApproval.getResult().equals("Z")){
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
			String bizKey = keyParamDto.getDoc_no();	//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (휴양소신청서작성) - 프로세스 정의서 참조
			String loginUserId = keyParamDto.getKey_eeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = rCode;   //휴양소 담당자 역할코드
			
			//역할정보
			List<String> approveList = commonApproval.getApproveList();
			List<String> managerList = new ArrayList<String>();
			managerList.add(adminID);

			BpmApiUtil.sendCompleteTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList);
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));
			message.setCode(keyParamDto.getKey_pgs_st_cd());
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

	/**
	 * approve cancel
	 */
	@Override
	public CommonApproval setApprovalCancel(BgabGascba02 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req){
		appInfo.setIf_id(keyParamDto.getIf_id());
		appInfo.setTable_name("bgab_gascba02");
		
		CommonApproval commonApproval = new CommonApproval(); 
		if("".equals(StringUtil.isNullToString(keyParamDto.getIf_id()))){
			keyParamDto.setKey_pgs_st_cd("0");
			commonApproval.setResult("Z");
		}else{
			
			// BPM API호출
			String processCode = pCode; 		//프로세스 코드 (명함 프로세스) - 프로세스 정의서 참조
			String bizKey = keyParamDto.getDoc_no();		//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (명함신청서작성) - 프로세스 정의서 참조
			String loginUserId = keyParamDto.getUpdr_eeno();		//로그인 사용자 아이디
			String comment = null;
			String roleCode = rCode;  	//명함 담당자 역할코드
			
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add(adminID);
			
			BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
			
			commonApproval = ApprovalGasc.setApprovalCancelProcess(appInfo);
		}

		if(commonApproval.getResult().equals("Z")){
			
			// BPM API호출
			String processCode = pCode; 		//프로세스 코드 (명함 프로세스) - 프로세스 정의서 참조
			String bizKey = keyParamDto.getDoc_no();		//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (명함신청서작성) - 프로세스 정의서 참조
			String loginUserId = keyParamDto.getUpdr_eeno();		//로그인 사용자 아이디
			String comment = null;
			String roleCode = rCode;  	//명함 담당자 역할코드
			
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add(adminID);
			
			BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
			updateInfoBCToRequest(keyParamDto);
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0002"));
		}else{
			message.setMessage(commonApproval.getMessage());
		}

		return commonApproval;
	}

	@Override
	public BgabGascba01 getSelectInfoBCToSubmit(BgabGascba02 keyParamDto){
		return businessCardDao.getSelectInfoBCToSubmit(keyParamDto);
	}

	@Override
	public String getSelectApprovalInfo(BgabGascba02 keyParamDto){
		return businessCardDao.getSelectApprovalInfo(keyParamDto);
	}
	@Override
	public int getSelectCountBcToCardTypeManagement(BgabGascba03 dto){
		return Integer.parseInt(businessCardDao.getSelectCountBcToCardTypeManagement(dto));
	}
	@Override
	public List<BgabGascba03> getSelectListBcToCardTypeManagement(BgabGascba03 dto){
		return businessCardDao.getSelectListBcToCardTypeManagement(dto);
	}
	@Override
	public int insertListBcToCardTypeManagement(List<BgabGascba03> list){
		return businessCardDao.insertListBcToCardTypeManagement(list);
	}
	@Override
	public int updateListBcToCardTypeManagement(List<BgabGascba03> list){
		return businessCardDao.updateListBcToCardTypeManagement(list);
	}
	@Override
	public int deleteListBcToCardTypeManagement(List<BgabGascba03> list){
		return businessCardDao.deleteListBcToCardTypeManagement(list);
	}
	@Override
	public CommonMessage updateInfoBcToReject(BgabGascba02 dto, HttpServletRequest req) throws SessionException{
		CommonMessage message = new CommonMessage();
		
		// BPM API호출
		String processCode = pCode; 	//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
		String bizKey = dto.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01310030";	//액티비티 코드 (휴양소 당당자 확인) - 프로세스 정의서 참조
		String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디

		BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
		// reject
		businessCardDao.updateInfoBcToReject(dto);

		//컨펌취소 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String rtnText    = dto.getSnb_rson_sbc();
		String title      = HncisMessageSource.getMessage("business_card");
		String corp_cd    = dto.getCorp_cd();

		CommonApproval commonApproval = new CommonApproval();
		commonApproval.setRdcs_eeno(toEeno);
		commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

		Submit.returnEmail(fromEeno, fromStepNm, mailAdr,"", "Business Card", rtnText, corp_cd);
//		}

		return message;
	}

	/**
	 * Confirm search
	 * @param keyParamDto
	 * @return
	 */
	@Override
	public int getSelectCountBCToConfirm(BgabGascba02 keyParamDto){
		return Integer.parseInt(businessCardDao.getSelectCountBCToConfirm(keyParamDto));
	}
	@Override
	public List<BgabGascba01> getSelectListBCToConfirm(BgabGascba02 keyParamDto){
		return businessCardDao.getSelectListBCToConfirm(keyParamDto);
	}
}
