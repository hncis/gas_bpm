package com.hncis.batch.job.dao.impl;

import com.hncis.batch.job.dao.JobDetailDao;
import com.hncis.businessTravel.vo.BgabGascbt01;
import com.hncis.businessVehicles.vo.BgabGascbv01Dto;
import com.hncis.common.dao.CommonDao;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.system.vo.BgabGascz006Dto;
import com.hncis.system.vo.BgabGascz008Dto;

// TODO: Auto-generated Javadoc
/**
 * The Class JobDetailDaoImplByIbatis.
 */
public class JobDetailDaoImplByIbatis extends CommonDao implements JobDetailDao  {
	
	/** The Constant INSERT_BGABGASCI001_BACKUP. - sql 인터페이스 사용자 백업에 테이블 insert. */
	private final static String INSERT_BGABGASCI001_BACKUP 			= "insertBgabGascI001Backup";
	
	private final static String DELETE_BGABGASCI001_BACKUP 			= "deleteBgabGascI001Backup";
	
	/** The Constant MERGE_BGABGASCZ002_BY_BATCH. - sql 인터페이스 -사용자 백업 테이블에 insert/update*/
	private final static String MERGE_BGABGASCZ002_BY_BATCH 		= "mergeBgabGascZ002ByBatch";
	
	/** The Constant DELETE_BGABGASCI001. - sql 인터페이스 -사용자 테이블에 delete.*/
	private final static String DELETE_BGABGASCI001 				= "deleteBgabGascI001";
	
	/** The Constant DELETE_BGABGASCZ002_TEMP. - sql 사용자 백업 테이블에 delete.*/
	private final static String DELETE_BGABGASCZ002_TEMP 			= "deleteBgabGascZ002Temp";
	
	/** The Constant INSERT_BGABGASCZ002_TEMP. - sql 사용자 백업 테이블에 insert.*/
	private final static String INSERT_BGABGASCZ002_TEMP 			= "insertBgabGascZ002Temp";
	
	/** The Constant INSERT_BGABGASCI002_BACKUP. - sql 인터페이스 - 조직 백업 테이블에 insert.*/
	private final static String INSERT_BGABGASCI002_BACKUP 			= "insertBgabGascI002Backup";
	
	private final static String DELETE_BGABGASCI002_BACKUP 			= "deleteBgabGascI002Backup";
	
	/** The Constant MERGE_BGABGASCZ003_BY_BATCH. - sql 조직 테이블에 insert/update.*/
	private final static String MERGE_BGABGASCZ003_BY_BATCH 		= "mergeBgabGascZ003ByBatch";
	
	/** The Constant DELETE_BGABGASCI002. - sql 인터페이스 -조직 테이블에 delete.*/
	private final static String DELETE_BGABGASCI002 				= "deleteBgabGascI002";
	
	/** The Constant DELETE_BGABGASCZ003_TEMP. - sql 조직 백업 테이블에 delete.*/
	private final static String DELETE_BGABGASCZ003_TEMP 			= "deleteBgabGascZ003Temp";
	
	/** The Constant INSERT_BGABGASCZ003_TEMP. - sql 조직 백업 테이블에 insert.*/
	private final static String INSERT_BGABGASCZ003_TEMP 			= "insertBgabGascZ003Temp";
	
	/** The Constant DELETE_BGABGASCZ008_TEMP. - sql 결재선 백업 테이블에 delete.*/
	private final static String DELETE_BGABGASCZ008_TEMP 			= "deleteBgabGascZ008Temp";
	
	/** The Constant INSERT_BGABGASCZ008_TEMP. - sql 결재선 백업 테이블에 insert.*/
	private final static String INSERT_BGABGASCZ008_TEMP 			= "insertBgabGascZ008Temp";
	
	/** The Constant SELECT_BGABGASCZ008_INFO. - sql 부서별 결재권자 조회.*/
	private final static String SELECT_BGABGASCZ008_INFO 			= "selectBgabGascZ008Info";
	
	/** The Constant MERGE_BGABGASCZ008_BY_BATCH. - sql 결재선 테이블 UPDATE/INSERT.*/
	private final static String MERGE_BGABGASCZ008_BY_BATCH 		= "mergeBgabGascZ008ByBatch";
	
	/** The Constant UPDATE_BGABGASCZ008_BY_UPPER_DEPT. - sql 결재선 테이블 - 부서를 조건으로 상위부서 UPDATE.*/
	private final static String UPDATE_BGABGASCZ008_BY_UPPER_DEPT 	= "updateBgabGascZ008ByUpperDept";
	
	private final static String DELETE_BGABGASCZ008_BY_EXPIRE 		= "deleteBgabGascZ008ByExpire";
	
	private final static String SELECT_COORDI_INFO 					= "selectCoordiInfo";
	
	/** 거리누적에 대한 메일발송 데이터 업데이트 UPDATE*/
	private final static String UPDATE_AS_VEHICLE_INFO 				= "updateAsVehicleInfo";
	
	private final static String DELETE_BGABGASCZ011_BY_FILEREMOVE 	= "deleteBgabGascZ011ByFileRemove";
	
	private final static String SELECT_COUNT_XGASC_INFO 			= "selectCountXgascInfo";
	
	/**
	 * 인터페이스 사용자 백업에 테이블 insert.
	 *
	 * @return void
	 */
	public void insertBgabGasci001Backup() {
		getSqlMapClientTemplate().insert(INSERT_BGABGASCI001_BACKUP);
	}
	
