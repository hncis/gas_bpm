package com.hncis.system.manager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.common.vo.BgabGascz005Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonMessage;
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

@Transactional
public interface SystemManager {

	/**
	 * system userManagement menu SELECT - 사용자 별 메뉴 정보 조회
	 * @return List<HgazGascMenu>
	 */
	public List<BgabGascz004Dto> getSelectMenuListToUserManagement(BgabGascz002Dto bgabGascz002Dto);

	/**
	 * system userManagement UPDATE - 사용자 별 메뉴 정보 수정
	 * @param bgabGascz002Dto- 조건
	 * @return int
	 */
	public int updateUserInfoToUserManagement(BgabGascz002Dto bgabGascz002Dto);

	/**
	 * system userManagement menu SELECT - 사용자 별 메뉴 정보 갯수 조회
	 * @param bgabGascMenu- 조회조건
	 * @return int
	 */
	public int getSelectMenuCountToMenuManagement(BgabGascz004Dto bgabGascMenu);
	/**
	 * system userManagement menu SELECT - 메뉴 정보 조회
	 * @param bgabGascMenu- 조회조건
	 * @return List<BgabGascMenu>
	 */
	public List<BgabGascz004Dto> getSelectMenuListToMenuManagement(BgabGascz004Dto bgabGascMenu);

	/**
	 * system menuManagement menu INSERT - 메뉴 정보 입력
	 * @param bgabGascMenu- 조건
	 * @return int
	 */
	public int saveListToMenuManagement(List<BgabGascz004Dto> iBgabGascMenu, List<BgabGascz004Dto> uBgabGascMenu);

	/**
	 * system menuManagement menu UPDATE - 메뉴 정보 수정
	 * @param bgabGascMenu- 조건
	 * @return int
	 */
	public int updateListToMenuManagement(List<BgabGascz004Dto> bgabGascMenu);

	/**
	 * system menuManagement menu DELETE - 메뉴 정보 삭제
	 * @param bgabGascMenu- 조건
	 * @return int
	 */
	public int deleteListToMenuManagement(List<BgabGascz004Dto> bgabGascMenu);

	/**
	 * system tableInformation table information SELECT - 테이블 정보 조회
	 * @param TableInfo- 조회조건
	 * @return List<TableInfo>
	 */
	public List<TableInfo> getSelectTableListToTableInformation(TableInfo tableInfo);

	/**
	 * system tableInformation attribute SELECT - 테이블 컬럼 정보 조회
	 * @param TableInfo- 조회조건
	 * @return List<TableInfo>
	 */
	public List<TableInfo> getSelectAttributeListToTableInformation(TableInfo tableInfo);

	/**
	 * system tableInformation index SELECT - 테이블 인덱스 정보 조회
	 * @param TableInfo- 조회조건
	 * @return List<TableInfo>
	 */
	public List<TableInfo> getSelectIndexListToTableInformation(TableInfo tableInfo);

	/**
	 * system batchManagement batch SELECT - 배치 정보 조회
	 * @param BatchInfo- 조회조건
	 * @return List<BatchInfo>
	 */
	public List<BatchInfo> getSelectListToBatchManagement(BatchInfo batchInfo);

	/**
	 * system batchManagement batch SELECT - 배치 파라미터 정보 조회
	 * @param BatchInfo- 조회조건
	 * @return List<BatchInfo>
	 */
	public List<BatchInfo> getSelectParameterListToBatchManagement(BatchInfo batchInfo);

	/**
	 * system deptChangeManagement COUNT - 부서 갯수 조회
	 * @param vo- 조회조건
	 * @return List<BgabGascDeptChange>
	 */
	public int getSelectCountToDeptChangeManagement(BgabGascz006Dto vo);
	/**
	 * system deptChangeManagement SELECT - 부서 정보 조회
	 * @param vo- 조회조건
	 * @return List<BgabGascDeptChange>
	 */
	public List<BgabGascz006Dto> getSelectListToDeptChangeManagement(BgabGascz006Dto vo);
	/**
	 * system deptChangeManagement INSERT - 부서 정보 입력
	 * @param list- 조건
	 * @return List<BgabGascDeptChange>
	 */
	public int insertToDeptChangeManagement(List<BgabGascz006Dto> iList, List<BgabGascz006Dto> uList);
	/**
	 * system deptChangeManagement UPDATE - 부서 정보 수정
	 * @param list- 조건
	 * @return List<BgabGascDeptChange>
	 */
	public int updateToDeptChangeManagement(List<BgabGascz006Dto> list);
	/**
	 * system deptChangeManagement DELETE - 부서 정보 삭제
	 * @param list- 조건
	 * @return List<BgabGascDeptChange>
	 */
	public int deleteToDeptChangeManagement(List<BgabGascz006Dto> list);

