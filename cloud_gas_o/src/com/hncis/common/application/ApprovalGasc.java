package com.hncis.common.application;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.businessCard.dao.BusinessCardDao;
import com.hncis.businessTravel.dao.BusinessTravelDao;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.StringUtil;
import com.hncis.common.util.Submit;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonCode;
import com.hncis.pickupService.dao.PickupServiceDao;
import com.hncis.roomsMeals.dao.RoomsMealsDao;
import com.hncis.system.vo.BgabGascz007Dto;
import com.hncis.system.vo.BgabGascz008Dto;
import com.hncis.taxi.dao.TaxiDao;
import com.hncis.common.util.BpmApiUtil;

@Component
public class ApprovalGasc{

	private static CommonJobDao commonJobDao;
	private static BusinessCardDao businessCardDao;
	private static BusinessTravelDao businessTravelDao;
	private static PickupServiceDao pickupServiceDao;
	private static RoomsMealsDao roomsMealsDao;
	private static TaxiDao taxiDao;

	private static final String appType02 = "SE02";
	private static final String appType03 = "SE03";
	private static final String appType04 = "SE04";
	private static final String appType05 = "SE05";
	

	@Autowired
	public void setCommonJobDao(CommonJobDao commonJobDao){
		ApprovalGasc.commonJobDao = commonJobDao;
	}

	@Autowired
	public void setBusinessCardDao(BusinessCardDao businessCardDao){
		ApprovalGasc.businessCardDao = businessCardDao;
	}

	@Autowired
	public void setBusinessTravelDao(BusinessTravelDao businessTravelDao){
		ApprovalGasc.businessTravelDao = businessTravelDao;
	}

	@Autowired
	public void setPickupServiceDao(PickupServiceDao pickupServiceDao){
		ApprovalGasc.pickupServiceDao = pickupServiceDao;
	}

	@Autowired
	public void setRoomsMealsDao(RoomsMealsDao roomsMealsDao){
		ApprovalGasc.roomsMealsDao = roomsMealsDao;
	}

	@Autowired
	public void setTaxiDao(TaxiDao taxiDao){
		ApprovalGasc.taxiDao = taxiDao;
	}


