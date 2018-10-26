package com.hncis.system.dao;

import java.util.HashMap;
import java.util.List;

import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.common.vo.BgabGascz005Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonCode;
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

public interface SystemDao {

	public List<BgabGascz004Dto> getSelectMenuListToUserManagement(BgabGascz002Dto bgabGascz002Dto);

	public int updateUserInfoToUserManagement(BgabGascz002Dto bgabGascz002Dto);

	public int getSelectMenuCountToMenuManagement(BgabGascz004Dto bgabGascMenu);

	public List<BgabGascz004Dto> getSelectMenuListToMenuManagement(BgabGascz004Dto bgabGascMenu);

	public int insertListToMenuManagement(List<BgabGascz004Dto> bgabGascMenu);

	public int updateListToMenuManagement (List<BgabGascz004Dto> bgabGascMenu);

	public int deleteListToMenuManagement (List<BgabGascz004Dto> bgabGascMenu);

	public List<TableInfo> getSelectTableListToTableInformation(TableInfo tableInfo);

	public List<TableInfo> getSelectAttributeListToTableInformation(TableInfo tableInfo);

	public List<TableInfo> getSelectIndexListToTableInformation(TableInfo tableInfo);

	public List<BatchInfo> getSelectListToBatchManagement(BatchInfo batchInfo);

	public List<BatchInfo> getSelectParameterListToBatchManagement(BatchInfo batchInfo);

	public int getSelectCountToDeptChangeManagement(BgabGascz006Dto bgabGascDeptChange);

	public List<BgabGascz006Dto> getSelectListToDeptChangeManagement(BgabGascz006Dto vo);

	public int insertToDeptChangeManagement(List<BgabGascz006Dto> list);

	public int updateToDeptChangeManagement(List<BgabGascz006Dto> list);

	public int updateToInsaDeptChangeManagement(List<BgabGascz006Dto> list);

	public int deleteToDeptChangeManagement(List<BgabGascz006Dto> list);

	public int updateBatchInfoToBatchManagement(List<BatchInfo> list);

	public int updateParameterToBatchManagement(List<BatchInfo> list);

	public int updateBatchInfoToBatchManagementByExecute(List<BatchInfo> list);

	public int getSelectCountToManagerManagement(BgabGascz007Dto vo);

	public List<BgabGascz007Dto> getSelectListToManagerManagement(BgabGascz007Dto vo);

	public List<CommonCode> getSelectMenuToComboList(CommonCode code);

	public int getSelectCountToBatchProcessResult(BatchProcess vo);

	public List<BatchProcess> getSelectListToBatchProcessResult(BatchProcess vo);

	public int insertToManagerManagement(List<BgabGascz007Dto> list);

	public String getSelectInfoToManagerManagementAuth(String xdsmEmpno);

	public String getSelectInfoToManagerManagementMenuMgrpCd(BgabGascz007Dto vo);

	public int updateToManagerManagementXusrAuth(BgabGascz002Dto xusr);

	public int updateToManagerManagement(List<BgabGascz007Dto> list);

	public int deleteToManagerManagement(List<BgabGascz007Dto> list);

	public List<BgabGasckcalDto> getSelectListToCalendarManagement(BgabGasckcalDto dto);

	public int getSelectCountToCalendarManagement(BgabGasckcalDto vo);

	public int insertToCalendarManagement(List<BgabGasckcalDto> list);

	public int updateToCalendarManagement(List<BgabGasckcalDto> list);

	public int getSelectCountToMyApproval(CommonApproval vo);

	public List<CommonApproval> getSelectListToMyApproval(CommonApproval vo);

	public int updateToMyApprovalForDepute(BgabGascz008Dto vo);

	public int updateToMyApprovalForDeputeByApprovalInfo(BgabGascz008Dto vo);
	public int updateToMyApprovalForDeputeByApprovalInfoDetail(BgabGascz008Dto vo);

	public int updateToMyApprovalForDeputeByRestore(BgabGascz008Dto vo);

