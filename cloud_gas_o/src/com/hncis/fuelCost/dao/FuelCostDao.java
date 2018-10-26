package com.hncis.fuelCost.dao;

import java.util.List;

import com.hncis.fuelCost.vo.BgabGascfc01Dto;
import com.hncis.fuelCost.vo.BgabGascfc02Dto;

public interface FuelCostDao {
	int insertXfc01Info(BgabGascfc01Dto dto);

	int updateXfc01Info(BgabGascfc01Dto dto);

	int deleteXfc01Info(BgabGascfc01Dto dto);

	int updateXfc05Info(BgabGascfc02Dto dto);

	BgabGascfc01Dto selectXfc01Info(BgabGascfc01Dto dto);
	
	BgabGascfc01Dto selectXfc01InfoByIfId(BgabGascfc01Dto dto);

	int selectXfc01InfoListCount(BgabGascfc01Dto dto);

	List<BgabGascfc01Dto> selectXfc01InfoList(BgabGascfc01Dto dto);

	int updateXfcPgsStCd(BgabGascfc01Dto dto);

	int selectXfcRequestInfoListCount(BgabGascfc01Dto dto);

	List<BgabGascfc01Dto> selectXfcRequestInfoList(BgabGascfc01Dto dto);

	int selectXfc04InfoListCount(BgabGascfc01Dto dto);

	List<BgabGascfc01Dto> selectXfc04InfoList(BgabGascfc01Dto dto);

	int updateXfc05InfoPast(BgabGascfc02Dto dto);

	int insertXfc05Info(BgabGascfc02Dto dto);

	BgabGascfc02Dto selectXfc05Info(BgabGascfc02Dto dto);

	int selectXfc05InfoListCount(BgabGascfc02Dto dto);

	List<BgabGascfc02Dto> selectXfc05InfoList(BgabGascfc02Dto dto);
	
	int updateXfcToReject(BgabGascfc01Dto dto);
}