	/**
	 * 결재상신
	 * @return CommonApproval
	 * @param CommonApproval
	 */
	public static CommonApproval setApprovalRequest(CommonApproval commonApproval){

		// 결재번호 생성
		String if_id = commonApproval.getSystem_code()+CurrentDateTime.getDate()+CurrentDateTime.getTime()+StringUtil.lpad(Integer.toString((int)(java.lang.Math.random()*10000)), 4, "0");
		int chkCount = 0;
		int totCount = 0;
		int rankCount = 0;
		boolean errorChk = true;

		// tot_level setting(출장(국내/해외)/배차)

		// approval master setting
		commonApproval.setIf_id(if_id);														// 결재번호
		commonApproval.setNow_level(1);														// 현재 level
		commonApproval.setCancel("N");														// 취소상태
		commonApproval.setResult("A");														// 결재상태

		// 전결권자 레벨 가져오기
		String approveLevel = commonJobDao.selectApproveLevel(commonApproval);

		//sql - select
		chkCount = commonJobDao.getSelectApprovalUserCount(commonApproval);

		if(chkCount > 0){
			commonApproval.setResult("E");
			commonApproval.setMessage("There is no person who can approve your request in upper department, Please ask system administrator.");
			return commonApproval;
		}

		//sql - select
		chkCount = commonJobDao.getSelectApprovalDeptCount(commonApproval);

		if(chkCount == 0){
			commonApproval.setResult("E");
			commonApproval.setMessage("Your department does not exist, Please ask system administrator.");
			return commonApproval;
		}


		if(StringUtil.isNullToString(commonApproval.getAppType()).equals("")){		// 일반 결재
			// select LevelInfo In Code Table
			CommonCode codeInfo = new CommonCode();
			codeInfo.setCodknd("X1011");
			codeInfo.setCode(commonApproval.getSystem_code());
			//sql - select : BA, BC, BT, BZ, PC, TS
			CommonCode commonCode = commonJobDao.getCodeInfoNotIncludeXcodAplyFlag(codeInfo);

			// 전체 level
			commonApproval.setTot_level(Integer.parseInt(commonCode.getName()));

			//totCount = commonJobDao.getSelectApprovalUserTotalLevelCount(commonApproval); //sql - select
			totCount = 1; //결재자가 1명이기 때문에 1로 지정함.

			// 전체 결재자 count값 비교
			if(totCount < commonApproval.getTot_level()){
				commonApproval.setTot_level(totCount);
			}

		}else if(StringUtil.isNullToString(commonApproval.getAppType()).equals(appType02)
					|| StringUtil.isNullToString(commonApproval.getAppType()).equals(appType03)
					|| StringUtil.isNullToString(commonApproval.getAppType()).equals(appType04)){   // Security 결재
			totCount = 2;
			commonApproval.setTot_level(2);
		}else if(StringUtil.isNullToString(commonApproval.getAppType()).equals(appType05)){
			totCount = 3;
			commonApproval.setTot_level(3);
		}else{
			if(StringUtil.isNullToString(commonApproval.getAppType()).equals("BT01")){				// 출장_직급상_근처
				// 실장(03) count
				commonApproval.setCode1("");
				commonApproval.setCode2("03");
				rankCount =commonJobDao.getSelectApprovalUserTotalLevelCount(commonApproval);

				if(rankCount < 1){
					commonApproval.setCode1("04");
					commonApproval.setCode2("");
				}else{
					// 전체 level => 실장(03)
					commonApproval.setCode1("03");
					commonApproval.setCode2("");
				}
				totCount = commonJobDao.getSelectApprovalUserTotalLevelCount(commonApproval);
				commonApproval.setTot_level(totCount);
			}else if(StringUtil.isNullToString(commonApproval.getAppType()).equals("BT02")){		// 출장_직급하_근처
				// 전체 level => 실장(03)
				commonApproval.setCode1("04");
				totCount = commonJobDao.getSelectApprovalUserTotalLevelCount(commonApproval);
				commonApproval.setTot_level(totCount);
			}else if(StringUtil.isNullToString(commonApproval.getAppType()).equals("BT03")){		// 출장_직급상_해외
				// 전체 level => 실장(03)
				commonApproval.setCode1("01");
				totCount = commonJobDao.getSelectApprovalUserTotalLevelCount(commonApproval);
				commonApproval.setTot_level(totCount);
			}else if(StringUtil.isNullToString(commonApproval.getAppType()).equals("BT04")){		// 출장_직급하_해외
				// 전체 level => 실장(03)
				commonApproval.setCode1("03");

				// 실장(03) count
				commonApproval.setCode1("");
				commonApproval.setCode2("03");
				rankCount =commonJobDao.getSelectApprovalUserTotalLevelCount(commonApproval);

				if(rankCount < 1){
					commonApproval.setCode1("01");
					commonApproval.setCode2("");
				}else{
					// 전체 level => 실장(03)
					commonApproval.setCode1("03");
					commonApproval.setCode2("");
				}

				totCount = commonJobDao.getSelectApprovalUserTotalLevelCount(commonApproval);
				commonApproval.setTot_level(totCount);
			}else{
				commonApproval.setResult("E");
				commonApproval.setMessage("Please ask system administrator.");
				return commonApproval;
			}
		}

		// NEXT 결재권자
		CommonApproval rdcsSet = new CommonApproval();
		rdcsSet.setRpts_dept(commonApproval.getRpts_dept());
		rdcsSet.setNow_level(commonApproval.getNow_level());
		CommonApproval rdcsInfo = commonJobDao.getApprovalLevelInfo(rdcsSet);

		if(totCount < 1){
			commonApproval.setRdcs_eeno(commonApproval.getRpts_eeno());
			commonApproval.setTot_level(1);
		}else{
			// next 결재자 setting
			commonApproval.setRdcs_eeno(rdcsInfo.getRdcs_eeno());
			commonApproval.setRdcs_dept(rdcsInfo.getRdcs_dept());
		}


		// 팀장 체크
		int readerCnt = 0;
		String readerYn = "N";
//		if(commonApproval.getStep_code().equals("05")){
//			readerCnt = commonJobDao.getSelectApprovalReaderCount(commonApproval);
//		}

		//if(Integer.parseInt(commonApproval.getStep_code()) > 5 || readerCnt > 0){
		if(commonApproval.getRpts_eeno().equals(commonApproval.getRdcs_eeno())){
			readerYn = "Y";
			commonApproval.setResult("S");
		}

		// approval master insert
		commonJobDao.insertApprovalInfo(commonApproval);

//		if(readerYn.equals("Y")){
//			return commonApproval;
//		}

		// approval detail setting
		int cnt = 0;
		for(int i=0; i<commonApproval.getTot_level(); i++){
			CommonApproval approvalDetail = new CommonApproval();
			approvalDetail.setIf_id(commonApproval.getIf_id());

			approvalDetail.setLevel_index(i+1);
			if(i == 0){
				approvalDetail.setResult("1");
				approvalDetail.setRdcs_eeno(rdcsInfo.getRdcs_eeno());
				approvalDetail.setRdcs_dept(rdcsInfo.getRdcs_dept());
			}else{
				approvalDetail.setResult("0");

				// Security 결재
				if(StringUtil.isNullToString(commonApproval.getAppType()).equals(appType02)){
					rdcsInfo.setCode1("2");
					CommonApproval securtiyInfo = commonJobDao.getSecurityNextApprovalInfo(rdcsInfo);
					if(null == securtiyInfo){
						cnt++;
					}else{
						approvalDetail.setRdcs_eeno(securtiyInfo.getRdcs_eeno());
						approvalDetail.setRdcs_dept(securtiyInfo.getRdcs_dept());
					}
				}else if(StringUtil.isNullToString(commonApproval.getAppType()).equals(appType03)){
					rdcsInfo.setCode1("3");
					CommonApproval securtiyInfo = commonJobDao.getSecurityNextApprovalInfo(rdcsInfo);
					if(null == securtiyInfo){
						cnt++;
					}else{
						approvalDetail.setRdcs_eeno(securtiyInfo.getRdcs_eeno());
						approvalDetail.setRdcs_dept(securtiyInfo.getRdcs_dept());
					}
				}else if(StringUtil.isNullToString(commonApproval.getAppType()).equals(appType04)
							|| StringUtil.isNullToString(commonApproval.getAppType()).equals(appType05) && i == 1){
					rdcsInfo.setCode1("A");
					rdcsInfo.setCode2(commonApproval.getCode2());
					CommonApproval securtiyInfo = commonJobDao.getSecurityNextApprovalInfo(rdcsInfo);
					if(null == securtiyInfo){
						cnt++;
					}else{
						approvalDetail.setRdcs_eeno(securtiyInfo.getRdcs_eeno());
						approvalDetail.setRdcs_dept(securtiyInfo.getRdcs_dept());
					}
				}else if(StringUtil.isNullToString(commonApproval.getAppType()).equals(appType05) && i == 2){
					rdcsInfo.setCode1("4");
					CommonApproval securtiyInfo = commonJobDao.getSecurityNextApprovalInfo(rdcsInfo);
					if(null == securtiyInfo){
						cnt++;
					}else{
						approvalDetail.setRdcs_eeno(securtiyInfo.getRdcs_eeno());
						approvalDetail.setRdcs_dept(securtiyInfo.getRdcs_dept());
					}
				}
			}
			// approval detail insert
			commonJobDao.insertApprovalDetailInfo(approvalDetail);
		}

		if(cnt > 0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonApproval.setResult("E");
			commonApproval.setMessage("Please ask system administrator.");
		}else{
			if(commonApproval.getRpts_eeno().equals(commonApproval.getRdcs_eeno())){
				readerYn = "Y";
				commonApproval.setResult("S");
			}else{
				// 정상처리 전송
				commonApproval.setResult("Z");

				// 메일발송 - 결재 상신
				String fromEeno   = commonApproval.getRpts_eeno_nm();
				String fromStepNm = commonApproval.getStep_nm();
				String mode       = "approve";
				String toEeno     = commonApproval.getRdcs_eeno();
				String title      = commonApproval.getTitle_nm();
				String corp_cd    = commonApproval.getCorp_cd();

				String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

				Submit.sendEmail(fromEeno, fromStepNm, toEeno, mailAdr, mode, title, corp_cd);
			}
		}
		return commonApproval;
	}

