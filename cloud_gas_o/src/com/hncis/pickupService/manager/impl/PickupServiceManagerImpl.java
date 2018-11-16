package com.hncis.pickupService.manager.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.businessTravel.dao.BusinessTravelDao;
import com.hncis.common.Constant;
import com.hncis.common.application.ApprovalGasc;
import com.hncis.common.application.RfcPoCreate;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.ExcelTemplat;
import com.hncis.common.util.FileUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz005Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.CommonSap;
import com.hncis.common.vo.RfcPoCreateVo;
import com.hncis.pickupService.dao.PickupServiceDao;
import com.hncis.pickupService.manager.PickupServiceManager;
import com.hncis.pickupService.vo.BgabGascps01Dto;
import com.hncis.pickupService.vo.BgabGascps02Dto;
import com.hncis.pickupService.vo.BgabGascps03Dto;
import com.hncis.pickupService.vo.BgabGascps04Dto;
import com.hncis.pickupService.vo.BgabGascps05Dto;
import com.hncis.pickupService.vo.PickupServiceDto;
import com.hncis.system.dao.SystemDao;
import com.hncis.system.vo.BgabGascz016Dto;

@Service("pickupServiceManagerImpl")
public class PickupServiceManagerImpl implements PickupServiceManager{
    private transient Log logger = LogFactory.getLog(getClass());

    private static final String pCode = "P-D-003";
    private static final String sCode = "GASBZ01430010";
    private static final String rCode = "GASROLE01430030";
    private static final String adminID = "10000001";
    private static final String orgCode = "H301";
    private static final String strMessege = "messege";
    
	@Autowired
	public PickupServiceDao pickupServiceDao;

	@Autowired
	public CommonJobDao commonJobDao;

	@Autowired
	public SystemDao systemDao;

	@Autowired
	public BusinessTravelDao businessTravelDao;

	@Override
	public List<PickupServiceDto> getComboListFromPlace(PickupServiceDto code) {
		return pickupServiceDao.getComboListFromPlace(code);
	}
	@Override
	public List<PickupServiceDto> getComboListToPlace(PickupServiceDto code) {
		return pickupServiceDao.getComboListToPlace(code);
	}

	/*************************************************************/
	/** pickupService request page                              **/
	/*************************************************************/

