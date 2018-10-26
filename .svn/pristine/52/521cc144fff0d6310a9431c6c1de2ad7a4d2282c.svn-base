package com.hncis.security.dao;

import java.util.List;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.security.vo.BgabGascse01;
import com.hncis.security.vo.BgabGascse02;
import com.hncis.security.vo.BgabGascse03;
import com.hncis.security.vo.BgabGascse04;
import com.hncis.security.vo.BgabGascse05;
import com.hncis.security.vo.BgabGascse06;

public interface SecurityDao {
	public int insertSecurityRequestMaster(BgabGascse01 param);
	public int updateSecurityRequestMaster(BgabGascse01 param);
	public int deleteSecurityRequestMaster(BgabGascse01 param);
	public int insertSecurityRequestVehicle(BgabGascse01 param);
	public int updateSecurityRequestVehicle(BgabGascse01 param);
	public int deleteSecurityRequestVehicle(List<BgabGascse01> paramList);
	public List<BgabGascse01> getSelectSecurityRequestVehicle(BgabGascse01 param);

	public int insertSecurityRequestMaterial(BgabGascse02 param);
	public int updateSecurityRequestMaterial(BgabGascse02 param);
	public int deleteSecurityRequestMaterial(List<BgabGascse02> paramList);
	public List<BgabGascse02> getSelectSecurityRequestMaterial(BgabGascse02 param);

	public int insertSecurityRequestDevices(BgabGascse03 param);
	public int updateSecurityRequestDevices(BgabGascse03 param);
	public int deleteSecurityRequestDevices(List<BgabGascse03> paramList);
	public List<BgabGascse03> getSelectSecurityRequestDevices(BgabGascse03 param);
	
	public int insertSecurityRequestFilm(BgabGascse04 param);
	public int updateSecurityRequestFilm(BgabGascse04 param);
	public int deleteSecurityRequestFilm(List<BgabGascse04> paramList);
	public List<BgabGascse04> getSelectSecurityRequestFilm(BgabGascse04 param);
	
	public Object updateRequestSecurityRequestVehicle(BgabGascse01 keyParamDto);
	public Object updateRequestSecurityRequestMaterial(BgabGascse02 keyParamDto);
	public BgabGascse01 getSelectSecurtyRequestType(BgabGascse01 vo);

	public int getSelectCountSecurityRequestConfirmList(BgabGascse01 param);
	public List<BgabGascse01> getSelectSecurityRequestConfirmList(BgabGascse01 param);
	
	public int getSelectSecurityManagerMgmtListCount(BgabGascse05 param);
	public List<BgabGascse05> getSelectSecurityManagerMgmtList(BgabGascse05 param);
	
	public String getSelectSecurityApprovalInfo(CommonApproval apprVo);
	
	public int getSelectSecurityManagerMgmtCheck(BgabGascse05 param);
	public int insertSecurityManagerMgmt(BgabGascse05 param);
	public int updateSecurityManagerMgmt(BgabGascse05 param);
	public int deleteSecurityManagerMgmt(List<BgabGascse05> param);
	
	public List<BgabGascse05> selectFilmComboArea();
	
	public int insertSecurityRequestEntrance(BgabGascse06 param);
	public int updateSecurityRequestEntrance(BgabGascse06 param);
	public int deleteSecurityRequestEntrance(List<BgabGascse06> paramList);
	public List<BgabGascse06> getSelectSecurityRequestEntrance(BgabGascse06 param);
	public BgabGascse01 getSelectSecurityRemark(BgabGascse01 param);
	
	public List<BgabGascZ011Dto> getSelectXVToFile(BgabGascZ011Dto btReqDto);
	public Integer insertXVToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public Integer deleteXVToFile(List<BgabGascZ011Dto> bgabGascZ011IList);
	public BgabGascZ011Dto getSelectXVToFileInfo(BgabGascZ011Dto btReqDto);
	public int updateInfoSecurityToReject(BgabGascse01 dto);
	
	public int updateApproveSecurityEntrance(BgabGascse01 dto);
	public int updateApproveCancelSecurityEntrance(BgabGascse01 dto);
	public int selectCountSecurityVistiorCustomer(BgabGascse01 param);
	public List<BgabGascse06> selectSecurityVistiorCustomer(BgabGascse01 param);
	
	public int getSelectCountSecurityRequestListType1(BgabGascse01 param);
	public int getSelectCountSecurityRequestListType2(BgabGascse01 param);
	public int getSelectCountSecurityRequestListType3(BgabGascse01 param);
	public int getSelectCountSecurityRequestListType4(BgabGascse01 param);
	public int getSelectCountSecurityRequestListType5(BgabGascse01 param);
	
	public List<BgabGascse01> getSelectSecurityRequestListType1(BgabGascse01 param);
	public List<BgabGascse01> getSelectSecurityRequestListType2(BgabGascse01 param);
	public List<BgabGascse01> getSelectSecurityRequestListType3(BgabGascse01 param);
	public List<BgabGascse01> getSelectSecurityRequestListType4(BgabGascse01 param);
}
