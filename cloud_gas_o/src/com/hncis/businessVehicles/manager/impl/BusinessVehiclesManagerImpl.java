package com.hncis.businessVehicles.manager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.businessTravel.vo.BgabGascbt06;
import com.hncis.businessVehicles.dao.BusinessVehiclesDao;
import com.hncis.businessVehicles.manager.BusinessVehiclesManager;
import com.hncis.businessVehicles.vo.BgabGascbv01Dto;
import com.hncis.businessVehicles.vo.BgabGascbv02Dto;
import com.hncis.businessVehicles.vo.BgabGascbv03Dto;
import com.hncis.common.application.ApprovalGasc;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.FileUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;

@Service("businessVehiclesManagerImpl")
public class BusinessVehiclesManagerImpl implements BusinessVehiclesManager{

	@Autowired
	public BusinessVehiclesDao businessVehiclesDao;

	@Autowired
	public CommonJobDao commonJobDao;

	/*************************************************************/
	/** vehicle list for dept page                  	   			                **/
	/*************************************************************/
	@Override
	public int getSelectCountBvToVehicleListForDept(BgabGascbv01Dto dto) {
		return businessVehiclesDao.getSelectCountBvToVehicleListForDept(dto);
	}

	@Override
	public List<BgabGascbv01Dto> getSelectListBvToVehicleListForDept(BgabGascbv01Dto dto) {
		return businessVehiclesDao.getSelectListBvToVehicleListForDept(dto);
	}

