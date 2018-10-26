package com.hncis.entranceMeal.manager.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.common.application.ApprovalGasc;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.entranceMeal.dao.EntranceMealDao;
import com.hncis.entranceMeal.manager.EntranceMealManager;
import com.hncis.entranceMeal.vo.BgabGascem01Dto;
import com.hncis.entranceMeal.vo.BgabGascem02Dto;

@Service("entranceMealManagerImpl")
public class EntranceMealManagerImpl implements EntranceMealManager{

	@Autowired
	public EntranceMealDao entranceMealDao;

	@Autowired
	public CommonJobDao commonJobDao;


	/*************************************************************/
	/** entrance&meal request page                              **/
	/*************************************************************/
	@Override
	public BgabGascem01Dto insertEmToRequest(BgabGascem01Dto bgabGascem01Dto,List<BgabGascem02Dto> bgabGascem02DtoList) {

		BgabGascem01Dto reqDto = new BgabGascem01Dto();

		String docNo = "";
		int emCnt2 = 0;

		if(bgabGascem01Dto.getDoc_no().equals("")){
			docNo = StringUtil.getDocNo();
			bgabGascem01Dto.setDoc_no(docNo);
		}

		try {

			int emCnt1 = entranceMealDao.insertEmToRequestBasic(bgabGascem01Dto);
			for(int i = 0 ; i < bgabGascem02DtoList.size() ; i++){

				if(!docNo.equals("")){
					bgabGascem02DtoList.get(i).setDoc_no(docNo);
				}
				emCnt2 += entranceMealDao.insertEmToRequestVisit(bgabGascem02DtoList.get(i));
			}

			if(emCnt1 !=1 || emCnt2 != bgabGascem02DtoList.size()){
				reqDto.setErrYn(true);
				reqDto.setErrMsg(HncisMessageSource.getMessage("SAVE.0001"));
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			else{
				reqDto.setDoc_no(bgabGascem01Dto.getDoc_no());
			}

		} catch (Exception e) {
			reqDto.setErrYn(true);
			reqDto.setErrMsg(HncisMessageSource.getMessage("SAVE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return reqDto;

	}
	@Override
	public BgabGascem01Dto deleteEmToRequest(BgabGascem01Dto bgabGascem01Dto) {
		BgabGascem01Dto reqDto = new BgabGascem01Dto();

		try {

			int cnt1 = entranceMealDao.deleteEmToRequest(bgabGascem01Dto);

			int cnt = entranceMealDao.selectCountEmToRequestVisitors(bgabGascem01Dto);
			int cnt2 = entranceMealDao.deleteEmToRequestVisitors(bgabGascem01Dto);


			System.out.println(cnt1+"::::"+cnt+"::::"+cnt2);
			if(cnt1 !=1 || cnt != cnt2){
				reqDto.setErrYn(true);
				reqDto.setErrMsg(HncisMessageSource.getMessage("DELETE.0001"));
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			else{
				reqDto.setDoc_no(bgabGascem01Dto.getDoc_no());
			}

		} catch (Exception e) {
			reqDto.setErrYn(true);
			reqDto.setErrMsg(HncisMessageSource.getMessage("DELETE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return reqDto;
	}
	@Override
	public BgabGascem01Dto getSelectInfoEmToRequest(BgabGascem01Dto dto) {
		return entranceMealDao.getSelectInfoEmToRequest(dto);
	}

	@Override
	public List<BgabGascem01Dto> getSelectListEmToRequest(BgabGascem01Dto dto) {
		return entranceMealDao.getSelectListEmToRequest(dto);
	}
	@Override
	public int deleteVisitorsEmToRequest(BgabGascem02Dto dto) {
		return entranceMealDao.deleteVisitorsEmToRequest(dto);
	}

	@Override
	public CommonMessage setApproval(BgabGascem01Dto reqDto,HttpServletRequest req) throws SessionException {

		CommonMessage message = new CommonMessage();

		if(reqDto.getApp_yn().equals("Y")){


			CommonApproval appInfo = new CommonApproval();
			appInfo.setDoc_no(reqDto.getDoc_no());							// 문서번호
			appInfo.setSystem_code("EM");									// 시스템코드
			appInfo.setTable_name("bgab_gascem01");							// 업무 테이블이름
			appInfo.setRpts_eeno(SessionInfo.getSess_empno(req));			// 상신자 사번
			appInfo.setRpts_dept(SessionInfo.getSess_dept_code(req));		// 상신자 부서코드
			appInfo.setStep_code(SessionInfo.getSess_step_code(req));		// 상신자 직위코드
			appInfo.setRpts_eeno_nm(SessionInfo.getSess_name(req));			// 상신자 성명
			appInfo.setStep_nm(SessionInfo.getSess_step_name(req));			// 직위 이름
			appInfo.setTitle_nm("Entrance and Meal");						// 업무 이름
			appInfo.setMax_level(5);										// 기준 결재 LEVEL => 추가
			appInfo.setAppType("EM01");										// 전결권자 업무
			appInfo.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			// 결재요청
			CommonApproval commonApproval = ApprovalGasc.setApprovalRequestUseYn(appInfo);

			if(commonApproval.getResult().equals("Z")){
				message.setMessage(HncisMessageSource.getMessage("APPROVE.0000"));
				message.setCode(reqDto.getPgs_st_cd());
				message.setCode1(reqDto.getIf_id());
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				message.setMessage(commonApproval.getMessage());
				message.setCode("");
				message.setCode1("");
			}
		}
		else{

			entranceMealDao.updateInfoEmToConfirm(reqDto);
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
			message.setCode("");
			message.setCode1("");
		}



		return message;
	}
	@Override
	public CommonMessage setApprovalCancel(BgabGascem01Dto regDto, HttpServletRequest req) {

		CommonMessage message = new CommonMessage();
		CommonApproval appInfo = new CommonApproval();
		appInfo.setIf_id(regDto.getIf_id());
		appInfo.setTable_name("bgab_gascem01");
		CommonApproval commonApproval = ApprovalGasc.setApprovalCancelProcess(appInfo);

		if(commonApproval.getResult().equals("Z")){
			message.setMessage(HncisMessageSource.getMessage("APPROVE.0002"));
		}else{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage(commonApproval.getMessage());
		}

		return message;
	}
	@Override
	public String getSelectApprovalInfoByEm(BgabGascem01Dto rsReqDto) {
		return entranceMealDao.getSelectApprovalInfoByEm(rsReqDto);
	}


	@Override
	public int updateInfoEmToConfirm(BgabGascem01Dto bgabGascem01Dto,List<BgabGascem02Dto> bgabGascem02DtoList) {

		int emCnt2 = 0;
		for(int i = 0 ; i < bgabGascem02DtoList.size() ; i++){

			emCnt2 += entranceMealDao.insertEmToRequestVisit(bgabGascem02DtoList.get(i));
		}

		int cnt = entranceMealDao.updateInfoEmToConfirm(bgabGascem01Dto);

		return cnt;
	}
	@Override
	public int updateInfoEmToConfirmCancel(BgabGascem01Dto dto) {
		return entranceMealDao.updateInfoEmToConfirmCancel(dto);
	}
	@Override
	public BgabGascem01Dto getSelectInfoEmToRequestForApprove(BgabGascem01Dto dto) {
		return entranceMealDao.getSelectInfoEmToRequestForApprove(dto);
	}

	@Override
	public List<BgabGascem01Dto> getSelectListEmToRequestForApprove(BgabGascem01Dto dto) {
		return entranceMealDao.getSelectListEmToRequestForApprove(dto);
	}
	/*************************************************************/
	/** list page                                          		**/
	/*************************************************************/
	@Override
	public int getSelectCountEmToList(BgabGascem01Dto bgabGascem01Dto) {
		return Integer.parseInt(entranceMealDao.getSelectCountEmToList(bgabGascem01Dto));
	}
	@Override
	public List<BgabGascem01Dto> getSelectListEmToList(BgabGascem01Dto bgabGascem01Dto) {
		return entranceMealDao.getSelectListEmToList(bgabGascem01Dto);
	}
	/*************************************************************/
	/** meal list page                                          **/
	/*************************************************************/
	@Override
	public int getSelectCountEmToListForMeal(BgabGascem01Dto bgabGascem01Dto) {
		return Integer.parseInt(entranceMealDao.getSelectCountEmToListForMeal(bgabGascem01Dto));
	}

	@Override
	public List<BgabGascem01Dto> getSelectListEmToListForMeal(BgabGascem01Dto bgabGascem01Dto) {
		return entranceMealDao.getSelectListEmToListForMeal(bgabGascem01Dto);
	}

	/*************************************************************/
	/** entrance list page                                      **/
	/*************************************************************/
	@Override
	public int getSelectCountEmToListForEntrance(BgabGascem01Dto bgabGascem01Dto) {
		return Integer.parseInt(entranceMealDao.getSelectCountEmToListForEntrance(bgabGascem01Dto));
	}
	@Override
	public List<BgabGascem01Dto> getSelectListEmToListForEntrance(BgabGascem01Dto bgabGascem01Dto) {
		return entranceMealDao.getSelectListEmToListForEntrance(bgabGascem01Dto);
	}
	/*************************************************************/
	/** list page for worker                               		**/
	/*************************************************************/
	@Override
	public int getSelectCountEmToListForWorker(BgabGascem01Dto bgabGascem01Dto) {
		return Integer.parseInt(entranceMealDao.getSelectCountEmToListForWorker(bgabGascem01Dto));
	}
	@Override
	public List<BgabGascem01Dto> getSelectListEmToListForWorker(BgabGascem01Dto bgabGascem01Dto) {
		return entranceMealDao.getSelectListEmToListForWorker(bgabGascem01Dto);
	}
}
