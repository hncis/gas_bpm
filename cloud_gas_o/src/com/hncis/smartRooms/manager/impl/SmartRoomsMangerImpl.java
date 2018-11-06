package com.hncis.smartRooms.manager.impl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.common.application.SessionInfo;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.CommonCode;
import com.hncis.smartRooms.dao.SmartRoomsDao;
import com.hncis.smartRooms.manager.SmartRoomsManager;
import com.hncis.smartRooms.vo.BgabGascsm01;
import com.hncis.smartRooms.vo.BgabGascsm02;
import com.hncis.smartRooms.vo.BgabGascsm03;
import com.hncis.smartRooms.vo.BgabGascsm04;
import com.hncis.smartRooms.vo.BgabGascsm05;
import com.hncis.smartRooms.vo.BgabGascsm06;
import com.hncis.smartRooms.vo.BgabGascsm07;
import com.hncis.smartRooms.vo.BgabGascsmDto;

@Service("smartRoomsManagerImpl")
public class SmartRoomsMangerImpl implements SmartRoomsManager{
    private transient Log logger = LogFactory.getLog(getClass());
	@Autowired
	public SmartRoomsDao smartRoomsDao;
	
	/**
	 * 회의실 공통
	 */
	public List<BgabGascsm05> searchComboByXsm(BgabGascsm05 sm05Vo){
		return smartRoomsDao.searchComboByXsm(sm05Vo);
	}
	public BgabGascsmDto searchXsmByDocNo(HashMap<String, String> paramMap){
		return smartRoomsDao.searchXsmByDocNo(paramMap);
	}
	//권한구분에 따른 사용자 조회
	public String searchLoginTimeUser(String sessEmpno, String sessCorp) {
		String str = "";
		BgabGascsmDto xsmDto = new BgabGascsmDto();
		xsmDto.setCorp_cd(sessCorp);
		xsmDto.setEtcUser(sessEmpno);
		xsmDto.setAuth_cd("B"); //장기예약담당
		List<BgabGascsmDto> etcUserList = smartRoomsDao.searchEtcUserList(xsmDto);
		if(etcUserList.size() > 0){
			str = "Y";
		}else{
			str = "N";
		}
		
		return str;
	}
	public HashMap<String, Object> searchConferencePolicy(HashMap<String, String> paramMap) {
		return smartRoomsDao.searchConferencePolicy(paramMap);
	}
	public int searchTodayReservCnt(BgabGascsm01 teamCntVo) {
		return smartRoomsDao.searchTodayReservCnt(teamCntVo);
	}
	
