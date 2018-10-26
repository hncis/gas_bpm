package com.hncis.businessVehicles.dao.impl;

import java.util.List;

import com.hncis.businessTravel.vo.BgabGascbt06;
import com.hncis.businessVehicles.dao.BusinessVehiclesDao;
import com.hncis.businessVehicles.vo.BgabGascbv01Dto;
import com.hncis.businessVehicles.vo.BgabGascbv02Dto;
import com.hncis.businessVehicles.vo.BgabGascbv03Dto;
import com.hncis.common.dao.CommonDao;
import com.hncis.common.vo.BgabGascZ011Dto;

public class BusinessVehiclesDaoImplByIBatis extends CommonDao implements BusinessVehiclesDao{

	private final String SELECT_COUNT_BV_TO_VEHICLE_LIST_FOR_DEPT 	= "selectCountBvToVehicleListForDept";
	private final String SELECT_LIST_BV_TO_VEHICLE_LIST_FOR_DEPT 	= "selectListBvToVehicleListForDept";
	
	//BusinessVehicles request page
	private final String INSERT_BV_TO_REQUEST 						= "insertBvToRequest";
	private final String SELECT_INFO_BV_TO_REQUEST 					= "selectInfoBvToRequest";
	private final String DELETE_BV_TO_REQUEST 						= "deleteBvToRequest";
	private final String UPDATE_INFO_BV_TO_CONFIRM 					= "updateInfoBvToConfirm";
	private final String UPDATE_INFO_BV_TO_CONFIRMCANCEL 			= "updateInfoBvToConfirmcancel";
	private final String UPDATE_INFO_BV_TO_APPROVAL 				= "updateInfoBvToApproval";
	private final String SELECT_APPROVAL_INFO_BY_BV 				= "selectApprovalInfoByBv";
	private final String SELECT_INFO_BV_TO_REQUEST_FOR_APPROVE 		= "selectInfoBvToRequestForApprove";
	
	private final String SELECT_COUNT_BV_TO_LIST 					= "selectCountBvToList";
	private final String SELECT_LIST_BV_TO_LIST 					= "selectListBvToList";
	
	private final String SELECT_COUNT_BV_TO_VEHICLE_MANAGEMENT 		= "selectCountBvToVehicleManagement";
	private final String INSERT_BV_TO_VEHICLE_MANAGEMENT 			= "insertBvToVehicleManagement";
	private final String SELECT_INFO_BV_TO_VEHICLE_MANAGEMENT 		= "selectInfoBvToVehicleManagement";
	private final String DELETE_BV_TO_VEHICLE_MANAGEMENT 			= "deleteBvToVehicleManagement";
	
	private final String SELECT_COUNT_BV_TO_VEHICLE_LIST 			= "selectCountBvToVehicleList";
	private final String SELECT_LIST_BV_TO_VEHICLE_LIST 			= "selectListBvToVehicleList";
	
	private final String SELECT_COUNT_BV_TO_CODE_MANAGEMENT 		= "selectCountBvToCodeManagement";
	private final String SELECT_LIST_BV_TO_CODE_MANAGEMENT 			= "selectListBvToCodeManagement";
	private final String INSERT_BV_TO_CODE_MANAGEMENT 				= "insertBvToCodeManagement";
	private final String DELETE_BV_TO_CODE_MANAGEMENT 				= "deleteBvToCodeManagement";
	
	private final String SELECT_COUNT_BV_TO_VEHICLE_REG_INFO 		= "selectCountBvToVehicleRegInfo";
	private final String SELECT_LIST_BV_TO_VEHICLE_REG_INFO 		= "selectListBvToVehicleRegInfo";
	
	private final String INSERT_BV_TO_FILE							= "insertBvToFile";
	private final String SELECT_BV_TO_FILE							= "selectBvToFile";
	private final String DELETE_BV_TO_FILE							= "deleteBvToFile";
	
	private final String UPDATE_INFO_BV_TO_REJECT					= "updateInfoBvToReject";
	
	private final String INSERT_MAINTENANCE_TO_FILE					= "insertMaintenanceToFile";
	private final String SELECT_MAINTENANCE_TO_FILE					= "selectMaintenanceToFile";
	private final String DELETE_MAINTENANCE_TO_FILE					= "deleteMaintenanceToFile";

	private final String UPDATE_INFO_BV_TO_APPROVE_STATUS 			= "updateInfoBvToApproveStatus";
	
