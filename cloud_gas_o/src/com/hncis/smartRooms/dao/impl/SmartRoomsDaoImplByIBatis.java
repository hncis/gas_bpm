package com.hncis.smartRooms.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.common.vo.CommonCode;
import com.hncis.smartRooms.dao.SmartRoomsDao;
import com.hncis.smartRooms.vo.BgabGascsm01;
import com.hncis.smartRooms.vo.BgabGascsm02;
import com.hncis.smartRooms.vo.BgabGascsm03;
import com.hncis.smartRooms.vo.BgabGascsm04;
import com.hncis.smartRooms.vo.BgabGascsm05;
import com.hncis.smartRooms.vo.BgabGascsm06;
import com.hncis.smartRooms.vo.BgabGascsm07;
import com.hncis.smartRooms.vo.BgabGascsmDto;

public class SmartRoomsDaoImplByIBatis extends CommonDao implements SmartRoomsDao{
	private static final String uncheck = "unchecked";
	/**
	 * 회의실 공통
	 */
	@SuppressWarnings(uncheck)
	public List<BgabGascsm05> searchComboByXsm(BgabGascsm05 sm05Vo){
		return getSqlMapClientTemplate().queryForList("searchComboByXsm", sm05Vo);
	}
	public BgabGascsmDto searchXsmByDocNo(HashMap<String, String> paramMap){
		return (BgabGascsmDto)getSqlMapClientTemplate().queryForObject("searchXsmByDocNo", paramMap);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsm01> searchXsmBySpecialDay(HashMap<String, Object> paramMap) {
		return getSqlMapClientTemplate().queryForList("searchXsmBySpecialDay", paramMap);
	}
	public String searchCormDupCheck(BgabGascsm01 reSm01Vo) {
		return (String)getSqlMapClientTemplate().queryForObject("searchCormDupCheck", reSm01Vo);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsmDto> searchEtcUserList(BgabGascsmDto xsmDto) {
		return getSqlMapClientTemplate().queryForList("searchEtcUserList", xsmDto);
	}
	@SuppressWarnings(uncheck)
	public HashMap<String, Object> searchConferencePolicy(HashMap<String, String> paramMap) {
		return (HashMap<String, Object>)getSqlMapClientTemplate().queryForObject("searchConferencePolicy", paramMap);
	}
	public int searchTodayReservCnt(BgabGascsm01 teamCntVo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("searchTodayReservCnt", teamCntVo));
	}
	
	public BgabGascsm01 searchConferenceReservInfo(BgabGascsm01 sm01Vo){
		return (BgabGascsm01)getSqlMapClientTemplate().queryForObject("searchConferenceReservInfo", sm01Vo);
	}
	public BgabGascsm04 searchConferenceInfo(BgabGascsm04 sm04Vo){
		return (BgabGascsm04)getSqlMapClientTemplate().queryForObject("searchConferenceInfo", sm04Vo);
	}
	public int insertConferenceRoom1(BgabGascsm01 sm01Vo) {
		return super.insert("insertConferenceRoom1", sm01Vo);
	}
	public int insertConferenceRoom2(BgabGascsm01 sm01Vo) {
		return super.insert("insertConferenceRoom2", sm01Vo);
	}
	public int updateConferenceRoom1(BgabGascsm01 sm01Vo) {
		return super.update("updateConferenceRoom1", sm01Vo);
	}
	public int updateConferenceRoom2(BgabGascsm01 sm01Vo) {
		return super.update("updateConferenceRoom2", sm01Vo);
	}
	public int deleteConferenceRoom2(BgabGascsm01 sm01Vo) {
		return super.delete("deleteConferenceRoom2", sm01Vo);
	}
	public int deleteConferenceRoom1(BgabGascsm01 sm01Vo) {
		return super.delete("deleteConferenceRoom1", sm01Vo);
	}
	
	/**
	 * 빠른예약
	 */
	public String searchFastReservCormTotalCount(BgabGascsm01 paramVo) {
		return (String)getSqlMapClientTemplate().queryForObject("searchFastReservCormTotalCount", paramVo);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsm01> searchFastReservCormList(BgabGascsm01 paramVo) {
		return getSqlMapClientTemplate().queryForList("searchFastReservCormList", paramVo);
	}
	
	/**
	 * 상세검색 및 예약
	 */
	@SuppressWarnings(uncheck)
	public List<BgabGascsm02> selectConferenceRoomList1(BgabGascsm02 sm02Vo) {
		return getSqlMapClientTemplate().queryForList("searchConferenceRoomList1", sm02Vo);
	}
	public String selectConferenceRoomTotalCount(BgabGascsm02 sm02Vo) {
		return (String)getSqlMapClientTemplate().queryForObject("searchConferenceRoomTotalCount", sm02Vo);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsm02> selectConferenceRoomList2(BgabGascsm02 sm02Vo) {
		return getSqlMapClientTemplate().queryForList("searchConferenceRoomList2", sm02Vo);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsmDto> searchReservationList(BgabGascsm01 paramVo) {
		return getSqlMapClientTemplate().queryForList("searchReservationList", paramVo);
	}
	
	/**
	 * 나의예약현황
	 */
	public String searchMyReservationTotalCount(BgabGascsm01 paramVo) {
		return (String)getSqlMapClientTemplate().queryForObject("searchMyReservationTotalCount", paramVo);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsm01> searchMyReservationList(BgabGascsm01 paramVo){
		return getSqlMapClientTemplate().queryForList("searchMyReservationList", paramVo);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsm01> searchMyReservationExcel(BgabGascsm01 paramVo){
		return getSqlMapClientTemplate().queryForList("searchMyReservationExcel", paramVo);
	}
	
	/**
	 * 예약현황
	 */
	public int searchCountByXsm04(BgabGascsm01 smParamVo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("searchCountByXsm04", smParamVo));
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsm01> searchByXsm04(BgabGascsm01 smParamVo) {
		return getSqlMapClientTemplate().queryForList("searchByXsm04", smParamVo);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsm01> searchExcelByXsm04(BgabGascsm01 smParamVo) {
		return getSqlMapClientTemplate().queryForList("searchExcelByXsm04", smParamVo);
	}
	public int updateUseApproveByXsm04(BgabGascsm01 sm01Vo){
		return super.update("updateUseApproveByXsm04", sm01Vo);
	}
	public int updateNotUseApproveByXsm04(BgabGascsm01 sm01Vo){
		return super.update("updateNotUseApproveByXsm04", sm01Vo);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsmDto> searchXsmConfirmDamdang(BgabGascsm01 smParamVo) {
		return getSqlMapClientTemplate().queryForList("searchXsmConfirmDamdang", smParamVo);
	}
	
	/**
	 * 건물 관리
	 */
	@SuppressWarnings(uncheck)
	public List<CommonCode> searchBuildingComboByXsm05(BgabGascsm03 smParamVo) {
		return getSqlMapClientTemplate().queryForList("searchBuildingComboByXsm05", smParamVo);
	}
	public int searchBuildingMgmtCountByXsm05(BgabGascsm03 smParamVo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("searchBuildingMgmtCountByXsm05", smParamVo));
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsm03> searchBuildingMgmtByXsm05(BgabGascsm03 smParamVo) {
		return getSqlMapClientTemplate().queryForList("searchBuildingMgmtByXsm05", smParamVo);
	}
	public int insertBuildingMgmtByXsm05(List<BgabGascsm03> smSaveList) {
		return super.insert("insertBuildingMgmtByXsm05", smSaveList);
	}
	public int updateBuildingMgmtByXsm05(List<BgabGascsm03> smModifyList) {
		return super.update("updateBuildingMgmtByXsm05", smModifyList);
	}

	public int deleteBuildingMgmtByXsm05(List<BgabGascsm03> smDelList) {
		return super.update("deleteBuildingMgmtByXsm05", smDelList);
	}
	
	/**
	 * 회의실 관리
	 */
	@SuppressWarnings(uncheck)
	public List<CommonCode> searchConferenceFlComboByXsm06(BgabGascsm04 smParamVo){
		return getSqlMapClientTemplate().queryForList("searchConferenceFlComboByXsm06", smParamVo);
	}
	@SuppressWarnings(uncheck)
	public List<CommonCode> searchConferenceComboByXsm06(BgabGascsm04 smParamVo){
		return getSqlMapClientTemplate().queryForList("searchConferenceComboByXsm06", smParamVo);
	}
	public int searchConferenceRoomMgmtCountByXsm06(BgabGascsm04 smParamVo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("searchConferenceRoomMgmtCountByXsm06", smParamVo));
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsm04> searchConferenceRoomMgmtByXsm06(BgabGascsm04 smParamVo) {
		return getSqlMapClientTemplate().queryForList("searchConferenceRoomMgmtByXsm06", smParamVo);
	}
	public int insertConferenceRoomMgmtByXsm06(List<BgabGascsm04> smSaveList) {
		return super.insert("insertConferenceRoomMgmtByXsm06", smSaveList);
	}
	public int updateConferenceRoomMgmtByXsm06(List<BgabGascsm04> smModifyList) {
		return super.update("updateConferenceRoomMgmtByXsm06", smModifyList);
	}
	public int deleteConferenceRoomMgmtByXsm06(List<BgabGascsm04> smModifyList) {
		return super.update("deleteConferenceRoomMgmtByXsm06", smModifyList);
	}
	public int updateConferenceRoomMgmtPolicyByXsm06(List<BgabGascsm04> smModifyList) {
		return super.update("updateConferenceRoomMgmtPolicyByXsm06", smModifyList);
	}
	
	/**
	 * 회의실 코드 관리
	 */
	public int searchConferenceRoomCodeMgmtCountByXsm07(BgabGascsm05 smParamVo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("searchConferenceRoomCodeMgmtCountByXsm07", smParamVo));
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsm05> searchConferenceRoomCodeMgmtByXsm07(BgabGascsm05 smParamVo) {
		return getSqlMapClientTemplate().queryForList("searchConferenceRoomCodeMgmtByXsm07", smParamVo);
	}
	public int insertConferenceRoomCodeMgmtByXsm07(List<BgabGascsm05> smSaveList) {
		return super.insert("insertConferenceRoomCodeMgmtByXsm07", smSaveList);
	}
	public int updateConferenceRoomCodeMgmtByXsm07(List<BgabGascsm05> smModifyList) {
		return super.update("updateConferenceRoomCodeMgmtByXsm07", smModifyList);
	}
	public int deleteConferenceRoomCodeMgmtByXsm07(List<BgabGascsm05> smModifyList) {
		return super.update("deleteConferenceRoomCodeMgmtByXsm07", smModifyList);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsm05> searchCodeKndCombo(BgabGascsm05 sm05Vo) {
		return getSqlMapClientTemplate().queryForList("searchCodeKndCombo", sm05Vo);
	}
	public int searchTitleCountByXsm(BgabGascsm01 smParamVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("searchTitleCountByXsm", smParamVo));
	}
	
	/**
	 * 상용구 조회
	 */
	@SuppressWarnings(uncheck)
	public List<BgabGascsm01> searchTitleByXsm(BgabGascsm01 smParamVo){
		return getSqlMapClientTemplate().queryForList("searchTitleByXsm", smParamVo);
	}
	
	/**
	 * 권한 관리
	 */
	@SuppressWarnings(uncheck)
	public List<BgabGascsm05> searchAuthCormComboByXsm10(BgabGascsm05 smParamVo) {
		return getSqlMapClientTemplate().queryForList("searchAuthCormComboByXsm10", smParamVo);
	}
	public int searchAuthMgmtCountByXsm10(BgabGascsm06 smParamVo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("searchAuthMgmtCountByXsm10", smParamVo));
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsm06> searchAuthMgmtByXsm10(BgabGascsm06 smParamVo) {
		return getSqlMapClientTemplate().queryForList("searchAuthMgmtByXsm10", smParamVo);
	}
	public int insertAuthMgmtByXsm10(List<BgabGascsm06> smSaveList) {
		return super.insert("insertAuthMgmtByXsm10", smSaveList);
	}
	public int updateAuthMgmtByXsm10(List<BgabGascsm06> smModifyList) {
		return super.update("updateAuthMgmtByXsm10", smModifyList);
	}
	public int deleteAuthMgmtByXsm10(List<BgabGascsm06> smDelList) {
		return super.update("deleteAuthMgmtByXsm10", smDelList);
	}
	
	/**
	 * 통계현황
	 */
	@SuppressWarnings(uncheck)
	public List<BgabGascsm07> searchConferenceRoomListByXsm11(BgabGascsm07 paramVo){
		return getSqlMapClientTemplate().queryForList("searchConferenceRoomListByXsm11", paramVo);
	}
	@SuppressWarnings(uncheck)
	public List<BgabGascsm07> searchListByXsm11(BgabGascsm07 paramVo){
		return getSqlMapClientTemplate().queryForList("searchListByXsm11", paramVo);
	}
}