	public String getSelectToMyApprovalForDepute(BgabGascz008Dto vo);

	public BgabGascz008Dto getSelectToMyApprovalUserInfo(BgabGascz008Dto vo);

	public int getSelectCountReaderManagement(BgabGascz008Dto dto);
	public List<BgabGascz008Dto> getSelectReaderManagement(BgabGascz008Dto dto);

	public int updateReaderManagement(BgabGascz008Dto dto);

	public int getSelectCountCodeManagement(BgabGascz005Dto vo);
	public List<BgabGascz005Dto> getSelectCodeManagement(BgabGascz005Dto dto);

	public int insertListToCodeManagement(List<BgabGascz005Dto> dto);
	public int updateListToCodeManagement(List<BgabGascz005Dto> dto);
	public int deleteListToCodeManagement(List<BgabGascz005Dto> dto);

	public String getSelectBackgroundImage(String corp_cd);

	public List<BgabGascz005Dto> getSelectBackgroundImageAll(BgabGascz005Dto dto);

	public int updateToBackgroundImage(BgabGascz005Dto vo);
	public int updateToBackgroundImageApplyY(BgabGascz005Dto vo);
	public int updateToBackgroundImageApplyN(BgabGascz005Dto vo);

	public List<DashBoard> getSelectChartToDashBoardEm(DashBoard code);
	public List<DashBoard> getSelectChartToDashBoardBt(DashBoard code);
	public List<DashBoard> getSelectChartToDashBoardBv(DashBoard code);
	public List<DashBoard> getSelectChartToDashBoardPs(DashBoard code);

	public String getSelectCountBackgroundImageAll(BgabGascz005Dto vo);

	public int insertToBackgroundImage(BgabGascz005Dto vo);

	public int deleteToBackgroundImage(BgabGascz005Dto vo);

	public List<BgabGascz005Dto> getSelectSwitchToBudgetCheckList();

	public BgabGascz005Dto getSelectSwitchToBudgetCheck(BgabGascz005Dto bgabGascz005Dto);

	public int saveSwitchToBudgetCheck(List<BgabGascz005Dto> bgabGascz005Dto);

	public int getSelectCountCoordiManagement(BgabGascz003Dto paramDto);

	public List<BgabGascz003Dto> getSelectCoordiManagement(BgabGascz003Dto paramDto);

	public int updateCoordiManagement(BgabGascz003Dto bgabGascz003Dto);

	public int getSelectCountCoordiDept(String crdDcd);

	public int updateCoordiInfoApp(BgabGascz003Dto bgabGascz003Dto);

	public BgabGascz008Dto getSelectDeptInfoApp(String xorg_orga_c);

	public int updateCoordiInfoAppForInsert(BgabGascz003Dto bgabGascz003Dto);

	public int insertCoordiInfoApp(BgabGascz008Dto bgabGascz008Dto);

	public int updateCoordiInfoAppForDelete(BgabGascz008Dto bgabGascz008Dto);

	public int deleteCoordiInfoApp(BgabGascz003Dto bgabGascz003Dto);

	public int selectCountToApproveLevelManagement(BgabGascz013Dto bgabGascz013Dto);
	public List<BgabGascz013Dto> selectListToApproveLevelManagement(BgabGascz013Dto bgabGascz013Dto);
	public String selectMaxSeqToApproveLevelManagement(BgabGascz013Dto bgabGascz013Dto);
	public int insertListToApproveLevelManagement(List<BgabGascz013Dto> bgabGascz013Dto);
	public int updateListToApproveLevelManagement(List<BgabGascz013Dto> bgabGascz013Dto);
	public int deleteListToApproveLevelManagement(List<BgabGascz013Dto> bgabGascz013Dto);

	/**
	 * vendor management.
	 */
	public int getSelectCountVendorManagement(BgabGascz014Dto param);
	public List<BgabGascz014Dto> getSelectVendorManagement(BgabGascz014Dto param);
	public int insertListToVendorManagement(List<BgabGascz014Dto> dto);
	public int updateListToVendorManagement(List<BgabGascz014Dto> dto);
	public int deleteListToVendorManagement(List<BgabGascz014Dto> dto);
	public int selectToVendorManagementData(BgabGascz014Dto dto);

