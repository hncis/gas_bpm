package com.hncis.security.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.security.dao.SecurityDao;
import com.hncis.security.vo.BgabGascse01;
import com.hncis.security.vo.BgabGascse02;
import com.hncis.security.vo.BgabGascse03;
import com.hncis.security.vo.BgabGascse04;
import com.hncis.security.vo.BgabGascse05;
import com.hncis.security.vo.BgabGascse06;

public class SecurityDaoImplByIBatis extends CommonDao implements SecurityDao{
	private final String INSERT_SECURITY_REQUEST_MASTER  = "insertSecurityRequestMaster";
	private final String UPDATE_SECURITY_REQUEST_MASTER  = "updateSecurityRequestMaster";
	private final String DELETE_SECURITY_REQUEST_MASTER  = "deleteSecurityRequestMaster";
	private final String INSERT_SECURITY_REQUEST_VEHICLE  = "insertSecurityRequestVehicle";
	private final String UPDATE_SECURITY_REQUEST_VEHICLE  = "updateSecurityRequestVehicle";
	private final String DELETE_SECURITY_REQUEST_VEHICLE  = "deleteSecurityRequestVehicle";
	private final String SELECT_SECURITY_REQUEST_VEHICLE  = "selectSecurityRequestVehicle";
	private final String INSERT_SECURITY_REQUEST_MATERIAL  = "insertSecurityRequestMaterial";
	private final String UPDATE_SECURITY_REQUEST_MATERIAL= "updateSecurityRequestMaterial";
	private final String DELETE_SECURITY_REQUEST_MATERIAL= "deleteSecurityRequestMaterial";
	private final String SELECT_SECURITY_REQUEST_MATERIAL= "selectSecurityRequestMaterial";
	private final String INSERT_SECURITY_REQUEST_DEVICES  = "insertSecurityRequestDevices";
	private final String UPDATE_SECURITY_REQUEST_DEVICES= "updateSecurityRequestDevices";
	private final String DELETE_SECURITY_REQUEST_DEVICES= "deleteSecurityRequestDevices";
	private final String SELECT_SECURITY_REQUEST_DEVICES= "selectSecurityRequestDevices";
	private final String INSERT_SECURITY_REQUEST_FILM  = "insertSecurityRequestFilm";
	private final String UPDATE_SECURITY_REQUEST_FILM= "updateSecurityRequestFilm";
	private final String DELETE_SECURITY_REQUEST_FILM= "deleteSecurityRequestFilm";
	private final String SELECT_SECURITY_REQUEST_FILM= "selectSecurityRequestFilm";
	private final String UPDATE_REQUEST_SECURITY_REQUEST_VEHICLE= "updateRequestSecurityRequestVehicle";
	private final String UPDATE_REQUEST_SECURITY_REQUEST_MATERIAL= "updateRequestSecurityRequestMaterial";
	private final String SELECT_SECURITY_REQUEST_TYPE= "selectSecurtyRequestType";
	private final String SELECT_COUNT_SECURITY_REQUEST_CONFIRM_LIST= "selectCountSecurityRequestConfirmList";
	private final String SELECT_SECURITY_REQUEST_CONFIRM_LIST= "selectSecurityRequestConfirmList";
	private final String SELECT_SECURITY_APPROVAL_INFO= "selectSecurityApprovalInfo";
	private final String SELECT_SECURITY_MANAGER_MGMT_LIST_COUNT= "selectSecurityManagerMgmtListCount";
	private final String SELECT_SECURITY_MANAGER_MGMT_LIST= "selectSecurityManagerMgmtList";
	private final String INSERT_SECURITY_MANAGER_MGMT= "insertSecurityManagerMgmt";
	private final String UPDATE_SECURITY_MANAGER_MGMT= "updateSecurityManagerMgmt";
	private final String DELETE_SECURITY_MANAGER_MGMT= "deleteSecurityManagerMgmt";
	private final String SELECT_FILM_COMBO_AREA= "selectFilmComboArea";
	private final String SELECT_SECURITY_MANAGER_MGMT_CHECK= "selectSecurityManagerMgmtCheck";
	private final String INSERT_SECURITY_REQUEST_ENTRANCE  = "insertSecurityRequestEntrance";
	private final String UPDATE_SECURITY_REQUEST_ENTRANCE= "updateSecurityRequestEntrance";
	private final String DELETE_SECURITY_REQUEST_ENTRANCE= "deleteSecurityRequestEntrance";
	private final String SELECT_SECURITY_REQUEST_ENTRANCE= "selectSecurityRequestEntrance";
	private final String SELECT_SECURITY_REMARK= "selectSecurityRemark";
	private final String UPDATE_INFO_SECURITY_TO_REJECT= "updateInfoSecurityToReject";

