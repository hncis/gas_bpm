package com.hncis.vehicleLog.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.common.application.ApprovalGasc;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.vehicleLog.dao.VehicleLogDao;
import com.hncis.vehicleLog.manager.VehicleLogManager;
import com.hncis.vehicleLog.vo.BgabGascvl01Dto;

@Service("vehicleLogManagerImpl")
public class VehicleLogManagerImpl implements VehicleLogManager{

    private static final String pCode = "P-D-007";
    private static final String sCode = "GASBZ01470010";
    private static final String rCode = "GASROLE01470030";
    private static final String adminID = "10000001";
    
	@Autowired
	public VehicleLogDao vehicleLogDao;

	@Autowired
	public CommonJobDao commonJobDao;

	@Override
	public CommonMessage saveXvl01Info(BgabGascvl01Dto dto) {

		CommonMessage message = new CommonMessage();

		if(dto.getDoc_no().equals("")){
			String docNo = StringUtil.getDocNo();
			dto.setDoc_no(docNo);

		}

		try{

			int cnt = vehicleLogDao.saveXvl01Info(dto);

			if(cnt !=1){
				message.setResult("N");
				message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			else{

				// BPM API호출
				String processCode = pCode; 	//프로세스 코드 (운행일지 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (운행일지 작성) - 프로세스 정의서 참조
				String loginUserId = dto.getEeno();	//로그인 사용자 아이디
				String comment = null;
				String roleCode = rCode;   //담당자 역할코드
				//역할정보
				List<String> approveList = new ArrayList<String>();
				List<String> managerList = new ArrayList<String>();
				managerList.add(adminID);
				
				BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
				
				message.setResult("Y");
				message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
				message.setCode(dto.getDoc_no());
			}

		} catch (Exception e) {
			message.setResult("N");
			message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return message;
	}

	@Override
	public BgabGascvl01Dto selectXvl01Info(BgabGascvl01Dto dto) {
		return vehicleLogDao.selectXvl01Info(dto);
	}
	
	@Override
	public BgabGascvl01Dto selectXvl01InfoByIfId(BgabGascvl01Dto dto) {
		return vehicleLogDao.selectXvl01InfoByIfId(dto);
	}

	@Override
	public CommonMessage deleteXvl01Info(BgabGascvl01Dto dto) {

		CommonMessage message = new CommonMessage();

		if(dto.getDoc_no().equals("")){
			String docNo = StringUtil.getDocNo();
			dto.setDoc_no(docNo);

		}

		try{

			int cnt = vehicleLogDao.deleteXvl01Info(dto);

			if(cnt !=1){
				message.setResult("N");
				message.setMessage(HncisMessageSource.getMessage("DELETE.0001"));
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			else{
				
				// BPM API호출
				String processCode = pCode; 	//프로세스 코드 (명함 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (명함 신청서작성) - 프로세스 정의서 참조
				String loginUserId = dto.getEeno();	//로그인 사용자 아이디
				
				BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
				message.setResult("Y");
				message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
				message.setCode(dto.getDoc_no());
			}

		} catch (Exception e) {
			message.setResult("N");
			message.setMessage(HncisMessageSource.getMessage("DELETE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return message;
	}

	@Override
	public int selectXvl01InfoListCount(BgabGascvl01Dto dto) {
		return vehicleLogDao.selectXvl01InfoListCount(dto);
	}

	@Override
	public List<BgabGascvl01Dto> selectXvl01InfoList(BgabGascvl01Dto dto) {
		return vehicleLogDao.selectXvl01InfoList(dto);
	}

	@Override
	public int selectXvl02InfoListCount(BgabGascvl01Dto dto) {
		return vehicleLogDao.selectXvl02InfoListCount(dto);
	}

	@Override
	public List<BgabGascvl01Dto> selectXvl02InfoList(BgabGascvl01Dto dto) {
		return vehicleLogDao.selectXvl02InfoList(dto);
	}

	@Override
	public int selectXvl03InfoListCount(BgabGascvl01Dto dto) {
		return vehicleLogDao.selectXvl03InfoListCount(dto);
	}

	@Override
	public List<BgabGascvl01Dto> selectXvl03InfoList(BgabGascvl01Dto dto) {
		return vehicleLogDao.selectXvl03InfoList(dto);
	}
	
	public CommonMessage setApproveVLToRequest(BgabGascvl01Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) {
		
		BgabGascz002Dto userParam = new BgabGascz002Dto();
		userParam.setCorp_cd(dto.getCorp_cd());
		userParam.setXusr_empno(dto.getEeno());
		BgabGascz002Dto userInfo = commonJobDao.getXusrInfo(userParam);
		
		appInfo.setDoc_no(dto.getDoc_no());					// 문서번호
		appInfo.setSystem_code("VL");								// 시스템코드
		appInfo.setTable_name("bgab_gascvl01");						// 업무 테이블이름
		appInfo.setRpts_eeno(userInfo.getXusr_empno());		// 상신자 사번
		appInfo.setRpts_dept(userInfo.getXusr_dept_code());	// 상신자 부서코드
		appInfo.setStep_code(userInfo.getXusr_step_code());	// 상신자 직위코드
		appInfo.setRpts_eeno_nm(userInfo.getXusr_name());		// 상신자 성명
		appInfo.setStep_nm(userInfo.getXusr_step_name());		// 직위 이름
		appInfo.setTitle_nm(HncisMessageSource.getMessage("run_hist"));								// 업무 이름
		appInfo.setAppType("VL");									// 전결권자 업무
		appInfo.setMax_level(5);									// 해외 결재 LEVEL
		appInfo.setCorp_cd(userInfo.getCorp_cd());

		CommonApproval commonApproval = ApprovalGasc.setApprovalRequestUseYn(appInfo);

		dto.setIf_id(commonApproval.getIf_id());
		dto.setRpts_eeno(userInfo.getXusr_empno());

		if(commonApproval.getResult().equals("Z")){
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (차량신청 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (차량신청 작성) - 프로세스 정의서 참조
			String loginUserId = dto.getEeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = rCode;   //담당자 역할코드
			
			//역할정보
			List<String> approveList = commonApproval.getApproveList();
			List<String> managerList = new ArrayList<String>();
			managerList.add(adminID);

			BpmApiUtil.sendCompleteTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList);
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));
		}else{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0001"));
			message.setErrorCode("E");
			message.setCode("");
			message.setCode1("");
		}

		return message;
	}

	public CommonMessage setApproveCancelVLToRequest(BgabGascvl01Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) {
		if("".equals(StringUtil.isNullToString(dto.getIf_id()))){
			int cnt = vehicleLogDao.approveCancelVLToRequest(dto);
			
			if(cnt > 0){
				// BPM API호출
				String processCode = pCode; 		//프로세스 코드 (차량신청 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();		//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (차량신청서작성) - 프로세스 정의서 참조
				String loginUserId = dto.getUpdr_eeno();		//로그인 사용자 아이디
				String comment = null;
				String roleCode = rCode;  	//담당자 역할코드
				
				//역할정보
				List<String> approveList = new ArrayList<String>();
				List<String> managerList = new ArrayList<String>();
				managerList.add(adminID);
				
				BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
				
				message.setMessage(HncisMessageSource.getMessage("APPROVE.0002"));
			}else{
				message.setMessage(HncisMessageSource.getMessage("APPROVE.0003"));
			}
		}else{
			appInfo.setIf_id(dto.getIf_id());
			appInfo.setTable_name("bgab_gascvl01");
			appInfo.setCorp_cd(dto.getCorp_cd());
			
			CommonApproval commonApproval = ApprovalGasc.setApprovalCancelProcess(appInfo);

			if(commonApproval.getResult().equals("Z")){
				// BPM API호출
				String processCode = pCode; 		//프로세스 코드 (차량신청 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();		//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (차량신청서작성) - 프로세스 정의서 참조
				String loginUserId = dto.getUpdr_eeno();		//로그인 사용자 아이디
				String comment = null;
				String roleCode = rCode;  	//담당자 역할코드
				
				//역할정보
				List<String> approveList = new ArrayList<String>();
				List<String> managerList = new ArrayList<String>();
				managerList.add(adminID);
				
				BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
				
				message.setMessage(HncisMessageSource.getMessage("APPROVE.0002"));
			}else{
				message.setMessage(commonApproval.getMessage());
			}
		}
		
		return message;
	}
	
	public int confirmTRToRequest(BgabGascvl01Dto dto) {
		
		// BPM API호출
		String processCode = pCode; 	//프로세스 코드 (차량신청 프로세스) - 프로세스 정의서 참조
		String bizKey = dto.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01470030";	//액티비티 코드 (차량신청 담당자확인) - 프로세스 정의서 참조
		String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
		String comment = null;

		BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
		
		return vehicleLogDao.confirmVLToRequest(dto);
	}

	public CommonMessage updateInfoTrToReject(BgabGascvl01Dto dto){
		CommonMessage message = new CommonMessage();
		try{
			int cnt = vehicleLogDao.updateInfoVLToReject(dto);
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (차량신청 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01470030";	//액티비티 코드 (차량신청 당당자 확인) - 프로세스 정의서 참조
			String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디

			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
			message.setCode1("Y");
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
			message.setCode1("N");
		}
		return message;
	}
	
	
}