	/**
	 * 결재상신
	 * @return CommonApproval
	 * @param CommonApproval
	 */
	public static CommonApproval setApprovalRequestNew(CommonApproval commonApproval){

		// 결재번호 생성
		String if_id = commonApproval.getSystem_code()+CurrentDateTime.getDate()+CurrentDateTime.getTime()+StringUtil.lpad(Integer.toString((int)(java.lang.Math.random()*10000)), 4, "0");
		// 상신자와 결재자 비교 temp value
		boolean rptsEqualChk = false;

		// approval master setting
		commonApproval.setIf_id(if_id);														// 결재번호
		commonApproval.setNow_level(1);														// 현재 level
		commonApproval.setCancel("N");														// 취소상태
		commonApproval.setResult("A");														// 결재상태

		BgabGascz008Dto applevelInfo = new BgabGascz008Dto();
		applevelInfo.setOrga_c(commonApproval.getRpts_dept());
//		applevelInfo.setMax_levl(commonApproval.getMax_level());

		// 전결권자 레벨 가져오기
		String approveLevel = commonJobDao.selectApproveLevel(commonApproval);
//		applevelInfo.setMax_levl(approveLevel);

		// 코디여부 체크
//		String coordiChkCnt = commonJobDao.getSelectCoordiChk(commonApproval);
//
//		if(Integer.parseInt(coordiChkCnt) > 0){
//			applevelInfo.setCoordi_yn("Y");
//		}

		// 결재라인 LIST
		List<BgabGascz008Dto> applevelList = commonJobDao.getSelectAppLineList(applevelInfo);

		// 결재라인 count 체크
		if(applevelList.size()== 0){
			if(Integer.parseInt(commonApproval.getStep_code()) > 40){

				BgabGascz008Dto appInfo = new BgabGascz008Dto();
				appInfo.setEmpno(commonApproval.getRpts_eeno());
				appInfo.setOrga_c(commonApproval.getRpts_dept());

				applevelList.add(appInfo);

			}else{
				commonApproval.setResult("E");
				commonApproval.setMessage("Your department does not exist, Please ask system administrator.");
				return commonApproval;
			}
		}

		// 상신자와 결재자 동일인 체크
		if(StringUtil.isNullToString(applevelList.get(0).getEmpno()).equals(StringUtil.isNullToString(commonApproval.getRpts_eeno()))){
			rptsEqualChk = true;
		}

		int appLineCnt = 0;
		String prev_empno = "";

		int cntLv = 1;
		for(int i = 0 ; i < applevelList.size() ; i++){

			// 결재자 정보 체크
			if(applevelList.get(i).getEmpno().equals("00000000") || StringUtil.isNullToString(applevelList.get(i).getEmpno()).equals("")){
				commonApproval.setResult("E");
				commonApproval.setMessage("There is no person who can approve your request in upper department, Please ask system administrator.");
				return commonApproval;
			}

			CommonApproval approvalDetail = new CommonApproval();
			approvalDetail.setIf_id(commonApproval.getIf_id());
			approvalDetail.setLevel_index(cntLv);
			approvalDetail.setCorp_cd(commonApproval.getCorp_cd());

			// 상신자와 결재자 동일여부에 따른 result 값 디폴트 세팅
			if(i==0){
				if(rptsEqualChk){
					approvalDetail.setResult("Z");
				}else{
					approvalDetail.setResult("1");
				}
			}else if(i==1){
				if(rptsEqualChk){
					approvalDetail.setResult("1");
				}else{
					approvalDetail.setResult("0");
				}
			}else{
				approvalDetail.setResult("0");
			}

			if(!prev_empno.equals(applevelList.get(i).getEmpno())){
				approvalDetail.setRdcs_eeno(applevelList.get(i).getEmpno());
				approvalDetail.setRdcs_dept(applevelList.get(i).getOrga_c());

				// 결재 detail insert
				commonJobDao.insertApprovalDetailInfo(approvalDetail);
				prev_empno = applevelList.get(i).getEmpno();
				appLineCnt++;
				cntLv++;
			}
		}

		// Security 결재
		CommonApproval apprDetail = new CommonApproval();
		CommonApproval rdcsInfo = new CommonApproval();
		int cnt = 0;
		apprDetail.setIf_id(commonApproval.getIf_id());
		if(StringUtil.isNullToString(commonApproval.getAppr_type()).equals(appType02)){
			rdcsInfo.setCode1("2");
			CommonApproval securtiyInfo = commonJobDao.getSecurityNextApprovalInfo(rdcsInfo);
			if(null == securtiyInfo){
				cnt++;
			}else{
				apprDetail.setLevel_index(cntLv);
				apprDetail.setResult("0");
				apprDetail.setRdcs_eeno(securtiyInfo.getRdcs_eeno());
				apprDetail.setRdcs_dept(securtiyInfo.getRdcs_dept());
				// 결재 detail insert
				commonJobDao.insertApprovalDetailInfo(apprDetail);
				appLineCnt++;
				cntLv++;
			}
		}
		if(StringUtil.isNullToString(commonApproval.getAppr_type()).equals(appType03)){
			rdcsInfo.setCode1("3");
			CommonApproval securtiyInfo = commonJobDao.getSecurityNextApprovalInfo(rdcsInfo);
			if(null == securtiyInfo){
				cnt++;
			}else{
				apprDetail.setLevel_index(cntLv);
				apprDetail.setResult("0");
				apprDetail.setRdcs_eeno(securtiyInfo.getRdcs_eeno());
				apprDetail.setRdcs_dept(securtiyInfo.getRdcs_dept());
				// 결재 detail insert
				commonJobDao.insertApprovalDetailInfo(apprDetail);
				appLineCnt++;
				cntLv++;
			}
		}
		if(StringUtil.isNullToString(commonApproval.getAppr_type()).equals(appType04) || StringUtil.isNullToString(commonApproval.getAppr_type()).equals(appType05)){
			rdcsInfo.setCode1("A");
			rdcsInfo.setCode2(commonApproval.getCode2());
			CommonApproval securtiyInfo = commonJobDao.getSecurityNextApprovalInfo(rdcsInfo);
			if(null == securtiyInfo){
				cnt++;
			}else{
				apprDetail.setLevel_index(cntLv);
				apprDetail.setResult("0");
				apprDetail.setRdcs_eeno(securtiyInfo.getRdcs_eeno());
				apprDetail.setRdcs_dept(securtiyInfo.getRdcs_dept());
				// 결재 detail insert
				commonJobDao.insertApprovalDetailInfo(apprDetail);
				appLineCnt++;
				cntLv++;
			}
		}
		if(StringUtil.isNullToString(commonApproval.getAppr_type()).equals(appType05)){
			rdcsInfo.setCode1("4");
			CommonApproval securtiyInfo = commonJobDao.getSecurityNextApprovalInfo(rdcsInfo);
			if(null == securtiyInfo){
				cnt++;
			}else{
				apprDetail.setLevel_index(cntLv);
				apprDetail.setResult("0");
				apprDetail.setRdcs_eeno(securtiyInfo.getRdcs_eeno());
				apprDetail.setRdcs_dept(securtiyInfo.getRdcs_dept());
				// 결재 detail insert
				commonJobDao.insertApprovalDetailInfo(apprDetail);
				appLineCnt++;
				cntLv++;
			}
		}

		// rooms & meals 결재자 추가..
		if(StringUtil.isNullToString(commonApproval.getSystem_code()).equals("RM")){
			rdcsInfo.setDoc_no(commonApproval.getDoc_no());
			CommonApproval roomMgmtInfo = commonJobDao.getRoomsNextApprovalInfo(rdcsInfo);
			if(null == roomMgmtInfo){
				cnt++;
			}else{
				if(!"".equals(StringUtil.isNullToString(roomMgmtInfo.getRdcs_eeno()))){
					apprDetail.setLevel_index(cntLv);
					apprDetail.setResult("0");
					apprDetail.setRdcs_eeno(roomMgmtInfo.getRdcs_eeno());
					apprDetail.setRdcs_dept(roomMgmtInfo.getRdcs_dept());
					// 결재 detail insert
					commonJobDao.insertApprovalDetailInfo(apprDetail);
					appLineCnt++;
					cntLv++;
				}
			}
		}


		if(cnt > 0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonApproval.setResult("E");
			commonApproval.setMessage("Please ask system administrator.");
		}else{
			// total level setting
			//commonApproval.setTot_level(applevelList.size());
			commonApproval.setTot_level(appLineCnt);

			// Next 결재자 setting
			commonApproval.setRdcs_eeno(applevelList.get(0).getEmpno());
			commonApproval.setRdcs_dept(applevelList.get(0).getOrga_c());

			// 상신자와 결재자 동일인일 경우 now level +1 setting
			if(rptsEqualChk){
				if(applevelList.size() > 1){
					commonApproval.setNow_level(2);
					commonApproval.setRdcs_eeno(applevelList.get(1).getEmpno());
					commonApproval.setRdcs_dept(applevelList.get(1).getOrga_c());
				}else{
					// 업무 table 결재 완료 update
					commonApproval.setResult("Z");
					//commonJobDao.updateSystemStatusByApprovalDoc(commonApproval);
				}
			}

			commonJobDao.updateSystemStatusByApprovalDoc(commonApproval);

			// 결재 마스터 insert
			commonJobDao.insertApprovalInfo(commonApproval);
			commonApproval.setResult("Z");

			// 메일발송 - 결재 상신
			String fromEeno   = commonApproval.getRpts_eeno_nm();
			String fromStepNm = commonApproval.getStep_nm();
			String toEeno     = commonApproval.getRdcs_eeno();
			String mode       = "approve";
			String title      = commonApproval.getTitle_nm();
			String corp_cd    = commonApproval.getCorp_cd();
			
			String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

			Submit.sendEmail(fromEeno, fromStepNm, toEeno, mailAdr, mode, title, corp_cd);
		}
		return commonApproval;
	}

