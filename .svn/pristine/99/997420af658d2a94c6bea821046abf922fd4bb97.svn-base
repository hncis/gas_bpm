package com.hncis.system.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.common.vo.BgabGascz005Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonCode;
import com.hncis.system.dao.SystemDao;
import com.hncis.system.vo.BatchInfo;
import com.hncis.system.vo.BatchProcess;
import com.hncis.system.vo.BgabGasckcalDto;
import com.hncis.system.vo.BgabGascz004Dto;
import com.hncis.system.vo.BgabGascz006Dto;
import com.hncis.system.vo.BgabGascz007Dto;
import com.hncis.system.vo.BgabGascz008Dto;
import com.hncis.system.vo.BgabGascz013Dto;
import com.hncis.system.vo.BgabGascz014Dto;
import com.hncis.system.vo.BgabGascz015Dto;
import com.hncis.system.vo.BgabGascz016Dto;
import com.hncis.system.vo.BgabGascz019Dto;
import com.hncis.system.vo.BgabGascz030Dto;
import com.hncis.system.vo.BgabGascz031Dto;
import com.hncis.system.vo.BgabGascz033Dto;
import com.hncis.system.vo.BgabGascz035Dto;
import com.hncis.system.vo.DashBoard;
import com.hncis.system.vo.TableInfo;

public class SystemDaoImplByIBatis extends CommonDao implements SystemDao{

	private final String SELECT_MENU_LIST_TO_USER_MANAGEMENT 		= "selectMenuListToUserManagement";
	private final String UPDATE_USERINFO_TO_USER_MANAGEMENT 		= "updateUserInfoToUserManagement";
	private final String SELECT_MENU_LIST_TO_MENU_MANAGEMENT 		= "selectMenuListToMenuManagement";
	private final String SELECT_MENU_COUNT_TO_MENU_MANAGEMENT 		= "selectMenuCountToMenuManagement";
	private final String INSERT_LIST_TO_MENU_MANAGEMENT 			= "insertListToMenuManagement";
	private final String UPDATE_LIST_TO_MENU_MANAGEMENT 			= "updateListToMenuManagement";
	private final String DELETE_LIST_TO_MENU_MANAGEMENT 			= "deleteListToMenuManagement";
	private final String SELECT_TABLE_LIST_TO_TABLE_INFORMATION 	= "selectTableListToTableInformation";
	private final String SELECT_ATTRIBUTE_LIST_TO_TABLE_INFORMATION = "selectAttributeListToTableInformation";
	private final String SELECT_INDEX_LIST_TO_TABLE_INFORMATION 	= "selectIndexListToTableInformation";
	private final String SELECT_LIST_TO_BATCH_MANAGEMENT 			= "selectListToBatchManagement";
	private final String SELECT_PARAMETER_LIST_TO_BATCH_MANAGEMENT	= "selectParameterListToBatchManagement";
	private final String SELECT_COUNT_TO_DEPT_CHANGE_MANAGEMENT 	= "selectCountToDeptChangeManagement";
	private final String SELECT_LIST_TO_DEPT_CHANGE_MANAGEMENT 		= "selectListToDeptChangeManagement";
	private final String INSERT_TO_DEPT_CHANGE_MANAGEMENT			= "insertToDeptChangeManagement";
	private final String UPDATE_TO_DEPT_CHANGE_MANAGEMENT			= "updateToDeptChangeManagement";
	private final String UPDATE_TO_INSADEPT_CHANGE_MANAGEMENT		= "updateToInsaDeptChangeManagement";
	private final String DELETE_TO_DEPT_CHANGE_MANAGEMENT			= "deleteToDeptChangeManagement";
	private final String UPDATE_BATCHINFO_TO_BATCH_MANAGEMENT		= "updateBatchInfoToBatchManagement";
	private final String UPDATE_PARAMETER_TO_BATCH_MANAGEMENT		= "updateParameterToBatchManagement";
	private final String UPDATE_BATCHINFO_TO_BATCH_MANAGEMENT_BY_EXECUTE	= "updateBatchInfoToBatchManagementByExecute";

	private final String SELECT_COUNT_TO_MANAGER_MANAGEMENT 		= "selectCountToManagerManagement";
	private final String SELECT_LIST_TO_MANAGER_MANAGEMENT 			= "selectListToManagerManagement";
	private final String SELECT_MENU_TO_COMBO_LIST 					= "selectMenuToComboList";
	private final String SELECT_COUNT_TO_BATCH_PROCESS_RESULT 		= "selectCountToBatchProcessResult";
	private final String SELECT_LIST_TO_BATCH_PROCESS_RESULT 		= "selectListToBatchProcessResult";
	private final String INSERT_TO_MANAGER_MANAGEMENT 				= "insertToManagerManagement";
	private final String SELECT_INFO_TO_MANAGER_MANAGEMENT_AUTH 	= "selectInfoToManagerManagementAuth";
	private final String SELECT_INFO_TO_MANAGER_MANAGEMENT_MENU_MGRP_CD = "selectInfoManagerManagementMenuMgrpCd";
	private final String UPDATE_TO_MANAGER_MANAGEMENT_XUSR_AUTH		= "updateToManagerManagementXusrAuth";
	private final String UPDATE_TO_MANAGER_MANAGEMENT 				= "updateToManagerManagement";
	private final String DELETE_TO_MANAGER_MANAGEMENT 				= "deleteToManagerManagement";
	private final String SELECT_LIST_TO_CALENDAR_MANAGEMENT 		= "selectListToCalendarManagement";
	private final String SELECT_COUNT_TO_CALENDAR_MANAGEMENT 		= "selectCountToCalendarManagement";
	private final String INSERT_TO_CALENDAR_MANAGEMENT 				= "insertToCalendarManagement";
	private final String UPDATE_TO_CALENDAR_MANAGEMENT 				= "updateToCalendarManagement";
	private final String SELECT_COUNT_TO_MY_APPROVAL 				= "selectCountToMyApproval";
	private final String SELECT_LIST_TO_MY_APPROVAL 				= "selectListToMyApproval";
	private final String UPDATE_TO_MY_APPROVAL_FOR_DEPUTE			= "updateToMyApprovalForDepute";
	private final String UPDATE_TO_MY_APPROVAL_FOR_DEPUTE_BY_APPROVAL_INFO = "updateToMyApprovalForDeputeByApprovalInfo";
	private final String UPDATE_TO_MY_APPROVAL_FOR_DEPUTE_BY_APPROVAL_INFO_DETAIL = "updateToMyApprovalForDeputeByApprovalInfoDetail";
	private final String UPDATE_TO_MY_APPROVAL_FOR_DEPUTE_BY_RESTORE	= "updateToMyApprovalForDeputeByRestore";
	private final String SELECT_TO_MY_APPROVAL_FOR_DEPUTE			= "selectToMyApprovalForDepute";
	private final String SELECT_TO_MY_APPROVAL_USER_INFO			= "selectToMyApprovalUserInfo";
	private final String SELECT_COUNT_READER_MANAGEMENT				= "selectCountReaderManagement";
	private final String SELECT_READER_MANAGEMENT					= "selectReaderManagement";
	private final String UPDATE_READER_MANAGEMENT					= "updateReaderManagement";
	private final String SELECT_COUNT_CODE_MANAGEMENT_BY_SYSTEM		= "selectCountCodeManagementBySystem";
	private final String SELECT_CODE_MANAGEMENT_BY_SYSTEM			= "selectCodeManagementBySystem";
	private final String INSERT_LIST_TO_CODE_MANAGEMENT				= "insertListToCodeManagementBySystem";
	private final String UPDATE_LIST_TO_CODE_MANAGEMENT				= "updateListToCodeManagementBySystem";
	private final String DELETE_LIST_TO_CODE_MANAGEMENT				= "deleteListToCodeManagementBySystem";
	private final String SELECT_BACKGROUND_IMAGE					= "selectBackgroundImage";
	private final String UPDATE_TO_BACKGROUND_IMAGE					= "updateToBackgroundImage";
	private final String UPDATE_TO_BACKGROUND_IMAGE_APPLY_Y			= "updateToBackgroundImageApplyY";
	private final String UPDATE_TO_BACKGROUND_IMAGE_APPLY_N			= "updateToBackgroundImageApplyN";
	private final String SELECT_CHART_TO_DASH_BOARD_EM				= "selectChartToDashBoardEm";
	private final String SELECT_CHART_TO_DASH_BOARD_BT				= "selectChartToDashBoardBt";
	private final String SELECT_CHART_TO_DASH_BOARD_BV				= "selectChartToDashBoardBv";
	private final String SELECT_CHART_TO_DASH_BOARD_PS				= "selectChartToDashBoardPs";

