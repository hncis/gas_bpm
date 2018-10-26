package com.hncis.roomsMeals.dao;

import java.util.List;

import com.hncis.roomsMeals.vo.BgabGascrm01Dto;
import com.hncis.roomsMeals.vo.BgabGascrm02Dto;
import com.hncis.roomsMeals.vo.BgabGascrm03Dto;
import com.hncis.roomsMeals.vo.BgabGascrm04Dto;
import com.hncis.system.vo.BgabGascz016Dto;

public interface RoomsMealsDao {
	
	public List<BgabGascrm03Dto> selectRmToMultiComboPlant(BgabGascrm03Dto dto);
	public List<BgabGascrm04Dto> selectRmToMultiComboMeal(BgabGascrm04Dto dto);
	public List<BgabGascrm03Dto> selectRmToRoomAuthInfo(BgabGascrm03Dto dto);
	
	public List<String> getSelectRmToCheckUseTime(BgabGascrm01Dto dto);
	public List<String> getSelectRmToCheckUseTime2(BgabGascrm01Dto dto);
	public int insertRmToRequest(BgabGascrm01Dto dto);
	public int deleteRmToReqList(BgabGascrm01Dto dto);
	public int insertRmToReqList(List<BgabGascrm02Dto> dtoDtl);
	public BgabGascrm01Dto selectInfoRmToRequest(BgabGascrm01Dto dto);
	public List<BgabGascrm01Dto> selectInfoRmToPlaceList(BgabGascrm01Dto dto);
	public List<BgabGascrm01Dto> selectInfoRmToReqList(BgabGascrm01Dto dto);
	public int deleteRmToRequest(BgabGascrm01Dto dto);
	public int updateRmToRequestForConfirm(BgabGascrm01Dto dto);
	public String selectRmToApproveForVipRoomCheck(BgabGascrm01Dto dto);
	public int updateRmToApproveForConfirm(BgabGascrm01Dto dto);
	public int updateRmToRequestForConfirmCancel(BgabGascrm01Dto dto);
	
	public List<BgabGascrm01Dto> selectInfoRmToListForMon(BgabGascrm01Dto dto);
	
	public int selectCountRmToListDaily(BgabGascrm01Dto dto);
	public List<BgabGascrm01Dto> selectListRmToListDaily(BgabGascrm01Dto dto);
	public int updateRmToListDailyForDone(BgabGascrm01Dto dto);
	public int updateRmToListDailyForCancel(List<BgabGascrm01Dto> dtoList);

	public int selectCountRmToRoomsManagement(BgabGascrm03Dto dto);
	public List<BgabGascrm03Dto> selectListRmToRoomsManagement(BgabGascrm03Dto dto);
	public int insertRmToRoomsManagement(List<BgabGascrm03Dto> dtoIList);
	public int updateRmToRoomsManagement(List<BgabGascrm03Dto> dtoUList);
	public int deleteRmToRoomsManagement(List<BgabGascrm03Dto> dtoList);
	
	public int selectCountRmToMealsManagement(BgabGascrm04Dto dto);
	public List<BgabGascrm04Dto> selectListRmToMealsManagement(BgabGascrm04Dto dto);
	public int insertRmToMealsManagement(List<BgabGascrm04Dto> dtoIList);
	public int updateRmToMealsManagement(List<BgabGascrm04Dto> dtoUList);
	public int deleteRmToMealsManagement(List<BgabGascrm04Dto> dtoList);
	
	
	public int selectCountRmToMealsMgmtList(BgabGascrm01Dto dto);
	public List<BgabGascrm01Dto> selectListRmToMealsMgmtList(BgabGascrm01Dto dto);
	public int updateRmToMealsMgmtStatus(List<BgabGascrm01Dto> dtoList);
	
	public int selectCountRmToConfirmList(BgabGascrm01Dto dto);
	public List<BgabGascrm01Dto> selectListRmToConfirmList(BgabGascrm01Dto dto);
	public int updateRmToConfirmList(List<BgabGascrm01Dto> dtoList);
	public int updateRmToConfirmCancelList(List<BgabGascrm01Dto> dtoList);
	public BgabGascrm01Dto selectInfoRmToRequestForAprv(BgabGascrm01Dto dto);
	
	public int updateRoomsMealsPoInfo(BgabGascrm01Dto dto);
	
	public BgabGascz016Dto getSelectRoomsMaterialManagement(BgabGascz016Dto dto);
	
	public int updateInfoRmToReject(BgabGascrm01Dto dto);
	public int updateInfoRmToDirectConfirm(BgabGascrm01Dto dto);
	
	public int updateRmToApprveInfo(BgabGascrm01Dto reqDto);
}
