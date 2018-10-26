package com.hncis.product.manager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.books.vo.BgabGascbk01Dto;
import com.hncis.books.vo.BgabGascbk02Dto;
import com.hncis.books.vo.BgabGascbk03Dto;
import com.hncis.books.vo.BgabGascbk04Dto;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.FileUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonMessage;
import com.hncis.product.dao.ProductDao;
import com.hncis.product.manager.ProductManager;
import com.hncis.product.vo.BgabGascpd01Dto;
import com.hncis.product.vo.BgabGascpd02Dto;
import com.hncis.product.vo.BgabGascpd03Dto;
import com.hncis.product.vo.BgabGascpd04Dto;

@Service("productManagerImpl")
public class ProductManagerImpl implements ProductManager{

	@Autowired
	public ProductDao productDao;
	
	@Autowired
	public CommonJobDao commonJobDao;
	
	@Override
	public void savePdToLrgList(List<BgabGascpd03Dto> iList, List<BgabGascpd03Dto> uList) {
		
		int iCnt = productDao.insertPdToLrgList(iList);
		int uCnt = productDao.updatePdToLrgList(uList);
		
	}

	@Override
	public List<BgabGascpd03Dto> selectPdListToLrgInfo(BgabGascpd03Dto vo) {
		return productDao.selectPdListToLrgInfo(vo);
	}

	@Override
	public void savePdToMrgList(List<BgabGascpd04Dto> iList, List<BgabGascpd04Dto> uList) {
		
		int iCnt = productDao.insertPdToMrgList(iList);
		int uCnt = productDao.updatePdToMrgList(uList);
	}

	@Override
	public List<BgabGascpd04Dto> selectPdListToMrgInfo(BgabGascpd04Dto vo) {
		return productDao.selectPdListToMrgInfo(vo);
	}

	@Override
	public void deletePdToLrgList(List<BgabGascpd03Dto> dList) {
		
		int cnt1 = productDao.deletePdToLrgList(dList);
		int cnt2 = productDao.deletePdToMrgDtlList(dList);
	}

	@Override
	public void deletePdToMrgList(List<BgabGascpd04Dto> dList) {
		int cnt2 = productDao.deletePdToMrgList(dList);
	}
	
	@Override
	public List<BgabGascpd03Dto> selectPdToLrgCombo(BgabGascpd03Dto dto) {
		return productDao.selectPdToLrgCombo(dto);
	}

	@Override
	public List<BgabGascpd04Dto> selectPdToMrgCombo(BgabGascpd04Dto dto) {
		return productDao.selectPdToMrgCombo(dto);
	}

	@Override
	public int selectCountPdToProductList(BgabGascpd01Dto dto) {
		return productDao.selectCountPdToProductList(dto);
	}

	@Override
	public List<BgabGascpd01Dto> selectPdToProductList(BgabGascpd01Dto dto) {
		
		List<BgabGascpd01Dto> list = productDao.selectPdToProductList(dto);
		
		for(BgabGascpd01Dto vo : list){
			vo.setPd_rent(vo.getPd_rent_yn().equals("Y") ? "<font color='blue'>"+HncisMessageSource.getMessage("psb")+"</font>" : "<font color='red'>"+HncisMessageSource.getMessage("ipsb")+"</font>");
			
			if(!StringUtil.isNullToString(vo.getOrg_file_nm()).equals("")){
				vo.setImg_pop(HncisMessageSource.getMessage("preview"));
			}
		}
		
		return list;
	}

	@Override
	public BgabGascpd01Dto selectInfoPdToProductInfo(BgabGascpd01Dto dto) {
		return productDao.selectInfoPdToProductInfo(dto);
	}