	/**
	 * system batchManagement batchInfo UPDATE - 배치 정보 수정
	 * @param list- 조건
	 * @return int
	 */
	public int updateBatchInfoToBatchManagement(List<BatchInfo> list);

	/**
	 * system batchManagement parameter UPDATE - 배치 파라미터 수정
	 * @param list- 조건
	 * @return int
	 */
	public int updateParameterToBatchManagement(List<BatchInfo> list);

	/**
	 * system batchManagement batch execute UPDATE - 배치 수동 실행
	 * @param list- 조건
	 * @return int
	 */
	public int updateBatchInfoToBatchManagementByExecute(List<BatchInfo> list);
	/**
	 * system ManagerManagement COUNT - Manager 갯수 조회
	 * @param dto- 조회조건
	 * @return int
	 */
	public int getSelectCountToManagerManagement(BgabGascz007Dto dto);
	/**
	 * system ManagerManagement SELECT - Manager 정보 조회
	 * @param dto- 조회조건
	 * @return List<BgabGascManager>
	 */
	public List<BgabGascz007Dto> getSelectListToManagerManagement(BgabGascz007Dto dto);
	/**
	 * system combo menu - 검색조건 combo 데이터 조회
	 * @param code- 조회조건
	 * @return List<CommonCode>
	 */
	public List<CommonCode> getSelectMenuToComboList(CommonCode code);
	/**
	 * system BatchProcessResult COUNT - Batch Process result 갯수 조회
	 * @param dto- 조회조건
	 * @return int
	 */
	public int getSelectCountToBatchProcessResult(BatchProcess dto);
	/**
	 * system BatchProcessResult List - Batch Process result 목록 조회
	 * @param dto- 조회조건
	 * @return List<BatchProcess>
	 */
	public List<BatchProcess> getSelectListToBatchProcessResult(BatchProcess dto);
	/**
	 * Manager Management insert - Manager 입력
	 * @param list- 조회조건
	 * @return int
	 */
	public int saveToManagerManagement(List<BgabGascz007Dto> iList, List<BgabGascz007Dto> uList);
	/**
	 * Manager Management select - Manager 권한 조회
	 * @param xdsmEmpno- 조회조건
	 * @return int
	 */
	public String getSelectInfoToManagerManagementAuth(String xdsmEmpno);


	/**
	 * Manager Management update - 메뉴 정보 수정
	 * @param list- 조건
	 * @return int
	 */
	public int updateToManagerManagement(List<BgabGascz007Dto> list);
	/**
	 * Manager Management delete - 메뉴 정보 삭제
	 * @param list- 조건
	 * @return int
	 */
	public int deleteToManagerManagement(List<BgabGascz007Dto> list);

	public List<BgabGasckcalDto> getSelectListToCalendarManagement(BgabGasckcalDto dto);
	/**
	 * Calendar Management Count
	 * @param dto- 조회조건
	 * @return int
	 */
	public int getSelectCountToCalendarManagement(BgabGasckcalDto dto);
	/**
	 * Calendar Management insert
	 * @param dto- 조건
	 * @return int
	 */
	public int insertToCalendarManagement(List<BgabGasckcalDto> list);
	/**
	 * Calendar Management update
	 * @param list- 조건
	 * @return int
	 */
	public int updateToCalendarManagement(List<BgabGasckcalDto> list);

	/**
	 * 결재 개수 조회
	 * @param dto- 조회조건
	 * @return int
	 */
	public int getSelectCountToMyApproval(CommonApproval dto);

	/**
	 * 결재 상세 정보 조회
	 * @param approvalList- 조회조건
	 * @param req
	 * @return CommonApproval
	 * @throws SessionException
	 */
	public CommonApproval doApproveToMyApproval(List<CommonApproval> approvalList,HttpServletRequest req) throws SessionException;