	/**
	 * 결재상신
	 * @return CommonApproval
	 * @param CommonApproval
	 */
	public static CommonApproval setApprovalRequestUseYn(CommonApproval commonApproval){

		// 결재번호 생성
		String if_id = commonApproval.getSystem_code()+CurrentDateTime.getDate()+CurrentDateTime.getTime()+StringUtil.lpad(Integer.toString((int)(java.lang.Math.random()*10000)), 4, "0");
		// 상신자와 결재자 비교 temp value
		boolean rptsEqualChk = false;

		// approval master setting
		commonApproval.setIf_id(if_id);														// 결재번호
		commonApproval.setNow_level(1);														// 현재 level
		commonApproval.setCancel("N");														// 취소상태
		commonApproval.setResult("A");														// 결재상태

		BgabGascz008Dto applevelInfo = new BgabGascz008Dto();
		applevelInfo.setOrga_c(commonApproval.getRpts_dept());
//		applevelInfo.setMax_levl(commonApproval.getMax_level());

		// 전결권자 레벨 가져오기
		String tmpSysCode = commonApproval.getSystem_code();
		String approveStepLevel = commonJobDao.selectApproveStepLevel(commonApproval);
		if("EV".equals(commonApproval.getSystem_code())){
			commonApproval.setSystem_code("SE");
		}else if("BN".equals(tmpSysCode)){
			commonApproval.setSystem_code("BN");
		}

		List<BgabGascz008Dto> applevelList = new ArrayList<BgabGascz008Dto>();

		int appLineCnt = 0;
		String prev_empno = "";
		int cntLv = 1;
		int cnt = 0;

		if(approveStepLevel == null || "".equals(approveStepLevel) || "0".equals(approveStepLevel)){
//			commonApproval.setResult("Z");														// 결재상태
//			commonJobDao.updateSystemStatusByApprovalDoc(commonApproval);

			CommonApproval approvalDetail = new CommonApproval();
			approvalDetail.setDoc_no(commonApproval.getDoc_no());
			approvalDetail.setCorp_cd(commonApproval.getCorp_cd());
			approvalDetail.setRpts_eeno(commonApproval.getRpts_eeno());
			approvalDetail.setTable_name(commonApproval.getTable_name());
			approvalDetail.setResult("Z");														// 결재상태
			commonJobDao.updateSystemStatusByApprovalDoc(approvalDetail);
			commonApproval.setResult("Z");

			//BPM 역할정보
			List<String> approveList = new ArrayList<String>();
			commonApproval.setApproveList(approveList);
			
		} else {
			CommonApproval approvalDetail1 = new CommonApproval();
			approvalDetail1.setCorp_cd(commonApproval.getCorp_cd());
			approvalDetail1.setRpts_dept(commonApproval.getRpts_dept());
			approvalDetail1.setNow_level(Integer.parseInt(approveStepLevel));

			//int maxApprovalLevel = commonJobDao.selectApprovalMaxLevelInfo(approvalDetail1);

			applevelInfo.setMax_levl(Integer.parseInt(approveStepLevel));
			applevelInfo.setCorp_cd(commonApproval.getCorp_cd());
		// 코디여부 체크
	//		String coordiChkCnt = commonJr34obDao.getSelectCoordiChk(commonApproval);
	//
	//		if(Integer.parseInt(coordiChkCnt) > 0){
	//			applevelInfo.setCoordi_yn("Y");
	//		}

			// 결재라인 LIST
			applevelList = commonJobDao.getSelectAppLineList(applevelInfo);

			// 결재라인 count 체크
			if(applevelList.size()== 0){
				if(Integer.parseInt(commonApproval.getStep_code()) > 40){

					BgabGascz008Dto appInfo = new BgabGascz008Dto();
					appInfo.setEmpno(commonApproval.getRpts_eeno());
					appInfo.setOrga_c(commonApproval.getRpts_dept());
					appInfo.setCorp_cd(commonApproval.getCorp_cd());

					applevelList.add(appInfo);

				}else{
					commonApproval.setResult("E");
					commonApproval.setMessage("부서가 존재하지 않습니다. 시스템 관리자에게 문의하세요.");
					return commonApproval;
				}
			}

			// 상신자와 결재자 동일인 체크
			if(StringUtil.isNullToString(applevelList.get(0).getEmpno()).equals(StringUtil.isNullToString(commonApproval.getRpts_eeno()))){
				rptsEqualChk = true;
			}

			for(int i = 0 ; i < applevelList.size() ; i++){

				// 결재자 정보 체크
				if(applevelList.get(i).getEmpno().equals("00000000") || StringUtil.isNullToString(applevelList.get(i).getEmpno()).equals("")){
					commonApproval.setResult("E");
//					commonApproval.setMessage("There is no person who can approve your request in upper department, Please ask system administrator.");
					commonApproval.setMessage("상위 부서에서 결재권자가 없습니다. 시스템 관리자에게 문의하세요.");
					return commonApproval;
				}

				CommonApproval approvalDetail = new CommonApproval();
				approvalDetail.setIf_id(commonApproval.getIf_id());
				approvalDetail.setLevel_index(cntLv);
				approvalDetail.setCorp_cd(commonApproval.getCorp_cd());

				// 상신자와 결재자 동일여부에 따른 result 값 디폴트 세팅
				if(i==0){
					if(rptsEqualChk){
						approvalDetail.setResult("Z");
					}else{
						approvalDetail.setResult("1");
					}
				}else if(i==1){
					if(rptsEqualChk){
						approvalDetail.setResult("1");
					}else{
						approvalDetail.setResult("0");
					}
				}else{
					approvalDetail.setResult("0");
				}

				if(!prev_empno.equals(applevelList.get(i).getEmpno())){
					approvalDetail.setRdcs_eeno(applevelList.get(i).getEmpno());
					approvalDetail.setRdcs_dept(applevelList.get(i).getOrga_c());

					// 결재 detail insert
					commonJobDao.insertApprovalDetailInfo(approvalDetail);
					prev_empno = applevelList.get(i).getEmpno();
					appLineCnt++;
					cntLv++;
				}
			}

			// Security 결재
			CommonApproval apprDetail = new CommonApproval();
			CommonApproval rdcsInfo = new CommonApproval();

			apprDetail.setIf_id(commonApproval.getIf_id());
			if(StringUtil.isNullToString(commonApproval.getAppr_type()).equals(appType02)){
				rdcsInfo.setCode1("2");
				CommonApproval securtiyInfo = commonJobDao.getSecurityNextApprovalInfo(rdcsInfo);
				if(null == securtiyInfo){
					cnt++;
				}else{
					apprDetail.setLevel_index(cntLv);
					apprDetail.setResult("0");
					apprDetail.setRdcs_eeno(securtiyInfo.getRdcs_eeno());
					apprDetail.setRdcs_dept(securtiyInfo.getRdcs_dept());
					// 결재 detail insert
					commonJobDao.insertApprovalDetailInfo(apprDetail);
					appLineCnt++;
					cntLv++;
				}
			}
			if(StringUtil.isNullToString(commonApproval.getAppr_type()).equals(appType03)){
				rdcsInfo.setCode1("3");
				CommonApproval securtiyInfo = commonJobDao.getSecurityNextApprovalInfo(rdcsInfo);
				if(null == securtiyInfo){
					cnt++;
				}else{
					apprDetail.setLevel_index(cntLv);
					apprDetail.setResult("0");
					apprDetail.setRdcs_eeno(securtiyInfo.getRdcs_eeno());
					apprDetail.setRdcs_dept(securtiyInfo.getRdcs_dept());
					// 결재 detail insert
					commonJobDao.insertApprovalDetailInfo(apprDetail);
					appLineCnt++;
					cntLv++;
				}
			}
			if(StringUtil.isNullToString(commonApproval.getAppr_type()).equals(appType04) || StringUtil.isNullToString(commonApproval.getAppr_type()).equals(appType05)){
				rdcsInfo.setCode1("A");
				rdcsInfo.setCode2(commonApproval.getCode2());
				CommonApproval securtiyInfo = commonJobDao.getSecurityNextApprovalInfo(rdcsInfo);
				if(null == securtiyInfo){
					cnt++;
				}else{
					apprDetail.setLevel_index(cntLv);
					apprDetail.setResult("0");
					apprDetail.setRdcs_eeno(securtiyInfo.getRdcs_eeno());
					apprDetail.setRdcs_dept(securtiyInfo.getRdcs_dept());
					// 결재 detail insert
					commonJobDao.insertApprovalDetailInfo(apprDetail);
					appLineCnt++;
					cntLv++;
				}
			}
			if(StringUtil.isNullToString(commonApproval.getAppr_type()).equals(appType05)){
				rdcsInfo.setCode1("4");
				CommonApproval securtiyInfo = commonJobDao.getSecurityNextApprovalInfo(rdcsInfo);
				if(null == securtiyInfo){
					cnt++;
				}else{
					apprDetail.setLevel_index(cntLv);
					apprDetail.setResult("0");
					apprDetail.setRdcs_eeno(securtiyInfo.getRdcs_eeno());
					apprDetail.setRdcs_dept(securtiyInfo.getRdcs_dept());
					// 결재 detail insert
					commonJobDao.insertApprovalDetailInfo(apprDetail);
					appLineCnt++;
					cntLv++;
				}
			}

			// rooms & meals 결재자 추가..
//			if(StringUtil.isNullToString(commonApproval.getSystem_code()).equals("RM")){
//				rdcsInfo.setDoc_no(commonApproval.getDoc_no());
//				CommonApproval roomMgmtInfo = commonJobDao.getRoomsNextApprovalInfo(rdcsInfo);
//				if(null == roomMgmtInfo){
//					cnt++;
//				}else{
//					if(!"".equals(StringUtil.isNullToString(roomMgmtInfo.getRdcs_eeno()))){
//						apprDetail.setLevel_index(cntLv);
//						apprDetail.setResult("0");
//						apprDetail.setRdcs_eeno(roomMgmtInfo.getRdcs_eeno());
//						apprDetail.setRdcs_dept(roomMgmtInfo.getRdcs_dept());
//						// 결재 detail insert
//						commonJobDao.insertApprovalDetailInfo(apprDetail);
//						appLineCnt++;
//						cntLv++;
//					}
//				}
//			}


			if(cnt > 0){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				commonApproval.setResult("E");
				commonApproval.setMessage("Please ask system administrator.");
			}else{
				// total level setting
				//commonApproval.setTot_level(applevelList.size());
				commonApproval.setTot_level(appLineCnt);

				// Next 결재자 setting
				commonApproval.setRdcs_eeno(applevelList.get(0).getEmpno());
				commonApproval.setRdcs_dept(applevelList.get(0).getOrga_c());


				// 상신자와 결재자 동일인일 경우 now level +1 setting
				if(rptsEqualChk){
					if(applevelList.size() > 1){
						commonApproval.setNow_level(2);
						commonApproval.setRdcs_eeno(applevelList.get(1).getEmpno());
						commonApproval.setRdcs_dept(applevelList.get(1).getOrga_c());
					}else{
						// 업무 table 결재 완료 update
						commonApproval.setResult("Z");
						//commonJobDao.updateSystemStatusByApprovalDoc(commonApproval);
					}
				}

				commonJobDao.updateSystemStatusByApprovalDoc(commonApproval);

				// 결재 마스터 insert
				commonJobDao.insertApprovalInfo(commonApproval);
				commonApproval.setResult("Z");

				// 메일발송 - 결재 상신
				String fromEeno   = commonApproval.getRpts_eeno_nm();
				String fromStepNm = commonApproval.getStep_nm();
				String toEeno     = commonApproval.getRdcs_eeno();
				String mode       = "approve";
				String title      = commonApproval.getTitle_nm();
				String corp_cd    = commonApproval.getCorp_cd();

				String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

				Submit.sendEmail(fromEeno, fromStepNm, toEeno, mailAdr, mode, title, corp_cd);
				
				// BMP 역할정보
				List<String> approveList = new ArrayList<String>();
				for(int i = 0 ; i < applevelList.size() ; i++){
					approveList.add(applevelList.get(i).getEmpno());
				}
				commonApproval.setApproveList(approveList);	
				
			}
		}
		return commonApproval;
	}

