package com.hncis.generalService.manager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.common.application.RfcPoCreate;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.FileUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.RfcPoCreateVo;
import com.hncis.generalService.dao.GeneralServiceDao;
import com.hncis.generalService.manager.GeneralServiceManager;
import com.hncis.generalService.vo.BgabGascgs01;
import com.hncis.generalService.vo.BgabGascgs02;
import com.hncis.generalService.vo.BgabGascgs03;
import com.hncis.generalService.vo.BgabGascgs04;
import com.hncis.generalService.vo.BgabGascgsDto;

@Service("generalServiceManagerImpl")
public class GeneralServiceManagerImpl implements GeneralServiceManager{
    private transient Log logger = LogFactory.getLog(getClass());

    private static final String pCode = "P-C-002";
    private static final String sCode = "GASBZ01320010";
    private static final String strMessege = "messege";
    
	@Autowired
	public GeneralServiceDao generalServiceDao;

	@Autowired
	public CommonJobDao commonJobDao;

	@Override
	public List<CommonCode> getGeneralServiceCombo(CommonCode code) {
		return generalServiceDao.getGeneralServiceCombo(code);
	}

	/**
	 * request
	 */
	@Override
	public BgabGascgs01 doSearchByRequestInfo(BgabGascgs01 gsParamVo){
		return generalServiceDao.doSearchByRequestInfo(gsParamVo);
	}

	@Override
	public List<BgabGascgs03> doSearchByRequestList(BgabGascgs03 gsParamVo){
		return generalServiceDao.doSearchByRequestList(gsParamVo);
	}

	@Override
	public int doInsertByRequest(BgabGascgs01 gsSaveVo, List<BgabGascgs03> gsSaveList, List<BgabGascgs03> gsModifyList){
		int rs = 0;
		if("I".equals(gsSaveVo.getBasic_mode())){
			rs = generalServiceDao.doInsertByRequest(gsSaveVo);
		}
		rs = generalServiceDao.doInsertByList(gsSaveList);
		generalServiceDao.doUpdateByList(gsModifyList);
				
		// BPM API호출
		String processCode = pCode; 	//프로세스 코드 (전산용품 프로세스) - 프로세스 정의서 참조
		String bizKey = gsSaveVo.getDoc_no();	//신청서 번호
		String statusCode = sCode;	//액티비티 코드 (전산용품신청서작성) - 프로세스 정의서 참조
		String loginUserId = gsSaveVo.getEeno();	//로그인 사용자 아이디
		String comment = null;
		String roleCode = "GASROLE01320030";  //전산용품 담당자 역할코드
		
		//역할정보
		List<String> approveList = new ArrayList<String>();
		List<String> managerList = new ArrayList<String>();
		managerList.add("10000001");
	
		BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
				
		return rs;
	}

	@Override
	public int doDeleteByRequest(BgabGascgs01 gsDelVo){
		int rs = 0;
		rs = generalServiceDao.doDeleteByRequest(gsDelVo);
		if(rs>0){
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (전산용품 프로세스) - 프로세스 정의서 참조
			String bizKey = gsDelVo.getDoc_no();	//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (전산용품신청서작성) - 프로세스 정의서 참조
			String loginUserId = gsDelVo.getUpdr_eeno();	//로그인 사용자 아이디
	
			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
			
		}
		return rs;
	}

	@Override
	public int doDeleteByRequestList(BgabGascgs03 gsDelVo){
		int rs = 0;
		rs = generalServiceDao.doDeleteByRequestList(gsDelVo);

		//리스트 업무에서 개별 삭제시 doc_no의 갯수가 0일경우 마스터도 삭제한다.
		if("xgs07".equals(gsDelVo.getMessage())){
			int isCnt = generalServiceDao.doSelectByRequestListCnt(gsDelVo);
			if(isCnt <= 0){
				BgabGascgs01 vo = new BgabGascgs01();
				vo.setDoc_no(gsDelVo.getDoc_no());
				generalServiceDao.doDeleteByRequest(vo);
			}
		}
		return rs;
	}