	/**
	 * 결재 목록 조회
	 * @param dto- 조회조건
	 * @return List<CommonApproval>
	 */
	public List<CommonApproval> getSelectListToMyApproval(CommonApproval dto);
	/**
	 * My Approval For Depute update - 위임, 현재 사용하지 않음
	 * @param vo- 조건
	 * @return int
	 */
	public int updateToMyApprovalForDepute(BgabGascz008Dto vo);
	/**
	 * My Approval For Depute restore - 위임 복원, 현재 사용하지 않음
	 * @param vo- 조건
	 * @return int
	 */
	public int updateToMyApprovalForDeputeByRestore(BgabGascz008Dto vo);
	/**
	 * My Approval User Info - 결재 사용자 정보 조회
	 * @param vo- 조회조건
	 * @return int
	 */
	public BgabGascz008Dto getSelectToMyApprovalUserInfo(BgabGascz008Dto vo);


	/**
	 * Gets the select count reader management. - 리더 갯수 조회
	 *
	 * @param dto the dto - 조회조건
	 * @return the select count reader management
	 */
	public int getSelectCountReaderManagement(BgabGascz008Dto dto);

	/**
	 * Gets the select reader management. - 리더 조회
	 *
	 * @param dto the dto- 조회조건
	 * @return the select reader management
	 */
	public List<BgabGascz008Dto> getSelectReaderManagement(BgabGascz008Dto dto);

	/**
	 * Update reader management. - 해당 부서의 리더 수정
	 *
	 * @param dtoList the dto list
	 * @return the int
	 */
	public int updateReaderManagement(List<BgabGascz008Dto> dtoList);

	/**
	 * Gets the select count code management. -  코드 갯수 조회
	 *
	 * @param vo the vo- 조건
	 * @return the select count code management
	 */
	public int getSelectCountCodeManagement(BgabGascz005Dto vo);

	/**
	 * Gets the select code management. - 코드 목록 조회
	 *
	 * @param dto the dto- 조건
	 * @return the select code management
	 */
	public List<BgabGascz005Dto> getSelectCodeManagement(BgabGascz005Dto dto);

	/**
	 * Insert list to code management. - 코드 입력
	 *
	 * @param dto the dto - 조건
	 * @return the int
	 */
	public int insertListToCodeManagement(List<BgabGascz005Dto> dtoI, List<BgabGascz005Dto> dtoU);

	/**
	 * Update list to code management. - 코드 수정
	 *
	 * @param dto the dto- 조건
	 * @return the int
	 */
	public int updateListToCodeManagement(List<BgabGascz005Dto> dto);

	/**
	 * Delete list to code management. - 코드 삭제
	 *
	 * @param dto the dto- 조건
	 * @return the int
	 */
	public int deleteListToCodeManagement(List<BgabGascz005Dto> dto);

	/**
	 * Update to background image. - background image 변경
	 *
	 * @param vo the vo- 조건
	 * @return the int
	 */
	public int updateToBackgroundImage(BgabGascz005Dto vo);

	/**
	 * Update to background image apply.- background image 적용
	 *
	 * @param vo the vo- 조건
	 * @return the int
	 */
	public int updateToBackgroundImageApply(BgabGascz005Dto vo);


	public List<DashBoard> getSelectChartToDashBoardEm(DashBoard code);
	public List<DashBoard> getSelectChartToDashBoardBt(DashBoard code);
	public List<DashBoard> getSelectChartToDashBoardBv(DashBoard code);
	public List<DashBoard> getSelectChartToDashBoardPs(DashBoard code);

	/**
	 * Gets the select count background image all. - background image 갯수 조회
	 *
	 * @param vo the vo- 조회조건
	 * @return the select count background image all
	 */
	public int getSelectCountBackgroundImageAll(BgabGascz005Dto vo);

	/**
	 * Gets the select background image all. - background image 목록 조회
	 *
	 * @param vo the vo- 조회조건
	 * @return the select background image all
	 */
	public List<BgabGascz005Dto> getSelectBackgroundImageAll(BgabGascz005Dto vo);

	/**
	 * Insert to background image. background image 등록
	 *
	 * @param vo the vo- 조건
	 * @param req the req
	 * @param res the res
	 * @return the int
	 */
	public int insertToBackgroundImage(BgabGascz005Dto vo, HttpServletRequest req, HttpServletResponse res);

	/**
	 * Delete to background image apply. background image 삭제
	 *
	 * @param vo the vo- 조건
	 * @return the int
	 */
	public int deleteToBackgroundImageApply(BgabGascz005Dto vo);