	private final String UPDATE_APPROVE_SECURITY_ENTRANCE = "updateApproveSecurityEntrance";

	private final String SELECT_COUNT_SECURITY_VISTIOR_CUSTOMER = "selectCountSecurityVistiorCustomer";
	private final String SELECT_SECURITY_VISTIOR_CUSTOMER 		= "selectSecurityVistiorCustomer";
	private static final String uncheck = "unchecked";

	@Override
	public int insertSecurityRequestMaster(BgabGascse01 param){
		return super.insert(INSERT_SECURITY_REQUEST_MASTER, param);
	}

	@Override
	public int updateSecurityRequestMaster(BgabGascse01 param){
		return super.update(UPDATE_SECURITY_REQUEST_MASTER, param);
	}

	@Override
	public int deleteSecurityRequestMaster(BgabGascse01 param){
		return super.delete(DELETE_SECURITY_REQUEST_MASTER, param);
	}

	@Override
	public int insertSecurityRequestVehicle(BgabGascse01 param){
		return super.insert(INSERT_SECURITY_REQUEST_VEHICLE, param);
	}

	@Override
	public int updateSecurityRequestVehicle(BgabGascse01 param){
		return super.update(UPDATE_SECURITY_REQUEST_VEHICLE, param);
	}

	@Override
	public int deleteSecurityRequestVehicle(List<BgabGascse01> list){
		return super.delete(DELETE_SECURITY_REQUEST_VEHICLE, list);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascse01> getSelectSecurityRequestVehicle(BgabGascse01 param){
		return  getSqlMapClientTemplate().queryForList(SELECT_SECURITY_REQUEST_VEHICLE, param);
	}

	@Override
	public int insertSecurityRequestMaterial(BgabGascse02 param){
		return super.insert(INSERT_SECURITY_REQUEST_MATERIAL, param);
	}

	@Override
	public int updateSecurityRequestMaterial(BgabGascse02 param){
		return super.update(UPDATE_SECURITY_REQUEST_MATERIAL, param);
	}

	@Override
	public int deleteSecurityRequestMaterial(List<BgabGascse02> list){
		return super.delete(DELETE_SECURITY_REQUEST_MATERIAL, list);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascse02> getSelectSecurityRequestMaterial(BgabGascse02 param){
		return  getSqlMapClientTemplate().queryForList(SELECT_SECURITY_REQUEST_MATERIAL, param);
	}

	@Override
	public int insertSecurityRequestDevices(BgabGascse03 param){
		return super.insert(INSERT_SECURITY_REQUEST_DEVICES, param);
	}

	@Override
	public int updateSecurityRequestDevices(BgabGascse03 param){
		return super.update(UPDATE_SECURITY_REQUEST_DEVICES, param);
	}

	@Override
	public int deleteSecurityRequestDevices(List<BgabGascse03> list){
		return super.delete(DELETE_SECURITY_REQUEST_DEVICES, list);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascse03> getSelectSecurityRequestDevices(BgabGascse03 param){
		return  getSqlMapClientTemplate().queryForList(SELECT_SECURITY_REQUEST_DEVICES, param);
	}


	@Override
	public int insertSecurityRequestFilm(BgabGascse04 param){
		return super.insert(INSERT_SECURITY_REQUEST_FILM, param);
	}

	@Override
	public int updateSecurityRequestFilm(BgabGascse04 param){
		return super.update(UPDATE_SECURITY_REQUEST_FILM, param);
	}

	@Override
	public int deleteSecurityRequestFilm(List<BgabGascse04> list){
		return super.delete(DELETE_SECURITY_REQUEST_FILM, list);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascse04> getSelectSecurityRequestFilm(BgabGascse04 param){
		return  getSqlMapClientTemplate().queryForList(SELECT_SECURITY_REQUEST_FILM, param);
	}

	@Override
	public Object updateRequestSecurityRequestVehicle(BgabGascse01 keyParamDto){
		return super.update(UPDATE_REQUEST_SECURITY_REQUEST_VEHICLE, keyParamDto);
	}

	@Override
	public Object updateRequestSecurityRequestMaterial(BgabGascse02 keyParamDto){
		return super.update(UPDATE_REQUEST_SECURITY_REQUEST_MATERIAL, keyParamDto);
	}

	@Override
	public BgabGascse01 getSelectSecurtyRequestType(BgabGascse01 vo){
		return (BgabGascse01)getSqlMapClientTemplate().queryForObject(SELECT_SECURITY_REQUEST_TYPE, vo);
	}

	@Override
	public int getSelectCountSecurityRequestConfirmList(BgabGascse01 param){
		return  Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_SECURITY_REQUEST_CONFIRM_LIST, param));
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascse01> getSelectSecurityRequestConfirmList(BgabGascse01 param){
		return  getSqlMapClientTemplate().queryForList(SELECT_SECURITY_REQUEST_CONFIRM_LIST, param);
	}

	@Override
	public String getSelectSecurityApprovalInfo(CommonApproval apprVo){
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("if_id", apprVo.getIf_id());
		paramMap.put("system_code", apprVo.getSystem_code());
		paramMap.put("code1", apprVo.getCode1());
		paramMap.put("corp_cd", apprVo.getCorp_cd());

		HashMap<String, String> rtnMap = (HashMap<String, String>)getSqlMapClientTemplate().queryForObject(SELECT_SECURITY_APPROVAL_INFO, paramMap);

		if(rtnMap != null){
			return rtnMap.get("ResultVal").toString();
		}

		return "";
	}

	@Override
	public int getSelectSecurityManagerMgmtListCount(BgabGascse05 param){
		return  Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_SECURITY_MANAGER_MGMT_LIST_COUNT, param));
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascse05> getSelectSecurityManagerMgmtList(BgabGascse05 param){
		return  getSqlMapClientTemplate().queryForList(SELECT_SECURITY_MANAGER_MGMT_LIST, param);
	}

	@Override
	public int getSelectSecurityManagerMgmtCheck(BgabGascse05 param){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_SECURITY_MANAGER_MGMT_CHECK, param));
	}

