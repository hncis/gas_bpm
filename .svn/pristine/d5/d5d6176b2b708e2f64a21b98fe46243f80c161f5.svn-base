package com.hncis.entranceMeal.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.vo.CommonMessage;
import com.hncis.entranceMeal.vo.BgabGascem01Dto;
import com.hncis.entranceMeal.vo.BgabGascem02Dto;

@Transactional
public interface EntranceMealManager {

	BgabGascem01Dto insertEmToRequest(BgabGascem01Dto bgabGascem01Dto,List<BgabGascem02Dto> bgabGascem02DtoList);
	BgabGascem01Dto getSelectInfoEmToRequest(BgabGascem01Dto dto);
	List<BgabGascem01Dto> getSelectListEmToRequest(BgabGascem01Dto bgabGascem01Dto);
	int deleteVisitorsEmToRequest(BgabGascem02Dto dto);
	BgabGascem01Dto deleteEmToRequest(BgabGascem01Dto bgabGascem01Dto);
	CommonMessage setApproval(BgabGascem01Dto dto,HttpServletRequest req) throws SessionException;
	CommonMessage setApprovalCancel(BgabGascem01Dto dto, HttpServletRequest req);
	int updateInfoEmToConfirm(BgabGascem01Dto bgabGascem01Dto,List<BgabGascem02Dto> bgabGascem02DtoList);
	int updateInfoEmToConfirmCancel(BgabGascem01Dto dto);
	String getSelectApprovalInfoByEm(BgabGascem01Dto rsReqDto);
	BgabGascem01Dto getSelectInfoEmToRequestForApprove(BgabGascem01Dto dto);
	List<BgabGascem01Dto> getSelectListEmToRequestForApprove(BgabGascem01Dto bgabGascem01Dto);

	int getSelectCountEmToList(BgabGascem01Dto bgabGascem01Dto);
	List<BgabGascem01Dto> getSelectListEmToList(BgabGascem01Dto bgabGascem01Dto);

	int getSelectCountEmToListForMeal(BgabGascem01Dto bgabGascem01Dto);
	List<BgabGascem01Dto> getSelectListEmToListForMeal(BgabGascem01Dto bgabGascem01Dto);

	int getSelectCountEmToListForEntrance(BgabGascem01Dto bgabGascem01Dto);
	List<BgabGascem01Dto> getSelectListEmToListForEntrance(BgabGascem01Dto bgabGascem01Dto);

	int getSelectCountEmToListForWorker(BgabGascem01Dto bgabGascem01Dto);
	List<BgabGascem01Dto> getSelectListEmToListForWorker(BgabGascem01Dto bgabGascem01Dto);






}
