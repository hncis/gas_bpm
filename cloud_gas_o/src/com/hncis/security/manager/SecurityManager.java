package com.hncis.security.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.security.vo.BgabGascse01;
import com.hncis.security.vo.BgabGascse02;
import com.hncis.security.vo.BgabGascse03;
import com.hncis.security.vo.BgabGascse04;
import com.hncis.security.vo.BgabGascse05;
import com.hncis.security.vo.BgabGascse06;

@Transactional
public interface SecurityManager {

	public BgabGascse01 insertSecurityRequestVehicle(List<BgabGascse01> paramList);
	public int deleteSecurityRequestVehicle(List<BgabGascse01> paramList);
	public List<BgabGascse01> getSelectSecurityRequestVehicle(BgabGascse01 param);

	public BgabGascse02 insertSecurityRequestMaterial(List<BgabGascse02> paramList);
	public int deleteSecurityRequestMaterial(List<BgabGascse02> paramList);
	public List<BgabGascse02> getSelectSecurityRequestMaterial(BgabGascse02 param);

	public BgabGascse03 insertSecurityRequestDevices(List<BgabGascse03> paramList);
	public int deleteSecurityRequestDevices(List<BgabGascse03> paramList);
	public List<BgabGascse03> getSelectSecurityRequestDevices(BgabGascse03 param);

	public BgabGascse04 insertSecurityRequestFilm(List<BgabGascse04> paramList);
	public int deleteSecurityRequestFilm(List<BgabGascse04> paramList);
	public List<BgabGascse04> getSelectSecurityRequestFilm(BgabGascse04 param);

	public String setSecurityApproval(BgabGascse01 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) throws SessionException;
	public CommonMessage setSecurityApprovalCancel(BgabGascse01 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	public Object updateRequestSecurityRequestVehicle(BgabGascse01 keyParamDto);
	public Object updateRequestSecurityRequestMaterial(BgabGascse02 keyParamDto);
	public BgabGascse01 getSelectSecurtyRequestType(BgabGascse01 param);
	public int getSelectCountSecurityRequestConfirmList(BgabGascse01 param);
	public List<BgabGascse01> getSelectSecurityRequestConfirmList(BgabGascse01 param);
	public int getSelectSecurityManagerMgmtListCount(BgabGascse05 param);
	public List<BgabGascse05> getSelectSecurityManagerMgmtList(BgabGascse05 param);

	public int getSelectSecurityManagerMgmtCheck(List<BgabGascse05> paramList);
	public int insertSecurityManagerMgmt(List<BgabGascse05> paramList);
	public int deleteSecurityManagerMgmt(List<BgabGascse05> paramList);

	public String getSelectSecurityApprovalInfo(CommonApproval apprVo);

	public List<BgabGascse05> selectFilmComboArea();

	public BgabGascse06 insertSecurityRequestEntrance(List<BgabGascse06> paramList);
	public int deleteSecurityRequestEntrance(List<BgabGascse06> paramList);
	public List<BgabGascse06> getSelectSecurityRequestEntrance(BgabGascse06 param);

	public BgabGascse01 getSelectSecurityRemark(BgabGascse01 param);
	public List<BgabGascZ011Dto> getSelectXVToFile(BgabGascZ011Dto btReqDto);
	public void saveXVToFile(HttpServletRequest req, HttpServletResponse res,BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteXVToFile(List<BgabGascZ011Dto> bt006Dto);
	public BgabGascZ011Dto getSelectXVToFileInfo(BgabGascZ011Dto btReqDto);
	public int updateInfoSecurityToReject(BgabGascse01 dto, HttpServletRequest req) throws SessionException;

	public int updateApproveSecurityEntrance(BgabGascse01 dto);

	public BgabGascse06 insertSecurityRequestEntranceCopy(List<BgabGascse06> paramList);
	public BgabGascse03 insertSecurityRequestDevicesCopy(List<BgabGascse03> paramList);
	public BgabGascse02 insertSecurityRequestMaterialCopy(List<BgabGascse02> paramList);
	public BgabGascse01 insertSecurityRequestVehicleCopy(List<BgabGascse01> paramList);
	public BgabGascse04 insertSecurityRequestFilmCopy(List<BgabGascse04> paramList);

	public int selectCountSecurityVistiorCustomer(BgabGascse01 param);
	public List<BgabGascse06> selectSecurityVistiorCustomer(BgabGascse01 param);

	public int getSelectCountSecurityRequestList(BgabGascse01 param);
	public List<BgabGascse01> getSelectSecurityRequestList(BgabGascse01 param);
}