	public void deleteBgabGasci001Backup() {
		getSqlMapClientTemplate().insert(DELETE_BGABGASCI001_BACKUP);
	}
	
	/**
	 * 인터페이스 -사용자 백업 테이블에 insert/update
	 *
	 * @param dto the dto
	 * @return void
	 */
	public void mergeBgabGascz002(BgabGascz002Dto dto){
		getSqlMapClientTemplate().update(MERGE_BGABGASCZ002_BY_BATCH, dto);
	}
	
	/**
	 * 인터페이스 -사용자 테이블에 delete.
	 *
	 * @return void
	 */
	public void deleteBgabGasci001() {
		getSqlMapClientTemplate().delete(DELETE_BGABGASCI001);
	}
	
	/**
	 * 사용자 백업 테이블에 delete.
	 *
	 * @return void
	 */
	public void deleteBgabGascz002Temp() {
		getSqlMapClientTemplate().delete(DELETE_BGABGASCZ002_TEMP);
	}
	
	/**
	 * 사용자 백업 테이블에 insert.
	 *
	 * @return void
	 */
	public void insertBgabGascz002Temp() {
		getSqlMapClientTemplate().insert(INSERT_BGABGASCZ002_TEMP);
	}
	
	/**
	 * 인터페이스 - 조직 백업 테이블에 insert.
	 *
	 * @return void
	 */
	public void insertBgabGasci002Backup() {
		getSqlMapClientTemplate().insert(INSERT_BGABGASCI002_BACKUP);
	}
	
	public void deleteBgabGasci002Backup() {
		getSqlMapClientTemplate().delete(DELETE_BGABGASCI002_BACKUP);
	}
	
	/**
	 * 조직 테이블에 insert/update.
	 *
	 * @param dto the dto
	 * @return void
	 */
	public void mergeBgabGascz003(BgabGascz003Dto dto){
		getSqlMapClientTemplate().update(MERGE_BGABGASCZ003_BY_BATCH, dto);
	}
	
	/**
	 * 인터페이스 -조직 테이블에 delete.
	 *
	 * @return void
	 */
	public void deleteBgabGasci002() {
		getSqlMapClientTemplate().delete(DELETE_BGABGASCI002);
	}
	
	/**
	 * 조직 백업 테이블에 delete.
	 *
	 * @return void
	 */
	public void deleteBgabGascz003Temp() {
		getSqlMapClientTemplate().delete(DELETE_BGABGASCZ003_TEMP);
	}
	
	/**
	 * 조직 백업 테이블에 insert.
	 *
	 * @return void
	 */
	public void insertBgabGascz003Temp() {
		getSqlMapClientTemplate().insert(INSERT_BGABGASCZ003_TEMP);
	}
	
	/**
	 * 결재선 백업 테이블에 delete.
	 *
	 * @return void
	 */
	public void deleteBgabGascz008Temp() {
		getSqlMapClientTemplate().delete(DELETE_BGABGASCZ008_TEMP);
	}
	
	/**
	 * 결재선 백업 테이블에 insert.
	 *
	 * @return void
	 */
	public void insertBgabGascz008Temp() {
		getSqlMapClientTemplate().insert(INSERT_BGABGASCZ008_TEMP);
	}
	
	/**
	 * 부서별 결재권자 조회.
	 *
	 * @param bgabGascz008Dto the bgab gascz008 dto
	 * @return void
	 */
	public BgabGascz008Dto getBgabGascz008Info(BgabGascz008Dto bgabGascz008Dto){
		return (BgabGascz008Dto)getSqlMapClientTemplate().queryForObject(SELECT_BGABGASCZ008_INFO, bgabGascz008Dto);
	}
	
	/**
	 * 결재선 테이블 UPDATE/INSERT.
	 *
	 * @param dto the dto
	 * @return void
	 */
	public void mergeBgabGascz008(BgabGascz008Dto dto){
		getSqlMapClientTemplate().update(MERGE_BGABGASCZ008_BY_BATCH, dto);
	}
	
	/**
	 * 결재선 테이블 - 부서를 조건으로 상위부서 UPDATE.
	 *
	 * @param dto the dto
	 * @return void
	 */
	public void updateBgabGascz008ByUpperDept(BgabGascz008Dto dto){
		getSqlMapClientTemplate().update(UPDATE_BGABGASCZ008_BY_UPPER_DEPT, dto);
	}
	
	public void deleteBgabGascz008ByExpire(){
		getSqlMapClientTemplate().delete(DELETE_BGABGASCZ008_BY_EXPIRE);
	}
	
	public BgabGascz008Dto getCoordiInfo(BgabGascz008Dto bgabGascz008Dto){
		return (BgabGascz008Dto)getSqlMapClientTemplate().queryForObject(SELECT_COORDI_INFO, bgabGascz008Dto);
	}

	@Override
	public int updateAsVehicleInfo(BgabGascbv01Dto bgabGascbv01Dto) {
		return getSqlMapClientTemplate().update(UPDATE_AS_VEHICLE_INFO, bgabGascbv01Dto);
	}
	
	public void deleteBgabGascZ011(BgabGascZ011Dto dto){
		getSqlMapClientTemplate().delete(DELETE_BGABGASCZ011_BY_FILEREMOVE, dto);
	}
	
	public int selectCountXgascInfo(BgabGascz006Dto dto){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_XGASC_INFO, dto));
	}

}