	/**
	 * 결재처리
	 * @return CommonApproval
	 * @param CommonApproval
	 * @throws SessionException
	 */
	public static CommonApproval setApprovalProcess(CommonApproval commonApproval, HttpServletRequest req) throws SessionException{

		String returnCd = "Z";
		String returnMsg = "";
		String pgs_st_cd = "";
		String corp_cd = commonApproval.getCorp_cd();
		
		CommonApproval rpts_info = commonJobDao.getApprovalRptsInfo(commonApproval);

		if(commonApproval.getResult().equals("R")){		// 반송

			// 현재 결재건  Setting
			CommonApproval approvalDetail = new CommonApproval();
			approvalDetail.setIf_id(commonApproval.getIf_id());
			approvalDetail.setResult(commonApproval.getResult());
			approvalDetail.setLevel_index(commonApproval.getLevel_index());
			approvalDetail.setReturn_detail(commonApproval.getReturn_detail());
			approvalDetail.setCorp_cd(commonApproval.getCorp_cd());

			commonJobDao.updateApprovalDetailInfo(approvalDetail);

			// 결재 Master Setting
			CommonApproval approvalMaster = new CommonApproval();
			approvalMaster.setIf_id(commonApproval.getIf_id());
			approvalMaster.setResult(commonApproval.getResult());
			approvalMaster.setNow_level(commonApproval.getLevel_index());
			approvalMaster.setCorp_cd(commonApproval.getCorp_cd());

			commonJobDao.updateApprovalInfo(approvalDetail);

			// 업무 상태값 select
			pgs_st_cd = commonJobDao.getSystemStatusByApproval(commonApproval);

			if(StringUtil.isNullToString(pgs_st_cd).equals("A")){
				// 업무 결재정보 update
				commonJobDao.updateSystemStatusByApproval(commonApproval);

				String fromEeno   = SessionInfo.getSess_name(req);
				String fromStepNm = SessionInfo.getSess_step_name(req);
				String mode       = "reject";
				String title      = commonApproval.getTitle_nm();
				String rtnText    = commonApproval.getReturn_detail();

				//결제자가 아닌 신청자에게 반송메일 발송
				commonApproval.setRdcs_eeno(commonApproval.getRpts_eeno());
				String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

				Submit.returnEmail(fromEeno, fromStepNm, mailAdr, mode, title,rtnText, corp_cd);
			}else{
				returnCd = "E";
				returnMsg = "You can't ask your request, Please ask system administrator.";
			}
		}else if(commonApproval.getResult().equals("Z")){		// 승인
			if(commonApproval.getLevel_index() < commonApproval.getTot_level()){	// NEXT 결재 존재
				// 현재 결재건  Setting
				CommonApproval approvalDetail = new CommonApproval();
				approvalDetail.setIf_id(commonApproval.getIf_id());
				approvalDetail.setResult(commonApproval.getResult());
				approvalDetail.setLevel_index(commonApproval.getLevel_index());
				approvalDetail.setReturn_detail("");
				approvalDetail.setCorp_cd(commonApproval.getCorp_cd());

				commonJobDao.updateApprovalDetailInfo(approvalDetail);

				// NEXT 결재건 Setting
				approvalDetail.setLevel_index(commonApproval.getLevel_index()+1);
				approvalDetail.setResult("1");
				approvalDetail.setReturn_detail("");

				commonJobDao.updateApprovalDetailInfo(approvalDetail);

				CommonApproval nextInfo = commonJobDao.getNextApprovalInfo(approvalDetail);

				// 결재 Master Setting
				CommonApproval approvalMaster = new CommonApproval();
				approvalMaster.setIf_id(commonApproval.getIf_id());
				approvalMaster.setNow_level(commonApproval.getLevel_index()+1);
				approvalMaster.setRdcs_eeno(nextInfo.getRdcs_eeno());	// 다음 결재자 setting
				approvalMaster.setRdcs_dept(nextInfo.getRdcs_dept());
				approvalMaster.setCorp_cd(commonApproval.getCorp_cd());

				commonJobDao.updateApprovalInfo(approvalMaster);

				// 다음 결재자에게 메일발송
				String fromEeno   = rpts_info.getRpts_name();
				String fromStepNm = rpts_info.getRpts_step();
				String toEeno     = nextInfo.getRdcs_eeno();
				String mode       = "approve";
				String title      = commonApproval.getTitle_nm();
				
				String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

				Submit.sendEmail(fromEeno, fromStepNm, toEeno, mailAdr, mode, title, corp_cd);

			}else{	// 최종결재

				// 현재 결재건  Setting
				CommonApproval approvalDetail = new CommonApproval();
				approvalDetail.setIf_id(commonApproval.getIf_id());
				approvalDetail.setResult(commonApproval.getResult());
				approvalDetail.setLevel_index(commonApproval.getLevel_index());
				approvalDetail.setReturn_detail("");
				approvalDetail.setCorp_cd(commonApproval.getCorp_cd());

				commonJobDao.updateApprovalDetailInfo(approvalDetail);


				// 결재 Master Setting
				CommonApproval approvalMaster = new CommonApproval();
				approvalMaster.setIf_id(commonApproval.getIf_id());
				approvalMaster.setNow_level(commonApproval.getLevel_index());
				approvalMaster.setResult(commonApproval.getResult());
				approvalMaster.setCorp_cd(commonApproval.getCorp_cd());

				commonJobDao.updateApprovalInfo(approvalMaster);

				// 업무 상태값 select
				pgs_st_cd = commonJobDao.getSystemStatusByApproval(commonApproval);

				if(StringUtil.isNullToString(pgs_st_cd).equals("A")){

					// 업무 결재정보 Setting
					approvalDetail.setTable_name(commonApproval.getTable_name());
					approvalDetail.setResult(commonApproval.getResult());
					approvalDetail.setRdcs_eeno(commonApproval.getRdcs_eeno());
					approvalDetail.setCorp_cd(commonApproval.getCorp_cd());

					commonJobDao.updateSystemStatusByApproval(approvalDetail);

					// 출입식수 최종 결재시  Confirm으로 처리되므로 Confirm메일 발송
					if(commonApproval.getIf_id().subSequence(0,2).equals("EM")){

						// 상신자에게 메일발송
						String fromEeno   = SessionInfo.getSess_name(req);
						String fromStepNm = SessionInfo.getSess_step_name(req);
						String title      = commonApproval.getTitle_nm();

						String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

						Submit.confirmEmail(fromEeno, fromStepNm, mailAdr, title);
					} else {
						//결제 완료시 담당자에게 메일을 발송한다.
						String fromEeno   = SessionInfo.getSess_name(req);
						String fromStepNm = SessionInfo.getSess_step_name(req);
						String title      = commonApproval.getTitle_nm();

						String systemCode = commonApproval.getIf_id().substring(0,2);

						BgabGascz007Dto bgabGascz007Dto = new BgabGascz007Dto();
						bgabGascz007Dto.setCorp_cd(commonApproval.getCorp_cd());

						if("RC".equals(systemCode)){		//휴양소
							bgabGascz007Dto.setXdsm_gubn1("M2");
							bgabGascz007Dto.setXdsm_gubn2("001");
						} else if("AF".equals(systemCode)){	//근무복
							bgabGascz007Dto.setXdsm_gubn1("M2");
							bgabGascz007Dto.setXdsm_gubn2("002");
						} else if("GF".equals(systemCode)){	//선물
							bgabGascz007Dto.setXdsm_gubn1("M2");
							bgabGascz007Dto.setXdsm_gubn2("003");
						} else if("BK".equals(systemCode)){	//도서
							bgabGascz007Dto.setXdsm_gubn1("M2");
							bgabGascz007Dto.setXdsm_gubn2("004");
						} else if("TR".equals(systemCode)){	//교육
							bgabGascz007Dto.setXdsm_gubn1("M2");
							bgabGascz007Dto.setXdsm_gubn2("007");
						} else if("BC".equals(systemCode)){	//명함
							bgabGascz007Dto.setXdsm_gubn1("M3");
							bgabGascz007Dto.setXdsm_gubn2("009");
						} else if("GS".equals(systemCode)){	//전산용품
							bgabGascz007Dto.setXdsm_gubn1("M3");
							bgabGascz007Dto.setXdsm_gubn2("010");
						} else if("OS".equals(systemCode)){	//사무용품
							bgabGascz007Dto.setXdsm_gubn1("M3");
							bgabGascz007Dto.setXdsm_gubn2("012");
						} else if("BT".equals(systemCode)){	//출장
							bgabGascz007Dto.setXdsm_gubn1("M4");
							bgabGascz007Dto.setXdsm_gubn2("014");
						} else if("BN".equals(systemCode)){	//출장
							bgabGascz007Dto.setXdsm_gubn1("M4");
							bgabGascz007Dto.setXdsm_gubn2("014");
						} else if("PS".equals(systemCode)){	//픽업
							bgabGascz007Dto.setXdsm_gubn1("M4");
							bgabGascz007Dto.setXdsm_gubn2("016");
						} else if("TX".equals(systemCode)){	//교통비
							bgabGascz007Dto.setXdsm_gubn1("M4");
							bgabGascz007Dto.setXdsm_gubn2("017");
						} else if("BV".equals(systemCode)){	//차량신청
							bgabGascz007Dto.setXdsm_gubn1("M4");
							bgabGascz007Dto.setXdsm_gubn2("018");
						} else if("FC".equals(systemCode)){	//주유비
							bgabGascz007Dto.setXdsm_gubn1("M4");
							bgabGascz007Dto.setXdsm_gubn2("020");
						} else if("VL".equals(systemCode)){	//운행일지
							bgabGascz007Dto.setXdsm_gubn1("M4");
							bgabGascz007Dto.setXdsm_gubn2("020");
						} else if("SE".equals(systemCode)){	//방문
							bgabGascz007Dto.setXdsm_gubn1("M5");
							bgabGascz007Dto.setXdsm_gubn2("008");
						} else if("RM".equals(systemCode)){	//회의실
							bgabGascz007Dto.setXdsm_gubn1("M5");
							bgabGascz007Dto.setXdsm_gubn2("011");
						} else if("CE".equals(systemCode)){	//증명서
							bgabGascz007Dto.setXdsm_gubn1("M5");
							bgabGascz007Dto.setXdsm_gubn2("013");
						} else if("LV".equals(systemCode)){	//연차
							bgabGascz007Dto.setXdsm_gubn1("M5");
							bgabGascz007Dto.setXdsm_gubn2("022");
						}

						List<BgabGascz007Dto> picList = commonJobDao.selectInfoToPicEMailAddr(bgabGascz007Dto);

						if(picList.size() > 0){
							SecureRandom sr = new SecureRandom();

							int i = sr.nextInt(picList.size());

							String mailAdr = picList.get(i).getXawy_mail();

							Submit.sendStandByConfirmEmail(fromEeno, fromStepNm, mailAdr, "", title, corp_cd);
						}

					}
				}else{
					returnCd = "E";
					returnMsg = "You can't ask your request, Please ask system administrator.";
				}
			}
		}else{
			returnCd = "E";
			returnMsg = "Please ask system administrator.";
		}

		return commonApproval;
	}

