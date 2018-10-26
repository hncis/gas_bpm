package com.hncis.vehicleLog.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.vehicleLog.dao.VehicleLogDao;
import com.hncis.vehicleLog.vo.BgabGascvl01Dto;

public class VehicleLogDaoImplByIBatis extends CommonDao implements VehicleLogDao{

	private final String SELECT_COUNT_VL_TO_VEHICLE_LIST_FOR_DEPT 	= "selectCountVlToVehicleListForDept";
	private final String SELECT_LIST_VL_TO_VEHICLE_LIST_FOR_DEPT 	= "selectListVlToVehicleListForDept";

	//VehicleLog request page
	private final String INSERT_VL_TO_REQUEST 						= "insertVlToRequest";
	private final String SELECT_INFO_VL_TO_REQUEST 					= "selectInfoVlToRequest";
	private final String DELETE_VL_TO_REQUEST 						= "deleteVlToRequest";
	private final String UPDATE_INFO_VL_TO_CONFIRM 					= "updateInfoVlToConfirm";
	private final String UPDATE_INFO_VL_TO_CONFIRMCANCEL 			= "updateInfoVlToConfirmcancel";
	private final String UPDATE_INFO_VL_TO_APPROVAL 				= "updateInfoVlToApproval";
	private final String SELECT_APPROVAL_INFO_BY_BV 				= "selectApprovalInfoByVl";
	private final String SELECT_INFO_VL_TO_REQUEST_FOR_APPROVE 		= "selectInfoVlToRequestForApprove";

	private final String SELECT_COUNT_VL_TO_LIST 					= "selectCountVlToList";
	private final String SELECT_LIST_VL_TO_LIST 					= "selectListVlToList";

	private final String SELECT_COUNT_VL_TO_VEHICLE_MANAGEMENT 		= "selectCountVlToVehicleManagement";
	private final String INSERT_VL_TO_VEHICLE_MANAGEMENT 			= "insertVlToVehicleManagement";
	private final String SELECT_INFO_VL_TO_VEHICLE_MANAGEMENT 		= "selectInfoVlToVehicleManagement";
	private final String DELETE_VL_TO_VEHICLE_MANAGEMENT 			= "deleteVlToVehicleManagement";

	private final String SELECT_COUNT_VL_TO_VEHICLE_LIST 			= "selectCountVlToVehicleList";
	private final String SELECT_LIST_VL_TO_VEHICLE_LIST 			= "selectListVlToVehicleList";

	private final String SELECT_COUNT_VL_TO_CODE_MANAGEMENT 		= "selectCountVlToCodeManagement";
	private final String SELECT_LIST_VL_TO_CODE_MANAGEMENT 			= "selectListVlToCodeManagement";
	private final String INSERT_VL_TO_CODE_MANAGEMENT 				= "insertVlToCodeManagement";
	private final String DELETE_VL_TO_CODE_MANAGEMENT 				= "deleteVlToCodeManagement";

	private final String SELECT_COUNT_VL_TO_VEHICLE_REG_INFO 		= "selectCountVlToVehicleRegInfo";
	private final String SELECT_LIST_VL_TO_VEHICLE_REG_INFO 		= "selectListVlToVehicleRegInfo";

	private final String INSERT_VL_TO_FILE							= "insertVlToFile";
	private final String SELECT_VL_TO_FILE							= "selectVlToFile";
	private final String DELETE_VL_TO_FILE							= "deleteVlToFile";

	private final String INSERT_MAINTENANCE_TO_FILE					= "insertMaintenanceToFile";
	private final String SELECT_MAINTENANCE_TO_FILE					= "selectMaintenanceToFile";
	private final String DELETE_MAINTENANCE_TO_FILE					= "deleteMaintenanceToFile";

	private final String UPDATE_INFO_VL_TO_APPROVE_STATUS 			= "updateInfoVlToApproveStatus";

