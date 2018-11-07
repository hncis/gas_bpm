package com.hncis.gift.manager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.common.application.ApprovalGasc;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.FileUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.gift.dao.GiftDao;
import com.hncis.gift.manager.GiftManager;
import com.hncis.gift.vo.BgabGascgf01Dto;
import com.hncis.gift.vo.BgabGascgf02Dto;
import com.hncis.gift.vo.BgabGascgf03Dto;
import com.hncis.gift.vo.BgabGascgf04Dto;
import com.hncis.gift.vo.BgabGascgf05Dto;

@Service("giftManagerImpl")
public class GiftManagerImpl implements GiftManager{
    private transient Log logger = LogFactory.getLog(getClass());

    private static final String pCode = "P-B-003";
    private static final String sCode = "GASBZ01230010";
    private static final String rCode = "GASROLE01230030";
    private static final String adminID = "10000001";
    private static final String strMessege = "messege";

	@Autowired
	public GiftDao giftDao;
	
	@Autowired
	public CommonJobDao commonJobDao;


	@Override
	public List<BgabGascgf03Dto> selectGfToLrgCombo(BgabGascgf03Dto dto) {
		return giftDao.selectGfToLrgCombo(dto);
	}

	@Override
	public List<BgabGascgf04Dto> selectGfToMrgCombo(BgabGascgf04Dto dto) {
		return giftDao.selectGfToMrgCombo(dto);
	}


	@Override
	public List<BgabGascgf01Dto> selectGfToGiftList(BgabGascgf01Dto vo) {
		return giftDao.selectGfToGiftList(vo);
	}
	@Override
	public int selectGfToGiftCount(BgabGascgf01Dto vo) {
		return giftDao.selectGfToGiftCount(vo);
	}
	
	@Override
	public CommonMessage insertGfToGiftRequest(BgabGascgf02Dto dto) {
		CommonMessage message = new CommonMessage();

		try{

			if("".equals(StringUtil.isNullToString(dto.getDoc_no()))){
				if(giftDao.getChkRequestYmd(dto) == 0){
					message.setCode1("N");
					message.setMessage(HncisMessageSource.getMessage("REQUEST.0005"));
					return message;
				}
				if(giftDao.getChkRequestPossible(dto) > 0){
					message.setCode1("N");
					message.setMessage(HncisMessageSource.getMessage("REQUEST.0006"));
					return message;
				}
				String docNo = StringUtil.getDocNo();
				dto.setDoc_no(docNo);
			}
			int cnt = giftDao.insertGfToGiftRequest(dto);

			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
			message.setCode(dto.getDoc_no());
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (선물신청서작성) - 프로세스 정의서 참조
			String loginUserId = dto.getEeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = rCode;   //휴양소 담당자 역할코드
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add(adminID);

			BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );

		}catch (Exception e) {
			logger.error(strMessege, e);
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}