	@Override
	public BgabGascps01Dto getSelectInfoPsToRequest(BgabGascps01Dto dto) {
		return pickupServiceDao.getSelectInfoPsToRequest(dto);
	}
	@Override
	public List<BgabGascps02Dto> getSelectListPsToRequest(BgabGascps02Dto dto) {
		return pickupServiceDao.getSelectListPsToRequest(dto);
	}
	@Override
	public BgabGascps01Dto insertPsToRequest(BgabGascps01Dto dto, List<BgabGascps02Dto> list) {
		BgabGascps01Dto reqDto = new BgabGascps01Dto();

		try{

			int cntDto = pickupServiceDao.insertPsToRequest(dto);
			int delList = pickupServiceDao.deletePsToRequestList(dto);
			int cntList = pickupServiceDao.insertPsToRequestList(list);

			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (픽업 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (픽업 작성) - 프로세스 정의서 참조
			String loginUserId = dto.getEeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = rCode;   //명함신청 담당자 역할코드
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add(adminID);
			
			BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
			
			if(cntList != list.size()){
				reqDto.setErrYn("Y");
				reqDto.setErrMsg(HncisMessageSource.getMessage("SAVE.0001"));
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			else{
				reqDto.setErrYn("N");
				reqDto.setDoc_no(dto.getDoc_no());
				
				
			}

		} catch (Exception e) {
			logger.error(strMessege, e);
			reqDto.setErrYn("Y");
			reqDto.setErrMsg(HncisMessageSource.getMessage("SAVE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return reqDto;
	}
	@Override
	public int deletePsToRequest(BgabGascps01Dto dto) {
		int cnt = 0;
		try{


			cnt = pickupServiceDao.deletePsToRequest(dto);
			int cnt2 = pickupServiceDao.deletePsToRequestList(dto);
			BgabGascps04Dto diDto = new BgabGascps04Dto();
			diDto.setDoc_no(dto.getDoc_no());
			diDto.setSeq(dto.getSeq());
			diDto.setCorp_cd(dto.getCorp_cd());
			pickupServiceDao.deletePsDriverInfoToRequestList(diDto);
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (명함 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (명함 신청서작성) - 프로세스 정의서 참조
			String loginUserId = dto.getEeno();	//로그인 사용자 아이디
			
			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);

			if(cnt != 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		}catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return cnt;
	}
	@Override
	public int updateInfoPsToConfirm(BgabGascps01Dto dto, List<BgabGascps02Dto> list, HttpServletRequest req, List<BgabGascps04Dto> diList) throws SessionException {
		int cnt = 0;

		try{
			
		cnt = pickupServiceDao.updateInfoPsToConfirm(dto);
		int delList = pickupServiceDao.deletePsToRequestList(dto);
		int cnt1 = pickupServiceDao.insertPsToRequestList(list);
		BgabGascps04Dto diDto = new BgabGascps04Dto();
		diDto.setDoc_no(dto.getDoc_no());
		diDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		pickupServiceDao.deletePsDriverInfoToRequestList(diDto);
		
		// BPM API호출
		String processCode = pCode; 	//프로세스 코드 (명함 프로세스) - 프로세스 정의서 참조
		String bizKey = dto.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01430030";	//액티비티 코드 (명함 담당자확인) - 프로세스 정의서 참조
		String loginUserId = dto.getAcpc_eeno();	//로그인 사용자 아이디
		String comment = null;

		BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
		

		//컨펌 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();

		CommonApproval commonApproval = new CommonApproval();
		commonApproval.setRdcs_eeno(toEeno);
		commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

		Submit.confirmEmail(fromEeno, fromStepNm, mailAdr, "Pick-up Service");

		}catch(Exception e){
			logger.error(strMessege, e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return cnt;
	}
	@Override
	public int updateInfoPsToConfirmCancel(BgabGascps01Dto dto, HttpServletRequest req) throws SessionException {
		//컨펌취소 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String rtnText    = dto.getSnb_rson_sbc();

		CommonApproval commonApproval = new CommonApproval();
		commonApproval.setRdcs_eeno(toEeno);
		commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

		Submit.confirmCancelEmail(fromEeno, fromStepNm, mailAdr, "Pick-up Service", rtnText);

		return pickupServiceDao.updateInfoPsToConfirmCancel(dto);
	}

	@Override
	public CommonMessage updateInfoPsToReject(BgabGascps01Dto dto, HttpServletRequest req) throws SessionException {
		CommonMessage message = new CommonMessage();

		// switch 조회
//		BgabGascz005Dto switch_param = new BgabGascz005Dto();
//		switch_param.setXcod_code("PS");
//		BgabGascz005Dto switchInfo = systemDao.getSelectCheckBudgetSwitch(switch_param);
//
//		if("Y".equals(switchInfo.getXcod_hname())){
//			BgabGascps01Dto p_param = new BgabGascps01Dto();
//			p_param.setDoc_no(dto.getDoc_no());
//			BgabGascps01Dto pickupInfo = pickupServiceDao.getSelectInfoPsToRequest(p_param);
//
//			if(!"".equals(StringUtil.isNullToString(pickupInfo.getPo_no()))){
//				RfcPoCreate crfc = new RfcPoCreate();
//				RfcPoCreateVo i_PoInfo = new RfcPoCreateVo();
//				i_PoInfo.setI_date(CurrentDateTime.getDate());
//				i_PoInfo.setI_po_no(pickupInfo.getPo_no());
//				i_PoInfo.setI_po_desc("cancel");
//
//				RfcPoCreateVo o_PoInfo = new RfcPoCreateVo();
//				try {
//					o_PoInfo = crfc.doPoDelete(i_PoInfo);
//
//					if("Z".equals(o_PoInfo.getO_if_result())){
//						BgabGascps01Dto info = new BgabGascps01Dto();
//						info.setDoc_no(pickupInfo.getDoc_no());
//						info.setPo_no("");
//
//						pickupServiceDao.updatePickupServicePoInfo(info);
//					}else{
//						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//						message.setMessage(o_PoInfo.getO_if_fail_msg());
//						message.setErrorCode("E");
//					}
//				} catch (Exception e) {
//					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//					message.setMessage(o_PoInfo.getO_if_fail_msg());
//					message.setErrorCode("E");
//					logger.error("messege", e);
//				}
//			}
//
//			BgabGascps04Dto d_param = new BgabGascps04Dto();
//			d_param.setDoc_no(dto.getDoc_no());
//			List<BgabGascps04Dto> driverInfo = pickupServiceDao.selectPsDriverInfoToRequestList(d_param);
//
//			for(int i=0; i<driverInfo.size(); i++){
//				if(!"".equals(StringUtil.isNullToString(driverInfo.get(i).getF_po_no()))){
//					RfcPoCreate crfc = new RfcPoCreate();
//					RfcPoCreateVo i_PoInfo = new RfcPoCreateVo();
//					i_PoInfo.setI_date(CurrentDateTime.getDate());
//					i_PoInfo.setI_po_no(driverInfo.get(i).getF_po_no());
//					i_PoInfo.setI_po_desc("cancel");
//
//					RfcPoCreateVo o_PoInfo = new RfcPoCreateVo();
//					try {
//						o_PoInfo = crfc.doPoDelete(i_PoInfo);
//
//						if("Z".equals(o_PoInfo.getO_if_result())){
//							BgabGascps04Dto info = new BgabGascps04Dto();
//							info.setDoc_no(dto.getDoc_no());
//							info.setSeq(driverInfo.get(i).getSeq());
//							info.setPo_no("");
//
//							pickupServiceDao.updatePickupServiceFinalPoInfo(info);
//						}else{
//							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//							message.setMessage(o_PoInfo.getO_if_fail_msg());
//							message.setErrorCode("E");
//						}
//					} catch (Exception e) {
//						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//						message.setMessage(o_PoInfo.getO_if_fail_msg());
//						message.setErrorCode("E");
//						logger.error("messege", e);
//					}
//				}
//			}
//		}
//
//		if(!"E".equals(message.getErrorCode())){
			// reject
			pickupServiceDao.updateInfoPsToReject(dto);
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01430030";	//액티비티 코드 (휴양소 당당자 확인) - 프로세스 정의서 참조
			String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디

			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);

//			컨펌취소 메일 발송
			String fromEeno   = SessionInfo.getSess_name(req);
			String fromStepNm = SessionInfo.getSess_step_name(req);
			String toEeno     = dto.getEeno();
			String rtnText    = dto.getSnb_rson_sbc();
			String title	  = HncisMessageSource.getMessage("pkup");
			String corp_cd 	  = dto.getCorp_cd();
			
			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setRdcs_eeno(toEeno);
			commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

			Submit.returnEmail(fromEeno, fromStepNm, mailAdr,"", title, rtnText, corp_cd);
//		}
			
		return message;
	}

	@Override
	public CommonMessage setApproval(BgabGascps01Dto reqDto, HttpServletRequest req) {

		CommonMessage message = new CommonMessage();
		CommonApproval appInfo = new CommonApproval();
		BgabGascz002Dto userParam = new BgabGascz002Dto();
		userParam.setCorp_cd(reqDto.getCorp_cd());
		userParam.setXusr_empno(reqDto.getEeno());
		BgabGascz002Dto userInfo = commonJobDao.getXusrInfo(userParam);

		appInfo.setDoc_no(reqDto.getDoc_no());							// 문서번호
		appInfo.setSystem_code("PS");									// 시스템코드
		appInfo.setTable_name("bgab_gascps01");							// 업무 테이블이름
		appInfo.setRpts_eeno(userInfo.getXusr_empno());		// 상신자 사번
		appInfo.setRpts_dept(userInfo.getXusr_dept_code());	// 상신자 부서코드
		appInfo.setStep_code(userInfo.getXusr_step_code());	// 상신자 직위코드
		appInfo.setRpts_eeno_nm(userInfo.getXusr_name());		// 상신자 성명
		appInfo.setStep_nm(userInfo.getXusr_step_name());		// 직위 이름
		appInfo.setTitle_nm(HncisMessageSource.getMessage("pkup"));							// 업무 이름
		appInfo.setMax_level(4);										// 기준 결재 LEVEL => 추가
		appInfo.setAppType("PS");										// 전결권자 업무
		appInfo.setCorp_cd(userInfo.getCorp_cd());

		
		// 결재요청
		CommonApproval commonApproval = ApprovalGasc.setApprovalRequestUseYn(appInfo);

		reqDto.setIf_id(commonApproval.getIf_id());
		reqDto.setRpts_eeno(userInfo.getXusr_empno());

		if(commonApproval.getResult().equals("Z")){
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (픽업 프로세스) - 프로세스 정의서 참조
			String bizKey = reqDto.getDoc_no();	//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (픽업 신청서작성) - 프로세스 정의서 참조
			String loginUserId = reqDto.getEeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = rCode;   //휴양소 담당자 역할코드
			
			//역할정보
			List<String> approveList = commonApproval.getApproveList();
			List<String> managerList = new ArrayList<String>();
			managerList.add(adminID);

			BpmApiUtil.sendCompleteTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList);
			
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));
		}else{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage(HncisMessageSource.getMessage("APPROVE.0001"));
			message.setErrorCode("E");
			message.setCode("");
			message.setCode1("");
		}

		return message;
	}

	@Override
	public CommonMessage setApprovalCancel(BgabGascps01Dto regDto, HttpServletRequest req) {
		CommonMessage message = new CommonMessage();

		if("".equals(StringUtil.isNullToString(regDto.getIf_id()))){
			int cnt = pickupServiceDao.approveCancelPSToRequest(regDto);

			if(cnt > 0){
				
				// BPM API호출
				String processCode = pCode; 		//프로세스 코드 (명함 프로세스) - 프로세스 정의서 참조
				String bizKey = regDto.getDoc_no();		//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (명함신청서작성) - 프로세스 정의서 참조
				String loginUserId = regDto.getUpdr_eeno();		//로그인 사용자 아이디
				String comment = null;
				String roleCode = rCode;  	//명함 담당자 역할코드
				
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
			CommonApproval appInfo = new CommonApproval();
			appInfo.setIf_id(regDto.getIf_id());
			appInfo.setTable_name("bgab_gascps01");
			appInfo.setCorp_cd(regDto.getCorp_cd());
			CommonApproval commonApproval = ApprovalGasc.setApprovalCancelProcess(appInfo);

			if(commonApproval.getResult().equals("Z")){
				
				// BPM API호출
				String processCode = pCode; 		//프로세스 코드 (명함 프로세스) - 프로세스 정의서 참조
				String bizKey = regDto.getDoc_no();		//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (명함신청서작성) - 프로세스 정의서 참조
				String loginUserId = regDto.getUpdr_eeno();		//로그인 사용자 아이디
				String comment = null;
				String roleCode = rCode;  	//명함 담당자 역할코드
				
				//역할정보
				List<String> approveList = new ArrayList<String>();
				List<String> managerList = new ArrayList<String>();
				managerList.add(adminID);
				
				BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
				message.setMessage(HncisMessageSource.getMessage("APPROVE.0002"));
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				message.setMessage(commonApproval.getMessage());
			}
		}

		return message;
	}
	@Override
	public String getSelectApprovalInfoByPs(BgabGascps01Dto rsReqDto) {
		return pickupServiceDao.getSelectApprovalInfoByPs(rsReqDto);
	}
	@Override
	public int deleteScheduleToRequest(List<BgabGascps02Dto> list) {
		int cnt2 = 0;
		int cnt1 = pickupServiceDao.deleteScheduleToRequest(list);

//		if(list.size() > 0){
//			cnt2 = pickupServiceDao.updateScheduleToRequest(list.get(0));
//		}

//		if(cnt1 != list.size() || cnt2 != 1){
		if(cnt1 != list.size()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return cnt1;
	}

	@Override
	public BgabGascps03Dto getSelectInfoPsToPickupAmount(BgabGascps03Dto dto) {
		return pickupServiceDao.getSelectInfoPsToPickupAmount(dto);
	}

	/*************************************************************/
	/** pickupService list page                                 **/
	/*************************************************************/
	@Override
	public int getSelectCountPsToList(BgabGascps01Dto dto) {
		return pickupServiceDao.getSelectCountPsToList(dto);
	}

	@Override
	public List<BgabGascps01Dto> getSelectListPsToList(BgabGascps01Dto dto) {
		return pickupServiceDao.getSelectListPsToList(dto);
	}

	/*************************************************************/
	/** pickupService confirm page                                 **/
	/*************************************************************/
	@Override
	public int getSelectCountPsToConfirm(BgabGascps01Dto dto) {
		return pickupServiceDao.getSelectCountPsToConfirm(dto);
	}

	@Override
	public List<BgabGascps01Dto> getSelectListPsToConfirm(BgabGascps01Dto dto) {
		return pickupServiceDao.getSelectListPsToConfirm(dto);
	}
	@Override
	public List<CommonSap> getExpenseExcelList(List<BgabGascps01Dto> reportDataList) {
		List<CommonSap> excelMergeList = new ArrayList<CommonSap>();

		boolean flag = false;
		try{

			for(int i=0; i<reportDataList.size(); i++){
				CommonSap excelMerge = new CommonSap();

				if(reportDataList.get(i).getSrl_no().equals("")||reportDataList.get(i).getSrl_no()==null){
					excelMerge.setHeader01(CurrentDateTime.getDate()+reportDataList.get(i).getInvc_no_h());	// GA number
					reportDataList.get(i).setInvc_no(reportDataList.get(i).getInvc_no_h());
					flag = true;
				}
				else{
					excelMerge.setHeader01(reportDataList.get(i).getSrl_no());								// GA number
				}
				excelMerge.setHeader02(reportDataList.get(i).getDoc_ymd());									// Document date
				excelMerge.setHeader03(reportDataList.get(i).getDue_ymd());									// Due date
				excelMerge.setHeader04(reportDataList.get(i).getFirm_cd());									// Vendor
				excelMerge.setHeader05(reportDataList.get(i).getInvc_no());									// Vendor invoice
				excelMerge.setHeader06(Integer.toString(i+1));												// Item number
				excelMerge.setHeader07(reportDataList.get(i).getBudg_no());															// G/L account
				excelMerge.setHeader08(reportDataList.get(i).getCost_cd());									// Cost center
				excelMerge.setHeader09(reportDataList.get(i).getStap_nm());									// Route
				excelMerge.setHeader10(reportDataList.get(i).getRem_sbc());									// PAX
				excelMerge.setHeader11(reportDataList.get(i).getGrss_amt());								// Total amount

				excelMergeList.add(excelMerge);

			}

		if(flag){
			int cnt = pickupServiceDao.updatePsToReportBySapYn(reportDataList);
		}
		}catch(Exception e){
			e.getStackTrace();

		}

		return excelMergeList;
	}
	@Override
	public int updatePsToCancelSapData(List<BgabGascps01Dto> list) {
		return pickupServiceDao.updatePsToCancelSapData(list);
	}
	@Override
	public int getCheckPsToSrlNo(BgabGascps01Dto dto) {
		return pickupServiceDao.getCheckPsToSrlNo(dto);
	}
	@Override
	public Boolean setExpenseTemplatMake(BgabGascps01Dto dto, List<BgabGascps01Dto> list, HttpServletRequest req) throws SessionException {

		String realFilePath = "";
		String destFilePath = "";

		Map<String,Object> map = new HashMap();
		BgabGascps01Dto excelInfo = new BgabGascps01Dto();

		NumberFormat df = new DecimalFormat("#,###.00");

		excelInfo.setPo_no(dto.getPo_no());
		excelInfo.setInvc_no(dto.getInvc_no());
		excelInfo.setDoc_ymd(StringUtil.getMaskDate(dto.getDoc_ymd(), "-"));
		excelInfo.setDue_ymd(StringUtil.getMaskDate(dto.getDue_ymd(), "-"));

		excelInfo.setEeno(SessionInfo.getSess_empno(req));
		excelInfo.setFirm_cd(dto.getFirm_cd());
		excelInfo.setFirm_nm(dto.getFirm_nm());
		excelInfo.setGrss_amt(df.format(Double.parseDouble(dto.getGrss_amt())));
		excelInfo.setDoc_no(CurrentDateTime.getDate() + CurrentDateTime.getTime());

		String costCd = "";
		String deptNm = "";
		String budgNo = "";

		for(int i=0 ; i < list.size() ; i++){
			if(i == 0 ){
				costCd = list.get(i).getCost_cd();
				deptNm = list.get(i).getDept_nm();
				budgNo = list.get(i).getBudg_no();
			}
			else{
				if(!costCd.contains(list.get(i).getCost_cd())){
					costCd = costCd+"/"+list.get(i).getCost_cd();
				}
				if(!deptNm.contains(list.get(i).getDept_nm())){
					deptNm = deptNm+" "+list.get(i).getDept_nm();
				}
				if(!budgNo.contains(list.get(i).getBudg_no())){
					budgNo = budgNo+"/"+list.get(i).getBudg_no();
				}
			}
		}
		excelInfo.setBdgt_cd(budgNo);
		excelInfo.setCost_cd(costCd);
		excelInfo.setDept_nm(deptNm);

	try {

		map.put("excelInfo", excelInfo);

		String temp_path = "";
		if(StringUtil.getSystemArea().equals("LOCAL")){
			temp_path = Constant.UPLOAD_LOCAL_PATH;
		}else if(StringUtil.getSystemArea().equals("TEST")){
			temp_path = Constant.UPLOAD_TEST_PATH;
		}else{
			temp_path = Constant.UPLOAD_REAL_PATH;
		}

		realFilePath = temp_path+Constant.EXCEL_TEMPLAT_PS;
		destFilePath = temp_path+"/temp/"+dto.getPo_no()+".xls";
    	ExcelTemplat.createXlsFile(realFilePath, destFilePath, map);

    	if(dto.getFst_yn().equals("Y")){
    		int cnt = pickupServiceDao.updatePsToReportBySapYn(list);
    	}


    } catch (Exception e) {
       logger.error(strMessege, e);
    }
	return true;
	}

	/*************************************************************/
	/** agency managerment page                  	            **/
	/*************************************************************/
	@Override
	public int getSelectCountPsToAgencyManagement(BgabGascps05Dto dto) {
		//Submit.sendEmail("BAEK SEUNG HUN", "TESTER", "37102488", "", "TEST GA");
		return pickupServiceDao.getSelectCountPsToAgencyManagement(dto);
	}

	@Override
	public List<BgabGascps05Dto> getSelectListPsToAgencyManagement(BgabGascps05Dto dto) {
		return pickupServiceDao.getSelectListPsToAgencyManagement(dto);
	}

	@Override
	public int insertPsToAgencyManagement(List<BgabGascps05Dto> dto) {
		return pickupServiceDao.insertPsToAgencyManagement(dto);
	}

	@Override
	public int deletePsToAgencyManagement(List<BgabGascps05Dto> dto) {
		return pickupServiceDao.deletePsToAgencyManagement(dto);
	}

	/*************************************************************/
	/** Pick-up Place managerment page                          **/
	/*************************************************************/
	@Override
	public int getSelectCountPsToPlaceManagement(BgabGascps03Dto dto) {
		return pickupServiceDao.getSelectCountPsToPlaceManagement(dto);
	}

	@Override
	public List<BgabGascps03Dto> getSelectListPsToPlaceManagement(BgabGascps03Dto dto) {
		return pickupServiceDao.getSelectListPsToPlaceManagement(dto);
	}

	@Override
	public int insertPsToPlaceManagement(List<BgabGascps03Dto> dto) {
		return pickupServiceDao.insertPsToPlaceManagement(dto);
	}

	@Override
	public int deletePsToPlaceManagement(List<BgabGascps03Dto> dto) {
		return pickupServiceDao.deletePsToPlaceManagement(dto);
	}

	/*************************************************************/
	/** pickupService approve page                              **/
	/*************************************************************/

	@Override
	public BgabGascps01Dto getSelectInfoPsToRequestForApprove(BgabGascps01Dto dto) {
		return pickupServiceDao.getSelectInfoPsToRequestForApprove(dto);
	}
	@Override
	public List<BgabGascps02Dto> getSelectListPsToRequestForApprove(BgabGascps02Dto dto) {
		return pickupServiceDao.getSelectListPsToRequestForApprove(dto);
	}


@Override
public void savePsToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto){

		String msg        = "";
		String resultUrl  = "xps01_file.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{

			paramVal[0] = "file_name";
			paramVal[1] = "old_file_name";
			paramVal[2] = "pickupService";

			result = FileUtil.uploadFile(req, res, paramVal);

			if(result != null){
				if(result[0] != null){
					bgabGascZ011Dto.setOgc_fil_nm(result[0]);
					bgabGascZ011Dto.setFil_nm(result[5]);
					bgabGascZ011Dto.setFil_mgn_qty(Integer.parseInt(result[3]));
					Integer fileRs = pickupServiceDao.insertPsToFile(bgabGascZ011Dto);
				}
				msg = result[4];

			}else{
				resultUrl = "xps01_file.gas";
				msg = HncisMessageSource.getMessage("FILE.0001");
			}


		}catch(Exception e){
			resultUrl = "xps01_file.gas";
			msg = HncisMessageSource.getMessage("FILE.0001");
			logger.error(strMessege, e);
		}
		try{
			String dispatcherYN = "Y";
			String use_yn = "Y";

			req.setAttribute("hid_doc_no",  bgabGascZ011Dto.getDoc_no());
			req.setAttribute("hid_eeno",  bgabGascZ011Dto.getEeno());
			req.setAttribute("hid_seq",  bgabGascZ011Dto.getSeq());
			req.setAttribute("dispatcherYN", dispatcherYN);
			req.setAttribute("csrfToken", bgabGascZ011Dto.getCsrfToken());
			req.setAttribute("hid_use_yn", use_yn);
			req.setAttribute("message",  msg);
			req.getRequestDispatcher(resultUrl).forward(req, res);

			return;
		}catch(Exception e){
			logger.error(strMessege, e);
		}
		

	}

	@Override
	public List<BgabGascZ011Dto> getSelectPsToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return pickupServiceDao.getSelectPsToFile(bgabGascZ011Dto);
	}

	@Override
	public BgabGascZ011Dto getSelectPsToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return pickupServiceDao.getSelectPsToFileInfo(bgabGascZ011Dto);
	}

	@Override
	public int deletePsToFile(List<BgabGascZ011Dto> bgabGascZ011Dto){
		String fileResult = "";
		for(int i=0; i<bgabGascZ011Dto.size(); i++){
			BgabGascZ011Dto fileInfo = bgabGascZ011Dto.get(i);
			try {
				fileResult = FileUtil.deleteFile(fileInfo.getCorp_cd(), "pickupService", fileInfo.getOgc_fil_nm());
			} catch (IOException e) {
				logger.error(strMessege, e);
			}
		}

		Integer fileDRs = pickupServiceDao.deletePsToFile(bgabGascZ011Dto);

		return fileDRs;
	}

	@Override
	public int selectCountPsToPickupSchedule(BgabGascps02Dto dto){
		return pickupServiceDao.selectCountPsToPickupSchedule(dto);
	}

	@Override
	public List<BgabGascps02Dto> selectListPsToPickupSchedule(BgabGascps02Dto dto){
		return pickupServiceDao.selectListPsToPickupSchedule(dto);
	}

	public List<BgabGascps02Dto> selectExcelPsToPickupSchedule(BgabGascps02Dto dto){
		List<BgabGascps02Dto> list = pickupServiceDao.selectListPsToPickupSchedule(dto);

		for(BgabGascps02Dto data : list){
			data.setSvca_amt(StringUtil.formatComma(data.getSvca_amt()));
		}

		return list;
	}

	@Override
	public List<BgabGascps04Dto> selectPsDriverInfoToRequestList(BgabGascps04Dto dto){
		return pickupServiceDao.selectPsDriverInfoToRequestList(dto);
	}
//	public int savePsDriverInfoToRequestList (List<BgabGascps04Dto> iList, List<BgabGascps04Dto> uList){
//		int iCnt = pickupServiceDao.insertPsDriverInfoToRequestList(iList);
//		int uCnt = pickupServiceDao.updatePsDriverInfoToRequestList(uList);
//
//		return iCnt + uCnt;
//	}
	@Override
	public int deletePsDriverInfoToRequestList (BgabGascps04Dto dto){
		return pickupServiceDao.deletePsDriverInfoToRequestList(dto);
	}

	@Override
	public CommonMessage updateCompletePickUpPo(BgabGascps04Dto reqDto){
		CommonMessage message = new CommonMessage();
		// switch 조회
		BgabGascz005Dto switch_param = new BgabGascz005Dto();
		switch_param.setXcod_code("PS");
		BgabGascz005Dto switchInfo = systemDao.getSelectCheckBudgetSwitch(switch_param);

		if("Y".equals(switchInfo.getXcod_hname())){
			List<BgabGascps04Dto> d_list = pickupServiceDao.selectPsDriverInfoToRequestList(reqDto);

			for(int i=0; i<d_list.size(); i++){
				BgabGascps01Dto tmp_param = new BgabGascps01Dto();
				tmp_param.setDoc_no(reqDto.getDoc_no());
				BgabGascps01Dto tmpInfo = pickupServiceDao.getSelectInfoPsToRequest(tmp_param);
				Boolean flag = true;

				if("".equals(StringUtil.isNullToString(d_list.get(i).getF_po_no()))){
					// Material
					BgabGascz016Dto m_param = new BgabGascz016Dto();
					m_param.setKey_job("XPS");
					m_param.setStartRow(0);
					m_param.setEndRow(10);
					List<BgabGascz016Dto> m_info = systemDao.getSelectMaterialManagement(m_param);

					if(m_info.size() == 0){
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						message.setErrorCode("E");
						message.setMessage(HncisMessageSource.getMessage("MATERIAL.0001"));
						flag = false;
					}

					if(flag){
						////////////////////////////////////// FINAL PO CREATE /////////////////////////////////////////////
						String budg = reqDto.getBudg_type();
						CurrentDateTime d = new CurrentDateTime();
						// 지역 조회
						BgabGascz002Dto w_info = new BgabGascz002Dto();
						w_info.setXusr_empno(reqDto.getEeno());
						BgabGascz002Dto	workPlace = systemDao.getSelectUserWorkPlace(w_info);

						RfcPoCreate rfc = new RfcPoCreate();
						RfcPoCreateVo i_PoInfo = new RfcPoCreateVo();
						i_PoInfo.setI_date(CurrentDateTime.getDate());
						i_PoInfo.setI_vendor_code(d_list.get(i).getDriver_agency());		// mapping
						i_PoInfo.setI_vendor_name(d_list.get(i).getFil_nm());				// mapping
						i_PoInfo.setI_pur_org_code(orgCode);
						i_PoInfo.setI_pur_group("B11");
						i_PoInfo.setI_wrkplc_cd(workPlace.getXusr_plac_work());			// Piracicaba, São Paulo
						i_PoInfo.setI_usn(reqDto.getEeno());
						i_PoInfo.setI_material_code(m_info.get(0).getMaterial_code());
						i_PoInfo.setI_material_desc(m_info.get(0).getMaterial_desc());
						i_PoInfo.setI_mat_group(m_info.get(0).getMaterial_group());
						i_PoInfo.setI_qty("1");
						i_PoInfo.setI_price(d_list.get(0).getDriver_tot_amt());
						i_PoInfo.setI_delivery_date(d.getMonth(d.getDate(), 1));
						i_PoInfo.setI_cost_cd(reqDto.getCost_cd());
						i_PoInfo.setI_company_code(orgCode);

						if("D".equals(budg)){
							i_PoInfo.setI_account_category("K");
							i_PoInfo.setI_account_code(reqDto.getBudg_no());
						}else if("I".equals(budg)){
							i_PoInfo.setI_account_category("F");
							i_PoInfo.setI_account_code(reqDto.getBudg_no());
							i_PoInfo.setI_io_cd(reqDto.getBudg_text());
						}else if("W".equals(budg)){
							i_PoInfo.setI_account_category("P");
							i_PoInfo.setI_account_code(reqDto.getBudg_no());
							i_PoInfo.setI_wbs_cd(reqDto.getBudg_text());
						}

						RfcPoCreateVo o_PoInfo = null;
						try {
							o_PoInfo = rfc.getResult(i_PoInfo);

							if("Z".equals(o_PoInfo.getO_if_result())){
								BgabGascps04Dto info = new BgabGascps04Dto();
								info.setDoc_no(reqDto.getDoc_no());
								info.setSeq(d_list.get(i).getSeq());
								info.setPo_no(o_PoInfo.getO_po_no());
								info.setUpdr_eeno(d_list.get(i).getUpdr_eeno());

								pickupServiceDao.updatePickupServiceFinalPoInfo(info);

								////////////////////////////////////// FINAL PO END /////////////////////////////////////////////


								////////////////////////////////////// TEMP PO DELETE /////////////////////////////////////////////
								RfcPoCreateVo z_PoInfo = new RfcPoCreateVo();
								if(!"".equals(StringUtil.isNullToString(reqDto.getPo_no(),""))){
									RfcPoCreate drfc = new RfcPoCreate();
									RfcPoCreateVo d_PoInfo = new RfcPoCreateVo();
									d_PoInfo.setI_date(CurrentDateTime.getDate());
									d_PoInfo.setI_po_no(reqDto.getPo_no());
									d_PoInfo.setI_po_desc("cancel");

									try {
										z_PoInfo = drfc.doPoDelete(d_PoInfo);

										if("Z".equals(z_PoInfo.getO_if_result())){
											BgabGascps01Dto z_info = new BgabGascps01Dto();
											z_info.setDoc_no(reqDto.getDoc_no());
											z_info.setPo_no("");
											z_info.setTot_amt("0");
											z_info.setUpdr_eeno(d_list.get(i).getUpdr_eeno());

											pickupServiceDao.updatePickupServicePoInfo(z_info);

											////////////////////////////////////// TEMP PO END /////////////////////////////////////////////

											if(flag){
												////////////////////////////////////// TEMP PO CREATE /////////////////////////////////////////////
												// Dummy Vendor Info
												BgabGascz005Dto dummyDto = new BgabGascz005Dto();
												dummyDto.setXcod_knd("X3015");
												BgabGascz005Dto dummyInfo = businessTravelDao.getSelectDummyVendorInfo(dummyDto);

												Double tmpAmt = Double.parseDouble(tmpInfo.getTot_amt()) - Double.parseDouble(d_list.get(0).getDriver_tot_amt());

												RfcPoCreate crfc = new RfcPoCreate();
												RfcPoCreateVo it_PoInfo = new RfcPoCreateVo();
												it_PoInfo.setI_date(CurrentDateTime.getDate());
												it_PoInfo.setI_vendor_code(dummyInfo.getXcod_code());		// mapping
												it_PoInfo.setI_vendor_name(dummyInfo.getXcod_hname());		// mapping
												it_PoInfo.setI_pur_org_code(orgCode);
												it_PoInfo.setI_pur_group("B11");
												it_PoInfo.setI_wrkplc_cd(workPlace.getXusr_plac_work());			// Piracicaba, São Paulo
												it_PoInfo.setI_usn(reqDto.getEeno());
												it_PoInfo.setI_material_code(m_info.get(0).getMaterial_code());
												it_PoInfo.setI_material_desc(m_info.get(0).getMaterial_desc());
												it_PoInfo.setI_mat_group(m_info.get(0).getMaterial_group());
												it_PoInfo.setI_qty("1");
												it_PoInfo.setI_price(Double.toString(tmpAmt));
												it_PoInfo.setI_delivery_date(d.getMonth(d.getDate(), 1));
												it_PoInfo.setI_cost_cd(reqDto.getCost_cd());
												it_PoInfo.setI_company_code(orgCode);

												if("D".equals(budg)){
													it_PoInfo.setI_account_category("K");
													it_PoInfo.setI_account_code(reqDto.getBudg_no());
												}else if("I".equals(budg)){
													it_PoInfo.setI_account_category("F");
													it_PoInfo.setI_account_code(reqDto.getBudg_no());
													it_PoInfo.setI_io_cd(reqDto.getBudg_text());
												}else if("W".equals(budg)){
													it_PoInfo.setI_account_category("P");
													it_PoInfo.setI_account_code(reqDto.getBudg_no());
													it_PoInfo.setI_wbs_cd(reqDto.getBudg_text());
												}

												RfcPoCreateVo ot_PoInfo = null;
												if(tmpAmt > 0){
													ot_PoInfo = crfc.getResult(it_PoInfo);
												}else{

												}

												if("Z".equals(o_PoInfo.getO_if_result())){
													BgabGascps01Dto t_info = new BgabGascps01Dto();
													t_info.setDoc_no(reqDto.getDoc_no());
													t_info.setPo_no(o_PoInfo.getO_po_no());
													if(tmpAmt > 0){
														t_info.setTot_amt(Double.toString(tmpAmt));
													}else{
														t_info.setTot_amt("0");
													}
													pickupServiceDao.updatePickupServicePoInfo(t_info);

													////////////////////////////////////// TEMP PO END /////////////////////////////////////////////
												}else{
													TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
													message.setErrorCode("E");
													message.setMessage(ot_PoInfo.getO_if_fail_msg());
												}
											}
										}else{
											TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
											message.setErrorCode("E");
											message.setMessage(z_PoInfo.getO_if_fail_msg());
										}
									} catch (Exception e) {
										TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
										message.setMessage(z_PoInfo.getO_if_fail_msg());
										message.setErrorCode("E");
										logger.error(strMessege, e);
									}
								}

							}else{
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
								message.setErrorCode("E");
								message.setMessage(o_PoInfo.getO_if_fail_msg());
							}
						} catch (Exception e) {
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							message.setErrorCode("E");
							message.setMessage(o_PoInfo.getO_if_fail_msg());
							logger.error(strMessege, e);
						}
					}
				}
			}
		}else{
			message.setMessage(HncisMessageSource.getMessage("SWITCH.0001"));
		}

		return message;
	}
}
