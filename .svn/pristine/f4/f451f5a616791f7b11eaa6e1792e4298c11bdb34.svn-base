package com.hncis.roomsMeals.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.roomsMeals.dao.RoomsMealsDao;
import com.hncis.roomsMeals.vo.BgabGascrm01Dto;
import com.hncis.roomsMeals.vo.BgabGascrm02Dto;
import com.hncis.roomsMeals.vo.BgabGascrm03Dto;
import com.hncis.roomsMeals.vo.BgabGascrm04Dto;
import com.hncis.system.vo.BgabGascz016Dto;

public class RoomsMealsDaoImplByIBatis extends CommonDao implements RoomsMealsDao{

	private final String SELECT_RM_TO_MULTI_COMBO_PLANT 			= "selectRmToMultiComboPlant";
	private final String SELECT_RM_TO_MULTI_COMBO_MEAL 				= "selectRmToMultiComboMeal";
	private final String SELECT_RM_TO_ROOM_AUTH_INFO 				= "selectRmToRoomAuthInfo";
	
	
	private final String SELECT_RM_TO_CHECK_USE_TIME 				= "selectRmToCheckUseTime";
	private final String SELECT_RM_TO_CHECK_USE_TIME2 				= "selectRmToCheckUseTime2";
	private final String INSERT_RM_TO_REQUEST 						= "insertRmToRequest";
	private final String DELETE_RM_TO_REQ_LIST 						= "deleteRmToReqList";
	private final String INSERT_RM_TO_REQ_LIST 						= "insertRmToReqList";
	private final String SELECT_INFO_RM_TO_REQUEST 					= "selectInfoRmToRequest";
	private final String SELECT_INFO_RM_TO_PLACE_LIST 				= "selectInfoRmToPlaceList";
	private final String SELECT_INFO_RM_TO_REQ_LIST 				= "selectInfoRmToReqList";
	private final String DELETE_RM_TO_REQUEST						= "deleteRmToRequest";
	private final String UPDATE_RM_TO_REQUEST_FOR_CONFIRM 			= "updateRmToRequestForConfirm";
	private final String SELECT_RM_TO_APPROVE_FOR_VIP_ROOM_CHECK	= "selectRmToApproveForVipRoomCheck";
	private final String UPDATE_RM_TO_APPROVE_FOR_CONFIRM 			= "updateRmToApproveForConfirm";
	private final String UPDATE_RM_TO_REQUEST_FOR_CONFIRM_CANCEL	= "updateRmToRequestForConfirmCancel";
	
	private final String SELECT_INFO_RM_TO_LIST_FOR_MON 			= "selectInfoRmToListForMon";
	
	private final String SELECT_COUNT_RM_TO_LIST_DAILY 				= "selectCountRmToListDaily";
	private final String SELECT_LIST_RM_TO_LIST_DAILY 				= "selectListRmToListDaily";
	private final String UPDATE_RM_TO_LIST_DAILY_FOR_DONE 			= "updateRmToListDailyForDone";
	private final String UPDATE_RM_TO_LIST_DAILY_FOR_CANCEL 		= "updateRmToListDailyForCancel";
	
	
	private final String SELECT_COUNT_RM_TO_ROOMS_MANAGEMENT 		= "selectCountRmToRoomsManagement";
	private final String SELECT_LIST_RM_TO_ROOMS_MANAGEMENT 		= "selectListRmToRoomsManagement";
	private final String INSERT_RM_TO_ROOMS_MANAGEMENT 				= "insertRmToRoomsManagement";
	private final String UPDATE_RM_TO_ROOMS_MANAGEMENT 				= "updateRmToRoomsManagement";
	private final String DELETE_RM_TO_ROOMS_MANAGEMENT 				= "deleteRmToRoomsManagement";
	
	private final String SELECT_COUNT_RM_TO_MEALS_MANAGEMENT 		= "selectCountRmToMealsManagement";
	private final String SELECT_LIST_RM_TO_MEALS_MANAGEMENT 		= "selectListRmToMealsManagement";
	private final String INSERT_RM_TO_MEALS_MANAGEMENT 				= "insertRmToMealsManagement";
	private final String UPDATE_RM_TO_MEALS_MANAGEMENT 				= "updateRmToMealsManagement";
	private final String DELETE_RM_TO_MEALS_MANAGEMENT 				= "deleteRmToMealsManagement";
	
	private final String SELECT_COUNT_RM_TO_MEALS_MGMT_LIST 		= "selectCountRmToMealsMgmtList";
	private final String SELECT_LIST_RM_TO_MEALS_MGMT_LIST 			= "selectListRmToMealsMgmtList";
	private final String UPDATE_RM_TO_MEALS_MGMT_STATUS 			= "updateRmToMealsMgmtStatus";
	