	private final String SELECT_BACKGROUND_IMAGE_ALL				= "selectBackgroundImageAll";

	private final String SELECT_COUNT_BACKGROUND_IMAGE_ALL          = "selectCountBackgroundImageAll";

	private final String INSERT_TO_BACKGROUND_IMAGE					= "insertToBackgroundImage";
	private final String DELETE_TO_BACKGROUND_IMAGE					= "deleteToBackgroundImage";
	private final String SELECT_SWITCH_TO_BUDGET_CHECK_LIST			= "selectSwitchToBudgetCheckList";
	private final String SELECT_SWITCH_TO_BUDGET_CHECK				= "selectSwitchToBudgetCheck";
	private final String UPDATE_SWITCH_TO_BUDGET_CHECK				= "updateSwitchToBudgetCheck";

	private final String SELECT_COUNT_COORDI_MANAGEMENT				= "selectCountCoordiManagement";
	private final String SELECT_COORDI_MANAGEMENT					= "selectCoordiManagement";
	private final String UPDATE_COORDI_MANAGEMENT					= "updateCoordiManagement";
	private final String SELECT_COUNT_COORDI_DEPT					= "selectCountCoordiDept";
	private final String UPDATE_COORDI_INFO_APP						= "updateCoordiInfoApp";
	private final String SELECT_DEPT_INFO_APP						= "selectDeptInfoApp";
	private final String UPDATE_COORDI_INFO_APP_FOR_INSERT			= "updateCoordiInfoAppForInsert";
	private final String INSERT_COORDI_INFO_APP						= "insertCoordiInfoApp";
	private final String UPDATE_COORDI_INFO_APP_FOR_DELETE			= "updateCoordiInfoAppForDelete";
	private final String DELETE_COORDI_INFO_APP						= "deleteCoordiInfoApp";

	private final String SELECT_COUNT_TO_APPROVE_LEVEL_MANAGEMENT		= "selectCountToApproveLevelManagement";
	private final String SELECT_LIST_TO_APPROVE_LEVEL_MANAGEMENT		= "selectListToApproveLevelManagement";
	private final String SELECT_MAX_SEQ_TO_APPROVE_LEVEL_MANAGEMENT		= "selectMaxSeqToApproveLevelManagement";
	private final String INSERT_LIST_TO_APPROVE_LEVEL_MANAGEMENT		= "insertListToApproveLevelManagement";
	private final String UPDATE_LIST_TO_APPROVE_LEVEL_MANAGEMENT		= "updateListToApproveLevelManagement";
	private final String DELETE_LIST_TO_APPROVE_LEVEL_MANAGEMENT		= "deleteListToApproveLevelManagement";

	private final String SELECT_COUNT_VENDOR_MANAGEMENT					= "selectCountVendorManagement";
	private final String SELECT_VENDOR_MANAGEMENT						= "selectVendorManagement";
	private final String INSERT_LIST_TO_VENDOR_MANAGEMENT				= "insertListToVendorManagement";
	private final String UPDATE_LIST_TO_VENDOR_MANAGEMENT				= "updateListToVendorManagement";
	private final String DELETE_LIST_TO_VENDOR_MANAGEMENT				= "deleteListToVendorManagement";
	private final String SELECT_TO_VENDOR_MANAGEMENT_DATA 				= "selectToVendorManagementData";

	private final String SELECT_COUNT_PURCHASEORDER_MANAGEMENT			= "selectCountPurchaseOrderManagement";
	private final String SELECT_PURCHASEORDER_MANAGEMENT				= "selectPurchaseOrderManagement";
	private final String INSERT_LIST_TO_PURCHASEORDER_MANAGEMENT		= "insertListToPurchaseOrderManagement";
	private final String UPDATE_LIST_TO_PURCHASEORDER_MANAGEMENT		= "updateListToPurchaseOrderManagement";
	private final String DELETE_LIST_TO_PURCHASEORDER_MANAGEMENT		= "deleteListToPurchaseOrderManagement";
	private final String SELECT_PURCHASE_ORDER_COMBO_LIST				= "selectPurchaseOrderComboList";

	private final String SELECT_COUNT_MATERIAL_MANAGEMENT				= "selectCountMaterialManagement";
	private final String SELECT_MATERIAL_MANAGEMENT						= "selectMaterialManagement";
	private final String INSERT_LIST_TO_MATERIAL_MANAGEMENT				= "insertListToMaterialManagement";
	private final String UPDATE_LIST_TO_MATERIAL_MANAGEMENT				= "updateListToMaterialManagement";
	private final String DELETE_LIST_TO_MATERIAL_MANAGEMENT				= "deleteListToMaterialManagement";
	private final String SELECT_TO_MATERIAL_MANAGEMENT_DATA 			= "selectToMaterialManagementData";

	private final String DELETE_USER_INFO_TEMP_BY_EXCEL_UPLOAD 			= "deleteUserInfoTempByExcelUpload";
	private final String DELETE_USER_INFO_BY_EXCEL_UPLOAD 			    = "deleteUserInfoByExcelUpload";
	private final String INSERT_USER_INFO_TO_USER_INFO_TEMP 		    = "insertUserInfoToUserInfoTemp";
	private final String INSERT_USER_INFO_BY_EXCEL_UPLOAD 		        = "insertUserInfoByExcelUpload";

	private final String DELETE_DEPT_INFO_TEMP_BY_EXCEL_UPLOAD 			= "deleteDeptInfoTempByExcelUpload";
	private final String DELETE_DEPT_INFO_BY_EXCEL_UPLOAD 			    = "deleteDeptInfoByExcelUpload";
	private final String INSERT_DEPT_INFO_TO_DEPT_INFO_TEMP 		    = "insertDeptInfoToDeptInfoTemp";
	private final String INSERT_DEPT_INFO_BY_EXCEL_UPLOAD 		        = "insertDeptInfoByExcelUpload";

	private final String SELECT_XST30_INFO_LIST_COUNT 		        	= "selectXst30InfoListCount";
	private final String SELECT_XST30_INFO_LIST	 		    	    	= "selectXst30InfoList";
	private final String SELECT_MENU_INFO	 		    	    		= "selectMenuInfo";
	private final String UPDATE_MENU_STATUS	 		    	    		= "updateMenuStatus";
	private final String UPDATE_XST30_PGS_ST_CD	 		    	    	= "updateXst30PgsStCd";
	private final String UPDATE_XST30_TO_APPR_USE_FLAG	 		    	= "updateXst30ToApprUseFlag";
	private final String INSERT_USER_INFO	 		    	    		= "insertUserInfo";

	private final String SELECT_USER_LIST   	    					= "selectUserList";
	private final String SELECT_DEPT_INFO_LIST	 		    	    	= "selectDeptInfoList";
	private final String DELETE_APRV_INFO	 		    	    		= "deleteAprvInfo";
	private final String INSERT_APRV_INFO	 		    	    		= "insertAprvInfo";

	private final String INSERT_APPROVAL_USE_YN	 		    	    	= "insertApprovalUseYn";
	private final String UPDATE_APPROVAL_USE_YN	 		    	    	= "updateApprovalUseYn";

	private final String SELECT_GRID_TO_DEPT_INFO_MANAGEMENT_COUNT		= "selectGridToDeptInfoManagementCount";
	private final String SELECT_GRID_TO_DEPT_INFO_MANAGEMENT_LIST		= "selectGridToDeptInfoManagementList";
	
	private final String INSERT_LIST_TO_DEPT_INFO_MANAGEMENT	 		= "insertListToDeptInfoManagement";
	private final String UPDATE_LIST_TO_DEPT_INFO_MANAGEMENT	 		= "updateListToDeptInfoManagement";
	private final String DELETE_LIST_TO_DEPT_INFO_MANAGEMENT	 		= "deleteListToDeptInfoManagement";
	