	/**
	 * PurchaseOrder management.
	 */
	public int getSelectCountPurchaseOrderManagement(BgabGascz015Dto param);
	public List<BgabGascz015Dto> getSelectPurchaseOrderManagement(BgabGascz015Dto param);
	public int insertListToPurchaseOrderManagement(List<BgabGascz015Dto> dto);
	public int updateListToPurchaseOrderManagement(List<BgabGascz015Dto> dto);
	public int deleteListToPurchaseOrderManagement(List<BgabGascz015Dto> dto);
	public List<CommonCode> getSelectPurchaseOrderCombo(CommonCode code);

	/**
	 * restrant menu management
	 */
	public List<BgabGascz019Dto> doSearchByDateMenu(BgabGascz019Dto param);
	public List<BgabGascz019Dto> doSearchByBrasilanMenu(BgabGascz019Dto param);
	public List<BgabGascz019Dto> doSearchByKoreanMenu(BgabGascz019Dto param);
	public List<BgabGascz019Dto> doSearchByCoffee(BgabGascz019Dto param);
	public int doInsertByBrasilanMenu(List<BgabGascz019Dto> gsSaveVo);
	public int doUpdateByBrasilanMenu(List<BgabGascz019Dto> gsModifyVo);
	public int doInsertByKoreanMenu(List<BgabGascz019Dto> gsSaveVo);
	public int doUpdateByKoreanMenu(List<BgabGascz019Dto> gsModifyVo);
	public int doInsertByCoffee(List<BgabGascz019Dto> gsSaveVo);
	public int doUpdateByCoffee(List<BgabGascz019Dto> gsModifyVo);
	public int doDeleteByBrasilanMenu(List<BgabGascz019Dto> gsDelVo);
	public int doDeleteByKoreanMenu(List<BgabGascz019Dto> gsDelVo);
	public int doDeleteByCoffee(List<BgabGascz019Dto> gsDelVo);

	public List<CommonCode> getSelectVendorCodeCombo(CommonCode code);


	/**
	 * Material management.
	 */
	public int getSelectCountMaterialManagement(BgabGascz016Dto param);
	public List<BgabGascz016Dto> getSelectMaterialManagement(BgabGascz016Dto param);
	public int insertListToMaterialManagement(List<BgabGascz016Dto> dto);
	public int updateListToMaterialManagement(List<BgabGascz016Dto> dto);
	public int deleteListToMaterialManagement(List<BgabGascz016Dto> dto);
	public int selectToMaterialManagementData(BgabGascz016Dto dto);

	public BgabGascz002Dto getSelectUserWorkPlace(BgabGascz002Dto dto);
	public BgabGascz005Dto getSelectCheckBudgetSwitch(BgabGascz005Dto dto);

	public BgabGascz002Dto getSelectUserInfoDetailPopup(BgabGascz002Dto bgabGascz002Dto);

	int deleteUserInfoTempByExcelUpload(BgabGascz002Dto bgabGascz002Dto);

	int deleteUserInfoByExcelUpload(BgabGascz002Dto bgabGascz002Dto);

	int insertUserInfoToUserInfoTemp(BgabGascz002Dto bgabGascz002Dto);

	int insertUserInfoByExcelUpload(List<BgabGascz002Dto> bgabGascz002Dto);

	int insertDeptInfoByExcelUpload(List<BgabGascz003Dto> bgabGascz003Dto);

	int insertDeptInfoToDeptInfoTemp(BgabGascz003Dto bgabGascz003Dto);

	int deleteDeptInfoTempByExcelUpload(BgabGascz003Dto bgabGascz003Dto);

	int deleteDeptInfoByExcelUpload(BgabGascz003Dto bgabGascz003Dto);

	int selectXst30InfoListCount(BgabGascz030Dto dto);

	List<BgabGascz030Dto> selectXst30InfoList(BgabGascz030Dto dto);