	public BgabGascsm01 searchConferenceReservInfo(BgabGascsm01 sm04Vo){
		return smartRoomsDao.searchConferenceReservInfo(sm04Vo);
	}
	public BgabGascsm04 searchConferenceInfo(BgabGascsm04 sm04Vo){
		return smartRoomsDao.searchConferenceInfo(sm04Vo);
	}
	public int insertConferenceRoom1(BgabGascsm01 sm01Vo) {
		return smartRoomsDao.insertConferenceRoom1(sm01Vo);
	}
	public int insertConferenceRoom2(BgabGascsm01 sm01Vo) {
		return smartRoomsDao.insertConferenceRoom2(sm01Vo);
	}
	public BgabGascsm01 updateConferenceRoom(HttpServletRequest req, BgabGascsm01 sm01Vo){
		BgabGascsm01 rtnVo = new BgabGascsm01();
		String message = "";
		boolean smsRtn = true;
		boolean dupRtn = false;
		try{
			String keyNo = StringUtil.isNullToString(sm01Vo.getCode1());
			if("".equals(StringUtil.isNullToString(sm01Vo.getDoc_no()))){
				if(!"".equals(keyNo)){
					HashMap<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("key_no", keyNo.split("-")[0]);
					paramMap.put("cnf_ymd", keyNo.split("-")[1]);
					paramMap.put("cnf_time", keyNo.split("-")[2]);
	
					BgabGascsmDto xsmDto = smartRoomsDao.searchXsmByDocNo(paramMap);
					sm01Vo.setDoc_no(xsmDto.getDoc_no());
				}else{
					dupRtn = true;
				}
			}
			
			//특정일 insert
			String cnfSpeDay = sm01Vo.getCnf_spe_day();
			if(!"".equals(cnfSpeDay)){
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("from_ymd", (String)sm01Vo.getCnf_from_ymd());
				paramMap.put("to_ymd", (String)sm01Vo.getCnf_to_ymd());
				paramMap.put("spe_day", (String[])cnfSpeDay.split(","));
				List<BgabGascsm01> specList = smartRoomsDao.searchXsmBySpecialDay(paramMap);

				//처음 저장된 날짜와 시간은 삭제한다.
				smartRoomsDao.deleteConferenceRoom2(sm01Vo);
				smartRoomsDao.deleteConferenceRoom1(sm01Vo);
				
				for(int i=0; i<specList.size(); i++){
					//특정일 날짜만큼 Master/sub table에 INSERT 한다.
					BgabGascsm01 reSm01Vo = new BgabGascsm01();
					String docNo = StringUtil.getMakeDocNo();
					reSm01Vo.setDoc_no(docNo);
			        reSm01Vo.setRegn_cd(sm01Vo.getRegn_cd());
			        reSm01Vo.setBd_cd(sm01Vo.getBd_cd());
			        reSm01Vo.setCorm_fl_cd(sm01Vo.getCorm_fl_cd());
			        reSm01Vo.setCorm_cd(sm01Vo.getCorm_cd());
			    	reSm01Vo.setCnf_title(sm01Vo.getCnf_title());
			    	reSm01Vo.setCnf_eeno(sm01Vo.getCnf_eeno());
			    	reSm01Vo.setDept_cd(sm01Vo.getDept_cd());
					reSm01Vo.setCnf_alr_email(sm01Vo.getCnf_alr_email());
					reSm01Vo.setCnf_alr_sms(sm01Vo.getCnf_alr_sms());
					reSm01Vo.setCnf_spe_day(specList.get(i).getWeek_day_cnt());
			    	reSm01Vo.setCnf_attde_eeno(sm01Vo.getCnf_attde_eeno());
			    	reSm01Vo.setCnf_attde_name(sm01Vo.getCnf_attde_name());
			    	reSm01Vo.setCnf_out_attde(sm01Vo.getCnf_out_attde());
			    	reSm01Vo.setCnf_agenda(sm01Vo.getCnf_agenda());
			    	reSm01Vo.setCnf_attde_cnt(sm01Vo.getCnf_attde_cnt());
			    	reSm01Vo.setCnf_out_cnt(sm01Vo.getCnf_out_cnt());
					reSm01Vo.setCnf_from_ymd(specList.get(i).getPtt_ymd());
					reSm01Vo.setCnf_from_time(sm01Vo.getCnf_from_time());
					reSm01Vo.setCnf_to_ymd(specList.get(i).getPtt_ymd());
					reSm01Vo.setCnf_to_time(sm01Vo.getCnf_to_time());
					reSm01Vo.setIpe_eeno(SessionInfo.getSess_empno(req));
					reSm01Vo.setUpdr_eeno(SessionInfo.getSess_empno(req));
					
				    //회의실 중복 체크
				    String isDuplicate = smartRoomsDao.searchCormDupCheck(reSm01Vo);
				    
				    //Y:중복, N:중복아님
				    if("N".equals(isDuplicate)){
				    	//smartRoomsDao.mergeConferenceRoom(reSm01Vo);
				    	smartRoomsDao.insertConferenceRoom1(reSm01Vo);
				    	smartRoomsDao.insertConferenceRoom2(reSm01Vo);
				    }else{
				    	dupRtn = true;
				    	break;
				    }
				}
	        }else{
			    //회의실 중복 체크
			    String isDuplicate = smartRoomsDao.searchCormDupCheck(sm01Vo);
			    //Y:중복, N:중복아님
			    if("N".equals(isDuplicate)){
		    	    //나머지 예약입력필드 채워서 업데이트.(제목, 참석자 등등....)
			    	smartRoomsDao.updateConferenceRoom1(sm01Vo);
			    	smartRoomsDao.updateConferenceRoom2(sm01Vo);
				
					//이메일발송
		    	    /*
					if(!"".equals(StringUtil.isNullToString(paramVo.getCnf_alr_email(), ""))){
						Xsm03Vo cfrInfo = new Xsm03Vo();
						cfrInfo.setDoc_no(StringUtil.isNullToString(paramVo.getDoc_no(), ""));
						cfrInfo = selectConferenceReport(cfrInfo);
						if(cfrInfo != null){
							cfrInfo.setMode(paramVo.getMode());
							returnReportEmailForXsm(cfrInfo, req, "ETC");
						}
					}
					*/
			    }else{
			    	dupRtn = true;
			    }
	        }
			
			//중복검사
			if(dupRtn){
				//rtnVo.setCode1("D");
				//변경해서 다시 사용할 수 있도록 선점 삭제는 안함.
				rtnVo.setCode1("N");
				message = "예약된 회의실이 존재합니다.";
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}else{
				rtnVo.setCode1("Y");
				message = "정상적으로 예약완료 되었습니다.";
			}
		}catch(DataIntegrityViolationException dve){
			rtnVo.setCode1("N");
			message = "입력 에러";
			logger.error("messege", dve);
		}catch(Exception e){
			rtnVo.setCode1("N");
			message = "SYSTEM 오류 입니다. IT 담당자에게 문의 하세요!";
			logger.error("messege", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}finally{
			rtnVo.setMessage(message);
		}
		return rtnVo;
	}
	public int deleteConferenceRoom2(BgabGascsm01 sm01Vo) {
		return smartRoomsDao.deleteConferenceRoom2(sm01Vo);
	}
	public int deleteConferenceRoom1(BgabGascsm01 sm01Vo) {
		return smartRoomsDao.deleteConferenceRoom1(sm01Vo);
	}
	
	/**
	 * 빠른예약
	 */
	public String searchFastReservCormTotalCount(BgabGascsm01 paramVo) {
		return smartRoomsDao.searchFastReservCormTotalCount(paramVo);
	}
	public List<BgabGascsm01> searchFastReservCormList(BgabGascsm01 paramVo) {
		return smartRoomsDao.searchFastReservCormList(paramVo);
	}
	
	/**
	 * 상세검색 및 예약, 주간, 월간
	 */
	public List<BgabGascsm02> selectConferenceRoomList1(BgabGascsm02 paramVo){
        return smartRoomsDao.selectConferenceRoomList1(paramVo);
	}
	public String selectConferenceRoomTotalCount(BgabGascsm02 paramVo){
		return smartRoomsDao.selectConferenceRoomTotalCount(paramVo);
	}
	public List<BgabGascsm02> selectConferenceRoomList2(BgabGascsm02 paramVo){
		return smartRoomsDao.selectConferenceRoomList2(paramVo);
	}
	public List<BgabGascsmDto> searchReservationList(BgabGascsm01 paramVo) {
		return smartRoomsDao.searchReservationList(paramVo);
	}
	
	/**
	 * 나의예약현황
	 */
	public String searchMyReservationTotalCount(BgabGascsm01 paramVo){
		return smartRoomsDao.searchMyReservationTotalCount(paramVo);
	}
	public List<BgabGascsm01> searchMyReservationList(BgabGascsm01 paramVo){
		return smartRoomsDao.searchMyReservationList(paramVo);
	}
	public List<BgabGascsm01> searchMyReservationExcel(BgabGascsm01 paramVo){
		return smartRoomsDao.searchMyReservationExcel(paramVo);
	}
	
	/**
	 * 예약현황
	 */
	public int searchCountByXsm04(BgabGascsm01 smParamVo) {
		return smartRoomsDao.searchCountByXsm04(smParamVo);
	}
	public List<BgabGascsm01> searchByXsm04(BgabGascsm01 smParamVo) {
		return smartRoomsDao.searchByXsm04(smParamVo);
	}
	public List<BgabGascsm01> searchExcelByXsm04(BgabGascsm01 smParamVo){
		return smartRoomsDao.searchExcelByXsm04(smParamVo);
	}
	public int updateUseApproveByXsm04(BgabGascsm01 smParamVo){
		return smartRoomsDao.updateUseApproveByXsm04(smParamVo);
	}
	public int updateNotUseApproveByXsm04(BgabGascsm01 smParamVo){
		return smartRoomsDao.updateNotUseApproveByXsm04(smParamVo);
	}
	public List<BgabGascsmDto> searchXsmConfirmDamdang(BgabGascsm01 smParamVo) {
		return smartRoomsDao.searchXsmConfirmDamdang(smParamVo);
	}
	
	/**
	 * 건물 관리
	 */
	public List<CommonCode> searchBuildingComboByXsm05(BgabGascsm03 smParamVo){
		return smartRoomsDao.searchBuildingComboByXsm05(smParamVo);
	}
	public int searchBuildingMgmtCountByXsm05(BgabGascsm03 smParamVo){
		return smartRoomsDao.searchBuildingMgmtCountByXsm05(smParamVo);
	}
	public List<BgabGascsm03> searchBuildingMgmtByXsm05(BgabGascsm03 smParamVo){
		return smartRoomsDao.searchBuildingMgmtByXsm05(smParamVo);
	}
	public int saveBuildingMgmtByXsm05(List<BgabGascsm03> smSaveList, List<BgabGascsm03> smModifyList){
		int rs = 0;
		rs += smartRoomsDao.insertBuildingMgmtByXsm05(smSaveList);
		rs += smartRoomsDao.updateBuildingMgmtByXsm05(smModifyList);
		return rs; 
	}
	public int deleteBuildingMgmtByXsm05(List<BgabGascsm03> smDelList){
		return smartRoomsDao.deleteBuildingMgmtByXsm05(smDelList); 
	}
	
	/**
	 * 회의실 관리
	 */
	public List<CommonCode> searchConferenceFlComboByXsm06(BgabGascsm04 smParamVo){
		return smartRoomsDao.searchConferenceFlComboByXsm06(smParamVo);
	}
	public List<CommonCode> searchConferenceComboByXsm06(BgabGascsm04 smParamVo){
		return smartRoomsDao.searchConferenceComboByXsm06(smParamVo);
	}
	public int searchConferenceRoomMgmtCountByXsm06(BgabGascsm04 smParamVo){
		return smartRoomsDao.searchConferenceRoomMgmtCountByXsm06(smParamVo);
	}
	public List<BgabGascsm04> searchConferenceRoomMgmtByXsm06(BgabGascsm04 smParamVo){
		return smartRoomsDao.searchConferenceRoomMgmtByXsm06(smParamVo);
	}
	public int saveConferenceRoomMgmtByXsm06(List<BgabGascsm04> smSaveList, List<BgabGascsm04> smModifyList) {
		int rs = 0;
		rs += smartRoomsDao.insertConferenceRoomMgmtByXsm06(smSaveList);
		rs += smartRoomsDao.updateConferenceRoomMgmtByXsm06(smModifyList);
		return rs;
	}
	public int deleteConferenceRoomMgmtByXsm06(List<BgabGascsm04> smModifyList){
		return smartRoomsDao.deleteConferenceRoomMgmtByXsm06(smModifyList);
	}
	public int saveConferenceRoomMgmtPolicyByXsm06(List<BgabGascsm04> smModifyList) {
		int rs = 0;
		rs += smartRoomsDao.updateConferenceRoomMgmtPolicyByXsm06(smModifyList);
		return rs;
	}
	
	/**
	 * 회의실 코드 관리
	 */
	public int searchConferenceRoomCodeMgmtCountByXsm07(BgabGascsm05 smParamVo){
		return smartRoomsDao.searchConferenceRoomCodeMgmtCountByXsm07(smParamVo);
	}
	public List<BgabGascsm05> searchConferenceRoomCodeMgmtByXsm07(BgabGascsm05 smParamVo){
		return smartRoomsDao.searchConferenceRoomCodeMgmtByXsm07(smParamVo);
	}
	public int saveConferenceRoomCodeMgmtByXsm07(List<BgabGascsm05> smSaveList, List<BgabGascsm05> smModifyList) {
		int rs = 0;
		rs += smartRoomsDao.insertConferenceRoomCodeMgmtByXsm07(smSaveList);
		rs += smartRoomsDao.updateConferenceRoomCodeMgmtByXsm07(smModifyList);
		return rs;
	}
	public int deleteConferenceRoomCodeMgmtByXsm07(List<BgabGascsm05> smModifyList) {
		return smartRoomsDao.deleteConferenceRoomCodeMgmtByXsm07(smModifyList);
	}
	public List<BgabGascsm05> searchCodeKndCombo(BgabGascsm05 sm05Vo) {
		return smartRoomsDao.searchCodeKndCombo(sm05Vo);
	}
	public int searchTitleCountByXsm(BgabGascsm01 smParamVo){
		return smartRoomsDao.searchTitleCountByXsm(smParamVo);
	}
	
	/**
	 * 상용구 조회
	 */
	public List<BgabGascsm01> searchTitleByXsm(BgabGascsm01 smParamVo){
		return smartRoomsDao.searchTitleByXsm(smParamVo);
	}
	
	/**
	 * 권한 관리
	 */
	public List<BgabGascsm05> searchAuthCormComboByXsm10(BgabGascsm05 smParamVo){
		return smartRoomsDao.searchAuthCormComboByXsm10(smParamVo);
	}
	public int searchAuthMgmtCountByXsm10(BgabGascsm06 smParamVo){
		return smartRoomsDao.searchAuthMgmtCountByXsm10(smParamVo);
	}
	public List<BgabGascsm06> searchAuthMgmtByXsm10(BgabGascsm06 smParamVo){
		return smartRoomsDao.searchAuthMgmtByXsm10(smParamVo);
	}
	public int saveAuthMgmtByXsm10(List<BgabGascsm06> smSaveList, List<BgabGascsm06> smModifyList){
		int rs = 0;
		rs += smartRoomsDao.insertAuthMgmtByXsm10(smSaveList);
		rs += smartRoomsDao.updateAuthMgmtByXsm10(smModifyList);
		return rs; 
	}
	public int deleteAuthMgmtByXsm10(List<BgabGascsm06> smDelList){
		return smartRoomsDao.deleteAuthMgmtByXsm10(smDelList); 
	}
	
	/**
	 * 통계현황
	 * @param paramVo
	 * @return
	 */
	public List<BgabGascsm07> searchConferenceRoomListByXsm11(BgabGascsm07 paramVo){
		return smartRoomsDao.searchConferenceRoomListByXsm11(paramVo); 
	}
	public List<BgabGascsm07> searchListByXsm11(BgabGascsm07 paramVo){
		return smartRoomsDao.searchListByXsm11(paramVo); 
	}
}