	/*************************************************************/
	/** request page                  	   		                **/
	/*************************************************************/
	@Override
	public BgabGascbv02Dto insertBvToRequest(BgabGascbv02Dto dto) {

		BgabGascbv02Dto reqDto = new BgabGascbv02Dto();

		if(dto.getDoc_no().equals("")){
			String docNo = StringUtil.getDocNo();
			dto.setDoc_no(docNo);

		}

		try{

			int cnt = businessVehiclesDao.insertBvToRequest(dto);

			if(cnt !=1){
				reqDto.setErrYn(true);
				reqDto.setErrMsg(HncisMessageSource.getMessage("SAVE.0001"));
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			else{

				// BPM API호출
				String processCode = "P-D-005"; 	//프로세스 코드 (차량신청 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = "GASBZ01450010";	//액티비티 코드 (차량신청 작성) - 프로세스 정의서 참조
				String loginUserId = dto.getEeno();	//로그인 사용자 아이디
				String comment = null;
				String roleCode = "GASROLE01450030";   //차량신청 담당자 역할코드
				//역할정보
				List<String> approveList = new ArrayList<String>();
				List<String> managerList = new ArrayList<String>();
				managerList.add("10000001");
				
				BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
				
				reqDto.setDoc_no(dto.getDoc_no());
			}

		} catch (Exception e) {
			reqDto.setErrYn(true);
			reqDto.setErrMsg(HncisMessageSource.getMessage("SAVE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return reqDto;
	}

	@Override
	public int deleteBvToRequest(BgabGascbv02Dto dto) {
		
		// BPM API호출
		String processCode = "P-D-005"; 	//프로세스 코드 (명함 프로세스) - 프로세스 정의서 참조
		String bizKey = dto.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01450010";	//액티비티 코드 (명함 신청서작성) - 프로세스 정의서 참조
		String loginUserId = dto.getEeno();	//로그인 사용자 아이디
		
		BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
		return businessVehiclesDao.deleteBvToRequest(dto);
	}

	@Override
	public CommonMessage setApproval(BgabGascbv02Dto reqDto, HttpServletRequest req) {

		CommonMessage message = new CommonMessage();
		CommonApproval appInfo = new CommonApproval();
		BgabGascz002Dto userParam = new BgabGascz002Dto();
		userParam.setCorp_cd(reqDto.getCorp_cd());
		userParam.setXusr_empno(reqDto.getEeno());
		BgabGascz002Dto userInfo = commonJobDao.getXusrInfo(userParam);

		appInfo.setDoc_no(reqDto.getDoc_no());					// 문서번호
		appInfo.setSystem_code("BV");							// 시스템코드
		appInfo.setTable_name("bgab_gascbv02");					// 업무 테이블이름
		appInfo.setRpts_eeno(userInfo.getXusr_empno());			// 상신자 사번
		appInfo.setRpts_dept(userInfo.getXusr_dept_code());		// 상신자 부서코드
		appInfo.setStep_code(userInfo.getXusr_step_code());		// 상신자 직위코드
		appInfo.setRpts_eeno_nm(userInfo.getXusr_name());		// 상신자 성명
		appInfo.setStep_nm(userInfo.getXusr_step_name());		// 직위 이름
		appInfo.setTitle_nm(HncisMessageSource.getMessage("bv"));				// 업무 이름
		appInfo.setAppType("BV");								// 전결권자 업무
		appInfo.setMax_level(5);								// 해외 결재 LEVEL
		appInfo.setCorp_cd(userInfo.getCorp_cd());

		CommonApproval commonApproval = ApprovalGasc.setApprovalRequestUseYn(appInfo);

		reqDto.setIf_id(commonApproval.getIf_id());
		reqDto.setRpts_eeno(userInfo.getXusr_empno());

		if(commonApproval.getResult().equals("Z")){
			
			// BPM API호출
			String processCode = "P-D-005"; 	//프로세스 코드 (픽업 프로세스) - 프로세스 정의서 참조
			String bizKey = reqDto.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01450010";	//액티비티 코드 (픽업 신청서작성) - 프로세스 정의서 참조
			String loginUserId = reqDto.getEeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = "GASROLE01450030";   //휴양소 담당자 역할코드
			
			//역할정보
			List<String> approveList = commonApproval.getApproveList();
			List<String> managerList = new ArrayList<String>();
			managerList.add("10000001");

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
	@Override
	public CommonMessage setApprovalCancel(BgabGascbv02Dto regDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) {
		
		if("".equals(StringUtil.isNullToString(regDto.getIf_id()))){
			int cnt = businessVehiclesDao.updateInfoBvToApproveStatus(regDto);

			if(cnt > 0){
				
				// BPM API호출
				String processCode = "P-D-005"; 		//프로세스 코드 (차량신청 프로세스) - 프로세스 정의서 참조
				String bizKey = regDto.getDoc_no();		//신청서 번호
				String statusCode = "GASBZ01450010";	//액티비티 코드 (차량신청서작성) - 프로세스 정의서 참조
				String loginUserId = regDto.getUpdr_eeno();		//로그인 사용자 아이디
				String comment = null;
				String roleCode = "GASROLE01450030";  	//담당자 역할코드
				
				//역할정보
				List<String> approveList = new ArrayList<String>();
				List<String> managerList = new ArrayList<String>();
				managerList.add("10000001");
				
				BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
				message.setMessage(HncisMessageSource.getMessage("APPROVE.0002"));
			}else{
				message.setMessage(HncisMessageSource.getMessage("APPROVE.0003"));
			}
		}else{
			appInfo.setIf_id(regDto.getIf_id());
			appInfo.setTable_name("bgab_gascbv02");
			appInfo.setCorp_cd(regDto.getCorp_cd());
			CommonApproval commonApproval = ApprovalGasc.setApprovalCancelProcess(appInfo);

			if(commonApproval.getResult().equals("Z")){
				
				// BPM API호출
				String processCode = "P-D-005"; 		//프로세스 코드 (차량신청 프로세스) - 프로세스 정의서 참조
				String bizKey = regDto.getDoc_no();		//신청서 번호
				String statusCode = "GASBZ01450010";	//액티비티 코드 (차량신청서작성) - 프로세스 정의서 참조
				String loginUserId = regDto.getUpdr_eeno();		//로그인 사용자 아이디
				String comment = null;
				String roleCode = "GASROLE01450030";  	//담당자 역할코드
				
				//역할정보
				List<String> approveList = new ArrayList<String>();
				List<String> managerList = new ArrayList<String>();
				managerList.add("10000001");
				
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
	public String getSelectApprovalInfoByBv(BgabGascbv02Dto rsReqDto) {
		return businessVehiclesDao.getSelectApprovalInfoByBv(rsReqDto);
	}

	@Override
	public int updateInfoBvToConfirm(BgabGascbv02Dto dto, HttpServletRequest req) throws SessionException {

		//컨펌 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();

		CommonApproval commonApproval = new CommonApproval();
		commonApproval.setRdcs_eeno(toEeno);
		commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		
		String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

		Submit.confirmEmail(fromEeno, fromStepNm, mailAdr, "Business Vehicles");
		
		// BPM API호출
		String processCode = "P-D-005"; 	//프로세스 코드 (차량신청 프로세스) - 프로세스 정의서 참조
		String bizKey = dto.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01450030";	//액티비티 코드 (담당자확인) - 프로세스 정의서 참조
		String loginUserId = dto.getAcpc_eeno();	//로그인 사용자 아이디
		String comment = null;

		BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);

		return businessVehiclesDao.updateInfoBvToConfirm(dto);
	}

	@Override
	public int updateInfoBvToConfirmCancel(BgabGascbv02Dto dto, HttpServletRequest req) throws SessionException {

		//컨펌취소 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String rtnText    = dto.getSnb_rson_sbc();

		CommonApproval commonApproval = new CommonApproval();
		commonApproval.setRdcs_eeno(toEeno);
		commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		
		String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

		Submit.confirmCancelEmail(fromEeno, fromStepNm, mailAdr, "Business Vehicles", rtnText);

		return businessVehiclesDao.updateInfoBvToConfirmCancel(dto);
	}


	@Override
	public BgabGascbv02Dto getSelectInfoBvToRequest(BgabGascbv02Dto dto) {
		return businessVehiclesDao.getSelectInfoBvToRequest(dto);
	}

	@Override
	public BgabGascbv02Dto getSelectInfoBvToRequestForApprove(BgabGascbv02Dto dto) {
		return businessVehiclesDao.getSelectInfoBvToRequestForApprove(dto);
	}
	/*************************************************************/
	/** list page                  	   			                **/
	/*************************************************************/
	@Override
	public int getSelectCountBvToList(BgabGascbv02Dto dto) {
		return businessVehiclesDao.getSelectCountBvToList(dto);
	}

	@Override
	public List<BgabGascbv02Dto> getSelectListBvToList(BgabGascbv02Dto dto) {
		List<BgabGascbv02Dto> list = businessVehiclesDao.getSelectListBvToList(dto); 
		for(BgabGascbv02Dto vo : list){
			vo.setStrt_trvg_dist(StringUtil.formatComma(StringUtil.isNullToString(vo.getStrt_trvg_dist(),"0")));
			vo.setFnh_trvg_dist(StringUtil.formatComma(StringUtil.isNullToString(vo.getFnh_trvg_dist(),"0")));
		}
		return list;
	}
	
	
	/*************************************************************/
	/** vehicles management page                  	   			                **/
	/*************************************************************/
	@Override
	public BgabGascbv01Dto insertBvToVehicleManagement(BgabGascbv01Dto dto) {

		BgabGascbv01Dto reqDto = new BgabGascbv01Dto();

		try{
			if("".equals(StringUtil.isNullToString(dto.getApl_strt_trvg_dist(), ""))){
				dto.setApl_strt_trvg_dist("0");
			}
			dto.setApl_strt_trvg_dist_dbl(Double.parseDouble(dto.getApl_strt_trvg_dist()));
			int cnt = businessVehiclesDao.insertBvToVehicleManagement(dto);

//			if(cnt != 1){
//				reqDto.setErrYn(true);
//				reqDto.setErrMsg(HncisMessageSource.getMessage("SAVE.0001"));
//				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			}
//			else
//			{
//				reqDto.setChss_no(dto.getChss_no());
//			}
			reqDto.setChss_no(dto.getChss_no());

		} catch (Exception e) {
			e.printStackTrace();
			reqDto.setErrYn(true);
			reqDto.setErrMsg(HncisMessageSource.getMessage("SAVE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return reqDto;
	}

	@Override
	public BgabGascbv01Dto getSelectInfoBvToVehicleManagement(BgabGascbv01Dto dto) {
		return businessVehiclesDao.getSelectInfoBvToVehicleManagement(dto);
	}

	@Override
	public int deleteBvToVehicleManagement(BgabGascbv01Dto dto) {
		return businessVehiclesDao.deleteBvToVehicleManagement(dto);
	}

	/*************************************************************/
	/** list page                  	   			                **/
	/*************************************************************/
	@Override
	public int getSelectCountBvToVehicleList(BgabGascbv01Dto dto) {
		return businessVehiclesDao.getSelectCountBvToVehicleList(dto);
	}

	@Override
	public List<BgabGascbv01Dto> getSelectListBvToVehicleList(BgabGascbv01Dto dto) {
		return businessVehiclesDao.getSelectListBvToVehicleList(dto);
	}

	/*************************************************************/
	/** code managerment page                  	                **/
	/*************************************************************/
	@Override
	public int getSelectCountBvToCodeManagement(BgabGascbv03Dto dto) {
		return businessVehiclesDao.getSelectCountBvToCodeManagement(dto);
	}

	@Override
	public List<BgabGascbv03Dto> getSelectListBvToCodeManagement(BgabGascbv03Dto dto) {
		return businessVehiclesDao.getSelectListBvToCodeManagement(dto);
	}

	@Override
	public int insertBvToCodeManagement(List<BgabGascbv03Dto> dto) {
		return businessVehiclesDao.insertBvToCodeManagement(dto);
	}

	@Override
	public int deleteBvToCodeManagement(List<BgabGascbv03Dto> dto) {
		return businessVehiclesDao.deleteBvToCodeManagement(dto);
	}

	/*************************************************************/
	/** Vehicle registration info list page                     **/
	/*************************************************************/
	@Override
	public int getSelectCountBvToVehicleRegInfo(BgabGascbv01Dto dto) {
		return businessVehiclesDao.getSelectCountBvToVehicleRegInfo(dto);
	}

	@Override
	public List<BgabGascbv01Dto> getSelectListBvToVehicleRegInfo(BgabGascbv01Dto dto) {

		return businessVehiclesDao.getSelectListBvToVehicleRegInfo(dto);
	}

	@Override
	public void saveBvToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto){

		String msg        = "";
		String resultUrl  = "xbv04_file.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{

			paramVal[0] = "file_name";
			paramVal[1] = "old_file_name";
			paramVal[2] = "businessVehicles";

			result = FileUtil.uploadFile(req, res, paramVal);
			System.out.println("result[0]="+result[0]);
			System.out.println("result[1]="+result[1]);
			System.out.println("result[2]="+result[2]);
			System.out.println("result[3]="+result[3]);
			System.out.println("result[4]="+result[4]);
			System.out.println("result[5]="+result[5]);

			if(result != null){
				if(result[0] != null){
					bgabGascZ011Dto.setOgc_fil_nm(result[0]);
					bgabGascZ011Dto.setFil_nm(result[5]);
					bgabGascZ011Dto.setFil_mgn_qty(Integer.parseInt(result[3]));
					Integer fileRs = businessVehiclesDao.insertBvToFile(bgabGascZ011Dto);
				}
				msg = result[4];

			}else{
				resultUrl = "xbv04_file.gas";
				msg = HncisMessageSource.getMessage("FILE.0001");
			}


		}catch(Exception e){
			resultUrl = "xbv04_file.gas";
			msg = HncisMessageSource.getMessage("FILE.0001");
			e.printStackTrace();
		}finally{
			try{
				System.out.println("getDoc_no="+bgabGascZ011Dto.getDoc_no());
				System.out.println("getEeno="+bgabGascZ011Dto.getEeno());
				System.out.println("getSeq="+bgabGascZ011Dto.getSeq());
				System.out.println("msg="+msg);

				String dispatcherYN = "Y";

				req.setAttribute("hid_doc_no",  bgabGascZ011Dto.getDoc_no());
				req.setAttribute("hid_eeno",  bgabGascZ011Dto.getEeno());
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
	public List<BgabGascZ011Dto> getSelectBvToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return businessVehiclesDao.getSelectBvToFile(bgabGascZ011Dto);
	}

	@Override
	public BgabGascZ011Dto getSelectBvToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return businessVehiclesDao.getSelectBvToFileInfo(bgabGascZ011Dto);
	}

	@Override
	public int deleteBvToFile(List<BgabGascZ011Dto> list){
		String fileResult = "";
		for(int i=0; i<list.size(); i++){
			BgabGascZ011Dto fileInfo = list.get(i);
			try {
				fileResult = FileUtil.deleteFile(fileInfo.getCorp_cd(), "businessVehicles", fileInfo.getOgc_fil_nm());
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("fileResult="+fileResult);
		}

		Integer fileDRs = businessVehiclesDao.deleteBvToFile(list);

		return fileDRs;
	}

	@Override
	public int updateInfoBvToReject(BgabGascbv02Dto dto, HttpServletRequest req) throws SessionException{

		//컨펌취소 메일 발송
			String fromEeno   = SessionInfo.getSess_name(req);
			String fromStepNm = SessionInfo.getSess_step_name(req);
			String toEeno     = dto.getEeno();
			String rtnText    = dto.getSnb_rson_sbc();
			String title      = HncisMessageSource.getMessage("bv");
			String corp_cd    = dto.getCorp_cd();

			CommonApproval commonApproval = new CommonApproval();
			commonApproval.setRdcs_eeno(toEeno);
			commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));
			
			String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

			Submit.returnEmail(fromEeno, fromStepNm, mailAdr,"", title, rtnText, corp_cd);
			
			// BPM API호출
			String processCode = "P-D-005"; 	//프로세스 코드 (차량신청 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01450030";	//액티비티 코드 (당당자 확인) - 프로세스 정의서 참조
			String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디

			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);

		return businessVehiclesDao.updateInfoBvToReject(dto);
	}

@Override
public void saveMaintenanceToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto){

		String msg        = "";
		String resultUrl  = "xbv07_file.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{

			paramVal[0] = "file_name";
			paramVal[1] = "old_file_name";
			paramVal[2] = "companyCar";

			result = FileUtil.uploadFile(req, res, paramVal);

			if(result != null){
				if(result[0] != null){
					bgabGascZ011Dto.setOgc_fil_nm(result[0]);
					bgabGascZ011Dto.setFil_nm(result[5]);
					bgabGascZ011Dto.setFil_mgn_qty(Integer.parseInt(result[3]));
					Integer fileRs = businessVehiclesDao.insertMaintenanceToFile(bgabGascZ011Dto);
				}
				msg = result[4];

			}else{
				resultUrl = "xbv07_file.gas";
				msg = HncisMessageSource.getMessage("FILE.0001");
			}
		}catch(Exception e){
			resultUrl = "xbv07_file.gas";
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
	public List<BgabGascZ011Dto> getSelectMaintenanceToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return businessVehiclesDao.getSelectMaintenanceToFile(bgabGascZ011Dto);
	}

	@Override
	public BgabGascZ011Dto getSelectMaintenanceToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return businessVehiclesDao.getSelectMaintenanceToFileInfo(bgabGascZ011Dto);
	}

	@Override
	public int deleteMaintenanceToFile(List<BgabGascZ011Dto> bgabGascZ011IList){
		String fileResult = "";
		for(int i=0; i<bgabGascZ011IList.size(); i++){
			BgabGascZ011Dto fileInfo = bgabGascZ011IList.get(i);
			try {
				fileResult = FileUtil.deleteFile(fileInfo.getCorp_cd(), "companyCar", fileInfo.getOgc_fil_nm());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Integer fileDRs = businessVehiclesDao.deleteMaintenanceToFile(bgabGascZ011IList);
		return fileDRs;
	}

	@Override
	public int getSelectCountMaintenanceExpenseManagement(BgabGascbt06 bgabGascbt06){
		return Integer.parseInt(businessVehiclesDao.getSelectCountMaintenanceExpenseManagement(bgabGascbt06));
	}

	@Override
	public List<BgabGascbt06> getSelectMaintenanceExpenseManagement(BgabGascbt06 bgabGascbt06){
		return businessVehiclesDao.getSelectMaintenanceExpenseManagement(bgabGascbt06);
	}

	@Override
	public int saveListToMaintenanceExpenseManagement(List<BgabGascbt06> dtoI, List<BgabGascbt06> dtoU){
		int iCnt = businessVehiclesDao.insertListToMaintenanceExpenseManagement(dtoI);
		int uCnt = businessVehiclesDao.updateListToMaintenanceExpenseManagement(dtoU);
		return iCnt+uCnt;
	}

	@Override
	public int deleteListToMaintenanceExpenseManagement(List<BgabGascbt06> dtoD){
		return businessVehiclesDao.deleteListToMaintenanceExpenseManagement(dtoD);
	}
}
