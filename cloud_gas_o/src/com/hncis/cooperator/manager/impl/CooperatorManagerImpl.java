package com.hncis.cooperator.manager.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.cooperator.dao.CooperatorDao;
import com.hncis.cooperator.manager.CooperatorManager;
import com.hncis.cooperator.vo.BgabGascco01Dto;
import com.hncis.cooperator.vo.BgabGascco02Dto;

@Service("cooperatorManagerImpl")
public class CooperatorManagerImpl implements CooperatorManager{
    private transient Log logger = LogFactory.getLog(getClass());

	@Autowired
	public CooperatorDao cooperatorDao;

	@Autowired
	public CommonJobDao commonJobDao;
	
	public String selectXcoCorpIdx(BgabGascco01Dto co01Dto) {
		return cooperatorDao.selectXcoCorpIdx(co01Dto);
	}
	
	/**
	 * 협력업체 등록 
	 */
	@Override
	public int insertXcoToRequest(BgabGascco01Dto co01Dto, List<BgabGascco02Dto> co02Dto) {
		int basicCnt = cooperatorDao.mergeXcoToRequestByBasic(co01Dto);
		int picCnt = 0;
		for(int i = 0; i < co02Dto.size(); i++) {
			BgabGascco02Dto vo = co02Dto.get(i);
			vo.setCorp_cd(co01Dto.getCorp_cd());
			vo.setCorp_idx(co01Dto.getCorp_idx());
			vo.setIpe_eeno(co01Dto.getIpe_eeno());
			vo.setUpdr_eeno(co01Dto.getUpdr_eeno());
			picCnt = cooperatorDao.mergeXcoToRequestByPic(vo);
			
			// BPM API호출
			String processCode = "P-E-005"; 	//프로세스 코드 (협력업체 신청 프로세스) - 프로세스 정의서 참조
			String bizKey = co01Dto.getCorp_cd();	//회사 코드 번호
			String statusCode = "GASBZ01550010";	//액티비티 코드 (협력업체 신청서작성) - 프로세스 정의서 참조
			String loginUserId = co01Dto.getIpe_eeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = "";   //명함신청 담당자 역할코드
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add("10000001");
			
			String msg = BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
			
			logger.info(msg);
		}
		
		return basicCnt+picCnt; 
	}
	
	/**
	 * 협력업체  삭제
	 */
	@Override
	public int deleteXcoToRequest(BgabGascco01Dto co01Dto) {
		
		// BPM API호출
		String processCode = "P-E-005"; 	//프로세스 코드 (협력업체 프로세스) - 프로세스 정의서 참조
		String bizKey = co01Dto.getCorp_cd();	//신청서 번호
		String statusCode = "GASBZ01550010";	//액티비티 코드 (협력업체 신청서작성) - 프로세스 정의서 참조
		String loginUserId = co01Dto.getIpe_eeno();	//로그인 사용자 아이디
		
		BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
		return cooperatorDao.deleteXcoToRequest(co01Dto);
	}
	
	/**
	 * 협력업체 담당자 삭제
	 */
	@Override
	public int deleteXcoPicToRequest(BgabGascco02Dto co02Dto) {
		return cooperatorDao.deleteXcoPicToRequest(co02Dto);
	}
	
	/**
	 * 협력업체 담당자 삭제(그리드)
	 */
	@Override
	public int deleteXcoPicToList(List<BgabGascco02Dto> co02ListDto) {
		return cooperatorDao.deleteXcoPicToList(co02ListDto);
	}
	
	/**
	 * 협력업체 조회
	 */
	@Override
	public BgabGascco01Dto selectInfoXcoToRequest(BgabGascco01Dto co01Dto) {
		return cooperatorDao.selectInfoXcoToRequest(co01Dto);
	}
	
	/**
	 * 협력업체 담당자 조회
	 */
	@Override
	public List<BgabGascco02Dto> selectPicListXcoToRequest(BgabGascco02Dto co02Dto) {
		return cooperatorDao.selectPicListXcoToRequest(co02Dto);
	}
	
	/**
	 * 협력업체 리스트 조회
	 */
	public List<BgabGascco02Dto> selectXcoToList(BgabGascco01Dto co01Dto) {
		return cooperatorDao.selectXcoToList(co01Dto);
	}
}