	/**
	 * 결재취소처리
	 * @return CommonApproval
	 * @param CommonApproval
	 */
	public static CommonApproval setApprovalCancelProcess(CommonApproval commonApproval){

		String returnCd = "Z";
		String returnMsg = "";
		String pgs_st_cd = "";

		// 결재상태 확인
		CommonApproval statusInfo = commonJobDao.getApprovalStatusInfo(commonApproval);

		if(StringUtil.isNullToString(statusInfo.getResult()).equals("A") && StringUtil.isNullToString(statusInfo.getCancel_yn()).equals("Y") && StringUtil.isNullToString(statusInfo.getCancel()).equals("N")){

			// 결재정보 취소처리 setting - cancel 컬럼을 Y 로 변경.
			CommonApproval cancelInfo = new CommonApproval();
			cancelInfo.setIf_id(commonApproval.getIf_id());
			cancelInfo.setCancel("Y");
			cancelInfo.setNow_level(1);
			cancelInfo.setCorp_cd(commonApproval.getCorp_cd());
			// 결재정보 취소처리
			commonJobDao.updateApprovalInfo(cancelInfo);

			// 업무 상태값 select
			pgs_st_cd = commonJobDao.getSystemStatusByApproval(commonApproval);

			if(StringUtil.isNullToString(pgs_st_cd).equals("A")){

				// 업무 결재정보 Setting
				CommonApproval jobInfo = new CommonApproval();
				jobInfo.setIf_id(commonApproval.getIf_id());
				jobInfo.setTable_name(commonApproval.getTable_name());
				jobInfo.setResult("C");
				jobInfo.setCorp_cd(commonApproval.getCorp_cd());

				commonJobDao.updateSystemStatusByApproval(jobInfo);
			}else{
				returnCd = "E";
				returnMsg = "You can't ask your request cancel, Please ask system administrator.";
			}
		}else{
			returnCd = "E";
			returnMsg = HncisMessageSource.getMessage("MSG.STAT.0006");
		}

		// 처리결과 전송
		commonApproval.setResult(returnCd);
		commonApproval.setMessage(returnMsg);

		return commonApproval;

	}

}
