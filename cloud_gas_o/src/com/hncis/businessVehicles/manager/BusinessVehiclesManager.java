package com.hncis.businessVehicles.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.businessTravel.vo.BgabGascbt06;
import com.hncis.businessVehicles.vo.BgabGascbv01Dto;
import com.hncis.businessVehicles.vo.BgabGascbv02Dto;
import com.hncis.businessVehicles.vo.BgabGascbv03Dto;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;

@Transactional
public interface BusinessVehiclesManager {

	int getSelectCountBvToVehicleListForDept(BgabGascbv01Dto dto);
	List<BgabGascbv01Dto> getSelectListBvToVehicleListForDept(BgabGascbv01Dto dto);

	BgabGascbv02Dto insertBvToRequest(BgabGascbv02Dto dto);
	BgabGascbv02Dto getSelectInfoBvToRequest(BgabGascbv02Dto dto);
	int deleteBvToRequest(BgabGascbv02Dto dto);
	int updateInfoBvToConfirm(BgabGascbv02Dto dto, HttpServletRequest req) throws SessionException;
	int updateInfoBvToConfirmCancel(BgabGascbv02Dto dto, HttpServletRequest req) throws SessionException;
	CommonMessage setApproval(BgabGascbv02Dto regDto, HttpServletRequest req);
	CommonMessage setApprovalCancel(BgabGascbv02Dto regDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	String getSelectApprovalInfoByBv(BgabGascbv02Dto rsReqDto);
	BgabGascbv02Dto getSelectInfoBvToRequestForApprove(BgabGascbv02Dto dto);

	int getSelectCountBvToList(BgabGascbv02Dto dto);
	List<BgabGascbv02Dto> getSelectListBvToList(BgabGascbv02Dto dto);

	int getSelectCountBvToCodeManagement(BgabGascbv03Dto dto);
	List<BgabGascbv03Dto> getSelectListBvToCodeManagement(BgabGascbv03Dto dto);
	int insertBvToCodeManagement(List<BgabGascbv03Dto> dto);
	int deleteBvToCodeManagement(List<BgabGascbv03Dto> dto);

	int getSelectCountBvToVehicleList(BgabGascbv01Dto dto);
	List<BgabGascbv01Dto> getSelectListBvToVehicleList(BgabGascbv01Dto dto);

	BgabGascbv01Dto insertBvToVehicleManagement(BgabGascbv01Dto dto);
	BgabGascbv01Dto getSelectInfoBvToVehicleManagement(BgabGascbv01Dto dto);
	int deleteBvToVehicleManagement(BgabGascbv01Dto dto);

	int getSelectCountBvToVehicleRegInfo(BgabGascbv01Dto dto);
	List<BgabGascbv01Dto> getSelectListBvToVehicleRegInfo(BgabGascbv01Dto dto);

	void saveBvToFile(HttpServletRequest req, HttpServletResponse res,BgabGascZ011Dto bgabGascZ011Dto);
	List<BgabGascZ011Dto> getSelectBvToFile(BgabGascZ011Dto btReqDto);
	BgabGascZ011Dto getSelectBvToFileInfo(BgabGascZ011Dto btReqDto);
	int deleteBvToFile(List<BgabGascZ011Dto> bgabGascZ011Dto);
	public int updateInfoBvToReject (BgabGascbv02Dto dto, HttpServletRequest req) throws SessionException;

	public void saveMaintenanceToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectMaintenanceToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectMaintenanceToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteMaintenanceToFile(List<BgabGascZ011Dto> bgabGascZ011IList);

	public int getSelectCountMaintenanceExpenseManagement(BgabGascbt06 bgabGascbt06);
	public List<BgabGascbt06> getSelectMaintenanceExpenseManagement(BgabGascbt06 bgabGascbt06);
	public int saveListToMaintenanceExpenseManagement(List<BgabGascbt06> dtoI, List<BgabGascbt06> dtoU);
	public int deleteListToMaintenanceExpenseManagement(List<BgabGascbt06> dtoD);
}