	BgabGascz030Dto selectMenuInfo(BgabGascz030Dto dto);
	
	int updateMenuStatus(BgabGascz031Dto dto);

	int updateXst30PgsStCd(BgabGascz030Dto dto);
	
	int updateXst30ToApprUseFlag(BgabGascz030Dto dto);

	void validateBgabGascz002(HashMap<String, String> map);

	void createBgabGascz002(HashMap<String, String> map);

	void validateBgabGascz003(HashMap<String, String> map);

	void createBgabGascz003(HashMap<String, String> map);
	
	void validateBgabGascz005(HashMap<String, String> map);
	
	void createBgabGascz005(HashMap<String, String> map);

	void validateBgabGascz007(HashMap<String, String> map);

	void createBgabGascz007(HashMap<String, String> map);

	void validateBgabGascz008(HashMap<String, String> map);

	void createBgabGascz008(HashMap<String, String> map);

	void validateBgabGascz009(HashMap<String, String> map);

	void createBgabGascz009(HashMap<String, String> map);

	void validateBgabGascz010(HashMap<String, String> map);

	void createBgabGascz010(HashMap<String, String> map);

	void validateBgabGascz011(HashMap<String, String> map);

	void createBgabGascz011(HashMap<String, String> map);

	void validateBgabGascz012(HashMap<String, String> map);

	void createBgabGascz012(HashMap<String, String> map);
	
	void validateBgabGascz013(HashMap<String, String> map);

	void createBgabGascz013(HashMap<String, String> map);
	
	void validateBgabGascCal(HashMap<String, String> map);

	void createBgabGascCal(HashMap<String, String> map);
	
	void validateBgabGascz032(HashMap<String, String> map);

	void createBgabGascz032(HashMap<String, String> map);
	
	void insertBgabGascz032(HashMap<String, String> map);

	void validateBgabGascRC01(HashMap<String, String> map);

	void createBgabGascRC01(HashMap<String, String> map);

	void validateBgabGascRC02(HashMap<String, String> map);

	void createBgabGascRC02(HashMap<String, String> map);

	void validateBgabGascRC03(HashMap<String, String> map);

	void createBgabGascRC03(HashMap<String, String> map);

	void validateBgabGascRC04(HashMap<String, String> map);

	void createBgabGascRC04(HashMap<String, String> map);

	void validateBgabGascRC05(HashMap<String, String> map);

	void createBgabGascRC05(HashMap<String, String> map);

	void validateBgabGascRC06(HashMap<String, String> map);

	void createBgabGascRC06(HashMap<String, String> map);

	void validateBgabGascAF01(HashMap<String, String> map);

	void createBgabGascAF01(HashMap<String, String> map);

	void validateBgabGascAF02(HashMap<String, String> map);

	void createBgabGascAF02(HashMap<String, String> map);

	void validateBgabGascAF03(HashMap<String, String> map);

	void createBgabGascAF03(HashMap<String, String> map);

	void validateBgabGascAF04(HashMap<String, String> map);

	void createBgabGascAF04(HashMap<String, String> map);

	void validateBgabGascAF05(HashMap<String, String> map);

	void createBgabGascAF05(HashMap<String, String> map);

	void validateBgabGascGF01(HashMap<String, String> map);

	void createBgabGascGF01(HashMap<String, String> map);

	void validateBgabGascGF02(HashMap<String, String> map);

	void createBgabGascGF02(HashMap<String, String> map);

	void validateBgabGascGF03(HashMap<String, String> map);

	void createBgabGascGF03(HashMap<String, String> map);

	void validateBgabGascGF04(HashMap<String, String> map);

	void createBgabGascGF04(HashMap<String, String> map);

	void validateBgabGascGF05(HashMap<String, String> map);

	void createBgabGascGF05(HashMap<String, String> map);

	void validateBgabGascGFExcelTemp(HashMap<String, String> map);

	void createBgabGascGFExcelTemp(HashMap<String, String> map);

