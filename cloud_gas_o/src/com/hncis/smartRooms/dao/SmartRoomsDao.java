package com.hncis.smartRooms.dao;

import java.util.HashMap;
import java.util.List;

import com.hncis.common.vo.CommonCode;
import com.hncis.smartRooms.vo.BgabGascsm01;
import com.hncis.smartRooms.vo.BgabGascsm02;
import com.hncis.smartRooms.vo.BgabGascsm03;
import com.hncis.smartRooms.vo.BgabGascsm04;
import com.hncis.smartRooms.vo.BgabGascsm05;
import com.hncis.smartRooms.vo.BgabGascsm06;
import com.hncis.smartRooms.vo.BgabGascsm07;
import com.hncis.smartRooms.vo.BgabGascsmDto;

public interface SmartRoomsDao {
	/**
	 * 회의실 공통
	 */
	public List<BgabGascsm05> searchComboByXsm(BgabGascsm05 sm05Vo);
	public BgabGascsmDto searchXsmByDocNo(HashMap<String, String> paramMap);
	public List<BgabGascsm01> searchXsmBySpecialDay(HashMap<String, Object> paramMap);
	public String searchCormDupCheck(BgabGascsm01 reSm01Vo);
	public List<BgabGascsmDto> searchEtcUserList(BgabGascsmDto xsmDto);
	public HashMap<String, Object> searchConferencePolicy(HashMap<String, String> paramMap);
	public int searchTodayReservCnt(BgabGascsm01 teamCntVo);
	
	public BgabGascsm01 searchConferenceReservInfo(BgabGascsm01 sm01Vo);
	public BgabGascsm04 searchConferenceInfo(BgabGascsm04 sm04Vo);
	public int insertConferenceRoom1(BgabGascsm01 sm01Vo);
	public int insertConferenceRoom2(BgabGascsm01 sm01Vo);
	public int updateConferenceRoom1(BgabGascsm01 sm01Vo);
	public int updateConferenceRoom2(BgabGascsm01 sm01Vo);
	public int deleteConferenceRoom2(BgabGascsm01 sm01Vo);
	public int deleteConferenceRoom1(BgabGascsm01 sm01Vo);
	
	/**
	 * 빠른예약
	 */
	public String searchFastReservCormTotalCount(BgabGascsm01 paramVo);
	public List<BgabGascsm01> searchFastReservCormList(BgabGascsm01 paramVo);
	
	/**
	 * 상세검색 및 예약, 주간, 월간
	 */
	public List<BgabGascsm02> selectConferenceRoomList1(BgabGascsm02 sm02Vo);
	public String selectConferenceRoomTotalCount(BgabGascsm02 sm02Vo);
	public List<BgabGascsm02> selectConferenceRoomList2(BgabGascsm02 sm02Vo);
	public List<BgabGascsmDto> searchReservationList(BgabGascsm01 paramVo);
	
	/**
	 * 나의예약현황
	 */
	public String searchMyReservationTotalCount(BgabGascsm01 paramVo);
	public List<BgabGascsm01> searchMyReservationList(BgabGascsm01 paramVo);
	public List<BgabGascsm01> searchMyReservationExcel(BgabGascsm01 paramVo);
	
	/**
	 * 예약현황
	 */
	public int searchCountByXsm04(BgabGascsm01 smParamVo);
	public List<BgabGascsm01> searchByXsm04(BgabGascsm01 smParamVo);
	public List<BgabGascsm01> searchExcelByXsm04(BgabGascsm01 smParamVo);
	public int updateUseApproveByXsm04(BgabGascsm01 smParamVo);
	public int updateNotUseApproveByXsm04(BgabGascsm01 smParamVo);
	public List<BgabGascsmDto> searchXsmConfirmDamdang(BgabGascsm01 smParamVo);
	
	/**
	 * 건물 관리
	 */
	public List<CommonCode> searchBuildingComboByXsm05(BgabGascsm03 smParamVo);
	public int searchBuildingMgmtCountByXsm05(BgabGascsm03 smParamVo);
	public List<BgabGascsm03> searchBuildingMgmtByXsm05(BgabGascsm03 smParamVo);
	public int insertBuildingMgmtByXsm05(List<BgabGascsm03> smSaveList);
	public int updateBuildingMgmtByXsm05(List<BgabGascsm03> smModifyList);
	public int deleteBuildingMgmtByXsm05(List<BgabGascsm03> smDelList);
	
	/**
	 * 회의실 관리
	 */
	public List<CommonCode> searchConferenceFlComboByXsm06(BgabGascsm04 smParamVo);
	public List<CommonCode> searchConferenceComboByXsm06(BgabGascsm04 smParamVo);
	public int searchConferenceRoomMgmtCountByXsm06(BgabGascsm04 smParamVo);
	public List<BgabGascsm04> searchConferenceRoomMgmtByXsm06(BgabGascsm04 smParamVo);
	public int insertConferenceRoomMgmtByXsm06(List<BgabGascsm04> smSaveList);
	public int updateConferenceRoomMgmtByXsm06(List<BgabGascsm04> smModifyList);
	public int deleteConferenceRoomMgmtByXsm06(List<BgabGascsm04> smModifyList);
	public int updateConferenceRoomMgmtPolicyByXsm06(List<BgabGascsm04> smModifyList);
	
	/**
	 * 회의실 코드 관리
	 */
	public int searchConferenceRoomCodeMgmtCountByXsm07(BgabGascsm05 smParamVo);
	public List<BgabGascsm05> searchConferenceRoomCodeMgmtByXsm07(BgabGascsm05 smParamVo);
	public int insertConferenceRoomCodeMgmtByXsm07(List<BgabGascsm05> smSaveList);
	public int updateConferenceRoomCodeMgmtByXsm07(List<BgabGascsm05> smModifyList);
	public int deleteConferenceRoomCodeMgmtByXsm07(List<BgabGascsm05> smModifyList);
	public List<BgabGascsm05> searchCodeKndCombo(BgabGascsm05 sm05Vo);
	public int searchTitleCountByXsm(BgabGascsm01 smParamVo);
	
	/**
	 * 상용구 조회
	 */
	public List<BgabGascsm01> searchTitleByXsm(BgabGascsm01 smParamVo);
	
	/**
	 * 권한 관리
	 */
	public List<BgabGascsm05> searchAuthCormComboByXsm10(BgabGascsm05 smParamVo);
	public int searchAuthMgmtCountByXsm10(BgabGascsm06 smParamVo);
	public List<BgabGascsm06> searchAuthMgmtByXsm10(BgabGascsm06 smParamVo);
	public int insertAuthMgmtByXsm10(List<BgabGascsm06> smSaveList);
	public int updateAuthMgmtByXsm10(List<BgabGascsm06> smModifyList);
	public int deleteAuthMgmtByXsm10(List<BgabGascsm06> smDelList);
	
	/**
	 * 통계현황
	 */
	public List<BgabGascsm07> searchConferenceRoomListByXsm11(BgabGascsm07 paramVo);
	public List<BgabGascsm07> searchListByXsm11(BgabGascsm07 paramVo);
}
