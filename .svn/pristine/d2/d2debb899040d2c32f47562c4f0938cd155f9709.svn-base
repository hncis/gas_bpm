package com.hncis.dashBoard.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hncis.dashBoard.dao.DashBoardDao;
import com.hncis.dashBoard.manager.DashBoardManager;
import com.hncis.dashBoard.vo.DashBoardVo;

@Service("dashBoardManagerImpl")
public class DashBoardManagerImpl implements DashBoardManager{

	@Autowired
	public DashBoardDao dashBoardDao;

	@Override
	public List<DashBoardVo> selectDbToDeptCombo(DashBoardVo dto) {
		return dashBoardDao.selectDbToDeptCombo(dto);
	}

	@Override
	public List<DashBoardVo> selectDbToDashBoard(DashBoardVo dto) {
		List<DashBoardVo> list = null;
		if(dto.getKey_category().equals("ET")){
			list = dashBoardDao.selectDbToDashBoardForEt(dto);
		}else if(dto.getKey_category().equals("VE")){
			list = dashBoardDao.selectDbToDashBoardForVe(dto);
		}else if(dto.getKey_category().equals("ML")){
			list = dashBoardDao.selectDbToDashBoardForMl(dto);
		}else if(dto.getKey_category().equals("IT")){
			list = dashBoardDao.selectDbToDashBoardForIt(dto);
		}else if(dto.getKey_category().equals("FP")){
			list = dashBoardDao.selectDbToDashBoardForFp(dto);
		}else if(dto.getKey_category().equals("RM")){
			list = dashBoardDao.selectDbToDashBoardForRm(dto);
		}else if(dto.getKey_category().equals("BC")){
			list = dashBoardDao.selectDbToDashBoardForBc(dto);
		}else if(dto.getKey_category().equals("TX")){
			list = dashBoardDao.selectDbToDashBoardForTx(dto);
		}else if(dto.getKey_category().equals("TW")){
			list = dashBoardDao.selectDbToDashBoardForTw(dto);
		}else if(dto.getKey_category().equals("SB")){
			list = dashBoardDao.selectDbToDashBoardForSb(dto);
		}else if(dto.getKey_category().equals("BT")){
			list = dashBoardDao.selectDbToDashBoardForBt(dto);
		}else if(dto.getKey_category().equals("CC")){
			list = dashBoardDao.selectDbToDashBoardForCc(dto);
		}else if(dto.getKey_category().equals("PS")){
			list = dashBoardDao.selectDbToDashBoardForPs(dto);
		}else if(dto.getKey_category().equals("GS")){
			list = dashBoardDao.selectDbToDashBoardForGs(dto);
		}else if(dto.getKey_category().equals("OS")){
			list = dashBoardDao.selectDbToDashBoardForOs(dto);
		}else if(dto.getKey_category().equals("PO")){
			list = dashBoardDao.selectDbToDashBoardForPo(dto);
		}else if(dto.getKey_category().equals("TT")){
			list = dashBoardDao.selectDbToDashBoardForTt(dto);
		}else if(dto.getKey_category().equals("CE")){
			list = dashBoardDao.selectDbToDashBoardForCe(dto);
		}else if(dto.getKey_category().equals("VL")){
			list = dashBoardDao.selectDbToDashBoardForVl(dto);
		}else if(dto.getKey_category().equals("RC")){
			list = dashBoardDao.selectDbToDashBoardForRc(dto);
		}else if(dto.getKey_category().equals("TR")){
			list = dashBoardDao.selectDbToDashBoardForTr(dto);
		}else if(dto.getKey_category().equals("FC")){
			list = dashBoardDao.selectDbToDashBoardForFc(dto);
		}else if(dto.getKey_category().equals("LV")){
			list = dashBoardDao.selectDbToDashBoardForLv(dto);
		}else if(dto.getKey_category().equals("FJ")){
			list = dashBoardDao.selectDbToDashBoardForFJ(dto);
		}


		return list;
	}

}