	@Override
	public int insertSecurityManagerMgmt(BgabGascse05 param){
		return super.insert(INSERT_SECURITY_MANAGER_MGMT, param);
	}

	@Override
	public int updateSecurityManagerMgmt(BgabGascse05 param){
		return super.update(UPDATE_SECURITY_MANAGER_MGMT, param);
	}

	@Override
	public int deleteSecurityManagerMgmt(List<BgabGascse05> param){
		return super.delete(DELETE_SECURITY_MANAGER_MGMT, param);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascse05> selectFilmComboArea(){
		return getSqlMapClientTemplate().queryForList(SELECT_FILM_COMBO_AREA);
	}

	@Override
	public int insertSecurityRequestEntrance(BgabGascse06 param){
		return super.insert(INSERT_SECURITY_REQUEST_ENTRANCE, param);
	}

	@Override
	public int updateSecurityRequestEntrance(BgabGascse06 param){
		return super.update(UPDATE_SECURITY_REQUEST_ENTRANCE, param);
	}

	@Override
	public int deleteSecurityRequestEntrance(List<BgabGascse06> list){
		return super.delete(DELETE_SECURITY_REQUEST_ENTRANCE, list);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascse06> getSelectSecurityRequestEntrance(BgabGascse06 param){
		return  getSqlMapClientTemplate().queryForList(SELECT_SECURITY_REQUEST_ENTRANCE, param);
	}

	@Override
	public BgabGascse01 getSelectSecurityRemark(BgabGascse01 param){
		return (BgabGascse01)getSqlMapClientTemplate().queryForObject(SELECT_SECURITY_REMARK, param);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascZ011Dto> getSelectXVToFile(BgabGascZ011Dto btReqDto){
		return getSqlMapClientTemplate().queryForList("selectXVToFile", btReqDto);
	}

	@Override
	public Integer insertXVToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return super.insert("insertXVToFile", bgabGascZ011Dto);
	}

	@Override
	public Integer deleteXVToFile(List<BgabGascZ011Dto> bgabGascZ011IList){
		return super.delete("deleteXVToFile", bgabGascZ011IList);
	}

	@Override
	public BgabGascZ011Dto getSelectXVToFileInfo(BgabGascZ011Dto btReqDto){
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject("selectXVToFile", btReqDto);
	}

	@Override
	public int updateInfoSecurityToReject(BgabGascse01 dto){
		return super.update(UPDATE_INFO_SECURITY_TO_REJECT, dto);
	}

	@Override
	public int updateApproveSecurityEntrance(BgabGascse01 dto){
		return update(UPDATE_APPROVE_SECURITY_ENTRANCE, dto);
	}
	
	@Override
	public int updateApproveCancelSecurityEntrance(BgabGascse01 dto){
		return update("updateApproveCancelSecurityEntrance", dto);
	}

	@Override
	public int selectCountSecurityVistiorCustomer(BgabGascse01 param) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_SECURITY_VISTIOR_CUSTOMER, param));
	}

	@Override
	public List<BgabGascse06> selectSecurityVistiorCustomer(BgabGascse01 param) {
		return  getSqlMapClientTemplate().queryForList(SELECT_SECURITY_VISTIOR_CUSTOMER, param);
	}

	@Override
	public int getSelectCountSecurityRequestListType1(BgabGascse01 param) {
		return  Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectCountSecurityRequestListType1", param));
	}

	@Override
	public int getSelectCountSecurityRequestListType2(BgabGascse01 param) {
		return  Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectCountSecurityRequestListType2", param));
	}

	@Override
	public int getSelectCountSecurityRequestListType3(BgabGascse01 param) {
		return  Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectCountSecurityRequestListType3", param));
	}

	@Override
	public int getSelectCountSecurityRequestListType4(BgabGascse01 param) {
		return  Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectCountSecurityRequestListType4", param));
	}

	@Override
	public int getSelectCountSecurityRequestListType5(BgabGascse01 param) {
		return  Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectCountSecurityRequestListType5", param));
	}

	@Override
	public List<BgabGascse01> getSelectSecurityRequestListType1(
			BgabGascse01 param) {
		return getSqlMapClientTemplate().queryForList("getSelectSecurityRequestListType1", param);
	}

	@Override
	public List<BgabGascse01> getSelectSecurityRequestListType2(
			BgabGascse01 param) {
		return getSqlMapClientTemplate().queryForList("getSelectSecurityRequestListType2", param);
	}

	@Override
	public List<BgabGascse01> getSelectSecurityRequestListType3(
			BgabGascse01 param) {
		return getSqlMapClientTemplate().queryForList("getSelectSecurityRequestListType3", param);
	}

	@Override
	public List<BgabGascse01> getSelectSecurityRequestListType4(
			BgabGascse01 param) {
		return getSqlMapClientTemplate().queryForList("getSelectSecurityRequestListType4", param);
	}
}