	void validateBgabGascBK01(HashMap<String, String> map);

	void createBgabGascBK01(HashMap<String, String> map);

	void validateBgabGascBK02(HashMap<String, String> map);

	void createBgabGascBK02(HashMap<String, String> map);

	void validateBgabGascBK03(HashMap<String, String> map);

	void createBgabGascBK03(HashMap<String, String> map);

	void validateBgabGascBK04(HashMap<String, String> map);

	void createBgabGascBK04(HashMap<String, String> map);

	void validateBgabGascBKExcelTemp(HashMap<String, String> map);

	void createBgabGascBKExcelTemp(HashMap<String, String> map);

	void validateBgabGascTR01(HashMap<String, String> map);

	void createBgabGascTR01(HashMap<String, String> map);

	void validateBgabGascBA01(HashMap<String, String> map);

	void createBgabGascBA01(HashMap<String, String> map);

	void validateBgabGascBA02(HashMap<String, String> map);

	void createBgabGascBA02(HashMap<String, String> map);

	void validateBgabGascBA03(HashMap<String, String> map);

	void createBgabGascBA03(HashMap<String, String> map);

	void validateBgabGascGS01(HashMap<String, String> map);

	void createBgabGascGS01(HashMap<String, String> map);

	void validateBgabGascGS02(HashMap<String, String> map);

	void createBgabGascGS02(HashMap<String, String> map);

	void validateBgabGascGS03(HashMap<String, String> map);

	void createBgabGascGS03(HashMap<String, String> map);

	void validateBgabGascGS04(HashMap<String, String> map);

	void createBgabGascGS04(HashMap<String, String> map);

	void validateBgabGascOS01(HashMap<String, String> map);

	void createBgabGascOS01(HashMap<String, String> map);

	void validateBgabGascOS02(HashMap<String, String> map);

	void createBgabGascOS02(HashMap<String, String> map);

	void validateBgabGascOS03(HashMap<String, String> map);

	void createBgabGascOS03(HashMap<String, String> map);

	void validateBgabGascOS04(HashMap<String, String> map);

	void createBgabGascOS04(HashMap<String, String> map);

	void validateBgabGascBT01(HashMap<String, String> map);

	void createBgabGascBT01(HashMap<String, String> map);

	void validateBgabGascBT02(HashMap<String, String> map);

	void createBgabGascBT02(HashMap<String, String> map);

	void validateBgabGascBT03(HashMap<String, String> map);

	void createBgabGascBT03(HashMap<String, String> map);

	void validateBgabGascBT04(HashMap<String, String> map);

	void createBgabGascBT04(HashMap<String, String> map);

	void validateBgabGascBT05(HashMap<String, String> map);

	void createBgabGascBT05(HashMap<String, String> map);

	void validateBgabGascBT06(HashMap<String, String> map);

	void createBgabGascBT06(HashMap<String, String> map);

	void validateBgabGascBT07(HashMap<String, String> map);

	void createBgabGascBT07(HashMap<String, String> map);

	void validateBgabGascBT08(HashMap<String, String> map);

	void createBgabGascBT08(HashMap<String, String> map);

	void validateBgabGascBT09(HashMap<String, String> map);

	void createBgabGascBT09(HashMap<String, String> map);

	void validateBgabGascPS01(HashMap<String, String> map);

	void createBgabGascPS01(HashMap<String, String> map);

	void validateBgabGascPS02(HashMap<String, String> map);

	void createBgabGascPS02(HashMap<String, String> map);

	void validateBgabGascPS03(HashMap<String, String> map);

	void createBgabGascPS03(HashMap<String, String> map);

	void validateBgabGascPS04(HashMap<String, String> map);

	void createBgabGascPS04(HashMap<String, String> map);
	
	void validateBgabGascTX01(HashMap<String, String> map);

	void createBgabGascTX01(HashMap<String, String> map);

	void validateBgabGascTX02(HashMap<String, String> map);

	void createBgabGascTX02(HashMap<String, String> map);

	void validateBgabGascTX03(HashMap<String, String> map);

