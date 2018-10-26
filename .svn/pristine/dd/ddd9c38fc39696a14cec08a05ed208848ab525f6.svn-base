package com.hncis.trafficTicket.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.common.application.RfcBudgetCheck;
import com.hncis.common.application.RfcPoCreate;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz005Dto;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.RfcBudgetCheckVo;
import com.hncis.common.vo.RfcPoCreateVo;
import com.hncis.system.dao.SystemDao;
import com.hncis.system.vo.BgabGascz014Dto;
import com.hncis.system.vo.BgabGascz015Dto;
import com.hncis.system.vo.BgabGascz016Dto;
import com.hncis.trafficTicket.dao.TrafficTicketServiceDao;
import com.hncis.trafficTicket.manager.TrafficTicketManager;
import com.hncis.trafficTicket.vo.BgabGascTm01;
import com.hncis.trafficTicket.vo.BgabGascTm02;

@Service("trafficTicketManagerImpl")
public class TrafficTicketManagerImpl implements TrafficTicketManager{
	@Autowired
	public TrafficTicketServiceDao trafficTicketDao;
	
	@Autowired
	public CommonJobDao commonJobDao;
	
	@Autowired
	public SystemDao systemDao;
	
	/**
	 * management list
	 */
	public int getSelectByXtm01ListCount(BgabGascTm01 gsReqVo){
		return trafficTicketDao.getSelectByXtm01ListCount(gsReqVo);
	}
	
	public List<BgabGascTm01> getSelectByXtm01List(BgabGascTm01 gsReqVo){
		return trafficTicketDao.getSelectByXtm01List(gsReqVo);
	}
	
	public int doInsertByXtm01List(BgabGascTm01 tm01Svvo){
		return trafficTicketDao.doInsertByXtm01List(tm01Svvo);
	}
	
	public int doUpdateByXtm01List(BgabGascTm01 tm01MdVo){
		return trafficTicketDao.doUpdateByXtm01List(tm01MdVo);
	}
	
	public int doDeleteByXtm01List(BgabGascTm01 tm01DlVo){
		return trafficTicketDao.doDeleteByXtm01List(tm01DlVo);
	}
	