	@Override
	public CommonMessage approveGfToGiftRequest(BgabGascgf02Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) {
		BgabGascz002Dto userParam = new BgabGascz002Dto();
		userParam.setCorp_cd(dto.getCorp_cd());
		userParam.setXusr_empno(dto.getEeno());
		BgabGascz002Dto userInfo = commonJobDao.getXusrInfo(userParam);
		
		appInfo.setDoc_no(dto.getDoc_no());					// 문서번호
		appInfo.setSystem_code("GF");						// 시스템코드
		appInfo.setTable_name("bgab_gascgf02");				// 업무 테이블이름
		appInfo.setRpts_eeno(userInfo.getXusr_empno());		// 상신자 사번
		appInfo.setRpts_dept(userInfo.getXusr_dept_code());	// 상신자 부서코드
		appInfo.setStep_code(userInfo.getXusr_step_code());	// 상신자 직위코드
		appInfo.setRpts_eeno_nm(userInfo.getXusr_name());	// 상신자 성명
		appInfo.setStep_nm(userInfo.getXusr_step_name());	// 직위 이름
		appInfo.setTitle_nm(HncisMessageSource.getMessage("gift"));						// 업무 이름
		appInfo.setAppType("GF");							// 전결권자 업무
		appInfo.setMax_level(5);							// 해외 결재 LEVEL
		appInfo.setCorp_cd(userInfo.getCorp_cd());

		CommonApproval commonApproval = ApprovalGasc.setApprovalRequestUseYn(appInfo);

		dto.setIf_id(commonApproval.getIf_id());
		dto.setRpts_eeno(userInfo.getXusr_empno());

		if(commonApproval.getResult().equals("Z")){
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (선물 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (선물신청서작성) - 프로세스 정의서 참조
			String loginUserId = dto.getEeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = rCode;   //선물 담당자 역할코드
			
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
	public CommonMessage approveCancelGfToGiftRequest(BgabGascgf02Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) {
		if("".equals(StringUtil.isNullToString(dto.getIf_id()))){
			int cnt = giftDao.approveCancelGFToRequest(dto);
			
			if(cnt > 0){
				// BPM API호출
				String processCode = pCode; 	//프로세스 코드 (선물  프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (선물신청서작성) - 프로세스 정의서 참조
				String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
				String comment = null;
				String roleCode = rCode;   //선물  담당자 역할코드
				
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
			appInfo.setTable_name("bgab_gascgf02");
			appInfo.setCorp_cd(dto.getCorp_cd());
			
			CommonApproval commonApproval = ApprovalGasc.setApprovalCancelProcess(appInfo);

			if(commonApproval.getResult().equals("Z")){
				// BPM API호출
				String processCode = pCode; 	//프로세스 코드 (선물  프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (선물신청서작성) - 프로세스 정의서 참조
				String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
				String comment = null;
				String roleCode = rCode;   //선물  담당자 역할코드
				
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
	
	public CommonMessage confirmGFToRequest(BgabGascgf02Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt = giftDao.confirmGFToRequest(dto);
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
			message.setCode1("Y");
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (선물 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01230030";	//액티비티 코드 (선물 담당자확인) - 프로세스 정의서 참조
			String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
			String comment = null;
	
			BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
					
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
			message.setCode1("N");
		}
		return message;
	}
	
	public CommonMessage rejectGFToRequest(BgabGascgf02Dto dto){
		CommonMessage message = new CommonMessage();
		try{
			int cnt = giftDao.rejectGFToRequest(dto);
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
			message.setCode1("Y");
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (선물 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01230030";	//액티비티 코드 (선물 담당자확인) - 프로세스 정의서 참조
			String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
			
			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
			
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
			message.setCode1("N");
		}
		return message;
	}

	@Override
	public int deleteGfToRequestCancel(BgabGascgf02Dto dto){
								
		return giftDao.deleteGfToRequestCancel(dto);
	}

	@Override
	public int updateGfToStatus(BgabGascgf02Dto dto, List<BgabGascgf02Dto> uList){
		if("C".equals(dto.getPgs_st_cd())){
			giftDao.updateGfToGiftRequestCancelQty(uList);
		}
		return giftDao.updateGfToStatus(uList);
	}

	@Override
	public int selectCountGfToReqList(BgabGascgf02Dto dto) {
		return giftDao.selectCountGfToReqList(dto);
	}

	@Override
	public List<BgabGascgf02Dto> selectGfToReqList(BgabGascgf02Dto dto) {
		return giftDao.selectGfToReqList(dto);
	}

	@Override
	public void saveGfToLrgList(List<BgabGascgf03Dto> iList, List<BgabGascgf03Dto> uList) {

		int iCnt = giftDao.insertGfToLrgList(iList);
		int uCnt = giftDao.updateGfToLrgList(uList);
	}

	@Override
	public List<BgabGascgf03Dto> selectGfListToLrgInfo(BgabGascgf03Dto vo) {
		return giftDao.selectGfListToLrgInfo(vo);
	}

	@Override
	public void saveGfToMrgList(List<BgabGascgf04Dto> iList, List<BgabGascgf04Dto> uList) {

		int iCnt = giftDao.insertGfToMrgList(iList);
		int uCnt = giftDao.updateGfToMrgList(uList);
	}

	@Override
	public List<BgabGascgf04Dto> selectGfListToMrgInfo(BgabGascgf04Dto vo) {
		return giftDao.selectGfListToMrgInfo(vo);
	}

	@Override
	public void deleteGfToLrgList(List<BgabGascgf03Dto> dList) {

		int cnt1 = giftDao.deleteGfToLrgList(dList);
		int cnt2 = giftDao.deleteGfToMrgDtlList(dList);

	}

	@Override
	public void deleteGfToMrgList(List<BgabGascgf04Dto> dList) {
		int cnt1 = giftDao.deleteGfToMrgList(dList);
	}

	@Override
	public int selectCountGfToGiftMgmtList(BgabGascgf01Dto dto) {
		return giftDao.selectCountGfToGiftMgmtList(dto);
	}

	@Override
	public List<BgabGascgf01Dto> selectGfToGiftMgmtList(BgabGascgf01Dto dto) {
		List<BgabGascgf01Dto> dtoList = giftDao.selectGfToGiftMgmtList(dto);
		
		for(int i=0;i<dtoList.size();i++){
			if(dtoList.get(i).getUse_yn().equals("Y")){
				dtoList.get(i).setUse_yn_nm(HncisMessageSource.getMessage("use_y"));
			} else if(dtoList.get(i).getUse_yn().equals("N")){
				dtoList.get(i).setUse_yn_nm(HncisMessageSource.getMessage("use_n"));
			}
		}
		
		return dtoList;
	}

	@Override
	public CommonMessage isnertGfToGiftInfo(BgabGascgf01Dto dto) {

		CommonMessage message = new CommonMessage();

		try{

			int cnt = giftDao.isnertGfToGiftInfo(dto);

			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
			message.setCode(dto.getItem_seq());
		}catch (Exception e) {
			logger.error(strMessege, e);
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}

	@Override
	public CommonMessage deleteGfToGiftInfo(BgabGascgf01Dto dto) {

		CommonMessage message = new CommonMessage();

		try{

			int cnt = giftDao.deleteGfToGiftInfo(dto);

			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
			message.setCode(dto.getItem_seq());
		}catch (Exception e) {
			logger.error(strMessege, e);
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("DELETE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}

	@Override
	public BgabGascgf01Dto selectInfoGfToGiftInfo(BgabGascgf01Dto dto) {
		return giftDao.selectInfoGfToGiftInfo(dto);
	}
	
	@Override
	public BgabGascgf01Dto selectInfoGfToGiftInfoByIfId(BgabGascgf01Dto dto) {
		return giftDao.selectInfoGfToGiftInfoByIfId(dto);
	}

	@Override
	public BgabGascgf02Dto selectInfoGfToGiftRequstInfo(BgabGascgf02Dto dto) {
		return giftDao.selectInfoGfToGiftRequstInfo(dto);
	}


	@Override
	public void saveGfToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto){

		String msg        = "";
		String resultUrl  = "xgf06_file.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{

			paramVal[0] = "file_name";
			paramVal[1] = "old_file_name";
			paramVal[2] = "gift";

			result = FileUtil.uploadFileView(req, res, paramVal);

			if(result != null){
				if(result[0] != null){
					bgabGascZ011Dto.setOgc_fil_nm(result[0]);
					bgabGascZ011Dto.setFil_nm(result[5]);
					bgabGascZ011Dto.setFil_mgn_qty(Integer.parseInt(result[3]));
					Integer fileRs = giftDao.insertGfToFile(bgabGascZ011Dto);
				}
				msg = result[4];

			}else{
				resultUrl = "xgf06_file.gas";
				msg = HncisMessageSource.getMessage("FILE.0001");
			}
		}catch(Exception e){
			resultUrl = "xgf06_file.gas";
			msg = HncisMessageSource.getMessage("FILE.0001");
			logger.error(strMessege, e);
		}
		try{
			String dispatcherYN = "Y";
			req.setAttribute("hid_doc_no",  bgabGascZ011Dto.getDoc_no());
			req.setAttribute("hid_eeno",  bgabGascZ011Dto.getEeno());
			req.setAttribute("hid_pgs_st_cd",  bgabGascZ011Dto.getPgs_st_cd());
			req.setAttribute("hid_seq",  bgabGascZ011Dto.getSeq());
			req.setAttribute("dispatcherYN", dispatcherYN);
			req.setAttribute("csrfToken", bgabGascZ011Dto.getCsrfToken());
			req.setAttribute("message",  msg);
			req.setAttribute("saveYn",  "Y");
			req.getRequestDispatcher(resultUrl).forward(req, res);

			return;
		}catch(Exception e){
			logger.error(strMessege, e);
		}
		
	}

	@Override
	public List<BgabGascZ011Dto> getSelectGfToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return giftDao.getSelectGfToFile(bgabGascZ011Dto);
	}

	@Override
	public BgabGascZ011Dto getSelectGfToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return giftDao.getSelectGfToFileInfo(bgabGascZ011Dto);
	}

	@Override
	public int deleteGfToFile(List<BgabGascZ011Dto> bgabGascZ011IList){
		String fileResult = "";
		for(int i=0; i<bgabGascZ011IList.size(); i++){
			BgabGascZ011Dto fileInfo = bgabGascZ011IList.get(i);
			try {
				fileResult = FileUtil.deleteFile(fileInfo.getCorp_cd(), "gift", fileInfo.getOgc_fil_nm());
			} catch (IOException e) {
				logger.error(strMessege, e);
			}
		}
		Integer fileDRs = giftDao.deleteGfToFile(bgabGascZ011IList);
		return fileDRs;
	}

	@Override
	public BgabGascgf05Dto selectXgf08Info(BgabGascgf05Dto dto) {
		return giftDao.selectXgf08Info(dto);
	}

	@Override
	public CommonMessage saveXgf08Info(BgabGascgf05Dto dto) {

		CommonMessage message = new CommonMessage();

		try{

			if(giftDao.updateXgf08Info(dto) == 0){
				if(giftDao.insertXgf08Info(dto) == 0){
					throw new Exception();
				}
			}

			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		}catch (Exception e) {
			logger.error(strMessege, e);
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}

	
}