	void createBgabGascTX03(HashMap<String, String> map);

	void validateBgabGascTX04(HashMap<String, String> map);

	void createBgabGascTX04(HashMap<String, String> map);

	void validateBgabGascBV01(HashMap<String, String> map);

	void createBgabGascBV01(HashMap<String, String> map);

	void validateBgabGascBV02(HashMap<String, String> map);

	void createBgabGascBV02(HashMap<String, String> map);

	void validateBgabGascBV03(HashMap<String, String> map);

	void createBgabGascBV03(HashMap<String, String> map);

	void validateBgabGascBV04(HashMap<String, String> map);

	void createBgabGascBV04(HashMap<String, String> map);

	void validateBgabGascFC01(HashMap<String, String> map);

	void createBgabGascFC01(HashMap<String, String> map);

	void validateBgabGascFC02(HashMap<String, String> map);

	void createBgabGascFC02(HashMap<String, String> map);

	void validateBgabGascVL01(HashMap<String, String> map);

	void createBgabGascVL01(HashMap<String, String> map);

	void validateBgabGascSE01(HashMap<String, String> map);

	void createBgabGascSE01(HashMap<String, String> map);

	void validateBgabGascSE02(HashMap<String, String> map);

	void createBgabGascSE02(HashMap<String, String> map);

	void validateBgabGascSE03(HashMap<String, String> map);

	void createBgabGascSE03(HashMap<String, String> map);

	void validateBgabGascSE04(HashMap<String, String> map);

	void createBgabGascSE04(HashMap<String, String> map);

	void validateBgabGascSE05(HashMap<String, String> map);

	void createBgabGascSE05(HashMap<String, String> map);

	void validateBgabGascSE06(HashMap<String, String> map);

	void createBgabGascSE06(HashMap<String, String> map);

	void validateBgabGascSE07(HashMap<String, String> map);

	void createBgabGascSE07(HashMap<String, String> map);

	void validateBgabGascRM01(HashMap<String, String> map);

	void createBgabGascRM01(HashMap<String, String> map);

	void validateBgabGascRM02(HashMap<String, String> map);

	void createBgabGascRM02(HashMap<String, String> map);

	void validateBgabGascRM03(HashMap<String, String> map);

	void createBgabGascRM03(HashMap<String, String> map);

	void validateBgabGascRM04(HashMap<String, String> map);

	void createBgabGascRM04(HashMap<String, String> map);

	void validateBgabGascCE01(HashMap<String, String> map);

	void createBgabGascCE01(HashMap<String, String> map);

	void validateBgabGascLV01(HashMap<String, String> map);

	void createBgabGascLV01(HashMap<String, String> map);

	void validateBgabGascLV02(HashMap<String, String> map);

	void createBgabGascLV02(HashMap<String, String> map);

	void validateBgabGascLV03(HashMap<String, String> map);

	void createBgabGascLV03(HashMap<String, String> map);

	void validateBgabGascBD01(HashMap<String, String> map);

	void createBgabGascBD01(HashMap<String, String> map);

	void validateBgabGascSB01(HashMap<String, String> map);

	void createBgabGascSB01(HashMap<String, String> map);

	void validateBgabGascBS02(HashMap<String, String> map);

	void createBgabGascSB02(HashMap<String, String> map);
	
	void validateBgabGascFJ01(HashMap<String, String> map);
	
	void createBgabGascFJ01(HashMap<String, String> map);
	
	void validateBgabGascFJ02(HashMap<String, String> map);
	
	void createBgabGascFJ02(HashMap<String, String> map);
	
	void validateBgabGascFJ03(HashMap<String, String> map);
	
	void createBgabGascFJ03(HashMap<String, String> map);
	
	void validateBgabGascPD01(HashMap<String, String> map);
	
	void createBgabGascPD01(HashMap<String, String> map);
	
	void validateBgabGascPD02(HashMap<String, String> map);
	
	void createBgabGascPD02(HashMap<String, String> map);
	
	void validateBgabGascPD03(HashMap<String, String> map);
	
