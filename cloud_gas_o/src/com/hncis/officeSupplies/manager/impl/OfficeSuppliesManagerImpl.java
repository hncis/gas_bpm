package com.hncis.officeSupplies.manager.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.hncis.officeSupplies.dao.OfficeSuppliesDao;
import com.hncis.officeSupplies.manager.OfficeSuppliesManager;
import com.hncis.officeSupplies.vo.BgabGascos01;
import com.hncis.officeSupplies.vo.BgabGascos02;
import com.hncis.officeSupplies.vo.BgabGascos03;
import com.hncis.officeSupplies.vo.BgabGascos04;
import com.hncis.officeSupplies.vo.BgabGascosDto;

@Service("officeSuppliesManagerImpl")
public class OfficeSuppliesManagerImpl implements OfficeSuppliesManager{
    private transient Log logger = LogFactory.getLog(getClass());

    private static final String pCode = "P-C-003";
    private static final String strMessege = "messege";
    
	@Autowired
	public OfficeSuppliesDao officeSuppliesDao;

	@Autowired
	public CommonJobDao commonJobDao;

	@Override
	public List<CommonCode> getOfficeCombo(CommonCode code) {
		return officeSuppliesDao.getOfficeCombo(code);
	}

	/**
	 * request
	 */
	@Override
	public BgabGascos01 doSearchByRequestInfo(BgabGascos01 gsParamVo){
		return officeSuppliesDao.doSearchByRequestInfo(gsParamVo);
	}

	@Override
	public List<BgabGascos03> doSearchByRequestList(BgabGascos03 gsParamVo){
		return officeSuppliesDao.doSearchByRequestList(gsParamVo);
	}

	@Override
	public int doInsertByRequest(BgabGascos01 gsSaveVo, List<BgabGascos03> gsSaveList, List<BgabGascos03> gsModifyList){
		
		int rs = 0;
		if("I".equals(gsSaveVo.getBasic_mode())){
			rs = officeSuppliesDao.doInsertByRequest(gsSaveVo);
			if(rs>0){
				// BPM API호출
				String processCode = pCode; 	//프로세스 코드 (사무용품 프로세스) - 프로세스 정의서 참조
				String bizKey = gsSaveVo.getDoc_no();	//신청서 번호
				String statusCode = "GASBZ01330010";	//액티비티 코드 (사무용품신청서작성) - 프로세스 정의서 참조
				String loginUserId = gsSaveVo.getEeno();	//로그인 사용자 아이디
				String comment = null;
				String roleCode = "GASROLE01330030";   //사무용품 담당자 역할코드
				//역할정보
				List<String> approveList = new ArrayList<String>();
				List<String> managerList = new ArrayList<String>();
				managerList.add("10000001");

				BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
			
			}
		}
		rs = officeSuppliesDao.doInsertByList(gsSaveList);
		officeSuppliesDao.doUpdateByList(gsModifyList);
		
		return rs;
	}

	@Override
	public int doDeleteByRequest(BgabGascos01 gsDelVo){
		int rs = 0;
		rs = officeSuppliesDao.doDeleteByRequest(gsDelVo);
		if(rs>0){
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (사무용품 프로세스) - 프로세스 정의서 참조
			String bizKey = gsDelVo.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01330010";	//액티비티 코드 (사무용품신청서작성) - 프로세스 정의서 참조
			String loginUserId = gsDelVo.getUpdr_eeno();	//로그인 사용자 아이디
	
			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
									
		}
		
		return rs;
	}

	@Override
	public int doDeleteByRequestList(BgabGascos03 gsDelVo){
		int rs = 0;
		rs = officeSuppliesDao.doDeleteByRequestList(gsDelVo);

		//리스트 업무에서 개별 삭제시 doc_no의 갯수가 0일경우 마스터도 삭제한다.
		if("xgs07".equals(gsDelVo.getMessage())){
			int isCnt = officeSuppliesDao.doSelectByRequestListCnt(gsDelVo);
			if(isCnt <= 0){
				BgabGascos01 vo = new BgabGascos01();
				vo.setDoc_no(gsDelVo.getDoc_no());
				officeSuppliesDao.doDeleteByRequest(vo);
			}
		}
		return rs;
	}

