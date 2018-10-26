package com.hncis.roomsMeals.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.vo.CommonMessage;
import com.hncis.roomsMeals.vo.BgabGascrm01Dto;
import com.hncis.roomsMeals.vo.BgabGascrm03Dto;
import com.hncis.roomsMeals.vo.BgabGascrm04Dto;

@Transactional
public interface RoomsMealsManager {

	List<BgabGascrm03Dto> selectRmToMultiComboPlant(BgabGascrm03Dto code);
	List<BgabGascrm04Dto> selectRmToMultiComboMeal(BgabGascrm04Dto vo);
	BgabGascrm03Dto selectRmToRoomAuthInfo(BgabGascrm03Dto dto);

	BgabGascrm01Dto saveRmToRequest(HttpServletRequest req, BgabGascrm01Dto dto);
	BgabGascrm01Dto selectInfoRmToRequest(BgabGascrm01Dto dto);
	List<BgabGascrm01Dto> selectInfoRmToReqList(BgabGascrm01Dto dto, HttpServletRequest req);
	CommonMessage deleteRmToRequest(HttpServletRequest req, BgabGascrm01Dto dto);
	CommonMessage setApproval(BgabGascrm01Dto dto, HttpServletRequest req);
	CommonMessage setApprovalCancel(BgabGascrm01Dto regDto, HttpServletRequest req);
	CommonMessage updateRmToRequestForConfirm(HttpServletRequest req, BgabGascrm01Dto dto);
	CommonMessage doApproveToMyConfirm(List<BgabGascrm01Dto> list);
	CommonMessage updateRmToRequestForConfirmCancel(HttpServletRequest req, BgabGascrm01Dto dto);

	List<BgabGascrm01Dto> selectInfoRmToListForMon(BgabGascrm01Dto dto, HttpServletRequest req);

	int selectCountRmToListDaily(BgabGascrm01Dto dto);
	List<BgabGascrm01Dto> selectListRmToListDaily(BgabGascrm01Dto dto);
	BgabGascrm01Dto updateRmToListDailyForDone(HttpServletRequest req, List<BgabGascrm01Dto> dtoList);
	void updateRmToListDailyForCancel(List<BgabGascrm01Dto> dtoList);

	int selectCountRmToRoomsManagement(BgabGascrm03Dto dto);
	List<BgabGascrm03Dto> selectListRmToRoomsManagement(BgabGascrm03Dto dto);
	void saveRmToRoomsManagement(List<BgabGascrm03Dto> dtoIList, List<BgabGascrm03Dto> dtoUList);
	void deleteRmToRoomsManagement(List<BgabGascrm03Dto> dtoList);

	int selectCountRmToMealsManagement(BgabGascrm04Dto dto);
	List<BgabGascrm04Dto> selectListRmToMealsManagement(BgabGascrm04Dto dto);
	void saveRmToMealsManagement(List<BgabGascrm04Dto> dtoIList, List<BgabGascrm04Dto> dtoUList);
	void deleteRmToMealsManagement(List<BgabGascrm04Dto> dtoList);

	int selectCountRmToMealsMgmtList(BgabGascrm01Dto dto);
	List<BgabGascrm01Dto> selectListRmToMealsMgmtList(BgabGascrm01Dto dto);
	void updateRmToMealsMgmtStatus(List<BgabGascrm01Dto> dtoList);

	int selectCountRmToConfirmList(BgabGascrm01Dto dto);
	List<BgabGascrm01Dto> selectListRmToConfirmList(BgabGascrm01Dto dto);
	void updateRmToConfirmList(HttpServletRequest req, List<BgabGascrm01Dto> dtoList);
	void updateRmToConfirmCancelList(HttpServletRequest req, List<BgabGascrm01Dto> dtoList);

	BgabGascrm01Dto selectInfoRmToRequestForAprv(BgabGascrm01Dto dto);
	public int updateInfoRmToReject(BgabGascrm01Dto dto, HttpServletRequest req) throws SessionException;
}
