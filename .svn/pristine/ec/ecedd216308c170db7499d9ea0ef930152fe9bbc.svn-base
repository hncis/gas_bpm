package com.hncis.batch.job.dao;

import com.hncis.businessVehicles.vo.BgabGascbv01Dto;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.system.vo.BgabGascz006Dto;
import com.hncis.system.vo.BgabGascz008Dto;

public interface JobDetailDao {
	
	/**
	 * 인터페이스 사용자 백업에 테이블 insert
	 * @param 
	 * @return void
	 */
	public void insertBgabGasci001Backup();
	
	public void deleteBgabGasci001Backup();

	/**
	 * 인터페이스 -사용자 백업 테이블에 insert
	 * @param 
	 * @return void
	 */
	public void mergeBgabGascz002(BgabGascz002Dto dto);
	
	/**
	 * 인터페이스 -사용자 테이블에 delete
	 * @param 
	 * @return void
	 */
	public void deleteBgabGasci001();
	
	/**
	 * 사용자 백업 테이블에 delete
	 * @param 
	 * @return void
	 */
	public void deleteBgabGascz002Temp();
	
	/**
	 * 사용자 백업 테이블에 insert
	 * @param 
	 * @return void
	 */
	public void insertBgabGascz002Temp();
	
	/**
	 * 인테페이스 - 조직 백업 테이블에 insert
	 * @param 
	 * @return void
	 */
	public void insertBgabGasci002Backup();
	
	public void deleteBgabGasci002Backup();
	
	/**
	 * 조직 테이블에 insert/update
	 * @param 
	 * @return void
	 */
	public void mergeBgabGascz003(BgabGascz003Dto dto);
	
	/**
	 * 인터페이스 -조직 테이블에 delete
	 * @param 
	 * @return void
	 */
	public void deleteBgabGasci002();
	
	/**
	 * 조직 백업 테이블에 delete
	 * @param 
	 * @return void
	 */
	public void deleteBgabGascz003Temp();
	
	/**
	 * 조직 백업 테이블에 insert
	 * @param 
	 * @return void
	 */
	public void insertBgabGascz003Temp();
	
	/**
	 * 결재선 백업 테이블에 delete
	 * @param 
	 * @return void
	 */
	public void deleteBgabGascz008Temp();
	
	/**
	 * 결재선 백업 테이블에 insert
	 * @param 
	 * @return void
	 */
	public void insertBgabGascz008Temp();
	
	/**
	 * 부서별 결재권자 조회
	 * @param 
	 * @return void
	 */
	public BgabGascz008Dto getBgabGascz008Info(BgabGascz008Dto bgabGascz008Dto);
	
	/**
	 * 결재선 테이블 UPDATE/INSERT
	 * @param 
	 * @return void
	 */
	public void mergeBgabGascz008(BgabGascz008Dto dto);
	
	/**
	 * 결재선 테이블 - 부서를 조건으로 상위부서 UPDATE
	 * @param 
	 * @return void
	 */
	public void updateBgabGascz008ByUpperDept(BgabGascz008Dto dto);
	
	public void deleteBgabGascz008ByExpire();
	
	public BgabGascz008Dto getCoordiInfo(BgabGascz008Dto bgabGascz008Dto);
	/**
	 * 거리누적에 대한 메일발송 데이터 업데이트
	 */
	public int updateAsVehicleInfo(BgabGascbv01Dto bgabGascbv01Dto);
	
	public void deleteBgabGascZ011(BgabGascZ011Dto dto);
	
	public int selectCountXgascInfo(BgabGascz006Dto dto);
	
}
