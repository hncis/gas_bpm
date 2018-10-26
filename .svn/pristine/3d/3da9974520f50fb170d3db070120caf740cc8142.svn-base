package com.hncis.vehicleLog.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.vehicleLog.vo.BgabGascvl01Dto;

@Transactional
public interface VehicleLogManager {
	CommonMessage saveXvl01Info(BgabGascvl01Dto dto);
	BgabGascvl01Dto selectXvl01Info(BgabGascvl01Dto dto);
	BgabGascvl01Dto selectXvl01InfoByIfId(BgabGascvl01Dto dto);
	CommonMessage deleteXvl01Info(BgabGascvl01Dto dto);
	int selectXvl01InfoListCount(BgabGascvl01Dto dto);

	List<BgabGascvl01Dto> selectXvl01InfoList(BgabGascvl01Dto dto);
	int selectXvl02InfoListCount(BgabGascvl01Dto dto);
	List<BgabGascvl01Dto> selectXvl02InfoList(BgabGascvl01Dto dto);

	int selectXvl03InfoListCount(BgabGascvl01Dto dto);
	List<BgabGascvl01Dto> selectXvl03InfoList(BgabGascvl01Dto dto);
	
	CommonMessage setApproveVLToRequest(BgabGascvl01Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	CommonMessage setApproveCancelVLToRequest(BgabGascvl01Dto dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	
	int confirmTRToRequest(BgabGascvl01Dto dto);
	CommonMessage updateInfoTrToReject(BgabGascvl01Dto dto);
}
