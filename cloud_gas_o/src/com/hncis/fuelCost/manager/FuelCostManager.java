package com.hncis.fuelCost.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.fuelCost.vo.BgabGascfc01Dto;
import com.hncis.fuelCost.vo.BgabGascfc02Dto;

@Transactional
public interface FuelCostManager {
	CommonMessage saveXfc01Info(BgabGascfc01Dto dto);

	CommonMessage deleteXfc01Info(BgabGascfc01Dto dto);

	BgabGascfc01Dto selectXfc01Info(BgabGascfc01Dto dto);
	
	BgabGascfc01Dto selectXfc01InfoByIfId(BgabGascfc01Dto dto);

	int selectXfc01InfoListCount(BgabGascfc01Dto dto);

	List<BgabGascfc01Dto> selectXfc01InfoList(BgabGascfc01Dto dto);

	BgabGascfc01Dto selectFuelCostCal(BgabGascfc01Dto dto);

	CommonMessage updateXfcRequest(BgabGascfc01Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);

	CommonMessage updateXfcRequestCancel(BgabGascfc01Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);

	CommonMessage updateXfcConfirm(BgabGascfc01Dto dto);

	CommonMessage updateXfcReject(BgabGascfc01Dto dto);

	int selectXfcRequestInfoListCount(BgabGascfc01Dto dto);

	List<BgabGascfc01Dto> selectXfcRequestInfoList(BgabGascfc01Dto dto);

	int selectXfc04InfoListCount(BgabGascfc01Dto dto);

	List<BgabGascfc01Dto> selectXfc04InfoList(BgabGascfc01Dto dto);

	CommonMessage saveXfc05Info(BgabGascfc02Dto dto);

	BgabGascfc02Dto selectXfc05Info(BgabGascfc02Dto dto);

	int selectXfc05InfoListCount(BgabGascfc02Dto dto);

	List<BgabGascfc02Dto> selectXfc05InfoList(BgabGascfc02Dto dto);
	
	CommonMessage updateXfcToReject(BgabGascfc01Dto dto);
}