	private final String SELECT_COUNT_MAINTENANCE_EXPENSE_MANAGEMENT		= "selectCountMaintenanceExpenseManagement";
	private final String SELECT_MAINTENANCE_EXPENSE_MANAGEMENT				= "selectMaintenanceExpenseManagement";
	private final String INSERT_LIST_TO_MAINTENANCE_EXPENSE_MANAGEMENT		= "insertListToMaintenanceExpenseManagement";
	private final String UPDATE_LIST_TO_MAINTENANCE_EXPENSE_MANAGEMENT		= "updateListToMaintenanceExpenseManagement";
	private final String DELETE_LIST_TO_MAINTENANCE_EXPENSE_MANAGEMENT		= "deleteListToMaintenanceExpenseManagement";
	
	
	/*************************************************************/
	/** vehicle list for dept page                  	   			                **/
	/*************************************************************/
	@Override
	public int getSelectCountBvToVehicleListForDept(BgabGascbv01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BV_TO_VEHICLE_LIST_FOR_DEPT, dto));
	}

	@Override
	public List<BgabGascbv01Dto> getSelectListBvToVehicleListForDept(BgabGascbv01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_BV_TO_VEHICLE_LIST_FOR_DEPT, dto);
	}
	
	/*************************************************************/
	/** BusinessVehicles request page                              **/
	/*************************************************************/
	@Override
	public int insertBvToRequest(BgabGascbv02Dto dto) {
		return super.insert(INSERT_BV_TO_REQUEST, dto);
	}

	@Override
	public BgabGascbv02Dto getSelectInfoBvToRequest(BgabGascbv02Dto dto) {
		return (BgabGascbv02Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_BV_TO_REQUEST, dto);
	}
	@Override
	public int deleteBvToRequest(BgabGascbv02Dto dto) {
		return super.delete(DELETE_BV_TO_REQUEST, dto);
	}

	@Override
	public int updateInfoBvToConfirm(BgabGascbv02Dto dto) {
		return super.update(UPDATE_INFO_BV_TO_CONFIRM, dto);
	}

	@Override
	public int updateInfoBvToConfirmCancel(BgabGascbv02Dto dto) {
		return super.update(UPDATE_INFO_BV_TO_CONFIRMCANCEL, dto);
	}
	@Override
	public int updateInfoBvToApproval(BgabGascbv02Dto dto) {
		return super.update(UPDATE_INFO_BV_TO_APPROVAL, dto);
	}
	@Override
	public String getSelectApprovalInfoByBv(BgabGascbv02Dto rsReqDto) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_INFO_BY_BV, rsReqDto);
	}
	@Override
	public BgabGascbv02Dto getSelectInfoBvToRequestForApprove(BgabGascbv02Dto dto) {
		return (BgabGascbv02Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_BV_TO_REQUEST_FOR_APPROVE, dto);
	}
	/*************************************************************/
	/** list page                  	   			                **/
	/*************************************************************/
	@Override
	public int getSelectCountBvToList(BgabGascbv02Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BV_TO_LIST, dto));
	}

	@Override
	public List<BgabGascbv02Dto> getSelectListBvToList(BgabGascbv02Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_BV_TO_LIST, dto);
	}
	
	/*************************************************************/
	/** Vehicle Management page                  	   	        **/
	/*************************************************************/
	@Override
	public int getSelectBvToCheckVehicle(BgabGascbv01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BV_TO_VEHICLE_MANAGEMENT, dto));
	}
	@Override
	public int insertBvToVehicleManagement(BgabGascbv01Dto dto) {
		return super.update(INSERT_BV_TO_VEHICLE_MANAGEMENT, dto);
	}
	
	@Override
	public BgabGascbv01Dto getSelectInfoBvToVehicleManagement(BgabGascbv01Dto dto) {
		return (BgabGascbv01Dto)getSqlMapClientTemplate().queryForObject(SELECT_INFO_BV_TO_VEHICLE_MANAGEMENT, dto);
	}

	@Override
	public int deleteBvToVehicleManagement(BgabGascbv01Dto dto) {
		return super.update(DELETE_BV_TO_VEHICLE_MANAGEMENT, dto);
	}
	
	/*************************************************************/
	/** vehicle list page                  	   			                **/
	/*************************************************************/
	@Override
	public int getSelectCountBvToVehicleList(BgabGascbv01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BV_TO_VEHICLE_LIST, dto));
	}

	@Override
	public List<BgabGascbv01Dto> getSelectListBvToVehicleList(BgabGascbv01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_BV_TO_VEHICLE_LIST, dto);
	}
	
	/*************************************************************/
	/** code managerment page                  	                **/
	/*************************************************************/
	@Override
	public int getSelectCountBvToCodeManagement(BgabGascbv03Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BV_TO_CODE_MANAGEMENT, dto));
	}

	@Override
	public List<BgabGascbv03Dto> getSelectListBvToCodeManagement(BgabGascbv03Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_BV_TO_CODE_MANAGEMENT, dto);
	}

	@Override
	public int insertBvToCodeManagement(List<BgabGascbv03Dto> dto) {
		return super.insert(INSERT_BV_TO_CODE_MANAGEMENT, dto);
	}

	@Override
	public int deleteBvToCodeManagement(List<BgabGascbv03Dto> dto) {
		return super.delete(DELETE_BV_TO_CODE_MANAGEMENT, dto);
	}

	/*************************************************************/
	/** Vehicle registration info list page                     **/
	/*************************************************************/
	@Override
	public int getSelectCountBvToVehicleRegInfo(BgabGascbv01Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BV_TO_VEHICLE_REG_INFO, dto));
	}

	@Override
	public List<BgabGascbv01Dto> getSelectListBvToVehicleRegInfo(BgabGascbv01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_BV_TO_VEHICLE_REG_INFO, dto);
	}

	public int insertBvToFile(BgabGascZ011Dto dto){
		return super.insert(INSERT_BV_TO_FILE, dto);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascZ011Dto> getSelectBvToFile(BgabGascZ011Dto dto){
		return getSqlMapClientTemplate().queryForList(SELECT_BV_TO_FILE, dto);
	}
	
	public BgabGascZ011Dto getSelectBvToFileInfo(BgabGascZ011Dto dto){
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject(SELECT_BV_TO_FILE, dto);
	}
	
	public int deleteBvToFile (List<BgabGascZ011Dto> list){
		return super.delete(DELETE_BV_TO_FILE, list);
	}
	
	public int updateInfoBvToReject (BgabGascbv02Dto dto){
		return update(UPDATE_INFO_BV_TO_REJECT, dto);
	}
	
	public int insertMaintenanceToFile (BgabGascZ011Dto bgabGascZ011Dto){
		return super.insert(INSERT_MAINTENANCE_TO_FILE, bgabGascZ011Dto);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascZ011Dto> getSelectMaintenanceToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return getSqlMapClientTemplate().queryForList(SELECT_MAINTENANCE_TO_FILE, bgabGascZ011Dto);
	}
	
	public BgabGascZ011Dto getSelectMaintenanceToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject(SELECT_MAINTENANCE_TO_FILE, bgabGascZ011Dto);
	}
	
	public int deleteMaintenanceToFile (List<BgabGascZ011Dto> bgabGascZ011List){
		return super.delete(DELETE_MAINTENANCE_TO_FILE, bgabGascZ011List);
	}
	public int updateInfoBvToApproveStatus(BgabGascbv02Dto dto){
		return update(UPDATE_INFO_BV_TO_APPROVE_STATUS, dto);
	}
	
	public String getSelectCountMaintenanceExpenseManagement(BgabGascbt06 bgabGascbt06){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_MAINTENANCE_EXPENSE_MANAGEMENT, bgabGascbt06);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascbt06> getSelectMaintenanceExpenseManagement(BgabGascbt06 bgabGascbt06){
		return getSqlMapClientTemplate().queryForList(SELECT_MAINTENANCE_EXPENSE_MANAGEMENT, bgabGascbt06);
	}
	
	public int insertListToMaintenanceExpenseManagement (List<BgabGascbt06> bgabGascbt06){
		return super.insert(INSERT_LIST_TO_MAINTENANCE_EXPENSE_MANAGEMENT, bgabGascbt06);
	}
	
	public int updateListToMaintenanceExpenseManagement (List<BgabGascbt06> bgabGascbt06){
		return super.update(UPDATE_LIST_TO_MAINTENANCE_EXPENSE_MANAGEMENT, bgabGascbt06);
	}
	
	public int deleteListToMaintenanceExpenseManagement (List<BgabGascbt06> bgabGascbt06){
		return super.update(DELETE_LIST_TO_MAINTENANCE_EXPENSE_MANAGEMENT, bgabGascbt06);
	}
}
