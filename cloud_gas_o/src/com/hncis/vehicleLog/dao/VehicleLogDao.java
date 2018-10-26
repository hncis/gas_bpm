package com.hncis.vehicleLog.dao;

import java.util.List;

import com.hncis.vehicleLog.vo.BgabGascvl01Dto;

public interface VehicleLogDao {
	int saveXvl01Info(BgabGascvl01Dto dto);
	BgabGascvl01Dto selectXvl01Info(BgabGascvl01Dto dto);
	BgabGascvl01Dto selectXvl01InfoByIfId(BgabGascvl01Dto dto);
	int deleteXvl01Info(BgabGascvl01Dto dto);
	int selectXvl01InfoListCount(BgabGascvl01Dto dto);

	List<BgabGascvl01Dto> selectXvl01InfoList(BgabGascvl01Dto dto);
	int selectXvl02InfoListCount(BgabGascvl01Dto dto);
	List<BgabGascvl01Dto> selectXvl02InfoList(BgabGascvl01Dto dto);

	int selectXvl03InfoListCount(BgabGascvl01Dto dto);
	List<BgabGascvl01Dto> selectXvl03InfoList(BgabGascvl01Dto dto);
	
	public int approveCancelVLToRequest(BgabGascvl01Dto dto);
	public int confirmVLToRequest(BgabGascvl01Dto dto);
	public int updateInfoVLToReject(BgabGascvl01Dto dto);
}
