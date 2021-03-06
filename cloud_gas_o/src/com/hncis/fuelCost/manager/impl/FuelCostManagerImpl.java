package com.hncis.fuelCost.manager.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.common.application.ApprovalGasc;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.fuelCost.dao.FuelCostDao;
import com.hncis.fuelCost.manager.FuelCostManager;
import com.hncis.fuelCost.vo.BgabGascfc01Dto;
import com.hncis.fuelCost.vo.BgabGascfc02Dto;

@Service("fuelCostManagerImpl")
public class FuelCostManagerImpl implements FuelCostManager{
    private transient Log logger = LogFactory.getLog(getClass());

    private static final String pCode = "P-D-006";
    private static final String sCode = "GASBZ01460010";
    private static final String rCode = "GASROLE01460030";
    private static final String adminID = "10000001";
    private static final String saveMsg0 = "SAVE.0000";
    private static final String saveMsg1 = "SAVE.0001";
    
	@Autowired
	public FuelCostDao fuelCostDao;

	@Autowired
	public CommonJobDao commonJobDao;

	@Override
	public CommonMessage saveXfc01Info(BgabGascfc01Dto dto) {
		
		CommonMessage message = new CommonMessage();

		if(dto.getDoc_no().equals("")){
			String docNo = StringUtil.getDocNo();
			dto.setDoc_no(docNo);
		}

		try{

			int cnt = fuelCostDao.updateXfc01Info(dto);

			if(cnt == 0){
				cnt = fuelCostDao.insertXfc01Info(dto);
				if(cnt == 0){
					message.setResult("N");
					message.setMessage(HncisMessageSource.getMessage(saveMsg1));
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				} else {
					message.setResult("Y");
					message.setCode(dto.getDoc_no());
					message.setMessage(HncisMessageSource.getMessage(saveMsg0));
					
					// BPM API호출
					String processCode = pCode; 	//프로세스 코드 (주유비 프로세스) - 프로세스 정의서 참조
					String bizKey = dto.getDoc_no();	//신청서 번호
					String statusCode = sCode;	//액티비티 코드 (주유비 신청서작성) - 프로세스 정의서 참조
					String loginUserId = dto.getEeno();	//로그인 사용자 아이디
					String comment = null;
					String roleCode = rCode;  //주유비 담당자 역할코드
					
					//역할정보
					List<String> approveList = new ArrayList<String>();
					List<String> managerList = new ArrayList<String>();
					managerList.add(adminID);

					BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
				}
			}else{
				message.setResult("Y");
				message.setCode(dto.getDoc_no());
				message.setMessage(HncisMessageSource.getMessage(saveMsg0));
			}

		} catch (Exception e) {
			message.setResult("N");
			message.setMessage(HncisMessageSource.getMessage(saveMsg1));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return message;
	}

	@Override
	public CommonMessage deleteXfc01Info(BgabGascfc01Dto dto) {

		CommonMessage message = new CommonMessage();

		try{

			int cnt = fuelCostDao.deleteXfc01Info(dto);

			if(cnt == 0){
				message.setResult("N");
				message.setMessage(HncisMessageSource.getMessage("DELETE.0001"));
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}else{
				message.setResult("Y");
				
				// BPM API호출
				String processCode = pCode; 	//프로세스 코드 (주유비 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (주유비 신청서작성) - 프로세스 정의서 참조
				String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
				
				BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
					
				message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
			}

		} catch (Exception e) {
			message.setResult("N");
			message.setMessage(HncisMessageSource.getMessage("DELETE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return message;
	}

	@Override
	public BgabGascfc01Dto selectXfc01Info(BgabGascfc01Dto dto){
		return fuelCostDao.selectXfc01Info(dto);
	}
	
	@Override
	public BgabGascfc01Dto selectXfc01InfoByIfId(BgabGascfc01Dto dto){
		return fuelCostDao.selectXfc01InfoByIfId(dto);
	}

	@Override
	public int selectXfc01InfoListCount(BgabGascfc01Dto dto){
		return fuelCostDao.selectXfc01InfoListCount(dto);
	}

	@Override
	public List<BgabGascfc01Dto> selectXfc01InfoList(BgabGascfc01Dto dto){
		List<BgabGascfc01Dto> list = fuelCostDao.selectXfc01InfoList(dto);
		
		for(int i=0;i<list.size();i++){
			list.get(i).setTrvg_dist(list.get(i).getTrvg_dist()+HncisMessageSource.getMessage("won"));
		}
		
		return list;
	}

	@Override
	public BgabGascfc01Dto selectFuelCostCal(BgabGascfc01Dto dto){
		BgabGascfc01Dto rtnDto = new BgabGascfc01Dto();
		BgabGascfc02Dto dto02 = new BgabGascfc02Dto();
		dto02.setCorp_cd(dto.getCorp_cd());
		BgabGascfc02Dto stdDto = fuelCostDao.selectXfc05Info(dto02);

		BigDecimal trvgDist = new BigDecimal(dto.getTrvg_dist());

		String fuelTypeCd = dto.getFuel_type_cd();

		BigDecimal stdDist = null;
		BigDecimal stdCost = null;
		BigDecimal fuelCost = null;
		BigDecimal fuelLiter = null;

		if("A".equals(fuelTypeCd)){
			stdDist =  new BigDecimal(stdDto.getGas_dist());
			stdCost =  new BigDecimal(stdDto.getGas_cost());
		} else if("B".equals(fuelTypeCd)){
			stdDist =  new BigDecimal(stdDto.getDsl_dist());
			stdCost =  new BigDecimal(stdDto.getDsl_cost());
		} else if("C".equals(fuelTypeCd)){
			stdDist =  new BigDecimal(stdDto.getLpg_dist());
			stdCost =  new BigDecimal(stdDto.getLpg_cost());
		}
		
		fuelLiter = trvgDist.divide(stdDist,1,BigDecimal.ROUND_CEILING);
		fuelCost = fuelLiter.multiply(stdCost).setScale(0,BigDecimal.ROUND_CEILING);

		rtnDto.setFuel_cost(fuelCost.toString());
		rtnDto.setFuel_liter(fuelLiter.toString());
		return rtnDto;
	}

	@Override
	public CommonMessage updateXfcRequest(BgabGascfc01Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) {
		try{
			BgabGascz002Dto userParam = new BgabGascz002Dto();
			userParam.setCorp_cd(dto.getCorp_cd());
			userParam.setXusr_empno(dto.getEeno());
			BgabGascz002Dto userInfo = commonJobDao.getXusrInfo(userParam);

			appInfo.setDoc_no(dto.getDoc_no());					// 문서번호
			appInfo.setSystem_code("FC");								// 시스템코드
			appInfo.setTable_name("bgab_gascfc01");						// 업무 테이블이름
			appInfo.setRpts_eeno(userInfo.getXusr_empno());		// 상신자 사번
			appInfo.setRpts_dept(userInfo.getXusr_dept_code());	// 상신자 부서코드
			appInfo.setStep_code(userInfo.getXusr_step_code());	// 상신자 직위코드
			appInfo.setRpts_eeno_nm(userInfo.getXusr_name());		// 상신자 성명
			appInfo.setStep_nm(userInfo.getXusr_step_name());		// 직위 이름
			appInfo.setTitle_nm(HncisMessageSource.getMessage("fuel_cost"));								// 업무 이름
			appInfo.setAppType("FC");									// 전결권자 업무
			appInfo.setMax_level(5);									// 해외 결재 LEVEL
			appInfo.setCorp_cd(userInfo.getCorp_cd());

			CommonApproval commonApproval = ApprovalGasc.setApprovalRequestUseYn(appInfo);

			dto.setIf_id(commonApproval.getIf_id());
			dto.setRpts_eeno(userInfo.getXusr_empno());

			if(commonApproval.getResult().equals("Z")){
				message.setMessage(HncisMessageSource.getMessage("REQUEST.0000"));
				
				// BPM API호출
				String processCode = pCode; 	//프로세스 코드 (주유비 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = sCode;	//액티비티 코드 (주유비신청서작성) - 프로세스 정의서 참조
				String loginUserId = dto.getEeno();	//로그인 사용자 아이디
				String comment = null;
				String roleCode = rCode;   //주유비 담당자 역할코드
				
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
			
			
		} catch (Exception e) {
			message.setResult("N");
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}

	@Override
	public CommonMessage updateXfcRequestCancel(BgabGascfc01Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) {
		try{
			if("".equals(StringUtil.isNullToString(dto.getIf_id()))){
				int cnt = fuelCostDao.updateXfcPgsStCd(dto);
				if(cnt == 0){
					message.setResult("N");
					message.setMessage(HncisMessageSource.getMessage("REQUEST.0003"));
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}else{
					message.setResult("Y");
					
					// BPM API호출
					String processCode = pCode; 	//프로세스 코드 (주유비 프로세스) - 프로세스 정의서 참조
					String bizKey = dto.getDoc_no();	//신청서 번호
					String statusCode = sCode;	//액티비티 코드 (주유비 신청서작성) - 프로세스 정의서 참조
					String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
					String comment = null;
					String roleCode = rCode;  //주유비 담당자 역할코드
						
					//역할정보
					List<String> approveList = new ArrayList<String>();
					List<String> managerList = new ArrayList<String>();
					managerList.add(adminID);
					
					BpmApiUtil.sendCollectTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );

					message.setMessage(HncisMessageSource.getMessage("REQUEST.0002"));
				}
			}else{
				appInfo.setIf_id(dto.getIf_id());
				appInfo.setTable_name("bgab_gascfc01");
				appInfo.setCorp_cd(dto.getCorp_cd());
				
				CommonApproval commonApproval = ApprovalGasc.setApprovalCancelProcess(appInfo);

				if(commonApproval.getResult().equals("Z")){
					
					// BPM API호출
					String processCode = pCode; 	//프로세스 코드 (주유비 프로세스) - 프로세스 정의서 참조
					String bizKey = dto.getDoc_no();	//신청서 번호
					String statusCode = sCode;	//액티비티 코드 (주유비 신청서작성) - 프로세스 정의서 참조
					String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
					String comment = null;
					String roleCode = rCode;  //주유비 담당자 역할코드
						
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
		} catch (Exception e) {
			message.setResult("N");
			message.setMessage(HncisMessageSource.getMessage("REQUEST.0003"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}

	@Override
	public CommonMessage updateXfcConfirm(BgabGascfc01Dto dto) {
		CommonMessage message = new CommonMessage();
		try{

			int cnt = fuelCostDao.updateXfcPgsStCd(dto);
			if(cnt == 0){
				message.setResult("N");
				message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}else{
				message.setResult("Y");
				message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
				
				// BPM API호출
				String processCode = pCode; 	//프로세스 코드 (주유비 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = "GASBZ01460030";	//액티비티 코드 (주유비 담당자확인) - 프로세스 정의서 참조
				String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
				String comment = null;
				
				
				BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);

			}
		} catch (Exception e) {
			message.setResult("N");
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}

	@Override
	public CommonMessage updateXfcReject(BgabGascfc01Dto dto) {
		CommonMessage message = new CommonMessage();
		try{

			int cnt = fuelCostDao.updateXfcPgsStCd(dto);
			if(cnt == 0){
				message.setResult("N");
				message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}else{
				message.setResult("Y");
				message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
			}
		} catch (Exception e) {
			message.setResult("N");
			message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}

	@Override
	public CommonMessage saveXfc05Info(BgabGascfc02Dto dto) {
		CommonMessage message = new CommonMessage();

		try{

			int cnt = fuelCostDao.updateXfc05Info(dto);

			if(cnt == 0){
				fuelCostDao.updateXfc05InfoPast(dto);
				cnt = fuelCostDao.insertXfc05Info(dto);
				if(cnt == 0){
					message.setResult("N");
					message.setMessage(HncisMessageSource.getMessage(saveMsg1));
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				} else {
					message.setResult("Y");
					message.setMessage(HncisMessageSource.getMessage(saveMsg0));
				}
			}else{
				message.setResult("Y");
				message.setMessage(HncisMessageSource.getMessage(saveMsg0));
			}

		} catch (Exception e) {
			message.setResult("N");
			message.setMessage(HncisMessageSource.getMessage(saveMsg1));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return message;
	}

	@Override
	public int selectXfcRequestInfoListCount(BgabGascfc01Dto dto){
		return fuelCostDao.selectXfcRequestInfoListCount(dto);
	}

	@Override
	public List<BgabGascfc01Dto> selectXfcRequestInfoList(BgabGascfc01Dto dto){
		List<BgabGascfc01Dto> list = fuelCostDao.selectXfcRequestInfoList(dto);
		
		for(int i=0;i<list.size();i++){
			list.get(i).setTrvg_dist(list.get(i).getTrvg_dist()+HncisMessageSource.getMessage("won"));
		}
		
		return list;
	}

	@Override
	public int selectXfc04InfoListCount(BgabGascfc01Dto dto){
		return fuelCostDao.selectXfc04InfoListCount(dto);
	}

	@Override
	public List<BgabGascfc01Dto> selectXfc04InfoList(BgabGascfc01Dto dto){
		List<BgabGascfc01Dto> list = fuelCostDao.selectXfc04InfoList(dto);
		for(int i=0;i<list.size();i++){
			if(list.get(i).getGubun().equals("금액")){
				list.get(i).setGubun(HncisMessageSource.getMessage("rt_amt"));
			}else if(list.get(i).getGubun().equals("리터")){
				list.get(i).setGubun(HncisMessageSource.getMessage("liter"));
			}
		}
		return list;
	}

	@Override
	public BgabGascfc02Dto selectXfc05Info(BgabGascfc02Dto dto){
		return fuelCostDao.selectXfc05Info(dto);
	}

	@Override
	public int selectXfc05InfoListCount(BgabGascfc02Dto dto){
		return fuelCostDao.selectXfc05InfoListCount(dto);
	}

	@Override
	public List<BgabGascfc02Dto> selectXfc05InfoList(BgabGascfc02Dto dto){
		return fuelCostDao.selectXfc05InfoList(dto);
	}
	
	public CommonMessage updateXfcToReject(BgabGascfc01Dto dto){
		CommonMessage message = new CommonMessage();
		try{
			int cnt = fuelCostDao.updateXfcToReject(dto);
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
			message.setCode1("Y");
			
			// BPM API호출
			String processCode = pCode; 	//프로세스 코드 (주유비 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01460030";	//액티비티 코드 (주유비 담당자확인) - 프로세스 정의서 참조
			String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
			
			BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
							
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
			message.setCode1("N");
		}
		return message;
	}
}