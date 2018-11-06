package com.hncis.shuttleBus.manager.impl;

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
import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonMessage;
import com.hncis.shuttleBus.dao.ShuttleBusDao;
import com.hncis.shuttleBus.manager.ShuttleBusManager;
import com.hncis.shuttleBus.vo.BgabGascsb01;
import com.hncis.shuttleBus.vo.BgabGascsb02;
import com.hncis.shuttleBus.vo.BgabGascsb03;
import com.hncis.shuttleBus.vo.BgabGascsb04;

@Service("shuttleBusManagerImpl")
public class ShuttleBusManagerImpl implements ShuttleBusManager{
    private transient Log logger = LogFactory.getLog(getClass());

    private static final String msgCode = "FILE.0001";
	@Autowired
	public ShuttleBusDao shuttleBusDao;

	@Autowired
	public CommonJobDao commonJobDao;

	@Override
	public void insertShuttleBusRequstNew(HttpServletRequest req, HttpServletResponse res, BgabGascsb01 vo){
		String msg        = "";
		String status     = "true";
		String resultUrl  = "xsb03_hidden.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{
			paramVal[0] = "file_name";
			paramVal[1] = "old_file_name";
			paramVal[2] = "shuttleBus";

			result = FileUtil.uploadFile(req, res, paramVal);
			if(result != null || !"".equals(vo.getOrg_fil_nm())){
				if(result != null && result[0] != null){
					vo.setFil_nm(result[0]);
					vo.setOrg_fil_nm(result[5]);
					vo.setFil_size(result[3]);
				}
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultUrl = "xsb03_hidden.gas";
				msg = HncisMessageSource.getMessage(msgCode);
				status = "false";
			}

			// 저장
			if("".equals(vo.getDoc_no())){
				String doc_no = StringUtil.getDocNo();
				vo.setDoc_no(doc_no);
				shuttleBusDao.updateShuttleBusRequstFinalN(vo);
				shuttleBusDao.insertShuttleBusRequstNew(vo);
			}else{
				shuttleBusDao.updateShuttleBusRequstNew(vo);
			}

			msg = HncisMessageSource.getMessage("SAVE.0000");
		}catch(Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultUrl = "xsb03_hidden.gas";
			msg = HncisMessageSource.getMessage(msgCode);
			status = "false";
			logger.error("messege", e);
		}finally{
			try{
				req.setAttribute("status",  status);
				req.setAttribute("doc_no",  vo.getDoc_no());
				req.setAttribute("eeno",  vo.getEeno());
				req.setAttribute("message",  msg);
				req.getRequestDispatcher(resultUrl).forward(req, res);

				return;
			}catch(Exception e){
				logger.error("messege", e);
			}
		}
	}

	@Override
	public int saveShuttleBusRequst(BgabGascsb01 param){
		int cnt = 0;
		if("".equals(param.getHid_doc_no())){
			cnt = shuttleBusDao.insertShuttleBusRequst(param);
		}else{
			cnt = shuttleBusDao.updateShuttleBusRequst(param);
		}
		if(cnt > 0){
			// BPM API호출
			String processCode = "P-C-004"; 	//프로세스 코드 (통근버스 프로세스) - 프로세스 정의서 참조
			String bizKey = param.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01340010";	//액티비티 코드 (통근버스신청서작성) - 프로세스 정의서 참조
			String loginUserId = param.getEeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = "GASROLE01340030";   //담당자 역할코드
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add("10000001");
			
			BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
		}
		return cnt;
	}
	
	@Override
	public int deleteShuttleBusRequstNew(BgabGascsb01 param){
		return shuttleBusDao.deleteShuttleBusRequstNew(param);
	}

	@Override
	public CommonMessage requestShuttleBusRequstNew(BgabGascsb01 dto){
		CommonMessage message = new CommonMessage();

		int cnt = shuttleBusDao.updateShuttleBusApprovalStatus(dto);

		if(cnt > 0){
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));
			
			// BPM API호출
			String processCode = "P-C-004"; 	//프로세스 코드 (통근버스 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01340010";	//액티비티 코드 (통근버스신청서작성) - 프로세스 정의서 참조
			String loginUserId = "";
			if(dto.getPgs_st_cd().equals("3")){ // 신청
				loginUserId = dto.getEeno();	//로그인 사용자 아이디
			}else { // 신청취소
				loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
			}
			String comment = null;
			String roleCode = "GASROLE01340030";   //담당자 역할코드
			
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			approveList.add("10000001");
			managerList.add("10000001");
			
			if(dto.getPgs_st_cd().equals("3")){ // 신청
				BpmApiUtil.sendCompleteTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList);
			}else if(dto.getPgs_st_cd().equals("0")){ // 신청취소
				BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
			}else{ // 만료
				statusCode = "GASBZ01340030";
				BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
			}
		}else{
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0001"));
			message.setErrorCode("E");
		}

		return message;
	}

	@Override
	public CommonApproval requestShuttleBusNewRequstCancel(BgabGascsb01 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) throws SessionException{
		appInfo.setIf_id(keyParamDto.getIf_id());
		appInfo.setTable_name("bgab_gascsb01");
		appInfo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		CommonApproval commonApproval = ApprovalGasc.setApprovalCancelProcess(appInfo);

		keyParamDto.setKey_mode("requestCancel");
		keyParamDto.setKey_pgs_st_cd("0");
		updateShuttleBusRequstNewRequest(keyParamDto);

		if(commonApproval.getResult().equals("Z")){
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0002"));
		}else{
			message.setMessage(commonApproval.getMessage());
		}

		return commonApproval;
	}

	@Override
	public Object updateShuttleBusRequstNewRequest(BgabGascsb01 param){
		return shuttleBusDao.updateShuttleBusRequstNewRequest(param);
	}

	@Override
	public int updateShuttleBusRequstConfirmFinal(BgabGascsb01 param){
		return shuttleBusDao.updateShuttleBusRequstConfirmFinal(param);
	}

	@Override
	public BgabGascsb01 getSelectShuttleBusNewInfo(BgabGascsb01 param){
		return shuttleBusDao.getSelectShuttleBusNewInfo(param);
	}

	@Override
	public int getSelectShuttleBusListCount(BgabGascsb01 param){
		return shuttleBusDao.getSelectShuttleBusListCount(param);
	}

	@Override
	public List<BgabGascsb01> getSelectShuttleBusList(BgabGascsb01 param){
		return shuttleBusDao.getSelectShuttleBusList(param);
	}

	@Override
	public List<BgabGascsb02> getSelectTransporteFretadoList(BgabGascsb02 param){
		return shuttleBusDao.getSelectTransporteFretadoList(param);
	}
	@Override
	public List<BgabGascsb02> getSelectPonteExistenteList(BgabGascsb02 param){
		return shuttleBusDao.getSelectPonteExistenteList(param);
	}

	@Override
	public int insertTransporteFretadoList(List<BgabGascsb02> paramList){
		int cnt = 0;
		for(int i=0; i<paramList.size(); i++){
			if("".equals(paramList.get(i).getFret_seq())){
				cnt += shuttleBusDao.insertTransporteFretadoList(paramList.get(i));
			}else{
				cnt += shuttleBusDao.updateTransporteFretadoList(paramList.get(i));
			}
		}

		return cnt;
	}

	@Override
	public int insertPontoExistenteList(List<BgabGascsb02> paramList){
		int cnt = 0;
		for(int i=0; i<paramList.size(); i++){
			if("".equals(paramList.get(i).getSeq())){
				cnt += shuttleBusDao.insertPontoExistenteList(paramList.get(i));
			}else{
				cnt += shuttleBusDao.updatePontoExistenteList(paramList.get(i));
			}
		}

		return cnt;
	}

	@Override
	public int deleteTransporteFretadoList(List<BgabGascsb02> param){
		return shuttleBusDao.deleteTransporteFretadoList(param);
	}

	@Override
	public int deletePontoExistenteList(List<BgabGascsb02> param){
		return shuttleBusDao.deletePontoExistenteList(param);
	}

	@Override
	public BgabGascsb01 getSelectShuttleBusRequestCheck(BgabGascsb01 param){
		return shuttleBusDao.getSelectShuttleBusRequestCheck(param);
	}

	@Override
	public BgabGascsb03 getSelectShuttleBusBeforeView(BgabGascsb01 param){
		return shuttleBusDao.getSelectShuttleBusBeforeView(param);
	}

	@Override
	public List<BgabGascsb02> getSelectShuttleBusLineCombo(BgabGascsb02 dto){
		return shuttleBusDao.getSelectShuttleBusLineCombo(dto);
	}

	@Override
	public int getSelectPonteExistentePopupListCount(BgabGascsb02 param){
		return shuttleBusDao.getSelectPonteExistentePopupListCount(param);
	}

	@Override
	public List<BgabGascsb02> getSelectPonteExistentePopupList(BgabGascsb02 param){
		return shuttleBusDao.getSelectPonteExistentePopupList(param);
	}

	@Override
	public BgabGascsb04 getSelectShuttleBusSapInformationView(BgabGascsb04 param){
		return shuttleBusDao.getSelectShuttleBusSapInformationView(param);
	}

	@Override
	public List<CommonCode> getSelectWorkShiftComboList(CommonCode param){
		return shuttleBusDao.getSelectWorkShiftComboList(param);
	}
	@Override
	public int updateInfoSbToReject(BgabGascsb01 param, HttpServletRequest req) throws SessionException{

		//컨펌취소 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = param.getEeno();
		String rtnText    = param.getSnb_rson_sbc();

		CommonApproval commonApproval = new CommonApproval();
		commonApproval.setRdcs_eeno(toEeno);
		commonApproval.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonJobDao.getSelectInfoToEenoEmailAdr(commonApproval);

		Submit.returnEmail(fromEeno, fromStepNm, mailAdr,"", "Shuttle Bus", rtnText, SessionInfo.getSess_corp_cd(req));

		return shuttleBusDao.updateInfoSbToReject(param);
	}

	@Override
	public BgabGascsb01 getSelectShuttleBusChangeToDocNo(BgabGascsb01 param){
		return shuttleBusDao.getSelectShuttleBusChangeToDocNo(param);
	}

	@Override
	public BgabGascsb02 getSelectShuttleBusLineTime(BgabGascsb02 dto){
		return shuttleBusDao.getSelectShuttleBusLineTime(dto);
	}

	@Override
	public String insertShuttleBusRequstInclusion(BgabGascsb01 dto){
		String doc_no = StringUtil.getDocNo();
		dto.setDoc_no(doc_no);
		shuttleBusDao.updateShuttleBusRequstFinalN(dto);
		shuttleBusDao.insertShuttleBusRequstNew(dto);
		return doc_no;
	}

	@Override
	public void saveShuttleBusToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto){

		String msg        = "";
		String resultUrl  = "xsb04_file.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{

			paramVal[0] = "file_name";
			paramVal[1] = "old_file_name";
			paramVal[2] = "shuttleBus";

			result = FileUtil.uploadFile(req, res, paramVal);

			if(result != null){
				if(result[0] != null){
					bgabGascZ011Dto.setOgc_fil_nm(result[0]);
					bgabGascZ011Dto.setFil_nm(result[5]);
					bgabGascZ011Dto.setFil_mgn_qty(Integer.parseInt(result[3]));
					Integer fileRs = shuttleBusDao.insertShuttleBusToFile(bgabGascZ011Dto);
				}
				msg = result[4];

			}else{
				resultUrl = "xsb04_file.gas";
				msg = HncisMessageSource.getMessage(msgCode);
			}
		}catch(Exception e){
			resultUrl = "xsb04_file.gas";
			msg = HncisMessageSource.getMessage(msgCode);
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
				req.getRequestDispatcher(resultUrl).forward(req, res);

				return;
			}catch(Exception e){
				logger.error("messege", e);
			}
		}
	}

	@Override
	public List<BgabGascZ011Dto> getSelectShuttleBusToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return shuttleBusDao.getSelectShuttleBusToFile(bgabGascZ011Dto);
	}

	@Override
	public BgabGascZ011Dto getSelectShuttleBusToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return shuttleBusDao.getSelectShuttleBusToFileInfo(bgabGascZ011Dto);
	}

	@Override
	public int deleteShuttleBusToFile(List<BgabGascZ011Dto> bgabGascZ011IList){
		String fileResult = "";
		for(int i=0; i<bgabGascZ011IList.size(); i++){
			BgabGascZ011Dto fileInfo = bgabGascZ011IList.get(i);
			try {
				fileResult = FileUtil.deleteFile(fileInfo.getCorp_cd(), "shuttleBus", fileInfo.getOgc_fil_nm());
			} catch (IOException e) {
				logger.error("messege", e);
			}
		}
		Integer fileDRs = shuttleBusDao.deleteShuttleBusToFile(bgabGascZ011IList);
		return fileDRs;
	}
	
	public List<CommonCode> getShuttleBusCombo1(CommonCode code){
		return shuttleBusDao.getShuttleBusCombo1(code);
	}
	
	public List<CommonCode> getShuttleBusCombo2(CommonCode code){
		return shuttleBusDao.getShuttleBusCombo2(code);
	}
	
	@Override
	public BgabGascsb01 doSearchShuttleBusBeforeRequst(BgabGascsb01 param){
		return shuttleBusDao.doSearchShuttleBusBeforeRequst(param);
	}
}
