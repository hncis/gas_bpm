package com.hncis.batch.job.manager;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.businessVehicles.vo.BgabGascbv01Dto;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.system.vo.BgabGascz006Dto;
import com.hncis.system.vo.BgabGascz008Dto;

@Transactional
public interface JobDetailManager {
	
	public void insertBgabGasci001Backup();
	
	public void deleteBgabGasci001Backup();
	
	public void mergeBgabGascz002(BgabGascz002Dto dto);
	
	public void deleteBgabGasci001();
	
	public void deleteBgabGascz002Temp();
	
	public void insertBgabGascz002Temp();
	
	public void insertBgabGasci002Backup();
	
	public void deleteBgabGasci002Backup();
	
	public void mergeBgabGascz003(BgabGascz003Dto dto);
	
	public void deleteBgabGasci002();
	
	public void deleteBgabGascz003Temp();
	
	public void insertBgabGascz003Temp();
	
	public void deleteBgabGascz008Temp();
	
	public void insertBgabGascz008Temp();
	
	public BgabGascz008Dto getBgabGascz008Info(BgabGascz008Dto bgabGascz008Dto);
	
	public void updateApprovalerChangeByBatch(BgabGascz008Dto bgabGascz008Dto);
	
	public void updateApprovalerChangeDetailByBatch(BgabGascz008Dto bgabGascz008Dto);
	
	public void mergeBgabGascz008(BgabGascz008Dto dto);
	
	public void updateBgabGascz008ByUpperDept(BgabGascz008Dto dto);
	
	public void deleteBgabGascz008ByExpire();
	
	public BgabGascz008Dto getCoordiInfo(BgabGascz008Dto bgabGascz008Dto);

	public int updateAsVehicleInfo(BgabGascbv01Dto bgabGascbv01Dto);
	
	public void deleteBgabGascZ011(BgabGascZ011Dto dto);
	
	public int selectCountXgascInfo(BgabGascz006Dto dto);
}