	public CommonMessage doActionByXtm01List(BgabGascTm01 gsParamVo) {
		CommonMessage message = new CommonMessage();
		
		if("4".equals(gsParamVo.getPgs_st_cd())){
			List<BgabGascz015Dto> b_info = null;
			
			// switch 조회
			BgabGascz005Dto switch_param = new BgabGascz005Dto();
			switch_param.setXcod_code("TM");
			BgabGascz005Dto switchInfo = systemDao.getSelectCheckBudgetSwitch(switch_param);
			
			if("Y".equals(switchInfo.getXcod_hname())){
				// Material
				BgabGascz015Dto b_param = new BgabGascz015Dto();
				b_param.setKey_job("XTM");
				b_param.setStartRow(0);
				b_param.setEndRow(10);
				b_info = systemDao.getSelectPurchaseOrderManagement(b_param);
			}
			
			if("Y".equals(switchInfo.getXcod_hname()) && b_info.size() == 0){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				message.setErrorCode("E");
				message.setMessage(HncisMessageSource.getMessage("GLACCOUNT.0001"));
			}else{
				RfcBudgetCheck rfc = new RfcBudgetCheck("D");
				RfcBudgetCheckVo i_BudgetInfo = new RfcBudgetCheckVo();
				if("Y".equals(switchInfo.getXcod_hname())){
					// cost_cd
					BgabGascz002Dto w_info = new BgabGascz002Dto();
					w_info.setXusr_empno(gsParamVo.getEeno());
					BgabGascz002Dto	workPlace = systemDao.getSelectUserWorkPlace(w_info);
					
					i_BudgetInfo.setI_gubn("D");
					i_BudgetInfo.setI_date(CurrentDateTime.getDate());
					i_BudgetInfo.setI_gjahr(CurrentDateTime.getYear());
					i_BudgetInfo.setI_kostl(workPlace.getXusr_cost_cd());
					i_BudgetInfo.setI_hkont(b_info.get(0).getOrder_no());
				}
				RfcBudgetCheckVo o_BudgetInfo = new RfcBudgetCheckVo();
				try {
					double balanceAmt = 0;
					if("Y".equals(switchInfo.getXcod_hname())){
						o_BudgetInfo = rfc.getResult(i_BudgetInfo);
						balanceAmt = Double.parseDouble(StringUtil.isNullToString(o_BudgetInfo.getO_balance().replaceAll(",", ""),"0"));
						
						if(StringUtil.isNullToString(balanceAmt).equals("")){
							balanceAmt = 0;
						}
					}else{
						o_BudgetInfo.setO_actual("Z");
					}
					
					if("E".equals(o_BudgetInfo.getO_actual())){
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						message.setErrorCode("E");
						message.setMessage(o_BudgetInfo.getGs_msg());
					}else{
						trafficTicketDao.doActionByXtm01List(gsParamVo);
						
						if("Y".equals(switchInfo.getXcod_hname())){
							System.out.println("###################################");
							System.out.println(o_BudgetInfo.getO_balance());
							System.out.println(Double.parseDouble(gsParamVo.getTic_amt()) );
							System.out.println("balanceAmt : " + balanceAmt);
							System.out.println("###################################");
							if(Double.parseDouble(gsParamVo.getTic_amt()) > balanceAmt){
								// 예산부족						
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
								message.setErrorCode("E");
								message.setMessage(HncisMessageSource.getMessage("BUDGET.0001"));
							}else{
								Boolean flag = true;
								// Material
								BgabGascz016Dto m_param = new BgabGascz016Dto();
								m_param.setKey_job("XTM");
								m_param.setStartRow(0);
								m_param.setEndRow(10);
								List<BgabGascz016Dto> m_info = systemDao.getSelectMaterialManagement(m_param);
								
								if(m_info.size() == 0){
									TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
									message.setErrorCode("E");
									message.setMessage(HncisMessageSource.getMessage("MATERIAL.0001"));
									flag = false;
								}
								
								// Vendor
								BgabGascz014Dto v_param = new BgabGascz014Dto();
								v_param.setKey_job("XTM");
								v_param.setStartRow(0);
								v_param.setEndRow(10);
								List<BgabGascz014Dto> vendorInfo = systemDao.getSelectVendorManagement(v_param);
								
								if(vendorInfo.size() == 0){
									TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
									message.setErrorCode("E");
									message.setMessage(HncisMessageSource.getMessage("VENDOR.0001"));
									flag = false;
								}
								
								if(flag){
									// P.O 생성
									CurrentDateTime d = new CurrentDateTime();
									// 지역 조회
									BgabGascz002Dto w_info = new BgabGascz002Dto();
									w_info.setXusr_empno(gsParamVo.getEeno());
									BgabGascz002Dto	workPlace = systemDao.getSelectUserWorkPlace(w_info);
									
									RfcPoCreate crfc = new RfcPoCreate();
									RfcPoCreateVo i_PoInfo = new RfcPoCreateVo();
									i_PoInfo.setI_date(CurrentDateTime.getDate());
									i_PoInfo.setI_vendor_code(vendorInfo.get(0).getVendor_code());		// mapping
									i_PoInfo.setI_vendor_name(vendorInfo.get(0).getVendor_name());		// mapping
									i_PoInfo.setI_pur_org_code("H301");
									i_PoInfo.setI_pur_group("B11");
									i_PoInfo.setI_wrkplc_cd(workPlace.getXusr_plac_work());			// Piracicaba, São Paulo
									i_PoInfo.setI_usn(gsParamVo.getEeno());
									i_PoInfo.setI_material_code(m_info.get(0).getMaterial_code());	
									i_PoInfo.setI_material_desc(m_info.get(0).getMaterial_desc());
									i_PoInfo.setI_mat_group(m_info.get(0).getMaterial_group());
									i_PoInfo.setI_qty("1");
									i_PoInfo.setI_price(gsParamVo.getTic_amt());
									i_PoInfo.setI_delivery_date(d.getMonth(d.getDate(), 1));
									i_PoInfo.setI_cost_cd(gsParamVo.getCost_cd());
									i_PoInfo.setI_company_code("H301");
									i_PoInfo.setI_account_category("K");
									i_PoInfo.setI_account_code(b_info.get(0).getOrder_no());
									
									RfcPoCreateVo o_PoInfo = null;
									o_PoInfo = crfc.getResult(i_PoInfo);
			
									if("Z".equals(o_PoInfo.getO_if_result())){
										BgabGascTm01 info = new BgabGascTm01();
										info.setDoc_no(gsParamVo.getDoc_no());
										info.setPo_no(o_PoInfo.getO_po_no());
										info.setUpdr_eeno(gsParamVo.getUpdr_eeno());
										
										trafficTicketDao.updateTrafficTicketPoInfo(info);
									}else{
										TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
										message.setErrorCode("E");
										message.setMessage(o_PoInfo.getO_if_fail_msg());
									}
								}
							}
						}
					}
				} catch (Exception e) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					message.setErrorCode("E");
					message.setMessage(o_BudgetInfo.getGs_msg());
					e.printStackTrace();
				}
			}
		}else{
			trafficTicketDao.doActionByXtm01List(gsParamVo);
		}
		return message;
	}
	
	/**
	 * list
	 */
	public int getSelectByXtm02ListCount(BgabGascTm01 gsReqVo){
		return trafficTicketDao.getSelectByXtm02ListCount(gsReqVo);
	}
	
	public List<BgabGascTm01> getSelectByXtm02List(BgabGascTm01 gsReqVo){
		return trafficTicketDao.getSelectByXtm02List(gsReqVo);
	}
	
	public List<BgabGascTm01> getSelectByXtm02ExcelList(BgabGascTm01 gsReqVo){
		return trafficTicketDao.getSelectByXtm02ExcelList(gsReqVo);
	}
	
	/**
	 * view info
	 */
	
	public BgabGascTm01 doSearchToCarInfo(BgabGascTm01 gsParamVo){
		return trafficTicketDao.doSearchToCarInfo(gsParamVo);
	}
	public BgabGascTm01 getSearchByXtm03ViewInfo(BgabGascTm01 gsParamVo){
		return trafficTicketDao.getSearchByXtm03ViewInfo(gsParamVo);
	}
	
	public int doUpdateByXtm03Accept(BgabGascTm01 gsParamVo) {
		return trafficTicketDao.doUpdateByXtm03Accept(gsParamVo);
	}
	
	/**
	 * HR report list
	 */
	public int getSelectByXtm04ListCount(BgabGascTm01 gsReqVo) {
		return trafficTicketDao.getSelectByXtm04ListCount(gsReqVo);
	}
	
	
	@Override
	public List<BgabGascTm01> getSelectByXtm04List(BgabGascTm01 gsReqVo) {
		return trafficTicketDao.getSelectByXtm04List(gsReqVo);
	}

	@Override
	public int doUpdateByXtm04ListDone(BgabGascTm01 gsParamVo) {
		return trafficTicketDao.doUpdateByXtm04ListDone(gsParamVo);
	}

	@Override
	public int doUpdateByXtm04ListDoneCancel(BgabGascTm01 gsParamVo) {
		return trafficTicketDao.doUpdateByXtm04ListDoneCancel(gsParamVo);
	}
	
	@Override
	public int getSelectByXtm05ListCount(BgabGascTm02 gsReqVo){
		return trafficTicketDao.getSelectByXtm05ListCount(gsReqVo);
	}
	@Override
	public List<BgabGascTm02> getSelectByXtm05List(BgabGascTm02 gsReqVo){
		return trafficTicketDao.getSelectByXtm05List(gsReqVo);
	}
	@Override
	public int saveByXtm05List(List<BgabGascTm02> iList, List<BgabGascTm02> uList){
		int iCnt = 0;
		int uCnt = 0;
		
		iCnt = trafficTicketDao.insertByXtm05List(iList);
		uCnt = trafficTicketDao.updateByXtm05List(uList);
		
		return iCnt + uCnt;
	}
	@Override
	public int deleteByXtm05List(List<BgabGascTm02> gsParamVo){
		return trafficTicketDao.deleteByXtm05List(gsParamVo);
	}
	
	@Override
	public List<BgabGascTm02> getSelectByDescrCommboList(){
		return trafficTicketDao.getSelectByDescrCommboList();
	}
	
	public BgabGascTm02 getSelectToTransitoInfo(BgabGascTm02 dto){
		return trafficTicketDao.getSelectToTransitoInfo(dto);
	}
}