	private final String SELECT_APPR_TO_USEYN	 		    	    	= "selectApprToUseYn";
	
	
	private final String SELECT_LOGO_MNGR_TO_FILE	 		    	    = "selectLogoMngrToFile";
	private final String MERGE_LOGO_MNGR_TO_FILE	 		    	    = "mergeLogoMngrToFile";
	
	
	private final String INSERT_BGAB_GASCRC01 = "insertBgabGascrc01";
	private final String INSERT_BGAB_GASCRC02 = "insertBgabGascrc02";
	private final String INSERT_BGAB_GASCRC03 = "insertBgabGascrc03";
	private final String INSERT_BGAB_GASCRC04 = "insertBgabGascrc04";
	private final String INSERT_BGAB_GASCRC05 = "insertBgabGascrc05";
	private final String INSERT_BGAB_GASCAF02 = "insertBgabGascaf02";
	private final String INSERT_BGAB_GASCAF03 = "insertBgabGascaf03";
	private final String INSERT_BGAB_GASCGF01 = "insertBgabGascgf01";
	private final String INSERT_BGAB_GASCGF03 = "insertBgabGascgf03";
	private final String INSERT_BGAB_GASCGF04 = "insertBgabGascgf04";
	private final String INSERT_BGAB_GASCGF05 = "insertBgabGascgf05";
	private final String INSERT_BGAB_GASCBK01 = "insertBgabGascbk01";
	private final String INSERT_BGAB_GASCBK03 = "insertBgabGascbk03";
	private final String INSERT_BGAB_GASCBK04 = "insertBgabGascbk04";
	private final String INSERT_BGAB_GASCBA03 = "insertBgabGascba03";
	private final String INSERT_BGAB_GASCGS02 = "insertBgabGascgs02";
	private final String INSERT_BGAB_GASCOS02 = "insertBgabGascos02";
	private final String INSERT_BGAB_GASCBT06 = "insertBgabGascbt06";
	private final String INSERT_BGAB_GASCPS03 = "insertBgabGascps03";
	private final String INSERT_BGAB_GASCFC02 = "insertBgabGascfc02";
	private final String INSERT_BGAB_GASCRM03 = "insertBgabGascrm03";
	private final String INSERT_BGAB_GASCLV03 = "insertBgabGasclv03";
	private final String INSERT_BGAB_GASCZ011 = "insertBgabGascz011";
	private final String INSERT_BGAB_GASCBV01 = "insertBgabGascBv01";
	private final String INSERT_BGAB_GASCSB02 = "insertBgabGascSb02";
	private final String INSERT_BGAB_GASCFJ02 = "insertBgabGascFj02";
	private final String INSERT_BGAB_GASCFJ03 = "insertBgabGascFj03";
	
	private final String INSERT_XST35_HIST = "insertXst35Hist";
	private final String SELECT_XST35_LIST_COUNT = "selectXst35ListCount";
	private final String SELECT_XST35_LIST = "selectXst35List";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BgabGascz004Dto> getSelectMenuListToUserManagement(BgabGascz002Dto bgabGascz002Dto){
		return getSqlMapClientTemplate().queryForList(SELECT_MENU_LIST_TO_USER_MANAGEMENT, bgabGascz002Dto);
	}

	@Override
	public int updateUserInfoToUserManagement(BgabGascz002Dto bgabGascz002Dto){
		return update(UPDATE_USERINFO_TO_USER_MANAGEMENT, bgabGascz002Dto);
	}

