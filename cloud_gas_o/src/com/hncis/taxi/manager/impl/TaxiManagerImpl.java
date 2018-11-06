package com.hncis.taxi.manager.impl;

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
import com.hncis.common.application.SessionInfo;
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
import com.hncis.system.dao.SystemDao;
import com.hncis.taxi.dao.TaxiDao;
import com.hncis.taxi.manager.TaxiManager;
import com.hncis.taxi.vo.BgabGasctx01;
import com.hncis.taxi.vo.BgabGasctx02;
import com.hncis.taxi.vo.BgabGasctx03;
import com.hncis.taxi.vo.BgabGasctx04;

@Service("taxiManagerImpl")
public class TaxiManagerImpl implements TaxiManager{
    private transient Log logger = LogFactory.getLog(getClass());

    private static final String pCode = "P-D-004";
    private static final String sCode = "GASBZ01440010";
    private static final String rCode = "GASROLE01440030";
    private static final String adminID = "10000001";

	@Autowired
	public TaxiDao taxiDao;

	@Autowired
	public SystemDao systemDao;

	@Autowired
	public CommonJobDao commonJobDao;

	@Override
	public List<BgabGasctx03> getComboListTxToTransport(BgabGasctx03 code) {
		return taxiDao.getComboListTxToTransport(code);
	}
	@Override
	public List<BgabGasctx03> getComboListTxFromPlace(BgabGasctx03 code) {
		return taxiDao.getComboListTxFromPlace(code);
	}
	@Override
	public List<BgabGasctx03> getComboListTxToPlace(BgabGasctx03 code) {
		return taxiDao.getComboListTxToPlace(code);
	}

	@Override
	public BgabGasctx03 getSelectInfoTxToTaxiAmount(BgabGasctx03 dto){
		return taxiDao.getSelectInfoTxToTaxiAmount(dto);
	}

	/**
	 * accept search
	 * @param keyParamDto
	 * @return
	 */
	@Override
	public int getSelectCountTXToAccept(BgabGasctx02 keyParamDto){
		return Integer.parseInt(taxiDao.getSelectCountTXToAccept(keyParamDto));
	}

	/**
	 * request apply
	 * @return
	 */
	@Override
	public Object insertInfoTXToRequest_1(BgabGasctx01 cgabGasctx01){
//		RfcBudgetCheck rfc = new RfcBudgetCheck("I");
//		RfcBudgetCheckVo i_BudgetInfo = new RfcBudgetCheckVo();
//		i_BudgetInfo.setI_gubn("I");
//		i_BudgetInfo.setI_date(CurrentDateTime.getDate());
//		i_BudgetInfo.setI_gjahr(CurrentDateTime.getYear());
//		i_BudgetInfo.setI_kostl("H4200");
//		i_BudgetInfo.setI_hkont("2100080");
//
//		RfcBudgetCheckVo o_BudgetInfo = null;
//		try {
//			o_BudgetInfo = rfc.getResult(i_BudgetInfo);
//		} catch (Exception e) {
//			logger.error("messege", e);
//		}
//
//
//		RfcPoCreate rfc = new RfcPoCreate();
//		RfcPoCreateVo i_PoInfo = new RfcPoCreateVo();
//		i_PoInfo.setI_date(CurrentDateTime.getDate());
//		i_PoInfo.setI_vendor_code("VENDORCODE");		// mapping
//		i_PoInfo.setI_vendor_name("VENDORNAME");		// mapping
//		i_PoInfo.setI_pur_org_code("H301");
//		i_PoInfo.setI_pur_group("B11");
//		i_PoInfo.setI_wrkplc_cd("Piracicaba");			// Piracicaba, São Paulo
//		i_PoInfo.setI_usn("37102488");
//		i_PoInfo.setI_account_category("K");			// K (부서비용), F (Internal order예산), P (WBS예산)
//		i_PoInfo.setI_material_code("MATERIALCODE");
//		i_PoInfo.setI_material_desc("MATERIALDESC");
//		i_PoInfo.setI_mat_group("H1_00002");
//		i_PoInfo.setI_qty("1");
//		i_PoInfo.setI_price("100");
//		i_PoInfo.setI_delivery_date("20160225");
//		i_PoInfo.setI_cost_cd("H4200");
//		i_PoInfo.setI_wbs_cd("1000");
//		i_PoInfo.setI_io_cd("AAAA");
//		i_PoInfo.setI_account_code("40000000");
//		i_PoInfo.setI_company_code("H301");
//
//		RfcPoCreateVo o_PoInfo = null;
//		try {
//			o_PoInfo = rfc.getResult(i_PoInfo);
//		} catch (Exception e) {
//			logger.error("messege", e);
//		}

		// BPM API호출
		String processCode = pCode; 	//프로세스 코드 (교통비 프로세스) - 프로세스 정의서 참조
		String bizKey = cgabGasctx01.getDoc_no();	//신청서 번호
		String statusCode = sCode;	//액티비티 코드 (교통비 작성) - 프로세스 정의서 참조
		String loginUserId = cgabGasctx01.getEeno();	//로그인 사용자 아이디
		String comment = null;
		String roleCode = rCode;   //명함신청 담당자 역할코드
		//역할정보
		List<String> approveList = new ArrayList<String>();
		List<String> managerList = new ArrayList<String>();
		managerList.add(adminID);
		
		BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );

		return taxiDao.insertInfoTXToRequest_1(cgabGasctx01);
	}
	@Override
	public Object insertInfoTXToRequest_2(BgabGasctx01 cgabGasctx01, List<BgabGasctx04> list){
		taxiDao.insertTxToRequestList(list);
		return taxiDao.insertInfoTXToRequest_2(cgabGasctx01);
	}

	/**
	 * request update
	 * @return
	 */
	@Override
	public Object updateInfoTXToRequest(BgabGasctx01 cgabGasctx01, List<BgabGasctx04> list){
//		BgabGasctx04 delParam = new BgabGasctx04();
//		delParam.setDoc_no(cgabGasctx01.getDoc_no());
//		delParam.setCorp_cd(cgabGasctx01.getCorp_cd());
//		taxiDao.deleteTxToRequestList(delParam);
		for(int i=0; i<list.size(); i++){
			if("".equals(list.get(i).getSeq())){
				taxiDao.insertTxToRequestRowData(list.get(i));
			}else{
				taxiDao.updateTxToRequestRowData(list.get(i));
			}
		}
		return taxiDao.updateInfoTXToRequest(cgabGasctx01);
	}

	/**
	 * request delete
	 * @return
	 */
	@Override
	public Object deleteInfoTXToRequest(BgabGasctx02 keyParamDto){
		BgabGasctx04 delParam = new BgabGasctx04();
		delParam.setDoc_no(keyParamDto.getDoc_no());
		delParam.setCorp_cd(keyParamDto.getCorp_cd());
		taxiDao.deleteTxToRequestList(delParam);
		
		// BPM API호출
		String processCode = pCode; 	//프로세스 코드 (명함 프로세스) - 프로세스 정의서 참조
		String bizKey = keyParamDto.getDoc_no();	//신청서 번호
		String statusCode = sCode;	//액티비티 코드 (명함 신청서작성) - 프로세스 정의서 참조
		String loginUserId = keyParamDto.getEeno();	//로그인 사용자 아이디
		
		BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
		return taxiDao.deleteInfoTXToRequest(keyParamDto);
	}

	@Override
	public Object deleteTxToRequestList(BgabGasctx04 keyParamDto){
		int dCnt = 0;
		taxiDao.deleteTxToRequestList(keyParamDto);
		taxiDao.updateInfoTxRequestAmount(keyParamDto);
		return dCnt;
	}

	/**
	 * approve
	 * @throws SessionException
	 */
	@Override
	public CommonMessage setApproval(BgabGasctx02 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) throws SessionException{
		
		BgabGascz002Dto userParam = new BgabGascz002Dto();
		userParam.setCorp_cd(keyParamDto.getCorp_cd());
		userParam.setXusr_empno(keyParamDto.getKey_eeno());
		BgabGascz002Dto userInfo = commonJobDao.getXusrInfo(userParam);
		
		appInfo.setDoc_no(keyParamDto.getDoc_no());					// 문서번호
		appInfo.setSystem_code("TX");								// 시스템코드
		appInfo.setTable_name("bgab_gasctx02");						// 업무 테이블이름
		appInfo.setRpts_eeno(userInfo.getXusr_empno());		// 상신자 사번
		appInfo.setRpts_dept(userInfo.getXusr_dept_code());	// 상신자 부서코드
		appInfo.setStep_code(userInfo.getXusr_step_code());	// 상신자 직위코드
		appInfo.setRpts_eeno_nm(userInfo.getXusr_name());		// 상신자 성명
		appInfo.setStep_nm(userInfo.getXusr_step_name());		// 직위 이름
		appInfo.setTitle_nm(HncisMessageSource.getMessage("trpt_cost"));								// 업무 이름
		appInfo.setAppType("TX");									// 전결권자 업무
		appInfo.setMax_level(5);									// 해외 결재 LEVEL
		appInfo.setCorp_cd(userInfo.getCorp_cd());

		CommonApproval commonApproval = ApprovalGasc.setApprovalRequestUseYn(appInfo);

		keyParamDto.setIf_id(commonApproval.getIf_id());
		keyParamDto.setRpts_eeno(SessionInfo.getSess_empno(req));

		if(commonApproval.getResult().equals("Z")){
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (교통비 프로세스) - 프로세스 정의서 참조
			String bizKey = keyParamDto.getDoc_no();	//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (교통비 신청서작성) - 프로세스 정의서 참조
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
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0001"));
			message.setErrorCode("E");
			message.setCode("");
			message.setCode1("");
		}

		return message;
	}

	@Override
	public CommonMessage setApprovalForSpecialAuth(BgabGasctx02 keyParamDto, HttpServletRequest req){

		CommonMessage message = new CommonMessage();
		try{
			taxiDao.updateInfoTXToApproveForSpecialAuth(keyParamDto);
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));
			message.setCode(keyParamDto.getKey_pgs_st_cd());
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setErrorCode("E");
			logger.error("messege", e);
		}
		return message;
	}

	/**
	 * approve cancel
	 * @throws SessionException 
	 */
	@Override
	public CommonMessage setApprovalCancel(BgabGasctx02 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) throws SessionException{
		if("".equals(StringUtil.isNullToString(keyParamDto.getIf_id()))){
			keyParamDto.setKey_pgs_st_cd("0");
			int cnt = taxiDao.updateInfoTXToApprove(keyParamDto);;
			
			if(cnt > 0){
				
				// BPM API호출
				String processCode = pCode; 		//프로세스 코드 (교통비 프로세스) - 프로세스 정의서 참조
				String bizKey = keyParamDto.getDoc_no();		//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (교통비 신청서작성) - 프로세스 정의서 참조
				String loginUserId = keyParamDto.getUpdr_eeno();		//로그인 사용자 아이디
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
			appInfo.setIf_id(keyParamDto.getIf_id());
			appInfo.setTable_name("bgab_gasctx02");
			appInfo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
			
			CommonApproval commonApproval = ApprovalGasc.setApprovalCancelProcess(appInfo);
			
			if(commonApproval.getResult().equals("Z")){
				
				// BPM API호출
				String processCode = pCode; 		//프로세스 코드 (교통비 프로세스) - 프로세스 정의서 참조
				String bizKey = keyParamDto.getDoc_no();		//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (교통비 신청서작성) - 프로세스 정의서 참조
				String loginUserId = keyParamDto.getUpdr_eeno();		//로그인 사용자 아이디
				String comment = null;
				String roleCode = rCode;  	//명함 담당자 역할코드
				
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

	/**
	 * request approve/confirm/confirm cancel
	 * @param keyParamDto
	 */
	@Override
	public int updateInfoTXToApprove(BgabGasctx02 keyParamDto){
		
		// BPM API호출
		String processCode = pCode; 	//프로세스 코드 (교통비 프로세스) - 프로세스 정의서 참조
		String bizKey = keyParamDto.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01440030";	//액티비티 코드 (교통비 담당자확인) - 프로세스 정의서 참조
		String loginUserId = keyParamDto.getUpdr_eeno();	//로그인 사용자 아이디
		String comment = null;

		BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
		return taxiDao.updateInfoTXToApprove(keyParamDto);
	}

	/**
	 * request search
	 * @return
	 */
	@Override
	public BgabGasctx01 getSelectInfoTXToRequest(BgabGasctx02 keyParamDto){
		return taxiDao.getSelectInfoTXToRequest(keyParamDto);
	}

	@Override
	public List<BgabGasctx04> getSelectListTxToRequestApprove(BgabGasctx04 dto){
		return taxiDao.getSelectListTxToRequestApprove(dto);
	}

	@Override
	public List<BgabGasctx04> getSelectListTxToRequest(BgabGasctx04 dto) {
		return taxiDao.getSelectListTxToRequest(dto);
	}

	@Override
	public String getSelectApprovalInfo(BgabGasctx02 keyParamDto){
		return taxiDao.getSelectApprovalInfo(keyParamDto);
	}

	@Override
	public BgabGasctx01 getSelectInfoTXToSubmit(BgabGasctx02 keyParamDto){
		return taxiDao.getSelectInfoTXToSubmit(keyParamDto);
	}

	@Override
	public int getSelectCountTXToList(BgabGasctx01 bgabGasctx01){
		return Integer.parseInt(taxiDao.getSelectCountTXToList(bgabGasctx01));
	}

	@Override
	public List<BgabGasctx01> getSelectTXToList(BgabGasctx01 bgabGasctx01){
		return taxiDao.getSelectTXToList(bgabGasctx01);
	}

	@Override
	public List<BgabGasctx01> getSelectListTXToAccept(BgabGasctx02 keyParamDto){
		return taxiDao.getSelectListTXToAccept(keyParamDto);
	}

	/*************************************************************/
	/** Taxi Place managerment page                          **/
	/*************************************************************/
	@Override
	public int getSelectCountTxToPlaceManagement(BgabGasctx03 dto) {
		return taxiDao.getSelectCountTxToPlaceManagement(dto);
	}

	@Override
	public List<BgabGasctx03> getSelectListTxToPlaceManagement(BgabGasctx03 dto) {
		return taxiDao.getSelectListTxToPlaceManagement(dto);
	}

	@Override
	public int insertTxToPlaceManagement(List<BgabGasctx03> dto) {
		return taxiDao.insertTxToPlaceManagement(dto);
	}

	@Override
	public int deleteTxToPlaceManagement(List<BgabGasctx03> dto) {
		return taxiDao.deleteTxToPlaceManagement(dto);
	}
	@Override
	public CommonMessage updateInfoTxToReject(BgabGasctx02 dto, HttpServletRequest req) throws SessionException{
		CommonMessage message = new CommonMessage();
		try{
			int cnt = taxiDao.updateInfoTxToReject(dto);
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
			message.setCode1("Y");
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (휴양소 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01440030";	//액티비티 코드 (휴양소 당당자 확인) - 프로세스 정의서 참조
			String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디

			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
			message.setCode1("N");
		}
		return message;
	}

	@Override
	public BgabGascz002Dto getSelectTaxiUserInfo(BgabGascz002Dto dto){
		return taxiDao.getSelectTaxiUserInfo(dto);
	}

@Override
public void saveTxToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto){

		String msg        = "";
		String resultUrl  = "xtx01_file.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{

			paramVal[0] = "file_name";
			paramVal[1] = "old_file_name";
			paramVal[2] = "taxi";

			result = FileUtil.uploadFileView(req, res, paramVal);

			if(result != null){
				if(result[0] != null){
					bgabGascZ011Dto.setOgc_fil_nm(result[0]);
					bgabGascZ011Dto.setFil_nm(result[5]);
					bgabGascZ011Dto.setFil_mgn_qty(Integer.parseInt(result[3]));
					Integer fileRs = taxiDao.insertTxToFile(bgabGascZ011Dto);
				}
				msg = result[4];

			}else{
				resultUrl = "xtx01_file.gas";
				msg = HncisMessageSource.getMessage("FILE.0001");
			}
		}catch(Exception e){
			resultUrl = "xtx01_file.gas";
			msg = HncisMessageSource.getMessage("FILE.0001");
			logger.error("messege", e);
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
				req.setAttribute("saveYn",  "Y");
				req.getRequestDispatcher(resultUrl).forward(req, res);

				return;
			}catch(Exception e){
				logger.error("messege", e);
			}
		}
	}

	@Override
	public List<BgabGascZ011Dto> getSelectTxToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return taxiDao.getSelectTxToFile(bgabGascZ011Dto);
	}

	@Override
	public BgabGascZ011Dto getSelectTxToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return taxiDao.getSelectTxToFileInfo(bgabGascZ011Dto);
	}

	@Override
	public int deleteTxToFile(List<BgabGascZ011Dto> bgabGascZ011IList){
		String fileResult = "";
		for(int i=0; i<bgabGascZ011IList.size(); i++){
			BgabGascZ011Dto fileInfo = bgabGascZ011IList.get(i);
			try {
				fileResult = FileUtil.deleteFile(fileInfo.getCorp_cd(), "taxi", fileInfo.getOgc_fil_nm());
			} catch (IOException e) {
				logger.error("messege", e);
			}
		}
		Integer fileDRs = taxiDao.deleteTxToFile(bgabGascZ011IList);
		return fileDRs;
	}

}