	private final String SELECT_COUNT_RM_TO_CONFIRM_LIST 			= "selectCountRmToConfirmList";
	private final String SELECT_LIST_RM_TO_CONFIRM_LIST 			= "selectListRmToConfirmList";
	private final String UPDATE_RM_TO_CONFIRM_LIST 					= "updateRmToConfirmList";
	private final String UPDATE_RM_TO_CONFIRM_CANCEL_LIST 			= "updateRmToConfirmCancelList";
	
	private final String SELECT_INFO_RM_TO_REQUEST_FOR_APRV 		= "selectInfoRmToRequestForAprv";
	private final String UPDATE_ROOMSMEALS_PO_INFO 					= "updateRoomsMealsPoInfo";
	
	private final String UPDATE_INFO_RM_TO_REJECT 					= "updateInfoRmToReject";
	private final String UPDATE_INFO_RM_TO_DIRECT_CONFIRM			= "updateInfoRmToDirectConfirm";
	private final String UPDATE_RM_TO_APPRVE_INFO					= "updateRmToApprveInfo";
	
	
	@Override
	public List<BgabGascrm03Dto> selectRmToMultiComboPlant(BgabGascrm03Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_RM_TO_MULTI_COMBO_PLANT, dto);
	}
	@Override
	public List<BgabGascrm04Dto> selectRmToMultiComboMeal(BgabGascrm04Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_RM_TO_MULTI_COMBO_MEAL, dto);
	}
	@Override
	public List<BgabGascrm03Dto> selectRmToRoomAuthInfo(BgabGascrm03Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_RM_TO_ROOM_AUTH_INFO, dto);
	}
	
	@Override
	public List<String> getSelectRmToCheckUseTime(BgabGascrm01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_RM_TO_CHECK_USE_TIME, dto);
	}
	@Override
	public List<String> getSelectRmToCheckUseTime2(BgabGascrm01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_RM_TO_CHECK_USE_TIME2, dto);
	}
	
	@Override
	public int insertRmToRequest(BgabGascrm01Dto dto) {
		return super.insert(INSERT_RM_TO_REQUEST, dto);
	}
	@Override
	public int deleteRmToReqList(BgabGascrm01Dto dto) {
		return super.delete(DELETE_RM_TO_REQ_LIST, dto);
	}
	@Override
	public int insertRmToReqList(List<BgabGascrm02Dto> dto) {
		return super.insert(INSERT_RM_TO_REQ_LIST, dto);
	}
	
	public BgabGascrm01Dto selectInfoRmToRequest(BgabGascrm01Dto dto){
		return (BgabGascrm01Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_RM_TO_REQUEST, dto);
	}
	
	@Override
	public List<BgabGascrm01Dto> selectInfoRmToPlaceList(BgabGascrm01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_INFO_RM_TO_PLACE_LIST, dto);
	}
	@Override
	public List<BgabGascrm01Dto> selectInfoRmToReqList(BgabGascrm01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_INFO_RM_TO_REQ_LIST, dto);
	}
	@Override
	public int deleteRmToRequest(BgabGascrm01Dto dto) {
		return super.update(DELETE_RM_TO_REQUEST, dto);
	}
	
	@Override
	public int updateRmToRequestForConfirm(BgabGascrm01Dto dto) {
		return super.update(UPDATE_RM_TO_REQUEST_FOR_CONFIRM, dto);
	}
	
	@Override
	public String selectRmToApproveForVipRoomCheck(BgabGascrm01Dto dto){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_RM_TO_APPROVE_FOR_VIP_ROOM_CHECK, dto);
	}
	
	@Override
	public int updateRmToApproveForConfirm(BgabGascrm01Dto dto) {
		return super.update(UPDATE_RM_TO_APPROVE_FOR_CONFIRM, dto);
	}
	
	@Override
	public int updateRmToRequestForConfirmCancel(BgabGascrm01Dto dto) {
		return super.update(UPDATE_RM_TO_REQUEST_FOR_CONFIRM_CANCEL, dto);
	}
	
	@Override
	public List<BgabGascrm01Dto> selectInfoRmToListForMon(BgabGascrm01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_INFO_RM_TO_LIST_FOR_MON, dto);
	}
	
	@Override
	public int selectCountRmToListDaily(BgabGascrm01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_RM_TO_LIST_DAILY, dto));
	}

	@Override
	public List<BgabGascrm01Dto> selectListRmToListDaily(BgabGascrm01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_RM_TO_LIST_DAILY, dto);
	}
	
	@Override
	public int updateRmToListDailyForDone(BgabGascrm01Dto dto) {
		return super.update(UPDATE_RM_TO_LIST_DAILY_FOR_DONE, dto);
	}
	@Override
	public int updateRmToListDailyForCancel(List<BgabGascrm01Dto> dtoList) {
		return super.update(UPDATE_RM_TO_LIST_DAILY_FOR_CANCEL, dtoList);
	}
	
	@Override
	public int selectCountRmToRoomsManagement(BgabGascrm03Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_RM_TO_ROOMS_MANAGEMENT, dto));
	}

	@Override
	public List<BgabGascrm03Dto> selectListRmToRoomsManagement(BgabGascrm03Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_RM_TO_ROOMS_MANAGEMENT, dto);
	}

	@Override
	public int insertRmToRoomsManagement(List<BgabGascrm03Dto> list) {
		return super.update(INSERT_RM_TO_ROOMS_MANAGEMENT, list);
	}

	@Override
	public int updateRmToRoomsManagement(List<BgabGascrm03Dto> list) {
		return super.update(UPDATE_RM_TO_ROOMS_MANAGEMENT, list);
	}

	@Override
	public int deleteRmToRoomsManagement(List<BgabGascrm03Dto> list) {
		return super.update(DELETE_RM_TO_ROOMS_MANAGEMENT, list);
	}
	
	
	@Override
	public int selectCountRmToMealsManagement(BgabGascrm04Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_RM_TO_MEALS_MANAGEMENT, dto));
	}

	@Override
	public List<BgabGascrm04Dto> selectListRmToMealsManagement(BgabGascrm04Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_RM_TO_MEALS_MANAGEMENT, dto);
	}

	@Override
	public int insertRmToMealsManagement(List<BgabGascrm04Dto> list) {
		return super.update(INSERT_RM_TO_MEALS_MANAGEMENT, list);
	}

	@Override
	public int updateRmToMealsManagement(List<BgabGascrm04Dto> list) {
		return super.update(UPDATE_RM_TO_MEALS_MANAGEMENT, list);
	}

	@Override
	public int deleteRmToMealsManagement(List<BgabGascrm04Dto> list) {
		return super.update(DELETE_RM_TO_MEALS_MANAGEMENT, list);
	}
	
	
	@Override
	public int selectCountRmToMealsMgmtList(BgabGascrm01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_RM_TO_MEALS_MGMT_LIST, dto));
	}

	@Override
	public List<BgabGascrm01Dto> selectListRmToMealsMgmtList(BgabGascrm01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_RM_TO_MEALS_MGMT_LIST, dto);
	}
	
	@Override
	public int updateRmToMealsMgmtStatus(List<BgabGascrm01Dto> list) {
		return super.update(UPDATE_RM_TO_MEALS_MGMT_STATUS, list);
	}
	@Override
	public int selectCountRmToConfirmList(BgabGascrm01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_RM_TO_CONFIRM_LIST, dto));
	}
	@Override
	public List<BgabGascrm01Dto> selectListRmToConfirmList(BgabGascrm01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_RM_TO_CONFIRM_LIST, dto);
	}
	@Override
	public int updateRmToConfirmList(List<BgabGascrm01Dto> list) {
		return super.update(UPDATE_RM_TO_CONFIRM_LIST, list);
	}
	@Override
	public int updateRmToConfirmCancelList(List<BgabGascrm01Dto> list) {
		return super.update(UPDATE_RM_TO_CONFIRM_CANCEL_LIST, list);
	}
	@Override
	public BgabGascrm01Dto selectInfoRmToRequestForAprv(BgabGascrm01Dto dto) {
		return (BgabGascrm01Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_RM_TO_REQUEST_FOR_APRV, dto);
	}
	
	public int updateRoomsMealsPoInfo(BgabGascrm01Dto dto){
		return update(UPDATE_ROOMSMEALS_PO_INFO,dto);
	}
	
	public BgabGascz016Dto getSelectRoomsMaterialManagement(BgabGascz016Dto dto){
		return (BgabGascz016Dto)getSqlMapClientTemplate().queryForObject("selectRoomsMaterialManagement", dto);
	}
	@Override
	public int updateInfoRmToReject(BgabGascrm01Dto dto) {
		return super.update(UPDATE_INFO_RM_TO_REJECT, dto);
	}
	@Override
	public int updateInfoRmToDirectConfirm(BgabGascrm01Dto dto) {
		return super.update(UPDATE_INFO_RM_TO_DIRECT_CONFIRM, dto);
	}
	@Override
	public int updateRmToApprveInfo(BgabGascrm01Dto dto) {
		return super.update(UPDATE_RM_TO_APPRVE_INFO, dto);
	}
}
