package com.hncis.businessVehicles.dao;

import java.util.List;

import com.hncis.businessTravel.vo.BgabGascbt06;
import com.hncis.businessVehicles.vo.BgabGascbv01Dto;
import com.hncis.businessVehicles.vo.BgabGascbv02Dto;
import com.hncis.businessVehicles.vo.BgabGascbv03Dto;
import com.hncis.common.vo.BgabGascZ011Dto;

public interface BusinessVehiclesDao {

	int getSelectCountBvToVehicleListForDept(BgabGascbv01Dto dto);
	List<BgabGascbv01Dto> getSelectListBvToVehicleListForDept(BgabGascbv01Dto dto);
	
	int insertBvToRequest(BgabGascbv02Dto dto);
	BgabGascbv02Dto getSelectInfoBvToRequest(BgabGascbv02Dto dto);
	int deleteBvToRequest(BgabGascbv02Dto dto);
	int updateInfoBvToConfirm(BgabGascbv02Dto dto);
	int updateInfoBvToConfirmCancel(BgabGascbv02Dto dto);
	int updateInfoBvToApproval(BgabGascbv02Dto dto);
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
	
	
	int getSelectBvToCheckVehicle(BgabGascbv01Dto dto);
	int insertBvToVehicleManagement(BgabGascbv01Dto dto);
	BgabGascbv01Dto getSelectInfoBvToVehicleManagement(BgabGascbv01Dto dto);
	int deleteBvToVehicleManagement(BgabGascbv01Dto dto);
	
	int getSelectCountBvToVehicleRegInfo(BgabGascbv01Dto dto);
	List<BgabGascbv01Dto> getSelectListBvToVehicleRegInfo(BgabGascbv01Dto dto);

	public int insertBvToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectBvToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectBvToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteBvToFile(List<BgabGascZ011Dto> list);
	public int updateInfoBvToReject (BgabGascbv02Dto dto);
	
	public int insertMaintenanceToFile (BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectMaintenanceToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectMaintenanceToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteMaintenanceToFile (List<BgabGascZ011Dto> bgabGascZ011List);
	
	public int updateInfoBvToApproveStatus(BgabGascbv02Dto dto);
	
	public String getSelectCountMaintenanceExpenseManagement(BgabGascbt06 bgabGascbt06);
	public List<BgabGascbt06> getSelectMaintenanceExpenseManagement(BgabGascbt06 bgabGascbt06);
	public int insertListToMaintenanceExpenseManagement (List<BgabGascbt06> bgabGascbt06);
	public int updateListToMaintenanceExpenseManagement (List<BgabGascbt06> bgabGascbt06);
	public int deleteListToMaintenanceExpenseManagement (List<BgabGascbt06> bgabGascbt06);
}