	/**
	 * Budget Check search
	 * @param BgabGascz005Dto
	 * @return BgabGascz005Dto
	 */
	public List<BgabGascz005Dto> getSelectSwitchToBudgetCheckList();

	/**
	 * Budget Check save
	 * @param BgabGascz005Dto
	 * @return int
	 */
	public int saveSwitchToBudgetCheck(List<BgabGascz005Dto> bgabGascz005Dto);
	/**
	 * Coordinator Search count
	 * @param BgabGascz003Dto
	 * @return int
	 */
	public int getSelectCountCoordiManagement(BgabGascz003Dto paramDto);
	/**
	 * Coordinator Search list
	 * @param BgabGascz003Dto
	 * @return List<BgabGascz003Dto>
	 */
	public List<BgabGascz003Dto> getSelectCoordiManagement(BgabGascz003Dto paramDto);
	/**
	 * Save Coodinator info
	 * @param BgabGascz003Dto
	 * @return int
	 */
	public CommonMessage updateCoordiManagement(List<BgabGascz003Dto> list, HttpServletRequest req);

	public int selectCountToApproveLevelManagement(BgabGascz013Dto bgabGascz013Dto);
	public List<BgabGascz013Dto> selectListToApproveLevelManagement(BgabGascz013Dto bgabGascz013Dto);
	public int saveListToApproveLevelManagement(List<BgabGascz013Dto> iList, List<BgabGascz013Dto> uList);
	public int deleteListToApproveLevelManagement(List<BgabGascz013Dto> dList);

	/**
	 * vendor management.
	 */
	public int getSelectCountVendorManagement(BgabGascz014Dto param);
	public List<BgabGascz014Dto> getSelectVendorManagement(BgabGascz014Dto param);
	public int insertListToVendorManagement(List<BgabGascz014Dto> dtoI, List<BgabGascz014Dto> dtoU);
	public int deleteListToVendorManagement(List<BgabGascz014Dto> dto);

	/**
	 * PurchaseOrder management.
	 */
	public int getSelectCountPurchaseOrderManagement(BgabGascz015Dto param);
	public List<BgabGascz015Dto> getSelectPurchaseOrderManagement(BgabGascz015Dto param);
	public int insertListToPurchaseOrderManagement(List<BgabGascz015Dto> dtoI, List<BgabGascz015Dto> dtoU);
	public int deleteListToPurchaseOrderManagement(List<BgabGascz015Dto> dto);

	public List<CommonCode> getSelectPurchaseOrderCombo(CommonCode code);

	/**
	 * retrant mgmt
	 */
	public List<BgabGascz019Dto> doSearchByDateMenu(BgabGascz019Dto param);
	public List<BgabGascz019Dto> doSearchByBrasilanMenu(BgabGascz019Dto param);
	public List<BgabGascz019Dto> doSearchByKoreanMenu(BgabGascz019Dto param);
	public List<BgabGascz019Dto> doSearchByCoffee(BgabGascz019Dto param);
	public int doInsertByBrasilanMenu(List<BgabGascz019Dto> gsSaveVo, List<BgabGascz019Dto> gsModifyVo);
	public int doInsertByKoreanMenu(List<BgabGascz019Dto> gsSaveVo, List<BgabGascz019Dto> gsModifyVo);
	public int doInsertByCoffee(List<BgabGascz019Dto> gsSaveVo, List<BgabGascz019Dto> gsModifyVo);
	public int doDeleteByBrasilanMenu(List<BgabGascz019Dto> gsDelVo);
	public int doDeleteByKoreanMenu(List<BgabGascz019Dto> gsDelVo);
	public int doDeleteByCoffee(List<BgabGascz019Dto> gsDelVo);

	public List<CommonCode> getSelectVendorCodeCombo(CommonCode code);

	/**
	 * Material management.
	 */
	public int getSelectCountMaterialManagement(BgabGascz016Dto param);
	public List<BgabGascz016Dto> getSelectMaterialManagement(BgabGascz016Dto param);
	public int insertListToMaterialManagement(List<BgabGascz016Dto> dtoI, List<BgabGascz016Dto> dtoU);
	public int deleteListToMaterialManagement(List<BgabGascz016Dto> dto);

	public BgabGascz002Dto getSelectUserInfoDetailPopup(BgabGascz002Dto bgabGascz002Dto);