	@Override
	public int getSelectMenuCountToMenuManagement(BgabGascz004Dto bgabGascMenu){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_MENU_COUNT_TO_MENU_MANAGEMENT, bgabGascMenu));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz004Dto> getSelectMenuListToMenuManagement(BgabGascz004Dto bgabGascMenu){
		return getSqlMapClientTemplate().queryForList(SELECT_MENU_LIST_TO_MENU_MANAGEMENT, bgabGascMenu);
	}

	@Override
	public int insertListToMenuManagement(List<BgabGascz004Dto> bgabGascMenu){
		return super.insert(INSERT_LIST_TO_MENU_MANAGEMENT, bgabGascMenu);
	}

	@Override
	public int updateListToMenuManagement(List<BgabGascz004Dto> bgabGascMenu){
		return super.update(UPDATE_LIST_TO_MENU_MANAGEMENT, bgabGascMenu);
	}

	@Override
	public int deleteListToMenuManagement(List<BgabGascz004Dto> bgabGascMenu){
		return super.delete(DELETE_LIST_TO_MENU_MANAGEMENT, bgabGascMenu);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TableInfo> getSelectTableListToTableInformation(TableInfo tableInfo){
		return getSqlMapClientTemplate().queryForList(SELECT_TABLE_LIST_TO_TABLE_INFORMATION, tableInfo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TableInfo> getSelectAttributeListToTableInformation(TableInfo tableInfo){
		return getSqlMapClientTemplate().queryForList(SELECT_ATTRIBUTE_LIST_TO_TABLE_INFORMATION, tableInfo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TableInfo> getSelectIndexListToTableInformation(TableInfo tableInfo){
		return getSqlMapClientTemplate().queryForList(SELECT_INDEX_LIST_TO_TABLE_INFORMATION, tableInfo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BatchInfo> getSelectListToBatchManagement(BatchInfo batchInfo){
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TO_BATCH_MANAGEMENT, batchInfo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BatchInfo> getSelectParameterListToBatchManagement(BatchInfo batchInfo){
		return getSqlMapClientTemplate().queryForList(SELECT_PARAMETER_LIST_TO_BATCH_MANAGEMENT, batchInfo);
	}
	@Override
	public int getSelectCountToDeptChangeManagement(BgabGascz006Dto vo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_TO_DEPT_CHANGE_MANAGEMENT, vo));
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz006Dto> getSelectListToDeptChangeManagement(BgabGascz006Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TO_DEPT_CHANGE_MANAGEMENT, vo);
	}
	@Override
	public int insertToDeptChangeManagement(List<BgabGascz006Dto> list){
		return super.insert(INSERT_TO_DEPT_CHANGE_MANAGEMENT, list);
	}
	@Override
	public int updateToDeptChangeManagement(List<BgabGascz006Dto> list){
		return super.update(UPDATE_TO_DEPT_CHANGE_MANAGEMENT, list);
	}
	@Override
	public int updateToInsaDeptChangeManagement(List<BgabGascz006Dto> list){
		return super.update(UPDATE_TO_INSADEPT_CHANGE_MANAGEMENT, list);
	}
	@Override
	public int deleteToDeptChangeManagement(List<BgabGascz006Dto> list){
		return super.delete(DELETE_TO_DEPT_CHANGE_MANAGEMENT, list);
	}
	@Override
	public int updateBatchInfoToBatchManagement(List<BatchInfo> list){
		return super.update(UPDATE_BATCHINFO_TO_BATCH_MANAGEMENT, list);
	}
	@Override
	public int updateParameterToBatchManagement(List<BatchInfo> list){
		return super.update(UPDATE_PARAMETER_TO_BATCH_MANAGEMENT, list);
	}
	@Override
	public int updateBatchInfoToBatchManagementByExecute(List<BatchInfo> list){
		return super.update(UPDATE_BATCHINFO_TO_BATCH_MANAGEMENT_BY_EXECUTE, list);
	}
	@Override
	public int getSelectCountToManagerManagement(BgabGascz007Dto vo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_TO_MANAGER_MANAGEMENT, vo));
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz007Dto> getSelectListToManagerManagement(BgabGascz007Dto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TO_MANAGER_MANAGEMENT, vo);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<CommonCode> getSelectMenuToComboList(CommonCode code){
		return getSqlMapClientTemplate().queryForList(SELECT_MENU_TO_COMBO_LIST, code);
	}
	@Override
	public int getSelectCountToBatchProcessResult(BatchProcess vo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_TO_BATCH_PROCESS_RESULT, vo));
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BatchProcess> getSelectListToBatchProcessResult(BatchProcess vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TO_BATCH_PROCESS_RESULT, vo);
	}
	@Override
	public int insertToManagerManagement(List<BgabGascz007Dto> list){
		return super.insert(INSERT_TO_MANAGER_MANAGEMENT, list);
	}
	@Override
	@SuppressWarnings("unchecked")
	public String getSelectInfoToManagerManagementAuth(String xdsmEmpno) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_INFO_TO_MANAGER_MANAGEMENT_AUTH, xdsmEmpno);
	}
	@Override
	@SuppressWarnings("unchecked")
	public String getSelectInfoToManagerManagementMenuMgrpCd(BgabGascz007Dto vo) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_INFO_TO_MANAGER_MANAGEMENT_MENU_MGRP_CD, vo);
	}
	@Override
	public int updateToManagerManagementXusrAuth(BgabGascz002Dto xusr){
		return super.update(UPDATE_TO_MANAGER_MANAGEMENT_XUSR_AUTH, xusr);
	}
	@Override
	public int updateToManagerManagement(List<BgabGascz007Dto> list){
		return super.update(UPDATE_TO_MANAGER_MANAGEMENT, list);
	}
	@Override
	public int deleteToManagerManagement(List<BgabGascz007Dto> list){
		return super.delete(DELETE_TO_MANAGER_MANAGEMENT, list);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGasckcalDto> getSelectListToCalendarManagement(BgabGasckcalDto vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TO_CALENDAR_MANAGEMENT, vo);
	}
	@Override
	public int getSelectCountToCalendarManagement(BgabGasckcalDto vo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_TO_CALENDAR_MANAGEMENT, vo));
	}
	@Override
	public int insertToCalendarManagement(List<BgabGasckcalDto> list){
		return super.insert(INSERT_TO_CALENDAR_MANAGEMENT, list);
	}
	@Override
	public int updateToCalendarManagement(List<BgabGasckcalDto> list){
		return super.update(UPDATE_TO_CALENDAR_MANAGEMENT, list);
	}
	@Override
	public int getSelectCountToMyApproval(CommonApproval vo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_TO_MY_APPROVAL, vo));
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<CommonApproval> getSelectListToMyApproval(CommonApproval vo) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TO_MY_APPROVAL, vo);
	}
	@Override
	public int updateToMyApprovalForDepute(BgabGascz008Dto vo){
		return super.update(UPDATE_TO_MY_APPROVAL_FOR_DEPUTE, vo);
	}
	@Override
	public int updateToMyApprovalForDeputeByApprovalInfo(BgabGascz008Dto vo){
		return super.update(UPDATE_TO_MY_APPROVAL_FOR_DEPUTE_BY_APPROVAL_INFO, vo);
	}
	@Override
	public int updateToMyApprovalForDeputeByApprovalInfoDetail(BgabGascz008Dto vo){
		return super.update(UPDATE_TO_MY_APPROVAL_FOR_DEPUTE_BY_APPROVAL_INFO_DETAIL, vo);
	}
	@Override
	public int updateToMyApprovalForDeputeByRestore(BgabGascz008Dto vo){
		return super.update(UPDATE_TO_MY_APPROVAL_FOR_DEPUTE_BY_RESTORE, vo);
	}
	@Override
	public String getSelectToMyApprovalForDepute(BgabGascz008Dto vo) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_TO_MY_APPROVAL_FOR_DEPUTE, vo);
	}
	@Override
	public BgabGascz008Dto getSelectToMyApprovalUserInfo(BgabGascz008Dto vo) {
		return (BgabGascz008Dto)getSqlMapClientTemplate().queryForObject(SELECT_TO_MY_APPROVAL_USER_INFO, vo);
	}
	@Override
	public int getSelectCountReaderManagement(BgabGascz008Dto dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_READER_MANAGEMENT, dto));
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz008Dto> getSelectReaderManagement(BgabGascz008Dto dto){
		return getSqlMapClientTemplate().queryForList(SELECT_READER_MANAGEMENT, dto);
	}

	@Override
	public int updateReaderManagement(BgabGascz008Dto dto){
		return update(UPDATE_READER_MANAGEMENT, dto);
	}

	@Override
	public int getSelectCountCodeManagement(BgabGascz005Dto vo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_CODE_MANAGEMENT_BY_SYSTEM, vo));
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz005Dto> getSelectCodeManagement(BgabGascz005Dto dto){
		return getSqlMapClientTemplate().queryForList(SELECT_CODE_MANAGEMENT_BY_SYSTEM, dto);
	}

	@Override
	public int insertListToCodeManagement(List<BgabGascz005Dto> dto){
		return super.insert(INSERT_LIST_TO_CODE_MANAGEMENT, dto);
	}
	@Override
	public int updateListToCodeManagement(List<BgabGascz005Dto> dto){
		return super.update(UPDATE_LIST_TO_CODE_MANAGEMENT, dto);
	}
	@Override
	public int deleteListToCodeManagement(List<BgabGascz005Dto> dto){
		return super.delete(DELETE_LIST_TO_CODE_MANAGEMENT, dto);
	}
	@Override
	public String getSelectBackgroundImage(String corp_cd) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_BACKGROUND_IMAGE, corp_cd);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz005Dto> getSelectBackgroundImageAll(BgabGascz005Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_BACKGROUND_IMAGE_ALL, dto);
	}

	@Override
	public String getSelectCountBackgroundImageAll(BgabGascz005Dto vo) {
		// TODO Auto-generated method stub
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BACKGROUND_IMAGE_ALL, vo);
	}

	@Override
	public int updateToBackgroundImage(BgabGascz005Dto vo) {
		// TODO Auto-generated method stub
		return super.update(UPDATE_TO_BACKGROUND_IMAGE, vo);
	}

	@Override
	public int updateToBackgroundImageApplyY(BgabGascz005Dto vo){
		return super.update(UPDATE_TO_BACKGROUND_IMAGE_APPLY_Y, vo);
	}

	@Override
	public int updateToBackgroundImageApplyN(BgabGascz005Dto vo) {
		// TODO Auto-generated method stub
		return super.update(UPDATE_TO_BACKGROUND_IMAGE_APPLY_N, vo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoard> getSelectChartToDashBoardEm(DashBoard code) {
		return getSqlMapClientTemplate().queryForList(SELECT_CHART_TO_DASH_BOARD_EM, code);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoard> getSelectChartToDashBoardBt(DashBoard code) {
		return getSqlMapClientTemplate().queryForList(SELECT_CHART_TO_DASH_BOARD_BT, code);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoard> getSelectChartToDashBoardBv(DashBoard code) {
		return getSqlMapClientTemplate().queryForList(SELECT_CHART_TO_DASH_BOARD_BV, code);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoard> getSelectChartToDashBoardPs(DashBoard code) {
		return getSqlMapClientTemplate().queryForList(SELECT_CHART_TO_DASH_BOARD_PS, code);
	}

	@Override
	public int insertToBackgroundImage(BgabGascz005Dto vo) {
		// TODO Auto-generated method stub
		return super.insert(INSERT_TO_BACKGROUND_IMAGE, vo);
	}

	@Override
	public int deleteToBackgroundImage(BgabGascz005Dto vo) {
		// TODO Auto-generated method stub
		return super.delete(DELETE_TO_BACKGROUND_IMAGE, vo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz005Dto> getSelectSwitchToBudgetCheckList(){
		return getSqlMapClientTemplate().queryForList(SELECT_SWITCH_TO_BUDGET_CHECK_LIST);
	}

	@Override
	@SuppressWarnings("unchecked")
	public BgabGascz005Dto getSelectSwitchToBudgetCheck(BgabGascz005Dto bgabGascz005Dto){
		return (BgabGascz005Dto)getSqlMapClientTemplate().queryForObject(SELECT_SWITCH_TO_BUDGET_CHECK, bgabGascz005Dto);
	}

	@Override
	public int saveSwitchToBudgetCheck(List<BgabGascz005Dto> bgabGascz005Dto){
		return super.update(UPDATE_SWITCH_TO_BUDGET_CHECK, bgabGascz005Dto);
	}

	@Override
	public int getSelectCountCoordiManagement(BgabGascz003Dto paramDto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_COORDI_MANAGEMENT, paramDto));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz003Dto> getSelectCoordiManagement(BgabGascz003Dto paramDto) {
		return getSqlMapClientTemplate().queryForList(SELECT_COORDI_MANAGEMENT, paramDto);
	}

	@Override
	public int updateCoordiManagement(BgabGascz003Dto bgabGascz003Dto) {
		return super.update(UPDATE_COORDI_MANAGEMENT, bgabGascz003Dto);
	}
	@Override
	public int getSelectCountCoordiDept(String crdDcd) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_COORDI_DEPT, crdDcd));
	}

	@Override
	public int updateCoordiInfoApp(BgabGascz003Dto bgabGascz003Dto) {
		return super.update(UPDATE_COORDI_INFO_APP, bgabGascz003Dto);
	}

	@Override
	public BgabGascz008Dto getSelectDeptInfoApp(String xorg_orga_c) {
		return (BgabGascz008Dto)getSqlMapClientTemplate().queryForObject(SELECT_DEPT_INFO_APP, xorg_orga_c);
	}
	@Override
	public int updateCoordiInfoAppForInsert(BgabGascz003Dto bgabGascz003Dto) {
		return super.update(UPDATE_COORDI_INFO_APP_FOR_INSERT, bgabGascz003Dto);
	}
	@Override
	public int insertCoordiInfoApp(BgabGascz008Dto bgabGascz008Dto) {
		return super.insert(INSERT_COORDI_INFO_APP, bgabGascz008Dto);
	}

	@Override
	public int updateCoordiInfoAppForDelete(BgabGascz008Dto bgabGascz008Dto) {
		return super.update(UPDATE_COORDI_INFO_APP_FOR_DELETE, bgabGascz008Dto);
	}

	@Override
	public int deleteCoordiInfoApp(BgabGascz003Dto bgabGascz003Dto) {
		return super.delete(DELETE_COORDI_INFO_APP, bgabGascz003Dto);
	}


	@Override
	public int selectCountToApproveLevelManagement(BgabGascz013Dto bgabGascz013Dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_TO_APPROVE_LEVEL_MANAGEMENT, bgabGascz013Dto));
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BgabGascz013Dto> selectListToApproveLevelManagement(BgabGascz013Dto bgabGascz013Dto){
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TO_APPROVE_LEVEL_MANAGEMENT, bgabGascz013Dto);
	}
	@Override
	public String selectMaxSeqToApproveLevelManagement(BgabGascz013Dto bgabGascz013Dto) {
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_MAX_SEQ_TO_APPROVE_LEVEL_MANAGEMENT, bgabGascz013Dto);
	}
	@Override
	public int insertListToApproveLevelManagement(List<BgabGascz013Dto> bgabGascz013Dto) {
		return super.insert(INSERT_LIST_TO_APPROVE_LEVEL_MANAGEMENT, bgabGascz013Dto);
	}
	@Override
	public int updateListToApproveLevelManagement(List<BgabGascz013Dto> bgabGascz013Dto) {
		return super.update(UPDATE_LIST_TO_APPROVE_LEVEL_MANAGEMENT, bgabGascz013Dto);
	}
	@Override
	public int deleteListToApproveLevelManagement(List<BgabGascz013Dto> bgabGascz013Dto) {
		return super.delete(DELETE_LIST_TO_APPROVE_LEVEL_MANAGEMENT, bgabGascz013Dto);
	}

	/**
	 * vendor management.
	 */
	@Override
	public int getSelectCountVendorManagement(BgabGascz014Dto param){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_VENDOR_MANAGEMENT, param));
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz014Dto> getSelectVendorManagement(BgabGascz014Dto param){
		return getSqlMapClientTemplate().queryForList(SELECT_VENDOR_MANAGEMENT, param);
	}
	@Override
	public int insertListToVendorManagement(List<BgabGascz014Dto> dto){
		return super.insert(INSERT_LIST_TO_VENDOR_MANAGEMENT, dto);
	}
	@Override
	public int updateListToVendorManagement(List<BgabGascz014Dto> dto){
		return super.update(UPDATE_LIST_TO_VENDOR_MANAGEMENT, dto);
	}
	@Override
	public int deleteListToVendorManagement(List<BgabGascz014Dto> dto){
		return super.delete(DELETE_LIST_TO_VENDOR_MANAGEMENT, dto);
	}
	@Override
	public int selectToVendorManagementData(BgabGascz014Dto dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_TO_VENDOR_MANAGEMENT_DATA, dto));
	}

	/**
	 * PurchaseOrder management.
	 */
	@Override
	public int getSelectCountPurchaseOrderManagement(BgabGascz015Dto param){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_PURCHASEORDER_MANAGEMENT, param));
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz015Dto> getSelectPurchaseOrderManagement(BgabGascz015Dto param){
		return getSqlMapClientTemplate().queryForList(SELECT_PURCHASEORDER_MANAGEMENT, param);
	}
	@Override
	public int insertListToPurchaseOrderManagement(List<BgabGascz015Dto> dto){
		return super.insert(INSERT_LIST_TO_PURCHASEORDER_MANAGEMENT, dto);
	}
	@Override
	public int updateListToPurchaseOrderManagement(List<BgabGascz015Dto> dto){
		return super.update(UPDATE_LIST_TO_PURCHASEORDER_MANAGEMENT, dto);
	}
	@Override
	public int deleteListToPurchaseOrderManagement(List<BgabGascz015Dto> dto){
		return super.delete(DELETE_LIST_TO_PURCHASEORDER_MANAGEMENT, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<CommonCode> getSelectPurchaseOrderCombo(CommonCode code){
		return getSqlMapClientTemplate().queryForList(SELECT_PURCHASE_ORDER_COMBO_LIST, code);
	}

	/**
	 * restrant menu management
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz019Dto> doSearchByDateMenu(BgabGascz019Dto param){
		return getSqlMapClientTemplate().queryForList("selectByDateMenu", param);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz019Dto> doSearchByBrasilanMenu(BgabGascz019Dto param){
		return getSqlMapClientTemplate().queryForList("selectByBrasilMenu", param);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz019Dto> doSearchByKoreanMenu(BgabGascz019Dto param){
		return getSqlMapClientTemplate().queryForList("selectByKoreaMenu", param);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz019Dto> doSearchByCoffee(BgabGascz019Dto param){
		return getSqlMapClientTemplate().queryForList("selectByCoffee", param);
	}

	//brasilan
	@Override
	public int doInsertByBrasilanMenu(List<BgabGascz019Dto> gsSaveVo){
		return super.insert("insertByBrasilanMenu", gsSaveVo);
	}
	@Override
	public int doUpdateByBrasilanMenu(List<BgabGascz019Dto> gsModifyVo){
		return super.update("updateByBrasilanMenu", gsModifyVo);
	}
	@Override
	public int doDeleteByBrasilanMenu(List<BgabGascz019Dto> gsDelVo){
		return super.delete("deleteByBrasilanMenu", gsDelVo);
	}

	//korean
	@Override
	public int doInsertByKoreanMenu(List<BgabGascz019Dto> gsSaveVo){
		return super.insert("insertByKoreanMenu", gsSaveVo);
	}
	@Override
	public int doUpdateByKoreanMenu(List<BgabGascz019Dto> gsModifyVo){
		return super.update("updateByKoreanMenu", gsModifyVo);
	}
	@Override
	public int doDeleteByKoreanMenu(List<BgabGascz019Dto> gsDelVo){
		return super.delete("deleteByKoreanMenu", gsDelVo);
	}

	//coffee
	@Override
	public int doInsertByCoffee(List<BgabGascz019Dto> gsSaveVo){
		return super.insert("insertByCoffee", gsSaveVo);
	}
	@Override
	public int doUpdateByCoffee(List<BgabGascz019Dto> gsModifyVo){
		return super.update("updateByCoffee", gsModifyVo);
	}
	@Override
	public int doDeleteByCoffee(List<BgabGascz019Dto> gsDelVo){
		return super.delete("deleteByCoffee", gsDelVo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CommonCode> getSelectVendorCodeCombo(CommonCode code){
		return getSqlMapClientTemplate().queryForList("selectVendorCodeCombo", code);
	}

	/**
	 * Material management.
	 */
	@Override
	public int getSelectCountMaterialManagement(BgabGascz016Dto param){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_MATERIAL_MANAGEMENT, param));
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz016Dto> getSelectMaterialManagement(BgabGascz016Dto param){
		return getSqlMapClientTemplate().queryForList(SELECT_MATERIAL_MANAGEMENT, param);
	}
	@Override
	public int insertListToMaterialManagement(List<BgabGascz016Dto> dto){
		return super.insert(INSERT_LIST_TO_MATERIAL_MANAGEMENT, dto);
	}
	@Override
	public int updateListToMaterialManagement(List<BgabGascz016Dto> dto){
		return super.update(UPDATE_LIST_TO_MATERIAL_MANAGEMENT, dto);
	}
	@Override
	public int deleteListToMaterialManagement(List<BgabGascz016Dto> dto){
		return super.delete(DELETE_LIST_TO_MATERIAL_MANAGEMENT, dto);
	}
	@Override
	public int selectToMaterialManagementData(BgabGascz016Dto dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_TO_MATERIAL_MANAGEMENT_DATA, dto));
	}
	@Override
	public BgabGascz002Dto getSelectUserWorkPlace(BgabGascz002Dto dto){
		return (BgabGascz002Dto)getSqlMapClientTemplate().queryForObject("selectUserWorkPlace", dto);
	}
	@Override
	public BgabGascz005Dto getSelectCheckBudgetSwitch(BgabGascz005Dto dto){
		return (BgabGascz005Dto)getSqlMapClientTemplate().queryForObject("selectCheckBudgetSwitch", dto);
	}
	@Override
	public BgabGascz002Dto getSelectUserInfoDetailPopup(BgabGascz002Dto bgabGascz002Dto){
		return (BgabGascz002Dto)getSqlMapClientTemplate().queryForObject("selectUserInfoDetailPopup", bgabGascz002Dto);
	}

	@Override
	public int insertUserInfoByExcelUpload(List<BgabGascz002Dto> bgabGascz002Dto){
		return insert(INSERT_USER_INFO_BY_EXCEL_UPLOAD, bgabGascz002Dto);
	}

	@Override
	public int insertUserInfoToUserInfoTemp(BgabGascz002Dto bgabGascz002Dto){
		return insert(INSERT_USER_INFO_TO_USER_INFO_TEMP, bgabGascz002Dto);
	}
	@Override
	public int deleteUserInfoTempByExcelUpload(BgabGascz002Dto bgabGascz002Dto){
		return delete(DELETE_USER_INFO_TEMP_BY_EXCEL_UPLOAD, bgabGascz002Dto);
	}

	@Override
	public int deleteUserInfoByExcelUpload(BgabGascz002Dto bgabGascz002Dto){
		return delete(DELETE_USER_INFO_BY_EXCEL_UPLOAD, bgabGascz002Dto);
	}

	@Override
	public int insertDeptInfoByExcelUpload(List<BgabGascz003Dto> bgabGascz003Dto){
		return insert(INSERT_DEPT_INFO_BY_EXCEL_UPLOAD, bgabGascz003Dto);
	}
	@Override
	public int insertDeptInfoToDeptInfoTemp(BgabGascz003Dto bgabGascz003Dto){
		return insert(INSERT_DEPT_INFO_TO_DEPT_INFO_TEMP, bgabGascz003Dto);
	}
	@Override
	public int deleteDeptInfoTempByExcelUpload(BgabGascz003Dto bgabGascz003Dto){
		return delete(DELETE_DEPT_INFO_TEMP_BY_EXCEL_UPLOAD, bgabGascz003Dto);
	}

	@Override
	public int deleteDeptInfoByExcelUpload(BgabGascz003Dto bgabGascz003Dto){
		return delete(DELETE_DEPT_INFO_BY_EXCEL_UPLOAD, bgabGascz003Dto);
	}

	@Override
	public int selectXst30InfoListCount(BgabGascz030Dto dto){
		return (Integer)getSqlMapClientTemplate().queryForObject(SELECT_XST30_INFO_LIST_COUNT, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGascz030Dto> selectXst30InfoList(BgabGascz030Dto dto){
		return getSqlMapClientTemplate().queryForList(SELECT_XST30_INFO_LIST, dto);
	}
	@Override
	public BgabGascz030Dto selectMenuInfo(BgabGascz030Dto dto){
		return (BgabGascz030Dto) getSqlMapClientTemplate().queryForObject(SELECT_MENU_INFO, dto);
	}
	@Override
	public int updateMenuStatus(BgabGascz031Dto dto){
		return update(UPDATE_MENU_STATUS, dto);
	}
	@Override
	public int updateXst30PgsStCd(BgabGascz030Dto dto){
		return update(UPDATE_XST30_PGS_ST_CD, dto);
	}
	@Override
	public int updateXst30ToApprUseFlag(BgabGascz030Dto dto){
		return update(UPDATE_XST30_TO_APPR_USE_FLAG, dto);
	}
	@Override
	public void validateBgabGascz002(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascz002", map);
	}
	@Override
	public void createBgabGascz002(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascz002", map);
	}
	@Override
	public void validateBgabGascz003(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascz003", map);
	}
	@Override
	public void createBgabGascz003(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascz003", map);
	}
	@Override
	public void validateBgabGascz005(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascz005", map);
	}
	@Override
	public void createBgabGascz005(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascz005", map);
	}
	@Override
	public void validateBgabGascz007(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascz007", map);
	}
	@Override
	public void createBgabGascz007(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascz007", map);
	}
	@Override
	public void validateBgabGascz008(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascz008", map);
	}
	@Override
	public void createBgabGascz008(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascz008", map);
	}
	@Override
	public void validateBgabGascz009(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascz009", map);
	}
	@Override
	public void createBgabGascz009(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascz009", map);
	}
	@Override
	public void validateBgabGascz010(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascz010", map);
	}
	@Override
	public void createBgabGascz010(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascz010", map);
	}
	@Override
	public void validateBgabGascz011(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascz011", map);
	}
	@Override
	public void createBgabGascz011(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascz011", map);
	}
	@Override
	public void validateBgabGascz012(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascz012", map);
	}
	@Override
	public void createBgabGascz012(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascz012", map);
	}
	@Override
	public void validateBgabGascz013(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascz013", map);
	}
	@Override
	public void createBgabGascz013(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascz013", map);
	}
	@Override
	public void validateBgabGascCal(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascCal", map);
	}
	@Override
	public void createBgabGascCal(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascCal", map);
	}
	@Override
	public void validateBgabGascz032(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascz032", map);
	}
	@Override
	public void createBgabGascz032(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascz032", map);
	}
	@Override
	public void insertBgabGascz032(HashMap<String, String> map){
		insert("insertBgabGascz032", map);
	}
	@Override
	public void validateBgabGascRC01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascRC01", map);
	}
	@Override
	public void createBgabGascRC01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascRC01", map);
	}
	@Override
	public void validateBgabGascRC02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascRC02", map);
	}
	@Override
	public void createBgabGascRC02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascRC02", map);
	}
	@Override
	public void validateBgabGascRC03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascRC03", map);
	}
	@Override
	public void createBgabGascRC03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascRC03", map);
	}
	@Override
	public void validateBgabGascRC04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascRC04", map);
	}
	@Override
	public void createBgabGascRC04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascRC04", map);
	}
	@Override
	public void validateBgabGascRC05(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascRC05", map);
	}
	@Override
	public void createBgabGascRC05(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascRC05", map);
	}
	@Override
	public void validateBgabGascRC06(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascRC06", map);
	}
	@Override
	public void createBgabGascRC06(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascRC06", map);
	}
	@Override
	public void validateBgabGascAF01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascAF01", map);
	}
	@Override
	public void createBgabGascAF01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascAF01", map);
	}
	@Override
	public void validateBgabGascAF02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascAF02", map);
	}
	@Override
	public void createBgabGascAF02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascAF02", map);
	}
	@Override
	public void validateBgabGascAF03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascAF03", map);
	}
	@Override
	public void createBgabGascAF03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascAF03", map);
	}
	@Override
	public void validateBgabGascAF04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascAF04", map);
	}
	@Override
	public void createBgabGascAF04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascAF04", map);
	}
	@Override
	public void validateBgabGascAF05(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascAF05", map);
	}
	@Override
	public void createBgabGascAF05(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascAF05", map);
	}
	@Override
	public void validateBgabGascGF01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascGF01", map);
	}
	@Override
	public void createBgabGascGF01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascGF01", map);
	}
	@Override
	public void validateBgabGascGF02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascGF02", map);
	}
	@Override
	public void createBgabGascGF02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascGF02", map);
	}
	@Override
	public void validateBgabGascGF03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascGF03", map);
	}
	@Override
	public void createBgabGascGF03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascGF03", map);
	}
	@Override
	public void validateBgabGascGF04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascGF04", map);
	}
	@Override
	public void createBgabGascGF04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascGF04", map);
	}
	@Override
	public void validateBgabGascGF05(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascGF05", map);
	}
	@Override
	public void createBgabGascGF05(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascGF05", map);
	}
	@Override
	public void validateBgabGascGFExcelTemp(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascGF05", map);
	}
	@Override
	public void createBgabGascGFExcelTemp(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascGFExcelTemp", map);
	}
	@Override
	public void validateBgabGascBK01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBK01", map);
	}
	@Override
	public void createBgabGascBK01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBK01", map);
	}
	@Override
	public void validateBgabGascBK02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBK02", map);
	}
	@Override
	public void createBgabGascBK02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBK02", map);
	}
	@Override
	public void validateBgabGascBK03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBK03", map);
	}
	@Override
	public void createBgabGascBK03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBK03", map);
	}
	@Override
	public void validateBgabGascBK04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBK04", map);
	}
	@Override
	public void createBgabGascBK04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBK04", map);
	}
	@Override
	public void validateBgabGascBKExcelTemp(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBKExcelTemp", map);
	}
	@Override
	public void createBgabGascBKExcelTemp(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBKExcelTemp", map);
	}
	@Override
	public void validateBgabGascTR01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascTR01", map);
	}
	@Override
	public void createBgabGascTR01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascTR01", map);
	}
	@Override
	public void validateBgabGascBA01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBA01", map);
	}
	@Override
	public void createBgabGascBA01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBA01", map);
	}
	@Override
	public void validateBgabGascBA02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBA02", map);
	}
	@Override
	public void createBgabGascBA02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBA02", map);
	}
	@Override
	public void validateBgabGascBA03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBA03", map);
	}
	@Override
	public void createBgabGascBA03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBA03", map);
	}
	@Override
	public void validateBgabGascGS01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascGS01", map);
	}
	@Override
	public void createBgabGascGS01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascGS01", map);
	}
	@Override
	public void validateBgabGascGS02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascGS02", map);
	}
	@Override
	public void createBgabGascGS02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascGS02", map);
	}
	@Override
	public void validateBgabGascGS03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascGS03", map);
	}
	@Override
	public void createBgabGascGS03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascGS03", map);
	}
	@Override
	public void validateBgabGascGS04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascGS04", map);
	}
	@Override
	public void createBgabGascGS04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascGS04", map);
	}
	@Override
	public void validateBgabGascOS01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascOS01", map);
	}
	@Override
	public void createBgabGascOS01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascOS01", map);
	}
	@Override
	public void validateBgabGascOS02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascOS02", map);
	}
	@Override
	public void createBgabGascOS02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascOS02", map);
	}
	@Override
	public void validateBgabGascOS03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascOS03", map);
	}
	@Override
	public void createBgabGascOS03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascOS03", map);
	}
	@Override
	public void validateBgabGascOS04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascOS04", map);
	}
	@Override
	public void createBgabGascOS04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascOS04", map);
	}
	@Override
	public void validateBgabGascBT01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBT01", map);
	}
	@Override
	public void createBgabGascBT01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBT01", map);
	}
	@Override
	public void validateBgabGascBT02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBT02", map);
	}
	@Override
	public void createBgabGascBT02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBT02", map);
	}
	@Override
	public void validateBgabGascBT03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBT03", map);
	}
	@Override
	public void createBgabGascBT03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBT03", map);
	}
	@Override
	public void validateBgabGascBT04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBT04", map);
	}
	@Override
	public void createBgabGascBT04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBT04", map);
	}
	@Override
	public void validateBgabGascBT05(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBT05", map);
	}
	@Override
	public void createBgabGascBT05(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBT05", map);
	}
	@Override
	public void validateBgabGascBT06(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBT06", map);
	}
	@Override
	public void createBgabGascBT06(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBT06", map);
	}
	@Override
	public void validateBgabGascBT07(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBT07", map);
	}
	@Override
	public void createBgabGascBT07(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBT07", map);
	}
	@Override
	public void validateBgabGascBT08(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBT08", map);
	}
	@Override
	public void createBgabGascBT08(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBT08", map);
	}
	@Override
	public void validateBgabGascBT09(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBT09", map);
	}
	@Override
	public void createBgabGascBT09(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBT09", map);
	}
	@Override
	public void validateBgabGascPS01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascPS01", map);
	}
	@Override
	public void createBgabGascPS01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascPS01", map);
	}
	@Override
	public void validateBgabGascPS02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascPS02", map);
	}
	@Override
	public void createBgabGascPS02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascPS02", map);
	}
	@Override
	public void validateBgabGascPS03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascPS03", map);
	}
	@Override
	public void createBgabGascPS03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascPS03", map);
	}
	@Override
	public void validateBgabGascPS04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascPS04", map);
	}
	@Override
	public void createBgabGascPS04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascPS04", map);
	}
	@Override
	public void validateBgabGascTX01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascTX01", map);
	}
	@Override
	public void createBgabGascTX01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascTX01", map);
	}
	@Override
	public void validateBgabGascTX02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascTX02", map);
	}
	@Override
	public void createBgabGascTX02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascTX02", map);
	}
	@Override
	public void validateBgabGascTX03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascTX03", map);
	}
	@Override
	public void createBgabGascTX03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascTX03", map);
	}
	@Override
	public void validateBgabGascTX04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascTX04", map);
	}
	@Override
	public void createBgabGascTX04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascTX04", map);
	}
	@Override
	public void validateBgabGascBV01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBV01", map);
	}
	@Override
	public void createBgabGascBV01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBV01", map);
	}
	@Override
	public void validateBgabGascBV02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBV02", map);
	}
	@Override
	public void createBgabGascBV02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBV02", map);
	}
	@Override
	public void validateBgabGascBV03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBV03", map);
	}
	@Override
	public void createBgabGascBV03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBV03", map);
	}
	@Override
	public void validateBgabGascBV04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBV04", map);
	}
	@Override
	public void createBgabGascBV04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBV04", map);
	}
	@Override
	public void validateBgabGascFC01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascFC01", map);
	}
	@Override
	public void createBgabGascFC01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascFC01", map);
	}
	@Override
	public void validateBgabGascFC02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascFC02", map);
	}
	@Override
	public void createBgabGascFC02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascFC02", map);
	}
	@Override
	public void validateBgabGascVL01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascVL01", map);
	}
	@Override
	public void createBgabGascVL01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascVL01", map);
	}
	@Override
	public void validateBgabGascSE01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascSE01", map);
	}
	@Override
	public void createBgabGascSE01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascSE01", map);
	}
	@Override
	public void validateBgabGascSE02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascSE02", map);
	}
	@Override
	public void createBgabGascSE02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascSE02", map);
	}
	@Override
	public void validateBgabGascSE03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascSE03", map);
	}
	@Override
	public void createBgabGascSE03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascSE03", map);
	}
	@Override
	public void validateBgabGascSE04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascSE04", map);
	}
	@Override
	public void createBgabGascSE04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascSE04", map);
	}
	@Override
	public void validateBgabGascSE05(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascSE05", map);
	}
	@Override
	public void createBgabGascSE05(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascSE05", map);
	}
	@Override
	public void validateBgabGascSE06(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascSE06", map);
	}
	@Override
	public void createBgabGascSE06(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascSE06", map);
	}
	@Override
	public void validateBgabGascSE07(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascSE07", map);
	}
	@Override
	public void createBgabGascSE07(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascSE07", map);
	}
	@Override
	public void validateBgabGascRM01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascRM01", map);
	}
	@Override
	public void createBgabGascRM01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascRM01", map);
	}
	@Override
	public void validateBgabGascRM02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascRM02", map);
	}
	@Override
	public void createBgabGascRM02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascRM02", map);
	}
	@Override
	public void validateBgabGascRM03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascRM03", map);
	}
	@Override
	public void createBgabGascRM03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascRM03", map);
	}
	@Override
	public void validateBgabGascRM04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascRM04", map);
	}
	@Override
	public void createBgabGascRM04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascRM04", map);
	}
	@Override
	public void validateBgabGascCE01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascCE01", map);
	}
	@Override
	public void createBgabGascCE01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascCE01", map);
	}
	@Override
	public void validateBgabGascLV01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascLV01", map);
	}
	@Override
	public void createBgabGascLV01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascLV01", map);
	}
	@Override
	public void validateBgabGascLV02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascLV02", map);
	}
	@Override
	public void createBgabGascLV02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascLV02", map);
	}
	@Override
	public void validateBgabGascLV03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascLV03", map);
	}
	@Override
	public void createBgabGascLV03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascLV03", map);
	}
	@Override
	public void validateBgabGascBD01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBD01", map);
	}
	@Override
	public void createBgabGascBD01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascBD01", map);
	}
	@Override
	public int insertUserInfo(BgabGascz002Dto bgabGascz002Dto){
		return insert(INSERT_USER_INFO, bgabGascz002Dto);
	}
	@Override
	public void validateBgabGascSB01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascSB01", map);
	}
	@Override
	public void createBgabGascSB01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascSB01", map);
	}
	@Override
	public void validateBgabGascBS02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascBS02", map);
	}
	@Override
	public void createBgabGascSB02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascSB02", map);
	}
	@Override
	public void validateBgabGascFJ01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascFJ01", map);
	}
	@Override
	public void createBgabGascFJ01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascFJ01", map);
	}
	@Override
	public void validateBgabGascFJ02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascFJ02", map);
	}
	@Override
	public void createBgabGascFJ02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascFJ02", map);
	}
	@Override
	public void validateBgabGascFJ03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascFJ03", map);
	}
	@Override
	public void createBgabGascFJ03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascFJ03", map);
	}
	@Override
	public void validateBgabGascPD01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascPD01", map);
	}
	@Override
	public void createBgabGascPD01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascPD01", map);
	}
	@Override
	public void validateBgabGascPD02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascPD02", map);
	}
	@Override
	public void createBgabGascPD02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascPD02", map);
	}
	@Override
	public void validateBgabGascPD03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascPD03", map);
	}
	@Override
	public void createBgabGascPD03(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascPD03", map);
	}
	@Override
	public void validateBgabGascPD04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascPD04", map);
	}
	@Override
	public void createBgabGascPD04(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascPD04", map);
	}
	@Override
	public void validateBgabGascCO01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascCO01", map);
	}
	@Override
	public void createBgabGascCO01(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascCO01", map);
	}
	@Override
	public void validateBgabGascCO02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("validateBgabGascCO02", map);
	}
	@Override
	public void createBgabGascCO02(HashMap<String, String> map){
		getSqlMapClientTemplate().queryForObject("createBgabGascCO02", map);
	}
	
	@Override 
	public int insertBgabGascrc01(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCRC01, map); }
	@Override 
	public int insertBgabGascrc02(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCRC02, map); }
	@Override 
	public int insertBgabGascrc03(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCRC03, map); }
	@Override 
	public int insertBgabGascrc04(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCRC04, map); }
	@Override 
	public int insertBgabGascrc05(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCRC05, map); }
	@Override 
	public int insertBgabGascaf02(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCAF02, map); }
	@Override 
	public int insertBgabGascaf03(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCAF03, map); }
	@Override 
	public int insertBgabGascgf01(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCGF01, map); }
	@Override 
	public int insertBgabGascgf03(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCGF03, map);
	}
	@Override 
	public int insertBgabGascgf04(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCGF04, map);
	}
	@Override 
	public int insertBgabGascgf05(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCGF05, map);
	}
	@Override 
	public int insertBgabGascbk01(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCBK01, map);
	}
	@Override 
	public int insertBgabGascbk03(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCBK03, map);
	}
	@Override 
	public int insertBgabGascbk04(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCBK04, map);
	}
	@Override 
	public int insertBgabGascba03(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCBA03, map);
	}
	@Override 
	public int insertBgabGascgs02(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCGS02, map);
	}
	@Override 
	public int insertBgabGascos02(HashMap<String, String> map){
		return insert(INSERT_BGAB_GASCOS02, map);
	}
	@Override 
	public int insertBgabGascbt06(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCBT06, map);
	}
	@Override 
	public int insertBgabGascps03(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCPS03, map);
	}
	@Override 
	public int insertBgabGascfc02(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCFC02, map);
	}
	@Override 
	public int insertBgabGascrm03(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCRM03, map);
	}
	@Override
	public int insertBgabGasclv03(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCLV03, map);
	}
	@Override
	public int insertBgabGascz011(HashMap<String, String> map){ 
		return insert(INSERT_BGAB_GASCZ011, map);
	}
	
	@Override
	public int insertBgabGascz005(HashMap<String, String> map){ 
		return insert("insertBgabGascz005", map);
	}

	@Override
	public int insertBgabGascPd03(HashMap<String, String> map) {
		return insert("insertBgabGascPd03", map);
	}

	@Override
	public int insertBgabGascPd04(HashMap<String, String> map) {
		return insert("insertBgabGascPd04", map);
	}

	@Override
	public int insertBgabGascBv01(HashMap<String, String> map) {
		return insert(INSERT_BGAB_GASCBV01, map);
	}

	@Override
	public int insertBgabGascSb02(HashMap<String, String> map) {
		return insert(INSERT_BGAB_GASCSB02, map);
	}

	@Override
	public int insertBgabGascFj02(HashMap<String, String> map) {
		return insert(INSERT_BGAB_GASCFJ02, map);
	}

	@Override
	public int insertBgabGascFj03(HashMap<String, String> map) {
		return insert(INSERT_BGAB_GASCFJ03, map);
	}

	@Override
	public List<BgabGascz002Dto> selectUserInfoList(BgabGascz002Dto delDto) {
		return getSqlMapClientTemplate().queryForList(SELECT_USER_LIST, delDto);
	}

	@Override
	public List<BgabGascz003Dto> selectDeptInfoList(BgabGascz003Dto delDto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DEPT_INFO_LIST, delDto);
	}

	@Override
	public int deleteAprvInfo(BgabGascz003Dto z003Dto) {
		return delete(DELETE_APRV_INFO, z003Dto);
	}

	@Override
	public int insertAprvInfo(List<BgabGascz008Dto> z008List) {
		return insert(INSERT_APRV_INFO, z008List);
	}

	@Override
	public int selectGridToDeptInfoManagementCount(BgabGascz003Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_GRID_TO_DEPT_INFO_MANAGEMENT_COUNT, dto));
	}

	@Override
	public List<BgabGascz003Dto> selectGridToDeptInfoManagementList(BgabGascz003Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_GRID_TO_DEPT_INFO_MANAGEMENT_LIST, dto);
	}

	@Override
	public int insertListToDeptInfoManagement(List<BgabGascz003Dto> dtoIList) {
		return insert(INSERT_LIST_TO_DEPT_INFO_MANAGEMENT, dtoIList);
	}

	@Override
	public int updateListToDeptInfoManagement(List<BgabGascz003Dto> dtoUList) {
		return update(UPDATE_LIST_TO_DEPT_INFO_MANAGEMENT, dtoUList);
	}

	@Override
	public void deleteListToDeptInfoManagement(List<BgabGascz003Dto> dtoList) {
		delete(DELETE_LIST_TO_DEPT_INFO_MANAGEMENT, dtoList);
	}
	
	@Override
	public int insertApprovalUseYn(BgabGascz031Dto inputDto){
		return insert(INSERT_APPROVAL_USE_YN, inputDto);
	}
	@Override
	public int updateApprovalUseYn(BgabGascz031Dto inputDto){
		return update(UPDATE_APPROVAL_USE_YN, inputDto);
	}

	@Override
	public BgabGascz013Dto selectApprToUseYn(BgabGascz013Dto z013dto) {
		if("VE".equals(z013dto.getApp_type())){
			z013dto.setApp_type("EV");
		}
		return (BgabGascz013Dto)getSqlMapClientTemplate().queryForObject(SELECT_APPR_TO_USEYN, z013dto);
	}

	
	
	@Override
	public BgabGascz033Dto selectLogoMngrToFile(BgabGascz033Dto dto) {
		return (BgabGascz033Dto)getSqlMapClientTemplate().queryForObject(SELECT_LOGO_MNGR_TO_FILE, dto);
	}
	@Override
	public int mergeLogoMngrToFile(BgabGascz033Dto inputDto){
		return insert(MERGE_LOGO_MNGR_TO_FILE, inputDto);
	}
	
	/**
	 * 라이선스 구매이력 저장
	 */
	public int insertXst35Hist(BgabGascz035Dto inDto) {
		return insert(INSERT_XST35_HIST, inDto);
	}
	
	/**
	 * 라이선스 구매이력 총건수
	 */
	public int selectXst35ListCount(BgabGascz035Dto dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_XST35_LIST_COUNT, dto));		
	}
	
	/**
	 * 라이선스 구매이력 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<BgabGascz035Dto> doSearchXst35List(BgabGascz035Dto dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_XST35_LIST, dto);
	}
}