	void createBgabGascPD03(HashMap<String, String> map);
	
	int insertUserInfo(BgabGascz002Dto bgabGascz002Dto);
	
	public int insertBgabGascrc01(HashMap<String, String> map);
	public int insertBgabGascrc02(HashMap<String, String> map);
	public int insertBgabGascrc03(HashMap<String, String> map);
	public int insertBgabGascrc04(HashMap<String, String> map);
	public int insertBgabGascrc05(HashMap<String, String> map);
	public int insertBgabGascaf02(HashMap<String, String> map);
	public int insertBgabGascaf03(HashMap<String, String> map);
	public int insertBgabGascgf01(HashMap<String, String> map);
	public int insertBgabGascgf03(HashMap<String, String> map);
	public int insertBgabGascgf04(HashMap<String, String> map);
	public int insertBgabGascgf05(HashMap<String, String> map);
	public int insertBgabGascbk01(HashMap<String, String> map);
	public int insertBgabGascbk03(HashMap<String, String> map);
	public int insertBgabGascbk04(HashMap<String, String> map);
	public int insertBgabGascba03(HashMap<String, String> map);
	public int insertBgabGascgs02(HashMap<String, String> map);
	public int insertBgabGascos02(HashMap<String, String> map);
	public int insertBgabGascbt06(HashMap<String, String> map);
	public int insertBgabGascps03(HashMap<String, String> map);
	public int insertBgabGascfc02(HashMap<String, String> map);
	public int insertBgabGascrm03(HashMap<String, String> map);
	public int insertBgabGasclv03(HashMap<String, String> map);
	public int insertBgabGascz011(HashMap<String, String> map);
	public int insertBgabGascz005(HashMap<String, String> map);
	public int insertBgabGascBv01(HashMap<String, String> map);
	public int insertBgabGascSb02(HashMap<String, String> map);
	public int insertBgabGascFj02(HashMap<String, String> map);
	public int insertBgabGascFj03(HashMap<String, String> map);
	
	public int insertBgabGascPd03(HashMap<String, String> map);
	public int insertBgabGascPd04(HashMap<String, String> map);
	
	public List<BgabGascz002Dto> selectUserInfoList(BgabGascz002Dto delDto);

	public List<BgabGascz003Dto> selectDeptInfoList(BgabGascz003Dto delDto);

	public int deleteAprvInfo(BgabGascz003Dto delDto);

	public int insertAprvInfo(List<BgabGascz008Dto> z008List);

	public int selectGridToDeptInfoManagementCount(BgabGascz003Dto dto);

	public List<BgabGascz003Dto> selectGridToDeptInfoManagementList(BgabGascz003Dto dto);

	public int insertListToDeptInfoManagement(List<BgabGascz003Dto> dtoIList);

	public int updateListToDeptInfoManagement(List<BgabGascz003Dto> dtoUList);

	public void deleteListToDeptInfoManagement(List<BgabGascz003Dto> dtoList);

	int insertApprovalUseYn(BgabGascz031Dto inputDto);

	int updateApprovalUseYn(BgabGascz031Dto inputDto);

	public BgabGascz013Dto selectApprToUseYn(BgabGascz013Dto z013dto);
	
	public BgabGascz033Dto selectLogoMngrToFile(BgabGascz033Dto dto);
	
	public int mergeLogoMngrToFile(BgabGascz033Dto inputDto);

	void validateBgabGascPD04(HashMap<String, String> map);

	void createBgabGascPD04(HashMap<String, String> map);

	void validateBgabGascCO01(HashMap<String, String> map);
	
	void createBgabGascCO01(HashMap<String, String> map);
	
	void validateBgabGascCO02(HashMap<String, String> map);
	
	void createBgabGascCO02(HashMap<String, String> map);

	public int selectXst35ListCount(BgabGascz035Dto dto);

	public List<BgabGascz035Dto> doSearchXst35List(BgabGascz035Dto dto);

	public int insertXst35Hist(BgabGascz035Dto inDto);

}