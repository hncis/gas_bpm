package com.hncis.leave.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.leave.vo.BgabGasclv01Dto;
import com.hncis.leave.vo.BgabGasclv03Dto;

@Transactional
public interface LeaveManager {

	BgabGasclv01Dto selectLvToRemainDaysInfo(BgabGasclv01Dto dto);
	CommonMessage insertLvToRequestInfo(BgabGasclv01Dto dto);
	BgabGasclv01Dto selectLvToRequestInfo(BgabGasclv01Dto dto);
	List<BgabGasclv01Dto> selectLvToHistoryList(BgabGasclv01Dto vo);
	CommonMessage setApproval(BgabGasclv01Dto keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	CommonMessage setApprovalCancel(BgabGasclv01Dto keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	public CommonMessage updateLvToRequestForConfirm(BgabGasclv01Dto dto);
	public CommonMessage updateLvToRequestForReject(BgabGasclv01Dto dto);
	CommonMessage deleteLvToRequest(BgabGasclv01Dto dto);
	
	void updateLvToLeaveDayInfo(BgabGasclv03Dto dto);
	BgabGasclv03Dto selectLvToLeaveDayInfo(BgabGasclv03Dto dto);
	
	int selectCountLvToReqList(BgabGasclv01Dto dto);
	List<BgabGasclv01Dto> selectLvToReqList(BgabGasclv01Dto dto);
	
	
	
	
	

}
