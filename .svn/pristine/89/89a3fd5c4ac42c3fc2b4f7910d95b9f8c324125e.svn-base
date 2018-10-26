package com.hncis.certificate.manager.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.certificate.dao.CertificateDao;
import com.hncis.certificate.manager.CertificateManager;
import com.hncis.certificate.vo.BgabGascce01;
import com.hncis.common.application.ApprovalGasc;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;

@Service("certificateManagerImpl")
public class CertificateManagerImpl implements CertificateManager{

	@Autowired
	public CertificateDao certificateDao;
	
	@Autowired
	public CommonJobDao commonJobDao;
	
	public int insertCertificateToRequest(BgabGascce01 dto){
		int cnt = 0;
		if("".equals(dto.getHid_doc_no())){
			cnt = certificateDao.insertCertificateToRequest(dto);
		}else{
			cnt = certificateDao.updateCertificateToRequest(dto);
		}
		return cnt;
	}
	public int deleteCertificateToRequest(BgabGascce01 dto){
		return certificateDao.deleteCertificateToRequest(dto);
	}
	public BgabGascce01 getSelectCertificateToRequest(BgabGascce01 dto){
		return certificateDao.getSelectCertificateToRequest(dto);
	}
	public int getSelectGridCertificateToListCount(BgabGascce01 dto){
		return certificateDao.getSelectGridCertificateToListCount(dto);
	}
	public List<BgabGascce01> getSelectGridCertificateToList(BgabGascce01 dto){
		return certificateDao.getSelectGridCertificateToList(dto);
	}
	public CommonMessage updateCertificateToApprove(BgabGascce01 dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req){
		BgabGascz002Dto userParam = new BgabGascz002Dto();
		userParam.setCorp_cd(dto.getCorp_cd());
		userParam.setXusr_empno(dto.getEeno());
		BgabGascz002Dto userInfo = commonJobDao.getXusrInfo(userParam);
		
		appInfo.setDoc_no(dto.getDoc_no());					// 문서번호
		appInfo.setSystem_code("CE");								// 시스템코드
		appInfo.setTable_name("bgab_gascce01");						// 업무 테이블이름
		appInfo.setRpts_eeno(userInfo.getXusr_empno());		// 상신자 사번
		appInfo.setRpts_dept(userInfo.getXusr_dept_code());	// 상신자 부서코드
		appInfo.setStep_code(userInfo.getXusr_step_code());	// 상신자 직위코드
		appInfo.setRpts_eeno_nm(userInfo.getXusr_name());		// 상신자 성명
		appInfo.setStep_nm(userInfo.getXusr_step_name());		// 직위 이름
		appInfo.setTitle_nm(HncisMessageSource.getMessage("cert"));								// 업무 이름
		appInfo.setAppType("CE");									// 전결권자 업무
		appInfo.setMax_level(5);									// 해외 결재 LEVEL
		appInfo.setCorp_cd(userInfo.getCorp_cd());

		CommonApproval commonApproval = ApprovalGasc.setApprovalRequestUseYn(appInfo);

		dto.setIf_id(commonApproval.getIf_id());
		dto.setRpts_eeno(userInfo.getXusr_empno());

		if(commonApproval.getResult().equals("Z")){
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));
		}else{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0001"));
			message.setErrorCode("E");
			message.setCode("");
			message.setCode1("");
		}

		return message;
		
		
//		return certificateDao.updateCertificateToApprove(dto);
	}
	
	public CommonMessage updateCertificateToApproveCancel(BgabGascce01 dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req){
		if("".equals(StringUtil.isNullToString(dto.getIf_id()))){
			int cnt = certificateDao.updateCertificateToApprove(dto);
			
			if(cnt > 0){
				message.setMessage(HncisMessageSource.getMessage("APPROVE.0002"));
			}else{
				message.setMessage(HncisMessageSource.getMessage("APPROVE.0003"));
			}
		}else{
			appInfo.setIf_id(dto.getIf_id());
			appInfo.setTable_name("bgab_gascce01");
			appInfo.setCorp_cd(dto.getCorp_cd());
			
			CommonApproval commonApproval = ApprovalGasc.setApprovalCancelProcess(appInfo);

			if(commonApproval.getResult().equals("Z")){
				message.setMessage(HncisMessageSource.getMessage("APPROVE.0002"));
			}else{
				message.setMessage(commonApproval.getMessage());
			}
		}
		
		return message;
		
		
//		return certificateDao.updateCertificateToApprove(dto);
	}
	
	public int updateCertificateToConfirm(BgabGascce01 dto){
		return certificateDao.confirmCEToRequest(dto);
	}
	
	public int updateCertificateToReject(BgabGascce01 dto){
		return certificateDao.updateInfoCEToReject(dto);
	}
}
