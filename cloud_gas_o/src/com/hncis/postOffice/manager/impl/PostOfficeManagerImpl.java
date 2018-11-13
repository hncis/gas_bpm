package com.hncis.postOffice.manager.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.FileUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonMessage;
import com.hncis.postOffice.dao.PostOfficeDao;
import com.hncis.postOffice.manager.PostOfficeManager;
import com.hncis.postOffice.vo.BgabGascpo01Dto;
import com.hncis.roomsMeals.dao.RoomsMealsDao;
import com.hncis.roomsMeals.vo.BgabGascrm01Dto;

@Service("postOfficeManagerImpl")
public class PostOfficeManagerImpl implements PostOfficeManager{
    private transient Log logger = LogFactory.getLog(getClass());
    
    private static final String strMessege = "messege";

	@Autowired
	public PostOfficeDao postOfficeDao;
	
//	@Autowired
//	public CommonJobDao commonJobDao;
	
	@Override
	public CommonMessage savePoToRequest(HttpServletRequest req, BgabGascpo01Dto dto) {
		CommonMessage message = new CommonMessage();
		
		//if(dto.getDoc_no().equals("")){
		//	String docNo = StringUtil.getDocNo();
		//	dto.setDoc_no(docNo);
		//}
		
		try{
			int cnt = postOfficeDao.insertPoToRequest(dto);
			
			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
			message.setCode(dto.getDoc_no());
		}catch (Exception e) {
			logger.error(strMessege, e);
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}
	
	@Override
	public BgabGascpo01Dto selectInfoPoToRequest(BgabGascpo01Dto dto) {
		return postOfficeDao.selectInfoPoToRequest(dto);
	}
	
	@Override
	public CommonMessage updatePoToRequestForRequest(HttpServletRequest req, BgabGascpo01Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt  = postOfficeDao.updatePoToRequestForRequest(dto);
			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));
		}catch (Exception e) {
			logger.error(strMessege, e);
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}
	
	@Override
	public CommonMessage updatePoToRequestForRequestCancel(HttpServletRequest req, BgabGascpo01Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt  = postOfficeDao.updatePoToRequestForRequestCancel(dto);
			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0002"));
		}catch (Exception e) {
			logger.error(strMessege, e);
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0003"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}
	
	@Override
	public CommonMessage updatePoToRequestForConfirm(HttpServletRequest req, BgabGascpo01Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt  = postOfficeDao.updatePoToRequestForConfirm(dto);
			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
		}catch (Exception e) {
			logger.error(strMessege, e);
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}
	
	@Override
	public CommonMessage updatePoToRequestForConfirmCancel(HttpServletRequest req, BgabGascpo01Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt  = postOfficeDao.updatePoToRequestForConfirmCancel(dto);
			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0000"));
		}catch (Exception e) {
			logger.error(strMessege, e);
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}
	
	@Override
	public CommonMessage deletePoToRequest(HttpServletRequest req, BgabGascpo01Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt  = postOfficeDao.deletePoToRequest(dto);
			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		}catch (Exception e) {
			logger.error(strMessege, e);
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("DELETE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}

	@Override
	public int selectCountPoToList(BgabGascpo01Dto dto) {
		int cnt = 0;
		try{
			cnt = postOfficeDao.selectCountPoToList(dto);
		}catch (Exception e) {
			logger.error(strMessege, e);
		}
		return cnt;
	}
	@Override
	public List<BgabGascpo01Dto> selectListPoToList(BgabGascpo01Dto dto) {
		List<BgabGascpo01Dto> list = null;
		try{
			list = postOfficeDao.selectListPoToList(dto);
		}catch (Exception e) {
			logger.error(strMessege, e);
		}
		return list;
	}
	
	@Override
	public int selectCountPoToConfirm(BgabGascpo01Dto dto) {
		int cnt = 0;
		try{
			cnt = postOfficeDao.selectCountPoToConfirm(dto);
		}catch (Exception e) {
			logger.error(strMessege, e);
		}
		return cnt;
	}
	@Override
	public List<BgabGascpo01Dto> selectListPoToConfirm(BgabGascpo01Dto dto) {
		List<BgabGascpo01Dto> list = null;
		try{
			list = postOfficeDao.selectListPoToConfirm(dto);
		}catch (Exception e) {
			logger.error(strMessege, e);
		}
		return list;
	}
	
	public void savePoToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto){
		
		String msg        = "";
		String resultUrl  = "xpo01_file.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];
		
		try{
			
			paramVal[0] = "file_name";
			paramVal[1] = "old_file_name";
			paramVal[2] = "postOffice";
			
			result = FileUtil.uploadFile(req, res, paramVal);
			
			if(result != null){
				if(result[0] != null){
					bgabGascZ011Dto.setOgc_fil_nm(result[0]);
					bgabGascZ011Dto.setFil_nm(result[5]);
					bgabGascZ011Dto.setFil_mgn_qty(Integer.parseInt(result[3]));
					Integer fileRs = (Integer)postOfficeDao.insertPoToFile(bgabGascZ011Dto);
				}
				msg = result[4];
				
			}else{
				resultUrl = "xpo01_file.gas";
				msg = HncisMessageSource.getMessage("FILE.0001");
			}
		}catch(Exception e){
			resultUrl = "xpo01_file.gas";
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
			req.getRequestDispatcher(resultUrl).forward(req, res);
		
			return;
		}catch(Exception e){
			logger.error(strMessege, e);
		}
		
	}
	
	public List<BgabGascZ011Dto> getSelectPoToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return postOfficeDao.getSelectPoToFile(bgabGascZ011Dto);
	}
	
	public BgabGascZ011Dto getSelectPoToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return postOfficeDao.getSelectPoToFileInfo(bgabGascZ011Dto);
	}
	
	public int deletePoToFile(List<BgabGascZ011Dto> bgabGascZ011IList){
		String fileResult = "";
		for(int i=0; i<bgabGascZ011IList.size(); i++){
			BgabGascZ011Dto fileInfo = bgabGascZ011IList.get(i);
			try {
				fileResult = FileUtil.deleteFile(fileInfo.getCorp_cd(), "postOffice", fileInfo.getOgc_fil_nm());
			} catch (IOException e) {
				logger.error(strMessege, e);
			}
		}
		Integer fileDRs = (Integer)postOfficeDao.deletePoToFile(bgabGascZ011IList);
		return fileDRs;
	}
	
	public int rejectPoToRequest (BgabGascpo01Dto dto){
		return postOfficeDao.rejectPoToRequest(dto);
	}

}