	@Override
	public int doUpdateByRequest(BgabGascgs03 gsReqVo){
		int rs = 0;
		rs = generalServiceDao.doUpdateByRequest(gsReqVo);
		
		// BPM API호출
		String processCode = pCode; 	//프로세스 코드 (전산용품 프로세스) - 프로세스 정의서 참조
		String bizKey = gsReqVo.getDoc_no();	//신청서 번호
		String statusCode = sCode;	//액티비티 코드 (전산용품신청서작성) - 프로세스 정의서 참조
		String loginUserId = gsReqVo.getEeno();	//로그인 사용자 아이디
		String comment = null;
		String roleCode = "GASROLE01320030";  //전산용품 담당자 역할코드
		
		//역할정보
		List<String> approveList = new ArrayList<String>();
		List<String> managerList = new ArrayList<String>();
		managerList.add("10000001");
	
		BpmApiUtil.sendCompleteTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
				
		return rs;
	}
	@Override
	public int doUpdateByRequestCancel(BgabGascgs03 gsReqVo){
		int rs = 0;
		rs = generalServiceDao.doUpdateByRequestCancel(gsReqVo);
		if(rs>0){
			
			// BPM API호출
			String processCode = pCode; 		//프로세스 코드 (전산용품 프로세스) - 프로세스 정의서 참조
			String bizKey = gsReqVo.getDoc_no();		//신청서 번호
			String statusCode = sCode;	//액티비티 코드 (전산용품신청서작성) - 프로세스 정의서 참조
			String loginUserId = gsReqVo.getUpdr_eeno();		//로그인 사용자 아이디
			String comment = null;
			String roleCode = "GASROLE01320030";  	//전산용품 담당자 역할코드
			
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add("10000001");
			
			BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );

		}
		return rs;
	}
	@Override
	public int doUpdateByConfirm(BgabGascgs03 gsReqVo){
		int rs = 0;
		rs = generalServiceDao.doUpdateByConfirm(gsReqVo);
		
		// BPM API호출
		String processCode = pCode; 	//프로세스 코드 (전산용품 프로세스) - 프로세스 정의서 참조
		String bizKey = gsReqVo.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01320030";	//액티비티 코드 (담당자 확인) - 프로세스 정의서 참조
		String loginUserId = gsReqVo.getUpdr_eeno();	//로그인 사용자 아이디
		String comment = null;
		
	
		BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
		
		return rs;
	}
	@Override
	public int doUpdateByConfirmCancel(BgabGascgs03 gsReqVo){
		int rs = 0;
		rs = generalServiceDao.doUpdateByConfirmCancel(gsReqVo);
		return rs;
	}

	/**
	 * List
	 */
	@Override
	public int getSelectByXgs02ListCount(BgabGascgs01 gsReqVo){
		return generalServiceDao.getSelectByXgs02ListCount(gsReqVo);
	}

	@Override
	public List<BgabGascgs03> getSelectByXgs02List(BgabGascgs01 gsReqVo){
		return generalServiceDao.getSelectByXgs02List(gsReqVo);
	}

	@Override
	public int doInsertByXgs07Basic(BgabGascgs01 gsSvvo){
		return generalServiceDao.doInsertByXgs07Basic(gsSvvo);
	}

	@Override
	public int doInsertByXgs07List(BgabGascgs03 gsSvvo){
		return generalServiceDao.doInsertByXgs07List(gsSvvo);
	}

	@Override
	public int doUpdateByXgs07List(BgabGascgs03 gsMdvo){
		return generalServiceDao.doUpdateByXgs07List(gsMdvo);
	}

	/**
	 * Confirm
	 */
	@Override
	public int getSelectByXgs03ListCount(BgabGascgs01 gsReqVo){
		return generalServiceDao.getSelectByXgs03ListCount(gsReqVo);
	}
	@Override
	public List<BgabGascgs03> getSelectByXgs03List(BgabGascgs01 gsReqVo){
		return generalServiceDao.getSelectByXgs03List(gsReqVo);
	}
	@Override
	public List<BgabGascgs03> getSelectByXgs03ExcelList(BgabGascgs01 gsReqVo){
		return generalServiceDao.getSelectByXgs03ExcelList(gsReqVo);
	}

	/**
	 * total list
	 */
	@Override
	public int getSelectByXgs04ListCount(BgabGascgsDto gsReqVo){
		return generalServiceDao.getSelectByXgs04ListCount(gsReqVo);
	}

	@Override
	public List<BgabGascgsDto> getSelectByXgs04List(BgabGascgsDto gsReqVo){
		return generalServiceDao.getSelectByXgs04List(gsReqVo);
	}

	/**
	 * production management
	 */
	@Override
	public List<BgabGascgs02> getSearchByGeneralService(BgabGascgs02 gsReqVo){
		return generalServiceDao.getSearchByGeneralService(gsReqVo);
	}

	@Override
	public List<BgabGascgs02> getSearchByGeneralService2(BgabGascgs02 gsReqVo){
		return generalServiceDao.getSearchByGeneralService2(gsReqVo);
	}

	@Override
	public List<BgabGascgs02> getSearchByGeneralService3(BgabGascgs02 gsReqVo){
		return generalServiceDao.getSearchByGeneralService3(gsReqVo);
	}

	@Override
	public List<BgabGascgs02> getSearchByGeneralService4(BgabGascgs02 gsReqVo){
		return generalServiceDao.getSearchByGeneralService4(gsReqVo);
	}

	@Override
	public CommonMessage doInsertByGeneralService(List<BgabGascgs02> gsSaveVo, List<BgabGascgs02> gsModifyVo){
		CommonMessage message = new CommonMessage();

		for(BgabGascgs02 vo : gsSaveVo){
			if(generalServiceDao.getSelectByGeneralServiceCheck(vo) > 0){
				TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();

				message.setResult("N");
				message.setMessage(HncisMessageSource.getMessage("SAVE.0003") + "( Code : " + vo.getCatg_3() + " )");
				return message;
			}
		}

		for(BgabGascgs02 vo : gsModifyVo){
			String oldCatg1 = vo.getOld_catg_1();
			String catg1 = vo.getCatg_1();
			if(!StringUtils.isEmpty(oldCatg1)){
				if(oldCatg1.equals(catg1)){
					continue;
				}
			}
			if(generalServiceDao.getSelectByGeneralServiceCheck(vo) > 0){
				TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();

				message.setResult("N");
				message.setMessage(HncisMessageSource.getMessage("SAVE.0003") + "( Code : " + vo.getCatg_3() + " )");
				return message;
			}
		}

		generalServiceDao.doInsertByGeneralService(gsSaveVo);
		generalServiceDao.doUpdateByGeneralService(gsModifyVo);

		message.setResult("Y");
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		return message;
	}

	@Override
	public int doDeleteByGeneralService(List<BgabGascgs02> gsDelVo){
		return generalServiceDao.doDeleteByGeneralService(gsDelVo);
	}

	/**
	 * manager management
	 */
	@Override
	public int getSelectByXgs06ListCount(BgabGascgs04 gsReqVo){
		return generalServiceDao.getSelectByXgs06ListCount(gsReqVo);
	}

	@Override
	public List<BgabGascgs04> getSelectByXgs06List(BgabGascgs04 gsReqVo){
		return generalServiceDao.getSelectByXgs06List(gsReqVo);
	}

	@Override
	public int doInsertByXgs06(List<BgabGascgs04> gsSaveVo, List<BgabGascgs04> gsModifyVo){
		int rs = 0;
		rs = generalServiceDao.doInsertByXgs06(gsSaveVo);
		generalServiceDao.doUpdateByXgs06(gsModifyVo);
		return rs;
	}

	@Override
	public int doDeleteByXgs06(List<BgabGascgs04> gsDelVo){
		int rs = 0;
		rs = generalServiceDao.doDeleteByXgs06(gsDelVo);
		return rs;
	}

	@Override
	public String getSelectByXgsIsManager(BgabGascgs04 gs04Vo){
		return generalServiceDao.getSelectByXgsIsManager(gs04Vo);
	}

	@Override
	public RfcPoCreateVo doSearchPoCreate(RfcPoCreateVo poParamVo){

		RfcPoCreate rfc = new RfcPoCreate();

		RfcPoCreateVo o_PoInfo = null;
		try {
			o_PoInfo = rfc.doPoCreate(poParamVo);
		} catch (Exception e) {
			logger.error(strMessege, e);
		}
		return o_PoInfo;
	}

	@Override
	public RfcPoCreateVo doSearchPoDelete(RfcPoCreateVo poParamVo){

		RfcPoCreate rfc = new RfcPoCreate();

		RfcPoCreateVo o_PoInfo = null;
		try {
			o_PoInfo = rfc.doPoDelete(poParamVo);
		} catch (Exception e) {
			logger.error(strMessege, e);
		}
		return o_PoInfo;
	}

	@Override
	public int updateByXgs03Reject(List<BgabGascgs03> gsModifyVo){
		
		BgabGascgs03 getGs03vo = new BgabGascgs03();
		getGs03vo = gsModifyVo.get(0);
		
		// BPM API호출
		String processCode = pCode; 	//프로세스 코드 (전산용품 프로세스) - 프로세스 정의서 참조
		String bizKey = getGs03vo.getDoc_no();	//신청서 번호
		String statusCode = "GASBZ01320030";	//액티비티 코드 (전산용품신청서작성) - 프로세스 정의서 참조
		String loginUserId = getGs03vo.getUpdr_eeno();	//로그인 사용자 아이디
		
		BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
					
		return generalServiceDao.updateByXgs03Reject(gsModifyVo);
	}


	@Override
	public void saveGeneralServiceToImgFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto){

		String msg        = "";
		String resultUrl  = "xgs05_img_file.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{

			if(StringUtil.isEmpty(bgabGascZ011Dto.getDoc_no())){
				bgabGascZ011Dto.setDoc_no(StringUtil.getDocNo());
			}

			paramVal[0] = "file_name";
			paramVal[1] = "old_file_name";
			paramVal[2] = "generalService";

			result = FileUtil.uploadImgFile(req, res, paramVal);

			if(result != null){
				if(result[0] != null){
					bgabGascZ011Dto.setOgc_fil_nm(result[0]);
					bgabGascZ011Dto.setFil_nm(result[5]);
					bgabGascZ011Dto.setFil_mgn_qty(Integer.parseInt(result[3]));
					Integer fileRs = generalServiceDao.insertGeneralServiceToFile(bgabGascZ011Dto);
				}
				msg = result[4];

			}else{
				resultUrl = "xgs05_img_file.gas";
				msg = HncisMessageSource.getMessage("FILE.0001");
			}
		}catch(Exception e){
			resultUrl = "xgs05_img_file.gas";
			msg = HncisMessageSource.getMessage("FILE.0001");
			logger.error(strMessege, e);
		}
		
		try{
			String dispatcherYN = "Y";
			req.setAttribute("file_doc_no",  bgabGascZ011Dto.getDoc_no());
			req.setAttribute("file_eeno",  bgabGascZ011Dto.getEeno());
			req.setAttribute("file_pgs_st_cd",  bgabGascZ011Dto.getPgs_st_cd());
			req.setAttribute("file_seq",  bgabGascZ011Dto.getSeq());
			req.setAttribute("dispatcherYN", dispatcherYN);
			req.setAttribute("csrfToken", bgabGascZ011Dto.getCsrfToken());
			req.setAttribute("message",  msg);
			req.getRequestDispatcher(resultUrl).forward(req, res);

			return;
		}catch(Exception e){
			logger.error(strMessege, e);
		}
	}

	@Override
	public List<BgabGascZ011Dto> getSelectGeneralServiceToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return generalServiceDao.getSelectGeneralServiceToFile(bgabGascZ011Dto);
	}

	@Override
	public BgabGascZ011Dto getSelectGeneralServiceToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return generalServiceDao.getSelectGeneralServiceToFileInfo(bgabGascZ011Dto);
	}

	@Override
	public int deleteGeneralServiceToFile(List<BgabGascZ011Dto> bgabGascZ011IList){
		String fileResult = "";
		for(int i=0; i<bgabGascZ011IList.size(); i++){
			BgabGascZ011Dto fileInfo = bgabGascZ011IList.get(i);
			try {
				fileResult = FileUtil.deleteImgFile(fileInfo.getCorp_cd(),"generalService", fileInfo.getOgc_fil_nm());
			} catch (IOException e) {
				logger.error(strMessege, e);
			}
		}
		Integer fileDRs = generalServiceDao.deleteGeneralServiceToFile(bgabGascZ011IList);
		return fileDRs;
	}

	@Override
	public BgabGascgs02 selectByGeneralService3FileName(BgabGascgs02 bgabGascos02){
		return generalServiceDao.selectByGeneralService3FileName(bgabGascos02);
	}
}