	private final String SELECT_COUNT_MAINTENANCE_EXPENSE_MANAGEMENT		= "selectCountMaintenanceExpenseManagement";
	private final String SELECT_MAINTENANCE_EXPENSE_MANAGEMENT				= "selectMaintenanceExpenseManagement";
	private final String INSERT_LIST_TO_MAINTENANCE_EXPENSE_MANAGEMENT		= "insertListToMaintenanceExpenseManagement";
	private final String UPDATE_LIST_TO_MAINTENANCE_EXPENSE_MANAGEMENT		= "updateListToMaintenanceExpenseManagement";
	private final String DELETE_LIST_TO_MAINTENANCE_EXPENSE_MANAGEMENT		= "deleteListToMaintenanceExpenseManagement";

	private final String SAVE_XVL01_INFO				= "saveXvl01Info";
	private final String SELECT_XVL01_INFO				= "selectXvl01Info";
	private final String SELECT_XVL01_INFO_BY_IF_ID		= "selectXvl01InfoByIfId";
	private final String DELETE_XVL01_INFO				= "deleteXvl01Info";
	private final String SELECT_XVL01_INFO_LIST_COUNT	= "selectXvl01InfoListCount";
	private final String SELECT_XVL01_INFO_LIST			= "selectXvl01InfoList";

	private final String SELECT_XVL02_INFO_LIST_COUNT	= "selectXvl02InfoListCount";
	private final String SELECT_XVL02_INFO_LIST			= "selectXvl02InfoList";

	private final String SELECT_XVL03_INFO_LIST_COUNT	= "selectXvl03InfoListCount";
	private final String SELECT_XVL03_INFO_LIST			= "selectXvl03InfoList";
	
	private final String APPROVE_CANCEL_VLTO_REQUEST	= "approveCancelVLToRequest";
	private final String CONFIRM_VL_TO_REQUEST			= "confirmVLToRequest";
	private final String UPDATE_INFO_VL_TO_REJECT		= "updateInfoVLToReject";

	@Override
	public int saveXvl01Info(BgabGascvl01Dto dto) {
		return super.update(SAVE_XVL01_INFO, dto);
	}

	@Override
	public BgabGascvl01Dto selectXvl01Info(BgabGascvl01Dto dto) {
		return (BgabGascvl01Dto)getSqlMapClientTemplate().queryForObject(SELECT_XVL01_INFO, dto);
	}
	
	@Override
	public BgabGascvl01Dto selectXvl01InfoByIfId(BgabGascvl01Dto dto) {
		return (BgabGascvl01Dto)getSqlMapClientTemplate().queryForObject(SELECT_XVL01_INFO_BY_IF_ID, dto);
	}

	@Override
	public int deleteXvl01Info(BgabGascvl01Dto dto) {
		return super.delete(DELETE_XVL01_INFO, dto);
	}

	@Override
	public int selectXvl01InfoListCount(BgabGascvl01Dto dto) {
		return (Integer)getSqlMapClientTemplate().queryForObject(SELECT_XVL01_INFO_LIST_COUNT, dto);
	}
	@Override
	public List<BgabGascvl01Dto> selectXvl01InfoList(BgabGascvl01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_XVL01_INFO_LIST, dto);
	}

	@Override
	public int selectXvl02InfoListCount(BgabGascvl01Dto dto) {
		return (Integer)getSqlMapClientTemplate().queryForObject(SELECT_XVL02_INFO_LIST_COUNT, dto);
	}
	@Override
	public List<BgabGascvl01Dto> selectXvl02InfoList(BgabGascvl01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_XVL02_INFO_LIST, dto);
	}

	@Override
	public int selectXvl03InfoListCount(BgabGascvl01Dto dto) {
		return (Integer)getSqlMapClientTemplate().queryForObject(SELECT_XVL03_INFO_LIST_COUNT, dto);
	}
	@Override
	public List<BgabGascvl01Dto> selectXvl03InfoList(BgabGascvl01Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_XVL03_INFO_LIST, dto);
	}
	
	@Override
	public int approveCancelVLToRequest(BgabGascvl01Dto dto) {
		return update(APPROVE_CANCEL_VLTO_REQUEST, dto);
	}
	
	@Override
	public int confirmVLToRequest(BgabGascvl01Dto dto) {
		return update(CONFIRM_VL_TO_REQUEST, dto);
	}
	
	@Override
	public int updateInfoVLToReject(BgabGascvl01Dto dto) {
		return update(UPDATE_INFO_VL_TO_REJECT, dto);
	}
}