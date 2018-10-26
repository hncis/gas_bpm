package com.hncis.batch.job.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hncis.batch.job.dao.JobDetailDao;
import com.hncis.batch.job.manager.JobDetailManager;
import com.hncis.businessVehicles.vo.BgabGascbv01Dto;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.system.dao.SystemDao;
import com.hncis.system.vo.BgabGascz006Dto;
import com.hncis.system.vo.BgabGascz008Dto;

// TODO: Auto-generated Javadoc
/**
 * The Class JobDetailManagerImpl. - JobDetail 비지니스 로직 실제 수행 클래스
 */
@Service("jobDetailManagerImpl")
public class JobDetailManagerImpl implements JobDetailManager {
	
	/** The job detail dao. bus transportation 데이터 접근 객체 */
	@Autowired
	private JobDetailDao jobDetailDao;
	
	/** The system dao. system 데이터 접근 객체 */
	@Autowired
	public SystemDao systemDao;
	
	/**
	 * 인터페이스 사용자 백업에 테이블 insert.
	 */
	public void insertBgabGasci001Backup(){
		jobDetailDao.insertBgabGasci001Backup();
	}
	
	public void deleteBgabGasci001Backup(){
		jobDetailDao.deleteBgabGasci001Backup();
	}
	
	/**
	 * 인터페이스 -사용자 백업 테이블에 insert/update
	 */
	public void mergeBgabGascz002(BgabGascz002Dto dto){
		jobDetailDao.mergeBgabGascz002(dto);
	}
	
	/**
	 * 인터페이스 -사용자 테이블에 delete.
	 */
	public void deleteBgabGasci001(){
		jobDetailDao.deleteBgabGasci001();
	}
	
	/**
	 * 사용자 백업 테이블에 delete.
	 */
	public void deleteBgabGascz002Temp(){
		jobDetailDao.deleteBgabGascz002Temp();
	}
	
	/**
	 * 사용자 백업 테이블에 insert.
	 */
	public void insertBgabGascz002Temp(){
		jobDetailDao.insertBgabGascz002Temp();
	}
	
	/**
	 * 인터페이스 - 조직 백업 테이블에 insert.
	 */
	public void insertBgabGasci002Backup(){
		jobDetailDao.insertBgabGasci002Backup();
	}
	
	public void deleteBgabGasci002Backup(){
		jobDetailDao.deleteBgabGasci002Backup();
	}
	
	/**
	 * 조직 테이블에 insert/update.
	 * @param dto- 조회조건
	 * @return List<BgabGascBS01>
	 */
	public void mergeBgabGascz003(BgabGascz003Dto dto){
		jobDetailDao.mergeBgabGascz003(dto);
	}
	
	/**
	 * 인터페이스 -조직 테이블에 delete.
	 */
	public void deleteBgabGasci002(){
		jobDetailDao.deleteBgabGasci002();
	}
	
	/**
	 * 조직 백업 테이블에 delete.
	 */
	public void deleteBgabGascz003Temp(){
		jobDetailDao.deleteBgabGascz003Temp();
	}
	
	/**
	 * 조직 백업 테이블에 insert.
	 */
	public void insertBgabGascz003Temp(){
		jobDetailDao.insertBgabGascz003Temp();
	}
	
	/**
	 * 결재선 백업 테이블에 delete.
	 */
	public void deleteBgabGascz008Temp(){
		jobDetailDao.deleteBgabGascz008Temp();
	}
	
	/**
	 * 결재선 백업 테이블에 insert.
	 */
	public void insertBgabGascz008Temp(){
		jobDetailDao.insertBgabGascz008Temp();
	}
	
	/**
	 * 부서별 결재권자 조회.
	 * @param dto- 조회조건
	 */
	public BgabGascz008Dto getBgabGascz008Info(BgabGascz008Dto dto){
		return jobDetailDao.getBgabGascz008Info(dto);
	}
	
	/**
	 * 조회 - 버스 루트 콤보 박스
	 * @param dto- 조건
	 */
	public void updateApprovalerChangeByBatch(BgabGascz008Dto dto){
		systemDao.updateToMyApprovalForDeputeByApprovalInfo(dto);
	}
	
	/**
	 * 조회 - 버스 루트 콤보 박스
	 * @param dto- 조건
	 */
	public void updateApprovalerChangeDetailByBatch(BgabGascz008Dto dto){
		systemDao.updateToMyApprovalForDeputeByApprovalInfoDetail(dto);
	}
	
	/**
	 * 조회 - 버스 루트 콤보 박스
	 * @param dto- 조건
	 */
	public void mergeBgabGascz008(BgabGascz008Dto dto){
		jobDetailDao.mergeBgabGascz008(dto);
	}
	
	/**
	 * 조회 - 버스 루트 콤보 박스
	 * @param dto- 조건
	 */
	public void updateBgabGascz008ByUpperDept(BgabGascz008Dto dto){
		jobDetailDao.updateBgabGascz008ByUpperDept(dto);
	}
	
	public void deleteBgabGascz008ByExpire(){
		jobDetailDao.deleteBgabGascz008ByExpire();
	}
	
	public BgabGascz008Dto getCoordiInfo(BgabGascz008Dto bgabGascz008Dto){
		return jobDetailDao.getCoordiInfo(bgabGascz008Dto);
	}
	/**
	 * 거리누적에 대한 메일발송 데이터 업데이트
	 */
	public int updateAsVehicleInfo(BgabGascbv01Dto bgabGascbv01Dto) {
		return jobDetailDao.updateAsVehicleInfo(bgabGascbv01Dto);
	}
	
	public void deleteBgabGascZ011(BgabGascZ011Dto dto){
		jobDetailDao.deleteBgabGascZ011(dto);
	}
	
	public int selectCountXgascInfo(BgabGascz006Dto dto){
		return jobDetailDao.selectCountXgascInfo(dto);
	}
}
