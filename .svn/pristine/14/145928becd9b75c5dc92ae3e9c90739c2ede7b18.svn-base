package com.hncis.security.manager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.common.application.ApprovalGasc;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.FileUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.security.dao.SecurityDao;
import com.hncis.security.manager.SecurityManager;
import com.hncis.security.vo.BgabGascse01;
import com.hncis.security.vo.BgabGascse02;
import com.hncis.security.vo.BgabGascse03;
import com.hncis.security.vo.BgabGascse04;
import com.hncis.security.vo.BgabGascse05;
import com.hncis.security.vo.BgabGascse06;

@Service("securityManagerImpl")
public class SecurityManagerImpl implements SecurityManager{
	@Autowired
	public SecurityDao securityDao;

	@Autowired
	public CommonJobDao commonJobDao;

	@Override
	public BgabGascse01 insertSecurityRequestVehicle(List<BgabGascse01> paramList){
		int cnt = 0;
//		String doc_no = StringUtil.getDocNo();

		for(int i=0; i<paramList.size(); i++){
//			if("".equals(paramList.get(i).getDoc_no())){
//				paramList.get(i).setDoc_no(doc_no);
//			}

			if(i==0){
				if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
					securityDao.insertSecurityRequestMaster(paramList.get(i));
				}else{
					securityDao.updateSecurityRequestMaster(paramList.get(i));
				}
			}
			if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
				cnt += securityDao.insertSecurityRequestVehicle(paramList.get(i));
			}else{
				cnt += securityDao.updateSecurityRequestVehicle(paramList.get(i));
			}
		}

		return paramList.get(0);
	}

	@Override
	public int deleteSecurityRequestVehicle(List<BgabGascse01> paramList){
		securityDao.deleteSecurityRequestMaster(paramList.get(0));
		return securityDao.deleteSecurityRequestVehicle(paramList);
	}

	@Override
	public List<BgabGascse01> getSelectSecurityRequestVehicle(BgabGascse01 param){
		return securityDao.getSelectSecurityRequestVehicle(param);
	}

	@Override
	public BgabGascse02 insertSecurityRequestMaterial(List<BgabGascse02> paramList){
		int cnt = 0;
//		String doc_no = StringUtil.getDocNo();

		for(int i=0; i<paramList.size(); i++){
//			if("".equals(paramList.get(i).getDoc_no())){
//				paramList.get(i).setDoc_no(doc_no);
//			}

			if(i==0){
				BgabGascse01 param = new BgabGascse01();
				param.setEeno(paramList.get(i).getEeno());
				param.setApply_date(paramList.get(i).getApply_date());
				param.setPgs_st_cd(paramList.get(i).getPgs_st_cd());
				param.setPurpose(paramList.get(i).getPurpose());
				param.setRemark(paramList.get(i).getRemark());
				param.setReason(paramList.get(i).getReason());
				param.setType(paramList.get(i).getType());
				param.setIpe_eeno(paramList.get(i).getIpe_eeno());
				param.setUpdr_eeno(paramList.get(i).getUpdr_eeno());
				param.setDoc_no(paramList.get(i).getDoc_no());
				param.setCorp_cd(paramList.get(i).getCorp_cd());
				if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
					securityDao.insertSecurityRequestMaster(param);
				}else{
					securityDao.updateSecurityRequestMaster(param);
				}
			}
			if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
				cnt += securityDao.insertSecurityRequestMaterial(paramList.get(i));
			}else{
				cnt += securityDao.updateSecurityRequestMaterial(paramList.get(i));
			}
		}
		return paramList.get(0);
	}

	@Override
	public int deleteSecurityRequestMaterial(List<BgabGascse02> paramList){
		BgabGascse01 param = new BgabGascse01();
		param.setEeno(paramList.get(0).getEeno());
		param.setApply_date(paramList.get(0).getApply_date());
		param.setType(paramList.get(0).getType());
		param.setDoc_no(paramList.get(0).getDoc_no());
		securityDao.deleteSecurityRequestMaster(param);
		return securityDao.deleteSecurityRequestMaterial(paramList);
	}

	@Override
	public List<BgabGascse02> getSelectSecurityRequestMaterial(BgabGascse02 param){
		return securityDao.getSelectSecurityRequestMaterial(param);
	}

	@Override
	public BgabGascse03 insertSecurityRequestDevices(List<BgabGascse03> paramList){
		int cnt = 0;
//		String doc_no = StringUtil.getDocNo();

		for(int i=0; i<paramList.size(); i++){
//			if("".equals(paramList.get(i).getDoc_no())){
//				paramList.get(i).setDoc_no(doc_no);
//			}

			if(i==0){
				BgabGascse01 param = new BgabGascse01();
				param.setEeno(paramList.get(i).getEeno());
				param.setApply_date(paramList.get(i).getApply_date());
				param.setPgs_st_cd(paramList.get(i).getPgs_st_cd());
				param.setPurpose(paramList.get(i).getPurpose());
				param.setType(paramList.get(i).getType());
				param.setRemark(paramList.get(i).getRemark());
				param.setReason(paramList.get(i).getReason());
				param.setIpe_eeno(paramList.get(i).getIpe_eeno());
				param.setUpdr_eeno(paramList.get(i).getUpdr_eeno());
				param.setDoc_no(paramList.get(i).getDoc_no());
				param.setCorp_cd(paramList.get(i).getCorp_cd());
				if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
					securityDao.insertSecurityRequestMaster(param);
				}else{
					securityDao.updateSecurityRequestMaster(param);
				}
			}
			if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
				cnt += securityDao.insertSecurityRequestDevices(paramList.get(i));
			}else{
				cnt += securityDao.updateSecurityRequestDevices(paramList.get(i));
			}
		}
		return paramList.get(0);
	}

	@Override
	public int deleteSecurityRequestDevices(List<BgabGascse03> paramList){
		BgabGascse01 param = new BgabGascse01();
		param.setEeno(paramList.get(0).getEeno());
		param.setApply_date(paramList.get(0).getApply_date());
		param.setType(paramList.get(0).getType());
		param.setDoc_no(paramList.get(0).getDoc_no());
		param.setCorp_cd(paramList.get(0).getCorp_cd());
		securityDao.deleteSecurityRequestMaster(param);
		return securityDao.deleteSecurityRequestDevices(paramList);
	}

	@Override
	public List<BgabGascse03> getSelectSecurityRequestDevices(BgabGascse03 param){
		return securityDao.getSelectSecurityRequestDevices(param);
	}

	@Override
	public BgabGascse04 insertSecurityRequestFilm(List<BgabGascse04> paramList){
		int cnt = 0;
//		String doc_no = StringUtil.getDocNo();
		for(int i=0; i<paramList.size(); i++){
//			if("".equals(paramList.get(i).getDoc_no())){
//				paramList.get(i).setDoc_no(doc_no);
//			}

			if(i==0){
				BgabGascse01 param = new BgabGascse01();
				param.setEeno(paramList.get(i).getEeno());
				param.setApply_date(paramList.get(i).getApply_date());
				param.setPgs_st_cd(paramList.get(i).getPgs_st_cd());
				param.setPurpose(paramList.get(i).getPurpose());
				param.setType(paramList.get(i).getType());
				param.setRemark(paramList.get(i).getRemark());
				param.setReason(paramList.get(i).getReason());
				param.setIpe_eeno(paramList.get(i).getIpe_eeno());
				param.setUpdr_eeno(paramList.get(i).getUpdr_eeno());
				param.setDoc_no(paramList.get(i).getDoc_no());
				param.setCorp_cd(paramList.get(i).getCorp_cd());
				if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
					securityDao.insertSecurityRequestMaster(param);
				}else{
					securityDao.updateSecurityRequestMaster(param);
				}
			}
			if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
				cnt += securityDao.insertSecurityRequestFilm(paramList.get(i));
			}else{
				cnt += securityDao.updateSecurityRequestFilm(paramList.get(i));
			}
		}
		return paramList.get(0);
	}

	@Override
	public int deleteSecurityRequestFilm(List<BgabGascse04> paramList){
		BgabGascse01 param = new BgabGascse01();
		param.setEeno(paramList.get(0).getEeno());
		param.setApply_date(paramList.get(0).getApply_date());
		param.setType(paramList.get(0).getType());
		param.setDoc_no(paramList.get(0).getDoc_no());
		securityDao.deleteSecurityRequestMaster(param);
		return securityDao.deleteSecurityRequestFilm(paramList);
	}

	@Override
	public List<BgabGascse04> getSelectSecurityRequestFilm(BgabGascse04 param){
		return securityDao.getSelectSecurityRequestFilm(param);
	}

	/**
	 * approve
	 * @throws SessionException
	 */
	@Override
	public String setSecurityApproval(BgabGascse01 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) throws SessionException{
		BgabGascz002Dto userParam = new BgabGascz002Dto();
		userParam.setCorp_cd(keyParamDto.getCorp_cd());
		userParam.setXusr_empno(keyParamDto.getKey_eeno());
		BgabGascz002Dto userInfo = commonJobDao.getXusrInfo(userParam);
		
		appInfo.setDoc_no(keyParamDto.getDoc_no());						// 문서번호
		appInfo.setSystem_code("SE");									// 시스템코드
		appInfo.setTable_name("bgab_gascse01");							// 업무 테이블이름
		appInfo.setRpts_eeno(userInfo.getXusr_empno());		// 상신자 사번
		appInfo.setRpts_dept(userInfo.getXusr_dept_code());	// 상신자 부서코드
		appInfo.setStep_code(userInfo.getXusr_step_code());	// 상신자 직위코드
		appInfo.setRpts_eeno_nm(userInfo.getXusr_name());		// 상신자 성명
		appInfo.setStep_nm(userInfo.getXusr_step_name());		// 직위 이름
		appInfo.setTitle_nm(HncisMessageSource.getMessage("vst"));							// 업무 이름
		appInfo.setAppType("SE");						// 전결권자 업무
		appInfo.setMax_level(5);					// 국내 결재 LEVEL - 2014.02.07 국내출장 결재레벨 해외출장과 동일하게 변경. (Suzi요청)
		appInfo.setCorp_cd(userInfo.getCorp_cd());
		String param1 = keyParamDto.getType();
						
		// 결재요청
		CommonApproval commonApproval = ApprovalGasc.setApprovalRequestUseYn(appInfo);

		if(commonApproval.getResult().equals("Z")){
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));
			message.setCode(keyParamDto.getPgs_st_cd());
			message.setCode1(keyParamDto.getIf_id());
			
			if(param1.equals("1") || param1.equals("3") || param1.equals("5")) {
				String processCode = "";
				String statusCode = "";
				String roleCode = "";
				
				if(param1.equals("5")){
					// BMP API호출
					processCode = "P-E-001"; 	//프로세스 코드 (방문객 프로세스) - 프로세스 정의서 참조
					statusCode = "GASBZ01510010";	//액티비티 코드 (방문객 신청서작성) - 프로세스 정의서 참조
					roleCode = "GASROLE01510030";   //방문객 담당자 역할코드
				} else if(param1.equals("1")){
					// BMP API호출
					processCode = "P-E-002"; 	//프로세스 코드 (방문차량출입 프로세스) - 프로세스 정의서 참조
					statusCode = "GASBZ01520010";	//액티비티 코드 (방문차량출입 신청서작성) - 프로세스 정의서 참조
					roleCode = "GASROLE01520030";   //방문차량출입 담당자 역할코드
				} else if(param1.equals("3")){
					// BMP API호출
					processCode = "P-E-003"; 	//프로세스 코드 (방문IT장비반입 프로세스) - 프로세스 정의서 참조
					statusCode = "GASBZ01530010";	//액티비티 코드 (방문IT장비반입 신청서작성) - 프로세스 정의서 참조
					roleCode = "GASROLE01530030";   //방문IT장비반입 담당자 역할코드
				}
				
				String bizKey = keyParamDto.getDoc_no();	//신청서 번호
				String loginUserId = keyParamDto.getUpdr_eeno();
				//역할정보
				List<String> approveList = new ArrayList<String>();
				List<String> managerList = new ArrayList<String>();
				managerList.add("10000001");
	
				BpmApiUtil.sendCompleteTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList);
			}
			
		}else{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0001"));
			message.setCode("");
			message.setCode1("");
		}

		return commonApproval.getResult();
	}

	@Override
	public Object updateRequestSecurityRequestVehicle(BgabGascse01 keyParamDto){
		return securityDao.updateRequestSecurityRequestVehicle(keyParamDto);
	}

	@Override
	public CommonMessage setSecurityApprovalCancel(BgabGascse01 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req){
		
		String param1 = keyParamDto.getType();
		if("".equals(StringUtil.isNullToString(keyParamDto.getIf_id()))){
			int cnt = securityDao.updateApproveCancelSecurityEntrance(keyParamDto);
			
			if(cnt > 0){
				message.setMessage(HncisMessageSource.getMessage("APPROVE.0002"));
				
				if(param1.equals("1") || param1.equals("3") || param1.equals("5")) {
					String processCode = ""; 
					String statusCode = "";				
					String roleCode = "";
					if(param1.equals("5")){
						// BPM API호출
						processCode = "P-E-001"; 	//프로세스 코드 (방문객 프로세스) - 프로세스 정의서 참조
						statusCode = "GASBZ01510010";	//액티비티 코드 (방문객 신청서작성) - 프로세스 정의서 참조				
						roleCode = "GASROLE01510030";   //방문객 담당자 역할코드
					} else if(param1.equals("1")){
						// BPM API호출
						processCode = "P-E-002"; 	//프로세스 코드 (방문차량출입 프로세스) - 프로세스 정의서 참조
						statusCode = "GASBZ01520010";	//액티비티 코드 (방문차량출입 신청서작성) - 프로세스 정의서 참조				
						roleCode = "GASROLE01520030";   //방문차량출입 담당자 역할코드
					} else {
						// BPM API호출
						processCode = "P-E-003"; 	//프로세스 코드 (방문IT장비반입 프로세스) - 프로세스 정의서 참조
						statusCode = "GASBZ01530010";	//액티비티 코드 (방문IT장비반입 신청서작성) - 프로세스 정의서 참조				
						roleCode = "GASROLE01530030";   //방문IT장비반입 담당자 역할코드
					}
				
					String bizKey = keyParamDto.getDoc_no();	//신청서 번호
					String loginUserId = keyParamDto.getUpdr_eeno();
					String comment = null;
					
					//역할정보
					List<String> approveList = new ArrayList<String>();
					List<String> managerList = new ArrayList<String>();
					managerList.add("10000001");
					
					BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );

				}
			}else{
				message.setMessage(HncisMessageSource.getMessage("APPROVE.0003"));
			}
		}else{
			appInfo.setIf_id(keyParamDto.getIf_id());
			appInfo.setTable_name("bgab_gascse01");
			appInfo.setCorp_cd(keyParamDto.getCorp_cd());
			CommonApproval commonApproval = ApprovalGasc.setApprovalCancelProcess(appInfo);
			
			if(commonApproval.getResult().equals("Z")){
				message.setMessage(HncisMessageSource.getMessage("REQUEST.0002"));

				if(param1.equals("1") || param1.equals("3") || param1.equals("5")) {
					String processCode = ""; 
					String statusCode = "";				
					String roleCode = "";
					if(param1.equals("5")){
						// BPM API호출
						processCode = "P-E-001"; 	//프로세스 코드 (방문객 프로세스) - 프로세스 정의서 참조
						statusCode = "GASBZ01510010";	//액티비티 코드 (방문객 신청서작성) - 프로세스 정의서 참조				
						roleCode = "GASROLE01510030";   //방문객 담당자 역할코드
					} else if(param1.equals("1")){
						// BPM API호출
						processCode = "P-E-002"; 	//프로세스 코드 (방문차량출입 프로세스) - 프로세스 정의서 참조
						statusCode = "GASBZ01520010";	//액티비티 코드 (방문차량출입 신청서작성) - 프로세스 정의서 참조				
						roleCode = "GASROLE01520030";   //방문차량출입 담당자 역할코드
					} else {
						// BPM API호출
						processCode = "P-E-003"; 	//프로세스 코드 (방문IT장비반입 프로세스) - 프로세스 정의서 참조
						statusCode = "GASBZ01530010";	//액티비티 코드 (방문IT장비반입 신청서작성) - 프로세스 정의서 참조				
						roleCode = "GASROLE01530030";   //방문IT장비반입 담당자 역할코드
					}
				
					String bizKey = keyParamDto.getDoc_no();	//신청서 번호
					String loginUserId = keyParamDto.getUpdr_eeno();
					String comment = null;
					
					//역할정보
					List<String> approveList = new ArrayList<String>();
					List<String> managerList = new ArrayList<String>();
					managerList.add("10000001");
					
					BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );

				}
			}else{
				message.setMessage(commonApproval.getMessage());
			}
		}

		return message;
	}

	@Override
	public Object updateRequestSecurityRequestMaterial(BgabGascse02 keyParamDto){
		return securityDao.updateRequestSecurityRequestMaterial(keyParamDto);
	}


	@Override
	public BgabGascse01 getSelectSecurtyRequestType(BgabGascse01 vo){
		return securityDao.getSelectSecurtyRequestType(vo);
	}

	@Override
	public int getSelectCountSecurityRequestConfirmList(BgabGascse01 param){
		return securityDao.getSelectCountSecurityRequestConfirmList(param);
	}

	@Override
	public List<BgabGascse01> getSelectSecurityRequestConfirmList(BgabGascse01 param){
		List<BgabGascse01> list = securityDao.getSelectSecurityRequestConfirmList(param);
		for(BgabGascse01 data : list){
			data.setNum_req(StringUtil.formatComma(StringUtil.isNullToString(data.getNum_req(),"0")));
		}
		
		return list;
	}

	@Override
	public String getSelectSecurityApprovalInfo(CommonApproval apprVo){
		return securityDao.getSelectSecurityApprovalInfo(apprVo);
	}

	@Override
	public int getSelectSecurityManagerMgmtListCount(BgabGascse05 param){
		return securityDao.getSelectSecurityManagerMgmtListCount(param);
	}

	@Override
	public List<BgabGascse05> getSelectSecurityManagerMgmtList(BgabGascse05 param){
		return securityDao.getSelectSecurityManagerMgmtList(param);
	}

	@Override
	public int getSelectSecurityManagerMgmtCheck(List<BgabGascse05> paramList){
		int cnt = 0;
		for(int i=0; i<paramList.size(); i++){
			if("U".equals(paramList.get(i).getGb()) && !"A".equals(paramList.get(i).getType())){
				if(!paramList.get(i).getOld_type().equals(paramList.get(i).getType())
						|| !paramList.get(i).getOld_eeno().equals(paramList.get(i).getEeno())
						|| !paramList.get(i).getOld_dept_cd().equals(paramList.get(i).getDept_cd())){
					cnt += securityDao.getSelectSecurityManagerMgmtCheck(paramList.get(i));
				}
			}else{
				cnt += securityDao.getSelectSecurityManagerMgmtCheck(paramList.get(i));
			}
		}
		return cnt;
	}

	@Override
	public int insertSecurityManagerMgmt(List<BgabGascse05> paramList){
		int cnt = 0;
		for(int i=0; i<paramList.size(); i++){
			if("U".equals(paramList.get(i).getGb())){
				cnt += securityDao.updateSecurityManagerMgmt(paramList.get(i));
			}else{
				cnt += securityDao.insertSecurityManagerMgmt(paramList.get(i));
			}
		}

		return cnt;
	}

	@Override
	public int deleteSecurityManagerMgmt(List<BgabGascse05> paramList){
		return securityDao.deleteSecurityManagerMgmt(paramList);
	}

	@Override
	public List<BgabGascse05> selectFilmComboArea(){
		return securityDao.selectFilmComboArea();
	}

	@Override
	public BgabGascse06 insertSecurityRequestEntrance(List<BgabGascse06> paramList){
		int cnt = 0;
//		String doc_no = StringUtil.getDocNo();

		for(int i=0; i<paramList.size(); i++){
//			if("".equals(paramList.get(i).getDoc_no())){
//				paramList.get(i).setDoc_no(doc_no);
//			}
			if(i==0){
				BgabGascse01 param = new BgabGascse01();
				param.setEeno(paramList.get(i).getEeno());
				param.setApply_date(paramList.get(i).getApply_date());
				param.setPgs_st_cd(paramList.get(i).getPgs_st_cd());
				param.setPurpose(paramList.get(i).getPurpose());
				param.setType(paramList.get(i).getType());
				param.setRemark(paramList.get(i).getRemark());
				param.setReason(paramList.get(i).getReason());
				param.setIpe_eeno(paramList.get(i).getIpe_eeno());
				param.setUpdr_eeno(paramList.get(i).getUpdr_eeno());
				param.setDoc_no(paramList.get(i).getDoc_no());
				param.setCorp_cd(paramList.get(i).getCorp_cd());
				if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
					securityDao.insertSecurityRequestMaster(param);
				}else{
					securityDao.updateSecurityRequestMaster(param);
				}
			}
			if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
				cnt += securityDao.insertSecurityRequestEntrance(paramList.get(i));
			}else{
				cnt += securityDao.updateSecurityRequestEntrance(paramList.get(i));
			}
		}
		return paramList.get(0);
	}

	@Override
	public int deleteSecurityRequestEntrance(List<BgabGascse06> paramList){
		BgabGascse01 param = new BgabGascse01();
		param.setEeno(paramList.get(0).getEeno());
		param.setApply_date(paramList.get(0).getApply_date());
		param.setType(paramList.get(0).getType());
		param.setDoc_no(paramList.get(0).getDoc_no());
		param.setUpdr_eeno(paramList.get(0).getUpdr_eeno());
		param.setCorp_cd(paramList.get(0).getCorp_cd());
		

		// BPM API호출
		String processCode = "P-E-001"; 	//프로세스 코드 (방문객 프로세스) - 프로세스 정의서 참조
		String bizKey = param.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01510010";	//액티비티 코드 (방문객 신청서작성) - 프로세스 정의서 참조
		String loginUserId = param.getUpdr_eeno();	//로그인 사용자 아이디

		BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
		
		securityDao.deleteSecurityRequestMaster(param);
		return securityDao.deleteSecurityRequestEntrance(paramList);
	}

	@Override
	public List<BgabGascse06> getSelectSecurityRequestEntrance(BgabGascse06 param){
		return securityDao.getSelectSecurityRequestEntrance(param);
	}

	@Override
	public BgabGascse01 getSelectSecurityRemark(BgabGascse01 param){
		return securityDao.getSelectSecurityRemark(param);
	}

	@Override
	public List<BgabGascZ011Dto> getSelectXVToFile(BgabGascZ011Dto btReqDto){
		return securityDao.getSelectXVToFile(btReqDto);
	}

	@Override
	public void saveXVToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto){
		String msg        = "";
		String resultUrl  = "xve_file.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{

			paramVal[0] = "file_name";
			paramVal[1] = "old_file_name";
			paramVal[2] = "security";

			result = FileUtil.uploadFile(req, res, paramVal);

			if(result != null){
				if(result[0] != null){
					bgabGascZ011Dto.setOgc_fil_nm(result[0]);
					bgabGascZ011Dto.setFil_nm(result[5]);
					bgabGascZ011Dto.setFil_mgn_qty(Integer.parseInt(result[3]));
					Integer fileRs = securityDao.insertXVToFile(bgabGascZ011Dto);
				}
				msg = result[4];

			}else{
				resultUrl = "xve_file.gas";
				msg = HncisMessageSource.getMessage("FILE.0001");
			}
		}catch(Exception e){
			resultUrl = "xve_file.gas";
			msg = HncisMessageSource.getMessage("FILE.0001");
			e.printStackTrace();
		}finally{
			try{
				String dispatcherYN = "Y";
				req.setAttribute("hid_doc_no",  bgabGascZ011Dto.getDoc_no());
				req.setAttribute("hid_eeno",  bgabGascZ011Dto.getEeno());
				req.setAttribute("hid_pgs_st_cd",  bgabGascZ011Dto.getPgs_st_cd());
				req.setAttribute("hid_seq",  bgabGascZ011Dto.getSeq());
				req.setAttribute("dispatcherYN", dispatcherYN);
				req.setAttribute("csrfToken", bgabGascZ011Dto.getCsrfToken());
				req.setAttribute("message",  msg);
				req.getRequestDispatcher(resultUrl).forward(req, res);

				return;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public int deleteXVToFile(List<BgabGascZ011Dto> bgabGascZ011IList){
		for(int i=0; i<bgabGascZ011IList.size(); i++){
			BgabGascZ011Dto fileInfo = bgabGascZ011IList.get(i);
			try {
				FileUtil.deleteFile(fileInfo.getCorp_cd(), "businessTravel", fileInfo.getOgc_fil_nm());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Integer fileDRs = securityDao.deleteXVToFile(bgabGascZ011IList);
		return fileDRs;
	}

	@Override
	public BgabGascZ011Dto getSelectXVToFileInfo(BgabGascZ011Dto btReqDto){
		return securityDao.getSelectXVToFileInfo(btReqDto);
	}

	@Override
	public int updateInfoSecurityToReject(BgabGascse01 dto, HttpServletRequest req) throws SessionException{
		return securityDao.updateInfoSecurityToReject(dto);
	}

	@Override
	public int updateApproveSecurityEntrance(BgabGascse01 dto){
		return securityDao.updateApproveSecurityEntrance(dto);
	}

	@Override
	public BgabGascse06 insertSecurityRequestEntranceCopy(List<BgabGascse06> paramList){
		String doc_no = StringUtil.getDocNo();

		for(int i=0; i<paramList.size(); i++){
			if("".equals(paramList.get(i).getDoc_no())){
				paramList.get(i).setDoc_no(doc_no);
			}
			if(i==0){
				BgabGascse01 param = new BgabGascse01();
				param.setEeno(paramList.get(i).getEeno());
				param.setApply_date(paramList.get(i).getApply_date());
				param.setPgs_st_cd(paramList.get(i).getPgs_st_cd());
				param.setPurpose(paramList.get(i).getPurpose());
				param.setType(paramList.get(i).getType());
				param.setRemark(paramList.get(i).getRemark());
				param.setReason(paramList.get(i).getReason());
				param.setIpe_eeno(paramList.get(i).getIpe_eeno());
				param.setUpdr_eeno(paramList.get(i).getUpdr_eeno());
				param.setDoc_no(paramList.get(i).getDoc_no());
				param.setCorp_cd(paramList.get(i).getCorp_cd());
				if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
					securityDao.insertSecurityRequestMaster(param);
				}else{
					securityDao.updateSecurityRequestMaster(param);
				}
			}
			securityDao.insertSecurityRequestEntrance(paramList.get(i));
		}
		return paramList.get(0);
	}

	@Override
	public BgabGascse03 insertSecurityRequestDevicesCopy(List<BgabGascse03> paramList){
		String doc_no = StringUtil.getDocNo();

		for(int i=0; i<paramList.size(); i++){
			if("".equals(paramList.get(i).getDoc_no())){
				paramList.get(i).setDoc_no(doc_no);
			}

			if(i==0){
				BgabGascse01 param = new BgabGascse01();
				param.setEeno(paramList.get(i).getEeno());
				param.setApply_date(paramList.get(i).getApply_date());
				param.setPgs_st_cd(paramList.get(i).getPgs_st_cd());
				param.setPurpose(paramList.get(i).getPurpose());
				param.setType(paramList.get(i).getType());
				param.setRemark(paramList.get(i).getRemark());
				param.setReason(paramList.get(i).getReason());
				param.setIpe_eeno(paramList.get(i).getIpe_eeno());
				param.setUpdr_eeno(paramList.get(i).getUpdr_eeno());
				param.setDoc_no(paramList.get(i).getDoc_no());
				param.setCorp_cd(paramList.get(i).getCorp_cd());
				if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
					securityDao.insertSecurityRequestMaster(param);
				}else{
					securityDao.updateSecurityRequestMaster(param);
				}
			}
			securityDao.insertSecurityRequestDevices(paramList.get(i));
		}
		return paramList.get(0);
	}

	@Override
	public BgabGascse02 insertSecurityRequestMaterialCopy(List<BgabGascse02> paramList){
		String doc_no = StringUtil.getDocNo();

		for(int i=0; i<paramList.size(); i++){
			if("".equals(paramList.get(i).getDoc_no())){
				paramList.get(i).setDoc_no(doc_no);
			}

			if(i==0){
				BgabGascse01 param = new BgabGascse01();
				param.setEeno(paramList.get(i).getEeno());
				param.setApply_date(paramList.get(i).getApply_date());
				param.setPgs_st_cd(paramList.get(i).getPgs_st_cd());
				param.setPurpose(paramList.get(i).getPurpose());
				param.setRemark(paramList.get(i).getRemark());
				param.setReason(paramList.get(i).getReason());
				param.setType(paramList.get(i).getType());
				param.setIpe_eeno(paramList.get(i).getIpe_eeno());
				param.setUpdr_eeno(paramList.get(i).getUpdr_eeno());
				param.setDoc_no(paramList.get(i).getDoc_no());
				param.setCorp_cd(paramList.get(i).getCorp_cd());
				if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
					securityDao.insertSecurityRequestMaster(param);
				}else{
					securityDao.updateSecurityRequestMaster(param);
				}
			}
			securityDao.insertSecurityRequestMaterial(paramList.get(i));
		}
		return paramList.get(0);
	}

	@Override
	public BgabGascse01 insertSecurityRequestVehicleCopy(List<BgabGascse01> paramList){
		String doc_no = StringUtil.getDocNo();

		for(int i=0; i<paramList.size(); i++){
			if("".equals(paramList.get(i).getDoc_no())){
				paramList.get(i).setDoc_no(doc_no);
			}

			if(i==0){
				if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
					securityDao.insertSecurityRequestMaster(paramList.get(i));
				}else{
					securityDao.updateSecurityRequestMaster(paramList.get(i));
				}
			}
			securityDao.insertSecurityRequestVehicle(paramList.get(i));
		}

		return paramList.get(0);
	}

	@Override
	public BgabGascse04 insertSecurityRequestFilmCopy(List<BgabGascse04> paramList){
		String doc_no = StringUtil.getDocNo();

		for(int i=0; i<paramList.size(); i++){
			if("".equals(paramList.get(i).getDoc_no())){
				paramList.get(i).setDoc_no(doc_no);
			}

			if(i==0){
				BgabGascse01 param = new BgabGascse01();
				param.setEeno(paramList.get(i).getEeno());
				param.setApply_date(paramList.get(i).getApply_date());
				param.setPgs_st_cd(paramList.get(i).getPgs_st_cd());
				param.setPurpose(paramList.get(i).getPurpose());
				param.setType(paramList.get(i).getType());
				param.setRemark(paramList.get(i).getRemark());
				param.setReason(paramList.get(i).getReason());
				param.setIpe_eeno(paramList.get(i).getIpe_eeno());
				param.setUpdr_eeno(paramList.get(i).getUpdr_eeno());
				param.setDoc_no(paramList.get(i).getDoc_no());
				param.setCorp_cd(paramList.get(i).getCorp_cd());
				if(null == paramList.get(i).getSeq() || "".equals(paramList.get(i).getSeq())){
					securityDao.insertSecurityRequestMaster(param);
				}else{
					securityDao.updateSecurityRequestMaster(param);
				}
			}
			securityDao.insertSecurityRequestFilm(paramList.get(i));
		}
		return paramList.get(0);
	}

	@Override
	public int selectCountSecurityVistiorCustomer(BgabGascse01 param) {
		return securityDao.selectCountSecurityVistiorCustomer(param);
	}

	@Override
	public List<BgabGascse06> selectSecurityVistiorCustomer(BgabGascse01 param) {
		return securityDao.selectSecurityVistiorCustomer(param);
	}

	@Override
	public int getSelectCountSecurityRequestList(BgabGascse01 param){
		int cnt = 0;
		if("1".equals(param.getType())){
			cnt = securityDao.getSelectCountSecurityRequestListType1(param);
		}else if("2".equals(param.getType())){
			cnt = securityDao.getSelectCountSecurityRequestListType2(param);
		}else if("3".equals(param.getType())){
			cnt = securityDao.getSelectCountSecurityRequestListType3(param);
		}else if("4".equals(param.getType())){
			cnt = securityDao.getSelectCountSecurityRequestListType4(param);
		}else if("5".equals(param.getType())){
			cnt = securityDao.getSelectCountSecurityRequestListType5(param);
		}
		return cnt;
	}

	@Override
	public List<BgabGascse01> getSelectSecurityRequestList(BgabGascse01 param){
		List<BgabGascse01> list = null;
		if("1".equals(param.getType())){
			list = securityDao.getSelectSecurityRequestListType1(param);
		}else if("2".equals(param.getType())){
			list = securityDao.getSelectSecurityRequestListType2(param);
		}else if("3".equals(param.getType())){
			list = securityDao.getSelectSecurityRequestListType3(param);
		}else if("4".equals(param.getType())){
			list = securityDao.getSelectSecurityRequestListType4(param);
		}
		return list;
	}
}