	@Override
	public CommonMessage insertPdToProductInfo(List<BgabGascpd01Dto> dtoList) {
		
		CommonMessage message = new CommonMessage();
		
		try{
			int slrCnt = productDao.selectCountPdToSlrList(dtoList.get(0));
			
			int cnt = 0;
			
			if(slrCnt>0){
				if(productDao.deletePdToProductInfo(dtoList.get(0))>0){
					cnt = productDao.insertPdToProductInfo(dtoList);
				}
			}else{
				cnt = productDao.insertPdToProductInfo(dtoList);
			}
			
			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
			message.setCode(dtoList.get(0).getPd_seq());
		}catch (Exception e) {
			e.printStackTrace();
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}

	@Override
	public int selectCountPdToProductMgmtList(BgabGascpd01Dto dto) {
		return productDao.selectCountPdToProductMgmtList(dto);
	}

	@Override
	public List<BgabGascpd01Dto> selectPdToProductMgmtList(BgabGascpd01Dto dto) {
		
		List<BgabGascpd01Dto> list = productDao.selectPdToProductMgmtList(dto);
		
		for(BgabGascpd01Dto vo : list){
			if(!StringUtil.isNullToString(vo.getOrg_file_nm()).equals("")){
				vo.setImg_pop(HncisMessageSource.getMessage("preview"));
			}
		}
		return list;
	}

	@Override
	public List<BgabGascpd01Dto> doSearchPdListToSlrInfo(BgabGascpd01Dto dto) {
		return productDao.doSearchPdListToSlrInfo(dto);
	}

	@Override
	public List<BgabGascpd01Dto> selectSlrListCombo(BgabGascpd01Dto vo) {
		return productDao.selectSlrListCombo(vo);
	}

	@Override
	public CommonMessage updatePdToProductRequest(BgabGascpd02Dto dto) {
		CommonMessage message = new CommonMessage();
		
		String bpmSaveMsg="";
		String bpmReqMsg="";
		
		try{
			
			int chkCnt = productDao.selectPdToProductExtrQty(dto);
			
			if(chkCnt < 1){
				message.setCode1("N");
				message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0047"));
			}else{
				
				String docNo = StringUtil.getDocNo();
				dto.setDoc_no(docNo);
				int cnt = productDao.insertPdToProductRequest(dto);
				productDao.updatePdToRentYn(dto);
				message.setCode1("Y");
				message.setMessage(HncisMessageSource.getMessage("APPLY.0000"));
				message.setCode(dto.getPd_seq());
				
				// BPM API호출
				String processCode = "P-C-005"; 	//프로세스 코드 (도서 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = "GASBZ01350010";	//액티비티 코드 (도서신청서작성) - 프로세스 정의서 참조
				String loginUserId = dto.getEeno();	//로그인 사용자 아이디
				String comment = null;
				String roleCode = "GASROLE01350030";   //도서 담당자 역할코드
				//역할정보
				List<String> approveList = new ArrayList<String>();
				List<String> managerList = new ArrayList<String>();
				managerList.add("10000001");

				bpmSaveMsg = BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
				System.out.println("BPM 저장 메시지 : " + bpmSaveMsg);
				
				bpmReqMsg = BpmApiUtil.sendCompleteTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
				System.out.println("BPM 신청 메시지 : " + bpmSaveMsg);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("APPLY.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}

	@Override
	public int selectCountPdToProductRentList(BgabGascpd02Dto dto) {
		return productDao.selectCountPdToProductRentList(dto);
	}

	@Override
	public List<BgabGascpd02Dto> selectPdToProductRentList(BgabGascpd02Dto dto) {
		List<BgabGascpd02Dto> list = productDao.selectPdToProductRentList(dto);
		
		return list;
	}

	@Override
	public int deleteRentListToRequestCancel(List<BgabGascpd02Dto> dtoList) {
		int rentYn = 0;
		int cancelChk = 0;
		
		try{
			cancelChk = productDao.deleteRentListToRequestCancel(dtoList);
			if(cancelChk > 0){
				for(int i=0;i<dtoList.size();i++){
					rentYn = productDao.updatePdToRentYn(dtoList.get(i));
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			rentYn = 0;
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return rentYn;
		
	}

	@Override
	public CommonMessage updatePdToProductRent(List<BgabGascpd02Dto> dtoList) {
		CommonMessage message = new CommonMessage();
		
		try{
			
			int update = productDao.updatePdToProductRent(dtoList);
			
			if(update > 0 ){
				for(BgabGascpd02Dto dto : dtoList){
					productDao.updatePdToRentYn(dto);
					
					if(dto.getPgs_st_cd().equals("B")){
						// BPM API호출
						String processCode = "P-C-005"; 	//프로세스 코드 (도서 프로세스) - 프로세스 정의서 참조
						String bizKey = dto.getDoc_no();	//신청서 번호
						String statusCode = "GASBZ01350030";	//액티비티 코드 (도서 담당자확인) - 프로세스 정의서 참조
						String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
						String comment = null;
				
						BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
					}else if(dto.getPgs_st_cd().equals("B")){
						// BPM API호출
						String processCode = "P-C-005"; 	//프로세스 코드 (도서 프로세스) - 프로세스 정의서 참조
						String bizKey = dto.getDoc_no();	//신청서 번호
						String statusCode = "GASBZ01350030";	//액티비티 코드 (도서 담당자확인) - 프로세스 정의서 참조
						String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
				
						BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
						
					}
				}
			}

			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("RENT.0000"));
		}catch (Exception e){
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("RENT.0002"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			
		}
		return message;
	}

	@Override
	public CommonMessage updatePdToProductReturnList(List<BgabGascpd02Dto> dtoList) {
		CommonMessage message = new CommonMessage();
		
		try{
			
			int cnt = productDao.updatePdToProductReturnList(dtoList);
			
			if(cnt > 0){
				for(BgabGascpd02Dto dto : dtoList){
					productDao.updatePdToRentYn(dto);
				}
			}
			
			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("RETURN.0002"));
			
		}catch (Exception e) {
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("RETURN.0003"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}
	
	@Override
	public CommonMessage deletePdToProductInfo(BgabGascpd01Dto dto) {
		
		CommonMessage message = new CommonMessage();
		
		try{
			
			int cnt = productDao.deletePdToProductInfo(dto);
			
			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
			message.setCode(dto.getPd_seq());
		}catch (Exception e) {
			e.printStackTrace();
			message.setCode1("N");
			message.setMessage(HncisMessageSource.getMessage("DELETE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}

	@Override
	public List<BgabGascZ011Dto> getSelectPdToFile(BgabGascZ011Dto dto) {
		return productDao.getSelectPdToFile(dto);
	}

	@Override
	public void savePdToFile(HttpServletRequest req, HttpServletResponse res,
			BgabGascZ011Dto bgabGascZ011Dto) {
		
		String msg        = "";
		String resultUrl  = "xpd06_file.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];
		
		try{
			
			paramVal[0] = "file_name";
			paramVal[1] = "old_file_name";
			paramVal[2] = "product";
			
			result = FileUtil.uploadFileView(req, res, paramVal);
			
			if(result != null){
				if(result[0] != null){
					bgabGascZ011Dto.setOgc_fil_nm(result[0]);
					bgabGascZ011Dto.setFil_nm(result[5]);
					bgabGascZ011Dto.setFil_mgn_qty(Integer.parseInt(result[3]));
					Integer fileRs = (Integer)productDao.insertPdToFile(bgabGascZ011Dto);
				}
				msg = result[4];
				
			}else{
				resultUrl = "xpd06_file.gas";
				msg = HncisMessageSource.getMessage("FILE.0001");
			}
		}catch(Exception e){
			resultUrl = "xpd06_file.gas";
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
				req.setAttribute("saveYn",  "Y");
				req.getRequestDispatcher(resultUrl).forward(req, res);
			
				return;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public int deletePdToFile(List<BgabGascZ011Dto> dto) {
		String fileResult = "";
		for(int i=0; i<dto.size(); i++){
			BgabGascZ011Dto fileInfo = dto.get(i);
			try {
				fileResult = FileUtil.deleteFile(fileInfo.getCorp_cd(), "product", fileInfo.getOgc_fil_nm());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Integer fileDRs = (Integer)productDao.deletePdToFile(dto);
		return fileDRs;
		
	}

	@Override
	public BgabGascZ011Dto getSelectPdToFileInfo(BgabGascZ011Dto dto) {
		return productDao.getSelectPdToFileInfo(dto);
	}

}