	@Override
	public int doUpdateByRequest(BgabGascos03 gsReqVo){
		int rs = 0;
		rs = officeSuppliesDao.doUpdateByRequest(gsReqVo);
		
		return rs;
	}
	@Override
	public int doUpdateByRequestCancel(BgabGascos03 gsReqVo){
		int rs = 0;
		rs = officeSuppliesDao.doUpdateByRequestCancel(gsReqVo);
		
		if(rs>0){
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (사무용품 프로세스) - 프로세스 정의서 참조
			String bizKey = gsReqVo.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01330010";	//액티비티 코드 (사무용품신청서작성) - 프로세스 정의서 참조
			String loginUserId = gsReqVo.getUpdr_eeno();		//로그인 사용자 아이디
			String comment = null;
			String roleCode = "GASROLE01330030";   //사무용품 담당자 역할코드
			
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add("10000001");
			
			BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );

		}
		return rs;
	}
	@Override
	public int doUpdateByConfirm(BgabGascos03 gsReqVo){
		int rs = 0;
		rs = officeSuppliesDao.doUpdateByConfirm(gsReqVo);
		int isAllConfirm = officeSuppliesDao.doSearchByXosIsAllConfirm(gsReqVo);
		
		if(rs>0 && isAllConfirm ==0){
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (사무용품 프로세스) - 프로세스 정의서 참조
			String bizKey = gsReqVo.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01330030";	//액티비티 코드 (사무용품 담당자확인) - 프로세스 정의서 참조
			String loginUserId = gsReqVo.getUpdr_eeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = "GASROLE01330030";   //사무용품 담당자 역할코드
						
			BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
		}
		return rs;
	}
	@Override
	public int doUpdateByConfirmCancel(BgabGascos03 gsReqVo){
		int rs = 0;
		rs = officeSuppliesDao.doUpdateByConfirmCancel(gsReqVo);
		return rs;
	}

	/**
	 * List
	 */
	@Override
	public int getSelectByXos02ListCount(BgabGascos01 gsReqVo){
		return officeSuppliesDao.getSelectByXos02ListCount(gsReqVo);
	}

	@Override
	public List<BgabGascos03> getSelectByXos02List(BgabGascos01 gsReqVo){
		return officeSuppliesDao.getSelectByXos02List(gsReqVo);
	}

	@Override
	public int doInsertByXos07Basic(BgabGascos01 gsSvvo){
		return officeSuppliesDao.doInsertByXos07Basic(gsSvvo);
	}

	@Override
	public int doInsertByXos07List(BgabGascos03 gsSvvo){
		return officeSuppliesDao.doInsertByXos07List(gsSvvo);
	}

	@Override
	public int doUpdateByXos07List(BgabGascos03 gsMdvo){
		return officeSuppliesDao.doUpdateByXos07List(gsMdvo);
	}

	/**
	 * Confirm
	 */
	@Override
	public int getSelectByXos03ListCount(BgabGascos01 gsReqVo){
		return officeSuppliesDao.getSelectByXos03ListCount(gsReqVo);
	}
	@Override
	public List<BgabGascos03> getSelectByXos03List(BgabGascos01 gsReqVo){
		return officeSuppliesDao.getSelectByXos03List(gsReqVo);
	}
	@Override
	public List<BgabGascos03> getSelectByXos03ExcelList(BgabGascos01 gsReqVo){
		return officeSuppliesDao.getSelectByXos03ExcelList(gsReqVo);
	}

	/**
	 * total list
	 */
	@Override
	public int getSelectByXos04ListCount(BgabGascosDto gsReqVo){
		return officeSuppliesDao.getSelectByXos04ListCount(gsReqVo);
	}

	@Override
	public List<BgabGascosDto> getSelectByXos04List(BgabGascosDto gsReqVo){
		return officeSuppliesDao.getSelectByXos04List(gsReqVo);
	}

	/**
	 * production management
	 */
	@Override
	public List<BgabGascos02> getSearchByOffice(BgabGascos02 gsReqVo){
		return officeSuppliesDao.getSearchByOffice(gsReqVo);
	}

	@Override
	public List<BgabGascos02> getSearchByOffice2(BgabGascos02 gsReqVo){
		return officeSuppliesDao.getSearchByOffice2(gsReqVo);
	}

	@Override
	public List<BgabGascos02> getSearchByOffice3(BgabGascos02 gsReqVo){
		return officeSuppliesDao.getSearchByOffice3(gsReqVo);
	}

	@Override
	public List<BgabGascos02> getSearchByOffice4(BgabGascos02 gsReqVo){
		return officeSuppliesDao.getSearchByOffice4(gsReqVo);
	}

	@Override
	public CommonMessage doInsertByOffice(List<BgabGascos02> gsSaveVo, List<BgabGascos02> gsModifyVo){
		CommonMessage message = new CommonMessage();

		for(BgabGascos02 vo : gsSaveVo){
			if(officeSuppliesDao.getSelectByOfficeCheck(vo) > 0){
				TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();

				message.setResult("N");
				message.setMessage(HncisMessageSource.getMessage("SAVE.0003") + "( Code : " + vo.getCatg_3() + " )");
				return message;
			}
		}

		for(BgabGascos02 vo : gsModifyVo){
			String oldCatg1 = vo.getOld_catg_1();
			String catg1 = vo.getCatg_1();
			if(!StringUtils.isEmpty(oldCatg1)){
				if(oldCatg1.equals(catg1)){
					continue;
				}
			}
			if(officeSuppliesDao.getSelectByOfficeCheck(vo) > 0){
				TransactionAspectSupport.currentTransactionStatus().isRollbackOnly();

				message.setResult("N");
				message.setMessage(HncisMessageSource.getMessage("SAVE.0003") + "( Code : " + vo.getCatg_3() + " )");
				return message;
			}
		}

		officeSuppliesDao.doInsertByOffice(gsSaveVo);
		officeSuppliesDao.doUpdateByOffice(gsModifyVo);

		message.setResult("Y");
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		return message;
	}

	@Override
	public int doDeleteByOffice(List<BgabGascos02> gsDelVo){
		return officeSuppliesDao.doDeleteByOffice(gsDelVo);
	}

	/**
	 * manager management
	 */
	@Override
	public int getSelectByXos06ListCount(BgabGascos04 gsReqVo){
		return officeSuppliesDao.getSelectByXos06ListCount(gsReqVo);
	}

	@Override
	public List<BgabGascos04> getSelectByXos06List(BgabGascos04 gsReqVo){
		return officeSuppliesDao.getSelectByXos06List(gsReqVo);
	}

	@Override
	public int doInsertByXos06(List<BgabGascos04> gsSaveVo, List<BgabGascos04> gsModifyVo){
		int rs = 0;
		rs = officeSuppliesDao.doInsertByXos06(gsSaveVo);
		officeSuppliesDao.doUpdateByXos06(gsModifyVo);
		return rs;
	}

	@Override
	public int doDeleteByXos06(List<BgabGascos04> gsDelVo){
		int rs = 0;
		rs = officeSuppliesDao.doDeleteByXos06(gsDelVo);
		return rs;
	}

	@Override
	public String getSelectByXosIsManager(BgabGascos04 gs04Vo){
		return officeSuppliesDao.getSelectByXosIsManager(gs04Vo);
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
	public int updateByXos03Reject(List<BgabGascos03> gsModifyVo){
		
		for(BgabGascos03 vo : gsModifyVo){
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (사무용품 프로세스) - 프로세스 정의서 참조
			String bizKey = vo.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01330030";	//액티비티 코드 (사무용품 담당자확인) - 프로세스 정의서 참조
			String loginUserId = vo.getUpdr_eeno();	//로그인 사용자 아이디		
			
			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);

		}	
		
		return officeSuppliesDao.updateByXos03Reject(gsModifyVo);
	}


	@Override
	public void saveOfficeSuppliesToImgFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto){

		String msg        = "";
		String resultUrl  = "xos05_img_file.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{

			if(StringUtil.isEmpty(bgabGascZ011Dto.getDoc_no())){
				bgabGascZ011Dto.setDoc_no(StringUtil.getDocNo());
			}

			paramVal[0] = "file_name";
			paramVal[1] = "old_file_name";
			paramVal[2] = "officeSupplies";

			result = FileUtil.uploadImgFile(req, res, paramVal);

			if(result != null){
				if(result[0] != null){
					bgabGascZ011Dto.setOgc_fil_nm(result[0]);
					bgabGascZ011Dto.setFil_nm(result[5]);
					bgabGascZ011Dto.setFil_mgn_qty(Integer.parseInt(result[3]));
					Integer fileRs = officeSuppliesDao.insertOfficeSuppliesToFile(bgabGascZ011Dto);
				}
				msg = result[4];

			}else{
				resultUrl = "xos05_img_file.gas";
				msg = HncisMessageSource.getMessage("FILE.0001");
			}
		}catch(Exception e){
			resultUrl = "xos05_img_file.gas";
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
	public List<BgabGascZ011Dto> getSelectOfficeSuppliesToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return officeSuppliesDao.getSelectOfficeSuppliesToFile(bgabGascZ011Dto);
	}

	@Override
	public BgabGascZ011Dto getSelectOfficeSuppliesToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return officeSuppliesDao.getSelectOfficeSuppliesToFileInfo(bgabGascZ011Dto);
	}

	@Override
	public int deleteOfficeSuppliesToFile(List<BgabGascZ011Dto> bgabGascZ011IList){
		String fileResult = "";
		for(int i=0; i<bgabGascZ011IList.size(); i++){
			BgabGascZ011Dto fileInfo = bgabGascZ011IList.get(i);
			try {
				fileResult = FileUtil.deleteImgFile(fileInfo.getCorp_cd(), "officeSupplies", fileInfo.getOgc_fil_nm());
			} catch (IOException e) {
				logger.error(strMessege, e);
			}
		}
		Integer fileDRs = officeSuppliesDao.deleteOfficeSuppliesToFile(bgabGascZ011IList);
		return fileDRs;
	}

	@Override
	public BgabGascos02 selectByOffice3FileName(BgabGascos02 bgabGascos02){
		return officeSuppliesDao.selectByOffice3FileName(bgabGascos02);
	}
}