	int selectXst30InfoListCount(BgabGascz030Dto dto);

	List<BgabGascz030Dto> selectXst30InfoList(BgabGascz030Dto dto);
	
	BgabGascz030Dto selectMenuInfo(BgabGascz030Dto dto);

	CommonMessage doUpdateXst30IncomeCheck(List<BgabGascz030Dto> list, HttpServletRequest req);

	CommonMessage doUpdateXst30Confirm(List<BgabGascz030Dto> list, HttpServletRequest req);

	CommonMessage doUpdateXst30Reject(List<BgabGascz030Dto> list, HttpServletRequest req);

	boolean createBgabGascz002(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascz003(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascz005(HashMap<String, String> map) throws SQLException;
	
	boolean createBgabGascz007(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascz008(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascz009(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascz010(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascz011(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascz012(HashMap<String, String> map) throws SQLException;
	
	boolean createBgabGascz013(HashMap<String, String> map) throws SQLException;
	
	boolean createBgabGascCal(HashMap<String, String> map) throws SQLException;
	
	boolean createBgabGascz032(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascRC01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascRC02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascRC03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascRC04(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascRC05(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascRC06(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascAF01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascAF02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascAF03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascAF04(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascAF05(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascGF01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascGF02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascGF03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascGF04(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascGF05(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascGFExcelTemp(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBK01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBK02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBK03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBK04(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBKExcelTemp(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascTR01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBA01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBA02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBA03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascGS01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascGS02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascGS03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascGS04(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascOS01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascOS02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascOS03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascOS04(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBT01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBT02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBT03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBT04(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBT05(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBT06(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBT07(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBT08(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBT09(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascPS01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascPS02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascPS03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascPS04(HashMap<String, String> map) throws SQLException;
	
	boolean createBgabGascTX01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascTX02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascTX03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascTX04(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBV01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBV02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBV03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBV04(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascFC01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascFC02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascVL01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascSE01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascSE02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascSE03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascSE04(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascSE05(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascSE06(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascSE07(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascRM01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascRM02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascRM03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascRM04(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascCE01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascLV01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascLV02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascLV03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascBD(HashMap<String, String> map) throws SQLException;

	public int selectGridToDeptInfoManagementCount(BgabGascz003Dto dto);

	public List<BgabGascz003Dto> selectGridToDeptInfoManagementList(BgabGascz003Dto dto);

	public int insertListToDeptInfoManagement(List<BgabGascz003Dto> dtoIList, List<BgabGascz003Dto> dtoUList, HttpServletRequest req);

	public void deleteListToDeptInfoManagement(List<BgabGascz003Dto> dtoList);
	
	void insertApprovalUseYn(BgabGascz031Dto inputDto);
	
	public BgabGascz033Dto getSelectLogoToFile(BgabGascz033Dto bgabGascZ033Dto);
	
	public void saveLogoToFile(HttpServletRequest req, HttpServletResponse res, BgabGascz033Dto bgabGascZ033Dto);

	boolean createBgabGascSB01(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascSB02(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascFJ01(HashMap<String, String> map) throws SQLException;
	
	boolean createBgabGascFJ02(HashMap<String, String> map) throws SQLException;
	
	boolean createBgabGascFJ03(HashMap<String, String> map) throws SQLException;
	
	boolean createBgabGascPD01(HashMap<String, String> map) throws SQLException;
	
	boolean createBgabGascPD02(HashMap<String, String> map) throws SQLException;
	
	boolean createBgabGascPD03(HashMap<String, String> map) throws SQLException;

	boolean createBgabGascPD04(HashMap<String, String> map) throws SQLException;
	
	boolean createBgabGascCO01(HashMap<String, String> map) throws SQLException;
	
	boolean createBgabGascCO02(HashMap<String, String> map) throws SQLException;

	public int selectXst35ListCount(BgabGascz035Dto dto)  throws SQLException;

	public List<BgabGascz035Dto> doSearchXst35List(BgabGascz035Dto dto) throws SQLException;

	public int insertXst35Hist(BgabGascz035Dto inDto) throws SQLException;

//	int udpateApprovalUseYn(BgabGascz031Dto inputDto);
	
	public void doSystemTest()throws Exception;
}